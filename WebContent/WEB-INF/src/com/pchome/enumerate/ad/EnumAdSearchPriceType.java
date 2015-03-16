package com.pchome.enumerate.ad;

public enum EnumAdSearchPriceType {
	
	SUGGEST_PRICE(1,"系統建議出價"),
	MYSELF_PRICE(2,"自行設定出價");

	
	private final int typeId;
	private final String desc;
	
	private EnumAdSearchPriceType(int typeId, String desc) {
		this.typeId = typeId;
		this.desc = desc;
	}

	public int getTypeId() {
		return typeId;
	}

	public String getDesc() {
		return desc;
	}
	
}
