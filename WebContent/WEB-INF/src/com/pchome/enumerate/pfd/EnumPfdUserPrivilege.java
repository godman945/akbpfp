package com.pchome.enumerate.pfd;

/**
 * 帳號權限
 * 需與 管理後台 EnumPfdPrivilege 同步 
 */
public enum EnumPfdUserPrivilege {

	ROOT_USER(0, "總管理者", "全部"),
	ACCOUNT_MANAGER(1, "帳戶管理者", "全部"),
	REPORT_MANAGER(2, "行政管理者", "總覽、經銷商帳戶管理、廣告客戶查詢、開立廣告帳戶、加值、報表管理、帳單管理"),
	BILL_MANAGER(3, "帳務管理者", "總覽、報表管理、帳單管理"),
	SALES_MANAGER(4, "業務管理者", "總覽、廣告客戶查詢、廣告帳戶管理、報表管理");
	
	private final Integer privilege;
	private final String chName;
	private final String desc;
	
	private EnumPfdUserPrivilege(Integer privilege, String chName, String desc){
		this.privilege = privilege;
		this.chName = chName;
		this.desc = desc;
	}

	public Integer getPrivilege() {
		return privilege;
	}
	
	public String getChName() {
		return chName;
	}

	public String getDesc() {
		return desc;
	}
}
