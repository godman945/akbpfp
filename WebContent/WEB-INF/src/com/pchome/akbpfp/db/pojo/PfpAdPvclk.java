package com.pchome.akbpfp.db.pojo;
// Generated 2017/11/1 �U�� 02:28:26 by Hibernate Tools 3.4.0.CR1

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
 * PfpAdPvclk generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_pvclk")
public class PfpAdPvclk implements java.io.Serializable {

	private Integer adPvclkSeq;
	private PfpAd pfpAd;
	private String customerInfoId;
	private String pfbxCustomerInfoId;
	private String pfbxPositionId;
	private String pfdCustomerInfoId;
	private String pfdUserId;
	private Integer payType;
	private String adGroupSeq;
	private String adActionSeq;
	private int adType;
	private Date adPvclkDate;
	private int adPvclkTime;
	private String sex;
	private String ageCode;
	private String timeCode;
	private String templateProductSeq;
	private String templateAdSeq;
	private String adPvclkPropClassify;
	private String adPvclkWebsiteClassify;
	private String adPvclkAudienceClassify;
	private String adUrl;
	private String styleNo;
	private String adPriceType;
	private int adVpv;
	private int adPv;
	private int adClk;
	private int adView;
	private int adInvalidClk;
	private float adPvPrice;
	private float adClkPrice;
	private float adInvalidClkPrice;
	private String adPvclkDevice;
	private String adPvclkOs;
	private String adPvclkBrand;
	private String adPvclkArea;
	private float adActionControlPrice;
	private float adActionMaxPrice;
	private Date adPvclkCreateTime;
	private Date adPvclkUpdateTime;

	public PfpAdPvclk() {
	}

	public PfpAdPvclk(PfpAd pfpAd, String customerInfoId, int adType, Date adPvclkDate, int adPvclkTime,
			String templateProductSeq, String templateAdSeq, String adPvclkPropClassify, String adUrl, String styleNo,
			String adPriceType, int adVpv, int adPv, int adClk, int adView, int adInvalidClk, float adPvPrice,
			float adClkPrice, float adInvalidClkPrice, String adPvclkDevice, String adPvclkOs, String adPvclkBrand,
			String adPvclkArea, float adActionControlPrice, float adActionMaxPrice, Date adPvclkCreateTime,
			Date adPvclkUpdateTime) {
		this.pfpAd = pfpAd;
		this.customerInfoId = customerInfoId;
		this.adType = adType;
		this.adPvclkDate = adPvclkDate;
		this.adPvclkTime = adPvclkTime;
		this.templateProductSeq = templateProductSeq;
		this.templateAdSeq = templateAdSeq;
		this.adPvclkPropClassify = adPvclkPropClassify;
		this.adUrl = adUrl;
		this.styleNo = styleNo;
		this.adPriceType = adPriceType;
		this.adVpv = adVpv;
		this.adPv = adPv;
		this.adClk = adClk;
		this.adView = adView;
		this.adInvalidClk = adInvalidClk;
		this.adPvPrice = adPvPrice;
		this.adClkPrice = adClkPrice;
		this.adInvalidClkPrice = adInvalidClkPrice;
		this.adPvclkDevice = adPvclkDevice;
		this.adPvclkOs = adPvclkOs;
		this.adPvclkBrand = adPvclkBrand;
		this.adPvclkArea = adPvclkArea;
		this.adActionControlPrice = adActionControlPrice;
		this.adActionMaxPrice = adActionMaxPrice;
		this.adPvclkCreateTime = adPvclkCreateTime;
		this.adPvclkUpdateTime = adPvclkUpdateTime;
	}

	public PfpAdPvclk(PfpAd pfpAd, String customerInfoId, String pfbxCustomerInfoId, String pfbxPositionId,
			String pfdCustomerInfoId, String pfdUserId, Integer payType, String adGroupSeq, String adActionSeq,
			int adType, Date adPvclkDate, int adPvclkTime, String sex, String ageCode, String timeCode,
			String templateProductSeq, String templateAdSeq, String adPvclkPropClassify, String adPvclkWebsiteClassify,
			String adPvclkAudienceClassify, String adUrl, String styleNo, String adPriceType, int adVpv, int adPv,
			int adClk, int adView, int adInvalidClk, float adPvPrice, float adClkPrice, float adInvalidClkPrice,
			String adPvclkDevice, String adPvclkOs, String adPvclkBrand, String adPvclkArea, float adActionControlPrice,
			float adActionMaxPrice, Date adPvclkCreateTime, Date adPvclkUpdateTime) {
		this.pfpAd = pfpAd;
		this.customerInfoId = customerInfoId;
		this.pfbxCustomerInfoId = pfbxCustomerInfoId;
		this.pfbxPositionId = pfbxPositionId;
		this.pfdCustomerInfoId = pfdCustomerInfoId;
		this.pfdUserId = pfdUserId;
		this.payType = payType;
		this.adGroupSeq = adGroupSeq;
		this.adActionSeq = adActionSeq;
		this.adType = adType;
		this.adPvclkDate = adPvclkDate;
		this.adPvclkTime = adPvclkTime;
		this.sex = sex;
		this.ageCode = ageCode;
		this.timeCode = timeCode;
		this.templateProductSeq = templateProductSeq;
		this.templateAdSeq = templateAdSeq;
		this.adPvclkPropClassify = adPvclkPropClassify;
		this.adPvclkWebsiteClassify = adPvclkWebsiteClassify;
		this.adPvclkAudienceClassify = adPvclkAudienceClassify;
		this.adUrl = adUrl;
		this.styleNo = styleNo;
		this.adPriceType = adPriceType;
		this.adVpv = adVpv;
		this.adPv = adPv;
		this.adClk = adClk;
		this.adView = adView;
		this.adInvalidClk = adInvalidClk;
		this.adPvPrice = adPvPrice;
		this.adClkPrice = adClkPrice;
		this.adInvalidClkPrice = adInvalidClkPrice;
		this.adPvclkDevice = adPvclkDevice;
		this.adPvclkOs = adPvclkOs;
		this.adPvclkBrand = adPvclkBrand;
		this.adPvclkArea = adPvclkArea;
		this.adActionControlPrice = adActionControlPrice;
		this.adActionMaxPrice = adActionMaxPrice;
		this.adPvclkCreateTime = adPvclkCreateTime;
		this.adPvclkUpdateTime = adPvclkUpdateTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ad_pvclk_seq", unique = true, nullable = false)
	public Integer getAdPvclkSeq() {
		return this.adPvclkSeq;
	}

	public void setAdPvclkSeq(Integer adPvclkSeq) {
		this.adPvclkSeq = adPvclkSeq;
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

	@Column(name = "pfbx_customer_info_id", length = 20)
	public String getPfbxCustomerInfoId() {
		return this.pfbxCustomerInfoId;
	}

	public void setPfbxCustomerInfoId(String pfbxCustomerInfoId) {
		this.pfbxCustomerInfoId = pfbxCustomerInfoId;
	}

	@Column(name = "pfbx_position_id", length = 20)
	public String getPfbxPositionId() {
		return this.pfbxPositionId;
	}

	public void setPfbxPositionId(String pfbxPositionId) {
		this.pfbxPositionId = pfbxPositionId;
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

	@Column(name = "ad_type", nullable = false)
	public int getAdType() {
		return this.adType;
	}

	public void setAdType(int adType) {
		this.adType = adType;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ad_pvclk_date", nullable = false, length = 10)
	public Date getAdPvclkDate() {
		return this.adPvclkDate;
	}

	public void setAdPvclkDate(Date adPvclkDate) {
		this.adPvclkDate = adPvclkDate;
	}

	@Column(name = "ad_pvclk_time", nullable = false)
	public int getAdPvclkTime() {
		return this.adPvclkTime;
	}

	public void setAdPvclkTime(int adPvclkTime) {
		this.adPvclkTime = adPvclkTime;
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

	@Column(name = "time_code", length = 1)
	public String getTimeCode() {
		return this.timeCode;
	}

	public void setTimeCode(String timeCode) {
		this.timeCode = timeCode;
	}

	@Column(name = "template_product_seq", nullable = false, length = 20)
	public String getTemplateProductSeq() {
		return this.templateProductSeq;
	}

	public void setTemplateProductSeq(String templateProductSeq) {
		this.templateProductSeq = templateProductSeq;
	}

	@Column(name = "template_ad_seq", nullable = false, length = 20)
	public String getTemplateAdSeq() {
		return this.templateAdSeq;
	}

	public void setTemplateAdSeq(String templateAdSeq) {
		this.templateAdSeq = templateAdSeq;
	}

	@Column(name = "ad_pvclk_prop_classify", nullable = false, length = 100)
	public String getAdPvclkPropClassify() {
		return this.adPvclkPropClassify;
	}

	public void setAdPvclkPropClassify(String adPvclkPropClassify) {
		this.adPvclkPropClassify = adPvclkPropClassify;
	}

	@Column(name = "ad_pvclk_website_classify", length = 20)
	public String getAdPvclkWebsiteClassify() {
		return this.adPvclkWebsiteClassify;
	}

	public void setAdPvclkWebsiteClassify(String adPvclkWebsiteClassify) {
		this.adPvclkWebsiteClassify = adPvclkWebsiteClassify;
	}

	@Column(name = "ad_pvclk_audience_classify", length = 20)
	public String getAdPvclkAudienceClassify() {
		return this.adPvclkAudienceClassify;
	}

	public void setAdPvclkAudienceClassify(String adPvclkAudienceClassify) {
		this.adPvclkAudienceClassify = adPvclkAudienceClassify;
	}

	@Column(name = "ad_url", nullable = false, length = 1000)
	public String getAdUrl() {
		return this.adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	@Column(name = "style_no", nullable = false, length = 30)
	public String getStyleNo() {
		return this.styleNo;
	}

	public void setStyleNo(String styleNo) {
		this.styleNo = styleNo;
	}

	@Column(name = "ad_price_type", nullable = false, length = 3)
	public String getAdPriceType() {
		return this.adPriceType;
	}

	public void setAdPriceType(String adPriceType) {
		this.adPriceType = adPriceType;
	}

	@Column(name = "ad_vpv", nullable = false)
	public int getAdVpv() {
		return this.adVpv;
	}

	public void setAdVpv(int adVpv) {
		this.adVpv = adVpv;
	}

	@Column(name = "ad_pv", nullable = false)
	public int getAdPv() {
		return this.adPv;
	}

	public void setAdPv(int adPv) {
		this.adPv = adPv;
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

	@Column(name = "ad_pv_price", nullable = false, precision = 10)
	public float getAdPvPrice() {
		return this.adPvPrice;
	}

	public void setAdPvPrice(float adPvPrice) {
		this.adPvPrice = adPvPrice;
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

	@Column(name = "ad_pvclk_brand", nullable = false, length = 20)
	public String getAdPvclkBrand() {
		return this.adPvclkBrand;
	}

	public void setAdPvclkBrand(String adPvclkBrand) {
		this.adPvclkBrand = adPvclkBrand;
	}

	@Column(name = "ad_pvclk_area", nullable = false, length = 20)
	public String getAdPvclkArea() {
		return this.adPvclkArea;
	}

	public void setAdPvclkArea(String adPvclkArea) {
		this.adPvclkArea = adPvclkArea;
	}

	@Column(name = "ad_action_control_price", nullable = false, precision = 10)
	public float getAdActionControlPrice() {
		return this.adActionControlPrice;
	}

	public void setAdActionControlPrice(float adActionControlPrice) {
		this.adActionControlPrice = adActionControlPrice;
	}

	@Column(name = "ad_action_max_price", nullable = false, precision = 10)
	public float getAdActionMaxPrice() {
		return this.adActionMaxPrice;
	}

	public void setAdActionMaxPrice(float adActionMaxPrice) {
		this.adActionMaxPrice = adActionMaxPrice;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_pvclk_create_time", nullable = false, length = 19)
	public Date getAdPvclkCreateTime() {
		return this.adPvclkCreateTime;
	}

	public void setAdPvclkCreateTime(Date adPvclkCreateTime) {
		this.adPvclkCreateTime = adPvclkCreateTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_pvclk_update_time", nullable = false, length = 19)
	public Date getAdPvclkUpdateTime() {
		return this.adPvclkUpdateTime;
	}

	public void setAdPvclkUpdateTime(Date adPvclkUpdateTime) {
		this.adPvclkUpdateTime = adPvclkUpdateTime;
	}

}
