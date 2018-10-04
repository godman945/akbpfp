package com.pchome.akbpfp.db.vo.ad;

public class PfpCatalogUploadLogVO {

	private String catalogSeq; // 商品目錄ID
	private String updateWay; // 更新方式(1.取代,2.更新)
	private String updateContent; // 更新內容(檔名或網址)
	private int errorNum = 0; // 失敗筆數
	private int successNum = 0; // 成功筆數
	  
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

	

	
	
}
