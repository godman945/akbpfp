package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdOsReportVO;

public interface IAdOsReportService {

	public List<AdOsReportVO> loadOsReportDate(String sqlType, String adPvclkOs, String adSearchWay, String searchText, String customerInfoId,String startDate, String endDate,int page,int pageSize) throws Exception ;

}
