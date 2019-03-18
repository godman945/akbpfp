package com.pchome.akbpfp.db.vo.user;

public class UserVO {
	
	private String userId = "";
	private String memberId = "";
	private String customerInfoId = "";
	private int privilegeId = 0;
	private String userName = "";
	private String userEmail = "";
	private String status = "";
	private String createDate = "";
	private String loginDate = "";
	private String inviteFail = "N";
	private boolean otherActivate = false;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCustomerInfoId() {
		return customerInfoId;
	}
	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}
	public int getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(int privilegeId) {
		this.privilegeId = privilegeId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}
	public String getInviteFail() {
		return inviteFail;
	}
	public void setInviteFail(String inviteFail) {
		this.inviteFail = inviteFail;
	}
	public boolean isOtherActivate() {
		return otherActivate;
	}
	public void setOtherActivate(boolean otherActivate) {
		this.otherActivate = otherActivate;
	}


	
}
