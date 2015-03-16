package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdGroupReportVO;

public interface IAdGroupReportService {

	public List<AdGroupReportVO> loadReportDate(String sqlType, String adActionSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String startDate, String endDate, int page, int pageSize) throws Exception;

}
