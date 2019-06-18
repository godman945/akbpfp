package com.pchome.akbpfp.db.service.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.report.AdReportVO;
import com.pchome.akbpfp.db.dao.report.AdvertiseReportVO;

public interface IAdReportService {

	public List<AdReportVO> loadReportDate(String sqlType, String adGroupSeq, String adSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception;

	/**
	 * 廣告明細成效(明細)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public List<AdvertiseReportVO> queryReportAdvertiseData(AdvertiseReportVO vo) throws Exception;

	/**
	 * 廣告明細成效(加總)
	 * @param vo
	 * @return
	 */
	public List<AdvertiseReportVO> queryReportAdvertiseSumData(AdvertiseReportVO vo);

	/**
	 * 廣告明細成效(圖表)
	 * @param chartVo
	 * @return list
	 */
	public List<AdvertiseReportVO> queryReportAdvertiseChartData(AdvertiseReportVO vo);

	/**
	 * 廣告明細成效(圖表)
	 * @param chartVo
	 * @return map
	 */
	public Map<Date, Float> queryReportAdvertiseChartDataMap(AdvertiseReportVO vo);

}