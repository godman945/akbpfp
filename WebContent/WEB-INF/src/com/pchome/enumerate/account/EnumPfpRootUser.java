package com.pchome.enumerate.account;

/**
 * 管理者權限
 */
public enum EnumPfpRootUser {

	PCHOME_MANAGER("PM"),	// PChome 管理者
	PFD("PFD"),				// 經銷商			
	NO("N");				// 一般使用者 pfp
	
	private final String privilege;

	private EnumPfpRootUser(String privilege) {
		this.privilege = privilege;
	}

	public String getPrivilege() {
		return privilege;
	}
}
