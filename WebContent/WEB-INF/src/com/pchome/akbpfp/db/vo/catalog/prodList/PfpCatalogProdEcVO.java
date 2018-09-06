package com.pchome.akbpfp.db.vo.catalog.prodList;

public class PfpCatalogProdEcVO {
	
	String id; 				//流水號
	String catalogProdEcSeq;//商品ID	
	String catalogSeq;		//商品目錄ID
	String prodName;		//商品名稱
	String prodTitle;		//商品敘述
	String prodImg;			//商品圖像路徑
	String prodUrl;			//商品網址
	String prodPrice;		//商品價格
	String prodDiscountPrice;//商品特價
	String prodStockStatus;	 //商品庫存
	String prodUseStatus;	 //商品使用狀態(全新/二手)
	String prodCategory;	 //商品組合篩選分類
	String prodStatus;		 //商品狀態(開啟/關閉)
	String prodCheckStatus;	 //商品審核狀態
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCatalogProdEcSeq() {
		return catalogProdEcSeq;
	}
	public void setCatalogProdEcSeq(String catalogProdEcSeq) {
		this.catalogProdEcSeq = catalogProdEcSeq;
	}
	public String getCatalogSeq() {
		return catalogSeq;
	}
	public void setCatalogSeq(String catalogSeq) {
		this.catalogSeq = catalogSeq;
	}
	public String getProdName() {
		return prodName;
	}
	public void setProdName(String prodName) {
		this.prodName = prodName;
	}
	public String getProdTitle() {
		return prodTitle;
	}
	public void setProdTitle(String prodTitle) {
		this.prodTitle = prodTitle;
	}
	public String getProdImg() {
		return prodImg;
	}
	public void setProdImg(String prodImg) {
		this.prodImg = prodImg;
	}
	public String getProdUrl() {
		return prodUrl;
	}
	public void setProdUrl(String prodUrl) {
		this.prodUrl = prodUrl;
	}
	public String getProdPrice() {
		return prodPrice;
	}
	public void setProdPrice(String prodPrice) {
		this.prodPrice = prodPrice;
	}
	public String getProdDiscountPrice() {
		return prodDiscountPrice;
	}
	public void setProdDiscountPrice(String prodDiscountPrice) {
		this.prodDiscountPrice = prodDiscountPrice;
	}
	public String getProdStockStatus() {
		return prodStockStatus;
	}
	public void setProdStockStatus(String prodStockStatus) {
		this.prodStockStatus = prodStockStatus;
	}
	public String getProdUseStatus() {
		return prodUseStatus;
	}
	public void setProdUseStatus(String prodUseStatus) {
		this.prodUseStatus = prodUseStatus;
	}
	public String getProdCategory() {
		return prodCategory;
	}
	public void setProdCategory(String prodCategory) {
		this.prodCategory = prodCategory;
	}
	public String getProdStatus() {
		return prodStatus;
	}
	public void setProdStatus(String prodStatus) {
		this.prodStatus = prodStatus;
	}
	public String getProdCheckStatus() {
		return prodCheckStatus;
	}
	public void setProdCheckStatus(String prodCheckStatus) {
		this.prodCheckStatus = prodCheckStatus;
	}
}

