package com.pchome.enumerate.recognize;

public enum EnumRecognizeStatus {

	NO("N"),
	YES("Y");
	
	private final String status;

	private EnumRecognizeStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
	
}
