package com.pchome.enumerate.catalog.prodGroup;

public enum EnumProdGroupField {

	catalog_prod_ec_seq("catalog_prod_ec_seq", "string","EC商品ID"),
	prod_category("prod_category", "string","商品組合篩選分類"),
	prod_stock_status("prod_stock_status", "string","商品庫存"),
	prod_price("prod_price", "int","商品價格"),
	prod_discount_price("prod_discount_price", "int","商品特價");
	
	
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
