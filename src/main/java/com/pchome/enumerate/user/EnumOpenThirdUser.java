package com.pchome.enumerate.user;

public enum EnumOpenThirdUser {
	PFDC20150422001("PFDC20150422001"),
	PFDC20161012001("PFDC20161012001");

	
	private final String pfdCustomerInfoId;
	
	private EnumOpenThirdUser(String pfdCustomerInfoId){
		this.pfdCustomerInfoId = pfdCustomerInfoId;
	}

	public String getPfdCustomerInfoId() {
		return pfdCustomerInfoId;
	}

}
