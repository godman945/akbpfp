package com.pchome.akbpfp.struts2.ajax.account;


import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.pojo.AdmFreeGift;
import com.pchome.akbpfp.db.service.freeAction.IAdmFreeGiftService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.freeAction.EnumGiftSnoPayment;
import com.pchome.enumerate.freeAction.EnumGiftSnoStyle;
import com.pchome.enumerate.freeAction.EnumGiftSnoUsed;
import com.pchome.soft.util.DateValueUtil;

public class AccountAjax extends BaseCookieAction{

	private static final long serialVersionUID = -5195311542239203862L;
	private IAdmFreeGiftService admFreeGiftService;
	
	private int urlState;
	private String giftSno;			// 序號
	private String giftStatus;		// 序號狀態
	private float giftMoney;		// 序號金額
	private String giftActionName;	// 禮金活動名稱
	private float addMoney = 0;		// 儲值金額
	private float addTax = 0;		// 儲值稅額
	private float addTotal = 0;		// 總金額
	private String payment;			// 需要付款註記

	/**
	 * 序號檢查
	 */
	public String checkGiftSnoAjax(){
		
		AdmFreeGift admFreeGift = admFreeGiftService.findAdmFreeGiftSno(giftSno);

		if(admFreeGift != null){
			
			String today = DateValueUtil.getInstance().getDateValue(DateValueUtil.TODAY, DateValueUtil.DBPATH);
			String actionEndDate = DateValueUtil.getInstance().dateToString(admFreeGift.getAdmFreeAction().getActionEndDate());
			String actionStartDate = DateValueUtil.getInstance().dateToString(admFreeGift.getAdmFreeAction().getActionStartDate());
			
			long expired = DateValueUtil.getInstance().getDateDiffDay(today, actionEndDate);

			long early = DateValueUtil.getInstance().getDateDiffDay(actionStartDate, today);
			
			if(admFreeGift.getGiftSnoStatus().equals(EnumGiftSnoUsed.YES.getStatus())){
			/*if(admFreeGift.getCustomerInfoId() != null ||
					admFreeGift.getOpenDate() != null ||
					admFreeGift.getGiftSnoStatus().equals(EnumGiftSnoUsed.YES.getStatus())){*/
				// 序號已經被使用
				giftStatus = "used";
			}else if(expired < 0){
				// 序號超過使用期限
				giftStatus = "expired";
				
			}else if(early <= 0){
				log.info(">>> early ");
			} else if(!StringUtils.equals(EnumGiftSnoStyle.GIFT.getStatus(), admFreeGift.getAdmFreeAction().getGiftStyle())){
				giftStatus = "errStyle";
			}else{
				AdmFreeGift useHistory = admFreeGiftService.findUsedHistory(admFreeGift.getAdmFreeAction().getActionId(), super.getCustomer_info_id());
				
				if(useHistory != null){
					giftStatus = "notUsed";
				} else {
					giftStatus = "unused";
					giftMoney = admFreeGift.getAdmFreeAction().getGiftMoney();
					giftActionName = admFreeGift.getAdmFreeAction().getActionName();
					payment = admFreeGift.getAdmFreeAction().getPayment();
					
					if(StringUtils.equals(admFreeGift.getAdmFreeAction().getPayment(), EnumGiftSnoPayment.YES.getStatus())){
						addMoney = admFreeGift.getAdmFreeAction().getGiftCondition();
						addTax = (float) Math.floor(addMoney*0.05 + 0.5);
						addTotal = 	addMoney + 	addTax;
					}
				}

			}
		
		}else{
			// 找不到這組序號
			log.info(" sno is empty!!");
		}
		
		return SUCCESS;
	}

	public int getUrlState() {
		return urlState;
	}

	public void setGiftSno(String giftSno) {
		this.giftSno = giftSno.trim();
	}

	public String getGiftStatus() {
		return giftStatus;
	}

	public void setAdmFreeGiftService(IAdmFreeGiftService admFreeGiftService) {
		this.admFreeGiftService = admFreeGiftService;
	}

	public float getGiftMoney() {
		return giftMoney;
	}

	public String getGiftActionName() {
		return giftActionName;
	}

	public float getAddMoney() {
		return addMoney;
	}

	public float getAddTax() {
		return addTax;
	}

	public float getAddTotal() {
		return addTotal;
	}

	public String getPayment() {
		return payment;
	}
	
	
}
