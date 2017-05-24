package com.pchome.akbpfp.db.pojo;

// Generated 2017/5/24 �W�� 10:13:49 by Hibernate Tools 3.4.0.CR1

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
 * PfpOrder generated by hbm2java
 */
@Entity
@Table(name = "pfp_order", catalog = "akb")
public class PfpOrder implements java.io.Serializable {

	private String orderId;
	private PfpUser pfpUser;
	private PfpCustomerInfo pfpCustomerInfo;
	private String billingId;
	private float orderPrice;
	private float tax;
	private String status;
	private String giftSno;
	private float giftMoney;
	private Date notifyDate;
	private Date createDate;
	private Date updateDate;
	private Set<PfpOrderDetail> pfpOrderDetails = new HashSet<PfpOrderDetail>(0);

	public PfpOrder() {
	}

	public PfpOrder(String orderId, PfpUser pfpUser,
			PfpCustomerInfo pfpCustomerInfo, float orderPrice, float tax,
			String status, float giftMoney, Date createDate, Date updateDate) {
		this.orderId = orderId;
		this.pfpUser = pfpUser;
		this.pfpCustomerInfo = pfpCustomerInfo;
		this.orderPrice = orderPrice;
		this.tax = tax;
		this.status = status;
		this.giftMoney = giftMoney;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public PfpOrder(String orderId, PfpUser pfpUser,
			PfpCustomerInfo pfpCustomerInfo, String billingId,
			float orderPrice, float tax, String status, String giftSno,
			float giftMoney, Date notifyDate, Date createDate, Date updateDate,
			Set<PfpOrderDetail> pfpOrderDetails) {
		this.orderId = orderId;
		this.pfpUser = pfpUser;
		this.pfpCustomerInfo = pfpCustomerInfo;
		this.billingId = billingId;
		this.orderPrice = orderPrice;
		this.tax = tax;
		this.status = status;
		this.giftSno = giftSno;
		this.giftMoney = giftMoney;
		this.notifyDate = notifyDate;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.pfpOrderDetails = pfpOrderDetails;
	}

	@Id
	@Column(name = "order_id", unique = true, nullable = false, length = 20)
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public PfpUser getPfpUser() {
		return this.pfpUser;
	}

	public void setPfpUser(PfpUser pfpUser) {
		this.pfpUser = pfpUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_info_id", nullable = false)
	public PfpCustomerInfo getPfpCustomerInfo() {
		return this.pfpCustomerInfo;
	}

	public void setPfpCustomerInfo(PfpCustomerInfo pfpCustomerInfo) {
		this.pfpCustomerInfo = pfpCustomerInfo;
	}

	@Column(name = "billing_id", length = 20)
	public String getBillingId() {
		return this.billingId;
	}

	public void setBillingId(String billingId) {
		this.billingId = billingId;
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

	@Column(name = "status", nullable = false, length = 10)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "gift_sno", length = 20)
	public String getGiftSno() {
		return this.giftSno;
	}

	public void setGiftSno(String giftSno) {
		this.giftSno = giftSno;
	}

	@Column(name = "gift_money", nullable = false, precision = 10)
	public float getGiftMoney() {
		return this.giftMoney;
	}

	public void setGiftMoney(float giftMoney) {
		this.giftMoney = giftMoney;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "notify_date", length = 19)
	public Date getNotifyDate() {
		return this.notifyDate;
	}

	public void setNotifyDate(Date notifyDate) {
		this.notifyDate = notifyDate;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpOrder")
	public Set<PfpOrderDetail> getPfpOrderDetails() {
		return this.pfpOrderDetails;
	}

	public void setPfpOrderDetails(Set<PfpOrderDetail> pfpOrderDetails) {
		this.pfpOrderDetails = pfpOrderDetails;
	}

}
