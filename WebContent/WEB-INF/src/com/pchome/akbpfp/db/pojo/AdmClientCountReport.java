package com.pchome.akbpfp.db.pojo;
// Generated 2017/8/21 �W�� 10:28:10 by Hibernate Tools 3.4.0.CR1

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
 * AdmClientCountReport generated by hbm2java
 */
@Entity
@Table(name = "adm_client_count_report", catalog = "akb_video")
public class AdmClientCountReport implements java.io.Serializable {

	private Integer countSeq;
	private Date countDate;
	private int pfpClientCount;
	private int pfdClientCount;
	private float pfpAdClkPrice;
	private float pfdAdClkPrice;
	private float pfpAdActionMaxPrice;
	private float pfdAdActionMaxPrice;
	private int pfpAdCount;
	private int pfdAdCount;
	private int adPv;
	private int adClk;
	private int adInvalidClk;
	private float adClkPrice;
	private float adInvalidClkPrice;
	private float lossCost;
	private float pfpSave;
	private float pfpFree;
	private float pfpPostpaid;
	private float pfbSave;
	private float pfbFree;
	private float pfbPostpaid;
	private float pfdSave;
	private float pfdFree;
	private float pfdPostpaid;
	private Date createDate;

	public AdmClientCountReport() {
	}

	public AdmClientCountReport(Date countDate, int pfpClientCount, int pfdClientCount, float pfpAdClkPrice,
			float pfdAdClkPrice, float pfpAdActionMaxPrice, float pfdAdActionMaxPrice, int pfpAdCount, int pfdAdCount,
			int adPv, int adClk, int adInvalidClk, float adClkPrice, float adInvalidClkPrice, float lossCost,
			float pfpSave, float pfpFree, float pfpPostpaid, float pfbSave, float pfbFree, float pfbPostpaid,
			float pfdSave, float pfdFree, float pfdPostpaid, Date createDate) {
		this.countDate = countDate;
		this.pfpClientCount = pfpClientCount;
		this.pfdClientCount = pfdClientCount;
		this.pfpAdClkPrice = pfpAdClkPrice;
		this.pfdAdClkPrice = pfdAdClkPrice;
		this.pfpAdActionMaxPrice = pfpAdActionMaxPrice;
		this.pfdAdActionMaxPrice = pfdAdActionMaxPrice;
		this.pfpAdCount = pfpAdCount;
		this.pfdAdCount = pfdAdCount;
		this.adPv = adPv;
		this.adClk = adClk;
		this.adInvalidClk = adInvalidClk;
		this.adClkPrice = adClkPrice;
		this.adInvalidClkPrice = adInvalidClkPrice;
		this.lossCost = lossCost;
		this.pfpSave = pfpSave;
		this.pfpFree = pfpFree;
		this.pfpPostpaid = pfpPostpaid;
		this.pfbSave = pfbSave;
		this.pfbFree = pfbFree;
		this.pfbPostpaid = pfbPostpaid;
		this.pfdSave = pfdSave;
		this.pfdFree = pfdFree;
		this.pfdPostpaid = pfdPostpaid;
		this.createDate = createDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "count_seq", unique = true, nullable = false)
	public Integer getCountSeq() {
		return this.countSeq;
	}

	public void setCountSeq(Integer countSeq) {
		this.countSeq = countSeq;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "count_date", nullable = false, length = 0)
	public Date getCountDate() {
		return this.countDate;
	}

	public void setCountDate(Date countDate) {
		this.countDate = countDate;
	}

	@Column(name = "pfp_client_count", nullable = false)
	public int getPfpClientCount() {
		return this.pfpClientCount;
	}

	public void setPfpClientCount(int pfpClientCount) {
		this.pfpClientCount = pfpClientCount;
	}

	@Column(name = "pfd_client_count", nullable = false)
	public int getPfdClientCount() {
		return this.pfdClientCount;
	}

	public void setPfdClientCount(int pfdClientCount) {
		this.pfdClientCount = pfdClientCount;
	}

	@Column(name = "pfp_ad_clk_price", nullable = false, precision = 10, scale = 3)
	public float getPfpAdClkPrice() {
		return this.pfpAdClkPrice;
	}

	public void setPfpAdClkPrice(float pfpAdClkPrice) {
		this.pfpAdClkPrice = pfpAdClkPrice;
	}

	@Column(name = "pfd_ad_clk_price", nullable = false, precision = 10, scale = 3)
	public float getPfdAdClkPrice() {
		return this.pfdAdClkPrice;
	}

	public void setPfdAdClkPrice(float pfdAdClkPrice) {
		this.pfdAdClkPrice = pfdAdClkPrice;
	}

	@Column(name = "pfp_ad_action_max_price", nullable = false, precision = 16)
	public float getPfpAdActionMaxPrice() {
		return this.pfpAdActionMaxPrice;
	}

	public void setPfpAdActionMaxPrice(float pfpAdActionMaxPrice) {
		this.pfpAdActionMaxPrice = pfpAdActionMaxPrice;
	}

	@Column(name = "pfd_ad_action_max_price", nullable = false, precision = 16)
	public float getPfdAdActionMaxPrice() {
		return this.pfdAdActionMaxPrice;
	}

	public void setPfdAdActionMaxPrice(float pfdAdActionMaxPrice) {
		this.pfdAdActionMaxPrice = pfdAdActionMaxPrice;
	}

	@Column(name = "pfp_ad_count", nullable = false)
	public int getPfpAdCount() {
		return this.pfpAdCount;
	}

	public void setPfpAdCount(int pfpAdCount) {
		this.pfpAdCount = pfpAdCount;
	}

	@Column(name = "pfd_ad_count", nullable = false)
	public int getPfdAdCount() {
		return this.pfdAdCount;
	}

	public void setPfdAdCount(int pfdAdCount) {
		this.pfdAdCount = pfdAdCount;
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

	@Column(name = "loss_cost", nullable = false, precision = 10)
	public float getLossCost() {
		return this.lossCost;
	}

	public void setLossCost(float lossCost) {
		this.lossCost = lossCost;
	}

	@Column(name = "pfp_save", nullable = false, precision = 10)
	public float getPfpSave() {
		return this.pfpSave;
	}

	public void setPfpSave(float pfpSave) {
		this.pfpSave = pfpSave;
	}

	@Column(name = "pfp_free", nullable = false, precision = 10)
	public float getPfpFree() {
		return this.pfpFree;
	}

	public void setPfpFree(float pfpFree) {
		this.pfpFree = pfpFree;
	}

	@Column(name = "pfp_postpaid", nullable = false, precision = 10)
	public float getPfpPostpaid() {
		return this.pfpPostpaid;
	}

	public void setPfpPostpaid(float pfpPostpaid) {
		this.pfpPostpaid = pfpPostpaid;
	}

	@Column(name = "pfb_save", nullable = false, precision = 10)
	public float getPfbSave() {
		return this.pfbSave;
	}

	public void setPfbSave(float pfbSave) {
		this.pfbSave = pfbSave;
	}

	@Column(name = "pfb_free", nullable = false, precision = 10)
	public float getPfbFree() {
		return this.pfbFree;
	}

	public void setPfbFree(float pfbFree) {
		this.pfbFree = pfbFree;
	}

	@Column(name = "pfb_postpaid", nullable = false, precision = 10)
	public float getPfbPostpaid() {
		return this.pfbPostpaid;
	}

	public void setPfbPostpaid(float pfbPostpaid) {
		this.pfbPostpaid = pfbPostpaid;
	}

	@Column(name = "pfd_save", nullable = false, precision = 10)
	public float getPfdSave() {
		return this.pfdSave;
	}

	public void setPfdSave(float pfdSave) {
		this.pfdSave = pfdSave;
	}

	@Column(name = "pfd_free", nullable = false, precision = 10)
	public float getPfdFree() {
		return this.pfdFree;
	}

	public void setPfdFree(float pfdFree) {
		this.pfdFree = pfdFree;
	}

	@Column(name = "pfd_postpaid", nullable = false, precision = 10)
	public float getPfdPostpaid() {
		return this.pfdPostpaid;
	}

	public void setPfdPostpaid(float pfdPostpaid) {
		this.pfdPostpaid = pfdPostpaid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, length = 0)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
