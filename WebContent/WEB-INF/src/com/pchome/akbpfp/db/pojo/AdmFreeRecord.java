package com.pchome.akbpfp.db.pojo;

// Generated 2017/6/22 �W�� 10:22:48 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * AdmFreeRecord generated by hbm2java
 */
@Entity
@Table(name = "adm_free_record", catalog = "akb")
public class AdmFreeRecord implements java.io.Serializable {

	private Integer recordId;
	private AdmFreeAction admFreeAction;
	private String customerInfoId;
	private String orderId;
	private Date recordDate;
	private Date updateDate;
	private Date createDate;

	public AdmFreeRecord() {
	}

	public AdmFreeRecord(AdmFreeAction admFreeAction, String customerInfoId,
			Date recordDate, Date updateDate, Date createDate) {
		this.admFreeAction = admFreeAction;
		this.customerInfoId = customerInfoId;
		this.recordDate = recordDate;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	public AdmFreeRecord(AdmFreeAction admFreeAction, String customerInfoId,
			String orderId, Date recordDate, Date updateDate, Date createDate) {
		this.admFreeAction = admFreeAction;
		this.customerInfoId = customerInfoId;
		this.orderId = orderId;
		this.recordDate = recordDate;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "record_id", unique = true, nullable = false)
	public Integer getRecordId() {
		return this.recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "action_id", nullable = false)
	public AdmFreeAction getAdmFreeAction() {
		return this.admFreeAction;
	}

	public void setAdmFreeAction(AdmFreeAction admFreeAction) {
		this.admFreeAction = admFreeAction;
	}

	@Column(name = "customer_info_id", nullable = false, length = 20)
	public String getCustomerInfoId() {
		return this.customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	@Column(name = "order_id", length = 20)
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "record_date", nullable = false, length = 10)
	public Date getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
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
