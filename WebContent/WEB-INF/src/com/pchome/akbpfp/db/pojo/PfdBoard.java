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
 * PfdBoard generated by hbm2java
 */
@Entity
@Table(name = "pfd_board", catalog = "akb_video")
public class PfdBoard implements java.io.Serializable {

	private Integer boardId;
	private String pfdCustomerInfoId;
	private String pfdUserId;
	private String boardContent;
	private String boardType;
	private String isSysBoard;
	private Date startDate;
	private Date endDate;
	private String hasUrl;
	private String urlAddress;
	private String deleteId;
	private String msgPrivilege;
	private Date updateDate;
	private Date createDate;

	public PfdBoard() {
	}

	public PfdBoard(String boardContent, String boardType, String isSysBoard, Date startDate, Date endDate,
			String hasUrl, Date createDate) {
		this.boardContent = boardContent;
		this.boardType = boardType;
		this.isSysBoard = isSysBoard;
		this.startDate = startDate;
		this.endDate = endDate;
		this.hasUrl = hasUrl;
		this.createDate = createDate;
	}

	public PfdBoard(String pfdCustomerInfoId, String pfdUserId, String boardContent, String boardType,
			String isSysBoard, Date startDate, Date endDate, String hasUrl, String urlAddress, String deleteId,
			String msgPrivilege, Date updateDate, Date createDate) {
		this.pfdCustomerInfoId = pfdCustomerInfoId;
		this.pfdUserId = pfdUserId;
		this.boardContent = boardContent;
		this.boardType = boardType;
		this.isSysBoard = isSysBoard;
		this.startDate = startDate;
		this.endDate = endDate;
		this.hasUrl = hasUrl;
		this.urlAddress = urlAddress;
		this.deleteId = deleteId;
		this.msgPrivilege = msgPrivilege;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "board_id", unique = true, nullable = false)
	public Integer getBoardId() {
		return this.boardId;
	}

	public void setBoardId(Integer boardId) {
		this.boardId = boardId;
	}

	@Column(name = "pfd_customer_info_id", length = 20)
	public String getPfdCustomerInfoId() {
		return this.pfdCustomerInfoId;
	}

	public void setPfdCustomerInfoId(String pfdCustomerInfoId) {
		this.pfdCustomerInfoId = pfdCustomerInfoId;
	}

	@Column(name = "pfd_user_id", length = 20)
	public String getPfdUserId() {
		return this.pfdUserId;
	}

	public void setPfdUserId(String pfdUserId) {
		this.pfdUserId = pfdUserId;
	}

	@Column(name = "board_content", nullable = false, length = 500)
	public String getBoardContent() {
		return this.boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	@Column(name = "board_type", nullable = false, length = 1)
	public String getBoardType() {
		return this.boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	@Column(name = "is_sys_board", nullable = false, length = 1)
	public String getIsSysBoard() {
		return this.isSysBoard;
	}

	public void setIsSysBoard(String isSysBoard) {
		this.isSysBoard = isSysBoard;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_date", nullable = false, length = 0)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_date", nullable = false, length = 0)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "has_url", nullable = false, length = 1)
	public String getHasUrl() {
		return this.hasUrl;
	}

	public void setHasUrl(String hasUrl) {
		this.hasUrl = hasUrl;
	}

	@Column(name = "url_address", length = 200)
	public String getUrlAddress() {
		return this.urlAddress;
	}

	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}

	@Column(name = "delete_id", length = 300)
	public String getDeleteId() {
		return this.deleteId;
	}

	public void setDeleteId(String deleteId) {
		this.deleteId = deleteId;
	}

	@Column(name = "msg_privilege", length = 20)
	public String getMsgPrivilege() {
		return this.msgPrivilege;
	}

	public void setMsgPrivilege(String msgPrivilege) {
		this.msgPrivilege = msgPrivilege;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_date", length = 0)
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", nullable = false, length = 0)
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
