package com.pchome.akbpfp.db.dao.ad;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.akbpfp.db.vo.ad.PfpAdAdViewConditionVO;

public interface IPfpAdDAO extends IBaseDAO<PfpAd,String>{
	
	public List<PfpAd> getPfpAds(String adSeq, String adGroupSeq, String adClass, String adArea, String adStyle, String adStatus) throws Exception;

	public List<Object> findAdView(String adActionSeq, String adType, String adGroupSeq, String adSeq, String adStatus, String searchWord, String startDate, String endDate, int page, int pageSize, String customerInfoId) throws Exception;
	
	public String getCount(String adActionSeq, String adType, String adGroupSeq, String adSeq, String adStatus, String searchWord, String startDate, String endDate, int page, int pageSize, String customerInfoId) throws Exception;

	public PfpAd getPfpAdBySeq(String adSeq) throws Exception;
	
	public void saveOrUpdatePfpAd(PfpAd pfpAd) throws Exception;
	
	public void insertPfpAd(PfpAd pfpAd) throws Exception;
	
	public void updatePfpAd(PfpAd pfpAd) throws Exception;
	
	public void updatePfpAdStatus(String pfpAdStatus, String adSeq) throws Exception;
	
	public void saveOrUpdateWithCommit(PfpAd adAd) throws Exception;
	
	public List<Object> getAdAdVideoDetailView(PfpAdAdViewConditionVO pfpAdAdViewConditionVO) throws Exception;
	
	public List<Object> getAdAdVideoDetailViewCount(PfpAdAdViewConditionVO pfpAdAdViewConditionVO) throws Exception;
	
	/**
	 * (舊版)查詢廣告分類資料，由 pfp_ad_report 統計資料
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param adType
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAdAdPvclk(String customerInfoId, String adGroupseq, String keyword, int adType, Date startDate, Date endDate, int page, int pageSize) throws Exception;


	/**
	 * (新版)查詢廣告分類資料，由 pfp_ad_report 統計資料
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param adType
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAdAdReport(String customerInfoId, String adGroupseq, String keyword, int adType, Date startDate, Date endDate, int page, int pageSize) throws Exception;

	/**
	 * 查詢廣告明細資料(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<PfpAd> getPfpAdForView(String customerInfoId, String adGroupSeq, List<String> adSeqList, int page, int pageSize) throws Exception;

	/**
	 * 查詢廣告明細詳細內容
	 * @param adSeq
	 * @param adDetailId
	 * @param adDetailContent
	 * @return
	 * @throws Exception
	 */
	public List<PfpAdDetail> getPfpAdDetailByCondition(String adSeq, String adDetailId, String adDetailContent) throws Exception;

	/**
	 * 查詢廣告明細成效(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param adSeq
	 * @param adType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAdReportByAKS(String customerInfoId, String adGroupSeq, String adSeq, int adType, Date startDate, Date endDate) throws Exception;

	/**
	 * 查詢廣告明細筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public long getPfpAdCount(String customerInfoId, String adGroupSeq, String keyword, int page, int pageSize) throws Exception;

	/**
	 * 查詢廣告明細成效(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param adSeq
	 * @param adType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getAdReportByAdsList(String customerInfoId, String adGroupSeq, List<String> adSeqList, int adType, Date startDate, Date endDate) throws Exception;

	public List<PfpAd> validAdAd(String adGroupSeq) throws Exception;
}
