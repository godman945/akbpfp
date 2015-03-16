package com.pchome.akbpfp.db.service.ad;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.ad.PfpAdActionViewVO;
import com.pchome.enumerate.ad.EnumAdType;

public interface IPfpAdActionService extends IBaseService<PfpAdAction,String>{

	public boolean chkAdActionNameByCustomerInfoId(String adActionName, String adActionSeq, String customerInfoId) throws Exception;

	public PfpAdAction getAdActionByAdActionName(String adActionName, String customerInfoId) throws Exception;
	
	public PfpAdAction getPfpAdActionBySeq(String adActionSeq) throws Exception;

	public void updatePfpAdActionStatus(String pfpAdActionStatus, String adActionSeq) throws Exception;

	public void savePfpAdAction(PfpAdAction adAction) throws Exception;
	
	public List<PfpAdAction> getAdAction(String customerInfoId, Date today) throws Exception;
	
	public List<PfpAdActionViewVO> getAdActionView(String customerInfoId, String keyword, EnumAdType enumAdType, Date startDate, Date endDate, int page, int pageSize) throws Exception;

	/**
	 * 查詢廣告活動全部筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public long getPfpAdActionCount(String customerInfoId, String keyword) throws Exception;

	/**
	 * 查詢廣告活動分頁筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public long getPfpAdActionCount(String customerInfoId, String keyword, int page, int pageSize) throws Exception;

	public List<PfpAdAction> getAdActionByCustomerInfoId(String customerInfoId) throws Exception;
	
	public List<PfpAdAction> findBroadcastAdAction(String customerInfoId);
	
	public int getAdGroupCounts(String adActionSeq) throws Exception;
	
	//public Integer checkAdActionNumber(String customerInfoId) throws Exception;

	//public List<PfpAdAction> getAllPfpAdActions() throws Exception;
	
	//public List<PfpAdAction> getPfpAdActions(String adActionSeq, String adActionName, String adType, String adActionStartDate, String adActionEndDate, String adActionMax, String adActionStatus, String userID, PfpCustomerInfo pfpCustomerInfo) throws Exception;

	//public List<Object> findAdActionView(String actionName, String startDate, String endDate, String adType, int page, int pageSize, String customerInfoId) throws Exception;

	//public String getCount(String actionName, String startDate, String endDate, String adType, int page, int pageSize, String customerInfoId) throws Exception;
	
	//public void insertPfpAdAction(PfpAdAction pfpAdAction) throws Exception;
	
	//public void updatePfpAdAction(PfpAdAction pfpAdAction) throws Exception;
	
	//public void updatePfpAdActionMax(String adActionSeq, String pfpAdActionMax) throws Exception;

	//public List<PfpAdActionViewVO> getAllAdActionView(String customerInfoId, String keyword, EnumAdType enumAdType, Date startDate, Date endDate) throws Exception;
}
