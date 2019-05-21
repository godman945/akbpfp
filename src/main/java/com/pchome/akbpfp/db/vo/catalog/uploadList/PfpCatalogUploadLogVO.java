package com.pchome.akbpfp.db.vo.catalog.uploadList;

import java.util.Date;

public class PfpCatalogUploadLogVO {
	
	private String catalogUploadLogSeq; // 更新紀錄編號
	private String catalogSeq; // 商品目錄ID
	private String updateWay; // 更新方式(1.取代,2.更新)
	private String updateContent; // 更新內容(檔名或網址)
	private int errorNum = 0; // 失敗筆數
	private int successNum = 0; // 成功筆數
	private Date updateDatetime; // 開始執行的更新時間

	private int pageNo = 1; // 初始化目前頁數
	private int pageSize = 10; // 初始化每頁幾筆
	private int pageCount = 0; // 初始化共幾頁
	private int totalCount = 0; // 初始化共幾筆
	
	//accesslog用
	private String idPchome;
	private String customerInfoId;
	private String userId;
	private String remoteAddr;
	
	public String getCatalogUploadLogSeq() {
		return catalogUploadLogSeq;
	}

	public void setCatalogUploadLogSeq(String catalogUploadLogSeq) {
		this.catalogUploadLogSeq = catalogUploadLogSeq;
	}

	public String getCatalogSeq() {
		return catalogSeq;
	}

	public void setCatalogSeq(String catalogSeq) {
		this.catalogSeq = catalogSeq;
	}

	public String getUpdateWay() {
		return updateWay;
	}

	public void setUpdateWay(String updateWay) {
		this.updateWay = updateWay;
	}

	public String getUpdateContent() {
		return updateContent;
	}

	public void setUpdateContent(String updateContent) {
		this.updateContent = updateContent;
	}

	public int getErrorNum() {
		return errorNum;
	}

	public void setErrorNum(int errorNum) {
		this.errorNum = errorNum;
	}

	public int getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(int successNum) {
		this.successNum = successNum;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Date getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public String getIdPchome() {
		return idPchome;
	}

	public void setIdPchome(String idPchome) {
		this.idPchome = idPchome;
	}

	public String getCustomerInfoId() {
		return customerInfoId;
	}

	public void setCustomerInfoId(String customerInfoId) {
		this.customerInfoId = customerInfoId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

}