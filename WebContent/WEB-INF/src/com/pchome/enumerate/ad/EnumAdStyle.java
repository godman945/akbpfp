package com.pchome.enumerate.ad;

public enum EnumAdStyle {
	
	TXT("tpro_201306280003", "tad_201303070002", "文字式樣板", "adp_201303070002"),
	TMG("tpro_201306280001", "tad_201303070003", "圖文式樣板", "adp_201303070003");
	
	private final String tproSeq;
	private final String tadSeq;
	private final String tadName;
	private final String adPoolSeq;
	
	private EnumAdStyle(String tproSeq, String tadSeq, String tadName, String adPoolSeq){
		this.tproSeq = tproSeq;
		this.tadSeq = tadSeq;
		this.tadName = tadName;
		this.adPoolSeq = adPoolSeq;
	}

	public String getTproSeq() {
		return tproSeq;
	}

	public String getTadSeq() {
		return tadSeq;
	}

	public String getTadName() {
		return tadName;
	}

	public String getAdPoolSeq() {
		return adPoolSeq;
	}
}
