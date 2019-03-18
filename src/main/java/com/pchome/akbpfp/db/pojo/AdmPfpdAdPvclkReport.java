package com.pchome.akbpfp.db.pojo;
// Generated 2019/3/6 �U�� 01:55:32 by Hibernate Tools 3.5.0.Final

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
 * AdmPfpdAdPvclkReport generated by hbm2java
 */
@Entity
@Table(name = "adm_pfpd_ad_pvclk_report")
public class AdmPfpdAdPvclkReport implements java.io.Serializable {

	private Integer adPvclkReportSeq;
	private String customerInfoId;
	private String pfbxCustomerInfoId;
	private String pfdCustomerInfoId;
	private Date adPvclkDate;
	private String adClkPriceType;
	private String adPvclkDevice;
	private String timeCode;
	private String adUrl;
	private String adUrlName;
	private int adPv;
	private int adVpv;
	private int adClk;
	private int adView;
	private int adInvalidClk;
	private float adClkPrice;
	private float adInvalidClkPrice;
	private int convertCount;
	private int convertPriceCount;
	private Date createDate;
	private Date updateTime;

	public AdmPfpdAdPvclkReport() {
	}

	public AdmPfpdAdPvclkReport(String customerInfoId, Date adPvclkDate, String adClkPriceType, int adPv, int adVpv,
			int adClk, int adView, int adInvalidClk, float adClkPrice, float adInvalidClkPrice, int convertCount,
			int convertPriceCount, Date createDate, Date updateTime) {
		this.customerInfoId = customerInfoId;
		this.adPvclkDate = adPvclkDate;
		this.adClkPriceType = adClkPriceType;
		this.adPv = adPv;
		this.adVpv = adVpv;
		this.adClk = adClk;
		this.adView = adView;
		this.adInvalidClk = adInvalidClk;
		this.adClkPrice = adClkPrice;
		this.adInvalidClkPrice = adInvalidClkPrice;
		this.convertCount = convertCount;
		this.convertPriceCount = convertPriceCount;
		this.createDate = createDate;
		this.updateTime = updateTime;
	}

	public AdmPfpdAdPvclkReport(String customerInfoId, String pfbxCustomerInfoId, String pfdCustomerInfoId,
			Date adPvclkDate, String adClkPriceType, String adPvclkDevice, String timeCode, String adUrl,
			String adUrlName, int adPv, int adVpv, int adClk, int adView, int adInvalidClk, float adClkPrice,
			float adInvalidClkPrice, int convertCount, int convertPriceCount, Date createDate, Date updateTime) {
		this.customerInfoId = customerInfoId;
		this.pfbxCustomerInfoId = pfbxCustomerInfoId;
		this.pfdCustomerInfoId = pfdCustomerInfoId;
		this.adPvclkDate = adPvclkDate;
		this.adClkPriceType = adClkPriceType;
		this.adPvclkDevice = adPvclkDevice;
		this.timeCode = timeCode;
		this.adUrl = adUrl;
		this.adUrlName = adUrlName;
		this.adPv = adPv;
		this.adVpv = adVpv;
		this.adClk = adClk;
		this.adView = adView;
		this.adInvalidClk = adInvalidClk;
		this.adClkPrice = adClkPrice;
		this.adInvalidClkPrice = adInvalidClkPrice;
		this.convertCount = convertCount;
		this.convertPriceCount = convertPriceCount;
		this.createDate = createDate;
		this.updateTime = updateTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ad_pvclk_report_seq", unique = true, nullable = false)
	public Integer getAdPvclkReportSeq() {
		return this.adPvclkReportSeq;
	}

	public void setAdPvclkReportSeq(Integer adPvclkReportSeq) {
		this.adPvclkReportSeq = adPvclkReportSeq;
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

	@Column(name = "pfd_customer_info_id", length = 20)
	public String getPfdCustomerInfoId() {
		return this.pfdCustomerInfoId;
	}

	public void setPfdCustomerInfoId(String pfdCustomerInfoId) {
		this.pfdCustomerInfoId = pfdCustomerInfoId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "ad_pvclk_date", nullable = false, length = 10)
	public Date getAdPvclkDate() {
		return this.adPvclkDate;
	}

	public void setAdPvclkDate(Date adPvclkDate) {
		this.adPvclkDate = adPvclkDate;
	}

	@Column(name = "ad_clk_price_type", nullable = false, length = 3)
	public String getAdClkPriceType() {
		return this.adClkPriceType;
	}

	public void setAdClkPriceType(String adClkPriceType) {
		this.adClkPriceType = adClkPriceType;
	}

	@Column(name = "ad_pvclk_device", length = 20)
	public String getAdPvclkDevice() {
		return this.adPvclkDevice;
	}

	public void setAdPvclkDevice(String adPvclkDevice) {
		this.adPvclkDevice = adPvclkDevice;
	}

	@Column(name = "time_code", length = 1)
	public String getTimeCode() {
		return this.timeCode;
	}

	public void setTimeCode(String timeCode) {
		this.timeCode = timeCode;
	}

	@Column(name = "ad_url", length = 1000)
	public String getAdUrl() {
		return this.adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	@Column(name = "ad_url_name", length = 1000)
	public String getAdUrlName() {
		return this.adUrlName;
	}

	public void setAdUrlName(String adUrlName) {
		this.adUrlName = adUrlName;
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

	@Column(name = "convert_count", nullable = false)
	public int getConvertCount() {
		return this.convertCount;
	}

	public void setConvertCount(int convertCount) {
		this.convertCount = convertCount;
	}

	@Column(name = "convert_price_count", nullable = false)
	public int getConvertPriceCount() {
		return this.convertPriceCount;
	}

	public void setConvertPriceCount(int convertPriceCount) {
		this.convertPriceCount = convertPriceCount;
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
	@Column(name = "update_time", nullable = false, length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
