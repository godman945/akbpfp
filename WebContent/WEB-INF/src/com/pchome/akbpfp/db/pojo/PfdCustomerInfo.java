package com.pchome.akbpfp.db.pojo;
// Generated 2018/9/28 �W�� 10:58:33 by Hibernate Tools 3.4.0.CR1

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
 * PfdCustomerInfo generated by hbm2java
 */
@Entity
@Table(name = "pfd_customer_info")
public class PfdCustomerInfo implements java.io.Serializable {

	private String customerInfoId;
	private String status;
	private String companyName;
	private String companyTaxId;
	private String companyOwner;
	private String companyTel;
	private String companyFax;
	private String companyZip;
	private String companyAddr;
	private String companyWebsite;
	private String companyCategory;
	private String contactPerson;
	private String contactTel;
	private String contactMobile;
	private String contactEmail;
	private String financialOfficer;
	private String financialTel;
	private String financialMobile;
	private String financialEmail;
	private String note;
	private String pfpPayType;
	private String pfpAdtypeSelect;
	private int totalQuota;
	private int totalPfpQuota;
	private int totalAdCostQuota;
	private int remainQuota;
	private int balanceQuota;
	private String mixFlag;
	private Date activateDate;
	private Date createDate;
	private Date updateDate;
	private Set<PfdUser> pfdUsers = new HashSet<PfdUser>(0);
	private Set<PfdContract> pfdContracts = new HashSet<PfdContract>(0);
	private Set<PfdUserAdAccountRef> pfdUserAdAccountRefs = new HashSet<PfdUserAdAccountRef>(0);

	public PfdCustomerInfo() {
	}

	public PfdCustomerInfo(String customerInfoId, String status, String companyName, String companyTaxId,
			String companyOwner, String companyTel, String companyZip, String companyAddr, String companyCategory,
			String contactPerson, String contactTel, String contactEmail, int totalQuota, int totalPfpQuota,
			int totalAdCostQuota, int remainQuota, int balanceQuota, Date createDate, Date updateDate) {
		this.customerInfoId = customerInfoId;
		this.status = status;
		this.companyName = companyName;
		this.companyTaxId = companyTaxId;
		this.companyOwner = companyOwner;
		this.companyTel = companyTel;
		this.companyZip = companyZip;
		this.companyAddr = companyAddr;
		this.companyCategory = companyCategory;
		this.contactPerson = contactPerson;
		this.contactTel = contactTel;
		this.contactEmail = contactEmail;
		this.totalQuota = totalQuota;
		this.totalPfpQuota = totalPfpQuota;
		this.totalAdCostQuota = totalAdCostQuota;
		this.remainQuota = remainQuota;
		this.balanceQuota = balanceQuota;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public PfdCustomerInfo(String customerInfoId, String status, String companyName, String companyTaxId,
			String companyOwner, String companyTel, String companyFax, String companyZip, String companyAddr,
			String companyWebsite, String companyCategory, String contactPerson, String contactTel,
			String contactMobile, String contactEmail, String financialOfficer, String financialTel,
			String financialMobile, String financialEmail, String note, String pfpPayType, String pfpAdtypeSelect,
			int totalQuota, int totalPfpQuota, int totalAdCostQuota, int remainQuota, int balanceQuota, String mixFlag,
			Date activateDate, Date createDate, Date updateDate, Set<PfdUser> pfdUsers, Set<PfdContract> pfdContracts,
			Set<PfdUserAdAccountRef> pfdUserAdAccountRefs) {
		this.customerInfoId = customerInfoId;
		this.status = status;
		this.companyName = companyName;
		this.companyTaxId = companyTaxId;
		this.companyOwner = companyOwner;
		this.companyTel = companyTel;
		this.companyFax = companyFax;
		this.companyZip = companyZip;
		this.companyAddr = companyAddr;
		this.companyWebsite = companyWebsite;
		this.companyCategory = companyCategory;
		this.contactPerson = contactPerson;
		this.contactTel = contactTel;
		this.contactMobile = contactMobile;
		this.contactEmail = contactEmail;
		this.financialOfficer = financialOfficer;
		this.financialTel = financialTel;
		this.financialMobile = financialMobile;
		this.financialEmail = financialEmail;
		this.note = note;
		this.pfpPayType = pfpPayType;
		this.pfpAdtypeSelect = pfpAdtypeSelect;
		this.totalQuota = totalQuota;
		this.totalPfpQuota = totalPfpQuota;
		this.totalAdCostQuota = totalAdCostQuota;
		this.remainQuota = remainQuota;
		this.balanceQuota = balanceQuota;
		this.mixFlag = mixFlag;
		this.activateDate = activateDate;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.pfdUsers = pfdUsers;
		this.pfdContracts = pfdContracts;
		this.pfdUserAdAccountRefs = pfdUserAdAccountRefs;
	}

	@Id

	@Column(name = "customer_info_id", unique = true, nullable = false, length = 20)
	public String getCustomerInfoId() {
		return this.customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "company_name", nullable = false, length = 30)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(name = "company_tax_id", nullable = false, length = 8)
	public String getCompanyTaxId() {
		return this.companyTaxId;
	}

	public void setCompanyTaxId(String companyTaxId) {
		this.companyTaxId = companyTaxId;
	}

	@Column(name = "company_owner", nullable = false, length = 20)
	public String getCompanyOwner() {
		return this.companyOwner;
	}

	public void setCompanyOwner(String companyOwner) {
		this.companyOwner = companyOwner;
	}

	@Column(name = "company_tel", nullable = false, length = 20)
	public String getCompanyTel() {
		return this.companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	@Column(name = "company_fax", length = 20)
	public String getCompanyFax() {
		return this.companyFax;
	}

	public void setCompanyFax(String companyFax) {
		this.companyFax = companyFax;
	}

	@Column(name = "company_zip", nullable = false, length = 5)
	public String getCompanyZip() {
		return this.companyZip;
	}

	public void setCompanyZip(String companyZip) {
		this.companyZip = companyZip;
	}

	@Column(name = "company_addr", nullable = false, length = 50)
	public String getCompanyAddr() {
		return this.companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	@Column(name = "company_website")
	public String getCompanyWebsite() {
		return this.companyWebsite;
	}

	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}

	@Column(name = "company_category", nullable = false, length = 1)
	public String getCompanyCategory() {
		return this.companyCategory;
	}

	public void setCompanyCategory(String companyCategory) {
		this.companyCategory = companyCategory;
	}

	@Column(name = "contact_person", nullable = false, length = 20)
	public String getContactPerson() {
		return this.contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	@Column(name = "contact_tel", nullable = false, length = 20)
	public String getContactTel() {
		return this.contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	@Column(name = "contact_mobile", length = 10)
	public String getContactMobile() {
		return this.contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	@Column(name = "contact_email", nullable = false, length = 50)
	public String getContactEmail() {
		return this.contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	@Column(name = "financial_officer", length = 20)
	public String getFinancialOfficer() {
		return this.financialOfficer;
	}

	public void setFinancialOfficer(String financialOfficer) {
		this.financialOfficer = financialOfficer;
	}

	@Column(name = "financial_tel", length = 20)
	public String getFinancialTel() {
		return this.financialTel;
	}

	public void setFinancialTel(String financialTel) {
		this.financialTel = financialTel;
	}

	@Column(name = "financial_mobile", length = 10)
	public String getFinancialMobile() {
		return this.financialMobile;
	}

	public void setFinancialMobile(String financialMobile) {
		this.financialMobile = financialMobile;
	}

	@Column(name = "financial_email", length = 50)
	public String getFinancialEmail() {
		return this.financialEmail;
	}

	public void setFinancialEmail(String financialEmail) {
		this.financialEmail = financialEmail;
	}

	@Column(name = "note", length = 200)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name = "pfp_pay_type", length = 1)
	public String getPfpPayType() {
		return this.pfpPayType;
	}

	public void setPfpPayType(String pfpPayType) {
		this.pfpPayType = pfpPayType;
	}

	@Column(name = "pfp_adtype_select", length = 12)
	public String getPfpAdtypeSelect() {
		return this.pfpAdtypeSelect;
	}

	public void setPfpAdtypeSelect(String pfpAdtypeSelect) {
		this.pfpAdtypeSelect = pfpAdtypeSelect;
	}

	@Column(name = "total_quota", nullable = false)
	public int getTotalQuota() {
		return this.totalQuota;
	}

	public void setTotalQuota(int totalQuota) {
		this.totalQuota = totalQuota;
	}

	@Column(name = "total_pfp_quota", nullable = false)
	public int getTotalPfpQuota() {
		return this.totalPfpQuota;
	}

	public void setTotalPfpQuota(int totalPfpQuota) {
		this.totalPfpQuota = totalPfpQuota;
	}

	@Column(name = "total_ad_cost_quota", nullable = false)
	public int getTotalAdCostQuota() {
		return this.totalAdCostQuota;
	}

	public void setTotalAdCostQuota(int totalAdCostQuota) {
		this.totalAdCostQuota = totalAdCostQuota;
	}

	@Column(name = "remain_quota", nullable = false)
	public int getRemainQuota() {
		return this.remainQuota;
	}

	public void setRemainQuota(int remainQuota) {
		this.remainQuota = remainQuota;
	}

	@Column(name = "balance_quota", nullable = false)
	public int getBalanceQuota() {
		return this.balanceQuota;
	}

	public void setBalanceQuota(int balanceQuota) {
		this.balanceQuota = balanceQuota;
	}

	@Column(name = "mix_flag", length = 1)
	public String getMixFlag() {
		return this.mixFlag;
	}

	public void setMixFlag(String mixFlag) {
		this.mixFlag = mixFlag;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "activate_date", length = 19)
	public Date getActivateDate() {
		return this.activateDate;
	}

	public void setActivateDate(Date activateDate) {
		this.activateDate = activateDate;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfdCustomerInfo")
	public Set<PfdUser> getPfdUsers() {
		return this.pfdUsers;
	}

	public void setPfdUsers(Set<PfdUser> pfdUsers) {
		this.pfdUsers = pfdUsers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfdCustomerInfo")
	public Set<PfdContract> getPfdContracts() {
		return this.pfdContracts;
	}

	public void setPfdContracts(Set<PfdContract> pfdContracts) {
		this.pfdContracts = pfdContracts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfdCustomerInfo")
	public Set<PfdUserAdAccountRef> getPfdUserAdAccountRefs() {
		return this.pfdUserAdAccountRefs;
	}

	public void setPfdUserAdAccountRefs(Set<PfdUserAdAccountRef> pfdUserAdAccountRefs) {
		this.pfdUserAdAccountRefs = pfdUserAdAccountRefs;
	}

}
