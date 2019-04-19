package com.pchome.akbpfp.struts2.action.ad;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.ad.PfpAdGroupService;
import com.pchome.akbpfp.db.service.ad.PfpAdKeywordService;
import com.pchome.akbpfp.db.service.ad.PfpAdService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdDevice;
import com.pchome.enumerate.ad.EnumAdPriceType;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.utils.EnumStatus;


public class AdFinishAction extends BaseCookieAction{

	private static final long serialVersionUID = 1L;

	private String message = "";

	private String adActionName;
	private String adActionStartDate;
	private String selAdActionEndDate;
	private String adActionMax;

	private String adActionSeq;
	private String adGroupSeq;
	private String adGroupName;
	private String adGroupSearchPriceType;
	private String adGroupSearchPrice;
	private String adGroupChannelPrice;
	private String adGroupPriceTypeDesc;
	private List<PfpAd> pfpAdList;
	private List<PfpAdKeyword> pfpAdKeywordList;

	private PfpCustomerInfoService pfpCustomerInfoService;
	private PfpAdGroupService pfpAdGroupService;
	private PfpAdService pfpAdService;
	private PfpAdKeywordService pfpAdKeywordService;

	private String adCreateDate;
	private String adWorkDatee;
	SimpleDateFormat sdf = new  SimpleDateFormat("yyyy/MM/dd");
	
	private String adType;
	private String adTypeName;
	private String adDeviceName;
	private String adOperatingRule;
	
	public String AdAddFinish() throws Exception {
	    getadDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		adOperatingRule = pfpAdGroup.getPfpAdAction().getAdOperatingRule();
		adActionName  = pfpAdGroup.getPfpAdAction().getAdActionName();

		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		String customerInfoId = pfpCustomerInfo.getCustomerInfoId();
		String adCustomerInfoId = pfpAdGroup.getPfpAdAction().getPfpCustomerInfo().getCustomerInfoId();
		if(!customerInfoId.equals(adCustomerInfoId)) {
			return "notOwner";
		}

		adActionSeq = pfpAdGroup.getPfpAdAction().getAdActionSeq();
		adActionMax = NumberFormat.getIntegerInstance().format(pfpAdGroup.getPfpAdAction().getAdActionMax());
		adActionStartDate = sdf.format(pfpAdGroup.getPfpAdAction().getAdActionStartDate());
		adGroupName  = pfpAdGroup.getAdGroupName();
		adGroupSearchPrice = Integer.toString((int)pfpAdGroup.getAdGroupSearchPrice());
		adGroupChannelPrice = Integer.toString((int)pfpAdGroup.getAdGroupChannelPrice());
		
		adType = pfpAdGroup.getPfpAdAction().getAdType().toString();
		Integer adDevice = pfpAdGroup.getPfpAdAction().getAdDevice();
		adTypeName = "";
		
		for(EnumAdType enumAdType: EnumAdType.values()){
			if(Integer.parseInt(adType) == enumAdType.getType()){
				adTypeName = enumAdType.getTypeName();
			}
		}
		
		for(EnumAdDevice enumAdDevice:EnumAdDevice.values()){
			if(adDevice == enumAdDevice.getDevType()){
				adDeviceName = enumAdDevice.getDevTypeName();
			}
		}
		
		adGroupSearchPriceType = "";
		switch(pfpAdGroup.getAdGroupSearchPriceType()) {
			case 1:
				adGroupSearchPriceType = "系統建議出價";
				break;
			case 2:
				adGroupSearchPriceType = "自行設定出價NT$" + adGroupSearchPrice;
				break;
			default:
				adGroupSearchPriceType = "系統建議出價";
				break;
		}

		if(sdf.format(pfpAdGroup.getPfpAdAction().getAdActionEndDate()).equals("3000-12-31")) {
			selAdActionEndDate = "永久";
		} else {
			selAdActionEndDate = sdf.format(pfpAdGroup.getPfpAdAction().getAdActionEndDate());
		}

		pfpAdList = pfpAdService.getPfpAds(null, adGroupSeq, null, null, null, null);
		List<PfpAd> pfpAdNotCloseList = new ArrayList<PfpAd>();
		for(PfpAd pfpAd : pfpAdList){
			if(EnumStatus.Close.getStatusId() != pfpAd.getAdStatus()){
				pfpAdNotCloseList.add(pfpAd);
			}
		}
		pfpAdList = pfpAdNotCloseList;
		
		pfpAdKeywordList = pfpAdKeywordService.findAdKeywords(null, adGroupSeq, null, null, null, "10");
		return SUCCESS;
	}
	
	/*
	 * 影音廣告上稿完成
	 * */
	public String adAddVideoFinish() throws Exception{
	    getadDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		adActionName  = pfpAdGroup.getPfpAdAction().getAdActionName();
	
		//收費方式
		for (EnumAdPriceType enumAdPriceType : EnumAdPriceType.values()) {
			if(enumAdPriceType.getDbTypeName().equals(pfpAdGroup.getAdGroupPriceType())){
				adGroupPriceTypeDesc = enumAdPriceType.getTypeName();
				break;
			}
		}
		adOperatingRule = pfpAdGroup.getPfpAdAction().getAdOperatingRule();
		
		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		String customerInfoId = pfpCustomerInfo.getCustomerInfoId();
		String adCustomerInfoId = pfpAdGroup.getPfpAdAction().getPfpCustomerInfo().getCustomerInfoId();
		if(!customerInfoId.equals(adCustomerInfoId)) {
			return "notOwner";
		}
	
		adActionSeq = pfpAdGroup.getPfpAdAction().getAdActionSeq();
		adActionMax = NumberFormat.getIntegerInstance().format(pfpAdGroup.getPfpAdAction().getAdActionMax());
		adActionStartDate = sdf.format(pfpAdGroup.getPfpAdAction().getAdActionStartDate());
		adGroupName  = pfpAdGroup.getAdGroupName();
		
		adGroupSearchPrice = Integer.toString((int)pfpAdGroup.getAdGroupSearchPrice());
		//CPC為多媒體廣告其他為影音廣告
		if(pfpAdGroup.getAdGroupPriceType().equals(EnumAdPriceType.AD_PRICE_CPC.getDbTypeName())){
			adGroupChannelPrice = Integer.toString((int)pfpAdGroup.getAdGroupChannelPrice());
		}else if(pfpAdGroup.getAdGroupPriceType().equals(EnumAdPriceType.AD_PRICE_CPM.getDbTypeName())){
			adGroupChannelPrice = String.valueOf((int)pfpAdGroup.getAdGroupChannelPrice());
		}else if(pfpAdGroup.getAdGroupPriceType().equals(EnumAdPriceType.AD_PRICE_CPV.getDbTypeName())){
			adGroupChannelPrice = String.valueOf(pfpAdGroup.getAdGroupChannelPrice());
		}
		
		adType = pfpAdGroup.getPfpAdAction().getAdType().toString();
		Integer adDevice = pfpAdGroup.getPfpAdAction().getAdDevice();
		adTypeName = "";
		
		for(EnumAdType enumAdType: EnumAdType.values()){
			if(Integer.parseInt(adType) == enumAdType.getType()){
				adTypeName = enumAdType.getTypeName();
			}
		}
		
		for(EnumAdDevice enumAdDevice:EnumAdDevice.values()){
			if(adDevice == enumAdDevice.getDevType()){
				adDeviceName = enumAdDevice.getDevTypeName();
			}
		}
		
		adGroupSearchPriceType = "";
		switch(pfpAdGroup.getAdGroupSearchPriceType()) {
			case 1:
				adGroupSearchPriceType = "系統建議出價";
				break;
			case 2:
				adGroupSearchPriceType = "自行設定出價NT$" + adGroupSearchPrice;
				break;
			default:
				adGroupSearchPriceType = "系統建議出價";
				break;
		}
	
		if(sdf.format(pfpAdGroup.getPfpAdAction().getAdActionEndDate()).equals("3000-12-31")) {
			selAdActionEndDate = "永久";
		} else {
			selAdActionEndDate = sdf.format(pfpAdGroup.getPfpAdAction().getAdActionEndDate());
		}
	
		pfpAdList = pfpAdService.getPfpAds(null, adGroupSeq, null, null, null, null);
		List<PfpAd> pfpAdNotCloseList = new ArrayList<PfpAd>();
		for(PfpAd pfpAd : pfpAdList){
			if(EnumStatus.Close.getStatusId() != pfpAd.getAdStatus()){
				pfpAdNotCloseList.add(pfpAd);
			}
		}
		pfpAdList = pfpAdNotCloseList;
		
		pfpAdKeywordList = pfpAdKeywordService.findAdKeywords(null, adGroupSeq, null, null, null, "10");
		
		return SUCCESS;
	}
	
	
	
	
	
	public void getadDate(){
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.DAY_OF_MONTH, +3);    
	    SimpleDateFormat formatDate = new SimpleDateFormat("MM/dd");  
	    this.adCreateDate = formatDate.format(Calendar.getInstance().getTime());
	    this.adWorkDatee = formatDate.format(calendar.getTime());
	}
	

	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setPfpAdGroupService(PfpAdGroupService pfpAdGroupService) {
		this.pfpAdGroupService = pfpAdGroupService;
	}

	public void setPfpAdService(PfpAdService pfpAdService) {
		this.pfpAdService = pfpAdService;
	}


	public void setPfpAdKeywordService(PfpAdKeywordService pfpAdKeywordService) {
		this.pfpAdKeywordService = pfpAdKeywordService;
	}

	public String getAdActionName() {
		return adActionName;
	}

	public String getAdActionStartDate() {
		return adActionStartDate;
	}

	public String getSelAdActionEndDate() {
		return selAdActionEndDate;
	}

	public String getAdActionMax() {
		return adActionMax;
	}

	public String getAdActionSeq() {
		return adActionSeq;
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

	public String getAdGroupSearchPriceType() {
		return adGroupSearchPriceType;
	}

	public String getAdGroupSearchPrice() {
		return adGroupSearchPrice;
	}

	public String getAdGroupChannelPrice() {
		return adGroupChannelPrice;
	}

	public List<PfpAd> getPfpAdList() {
		return pfpAdList;
	}

	public List<PfpAdKeyword> getPfpAdKeywordList() {
		return pfpAdKeywordList;
	}

	public String getMessage() {
		return message;
	}

	public String getAdCreateDate() {
	    return adCreateDate;
	}

	public void setAdCreateDate(String adCreateDate) {
	    this.adCreateDate = adCreateDate;
	}

	public String getAdWorkDatee() {
	    return adWorkDatee;
	}

	public void setAdWorkDatee(String adWorkDatee) {
	    this.adWorkDatee = adWorkDatee;
	}

	public String getAdType() {
		return adType;
	}

	public String getAdTypeName() {
		return adTypeName;
	}

	public String getAdDeviceName() {
		return adDeviceName;
	}

	public void setAdDeviceName(String adDeviceName) {
		this.adDeviceName = adDeviceName;
	}

	public String getAdGroupPriceTypeDesc() {
		return adGroupPriceTypeDesc;
	}

	public void setAdGroupPriceTypeDesc(String adGroupPriceTypeDesc) {
		this.adGroupPriceTypeDesc = adGroupPriceTypeDesc;
	}

	public String getAdOperatingRule() {
		return adOperatingRule;
	}

	public void setAdOperatingRule(String adOperatingRule) {
		this.adOperatingRule = adOperatingRule;
	}
}
