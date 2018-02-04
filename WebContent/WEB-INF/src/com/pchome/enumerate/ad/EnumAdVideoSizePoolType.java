package com.pchome.enumerate.ad;

public enum EnumAdVideoSizePoolType {
    	DEFAULT_300250("300","250","DEFAULT","adp_201507210001","c_x05_mo_tad_0080","300","250"),
    	DEFAULT_336280("336","280","DEFAULT","adp_201601210002","c_x05_mo_tad_0080","336","280"),
    	DEFAULT_640390("640","390","DEFAULT","adp_201711060002","c_x05_mo_tad_0080","640","390"),
		BANNER_300600("300", "600","BANNER","adp_201709190001","c_x05_mo_tad_0080","300","600"),
    	BANNER_320480("320", "480","BANNER","adp_201601210001","c_x05_mo_tad_0080","320","480"),
    	BANNER_950390("310", "390","BANNER","adp_201711060001","c_x05_mo_tad_0080","950","390"),
    	BANNER_970250("579", "250","BANNER","adp_201601210003","c_x05_mo_tad_0080","970","250"),
    	VERTICAL_300600("300", "600","VERTICAL","adp_201709190001","c_x05_mo_tad_0110","300","600"),
    	VERTICAL_320400("320", "480","VERTICAL","adp_201709190001","c_x05_mo_tad_0110","320","480");
    	
	private final String widh;
	private final String height;
	private final String type;
	private final String poolType;
	private final String templateAdSeq;
	private final String realWidth;
	private final String realHeight;
	
	private EnumAdVideoSizePoolType(String widh, String height,String type,String poolType,String templateAdSeq,String realWidth,String realHeight ){
		this.widh = widh;
		this.height = height;
		this.type = type;
		this.poolType = poolType;
		this.templateAdSeq = templateAdSeq;
		this.realWidth = realWidth;
		this.realHeight = realHeight;
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

	public String getRealWidth() {
		return realWidth;
	}

	public String getRealHeight() {
		return realHeight;
	}


}

