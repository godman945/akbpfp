package com.pchome.enumerate.ad;

public enum EnumAdPvLimitStyle {

	NO_STYLE_LIMIT("0","不設限制"),
	AD_ACTION_LIMIT("1","廣告活動"),
	AD_GROUP_LIMIT("2","廣告分類"),	
	AD_AD_LIMIT("3","廣告明細");	
	
	private final String style;
	private final String name;
	
	private EnumAdPvLimitStyle(String style, String name){
		this.style = style;
		this.name = name;
	}
	
	public String getStyle() {
		return style;
	}

	public String getName() {
		return name;
	}
	
}
