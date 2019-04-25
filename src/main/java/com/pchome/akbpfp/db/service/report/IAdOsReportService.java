package com.pchome.akbpfp.db.service.report;

import java.util.List;

import com.pchome.akbpfp.db.dao.report.AdOsReportVO;

public interface IAdOsReportService {

	public List<AdOsReportVO> loadOsReportDate(String sqlType, String adPvclkOs, String adSearchWay, String searchText, String customerInfoId,String startDate, String endDate,int page,int pageSize) throws Exception ;

	/**
	 * 行動廣告成效(明細)
	 * @param vo
	 * @return
	 */
	public List<AdOsReportVO> queryReportAdOsData(AdOsReportVO vo);

	/**
	 * 行動廣告成效(加總)
	 * @param vo
	 * @return
	 */
	public List<AdOsReportVO> queryReportAdOsSumData(AdOsReportVO vo);

	/**
	 * 行動廣告成效(圖表)
	 * @param reportVo
	 * @return
	 */
	public List<AdOsReportVO> queryReportAdOsChartData(AdOsReportVO vo);

}