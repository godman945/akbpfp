//package com.pchome.akbpfp.db.service.report;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.StringUtils;
//
//import com.pchome.akbpfp.db.dao.report.AdProdPerformanceReportVO;
//import com.pchome.akbpfp.db.dao.report.IAdProdPerformanceReportDAO;
//import com.pchome.enumerate.report.EnumReport;
//import com.pchome.utils.CommonUtils;
//
//public class AdProdPerformanceReportService implements IAdProdPerformanceReportService {
//	private IAdProdPerformanceReportDAO adProdPerformanceReportDAO;
//	
//	/**
//	 * 商品成效(明細)
//	 */
//	@Override
//	public List<AdProdPerformanceReportVO> queryReportAdProdPerformanceData(AdProdPerformanceReportVO vo) {
//		List<Map<String, Object>> adProdPerformanceList = adProdPerformanceReportDAO.getAdProdPerformanceList(vo);
//
//		List<AdProdPerformanceReportVO> adProdPerformanceVOList = new ArrayList<>();
//		for (Map<String, Object> dataMap : adProdPerformanceList) {
//
//			vo.setProdAdName((String) dataMap.get("prod_ad_name"));
//			
//			AdProdPerformanceReportVO adProdPerformanceReportVO = new AdProdPerformanceReportVO();
//			
//			// 狀態 
//			String ecStatus = (String) dataMap.get("ec_status");
//			adProdPerformanceReportVO.setEcStatus(ecStatus); // 商品狀態(0:封存, 1:啟用)
//			adProdPerformanceReportVO.setEcStatusName("1".equals(ecStatus) ? "啟用" : "封存");
//			
//			// 廣告明細
//			adProdPerformanceReportVO.setProdImgPath("/" + (String) dataMap.get("ec_img")); // 商品圖
//			adProdPerformanceReportVO.setEcName((String) dataMap.get("ec_name")); // 商品名稱
//			
//			// 陳列次數(曝光數)
//			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
//			adProdPerformanceReportVO.setAdPvSum(adPvSum);
//			
//			// 商品點選數(互動數)
//			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
//			adProdPerformanceReportVO.setAdClkSum(adClkSum);
//			
//			// 商品點選率(互動率) = 商品點選數(總互動數) / 陳列次數(總曝光數) * 100
//			adProdPerformanceReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2));
//			
//			adProdPerformanceVOList.add(adProdPerformanceReportVO);
//		}
//		
//		// 處理排序
//		if (StringUtils.isNotBlank(vo.getSortBy())) {
//			CommonUtils.getInstance().sort(adProdPerformanceVOList, vo.getSortBy().split("-")[0], vo.getSortBy().split("-")[1]);
//		}
//		return adProdPerformanceVOList;
//	}
//
//	/**
//	 * 商品成效(加總)
//	 */
//	@Override
//	public List<AdProdPerformanceReportVO> queryReportAdProdPerformanceSumData(AdProdPerformanceReportVO vo) {
//		List<Map<String, Object>> adProdPerformanceListSum = adProdPerformanceReportDAO.getAdProdPerformanceListSum(vo);
//		
//		// 陳列次數(曝光數)
//		BigDecimal adPvSum = new BigDecimal(0);
//		// 商品點選數(互動數)
//		BigDecimal adClkSum = new BigDecimal(0);
//		
//		List<AdProdPerformanceReportVO> adProdPerformanceVOListSum = new ArrayList<>();
//		// 加總
//		for (Map<String, Object> dataMap : adProdPerformanceListSum) {
//			adPvSum = adPvSum.add((BigDecimal) dataMap.get("ad_pv_sum"));
//			adClkSum = adClkSum.add((BigDecimal) dataMap.get("ad_clk_sum"));
//		}
//		
//		AdProdPerformanceReportVO adProdPerformanceReportVO = new AdProdPerformanceReportVO();
//		// 陳列次數(曝光數)
//		adProdPerformanceReportVO.setAdPvSum(adPvSum);
//		
//		// 商品點選數(互動數)
//		adProdPerformanceReportVO.setAdClkSum(adClkSum);
//		
//		// 商品點選率(互動率) = 商品點選數(總互動數) / 陳列次數(總曝光數) * 100
//		adProdPerformanceReportVO.setCtr(CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2));
//		
//		// 總計幾筆
//		adProdPerformanceReportVO.setRowCount(adProdPerformanceListSum.size());
//		vo.setRowCount(adProdPerformanceListSum.size()); // 計算底下頁碼用
//		
//		adProdPerformanceVOListSum.add(adProdPerformanceReportVO);
//		return adProdPerformanceVOListSum;
//	}
//
//	/**
//	 * 商品成效(圖表)
//	 */
//	@Override
//	public Map<Date, Float> queryReportAdProdPerformanceChartDataMap(AdProdPerformanceReportVO vo) {
//		List<Map<String, Object>> adProdPerformanceList = adProdPerformanceReportDAO.getAdProdPerformanceListChart(vo);
//
//		String charType = vo.getCharType();
//		Map<Date, Float> flashDataMap = new HashMap<>();
//		for (Map<String, Object> dataMap : adProdPerformanceList) {
//			
//			// 日期
//			Date reportDate = (Date) dataMap.get("record_date");
//			// 陳列次數(曝光數)
//			BigDecimal adPvSum = (BigDecimal) dataMap.get("ad_pv_sum");
//			// 商品點選數(互動數)
//			BigDecimal adClkSum = (BigDecimal) dataMap.get("ad_clk_sum");
//			
//			if (charType.equals(EnumReport.REPORT_CHART_TYPE_PV.getTextValue())) {
//				flashDataMap.put(reportDate, adPvSum.floatValue());
//			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CLICK.getTextValue())) {
//				flashDataMap.put(reportDate, adClkSum.floatValue());
//			} else if (charType.equals(EnumReport.REPORT_CHART_TYPE_CTR.getTextValue())) {
//				// 商品點選率(互動率) = 商品點選數(總互動數) / 陳列次數(總曝光數) * 100
//				flashDataMap.put(reportDate, CommonUtils.getInstance().getCalculateDivisionValueRounding(adClkSum, adPvSum, 100, 2).floatValue());
//			}
//		}
//		
//		return flashDataMap;
//	}
//	
//	public void setAdProdPerformanceReportDAO(IAdProdPerformanceReportDAO adProdPerformanceReportDAO) {
//		this.adProdPerformanceReportDAO = adProdPerformanceReportDAO;
//	}
//
//}