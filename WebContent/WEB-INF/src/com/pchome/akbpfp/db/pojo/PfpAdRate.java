package com.pchome.akbpfp.db.pojo;

// Generated 2016/9/7 �U�� 05:32:40 by Hibernate Tools 3.4.0.CR1

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
 * PfpAdRate generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_rate", catalog = "akb")
public class PfpAdRate implements java.io.Serializable {

	private Long adRateId;
	private PfpAd pfpAd;
	private String customerInfoId;
	private int adType;
	private long adPv;
	private long adTotalPv;
	private Date recordDate;
	private int recordTime;
	private Date updateDate;
	private Date createDate;

	public PfpAdRate() {
	}

	public PfpAdRate(PfpAd pfpAd, String customerInfoId, int adType, long adPv,
			long adTotalPv, Date recordDate, int recordTime, Date updateDate,
			Date createDate) {
		this.pfpAd = pfpAd;
		this.customerInfoId = customerInfoId;
		this.adType = adType;
		this.adPv = adPv;
		this.adTotalPv = adTotalPv;
		this.recordDate = recordDate;
		this.recordTime = recordTime;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ad_rate_id", unique = true, nullable = false)
	public Long getAdRateId() {
		return this.adRateId;
	}

	public void setAdRateId(Long adRateId) {
		this.adRateId = adRateId;
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

	@Column(name = "ad_pv", nullable = false)
	public long getAdPv() {
		return this.adPv;
	}

	public void setAdPv(long adPv) {
		this.adPv = adPv;
	}

	@Column(name = "ad_total_pv", nullable = false)
	public long getAdTotalPv() {
		return this.adTotalPv;
	}

	public void setAdTotalPv(long adTotalPv) {
		this.adTotalPv = adTotalPv;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "record_date", nullable = false, length = 10)
	public Date getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	@Column(name = "record_time", nullable = false)
	public int getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(int recordTime) {
		this.recordTime = recordTime;
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
