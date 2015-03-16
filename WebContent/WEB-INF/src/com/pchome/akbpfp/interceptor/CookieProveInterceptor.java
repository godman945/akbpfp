package com.pchome.akbpfp.interceptor;



import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.soft.depot.utils.CookieFunction;
import com.pchome.soft.depot.utils.CookieUtil;

public class CookieProveInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = -6002551278284848164L;	
	
	private CookieFunction cookieUtils;
	
	protected static final Log log = LogFactory.getLog(CookieProveInterceptor.class);

	/**
	 * 檢查 id_pchome 是否被更改過
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception{

		String result = "index";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String id_pchome = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_MEMBER_ID_PCHOME.getValue(), 
							EnumCookieConstants.COOKIE_USING_CODE.getValue());
		String dna_pchome = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_MEMBER_DNA_PCHOME.getValue(), 
							EnumCookieConstants.COOKIE_USING_CODE.getValue());
		
		//log.info(" id_pchome: "+id_pchome);
		
		if(StringUtils.isNotEmpty(id_pchome) && StringUtils.isNotEmpty(dna_pchome)){
		
			String decode_dna_pchome = cookieUtils.Simple_Decode(dna_pchome);
			//log.info(" dna_pchome: "+decode_dna_pchome);
			
			if(decode_dna_pchome.equals(id_pchome)){
			
				result = invocation.invoke();
			}
		
		}
		
		//log.info(" result: "+result);		
		
		return result;
	}


	public void setCookieUtils(CookieFunction cookieUtils) {
		this.cookieUtils = cookieUtils;
	}

}
