package com.pchome.akbpfp.db.service.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.report.AdGroupReportVO;

public interface IAdGroupReportService {

	public List<AdGroupReportVO> loadReportDate(String sqlType, String adActionSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception;

	/**
	 * 總廣告成效-分類、分類成效共用(明細)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public List<AdGroupReportVO> queryReportAdGroupData(AdGroupReportVO vo) throws Exception;

	/**
	 * 總廣告成效-分類、分類成效共用(加總)
	 * @param vo
	 * @return
	 */
	public List<AdGroupReportVO> queryReportAdGroupSumData(AdGroupReportVO vo);

	/**
	 * 分類成效(圖表)
	 * @param vo
	 * @return list
	 */
	public List<AdGroupReportVO> queryReportAdGroupChartData(AdGroupReportVO vo);

	/**
	 * 總廣告成效-分類、分類成效共用(圖表)
	 * @param vo
	 * @return map
	 */
	public Map<Date, Float> queryReportAdGroupChartDataMap(AdGroupReportVO vo);

}