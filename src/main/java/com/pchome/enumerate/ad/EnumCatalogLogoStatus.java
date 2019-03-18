package com.pchome.enumerate.ad;

public enum EnumCatalogLogoStatus {
	LOGO_STATUS_NOVERIFY("0","審核中"),
	LOGO_STATUS_APPROVE("1","審核成功");
	
	private final String status;
	private final String type;
	
	private EnumCatalogLogoStatus(String status, String type) {
		this.status = status;
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public String getType() {
		return type;
	}
	
}
