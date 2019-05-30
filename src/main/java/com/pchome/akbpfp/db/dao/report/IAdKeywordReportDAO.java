package com.pchome.akbpfp.db.dao.report;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdKeywordPvclk;

public interface IAdKeywordReportDAO extends IBaseDAO<PfpAdKeywordPvclk, Integer> {
	
//	public List<AdKeywordReportVO> getReportList(String sqlType, String adGroupId, String searchText, String adSearchWay, String adShowWay,
//			String adPvclkDevice, String customerInfoId, String startDate, String endDate,
//			int page, int pageSize);

	/**
	 * 關鍵字成效(明細)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdKeywordList(AdKeywordReportVO vo);

	/**
	 * 取得平均廣告排名
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdKeywordRank(AdKeywordReportVO vo);

	/**
	 * 關鍵字成效(加總)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdKeywordListSum(AdKeywordReportVO vo);

	/**
	 * 關鍵字成效(圖表)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdKeywordListChart(AdKeywordReportVO vo);
	
}