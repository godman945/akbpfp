package com.pchome.akbpfp.struts2.ajax.apply;


import com.pchome.akbpfp.db.pojo.AdmFreeGift;
import com.pchome.akbpfp.db.service.freeAction.IAdmFreeGiftService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.freeAction.EnumGiftSnoUsed;
import com.pchome.soft.depot.utils.HttpUtil;
import com.pchome.soft.util.DateValueUtil;

public class ApplyAjax extends BaseCookieAction{

	private static final long serialVersionUID = -5195311542239203862L;
	private IAdmFreeGiftService admFreeGiftService;
	
	private String url;
	private int urlState;
	private String giftSno;			// 序號
	private String giftStatus;		// 序號狀態
	private float giftMoney;		// 序號金額
	
	/**
	 * 確認 url 是否存在
	 */
	public String checkUrlState() throws Exception{
		
		if(url.length() >= 7){
			String urlHead = url.substring(0,7);
			if("http://".equals(urlHead)){
				url = url.substring(7);
			}
		}
		
		if(url.length() >= 8){
			String urlHead = url.substring(0,8);
			if("https://".equals(urlHead)){
				url = url.substring(8);
			}
		}
		
		url = "http://"+url;
		
		urlState = HttpUtil.getInstance().getStatusCode(url);
		
		return SUCCESS;
	}

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
			
			if(admFreeGift.getCustomerInfoId() != null ||
					admFreeGift.getOpenDate() != null ||
					admFreeGift.getGiftSnoStatus().equals(EnumGiftSnoUsed.YES.getStatus())){
				// 序號已經被使用
				giftStatus = "used";
			}else if(expired < 0){
				// 序號超過使用期限
				giftStatus = "expired";
				
			}else if(early <= 0){
				log.info(">>> early ");
			}else{
				giftStatus = "unused";
				giftMoney = admFreeGift.getAdmFreeAction().getGiftMoney();
			}
		
		}else{
			// 找不到這組序號
			log.info(" sno is empty!!");
		}
		
		return SUCCESS;
	}

	public void setUrl(String url) {
		this.url = url.trim();
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
	
}
