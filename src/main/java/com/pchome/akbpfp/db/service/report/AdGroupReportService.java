package com.pchome.akbpfp.db.service.report;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.pchome.akbpfp.db.dao.report.AdGroupReportVO;
import com.pchome.akbpfp.db.dao.report.IAdGroupReportDAO;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.service.ad.IPfpAdGroupService;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.enumerate.report.EnumReportAdType;
import com.pchome.enumerate.report.EnumReportDevice;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.utils.CommonUtils;

public class AdGroupReportService implements IAdGroupReportService {

	private IPfpAdGroupService adGroupService;
	private IAdGroupReportDAO adGroupReportDAO;

	public void setAdGroupService(IPfpAdGroupService adGroupService) {
		this.adGroupService = adGroupService;
	}
	
	public void setAdGroupReportDAO(IAdGroupReportDAO adGroupReportDAO) {
		this.adGroupReportDAO = adGroupReportDAO;
	}

	public List<AdGroupReportVO> loadReportDate(String sqlType, String adActionSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception {
		return adGroupReportDAO.getReportList(sqlType, adActionSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);
	}

	/**
	 * 總廣告成效-分類、分類成效共用(明細)
	 * @throws Exception 
	 */
	@Override
	public List<AdGroupReportVO> queryReportAdGroupData(AdGroupReportVO vo) throws Exception {
		List<Map<String, Object>> adGroupList = adGroupReportDAO.getAdGroupList(vo);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		long nowTime = new Date().getTime();
		
		Map<String, String> adStatusMap = CommonUtils.getInstance().getAdStatusMap();
		Map<String, String> adStyleTypeMap = CommonUtils.getInstance().getAdStyleTypeMap();
		Map<String, String> adPriceTypeMap = CommonUtils.getInstance().getAdPriceTypeMap();
		
		// 檢查前端畫面選擇的篩選條件
		JSONObject tempJSONObject = new JSONObject();
		if(vo.getWhereMap() != null) {
			tempJSONObject = new JSONObject(vo.getWhereMap());
		}
		String selectAdType = tempJSONObject.optString("adType"); // 播放類型
		String selectAdDevice = tempJSONObject.optString("adDevice"); // 裝置
		
		List<AdGroupReportVO> adGroupVOList = new ArrayList<>();

		for (Map<String, Object> dataMap : adGroupList) {
			AdGroupReportVO adGroupReportVO = new AdGroupReportVO();
			
			String adGroupSeq = (String) dataMap.get("ad_group_seq");
			PfpAdGroup pfpAdGroup = adGroupService.getPfpAdGroupBySeq(adGroupSeq);
			String adGroupName = pfpAdGroup.getAdGroupName();
			int adGroupStatus = pfpAdGroup.getAdGroupStatus();

			String adActionName = pfpAdGroup.getPfpAdAction().getAdActionName();
			int adActionStatus = pfpAdGroup.getPfpAdAction().getAdActionStatus();
			Date adActionStartDate = pfpAdGroup.getPfpAdAction().getAdActionStartDate();
			Date adActionEndDate = pfpAdGroup.getPfpAdAction().getAdActionEndDate();
			
			//廣告狀態為開啟的話必須判斷走期( 待播放 or 走期中 or 已結束 )
			if (adActionStatus == EnumStatus.Open.getStatusId()) {
				long startDate = (dateFormat.parse(dateFormat2.format(adActionStartDate) + " 00:00:00")).getTime();
				long endDate = (dateFormat.parse(dateFormat2.format(adActionEndDate) + " 23:59:59")).getTime();
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
			if (adActionStatus == EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus == EnumStatus.Open.getStatusId()) {
				alter = "走期中";
				adGroupReportVO.setAdStatusOnOff(true); // on亮綠燈
			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus == EnumStatus.Open.getStatusId()) {
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus));
			} else if (adActionStatus == EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus != EnumStatus.Open.getStatusId()) {
				alter = "分類" + adStatusMap.get(Integer.toString(adGroupStatus));
			} else {
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus)) + "，" + "分類" + adStatusMap.get(Integer.toString(adGroupStatus));
			}
			adGroupReportVO.setAdStatusName(alter); // 產excel報表使用
			
			adGroupReportVO.setAdActionName(adActionName); // 廣告活動
			adGroupReportVO.setAdGroupName(adGroupName); // 廣告分類
			adGroupReportVO.setAdGroupSeq(adGroupSeq); // 廣告分類序號 (總廣告成效使用到)
			
			// 播放類型
			if (EnumReportAdType.SEARCHANDCHANNEL.getAdType().equalsIgnoreCase(selectAdType)) {
				adGroupReportVO.setAdType(EnumReportAdType.SEARCHANDCHANNEL.getAdTypeName());
			} else {
				int adType = (int) dataMap.get("ad_type");
				String adTypeName = "";
				if (EnumReportAdType.SEARCH.getAdType().equalsIgnoreCase(String.valueOf(adType))) {
					adTypeName = EnumReportAdType.SEARCH.getAdTypeName();
				} else if (EnumReportAdType.CHANNEL.getAdType().equalsIgnoreCase(String.valueOf(adType))) {
					adTypeName = EnumReportAdType.CHANNEL.getAdTypeName();
				}
				adGroupReportVO.setAdType(adTypeName);
			}

			adGroupReportVO.setAdOperatingRule(adStyleTypeMap.get(dataMap.get("ad_operating_rule"))); // 廣告樣式
			adGroupReportVO.setAdClkPriceType(adPriceTypeMap.get(dataMap.get("ad_clk_price_type"))); // 廣告計費方式
			
			// 裝置
			if (EnumReportDevice.PCANDMOBILE.getDevType().equalsIgnoreCase(selectAdDevice)) {
				adGroupReportVO.setAdDevice(EnumReportDevice.PCANDMOBILE.getDevTypeName());
			} else {
				String adDevice = (String) dataMap.get("ad_device");
				if (EnumReportDevice.PC.getDevType().equalsIgnoreCase(adDevice)) {
					adDevice = EnumReportDevice.PC.getDevTypeName();
				} else if (EnumReportDevice.MOBILE.getDevType().equalsIgnoreCase(adDevice)) {
					adDevice = EnumReportDevice.MOBILE.getDevTypeName();
				}
				adGroupReportVO.setAdDevice(adDevice);
			}
			
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adGroupReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adGroupReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adGroupReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2));
			
			// 無效點選次數(總廣告成效用)
			adGroupReportVO.setAdInvClkSum((BigDecimal) dataMap.get("ad_invalid_clk_sum"));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adGroupReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adGroupReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adClkSum, 2));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			adGroupReportVO.setKiloCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adPvSum, 1000, 2));
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adGroupReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adGroupReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertCount, adClkSum, 100, 2));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adGroupReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adGroupReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, convertCount, 2));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adGroupReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertPriceCount, adPriceSum, 100, 2));
			
			adGroupVOList.add(adGroupReportVO);
		}
		
		// 處理排序
		if (StringUtils.isNotBlank(vo.getSortBy())) {
			CommonUtils.getInstance().sort(adGroupVOList, vo.getSortBy().split("-")[0], vo.getSortBy().split("-")[1]);
		}
		return adGroupVOList;
	}

	/**
	 * 總廣告成效-分類、分類成效共用(加總)
	 */
	@Override
	public List<AdGroupReportVO> queryReportAdGroupSumData(AdGroupReportVO vo) {
		List<Map<String, Object>> adGroupListSum = adGroupReportDAO.getAdGroupListSum(vo);
		
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
		
		List<AdGroupReportVO> adGroupVOListSum = new ArrayList<>();
		// 加總
		for (Map<String, Object> dataMap : adGroupListSum) {
			adPvSum = adPvSum.add((BigDecimal) dataMap.get("ad_pv_sum"));
			adClkSum = adClkSum.add((BigDecimal) dataMap.get("ad_clk_sum"));
			adInvClkSum = adInvClkSum.add((BigDecimal) dataMap.get("ad_invalid_clk_sum"));
			adPriceSum = adPriceSum.add(BigDecimal.valueOf((Double) dataMap.get("ad_price_sum")));
			convertCount = convertCount.add((BigDecimal) dataMap.get("convert_count"));
			convertPriceCount = convertPriceCount.add((BigDecimal) dataMap.get("convert_price_count"));
		}
		
		AdGroupReportVO adGroupReportVO = new AdGroupReportVO();
		// 曝光數
		adGroupReportVO.setAdPvSum(adPvSum);
		
		// 互動數
		adGroupReportVO.setAdClkSum(adClkSum);
		
		// 互動率 = 總互動數 / 總曝光數 * 100
		adGroupReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2));
		
		// 無效點選次數(總廣告成效用)
		adGroupReportVO.setAdInvClkSum(adInvClkSum);
		
		// 費用
		adGroupReportVO.setAdPriceSum(adPriceSum.doubleValue());
		
		// 單次互動費用 = 總費用 / 總互動次數
		adGroupReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adClkSum, 2));
		
		// 千次曝光費用 = 總費用 / 曝光數 * 1000
		adGroupReportVO.setKiloCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adPvSum, 1000, 2));
		
		// 轉換次數
		adGroupReportVO.setConvertCount(convertCount);
		
		// 轉換率 = 轉換次數 / 互動數 * 100
		adGroupReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertCount, adClkSum, 100, 2));
		
		// 總轉換價值
		adGroupReportVO.setConvertPriceCount(convertPriceCount);
		
		// 平均轉換成本 = 費用 / 轉換次數
		adGroupReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, convertCount, 2));
		
		// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
		adGroupReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertPriceCount, adPriceSum, 100, 2));
		
		// 總計幾筆
		adGroupReportVO.setRowCount(adGroupListSum.size());
		vo.setRowCount(adGroupListSum.size()); // 計算底下頁碼用
		
		adGroupVOListSum.add(adGroupReportVO);
		
		return adGroupVOListSum;
	}

	/**
	 * 回傳List舊寫法
	 * 分類成效(圖表)
	 * return List
	 */
	@Override
	public List<AdGroupReportVO> queryReportAdGroupChartData(AdGroupReportVO vo) {

		List<Map<String, Object>> adGroupList = adGroupReportDAO.getAdGroupListChart(vo);
		
		List<AdGroupReportVO> adGroupVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adGroupList) {
			AdGroupReportVO adGroupReportVO = new AdGroupReportVO();
			
			// 日期
			adGroupReportVO.setReportDate((Date) dataMap.get("ad_pvclk_date"));

			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adGroupReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adGroupReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adGroupReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adGroupReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adGroupReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adClkSum, 2));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			adGroupReportVO.setKiloCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adPvSum, 1000, 2));
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adGroupReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adGroupReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertCount, adClkSum, 100, 2));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adGroupReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adGroupReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, convertCount, 2));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adGroupReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertPriceCount, adPriceSum, 100, 2));
			
			adGroupVOList.add(adGroupReportVO);
		}
		
		return adGroupVOList;
	}

	/**
	 * 回傳map新寫法
	 * 總廣告成效-分類、分類成效共用(圖表)
	 * return map
	 */
	@Override
	public Map<Date, Float> queryReportAdGroupChartDataMap(AdGroupReportVO vo) {
		List<Map<String, Object>> adGroupList = adGroupReportDAO.getAdGroupListChart(vo);
		
		String charType = vo.getCharType();
		Map<Date, Float> flashDataMap = new HashMap<>();
		for (Map<String, Object> dataMap : adGroupList) {
			
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
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_INV_CLK.getTextValue())) {
				flashDataMap.put(reportDate, adInvClkSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
				// 單次互動費用 = 總費用 / 總互動次數
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adClkSum, 2).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
				// 千次曝光費用 = 總費用 / 曝光數 * 1000
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adPvSum, 1000, 2).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
				flashDataMap.put(reportDate, adPriceSum.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())) {
				flashDataMap.put(reportDate, convertCount.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue())) {
				// 轉換率 = 轉換次數 / 互動數 * 100
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValueRounding(convertCount, adClkSum, 100, 2).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue())) {
				flashDataMap.put(reportDate, convertPriceCount.floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_COST.getTextValue())) {
				// 平均轉換成本 = 費用 / 轉換次數
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, convertCount, 2).floatValue());
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue())) {
				// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValueRounding(convertPriceCount, adPriceSum, 100, 2).floatValue());
			}
		}
		
		return flashDataMap;
	}
}