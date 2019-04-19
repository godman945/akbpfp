package com.pchome.akbpfp.db.vo.account;

import com.pchome.akbpfp.db.vo.member.MemberVO;

public class AccountVO {
	
	private String customerInfoId;
	private String memberId;
	private String customerInfoTitle;
	private String category;
	private String companyTitle;
	private String registration;	
	private String industry;
	private String urlYN;
	private String urlAddress;
	private String customerInfoStatus;
	private String createDate;		
	private MemberVO memberVO;
	private float remain;
	private float addMoney;
	private float addTax;	
	private float payMoney;		
	private String orderId;
	private String orderStatus;
	private String telephone;
	private String mobile;
	private String zip;
	private String address;
	private String authorizedPage;
	private String giftSno;				// 贈送序號
	private float giftMoney;			// 序號金額
	//private PfpAdGiftVO pfpAdGiftVO;
	
	public String getCustomerInfoId() {
		return customerInfoId;
	}
	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getCustomerInfoTitle() {
		return customerInfoTitle;
	}
	public void setCustomerInfoTitle(String customerInfoTitle) {
		this.customerInfoTitle = customerInfoTitle;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCompanyTitle() {
		return companyTitle;
	}
	public void setCompanyTitle(String companyTitle) {
		this.companyTitle = companyTitle;
	}
	public String getRegistration() {
		return registration;
	}
	public void setRegistration(String registration) {
		this.registration = registration;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getUrlYN() {
		return urlYN;
	}
	public void setUrlYN(String urlYN) {
		this.urlYN = urlYN;
	}
	public String getUrlAddress() {
		return urlAddress;
	}
	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}
	public String getCustomerInfoStatus() {
		return customerInfoStatus;
	}
	public void setCustomerInfoStatus(String customerInfoStatus) {
		this.customerInfoStatus = customerInfoStatus;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public MemberVO getMemberVO() {
		return memberVO;
	}
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	public float getRemain() {
		return remain;
	}
	public void setRemain(float remain) {
		this.remain = remain;
	}
	public float getAddMoney() {
		return addMoney;
	}
	public void setAddMoney(float addMoney) {
		this.addMoney = addMoney;
	}
	public float getAddTax() {
		return addTax;
	}
	public void setAddTax(float addTax) {
		this.addTax = addTax;
	}
	public float getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(float payMoney) {
		this.payMoney = payMoney;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAuthorizedPage() {
		return authorizedPage;
	}
	public void setAuthorizedPage(String authorizedPage) {
		this.authorizedPage = authorizedPage;
	}
	public String getGiftSno() {
		return giftSno;
	}
	public void setGiftSno(String giftSno) {
		this.giftSno = giftSno;
	}
	public float getGiftMoney() {
		return giftMoney;
	}
	public void setGiftMoney(float giftMoney) {
		this.giftMoney = giftMoney;
	}

	
	
//	public PfpAdGiftVO getPfpAdGiftVO() {
//		return pfpAdGiftVO;
//	}
//	public void setPfpAdGiftVO(PfpAdGiftVO pfpAdGiftVO) {
//		this.pfpAdGiftVO = pfpAdGiftVO;
//	}
	
}
