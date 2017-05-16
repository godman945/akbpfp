package com.pchome.enumerate.sequence;

public enum EnumSequenceTableName {
	
	ACCOUNT("pfp_customerInfo","AC"), // 帳戶編號 id
	ADM_FREE_ACTION("adm_free_action","FRA"), // 免費活動 id
	ADM_RECOGNIZE_RECORD("adm_recognize_record","RER"), // 加值金記錄 id
	ORDER("pfp_order","OR"), // 訂單編號 table ID
	USER("pfp_user","US"), // 使用者 ID
	PFP_AD("pfp_ad","ad"), // 廣告     	
	PFP_AD_ACTION("pfp_ad_action","aa"), // 廣告活動
	PFP_AD_DETAIL("pfp_ad_detail","add"), // 廣告明細
	PFP_AD_EXCLUDE_KEYWORD("pfp_ad_exclude_keyword","aek"), // 排除關鍵字廣告
	PFP_AD_GROUP("pfp_ad_group","ag"), // 廣告分類
	PFP_AD_KEYWORD("pfp_ad_keyword","ak"), // 關鍵字廣告
	PFP_AD_KEYWORD_PVCLK("pfp_ad_keyword_pvclk","akpv"), // 關鍵字廣告瀏覽點擊數
	PFP_AD_PVCLK("pfp_ad_pvclk","adpv"), // 廣告瀏覽點擊數
	PFP_AD_SPECIFIC_WEBSITE("pfp_ad_specific_website","aasw");
	
	private String snoName;
	private String charName;
	
	private EnumSequenceTableName(String snoName,String charName) {
		this.snoName = snoName;
		this.charName=charName;
	}

	public String getSnoName() {
		return snoName;
	}

	public String getCharName() {
		return charName;
	}
}
