package com.pchome.enumerate.ad;

public enum EnumAdCannelMobileSize {
	adp_201507210004("2", "300", "100"),
	adp_201507210001("1", "300", "250"),
	adp_201601210001("16", "320", "480"),
	adp_201601210002("17", "336", "280"),
	adp_201510190001("12", "120", "600"),
	adp_201510190002("13", "160", "600"),
	adp_201507210006("10", "250", "80"),
	adp_201507210005("9", "728", "90"),
	adp_201601210004("20", "140", "300"),
	adp_201507210007("11", "160", "240"),
	adp_201605040001("21", "180", "150"),
	adp_201601210003("18", "970", "250"),
	adp_201904090001("26", "1400", "160"),
	adp_201904090002("27", "1400", "60");
	
	
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
