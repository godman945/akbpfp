package com.pchome.akbpfp.db.pojo;
// Generated 2018/10/5 �W�� 11:02:40 by Hibernate Tools 3.4.0.CR1

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
 * PfpCatalogLogoDetail generated by hbm2java
 */
@Entity
@Table(name = "pfp_catalog_logo_detail")
public class PfpCatalogLogoDetail implements java.io.Serializable {

	private Integer catalogLogoDetailSeq;
	private PfpCatalogLogo pfpCatalogLogo;
	private String catalogLogoHexColor;
	private Date updateDate;
	private Date createDate;

	public PfpCatalogLogoDetail() {
	}

	public PfpCatalogLogoDetail(PfpCatalogLogo pfpCatalogLogo, String catalogLogoHexColor, Date updateDate,
			Date createDate) {
		this.pfpCatalogLogo = pfpCatalogLogo;
		this.catalogLogoHexColor = catalogLogoHexColor;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "catalog_logo_seq", nullable = false)
	public PfpCatalogLogo getPfpCatalogLogo() {
		return this.pfpCatalogLogo;
	}

	public void setPfpCatalogLogo(PfpCatalogLogo pfpCatalogLogo) {
		this.pfpCatalogLogo = pfpCatalogLogo;
	}

	@Column(name = "catalog_logo_hex_color", nullable = false, length = 20)
	public String getCatalogLogoHexColor() {
		return this.catalogLogoHexColor;
	}

	public void setCatalogLogoHexColor(String catalogLogoHexColor) {
		this.catalogLogoHexColor = catalogLogoHexColor;
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
