package com.pchome.enumerate.ad;

public enum EnumAdTimeCode {

	A("A","0-3"),
	B("B","4-7"),
	C("C","8-11"),
	D("D","12-15"),
	E("E","16-19"),
	F("F","20-23");
	
	private final String code;
	private final String name;
	
	private EnumAdTimeCode(String code, String name){
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
	
}
