package com.pchome.enumerate.catalog;

public enum EnumProdGroupList {
	
	E001("E001","沒有此商品組合ID的目錄"),
	E002("E002","此商品組合ID的目錄型態不正確"),
	E003("E003","此商品組合ID沒有篩選條件"),
	E004("E004","此商品組合ID沒有清單資料"),
	S001("S001","撈取此商品組合ID成功");
	
	private final String status;
	private final String desc;
	
	private EnumProdGroupList(String status, String desc) {
		this.status = status;
		this.desc = desc;
	}

	public String getStatus() {
		return status;
	}

	public String getDesc() {
		return desc;
	}
	
	
}
