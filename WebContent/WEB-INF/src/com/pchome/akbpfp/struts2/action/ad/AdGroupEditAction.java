package com.pchome.akbpfp.struts2.action.ad;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.api.SyspriceOperaterAPI;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.accesslog.AdmAccesslogService;
import com.pchome.akbpfp.db.service.ad.PfpAdGroupService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.rmi.accesslog.EnumAccesslogAction;


public class AdGroupEditAction extends BaseCookieAction{

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
	private String backPage;
	
	private String sysPriceAdPoolSeq;       //廣告建議價取得 pool from api prop 注入

	private PfpCustomerInfoService pfpCustomerInfoService;
	private PfpAdGroupService pfpAdGroupService;
	private SyspriceOperaterAPI syspriceOperaterAPI;
	private AdmAccesslogService admAccesslogService;

	public String adGroupEdit() throws Exception {
		log.info("AdGroupEdit => adActionSeq = " + adActionSeq);
		log.info("Referer = " + request.getHeader("Referer"));
		String referer = request.getHeader("Referer");

		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());

		PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		adGroupName = pfpAdGroup.getAdGroupName();
		defSearchPrice = Integer.toString((int)pfpAdGroup.getAdGroupChannelPrice());
		adGroupSearchPrice = Integer.toString((int)pfpAdGroup.getAdGroupSearchPrice());
		sysChannelPrice = Integer.toString((int)syspriceOperaterAPI.getAdSuggestPrice(sysPriceAdPoolSeq));
		adGroupChannelPrice = Integer.toString((int)pfpAdGroup.getAdGroupChannelPrice());
		adGroupSearchPriceType = Integer.toString((int)pfpAdGroup.getAdGroupSearchPriceType());
		adActionSeq = pfpAdGroup.getPfpAdAction().getAdActionSeq();
		adActionName  = pfpAdGroup.getPfpAdAction().getAdActionName();
		adActionMax = (int)pfpAdGroup.getPfpAdAction().getAdActionMax();
		String customerInfoId = pfpCustomerInfo.getCustomerInfoId();
		String adCustomerInfoId = pfpAdGroup.getPfpAdAction().getPfpCustomerInfo().getCustomerInfoId();
		if(!customerInfoId.equals(adCustomerInfoId)) {
			return "notOwner";
		}

		backPage = (referer != null && StringUtils.isNotEmpty(referer))?referer:"adGroupView.html?adActionSeq=" + adActionSeq;
		AdAsideRate = String.format("%,3.2f", syspriceOperaterAPI.getAdAsideRate(Float.parseFloat(adGroupChannelPrice)));

		return SUCCESS;
	}

	public String doAdGroupEdit() throws Exception {
		log.info("doAdGroupEdit");
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
			message = "請選擇搜尋廣告出價";
			return INPUT;
		} else {
			//一開始沒有關鍵字，所以建議出價跟預設出價相同，等廣告建完有關鍵字，再去呼叫api更新
			if(adGroupSearchPriceType.equals("1")) {
				adGroupSearchPrice = defSearchPrice;
			} else if(adGroupSearchPriceType.equals("2")) {
				log.info("adGroupSearchPrice = " + adGroupSearchPrice);
			} else {
				message = "請選擇搜尋廣告出價";
				return INPUT;
			}
		}

		//float iAdGroupChannelPrice = 0;
		if (StringUtils.isEmpty(adGroupChannelPrice)) {
			message = "請輸入聯播廣告出價！";
			return INPUT;
		} else {
			adGroupChannelPrice = adGroupChannelPrice.trim();
			if (adGroupChannelPrice.length() > 6) {
				message = "聯播廣告出價不可超過 6 位數！";
				return INPUT;
			}
		}

		PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);

		pfpAdGroup.setAdGroupName(adGroupName);
		pfpAdGroup.setAdGroupSearchPriceType(Integer.parseInt(adGroupSearchPriceType));
		// 搜尋廣告出價，有修改金額才修正並紀錄
		if(pfpAdGroup.getAdGroupSearchPrice() != Float.parseFloat(adGroupSearchPrice)) {
			String accesslogAction_Money = EnumAccesslogAction.AD_MONEY_MODIFY.getMessage();
			String accesslogMessage_Money = accesslogAction_Money + ":" + adGroupSeq + ", 活動名稱:" + pfpAdGroup.getPfpAdAction().getAdActionName() + ", 分類名稱:" + adGroupName + ", 搜尋廣告出價異動:" + (int)pfpAdGroup.getAdGroupSearchPrice() + "=>" + adGroupSearchPrice;
			admAccesslogService.recordAdLog(EnumAccesslogAction.AD_MONEY_MODIFY, accesslogMessage_Money, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			pfpAdGroup.setAdGroupSearchPrice(Float.parseFloat(adGroupSearchPrice));
		}
		// 聯播廣告出價，有修改金額才修正並紀錄
		float adGroupPrice_org = pfpAdGroup.getAdGroupChannelPrice();
		if(adGroupPrice_org != Float.parseFloat(adGroupChannelPrice)) {
			String accesslogAction_Money = EnumAccesslogAction.AD_MONEY_MODIFY.getMessage();
			String accesslogMessage_Money = accesslogAction_Money + ":" + adGroupSeq + ", 活動名稱:" + pfpAdGroup.getPfpAdAction().getAdActionName() + ", 分類名稱:" + adGroupName + ", 聯播廣告出價異動:" + (int)adGroupPrice_org + "=>" + adGroupChannelPrice;
			admAccesslogService.recordAdLog(EnumAccesslogAction.AD_MONEY_MODIFY, accesslogMessage_Money, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			pfpAdGroup.setAdGroupChannelPrice(Float.parseFloat(adGroupChannelPrice));
		}
		pfpAdGroup.setAdGroupUpdateTime(new Date());

		// 廣告活動不為開啟的話，就要調整為開啟
		if(pfpAdGroup.getPfpAdAction().getAdActionStatus() != EnumStatus.Open.getStatusId()) {
			String oldStatus = "";
			// 原本的廣告狀態
			for(EnumStatus status:EnumStatus.values()){
				if(status.getStatusId() == pfpAdGroup.getPfpAdAction().getAdActionStatus()){
					oldStatus = status.getStatusDesc();
				}
			}
			// 寫入 LOG
			String accesslogAction_Stauts = EnumAccesslogAction.AD_STATUS_MODIFY.getMessage();
			String accesslogMessage_Status = accesslogAction_Stauts + ":" + adActionSeq + ", 活動名稱:" + pfpAdGroup.getPfpAdAction().getAdActionName() + ", 分類名稱:" + adGroupName + ",廣告活動狀態異動:" + oldStatus + "=>" + EnumStatus.Open.getStatusDesc();
			admAccesslogService.recordAdLog(EnumAccesslogAction.AD_STATUS_MODIFY, accesslogMessage_Status, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());

			//更新廣告活動狀態為已完成(開啟)
			pfpAdGroup.getPfpAdAction().setAdActionStatus(EnumStatus.Open.getStatusId());
			
		}

		Set<PfpAdKeyword> AdKeywords = pfpAdGroup.getPfpAdKeywords();
		for(PfpAdKeyword pfpAdKeyword:AdKeywords){
			//log.info("pfpAdKeyword.getAdKeywordSeq() = " + pfpAdKeyword.getAdKeywordSeq());
			float adKeywordSearchPrice_org = pfpAdKeyword.getAdKeywordSearchPrice();
			if(adKeywordSearchPrice_org != Float.parseFloat(adGroupSearchPrice)) {
				String accesslogAction_Money = EnumAccesslogAction.AD_MONEY_MODIFY.getMessage();
				String accesslogMessage_Money = accesslogAction_Money + ":" + adGroupSeq + ", 活動名稱:" + pfpAdGroup.getPfpAdAction().getAdActionName() + ", 分類名稱:" + adGroupName + ", 關鍵字：" + pfpAdKeyword.getAdKeyword() + ", 搜尋廣告出價異動:" + adKeywordSearchPrice_org + "=>" + Float.parseFloat(adGroupSearchPrice);
				admAccesslogService.recordAdLog(EnumAccesslogAction.AD_MONEY_MODIFY, accesslogMessage_Money, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
				pfpAdKeyword.setAdKeywordSearchPrice(Float.parseFloat(adGroupSearchPrice));
			}

			float adKeywordChannelPrice_org = pfpAdKeyword.getAdKeywordChannelPrice();
			if(adKeywordChannelPrice_org != Float.parseFloat(adGroupChannelPrice)) {
				String accesslogAction_Money = EnumAccesslogAction.AD_MONEY_MODIFY.getMessage();
				String accesslogMessage_Money = accesslogAction_Money + ":" + adGroupSeq + ", 活動名稱:" + pfpAdGroup.getPfpAdAction().getAdActionName() + ", 分類名稱:" + adGroupName + ", 關鍵字：" + pfpAdKeyword.getAdKeyword() + ", 聯播廣告出價異動:" + adKeywordChannelPrice_org + "=>" + Float.parseFloat(adGroupChannelPrice);
				admAccesslogService.recordAdLog(EnumAccesslogAction.AD_MONEY_MODIFY, accesslogMessage_Money, super.getId_pchome(), super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
				pfpAdKeyword.setAdKeywordChannelPrice(Float.parseFloat(adGroupChannelPrice));
			}
		}
		pfpAdGroupService.savePfpAdGroup(pfpAdGroup);
		
		//系統價更新
		syspriceOperaterAPI.addAdSysprice(sysPriceAdPoolSeq, Float.valueOf(adGroupChannelPrice));

		return SUCCESS;
	}

	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setPfpAdGroupService(PfpAdGroupService pfpAdGroupService) {
		this.pfpAdGroupService = pfpAdGroupService;
	}

	public void setSyspriceOperaterAPI(SyspriceOperaterAPI syspriceOperaterAPI) {
		this.syspriceOperaterAPI = syspriceOperaterAPI;
	}

	public AdmAccesslogService getAdmAccesslogService() {
		return admAccesslogService;
	}

	public void setAdmAccesslogService(AdmAccesslogService admAccesslogService) {
		this.admAccesslogService = admAccesslogService;
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

	public void setBackPage(String backPage) {
		this.backPage = backPage;
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
