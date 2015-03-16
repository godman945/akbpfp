package com.pchome.akbpfp.db.pojo;

// Generated 2014/5/5 �U�� 05:30:59 by Hibernate Tools 3.4.0.CR1

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
 * AdmFeedbackRecord generated by hbm2java
 */
@Entity
@Table(name = "adm_feedback_record", catalog = "akb")
public class AdmFeedbackRecord implements java.io.Serializable {

	private String feedbackRecordId;
	private PfpCustomerInfo pfpCustomerInfo;
	private String reason;
	private float feedbackMoney;
	private String status;
	private Date giftDate;
	private String editor;
	private String author;
	private Date updateDate;
	private Date createDate;

	public AdmFeedbackRecord() {
	}

	public AdmFeedbackRecord(String feedbackRecordId,
			PfpCustomerInfo pfpCustomerInfo, String reason,
			float feedbackMoney, String status, Date giftDate, String editor,
			String author, Date updateDate, Date createDate) {
		this.feedbackRecordId = feedbackRecordId;
		this.pfpCustomerInfo = pfpCustomerInfo;
		this.reason = reason;
		this.feedbackMoney = feedbackMoney;
		this.status = status;
		this.giftDate = giftDate;
		this.editor = editor;
		this.author = author;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	@Id
	@Column(name = "feedback_record_id", unique = true, nullable = false, length = 20)
	public String getFeedbackRecordId() {
		return this.feedbackRecordId;
	}

	public void setFeedbackRecordId(String feedbackRecordId) {
		this.feedbackRecordId = feedbackRecordId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_info_id", nullable = false)
	public PfpCustomerInfo getPfpCustomerInfo() {
		return this.pfpCustomerInfo;
	}

	public void setPfpCustomerInfo(PfpCustomerInfo pfpCustomerInfo) {
		this.pfpCustomerInfo = pfpCustomerInfo;
	}

	@Column(name = "reason", nullable = false, length = 50)
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "feedback_money", nullable = false, precision = 10)
	public float getFeedbackMoney() {
		return this.feedbackMoney;
	}

	public void setFeedbackMoney(float feedbackMoney) {
		this.feedbackMoney = feedbackMoney;
	}

	@Column(name = "status", nullable = false, length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "gift_date", nullable = false, length = 10)
	public Date getGiftDate() {
		return this.giftDate;
	}

	public void setGiftDate(Date giftDate) {
		this.giftDate = giftDate;
	}

	@Column(name = "editor", nullable = false, length = 50)
	public String getEditor() {
		return this.editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	@Column(name = "author", nullable = false, length = 50)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

}
