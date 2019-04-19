package com.pchome.enumerate.codeManage;

public enum EnumConvertCodeType {
	WebImpConvertTracking("0","網頁曝光轉換追蹤"),
	WebClickConvertTracking("1","點擊事件轉換追蹤");
	
	private final String type;
	private final String chName;
	
	private EnumConvertCodeType(String type, String chName) {
		this.type = type;
		this.chName = chName;
	}

	public String getType() {
		return type;
	}

	public String getChName() {
		return chName;
	}

}
