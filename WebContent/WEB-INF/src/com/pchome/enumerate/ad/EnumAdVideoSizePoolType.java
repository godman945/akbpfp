package com.pchome.enumerate.ad;

public enum EnumAdVideoSizePoolType {
    	DEFAULT_300250("300","250","DEFAULT","adp_201507210001","c_x05_mo_tad_0093"),
    	DEFAULT_336280("336","280","DEFAULT","adp_201601210002","c_x05_mo_tad_0080"),
    	DEFAULT_640390("640","390","DEFAULT","adp_201711060002","c_x05_mo_tad_0080"),
		BANNER_300600("300", "600","BANNER","adp_201709190001","c_x05_mo_tad_0080"),
    	BANNER_320480("320", "480","BANNER","adp_201601210001","c_x05_mo_tad_0080"),
    	BANNER_950390("950", "390","BANNER","adp_201711060001","c_x05_mo_tad_0080"),
    	BANNER_970250("970", "250","BANNER","adp_201601210003","c_x05_mo_tad_0080");
    	
	private final String widh;
	private final String height;
	private final String type;
	private final String poolType;
	private final String templateAdSeq;
	
	private EnumAdVideoSizePoolType(String widh, String height,String type,String poolType,String templateAdSeq ){
		this.widh = widh;
		this.height = height;
		this.type = type;
		this.poolType = poolType;
		this.templateAdSeq = templateAdSeq;
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

	public String getTemplateAdSeq() {
		return templateAdSeq;
	}


}

