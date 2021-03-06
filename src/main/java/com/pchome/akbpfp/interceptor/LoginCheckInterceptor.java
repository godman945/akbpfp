package com.pchome.akbpfp.interceptor;



import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.pchome.akbpfd.db.service.user.PfdUserMemberRefService;
import com.pchome.akbpfp.api.CookieProccessAPI;
import com.pchome.akbpfp.db.pojo.PfdUserMemberRef;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.pojo.PfpUser;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;
import com.pchome.akbpfp.db.service.bu.PfpBuService;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.user.PfpUserMemberRefService;
import com.pchome.akbpfp.db.service.user.PfpUserService;
import com.pchome.enumerate.account.EnumAccountStatus;
import com.pchome.enumerate.account.EnumPfpRootUser;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.enumerate.cookie.EnumCookiePfpKey;
import com.pchome.enumerate.pfd.EnumPfdUserPrivilege;
import com.pchome.enumerate.privilege.EnumPrivilegeModel;
import com.pchome.enumerate.user.EnumUserStatus;
import com.pchome.soft.depot.utils.CookieStringToMap;
import com.pchome.soft.depot.utils.CookieUtil;

public class LoginCheckInterceptor extends AbstractInterceptor{

    private static final long serialVersionUID = 1L;
    protected final Logger log = LogManager.getRootLogger();
    private PfpUserService pfpUserService;
	private PfpCustomerInfoService pfpCustomerInfoService;
	private CookieProccessAPI cookieProccessAPI;
	private PfpUserMemberRefService pfpUserMemberRefService;
	private PfdUserMemberRefService pfdUserMemberRefService;
	private PfpBuService pfpBuService;
	//商店街bu對應經銷商
	private String buPortalPfdc;
	//商店街bu名稱
	private String pcstoreName;
	//商店街bu Referer來源
	private String buPcstoreReferer;
	/**
	 * 登入判斷
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String pcId = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_MEMBER_ID_PCHOME.getValue(), EnumCookieConstants.COOKIE_USING_CODE.getValue());
		String userData = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_AKBPFP_USER.getValue(),EnumCookieConstants.COOKIE_USING_CODE.getValue());
		if(StringUtils.isNotBlank(pcId) && StringUtils.isNotBlank(userData)){
			// 解析 cookie 
			EnumMap<EnumCookiePfpKey, String> cookieMap = CookieStringToMap.getInstance().transformEnumMap(userData);
			if(cookieMap == null){
				return "index";
			}
			//新增外部登出後重登
			//1.判斷目前cookie登陸使用者是否相同
			//2.判斷使用者權限 --> 小天使  > PFD > 一般user
			//3.具有管理者權限但非該系統管理者
			//4.寫入cookie
			String realCookieCustomerTitle = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_MEMBER_ID_PCHOME.getValue(),EnumCookieConstants.COOKIE_USING_CODE.getValue());
			String cookieCustomerTitle = cookieMap.get(EnumCookiePfpKey.PFP_REALITY_USER_TITLE);
			if(!realCookieCustomerTitle.equals(cookieCustomerTitle)){
			    PfpUser pfpUser = new PfpUser();
			    List<PfpUserMemberRef> pfpUserMemberRefList = new ArrayList<PfpUserMemberRef>();
			    pfpUserMemberRefList = pfpUserMemberRefService.validUserMemberRef(realCookieCustomerTitle);
			    boolean pfpAngelFlag= false;
			    boolean pfdAngelFlag = false;
			    for (PfpUserMemberRef pfpUserMemberRef : pfpUserMemberRefList) {
				if(pfpUserMemberRef.getId().getMemberId().equals(realCookieCustomerTitle)){
				    pfpUser = pfpUserMemberRef.getPfpUser();
				    if(EnumPrivilegeModel.ADM_USER.getPrivilegeId() == pfpUserMemberRef.getPfpUser().getPrivilegeId() ){
					pfpAngelFlag = true;
				    }
				    PfdUserMemberRef pfdUserMemberRef = pfdUserMemberRefService.getUserMemberRef(pfpUserMemberRef.getId().getMemberId());
				    if(!(pfdUserMemberRef == null)){
					if(EnumPfdUserPrivilege.ROOT_USER.getPrivilege() == pfdUserMemberRef.getPfdUser().getPrivilegeId() || 
							EnumPfdUserPrivilege.ACCOUNT_MANAGER.getPrivilege() == pfdUserMemberRef.getPfdUser().getPrivilegeId() || 
							EnumPfdUserPrivilege.SALES_MANAGER.getPrivilege() == pfdUserMemberRef.getPfdUser().getPrivilegeId() ){
					    pfdAngelFlag = true;
					}
				    }
				    if(pfpAngelFlag && pfdAngelFlag){
					cookieProccessAPI.deletePfpLoginCookie(response);
					cookieProccessAPI.writerPfpLoginCookie(response, pfpUser, EnumPfpRootUser.PCHOME_MANAGER, realCookieCustomerTitle);
					userData = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_AKBPFP_USER.getValue(),EnumCookieConstants.COOKIE_USING_CODE.getValue());
					return "index";
				    }else if(!pfpAngelFlag && !pfdAngelFlag){
					if(!StringUtils.isBlank(cookieCustomerTitle)){
					    cookieProccessAPI.deletePfpLoginCookie(response);
					    cookieProccessAPI.writerPfpLoginCookie(response, pfpUser, EnumPfpRootUser.NO, realCookieCustomerTitle);
					    userData = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_AKBPFP_USER.getValue(),EnumCookieConstants.COOKIE_USING_CODE.getValue());
					    return "summary";
					}else{
					    cookieProccessAPI.deletePfpLoginCookie(response);
					    cookieProccessAPI.writerPfpLoginCookie(response, pfpUser, EnumPfpRootUser.NO, realCookieCustomerTitle);
					    userData = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_AKBPFP_USER.getValue(),EnumCookieConstants.COOKIE_USING_CODE.getValue());
					    return "index";
					}
				    }else if(pfpAngelFlag){
					cookieProccessAPI.deletePfpLoginCookie(response);
					cookieProccessAPI.writerPfpLoginCookie(response, pfpUser, EnumPfpRootUser.PCHOME_MANAGER, realCookieCustomerTitle);
					userData = CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_AKBPFP_USER.getValue(),EnumCookieConstants.COOKIE_USING_CODE.getValue());
					return "index";
				    }
				    break; 
				}
			    }
			}
			
			// 檢查帳戶狀態
			String custmerInfoId = cookieMap.get(EnumCookiePfpKey.PFP_CUSTOMER_INFO_ID);
			log.info(">>> custmerInfoId: " + custmerInfoId);
			
			PfpCustomerInfo customerInfo = pfpCustomerInfoService.findCustomerInfo(custmerInfoId);
			
			if(customerInfo == null || customerInfo.getStatus().equals(EnumAccountStatus.APPLY.getStatus())){
				// 帳戶申請中
				return "apply";
			}else if(customerInfo.getStatus().equals(EnumAccountStatus.STOP.getStatus())){
				// 帳戶被停權
				return "customerInfoStop";
			}
			
			// 檢查登入者狀態
			String userId = cookieMap.get(EnumCookiePfpKey.PFP_USER_ID);
			log.info(">>> userId: " + userId);	
			
			PfpUser user = pfpUserService.findUser(userId);	
			
			if(user == null || 
					user.getStatus().equals(EnumUserStatus.APPLY.getStatusId()) || 
					user.getStatus().equals(EnumUserStatus.INVITE_PCID.getStatusId()) || 
					user.getStatus().equals(EnumUserStatus.INVITE_NOT_PCID.getStatusId())){
				// 帳號申請中 or 邀請中
				return invocation.invoke();
			}else if(user.getStatus().equals(EnumUserStatus.CLOSE.getStatusId())){
				// 帳號關閉
				return "userClose";
			}else if(user.getStatus().equals(EnumUserStatus.STOP.getStatusId())){
				// 帳號停權
				return "userStop";
			}else{
				
				// 確認 UserId 是否同一個pcId
				String refPcId = null;
				String manager = cookieMap.get(EnumCookiePfpKey.MANAGER);
				for(PfpUserMemberRef ref:user.getPfpUserMemberRefs()){
					//log.info("ref.getId().getMemberId() = " + ref.getId().getMemberId());
					refPcId = ref.getId().getMemberId();
				}
				// 不是管理者帳戶又不同一個 id
				if(!pcId.equals(refPcId) &&	!manager.equals(EnumPfpRootUser.PCHOME_MANAGER.getPrivilege()) &&	!manager.equals(EnumPfpRootUser.PFD.getPrivilege())){
					return "index";
				}
			}
		}
		else{
			return "index";
		}
				
		return invocation.invoke();
	}
	
	public void setPfpUserService(PfpUserService pfpUserService) {
		this.pfpUserService = pfpUserService;
	}
	
	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public CookieProccessAPI getCookieProccessAPI() {
	    return cookieProccessAPI;
	}

	public void setCookieProccessAPI(CookieProccessAPI cookieProccessAPI) {
	    this.cookieProccessAPI = cookieProccessAPI;
	}

	public PfpUserMemberRefService getPfpUserMemberRefService() {
	    return pfpUserMemberRefService;
	}

	public void setPfpUserMemberRefService(
		PfpUserMemberRefService pfpUserMemberRefService) {
	    this.pfpUserMemberRefService = pfpUserMemberRefService;
	}

	public PfdUserMemberRefService getPfdUserMemberRefService() {
	    return pfdUserMemberRefService;
	}

	public void setPfdUserMemberRefService(
		PfdUserMemberRefService pfdUserMemberRefService) {
	    this.pfdUserMemberRefService = pfdUserMemberRefService;
	}

	public PfpBuService getPfpBuService() {
		return pfpBuService;
	}

	public void setPfpBuService(PfpBuService pfpBuService) {
		this.pfpBuService = pfpBuService;
	}


	public String getBuPortalPfdc() {
		return buPortalPfdc;
	}

	public void setBuPortalPfdc(String buPortalPfdc) {
		this.buPortalPfdc = buPortalPfdc;
	}

	public String getPcstoreName() {
		return pcstoreName;
	}

	public void setPcstoreName(String pcstoreName) {
		this.pcstoreName = pcstoreName;
	}

	public String getBuPcstoreReferer() {
		return buPcstoreReferer;
	}

	public void setBuPcstoreReferer(String buPcstoreReferer) {
		this.buPcstoreReferer = buPcstoreReferer;
	}
	
	
}
