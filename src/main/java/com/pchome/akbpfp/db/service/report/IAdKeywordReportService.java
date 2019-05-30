package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdKeywordReportVO;

public interface IAdKeywordReportService {

//	public List<AdKeywordReportVO> loadReportDate(String sqlType, String adGroupId, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice,
//			String customerInfoId, String startDate, String endDate, int page, int pageSize);

	/**
	 * 關鍵字成效(明細)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public List<AdKeywordReportVO> queryReportAdKeywordData(AdKeywordReportVO vo) throws Exception;

	/**
	 * 關鍵字成效(加總)
	 * @param vo
	 * @return
	 */
	public List<AdKeywordReportVO> queryReportAdKeywordSumData(AdKeywordReportVO vo);

	/**
	 * 關鍵字成效(圖表)
	 * @param chartVo
	 * @return
	 */
	public List<AdKeywordReportVO> queryReportAdKeywordChartData(AdKeywordReportVO vo);

}