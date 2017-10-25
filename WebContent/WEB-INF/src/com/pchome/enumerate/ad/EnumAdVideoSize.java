package com.pchome.enumerate.ad;

public enum EnumAdVideoSize {
    	DEFAULT_300250("300","250","DEFAULT"),
    	DEFAULT_336280("336","280","DEFAULT"),
    	DEFAULT_640390("640","390","DEFAULT"),
		BANNER_300600("300", "600","BANNER"),
    	BANNER_320480("320", "480","BANNER"),
    	BANNER_950390("310", "390","BANNER"),
    	BANNER_970250("579", "250","BANNER");
    	
	private final String widh;
	private final String height;
	private final String type;
	
	private EnumAdVideoSize(String widh, String height,String type){
		this.widh = widh;
		this.height = height;
		this.type = type;
	}

	public String getWidh() {
	    return widh;
	}

	public String getHeight() {
	    return height;
	}

	public String getType() {
		return type;
	}


}

