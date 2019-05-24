package com.pchome.enumerate.ad;

public enum EnumAdArea {
	NORTH("AR01", "北臺灣"),
	CENTRAL("AR02", "中臺灣"),
	SOUTH("AR03", "南臺灣"),
	EAST("AR04", "東臺灣"),
	ISLAND("AR05", "金馬地區");
	private final String areaCode;
	private final String name;

	private EnumAdArea(String areaCode, String name){
		this.areaCode = areaCode;
		this.name = name;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getName() {
		return name;
	}
	
	
}
