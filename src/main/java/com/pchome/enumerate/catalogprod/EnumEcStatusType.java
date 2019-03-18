package com.pchome.enumerate.catalogprod;

public enum EnumEcStatusType {
	Close_Prod("0","封存"),		
	Open_Prod("1","啟用");
	
	private final String type;
	private final String chName;
	
	private EnumEcStatusType(String type, String chName) {
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
