package com.pchome.enumerate.redirect;

public enum EnumRedirectAction {
	
	// 會員中心 Action
	MEMBER_LOGIN("login.html"),
	MEMBER_LOGOUT("logout.html"),
	MEMBER_API_UPDATE_MEMBER("updateMemberInfo4ADAPI.html"),
	MEMBER_API_CHECK_AVAILABLE_EMAIL("checkEmailAvalibe4ADAPI.html"),
	MEMBER_API_FIND_MEMBER("findMemberInfo4ADAPI.html"),
	MEMBER_API_INVITE_MEMBER_LOGIN("ad_bind_auth_login.html"),
	MEMBER_API_INVITE_MEMBER_REGISTER("ad_bind_unauth_page.html"),
	
	// 廣告聯播網 Action
	PFP_INDEX("index.html"),
	PFP_SUMMARY("summary.html"),
	PFP_REDIRECT("redirect.html"),	
	PFP_API_ACTIVATE_USER("activateUser.html"),
	PFP_API_INVITE_PCID_USER("invitePCIdUser.html"),
	PFP_API_INVITE_EMAIL_USER("inviteEmailUser.html"),
	
	// 金流中心 Action
	BILLING_ORDER_CHECK("orderCheck.html"),  // url 還沒替換舊
	
	test("");
	
	private final String action;
	
	private EnumRedirectAction(String action){
		this.action = action;
	}

	public String getAction() {
		return action;
	}
}
