package com.pchome.enumerate.ssl;

public enum EnumSSLAction {
	
//	ACCOUNT_CONTENT("accountContent.html"),
//	ACCOUNT_CONTENT_UPDATE("accountContentUpdate.html"),
//	ACCOUNT_INFO_MODIFY("accountInfoModify.html"),
//	ACCOUNT_INFO_UPDATE("accountInfoUpdate.html"),
	PFD_APPLY("pfdApply.html");
//	APPLY("apply.html");
	
	private final String action;

	private EnumSSLAction(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}
	
	
}
