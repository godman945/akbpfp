package com.pchome.enumerate.ad;

public enum EnumProdAdDetail {
	PROD_REPORT_NAME("prod_report_name","adp_201809270001","dad_prod_report_name"),
	PROD_LIST("prod_list","adp_201809270001","dad_prod_list"),
	PROD_GROUP("prod_group","adp_201809270001","dad_prod_group"),
	PROD_AD_URL("prod_ad_url","adp_201809270001","dad_prod_ad_url"),
	LOGO_TYPE("logo_type","adp_201809270001","dad_logo_type"),
	LOGO_TXT("logo_txt","adp_201809270001","dad_logo_txt"),
	LOGO_FONT_COLOR("logo_font_color","adp_201809270001","dad_logo_font_color"),
	LOGO_BG_COLOR("logo_bg_color","adp_201809270001","dad_logo_bg_color"),
	BTN_TXT("buybtn_txt","adp_201809270001","dad_buybtn_txt"),
	BTN_FONT_COLOR("buybtn_font_color","adp_201809270001","dad_buybtn_font_color"),
	BTN_BG_COLOR("buybtn_bg_color","adp_201809270001","dad_buybtn_bg_color"),
	DIS_TXT_TYPE("dis_txt_type","adp_201809270001","dad_dis_txt_type"),
	DIS_FONT_COLOR("dis_font_color","adp_201809270001","dad_dis_font_color"),
	DIS_BG_COLOR("dis_bg_color","adp_201809270001","dad_dis_bg_color"),
	PROD_RADIO_LOGO_TYPE("prod_radio_logo_type","adp_201809270001",""),
	PROD_SALE_IMG("logo_sale_img_300x55","adp_201809270001",""),
	PROD_SALE_END_IMG("sale_img_300x250","adp_201809270001",""),
	LOGO_IMG_URL("logo_img_url","adp_201809270001","dad_logo_img_url");
	
	private final String adDetailId;
	private final String adPoolSeq;
	private final String defineAdSeq;
	
	private EnumProdAdDetail(String adDetailId, String adPoolSeq, String defineAdSeq) {
		this.adDetailId = adDetailId;
		this.adPoolSeq = adPoolSeq;
		this.defineAdSeq = defineAdSeq;
	}

	public String getAdDetailId() {
		return adDetailId;
	}

	public String getAdPoolSeq() {
		return adPoolSeq;
	}

	public String getDefineAdSeq() {
		return defineAdSeq;
	}


	
}
