package com.pchome.akbpfp.db.dao.report;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdTimeReport;

public interface IAdTimeReportDAO extends IBaseDAO<PfpAdTimeReport, Integer> {
	
	public List<AdTimeReportVO> getReportList(String sqlType, String searchTime, String searchText, String adSearchWay, String adPvclkDevice, String adType, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception;

	/**
	 * 廣告播放時段成效(明細)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdTimeList(AdTimeReportVO vo);

	/**
	 * 廣告播放時段成效(加總)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdTimeListSum(AdTimeReportVO vo);

	/**
	 * 廣告播放時段成效(圖表)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdTimeListChart(AdTimeReportVO vo);

}