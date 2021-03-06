package com.pchome.akbpfp.struts2;

import com.pchome.enumerate.ssl.EnumSSLAction;

public class BaseSSLAction extends BaseCookieAction{
	
	private final static String LOCAL_SCHEME = "http://";
	private final static String LOCAL_PORT = "8080";
	private final static String LOCAL_DONAME = "alex.pchome.com.tw";
	protected String redirectUrl;
	protected String resultType;
	
	/**
	 * 固定幾個頁面需走 https
	 * 1. EnumSSLAction
	 * 2. 如果 local 設定不判斷
	 */
	public void checkRedirectSSLUrl() {
		
		//log.info(" local: "+request.getScheme());
		//log.info(" local: "+LOCAL_DONAME);
		//log.info(" local: "+request.getServerName());
		
//		if(this.isSSLUrl()){
//			// 需要轉成 https
//			if(!request.getScheme().equals("https")){
//				if(request.getServerName().indexOf(LOCAL_DONAME) > -1){
//					redirectUrl = LOCAL_SCHEME+request.getServerName()+":"+LOCAL_PORT+request.getRequestURI();
//				}else{
//					redirectUrl = "https://"+request.getServerName()+request.getRequestURI();
//					resultType = "redirectHTTPS";
//				}
//			}
//		}else{
//			// 不需要轉成 https
			if(!request.getScheme().equals("http")){
				if(request.getServerName().indexOf(LOCAL_DONAME) > -1){
					redirectUrl = LOCAL_SCHEME+request.getServerName()+":"+LOCAL_PORT+request.getRequestURI();
				}else{
					redirectUrl = "http://"+request.getServerName()+request.getRequestURI();
					resultType = "redirectHTTPS";
				}				
			}
//		}
 
		//log.info(" redirectUrl = "+redirectUrl);
	}
	
//	private boolean isSSLUrl() {
//		
//		boolean result = false;
//		for(EnumSSLAction ssl:EnumSSLAction.values()){
//			if(request.getRequestURI().indexOf(ssl.getAction()) > -1){
//				result = true;
//				break;
//			}
//		}
//		
//		return result;
//	}

	public String getRedirectUrl() {
		return redirectUrl;
	}
}
