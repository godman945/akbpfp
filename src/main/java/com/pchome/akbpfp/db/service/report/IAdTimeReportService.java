package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdTimeReportVO;

public interface IAdTimeReportService {

	public List<AdTimeReportVO> loadReportDate(String sqlType, String searchTime, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception;

}
