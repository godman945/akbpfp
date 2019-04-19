package com.pchome.akbpfp.struts2.ajax.ad;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.oscache.util.StringUtil;
import com.pchome.akbpfp.api.SyspriceOperaterAPI;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.ad.PfpAdGroupService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdSearchPriceType;

public class AdGroupAjax extends BaseCookieAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5682482589340048870L;
	private PfpAdGroupService pfpAdGroupService;
	private SyspriceOperaterAPI syspriceOperaterAPI;
	
	private InputStream msg;
	private String adGroupName;
	private String adActionSeq;
	private String adActionMax;			//播放率
	private String adGroupSeq;

	public String chkAdGroupName() throws Exception{
		System.out.println("adGroupSeq = " + adGroupSeq);
		Boolean noexist = true;
		boolean AdGroupExist = pfpAdGroupService.chkAdGroupNameByAdActionSeq(adGroupName, adGroupSeq, adActionSeq);
		if(AdGroupExist)	noexist = false;
		msg = new ByteArrayInputStream(noexist.toString().getBytes());
		return SUCCESS;
	}

	public String getAdAsideRate() throws Exception{
		System.out.println("adActionMax = " + adActionMax);
		float AdAsideRate = syspriceOperaterAPI.getAdAsideRate(Float.parseFloat(adActionMax));
		System.out.println("AdAsideRate = " + AdAsideRate);
		String sAdAsideRate = Float.toString(AdAsideRate);
		msg = new ByteArrayInputStream(sAdAsideRate.getBytes("UTF-8"));
		return SUCCESS;
	}

	public String getAdGroup() throws Exception{
		String adGroup = "";
		System.out.println("adGroupSeq = " + adGroupSeq);
		if(StringUtils.isNotBlank(adGroupSeq)) {
			PfpAdGroup pfpAdGroup = pfpAdGroupService.getPfpAdGroupBySeq(adGroupSeq);
			
			adGroup = pfpAdGroup.getAdGroupName() + ",";
			if(pfpAdGroup.getAdGroupSearchPriceType() == 1) {
				adGroup += EnumAdSearchPriceType.SUGGEST_PRICE.getDesc() + ",";
			} else {
				adGroup += EnumAdSearchPriceType.MYSELF_PRICE.getDesc() + ",";
			}
			adGroup += (int)pfpAdGroup.getAdGroupSearchPrice() + ",";
			adGroup += (int)pfpAdGroup.getAdGroupChannelPrice() + ",";
		}
		msg = new ByteArrayInputStream(adGroup.getBytes());
		return SUCCESS;
	}

	public void setPfpAdGroupService(PfpAdGroupService pfpAdGroupService) {
		this.pfpAdGroupService = pfpAdGroupService;
	}

	public void setSyspriceOperaterAPI(SyspriceOperaterAPI syspriceOperaterAPI) {
		this.syspriceOperaterAPI = syspriceOperaterAPI;
	}

	public InputStream getMsg() {
		return msg;
	}

	public void setAdGroupName(String adGroupName) {
		this.adGroupName = adGroupName;
	}

	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
	}

	public void setAdActionMax(String adActionMax) {
		this.adActionMax = adActionMax;
	}

	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}
}
