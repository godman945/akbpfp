package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdKeywordReportVO;

public interface IAdKeywordReportService {

	public List<AdKeywordReportVO> loadReportDate(String sqlType, String adGroupId, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice,
			String customerInfoId, String startDate, String endDate, int page, int pageSize);

}
