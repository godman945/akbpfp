package com.pchome.enumerate.ad;

public enum EnumAdDetail {
	
	img("廣告圖片", 999),
	title("廣告標題", 17),
	content("廣告內容", 38),
	realUrl("廣告連結網址", 1024),
	showUrl("廣告顯示網址", 35);
	
	private final String AdDetailName;
	private final int maxlength;
	
	private EnumAdDetail(String AdDetailName, int maxlength){
		this.AdDetailName = AdDetailName;
		this.maxlength = maxlength;
	}

	public String getAdDetailName() {
		return AdDetailName;
	}

	public int getmaxlength() {
		return maxlength;
	}
}
