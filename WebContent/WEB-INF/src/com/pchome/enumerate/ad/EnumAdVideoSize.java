package com.pchome.enumerate.ad;

public enum EnumAdVideoSize {
    	DEFAULT_300250("300","250"),
    	DEFAULT_336280("336","280"),
    	DEFAULT_640390("640","390"),
		BANNER_300600("300", "600"),
    	BANNER_320480("320", "480"),
    	BANNER_950390("310", "390"),
    	BANNER_970250("579", "250");
    	
	private final String widh;
	private final String height;
	
	private EnumAdVideoSize(String widh, String height){
		this.widh = widh;
		this.height = height;
	}

	public String getWidh() {
	    return widh;
	}

	public String getHeight() {
	    return height;
	}


}
