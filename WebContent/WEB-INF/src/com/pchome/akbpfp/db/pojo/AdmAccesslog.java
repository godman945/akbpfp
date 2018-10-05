package com.pchome.akbpfp.db.pojo;
// Generated 2018/9/28 �W�� 10:58:33 by Hibernate Tools 3.4.0.CR1

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
 * AdmAccesslog generated by hbm2java
 */
@Entity
@Table(name = "adm_accesslog")
public class AdmAccesslog implements java.io.Serializable {

	private Integer accesslogId;
	private String channel;
	private String action;
	private String message;
	private String memberId;
	private String orderId;
	private String customerInfoId;
	private String userId;
	private String clientIp;
	private String mailSend;
	private Date createDate;

	public AdmAccesslog() {
	}

	public AdmAccesslog(String channel, String message, String mailSend, Date createDate) {
		this.channel = channel;
		this.message = message;
		this.mailSend = mailSend;
		this.createDate = createDate;
	}

	public AdmAccesslog(String channel, String action, String message, String memberId, String orderId,
			String customerInfoId, String userId, String clientIp, String mailSend, Date createDate) {
		this.channel = channel;
		this.action = action;
		this.message = message;
		this.memberId = memberId;
		this.orderId = orderId;
		this.customerInfoId = customerInfoId;
		this.userId = userId;
		this.clientIp = clientIp;
		this.mailSend = mailSend;
		this.createDate = createDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "accesslog_id", unique = true, nullable = false)
	public Integer getAccesslogId() {
		return this.accesslogId;
	}

	public void setAccesslogId(Integer accesslogId) {
		this.accesslogId = accesslogId;
	}

	@Column(name = "channel", nullable = false, length = 50)
	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	@Column(name = "action", length = 2)
	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Column(name = "message", nullable = false, length = 2500)
	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "member_id", length = 50)
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Column(name = "order_id", length = 20)
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "customerInfo_id", length = 20)
	public String getCustomerInfoId() {
		return this.customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	@Column(name = "user_id", length = 20)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "client_ip", length = 20)
	public String getClientIp() {
		return this.clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	@Column(name = "mail_send", nullable = false, length = 1)
	public String getMailSend() {
		return this.mailSend;
	}

	public void setMailSend(String mailSend) {
		this.mailSend = mailSend;
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
