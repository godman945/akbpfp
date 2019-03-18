package com.pchome.enumerate.ad;

public enum EnumAdPvLimitPeriod {

	NO_PREIOD_LIMIT("0","不設限制"),
	AD_DAY_LIMIT("D","每天"),
	AD_WEEK_LIMIT("W","每週"),	
	AD_MONTH_LIMIT("M","每月");	
	
	private final String period;
	private final String name;
	
	private EnumAdPvLimitPeriod(String period, String name){
		this.period = period;
		this.name = name;
	}
	
	public String getPeriod() {
		return period;
	}

	public String getName() {
		return name;
	}
	
}
