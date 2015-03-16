package com.pchome.akbpfp.db.dao.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdKeywordPvclk;

public interface IAdKeywordReportDAO extends IBaseDAO<PfpAdKeywordPvclk, Integer> {
	
	public List<AdKeywordReportVO> getReportList(String sqlType, String adGroupId, String searchText, String adSearchWay, String adShowWay,
			String adPvclkDevice, String customerInfoId, String startDate, String endDate,
			int page, int pageSize);
	
}
