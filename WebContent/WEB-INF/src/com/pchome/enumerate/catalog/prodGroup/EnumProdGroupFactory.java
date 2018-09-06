package com.pchome.enumerate.catalog.prodGroup;

public enum EnumProdGroupFactory {

	ecProdGroup("EC_PROD_GROUP", "一般購物類", "1"), 
	bookingProdGroup("BOOKING_PROD_GROUP", "訂房住宿類", "2"), 
	trafficProdGroup("TRAFFIC_PROD_GROUP", "交通航班類", "3"), 
	rentProdGroup("RENT_PROD_GROUP", "房產租售類", "4");

	private EnumProdGroupFactory(String catalogName, String catalogDesc, String catalogType) {
		this.catalogName = catalogName;
		this.catalogDesc = catalogDesc;
		this.catalogType = catalogType;
	}

	private String catalogName;
	private String catalogDesc;
	private String catalogType;

	public static EnumProdGroupFactory getCatalogName(String catalogType) {
		switch (catalogType) {
		case "1":
			return ecProdGroup;
		case "2":
			return bookingProdGroup;
		case "3":
			return trafficProdGroup;
		case "4":
			return rentProdGroup;
		default:
			return null;
		}
	}

	public String getCatalogName() {
		return catalogName;
	}

	public String getCatalogDesc() {
		return catalogDesc;
	}

	public String getCatalogType() {
		return catalogType;
	}

}
