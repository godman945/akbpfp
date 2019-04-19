package com.pchome.enumerate.ad;

public enum EnumAdCountry {
	
	ALL("NULL","全球 (預設)"),
	TAIWAN("Taiwan","臺灣");
	
	private final String countryType;
	private final String countryName;
	
	private EnumAdCountry(String countryType, String countryName){
		this.countryType = countryType;
		this.countryName = countryName;
	}

	public String getCountryType() {
		return countryType;
	}

	public String getCountryName() {
		return countryName;
	}

	
}
