package com.pchome.akbpfp.db.pojo;

// Generated 2015/10/16 �W�� 10:15:06 by Hibernate Tools 3.4.0.CR1

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
 * AdmFreeAction generated by hbm2java
 */
@Entity
@Table(name = "adm_free_action", catalog = "akb")
public class AdmFreeAction implements java.io.Serializable {

	private String actionId;
	private String actionName;
	private String payment;
	private float giftCondition;
	private float giftMoney;
	private Date actionStartDate;
	private Date actionEndDate;
	private Date inviledDate;
	private String retrievedFlag;
	private String note;
	private Date updateDate;
	private Date createDate;
	private Set<AdmFreeGift> admFreeGifts = new HashSet<AdmFreeGift>(0);
	private Set<AdmFreeRecord> admFreeRecords = new HashSet<AdmFreeRecord>(0);

	public AdmFreeAction() {
	}

	public AdmFreeAction(String actionId, String actionName, String payment,
			float giftCondition, float giftMoney, Date updateDate,
			Date createDate) {
		this.actionId = actionId;
		this.actionName = actionName;
		this.payment = payment;
		this.giftCondition = giftCondition;
		this.giftMoney = giftMoney;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	public AdmFreeAction(String actionId, String actionName, String payment,
			float giftCondition, float giftMoney, Date actionStartDate,
			Date actionEndDate, Date inviledDate, String retrievedFlag,
			String note, Date updateDate, Date createDate,
			Set<AdmFreeGift> admFreeGifts, Set<AdmFreeRecord> admFreeRecords) {
		this.actionId = actionId;
		this.actionName = actionName;
		this.payment = payment;
		this.giftCondition = giftCondition;
		this.giftMoney = giftMoney;
		this.actionStartDate = actionStartDate;
		this.actionEndDate = actionEndDate;
		this.inviledDate = inviledDate;
		this.retrievedFlag = retrievedFlag;
		this.note = note;
		this.updateDate = updateDate;
		this.createDate = createDate;
		this.admFreeGifts = admFreeGifts;
		this.admFreeRecords = admFreeRecords;
	}

	@Id
	@Column(name = "action_id", unique = true, nullable = false, length = 20)
	public String getActionId() {
		return this.actionId;
	}

	public void setActionId(String actionId) {
		this.actionId = actionId;
	}

	@Column(name = "action_name", nullable = false, length = 20)
	public String getActionName() {
		return this.actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	@Column(name = "payment", nullable = false, length = 1)
	public String getPayment() {
		return this.payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	@Column(name = "gift_condition", nullable = false, precision = 10)
	public float getGiftCondition() {
		return this.giftCondition;
	}

	public void setGiftCondition(float giftCondition) {
		this.giftCondition = giftCondition;
	}

	@Column(name = "gift_money", nullable = false, precision = 10)
	public float getGiftMoney() {
		return this.giftMoney;
	}

	public void setGiftMoney(float giftMoney) {
		this.giftMoney = giftMoney;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "action_start_date", length = 19)
	public Date getActionStartDate() {
		return this.actionStartDate;
	}

	public void setActionStartDate(Date actionStartDate) {
		this.actionStartDate = actionStartDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "action_end_date", length = 19)
	public Date getActionEndDate() {
		return this.actionEndDate;
	}

	public void setActionEndDate(Date actionEndDate) {
		this.actionEndDate = actionEndDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "inviled_date", length = 10)
	public Date getInviledDate() {
		return this.inviledDate;
	}

	public void setInviledDate(Date inviledDate) {
		this.inviledDate = inviledDate;
	}

	@Column(name = "retrieved_flag", length = 1)
	public String getRetrievedFlag() {
		return this.retrievedFlag;
	}

	public void setRetrievedFlag(String retrievedFlag) {
		this.retrievedFlag = retrievedFlag;
	}

	@Column(name = "note", length = 200)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", nullable = false, length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "admFreeAction")
	public Set<AdmFreeGift> getAdmFreeGifts() {
		return this.admFreeGifts;
	}

	public void setAdmFreeGifts(Set<AdmFreeGift> admFreeGifts) {
		this.admFreeGifts = admFreeGifts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "admFreeAction")
	public Set<AdmFreeRecord> getAdmFreeRecords() {
		return this.admFreeRecords;
	}

	public void setAdmFreeRecords(Set<AdmFreeRecord> admFreeRecords) {
		this.admFreeRecords = admFreeRecords;
	}

}
