package com.pchome.akbpfp.api;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.soft.depot.utils.CookieFunction;
import com.pchome.soft.depot.utils.CookieUtil;
import com.pchome.soft.depot.utils.EncodeUtil;

public class CookieContentAPI {

	protected Log log = LogFactory.getLog(getClass());
	
	/**
	 *  解析 billing_customer_info 內容
	 * 1. [0] pfpPcId 金流交易明細查詢 memberId
	 */
	public String[] getBillingCustomerInfoCookieContent(){
		
		String[] content = null;
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String cookieContent = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_BILLING_CUSTOMERINFO.getValue(), 
				EnumCookieConstants.COOKIE_USING_CODE.getValue());
		
		if(cookieContent != null){
			
			String decodeContent = EncodeUtil.getInstance().decryptAES(cookieContent, EnumCookieConstants.COOKIE_BILLING_CUSTOMERINFO_SECRET_KEY.getValue());
			
			if(decodeContent != null){
				content = decodeContent.split(",");
			}
		}
		
		return content;
	}

	
}
