package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdTimeReportVO;
import com.pchome.akbpfp.db.dao.report.IAdTimeReportDAO;

public class AdTimeReportService implements IAdTimeReportService {

	private IAdTimeReportDAO adTimeReportDAO;

	public void setAdTimeReportDAO(IAdTimeReportDAO adTimeReportDAO) {
		this.adTimeReportDAO = adTimeReportDAO;
	}

	public List<AdTimeReportVO> loadReportDate(String sqlType, String searchTime, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception {
		return adTimeReportDAO.getReportList(sqlType, searchTime, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);
	}
}
