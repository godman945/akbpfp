package com.pchome.enumerate.ad;

public enum EnumPfpCatalog {
	
	/**
	 * 商品目錄類型 1:一般購物類
	 */
	CATALOG_SHOPPING("1", "一般購物"),
	/**
	 * 商品目錄類型 2:訂房住宿類
	 */
	CATALOG_STAY("2", "訂房住宿"),
	/**
	 * 商品目錄類型 3:交通航班類
	 */
	CATALOG_TRAFFIC("3", "交通航班"),
	/**
	 * 商品目錄類型 4:房產租售類
	 */
	CATALOG_ESTATE("4", "房產租售"),
	/**
	 * 上傳方式 1:檔案上傳
	 */
	CATALOG_UPLOAD_FILE_UPLOAD("1", "檔案上傳"), 
	/**
	 * 上傳方式 2:自動排程上傳
	 */
	CATALOG_UPLOAD_AUTOMATIC_SCHEDULING("2", "自動排程上傳"), 
	/**
	 * 上傳方式 3:賣場網址上傳
	 */
	CATALOG_UPLOAD_STORE_URL("3", "賣場網址上傳"), 
	/**
	 * 上傳方式 4:手動上傳
	 */
	CATALOG_UPLOAD_MANUAL_UPLOAD("4", "手動上傳"),
	/**
	 * 目錄圖檔顯示方式 1:全秀
	 */
	CATALOG_IMG_SHOW_SHOW_ALL("1", "全秀"),
	/**
	 * 目錄圖檔顯示方式 2:裁切
	 */
	CATALOG_IMG_SHOW_CUTTING("2", "裁切");
	
	
	private final String type;
	private final String typeName;

	private EnumPfpCatalog(String type, String typeName) {
		this.type = type;
		this.typeName = typeName;
	}

	public String getType() {
		return type;
	}

	public String getTypeName() {
		return typeName;
	}


	
	
//	AD_PRICE_CPV(0, "單次收視出價-CPV", "CPV", 0.5), 
//	AD_PRICE_CPM(1, "千次曝光出價-CPM", "CPM", 65), 
//	AD_PRICE_CPC(2, "單次點擊出價-CPC", "CPC", 3);
//	
//	private final int type;
//	private final String typeName;
//	private final String dbTypeName;
//	private final double price;
//	
//	private EnumPfpCatalog(int type, String typeName, String dbTypeName, double price) {
//		this.type = type;
//		this.typeName = typeName;
//		this.dbTypeName = dbTypeName;
//		this.price = price;
//	}
//
//	/**
//	 * 取得相對應的enum資料
//	 * @param dbTypeName CPV/CPM/CPC
//	 * @return
//	 */
//	public static EnumPfpCatalog getEnumAdPriceTypeData(final String dbTypeName) {
//		for (EnumPfpCatalog e : EnumPfpCatalog.values()) {
//			if (e.getDbTypeName().equalsIgnoreCase(dbTypeName)) {
//				return e;
//			}
//		}
//		return null;
//	}
//
//	public int getType() {
//		return type;
//	}
//
//	public String getTypeName() {
//		return typeName;
//	}
//	
//	public String getDbTypeName() {
//		return dbTypeName;
//	}
//
//	public double getPrice() {
//		return price;
//	}

}
