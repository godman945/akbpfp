package com.pchome.akbpfp.db.dao.report;

import java.util.List;


import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdOsReport;

public interface IAdOsReportDAO extends IBaseDAO<PfpAdOsReport, Integer> {
	
	public List<AdOsReportVO> getAdOsReportList(String sqlType, String adPvclkOs, String adSearchWay, String searchText, String customerInfoId, String startDate, String endDate, int page, int pageSize);
	
}
