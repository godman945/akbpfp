package com.pchome.enumerate.codeManage;

public enum EnumConvertBelongType {
	FinalClick("1","最終點擊"),		
	FirstClick("2","最初點擊");
	
	private final String type;
	private final String chName;
	
	private EnumConvertBelongType(String type, String chName) {
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
