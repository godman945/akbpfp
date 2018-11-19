package com.pchome.akbpfp.db.vo.catalog.uploadList;

public class PfpCatalogProdEcErrorVO {
	
	private String catalogUploadLogSeq; // 更新紀錄id
	private String catalogProdErrItem; // 上傳錯誤清單ID
	private String catalogProdSeqErrstatus; // 商品ID錯誤狀態(有值表示錯誤)
	private String catalogProdSeq; // 商品ID
	private String ecNameErrstatus; // 商品名稱錯誤狀態(有值表示錯誤)
	private String ecName; // 商品名稱
	private String ecImgErrstatus; // 商品圖像路徑錯誤狀態(有值表示錯誤)
	private String ecImg; // 商品圖像路徑
	private String ecUrlErrstatus; // 商品網址錯誤狀態(有值表示錯誤)
	private String ecUrl; // 商品網址
	private String ecPriceErrstatus; // 商品價格錯誤狀態(有值表示錯誤)
	private String ecPrice; // 商品價格
	private String ecDiscountPriceErrstatus; // 商品特價錯誤狀態(有值表示錯誤)
	private String ecDiscountPrice; // 商品特價
	private String ecStockStatusErrstatus; // 商品庫存錯誤狀態(有值表示錯誤)
	private String ecStockStatus; // 商品庫存(0:無庫存, 1:有庫存, 2:預購, 3:停售)
	private String ecUseStatusErrstatus; // 商品使用錯誤狀態(有值表示錯誤)
	private String ecUseStatus; // 商品使用狀態(0:全新, 1:二手, 2:福利品)
	private String ecCategoryErrstatus; // 商品組合篩選分類錯誤狀態(有值表示錯誤)
	private String ecCategory; // 商品組合篩選分類

	private int pageNo = 1; // 初始化目前頁數
	private int pageSize = 10; // 初始化每頁幾筆
	private int pageCount = 0; // 初始化共幾頁
	private int totalCount = 0; // 初始化共幾筆
	private boolean paginationFlag = true; // 判斷是否做分頁，預設是

	public String getCatalogUploadLogSeq() {
		return catalogUploadLogSeq;
	}

	public void setCatalogUploadLogSeq(String catalogUploadLogSeq) {
		this.catalogUploadLogSeq = catalogUploadLogSeq;
	}

	public String getCatalogProdErrItem() {
		return catalogProdErrItem;
	}

	public void setCatalogProdErrItem(String catalogProdErrItem) {
		this.catalogProdErrItem = catalogProdErrItem;
	}

	public String getCatalogProdSeqErrstatus() {
		return catalogProdSeqErrstatus;
	}

	public void setCatalogProdSeqErrstatus(String catalogProdSeqErrstatus) {
		this.catalogProdSeqErrstatus = catalogProdSeqErrstatus;
	}

	public String getCatalogProdSeq() {
		return catalogProdSeq;
	}

	public void setCatalogProdSeq(String catalogProdSeq) {
		this.catalogProdSeq = catalogProdSeq;
	}

	public String getEcNameErrstatus() {
		return ecNameErrstatus;
	}

	public void setEcNameErrstatus(String ecNameErrstatus) {
		this.ecNameErrstatus = ecNameErrstatus;
	}

	public String getEcName() {
		return ecName;
	}

	public void setEcName(String ecName) {
		this.ecName = ecName;
	}

	public String getEcImgErrstatus() {
		return ecImgErrstatus;
	}

	public void setEcImgErrstatus(String ecImgErrstatus) {
		this.ecImgErrstatus = ecImgErrstatus;
	}

	public String getEcImg() {
		return ecImg;
	}

	public void setEcImg(String ecImg) {
		this.ecImg = ecImg;
	}

	public String getEcUrlErrstatus() {
		return ecUrlErrstatus;
	}

	public void setEcUrlErrstatus(String ecUrlErrstatus) {
		this.ecUrlErrstatus = ecUrlErrstatus;
	}

	public String getEcUrl() {
		return ecUrl;
	}

	public void setEcUrl(String ecUrl) {
		this.ecUrl = ecUrl;
	}

	public String getEcPriceErrstatus() {
		return ecPriceErrstatus;
	}

	public void setEcPriceErrstatus(String ecPriceErrstatus) {
		this.ecPriceErrstatus = ecPriceErrstatus;
	}

	public String getEcPrice() {
		return ecPrice;
	}

	public void setEcPrice(String ecPrice) {
		this.ecPrice = ecPrice;
	}

	public String getEcDiscountPriceErrstatus() {
		return ecDiscountPriceErrstatus;
	}

	public void setEcDiscountPriceErrstatus(String ecDiscountPriceErrstatus) {
		this.ecDiscountPriceErrstatus = ecDiscountPriceErrstatus;
	}

	public String getEcDiscountPrice() {
		return ecDiscountPrice;
	}

	public void setEcDiscountPrice(String ecDiscountPrice) {
		this.ecDiscountPrice = ecDiscountPrice;
	}

	public String getEcStockStatusErrstatus() {
		return ecStockStatusErrstatus;
	}

	public void setEcStockStatusErrstatus(String ecStockStatusErrstatus) {
		this.ecStockStatusErrstatus = ecStockStatusErrstatus;
	}

	public String getEcStockStatus() {
		return ecStockStatus;
	}

	public void setEcStockStatus(String ecStockStatus) {
		this.ecStockStatus = ecStockStatus;
	}

	public String getEcUseStatusErrstatus() {
		return ecUseStatusErrstatus;
	}

	public void setEcUseStatusErrstatus(String ecUseStatusErrstatus) {
		this.ecUseStatusErrstatus = ecUseStatusErrstatus;
	}

	public String getEcUseStatus() {
		return ecUseStatus;
	}

	public void setEcUseStatus(String ecUseStatus) {
		this.ecUseStatus = ecUseStatus;
	}

	public String getEcCategoryErrstatus() {
		return ecCategoryErrstatus;
	}

	public void setEcCategoryErrstatus(String ecCategoryErrstatus) {
		this.ecCategoryErrstatus = ecCategoryErrstatus;
	}

	public String getEcCategory() {
		return ecCategory;
	}

	public void setEcCategory(String ecCategory) {
		this.ecCategory = ecCategory;
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

	public boolean isPaginationFlag() {
		return paginationFlag;
	}

	public void setPaginationFlag(boolean paginationFlag) {
		this.paginationFlag = paginationFlag;
	}
	
}