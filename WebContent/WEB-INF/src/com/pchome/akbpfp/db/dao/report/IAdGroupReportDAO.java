package com.pchome.akbpfp.db.dao.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdGroupReport;

public interface IAdGroupReportDAO extends IBaseDAO<PfpAdGroupReport, Integer> {
	
	public List<AdGroupReportVO> getReportList(String sqlType, String adActionSeq, String searchText, String adSearchWay, String adPvclkDevice, String adType, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception;

}
