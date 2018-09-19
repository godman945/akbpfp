package com.pchome.enumerate.catalog.prodGroup;

public enum EnumProdGroupField {

	catalog_prod_seq("catalog_prod_seq", "string","EC商品ID"),
	ec_category("ec_category", "string","商品組合篩選分類"),
	ec_stock_status("ec_stock_status", "string","商品庫存"),
	ec_use_status("ec_use_status", "string","商品使用狀態"),
	ec_price("ec_price", "int","商品價格"),
	ec_discount_price("ec_discount_price", "int","商品特價");
	
	
	private final String field;
	private final String fieldType;
	private final String fieldDesc;
	

	private EnumProdGroupField(String field, String fieldType, String fieldDesc) {
		this.field = field;
		this.fieldType = fieldType;
		this.fieldDesc = fieldDesc;
	}

	public String getField() {
		return field;
	}

	public String getFieldType() {
		return fieldType;
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

}
