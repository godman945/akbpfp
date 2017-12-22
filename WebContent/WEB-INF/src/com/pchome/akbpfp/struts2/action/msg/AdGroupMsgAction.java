package com.pchome.akbpfp.struts2.action.msg;

import com.pchome.akbpfp.api.SyspriceOperaterAPI;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.service.ad.PfpAdGroupService;
import com.pchome.akbpfp.db.service.sysprice.IPfpAdSyspriceService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdSearchPriceType;

public class AdGroupMsgAction extends BaseCookieAction{
	
	private static final long serialVersionUID = 1L;
	private PfpAdGroupService pfpAdGroupService;
	private SyspriceOperaterAPI syspriceOperaterAPI;
	private IPfpAdSyspriceService pfpAdSyspriceService;
	private String adPoolSeq;
	private int adType;
	private String adGroupSeq;	
	private PfpAdGroup adGroup;
	private float sysprice;
	private float adAsideRate; 
	private float userprice;
	private String searchPriceTypeName;
	private String adGroupPriceType;
	
	public String execute() throws Exception{
		
		return SUCCESS;
	}
	
	public String modifyAdGroupChannelPriceAction() throws Exception{
		adGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		if(adGroup.getAdGroupPriceType().equals("CPC")){
			//sysprice = adSysprice.getSysprice();
			//建議價
			sysprice = syspriceOperaterAPI.getAdSuggestPrice(adPoolSeq);
			//撥出率
			adAsideRate = syspriceOperaterAPI.getAdAsideRate(userprice);
			adGroupPriceType = adGroup.getAdGroupPriceType();
			adType = adGroup.getPfpAdAction().getAdType();
		}else if(adGroup.getAdGroupPriceType().equals("CPM")){
			userprice = userprice - 58;
			adAsideRate = syspriceOperaterAPI.getAdAsideRate(userprice);
			adGroupPriceType = adGroup.getAdGroupPriceType();
			adType = adGroup.getPfpAdAction().getAdType();
			System.out.println("CPM >>>>>>>>>>userprice:" + userprice);
		}else if(adGroup.getAdGroupPriceType().equals("CPV")){
			userprice = (userprice * 10) + 10;
			adAsideRate = syspriceOperaterAPI.getAdAsideRate(userprice);
			adGroupPriceType = adGroup.getAdGroupPriceType();
			adType = adGroup.getPfpAdAction().getAdType();
			System.out.println("CPV >>>>>>>>>>userprice:" + userprice);
		}
		return SUCCESS;
	}
	
	public String adGroupSuggestPriceAction()  throws Exception{
		
		adAsideRate = syspriceOperaterAPI.getAdAsideRate(userprice);
		
		return SUCCESS;
	}
	
	public String modifyAdGroupSearchPriceAction()  throws Exception{
		
		adGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
		
		for(EnumAdSearchPriceType type:EnumAdSearchPriceType.values()){
			
			if(type.getTypeId() == adGroup.getAdGroupSearchPriceType()){
				searchPriceTypeName = type.getDesc();
			}
		}
		
		return SUCCESS;
	}

	public void setPfpAdGroupService(PfpAdGroupService pfpAdGroupService) {
		this.pfpAdGroupService = pfpAdGroupService;
	}
	
	public void setSyspriceOperaterAPI(SyspriceOperaterAPI syspriceOperaterAPI) {
		this.syspriceOperaterAPI = syspriceOperaterAPI;
	}
	
	public void setPfpAdSyspriceService(IPfpAdSyspriceService pfpAdSyspriceService) {
		this.pfpAdSyspriceService = pfpAdSyspriceService;
	}

	public void setAdPoolSeq(String adPoolSeq) {
		this.adPoolSeq = adPoolSeq;
	}
	
	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}

	public PfpAdGroup getAdGroup() {
		return adGroup;
	}
	
	public float getSysprice() {
		return sysprice;
	}
	
	public float getAdAsideRate() {
		return adAsideRate;
	}
	
	public void setUserprice(float userprice) {
		this.userprice = userprice;
	}

	public float getUserprice() {
		return userprice;
	}

	public String getSearchPriceTypeName() {
		return searchPriceTypeName;
	}

	public String getAdGroupPriceType() {
		return adGroupPriceType;
	}

	public void setAdGroupPriceType(String adGroupPriceType) {
		this.adGroupPriceType = adGroupPriceType;
	}

	public int getAdType() {
		return adType;
	}

	public void setAdType(int adType) {
		this.adType = adType;
	}
	
}
