package com.pchome.rmi.accesslog;

public enum EnumAccesslogChannel {

	MEMBER("member"),   //會員中心
	PFP("pfp"),         //廣告刊登前台
	ADM("adm"),         //後台
	BILLING("billing"), //金流中心
	PFD("pfd"),         //經銷商前台
	PFB("pfb");         //聯播網

	private final String channel;

	private EnumAccesslogChannel(String channel) {
		this.channel = channel;
	}

	public String getChannel() {
		return channel;
	}
}
