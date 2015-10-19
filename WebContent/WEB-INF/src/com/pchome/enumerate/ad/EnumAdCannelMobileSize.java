package com.pchome.enumerate.ad;

public enum EnumAdCannelMobileSize {
	adp_201507210001("1", "300", "250"),
	adp_201507210004("7", "300", "100");
	
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
