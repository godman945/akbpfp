package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdOsReportVO;
import com.pchome.akbpfp.db.dao.report.IAdOsReportDAO;

public class AdOsReportService implements IAdOsReportService {

	private IAdOsReportDAO adOsReportDAO;

    public void setAdOsReportDAO(IAdOsReportDAO adOsReportDAO) {
		this.adOsReportDAO = adOsReportDAO;
	}

	@Override
	public List<AdOsReportVO> loadOsReportDate(String sqlType, String adPvclkOs, String adSearchWay, String searchText, String customerInfoId, String startDate, String endDate, int page, int pageSize) throws Exception {
		return adOsReportDAO.getAdOsReportList(sqlType, adPvclkOs, adSearchWay, searchText, customerInfoId, startDate, endDate, page, pageSize);
	}
}
