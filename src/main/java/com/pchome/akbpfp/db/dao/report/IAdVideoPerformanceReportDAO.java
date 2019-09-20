package com.pchome.akbpfp.db.dao.report;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdVideoReport;
import com.pchome.akbpfp.db.vo.report.ReportQueryConditionVO;

public interface IAdVideoPerformanceReportDAO extends IBaseDAO<PfpAdVideoReport, Integer> {
	
	public List<Object> getReportDataList(ReportQueryConditionVO reportQueryConditionVO) throws Exception;
	
	public List<Object> getReportCount(ReportQueryConditionVO reportQueryConditionVO) throws Exception;
	
	public List<Object> getReportChart(ReportQueryConditionVO reportQueryConditionVO) throws Exception;

	/**
	 * 影音廣告成效(明細)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdVideoPerformanceList(AdVideoPerformanceReportVO vo);

	/**
	 * 影音廣告成效(加總)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdVideoPerformanceListSum(AdVideoPerformanceReportVO vo);

	/**
	 * 影音廣告成效(圖表)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdVideoPerformanceListChart(AdVideoPerformanceReportVO vo);

	/**
	 * 影音廣告成效 尺寸下拉選單
	 * @param vo 
	 * @return
	 */
	public List<Map<String, Object>> getAdVideoPerformanceSizeList(AdVideoPerformanceReportVO vo);
}