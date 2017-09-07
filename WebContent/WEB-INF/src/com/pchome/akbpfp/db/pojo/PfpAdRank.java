package com.pchome.akbpfp.db.pojo;
// Generated 2017/9/6 �W�� 09:25:21 by Hibernate Tools 3.4.0.CR1

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
 * PfpAdRank generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_rank")
public class PfpAdRank implements java.io.Serializable {

	private Integer adRankSeq;
	private PfpAdKeyword pfpAdKeyword;
	private String customerInfoId;
	private int adType;
	private String adKeywordSearchStyle;
	private float adRankAvg;
	private Date adRankDate;
	private int adRankTime;
	private Date adRankCreateTime;
	private Date adRankUpdateTime;

	public PfpAdRank() {
	}

	public PfpAdRank(PfpAdKeyword pfpAdKeyword, String customerInfoId, int adType, float adRankAvg, Date adRankDate,
			int adRankTime, Date adRankCreateTime, Date adRankUpdateTime) {
		this.pfpAdKeyword = pfpAdKeyword;
		this.customerInfoId = customerInfoId;
		this.adType = adType;
		this.adRankAvg = adRankAvg;
		this.adRankDate = adRankDate;
		this.adRankTime = adRankTime;
		this.adRankCreateTime = adRankCreateTime;
		this.adRankUpdateTime = adRankUpdateTime;
	}

	public PfpAdRank(PfpAdKeyword pfpAdKeyword, String customerInfoId, int adType, String adKeywordSearchStyle,
			float adRankAvg, Date adRankDate, int adRankTime, Date adRankCreateTime, Date adRankUpdateTime) {
		this.pfpAdKeyword = pfpAdKeyword;
		this.customerInfoId = customerInfoId;
		this.adType = adType;
		this.adKeywordSearchStyle = adKeywordSearchStyle;
		this.adRankAvg = adRankAvg;
		this.adRankDate = adRankDate;
		this.adRankTime = adRankTime;
		this.adRankCreateTime = adRankCreateTime;
		this.adRankUpdateTime = adRankUpdateTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ad_rank_seq", unique = true, nullable = false)
	public Integer getAdRankSeq() {
		return this.adRankSeq;
	}

	public void setAdRankSeq(Integer adRankSeq) {
		this.adRankSeq = adRankSeq;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ad_keyword_seq", nullable = false)
	public PfpAdKeyword getPfpAdKeyword() {
		return this.pfpAdKeyword;
	}

	public void setPfpAdKeyword(PfpAdKeyword pfpAdKeyword) {
		this.pfpAdKeyword = pfpAdKeyword;
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

	@Column(name = "ad_keyword_search_style", length = 1)
	public String getAdKeywordSearchStyle() {
		return this.adKeywordSearchStyle;
	}

	public void setAdKeywordSearchStyle(String adKeywordSearchStyle) {
		this.adKeywordSearchStyle = adKeywordSearchStyle;
	}

	@Column(name = "ad_rank_avg", nullable = false, precision = 10)
	public float getAdRankAvg() {
		return this.adRankAvg;
	}

	public void setAdRankAvg(float adRankAvg) {
		this.adRankAvg = adRankAvg;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ad_rank_date", nullable = false, length = 0)
	public Date getAdRankDate() {
		return this.adRankDate;
	}

	public void setAdRankDate(Date adRankDate) {
		this.adRankDate = adRankDate;
	}

	@Column(name = "ad_rank_time", nullable = false)
	public int getAdRankTime() {
		return this.adRankTime;
	}

	public void setAdRankTime(int adRankTime) {
		this.adRankTime = adRankTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_rank_create_time", nullable = false, length = 0)
	public Date getAdRankCreateTime() {
		return this.adRankCreateTime;
	}

	public void setAdRankCreateTime(Date adRankCreateTime) {
		this.adRankCreateTime = adRankCreateTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_rank_update_time", nullable = false, length = 0)
	public Date getAdRankUpdateTime() {
		return this.adRankUpdateTime;
	}

	public void setAdRankUpdateTime(Date adRankUpdateTime) {
		this.adRankUpdateTime = adRankUpdateTime;
	}

}
