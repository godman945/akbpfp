package com.pchome.akbpfp.db.vo.catalog.prodList;

public class PfpCatalogProdEcVO {
	
	String id; 				//流水號
	String catalogProdSeq;//商品ID	
	String catalogSeq;		//商品目錄ID
	String ecName;		//商品名稱
	String ecTitle;		//商品敘述
	String ecImg;			//商品圖像路徑
	String ecUrl;			//商品網址
	String ecPrice;		//商品價格
	String ecDiscountPrice;//商品特價
	String ecStockStatus;	 //商品庫存
	String ecUseStatus;	 //商品使用狀態(全新/二手)
	String ecCategory;	 //商品組合篩選分類
	String ecStatus;		 //商品狀態(開啟/關閉)
	String ecCheckStatus;	 //商品審核狀態
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCatalogProdSeq() {
		return catalogProdSeq;
	}
	public void setCatalogProdSeq(String catalogProdSeq) {
		this.catalogProdSeq = catalogProdSeq;
	}
	public String getCatalogSeq() {
		return catalogSeq;
	}
	public void setCatalogSeq(String catalogSeq) {
		this.catalogSeq = catalogSeq;
	}
	public String getEcName() {
		return ecName;
	}
	public void setEcName(String ecName) {
		this.ecName = ecName;
	}
	public String getEcTitle() {
		return ecTitle;
	}
	public void setEcTitle(String ecTitle) {
		this.ecTitle = ecTitle;
	}
	public String getEcImg() {
		return ecImg;
	}
	public void setEcImg(String ecImg) {
		this.ecImg = ecImg;
	}
	public String getEcUrl() {
		return ecUrl;
	}
	public void setEcUrl(String ecUrl) {
		this.ecUrl = ecUrl;
	}
	public String getEcPrice() {
		return ecPrice;
	}
	public void setEcPrice(String ecPrice) {
		this.ecPrice = ecPrice;
	}
	public String getEcDiscountPrice() {
		return ecDiscountPrice;
	}
	public void setEcDiscountPrice(String ecDiscountPrice) {
		this.ecDiscountPrice = ecDiscountPrice;
	}
	public String getEcStockStatus() {
		return ecStockStatus;
	}
	public void setEcStockStatus(String ecStockStatus) {
		this.ecStockStatus = ecStockStatus;
	}
	public String getEcUseStatus() {
		return ecUseStatus;
	}
	public void setEcUseStatus(String ecUseStatus) {
		this.ecUseStatus = ecUseStatus;
	}
	public String getEcCategory() {
		return ecCategory;
	}
	public void setEcCategory(String ecCategory) {
		this.ecCategory = ecCategory;
	}
	public String getEcStatus() {
		return ecStatus;
	}
	public void setEcStatus(String ecStatus) {
		this.ecStatus = ecStatus;
	}
	public String getEcCheckStatus() {
		return ecCheckStatus;
	}
	public void setEcCheckStatus(String ecCheckStatus) {
		this.ecCheckStatus = ecCheckStatus;
	}
	
	
}

