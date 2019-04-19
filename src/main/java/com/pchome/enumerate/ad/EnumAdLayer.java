package com.pchome.enumerate.ad;

public enum EnumAdLayer {
	
	AD_ACTION("adAction"),			// 統計每項廣告成本
	AD_GROUP("adGroup"),			// 統計每項分類成本
	AD_KEYWORD("adKeyword"),		// 統計每項關鍵字成本
	AD_AD("adAd");					// 統計每項廣告明細成本
	
	private final String type;
	
	private EnumAdLayer(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	
}
