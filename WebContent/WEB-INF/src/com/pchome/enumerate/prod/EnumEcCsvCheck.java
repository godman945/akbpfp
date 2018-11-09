package com.pchome.enumerate.prod;

public enum EnumEcCsvCheck {
	
	EC_TITLE_ID("id*",true),
	EC_TITLE_NAME("商品名稱*",true),
	EC_TITLE_DISCOUNT_PRICE("促銷價*",true),
	EC_TITLE_STOCK("商品供應情況*",true),
	EC_TITLE_USE("商品使用狀況*",true),
	EC_TITLE_IMG_URL("廣告圖像網址*",true),
	EC_TITLE_URL("連結網址*",true),
	EC_TITLE_PRICE("原價",false),
	EC_TITLE_CATEGORY("商品類別",false);
	
	
	private final String titleName;
	private final boolean needFlag;
	
	private EnumEcCsvCheck(String titleName,boolean needFlag){
		this.titleName = titleName;
		this.needFlag = needFlag;
	}

	public String getTitleName() {
		return titleName;
	}

	public boolean isNeedFlag() {
		return needFlag;
	}

}
