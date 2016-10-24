package com.pchome.akbpfp.db.pojo;

// Generated 2016/10/24 �U�� 12:22:48 by Hibernate Tools 3.4.0.CR1

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
 * AdmChannelAccount generated by hbm2java
 */
@Entity
@Table(name = "adm_channel_account", catalog = "akb")
public class AdmChannelAccount implements java.io.Serializable {

	private Integer id;
	private String memberId;
	private String accountId;
	private String channelCategory;
	private Date updateDate;
	private Date createDate;

	public AdmChannelAccount() {
	}

	public AdmChannelAccount(String memberId, String accountId,
			String channelCategory, Date updateDate, Date createDate) {
		this.memberId = memberId;
		this.accountId = accountId;
		this.channelCategory = channelCategory;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "member_id", nullable = false, length = 20)
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	@Column(name = "account_id", nullable = false, length = 20)
	public String getAccountId() {
		return this.accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Column(name = "channel_category", nullable = false, length = 1)
	public String getChannelCategory() {
		return this.channelCategory;
	}

	public void setChannelCategory(String channelCategory) {
		this.channelCategory = channelCategory;
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
