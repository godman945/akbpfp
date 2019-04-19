package com.pchome.soft.depot.utils;

import java.util.EnumMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.struts2.ServletActionContext;

import com.pchome.akbpfp.api.CookieProccessAPI;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.enumerate.cookie.EnumCookiePfpKey;

public class CookieStringToMap {
	
	private Logger log = LogManager.getRootLogger();
	
	private static final CookieStringToMap cookieStringToMap = new CookieStringToMap();
	
	public static CookieStringToMap getInstance() {
		return cookieStringToMap;
	}
	
	/**
	 * pfp cookie 字串轉換成 enumMap
	 */
	public EnumMap<EnumCookiePfpKey, String> transformEnumMap(String dnCodeCookieData) {
		
		EnumMap<EnumCookiePfpKey, String> cookieMap = null;
		
		if(StringUtils.isNotBlank(dnCodeCookieData)){
			
			cookieMap = new EnumMap<EnumCookiePfpKey, String>(EnumCookiePfpKey.class);
			
			String encodeCookieData = EncodeUtil.getInstance().decryptAES(dnCodeCookieData, EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
			
			//log.info(" encodeCookieData: "+encodeCookieData);
			
			// 判斷是否舊版 cookie 
			if(encodeCookieData.indexOf(EnumCookiePfpKey.PFP_CUSTOMER_INFO_ID.toString()) == -1){

				// 清空 cookie
				HttpServletResponse response = ServletActionContext.getResponse();
				CookieProccessAPI cookieAPI = new CookieProccessAPI();
				cookieAPI.deleteAllCookie(response);
				
			}else{
				
				String[] pairs = encodeCookieData.replace("{", "").replace("}", "").split(",");
				
				for (int i=0;i<pairs.length;i++) {
				    String pair = pairs[i];
				    String[] keyValue = pair.split("=");		
				    
				    for(EnumCookiePfpKey key:EnumCookiePfpKey.values()){
				    	
				    	if(key.toString().equals(keyValue[0].trim())){
				    		cookieMap.put(key, keyValue[1].trim());
				    		break;
				    	}
				    }
				    
				}
			}
			
			//log.info(" cookieMap: "+cookieMap);
		}
		
		//log.info(" cookieMap: "+cookieMap);
		
		return cookieMap;
	}
}
