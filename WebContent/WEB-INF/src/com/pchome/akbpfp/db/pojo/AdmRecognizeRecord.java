package com.pchome.akbpfp.db.pojo;
// Generated 2017/8/22 �U�� 02:42:42 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AdmRecognizeRecord generated by hbm2java
 */
@Entity
@Table(name = "adm_recognize_record", catalog = "akb_video")
public class AdmRecognizeRecord implements java.io.Serializable {

	private String recognizeRecordId;
	private PfpCustomerInfo pfpCustomerInfo;
	private String recognizeOrderId;
	private String giftSno;
	private String orderType;
	private float orderPrice;
	private float tax;
	private float orderRemain;
	private float taxRemain;
	private String orderRemainZero;
	private Date saveDate;
	private Date updateDate;
	private Date createDate;
	private Set<AdmRecognizeDetail> admRecognizeDetails = new HashSet<AdmRecognizeDetail>(0);

	public AdmRecognizeRecord() {
	}

	public AdmRecognizeRecord(String recognizeRecordId, PfpCustomerInfo pfpCustomerInfo, String recognizeOrderId,
			String orderType, float orderPrice, float tax, float orderRemain, float taxRemain, String orderRemainZero,
			Date saveDate, Date updateDate, Date createDate) {
		this.recognizeRecordId = recognizeRecordId;
		this.pfpCustomerInfo = pfpCustomerInfo;
		this.recognizeOrderId = recognizeOrderId;
		this.orderType = orderType;
		this.orderPrice = orderPrice;
		this.tax = tax;
		this.orderRemain = orderRemain;
		this.taxRemain = taxRemain;
		this.orderRemainZero = orderRemainZero;
		this.saveDate = saveDate;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	public AdmRecognizeRecord(String recognizeRecordId, PfpCustomerInfo pfpCustomerInfo, String recognizeOrderId,
			String giftSno, String orderType, float orderPrice, float tax, float orderRemain, float taxRemain,
			String orderRemainZero, Date saveDate, Date updateDate, Date createDate,
			Set<AdmRecognizeDetail> admRecognizeDetails) {
		this.recognizeRecordId = recognizeRecordId;
		this.pfpCustomerInfo = pfpCustomerInfo;
		this.recognizeOrderId = recognizeOrderId;
		this.giftSno = giftSno;
		this.orderType = orderType;
		this.orderPrice = orderPrice;
		this.tax = tax;
		this.orderRemain = orderRemain;
		this.taxRemain = taxRemain;
		this.orderRemainZero = orderRemainZero;
		this.saveDate = saveDate;
		this.updateDate = updateDate;
		this.createDate = createDate;
		this.admRecognizeDetails = admRecognizeDetails;
	}

	@Id

	@Column(name = "recognize_record_id", unique = true, nullable = false, length = 20)
	public String getRecognizeRecordId() {
		return this.recognizeRecordId;
	}

	public void setRecognizeRecordId(String recognizeRecordId) {
		this.recognizeRecordId = recognizeRecordId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_info_id", nullable = false)
	public PfpCustomerInfo getPfpCustomerInfo() {
		return this.pfpCustomerInfo;
	}

	public void setPfpCustomerInfo(PfpCustomerInfo pfpCustomerInfo) {
		this.pfpCustomerInfo = pfpCustomerInfo;
	}

	@Column(name = "recognize_order_id", nullable = false, length = 20)
	public String getRecognizeOrderId() {
		return this.recognizeOrderId;
	}

	public void setRecognizeOrderId(String recognizeOrderId) {
		this.recognizeOrderId = recognizeOrderId;
	}

	@Column(name = "gift_sno", length = 20)
	public String getGiftSno() {
		return this.giftSno;
	}

	public void setGiftSno(String giftSno) {
		this.giftSno = giftSno;
	}

	@Column(name = "order_type", nullable = false, length = 1)
	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Column(name = "order_price", nullable = false, precision = 10)
	public float getOrderPrice() {
		return this.orderPrice;
	}

	public void setOrderPrice(float orderPrice) {
		this.orderPrice = orderPrice;
	}

	@Column(name = "tax", nullable = false, precision = 10)
	public float getTax() {
		return this.tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	@Column(name = "order_remain", nullable = false, precision = 10)
	public float getOrderRemain() {
		return this.orderRemain;
	}

	public void setOrderRemain(float orderRemain) {
		this.orderRemain = orderRemain;
	}

	@Column(name = "tax_remain", nullable = false, precision = 10)
	public float getTaxRemain() {
		return this.taxRemain;
	}

	public void setTaxRemain(float taxRemain) {
		this.taxRemain = taxRemain;
	}

	@Column(name = "order_remain_zero", nullable = false, length = 1)
	public String getOrderRemainZero() {
		return this.orderRemainZero;
	}

	public void setOrderRemainZero(String orderRemainZero) {
		this.orderRemainZero = orderRemainZero;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "save_date", nullable = false, length = 0)
	public Date getSaveDate() {
		return this.saveDate;
	}

	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", nullable = false, length = 0)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, length = 0)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "admRecognizeRecord")
	public Set<AdmRecognizeDetail> getAdmRecognizeDetails() {
		return this.admRecognizeDetails;
	}

	public void setAdmRecognizeDetails(Set<AdmRecognizeDetail> admRecognizeDetails) {
		this.admRecognizeDetails = admRecognizeDetails;
	}

}
