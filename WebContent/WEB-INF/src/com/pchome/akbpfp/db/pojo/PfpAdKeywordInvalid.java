package com.pchome.akbpfp.db.pojo;
// Generated 2017/9/22 �W�� 10:31:43 by Hibernate Tools 3.4.0.CR1

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
 * PfpAdKeywordInvalid generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_keyword_invalid")
public class PfpAdKeywordInvalid implements java.io.Serializable {

	private Integer adKeywordInvalidSeq;
	private PfpAdKeyword pfpAdKeyword;
	private String customerInfoId;
	private int adKeywordType;
	private int maliceType;
	private int adKeywordInvalidClk;
	private float adKeywordInvalidClkPrice;
	private Date adKeywordInvalidDate;
	private int adKeywordInvalidTime;
	private Date adKeywordInvalidCreateTime;
	private Date adKeywordInvalidUpdateTime;

	public PfpAdKeywordInvalid() {
	}

	public PfpAdKeywordInvalid(PfpAdKeyword pfpAdKeyword, String customerInfoId, int adKeywordType, int maliceType,
			int adKeywordInvalidClk, float adKeywordInvalidClkPrice, Date adKeywordInvalidDate,
			int adKeywordInvalidTime, Date adKeywordInvalidCreateTime, Date adKeywordInvalidUpdateTime) {
		this.pfpAdKeyword = pfpAdKeyword;
		this.customerInfoId = customerInfoId;
		this.adKeywordType = adKeywordType;
		this.maliceType = maliceType;
		this.adKeywordInvalidClk = adKeywordInvalidClk;
		this.adKeywordInvalidClkPrice = adKeywordInvalidClkPrice;
		this.adKeywordInvalidDate = adKeywordInvalidDate;
		this.adKeywordInvalidTime = adKeywordInvalidTime;
		this.adKeywordInvalidCreateTime = adKeywordInvalidCreateTime;
		this.adKeywordInvalidUpdateTime = adKeywordInvalidUpdateTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ad_keyword_invalid_seq", unique = true, nullable = false)
	public Integer getAdKeywordInvalidSeq() {
		return this.adKeywordInvalidSeq;
	}

	public void setAdKeywordInvalidSeq(Integer adKeywordInvalidSeq) {
		this.adKeywordInvalidSeq = adKeywordInvalidSeq;
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

	@Column(name = "ad_keyword_type", nullable = false)
	public int getAdKeywordType() {
		return this.adKeywordType;
	}

	public void setAdKeywordType(int adKeywordType) {
		this.adKeywordType = adKeywordType;
	}

	@Column(name = "malice_type", nullable = false)
	public int getMaliceType() {
		return this.maliceType;
	}

	public void setMaliceType(int maliceType) {
		this.maliceType = maliceType;
	}

	@Column(name = "ad_keyword_invalid_clk", nullable = false)
	public int getAdKeywordInvalidClk() {
		return this.adKeywordInvalidClk;
	}

	public void setAdKeywordInvalidClk(int adKeywordInvalidClk) {
		this.adKeywordInvalidClk = adKeywordInvalidClk;
	}

	@Column(name = "ad_keyword_invalid_clk_price", nullable = false, precision = 10)
	public float getAdKeywordInvalidClkPrice() {
		return this.adKeywordInvalidClkPrice;
	}

	public void setAdKeywordInvalidClkPrice(float adKeywordInvalidClkPrice) {
		this.adKeywordInvalidClkPrice = adKeywordInvalidClkPrice;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ad_keyword_invalid_date", nullable = false, length = 0)
	public Date getAdKeywordInvalidDate() {
		return this.adKeywordInvalidDate;
	}

	public void setAdKeywordInvalidDate(Date adKeywordInvalidDate) {
		this.adKeywordInvalidDate = adKeywordInvalidDate;
	}

	@Column(name = "ad_keyword_invalid_time", nullable = false)
	public int getAdKeywordInvalidTime() {
		return this.adKeywordInvalidTime;
	}

	public void setAdKeywordInvalidTime(int adKeywordInvalidTime) {
		this.adKeywordInvalidTime = adKeywordInvalidTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_keyword_invalid_create_time", nullable = false, length = 0)
	public Date getAdKeywordInvalidCreateTime() {
		return this.adKeywordInvalidCreateTime;
	}

	public void setAdKeywordInvalidCreateTime(Date adKeywordInvalidCreateTime) {
		this.adKeywordInvalidCreateTime = adKeywordInvalidCreateTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_keyword_invalid_update_time", nullable = false, length = 0)
	public Date getAdKeywordInvalidUpdateTime() {
		return this.adKeywordInvalidUpdateTime;
	}

	public void setAdKeywordInvalidUpdateTime(Date adKeywordInvalidUpdateTime) {
		this.adKeywordInvalidUpdateTime = adKeywordInvalidUpdateTime;
	}

}
