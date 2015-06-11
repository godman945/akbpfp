package com.pchome.akbpfp.struts2.action.api;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

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
		
//		System.out.println(returnAdHtml);
//		String StringFromInputStream = IOUtils.toString(returnAdHtml, "UTF-8");
//		System.out.println("FFFFFFFFFFFFFFFFFFFFF");
//		log.info(StringFromInputStream);
		
		
		
		 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(returnAdHtml, "UTF-8"));
		
		 String line = bufferedReader.readLine();
		 while(line != null){
		     log.info(line);
		 }

		
		
		
		
		
		
		
		
		
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
