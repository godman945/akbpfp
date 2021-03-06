package com.pchome.akbpfp.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class RedirectNotifyInterceptor extends AbstractInterceptor{

	protected static final Logger log = LogManager.getRootLogger();
	
	private final String checkServerName = "show.pchome.com.tw";
	
	/**
	 * 導公告頁面 Interceptor
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception{

		String result = null;
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		log.info(" server name: "+request.getServerName());
		
		// 導公告頁
		if(request.getServerName().equals(checkServerName)){
			result = "sysNotify";			
		}else{			
			result = invocation.invoke();	
		}
		
		log.info(" result: "+result);

		
		return result;
	}
	
}
