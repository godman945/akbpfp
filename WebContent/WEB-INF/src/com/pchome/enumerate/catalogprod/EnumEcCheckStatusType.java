package com.pchome.enumerate.catalogprod;

public enum EnumEcCheckStatusType {
	Unreviewed("0","審核中"),		
	Reviewed_Passed("1","審核通過"),
	Reject("2","已拒絕");
	
	private final String type;
	private final String chName;
	
	private EnumEcCheckStatusType(String type, String chName) {
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
