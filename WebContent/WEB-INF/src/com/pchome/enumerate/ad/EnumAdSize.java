package com.pchome.enumerate.ad;

public enum EnumAdSize {
    	adp_201507210001("1", "300", "250"),
    	//adp_201507210002("5", "478", "115"),
    	//adp_201507210003("6", "220", "90"),
    	//adp_201507210004("7", "300", "100"),
    	
    	//stg
    	//adp_201507210005("10", "728", "90"),
    	//adp_201507210006("11", "250", "80");
    	
    	//prd
    	adp_201507210005("9", "728", "90"),
    	adp_201507210006("10", "250", "80");
    	
    	//adp_201507210007("12", "160", "240");
    	
	private final String name;
	private final String widh;
	private final String height;
	
	private EnumAdSize(String name, String widh, String height){
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
