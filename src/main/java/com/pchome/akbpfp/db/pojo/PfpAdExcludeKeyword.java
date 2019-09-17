package com.pchome.akbpfp.db.pojo;
// Generated 2019/3/6 �U�� 01:55:32 by Hibernate Tools 3.5.0.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * PfpAdExcludeKeyword generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_exclude_keyword")
public class PfpAdExcludeKeyword implements java.io.Serializable {

	private String adExcludeKeywordSeq;
	private PfpAdGroup pfpAdGroup;
	private String adExcludeKeyword;
	private int adExcludeKeywordStatus;
	private Date adExcludeKeywordCreateTime;
	private Date adExcludeKeywordUpdateTime;

	public PfpAdExcludeKeyword() {
	}

	public PfpAdExcludeKeyword(String adExcludeKeywordSeq, PfpAdGroup pfpAdGroup, String adExcludeKeyword,
			int adExcludeKeywordStatus, Date adExcludeKeywordCreateTime, Date adExcludeKeywordUpdateTime) {
		this.adExcludeKeywordSeq = adExcludeKeywordSeq;
		this.pfpAdGroup = pfpAdGroup;
		this.adExcludeKeyword = adExcludeKeyword;
		this.adExcludeKeywordStatus = adExcludeKeywordStatus;
		this.adExcludeKeywordCreateTime = adExcludeKeywordCreateTime;
		this.adExcludeKeywordUpdateTime = adExcludeKeywordUpdateTime;
	}

	@Id

	@Column(name = "ad_exclude_keyword_seq", unique = true, nullable = false, length = 20)
	public String getAdExcludeKeywordSeq() {
		return this.adExcludeKeywordSeq;
	}

	public void setAdExcludeKeywordSeq(String adExcludeKeywordSeq) {
		this.adExcludeKeywordSeq = adExcludeKeywordSeq;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ad_group_seq", nullable = false)
	public PfpAdGroup getPfpAdGroup() {
		return this.pfpAdGroup;
	}

	public void setPfpAdGroup(PfpAdGroup pfpAdGroup) {
		this.pfpAdGroup = pfpAdGroup;
	}

	@Column(name = "ad_exclude_keyword", nullable = false, length = 50)
	public String getAdExcludeKeyword() {
		return this.adExcludeKeyword;
	}

	public void setAdExcludeKeyword(String adExcludeKeyword) {
		this.adExcludeKeyword = adExcludeKeyword;
	}

	@Column(name = "ad_exclude_keyword_status", nullable = false)
	public int getAdExcludeKeywordStatus() {
		return this.adExcludeKeywordStatus;
	}

	public void setAdExcludeKeywordStatus(int adExcludeKeywordStatus) {
		this.adExcludeKeywordStatus = adExcludeKeywordStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_exclude_keyword_create_time", nullable = false, length = 19)
	public Date getAdExcludeKeywordCreateTime() {
		return this.adExcludeKeywordCreateTime;
	}

	public void setAdExcludeKeywordCreateTime(Date adExcludeKeywordCreateTime) {
		this.adExcludeKeywordCreateTime = adExcludeKeywordCreateTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_exclude_keyword_update_time", nullable = false, length = 19)
	public Date getAdExcludeKeywordUpdateTime() {
		return this.adExcludeKeywordUpdateTime;
	}

	public void setAdExcludeKeywordUpdateTime(Date adExcludeKeywordUpdateTime) {
		this.adExcludeKeywordUpdateTime = adExcludeKeywordUpdateTime;
	}

}