package com.pchome.akbpfp.db.dao.report;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdWebsiteReport;

public interface IAdWebsiteReportDAO extends IBaseDAO<PfpAdWebsiteReport, Integer> {
	
	public List<AdWebsiteReportVO> getReportList(String sqlType, String searchWebsiteCode, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception;

	/**
	 * 網站類型成效(明細)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdWebsiteList(AdWebsiteReportVO vo);

	/**
	 * 網站類型成效(加總)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdWebsiteListSum(AdWebsiteReportVO vo);

	/**
	 * 網站類型成效(圖表)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdWebsiteListChart(AdWebsiteReportVO vo);

}