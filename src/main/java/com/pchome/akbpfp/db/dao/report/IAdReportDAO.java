package com.pchome.akbpfp.db.dao.report;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdReport;

public interface IAdReportDAO extends IBaseDAO<PfpAdReport, Integer> {
	
	public List<AdReportVO> getReportList(String sqlType, String adGroupId, String adSeq, String searchText, String adSearchWay, String adPvclkDevice, String adType, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize);

	/**
	 * 總廣告成效-明細、廣告明細成效共用(明細)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdvertiseList(AdvertiseReportVO vo);

	/**
	 * 總廣告成效-明細、廣告明細成效共用(加總)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdvertiseListSum(AdvertiseReportVO vo);

	/**
	 * 總廣告成效-明細、廣告明細成效共用(圖表)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdvertiseListChart(AdvertiseReportVO vo);
	
}