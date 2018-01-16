package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdAgesexReportVO;

public interface IAdAgesexReportService {

	public List<AdAgesexReportVO> loadReportDate(String sqlType, String searchAgesex, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception;

}
