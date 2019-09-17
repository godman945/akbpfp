package com.pchome.akbpfp.db.pojo;
// Generated 2019/3/6 �U�� 01:55:32 by Hibernate Tools 3.5.0.Final

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
 * PfpCodeConvert generated by hbm2java
 */
@Entity
@Table(name = "pfp_code_convert")
public class PfpCodeConvert implements java.io.Serializable {

	private String convertSeq;
	private PfpCode pfpCode;
	private String convertName;
	private String pfpCustomerInfoId;
	private String convertCodeType;
	private String convertType;
	private int clickRangeDate;
	private int impRangeDate;
	private String convertClass;
	private String convertPriceType;
	private float convertPrice;
	private String convertStatus;
	private String convertBelong;
	private String convertNumType;
	private int convertRuleNum;
	private Date updateDate;
	private Date createDate;
	private Set<PfpCodeConvertRule> pfpCodeConvertRules = new HashSet<PfpCodeConvertRule>(0);

	public PfpCodeConvert() {
	}

	public PfpCodeConvert(String convertSeq, PfpCode pfpCode, String convertName, String pfpCustomerInfoId,
			String convertCodeType, String convertType, int clickRangeDate, int impRangeDate, String convertClass,
			String convertPriceType, float convertPrice, String convertStatus, String convertBelong,
			String convertNumType, int convertRuleNum, Date updateDate, Date createDate) {
		this.convertSeq = convertSeq;
		this.pfpCode = pfpCode;
		this.convertName = convertName;
		this.pfpCustomerInfoId = pfpCustomerInfoId;
		this.convertCodeType = convertCodeType;
		this.convertType = convertType;
		this.clickRangeDate = clickRangeDate;
		this.impRangeDate = impRangeDate;
		this.convertClass = convertClass;
		this.convertPriceType = convertPriceType;
		this.convertPrice = convertPrice;
		this.convertStatus = convertStatus;
		this.convertBelong = convertBelong;
		this.convertNumType = convertNumType;
		this.convertRuleNum = convertRuleNum;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	public PfpCodeConvert(String convertSeq, PfpCode pfpCode, String convertName, String pfpCustomerInfoId,
			String convertCodeType, String convertType, int clickRangeDate, int impRangeDate, String convertClass,
			String convertPriceType, float convertPrice, String convertStatus, String convertBelong,
			String convertNumType, int convertRuleNum, Date updateDate, Date createDate,
			Set<PfpCodeConvertRule> pfpCodeConvertRules) {
		this.convertSeq = convertSeq;
		this.pfpCode = pfpCode;
		this.convertName = convertName;
		this.pfpCustomerInfoId = pfpCustomerInfoId;
		this.convertCodeType = convertCodeType;
		this.convertType = convertType;
		this.clickRangeDate = clickRangeDate;
		this.impRangeDate = impRangeDate;
		this.convertClass = convertClass;
		this.convertPriceType = convertPriceType;
		this.convertPrice = convertPrice;
		this.convertStatus = convertStatus;
		this.convertBelong = convertBelong;
		this.convertNumType = convertNumType;
		this.convertRuleNum = convertRuleNum;
		this.updateDate = updateDate;
		this.createDate = createDate;
		this.pfpCodeConvertRules = pfpCodeConvertRules;
	}

	@Id

	@Column(name = "convert_seq", unique = true, nullable = false, length = 20)
	public String getConvertSeq() {
		return this.convertSeq;
	}

	public void setConvertSeq(String convertSeq) {
		this.convertSeq = convertSeq;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pa_id", nullable = false)
	public PfpCode getPfpCode() {
		return this.pfpCode;
	}

	public void setPfpCode(PfpCode pfpCode) {
		this.pfpCode = pfpCode;
	}

	@Column(name = "convert_name", nullable = false, length = 20)
	public String getConvertName() {
		return this.convertName;
	}

	public void setConvertName(String convertName) {
		this.convertName = convertName;
	}

	@Column(name = "pfp_customer_info_id", nullable = false, length = 20)
	public String getPfpCustomerInfoId() {
		return this.pfpCustomerInfoId;
	}

	public void setPfpCustomerInfoId(String pfpCustomerInfoId) {
		this.pfpCustomerInfoId = pfpCustomerInfoId;
	}

	@Column(name = "convert_code_type", nullable = false, length = 1)
	public String getConvertCodeType() {
		return this.convertCodeType;
	}

	public void setConvertCodeType(String convertCodeType) {
		this.convertCodeType = convertCodeType;
	}

	@Column(name = "convert_type", nullable = false, length = 2)
	public String getConvertType() {
		return this.convertType;
	}

	public void setConvertType(String convertType) {
		this.convertType = convertType;
	}

	@Column(name = "click_range_date", nullable = false)
	public int getClickRangeDate() {
		return this.clickRangeDate;
	}

	public void setClickRangeDate(int clickRangeDate) {
		this.clickRangeDate = clickRangeDate;
	}

	@Column(name = "imp_range_date", nullable = false)
	public int getImpRangeDate() {
		return this.impRangeDate;
	}

	public void setImpRangeDate(int impRangeDate) {
		this.impRangeDate = impRangeDate;
	}

	@Column(name = "convert_class", nullable = false, length = 3)
	public String getConvertClass() {
		return this.convertClass;
	}

	public void setConvertClass(String convertClass) {
		this.convertClass = convertClass;
	}

	@Column(name = "convert_price_type", nullable = false, length = 2)
	public String getConvertPriceType() {
		return this.convertPriceType;
	}

	public void setConvertPriceType(String convertPriceType) {
		this.convertPriceType = convertPriceType;
	}

	@Column(name = "convert_price", nullable = false, precision = 8)
	public float getConvertPrice() {
		return this.convertPrice;
	}

	public void setConvertPrice(float convertPrice) {
		this.convertPrice = convertPrice;
	}

	@Column(name = "convert_status", nullable = false, length = 1)
	public String getConvertStatus() {
		return this.convertStatus;
	}

	public void setConvertStatus(String convertStatus) {
		this.convertStatus = convertStatus;
	}

	@Column(name = "convert_belong", nullable = false, length = 2)
	public String getConvertBelong() {
		return this.convertBelong;
	}

	public void setConvertBelong(String convertBelong) {
		this.convertBelong = convertBelong;
	}

	@Column(name = "convert_num_type", nullable = false, length = 1)
	public String getConvertNumType() {
		return this.convertNumType;
	}

	public void setConvertNumType(String convertNumType) {
		this.convertNumType = convertNumType;
	}

	@Column(name = "convert_rule_num", nullable = false)
	public int getConvertRuleNum() {
		return this.convertRuleNum;
	}

	public void setConvertRuleNum(int convertRuleNum) {
		this.convertRuleNum = convertRuleNum;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pfpCodeConvert")
	public Set<PfpCodeConvertRule> getPfpCodeConvertRules() {
		return this.pfpCodeConvertRules;
	}

	public void setPfpCodeConvertRules(Set<PfpCodeConvertRule> pfpCodeConvertRules) {
		this.pfpCodeConvertRules = pfpCodeConvertRules;
	}

}