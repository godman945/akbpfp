package com.pchome.akbpfp.db.pojo;
// Generated 2018/9/27 �W�� 09:32:12 by Hibernate Tools 3.4.0.CR1

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
 * PfpCatalogLogoDetail generated by hbm2java
 */
@Entity
@Table(name = "pfp_catalog_logo_detail")
public class PfpCatalogLogoDetail implements java.io.Serializable {

	private Integer catalogLogoDetailSeq;
	private String pfpCustomerInfoId;
	private String catalogLogoRgb;
	private Date updateDate;
	private Date createDate;

	public PfpCatalogLogoDetail() {
	}

	public PfpCatalogLogoDetail(String pfpCustomerInfoId, String catalogLogoRgb, Date updateDate, Date createDate) {
		this.pfpCustomerInfoId = pfpCustomerInfoId;
		this.catalogLogoRgb = catalogLogoRgb;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "catalog_logo_detail_seq", unique = true, nullable = false)
	public Integer getCatalogLogoDetailSeq() {
		return this.catalogLogoDetailSeq;
	}

	public void setCatalogLogoDetailSeq(Integer catalogLogoDetailSeq) {
		this.catalogLogoDetailSeq = catalogLogoDetailSeq;
	}

	@Column(name = "pfp_customer_info_id", nullable = false, length = 20)
	public String getPfpCustomerInfoId() {
		return this.pfpCustomerInfoId;
	}

	public void setPfpCustomerInfoId(String pfpCustomerInfoId) {
		this.pfpCustomerInfoId = pfpCustomerInfoId;
	}

	@Column(name = "catalog_logo_rgb", nullable = false, length = 20)
	public String getCatalogLogoRgb() {
		return this.catalogLogoRgb;
	}

	public void setCatalogLogoRgb(String catalogLogoRgb) {
		this.catalogLogoRgb = catalogLogoRgb;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", nullable = false, length = 0)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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
