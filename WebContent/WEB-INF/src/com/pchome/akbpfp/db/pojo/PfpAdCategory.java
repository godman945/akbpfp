package com.pchome.akbpfp.db.pojo;

// Generated 2016/9/7 �U�� 05:32:40 by Hibernate Tools 3.4.0.CR1

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
 * PfpAdCategory generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_category", catalog = "akb")
public class PfpAdCategory implements java.io.Serializable {

	private Integer seq;
	private String name;
	private Date createDate;

	public PfpAdCategory() {
	}

	public PfpAdCategory(String name, Date createDate) {
		this.name = name;
		this.createDate = createDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "seq", unique = true, nullable = false)
	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	@Column(name = "name", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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
