package com.pchome.akbpfp.db.pojo;
// Generated 2018/9/28 �W�� 10:58:33 by Hibernate Tools 3.4.0.CR1

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
 * PfpCatalogProdEc generated by hbm2java
 */
@Entity
@Table(name = "pfp_catalog_prod_ec")
public class PfpCatalogProdEc implements java.io.Serializable {

	private Integer id;
	private PfpCatalog pfpCatalog;
	private String catalogProdSeq;
	private String ecName;
	private String ecTitle;
	private String ecImg;
	private String ecUrl;
	private int ecPrice;
	private int ecDiscountPrice;
	private String ecStockStatus;
	private String ecUseStatus;
	private String ecCategory;
	private String ecStatus;
	private String ecCheckStatus;
	private Date updateDate;
	private Date createDate;

	public PfpCatalogProdEc() {
	}

	public PfpCatalogProdEc(PfpCatalog pfpCatalog, String catalogProdSeq, String ecName, String ecTitle, String ecImg,
			String ecUrl, int ecPrice, int ecDiscountPrice, String ecStockStatus, String ecUseStatus, String ecCategory,
			String ecStatus, String ecCheckStatus, Date updateDate, Date createDate) {
		this.pfpCatalog = pfpCatalog;
		this.catalogProdSeq = catalogProdSeq;
		this.ecName = ecName;
		this.ecTitle = ecTitle;
		this.ecImg = ecImg;
		this.ecUrl = ecUrl;
		this.ecPrice = ecPrice;
		this.ecDiscountPrice = ecDiscountPrice;
		this.ecStockStatus = ecStockStatus;
		this.ecUseStatus = ecUseStatus;
		this.ecCategory = ecCategory;
		this.ecStatus = ecStatus;
		this.ecCheckStatus = ecCheckStatus;
		this.updateDate = updateDate;
		this.createDate = createDate;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "catalog_seq", nullable = false)
	public PfpCatalog getPfpCatalog() {
		return this.pfpCatalog;
	}

	public void setPfpCatalog(PfpCatalog pfpCatalog) {
		this.pfpCatalog = pfpCatalog;
	}

	@Column(name = "catalog_prod_seq", nullable = false, length = 1024)
	public String getCatalogProdSeq() {
		return this.catalogProdSeq;
	}

	public void setCatalogProdSeq(String catalogProdSeq) {
		this.catalogProdSeq = catalogProdSeq;
	}

	@Column(name = "ec_name", nullable = false, length = 1024)
	public String getEcName() {
		return this.ecName;
	}

	public void setEcName(String ecName) {
		this.ecName = ecName;
	}

	@Column(name = "ec_title", nullable = false, length = 1024)
	public String getEcTitle() {
		return this.ecTitle;
	}

	public void setEcTitle(String ecTitle) {
		this.ecTitle = ecTitle;
	}

	@Column(name = "ec_img", nullable = false, length = 2048)
	public String getEcImg() {
		return this.ecImg;
	}

	public void setEcImg(String ecImg) {
		this.ecImg = ecImg;
	}

	@Column(name = "ec_url", nullable = false, length = 2048)
	public String getEcUrl() {
		return this.ecUrl;
	}

	public void setEcUrl(String ecUrl) {
		this.ecUrl = ecUrl;
	}

	@Column(name = "ec_price", nullable = false)
	public int getEcPrice() {
		return this.ecPrice;
	}

	public void setEcPrice(int ecPrice) {
		this.ecPrice = ecPrice;
	}

	@Column(name = "ec_discount_price", nullable = false)
	public int getEcDiscountPrice() {
		return this.ecDiscountPrice;
	}

	public void setEcDiscountPrice(int ecDiscountPrice) {
		this.ecDiscountPrice = ecDiscountPrice;
	}

	@Column(name = "ec_stock_status", nullable = false, length = 1)
	public String getEcStockStatus() {
		return this.ecStockStatus;
	}

	public void setEcStockStatus(String ecStockStatus) {
		this.ecStockStatus = ecStockStatus;
	}

	@Column(name = "ec_use_status", nullable = false, length = 1)
	public String getEcUseStatus() {
		return this.ecUseStatus;
	}

	public void setEcUseStatus(String ecUseStatus) {
		this.ecUseStatus = ecUseStatus;
	}

	@Column(name = "ec_category", nullable = false, length = 50)
	public String getEcCategory() {
		return this.ecCategory;
	}

	public void setEcCategory(String ecCategory) {
		this.ecCategory = ecCategory;
	}

	@Column(name = "ec_status", nullable = false, length = 1)
	public String getEcStatus() {
		return this.ecStatus;
	}

	public void setEcStatus(String ecStatus) {
		this.ecStatus = ecStatus;
	}

	@Column(name = "ec_check_status", nullable = false, length = 1)
	public String getEcCheckStatus() {
		return this.ecCheckStatus;
	}

	public void setEcCheckStatus(String ecCheckStatus) {
		this.ecCheckStatus = ecCheckStatus;
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
