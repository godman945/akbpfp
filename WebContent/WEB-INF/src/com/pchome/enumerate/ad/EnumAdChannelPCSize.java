package com.pchome.enumerate.ad;

public enum EnumAdChannelPCSize {
	adp_201507210001("1", "300", "250"),
	adp_201507210004("2", "300", "100"),
	adp_201507210005("9", "728", "90"),
	adp_201507210006("10", "250", "80"),
	adp_201507210007("11", "160", "240"),
	adp_201510190001("12", "120", "600"),
	adp_201510190002("13", "160", "600"),
	adp_201601210002("17", "336", "280"),
	adp_201601210003("18", "970", "250"),
	adp_201601210004("20", "140", "300"),
	adp_201605040001("21", "180", "150"),
	adp_201709190001("23", "300", "600"),
	adp_201711060001("24", "950", "390"),
	adp_201711060002("25", "640", "390");
	
	private final String name;
	private final String widh;
	private final String height;
	
	private EnumAdChannelPCSize(String name, String widh, String height){
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
