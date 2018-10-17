package com.pchome.enumerate.catalogprod;

public enum EnumEcProdGroupFilterContentType {

	catalog_prod_seq_like("catalog_prod_seq_like","ID","like","包含"),
	catalog_prod_seq_notlike("catalog_prod_seq_like_not like","ID","not like,","不包含"),
	
	ec_name_like("ec_name_like","ID","like","包含"),
	ec_name_notlike("ec_name_not like","ID","not like,","不包含"),
	
	ec_price_gt("ec_price_gt", "原價","gt","大於"),
	ec_price_lt("ec_price_lt", "原價","lt","小於"),
	ec_price_eq("ec_price_eq", "原價","eq","等於"),
	ec_price_neq("ec_price_neq", "原價","neq","不等於"),
	
	ec_discount_price_gt("ec_discount_price_gt", "特價","gt","大於"),
	ec_discount_price_lt("ec_discount_price_lt", "特價","lt","小於"),
	ec_discount_price_eq("ec_discount_price_eq", "特價","eq","等於"),
	ec_discount_price_neq("ec_discount_price_neq", "特價","neq","不等於"),
	
	ec_stock_status_eq("ec_stock_status_eq","供應情況","eq","屬於"),
	ec_stock_status_neq("ec_stock_status_neq","供應情況","neq","不屬於"),
	
	ec_use_status_eq("ec_use_status_eq","使用情況","eq","屬於"),
	ec_use_status_neq("ec_use_status_neq","使用情況","neq","不屬於"),
	
	ec_category_eq("ec_category_eq","類別","eq","屬於"),
	ec_category_neq("ec_category_neq","類別","neq","不屬於");
	
	
	private final String fieldCondition;
	private final String fieldDesc;
	private final String condition;
	private final String conditionDesc;
	

	private EnumEcProdGroupFilterContentType(String fieldCondition, String fieldDesc,String condition, String conditionDesc) {
		this.fieldCondition = fieldCondition;
		this.fieldDesc = fieldDesc;
		this.condition = condition;
		this.conditionDesc = conditionDesc;
	}



	public String getFieldCondition() {
		return fieldCondition;
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public String getCondition() {
		return condition;
	}


	public String getConditionDesc() {
		return conditionDesc;
	}
	
}
