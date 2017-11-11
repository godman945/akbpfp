package com.pchome.akbpfp.db.dao.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdVideoReport;
import com.pchome.akbpfp.db.vo.report.ReportQueryConditionVO;

public interface IAdVideoPerformanceReportDAO extends IBaseDAO<PfpAdVideoReport, Integer> {
	
	public List<Object> getReportDataList(ReportQueryConditionVO reportQueryConditionVO) throws Exception;
	
	public List<Object> getReportCount(ReportQueryConditionVO reportQueryConditionVO,String type) throws Exception;
}
