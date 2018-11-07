package com.pchome.enumerate.catalogprod;

public enum EnumCatalogGroupDeleteStatusType {
	NotDeleted("0","未刪除"),		
	Deleted("1","已刪除");
	
	private final String type;
	private final String chName;
	
	private EnumCatalogGroupDeleteStatusType(String type, String chName) {
		this.type = type;
		this.chName = chName;
	}

	public String getType() {
		return type;
	}

	public String getChName() {
		return chName;
	}

}
