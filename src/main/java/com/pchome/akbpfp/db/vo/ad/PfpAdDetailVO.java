package com.pchome.akbpfp.db.vo.ad;

import java.util.Date;

public class PfpAdDetailVO {
	private String adDetailSeq;
	private String adSeq;
	private String adDetailId;
	private String adDetailContent;
	private String adPoolSeq;
	private String defineAdSeq;
	private String verifyFlag;
	private String verifyStatus;
	private String illegalKeyword;
	private Date adDetailCreateTime;
	private Date adDetailUpdateTime;

	public String getAdDetailSeq() {
		return adDetailSeq;
	}
	public void setAdDetailSeq(String adDetailSeq) {
		this.adDetailSeq = adDetailSeq;
	}
	public String getAdSeq() {
		return adSeq;
	}
	public void setAdSeq(String adSeq) {
		this.adSeq = adSeq;
	}
	public String getAdDetailId() {
		return adDetailId;
	}
	public void setAdDetailId(String adDetailId) {
		this.adDetailId = adDetailId;
	}
	public String getAdDetailContent() {
		return adDetailContent;
	}
	public void setAdDetailContent(String adDetailContent) {
		this.adDetailContent = adDetailContent;
	}
	public String getAdPoolSeq() {
		return adPoolSeq;
	}
	public void setAdPoolSeq(String adPoolSeq) {
		this.adPoolSeq = adPoolSeq;
	}
	public String getDefineAdSeq() {
		return defineAdSeq;
	}
	public void setDefineAdSeq(String defineAdSeq) {
		this.defineAdSeq = defineAdSeq;
	}
	public String getVerifyFlag() {
		return verifyFlag;
	}
	public void setVerifyFlag(String verifyFlag) {
		this.verifyFlag = verifyFlag;
	}
	public String getVerifyStatus() {
		return verifyStatus;
	}
	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}
	public String getIllegalKeyword() {
		return illegalKeyword;
	}
	public void setIllegalKeyword(String illegalKeyword) {
		this.illegalKeyword = illegalKeyword;
	}
	public Date getAdDetailCreateTime() {
		return adDetailCreateTime;
	}
	public void setAdDetailCreateTime(Date adDetailCreateTime) {
		this.adDetailCreateTime = adDetailCreateTime;
	}
	public Date getAdDetailUpdateTime() {
		return adDetailUpdateTime;
	}
	public void setAdDetailUpdateTime(Date adDetailUpdateTime) {
		this.adDetailUpdateTime = adDetailUpdateTime;
	}
}
