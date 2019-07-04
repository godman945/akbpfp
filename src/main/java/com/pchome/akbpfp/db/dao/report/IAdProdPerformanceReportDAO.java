package com.pchome.akbpfp.db.dao.report;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdReport;

public interface IAdProdPerformanceReportDAO extends IBaseDAO<PfpAdReport, Integer> {

	/**
	 * 商品成效(明細)
	 * @param vo
	 * @return
	 */
	List<Map<String, Object>> getAdProdPerformanceList(AdProdPerformanceReportVO vo);

	/**
	 * 商品成效(加總)
	 * @param vo
	 * @return
	 */
	List<Map<String, Object>> getAdProdPerformanceListSum(AdProdPerformanceReportVO vo);

	/**
	 * 商品成效(圖表)
	 * @param vo
	 * @return
	 */
	List<Map<String, Object>> getAdProdPerformanceListChart(AdProdPerformanceReportVO vo);
}