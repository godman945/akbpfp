package com.pchome.akbpfp.db.service.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.pchome.akbpfp.db.dao.report.AdTimeReportVO;
import com.pchome.akbpfp.db.dao.report.IAdTimeReportDAO;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.service.ad.IPfpAdGroupService;
import com.pchome.enumerate.ad.EnumAdTimeCode;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.enumerate.report.EnumReportAdType;
import com.pchome.enumerate.report.EnumReportDevice;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.utils.CommonUtils;

public class AdTimeReportService implements IAdTimeReportService {

	private IPfpAdGroupService adGroupService;
	private IAdTimeReportDAO adTimeReportDAO;

	public void setAdGroupService(IPfpAdGroupService adGroupService) {
		this.adGroupService = adGroupService;
	}

	public void setAdTimeReportDAO(IAdTimeReportDAO adTimeReportDAO) {
		this.adTimeReportDAO = adTimeReportDAO;
	}

	public List<AdTimeReportVO> loadReportDate(String sqlType, String searchTime, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception {
		return adTimeReportDAO.getReportList(sqlType, searchTime, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);
	}

	/**
	 * 廣告播放時段成效(明細)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	@Override
	public List<AdTimeReportVO> queryReportAdTimeData(AdTimeReportVO vo) throws Exception {
		List<Map<String, Object>> adTimeList = adTimeReportDAO.getAdTimeList(vo);
		
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
		
		List<AdTimeReportVO> adTimeVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adTimeList) {
			AdTimeReportVO adTimeReportVO = new AdTimeReportVO();
			
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
				adTimeReportVO.setAdStatusOnOff(true); // on亮綠燈
			} else if (adActionStatus != EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus == EnumStatus.Open.getStatusId()) {
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus));
			} else if (adActionStatus == EnumStatus.Broadcast.getStatusId()
					&& adGroupStatus != EnumStatus.Open.getStatusId()) {
				alter = "分類" + adStatusMap.get(Integer.toString(adGroupStatus));
			} else {
				alter = "廣告" + adStatusMap.get(Integer.toString(adActionStatus)) + "，" + "分類" + adStatusMap.get(Integer.toString(adGroupStatus));
			}
			adTimeReportVO.setAdStatusName(alter); // 產excel報表使用
			
			adTimeReportVO.setAdActionName(adActionName); // 廣告活動
			adTimeReportVO.setAdGroupName(adGroupName); // 廣告分類
			
			if ("week".equalsIgnoreCase(vo.getViewType())) { // 星期
				int weekCode = (int) dataMap.get("week_code");
				if (weekCode == 1) { // 排序用，將禮拜日改為最大值
					adTimeReportVO.setWeekCode(8);
				} else {
					adTimeReportVO.setWeekCode(weekCode);
				}
				adTimeReportVO.setWeek(getWeekName(Integer.toString(weekCode)));
			} else { // 時段
				String timeCode = (String) dataMap.get("time_code");
				adTimeReportVO.setTimeCode(timeCode); // 排序用
				adTimeReportVO.setTime(EnumAdTimeCode.getEnumAdTimeCodeData(timeCode).getName());
			}
			
			// 播放類型
			if (EnumReportAdType.SEARCHANDCHANNEL.getAdType().equalsIgnoreCase(selectAdType)) {
				adTimeReportVO.setAdType(EnumReportAdType.SEARCHANDCHANNEL.getAdTypeName());
			} else {
				int adType = (int) dataMap.get("ad_type");
				String adTypeName = "";
				if (EnumReportAdType.SEARCH.getAdType().equalsIgnoreCase(String.valueOf(adType))) {
					adTypeName = EnumReportAdType.SEARCH.getAdTypeName();
				} else if (EnumReportAdType.CHANNEL.getAdType().equalsIgnoreCase(String.valueOf(adType))) {
					adTypeName = EnumReportAdType.CHANNEL.getAdTypeName();
				}
				adTimeReportVO.setAdType(adTypeName);
			}
			
			adTimeReportVO.setAdOperatingRule(adStyleTypeMap.get(dataMap.get("ad_operating_rule"))); // 廣告樣式
			adTimeReportVO.setAdClkPriceType(adPriceTypeMap.get(dataMap.get("ad_clk_price_type"))); // 廣告計費方式
			
			// 裝置
			if (EnumReportDevice.PCANDMOBILE.getDevType().equalsIgnoreCase(selectAdDevice)) {
				adTimeReportVO.setAdDevice(EnumReportDevice.PCANDMOBILE.getDevTypeName());
			} else {
				String adDevice = (String) dataMap.get("ad_device");
				if (EnumReportDevice.PC.getDevType().equalsIgnoreCase(adDevice)) {
					adDevice = EnumReportDevice.PC.getDevTypeName();
				} else if (EnumReportDevice.MOBILE.getDevType().equalsIgnoreCase(adDevice)) {
					adDevice = EnumReportDevice.MOBILE.getDevTypeName();
				}
				adTimeReportVO.setAdDevice(adDevice);
			}
			
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adTimeReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adTimeReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adTimeReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adTimeReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adTimeReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adClkSum, 2));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			adTimeReportVO.setKiloCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adPvSum, 1000, 2));
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adTimeReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adTimeReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertCount, adClkSum, 100, 2));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adTimeReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adTimeReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, convertCount, 2));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adTimeReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertPriceCount, adPriceSum, 100, 2));
			
			adTimeVOList.add(adTimeReportVO);
		}
		
		// 處理排序
		if (StringUtils.isNotBlank(vo.getSortBy())) {
			CommonUtils.getInstance().sort(adTimeVOList, vo.getSortBy().split("-")[0], vo.getSortBy().split("-")[1]);
		}
		return adTimeVOList;
	}

	/**
	 * 廣告播放時段成效(加總)
	 * @param vo
	 * @return
	 */
	@Override
	public List<AdTimeReportVO> queryReportAdTimeSumData(AdTimeReportVO vo) {
		List<Map<String, Object>> adTimeListSum = adTimeReportDAO.getAdTimeListSum(vo);
		
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
		
		List<AdTimeReportVO> adTimeVOListSum = new ArrayList<>();
		// 加總
		for (Map<String, Object> dataMap : adTimeListSum) {
			adPvSum = adPvSum.add((BigDecimal) dataMap.get("ad_pv_sum"));
			adClkSum = adClkSum.add((BigDecimal) dataMap.get("ad_clk_sum"));
			adPriceSum = adPriceSum.add(BigDecimal.valueOf((Double) dataMap.get("ad_price_sum")));
			convertCount = convertCount.add((BigDecimal) dataMap.get("convert_count"));
			convertPriceCount = convertPriceCount.add((BigDecimal) dataMap.get("convert_price_count"));
		}
		
		AdTimeReportVO adTimeReportVO = new AdTimeReportVO();
		// 曝光數
		adTimeReportVO.setAdPvSum(adPvSum);
		
		// 互動數
		adTimeReportVO.setAdClkSum(adClkSum);
		
		// 互動率 = 總互動數 / 總曝光數 * 100
		adTimeReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2));
		
		// 費用
		adTimeReportVO.setAdPriceSum(adPriceSum.doubleValue());
		
		// 單次互動費用 = 總費用 / 總互動次數
		adTimeReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adClkSum, 2));
		
		// 千次曝光費用 = 總費用 / 曝光數 * 1000
		Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adPvSum, 1000, 2);
		BigDecimal bg = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
		adTimeReportVO.setKiloCost(bg.setScale(2, RoundingMode.HALF_UP).doubleValue());
		
		// 轉換次數
		adTimeReportVO.setConvertCount(convertCount);
		
		// 轉換率 = 轉換次數 / 互動數 * 100
		adTimeReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertCount, adClkSum, 100, 2));
		
		// 總轉換價值
		adTimeReportVO.setConvertPriceCount(convertPriceCount);
		
		// 平均轉換成本 = 費用 / 轉換次數
		adTimeReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, convertCount, 2));
		
		// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
		adTimeReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertPriceCount, adPriceSum, 100, 2));
		
		// 總計幾筆
		adTimeReportVO.setRowCount(adTimeListSum.size());
		vo.setRowCount(adTimeListSum.size()); // 計算底下頁碼用
		
		adTimeVOListSum.add(adTimeReportVO);
		
		return adTimeVOListSum;
	}
	
	/**
	 * 廣告播放時段成效(圖表)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	@Override
	public List<AdTimeReportVO> queryReportAdTimeChartData(AdTimeReportVO vo) throws Exception {
		List<Map<String, Object>> adTimeList = adTimeReportDAO.getAdTimeListChart(vo);
		
		List<AdTimeReportVO> adTimeVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adTimeList) {
			AdTimeReportVO adTimeReportVO = new AdTimeReportVO();
			
			if ("week".equalsIgnoreCase(vo.getViewType())) { // 星期
				adTimeReportVO.setWeekCode((int) dataMap.get("week_code"));
			} else { // 時段
				adTimeReportVO.setTimeCode((String) dataMap.get("time_code"));
			}
			
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adTimeReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adTimeReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adTimeReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adTimeReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adTimeReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adClkSum, 2));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			adTimeReportVO.setKiloCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adPvSum, 1000, 2));
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adTimeReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adTimeReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertCount, adClkSum, 100, 2));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adTimeReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adTimeReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, convertCount, 2));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adTimeReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertPriceCount, adPriceSum, 100, 2));
			
			adTimeVOList.add(adTimeReportVO);
		}
		
		return adTimeVOList;
	}
	
	private String getWeekName(String weekCode) {
		String name = "";

		switch (weekCode) {
		case "1":
			name = "星期日";
			break;
		case "2":
			name = "星期一";
			break;
		case "3":
			name = "星期二";
			break;
		case "4":
			name = "星期三";
			break;
		case "5":
			name = "星期四";
			break;
		case "6":
			name = "星期五";
			break;
		case "7":
			name = "星期六";
			break;
		default:
			break;
		}

		return name;
	}

	/**
	 * 廣告播放時段成效(圖表)
	 * @param vo
	 * @return
	 */
	@Override
	public List<Double> queryReportAdTimeChartDataList(AdTimeReportVO vo) {
		List<Map<String, Object>> adTimeList = adTimeReportDAO.getAdTimeListChart(vo);
		
		String charType = vo.getCharType();
		//星期
		String weekCode = "";
		double sun = 0; // 星期日
		double mon = 0; // 星期一
		double tue = 0; // 星期二
		double wed = 0; // 星期三
		double thu = 0; // 星期四
		double fri = 0; // 星期五
		double sat = 0; // 星期六
		
		//時段
		String timeCode = "";
		double timeA = 0; // 0-3
		double timeB = 0; // 4-7
		double timeC = 0; // 8-11
		double timeD = 0; // 12-15
		double timeE = 0; // 16-19
		double timeF = 0; // 20-23

		for (Map<String, Object> dataMap : adTimeList) {
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
			
			double data = 0;
			if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
				data = adPvSum.doubleValue();
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
				data = adClkSum.doubleValue();
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
				// 互動率 = 總互動數 / 總曝光數 * 100
				data = CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2);
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_AVGCOST.getTextValue())) {
				// 單次互動費用 = 總費用 / 總互動次數
				data = CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adClkSum, 2);
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_KILOCOST.getTextValue())) {
				// 千次曝光費用 = 總費用 / 曝光數 * 1000
				data = CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adPvSum, 1000, 2);
			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_COST.getTextValue())) {
				data = adPriceSum.doubleValue();
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT.getTextValue())) {
				data = convertCount.doubleValue();
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_CTR.getTextValue())) {
				// 轉換率 = 轉換次數 / 互動數 * 100
				data = CommonUtils.getInstance().getCalculateDivisionValueRounding(convertCount, adClkSum, 100, 2);
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_PRICE.getTextValue())) {
				data = convertPriceCount.doubleValue();
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_COST.getTextValue())) {
				// 平均轉換成本 = 費用 / 轉換次數
				data = CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, convertCount, 2);
			} else if (charType.equals(EnumReport.REPORT_CHART_CONVERT_INVESTMENT.getTextValue())) {
				// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
				data = CommonUtils.getInstance().getCalculateDivisionValueRounding(convertPriceCount, adPriceSum, 100, 2);
			}

			if ("week".equalsIgnoreCase(vo.getViewType())) { // 星期
				weekCode = Integer.toString((int) dataMap.get("week_code"));
				switch (weekCode) {
				case "1":
					sun = data;
					break;
				case "2":
					mon = data;
					break;
				case "3":
					tue = data;
					break;
				case "4":
					wed = data;
					break;
				case "5":
					thu = data;
					break;
				case "6":
					fri = data;
					break;
				default:
					sat = data;
					break;
				}
			} else {
				timeCode = (String) dataMap.get("time_code");
				switch (timeCode) {
				case "B":
					timeB = data;
					break;
				case "C":
					timeC = data;
					break;
				case "D":
					timeD = data;
					break;
				case "E":
					timeE = data;
					break;
				case "F":
					timeF = data;
					break;
				default:
					timeA = data;
					break;
				}
			}
		}
		
		List<Double> dataList = new ArrayList<>();
		if ("week".equalsIgnoreCase(vo.getViewType())) {
			dataList.add(sun);
			dataList.add(mon);
			dataList.add(tue);
			dataList.add(wed);
			dataList.add(thu);
			dataList.add(fri);
			dataList.add(sat);
		} else {
			dataList.add(timeA);
			dataList.add(timeB);
			dataList.add(timeC);
			dataList.add(timeD);
			dataList.add(timeE);
			dataList.add(timeF);
		}
		return dataList;
	}
}