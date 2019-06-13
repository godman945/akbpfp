package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdDailyPerformanceReportVO;

public interface IAdDailyPerformanceReportService {

	/**
	 * 每日成效(明細)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public List<AdDailyPerformanceReportVO> queryReportAdDailyPerformanceData(AdDailyPerformanceReportVO vo) throws Exception;

	/**
	 * 每日成效(加總)
	 * @param vo
	 * @return
	 */
	public List<AdDailyPerformanceReportVO> queryReportAdDailyPerformanceSumData(AdDailyPerformanceReportVO vo);

	/**
	 * 每日成效(圖表)
	 * @param chartVo
	 * @return
	 */
	public List<AdDailyPerformanceReportVO> queryReportAdDailyPerformanceChartData(AdDailyPerformanceReportVO vo);

}