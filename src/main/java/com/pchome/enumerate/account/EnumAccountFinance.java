package com.pchome.enumerate.account;

public enum EnumAccountFinance {
	
	EXPENSE("-"),
	INCOME("+");
	
	private final String type;

	private EnumAccountFinance(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
}
