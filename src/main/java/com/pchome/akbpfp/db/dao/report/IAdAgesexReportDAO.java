package com.pchome.akbpfp.db.dao.report;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdAgeReport;

public interface IAdAgesexReportDAO extends IBaseDAO<PfpAdAgeReport, Integer> {
	
	public List<AdAgesexReportVO> getReportList(String sqlType, String searchAgesex, String searchText, String adSearchWay, String adPvclkDevice, String adType, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception;

	/**
	 * 廣告族群成效(明細)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdAgesexList(AdAgesexReportVO vo);

	/**
	 * 廣告族群成效(加總)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdAgesexListSum(AdAgesexReportVO vo);

	/**
	 * 廣告族群成效(圖表)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdAgesexListChart(AdAgesexReportVO vo);

}