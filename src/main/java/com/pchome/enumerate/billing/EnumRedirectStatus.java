package com.pchome.enumerate.billing;

public enum EnumRedirectStatus {
	
	APPLY("apply"),						// 帳戶申請
	APPLY_WAIT("applywait"),			// 帳戶等待開通
	APPLY_FAIL("applyfail"),			// 帳戶申請重新儲值
	SUMMARY("summary"),					// 帳戶開通成功
	ACCOUNT_REMAIN("accountRemain"),	// 帳戶加值
	SUCCESS("success");					
	
	private final String action;

	private EnumRedirectStatus(String action) {
		this.action = action;
	}

	public String getAction() {
		return action;
	}
	
}
