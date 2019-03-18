package com.pchome.akbpfp.db.dao.ad;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdPvclk;

public interface IPfpAdPvclkDAO extends IBaseDAO<PfpAdPvclk,String>{
 
	/**
	 * 統計帳戶指定區間內的 adPv(曝光數)、adClk(點擊數)、adClkPrice(點擊費用)、adInvalidClk(無效點擊數)、adInvalidClkPrice(無效點擊費用)
	 * 舊版讀取 pfp_ad_pvclk
	 * 新版讀取 pfp_ad_action_report
	 * @param customerInfoId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<Object> totalPvclkCost(String customerInfoId, Date startDate, Date endDate) throws Exception;
	
	/**
	 * 依照紀錄日期為群組，group by 統計帳戶指定區間內的 adPv(曝光數)、adClk(點擊數)、adClkPrice(點擊費用)、adInvalidClk(無效點擊數)、adInvalidClkPrice(無效點擊費用)
	 * 舊版讀取 pfp_ad_pvclk
	 * 新版讀取 pfp_ad_action_report
	 * @param customerInfoId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<Object> chartPvclkCost(String customerInfoId, Date startDate, Date endDate) throws Exception;
//	
//	
//	
//	/**
//	 * 查詢開啟中的 seq 列表
//	 * 依傳入的 adLayer 查詢走期中的 PfpAdAction、PfpAdGroup、PfpAdKeyword、PfpAd 的編號列表
//	 * @param customerInfoId
//	 * @param adLayer
//	 * @param startDate
//	 * @param endDate
//	 * @return
//	 * @throws Exception
//	 */
//	public List<Object> getSeqList(String customerInfoId, String adLayer, Date startDate, Date endDate)throws Exception;
//
//
//	/**
//	 * 依傳入的 adLayer 查詢走期中廣告成效、分類成效、關鍵字成效、明細成效
//	 * @param customerInfoId
//	 * @param adLayer
//	 * @param seqList
//	 * @param startDate
//	 * @param endDate
//	 * @param page
//	 * @param pageSize
//	 * @return
//	 * @throws Exception
//	 */
//	public List<Object> adActionCost(String customerInfoId, String adLayer, List<Object> seqList, Date startDate, Date endDate, int page, int pageSize)throws Exception;

	/**
	 * 依傳入的 adLayer 查詢走期中廣告成效、分類成效、關鍵字成效、明細成效 筆數
	 * @param customerInfoId
	 * @param adLayer
	 * @param seqList 查詢序號列表
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public long getAdCostCount(String customerInfoId, String adLayer, Date startDate, Date endDate, int page, int pageSize)throws Exception;

	/**
	 * 依傳入的 adLayer 查詢走期中廣告成效、分類成效、關鍵字成效、明細成效
	 * @param customerInfoId
	 * @param adLayer
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAdCost(String customerInfoId, String adLayer, Date startDate, Date endDate, int page, int pageSize)throws Exception;
}
