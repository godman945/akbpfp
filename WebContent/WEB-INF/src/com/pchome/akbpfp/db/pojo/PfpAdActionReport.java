package com.pchome.akbpfp.db.pojo;
// Generated 2017/9/6 �W�� 09:25:21 by Hibernate Tools 3.4.0.CR1

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
 * PfpAdActionReport generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_action_report")
public class PfpAdActionReport implements java.io.Serializable {

	private Integer adActionReportSeq;
	private Date adPvclkDate;
	private String adPvclkDevice;
	private String customerInfoId;
	private String adActionSeq;
	private String payType;
	private int adType;
	private String adOperatingRule;
	private String adClkPriceType;
	private int adPv;
	private int adVpv;
	private int adClk;
	private int adView;
	private int adInvalidClk;
	private float adClkPrice;
	private float adInvalidClkPrice;
	private float adActionMaxPrice;
	private int adActionCount;
	private Date createDate;
	private Date updateDate;

	public PfpAdActionReport() {
	}

	public PfpAdActionReport(Date adPvclkDate, String adPvclkDevice, String customerInfoId, String adActionSeq,
			String payType, int adType, String adOperatingRule, String adClkPriceType, int adPv, int adVpv, int adClk,
			int adView, int adInvalidClk, float adClkPrice, float adInvalidClkPrice, float adActionMaxPrice,
			int adActionCount, Date createDate, Date updateDate) {
		this.adPvclkDate = adPvclkDate;
		this.adPvclkDevice = adPvclkDevice;
		this.customerInfoId = customerInfoId;
		this.adActionSeq = adActionSeq;
		this.payType = payType;
		this.adType = adType;
		this.adOperatingRule = adOperatingRule;
		this.adClkPriceType = adClkPriceType;
		this.adPv = adPv;
		this.adVpv = adVpv;
		this.adClk = adClk;
		this.adView = adView;
		this.adInvalidClk = adInvalidClk;
		this.adClkPrice = adClkPrice;
		this.adInvalidClkPrice = adInvalidClkPrice;
		this.adActionMaxPrice = adActionMaxPrice;
		this.adActionCount = adActionCount;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ad_action_report_seq", unique = true, nullable = false)
	public Integer getAdActionReportSeq() {
		return this.adActionReportSeq;
	}

	public void setAdActionReportSeq(Integer adActionReportSeq) {
		this.adActionReportSeq = adActionReportSeq;
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

	@Column(name = "pay_type", nullable = false, length = 1)
	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Column(name = "ad_type", nullable = false)
	public int getAdType() {
		return this.adType;
	}

	public void setAdType(int adType) {
		this.adType = adType;
	}

	@Column(name = "ad_operating_rule", nullable = false, length = 5)
	public String getAdOperatingRule() {
		return this.adOperatingRule;
	}

	public void setAdOperatingRule(String adOperatingRule) {
		this.adOperatingRule = adOperatingRule;
	}

	@Column(name = "ad_clk_price_type", nullable = false, length = 3)
	public String getAdClkPriceType() {
		return this.adClkPriceType;
	}

	public void setAdClkPriceType(String adClkPriceType) {
		this.adClkPriceType = adClkPriceType;
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

	@Column(name = "ad_action_max_price", nullable = false, precision = 15)
	public float getAdActionMaxPrice() {
		return this.adActionMaxPrice;
	}

	public void setAdActionMaxPrice(float adActionMaxPrice) {
		this.adActionMaxPrice = adActionMaxPrice;
	}

	@Column(name = "ad_action_count", nullable = false)
	public int getAdActionCount() {
		return this.adActionCount;
	}

	public void setAdActionCount(int adActionCount) {
		this.adActionCount = adActionCount;
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
