package com.pchome.akbpfp.db.pojo;

// Generated 2015/12/22 �W�� 10:00:32 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PfpCustomerInfo generated by hbm2java
 */
@Entity
@Table(name = "pfp_customer_info", catalog = "akb")
public class PfpCustomerInfo implements java.io.Serializable {

	private String customerInfoId;
	private String customerInfoTitle;
	private String category;
	private String companyTitle;
	private String registration;
	private String industry;
	private String url;
	private String urlAddress;
	private String status;
	private float remain;
	private float laterRemain;
	private String memberId;
	private String telephone;
	private String mobile;
	private String zip;
	private String address;
	private String payType;
	private Date activateDate;
	private float totalAddMoney;
	private float totalSpend;
	private float totalRetrieve;
	private float totalLaterAddMoney;
	private float totalLaterSpend;
	private float totalLaterRetrieve;
	private String recognize;
	private String authorizedPage;
	private String black;
	private String blackReason;
	private Date blackTime;
	private Date createDate;
	private Date updateDate;
	private Set<PfpAdClick> pfpAdClicks = new HashSet<PfpAdClick>(0);
	private Set<PfpTransDetail> pfpTransDetails = new HashSet<PfpTransDetail>(0);
	private Set<AdmRecognizeRecord> admRecognizeRecords = new HashSet<AdmRecognizeRecord>(
			0);
	private Set<PfpOrder> pfpOrders = new HashSet<PfpOrder>(0);
	private Set<PfdUserAdAccountRef> pfdUserAdAccountRefs = new HashSet<PfdUserAdAccountRef>(
			0);
	private Set<PfpAdAction> pfpAdActions = new HashSet<PfpAdAction>(0);
	private Set<PfpUser> pfpUsers = new HashSet<PfpUser>(0);

	public PfpCustomerInfo() {
	}

	public PfpCustomerInfo(String customerInfoId, String customerInfoTitle,
			String category, String industry, String urlAddress, String status,
			float remain, float laterRemain, String memberId, String telephone,
			String mobile, String zip, String address, String payType,
			float totalAddMoney, float totalSpend, float totalRetrieve,
			float totalLaterAddMoney, float totalLaterSpend,
			float totalLaterRetrieve, String recognize, String authorizedPage,
			Date createDate, Date updateDate) {
		this.customerInfoId = customerInfoId;
		this.customerInfoTitle = customerInfoTitle;
		this.category = category;
		this.industry = industry;
		this.urlAddress = urlAddress;
		this.status = status;
		this.remain = remain;
		this.laterRemain = laterRemain;
		this.memberId = memberId;
		this.telephone = telephone;
		this.mobile = mobile;
		this.zip = zip;
		this.address = address;
		this.payType = payType;
		this.totalAddMoney = totalAddMoney;
		this.totalSpend = totalSpend;
		this.totalRetrieve = totalRetrieve;
		this.totalLaterAddMoney = totalLaterAddMoney;
		this.totalLaterSpend = totalLaterSpend;
		this.totalLaterRetrieve = totalLaterRetrieve;
		this.recognize = recognize;
		this.authorizedPage = authorizedPage;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public PfpCustomerInfo(String customerInfoId, String customerInfoTitle,
			String category, String companyTitle, String registration,
			String industry, String url, String urlAddress, String status,
			float remain, float laterRemain, String memberId, String telephone,
			String mobile, String zip, String address, String payType,
			Date activateDate, float totalAddMoney, float totalSpend,
			float totalRetrieve, float totalLaterAddMoney,
			float totalLaterSpend, float totalLaterRetrieve, String recognize,
			String authorizedPage, String black, String blackReason,
			Date blackTime, Date createDate, Date updateDate,
			Set<PfpAdClick> pfpAdClicks, Set<PfpTransDetail> pfpTransDetails,
			Set<AdmRecognizeRecord> admRecognizeRecords,
			Set<PfpOrder> pfpOrders,
			Set<PfdUserAdAccountRef> pfdUserAdAccountRefs,
			Set<PfpAdAction> pfpAdActions, Set<PfpUser> pfpUsers) {
		this.customerInfoId = customerInfoId;
		this.customerInfoTitle = customerInfoTitle;
		this.category = category;
		this.companyTitle = companyTitle;
		this.registration = registration;
		this.industry = industry;
		this.url = url;
		this.urlAddress = urlAddress;
		this.status = status;
		this.remain = remain;
		this.laterRemain = laterRemain;
		this.memberId = memberId;
		this.telephone = telephone;
		this.mobile = mobile;
		this.zip = zip;
		this.address = address;
		this.payType = payType;
		this.activateDate = activateDate;
		this.totalAddMoney = totalAddMoney;
		this.totalSpend = totalSpend;
		this.totalRetrieve = totalRetrieve;
		this.totalLaterAddMoney = totalLaterAddMoney;
		this.totalLaterSpend = totalLaterSpend;
		this.totalLaterRetrieve = totalLaterRetrieve;
		this.recognize = recognize;
		this.authorizedPage = authorizedPage;
		this.black = black;
		this.blackReason = blackReason;
		this.blackTime = blackTime;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.pfpAdClicks = pfpAdClicks;
		this.pfpTransDetails = pfpTransDetails;
		this.admRecognizeRecords = admRecognizeRecords;
		this.pfpOrders = pfpOrders;
		this.pfdUserAdAccountRefs = pfdUserAdAccountRefs;
		this.pfpAdActions = pfpAdActions;
		this.pfpUsers = pfpUsers;
	}

	@Id
	@Column(name = "customer_info_id", unique = true, nullable = false, length = 20)
	public String getCustomerInfoId() {
		return this.customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	@Column(name = "customer_info_title", nullable = false, length = 20)
	public String getCustomerInfoTitle() {
		return this.customerInfoTitle;
	}

	public void setCustomerInfoTitle(String customerInfoTitle) {
		this.customerInfoTitle = customerInfoTitle;
	}

	@Column(name = "category", nullable = false, length = 1)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "company_title", length = 50)
	public String getCompanyTitle() {
		return this.companyTitle;
	}

	public void setCompanyTitle(String companyTitle) {
		this.companyTitle = companyTitle;
	}

	@Column(name = "registration", length = 8)
	public String getRegistration() {
		return this.registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	@Column(name = "industry", nullable = false, length = 10)
	public String getIndustry() {
		return this.industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	@Column(name = "url", length = 1)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "url_address", nullable = false, length = 150)
	public String getUrlAddress() {
		return this.urlAddress;
	}

	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}

	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "remain", nullable = false, precision = 10)
	public float getRemain() {
		return this.remain;
	}

	public void setRemain(float remain) {
		this.remain = remain;
	}

	@Column(name = "later_remain", nullable = false, precision = 10)
	public float getLaterRemain() {
		return this.laterRemain;
	}

	public void setLaterRemain(float laterRemain) {
		this.laterRemain = laterRemain;
	}

	@Column(name = "member_id", nullable = false, length = 20)
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Column(name = "telephone", nullable = false, length = 20)
	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@Column(name = "mobile", nullable = false, length = 10)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "zip", nullable = false, length = 5)
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "address", nullable = false, length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "pay_type", nullable = false, length = 1)
	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "activate_date", length = 19)
	public Date getActivateDate() {
		return this.activateDate;
	}

	public void setActivateDate(Date activateDate) {
		this.activateDate = activateDate;
	}

	@Column(name = "total_add_money", nullable = false, precision = 10)
	public float getTotalAddMoney() {
		return this.totalAddMoney;
	}

	public void setTotalAddMoney(float totalAddMoney) {
		this.totalAddMoney = totalAddMoney;
	}

	@Column(name = "total_spend", nullable = false, precision = 10)
	public float getTotalSpend() {
		return this.totalSpend;
	}

	public void setTotalSpend(float totalSpend) {
		this.totalSpend = totalSpend;
	}

	@Column(name = "total_retrieve", nullable = false, precision = 10)
	public float getTotalRetrieve() {
		return this.totalRetrieve;
	}

	public void setTotalRetrieve(float totalRetrieve) {
		this.totalRetrieve = totalRetrieve;
	}

	@Column(name = "total_later_add_money", nullable = false, precision = 10)
	public float getTotalLaterAddMoney() {
		return this.totalLaterAddMoney;
	}

	public void setTotalLaterAddMoney(float totalLaterAddMoney) {
		this.totalLaterAddMoney = totalLaterAddMoney;
	}

	@Column(name = "total_later_spend", nullable = false, precision = 10)
	public float getTotalLaterSpend() {
		return this.totalLaterSpend;
	}

	public void setTotalLaterSpend(float totalLaterSpend) {
		this.totalLaterSpend = totalLaterSpend;
	}

	@Column(name = "total_later_retrieve", nullable = false, precision = 10)
	public float getTotalLaterRetrieve() {
		return this.totalLaterRetrieve;
	}

	public void setTotalLaterRetrieve(float totalLaterRetrieve) {
		this.totalLaterRetrieve = totalLaterRetrieve;
	}

	@Column(name = "recognize", nullable = false, length = 1)
	public String getRecognize() {
		return this.recognize;
	}

	public void setRecognize(String recognize) {
		this.recognize = recognize;
	}

	@Column(name = "authorized_page", nullable = false, length = 1)
	public String getAuthorizedPage() {
		return this.authorizedPage;
	}

	public void setAuthorizedPage(String authorizedPage) {
		this.authorizedPage = authorizedPage;
	}

	@Column(name = "black", length = 1)
	public String getBlack() {
		return this.black;
	}

	public void setBlack(String black) {
		this.black = black;
	}

	@Column(name = "black_reason", length = 20)
	public String getBlackReason() {
		return this.blackReason;
	}

	public void setBlackReason(String blackReason) {
		this.blackReason = blackReason;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "black_time", length = 19)
	public Date getBlackTime() {
		return this.blackTime;
	}

	public void setBlackTime(Date blackTime) {
		this.blackTime = blackTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", nullable = false, length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpCustomerInfo")
	public Set<PfpAdClick> getPfpAdClicks() {
		return this.pfpAdClicks;
	}

	public void setPfpAdClicks(Set<PfpAdClick> pfpAdClicks) {
		this.pfpAdClicks = pfpAdClicks;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpCustomerInfo")
	public Set<PfpTransDetail> getPfpTransDetails() {
		return this.pfpTransDetails;
	}

	public void setPfpTransDetails(Set<PfpTransDetail> pfpTransDetails) {
		this.pfpTransDetails = pfpTransDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpCustomerInfo")
	public Set<AdmRecognizeRecord> getAdmRecognizeRecords() {
		return this.admRecognizeRecords;
	}

	public void setAdmRecognizeRecords(
			Set<AdmRecognizeRecord> admRecognizeRecords) {
		this.admRecognizeRecords = admRecognizeRecords;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpCustomerInfo")
	public Set<PfpOrder> getPfpOrders() {
		return this.pfpOrders;
	}

	public void setPfpOrders(Set<PfpOrder> pfpOrders) {
		this.pfpOrders = pfpOrders;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpCustomerInfo")
	public Set<PfdUserAdAccountRef> getPfdUserAdAccountRefs() {
		return this.pfdUserAdAccountRefs;
	}

	public void setPfdUserAdAccountRefs(
			Set<PfdUserAdAccountRef> pfdUserAdAccountRefs) {
		this.pfdUserAdAccountRefs = pfdUserAdAccountRefs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpCustomerInfo")
	public Set<PfpAdAction> getPfpAdActions() {
		return this.pfpAdActions;
	}

	public void setPfpAdActions(Set<PfpAdAction> pfpAdActions) {
		this.pfpAdActions = pfpAdActions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpCustomerInfo")
	public Set<PfpUser> getPfpUsers() {
		return this.pfpUsers;
	}

	public void setPfpUsers(Set<PfpUser> pfpUsers) {
		this.pfpUsers = pfpUsers;
	}

}
