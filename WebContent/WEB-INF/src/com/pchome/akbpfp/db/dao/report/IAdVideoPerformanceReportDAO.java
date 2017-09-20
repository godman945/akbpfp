package com.pchome.akbpfp.db.dao.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdVideoReport;
import com.pchome.akbpfp.db.vo.report.ReportQueryConditionVO;

public interface IAdVideoPerformanceReportDAO extends IBaseDAO<PfpAdVideoReport, Integer> {
	
	public List<Object> getReportList(ReportQueryConditionVO reportQueryConditionVO) throws Exception;
	
	public List<Object> getReportChartList(ReportQueryConditionVO reportQueryConditionVO) throws Exception;
}
