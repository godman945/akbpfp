package com.pchome.enumerate.recognize;

public enum EnumOrderType {

	SAVE("S","儲值金"),
	GIFT("G","禮金");
	
	private final String typeTag;
	private final String typeName;
	
	private EnumOrderType(String typeTag, String typeName) {
		this.typeTag = typeTag;
		this.typeName = typeName;
	}

	public String getTypeTag() {
		return typeTag;
	}

	public String getTypeName() {
		return typeName;
	}
	
	
	
}
