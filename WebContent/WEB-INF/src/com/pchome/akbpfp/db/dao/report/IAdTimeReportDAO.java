package com.pchome.akbpfp.db.dao.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdTimeReport;

public interface IAdTimeReportDAO extends IBaseDAO<PfpAdTimeReport, Integer> {
	
	public List<AdTimeReportVO> getReportList(String sqlType, String searchTime, String searchText, String adSearchWay, String adPvclkDevice, String adType, String customerInfoId, String startDate, String endDate, int page, int pageSize) throws Exception;

}
