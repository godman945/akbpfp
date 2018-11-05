package com.pchome.enumerate.ad;

public enum EnumCatalogLogoStatus {
	LOGO_STATUS_NOVERIFY(0,"審核中"),
	LOGO_STATUS_APPROVE(1,"審核成功");
	
	private final int status;
	private final String type;
	
	private EnumCatalogLogoStatus(int status, String type) {
		this.status = status;
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public String getType() {
		return type;
	}




	
}
