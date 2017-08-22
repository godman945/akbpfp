package com.pchome.enumerate.ad;

public enum EnumAdPriceType {
	
	AD_PRICE_CPV(0,"單次收視出價-CPV","CPV",0.5),
	AD_PRICE_CPM(1,"千次曝光出價-CPM","CPM",65),
	AD_PRICE_CPC(2,"單次點擊出價-CPC","CPC",3);
	
	private final int type;
	private final String typeName;
	private final String dbTypeName;
	private final double price;
	
	private EnumAdPriceType(int type,String typeName,String dbTypeName, double price){
		this.type = type;
		this.typeName = typeName;
		this.dbTypeName = dbTypeName;
		this.price = price;
	}
	
	public int getType() {
		return type;
	}


	public String getDbTypeName() {
		return dbTypeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public double getPrice() {
		return price;
	}



}
