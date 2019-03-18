package com.pchome.enumerate.account;

/**
 * 是否開啟授權頁:Y是 N否
 */
public enum EnumPfpAuthorizAtion {

	YES("Y"),
	NO("N");
	
	private final String status;

	private EnumPfpAuthorizAtion(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}
