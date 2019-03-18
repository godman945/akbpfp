package com.pchome.enumerate.codeManage;

public enum EnumConvertNumType {
	EveryTime("1","每次"),		
	Once("2","一次");
	
	private final String type;
	private final String chName;
	
	private EnumConvertNumType(String type, String chName) {
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
