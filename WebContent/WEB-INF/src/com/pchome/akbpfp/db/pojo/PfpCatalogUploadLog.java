package com.pchome.akbpfp.db.pojo;
// Generated 2018/11/2 �W�� 09:57:28 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PfpCatalogUploadLog generated by hbm2java
 */
@Entity
@Table(name = "pfp_catalog_upload_log")
public class PfpCatalogUploadLog implements java.io.Serializable {

	private String catalogUploadLogSeq;
	private PfpCatalog pfpCatalog;
	private String updateWay;
	private String updateContent;
	private Date updateDatetime;
	private int errorNum;
	private int successNum;
	private Date updateDate;
	private Date createDate;
	private Set<PfpCatalogProdEcError> pfpCatalogProdEcErrors = new HashSet<PfpCatalogProdEcError>(0);
	private Set<PfpCatalogUploadErrLog> pfpCatalogUploadErrLogs = new HashSet<PfpCatalogUploadErrLog>(0);

	public PfpCatalogUploadLog() {
	}

	public PfpCatalogUploadLog(String catalogUploadLogSeq, PfpCatalog pfpCatalog, String updateWay,
			String updateContent, Date updateDatetime, int errorNum, int successNum, Date updateDate, Date createDate) {
		this.catalogUploadLogSeq = catalogUploadLogSeq;
		this.pfpCatalog = pfpCatalog;
		this.updateWay = updateWay;
		this.updateContent = updateContent;
		this.updateDatetime = updateDatetime;
		this.errorNum = errorNum;
		this.successNum = successNum;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	public PfpCatalogUploadLog(String catalogUploadLogSeq, PfpCatalog pfpCatalog, String updateWay,
			String updateContent, Date updateDatetime, int errorNum, int successNum, Date updateDate, Date createDate,
			Set<PfpCatalogProdEcError> pfpCatalogProdEcErrors, Set<PfpCatalogUploadErrLog> pfpCatalogUploadErrLogs) {
		this.catalogUploadLogSeq = catalogUploadLogSeq;
		this.pfpCatalog = pfpCatalog;
		this.updateWay = updateWay;
		this.updateContent = updateContent;
		this.updateDatetime = updateDatetime;
		this.errorNum = errorNum;
		this.successNum = successNum;
		this.updateDate = updateDate;
		this.createDate = createDate;
		this.pfpCatalogProdEcErrors = pfpCatalogProdEcErrors;
		this.pfpCatalogUploadErrLogs = pfpCatalogUploadErrLogs;
	}

	@Id

	@Column(name = "catalog_upload_log_seq", unique = true, nullable = false, length = 20)
	public String getCatalogUploadLogSeq() {
		return this.catalogUploadLogSeq;
	}

	public void setCatalogUploadLogSeq(String catalogUploadLogSeq) {
		this.catalogUploadLogSeq = catalogUploadLogSeq;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "catalog_seq", nullable = false)
	public PfpCatalog getPfpCatalog() {
		return this.pfpCatalog;
	}

	public void setPfpCatalog(PfpCatalog pfpCatalog) {
		this.pfpCatalog = pfpCatalog;
	}

	@Column(name = "update_way", nullable = false, length = 1)
	public String getUpdateWay() {
		return this.updateWay;
	}

	public void setUpdateWay(String updateWay) {
		this.updateWay = updateWay;
	}

	@Column(name = "update_content", nullable = false, length = 2048)
	public String getUpdateContent() {
		return this.updateContent;
	}

	public void setUpdateContent(String updateContent) {
		this.updateContent = updateContent;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_datetime", nullable = false, length = 19)
	public Date getUpdateDatetime() {
		return this.updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	@Column(name = "error_num", nullable = false)
	public int getErrorNum() {
		return this.errorNum;
	}

	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}

	@Column(name = "success_num", nullable = false)
	public int getSuccessNum() {
		return this.successNum;
	}

	public void setSuccessNum(int successNum) {
		this.successNum = successNum;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpCatalogUploadLog")
	public Set<PfpCatalogProdEcError> getPfpCatalogProdEcErrors() {
		return this.pfpCatalogProdEcErrors;
	}

	public void setPfpCatalogProdEcErrors(Set<PfpCatalogProdEcError> pfpCatalogProdEcErrors) {
		this.pfpCatalogProdEcErrors = pfpCatalogProdEcErrors;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpCatalogUploadLog")
	public Set<PfpCatalogUploadErrLog> getPfpCatalogUploadErrLogs() {
		return this.pfpCatalogUploadErrLogs;
	}

	public void setPfpCatalogUploadErrLogs(Set<PfpCatalogUploadErrLog> pfpCatalogUploadErrLogs) {
		this.pfpCatalogUploadErrLogs = pfpCatalogUploadErrLogs;
	}

}
