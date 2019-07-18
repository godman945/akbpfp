package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdActionReportVO;
import com.pchome.akbpfp.db.dao.report.IAdActionReportDAO;

public class AdActionReportService implements IAdActionReportService {

	private IAdActionReportDAO adActionReportDAO;

	public void setAdActionReportDAO(IAdActionReportDAO adActionReportDAO) {
		this.adActionReportDAO = adActionReportDAO;
	}

	public List<AdActionReportVO> loadReportDate(String sqlType, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate,
			int page,int pageSize) {
		return adActionReportDAO.getReportList(sqlType,searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);
	}
}
