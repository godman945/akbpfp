package com.pchome.akbpfp.db.dao.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdAgeReport;

public interface IAdAgesexReportDAO extends IBaseDAO<PfpAdAgeReport, Integer> {
	
	public List<AdAgesexReportVO> getReportList(String sqlType, String searchAgesex, String searchText, String adSearchWay, String adPvclkDevice, String adType, String customerInfoId, String startDate, String endDate, int page, int pageSize) throws Exception;

}
