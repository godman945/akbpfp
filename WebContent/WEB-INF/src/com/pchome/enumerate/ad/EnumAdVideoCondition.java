package com.pchome.enumerate.ad;

public enum EnumAdVideoCondition {
	
	AD_VIDEO_TOTAL_TIME("影片長度",30);
	
	private final int value;
	private final String name;
	
	private EnumAdVideoCondition(String name,int value){
		this.name = name;
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

}
