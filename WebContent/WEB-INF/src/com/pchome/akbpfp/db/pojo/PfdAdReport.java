package com.pchome.akbpfp.db.pojo;

// Generated Jul 14, 2015 12:06:31 PM by Hibernate Tools 3.4.0.CR1

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
 * PfdAdReport generated by hbm2java
 */
@Entity
@Table(name = "pfd_ad_report", catalog = "akb")
public class PfdAdReport implements java.io.Serializable {

    private Integer adReportSeq;
    private Date adPvclkDate;
    private String adPvclkDevice;
    private String pfdCustomerInfoId;
    private String pfdUserId;
    private String pfpCustomerInfoId;
    private String adSeq;
    private String adGroupSeq;
    private String adActionSeq;
    private String templateProductSeq;
    private int adType;
    private int adPv;
    private int adClk;
    private int adInvalidClk;
    private float adPvPrice;
    private float adClkPrice;
    private float adInvalidClkPrice;
    private Date createDate;
    private Date updateDate;

    public PfdAdReport() {
    }

    public PfdAdReport(Date adPvclkDate, String pfpCustomerInfoId,
	    String adSeq, String adGroupSeq, String adActionSeq,
	    String templateProductSeq, int adType, int adPv, int adClk,
	    int adInvalidClk, float adPvPrice, float adClkPrice,
	    float adInvalidClkPrice, Date createDate, Date updateDate) {
	this.adPvclkDate = adPvclkDate;
	this.pfpCustomerInfoId = pfpCustomerInfoId;
	this.adSeq = adSeq;
	this.adGroupSeq = adGroupSeq;
	this.adActionSeq = adActionSeq;
	this.templateProductSeq = templateProductSeq;
	this.adType = adType;
	this.adPv = adPv;
	this.adClk = adClk;
	this.adInvalidClk = adInvalidClk;
	this.adPvPrice = adPvPrice;
	this.adClkPrice = adClkPrice;
	this.adInvalidClkPrice = adInvalidClkPrice;
	this.createDate = createDate;
	this.updateDate = updateDate;
    }

    public PfdAdReport(Date adPvclkDate, String adPvclkDevice,
	    String pfdCustomerInfoId, String pfdUserId,
	    String pfpCustomerInfoId, String adSeq, String adGroupSeq,
	    String adActionSeq, String templateProductSeq, int adType,
	    int adPv, int adClk, int adInvalidClk, float adPvPrice,
	    float adClkPrice, float adInvalidClkPrice, Date createDate,
	    Date updateDate) {
	this.adPvclkDate = adPvclkDate;
	this.adPvclkDevice = adPvclkDevice;
	this.pfdCustomerInfoId = pfdCustomerInfoId;
	this.pfdUserId = pfdUserId;
	this.pfpCustomerInfoId = pfpCustomerInfoId;
	this.adSeq = adSeq;
	this.adGroupSeq = adGroupSeq;
	this.adActionSeq = adActionSeq;
	this.templateProductSeq = templateProductSeq;
	this.adType = adType;
	this.adPv = adPv;
	this.adClk = adClk;
	this.adInvalidClk = adInvalidClk;
	this.adPvPrice = adPvPrice;
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

    @Column(name = "ad_pvclk_device", length = 20)
    public String getAdPvclkDevice() {
	return this.adPvclkDevice;
    }

    public void setAdPvclkDevice(String adPvclkDevice) {
	this.adPvclkDevice = adPvclkDevice;
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

    @Column(name = "pfp_customer_info_id", nullable = false, length = 20)
    public String getPfpCustomerInfoId() {
	return this.pfpCustomerInfoId;
    }

    public void setPfpCustomerInfoId(String pfpCustomerInfoId) {
	this.pfpCustomerInfoId = pfpCustomerInfoId;
    }

    @Column(name = "ad_seq", nullable = false, length = 20)
    public String getAdSeq() {
	return this.adSeq;
    }

    public void setAdSeq(String adSeq) {
	this.adSeq = adSeq;
    }

    @Column(name = "ad_group_seq", nullable = false, length = 20)
    public String getAdGroupSeq() {
	return this.adGroupSeq;
    }

    public void setAdGroupSeq(String adGroupSeq) {
	this.adGroupSeq = adGroupSeq;
    }

    @Column(name = "ad_action_seq", nullable = false, length = 20)
    public String getAdActionSeq() {
	return this.adActionSeq;
    }

    public void setAdActionSeq(String adActionSeq) {
	this.adActionSeq = adActionSeq;
    }

    @Column(name = "template_product_seq", nullable = false, length = 20)
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

    @Column(name = "ad_clk_price", nullable = false, precision = 10)
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
