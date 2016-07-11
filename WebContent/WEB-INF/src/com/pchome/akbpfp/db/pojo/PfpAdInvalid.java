package com.pchome.akbpfp.db.pojo;

// Generated 2016/7/11 �W�� 11:35:46 by Hibernate Tools 3.4.0.CR1

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
 * PfpAdInvalid generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_invalid", catalog = "akb")
public class PfpAdInvalid implements java.io.Serializable {

	private Integer adInvalidSeq;
	private PfpAd pfpAd;
	private String customerInfoId;
	private int adType;
	private int maliceType;
	private int adInvalidClk;
	private float adInvalidClkPrice;
	private Date adInvalidDate;
	private int adInvalidTime;
	private Date adInvalidCreateTime;
	private Date adInvalidUpdateTime;

	public PfpAdInvalid() {
	}

	public PfpAdInvalid(PfpAd pfpAd, String customerInfoId, int adType,
			int maliceType, int adInvalidClk, float adInvalidClkPrice,
			Date adInvalidDate, int adInvalidTime, Date adInvalidCreateTime,
			Date adInvalidUpdateTime) {
		this.pfpAd = pfpAd;
		this.customerInfoId = customerInfoId;
		this.adType = adType;
		this.maliceType = maliceType;
		this.adInvalidClk = adInvalidClk;
		this.adInvalidClkPrice = adInvalidClkPrice;
		this.adInvalidDate = adInvalidDate;
		this.adInvalidTime = adInvalidTime;
		this.adInvalidCreateTime = adInvalidCreateTime;
		this.adInvalidUpdateTime = adInvalidUpdateTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ad_invalid_seq", unique = true, nullable = false)
	public Integer getAdInvalidSeq() {
		return this.adInvalidSeq;
	}

	public void setAdInvalidSeq(Integer adInvalidSeq) {
		this.adInvalidSeq = adInvalidSeq;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ad_seq", nullable = false)
	public PfpAd getPfpAd() {
		return this.pfpAd;
	}

	public void setPfpAd(PfpAd pfpAd) {
		this.pfpAd = pfpAd;
	}

	@Column(name = "customer_info_id", nullable = false, length = 20)
	public String getCustomerInfoId() {
		return this.customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	@Column(name = "ad_type", nullable = false)
	public int getAdType() {
		return this.adType;
	}

	public void setAdType(int adType) {
		this.adType = adType;
	}

	@Column(name = "malice_type", nullable = false)
	public int getMaliceType() {
		return this.maliceType;
	}

	public void setMaliceType(int maliceType) {
		this.maliceType = maliceType;
	}

	@Column(name = "ad_invalid_clk", nullable = false)
	public int getAdInvalidClk() {
		return this.adInvalidClk;
	}

	public void setAdInvalidClk(int adInvalidClk) {
		this.adInvalidClk = adInvalidClk;
	}

	@Column(name = "ad_invalid_clk_price", nullable = false, precision = 10)
	public float getAdInvalidClkPrice() {
		return this.adInvalidClkPrice;
	}

	public void setAdInvalidClkPrice(float adInvalidClkPrice) {
		this.adInvalidClkPrice = adInvalidClkPrice;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ad_invalid_date", nullable = false, length = 10)
	public Date getAdInvalidDate() {
		return this.adInvalidDate;
	}

	public void setAdInvalidDate(Date adInvalidDate) {
		this.adInvalidDate = adInvalidDate;
	}

	@Column(name = "ad_invalid_time", nullable = false)
	public int getAdInvalidTime() {
		return this.adInvalidTime;
	}

	public void setAdInvalidTime(int adInvalidTime) {
		this.adInvalidTime = adInvalidTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_invalid_create_time", nullable = false, length = 19)
	public Date getAdInvalidCreateTime() {
		return this.adInvalidCreateTime;
	}

	public void setAdInvalidCreateTime(Date adInvalidCreateTime) {
		this.adInvalidCreateTime = adInvalidCreateTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_invalid_update_time", nullable = false, length = 19)
	public Date getAdInvalidUpdateTime() {
		return this.adInvalidUpdateTime;
	}

	public void setAdInvalidUpdateTime(Date adInvalidUpdateTime) {
		this.adInvalidUpdateTime = adInvalidUpdateTime;
	}

}
