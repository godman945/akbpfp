package com.pchome.enumerate.sequence;

public enum EnumSequenceTableName {
	// 帳戶編號 id
	ACCOUNT("pfp_customerInfo","AC"),
	// 免費活動 id
	ADM_FREE_ACTION("adm_free_action","FRA"),
	// 加值金記錄 id
	ADM_RECOGNIZE_RECORD("adm_recognize_record","RER"), 
	// 訂單編號 table ID
	ORDER("pfp_order","OR"), 
	// 使用者 ID
	USER("pfp_user","US"), 
	// 廣告     
	PFP_AD("pfp_ad","ad"), 	
	// 廣告活動
	PFP_AD_ACTION("pfp_ad_action","aa"), 
	// 廣告明細
	PFP_AD_DETAIL("pfp_ad_detail","add"), 
	// 排除關鍵字廣告
	PFP_AD_EXCLUDE_KEYWORD("pfp_ad_exclude_keyword","aek"), 
	PFP_AD_GROUP("pfp_ad_group","ag"), // 廣告分類
	PFP_AD_KEYWORD("pfp_ad_keyword","ak"), // 關鍵字廣告
	// 關鍵字廣告瀏覽點擊數
	PFP_AD_KEYWORD_PVCLK("pfp_ad_keyword_pvclk","akpv"), 
	// 廣告瀏覽點擊數
	PFP_AD_PVCLK("pfp_ad_pvclk","adpv"), 
	
	PFP_AD_SPECIFIC_WEBSITE("pfp_ad_specific_website","aasw"),
	// 影片序號
	PFP_AD_VIDEO_SOURCE("pfp_ad_video_source","adv"),
	// 商品目錄群組序號
	PFP_CATALOG_GROUP("pfp_catalog_group","PCG"),
	// 商品LOGO
	PFP_CATALOG_LOG_SEQ("pfp_catalog_log_seq","PCL"),
	
	/**
	 * 商品目錄ID
	 */
	PFP_CATALOG("catalog_seq", "PC"),
	
	/**
	 * 商品目錄更新紀錄id
	 */
	PFP_CATALOG_UPLOAD_LOG("catalog_upload_log_seq", "PCUL"),
	
	// 再行銷追蹤代號
	PFP_CODE_TRACKING("pfp_code_tracking","TAC");
	
	
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
