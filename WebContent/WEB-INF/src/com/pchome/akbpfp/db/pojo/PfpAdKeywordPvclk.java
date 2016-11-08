package com.pchome.akbpfp.db.pojo;

// Generated 2016/11/8 �W�� 10:57:23 by Hibernate Tools 3.4.0.CR1

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
 * PfpAdKeywordPvclk generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_keyword_pvclk", catalog = "akb")
public class PfpAdKeywordPvclk implements java.io.Serializable {

	private Integer adKeywordPvclkSeq;
	private PfpAdKeyword pfpAdKeyword;
	private String customerInfoId;
	private String pfdCustomerInfoId;
	private String pfdUserId;
	private Integer payType;
	private String adGroupSeq;
	private String adActionSeq;
	private int adKeywordType;
	private String adKeywordSearchStyle;
	private String adKeywordPvclkPropClassify;
	private Date adKeywordPvclkDate;
	private int adKeywordPvclkTime;
	private int adKeywordPv;
	private int adKeywordClk;
	private int adKeywordInvalidClk;
	private float adKeywordPvPrice;
	private float adKeywordClkPrice;
	private float adKeywordInvalidClkPrice;
	private String adKeywordPvclkDevice;
	private String adKeywordPvclkOs;
	private String adKeywordPvclkBrand;
	private String adKeywordPvclkArea;
	private Date adKeywordPvclkCreateTime;
	private Date adKeywordPvclkUpdateTime;

	public PfpAdKeywordPvclk() {
	}

	public PfpAdKeywordPvclk(PfpAdKeyword pfpAdKeyword, String customerInfoId,
			int adKeywordType, String adKeywordPvclkPropClassify,
			Date adKeywordPvclkDate, int adKeywordPvclkTime, int adKeywordPv,
			int adKeywordClk, int adKeywordInvalidClk, float adKeywordPvPrice,
			float adKeywordClkPrice, float adKeywordInvalidClkPrice,
			Date adKeywordPvclkCreateTime, Date adKeywordPvclkUpdateTime) {
		this.pfpAdKeyword = pfpAdKeyword;
		this.customerInfoId = customerInfoId;
		this.adKeywordType = adKeywordType;
		this.adKeywordPvclkPropClassify = adKeywordPvclkPropClassify;
		this.adKeywordPvclkDate = adKeywordPvclkDate;
		this.adKeywordPvclkTime = adKeywordPvclkTime;
		this.adKeywordPv = adKeywordPv;
		this.adKeywordClk = adKeywordClk;
		this.adKeywordInvalidClk = adKeywordInvalidClk;
		this.adKeywordPvPrice = adKeywordPvPrice;
		this.adKeywordClkPrice = adKeywordClkPrice;
		this.adKeywordInvalidClkPrice = adKeywordInvalidClkPrice;
		this.adKeywordPvclkCreateTime = adKeywordPvclkCreateTime;
		this.adKeywordPvclkUpdateTime = adKeywordPvclkUpdateTime;
	}

	public PfpAdKeywordPvclk(PfpAdKeyword pfpAdKeyword, String customerInfoId,
			String pfdCustomerInfoId, String pfdUserId, Integer payType,
			String adGroupSeq, String adActionSeq, int adKeywordType,
			String adKeywordSearchStyle, String adKeywordPvclkPropClassify,
			Date adKeywordPvclkDate, int adKeywordPvclkTime, int adKeywordPv,
			int adKeywordClk, int adKeywordInvalidClk, float adKeywordPvPrice,
			float adKeywordClkPrice, float adKeywordInvalidClkPrice,
			String adKeywordPvclkDevice, String adKeywordPvclkOs,
			String adKeywordPvclkBrand, String adKeywordPvclkArea,
			Date adKeywordPvclkCreateTime, Date adKeywordPvclkUpdateTime) {
		this.pfpAdKeyword = pfpAdKeyword;
		this.customerInfoId = customerInfoId;
		this.pfdCustomerInfoId = pfdCustomerInfoId;
		this.pfdUserId = pfdUserId;
		this.payType = payType;
		this.adGroupSeq = adGroupSeq;
		this.adActionSeq = adActionSeq;
		this.adKeywordType = adKeywordType;
		this.adKeywordSearchStyle = adKeywordSearchStyle;
		this.adKeywordPvclkPropClassify = adKeywordPvclkPropClassify;
		this.adKeywordPvclkDate = adKeywordPvclkDate;
		this.adKeywordPvclkTime = adKeywordPvclkTime;
		this.adKeywordPv = adKeywordPv;
		this.adKeywordClk = adKeywordClk;
		this.adKeywordInvalidClk = adKeywordInvalidClk;
		this.adKeywordPvPrice = adKeywordPvPrice;
		this.adKeywordClkPrice = adKeywordClkPrice;
		this.adKeywordInvalidClkPrice = adKeywordInvalidClkPrice;
		this.adKeywordPvclkDevice = adKeywordPvclkDevice;
		this.adKeywordPvclkOs = adKeywordPvclkOs;
		this.adKeywordPvclkBrand = adKeywordPvclkBrand;
		this.adKeywordPvclkArea = adKeywordPvclkArea;
		this.adKeywordPvclkCreateTime = adKeywordPvclkCreateTime;
		this.adKeywordPvclkUpdateTime = adKeywordPvclkUpdateTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ad_keyword_pvclk_seq", unique = true, nullable = false)
	public Integer getAdKeywordPvclkSeq() {
		return this.adKeywordPvclkSeq;
	}

	public void setAdKeywordPvclkSeq(Integer adKeywordPvclkSeq) {
		this.adKeywordPvclkSeq = adKeywordPvclkSeq;
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

	@Column(name = "pfd_customer_info_id", length = 20)
	public String getPfdCustomerInfoId() {
		return this.pfdCustomerInfoId;
	}

	public void setPfdCustomerInfoId(String pfdCustomerInfoId) {
		this.pfdCustomerInfoId = pfdCustomerInfoId;
	}

	@Column(name = "pfd_user_id", length = 20)
	public String getPfdUserId() {
		return this.pfdUserId;
	}

	public void setPfdUserId(String pfdUserId) {
		this.pfdUserId = pfdUserId;
	}

	@Column(name = "pay_type")
	public Integer getPayType() {
		return this.payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	@Column(name = "ad_group_seq", length = 20)
	public String getAdGroupSeq() {
		return this.adGroupSeq;
	}

	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}

	@Column(name = "ad_action_seq", length = 20)
	public String getAdActionSeq() {
		return this.adActionSeq;
	}

	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
	}

	@Column(name = "ad_keyword_type", nullable = false)
	public int getAdKeywordType() {
		return this.adKeywordType;
	}

	public void setAdKeywordType(int adKeywordType) {
		this.adKeywordType = adKeywordType;
	}

	@Column(name = "ad_keyword_search_style", length = 1)
	public String getAdKeywordSearchStyle() {
		return this.adKeywordSearchStyle;
	}

	public void setAdKeywordSearchStyle(String adKeywordSearchStyle) {
		this.adKeywordSearchStyle = adKeywordSearchStyle;
	}

	@Column(name = "ad_keyword_pvclk_prop_classify", nullable = false, length = 100)
	public String getAdKeywordPvclkPropClassify() {
		return this.adKeywordPvclkPropClassify;
	}

	public void setAdKeywordPvclkPropClassify(String adKeywordPvclkPropClassify) {
		this.adKeywordPvclkPropClassify = adKeywordPvclkPropClassify;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ad_keyword_pvclk_date", nullable = false, length = 10)
	public Date getAdKeywordPvclkDate() {
		return this.adKeywordPvclkDate;
	}

	public void setAdKeywordPvclkDate(Date adKeywordPvclkDate) {
		this.adKeywordPvclkDate = adKeywordPvclkDate;
	}

	@Column(name = "ad_keyword_pvclk_time", nullable = false)
	public int getAdKeywordPvclkTime() {
		return this.adKeywordPvclkTime;
	}

	public void setAdKeywordPvclkTime(int adKeywordPvclkTime) {
		this.adKeywordPvclkTime = adKeywordPvclkTime;
	}

	@Column(name = "ad_keyword_pv", nullable = false)
	public int getAdKeywordPv() {
		return this.adKeywordPv;
	}

	public void setAdKeywordPv(int adKeywordPv) {
		this.adKeywordPv = adKeywordPv;
	}

	@Column(name = "ad_keyword_clk", nullable = false)
	public int getAdKeywordClk() {
		return this.adKeywordClk;
	}

	public void setAdKeywordClk(int adKeywordClk) {
		this.adKeywordClk = adKeywordClk;
	}

	@Column(name = "ad_keyword_invalid_clk", nullable = false)
	public int getAdKeywordInvalidClk() {
		return this.adKeywordInvalidClk;
	}

	public void setAdKeywordInvalidClk(int adKeywordInvalidClk) {
		this.adKeywordInvalidClk = adKeywordInvalidClk;
	}

	@Column(name = "ad_keyword_pv_price", nullable = false, precision = 10)
	public float getAdKeywordPvPrice() {
		return this.adKeywordPvPrice;
	}

	public void setAdKeywordPvPrice(float adKeywordPvPrice) {
		this.adKeywordPvPrice = adKeywordPvPrice;
	}

	@Column(name = "ad_keyword_clk_price", nullable = false, precision = 10)
	public float getAdKeywordClkPrice() {
		return this.adKeywordClkPrice;
	}

	public void setAdKeywordClkPrice(float adKeywordClkPrice) {
		this.adKeywordClkPrice = adKeywordClkPrice;
	}

	@Column(name = "ad_keyword_invalid_clk_price", nullable = false, precision = 10)
	public float getAdKeywordInvalidClkPrice() {
		return this.adKeywordInvalidClkPrice;
	}

	public void setAdKeywordInvalidClkPrice(float adKeywordInvalidClkPrice) {
		this.adKeywordInvalidClkPrice = adKeywordInvalidClkPrice;
	}

	@Column(name = "ad_keyword_pvclk_device", length = 20)
	public String getAdKeywordPvclkDevice() {
		return this.adKeywordPvclkDevice;
	}

	public void setAdKeywordPvclkDevice(String adKeywordPvclkDevice) {
		this.adKeywordPvclkDevice = adKeywordPvclkDevice;
	}

	@Column(name = "ad_keyword_pvclk_os", length = 20)
	public String getAdKeywordPvclkOs() {
		return this.adKeywordPvclkOs;
	}

	public void setAdKeywordPvclkOs(String adKeywordPvclkOs) {
		this.adKeywordPvclkOs = adKeywordPvclkOs;
	}

	@Column(name = "ad_keyword_pvclk_brand", length = 20)
	public String getAdKeywordPvclkBrand() {
		return this.adKeywordPvclkBrand;
	}

	public void setAdKeywordPvclkBrand(String adKeywordPvclkBrand) {
		this.adKeywordPvclkBrand = adKeywordPvclkBrand;
	}

	@Column(name = "ad_keyword_pvclk_area", length = 20)
	public String getAdKeywordPvclkArea() {
		return this.adKeywordPvclkArea;
	}

	public void setAdKeywordPvclkArea(String adKeywordPvclkArea) {
		this.adKeywordPvclkArea = adKeywordPvclkArea;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_keyword_pvclk_create_time", nullable = false, length = 19)
	public Date getAdKeywordPvclkCreateTime() {
		return this.adKeywordPvclkCreateTime;
	}

	public void setAdKeywordPvclkCreateTime(Date adKeywordPvclkCreateTime) {
		this.adKeywordPvclkCreateTime = adKeywordPvclkCreateTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_keyword_pvclk_update_time", nullable = false, length = 19)
	public Date getAdKeywordPvclkUpdateTime() {
		return this.adKeywordPvclkUpdateTime;
	}

	public void setAdKeywordPvclkUpdateTime(Date adKeywordPvclkUpdateTime) {
		this.adKeywordPvclkUpdateTime = adKeywordPvclkUpdateTime;
	}

}
