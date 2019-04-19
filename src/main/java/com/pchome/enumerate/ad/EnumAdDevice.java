package com.pchome.enumerate.ad;

public enum EnumAdDevice {
	
	DEVICE_ALL(0,"裝置","電腦+行動裝置"),
	DEVICE_PC(1,"電腦","電腦"),
	DEVICE_MOBILE(2,"行動裝置","行動裝置");
	
	private final int devType;
	private final String devChName;
	private final String devTypeName;
	
	private EnumAdDevice(int devType, String devChName, String devTypeName){
		this.devType = devType;
		this.devChName = devChName;
		this.devTypeName = devTypeName;
	}

	public int getDevType() {
		return devType;
	}

	public String getDevChName() {
		return devChName;
	}

	public String getDevTypeName() {
		return devTypeName;
	}
	
}
