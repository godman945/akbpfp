package com.pchome.akbpfp.interceptor;

import java.util.EnumMap;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.enumerate.cookie.EnumCookiePfpKey;
import com.pchome.enumerate.privilege.EnumPrivilegeModel;
import com.pchome.soft.depot.utils.CookieStringToMap;
import com.pchome.soft.depot.utils.CookieUtil;
import com.pchome.soft.depot.utils.EncodeUtil;

public class AdPrivilegeInterceptor extends AbstractInterceptor{

	protected final Logger log = LogManager.getRootLogger();
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception{
		
		String result = "summary";
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String userData = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_AKBPFP_USER.getValue(),EnumCookieConstants.COOKIE_USING_CODE.getValue());
		
		EnumMap<EnumCookiePfpKey, String> cookieMap = CookieStringToMap.getInstance().transformEnumMap(userData);
		
		int privilegeId = Integer.parseInt(cookieMap.get(EnumCookiePfpKey.PFP_USER_PRIVILEGE_ID));
		
		// 只有 root or adm  or ad 權限才能進入
		if(EnumPrivilegeModel.ROOT_USER.getPrivilegeId() == privilegeId ||
				EnumPrivilegeModel.ADM_USER.getPrivilegeId() == privilegeId ||
				EnumPrivilegeModel.AD_USER.getPrivilegeId() == privilegeId){
			result = invocation.invoke();
		}
		
		return result;
	}
}
