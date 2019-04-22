package com.pchome.akbpfp.db.dao.report;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdActionReport;

public interface IAdActionReportDAO extends IBaseDAO<PfpAdActionReport, Integer> {
	
	public List<AdActionReportVO> getReportList(String sqlType, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize);

	/**
	 * 每日花費成效(明細)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdDailyList(AdActionReportVO vo);

	/**
	 * 每日花費成效(加總)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdDailyListSum(AdActionReportVO vo);

//	/**
//	 * 每日花費成效(圖表)
//	 * @param vo
//	 * @return
//	 */
//	public List<AdActionReportVO> getAdDailyChartDataList(AdActionReportVO vo);
}
