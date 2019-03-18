package com.pchome.akbpfp.api;

import java.util.EnumMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pchome.akbpfp.db.pojo.PfdUserAdAccountRef;
import com.pchome.akbpfp.db.pojo.PfpUser;
import com.pchome.akbpfp.db.service.pfd.user.IPfdUserAdAccountRefService;
import com.pchome.enumerate.account.EnumPfdAccountPayType;
import com.pchome.enumerate.account.EnumPfpRootUser;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.enumerate.cookie.EnumCookiePfpKey;
import com.pchome.soft.depot.utils.CookieUtil;
import com.pchome.soft.depot.utils.EncodeUtil;

public class CookieProccessAPI {

	protected Log log = LogFactory.getLog(getClass());
	
	private IPfdUserAdAccountRefService pfdUserAdAccountRefService;
	
	/**
	 * PFP 登入 cookie 記錄
	 * 0. userId
	 * 1. userStatus
	 * 2. userPrivilegeId
	 * 3. customerInfoId
	 * 4. customerInfoTitle
	 * 5. pfdCustomerInfoId
	 * 6. pfdUserId
	 * 7. payType 					// 付款方式
	 * 8. rootUser  				// 管理者權限
	 * 9. 金流判斷總帳戶管理Id Cookie
	 */
	public void writerPfpLoginCookie(HttpServletResponse response, PfpUser user, EnumPfpRootUser enumPfpRootUser, String idPchome){
		
		this.deletePfpLoginCookie(response);
		
		String pfpCustomerInfoId = user.getPfpCustomerInfo().getCustomerInfoId();
		
		PfdUserAdAccountRef pfdRef = pfdUserAdAccountRefService.findPfdUserAdAccountRef(pfpCustomerInfoId);
		
		EnumMap<EnumCookiePfpKey, String> content = new EnumMap<EnumCookiePfpKey, String>(EnumCookiePfpKey.class);
		
		content.put(EnumCookiePfpKey.MANAGER, enumPfpRootUser.getPrivilege());		
		content.put(EnumCookiePfpKey.PFP_CUSTOMER_INFO_ID, user.getPfpCustomerInfo().getCustomerInfoId());
		content.put(EnumCookiePfpKey.PFP_CUSTOMER_TITLE, user.getPfpCustomerInfo().getCustomerInfoTitle());
		content.put(EnumCookiePfpKey.PFP_USER_ID, user.getUserId());
		content.put(EnumCookiePfpKey.PFP_USER_PRIVILEGE_ID, String.valueOf(user.getPrivilegeId()));
		content.put(EnumCookiePfpKey.PFP_USER_STATUS, user.getStatus());
		content.put(EnumCookiePfpKey.PFP_REALITY_USER_TITLE, idPchome);
		if(pfdRef != null){
			
			//content.put(EnumCookiePfpKey.PFD_CONTRACT_STATUS, "contract_status");
			content.put(EnumCookiePfpKey.PFD_CUSTOMER_INFO_ID, pfdRef.getPfdCustomerInfo().getCustomerInfoId());
			content.put(EnumCookiePfpKey.PFD_USER_ID, pfdRef.getPfdUser().getUserId());
			content.put(EnumCookiePfpKey.PFP_PAY_TYPE, pfdRef.getPfpPayType());			
		}else{
			
			// 沒有經銷商關聯，付款方式為預付
			content.put(EnumCookiePfpKey.PFP_PAY_TYPE, EnumPfdAccountPayType.ADVANCE.getPayType());
		}
		
		String encodeCookie = EncodeUtil.getInstance().encryptAES(content.toString(), EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
		
		CookieUtil.writeCookie(response, EnumCookieConstants.COOKIE_AKBPFP_USER.getValue(), encodeCookie,	
				EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue(), EnumCookieConstants.COOKIE_MAX_AGE, null);
			
		
		if(enumPfpRootUser.equals(EnumPfpRootUser.PCHOME_MANAGER) && StringUtils.isNotBlank(idPchome)){
			log.info(">>> idPchome: "+idPchome);
			
			// 小天使幫客戶加值 cookie
			this.writeBillingCookie(response, idPchome, user.getPfpCustomerInfo().getMemberId());
			
			// 小天使查詢金流交易 cookie
			this.writerBillingCustomerInfoCookie(response, user.getPfpCustomerInfo().getMemberId());
		}
		
				
	}
	/**
	 * 金流介接需要 cookie 判斷
	 */
	private void writeBillingCookie(HttpServletResponse response, String idPchome, String pfpPcId){
		
		this.deleteBillingCookie(response);
		
		log.info(" idPchome: "+idPchome);
		log.info(" pfpPcId: "+pfpPcId);
		
		// 寫入cookie 
		StringBuffer id = new StringBuffer();
		id.append(idPchome).append(",").append(pfpPcId);
		String encodeId = EncodeUtil.getInstance().encryptAES(id.toString(), EnumCookieConstants.COOKIE_BILLING_SECRET_KEY.getValue());		
		
		CookieUtil.writeCookie(response, EnumCookieConstants.COOKIE_BILLING_BILLING_PCHOME.getValue(), encodeId,	
								EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue(), EnumCookieConstants.COOKIE_ONE_HOUR_AGE, 
								EnumCookieConstants.COOKIE_USING_CODE.getValue());
	}
	/**
	 * 金流判斷總管理Id Cookie
	 */
	private void writerBillingCustomerInfoCookie(HttpServletResponse response, String pcId) {
		
		this.deleteBillingCustomerInfoCookie(response);
		
		// 寫入cookie 
		StringBuffer id = new StringBuffer();
		id.append(pcId);
		String encodeId = EncodeUtil.getInstance().encryptAES(id.toString(), EnumCookieConstants.COOKIE_BILLING_CUSTOMERINFO_SECRET_KEY.getValue());		
		
		CookieUtil.writeCookie(response, EnumCookieConstants.COOKIE_BILLING_CUSTOMERINFO.getValue(), encodeId,	
								EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue(), EnumCookieConstants.COOKIE_MAX_AGE, 
								EnumCookieConstants.COOKIE_USING_CODE.getValue());
		
	}	
	/**
	 * 刪掉 cookie 
	 * 1. 刪掉 PFP 登入 cookie
	 * 2. MEMBER 登入 cookie 
	 * 3. 刪掉 PFP 申請帳戶 cookie
	 * 4. 刪掉 PFP 查詢日期 cookie 
	 * 5. 刪掉金流 cookie
	 * 6. 刪掉 PFD 登入 cookie
	 * 7. 刪掉金流判斷總管理Id Cookie
	 */
	public void deleteAllCookie(HttpServletResponse response) {
		
		this.deletePfpLoginCookie(response);
		
		this.deletMemberLoginCookie(response);
		
		this.deleteApplyPfpCookie(response);
		
		this.deleteChooseDateCookie(response);
		
		this.deleteBillingCookie(response);
		
		this.deletePfdLoginCookie(response);
		
		this.deleteBillingCustomerInfoCookie(response);
	}
	
	/**
	 * 刪掉 PFP 登入 cookie 
	 */
	public void deletePfpLoginCookie (HttpServletResponse response){
		
		Cookie deleteCookie = new Cookie(EnumCookieConstants.COOKIE_AKBPFP_USER.getValue(), null);
		deleteCookie.setDomain(EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue());
		deleteCookie.setMaxAge(0);
		deleteCookie.setPath("/");
		response.addCookie(deleteCookie);
	}
	
	/**
	 * 刪掉 MEMBER 登入 cookie 
	 */
	public void deletMemberLoginCookie(HttpServletResponse response) {
		
		Cookie deleteCookie = new Cookie(EnumCookieConstants.COOKIE_MEMBER_ID_PCHOME.getValue(), null);
		deleteCookie.setDomain(EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue());
		deleteCookie.setMaxAge(0);
		deleteCookie.setPath("/");
		response.addCookie(deleteCookie);
	}
	
	/**
	 * 刪掉 PFP 申請帳戶 cookie 
	 * 1. 功能暫時還保留
	 */
	public void deleteApplyPfpCookie(HttpServletResponse response) {
		
		Cookie deleteCookie = new Cookie(EnumCookieConstants.COOKIE_AKBPFP_APPLY.getValue(), null);
		deleteCookie.setDomain(EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue());
		deleteCookie.setMaxAge(0);
		deleteCookie.setPath("/");
		response.addCookie(deleteCookie);
	}
	
	/**
	 * 刪掉 PFP 查詢日期 cookie 
	 */
	public void deleteChooseDateCookie(HttpServletResponse response) {
		
		Cookie deleteCookie = new Cookie(EnumCookieConstants.COOKIE_CHOOSE_DATE.getValue(), null);
		deleteCookie.setDomain(EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue());
		deleteCookie.setMaxAge(0);
		deleteCookie.setPath("/");
		response.addCookie(deleteCookie);
	}
	
	/**
	 * 刪掉金流 cookie
	 */
	public void deleteBillingCookie(HttpServletResponse response){
		
		// 清掉cookie
		Cookie deleteCookie = new Cookie(EnumCookieConstants.COOKIE_BILLING_BILLING_PCHOME.getValue(), null);
		deleteCookie.setDomain(EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue());
		deleteCookie.setMaxAge(0);
		deleteCookie.setPath("/");
		response.addCookie(deleteCookie);
		
	}
	
	/**
	 * 刪掉 PFD 登入 cookie
	 */
	public void deletePfdLoginCookie(HttpServletResponse response){
		
		// 清掉cookie
		Cookie deleteCookie = new Cookie(EnumCookieConstants.COOKIE_AKBPFD_USER.getValue(), null);
		deleteCookie.setDomain(EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue());
		deleteCookie.setMaxAge(0);
		deleteCookie.setPath("/");
		response.addCookie(deleteCookie);
		
	}

	/**
	 * 刪掉金流判斷總管理Id Cookie
	 */
	public void deleteBillingCustomerInfoCookie(HttpServletResponse response){
		
		// 清掉cookie
		Cookie deleteCookie = new Cookie(EnumCookieConstants.COOKIE_BILLING_CUSTOMERINFO.getValue(), null);
		deleteCookie.setDomain(EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue());
		deleteCookie.setMaxAge(0);
		deleteCookie.setPath("/");
		response.addCookie(deleteCookie);
		
	}
	
	public void testCookie(HttpServletResponse response, PfpUser user, EnumPfpRootUser enumPfpRootUser){
		
		// 寫 cookie 
		EnumMap<EnumCookiePfpKey, String> cookieMap = new EnumMap<EnumCookiePfpKey, String>(EnumCookiePfpKey.class);
		
		cookieMap.put(EnumCookiePfpKey.MANAGER, "PM");
		cookieMap.put(EnumCookiePfpKey.PFD_CUSTOMER_INFO_ID, "AC00001");
		cookieMap.put(EnumCookiePfpKey.PFD_USER_ID, "US00001");
		cookieMap.put(EnumCookiePfpKey.PFP_CUSTOMER_INFO_ID, "PAC0001");
		cookieMap.put(EnumCookiePfpKey.PFP_CUSTOMER_TITLE, "好事多");
		
		log.info(" map toString: "+cookieMap.toString());
		
		String encodeCookie = EncodeUtil.getInstance().encryptAES(cookieMap.toString(), EnumCookieConstants.COOKIE_PFP_SECRET_KEY.getValue());
		
		CookieUtil.writeCookie(response, "testCookie", encodeCookie,	
								EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue(), EnumCookieConstants.COOKIE_MAX_AGE, null);
		
		
	}
	
	public void deleteTestCookie(HttpServletResponse response){
		
		// 清掉cookie
		Cookie deleteCookie = new Cookie("testCookie", null);
		deleteCookie.setDomain(EnumCookieConstants.COOKIE_PCHOME_DOMAIN.getValue());
		deleteCookie.setMaxAge(0);
		deleteCookie.setPath("/");
		response.addCookie(deleteCookie);
		
	}

	public void setPfdUserAdAccountRefService(
			IPfdUserAdAccountRefService pfdUserAdAccountRefService) {
		this.pfdUserAdAccountRefService = pfdUserAdAccountRefService;
	}
	
	
}
