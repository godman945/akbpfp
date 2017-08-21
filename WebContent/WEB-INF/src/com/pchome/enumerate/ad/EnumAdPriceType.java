package com.pchome.enumerate.ad;

public enum EnumAdPriceType {
	
	AD_PRICE_CPV("單次收視出價-CPV",0.5),
	AD_PRICE_CPM("千次曝光出價-CPM",65);
	
	private final String key;
	private final double price;
	
	private EnumAdPriceType(String key, double price){
		this.key = key;
		this.price = price;
	}

	public String getKey() {
		return key;
	}

	public double getPrice() {
		return price;
	}



}
