package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdAgesexReportVO;
import com.pchome.akbpfp.db.dao.report.IAdAgesexReportDAO;

public class AdAgesexReportService implements IAdAgesexReportService {

	private IAdAgesexReportDAO adAgesexReportDAO;

	public void setAdAgesexReportDAO(IAdAgesexReportDAO adAgesexReportDAO) {
		this.adAgesexReportDAO = adAgesexReportDAO;
	}

	public List<AdAgesexReportVO> loadReportDate(String sqlType, String searchAgesex, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception {
		return adAgesexReportDAO.getReportList(sqlType, searchAgesex, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);
	}
}
