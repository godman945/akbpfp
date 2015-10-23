package com.pchome.akbpfp.db.dao.ad;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;

public interface IPfpAdActionDAO extends IBaseDAO<PfpAdAction,String>{

	public List<PfpAdAction> findPfpAdAction(String customerInfoId) throws Exception;

	public List<PfpAdAction> getPfpAdActions(String adActionSeq, String adActionName, String adType, String adActionStartDate, String adActionEndDate, String adActionMax, String adActionStatus, String userID, PfpCustomerInfo pfpCustomerInfo) throws Exception;

	public List<Object> findAdActionView(String actionName, String startDate, String endDate, String adType, int page, int pageSize, String customerInfoId) throws Exception;

	public String getCount(String actionName, String startDate, String endDate, String adType, int page, int pageSize, String customerInfoId) throws Exception;

	public boolean chkAdActionNameByCustomerInfoId(String adActionName, String adActionSeq, String customerInfoId) throws Exception;

	public PfpAdAction getAdActionByAdActionName(String adActionName, String customerInfoId) throws Exception;
	
	public PfpAdAction getPfpAdActionBySeq(String adActionSeq) throws Exception;
	/**
	 * 依傳入的 seq 列表，查詢廣告活動資料
	 * @param seqList
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> getPfpAdActionBySeqList(List<String> seqList) throws Exception;
	
	public void saveOrUpdatePfpAdAction(PfpAdAction pfpAdAction) throws Exception;
	
	public void insertPfpAdAction(PfpAdAction pfpAdAction) throws Exception;
	
	public void updatePfpAdAction(PfpAdAction pfpAdAction) throws Exception;
	
	public void updatePfpAdActionStatus(String pfpAdActionStatus, String adActionSeq) throws Exception;
	
	public void updatePfpAdActionMax(String adActionSeq, String pfpAdActionMax) throws Exception;
	
	public List<PfpAdAction> getAdAction(String customerInfoId, Date today) throws Exception;

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
	public HashMap<String, Object> getAdActionReportByAdActionsList(String customerInfoId, List<String> adActionSeqList, String adType, Date startDate, Date endDate) throws Exception;
	
	/**
	 * (舊版)查詢 廣告管理>檢視廣告>廣告列表 的資料，查詢 pfp_ad_action、pfp_ad_group、pfp_ad、pfp_ad_pvclk outer join起來
	 * @param customerInfoId 客戶帳號
	 * @param keyword 關鍵字
	 * @param adType 廣告類型
	 * @param startDate 查詢開始時間
	 * @param endDate 查詢結束時間
	 * @param page 頁數
	 * @param pageSize 每頁的筆數
	 * @return List<Object> 回傳查詢的List
	 * @throws Exception
	 */
	public List<Object> getAdActionPvclk(String customerInfoId, String keyword, int adType, Date startDate, Date endDate, int page, int pageSize) throws Exception;
	
	/**
	 * (新版)查詢 廣告管理>檢視廣告>廣告列表 的資料，查詢 pfp_ad_action 跟 pfp_ad_action_report outer join起來
	 * @param customerInfoId 客戶帳號
	 * @param keyword 關鍵字
	 * @param adType 廣告類型
	 * @param startDate 查詢開始時間
	 * @param endDate 查詢結束時間
	 * @param page 頁數
	 * @param pageSize 每頁的筆數
	 * @return List<Object> 回傳查詢的List
	 * @throws Exception
	 */
	public List<Object> getAdActionReport(String customerInfoId, String keyword, int adType, Date startDate, Date endDate, int page, int pageSize) throws Exception;

	/**
	 * 查詢廣告活動資料(檢視廣告使用)
	 * @param customerInfoId
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<PfpAdAction> getPfpAdActionForView(String customerInfoId, String keyword, String adType, int page, int pageSize) throws Exception;

	/**
	 * 查詢廣告活動成效(檢視廣告使用)
	 * @param customerInfoId
	 * @param adActionSeq
	 * @param adType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAdActionReportByCID(String customerInfoId, String adActionSeq, int adType, Date startDate, Date endDate) throws Exception;
	
	public List<PfpAdAction> getAdActionByCustomerInfoId(String customerInfoId) throws Exception;
	
	public List<PfpAdAction> findBroadcastAdAction(String customerInfoId);

    public int getAdGroupCounts(String adActionSeq) throws Exception;
}
