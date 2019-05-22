package com.pchome.enumerate.catalogprod;

public enum EnumProdCropType {
	CROP("0","crop","圖片自動填滿(預設)"),		
	NOCROP("1","nocrop","圖片完全套入");
	
	private final String type;
	private final String value;
	private final String chName;
	
	private EnumProdCropType(String type,String value, String chName) {
		this.type = type;
		this.value = value;
		this.chName = chName;
	}

	public String getType() {
		return type;
	}

	public String getChName() {
		return chName;
	}

	public String getValue() {
		return value;
	}

}
