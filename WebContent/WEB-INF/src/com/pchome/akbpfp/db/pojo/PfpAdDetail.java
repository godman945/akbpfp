package com.pchome.akbpfp.db.pojo;
// Generated 2017/11/1 �U�� 02:28:26 by Hibernate Tools 3.4.0.CR1

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
 * PfpAdDetail generated by hbm2java
 */
@Entity
@Table(name = "pfp_ad_detail")
public class PfpAdDetail implements java.io.Serializable {

	private String adDetailSeq;
	private PfpAd pfpAd;
	private String adDetailId;
	private String adDetailContent;
	private String adPoolSeq;
	private String defineAdSeq;
	private String verifyFlag;
	private String verifyStatus;
	private Date sysVerifyTime;
	private Date userVerifyTime;
	private String illegalKeyword;
	private Date adDetailCreateTime;
	private Date adDetailUpdateTime;

	public PfpAdDetail() {
	}

	public PfpAdDetail(String adDetailSeq, PfpAd pfpAd, String adDetailId, String adDetailContent, String verifyFlag,
			String verifyStatus, Date adDetailCreateTime, Date adDetailUpdateTime) {
		this.adDetailSeq = adDetailSeq;
		this.pfpAd = pfpAd;
		this.adDetailId = adDetailId;
		this.adDetailContent = adDetailContent;
		this.verifyFlag = verifyFlag;
		this.verifyStatus = verifyStatus;
		this.adDetailCreateTime = adDetailCreateTime;
		this.adDetailUpdateTime = adDetailUpdateTime;
	}

	public PfpAdDetail(String adDetailSeq, PfpAd pfpAd, String adDetailId, String adDetailContent, String adPoolSeq,
			String defineAdSeq, String verifyFlag, String verifyStatus, Date sysVerifyTime, Date userVerifyTime,
			String illegalKeyword, Date adDetailCreateTime, Date adDetailUpdateTime) {
		this.adDetailSeq = adDetailSeq;
		this.pfpAd = pfpAd;
		this.adDetailId = adDetailId;
		this.adDetailContent = adDetailContent;
		this.adPoolSeq = adPoolSeq;
		this.defineAdSeq = defineAdSeq;
		this.verifyFlag = verifyFlag;
		this.verifyStatus = verifyStatus;
		this.sysVerifyTime = sysVerifyTime;
		this.userVerifyTime = userVerifyTime;
		this.illegalKeyword = illegalKeyword;
		this.adDetailCreateTime = adDetailCreateTime;
		this.adDetailUpdateTime = adDetailUpdateTime;
	}

	@Id

	@Column(name = "ad_detail_seq", unique = true, nullable = false, length = 20)
	public String getAdDetailSeq() {
		return this.adDetailSeq;
	}

	public void setAdDetailSeq(String adDetailSeq) {
		this.adDetailSeq = adDetailSeq;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ad_seq", nullable = false)
	public PfpAd getPfpAd() {
		return this.pfpAd;
	}

	public void setPfpAd(PfpAd pfpAd) {
		this.pfpAd = pfpAd;
	}

	@Column(name = "ad_detail_id", nullable = false, length = 20)
	public String getAdDetailId() {
		return this.adDetailId;
	}

	public void setAdDetailId(String adDetailId) {
		this.adDetailId = adDetailId;
	}

	@Column(name = "ad_detail_content", nullable = false, length = 2048)
	public String getAdDetailContent() {
		return this.adDetailContent;
	}

	public void setAdDetailContent(String adDetailContent) {
		this.adDetailContent = adDetailContent;
	}

	@Column(name = "ad_pool_seq", length = 20)
	public String getAdPoolSeq() {
		return this.adPoolSeq;
	}

	public void setAdPoolSeq(String adPoolSeq) {
		this.adPoolSeq = adPoolSeq;
	}

	@Column(name = "define_ad_seq", length = 20)
	public String getDefineAdSeq() {
		return this.defineAdSeq;
	}

	public void setDefineAdSeq(String defineAdSeq) {
		this.defineAdSeq = defineAdSeq;
	}

	@Column(name = "verify_flag", nullable = false, length = 1)
	public String getVerifyFlag() {
		return this.verifyFlag;
	}

	public void setVerifyFlag(String verifyFlag) {
		this.verifyFlag = verifyFlag;
	}

	@Column(name = "verify_status", nullable = false, length = 1)
	public String getVerifyStatus() {
		return this.verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sys_verify_time", length = 19)
	public Date getSysVerifyTime() {
		return this.sysVerifyTime;
	}

	public void setSysVerifyTime(Date sysVerifyTime) {
		this.sysVerifyTime = sysVerifyTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "user_verify_time", length = 19)
	public Date getUserVerifyTime() {
		return this.userVerifyTime;
	}

	public void setUserVerifyTime(Date userVerifyTime) {
		this.userVerifyTime = userVerifyTime;
	}

	@Column(name = "illegal_keyword", length = 50)
	public String getIllegalKeyword() {
		return this.illegalKeyword;
	}

	public void setIllegalKeyword(String illegalKeyword) {
		this.illegalKeyword = illegalKeyword;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_detail_create_time", nullable = false, length = 19)
	public Date getAdDetailCreateTime() {
		return this.adDetailCreateTime;
	}

	public void setAdDetailCreateTime(Date adDetailCreateTime) {
		this.adDetailCreateTime = adDetailCreateTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ad_detail_update_time", nullable = false, length = 19)
	public Date getAdDetailUpdateTime() {
		return this.adDetailUpdateTime;
	}

	public void setAdDetailUpdateTime(Date adDetailUpdateTime) {
		this.adDetailUpdateTime = adDetailUpdateTime;
	}

}
