package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdActionReportVO;

public interface IAdActionReportService {

	public List<AdActionReportVO> loadReportDate(String sqlType, String searchText, String adSearchWay,
			String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate,
			int page, int pageSize);

	/**
	 * 每日花費成效(明細)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public List<AdActionReportVO> queryReportAdDailyData(AdActionReportVO vo) throws Exception;

	/**
	 * 每日花費成效(加總)
	 * @param vo
	 * @return
	 */
	public List<AdActionReportVO> queryReportAdDailySumData(AdActionReportVO vo);
	
	/**
	 * 每日花費成效(圖表)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public List<AdActionReportVO> queryReportAdDailyChartData(AdActionReportVO vo) throws Exception;
	
	/**
	 * 取得裝置中文
	 * @param whereMap JSONObject格式字串
	 * @return
	 */
	public String getAdDeviceName(String JSONObject);

}