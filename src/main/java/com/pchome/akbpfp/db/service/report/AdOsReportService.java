package com.pchome.akbpfp.db.service.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.pchome.akbpfp.db.dao.report.AdOsReportVO;
import com.pchome.akbpfp.db.dao.report.IAdOsReportDAO;
import com.pchome.utils.CommonUtils;

public class AdOsReportService implements IAdOsReportService {

	private IAdOsReportDAO adOsReportDAO;

    public void setAdOsReportDAO(IAdOsReportDAO adOsReportDAO) {
		this.adOsReportDAO = adOsReportDAO;
	}

	@Override
	public List<AdOsReportVO> loadOsReportDate(String sqlType, String adPvclkOs, String adSearchWay, String searchText, String customerInfoId, String startDate, String endDate, int page, int pageSize) throws Exception {
		return adOsReportDAO.getAdOsReportList(sqlType, adPvclkOs, adSearchWay, searchText, customerInfoId, startDate, endDate, page, pageSize);
	}

	/**
	 * 行動廣告成效(明細)
	 * @param vo
	 * @return
	 */
	@Override
	public List<AdOsReportVO> queryReportAdOsData(AdOsReportVO vo) {
		List<Map<String, Object>> adOsList = adOsReportDAO.getAdOsList(vo);
		
		List<AdOsReportVO> adOsVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adOsList) {
			AdOsReportVO adOsReportVO = new AdOsReportVO();
			// 裝置作業系統
			adOsReportVO.setAdPvclkOs((String) dataMap.get("ad_pvclk_os"));
			
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adOsReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adOsReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adOsReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adOsReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adOsReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
			BigDecimal bigDecimal = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
			adOsReportVO.setKiloCost(bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue());
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adOsReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adOsReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adOsReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adOsReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adOsReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
			
			adOsVOList.add(adOsReportVO);
		}
		
		// 處理排序
		if (StringUtils.isNotBlank(vo.getSortBy())) {
			CommonUtils.getInstance().sort(adOsVOList, vo.getSortBy().split("-")[0], vo.getSortBy().split("-")[1]);
		}
		return adOsVOList;
	}

	/**
	 * 行動廣告成效(加總)
	 * @param vo
	 * @return
	 */
	@Override
	public List<AdOsReportVO> queryReportAdOsSumData(AdOsReportVO vo) {
		List<Map<String, Object>> adOsListSum = adOsReportDAO.getAdOsListSum(vo);
		
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
		
		List<AdOsReportVO> adOsVOListSum = new ArrayList<>();
		// 加總
		for (Map<String, Object> dataMap : adOsListSum) {
			adPvSum = adPvSum.add((BigDecimal) dataMap.get("ad_pv_sum"));
			adClkSum = adClkSum.add((BigDecimal) dataMap.get("ad_clk_sum"));
			adPriceSum = adPriceSum.add(BigDecimal.valueOf((Double) dataMap.get("ad_price_sum")));
			convertCount = convertCount.add((BigDecimal) dataMap.get("convert_count"));
			convertPriceCount = convertPriceCount.add((BigDecimal) dataMap.get("convert_price_count"));
		}
		
		AdOsReportVO adOsReportVO = new AdOsReportVO();
		// 曝光數
		adOsReportVO.setAdPvSum(adPvSum);
		
		// 互動數
		adOsReportVO.setAdClkSum(adClkSum);
		
		// 互動率 = 總互動數 / 總曝光數 * 100
		adOsReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValue(adClkSum, adPvSum, 100));
		
		// 費用
		adOsReportVO.setAdPriceSum(adPriceSum.doubleValue());
		
		// 單次互動費用 = 總費用 / 總互動次數
		adOsReportVO.setAvgCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adClkSum));
		
		// 千次曝光費用 = 總費用 / 曝光數 * 1000
		Double kiloCost = CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, adPvSum, 1000);
		BigDecimal bg = BigDecimal.valueOf(kiloCost); // 算完千次曝光費用後，再處理小數至第二位，然後四捨五入
		adOsReportVO.setKiloCost(bg.setScale(2, RoundingMode.HALF_UP).doubleValue());
		
		// 轉換次數
		adOsReportVO.setConvertCount(convertCount);
		
		// 轉換率 = 轉換次數 / 互動數 * 100
		adOsReportVO.setConvertCTR(CommonUtils.getInstance().getCalculateDivisionValue(convertCount, adClkSum, 100));
		
		// 總轉換價值
		adOsReportVO.setConvertPriceCount(convertPriceCount);
		
		// 平均轉換成本 = 費用 / 轉換次數
		adOsReportVO.setConvertCost(CommonUtils.getInstance().getCalculateDivisionValue(adPriceSum, convertCount));
		
		// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
		adOsReportVO.setConvertInvestmentCost(CommonUtils.getInstance().getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
		
		// 總計幾筆
		adOsReportVO.setRowCount(adOsListSum.size());
		vo.setRowCount(adOsListSum.size()); // 計算底下頁碼用
		
		adOsVOListSum.add(adOsReportVO);
		
		return adOsVOListSum;
	}

	/**
	 * 行動廣告成效(圖表)
	 */
	@Override
	public List<AdOsReportVO> queryReportAdOsChartData(AdOsReportVO vo) {
		return queryReportAdOsData(vo);
	}
	
}