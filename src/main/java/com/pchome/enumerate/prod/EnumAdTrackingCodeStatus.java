package com.pchome.enumerate.prod;

public enum EnumAdTrackingCodeStatus {
	
	Close("0", "關閉"),
	Open("1", "開啟"),
	Delete("2", "刪除");
	
	private final String statusId;
	private final String statusDesc;
	
	private EnumAdTrackingCodeStatus(String statusId, String statusDesc){
		this.statusId = statusId;
		this.statusDesc = statusDesc;
	}

	public String getStatusId() {
		return statusId;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

}
