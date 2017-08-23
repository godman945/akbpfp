package com.pchome.akbpfp.db.pojo;
// Generated 2017/8/22 �U�� 02:42:42 by Hibernate Tools 3.4.0.CR1

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
 * PfpReport generated by hbm2java
 */
@Entity
@Table(name = "pfp_report", catalog = "akb_video")
public class PfpReport implements java.io.Serializable {

	private Integer id;
	private Date reportTime;
	private int activateNum;
	private float activatePrice;
	private int customerNum;
	private float customerPrice;
	private int advanceActivateNum;
	private float advanceActivatePrice;
	private int advanceCustomerNum;
	private float advanceCustomerPrice;
	private int laterActivateNum;
	private float laterActivatePrice;
	private int laterCustomerNum;
	private float laterCustomerPrice;
	private float giftPrice;
	private float refundPrice;
	private float remainPrice;
	private int adNew;
	private int adNum;
	private int adReady;
	private int adDue;
	private int adPv;
	private int adClk;
	private float adClkPrice;
	private float cpc;
	private float cpm;
	private float ctr;
	private int adUnderMax;
	private float reachRate;
	private float overPrice;
	private int adInvalidClk;
	private float adInvalidClkPrice;
	private Date updateTime;
	private Date createTime;

	public PfpReport() {
	}

	public PfpReport(Date reportTime, int activateNum, float activatePrice, int customerNum, float customerPrice,
			int advanceActivateNum, float advanceActivatePrice, int advanceCustomerNum, float advanceCustomerPrice,
			int laterActivateNum, float laterActivatePrice, int laterCustomerNum, float laterCustomerPrice,
			float giftPrice, float refundPrice, float remainPrice, int adNew, int adNum, int adReady, int adDue,
			int adPv, int adClk, float adClkPrice, float cpc, float cpm, float ctr, int adUnderMax, float reachRate,
			float overPrice, int adInvalidClk, float adInvalidClkPrice, Date updateTime, Date createTime) {
		this.reportTime = reportTime;
		this.activateNum = activateNum;
		this.activatePrice = activatePrice;
		this.customerNum = customerNum;
		this.customerPrice = customerPrice;
		this.advanceActivateNum = advanceActivateNum;
		this.advanceActivatePrice = advanceActivatePrice;
		this.advanceCustomerNum = advanceCustomerNum;
		this.advanceCustomerPrice = advanceCustomerPrice;
		this.laterActivateNum = laterActivateNum;
		this.laterActivatePrice = laterActivatePrice;
		this.laterCustomerNum = laterCustomerNum;
		this.laterCustomerPrice = laterCustomerPrice;
		this.giftPrice = giftPrice;
		this.refundPrice = refundPrice;
		this.remainPrice = remainPrice;
		this.adNew = adNew;
		this.adNum = adNum;
		this.adReady = adReady;
		this.adDue = adDue;
		this.adPv = adPv;
		this.adClk = adClk;
		this.adClkPrice = adClkPrice;
		this.cpc = cpc;
		this.cpm = cpm;
		this.ctr = ctr;
		this.adUnderMax = adUnderMax;
		this.reachRate = reachRate;
		this.overPrice = overPrice;
		this.adInvalidClk = adInvalidClk;
		this.adInvalidClkPrice = adInvalidClkPrice;
		this.updateTime = updateTime;
		this.createTime = createTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "report_time", nullable = false, length = 0)
	public Date getReportTime() {
		return this.reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	@Column(name = "activate_num", nullable = false)
	public int getActivateNum() {
		return this.activateNum;
	}

	public void setActivateNum(int activateNum) {
		this.activateNum = activateNum;
	}

	@Column(name = "activate_price", nullable = false, precision = 10)
	public float getActivatePrice() {
		return this.activatePrice;
	}

	public void setActivatePrice(float activatePrice) {
		this.activatePrice = activatePrice;
	}

	@Column(name = "customer_num", nullable = false)
	public int getCustomerNum() {
		return this.customerNum;
	}

	public void setCustomerNum(int customerNum) {
		this.customerNum = customerNum;
	}

	@Column(name = "customer_price", nullable = false, precision = 10)
	public float getCustomerPrice() {
		return this.customerPrice;
	}

	public void setCustomerPrice(float customerPrice) {
		this.customerPrice = customerPrice;
	}

	@Column(name = "advance_activate_num", nullable = false)
	public int getAdvanceActivateNum() {
		return this.advanceActivateNum;
	}

	public void setAdvanceActivateNum(int advanceActivateNum) {
		this.advanceActivateNum = advanceActivateNum;
	}

	@Column(name = "advance_activate_price", nullable = false, precision = 10)
	public float getAdvanceActivatePrice() {
		return this.advanceActivatePrice;
	}

	public void setAdvanceActivatePrice(float advanceActivatePrice) {
		this.advanceActivatePrice = advanceActivatePrice;
	}

	@Column(name = "advance_customer_num", nullable = false)
	public int getAdvanceCustomerNum() {
		return this.advanceCustomerNum;
	}

	public void setAdvanceCustomerNum(int advanceCustomerNum) {
		this.advanceCustomerNum = advanceCustomerNum;
	}

	@Column(name = "advance_customer_price", nullable = false, precision = 10)
	public float getAdvanceCustomerPrice() {
		return this.advanceCustomerPrice;
	}

	public void setAdvanceCustomerPrice(float advanceCustomerPrice) {
		this.advanceCustomerPrice = advanceCustomerPrice;
	}

	@Column(name = "later_activate_num", nullable = false)
	public int getLaterActivateNum() {
		return this.laterActivateNum;
	}

	public void setLaterActivateNum(int laterActivateNum) {
		this.laterActivateNum = laterActivateNum;
	}

	@Column(name = "later_activate_price", nullable = false, precision = 10)
	public float getLaterActivatePrice() {
		return this.laterActivatePrice;
	}

	public void setLaterActivatePrice(float laterActivatePrice) {
		this.laterActivatePrice = laterActivatePrice;
	}

	@Column(name = "later_customer_num", nullable = false)
	public int getLaterCustomerNum() {
		return this.laterCustomerNum;
	}

	public void setLaterCustomerNum(int laterCustomerNum) {
		this.laterCustomerNum = laterCustomerNum;
	}

	@Column(name = "later_customer_price", nullable = false, precision = 10)
	public float getLaterCustomerPrice() {
		return this.laterCustomerPrice;
	}

	public void setLaterCustomerPrice(float laterCustomerPrice) {
		this.laterCustomerPrice = laterCustomerPrice;
	}

	@Column(name = "gift_price", nullable = false, precision = 10)
	public float getGiftPrice() {
		return this.giftPrice;
	}

	public void setGiftPrice(float giftPrice) {
		this.giftPrice = giftPrice;
	}

	@Column(name = "refund_price", nullable = false, precision = 10)
	public float getRefundPrice() {
		return this.refundPrice;
	}

	public void setRefundPrice(float refundPrice) {
		this.refundPrice = refundPrice;
	}

	@Column(name = "remain_price", nullable = false, precision = 10)
	public float getRemainPrice() {
		return this.remainPrice;
	}

	public void setRemainPrice(float remainPrice) {
		this.remainPrice = remainPrice;
	}

	@Column(name = "ad_new", nullable = false)
	public int getAdNew() {
		return this.adNew;
	}

	public void setAdNew(int adNew) {
		this.adNew = adNew;
	}

	@Column(name = "ad_num", nullable = false)
	public int getAdNum() {
		return this.adNum;
	}

	public void setAdNum(int adNum) {
		this.adNum = adNum;
	}

	@Column(name = "ad_ready", nullable = false)
	public int getAdReady() {
		return this.adReady;
	}

	public void setAdReady(int adReady) {
		this.adReady = adReady;
	}

	@Column(name = "ad_due", nullable = false)
	public int getAdDue() {
		return this.adDue;
	}

	public void setAdDue(int adDue) {
		this.adDue = adDue;
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

	@Column(name = "ad_clk_price", nullable = false, precision = 10)
	public float getAdClkPrice() {
		return this.adClkPrice;
	}

	public void setAdClkPrice(float adClkPrice) {
		this.adClkPrice = adClkPrice;
	}

	@Column(name = "cpc", nullable = false, precision = 10)
	public float getCpc() {
		return this.cpc;
	}

	public void setCpc(float cpc) {
		this.cpc = cpc;
	}

	@Column(name = "cpm", nullable = false, precision = 10)
	public float getCpm() {
		return this.cpm;
	}

	public void setCpm(float cpm) {
		this.cpm = cpm;
	}

	@Column(name = "ctr", nullable = false, precision = 10)
	public float getCtr() {
		return this.ctr;
	}

	public void setCtr(float ctr) {
		this.ctr = ctr;
	}

	@Column(name = "ad_under_max", nullable = false)
	public int getAdUnderMax() {
		return this.adUnderMax;
	}

	public void setAdUnderMax(int adUnderMax) {
		this.adUnderMax = adUnderMax;
	}

	@Column(name = "reach_rate", nullable = false, precision = 10)
	public float getReachRate() {
		return this.reachRate;
	}

	public void setReachRate(float reachRate) {
		this.reachRate = reachRate;
	}

	@Column(name = "over_price", nullable = false, precision = 10)
	public float getOverPrice() {
		return this.overPrice;
	}

	public void setOverPrice(float overPrice) {
		this.overPrice = overPrice;
	}

	@Column(name = "ad_invalid_clk", nullable = false)
	public int getAdInvalidClk() {
		return this.adInvalidClk;
	}

	public void setAdInvalidClk(int adInvalidClk) {
		this.adInvalidClk = adInvalidClk;
	}

	@Column(name = "ad_invalid_clk_price", nullable = false, precision = 10)
	public float getAdInvalidClkPrice() {
		return this.adInvalidClkPrice;
	}

	public void setAdInvalidClkPrice(float adInvalidClkPrice) {
		this.adInvalidClkPrice = adInvalidClkPrice;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", nullable = false, length = 0)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false, length = 0)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
