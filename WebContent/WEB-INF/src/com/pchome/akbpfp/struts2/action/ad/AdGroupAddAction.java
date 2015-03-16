package com.pchome.akbpfp.struts2.action.ad;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.akbpfp.api.SyspriceOperaterAPI;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.ad.PfpAdActionService;
import com.pchome.akbpfp.db.service.ad.PfpAdGroupService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.soft.depot.utils.CookieUtil;
import com.pchome.soft.depot.utils.EncodeUtil;

public class AdGroupAddAction extends BaseCookieAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message = "";
	private String adActionSeq;
	private String adActionName;
	private int adActionMax;	//每日預算

	private String adGroupSeq;
	private String adGroupName;
	private String defSearchPrice = "3";	//搜尋廣告系統預設出價
	private String adGroupSearchPrice;		//搜尋廣告出價
	private String adGroupSearchPriceType;	//搜尋廣告出價類別。1:系統建議、2:系統預設
	private String sysChannelPrice = "3";	//聯播廣告系統建議出價
	private String adGroupChannelPrice;		//聯播廣告出價
	private String AdAsideRate;				//播放率
	private String backPage;				// 取消的返回頁面
	
	public void setAdActionMax(int adActionMax) {
		this.adActionMax = adActionMax;
	}

	public void setBackPage(String backPage) {
		this.backPage = backPage;
	}

	private String sysPriceAdPoolSeq;       //廣告建議價取得 pool from api prop 注入

	private PfpCustomerInfoService pfpCustomerInfoService;
	private ISequenceService sequenceService;
	private PfpAdActionService pfpAdActionService;
	private PfpAdGroupService pfpAdGroupService;
	
	private SyspriceOperaterAPI syspriceOperaterAPI;

	public String adGroupAdd() throws Exception {
		System.out.println("AdGroupAdd => adActionSeq = " + adActionSeq + "; adGroupSeq = " + adGroupSeq);
		String adCustomerInfoId = "";
		String referer = request.getHeader("Referer");
		
		/*
		if(referer.indexOf("adActionAdd") > 0 || referer.indexOf("adActionView") > 0 || referer.indexOf("adGroupView") > 0) {
			String encodeCookie = EncodeUtil.getInstance().encryptAES(referer, EnumCookieConstants.COOKIE_SECRET_KEY.getValue());
			
			CookieUtil.writeCookie(response, "preGroup", encodeCookie, EnumCookieConstants.COOKIE_DOMAIN.getValue(),
									EnumCookieConstants.COOKIE_APPLY_AGE, EnumCookieConstants.COOKIE_USING_CODE.getValue());
			
			System.out.println("write cookie data="+referer);
		}
		*/
		

		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		String tmpSeq = "";		// 回  adActionAdd.html 讀取資料用的 adActionSeq
		adGroupSearchPriceType = "2";

		// 由 adAddAction 取消回來時，會帶 adGroupSeq 的參數，但是不會帶 adActionSeq
		if(StringUtils.isNotBlank(adGroupSeq)) {
			PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
			adGroupName = pfpAdGroup.getAdGroupName();
			adGroupSearchPrice = Integer.toString((int)pfpAdGroup.getAdGroupSearchPrice());;
			adGroupChannelPrice = Integer.toString((int)pfpAdGroup.getAdGroupChannelPrice());
			adGroupSearchPriceType = Integer.toString((int)pfpAdGroup.getAdGroupSearchPriceType());
			adActionSeq = pfpAdGroup.getPfpAdAction().getAdActionSeq();
			adActionName  = pfpAdGroup.getPfpAdAction().getAdActionName();
			adActionMax = (int)pfpAdGroup.getPfpAdAction().getAdActionMax();
			tmpSeq = pfpAdGroup.getPfpAdAction().getAdActionSeq();
			adCustomerInfoId = pfpAdGroup.getPfpAdAction().getPfpCustomerInfo().getCustomerInfoId();
			defSearchPrice = adGroupSearchPrice;
			sysChannelPrice = Integer.toString((int)syspriceOperaterAPI.getAdSuggestPrice(sysPriceAdPoolSeq));
		}
		// 由 adActionAdd 下一步時，會帶 adActionSeq 的參數，但是不會帶 adGroupSeq
		else if(StringUtils.isNotBlank(adActionSeq)) {
			adGroupSeq = "";
			tmpSeq = adActionSeq;
			PfpAdAction pfpAdAction = pfpAdActionService.getPfpAdActionBySeq(adActionSeq);
			adActionName  = pfpAdAction.getAdActionName();
			adActionMax = (int)pfpAdAction.getAdActionMax();
			adCustomerInfoId = pfpAdAction.getPfpCustomerInfo().getCustomerInfoId();
	
			adGroupName = chkAdGroupName(adActionName);
			adGroupSearchPrice = defSearchPrice;		//一開始沒有關鍵字，所以預設為3元，等廣告建完有關鍵字，再去呼叫api更新
			sysChannelPrice = Integer.toString((int)syspriceOperaterAPI.getAdSuggestPrice(sysPriceAdPoolSeq));
			adGroupChannelPrice = "3";
		}
		String customerInfoId = pfpCustomerInfo.getCustomerInfoId();
		if(!customerInfoId.equals(adCustomerInfoId)) {
			return "notOwner";
		}

		// 讀取cookie
		
		if(referer.indexOf("adActionAdd") > 0 ) {
			backPage="adActionAdd.html?adActionSeq="+tmpSeq;

			String encodeCookie = EncodeUtil.getInstance().encryptAES(backPage, EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
			
			CookieUtil.writeCookie(response, "preGroup", encodeCookie, EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue(),
									EnumCookieConstants.COOKIE_ONE_HOUR_AGE, EnumCookieConstants.COOKIE_USING_CODE.getValue());
			
			log.info("write cookie data="+backPage);
		
		}else if(referer.indexOf("adActionView") > 0 || referer.indexOf("adGroupView") > 0 ) {
		
			backPage = referer;
			
            String encodeCookie = EncodeUtil.getInstance().encryptAES(backPage, EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
			
			CookieUtil.writeCookie(response, "preGroup", encodeCookie, EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue(),
									EnumCookieConstants.COOKIE_ONE_HOUR_AGE, EnumCookieConstants.COOKIE_USING_CODE.getValue());
			
			log.info("write cookie data="+backPage);
					
			//backPage="adActionAdd.html?adActionSeq="+tmpSeq;
		} else {
			//由 add 取消時
			String cookieData = CookieUtil.getCookie(request, "preGroup", EnumCookieConstants.COOKIE_USING_CODE.getValue());
			log.info("cookieData = " + cookieData);
			String decodePreGroup = EncodeUtil.getInstance().decryptAES(cookieData, EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
			backPage = decodePreGroup;
			log.info("backPage from cookie  = " + backPage);
		}
		
		//log.info("backPage = " + backPage);

		AdAsideRate = String.format("%,3.2f", syspriceOperaterAPI.getAdAsideRate(Float.parseFloat(adGroupChannelPrice)));
		return SUCCESS;
	}

	private String chkAdGroupName(String adGroupName) {
		String tmpAdGroupName = adGroupName;
		try {
			if(pfpAdGroupService.chkAdGroupNameByAdActionSeq(tmpAdGroupName, null, adActionSeq)) {
				List<PfpAdGroup> pfpAdGroups = pfpAdGroupService.getPfpAdGroups(null, adActionSeq, null, null, null, null);
				tmpAdGroupName = tmpAdGroupName + "-" + (pfpAdGroups.size() + 1);
			}
		} catch(Exception ex) {
			log.info("Exception(AdGroupAddAction.chkAdGroupName):" + ex);
		}
		return tmpAdGroupName;
	}
	
	@Transactional
	public String doAdGroupAdd() throws Exception {
		log.info("doAdGroupAdd");
		if(super.getCustomer_info_id() == null) {
			message = "請登入帳號！";
			return INPUT;
		}
		
		if (StringUtils.isEmpty(adGroupName)) {
			message = "請輸入分類名稱！";
			return INPUT;
		} else {
			adGroupName = adGroupName.trim();
			if (adGroupName.length() > 20) {
				message = "分類名稱不可超過 20 字！";
				return INPUT;
			}

			if(pfpAdGroupService.chkAdGroupNameByAdActionSeq(adGroupName, adGroupSeq, adActionSeq)) {
				message = "分類名稱已存在";
				return INPUT;
			}
		}

		if (StringUtils.isEmpty(adGroupSearchPriceType)) {
			message = "請選擇找東西廣告出價";
			return INPUT;
		} else {
			//一開始沒有關鍵字，所以建議出價跟預設出價相同，等廣告建完有關鍵字，再去呼叫api更新
			if(adGroupSearchPriceType.equals("1")) {
				adGroupSearchPrice = defSearchPrice;
			} else if(adGroupSearchPriceType.equals("2")) {
				log.info("adGroupSearchPrice = " + adGroupSearchPrice);
			} else {
				message = "請選擇找東西廣告出價";
				return INPUT;
			}
		}
		
		if (StringUtils.isEmpty(adGroupChannelPrice)) {
			message = "請輸入PChome頻道廣告出價！";
			return INPUT;
		} else {
			adGroupChannelPrice = adGroupChannelPrice.trim();
			if (adGroupChannelPrice.length() > 6) {
				message = "PChome頻道廣告出價不可超過 6 位數！";
				return INPUT;
			}
		}
		
		PfpAdGroup pfpAdGroup = new PfpAdGroup();
		if(StringUtils.isNotBlank(adGroupSeq)) {
			pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		} else {
			adGroupSeq = sequenceService.getId(EnumSequenceTableName.PFP_AD_GROUP, "_");
			//log.info("new adGroupSeq = " + adGroupSeq);
			PfpAdAction pfpAdAction = pfpAdActionService.getPfpAdActionBySeq(adActionSeq);
			pfpAdGroup.setAdGroupSeq(adGroupSeq);
			pfpAdGroup.setPfpAdAction(pfpAdAction);
			pfpAdGroup.setAdGroupCreateTime(new Date());
		}
		pfpAdGroup.setAdGroupName(adGroupName);
		pfpAdGroup.setAdGroupSearchPriceType(Integer.parseInt(adGroupSearchPriceType));
		pfpAdGroup.setAdGroupSearchPrice(Float.parseFloat(adGroupSearchPrice));
		pfpAdGroup.setAdGroupChannelPrice(Float.parseFloat(adGroupChannelPrice));
		pfpAdGroup.setAdGroupStatus(EnumStatus.UnDone.getStatusId());	// 新增廣告分類時，status 設定為未完成
		pfpAdGroup.setAdGroupUpdateTime(new Date());
		
		//更新廣告活動狀態為已完成(開啟)
		pfpAdGroup.getPfpAdAction().setAdActionStatus(EnumStatus.Open.getStatusId());
		
		pfpAdGroupService.savePfpAdGroup(pfpAdGroup);

		//系統價更新
		syspriceOperaterAPI.addAdSysprice(sysPriceAdPoolSeq, Float.valueOf(adGroupChannelPrice));

		return SUCCESS;
	}

	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setPfpAdActionService(PfpAdActionService pfpAdActionService) {
		this.pfpAdActionService = pfpAdActionService;
	}

	public void setPfpAdGroupService(PfpAdGroupService pfpAdGroupService) {
		this.pfpAdGroupService = pfpAdGroupService;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setSyspriceOperaterAPI(SyspriceOperaterAPI syspriceOperaterAPI) {
		this.syspriceOperaterAPI = syspriceOperaterAPI;
	}

	public String getAdActionSeq() {
		return adActionSeq;
	}

	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
	}

	public String getAdActionName() {
		return adActionName;
	}

	public void setAdActionName(String adActionName) {
		this.adActionName = adActionName;
	}

	public int getAdActionMax() {
		return adActionMax;
	}

	public String getAdGroupSeq() {
		return adGroupSeq;
	}

	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}

	public String getAdGroupName() {
		return adGroupName;
	}

	public void setAdGroupName(String adGroupName) {
		this.adGroupName = adGroupName;
	}

	public String getAdGroupSearchPriceType() {
		return adGroupSearchPriceType;
	}

	public void setAdGroupSearchPriceType(String adGroupSearchPriceType) {
		this.adGroupSearchPriceType = adGroupSearchPriceType;
	}

	public String getAdGroupSearchPrice() {
		return adGroupSearchPrice;
	}

	public void setAdGroupSearchPrice(String adGroupSearchPrice) {
		this.adGroupSearchPrice = adGroupSearchPrice;
	}

	public String getAdGroupChannelPrice() {
		return adGroupChannelPrice;
	}

	public void setAdGroupChannelPrice(String adGroupChannelPrice) {
		this.adGroupChannelPrice = adGroupChannelPrice;
	}

	public String getDefSearchPrice() {
		return defSearchPrice;
	}

	public String getSysChannelPrice() {
		return sysChannelPrice;
	}

	public String getAdAsideRate() {
		return AdAsideRate;
	}

	public String getBackPage() {
		return backPage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setSysPriceAdPoolSeq(String sysPriceAdPoolSeq) {
		this.sysPriceAdPoolSeq = sysPriceAdPoolSeq;
	}

}
