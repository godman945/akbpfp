package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdTimeReportVO;

public interface IAdTimeReportService {

	public List<AdTimeReportVO> loadReportDate(String sqlType, String searchTime, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception;

	/**
	 * 廣告播放時段成效(明細)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public List<AdTimeReportVO> queryReportAdTimeData(AdTimeReportVO vo) throws Exception;

	/**
	 * 廣告播放時段成效(加總)
	 * @param vo
	 * @return
	 */
	public List<AdTimeReportVO> queryReportAdTimeSumData(AdTimeReportVO vo);

	/**
	 * 廣告播放時段成效(圖表)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public List<AdTimeReportVO> queryReportAdTimeChartData(AdTimeReportVO vo) throws Exception;

}