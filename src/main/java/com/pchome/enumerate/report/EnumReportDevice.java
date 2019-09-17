package com.pchome.enumerate.report;

public enum EnumReportDevice {
	/**
	 * devType:all <br>
	 * devTypeName:全部
	 */
	ALL("all", "全部"), 
	/**
	 * devType:PCandMobile <br>
	 * devTypeName:電腦 + 行動
	 */
	PCANDMOBILE("PCandMobile", "電腦 + 行動"), 
	/**
	 * devType:mobile <br>
	 * devTypeName:行動
	 */
	MOBILE("mobile", "行動"), 
	/**
	 * devType:PC <br>
	 * devTypeName:電腦
	 */
	PC("PC", "電腦");

	private final String devType;
	private final String devTypeName;

	private EnumReportDevice(String devType, String devTypeName) {
		this.devType = devType;
		this.devTypeName = devTypeName;
	}

	public String getDevType() {
		return devType;
	}

	public String getDevTypeName() {
		return devTypeName;
	}
	
}