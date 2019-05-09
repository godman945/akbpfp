package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdAgesexReportVO;

public interface IAdAgesexReportService {

	public List<AdAgesexReportVO> loadReportDate(String sqlType, String searchAgesex, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception;

	/**
	 * 廣告族群成效(明細)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public List<AdAgesexReportVO> queryReportAdAgesexData(AdAgesexReportVO vo) throws Exception;

	/**
	 * 廣告族群成效(加總)
	 * @param vo
	 * @return
	 */
	public List<AdAgesexReportVO> queryReportAdAgesexSumData(AdAgesexReportVO vo);

	/**
	 * 廣告族群成效(圖表)
	 * @param chartVo
	 * @return
	 */
	public List<AdAgesexReportVO> queryReportAdAgesexChartData(AdAgesexReportVO vo);

}