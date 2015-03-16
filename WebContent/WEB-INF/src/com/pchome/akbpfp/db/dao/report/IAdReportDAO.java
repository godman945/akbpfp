package com.pchome.akbpfp.db.dao.report;

import java.util.List;



import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdReport;

public interface IAdReportDAO extends IBaseDAO<PfpAdReport, Integer> {
	
	public List<AdReportVO> getReportList(String sqlType, String adGroupId, String searchText, String adSearchWay, String adPvclkDevice, String adType, String customerInfoId, String startDate, String endDate, int page, int pageSize);
	
}
