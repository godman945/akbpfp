package com.pchome.enumerate.ad;

public enum EnumAdStyleType {
	
	AD_STYLE_MULTIMEDIA("多媒體廣告", 0,"MEDIA"),
	AD_STYLE_VIDEO("影音廣告", 1,"VEDIO");

	private final String key;
	private final int type;
	private final String typeName;
	
	private EnumAdStyleType(String key, int type,String typeName){
		this.key = key;
		this.type = type;
		this.typeName = typeName;
	}

	public String getKey() {
		return key;
	}

	public int getType() {
		return type;
	}

	public String getTypeName() {
		return typeName;
	}

}
