package com.pchome.akbpfp.db.service.ad;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.dao.ad.PfpAdKeywordDAO;
import com.pchome.akbpfp.db.pojo.AdmAccesslog;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.ad.PfpAdKeywordViewVO;


public interface IPfpAdKeywordService extends IBaseService<PfpAdKeyword,String>{
	
	public List<PfpAdKeyword> findAdKeywords(String adGroupSeq);
	
	public List<PfpAdKeyword> findAdKeywords(String adKeywordSeq, String adGroupSeq, String adKeyword, String adKeywordSearchPrice, String adKeywordChannelPrice, String adKeywordStatus) throws Exception;

	public PfpAdKeyword findAdKeyword(String adKeywordSeq)throws Exception;

	/**
	 * 查詢廣告關鍵字全部筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public long getPfpAdKeywordCount(String customerInfoId, String adGroupSeq, String keyword) throws Exception;

	/**
	 * 查詢廣告關鍵字分頁筆數(檢視廣告使用)
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
	 * (新版)查詢關鍵字統計全部資料，由 pfp_ad_keyword_pvclk 統計資料
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
	public List<Object> getAdKeywordReport(String customerInfoId, String adGroupSeq, String keyword, Date startDate, Date endDate) throws Exception;

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
	public List<PfpAdKeywordViewVO> getAdKeywordReport(String customerInfoId, String adGroupSeq, String keyword, Date startDate, Date endDate, int page, int pageSize) throws Exception;
	
	/**
	 * 查詢關鍵字廣告成效(For 檢視廣告)
	 * 拆成兩段進行，1. 先查詢關鍵字廣告的資料。 2.依查詢出來的關鍵字廣告，再去查詢平均排行及成效
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
	public List<PfpAdKeywordViewVO> findAdKeywordView(String customerInfoId, String adGroupSeq, String keyword, Date startDate, Date endDate, int page, int pageSize) throws Exception;

	/**
	 * 查詢全部的關鍵字廣告成效(For 檢視廣告)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<PfpAdKeywordViewVO> findAdKeywordView(String customerInfoId, String adGroupSeq, String keyword, Date startDate, Date endDate) throws Exception;
	
	public void saveOrUpdateWithCommit(PfpAdKeyword adKeyword) throws Exception;

	public List<PfpAdKeyword> validAdKeyword(String adGroupSeq) throws Exception;








	/**
	 * 查詢關鍵字廣告成效(For 檢視廣告)
	 * 拆成兩段進行，1. 先查詢關鍵字廣告的資料。 2.依查詢出來的關鍵字廣告，再去查詢平均排行及成效
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
	public List<PfpAdKeywordViewVO> getAdKeywordView(String customerInfoId, String adGroupSeq, String keyword, Date startDate, Date endDate, int page, int pageSize) throws Exception;
	
	//keyWord list update
	public void savePfpAdKeywordList(List<PfpAdKeyword> pfpAdKeywordList) throws Exception;

}
