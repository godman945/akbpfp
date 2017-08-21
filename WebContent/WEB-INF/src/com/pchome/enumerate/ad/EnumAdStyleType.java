package com.pchome.enumerate.ad;

public enum EnumAdStyleType {
	
	AD_STYLE_MULTIMEDIA_TEXT("TXT",0),
	AD_STYLE_MULTIMEDIA_PIC("IMG", 0),
	AD_STYLE_MULTIMEDIA_TEXT_PIC("TMG", 0),
	AD_STYLE_VIDEO("VIDEO", 1);

	private final String key;
	private final int type;
	
	private EnumAdStyleType(String key, int type){
		this.key = key;
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public int getType() {
		return type;
	}


}
