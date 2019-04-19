package com.pchome.akbpfp.db.dao.ad;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.mapping.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;

public interface IPfpAdKeywordDAO extends IBaseDAO<PfpAdKeyword,String>{
	
	public List<PfpAdKeyword> findAdKeywords(String adGroupSeq);
	
	public List<PfpAdKeyword> findAdKeywords(String adKeywordSeq, String adGroupSeq, String adKeyword, String adKeywordSearchPrice, String adKeywordChannelPrice, String adKeywordStatus) throws Exception;

	//public List<Object> findAdKeywordView(String adActionSeq, String adType, String adGroupSeq, String adKeywordStatus, String searchWord, String startDate, String endDate, int page, int pageSize, String customerInfoId) throws Exception;

	//public String getCount(String adActionSeq, String adType, String adGroupSeq, String adKeywordStatus, String searchWord, String startDate, String endDate, int page, int pageSize, String customerInfoId) throws Exception;

	public List<PfpAdKeyword> findAdKeyword(String adKeywordSeq) throws Exception;

	public PfpAdKeyword getPfpAdKeywordBySeq(String adKeywordSeq) throws Exception;

	public void saveOrUpdatePfpAdKeyword(PfpAdKeyword pfpAdKeyword) throws Exception;
	
	public void insertPfpAdKeyword(PfpAdKeyword pfpAdKeyword) throws Exception;
	
	public void updatePfpAdKeyword(PfpAdKeyword pfpAdKeyword) throws Exception;
	
	public void updatePfpAdKeywordStatus(String pfpAdKeywordStatus, String adKeywordSeq) throws Exception;
	
	public void saveOrUpdateWithCommit(PfpAdKeyword adKeyword) throws Exception;
	
	public List<Object> getAdRank(String customerInfoId, String adGroupSeq, String keyword, Date startDate, Date endDate) throws Exception;

	/**
	 * 查詢廣告關鍵字筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public long getPfpAdKeywordCount(String customerInfoId, String adGroupSeq, String keyword, int page, int pageSize) throws Exception;

	/**
	 * (舊版)查詢關鍵字統計資料，由 pfp_ad_keyword_pvclk 統計資料
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAdKeywordPvclk(String customerInfoId, String adGroupSeq, String keyword, Date startDate, Date endDate, int page, int pageSize) throws Exception;

	/**
	 * (新版)查詢關鍵字統計資料，由 pfp_ad_keyword_pvclk 統計資料
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAdKeywordReport(String customerInfoId, String adGroupSeq, String keyword, Date startDate, Date endDate, int page, int pageSize) throws Exception;

	/**
	 * 查詢廣告關鍵字資料(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<PfpAdKeyword> getPfpAdKeywordForView(String customerInfoId, String adGroupSeq, String keyword, int page, int pageSize) throws Exception;

	/**
	 * 查詢廣告關鍵字成效(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param adKeywordSeq
	 * @param adKeywordType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAdKeywordReportByAKS(String customerInfoId, String adGroupSeq, String adKeywordSeq, int adKeywordType, Date startDate, Date endDate) throws Exception;

	/**
	 * 查詢廣告關鍵字平均排名(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param adKeywordSeq
	 * @param adKeywordType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAdRankByAGS(String customerInfoId, String adGroupSeq, int adKeywordType, Date startDate, Date endDate) throws Exception;

	/**
	 * 查詢廣告關鍵字序號列表(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<String> getPfpAdKeywordSeqList(String customerInfoId, String adGroupSeq, String keyword, int page, int pageSize) throws Exception;

	/**
	 * 查詢廣告關鍵字成效(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param adKeywordSeq
	 * @param adKeywordType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> getAdKeywordReportByAdKeywordsList(String customerInfoId, String adGroupSeq, List<String> adKeywordSeqList, int adKeywordType, Date startDate, Date endDate) throws Exception;

	public List<PfpAdKeyword> validAdKeyword(String adGroupSeq) throws Exception;
	
	//keyWord list update
	public void savePfpAdKeywordList(List<PfpAdKeyword> pfpAdKeywordList) throws Exception;
}
