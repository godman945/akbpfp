package com.pchome.enumerate.catalog;

public enum EnumProdGroup {

	Ec_Prod_Group("Ec_Prod_Group", "1","一般購物類"),
	Booking_Prod_Group("Booking_Prod_Group", "2","訂房住宿類"),
	Traffic_Prod_Group("Traffic_Prod_Group", "3","交通航班類"),
	Rent_Prod_Group("Rent_Prod_Group", "4","房產租售類");
	
	private final String key;
	private final String catalogType;
	private final String desc;

	private EnumProdGroup(String key, String catalogType, String desc) {
		this.key = key;
		this.catalogType = catalogType;
		this.desc = desc;
	}

	public String getKey() {
		return key;
	}

	public String getCatalogType() {
		return catalogType;
	}

	public String getDesc() {
		return desc;
	}
	
}
