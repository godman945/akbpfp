package com.pchome.akbpfp.db.pojo;

// Generated 2016/7/14 �W�� 11:01:55 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * PfdUserAdAccountRef generated by hbm2java
 */
@Entity
@Table(name = "pfd_user_ad_account_ref", catalog = "akb")
public class PfdUserAdAccountRef implements java.io.Serializable {

	private Integer refId;
	private PfpUser pfpUser;
	private PfpCustomerInfo pfpCustomerInfo;
	private PfdUser pfdUser;
	private PfdCustomerInfo pfdCustomerInfo;
	private String pfpPayType;

	public PfdUserAdAccountRef() {
	}

	public PfdUserAdAccountRef(PfpUser pfpUser,
			PfpCustomerInfo pfpCustomerInfo, PfdUser pfdUser,
			PfdCustomerInfo pfdCustomerInfo, String pfpPayType) {
		this.pfpUser = pfpUser;
		this.pfpCustomerInfo = pfpCustomerInfo;
		this.pfdUser = pfdUser;
		this.pfdCustomerInfo = pfdCustomerInfo;
		this.pfpPayType = pfpPayType;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ref_id", unique = true, nullable = false)
	public Integer getRefId() {
		return this.refId;
	}

	public void setRefId(Integer refId) {
		this.refId = refId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pfp_user_id", nullable = false)
	public PfpUser getPfpUser() {
		return this.pfpUser;
	}

	public void setPfpUser(PfpUser pfpUser) {
		this.pfpUser = pfpUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pfp_customer_info_id", nullable = false)
	public PfpCustomerInfo getPfpCustomerInfo() {
		return this.pfpCustomerInfo;
	}

	public void setPfpCustomerInfo(PfpCustomerInfo pfpCustomerInfo) {
		this.pfpCustomerInfo = pfpCustomerInfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pfd_user_id", nullable = false)
	public PfdUser getPfdUser() {
		return this.pfdUser;
	}

	public void setPfdUser(PfdUser pfdUser) {
		this.pfdUser = pfdUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pfd_customer_info_id", nullable = false)
	public PfdCustomerInfo getPfdCustomerInfo() {
		return this.pfdCustomerInfo;
	}

	public void setPfdCustomerInfo(PfdCustomerInfo pfdCustomerInfo) {
		this.pfdCustomerInfo = pfdCustomerInfo;
	}

	@Column(name = "pfp_pay_type", nullable = false, length = 1)
	public String getPfpPayType() {
		return this.pfpPayType;
	}

	public void setPfpPayType(String pfpPayType) {
		this.pfpPayType = pfpPayType;
	}

}
