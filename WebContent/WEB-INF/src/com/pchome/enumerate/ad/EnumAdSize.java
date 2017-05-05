package com.pchome.enumerate.ad;

public enum EnumAdSize {
    	adp_201507210001("1", "300", "250"),
    	//adp_201507210002("5", "478", "115"),
    	//adp_201507210003("7", "220", "90"),
    	adp_201507210004("2", "300", "100"),
    	   	
    	
    	//prd
    	adp_201507210005("9", "728", "90"),
    	adp_201507210006("10", "250", "80"),
    	adp_201510190001("12", "120", "600"),
    	adp_201510190002("13", "160", "600"),
    	
    	
    	//2016/01/21	新增加尺寸
    	adp_201601210004("20", "140", "300"),
    	adp_201507210007("11", "160", "240"),
    	
    	//2016/05/04	新增加尺寸
    	adp_201601210001("16", "320", "480"),
    	adp_201605040001("21", "180", "150"),
    	
    	//2017/03/30	新增加尺寸
    	adp_201601210003("18", "970", "250"),
    	
    	//2017/05/05	新增加尺寸
    	adp_201601210002("17", "336", "280");
    	
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
