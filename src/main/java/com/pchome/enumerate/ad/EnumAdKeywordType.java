package com.pchome.enumerate.ad;

public enum EnumAdKeywordType {

	/**
	 * 1:廣泛比對
	 */
	WIDELY("widely", "1", "廣泛比對"),
	/**
	 * 2:詞組比對
	 */
	PHRASE("phrase", "2", "詞組比對"),
	/**
	 * 3:精準比對
	 */
	PRECISION("precision", "3", "精準比對");
	
	
	private final String type;
	private final String style;
	private final String chName;
	
	private EnumAdKeywordType(String type, String style, String chName) {
		this.type = type;
		this.style = style;
		this.chName = chName;
	}

	public String getType() {
		return type;
	}

	public String getStyle() {
		return style;
	}

	public String getChName() {
		return chName;
	}

}