package com.pchome.akbpfp.struts2;

import java.util.EnumMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.enumerate.cookie.EnumCookiePfpKey;
import com.pchome.soft.depot.utils.CookieStringToMap;
import com.pchome.soft.depot.utils.CookieUtil;

public class BaseAction extends ActionSupport implements ServletContextAware, ServletRequestAware, ServletResponseAware, SessionAware {

	private static final long serialVersionUID = 5529688509741898738L;
	protected Log log = LogFactory.getLog(getClass().getName());	
	protected ServletContext servletContext;
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected Map<String, Object> session;

	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;
	}
	
	public HttpServletResponse getResponse() {
		return response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		// TODO Auto-generated method stub
		this.servletContext = servletContext;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		// TODO Auto-generated method stub
		this.session = session;
	}
	
	public Map<String, Object> getSession() {
		return session;
	}

	/**
	 * 返回Pfd網址
	 */
	public String getBack2PFD() {

		String pfpCookie = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_AKBPFP_USER.getValue(), 
				EnumCookieConstants.COOKIE_USING_CODE.getValue());

		String pfdCookie = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_AKBPFD_USER.getValue(), 
				EnumCookieConstants.COOKIE_USING_CODE.getValue());

		//log.info(" pfpCookie "+pfpCookie);
		
		EnumMap<EnumCookiePfpKey, String> cookieMap = CookieStringToMap.getInstance().transformEnumMap(pfpCookie);
		
		//log.info(" length "+userItem.split(EnumCookieConstants.COOKIE_SEPARATOR.getValue()).length);
		
		if (StringUtils.isNotBlank(pfpCookie) && StringUtils.isNotBlank(pfdCookie) && 
				cookieMap.get(EnumCookiePfpKey.PFD_RETURN_ACTION) != null) {
			//log.info(" ServerName: " + request.getServerName());
			//log.info(" ContextPath: " + request.getContextPath());
			return "http://" + request.getServerName() + "/pfd/"+cookieMap.get(EnumCookiePfpKey.PFD_RETURN_ACTION);
		} else {
			return "";
		}
	}
}
