package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdWebsiteReportVO;

public interface IAdWebsiteReportService {
	public List<AdWebsiteReportVO> loadReportDate(String sqlType, String searchWebsiteCode, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String startDate, String endDate, int page, int pageSize) throws Exception;

}
