package com.pchome.akbpfp.db.vo.ad;

public class PfpCatalogUploadListVO {
	
	private String fileUploadPath = ""; // 檔案上傳路徑
	private String catalogType = ""; // 商品目錄類型(1:一般購物類, 2:訂房住宿類, 3:交通航班類, 4:房產租售類)
	
	public String getFileUploadPath() {
		return fileUploadPath;
	}

	public void setFileUploadPath(String fileUploadPath) {
		this.fileUploadPath = fileUploadPath;
	}

	public String getCatalogType() {
		return catalogType;
	}

	public void setCatalogType(String catalogType) {
		this.catalogType = catalogType;
	}
	
}
