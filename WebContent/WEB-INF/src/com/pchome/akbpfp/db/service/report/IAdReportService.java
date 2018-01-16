package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdReportVO;

public interface IAdReportService {

	public List<AdReportVO> loadReportDate(String sqlType, String adGroupSeq, String adSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception;

}
