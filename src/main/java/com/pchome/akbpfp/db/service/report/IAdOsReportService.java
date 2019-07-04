package com.pchome.akbpfp.db.service.report;

import java.util.List;
import java.util.Map;

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
	 * @param vo
	 * @return List
	 */
	public List<AdOsReportVO> queryReportAdOsChartData(AdOsReportVO vo);

	/**
	 * 行動廣告成效(圖表)
	 * @param vo
	 * @return Map
	 */
	public Map<String, Float> queryReportAdOsChartDataMap(AdOsReportVO vo);

}