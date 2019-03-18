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
 * PfpAdPvclkProd generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_pvclk_prod")
public class PfpAdPvclkProd implements java.io.Serializable {

	private Integer adPvclkProdSeq;
	private String pfpCustomerInfoId;
	private String catalogSeq;
	private String catalogGroupSeq;
	private String adSeq;
	private String catalogProdSeq;
	private int catalogProdClk;
	private int catalogProdPv;
	private Date recordDate;
	private int recordTime;
	private Date updateDate;
	private Date createDate;

	public PfpAdPvclkProd() {
	}

	public PfpAdPvclkProd(String pfpCustomerInfoId, String catalogSeq, String catalogGroupSeq, String adSeq,
			String catalogProdSeq, int catalogProdClk, int catalogProdPv, Date recordDate, int recordTime,
			Date updateDate, Date createDate) {
		this.pfpCustomerInfoId = pfpCustomerInfoId;
		this.catalogSeq = catalogSeq;
		this.catalogGroupSeq = catalogGroupSeq;
		this.adSeq = adSeq;
		this.catalogProdSeq = catalogProdSeq;
		this.catalogProdClk = catalogProdClk;
		this.catalogProdPv = catalogProdPv;
		this.recordDate = recordDate;
		this.recordTime = recordTime;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "ad_pvclk_prod_seq", unique = true, nullable = false)
	public Integer getAdPvclkProdSeq() {
		return this.adPvclkProdSeq;
	}

	public void setAdPvclkProdSeq(Integer adPvclkProdSeq) {
		this.adPvclkProdSeq = adPvclkProdSeq;
	}

	@Column(name = "pfp_customer_info_id", nullable = false, length = 20)
	public String getPfpCustomerInfoId() {
		return this.pfpCustomerInfoId;
	}

	public void setPfpCustomerInfoId(String pfpCustomerInfoId) {
		this.pfpCustomerInfoId = pfpCustomerInfoId;
	}

	@Column(name = "catalog_seq", nullable = false, length = 20)
	public String getCatalogSeq() {
		return this.catalogSeq;
	}

	public void setCatalogSeq(String catalogSeq) {
		this.catalogSeq = catalogSeq;
	}

	@Column(name = "catalog_group_seq", nullable = false, length = 20)
	public String getCatalogGroupSeq() {
		return this.catalogGroupSeq;
	}

	public void setCatalogGroupSeq(String catalogGroupSeq) {
		this.catalogGroupSeq = catalogGroupSeq;
	}

	@Column(name = "ad_seq", nullable = false, length = 20)
	public String getAdSeq() {
		return this.adSeq;
	}

	public void setAdSeq(String adSeq) {
		this.adSeq = adSeq;
	}

	@Column(name = "catalog_prod_seq", nullable = false, length = 1024)
	public String getCatalogProdSeq() {
		return this.catalogProdSeq;
	}

	public void setCatalogProdSeq(String catalogProdSeq) {
		this.catalogProdSeq = catalogProdSeq;
	}

	@Column(name = "catalog_prod_clk", nullable = false)
	public int getCatalogProdClk() {
		return this.catalogProdClk;
	}

	public void setCatalogProdClk(int catalogProdClk) {
		this.catalogProdClk = catalogProdClk;
	}

	@Column(name = "catalog_prod_pv", nullable = false)
	public int getCatalogProdPv() {
		return this.catalogProdPv;
	}

	public void setCatalogProdPv(int catalogProdPv) {
		this.catalogProdPv = catalogProdPv;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "record_date", nullable = false, length = 10)
	public Date getRecordDate() {
		return this.recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	@Column(name = "record_time", nullable = false)
	public int getRecordTime() {
		return this.recordTime;
	}

	public void setRecordTime(int recordTime) {
		this.recordTime = recordTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", nullable = false, length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
