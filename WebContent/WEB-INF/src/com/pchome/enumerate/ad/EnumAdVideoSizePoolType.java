package com.pchome.enumerate.ad;

public enum EnumAdVideoSizePoolType {
    	DEFAULT_300250("300","250","DEFAULT","adp_201507210001"),
    	DEFAULT_336280("336","280","DEFAULT","adp_201601210002"),
    	DEFAULT_640390("640","390","DEFAULT",""),
		BANNER_300600("300", "600","BANNER","adp_201709190001"),
    	BANNER_320480("320", "480","BANNER","adp_201601210001"),
    	BANNER_950390("310", "390","BANNER",""),
    	BANNER_970250("579", "250","BANNER","adp_201601210003");
    	
	private final String widh;
	private final String height;
	private final String type;
	private final String poolType;
	
	private EnumAdVideoSizePoolType(String widh, String height,String type,String poolType){
		this.widh = widh;
		this.height = height;
		this.type = type;
		this.poolType = poolType;
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

	public String getPoolType() {
		return poolType;
	}


}

