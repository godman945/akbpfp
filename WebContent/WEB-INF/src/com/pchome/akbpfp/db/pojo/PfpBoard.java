package com.pchome.akbpfp.db.pojo;

// Generated 2015/10/21 �U�� 02:37:05 by Hibernate Tools 3.4.0.CR1

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
 * PfpBoard generated by hbm2java
 */
@Entity
@Table(name = "pfp_board", catalog = "akb")
public class PfpBoard implements java.io.Serializable {

	private Integer boardId;
	private String customerInfoId;
	private String content;
	private String boardType;
	private String category;
	private String deleteId;
	private String startDate;
	private String endDate;
	private String display;
	private String hasUrl;
	private String urlAddress;
	private String editor;
	private String author;
	private Date updateDate;
	private Date createDate;

	public PfpBoard() {
	}

	public PfpBoard(String customerInfoId, String content, String boardType,
			String category, String startDate, String endDate, String display,
			String hasUrl, String editor, String author, Date updateDate,
			Date createDate) {
		this.customerInfoId = customerInfoId;
		this.content = content;
		this.boardType = boardType;
		this.category = category;
		this.startDate = startDate;
		this.endDate = endDate;
		this.display = display;
		this.hasUrl = hasUrl;
		this.editor = editor;
		this.author = author;
		this.updateDate = updateDate;
		this.createDate = createDate;
	}

	public PfpBoard(String customerInfoId, String content, String boardType,
			String category, String deleteId, String startDate, String endDate,
			String display, String hasUrl, String urlAddress, String editor,
			String author, Date updateDate, Date createDate) {
		this.customerInfoId = customerInfoId;
		this.content = content;
		this.boardType = boardType;
		this.category = category;
		this.deleteId = deleteId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.display = display;
		this.hasUrl = hasUrl;
		this.urlAddress = urlAddress;
		this.editor = editor;
		this.author = author;
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

	@Column(name = "customer_info_id", nullable = false, length = 20)
	public String getCustomerInfoId() {
		return this.customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	@Column(name = "content", nullable = false, length = 500)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "board_type", nullable = false, length = 1)
	public String getBoardType() {
		return this.boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	@Column(name = "category", nullable = false, length = 2)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "delete_id", length = 20)
	public String getDeleteId() {
		return this.deleteId;
	}

	public void setDeleteId(String deleteId) {
		this.deleteId = deleteId;
	}

	@Column(name = "start_date", nullable = false, length = 10)
	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@Column(name = "end_date", nullable = false, length = 10)
	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Column(name = "display", nullable = false, length = 1)
	public String getDisplay() {
		return this.display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	@Column(name = "has_url", nullable = false, length = 1)
	public String getHasUrl() {
		return this.hasUrl;
	}

	public void setHasUrl(String hasUrl) {
		this.hasUrl = hasUrl;
	}

	@Column(name = "url_address", length = 500)
	public String getUrlAddress() {
		return this.urlAddress;
	}

	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}

	@Column(name = "editor", nullable = false, length = 50)
	public String getEditor() {
		return this.editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	@Column(name = "author", nullable = false, length = 50)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
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
