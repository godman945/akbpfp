package com.pchome.akbpfp.db.service.ad;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.dao.ad.PfpAdGroupDAO;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.ad.PfpAdGroupViewVO;
import com.pchome.enumerate.ad.EnumAdType;


public interface IPfpAdGroupService extends IBaseService<PfpAdGroup,String>{
	
	public List<PfpAdGroup> getAllPfpAdGroups() throws Exception;

	public List<Object> findAdGroupView(String adActionSeq, String adType, String adGroupName, String startDate, String endDate, int page, int pageSize, String customerInfoId) throws Exception;

	public List<Object> findAdGroupView(String adActionSeq, String adType, String adGroupSeq, String adGroupName, String adGroupStatus, String startDate, String endDate, int page, int pageSize, String customerInfoId) throws Exception;
	
	public String getCount(String adActionSeq, String adType, String adGroupSeq, String adGroupName, String adGroupStatus, String startDate, String endDate, int page, int pageSize, String customerInfoId) throws Exception;

	public List<PfpAdGroup> getPfpAdGroups(String adGroupSeq, String adActionSeq, String adGroupName, String adGroupSearchPrice, String adGroupChannelPrice, String adGroupStatus) throws Exception;
	
	public boolean chkAdGroupNameByAdActionSeq(String adGroupName, String adGroupSeq, String adActionSeq) throws Exception;
	
	public PfpAdGroup getPfpAdGroupBySeq(String adGroupSeq) throws Exception;
	
	public void insertPfpAdGroup(PfpAdGroup pfpAdGroup) throws Exception;
	
	public void updatePfpAdGroup(PfpAdGroup pfpAdGroup) throws Exception;
	
	public void updatePfpAdGroupStatus(String pfpAdGroupStatus, String adGroupSeq) throws Exception;
	
	public void savePfpAdGroup(PfpAdGroup adGroup) throws Exception;
	
	public void saveOrUpdateWithCommit(PfpAdGroup adGroup) throws Exception;

	/**
	 * 查詢廣告分類全部筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param adActionSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public long getPfpAdGroupCount(String customerInfoId, String adActionSeq, String keyword) throws Exception;

	/**
	 * 查詢廣告分類分頁筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param adActionSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public long getPfpAdGroupCount(String customerInfoId, String adActionSeq, String keyword, int page, int pageSize) throws Exception;
	
	/**
	 * 查詢全部廣告分類成效(For 檢視廣告)
	 * 拆成兩段進行，1. 先查詢廣告分類的資料。 2.依查詢出來的廣告分類，再去查詢廣告成效
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
	public List<PfpAdGroupViewVO> getAdGroupView(String customerInfoId, String adActionSeq, String keyword, EnumAdType enumAdType, Date startDate, Date endDate, int page, int pageSize) throws Exception;
	
	/**
	 * 查詢全部廣告分類成效(For 檢視廣告)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<PfpAdGroupViewVO> getAllAdGroupView(String customerInfoId, String adActionSeq, String keyword, EnumAdType enumAdType, Date startDate, Date endDate) throws Exception;

	public List<PfpAdGroup> validAdGroup(String adActionSeq) throws Exception; 
	
}
