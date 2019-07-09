package com.pchome.akbpfp.db.dao.report;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdGroupReport;

public interface IAdGroupReportDAO extends IBaseDAO<PfpAdGroupReport, Integer> {
	
	public List<AdGroupReportVO> getReportList(String sqlType, String adActionSeq, String searchText, String adSearchWay, String adPvclkDevice, String adType, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception;

	/**
	 * 總廣告成效-分類、分類成效共用(明細)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdGroupList(AdGroupReportVO vo);

	/**
	 * 總廣告成效-分類、分類成效共用(加總)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdGroupListSum(AdGroupReportVO vo);

	/**
	 * 總廣告成效-分類、分類成效共用(圖表)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdGroupListChart(AdGroupReportVO vo);

}