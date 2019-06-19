package com.pchome.akbpfp.db.service.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
	 * @param vo
	 * @return List
	 */
	public List<AdDailyPerformanceReportVO> queryReportAdDailyPerformanceChartData(AdDailyPerformanceReportVO vo);

	/**
	 * 每日成效(圖表)
	 * @param vo
	 * @return Map
	 */
	public Map<Date, Float> queryReportAdDailyPerformanceChartDataMap(AdDailyPerformanceReportVO vo);

}