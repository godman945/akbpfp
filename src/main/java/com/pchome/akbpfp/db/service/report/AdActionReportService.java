package com.pchome.akbpfp.db.service.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.pchome.akbpfp.db.dao.report.AdActionReportVO;
import com.pchome.akbpfp.db.dao.report.AdCampaginReportVO;
import com.pchome.akbpfp.db.dao.report.IAdActionReportDAO;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.service.ad.IPfpAdActionService;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.enumerate.report.EnumReportDevice;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.utils.CommonUtils;

public class AdActionReportService implements IAdActionReportService {

	private IPfpAdActionService adActionService;
	private IAdActionReportDAO adActionReportDAO;

	public void setAdActionService(IPfpAdActionService adActionService) {
		this.adActionService = adActionService;
	}

	public void setAdActionReportDAO(IAdActionReportDAO adActionReportDAO) {
		this.adActionReportDAO = adActionReportDAO;
	}

	public List<AdActionReportVO> loadReportDate(String sqlType, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate,
			int page,int pageSize) {
		return adActionReportDAO.getReportList(sqlType,searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);
	}

	/**
	 * 每日花費成效(明細)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public List<AdActionReportVO> queryReportAdDailyData(AdActionReportVO vo) {
		List<Map<String, Object>> adDailyList = adActionReportDAO.getAdDailyList(vo);
		List<AdActionReportVO> adDailyVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adDailyList) {
			AdActionReportVO adActionReportVO = new AdActionReportVO();
			
			// 日期
			adActionReportVO.setReportDate((Date) dataMap.get("ad_pvclk_date"));
			
//			// 裝置
//			String adDevice = "全部";
//			if (vo.getWhereMap() != null) {
//				adDevice = CommonUtils.getInstance().getAdDeviceName(vo.getWhereMap());
//			}
//			adActionReportVO.setAdDevice(adDevice);
			
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adActionReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adActionReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adActionReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adActionReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adActionReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
			BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adActionReportVO.setKiloCost(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adActionReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adActionReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adActionReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adActionReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adActionReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
			
			adDailyVOList.add(adActionReportVO);
		}
		
		// 處理排序
		if (StringUtils.isNotBlank(vo.getSortBy())) {
			CommonUtils.getInstance().sort(adDailyVOList, vo.getSortBy().split("-")[0], vo.getSortBy().split("-")[1]);
		}
		
		return adDailyVOList;
	}
	
	/**
	 * 每日花費成效(加總)
	 * @param vo
	 * @return
	 */
	public List<AdActionReportVO> queryReportAdDailySumData(AdActionReportVO vo) {
		List<Map<String, Object>> adDailyListSum = adActionReportDAO.getAdDailyListSum(vo);
		
		// 曝光數
		BigDecimal adPvSum = new BigDecimal(0);
		// 互動數
		BigDecimal adClkSum = new BigDecimal(0);
		// 費用
		BigDecimal adPriceSum = new BigDecimal(0);
		// 轉換次數
		BigDecimal convertCount = new BigDecimal(0);
		// 總轉換價值
		BigDecimal convertPriceCount = new BigDecimal(0);
		
		List<AdActionReportVO> adDailyVOListSum = new ArrayList<>();
		// 加總
		for (Map<String, Object> dataMap : adDailyListSum) {
			adPvSum = adPvSum.add((BigDecimal) dataMap.get("ad_pv_sum"));
			adClkSum = adClkSum.add((BigDecimal) dataMap.get("ad_clk_sum"));
			adPriceSum = adPriceSum.add(BigDecimal.valueOf((Double) dataMap.get("ad_price_sum")));
			convertCount = convertCount.add((BigDecimal) dataMap.get("convert_count"));
			convertPriceCount = convertPriceCount.add((BigDecimal) dataMap.get("convert_price_count"));
		}
		
		AdActionReportVO adActionReportVO = new AdActionReportVO();
		// 曝光數
		adActionReportVO.setAdPvSum(adPvSum);
		
		// 互動數
		adActionReportVO.setAdClkSum(adClkSum);
		
		// 互動率 = 總互動數 / 總曝光數 * 100
		adActionReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
		
		// 費用
		adActionReportVO.setAdPriceSum(adPriceSum.doubleValue());
		
		// 單次互動費用 = 總費用 / 總互動次數
		adActionReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
		
		// 千次曝光費用 = 總費用 / 曝光數 * 1000
		Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
		BigDecimal bg = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
		adActionReportVO.setKiloCost(bg.setScale(2, RoundingMode.HALF_UP).doubleValue());
		
		// 轉換次數
		adActionReportVO.setConvertCount(convertCount);
		
		// 轉換率 = 轉換次數 / 互動數 * 100
		adActionReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
		
		// 總轉換價值
		adActionReportVO.setConvertPriceCount(convertPriceCount);
		
		// 平均轉換成本 = 費用 / 轉換次數
		adActionReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
		
		// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
		adActionReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
		
		// 總計幾筆
		adActionReportVO.setRowCount(adDailyListSum.size());
		vo.setRowCount(adDailyListSum.size()); // 計算底下頁碼用
		
		adDailyVOListSum.add(adActionReportVO);
		
		return adDailyVOListSum;
	}
	
	/**
	 * 每日花費成效(圖表)
	 * @param vo
	 * @return list
	 */
	public List<AdActionReportVO> queryReportAdDailyChartData(AdActionReportVO vo) {
		return queryReportAdDailyData(vo);
	}
	
	/**
	 * 每日花費成效(圖表)
	 * @param vo
	 * @return map
	 */
	public Map<Date, Float> queryReportAdDailyChartDataMap(AdActionReportVO vo) {
		List<Map<String, Object>> adDailyList = adActionReportDAO.getAdDailyList(vo);
		
		String charType = vo.getCharType();
		Map<Date, Float> flashDataMap = new HashMap<>();
		for (Map<String, Object> dataMap : adDailyList) {
			
			// 日期
			Date reportDate = (Date) dataMap.get("ad_pvclk_date");
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			
			if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
				flashDataMap.put(reportDate, adPvSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
				flashDataMap.put(reportDate, adClkSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
				// 互動率 = 總互動數 / 總曝光數 * 100
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
				// 單次互動費用 = 總費用 / 總互動次數
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
				// 千次曝光費用 = 總費用 / 曝光數 * 1000
				Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
				BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
				flashDataMap.put(reportDate, bigDecimal.setScale(2, RoundingMode.HALF_UP).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
				flashDataMap.put(reportDate, adPriceSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())) {
				flashDataMap.put(reportDate, convertCount.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue())) {
				// 轉換率 = 轉換次數 / 互動數 * 100
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue())) {
				flashDataMap.put(reportDate, convertPriceCount.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_COST.getTextValue())) {
				// 平均轉換成本 = 費用 / 轉換次數
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue())) {
				// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100).floatValue());
			}
		}
		
		return flashDataMap;
	}

	/**
	 * 總廣告成效、廣告成效共用(明細)
	 * @throws Exception 
	 */
	@Override
	public List<AdCampaginReportVO> queryReportAdCampaginData(AdCampaginReportVO vo) throws Exception {
		List<Map<String, Object>> adCampaginList = adActionReportDAO.getAdCampaginList(vo);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		long nowTime = new Date().getTime();
		
		Map<Integer, String> adTypeMap = CommonUtils.getInstance().getAdType();
		Map<String, String> adStyleTypeMap = CommonUtils.getInstance().getAdStyleTypeMap();
		Map<String, String> adStatusMap = CommonUtils.getInstance().getAdStatusMap();
		
		// 檢查前端畫面選擇的篩選條件
		JSONObject tempJSONObject = new JSONObject();
		if(vo.getWhereMap() != null) {
			tempJSONObject = new JSONObject(vo.getWhereMap());
		}
		String selectAdDevice = tempJSONObject.optString("adDevice");
		
		List<AdCampaginReportVO> adCampaginVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adCampaginList) {
			AdCampaginReportVO adCampaginReportVO = new AdCampaginReportVO();
			
			String adActionSeq = (String) dataMap.get("ad_action_seq");
			PfpAdAction pfpAdAction =  adActionService.getPfpAdActionBySeq(adActionSeq);
			String adActionName = pfpAdAction.getAdActionName();
		    int adActionStatus = pfpAdAction.getAdActionStatus();
		    String adActionStartDate = dateFormat2.format(pfpAdAction.getAdActionStartDate()); // 走期開始日
		    String adActionEndDate = dateFormat2.format(pfpAdAction.getAdActionEndDate()); // 走期結束日
			
			//狀態為開啟的話必須判斷走期( 待播放 or 走期中 or 已結束 )
			if (adActionStatus == EnumStatus.Open.getStatusId()) {
				long startDate = (dateFormat.parse(adActionStartDate + " 00:00:00")).getTime();
				long endDate = (dateFormat.parse(adActionEndDate + " 23:59:59")).getTime();
				if (nowTime < startDate) {
					adActionStatus = EnumStatus.Waitbroadcast.getStatusId();
				} else if (nowTime > endDate) {
					adActionStatus = EnumStatus.End.getStatusId();
				} else {
					adActionStatus = EnumStatus.Broadcast.getStatusId();
				}
			}

			// 播放狀態
			String alter = "";
			if (adActionStatus == EnumStatus.Broadcast.getStatusId()) { // 走期中
				alter = "走期中";
				adCampaginReportVO.setAdStatusOnOff(true); // on亮綠燈
			} else {
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus));
			}
			adCampaginReportVO.setAdStatusName(alter); // 產excel報表使用
			
			adCampaginReportVO.setAdActionName(adActionName); // 廣告活動
			adCampaginReportVO.setAdActionSeq(adActionSeq); // 廣告活動序號 (總廣告成效使用到)
			adCampaginReportVO.setAdType(adTypeMap.get(dataMap.get("ad_type"))); // 播放類型
			adCampaginReportVO.setAdOperatingRule(adStyleTypeMap.get(dataMap.get("ad_operating_rule"))); // 廣告樣式
			
			// 走期
			if ("3000-12-31".equals(adActionEndDate)) {
				adActionEndDate = "永久";
			}
			adCampaginReportVO.setAdActionStartDate(adActionStartDate);
			adCampaginReportVO.setAdActionEndDate(adActionEndDate);
			
			// 裝置
			if (EnumReportDevice.PCANDMOBILE.getDevType().equalsIgnoreCase(selectAdDevice)) {
				adCampaginReportVO.setAdDevice(EnumReportDevice.PCANDMOBILE.getDevTypeName());
			} else {
				String adDevice = (String) dataMap.get("ad_device");
				if (EnumReportDevice.PC.getDevType().equalsIgnoreCase(adDevice)) {
					adDevice = EnumReportDevice.PC.getDevTypeName();
				} else if (EnumReportDevice.MOBILE.getDevType().equalsIgnoreCase(adDevice)) {
					adDevice = EnumReportDevice.MOBILE.getDevTypeName();
				}
				adCampaginReportVO.setAdDevice(adDevice);
			}
			
			// 每日預算
			BigDecimal adActionMaxPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_action_max_price_sum"));
			BigDecimal adActionCountSum = (BigDecimal) dataMap.get("ad_action_count_sum");
			adCampaginReportVO.setAdActionMaxPriceAvg(CommonUtils.getInstance().getCalculateDivisionValue(adActionMaxPriceSum, adActionCountSum));
			
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adCampaginReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adCampaginReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adCampaginReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
			
			// 無效點選次數(總廣告成效用)
			adCampaginReportVO.setAdInvClkSum((BigDecimal) dataMap.get("ad_invalid_clk_sum"));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adCampaginReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adCampaginReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
			BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adCampaginReportVO.setKiloCost(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adCampaginReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adCampaginReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adCampaginReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adCampaginReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adCampaginReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
			
			adCampaginVOList.add(adCampaginReportVO);
		}
		
		// 處理排序
		if (StringUtils.isNotBlank(vo.getSortBy())) {
			CommonUtils.getInstance().sort(adCampaginVOList, vo.getSortBy().split("-")[0], vo.getSortBy().split("-")[1]);
		}
		
		return adCampaginVOList;
	}

	/**
	 * 總廣告成效、廣告成效共用(加總)
	 */
	@Override
	public List<AdCampaginReportVO> queryReportAdCampaginSumData(AdCampaginReportVO vo) {
		List<Map<String, Object>> adCampaginListSum = adActionReportDAO.getAdCampaginListSum(vo);
		
		// 每日預算
		BigDecimal adActionMaxPriceAvg = new BigDecimal(0);
		// 曝光數
		BigDecimal adPvSum = new BigDecimal(0);
		// 互動數
		BigDecimal adClkSum = new BigDecimal(0);
		// 無效點選次數
		BigDecimal adInvClkSum = new BigDecimal(0);
		// 費用
		BigDecimal adPriceSum = new BigDecimal(0);
		// 轉換次數
		BigDecimal convertCount = new BigDecimal(0);
		// 總轉換價值
		BigDecimal convertPriceCount = new BigDecimal(0);
		
		List<AdCampaginReportVO> adCampaginVOListSum = new ArrayList<>();
		// 加總
		for (Map<String, Object> dataMap : adCampaginListSum) {
			BigDecimal adActionMaxPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_action_max_price_sum"));
			BigDecimal adActionCountSum = (BigDecimal) dataMap.get("ad_action_count_sum");
			 // 算完每日預算後，再處理小數至第二位，然後四捨五入
			BigDecimal bigDecimal = BigDecimal.valueOf(CommonUtils.getInstance().getCalculateDivisionValue(adActionMaxPriceSum, adActionCountSum)).setScale(2, RoundingMode.HALF_UP);
			adActionMaxPriceAvg = adActionMaxPriceAvg.add(bigDecimal);
			
			adPvSum = adPvSum.add((BigDecimal) dataMap.get("ad_pv_sum"));
			adClkSum = adClkSum.add((BigDecimal) dataMap.get("ad_clk_sum"));
			adInvClkSum = adInvClkSum.add((BigDecimal) dataMap.get("ad_invalid_clk_sum"));
			adPriceSum = adPriceSum.add(BigDecimal.valueOf((Double) dataMap.get("ad_price_sum")));
			convertCount = convertCount.add((BigDecimal) dataMap.get("convert_count"));
			convertPriceCount = convertPriceCount.add((BigDecimal) dataMap.get("convert_price_count"));
		}
		
		AdCampaginReportVO adCampaginReportVO = new AdCampaginReportVO();
		// 每日預算
		adCampaginReportVO.setAdActionMaxPriceAvg(adActionMaxPriceAvg.doubleValue());
		
		// 曝光數
		adCampaginReportVO.setAdPvSum(adPvSum);
		
		// 互動數
		adCampaginReportVO.setAdClkSum(adClkSum);
		
		// 互動率 = 總互動數 / 總曝光數 * 100
		adCampaginReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
		
		// 無效點選次數(總廣告成效用)
		adCampaginReportVO.setAdInvClkSum(adInvClkSum);
		
		// 費用
		adCampaginReportVO.setAdPriceSum(adPriceSum.doubleValue());
		
		// 單次互動費用 = 總費用 / 總互動次數
		adCampaginReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
		
		// 千次曝光費用 = 總費用 / 曝光數 * 1000
		Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
		BigDecimal bg = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
		adCampaginReportVO.setKiloCost(bg.setScale(2, RoundingMode.HALF_UP).doubleValue());
		
		// 轉換次數
		adCampaginReportVO.setConvertCount(convertCount);
		
		// 轉換率 = 轉換次數 / 互動數 * 100
		adCampaginReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
		
		// 總轉換價值
		adCampaginReportVO.setConvertPriceCount(convertPriceCount);
		
		// 平均轉換成本 = 費用 / 轉換次數
		adCampaginReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
		
		// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
		adCampaginReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
		
		// 總計幾筆
		adCampaginReportVO.setRowCount(adCampaginListSum.size());
		vo.setRowCount(adCampaginListSum.size()); // 計算底下頁碼用
		
		adCampaginVOListSum.add(adCampaginReportVO);
		
		return adCampaginVOListSum;
	}

	/**
	 * 回傳List舊寫法
	 * 廣告成效(圖表)
	 * return List
	 */
	@Override
	public List<AdCampaginReportVO> queryReportAdCampaginChartData(AdCampaginReportVO vo) {
		List<Map<String, Object>> adCampaginList = adActionReportDAO.getAdCampaginListChart(vo);
		
		List<AdCampaginReportVO> adCampaginVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adCampaginList) {
			AdCampaginReportVO adCampaginReportVO = new AdCampaginReportVO();
			
			// 日期
			adCampaginReportVO.setReportDate((Date) dataMap.get("ad_pvclk_date"));

			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adCampaginReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adCampaginReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adCampaginReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adCampaginReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adCampaginReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
			BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adCampaginReportVO.setKiloCost(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adCampaginReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adCampaginReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adCampaginReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adCampaginReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adCampaginReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
			
			adCampaginVOList.add(adCampaginReportVO);
		}
		
		return adCampaginVOList;
	}

	/**
	 * 回傳map新寫法
	 * 總廣告成效、廣告成效共用(圖表)
	 * return map
	 */
	@Override
	public Map<Date, Float> queryReportAdCampaginChartDataMap(AdCampaginReportVO vo) {
		List<Map<String, Object>> adCampaginList = adActionReportDAO.getAdCampaginListChart(vo);
		
		String charType = vo.getCharType();
		Map<Date, Float> flashDataMap = new HashMap<>();
		for (Map<String, Object> dataMap : adCampaginList) {
			
			// 日期
			Date reportDate = (Date) dataMap.get("ad_pvclk_date");
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			// 無效點選次數(總廣告成效用)
			BigDecimal adInvClkSum = (BigDecimal) dataMap.get("ad_invalid_clk_sum");
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			
			if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
				flashDataMap.put(reportDate, adPvSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
				flashDataMap.put(reportDate, adClkSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
				// 互動率 = 總互動數 / 總曝光數 * 100
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_INV_CLK.getTextValue())) {
				flashDataMap.put(reportDate, adInvClkSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
				// 單次互動費用 = 總費用 / 總互動次數
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
				// 千次曝光費用 = 總費用 / 曝光數 * 1000
				Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
				BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
				flashDataMap.put(reportDate, bigDecimal.setScale(2, RoundingMode.HALF_UP).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
				flashDataMap.put(reportDate, adPriceSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())) {
				flashDataMap.put(reportDate, convertCount.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue())) {
				// 轉換率 = 轉換次數 / 互動數 * 100
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue())) {
				flashDataMap.put(reportDate, convertPriceCount.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_COST.getTextValue())) {
				// 平均轉換成本 = 費用 / 轉換次數
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue())) {
				// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100).floatValue());
			}
		}
		
		return flashDataMap;
	}

}