package com.pchome.akbpfp.db.pojo;
// Generated 2018/8/1 �U�� 02:45:05 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PfpCatalog generated by hbm2java
 */
@Entity
@Table(name = "pfp_catalog")
public class PfpCatalog implements java.io.Serializable {

	private String catalogSeq;
	private String catalogName;
	private String catalogType;
	private String pfpCustomerInfoId;
	private String catalogUploadType;
	private String catalogUploadContent;
	private int catalogProdNum;
	private String catalogImgShowType;
	private Date updateDate;
	private Date createDate;
	private Set<PfpCatalogProdEc> pfpCatalogProdEcs = new HashSet<PfpCatalogProdEc>(0);
	private Set<PfpCatalogUploadLog> pfpCatalogUploadLogs = new HashSet<PfpCatalogUploadLog>(0);

	public PfpCatalog() {
	}

	public PfpCatalog(String catalogSeq, String catalogName, String catalogType, String pfpCustomerInfoId,
			String catalogUploadType, String catalogUploadContent, int catalogProdNum, String catalogImgShowType,
			Date updateDate, Date createDate) {
		this.catalogSeq = catalogSeq;
		this.catalogName = catalogName;
		this.catalogType = catalogType;
		this.pfpCustomerInfoId = pfpCustomerInfoId;
		this.catalogUploadType = catalogUploadType;
		this.catalogUploadContent = catalogUploadContent;
		this.catalogProdNum = catalogProdNum;
		this.catalogImgShowType = catalogImgShowType;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	public PfpCatalog(String catalogSeq, String catalogName, String catalogType, String pfpCustomerInfoId,
			String catalogUploadType, String catalogUploadContent, int catalogProdNum, String catalogImgShowType,
			Date updateDate, Date createDate, Set<PfpCatalogProdEc> pfpCatalogProdEcs,
			Set<PfpCatalogUploadLog> pfpCatalogUploadLogs) {
		this.catalogSeq = catalogSeq;
		this.catalogName = catalogName;
		this.catalogType = catalogType;
		this.pfpCustomerInfoId = pfpCustomerInfoId;
		this.catalogUploadType = catalogUploadType;
		this.catalogUploadContent = catalogUploadContent;
		this.catalogProdNum = catalogProdNum;
		this.catalogImgShowType = catalogImgShowType;
		this.updateDate = updateDate;
		this.createDate = createDate;
		this.pfpCatalogProdEcs = pfpCatalogProdEcs;
		this.pfpCatalogUploadLogs = pfpCatalogUploadLogs;
	}

	@Id

	@Column(name = "catalog_seq", unique = true, nullable = false, length = 20)
	public String getCatalogSeq() {
		return this.catalogSeq;
	}

	public void setCatalogSeq(String catalogSeq) {
		this.catalogSeq = catalogSeq;
	}

	@Column(name = "catalog_name", nullable = false, length = 10)
	public String getCatalogName() {
		return this.catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	@Column(name = "catalog_type", nullable = false, length = 2)
	public String getCatalogType() {
		return this.catalogType;
	}

	public void setCatalogType(String catalogType) {
		this.catalogType = catalogType;
	}

	@Column(name = "pfp_customer_info_id", nullable = false, length = 20)
	public String getPfpCustomerInfoId() {
		return this.pfpCustomerInfoId;
	}

	public void setPfpCustomerInfoId(String pfpCustomerInfoId) {
		this.pfpCustomerInfoId = pfpCustomerInfoId;
	}

	@Column(name = "catalog_upload_type", nullable = false, length = 1)
	public String getCatalogUploadType() {
		return this.catalogUploadType;
	}

	public void setCatalogUploadType(String catalogUploadType) {
		this.catalogUploadType = catalogUploadType;
	}

	@Column(name = "catalog_upload_content", nullable = false, length = 2048)
	public String getCatalogUploadContent() {
		return this.catalogUploadContent;
	}

	public void setCatalogUploadContent(String catalogUploadContent) {
		this.catalogUploadContent = catalogUploadContent;
	}

	@Column(name = "catalog_prod_num", nullable = false)
	public int getCatalogProdNum() {
		return this.catalogProdNum;
	}

	public void setCatalogProdNum(int catalogProdNum) {
		this.catalogProdNum = catalogProdNum;
	}

	@Column(name = "catalog_img_show_type", nullable = false, length = 1)
	public String getCatalogImgShowType() {
		return this.catalogImgShowType;
	}

	public void setCatalogImgShowType(String catalogImgShowType) {
		this.catalogImgShowType = catalogImgShowType;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpCatalog")
	public Set<PfpCatalogProdEc> getPfpCatalogProdEcs() {
		return this.pfpCatalogProdEcs;
	}

	public void setPfpCatalogProdEcs(Set<PfpCatalogProdEc> pfpCatalogProdEcs) {
		this.pfpCatalogProdEcs = pfpCatalogProdEcs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpCatalog")
	public Set<PfpCatalogUploadLog> getPfpCatalogUploadLogs() {
		return this.pfpCatalogUploadLogs;
	}

	public void setPfpCatalogUploadLogs(Set<PfpCatalogUploadLog> pfpCatalogUploadLogs) {
		this.pfpCatalogUploadLogs = pfpCatalogUploadLogs;
	}

}
