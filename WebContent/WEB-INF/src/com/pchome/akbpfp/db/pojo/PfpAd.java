package com.pchome.akbpfp.db.pojo;

// Generated 2016/1/11 �U�� 01:57:12 by Hibernate Tools 3.4.0.CR1

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
 * PfpAd generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad", catalog = "akb")
public class PfpAd implements java.io.Serializable {

	private String adSeq;
	private PfpAdGroup pfpAdGroup;
	private String adClass;
	private String adArea;
	private String adStyle;
	private String templateProductSeq;
	private int adStatus;
	private String adCategorySeq;
	private Date adSendVerifyTime;
	private Date adSysVerifyTime;
	private Date adUserVerifyTime;
	private String adVerifyUser;
	private String adVerifyRejectReason;
	private float adSearchPrice;
	private float adChannelPrice;
	private Date adCreateTime;
	private Date adUpdateTime;
	private Set<PfpAdInvalid> pfpAdInvalids = new HashSet<PfpAdInvalid>(0);
	private Set<PfpAdRate> pfpAdRates = new HashSet<PfpAdRate>(0);
	private Set<PfpAdDetail> pfpAdDetails = new HashSet<PfpAdDetail>(0);
	private Set<PfpAdPvclk> pfpAdPvclks = new HashSet<PfpAdPvclk>(0);

	public PfpAd() {
	}

	public PfpAd(String adSeq, PfpAdGroup pfpAdGroup, String adClass,
			String adStyle, String templateProductSeq, int adStatus,
			float adSearchPrice, float adChannelPrice, Date adCreateTime,
			Date adUpdateTime) {
		this.adSeq = adSeq;
		this.pfpAdGroup = pfpAdGroup;
		this.adClass = adClass;
		this.adStyle = adStyle;
		this.templateProductSeq = templateProductSeq;
		this.adStatus = adStatus;
		this.adSearchPrice = adSearchPrice;
		this.adChannelPrice = adChannelPrice;
		this.adCreateTime = adCreateTime;
		this.adUpdateTime = adUpdateTime;
	}

	public PfpAd(String adSeq, PfpAdGroup pfpAdGroup, String adClass,
			String adArea, String adStyle, String templateProductSeq,
			int adStatus, String adCategorySeq, Date adSendVerifyTime,
			Date adSysVerifyTime, Date adUserVerifyTime, String adVerifyUser,
			String adVerifyRejectReason, float adSearchPrice,
			float adChannelPrice, Date adCreateTime, Date adUpdateTime,
			Set<PfpAdInvalid> pfpAdInvalids, Set<PfpAdRate> pfpAdRates,
			Set<PfpAdDetail> pfpAdDetails, Set<PfpAdPvclk> pfpAdPvclks) {
		this.adSeq = adSeq;
		this.pfpAdGroup = pfpAdGroup;
		this.adClass = adClass;
		this.adArea = adArea;
		this.adStyle = adStyle;
		this.templateProductSeq = templateProductSeq;
		this.adStatus = adStatus;
		this.adCategorySeq = adCategorySeq;
		this.adSendVerifyTime = adSendVerifyTime;
		this.adSysVerifyTime = adSysVerifyTime;
		this.adUserVerifyTime = adUserVerifyTime;
		this.adVerifyUser = adVerifyUser;
		this.adVerifyRejectReason = adVerifyRejectReason;
		this.adSearchPrice = adSearchPrice;
		this.adChannelPrice = adChannelPrice;
		this.adCreateTime = adCreateTime;
		this.adUpdateTime = adUpdateTime;
		this.pfpAdInvalids = pfpAdInvalids;
		this.pfpAdRates = pfpAdRates;
		this.pfpAdDetails = pfpAdDetails;
		this.pfpAdPvclks = pfpAdPvclks;
	}

	@Id
	@Column(name = "ad_seq", unique = true, nullable = false, length = 20)
	public String getAdSeq() {
		return this.adSeq;
	}

	public void setAdSeq(String adSeq) {
		this.adSeq = adSeq;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ad_group_seq", nullable = false)
	public PfpAdGroup getPfpAdGroup() {
		return this.pfpAdGroup;
	}

	public void setPfpAdGroup(PfpAdGroup pfpAdGroup) {
		this.pfpAdGroup = pfpAdGroup;
	}

	@Column(name = "ad_class", nullable = false, length = 20)
	public String getAdClass() {
		return this.adClass;
	}

	public void setAdClass(String adClass) {
		this.adClass = adClass;
	}

	@Column(name = "ad_area", length = 10)
	public String getAdArea() {
		return this.adArea;
	}

	public void setAdArea(String adArea) {
		this.adArea = adArea;
	}

	@Column(name = "ad_style", nullable = false, length = 3)
	public String getAdStyle() {
		return this.adStyle;
	}

	public void setAdStyle(String adStyle) {
		this.adStyle = adStyle;
	}

	@Column(name = "template_product_seq", nullable = false, length = 20)
	public String getTemplateProductSeq() {
		return this.templateProductSeq;
	}

	public void setTemplateProductSeq(String templateProductSeq) {
		this.templateProductSeq = templateProductSeq;
	}

	@Column(name = "ad_status", nullable = false)
	public int getAdStatus() {
		return this.adStatus;
	}

	public void setAdStatus(int adStatus) {
		this.adStatus = adStatus;
	}

	@Column(name = "ad_category_seq", length = 3)
	public String getAdCategorySeq() {
		return this.adCategorySeq;
	}

	public void setAdCategorySeq(String adCategorySeq) {
		this.adCategorySeq = adCategorySeq;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_send_verify_time", length = 19)
	public Date getAdSendVerifyTime() {
		return this.adSendVerifyTime;
	}

	public void setAdSendVerifyTime(Date adSendVerifyTime) {
		this.adSendVerifyTime = adSendVerifyTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_sys_verify_time", length = 19)
	public Date getAdSysVerifyTime() {
		return this.adSysVerifyTime;
	}

	public void setAdSysVerifyTime(Date adSysVerifyTime) {
		this.adSysVerifyTime = adSysVerifyTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_user_verify_time", length = 19)
	public Date getAdUserVerifyTime() {
		return this.adUserVerifyTime;
	}

	public void setAdUserVerifyTime(Date adUserVerifyTime) {
		this.adUserVerifyTime = adUserVerifyTime;
	}

	@Column(name = "ad_verify_user", length = 50)
	public String getAdVerifyUser() {
		return this.adVerifyUser;
	}

	public void setAdVerifyUser(String adVerifyUser) {
		this.adVerifyUser = adVerifyUser;
	}

	@Column(name = "ad_verify_reject_reason", length = 200)
	public String getAdVerifyRejectReason() {
		return this.adVerifyRejectReason;
	}

	public void setAdVerifyRejectReason(String adVerifyRejectReason) {
		this.adVerifyRejectReason = adVerifyRejectReason;
	}

	@Column(name = "ad_search_price", nullable = false, precision = 10)
	public float getAdSearchPrice() {
		return this.adSearchPrice;
	}

	public void setAdSearchPrice(float adSearchPrice) {
		this.adSearchPrice = adSearchPrice;
	}

	@Column(name = "ad_channel_price", nullable = false, precision = 10)
	public float getAdChannelPrice() {
		return this.adChannelPrice;
	}

	public void setAdChannelPrice(float adChannelPrice) {
		this.adChannelPrice = adChannelPrice;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_create_time", nullable = false, length = 19)
	public Date getAdCreateTime() {
		return this.adCreateTime;
	}

	public void setAdCreateTime(Date adCreateTime) {
		this.adCreateTime = adCreateTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_update_time", nullable = false, length = 19)
	public Date getAdUpdateTime() {
		return this.adUpdateTime;
	}

	public void setAdUpdateTime(Date adUpdateTime) {
		this.adUpdateTime = adUpdateTime;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpAd")
	public Set<PfpAdInvalid> getPfpAdInvalids() {
		return this.pfpAdInvalids;
	}

	public void setPfpAdInvalids(Set<PfpAdInvalid> pfpAdInvalids) {
		this.pfpAdInvalids = pfpAdInvalids;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpAd")
	public Set<PfpAdRate> getPfpAdRates() {
		return this.pfpAdRates;
	}

	public void setPfpAdRates(Set<PfpAdRate> pfpAdRates) {
		this.pfpAdRates = pfpAdRates;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpAd")
	public Set<PfpAdDetail> getPfpAdDetails() {
		return this.pfpAdDetails;
	}

	public void setPfpAdDetails(Set<PfpAdDetail> pfpAdDetails) {
		this.pfpAdDetails = pfpAdDetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpAd")
	public Set<PfpAdPvclk> getPfpAdPvclks() {
		return this.pfpAdPvclks;
	}

	public void setPfpAdPvclks(Set<PfpAdPvclk> pfpAdPvclks) {
		this.pfpAdPvclks = pfpAdPvclks;
	}

}
