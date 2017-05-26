package com.pchome.akbpfp.db.pojo;

// Generated 2017/5/26 �W�� 09:41:24 by Hibernate Tools 3.4.0.CR1

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
 * PfpMailbox generated by hbm2java
 */
@Entity
@Table(name = "pfp_mailbox", catalog = "akb")
public class PfpMailbox implements java.io.Serializable {

	private Integer mailboxId;
	private String customerInfoId;
	private String category;
	private String receiver;
	private String send;
	private Date updateDate;
	private Date createDate;

	public PfpMailbox() {
	}

	public PfpMailbox(String customerInfoId, String category, String receiver,
			String send, Date updateDate, Date createDate) {
		this.customerInfoId = customerInfoId;
		this.category = category;
		this.receiver = receiver;
		this.send = send;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "mailbox_id", unique = true, nullable = false)
	public Integer getMailboxId() {
		return this.mailboxId;
	}

	public void setMailboxId(Integer mailboxId) {
		this.mailboxId = mailboxId;
	}

	@Column(name = "customer_info_id", nullable = false, length = 20)
	public String getCustomerInfoId() {
		return this.customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	@Column(name = "category", nullable = false, length = 2)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "receiver", nullable = false, length = 50)
	public String getReceiver() {
		return this.receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Column(name = "send", nullable = false, length = 1)
	public String getSend() {
		return this.send;
	}

	public void setSend(String send) {
		this.send = send;
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
