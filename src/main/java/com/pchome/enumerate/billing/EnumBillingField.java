package com.pchome.enumerate.billing;

public enum EnumBillingField {

	MEM_ID(""),
	TOTAL_PRICE(""),
	USER_NAME(""),
	USER_MAIL(""),
	USER_SEX(""),
	USER_TEL(""),
	USER_MOBILE(""),
	USER_ZIP(""),
	USER_ADDR(""),
	CH_BTN_TEXT("返回服務"),
	CH_URL(""),
	ORD_ST_DATE(""),
	ORD_ED_DATE(""),
	DETAIL(""),
	
	BPD_ID(""),
	CPD_ID(""),
	PD_ATTB("M"),
	PD_QTY("1"),
	PD_UNTPRI(""),
	PD_TOTPRI(""),
	PD_ST_DATE(""),
	PD_ED_DATE(""),
	MONEY(""),
	
	YEAR(""),
	MONTH(""),
	DAY("");
	
	private final String value;
	
	private EnumBillingField(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
