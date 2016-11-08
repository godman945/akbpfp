package com.pchome.akbpfp.db.pojo;

// Generated 2016/11/8 �W�� 10:57:23 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PfpAdSysprice generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_sysprice", catalog = "akb")
public class PfpAdSysprice implements java.io.Serializable {

	private Integer id;
	private String adPoolSeq;
	private float sysprice;
	private int amount;

	public PfpAdSysprice() {
	}

	public PfpAdSysprice(String adPoolSeq, float sysprice, int amount) {
		this.adPoolSeq = adPoolSeq;
		this.sysprice = sysprice;
		this.amount = amount;
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

	@Column(name = "ad_pool_seq", nullable = false, length = 20)
	public String getAdPoolSeq() {
		return this.adPoolSeq;
	}

	public void setAdPoolSeq(String adPoolSeq) {
		this.adPoolSeq = adPoolSeq;
	}

	@Column(name = "sysprice", nullable = false, precision = 10)
	public float getSysprice() {
		return this.sysprice;
	}

	public void setSysprice(float sysprice) {
		this.sysprice = sysprice;
	}

	@Column(name = "amount", nullable = false)
	public int getAmount() {
		return this.amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
