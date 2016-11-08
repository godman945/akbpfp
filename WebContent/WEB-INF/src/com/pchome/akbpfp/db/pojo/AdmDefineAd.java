package com.pchome.akbpfp.db.pojo;

// Generated 2016/11/8 �W�� 10:57:23 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * AdmDefineAd generated by hbm2java
 */
@Entity
@Table(name = "adm_define_ad", catalog = "akb")
public class AdmDefineAd implements java.io.Serializable {

	private String defineAdSeq;
	private String defineAdId;
	private String defineAdName;
	private int defineAdType;
	private String defineAdWidth;
	private String defineAdHeight;
	private String defineAdFile;
	private String adPoolSeq;

	public AdmDefineAd() {
	}

	public AdmDefineAd(String defineAdSeq, String defineAdId,
			String defineAdName, int defineAdType, String defineAdWidth,
			String defineAdHeight, String defineAdFile) {
		this.defineAdSeq = defineAdSeq;
		this.defineAdId = defineAdId;
		this.defineAdName = defineAdName;
		this.defineAdType = defineAdType;
		this.defineAdWidth = defineAdWidth;
		this.defineAdHeight = defineAdHeight;
		this.defineAdFile = defineAdFile;
	}

	public AdmDefineAd(String defineAdSeq, String defineAdId,
			String defineAdName, int defineAdType, String defineAdWidth,
			String defineAdHeight, String defineAdFile, String adPoolSeq) {
		this.defineAdSeq = defineAdSeq;
		this.defineAdId = defineAdId;
		this.defineAdName = defineAdName;
		this.defineAdType = defineAdType;
		this.defineAdWidth = defineAdWidth;
		this.defineAdHeight = defineAdHeight;
		this.defineAdFile = defineAdFile;
		this.adPoolSeq = adPoolSeq;
	}

	@Id
	@Column(name = "define_ad_seq", unique = true, nullable = false, length = 20)
	public String getDefineAdSeq() {
		return this.defineAdSeq;
	}

	public void setDefineAdSeq(String defineAdSeq) {
		this.defineAdSeq = defineAdSeq;
	}

	@Column(name = "define_ad_id", nullable = false, length = 30)
	public String getDefineAdId() {
		return this.defineAdId;
	}

	public void setDefineAdId(String defineAdId) {
		this.defineAdId = defineAdId;
	}

	@Column(name = "define_ad_name", nullable = false, length = 50)
	public String getDefineAdName() {
		return this.defineAdName;
	}

	public void setDefineAdName(String defineAdName) {
		this.defineAdName = defineAdName;
	}

	@Column(name = "define_ad_type", nullable = false)
	public int getDefineAdType() {
		return this.defineAdType;
	}

	public void setDefineAdType(int defineAdType) {
		this.defineAdType = defineAdType;
	}

	@Column(name = "define_ad_width", nullable = false, length = 4)
	public String getDefineAdWidth() {
		return this.defineAdWidth;
	}

	public void setDefineAdWidth(String defineAdWidth) {
		this.defineAdWidth = defineAdWidth;
	}

	@Column(name = "define_ad_height", nullable = false, length = 4)
	public String getDefineAdHeight() {
		return this.defineAdHeight;
	}

	public void setDefineAdHeight(String defineAdHeight) {
		this.defineAdHeight = defineAdHeight;
	}

	@Column(name = "define_ad_file", nullable = false, length = 500)
	public String getDefineAdFile() {
		return this.defineAdFile;
	}

	public void setDefineAdFile(String defineAdFile) {
		this.defineAdFile = defineAdFile;
	}

	@Column(name = "ad_pool_seq", length = 20)
	public String getAdPoolSeq() {
		return this.adPoolSeq;
	}

	public void setAdPoolSeq(String adPoolSeq) {
		this.adPoolSeq = adPoolSeq;
	}

}
