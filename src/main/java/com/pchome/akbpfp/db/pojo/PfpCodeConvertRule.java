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
 * PfpCodeConvertRule generated by hbm2java
 */
@Entity
@Table(name = "pfp_code_convert_rule")
public class PfpCodeConvertRule implements java.io.Serializable {

	private String convertRuleId;
	private PfpCodeConvert pfpCodeConvert;
	private String convertRuleWay;
	private String convertRuleValue;
	private Date updateDate;
	private Date createDate;

	public PfpCodeConvertRule() {
	}

	public PfpCodeConvertRule(String convertRuleId, PfpCodeConvert pfpCodeConvert, String convertRuleWay,
			String convertRuleValue, Date updateDate, Date createDate) {
		this.convertRuleId = convertRuleId;
		this.pfpCodeConvert = pfpCodeConvert;
		this.convertRuleWay = convertRuleWay;
		this.convertRuleValue = convertRuleValue;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	@Id

	@Column(name = "convert_rule_id", unique = true, nullable = false, length = 20)
	public String getConvertRuleId() {
		return this.convertRuleId;
	}

	public void setConvertRuleId(String convertRuleId) {
		this.convertRuleId = convertRuleId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "convert_seq", nullable = false)
	public PfpCodeConvert getPfpCodeConvert() {
		return this.pfpCodeConvert;
	}

	public void setPfpCodeConvert(PfpCodeConvert pfpCodeConvert) {
		this.pfpCodeConvert = pfpCodeConvert;
	}

	@Column(name = "convert_rule_way", nullable = false, length = 2)
	public String getConvertRuleWay() {
		return this.convertRuleWay;
	}

	public void setConvertRuleWay(String convertRuleWay) {
		this.convertRuleWay = convertRuleWay;
	}

	@Column(name = "convert_rule_value", nullable = false, length = 500)
	public String getConvertRuleValue() {
		return this.convertRuleValue;
	}

	public void setConvertRuleValue(String convertRuleValue) {
		this.convertRuleValue = convertRuleValue;
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