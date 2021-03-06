package com.pchome.akbpfp.db.service.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.pchome.akbpfp.db.dao.report.AdDailyPerformanceReportVO;
import com.pchome.akbpfp.db.dao.report.IAdDailyPerformanceReportDAO;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.enumerate.report.EnumReportAdType;
import com.pchome.enumerate.report.EnumReportDevice;
import com.pchome.utils.CommonUtils;

public class AdDailyPerformanceReportService implements IAdDailyPerformanceReportService {

	private IAdDailyPerformanceReportDAO adDailyPerformanceReportDAO;
	
	/**
	 * 每日成效(明細)
	 * @throws Exception 
	 */
	@Override
	public List<AdDailyPerformanceReportVO> queryReportAdDailyPerformanceData(AdDailyPerformanceReportVO vo) throws Exception {
		List<Map<String, Object>> adDailyPerformanceList = adDailyPerformanceReportDAO.getAdDailyPerformanceList(vo);
		
		Map<String, String> adStyleTypeMap = CommonUtils.getInstance().getAdStyleTypeMap();
		Map<String, String> adPriceTypeMap = CommonUtils.getInstance().getAdPriceTypeMap();
		
		// 檢查前端畫面選擇的篩選條件
		JSONObject tempJSONObject = new JSONObject();
		if(vo.getWhereMap() != null) {
			tempJSONObject = new JSONObject(vo.getWhereMap());
		}
		String selectAdType = tempJSONObject.optString("adType"); // 播放類型
		String selectAdDevice = tempJSONObject.optString("adDevice"); // 裝置
		
		List<AdDailyPerformanceReportVO> adDailyPerformanceVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adDailyPerformanceList) {
			vo.setAdActionName((String) dataMap.get("ad_action_name")); // 麵包屑顯示名稱
			
			AdDailyPerformanceReportVO adDailyPerformanceReportVO = new AdDailyPerformanceReportVO();
			adDailyPerformanceReportVO.setReportDate((Date) dataMap.get("ad_pvclk_date")); // 日期

			// 播放類型
			if (EnumReportAdType.SEARCHANDCHANNEL.getAdType().equalsIgnoreCase(selectAdType)) {
				adDailyPerformanceReportVO.setAdType(EnumReportAdType.SEARCHANDCHANNEL.getAdTypeName());
			} else {
				int adType = (int) dataMap.get("ad_type");
				String adTypeName = "";
				if (EnumReportAdType.SEARCH.getAdType().equalsIgnoreCase(String.valueOf(adType))) {
					adTypeName = EnumReportAdType.SEARCH.getAdTypeName();
				} else if (EnumReportAdType.CHANNEL.getAdType().equalsIgnoreCase(String.valueOf(adType))) {
					adTypeName = EnumReportAdType.CHANNEL.getAdTypeName();
				}
				adDailyPerformanceReportVO.setAdType(adTypeName);
			}
			
			adDailyPerformanceReportVO.setAdOperatingRule(adStyleTypeMap.get(dataMap.get("ad_operating_rule"))); // 廣告樣式
			adDailyPerformanceReportVO.setAdClkPriceType(adPriceTypeMap.get(dataMap.get("ad_clk_price_type"))); // 廣告計費方式
			
			// 裝置
			if (EnumReportDevice.PCANDMOBILE.getDevType().equalsIgnoreCase(selectAdDevice)) {
				adDailyPerformanceReportVO.setAdDevice(EnumReportDevice.PCANDMOBILE.getDevTypeName());
			} else {
				String adDevice = (String) dataMap.get("ad_device");
				if (EnumReportDevice.PC.getDevType().equalsIgnoreCase(adDevice)) {
					adDevice = EnumReportDevice.PC.getDevTypeName();
				} else if (EnumReportDevice.MOBILE.getDevType().equalsIgnoreCase(adDevice)) {
					adDevice = EnumReportDevice.MOBILE.getDevTypeName();
				}
				adDailyPerformanceReportVO.setAdDevice(adDevice);
			}
			
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adDailyPerformanceReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adDailyPerformanceReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adDailyPerformanceReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adDailyPerformanceReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adDailyPerformanceReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adClkSum, 2));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			adDailyPerformanceReportVO.setKiloCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adPvSum, 1000, 2));
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adDailyPerformanceReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adDailyPerformanceReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertCount, adClkSum, 100, 2));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adDailyPerformanceReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adDailyPerformanceReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, convertCount, 2));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adDailyPerformanceReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertPriceCount, adPriceSum, 100, 2));
			
			adDailyPerformanceVOList.add(adDailyPerformanceReportVO);
		}
		
		// 處理排序
		if (StringUtils.isNotBlank(vo.getSortBy())) {
			CommonUtils.getInstance().sort(adDailyPerformanceVOList, vo.getSortBy().split("-")[0], vo.getSortBy().split("-")[1]);
		}
		return adDailyPerformanceVOList;
	}

	/**
	 * 每日成效(加總)
	 */
	@Override
	public List<AdDailyPerformanceReportVO> queryReportAdDailyPerformanceSumData(AdDailyPerformanceReportVO vo) {
		List<Map<String, Object>> adDailyPerformanceListSum = adDailyPerformanceReportDAO.getAdDailyPerformanceListSum(vo);
		
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
		
		List<AdDailyPerformanceReportVO> adDailyPerformanceVOListSum = new ArrayList<>();
		// 加總
		for (Map<String, Object> dataMap : adDailyPerformanceListSum) {
			adPvSum = adPvSum.add((BigDecimal) dataMap.get("ad_pv_sum"));
			adClkSum = adClkSum.add((BigDecimal) dataMap.get("ad_clk_sum"));
			adPriceSum = adPriceSum.add(BigDecimal.valueOf((Double) dataMap.get("ad_price_sum")));
			convertCount = convertCount.add((BigDecimal) dataMap.get("convert_count"));
			convertPriceCount = convertPriceCount.add((BigDecimal) dataMap.get("convert_price_count"));
		}
		
		AdDailyPerformanceReportVO adDailyPerformanceReportVO = new AdDailyPerformanceReportVO();
		// 曝光數
		adDailyPerformanceReportVO.setAdPvSum(adPvSum);
		
		// 互動數
		adDailyPerformanceReportVO.setAdClkSum(adClkSum);
		
		// 互動率 = 總互動數 / 總曝光數 * 100
		adDailyPerformanceReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2));
		
		// 費用
		adDailyPerformanceReportVO.setAdPriceSum(adPriceSum.doubleValue());
		
		// 單次互動費用 = 總費用 / 總互動次數
		adDailyPerformanceReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adClkSum, 2));
		
		// 千次曝光費用 = 總費用 / 曝光數 * 1000
		adDailyPerformanceReportVO.setKiloCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adPvSum, 1000, 2));
		
		// 轉換次數
		adDailyPerformanceReportVO.setConvertCount(convertCount);
		
		// 轉換率 = 轉換次數 / 互動數 * 100
		adDailyPerformanceReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertCount, adClkSum, 100, 2));
		
		// 總轉換價值
		adDailyPerformanceReportVO.setConvertPriceCount(convertPriceCount);
		
		// 平均轉換成本 = 費用 / 轉換次數
		adDailyPerformanceReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, convertCount, 2));
		
		// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
		adDailyPerformanceReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertPriceCount, adPriceSum, 100, 2));
		
		// 總計幾筆
		adDailyPerformanceReportVO.setRowCount(adDailyPerformanceListSum.size());
		vo.setRowCount(adDailyPerformanceListSum.size()); // 計算底下頁碼用
		
		adDailyPerformanceVOListSum.add(adDailyPerformanceReportVO);
		
		return adDailyPerformanceVOListSum;
	}

	/**
	 * 每日成效(圖表)
	 */
	@Override
	public List<AdDailyPerformanceReportVO> queryReportAdDailyPerformanceChartData(AdDailyPerformanceReportVO vo) {
		List<Map<String, Object>> adDailyPerformanceList = adDailyPerformanceReportDAO.getAdDailyPerformanceListChart(vo);
		
		List<AdDailyPerformanceReportVO> adDailyPerformanceVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adDailyPerformanceList) {
			AdDailyPerformanceReportVO adDailyPerformanceReportVO = new AdDailyPerformanceReportVO();
			
			// 日期
			adDailyPerformanceReportVO.setReportDate((Date) dataMap.get("ad_pvclk_date"));

			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adDailyPerformanceReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adDailyPerformanceReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adDailyPerformanceReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adDailyPerformanceReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adDailyPerformanceReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adClkSum, 2));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			adDailyPerformanceReportVO.setKiloCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, adPvSum, 1000, 2));
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adDailyPerformanceReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adDailyPerformanceReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertCount, adClkSum, 100, 2));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adDailyPerformanceReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adDailyPerformanceReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(adPriceSum, convertCount, 2));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adDailyPerformanceReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValueRounding(convertPriceCount, adPriceSum, 100, 2));
			
			adDailyPerformanceVOList.add(adDailyPerformanceReportVO);
		}
		
		return adDailyPerformanceVOList;
	}
	
	/**
	 * 每日成效(圖表)
	 * @param chartVo
	 * @return Map
	 */
	@Override
	public Map<Date, Float> queryReportAdDailyPerformanceChartDataMap(AdDailyPerformanceReportVO vo) {
		List<Map<String, Object>> adDailyPerformanceList = adDailyPerformanceReportDAO.getAdDailyPerformanceListChart(vo);

		String charType = vo.getCharType();
		Map<Date, Float> flashDataMap = new HashMap<>();
		for (Map<String, Object> dataMap : adDailyPerformanceList) {
			
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
				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2).floatValue());
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
	
	public void setAdDailyPerformanceReportDAO(IAdDailyPerformanceReportDAO adDailyPerformanceReportDAO) {
		this.adDailyPerformanceReportDAO = adDailyPerformanceReportDAO;
	}
}