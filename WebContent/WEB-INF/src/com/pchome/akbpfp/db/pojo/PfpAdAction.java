package com.pchome.akbpfp.db.pojo;

// Generated 2016/4/19 �U�� 02:15:49 by Hibernate Tools 3.4.0.CR1

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
import javax.persistence.UniqueConstraint;

/**
 * PfpAdAction generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_action", catalog = "akb", uniqueConstraints = @UniqueConstraint(columnNames = {
		"ad_action_name", "customer_info_id" }))
public class PfpAdAction implements java.io.Serializable {

	private String adActionSeq;
	private PfpCustomerInfo pfpCustomerInfo;
	private String adActionName;
	private String adActionDesc;
	private Integer adType;
	private Integer adDevice;
	private Date adActionStartDate;
	private Date adActionEndDate;
	private float adActionMax;
	private float adActionControlPrice;
	private int adActionStatus;
	private String aid;
	private String userId;
	private Date adActionCreatTime;
	private Date adActionUpdateTime;
	private Set<PfpAdGroup> pfpAdGroups = new HashSet<PfpAdGroup>(0);

	public PfpAdAction() {
	}

	public PfpAdAction(String adActionSeq, PfpCustomerInfo pfpCustomerInfo,
			String adActionName, String adActionDesc, Date adActionStartDate,
			Date adActionEndDate, float adActionMax,
			float adActionControlPrice, int adActionStatus, String userId,
			Date adActionCreatTime, Date adActionUpdateTime) {
		this.adActionSeq = adActionSeq;
		this.pfpCustomerInfo = pfpCustomerInfo;
		this.adActionName = adActionName;
		this.adActionDesc = adActionDesc;
		this.adActionStartDate = adActionStartDate;
		this.adActionEndDate = adActionEndDate;
		this.adActionMax = adActionMax;
		this.adActionControlPrice = adActionControlPrice;
		this.adActionStatus = adActionStatus;
		this.userId = userId;
		this.adActionCreatTime = adActionCreatTime;
		this.adActionUpdateTime = adActionUpdateTime;
	}

	public PfpAdAction(String adActionSeq, PfpCustomerInfo pfpCustomerInfo,
			String adActionName, String adActionDesc, Integer adType,
			Integer adDevice, Date adActionStartDate, Date adActionEndDate,
			float adActionMax, float adActionControlPrice, int adActionStatus,
			String aid, String userId, Date adActionCreatTime,
			Date adActionUpdateTime, Set<PfpAdGroup> pfpAdGroups) {
		this.adActionSeq = adActionSeq;
		this.pfpCustomerInfo = pfpCustomerInfo;
		this.adActionName = adActionName;
		this.adActionDesc = adActionDesc;
		this.adType = adType;
		this.adDevice = adDevice;
		this.adActionStartDate = adActionStartDate;
		this.adActionEndDate = adActionEndDate;
		this.adActionMax = adActionMax;
		this.adActionControlPrice = adActionControlPrice;
		this.adActionStatus = adActionStatus;
		this.aid = aid;
		this.userId = userId;
		this.adActionCreatTime = adActionCreatTime;
		this.adActionUpdateTime = adActionUpdateTime;
		this.pfpAdGroups = pfpAdGroups;
	}

	@Id
	@Column(name = "ad_action_seq", unique = true, nullable = false, length = 20)
	public String getAdActionSeq() {
		return this.adActionSeq;
	}

	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_info_id", nullable = false)
	public PfpCustomerInfo getPfpCustomerInfo() {
		return this.pfpCustomerInfo;
	}

	public void setPfpCustomerInfo(PfpCustomerInfo pfpCustomerInfo) {
		this.pfpCustomerInfo = pfpCustomerInfo;
	}

	@Column(name = "ad_action_name", nullable = false, length = 20)
	public String getAdActionName() {
		return this.adActionName;
	}

	public void setAdActionName(String adActionName) {
		this.adActionName = adActionName;
	}

	@Column(name = "ad_action_desc", nullable = false, length = 300)
	public String getAdActionDesc() {
		return this.adActionDesc;
	}

	public void setAdActionDesc(String adActionDesc) {
		this.adActionDesc = adActionDesc;
	}

	@Column(name = "ad_type")
	public Integer getAdType() {
		return this.adType;
	}

	public void setAdType(Integer adType) {
		this.adType = adType;
	}

	@Column(name = "ad_device")
	public Integer getAdDevice() {
		return this.adDevice;
	}

	public void setAdDevice(Integer adDevice) {
		this.adDevice = adDevice;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ad_action_start_date", nullable = false, length = 10)
	public Date getAdActionStartDate() {
		return this.adActionStartDate;
	}

	public void setAdActionStartDate(Date adActionStartDate) {
		this.adActionStartDate = adActionStartDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ad_action_end_date", nullable = false, length = 10)
	public Date getAdActionEndDate() {
		return this.adActionEndDate;
	}

	public void setAdActionEndDate(Date adActionEndDate) {
		this.adActionEndDate = adActionEndDate;
	}

	@Column(name = "ad_action_max", nullable = false, precision = 10)
	public float getAdActionMax() {
		return this.adActionMax;
	}

	public void setAdActionMax(float adActionMax) {
		this.adActionMax = adActionMax;
	}

	@Column(name = "ad_action_control_price", nullable = false, precision = 10)
	public float getAdActionControlPrice() {
		return this.adActionControlPrice;
	}

	public void setAdActionControlPrice(float adActionControlPrice) {
		this.adActionControlPrice = adActionControlPrice;
	}

	@Column(name = "ad_action_status", nullable = false)
	public int getAdActionStatus() {
		return this.adActionStatus;
	}

	public void setAdActionStatus(int adActionStatus) {
		this.adActionStatus = adActionStatus;
	}

	@Column(name = "aid", length = 16)
	public String getAid() {
		return this.aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	@Column(name = "user_id", nullable = false, length = 20)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_action_creat_time", nullable = false, length = 19)
	public Date getAdActionCreatTime() {
		return this.adActionCreatTime;
	}

	public void setAdActionCreatTime(Date adActionCreatTime) {
		this.adActionCreatTime = adActionCreatTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_action_update_time", nullable = false, length = 19)
	public Date getAdActionUpdateTime() {
		return this.adActionUpdateTime;
	}

	public void setAdActionUpdateTime(Date adActionUpdateTime) {
		this.adActionUpdateTime = adActionUpdateTime;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpAdAction")
	public Set<PfpAdGroup> getPfpAdGroups() {
		return this.pfpAdGroups;
	}

	public void setPfpAdGroups(Set<PfpAdGroup> pfpAdGroups) {
		this.pfpAdGroups = pfpAdGroups;
	}

}
