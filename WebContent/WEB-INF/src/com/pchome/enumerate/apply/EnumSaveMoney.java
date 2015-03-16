package com.pchome.enumerate.apply;

public enum EnumSaveMoney {

	Default(1000,5,500,0);			// 預設
	
	private final float price;		// 儲值金額
	private final float taxPercent;	// 扣稅百分比
	private final float min;		// 最少儲值金額
	private final float taxMoney;	// 扣稅金額

	private EnumSaveMoney(float price, float taxPercent, float min, float taxMoney) {
		this.price = price;
		this.taxPercent = taxPercent;
		this.min = min;
		this.taxMoney = price * (taxPercent / 100);
	}

	public float getPrice() {
		return price;
	}

	public float getTaxPercent() {
		return taxPercent;
	}

	public float getMin() {
		return min;
	}

	public float getTaxMoney() {
		return taxMoney;
	}
	
}
