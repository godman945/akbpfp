package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdVideoPerformanceReportVO;
import com.pchome.akbpfp.db.vo.report.ReportQueryConditionVO;

public interface IAdVideoPerformanceReportService {
	
	public List<AdVideoPerformanceReportVO> loadReportDate(ReportQueryConditionVO reportQueryConditionVO) throws Exception;
	
	public List<AdVideoPerformanceReportVO> loadReportChart(ReportQueryConditionVO reportQueryConditionVO) throws Exception;

}
