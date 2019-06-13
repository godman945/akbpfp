package com.pchome.akbpfp.db.service.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.pchome.akbpfp.db.dao.report.AdDailyPerformanceReportVO;
import com.pchome.akbpfp.db.dao.report.IAdDailyPerformanceReportDAO;
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
		
		Map<Integer, String> adTypeMap = CommonUtils.getInstance().getAdType();
		Map<String, String> adStyleTypeMap = CommonUtils.getInstance().getAdStyleTypeMap();
		Map<String, String> adPriceTypeMap = CommonUtils.getInstance().getAdPriceTypeMap();
		
		List<AdDailyPerformanceReportVO> adDailyPerformanceVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adDailyPerformanceList) {
			vo.setAdActionName((String) dataMap.get("ad_action_name")); // 麵包屑顯示名稱
			
			AdDailyPerformanceReportVO adDailyPerformanceReportVO = new AdDailyPerformanceReportVO();
			adDailyPerformanceReportVO.setReportDate((Date) dataMap.get("ad_pvclk_date")); // 日期
			adDailyPerformanceReportVO.setAdType(adTypeMap.get(dataMap.get("ad_type"))); // 播放類型
			adDailyPerformanceReportVO.setAdOperatingRule(adStyleTypeMap.get(dataMap.get("ad_operating_rule"))); // 廣告樣式
			adDailyPerformanceReportVO.setAdClkPriceType(adPriceTypeMap.get(dataMap.get("ad_clk_price_type"))); // 廣告計費方式
			
			String adDevice = "全部";
			if (vo.getWhereMap() != null) {
				adDevice = CommonUtils.getInstance().getAdDeviceName(vo.getWhereMap());
			}
			// 裝置
			adDailyPerformanceReportVO.setAdDevice(adDevice);
			
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adDailyPerformanceReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adDailyPerformanceReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adDailyPerformanceReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adDailyPerformanceReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adDailyPerformanceReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
			BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adDailyPerformanceReportVO.setKiloCost(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adDailyPerformanceReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adDailyPerformanceReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adDailyPerformanceReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adDailyPerformanceReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adDailyPerformanceReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
			
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
		adDailyPerformanceReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
		
		// 費用
		adDailyPerformanceReportVO.setAdPriceSum(adPriceSum.doubleValue());
		
		// 單次互動費用 = 總費用 / 總互動次數
		adDailyPerformanceReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
		
		// 千次曝光費用 = 總費用 / 曝光數 * 1000
		Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
		BigDecimal bg = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
		adDailyPerformanceReportVO.setKiloCost(bg.setScale(2, RoundingMode.HALF_UP).doubleValue());
		
		// 轉換次數
		adDailyPerformanceReportVO.setConvertCount(convertCount);
		
		// 轉換率 = 轉換次數 / 互動數 * 100
		adDailyPerformanceReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
		
		// 總轉換價值
		adDailyPerformanceReportVO.setConvertPriceCount(convertPriceCount);
		
		// 平均轉換成本 = 費用 / 轉換次數
		adDailyPerformanceReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
		
		// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
		adDailyPerformanceReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
		
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
			adDailyPerformanceReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adDailyPerformanceReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adDailyPerformanceReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
			BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adDailyPerformanceReportVO.setKiloCost(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adDailyPerformanceReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adDailyPerformanceReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adDailyPerformanceReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adDailyPerformanceReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adDailyPerformanceReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
			
			adDailyPerformanceVOList.add(adDailyPerformanceReportVO);
		}
		
		return adDailyPerformanceVOList;
	}
	
	public void setAdDailyPerformanceReportDAO(IAdDailyPerformanceReportDAO adDailyPerformanceReportDAO) {
		this.adDailyPerformanceReportDAO = adDailyPerformanceReportDAO;
	}
}