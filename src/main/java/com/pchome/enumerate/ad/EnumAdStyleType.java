package com.pchome.enumerate.ad;

public enum EnumAdStyleType {
	
	AD_STYLE_MULTIMEDIA("多媒體廣告", 0,"MEDIA"),
	AD_STYLE_VIDEO("影音廣告", 1,"VIDEO"),
	AD_STYLE_PRODUCT("商品廣告", 2,"PROD");

	private final String type;
	private final int value;
	private final String typeName;
	
	private EnumAdStyleType(String type, int value,String  typeName){
		this.type = type;
		this.value = value;
		this.typeName = typeName;
	}

	public String getType() {
		return type;
	}

	public int getValue() {
		return value;
	}

	public String getTypeName() {
		return typeName;
	}

	/**
	 * 取得相對應的enum資料
	 * @param typeName MEDIA/VIDEO/PROD
	 * @return
	 */
	public static EnumAdStyleType getEnumAdStyleTypeData(final String typeName) {
		for (EnumAdStyleType e : EnumAdStyleType.values()) {
			if (e.getTypeName().equalsIgnoreCase(typeName)) {
				return e;
			}
		}
		return null;
	}
}