package com.pchome.akbpfp.db.pojo;
// Generated 2017/11/1 �U�� 02:28:26 by Hibernate Tools 3.4.0.CR1

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
 * PfpAdReport generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_report")
public class PfpAdReport implements java.io.Serializable {

	private Integer adReportSeq;
	private Date adPvclkDate;
	private String adPvclkDevice;
	private String adPvclkOs;
	private String customerInfoId;
	private String adActionSeq;
	private String adGroupSeq;
	private String adSeq;
	private String templateProductSeq;
	private int adType;
	private String adOperatingRule;
	private String adClkPriceType;
	private String adCategorySeq;
	private int adPv;
	private int adVpv;
	private int adClk;
	private int adView;
	private int adInvalidClk;
	private float adClkPrice;
	private float adInvalidClkPrice;
	private Date createDate;
	private Date updateDate;

	public PfpAdReport() {
	}

	public PfpAdReport(Date adPvclkDate, String adPvclkDevice, String adPvclkOs, String customerInfoId,
			String adActionSeq, String adGroupSeq, String adSeq, int adType, String adOperatingRule,
			String adClkPriceType, int adPv, int adVpv, int adClk, int adView, int adInvalidClk, float adClkPrice,
			float adInvalidClkPrice, Date createDate, Date updateDate) {
		this.adPvclkDate = adPvclkDate;
		this.adPvclkDevice = adPvclkDevice;
		this.adPvclkOs = adPvclkOs;
		this.customerInfoId = customerInfoId;
		this.adActionSeq = adActionSeq;
		this.adGroupSeq = adGroupSeq;
		this.adSeq = adSeq;
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
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public PfpAdReport(Date adPvclkDate, String adPvclkDevice, String adPvclkOs, String customerInfoId,
			String adActionSeq, String adGroupSeq, String adSeq, String templateProductSeq, int adType,
			String adOperatingRule, String adClkPriceType, String adCategorySeq, int adPv, int adVpv, int adClk,
			int adView, int adInvalidClk, float adClkPrice, float adInvalidClkPrice, Date createDate, Date updateDate) {
		this.adPvclkDate = adPvclkDate;
		this.adPvclkDevice = adPvclkDevice;
		this.adPvclkOs = adPvclkOs;
		this.customerInfoId = customerInfoId;
		this.adActionSeq = adActionSeq;
		this.adGroupSeq = adGroupSeq;
		this.adSeq = adSeq;
		this.templateProductSeq = templateProductSeq;
		this.adType = adType;
		this.adOperatingRule = adOperatingRule;
		this.adClkPriceType = adClkPriceType;
		this.adCategorySeq = adCategorySeq;
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

	@Column(name = "ad_report_seq", unique = true, nullable = false)
	public Integer getAdReportSeq() {
		return this.adReportSeq;
	}

	public void setAdReportSeq(Integer adReportSeq) {
		this.adReportSeq = adReportSeq;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ad_pvclk_date", nullable = false, length = 10)
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

	@Column(name = "ad_pvclk_os", nullable = false, length = 20)
	public String getAdPvclkOs() {
		return this.adPvclkOs;
	}

	public void setAdPvclkOs(String adPvclkOs) {
		this.adPvclkOs = adPvclkOs;
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

	@Column(name = "ad_seq", nullable = false, length = 20)
	public String getAdSeq() {
		return this.adSeq;
	}

	public void setAdSeq(String adSeq) {
		this.adSeq = adSeq;
	}

	@Column(name = "template_product_seq", length = 20)
	public String getTemplateProductSeq() {
		return this.templateProductSeq;
	}

	public void setTemplateProductSeq(String templateProductSeq) {
		this.templateProductSeq = templateProductSeq;
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

	@Column(name = "ad_category_seq", length = 3)
	public String getAdCategorySeq() {
		return this.adCategorySeq;
	}

	public void setAdCategorySeq(String adCategorySeq) {
		this.adCategorySeq = adCategorySeq;
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
	@Column(name = "create_date", nullable = false, length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", nullable = false, length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
