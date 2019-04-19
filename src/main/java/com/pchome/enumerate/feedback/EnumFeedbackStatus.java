package com.pchome.enumerate.feedback;

public enum EnumFeedbackStatus {
	
	START("1","開啟"),
	DELETE("0","刪除");
	
	private final String status;
	private final String desc;
	
	private EnumFeedbackStatus(String status, String desc) {
		this.status = status;
		this.desc = desc;
	}

	public String getStatus() {
		return status;
	}

	public String getDesc() {
		return desc;
	}
	
	
}
