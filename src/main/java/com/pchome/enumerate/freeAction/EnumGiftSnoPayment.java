package com.pchome.enumerate.freeAction;

/**
 * 活動序號付款狀態
 */
public enum EnumGiftSnoPayment {

	YES("Y","需要介接金流"),
	NO("N","不需要介接金流");
	
	private final String status;
	private final String chName;
	
	private EnumGiftSnoPayment(String status, String chName) {
		this.status = status;
		this.chName = chName;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getChName() {
		return chName;
	}

}
