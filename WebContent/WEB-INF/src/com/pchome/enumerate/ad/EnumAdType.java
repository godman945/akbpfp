package com.pchome.enumerate.ad;

public enum EnumAdType {
	
	AD_CHANNEL(2,"聯播網廣告","聯播網廣告","PChome的合作網站聯播網"),		// 內容廣告
	AD_ALL(0,"全部","搜尋廣告+聯播網廣告","觸及廣告族群最廣泛"),			// 全部廣告
	AD_SEARCH(1,"搜尋廣告","搜尋廣告","PChome找東西搜尋和搜尋夥伴");	// 搜尋廣告
	
	private final int type;
	private final String chName;
	private final String typeName;
	private final String explanation;
	
	private EnumAdType(int type, String chName, String typeName, String explanation) {
		this.type = type;
		this.chName = chName;
		this.typeName = typeName;
		this.explanation = explanation;
	}

	public int getType() {
		return type;
	}

	public String getChName() {
		return chName;
	}

	public String getTypeName() {
		return typeName;
	}

	public String getExplanation() {
		return explanation;
	}

	
}
