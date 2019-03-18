package com.pchome.enumerate.pfd;

/**
 * pfd 要和後台同步一致
 */
public enum EnumContractStatus {

	CLOSE("0","作廢"),
	USE("1","生效中"),
	WAIT("2","尚未生效"),
	OVERTIME("3","過期失效");
	
	private final String statusId;
	private final String statusName;
	
	private EnumContractStatus(String statusId, String statusName) {
		this.statusId = statusId;
		this.statusName = statusName;
	}
	
	public String getStatusId() {
		return statusId;
	}
	public String getStatusName() {
		return statusName;
	}
	
	
}
