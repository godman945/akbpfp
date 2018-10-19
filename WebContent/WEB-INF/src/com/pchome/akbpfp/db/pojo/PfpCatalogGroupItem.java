package com.pchome.akbpfp.db.pojo;
// Generated 2018/10/8 �U�� 02:19:47 by Hibernate Tools 3.4.0.CR1

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
 * PfpCatalogGroupItem generated by hbm2java
 */
@Entity
@Table(name = "pfp_catalog_group_item")
public class PfpCatalogGroupItem implements java.io.Serializable {

	private Integer catalogGroupItemSeq;
	private PfpCatalogGroup pfpCatalogGroup;
	private String catalogGroupItemField;
	private String catalogGroupItemCondition;
	private String catalogGroupItemValue;
	private Date updateDate;
	private Date createDate;

	public PfpCatalogGroupItem() {
	}

	public PfpCatalogGroupItem(PfpCatalogGroup pfpCatalogGroup, String catalogGroupItemField,
			String catalogGroupItemCondition, String catalogGroupItemValue, Date updateDate, Date createDate) {
		this.pfpCatalogGroup = pfpCatalogGroup;
		this.catalogGroupItemField = catalogGroupItemField;
		this.catalogGroupItemCondition = catalogGroupItemCondition;
		this.catalogGroupItemValue = catalogGroupItemValue;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "catalog_group_item_seq", unique = true, nullable = false)
	public Integer getCatalogGroupItemSeq() {
		return this.catalogGroupItemSeq;
	}

	public void setCatalogGroupItemSeq(Integer catalogGroupItemSeq) {
		this.catalogGroupItemSeq = catalogGroupItemSeq;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "catalog_group_seq", nullable = false)
	public PfpCatalogGroup getPfpCatalogGroup() {
		return this.pfpCatalogGroup;
	}

	public void setPfpCatalogGroup(PfpCatalogGroup pfpCatalogGroup) {
		this.pfpCatalogGroup = pfpCatalogGroup;
	}

	@Column(name = "catalog_group_item_field", nullable = false, length = 25)
	public String getCatalogGroupItemField() {
		return this.catalogGroupItemField;
	}

	public void setCatalogGroupItemField(String catalogGroupItemField) {
		this.catalogGroupItemField = catalogGroupItemField;
	}

	@Column(name = "catalog_group_item_condition", nullable = false, length = 20)
	public String getCatalogGroupItemCondition() {
		return this.catalogGroupItemCondition;
	}

	public void setCatalogGroupItemCondition(String catalogGroupItemCondition) {
		this.catalogGroupItemCondition = catalogGroupItemCondition;
	}

	@Column(name = "catalog_group_item_value", nullable = false, length = 1024)
	public String getCatalogGroupItemValue() {
		return this.catalogGroupItemValue;
	}

	public void setCatalogGroupItemValue(String catalogGroupItemValue) {
		this.catalogGroupItemValue = catalogGroupItemValue;
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
