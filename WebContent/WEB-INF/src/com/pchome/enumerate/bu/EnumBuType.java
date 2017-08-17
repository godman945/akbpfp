package com.pchome.enumerate.bu;

public enum EnumBuType {

	BU_NAME("buName","buName"),
	BU_ID("buId","buId"),
	BU_PFD_CUSTOMER("pfdc","pfdc"),
	BU_URL("url","url"),
	BU_LOGIN_KEY("key","key");
	
	private final String key;
	private final String value;
	
	private EnumBuType(String key, String value){
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
