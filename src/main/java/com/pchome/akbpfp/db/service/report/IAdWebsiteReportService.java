package com.pchome.akbpfp.db.service.report;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.report.AdWebsiteReportVO;

public interface IAdWebsiteReportService {
	public List<AdWebsiteReportVO> loadReportDate(String sqlType, String searchWebsiteCode, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String adOperatingRule, String startDate, String endDate, int page, int pageSize) throws Exception;

	/**
	 * 網站類型成效(明細)
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public List<AdWebsiteReportVO> queryReportAdWebsiteData(AdWebsiteReportVO vo) throws Exception;

	/**
	 * 網站類型成效(加總)
	 * @param vo
	 * @return
	 */
	public List<AdWebsiteReportVO> queryReportAdWebsiteSumData(AdWebsiteReportVO vo);

	/**
	 * 網站類型成效(圖表)
	 * @param chartVo
	 * @return
	 */
	public List<AdWebsiteReportVO> queryReportAdWebsiteChartData(AdWebsiteReportVO vo);
	
	/**
	 * 取得網站類型資料
	 * @return
	 */
	public Map<String, String> getWebsiteCategoryMap();

}