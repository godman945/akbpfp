package com.pchome.akbpfp.db.pojo;
// Generated 2017/8/21 �U�� 01:20:59 by Hibernate Tools 3.4.0.CR1

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
 * PfpAdAgeReport generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_age_report", catalog = "akb_video")
public class PfpAdAgeReport implements java.io.Serializable {

	private Integer adAgeReportSeq;
	private Date adPvclkDate;
	private String adPvclkDevice;
	private String customerInfoId;
	private String adActionSeq;
	private String adGroupSeq;
	private int adType;
	private String adClkPriceType;
	private String sex;
	private String ageCode;
	private int adPv;
	private int adVpv;
	private int adClk;
	private int adView;
	private int adInvalidClk;
	private float adClkPrice;
	private float adInvalidClkPrice;
	private Date createDate;
	private Date updateDate;

	public PfpAdAgeReport() {
	}

	public PfpAdAgeReport(Date adPvclkDate, String adPvclkDevice, String customerInfoId, String adActionSeq,
			String adGroupSeq, int adType, String adClkPriceType, int adPv, int adVpv, int adClk, int adView,
			int adInvalidClk, float adClkPrice, float adInvalidClkPrice, Date createDate, Date updateDate) {
		this.adPvclkDate = adPvclkDate;
		this.adPvclkDevice = adPvclkDevice;
		this.customerInfoId = customerInfoId;
		this.adActionSeq = adActionSeq;
		this.adGroupSeq = adGroupSeq;
		this.adType = adType;
		this.adClkPriceType = adClkPriceType;
		this.adPv = adPv;
		this.adVpv = adVpv;
		this.adClk = adClk;
		this.adView = adView;
		this.adInvalidClk = adInvalidClk;
		this.adClkPrice = adClkPrice;
		this.adInvalidClkPrice = adInvalidClkPrice;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public PfpAdAgeReport(Date adPvclkDate, String adPvclkDevice, String customerInfoId, String adActionSeq,
			String adGroupSeq, int adType, String adClkPriceType, String sex, String ageCode, int adPv, int adVpv,
			int adClk, int adView, int adInvalidClk, float adClkPrice, float adInvalidClkPrice, Date createDate,
			Date updateDate) {
		this.adPvclkDate = adPvclkDate;
		this.adPvclkDevice = adPvclkDevice;
		this.customerInfoId = customerInfoId;
		this.adActionSeq = adActionSeq;
		this.adGroupSeq = adGroupSeq;
		this.adType = adType;
		this.adClkPriceType = adClkPriceType;
		this.sex = sex;
		this.ageCode = ageCode;
		this.adPv = adPv;
		this.adVpv = adVpv;
		this.adClk = adClk;
		this.adView = adView;
		this.adInvalidClk = adInvalidClk;
		this.adClkPrice = adClkPrice;
		this.adInvalidClkPrice = adInvalidClkPrice;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ad_age_report_seq", unique = true, nullable = false)
	public Integer getAdAgeReportSeq() {
		return this.adAgeReportSeq;
	}

	public void setAdAgeReportSeq(Integer adAgeReportSeq) {
		this.adAgeReportSeq = adAgeReportSeq;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ad_pvclk_date", nullable = false, length = 0)
	public Date getAdPvclkDate() {
		return this.adPvclkDate;
	}

	public void setAdPvclkDate(Date adPvclkDate) {
		this.adPvclkDate = adPvclkDate;
	}

	@Column(name = "ad_pvclk_device", nullable = false, length = 20)
	public String getAdPvclkDevice() {
		return this.adPvclkDevice;
	}

	public void setAdPvclkDevice(String adPvclkDevice) {
		this.adPvclkDevice = adPvclkDevice;
	}

	@Column(name = "customer_info_id", nullable = false, length = 20)
	public String getCustomerInfoId() {
		return this.customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	@Column(name = "ad_action_seq", nullable = false, length = 20)
	public String getAdActionSeq() {
		return this.adActionSeq;
	}

	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
	}

	@Column(name = "ad_group_seq", nullable = false, length = 20)
	public String getAdGroupSeq() {
		return this.adGroupSeq;
	}

	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}

	@Column(name = "ad_type", nullable = false)
	public int getAdType() {
		return this.adType;
	}

	public void setAdType(int adType) {
		this.adType = adType;
	}

	@Column(name = "ad_clk_price_type", nullable = false, length = 3)
	public String getAdClkPriceType() {
		return this.adClkPriceType;
	}

	public void setAdClkPriceType(String adClkPriceType) {
		this.adClkPriceType = adClkPriceType;
	}

	@Column(name = "sex", length = 1)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "age_code", length = 1)
	public String getAgeCode() {
		return this.ageCode;
	}

	public void setAgeCode(String ageCode) {
		this.ageCode = ageCode;
	}

	@Column(name = "ad_pv", nullable = false)
	public int getAdPv() {
		return this.adPv;
	}

	public void setAdPv(int adPv) {
		this.adPv = adPv;
	}

	@Column(name = "ad_vpv", nullable = false)
	public int getAdVpv() {
		return this.adVpv;
	}

	public void setAdVpv(int adVpv) {
		this.adVpv = adVpv;
	}

	@Column(name = "ad_clk", nullable = false)
	public int getAdClk() {
		return this.adClk;
	}

	public void setAdClk(int adClk) {
		this.adClk = adClk;
	}

	@Column(name = "ad_view", nullable = false)
	public int getAdView() {
		return this.adView;
	}

	public void setAdView(int adView) {
		this.adView = adView;
	}

	@Column(name = "ad_invalid_clk", nullable = false)
	public int getAdInvalidClk() {
		return this.adInvalidClk;
	}

	public void setAdInvalidClk(int adInvalidClk) {
		this.adInvalidClk = adInvalidClk;
	}

	@Column(name = "ad_clk_price", nullable = false, precision = 10, scale = 3)
	public float getAdClkPrice() {
		return this.adClkPrice;
	}

	public void setAdClkPrice(float adClkPrice) {
		this.adClkPrice = adClkPrice;
	}

	@Column(name = "ad_invalid_clk_price", nullable = false, precision = 10)
	public float getAdInvalidClkPrice() {
		return this.adInvalidClkPrice;
	}

	public void setAdInvalidClkPrice(float adInvalidClkPrice) {
		this.adInvalidClkPrice = adInvalidClkPrice;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, length = 0)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", nullable = false, length = 0)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
