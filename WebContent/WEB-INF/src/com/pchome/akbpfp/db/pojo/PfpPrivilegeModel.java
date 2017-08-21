package com.pchome.akbpfp.db.pojo;
// Generated 2017/8/21 �W�� 10:28:10 by Hibernate Tools 3.4.0.CR1

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
 * PfpPrivilegeModel generated by hbm2java
 */
@Entity
@Table(name = "pfp_privilege_model", catalog = "akb_video")
public class PfpPrivilegeModel implements java.io.Serializable {

	private Integer modelId;
	private String modelName;
	private String note;
	private Date createDate;
	private Date updateDate;

	public PfpPrivilegeModel() {
	}

	public PfpPrivilegeModel(String modelName, Date createDate, Date updateDate) {
		this.modelName = modelName;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public PfpPrivilegeModel(String modelName, String note, Date createDate, Date updateDate) {
		this.modelName = modelName;
		this.note = note;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "model_id", unique = true, nullable = false)
	public Integer getModelId() {
		return this.modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	@Column(name = "model_name", nullable = false, length = 20)
	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@Column(name = "note", length = 20)
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, length = 0)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", nullable = false, length = 0)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
