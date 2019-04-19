package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.IAdKeywordReportDAO;
import com.pchome.akbpfp.db.dao.report.AdKeywordReportVO;

public class AdKeywordReportService implements IAdKeywordReportService {

	private IAdKeywordReportDAO adKeywordReportDAO;

	public void setAdKeywordReportDAO(IAdKeywordReportDAO adKeywordReportDAO) {
		this.adKeywordReportDAO = adKeywordReportDAO;
	}

	public List<AdKeywordReportVO> loadReportDate(String sqlType, String adGroupId, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String startDate, String endDate, int page, int pageSize) {
		return adKeywordReportDAO.getReportList(sqlType, adGroupId, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate, page, pageSize);
	}
}
