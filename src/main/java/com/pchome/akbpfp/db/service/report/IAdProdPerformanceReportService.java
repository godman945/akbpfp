package com.pchome.akbpfp.db.service.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.report.AdProdPerformanceReportVO;

public interface IAdProdPerformanceReportService {

	/**
	 * 商品成效(明細)
	 * @param vo
	 * @return
	 */
	List<AdProdPerformanceReportVO> queryReportAdProdPerformanceData(AdProdPerformanceReportVO vo);

	/**
	 * 商品成效(加總)
	 * @param vo
	 * @return
	 */
	List<AdProdPerformanceReportVO> queryReportAdProdPerformanceSumData(AdProdPerformanceReportVO vo);

	/**
	 * 商品成效(圖表)
	 * @param chartVo
	 * @return
	 */
	Map<Date, Float> queryReportAdProdPerformanceChartDataMap(AdProdPerformanceReportVO vo);

}