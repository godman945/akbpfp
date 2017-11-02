package com.pchome.akbpfp.struts2.action.api;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.rmi.api.IAPIProvider;

public class AdModelAPIAction extends BaseCookieAction{
	
	private IAPIProvider admAPI;
	
	private String tproNo;
	private String adNo;
	private InputStream returnAdHtml;			// 回傳廣告
	
	private String adPreviewVideoURL;
	private String adPreviewVideoBgImg="";
	private String realUrl = "";
	
	/**
	 * 吐Html廣告
	 */
	public String adModelAction() throws Exception{
		
	    //log.info(" tproNo = "+tproNo+"  , adNo = "+adNo);
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    Date date = new Date();
	    String adHtml = admAPI.getAdContent(tproNo, adNo);
	    adHtml = adHtml.replaceAll("jpg", "jpg?time="+sdf.format(date));
	    log.info(adHtml);
	    returnAdHtml = new ByteArrayInputStream(adHtml.getBytes("UTF-8"));
	    return SUCCESS;
	}
	
	
	/**
	 * 吐影音廣告
	 */
	public String adModelVideoAction() throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    Date date = new Date();
	    String adHtml = admAPI.getAdVideoContent(adPreviewVideoURL,adPreviewVideoBgImg,realUrl);
	    log.info(adHtml);
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

	public void setAdPreviewVideoURL(String adPreviewVideoURL) {
		this.adPreviewVideoURL = adPreviewVideoURL;
	}


	public void setAdPreviewVideoBgImg(String adPreviewVideoBgImg) {
		this.adPreviewVideoBgImg = adPreviewVideoBgImg;
	}


	public void setRealUrl(String realUrl) {
		this.realUrl = realUrl;
	}

}
