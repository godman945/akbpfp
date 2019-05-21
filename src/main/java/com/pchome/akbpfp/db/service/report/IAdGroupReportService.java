package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdGroupReportVO;

public interface IAdGroupReportService {

	public List<AdGroupReportVO> loadReportDate(String sqlType, String adActionSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception;

	/**
	 * 分類成效(明細)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public List<AdGroupReportVO> queryReportAdGroupData(AdGroupReportVO vo) throws Exception;

	/**
	 * 分類成效(加總)
	 * @param vo
	 * @return
	 */
	public List<AdGroupReportVO> queryReportAdGroupSumData(AdGroupReportVO vo);

	/**
	 * 分類成效(圖表)
	 * @param vo
	 * @return
	 */
	public List<AdGroupReportVO> queryReportAdGroupChartData(AdGroupReportVO vo);

}