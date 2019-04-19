package com.pchome.akbpfp.struts2.ajax.ad;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.opensymphony.oscache.util.StringUtil;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.ad.IPfbxWebsiteCategoryService;
import com.pchome.akbpfp.db.service.ad.PfpAdActionService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.utils.EnumStatus;

public class AdActionAjax extends BaseCookieAction{
	
	private static final long serialVersionUID = 1L;
	private PfpCustomerInfoService pfpCustomerInfoService;
	private PfpAdActionService pfpAdActionService;
	private IPfbxWebsiteCategoryService pfbxWebsiteCategoryService;
	
	private String customerInfoId;
	private String adActionMax;
	private InputStream msg;
	private String adActionName;
	private String adActionSeq;
	
	 //網址回傳json格式字串
    private String result;
	
	public String closeAdActionAjax() throws Exception{
		
		// 帳戶下所有廣告
		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(customerInfoId);
		for(PfpAdAction adAction:pfpCustomerInfo.getPfpAdActions()){
			// 暫停所有廣告
			adAction.setAdActionStatus(EnumStatus.Pause.getStatusId());
			pfpAdActionService.savePfpAdAction(adAction);
		}
		
		return SUCCESS;
	}

	public String chkAdActionName() throws Exception{
		Boolean noexist = true;
		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		boolean AdActionExist = pfpAdActionService.chkAdActionNameByCustomerInfoId(adActionName, adActionSeq, pfpCustomerInfo.getCustomerInfoId());
		if(AdActionExist)	noexist = false;
		msg = new ByteArrayInputStream(noexist.toString().getBytes());
		return SUCCESS;
	}

	public String chkAdActionMax() throws Exception{
		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		System.out.println("Remain = " + pfpCustomerInfo.getRemain());
		System.out.println("adActionMax = " + adActionMax);
		float remain = pfpCustomerInfo.getRemain();
		if(!StringUtil.isEmpty(adActionMax) && Float.parseFloat(adActionMax) > remain) {
			String err = "over:" + (int)remain;
			msg = new ByteArrayInputStream(err.getBytes("UTF-8"));
			System.out.println("msg = " + msg);
			return ERROR;
		}
		return SUCCESS;
	}

	public String chkRemain() throws Exception{
		Boolean noOver = true;
		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id());
		System.out.println("Remain = " + pfpCustomerInfo.getRemain());
		System.out.println("adActionMax = " + adActionMax);
		float remain = pfpCustomerInfo.getRemain();
		if(!StringUtil.isEmpty(adActionMax) && Float.parseFloat(adActionMax) > remain) {
			noOver = false;
		}
		msg = new ByteArrayInputStream(noOver.toString().getBytes());
		return SUCCESS;
	}

	public String getAdAction() throws Exception{
		String adAction = "";
		System.out.println("adActionSeq = " + adActionSeq);
		if(StringUtils.isNotBlank(adActionSeq)) {
			PfpAdAction pfpAdAction = pfpAdActionService.getPfpAdActionBySeq(adActionSeq);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			adAction = pfpAdAction.getAdActionName() + "," +
					   pfpAdAction.getAdActionDesc() + "," +
					   sdf.format(pfpAdAction.getAdActionStartDate()) + ",";
			if(!sdf.format(pfpAdAction.getAdActionEndDate()).equals("3000-12-31")) {
				adAction += sdf.format(pfpAdAction.getAdActionEndDate()) + ",";
			} else {
				adAction += "永久" + ",";
			}
			adAction += (int)pfpAdAction.getAdActionMax() + ",";
		}
		msg = new ByteArrayInputStream(adAction.getBytes());
		return SUCCESS;
	}

	public String getPfbxWebsiteCategoryAll(){
		
		Map<String, List<Map<String, String>>> pfbxWebsiteCategoryListMap = new LinkedHashMap<String, List<Map<String, String>>>();
		pfbxWebsiteCategoryListMap = pfbxWebsiteCategoryService.getAllOrderByCode();
		
		JSONObject jsonObjectJacky = new JSONObject(pfbxWebsiteCategoryListMap);
        result = jsonObjectJacky.toString();
		
		return SUCCESS;
	}
	
	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setPfpAdActionService(PfpAdActionService pfpAdActionService) {
		this.pfpAdActionService = pfpAdActionService;
	}

	public void setPfbxWebsiteCategoryService(IPfbxWebsiteCategoryService pfbxWebsiteCategoryService) {
		this.pfbxWebsiteCategoryService = pfbxWebsiteCategoryService;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	public InputStream getMsg() {
		return msg;
	}

	public void setAdActionMax(String adActionMax) {
		this.adActionMax = adActionMax;
	}

	public void setAdActionName(String adActionName) {
		this.adActionName = adActionName;
	}

	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
