package com.pchome.akbpfp.struts2.action.ad;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.api.CookieProccessAPI;
import com.pchome.akbpfp.db.pojo.PfpUser;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;
import com.pchome.akbpfp.db.service.user.PfpUserMemberRefService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.account.EnumPfpRootUser;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.enumerate.privilege.EnumPrivilegeModel;
import com.pchome.soft.depot.utils.CookieUtil;
import com.pchome.soft.depot.utils.EncodeUtil;

public class AdFreeCheckAction extends BaseCookieAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private PfpUserMemberRefService PfpUserMemberRefService;
	private CookieProccessAPI cookieProccessAPI;
	private String classifiedsUrl;

	// get data
	private String aid;
	
	private String redirectUrl;
	
	private String ref;


	public String getRedirectUrl() {
		return redirectUrl;
	}

	public String adFreeCheck() throws Exception {
		
		String result ="";
		log.info("aid= " + aid);
		
		log.info("id_pchome: " +super.getId_pchome());
		
		if(StringUtils.isNotBlank(super.getId_pchome())){
		// 取使用者資料
		List<PfpUserMemberRef> userMemberRefs=null;
		userMemberRefs = PfpUserMemberRefService.activateUserMemberRef(super.getId_pchome());
		
		log.info("userMemberRefs: " +userMemberRefs);
		ref = request.getHeader("Referer");
		log.info("refer: "+ref);
		// 帳號不存在
		
		if(userMemberRefs.isEmpty()){
			log.info("userMemberRefs.isEmpty()");
			log.info("classifiedsUrl="+classifiedsUrl);
			
			//從免費刊登進入(無帳號)導open頁
			if(StringUtils.isNotBlank(ref)){
				if(ref.contains(classifiedsUrl)){
					log.info("return INPUT to open.html");
					result = "open";
				}
			}
		}else{
			//從免費刊登進入(有pfp帳號)導入原網頁
			//導到 AdFreeActionAddAction
			PfpUser user = userMemberRefs.get(0).getPfpUser();
			
			cookieProccessAPI.writerPfpLoginCookie(response, user, EnumPfpRootUser.NO, null);
			
			log.info("privilege: "+user.getPrivilegeId());
			//使用者權限 (總管理者,管理全帳戶,廣告、報表、帳單管理) 導向新增廣告頁面
			if (EnumPrivilegeModel.ROOT_USER.getPrivilegeId().equals(user.getPrivilegeId()) ||
				EnumPrivilegeModel.ADM_USER.getPrivilegeId().equals(user.getPrivilegeId())||
				EnumPrivilegeModel.AD_USER.getPrivilegeId().equals(user.getPrivilegeId())) {
				
				log.info("return to AdFreeAddAction.html");
				result = "forword"; 
			}else if(EnumPrivilegeModel.REPORT_USER.getPrivilegeId().equals(user.getPrivilegeId()) ||
					EnumPrivilegeModel.BILL_USER.getPrivilegeId().equals(user.getPrivilegeId())){
				
				log.info("alert message and return to Summary.html");
				result = "privilege"; 
			}
			
		}
	}

		return result;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}
	
	public void setPfpUserMemberRefService(
			PfpUserMemberRefService pfpUserMemberRefService) {
		PfpUserMemberRefService = pfpUserMemberRefService;
	}

	public void setCookieProccessAPI(CookieProccessAPI cookieProccessAPI) {
		this.cookieProccessAPI = cookieProccessAPI;
	}

	public void setClassifiedsUrl(String classifiedsUrl) {
		this.classifiedsUrl = classifiedsUrl;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}


}
