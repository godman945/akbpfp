package com.pchome.enumerate.ad;

public enum EnumAdCannelMobileSize {
	adp_201507210004("2", "300", "100"),
	//adp_201510220001("14", "320", "100"),
	adp_201507210001("1", "300", "250"),
	
	//2016/05/04	新增加尺寸
	adp_201601210001("16", "320", "480");
	
	private final String name;
	private final String widh;
	private final String height;
	
	private EnumAdCannelMobileSize(String name, String widh, String height){
		this.name = name;
		this.widh = widh;
		this.height = height;
	}

	public String getName() {
	    return name;
	}

	public String getWidh() {
	    return widh;
	}

	public String getHeight() {
	    return height;
	}
}
