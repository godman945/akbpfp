package com.pchome.akbpfp.db.service.ad;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pchome.akbpfp.db.dao.ad.IPfpAdActionDAO;
import com.pchome.akbpfp.db.dao.ad.PfpAdActionDAO;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.ad.PfpAdActionViewVO;
import com.pchome.enumerate.ad.EnumAdDevice;
import com.pchome.enumerate.ad.EnumAdStyleType;
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

	public List<PfpAdActionViewVO> getAdActionView(String customerInfoId, String keyword, String adType, Date startDate, Date endDate, int page, int pageSize) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 先攔掉 SQL Injection 攻擊語法
		customerInfoId = CommonUtils.filterSqlInjection(customerInfoId);
		keyword = CommonUtils.filterSqlInjection(keyword);
		// 查詢廣告活動
		List<PfpAdAction> pfpAdActionList = ((PfpAdActionDAO)dao).getPfpAdActionForView(customerInfoId, keyword, adType, page, pageSize);
		
		List<PfpAdActionViewVO> adActionViewVOList = null;
		if(pfpAdActionList.size() > 0){
			adActionViewVOList = new ArrayList<PfpAdActionViewVO>();
		}
		
		for (PfpAdAction pfpAdAction : pfpAdActionList) {
			// 逐筆讀出本頁的廣告活動序號
			List<String> adActionSeqs = new ArrayList<String>();
			adActionSeqs.add(pfpAdAction.getAdActionSeq());
			
			// 依照讀出本頁的廣告活動序號，查詢關鍵字成效
			HashMap<String, Object> pfpAdActionReports = ((PfpAdActionDAO)dao).getAdActionReportByAdActionsList(customerInfoId, adActionSeqs, adType, startDate, endDate);
			
			PfpAdActionViewVO adActionViewVO = new PfpAdActionViewVO();
			adActionViewVO.setAdActionSeq(pfpAdAction.getAdActionSeq());
			adActionViewVO.setAdActionName(pfpAdAction.getAdActionName());
			adActionViewVO.setAdActionMax(pfpAdAction.getAdActionMax());
			adActionViewVO.setAdStartDate(sdf.format(pfpAdAction.getAdActionStartDate()));
			adActionViewVO.setAdEndDate(sdf.format(pfpAdAction.getAdActionEndDate()));
			
			// 判斷廣告走期
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
					float thousandsCost = 0;
					adActionViewVO.setAdPv(pv);
					adActionViewVO.setAdClk(clk);
					adActionViewVO.setAdClkPrice(clkPrice);
					adActionViewVO.setInvalidClk(invalidClk);

					float clkRate = 0;
					float clkPriceAvg = 0;
					
					if(clk > 0 && pv > 0){
						clkRate = (float)clk / (float)pv * 100;
					}

					if(clkPrice > 0 && clk > 0){
						clkPriceAvg = clkPrice / (float)clk;
					}

					//計算千次曝光費用
					if(clkPrice > 0){
						thousandsCost = (clkPrice / ((float)pv) * 1000);
					}
					adActionViewVO.setAdClkRate(clkRate);
					adActionViewVO.setAdClkPriceAvg(clkPriceAvg);
					adActionViewVO.setThousandsCost(thousandsCost);
				}
			}
			
			// 廣告樣式
			for (EnumAdStyleType enumAdStyleType : EnumAdStyleType.values()) {
				if(enumAdStyleType.getTypeName().equals(pfpAdAction.getAdOperatingRule())){
					adActionViewVO.setAdOperatingRule(enumAdStyleType.getType());
					break;
				}
			}
			// 廣告類型
			for(EnumAdType type:EnumAdType.values()){
				int pfpAdType = pfpAdAction.getAdType();					
				if(type.getType() == pfpAdType){
					adActionViewVO.setAdType(type.getTypeName());
					break;
				}					
			}
			// 廣告播放裝置
			for(EnumAdDevice device:EnumAdDevice.values()){
				int adDevice = pfpAdAction.getAdDevice();
				if(device.getDevType() == adDevice){
					adActionViewVO.setAdDevice(device.getDevTypeName());
					break;
				}
			}
			// 廣告狀態
			for(EnumStatus status:EnumStatus.values()){
				int adStatus = pfpAdAction.getAdActionStatus();
				if(status.getStatusId() == adStatus){
					adActionViewVO.setAdActionStatus(adStatus);
					adActionViewVO.setAdActionStatusDesc(status.getStatusDesc());
					break;
				}
			}
			adActionViewVOList.add(adActionViewVO);
		}
		
		
//		if(pfpAdActionList.size() > 0) {
//			// 逐筆讀出本頁的廣告活動序號
//			List<String> adActionSeqs = new ArrayList<String>();
//			for(PfpAdAction pfpAdAction:pfpAdActionList) {
//				adActionSeqs.add(pfpAdAction.getAdActionSeq());
//			}
//
//			// 依照讀出本頁的廣告活動序號，查詢關鍵字成效
//			HashMap<String, Object> pfpAdActionReports = ((PfpAdActionDAO)dao).getAdActionReportByAdActionsList(customerInfoId, adActionSeqs, adType, startDate, endDate);
//			
//			for(PfpAdAction pfpAdAction:pfpAdActionList) {
//				if(adActionViewVOList == null){
//					adActionViewVOList = new ArrayList<PfpAdActionViewVO>();
//				}
//	
//				PfpAdActionViewVO adActionViewVO = new PfpAdActionViewVO();
//				adActionViewVO.setAdActionSeq(pfpAdAction.getAdActionSeq());
//				adActionViewVO.setAdActionName(pfpAdAction.getAdActionName());
//				
//				for (EnumAdStyleType enumAdStyleType : EnumAdStyleType.values()) {
//					if(enumAdStyleType.getTypeName().equals(pfpAdAction.getAdOperatingRule())){
//						adActionViewVO.setAdOperatingRule(enumAdStyleType.getType());
//						break;
//					}
//				}
//	
//				// 廣告類型
//				for(EnumAdType type:EnumAdType.values()){
//					int pfpAdType = pfpAdAction.getAdType();					
//					if(type.getType() == pfpAdType){
//						adActionViewVO.setAdType(type.getTypeName());
//					}					
//				}
//				
//				// 廣告播放裝置
//				for(EnumAdDevice device:EnumAdDevice.values()){
//					int adDevice = pfpAdAction.getAdDevice();
//					if(device.getDevType() == adDevice){
//						adActionViewVO.setAdDevice(device.getDevTypeName());
//					}
//				}
//				
//				// 廣告狀態
//				for(EnumStatus status:EnumStatus.values()){
//					int adStatus = pfpAdAction.getAdActionStatus();
//					if(status.getStatusId() == adStatus){
//						adActionViewVO.setAdActionStatus(adStatus);
//						adActionViewVO.setAdActionStatusDesc(status.getStatusDesc());
//					}
//				}
//				
//				adActionViewVO.setAdActionMax(pfpAdAction.getAdActionMax());								
//				
//				// 判斷廣告走期
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				adActionViewVO.setAdStartDate(sdf.format(pfpAdAction.getAdActionStartDate()));
//				adActionViewVO.setAdEndDate(sdf.format(pfpAdAction.getAdActionEndDate()));
//				
//				Date adStartDate = DateValueUtil.getInstance().stringToDate(adActionViewVO.getAdStartDate());
//				Date adEndDate = DateValueUtil.getInstance().stringToDate(adActionViewVO.getAdEndDate());
//	
//				if(adActionViewVO.getAdActionStatus() == EnumStatus.Open.getStatusId()){
//					
//					Date today = DateValueUtil.getInstance().getNowDateTime();
//					Date yesterday = DateValueUtil.getInstance().getDateForStartDateAddDay(DateValueUtil.getInstance().dateToString(today), -1);
//					
//					if(today.after(adStartDate) && yesterday.before(adEndDate)){ // 因為結束時間為  00:00:00 所以取昨天比較
//						adActionViewVO.setAdActionStatusDesc(EnumStatus.Broadcast.getStatusDesc());
//					}
//					else if(today.before(adStartDate)){
//						adActionViewVO.setAdActionStatusDesc(EnumStatus.Waitbroadcast.getStatusDesc());
//					}
//					else if(today.after(adEndDate)){ 
//						adActionViewVO.setAdActionStatusDesc(EnumStatus.End.getStatusDesc());
//					}
//				}
//	
//				// 依照關鍵字廣告序號，讀取、設定廣告成效，沒有廣告成效的，就不用設定了
//				if(pfpAdActionReports.size() > 0 && pfpAdActionReports.get(pfpAdAction.getAdActionSeq()) != null) {
//					Object[] obj = (Object[])pfpAdActionReports.get(pfpAdAction.getAdActionSeq());
//					if(obj != null) {
//						// 求點閱率				
//						int pv = Integer.parseInt(obj[1].toString());
//						int clk = Integer.parseInt(obj[2].toString());
//						float clkPrice = Float.parseFloat(obj[3].toString());
//						int invalidClk = Integer.parseInt(obj[4].toString());
//						float thousandsCost = 0;
//						adActionViewVO.setAdPv(pv);
//						adActionViewVO.setAdClk(clk);
//						adActionViewVO.setAdClkPrice(clkPrice);
//						adActionViewVO.setInvalidClk(invalidClk);
//	
//						float clkRate = 0;
//						float clkPriceAvg = 0;
//						
//						if(clk > 0 || pv > 0){
//							clkRate = (float)clk / (float)pv * 100;
//						}
//	
//						if(clkPrice > 0 || clk > 0){
//							clkPriceAvg = clkPrice / (float)clk;
//						}
//	
//						//計算千次曝光費用
//						if(clkPrice > 0){
//							thousandsCost = clkPrice / ((float)pv * 1000);
//						}
//						
//						adActionViewVO.setAdClkRate(clkRate);
//						adActionViewVO.setAdClkPriceAvg(clkPriceAvg);
//						adActionViewVO.setThousandsCost(thousandsCost);
//					}
//				}
//				adActionViewVOList.add(adActionViewVO);
//			}
//		}
		
		return adActionViewVOList;	
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
	public long getPfpAdActionCount(String customerInfoId, String keyword, String adType) throws Exception {
		return ((PfpAdActionDAO)dao).getPfpAdActionCount(customerInfoId, keyword, adType, -1, -1);
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
	public long getPfpAdActionCount(String customerInfoId, String keyword, String adType, int page, int pageSize) throws Exception {
		return ((PfpAdActionDAO)dao).getPfpAdActionCount(customerInfoId, keyword, adType, page, pageSize);
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

	public List<PfpAdAction> getAdActionByCustomerInfoIdAndMediaAd(String customerInfoId) throws Exception{
		return ((IPfpAdActionDAO) dao).getAdActionByCustomerInfoIdAndMediaAd(customerInfoId);
	}
}
