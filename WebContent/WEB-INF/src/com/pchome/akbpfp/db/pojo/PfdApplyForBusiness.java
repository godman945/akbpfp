package com.pchome.akbpfp.db.pojo;

// Generated 2016/1/11 �U�� 01:57:12 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PfdApplyForBusiness generated by hbm2java
 */
@Entity
@Table(name = "pfd_apply_for_business", catalog = "akb")
public class PfdApplyForBusiness implements java.io.Serializable {

	private Integer seq;
	private String pfdCustomerInfoId;
	private String pfdUserId;
	private String targetTaxId;
	private String targetAdUrl;
	private String status;
	private Date applyForTime;
	private Date checkTime;
	private Date sysPassTime;
	private String checkUser;
	private String rejectReason;
	private String illegalReason;

	public PfdApplyForBusiness() {
	}

	public PfdApplyForBusiness(String pfdCustomerInfoId, String pfdUserId,
			String targetTaxId, String targetAdUrl, String status,
			Date applyForTime) {
		this.pfdCustomerInfoId = pfdCustomerInfoId;
		this.pfdUserId = pfdUserId;
		this.targetTaxId = targetTaxId;
		this.targetAdUrl = targetAdUrl;
		this.status = status;
		this.applyForTime = applyForTime;
	}

	public PfdApplyForBusiness(String pfdCustomerInfoId, String pfdUserId,
			String targetTaxId, String targetAdUrl, String status,
			Date applyForTime, Date checkTime, Date sysPassTime,
			String checkUser, String rejectReason, String illegalReason) {
		this.pfdCustomerInfoId = pfdCustomerInfoId;
		this.pfdUserId = pfdUserId;
		this.targetTaxId = targetTaxId;
		this.targetAdUrl = targetAdUrl;
		this.status = status;
		this.applyForTime = applyForTime;
		this.checkTime = checkTime;
		this.sysPassTime = sysPassTime;
		this.checkUser = checkUser;
		this.rejectReason = rejectReason;
		this.illegalReason = illegalReason;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "seq", unique = true, nullable = false)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Column(name = "pfd_customer_info_id", nullable = false, length = 20)
	public String getPfdCustomerInfoId() {
		return this.pfdCustomerInfoId;
	}

	public void setPfdCustomerInfoId(String pfdCustomerInfoId) {
		this.pfdCustomerInfoId = pfdCustomerInfoId;
	}

	@Column(name = "pfd_user_id", nullable = false, length = 20)
	public String getPfdUserId() {
		return this.pfdUserId;
	}

	public void setPfdUserId(String pfdUserId) {
		this.pfdUserId = pfdUserId;
	}

	@Column(name = "target_tax_id", nullable = false, length = 8)
	public String getTargetTaxId() {
		return this.targetTaxId;
	}

	public void setTargetTaxId(String targetTaxId) {
		this.targetTaxId = targetTaxId;
	}

	@Column(name = "target_ad_url", nullable = false)
	public String getTargetAdUrl() {
		return this.targetAdUrl;
	}

	public void setTargetAdUrl(String targetAdUrl) {
		this.targetAdUrl = targetAdUrl;
	}

	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "apply_for_time", nullable = false, length = 19)
	public Date getApplyForTime() {
		return this.applyForTime;
	}

	public void setApplyForTime(Date applyForTime) {
		this.applyForTime = applyForTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "check_time", length = 19)
	public Date getCheckTime() {
		return this.checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sys_pass_time", length = 19)
	public Date getSysPassTime() {
		return this.sysPassTime;
	}

	public void setSysPassTime(Date sysPassTime) {
		this.sysPassTime = sysPassTime;
	}

	@Column(name = "check_user", length = 50)
	public String getCheckUser() {
		return this.checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	@Column(name = "reject_reason", length = 50)
	public String getRejectReason() {
		return this.rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	@Column(name = "illegal_reason", length = 500)
	public String getIllegalReason() {
		return this.illegalReason;
	}

	public void setIllegalReason(String illegalReason) {
		this.illegalReason = illegalReason;
	}

}
