package com.pchome.akbpfp.db.dao.report;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdReport;

public interface IAdDailyPerformanceReportDAO extends IBaseDAO<PfpAdReport, Integer> {
	
	/**
	 * 每日成效(明細)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdDailyPerformanceList(AdDailyPerformanceReportVO vo);

	/**
	 * 每日成效(加總)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdDailyPerformanceListSum(AdDailyPerformanceReportVO vo);

	/**
	 * 每日成效(圖表)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdDailyPerformanceListChart(AdDailyPerformanceReportVO vo);
	
}