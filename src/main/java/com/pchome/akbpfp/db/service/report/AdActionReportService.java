package com.pchome.akbpfp.db.service.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.pchome.akbpfp.db.dao.report.AdActionReportVO;
import com.pchome.akbpfp.db.dao.report.IAdActionReportDAO;

public class AdActionReportService implements IAdActionReportService {

	private IAdActionReportDAO adActionReportDAO;

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
	public List<AdActionReportVO> queryReportAdDailyData(AdActionReportVO vo) throws Exception {
		List<Map<String, Object>> adDailyList = adActionReportDAO.getAdDailyList(vo);
		List<AdActionReportVO> adDailyVOList = new ArrayList<>();
		for (Map<String, Object> dataMap : adDailyList) {
			AdActionReportVO adActionReportVO = new AdActionReportVO();
			
			// 日期
			adActionReportVO.setReportDate((Date) dataMap.get("ad_pvclk_date"));
			
			String adDevice = "全部";
			if (vo.getWhereMap() != null) {
				adDevice = getAdDeviceName(vo.getWhereMap());
			}
			// 裝置
			adActionReportVO.setAdDevice(adDevice);
			
			// 曝光數
			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
			adActionReportVO.setAdPvSum(adPvSum);
			
			// 互動數
			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
			adActionReportVO.setAdClkSum(adClkSum);
			
			// 互動率 = 總互動數 / 總曝光數 * 100
			adActionReportVO.setCtr(getCalculateDivisionValue(adClkSum, adPvSum, 100));
			
			// 費用
			BigDecimal adPriceSum = BigDecimal.valueOf((Double) dataMap.get("ad_price_sum"));
			adActionReportVO.setAdPriceSum(adPriceSum.doubleValue());
						
			// 單次互動費用 = 總費用 / 總互動次數
			adActionReportVO.setAvgCost(getCalculateDivisionValue(adPriceSum, adClkSum));
			
			// 千次曝光費用 = 總費用 / 曝光數 * 1000
			adActionReportVO.setKiloCost(getCalculateDivisionValue(adPriceSum, adPvSum, 1000));
			
			// 轉換次數
			BigDecimal convertCount = (BigDecimal) dataMap.get("convert_count");
			adActionReportVO.setConvertCount(convertCount);
			
			// 轉換率 = 轉換次數 / 互動數 * 100
			adActionReportVO.setConvertCTR(getCalculateDivisionValue(convertCount, adClkSum, 100));
			
			// 總轉換價值
			BigDecimal convertPriceCount = (BigDecimal) dataMap.get("convert_price_count");
			adActionReportVO.setConvertPriceCount(convertPriceCount);
			
			// 平均轉換成本 = 費用 / 轉換次數
			adActionReportVO.setConvertCost(getCalculateDivisionValue(adPriceSum, convertCount));
			
			// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
			adActionReportVO.setConvertInvestmentCost(getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
			
			adDailyVOList.add(adActionReportVO);
		}
		
		// 處理排序
		if (StringUtils.isNotBlank(vo.getSortBy())) {
			sort(adDailyVOList, vo.getSortBy().split("-")[0], vo.getSortBy().split("-")[1]);
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
		adActionReportVO.setCtr(getCalculateDivisionValue(adClkSum, adPvSum, 100));
		
		// 費用
		adActionReportVO.setAdPriceSum(adPriceSum.doubleValue());
		
		// 單次互動費用 = 總費用 / 總互動次數
		adActionReportVO.setAvgCost(getCalculateDivisionValue(adPriceSum, adClkSum));
		
		// 千次曝光費用 = 總費用 / 曝光數 * 1000
		adActionReportVO.setKiloCost(getCalculateDivisionValue(adPriceSum, adPvSum, 1000));
		
		// 轉換次數
		adActionReportVO.setConvertCount(convertCount);
		
		// 轉換率 = 轉換次數 / 互動數 * 100
		adActionReportVO.setConvertCTR(getCalculateDivisionValue(convertCount, adClkSum, 100));
		
		// 總轉換價值
		adActionReportVO.setConvertPriceCount(convertPriceCount);
		
		// 平均轉換成本 = 費用 / 轉換次數
		adActionReportVO.setConvertCost(getCalculateDivisionValue(adPriceSum, convertCount));
		
		// 廣告投資報酬率 = (總轉換價值 / 費用) * 100
		adActionReportVO.setConvertInvestmentCost(getCalculateDivisionValue(convertPriceCount, adPriceSum, 100));
		
		// 總計幾筆
		adActionReportVO.setRowCount(adDailyListSum.size());
		vo.setRowCount(adDailyListSum.size()); // 計算底下頁碼用
		
		adDailyVOListSum.add(adActionReportVO);
		
		return adDailyVOListSum;
	}
	
	/**
	 * 每日花費成效(圖表)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public List<AdActionReportVO> queryReportAdDailyChartData(AdActionReportVO vo) throws Exception {
		return queryReportAdDailyData(vo);
	}
	
	/**
	 * 取得裝置中文
	 * @param whereMap JSONObject格式字串
	 * @return
	 */
	public String getAdDeviceName(String whereMap) {
		String adDevice = "全部";
		JSONObject tempJSONObject = new JSONObject(whereMap);
		String tempStr = tempJSONObject.optString("adDevice");
		if ("PC".equalsIgnoreCase(tempStr)) {
			adDevice = "電腦";
		} else if ("mobile".equalsIgnoreCase(tempStr)) {
			adDevice = "行動裝置";
		}
		return adDevice;
	}
	
	/**
	 * 將查詢結果排序
	 * 參考 https://www.cnblogs.com/china-li/archive/2013/04/28/3048739.html
	 * @param list
	 * @param fieldName vo內有的參數
	 * @param sortBy
	 */
	public static void sort(List<?> list, String fieldName, String sortBy) {
        Comparator<?> mycmp = ComparableComparator.getInstance();
		if ("asc".equalsIgnoreCase(sortBy)) {
			mycmp = ComparatorUtils.nullLowComparator(mycmp);
		} else {
			mycmp = ComparatorUtils.reversedComparator(mycmp);
		}
        Collections.sort(list, new BeanComparator(fieldName, mycmp));
    }

	/**
	 * 取得除法計算值 <br/>
	 * 公式:dividend / divisor
	 * @param dividend 被除數
	 * @param divisor  除數
	 * @return true:unbmer false:0
	 */
	private Double getCalculateDivisionValue(BigDecimal dividend, BigDecimal divisor) {
		if (dividend.compareTo(BigDecimal.ZERO) >= 1 && divisor.compareTo(BigDecimal.ZERO) >= 1) {
			return dividend.divide(divisor, 6, RoundingMode.DOWN).doubleValue();
		}
		return (BigDecimal.ZERO).doubleValue();
	}
	
	/**
	 * 取得除法計算值 <br/>
	 * 公式:(dividend / divisor) * number
	 * @param dividend 被除數
	 * @param divisor  除數
	 * @param number   乘多少自行放入
	 * @return true:unbmer false:0
	 */
	private Double getCalculateDivisionValue(BigDecimal dividend, BigDecimal divisor, int number) {
		if (dividend.compareTo(BigDecimal.ZERO) >= 1 && divisor.compareTo(BigDecimal.ZERO) >= 1) {
			return dividend.divide(divisor, 6, RoundingMode.DOWN).multiply(new BigDecimal(number)).doubleValue();
		}
		return (BigDecimal.ZERO).doubleValue();
	}

}