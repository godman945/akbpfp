package com.pchome.akbpfp.db.dao.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdWebsiteReport;

public interface IAdWebsiteReportDAO extends IBaseDAO<PfpAdWebsiteReport, Integer> {
	
	public List<AdWebsiteReportVO> getReportList(String sqlType, String searchWebsiteCode, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception;

}
