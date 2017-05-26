package com.pchome.akbpfp.db.pojo;

// Generated 2017/5/26 �W�� 09:41:24 by Hibernate Tools 3.4.0.CR1

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
 * PfpSyspriceRate generated by hbm2java
 */
@Entity
@Table(name = "pfp_sysprice_rate", catalog = "akb")
public class PfpSyspriceRate implements java.io.Serializable {

	private Integer syspriceRateSeq;
	private String adPoolSeq;
	private float kernelPrice;
	private int syspriceRatePv;
	private int syspriceRateClk;
	private Date syspriceRateDate;
	private int syspriceRateTime;
	private Date syspriceRateCreateTime;
	private Date syspriceRateUpdateTime;

	public PfpSyspriceRate() {
	}

	public PfpSyspriceRate(String adPoolSeq, float kernelPrice,
			int syspriceRatePv, int syspriceRateClk, Date syspriceRateDate,
			int syspriceRateTime, Date syspriceRateCreateTime,
			Date syspriceRateUpdateTime) {
		this.adPoolSeq = adPoolSeq;
		this.kernelPrice = kernelPrice;
		this.syspriceRatePv = syspriceRatePv;
		this.syspriceRateClk = syspriceRateClk;
		this.syspriceRateDate = syspriceRateDate;
		this.syspriceRateTime = syspriceRateTime;
		this.syspriceRateCreateTime = syspriceRateCreateTime;
		this.syspriceRateUpdateTime = syspriceRateUpdateTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "sysprice_rate_seq", unique = true, nullable = false)
	public Integer getSyspriceRateSeq() {
		return this.syspriceRateSeq;
	}

	public void setSyspriceRateSeq(Integer syspriceRateSeq) {
		this.syspriceRateSeq = syspriceRateSeq;
	}

	@Column(name = "ad_pool_seq", nullable = false, length = 20)
	public String getAdPoolSeq() {
		return this.adPoolSeq;
	}

	public void setAdPoolSeq(String adPoolSeq) {
		this.adPoolSeq = adPoolSeq;
	}

	@Column(name = "kernel_price", nullable = false, precision = 10)
	public float getKernelPrice() {
		return this.kernelPrice;
	}

	public void setKernelPrice(float kernelPrice) {
		this.kernelPrice = kernelPrice;
	}

	@Column(name = "sysprice_rate_pv", nullable = false)
	public int getSyspriceRatePv() {
		return this.syspriceRatePv;
	}

	public void setSyspriceRatePv(int syspriceRatePv) {
		this.syspriceRatePv = syspriceRatePv;
	}

	@Column(name = "sysprice_rate_clk", nullable = false)
	public int getSyspriceRateClk() {
		return this.syspriceRateClk;
	}

	public void setSyspriceRateClk(int syspriceRateClk) {
		this.syspriceRateClk = syspriceRateClk;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "sysprice_rate_date", nullable = false, length = 10)
	public Date getSyspriceRateDate() {
		return this.syspriceRateDate;
	}

	public void setSyspriceRateDate(Date syspriceRateDate) {
		this.syspriceRateDate = syspriceRateDate;
	}

	@Column(name = "sysprice_rate_time", nullable = false)
	public int getSyspriceRateTime() {
		return this.syspriceRateTime;
	}

	public void setSyspriceRateTime(int syspriceRateTime) {
		this.syspriceRateTime = syspriceRateTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sysprice_rate_create_time", nullable = false, length = 19)
	public Date getSyspriceRateCreateTime() {
		return this.syspriceRateCreateTime;
	}

	public void setSyspriceRateCreateTime(Date syspriceRateCreateTime) {
		this.syspriceRateCreateTime = syspriceRateCreateTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sysprice_rate_update_time", nullable = false, length = 19)
	public Date getSyspriceRateUpdateTime() {
		return this.syspriceRateUpdateTime;
	}

	public void setSyspriceRateUpdateTime(Date syspriceRateUpdateTime) {
		this.syspriceRateUpdateTime = syspriceRateUpdateTime;
	}

}
