package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdGroupReportVO;
import com.pchome.akbpfp.db.dao.report.IAdGroupReportDAO;

public class AdGroupReportService implements IAdGroupReportService {

	private IAdGroupReportDAO adGroupReportDAO;

	public void setAdGroupReportDAO(IAdGroupReportDAO adGroupReportDAO) {
		this.adGroupReportDAO = adGroupReportDAO;
	}

	public List<AdGroupReportVO> loadReportDate(String sqlType, String adActionSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception {
		return adGroupReportDAO.getReportList(sqlType, adActionSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate, page, pageSize);
	}
}
