package com.pchome.enumerate.thread;

public enum EnumAdThreadType {
   	AD_VIEW_VIDEO_COUNT("QUERY_AD_VIEW_VIDEO_COUNT","查詢影音廣告明細"),
   	AD_VIEW_VIDEO_DETAIL("QUERY_AD_VIEW_VIDEO_DETAIL","查詢影音廣告總計"),
   	AD_VIDEO_PLAY_URL("AD_VIDEO_PLAY_URL","取得youtube影音播放網址");
    	
	private final String type;
	private final String content;
	
	private EnumAdThreadType(String type, String content){
		this.type = type;
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public String getContent() {
		return content;
	}




}

