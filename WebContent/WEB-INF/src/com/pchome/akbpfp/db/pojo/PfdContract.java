package com.pchome.akbpfp.db.pojo;

// Generated 2016/10/24 �U�� 12:22:48 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PfdContract generated by hbm2java
 */
@Entity
@Table(name = "pfd_contract", catalog = "akb")
public class PfdContract implements java.io.Serializable {

	private String pfdContractId;
	private PfdCustomerInfo pfdCustomerInfo;
	private String status;
	private Date contractDate;
	private Date startDate;
	private Date endDate;
	private Date closeDate;
	private String closeReason;
	private String pfpPayType;
	private int payDay;
	private double overdueFine;
	private String memo;
	private String remindFlag;
	private Date remindDate;
	private String continueFlag;
	private Date createDate;
	private Date updateDate;

	public PfdContract() {
	}

	public PfdContract(String pfdContractId, PfdCustomerInfo pfdCustomerInfo,
			String status, Date contractDate, Date startDate, Date endDate,
			String pfpPayType, int payDay, double overdueFine, Date createDate,
			Date updateDate) {
		this.pfdContractId = pfdContractId;
		this.pfdCustomerInfo = pfdCustomerInfo;
		this.status = status;
		this.contractDate = contractDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.pfpPayType = pfpPayType;
		this.payDay = payDay;
		this.overdueFine = overdueFine;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public PfdContract(String pfdContractId, PfdCustomerInfo pfdCustomerInfo,
			String status, Date contractDate, Date startDate, Date endDate,
			Date closeDate, String closeReason, String pfpPayType, int payDay,
			double overdueFine, String memo, String remindFlag,
			Date remindDate, String continueFlag, Date createDate,
			Date updateDate) {
		this.pfdContractId = pfdContractId;
		this.pfdCustomerInfo = pfdCustomerInfo;
		this.status = status;
		this.contractDate = contractDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.closeDate = closeDate;
		this.closeReason = closeReason;
		this.pfpPayType = pfpPayType;
		this.payDay = payDay;
		this.overdueFine = overdueFine;
		this.memo = memo;
		this.remindFlag = remindFlag;
		this.remindDate = remindDate;
		this.continueFlag = continueFlag;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	@Id
	@Column(name = "pfd_contract_id", unique = true, nullable = false, length = 20)
	public String getPfdContractId() {
		return this.pfdContractId;
	}

	public void setPfdContractId(String pfdContractId) {
		this.pfdContractId = pfdContractId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pfd_customer_info_id", nullable = false)
	public PfdCustomerInfo getPfdCustomerInfo() {
		return this.pfdCustomerInfo;
	}

	public void setPfdCustomerInfo(PfdCustomerInfo pfdCustomerInfo) {
		this.pfdCustomerInfo = pfdCustomerInfo;
	}

	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "contract_date", nullable = false, length = 19)
	public Date getContractDate() {
		return this.contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", nullable = false, length = 19)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", nullable = false, length = 19)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "close_date", length = 19)
	public Date getCloseDate() {
		return this.closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	@Column(name = "close_reason", length = 50)
	public String getCloseReason() {
		return this.closeReason;
	}

	public void setCloseReason(String closeReason) {
		this.closeReason = closeReason;
	}

	@Column(name = "pfp_pay_type", nullable = false, length = 1)
	public String getPfpPayType() {
		return this.pfpPayType;
	}

	public void setPfpPayType(String pfpPayType) {
		this.pfpPayType = pfpPayType;
	}

	@Column(name = "pay_day", nullable = false)
	public int getPayDay() {
		return this.payDay;
	}

	public void setPayDay(int payDay) {
		this.payDay = payDay;
	}

	@Column(name = "overdue_fine", nullable = false, precision = 22, scale = 0)
	public double getOverdueFine() {
		return this.overdueFine;
	}

	public void setOverdueFine(double overdueFine) {
		this.overdueFine = overdueFine;
	}

	@Column(name = "memo", length = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "remind_flag", length = 1)
	public String getRemindFlag() {
		return this.remindFlag;
	}

	public void setRemindFlag(String remindFlag) {
		this.remindFlag = remindFlag;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "remind_date", length = 19)
	public Date getRemindDate() {
		return this.remindDate;
	}

	public void setRemindDate(Date remindDate) {
		this.remindDate = remindDate;
	}

	@Column(name = "continue_flag", length = 1)
	public String getContinueFlag() {
		return this.continueFlag;
	}

	public void setContinueFlag(String continueFlag) {
		this.continueFlag = continueFlag;
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

}
