package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdWebsiteReportVO;
import com.pchome.akbpfp.db.dao.report.IAdWebsiteReportDAO;

public class AdWebsiteReportService implements IAdWebsiteReportService {

	private IAdWebsiteReportDAO adWebsiteReportDAO;

	public void setAdWebsiteReportDAO(IAdWebsiteReportDAO adWebsiteReportDAO) {
		this.adWebsiteReportDAO = adWebsiteReportDAO;
	}

	@Override
	public List<AdWebsiteReportVO> loadReportDate(String sqlType, String searchWebsiteCode, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception {
		return adWebsiteReportDAO.getReportList(sqlType, searchWebsiteCode, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);
	}
	
}
