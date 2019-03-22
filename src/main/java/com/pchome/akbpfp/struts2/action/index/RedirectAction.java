package com.pchome.akbpfp.struts2.action.index;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.pchome.akbpfp.api.CookieProccessAPI;
import com.pchome.akbpfp.api.MemberAPI;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.pojo.PfpUser;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;
import com.pchome.akbpfp.db.service.accesslog.IAdmAccesslogService;
import com.pchome.akbpfp.db.service.user.IPfpUserMemberRefService;
import com.pchome.akbpfp.db.service.user.IPfpUserService;
import com.pchome.akbpfp.db.vo.member.MemberVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.account.EnumPfpAuthorizAtion;
import com.pchome.enumerate.account.EnumPfpRootUser;
import com.pchome.enumerate.redirect.EnumRedirectAction;
import com.pchome.enumerate.user.EnumUserStatus;
import com.pchome.rmi.manager.IPfpProvider;

public class RedirectAction extends BaseCookieAction{

	private static final long serialVersionUID = 1L;

	private IPfpUserService pfpUserService;
	private IPfpUserMemberRefService PfpUserMemberRefService;
	private IPfpProvider pfpProviderProxy;
	private IAdmAccesslogService admAccesslogService;
	private CookieProccessAPI cookieProccessAPI;
	private MemberAPI memberAPI;
	private List<PfpUserMemberRef> userMemberRefs;
	private PfpUser pfpUser;
	
	private String memberServer;
	private String akbPfpServer;
	private String redirectUrl;

	
	public String execute() throws Exception{
		return SUCCESS;
	}

	public String loginAction() throws Exception{

		StringBuffer sb = new StringBuffer();
		sb.append(memberServer).append(EnumRedirectAction.MEMBER_LOGIN.getAction());
		sb.append("?ref=").append(akbPfpServer).append(EnumRedirectAction.PFP_REDIRECT.getAction());
		
		redirectUrl = sb.toString();		
		
		log.info("redirectUrl: "+redirectUrl);
		
		return SUCCESS;		
	}
	public String logoutAction() throws Exception{
		
		// 登出不走 https 協定
		String memberServer = "http://member.pchome.com.tw/";
		
		StringBuffer sb = new StringBuffer();
		sb.append(memberServer).append(EnumRedirectAction.MEMBER_LOGOUT.getAction());
		sb.append("?ref=").append(akbPfpServer).append(EnumRedirectAction.PFP_REDIRECT.getAction());
		
		redirectUrl = sb.toString();
		
		cookieProccessAPI.deleteAllCookie(super.response);
		
		return SUCCESS;
	}

	public String redirectAction() throws Exception{

		// 登入判斷
		if(!this.checkMemberIdExist()){
			return "login"; 
		}
		
		// 帳號認證
		if(!this.checkAuthMemberId()){
			// 未認證，踢回會員中心認證頁
			return "authMember";
		}
		
		// 登入成功，寫 accesslog
		admAccesslogService.recordMemberLog(super.getId_pchome(), request.getRemoteAddr());
		
		// 確認是否管理帳戶
		if(this.checkManagerMemberId()){
			return "manager";
		}
		
		// 管理者權限跳過以下判斷
		if(super.getRoot_user() == null || 
				(!super.getRoot_user().equals(EnumPfpRootUser.PCHOME_MANAGER.getPrivilege()) && 
				!super.getRoot_user().equals(EnumPfpRootUser.PFD.getPrivilege()))){
			
			// 帳戶是否存在
			if(!this.checkPfpAccountExist()){
				return "apply";
			}
			
			// 使用者是否存在
			if(!this.checkPfpUserExist()){
				return "userClose";
			}
			
//			// PFD 建立 PFP 帳戶需補填資料
//			if(this.checkPfpAuthorizedPage()){
//				
//				// 確認是否該帳戶的總管理者
//				if(this.pfpUser.getPfpCustomerInfo().getMemberId().equals(super.getId_pchome())){
//					// 補填帳戶資料 : 設定在struts-index.xml
//					return "authorization";
//				}else{
//					// 提示頁面 : 設定在struts-index.xml
//					return "notRootUser";
//				}
//			}
			
			// 記錄最後登入時間和IP
			this.updateLoginDateTime();
			
			cookieProccessAPI.writerPfpLoginCookie(super.response, this.pfpUser, EnumPfpRootUser.NO, null);
		}
		

		return SUCCESS;
	}
	
	private boolean checkMemberIdExist(){
		
		boolean exist = true;
		
		if(StringUtils.isBlank(super.getId_pchome())){
			exist = false;
		}
		
		return exist;
	}
	
	private boolean checkAuthMemberId() throws Exception{
		
		boolean isAuth = true;
		
		MemberVO memberVO = memberAPI.getMemberVOData(super.getId_pchome());
		String status = memberVO.getAuth();
		
		log.info(" status: " + status);
		
		if (StringUtils.isBlank(status) || !status.equals("y")) {
			isAuth = false;
		}
		
		return isAuth;
	}
	
	private boolean checkPfpAccountExist() {
		
		boolean exist = true;
		
		this.userMemberRefs = PfpUserMemberRefService.activateUserMemberRef(super.getId_pchome());
		
		if(userMemberRefs.isEmpty()){
			// 從 PFD 登入一定會 PFP customerInfoId
			if(StringUtils.isBlank(super.getCustomer_info_id())){
				exist = false;
			}			
		}
		
		//log.info(" checkPfpAccountExist: "+exist);		
		return exist;
	}
	
	private boolean checkPfpUserExist(){
		
		boolean exist = false;
		
		// 使用者狀態不是開啟，不能操作帳戶
		for(PfpUserMemberRef ref:this.userMemberRefs) {
			
			if(ref.getPfpUser().getStatus().equals(EnumUserStatus.START.getStatusId())) {
				this.pfpUser = ref.getPfpUser();
				exist = true;
				break;
			}
		}
		
		return exist;
	}
	
	private boolean checkPfpAuthorizedPage() {
		
		boolean isAuth = false;
		
		PfpCustomerInfo pfpCustomerInfo = this.pfpUser.getPfpCustomerInfo();
		
		if(pfpCustomerInfo.getAuthorizedPage().toUpperCase().equals(EnumPfpAuthorizAtion.YES.getStatus()) && 
				pfpCustomerInfo.getStatus().equals(EnumUserStatus.START.getStatusId())) {
			isAuth = true;
		}
		
		return isAuth;
	}
	
	private boolean checkManagerMemberId(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String ip = request.getRemoteAddr();
		log.info(">>>login  ip = " + ip);
		
		return pfpProviderProxy.isManager(super.getId_pchome(),ip);
	}
	
	private void updateLoginDateTime(){		
		
		this.pfpUser.setLastLoginDate(new Date());
		this.pfpUser.setLastLoginIp(request.getRemoteAddr());
		pfpUserService.saveOrUpdate(this.pfpUser);
	}
	
	public void setPfpUserService(IPfpUserService pfpUserService) {
		this.pfpUserService = pfpUserService;
	}
	
	public void setPfpUserMemberRefService(IPfpUserMemberRefService pfpUserMemberRefService) {
		PfpUserMemberRefService = pfpUserMemberRefService;
	}

	public void setPfpProviderProxy(IPfpProvider pfpProviderProxy) {
		this.pfpProviderProxy = pfpProviderProxy;
	}

	public void setAdmAccesslogService(IAdmAccesslogService admAccesslogService) {
		this.admAccesslogService = admAccesslogService;
	}

	public void setCookieProccessAPI(CookieProccessAPI cookieProccessAPI) {
		this.cookieProccessAPI = cookieProccessAPI;
	}

	public void setMemberServer(String memberServer) {
		this.memberServer = memberServer;
	}

	public void setAkbPfpServer(String akbPfpServer) {
		this.akbPfpServer = akbPfpServer;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setMemberAPI(MemberAPI memberAPI) {
		this.memberAPI = memberAPI;
	}
}
