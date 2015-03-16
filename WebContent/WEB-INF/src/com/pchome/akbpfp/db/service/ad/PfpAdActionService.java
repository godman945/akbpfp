package com.pchome.akbpfp.db.service.ad;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pchome.akbpfp.db.dao.ad.IPfpAdActionDAO;
import com.pchome.akbpfp.db.dao.ad.PfpAdActionDAO;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.ad.PfpAdActionViewVO;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.soft.util.DateValueUtil;
import com.pchome.utils.CommonUtils;

public class PfpAdActionService extends BaseService<PfpAdAction,String> implements IPfpAdActionService{

	public boolean chkAdActionNameByCustomerInfoId(String adActionName, String adActionSeq, String customerInfoId) throws Exception {
		return ((PfpAdActionDAO)dao).chkAdActionNameByCustomerInfoId(adActionName, adActionSeq, customerInfoId);
	}
	
	public PfpAdAction getAdActionByAdActionName(String adActionName, String customerInfoId) throws Exception {
		return ((PfpAdActionDAO)dao).getAdActionByAdActionName(adActionName, customerInfoId);
	}

	public PfpAdAction getPfpAdActionBySeq(String adActionSeq) throws Exception {
		return ((PfpAdActionDAO)dao).getPfpAdActionBySeq(adActionSeq);
	}

	public void updatePfpAdActionStatus(String pfpAdActionStatus, String adActionSeq) throws Exception {
		((PfpAdActionDAO)dao).updatePfpAdActionStatus(pfpAdActionStatus, adActionSeq);
	}

	public void savePfpAdAction(PfpAdAction adAction) throws Exception {
		((PfpAdActionDAO)dao).saveOrUpdatePfpAdAction(adAction);
	}

	public List<PfpAdAction> getAdAction(String customerInfoId, Date today) throws Exception{
		return ((PfpAdActionDAO)dao).getAdAction(customerInfoId, today);
	}

	public List<PfpAdActionViewVO> getAdActionView(String customerInfoId, String keyword, EnumAdType enumAdType, Date startDate, Date endDate, int page, int pageSize) throws Exception{

		// 先攔掉 SQL Injection 攻擊語法
		customerInfoId = CommonUtils.filterSqlInjection(customerInfoId);
		keyword = CommonUtils.filterSqlInjection(keyword);
	
		List<PfpAdActionViewVO> adActionViewVOs = null;
		
		// 查詢廣告活動
		List<PfpAdAction> pfpAdActions = ((PfpAdActionDAO)dao).getPfpAdActionForView(customerInfoId, keyword, page, pageSize);
		
		if(pfpAdActions.size() > 0) {
			// 逐筆讀出本頁的廣告活動序號
			List<String> adActionSeqs = new ArrayList<String>();
			for(PfpAdAction pfpAdAction:pfpAdActions) {
				adActionSeqs.add(pfpAdAction.getAdActionSeq());
			}

			// 依照讀出本頁的廣告活動序號，查詢關鍵字成效
			HashMap<String, Object> pfpAdActionReports = ((PfpAdActionDAO)dao).getAdActionReportByAdActionsList(customerInfoId, adActionSeqs, enumAdType.getType(), startDate, endDate);
	
			for(PfpAdAction pfpAdAction:pfpAdActions) {
				if(adActionViewVOs == null){
					adActionViewVOs = new ArrayList<PfpAdActionViewVO>();
				}
	
				PfpAdActionViewVO adActionViewVO = new PfpAdActionViewVO();
				adActionViewVO.setAdActionSeq(pfpAdAction.getAdActionSeq());
				adActionViewVO.setAdActionName(pfpAdAction.getAdActionName());
	
				// 廣告類型
				for(EnumAdType type:EnumAdType.values()){
					int adType = pfpAdAction.getAdType();					
					if(type.getType() == adType){
						adActionViewVO.setAdType(type.getChName());
					}					
				}
				// 廣告狀態
				for(EnumStatus status:EnumStatus.values()){
					int adStatus = pfpAdAction.getAdActionStatus();
					if(status.getStatusId() == adStatus){
						adActionViewVO.setAdActionStatus(adStatus);
						adActionViewVO.setAdActionStatusDesc(status.getStatusDesc());
					}
				}
				
				adActionViewVO.setAdActionMax(pfpAdAction.getAdActionMax());								
				
				// 判斷廣告走期
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				adActionViewVO.setAdStartDate(sdf.format(pfpAdAction.getAdActionStartDate()));
				adActionViewVO.setAdEndDate(sdf.format(pfpAdAction.getAdActionEndDate()));
				
				Date adStartDate = DateValueUtil.getInstance().stringToDate(adActionViewVO.getAdStartDate());
				Date adEndDate = DateValueUtil.getInstance().stringToDate(adActionViewVO.getAdEndDate());
	
				if(adActionViewVO.getAdActionStatus() == EnumStatus.Open.getStatusId()){
					
					Date today = DateValueUtil.getInstance().getNowDateTime();
					Date yesterday = DateValueUtil.getInstance().getDateForStartDateAddDay(DateValueUtil.getInstance().dateToString(today), -1);
					
					if(today.after(adStartDate) && yesterday.before(adEndDate)){ // 因為結束時間為  00:00:00 所以取昨天比較
						adActionViewVO.setAdActionStatusDesc(EnumStatus.Broadcast.getStatusDesc());
					}
					else if(today.before(adStartDate)){
						adActionViewVO.setAdActionStatusDesc(EnumStatus.Waitbroadcast.getStatusDesc());
					}
					else if(today.after(adEndDate)){ 
						adActionViewVO.setAdActionStatusDesc(EnumStatus.End.getStatusDesc());
					}
				}
	
				// 依照關鍵字廣告序號，讀取、設定廣告成效，沒有廣告成效的，就不用設定了
				if(pfpAdActionReports.size() > 0 && pfpAdActionReports.get(pfpAdAction.getAdActionSeq()) != null) {
					Object[] obj = (Object[])pfpAdActionReports.get(pfpAdAction.getAdActionSeq());
					if(obj != null) {
						// 求點閱率				
						int pv = Integer.parseInt(obj[1].toString());
						int clk = Integer.parseInt(obj[2].toString());
						float clkPrice = Float.parseFloat(obj[3].toString());
						int invalidClk = Integer.parseInt(obj[4].toString());
						//float invalidClkPrice = Float.parseFloat(obj[5].toString());
	
						//clkPrice = clkPrice - invalidClkPrice;
						//clk = clk - invalidClk;
						
	
						adActionViewVO.setAdPv(pv);
						adActionViewVO.setAdClk(clk);
						adActionViewVO.setAdClkPrice(clkPrice);
						adActionViewVO.setInvalidClk(invalidClk);
	
						float clkRate = 0;
						float clkPriceAvg = 0;
						
						if(clk > 0 || pv > 0){
							clkRate = (float)clk / (float)pv*100;
						}
	
						if(clkPrice > 0 || clk > 0){
							clkPriceAvg = clkPrice / (float)clk;
						}
	
						adActionViewVO.setAdClkRate(clkRate);
						adActionViewVO.setAdClkPriceAvg(clkPriceAvg);
					}
				}
				adActionViewVOs.add(adActionViewVO);
			}
		}
		
		return adActionViewVOs;	
	}

	/**
	 * 查詢廣告活動全部筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public long getPfpAdActionCount(String customerInfoId, String keyword) throws Exception {
		return ((PfpAdActionDAO)dao).getPfpAdActionCount(customerInfoId, keyword, -1, -1);
	}

	/**
	 * 查詢廣告活動分頁筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public long getPfpAdActionCount(String customerInfoId, String keyword, int page, int pageSize) throws Exception {
		return ((PfpAdActionDAO)dao).getPfpAdActionCount(customerInfoId, keyword, page, pageSize);
	}

	public List<PfpAdAction> getAdActionByCustomerInfoId(String customerInfoId) throws Exception{
		return ((PfpAdActionDAO)dao).getAdActionByCustomerInfoId(customerInfoId);
	}
	
	public List<PfpAdAction> findBroadcastAdAction(String customerInfoId) {
	    return ((IPfpAdActionDAO) dao).findBroadcastAdAction(customerInfoId);
	}
	
	public int getAdGroupCounts(String adActionSeq) throws Exception {
	    return ((IPfpAdActionDAO) dao).getAdGroupCounts(adActionSeq);
	}

//	public Integer checkAdActionNumber(String customerInfoId) throws Exception{
//		
//		List<PfpAdAction> adActionList = ((PfpAdActionDAO) dao).findPfpAdAction(customerInfoId);
//		
//		if(adActionList.size() > 0){
//			return adActionList.size();
//		}
//		else{
//			return 0;
//		}
//		
//	}
	
//	public List<PfpAdAction> getAllPfpAdActions() throws Exception{
//		return ((PfpAdActionDAO)dao).loadAll();
//	}
	
//	public List<PfpAdAction> getPfpAdActions(String adActionSeq, String adActionName, String adType, String adActionStartDate, String adActionEndDate, String adActionMax, String adActionStatus, String userID, PfpCustomerInfo pfpCustomerInfo) throws Exception{
//		return ((PfpAdActionDAO)dao).getPfpAdActions(adActionSeq, adActionName, adType, adActionStartDate, adActionEndDate, adActionMax, adActionStatus, userID, pfpCustomerInfo);
//	}
	
//	public List<Object> findAdActionView(String actionName, String startDate, String endDate, String adType, int page, int pageSize, String customerInfoId) throws Exception{
//		return ((PfpAdActionDAO)dao).findAdActionView(actionName, startDate, endDate, adType, page, pageSize, customerInfoId);
//	}
	
//	public String getCount(String actionName, String startDate, String endDate, String adType, int page, int pageSize, String customerInfoId) throws Exception{
//		return ((PfpAdActionDAO)dao).getCount(actionName, startDate, endDate, adType, page, pageSize, customerInfoId);
//	}

//	public void insertPfpAdAction(PfpAdAction pfpAdAction) throws Exception {
//		((PfpAdActionDAO)dao).insertPfpAdAction(pfpAdAction);
//	}

//	public void updatePfpAdAction(PfpAdAction pfpAdAction) throws Exception {
//		((PfpAdActionDAO)dao).updatePfpAdAction(pfpAdAction);
//	}

//	public void updatePfpAdActionMax(String adActionSeq, String pfpAdActionMax) throws Exception {
//		((PfpAdActionDAO)dao).updatePfpAdActionMax(adActionSeq, pfpAdActionMax);
//	}

//	public List<PfpAdActionViewVO> getAllAdActionView(String customerInfoId, String keyword, EnumAdType enumAdType, Date startDate, Date endDate) throws Exception{
//		//return ((PfpAdActionDAO)dao).getAdActionPvclk(customerInfoId, keyword, enumAdType.getType(), startDate, endDate, -1, -1);
//		//return ((PfpAdActionDAO)dao).getAdActionReport(customerInfoId, keyword, enumAdType.getType(), startDate, endDate, -1, -1);
//		return getAdActionView(customerInfoId, keyword, enumAdType, startDate, endDate, -1, -1);
//	}
}
