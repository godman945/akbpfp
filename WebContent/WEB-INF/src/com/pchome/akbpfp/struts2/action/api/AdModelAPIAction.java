package com.pchome.akbpfp.struts2.action.api;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.rmi.api.IAPIProvider;

public class AdModelAPIAction extends BaseCookieAction{
	
	private IAPIProvider admAPI;
	
	private String tproNo;
	private String adNo;
	private InputStream returnAdHtml;			// 回傳廣告
	
	/**
	 * 吐Html廣告
	 */
	public String adModelAction() throws Exception{
		
		//log.info(" tproNo = "+tproNo+"  , adNo = "+adNo);

		String adHtml = admAPI.getAdContent(tproNo, adNo);

		//log.info(" adHtml = "+adHtml);
		
		returnAdHtml = new ByteArrayInputStream(adHtml.getBytes("UTF-8"));	 
		
		return SUCCESS;
	}
	
	public void setAdmAPI(IAPIProvider admAPI) {
		this.admAPI = admAPI;
	}

	public InputStream getReturnAdHtml() {
		return returnAdHtml;
	}

	public void setTproNo(String tproNo) {
		this.tproNo = tproNo;
	}

	public void setAdNo(String adNo) {
		this.adNo = adNo;
	}

}
