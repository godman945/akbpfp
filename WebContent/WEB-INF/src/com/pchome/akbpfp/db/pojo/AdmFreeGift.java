package com.pchome.akbpfp.db.pojo;

// Generated 2015/11/27 �W�� 10:36:36 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * AdmFreeGift generated by hbm2java
 */
@Entity
@Table(name = "adm_free_gift", catalog = "akb", uniqueConstraints = @UniqueConstraint(columnNames = "gift_sno"))
public class AdmFreeGift implements java.io.Serializable {

	private Integer giftId;
	private AdmFreeAction admFreeAction;
	private String giftSno;
	private String customerInfoId;
	private Date openDate;
	private String giftSnoStatus;
	private Date createDate;
	private Date updateDate;

	public AdmFreeGift() {
	}

	public AdmFreeGift(AdmFreeAction admFreeAction, String giftSno,
			String giftSnoStatus, Date createDate, Date updateDate) {
		this.admFreeAction = admFreeAction;
		this.giftSno = giftSno;
		this.giftSnoStatus = giftSnoStatus;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	public AdmFreeGift(AdmFreeAction admFreeAction, String giftSno,
			String customerInfoId, Date openDate, String giftSnoStatus,
			Date createDate, Date updateDate) {
		this.admFreeAction = admFreeAction;
		this.giftSno = giftSno;
		this.customerInfoId = customerInfoId;
		this.openDate = openDate;
		this.giftSnoStatus = giftSnoStatus;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "gift_id", unique = true, nullable = false)
	public Integer getGiftId() {
		return this.giftId;
	}

	public void setGiftId(Integer giftId) {
		this.giftId = giftId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "adm_free_action", nullable = false)
	public AdmFreeAction getAdmFreeAction() {
		return this.admFreeAction;
	}

	public void setAdmFreeAction(AdmFreeAction admFreeAction) {
		this.admFreeAction = admFreeAction;
	}

	@Column(name = "gift_sno", unique = true, nullable = false, length = 10)
	public String getGiftSno() {
		return this.giftSno;
	}

	public void setGiftSno(String giftSno) {
		this.giftSno = giftSno;
	}

	@Column(name = "customer_info_id", length = 20)
	public String getCustomerInfoId() {
		return this.customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "open_date", length = 10)
	public Date getOpenDate() {
		return this.openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	@Column(name = "gift_sno_status", nullable = false, length = 2)
	public String getGiftSnoStatus() {
		return this.giftSnoStatus;
	}

	public void setGiftSnoStatus(String giftSnoStatus) {
		this.giftSnoStatus = giftSnoStatus;
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
