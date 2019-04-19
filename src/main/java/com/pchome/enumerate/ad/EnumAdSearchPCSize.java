package com.pchome.enumerate.ad;

public enum EnumAdSearchPCSize {
	adp_201510190001("12", "120", "600"),
	adp_201510190002("13", "160", "600");
	
	private final String name;
	private final String widh;
	private final String height;
	
	private EnumAdSearchPCSize(String name, String widh, String height){
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
