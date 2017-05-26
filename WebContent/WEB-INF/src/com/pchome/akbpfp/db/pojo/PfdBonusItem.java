package com.pchome.akbpfp.db.pojo;

// Generated 2017/5/26 �W�� 09:41:24 by Hibernate Tools 3.4.0.CR1

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
 * PfdBonusItem generated by hbm2java
 */
@Entity
@Table(name = "pfd_bonus_item", catalog = "akb")
public class PfdBonusItem implements java.io.Serializable {

	private Integer bonusItemId;
	private String bonusName;
	private String bonusType;
	private Date createDate;
	private Date updateDate;

	public PfdBonusItem() {
	}

	public PfdBonusItem(String bonusName, String bonusType, Date createDate,
			Date updateDate) {
		this.bonusName = bonusName;
		this.bonusType = bonusType;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "bonus_item_id", unique = true, nullable = false)
	public Integer getBonusItemId() {
		return this.bonusItemId;
	}

	public void setBonusItemId(Integer bonusItemId) {
		this.bonusItemId = bonusItemId;
	}

	@Column(name = "bonus_name", nullable = false, length = 50)
	public String getBonusName() {
		return this.bonusName;
	}

	public void setBonusName(String bonusName) {
		this.bonusName = bonusName;
	}

	@Column(name = "bonus_type", nullable = false, length = 1)
	public String getBonusType() {
		return this.bonusType;
	}

	public void setBonusType(String bonusType) {
		this.bonusType = bonusType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, length = 19)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", nullable = false, length = 19)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
