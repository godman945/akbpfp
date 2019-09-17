package com.pchome.enumerate.report;

public enum EnumReportAdType {
	
	/**
	 * adType:all <br>
	 * adTypeName:全部
	 */
	ALL("all", "全部"), 
	/**
	 * adType:0 <br>
	 * adTypeName:搜尋 + 聯播網廣告
	 */
	SEARCHANDCHANNEL("0", "搜尋 + 聯播網廣告"), 
	/**
	 * adType:1 <br>
	 * adTypeName:搜尋廣告
	 */
	SEARCH("1", "搜尋廣告"), 
	/**
	 * adType:2 <br>
	 * adTypeName:聯播網廣告
	 */
	CHANNEL("2", "聯播網廣告");

	private final String adType;
	private final String adTypeName;

	private EnumReportAdType(String adType, String adTypeName) {
		this.adType = adType;
		this.adTypeName = adTypeName;
	}

	public String getAdType() {
		return adType;
	}

	public String getAdTypeName() {
		return adTypeName;
	}
	
	/**
	 * 取得相對應的enum資料
	 * @param type all/0/1/2
	 * @return 全部/搜尋 + 聯播網廣告/搜尋廣告/聯播網廣告
	 */
	public static EnumReportAdType getEnumAdTypeData(final String type) {
		for (EnumReportAdType e : EnumReportAdType.values()) {
			if (e.getAdType().equalsIgnoreCase(type)) {
				return e;
			}
		}
		return null;
	}
}