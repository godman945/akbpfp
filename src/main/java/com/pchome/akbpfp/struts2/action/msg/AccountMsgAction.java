package com.pchome.akbpfp.struts2.action.msg;

import com.pchome.akbpfp.struts2.BaseCookieAction;

public class AccountMsgAction extends BaseCookieAction{
	
	private String userEmail;
	
	public String execute() throws Exception{
		
		return SUCCESS;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
}
