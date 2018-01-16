package com.pchome.enumerate.ad;

public enum EnumAdVideoSizeAddEnum {

	SIZE_300_250("300250"),
	SIZE_300_600("300600"),
	SIZE_336_280("336280"),
	SIZE_970_250("970250");
	
	private final String type;

	private EnumAdVideoSizeAddEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}


}
