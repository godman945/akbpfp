package com.pchome.akbpfp.db.service.report;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.report.AdGroupReportVO;
import com.pchome.akbpfp.db.dao.report.AdVideoPerformanceReportVO;
import com.pchome.akbpfp.db.vo.report.ReportQueryConditionVO;

public interface IAdVideoPerformanceReportService {
	
//	public List<AdVideoPerformanceReportVO> loadReportDateList(ReportQueryConditionVO reportQueryConditionVO) throws Exception;
//	
//	public List<AdVideoPerformanceReportVO> loadReportDateCount(ReportQueryConditionVO reportQueryConditionVO) throws Exception;
//	
//	public List<AdVideoPerformanceReportVO> loadReportChart(ReportQueryConditionVO reportQueryConditionVO) throws Exception;

	/**
	 * 影音廣告成效(明細)
	 * @param vo
	 * @return
	 */
	public List<AdVideoPerformanceReportVO> queryReportAdVideoPerformanceData(AdVideoPerformanceReportVO vo);

	/**
	 * 影音廣告成效(加總)
	 * @param vo
	 * @return
	 */
	public List<AdVideoPerformanceReportVO> queryReportAdVideoPerformanceSumData(AdVideoPerformanceReportVO vo);

	/**
	 * 影音廣告成效(圖表)
	 * @param chartVo
	 * @return
	 */
	public Map<Date, Float> queryReportAdVideoPerformanceChartDataMap(AdVideoPerformanceReportVO vo);

	/**
	 * 影音廣告成效 尺寸下拉選單
	 * @param vo 
	 * @return
	 */
	public LinkedHashMap<String, String> queryReportAdVideoPerformanceSize(AdVideoPerformanceReportVO vo);

}