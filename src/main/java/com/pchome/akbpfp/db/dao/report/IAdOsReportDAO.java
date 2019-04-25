package com.pchome.akbpfp.db.dao.report;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdOsReport;

public interface IAdOsReportDAO extends IBaseDAO<PfpAdOsReport, Integer> {
	
	public List<AdOsReportVO> getAdOsReportList(String sqlType, String adPvclkOs, String adSearchWay, String searchText, String customerInfoId, String startDate, String endDate, int page, int pageSize);

	/**
	 *  行動廣告成效(明細)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdOsList(AdOsReportVO vo);

	/**
	 * 行動廣告成效(加總)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdOsListSum(AdOsReportVO vo);
	
}
