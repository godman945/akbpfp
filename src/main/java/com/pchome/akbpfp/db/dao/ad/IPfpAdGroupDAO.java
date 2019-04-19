package com.pchome.akbpfp.db.dao.ad;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;

public interface IPfpAdGroupDAO extends IBaseDAO<PfpAdGroup,String>{
	
	public List<PfpAdGroup> getPfpAdGroups(String adGroupSeq, String adActionSeq, String adGroupName, String adGroupSearchPrice, String adGroupChannelPrice, String adGroupStatus) throws Exception;

	public List<Object> findAdGroupView(String adActionSeq, String adType, String adGroupName, String startDate, String endDate, int page, int pageSize, String customerInfoId) throws Exception;

	public List<Object> findAdGroupView(String adActionSeq, String adType, String adGroupSeq, String adGroupName, String adGroupStatus, String startDate, String endDate, int page, int pageSize, String customerInfoId) throws Exception;

	public boolean chkAdGroupNameByAdActionSeq(String adGroupName, String adGroupSeq, String adActionSeq) throws Exception;
	
	public PfpAdGroup getPfpAdGroupBySeq(String adGroupSeq) throws Exception;
	
	public void saveOrUpdatePfpAdGroup(PfpAdGroup pfpAdGroup) throws Exception;
	
	public void insertPfpAdGroup(PfpAdGroup pfpAdGroup) throws Exception;
	
	public void updatePfpAdGroup(PfpAdGroup pfpAdGroup) throws Exception;
	
	public void updatePfpAdGroupStatus(String pfpAdGroupStatus, String adGroupSeq) throws Exception;
	
	public void saveOrUpdateWithCommit(PfpAdGroup adGroup) throws Exception;
	
	/**
	 * (舊版)查詢廣告分類資料，由 pfp_ad_pvclks 統計資料
	 * @param customerInfoId
	 * @param adActionSeq
	 * @param keyword
	 * @param adType
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> getAdGroupPvclk(String customerInfoId, String adActionSeq, String keyword, int adType, Date startDate, Date endDate, int page, int pageSize) throws Exception;

	/**
	 * (新版)查詢廣告分類資料，由 pfp_ad_group_report 統計資料
	 * @param customerInfoId
	 * @param adActionSeq
	 * @param keyword
	 * @param adType
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return List<Object>
	 * @throws Exception
	 */
	public List<Object> getAdGroupReport(String customerInfoId, String adActionSeq, String keyword, int adType, Date startDate, Date endDate, int page, int pageSize) throws Exception;

	/**
	 * 查詢廣告分類資料(檢視廣告使用)
	 * @param customerInfoId
	 * @param adActionSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<PfpAdGroup> getPfpAdGroupForView(String customerInfoId, String adActionSeq, String keyword, int page, int pageSize) throws Exception;

	/**
	 * 查詢廣告分類成效(檢視廣告使用)
	 * @param customerInfoId
	 * @param adActionSeq
	 * @param adGroupSeq
	 * @param adType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<Object> getAdGroupReportByAGS(String customerInfoId, String adActionSeq, String adGroupSeq, int adType, Date startDate, Date endDate) throws Exception;

	/**
	 * 查詢廣告分類成效(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param adKeywordSeq
	 * @param adKeywordType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> getAdGroupReportByAdGroupsList(String customerInfoId, String adActionSeq, List<String> adGroupSeqList, int adType, Date startDate, Date endDate) throws Exception;

	public List<PfpAdGroup> validAdGroup(String adActionSeq) throws Exception;
}
