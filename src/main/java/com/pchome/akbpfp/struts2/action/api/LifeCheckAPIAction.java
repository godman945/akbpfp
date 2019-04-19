package com.pchome.akbpfp.struts2.action.api;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.rmi.api.IAPIProvider;

public class LifeCheckAPIAction extends BaseCookieAction{

	private IAPIProvider admAPI;
	private InputStream returnLifeCheck;		// 回傳lifeCheck
	
	public String lifeCheck() throws Exception{
		returnLifeCheck = new ByteArrayInputStream(admAPI.lifeCheck().getBytes("UTF-8"));
		return SUCCESS;
	}
	
	public void setAdmAPI(IAPIProvider admAPI) {
		this.admAPI = admAPI;
	}
	
	public InputStream getReturnLifeCheck() {
		return returnLifeCheck;
	}
}
