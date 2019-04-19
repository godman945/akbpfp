package com.pchome.enumerate.catalog.prodList;

public enum EnumProdListFactory {

	ecProdList("EC_PROD_LIST", "一般購物類", "1"), 
	bookingProdList("BOOKING_PROD_LIST", "訂房住宿類", "2"), 
	trafficProdList("TRAFFIC_PROD_LIST", "交通航班類", "3"), 
	rentProdList("RENT_PROD_LIST", "房產租售類", "4");

	private EnumProdListFactory(String catalogName, String catalogDesc, String catalogType) {
		this.catalogName = catalogName;
		this.catalogDesc = catalogDesc;
		this.catalogType = catalogType;
	}

	private String catalogName;
	private String catalogDesc;
	private String catalogType;

	public static EnumProdListFactory getCatalogName(String catalogType) {
		switch (catalogType) {
		case "1":
			return ecProdList;
		case "2":
			return bookingProdList;
		case "3":
			return trafficProdList;
		case "4":
			return rentProdList;
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
