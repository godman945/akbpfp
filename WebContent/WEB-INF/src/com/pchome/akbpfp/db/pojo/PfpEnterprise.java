package com.pchome.akbpfp.db.pojo;

// Generated 2015/10/21 �U�� 02:37:05 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PfpEnterprise generated by hbm2java
 */
@Entity
@Table(name = "pfp_enterprise", catalog = "akb")
public class PfpEnterprise implements java.io.Serializable {

	private String taxId;
	private String companyName;
	private Date createDate;

	public PfpEnterprise() {
	}

	public PfpEnterprise(String taxId, String companyName, Date createDate) {
		this.taxId = taxId;
		this.companyName = companyName;
		this.createDate = createDate;
	}

	@Id
	@Column(name = "tax_id", unique = true, nullable = false, length = 8)
	public String getTaxId() {
		return this.taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	@Column(name = "company_name", nullable = false, length = 30)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
