package com.pchome.enumerate.cookie;

public enum EnumCookieConstants {

	COOKIE_PFP_SECRET_KEY("pchomeakbpfp2012"),							// PFP cookie secret key
	COOKIE_PFD_SECRET_KEY("pchomeakbpfd2013"),							// PFD cookie secret key
	COOKIE_BILLING_SECRET_KEY("billingpfd2014"),						// billing cookie secret key
	COOKIE_BILLING_CUSTOMERINFO_SECRET_KEY("billingcustomerinfo0804"),	// billing cookie secret key
	COOKIE_SEPARATOR("&&&"),											// cookie separator
	COOKIE_MEMBER_ID_PCHOME("id_pchome"),								// member id_pchome cookie	
	COOKIE_MEMBER_DNA_PCHOME("dna_pchome"),								// member dna_pchome cookie
	COOKIE_AKBPFP_USER("akb_pfp_user"),									// akbpfp user cookie	
	COOKIE_AKBPFD_USER("akb_pfd_user"),									// akbpfd user cookie
	COOKIE_AKBPFP_APPLY("akb_pfp_apply"),								// akbpfp apply cookie (沒有用暫時保留)
	COOKIE_PCHOME_DOMAIN(".pchome.com.tw"),								// cookie domain	
	COOKIE_UTF8_CODE("utf-8"),											// 讀寫 cookie 所用的編碼
	COOKIE_USING_CODE("ISO-8859-1"),									// 讀寫 cookie 所用的編碼
	COOKIE_BILLING_BILLING_PCHOME("billing_pchome"),					// 金流處理訂單和登入帳戶 Id 不一樣cookie
	COOKIE_BILLING_CUSTOMERINFO("billing_customer_info"),				// 金流判斷總管理者 cookie
	COOKIE_CHOOSE_DATE("choose_date");									// PFP date cookie
	
	public static final int COOKIE_MAX_AGE = 60*60*24*365*30;	//cookie 可用時間
	public static final int COOKIE_ONE_HOUR_AGE = 60*60;		//cookie 可用時間
	public static final int COOKIE_TWELVE_HOUR_AGE = 60*60*12;	//cookie 可用時間
	
	private final String value;
	
	private EnumCookieConstants(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	
}
