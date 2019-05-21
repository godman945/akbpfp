package com.pchome.enumerate.codeManage;

public enum EnumConvertPriceType {
	ALL("1","不指定價值"),
	ONLY("2","統一轉換價值"),
	SELF("3","自訂轉換價值");
	
	private final String type;
	private final String chName;
	
	private EnumConvertPriceType(String type, String chName) {
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
