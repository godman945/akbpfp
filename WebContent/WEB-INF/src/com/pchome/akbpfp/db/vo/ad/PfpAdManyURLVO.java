package com.pchome.akbpfp.db.vo.ad;

import org.json.JSONArray;
import org.json.JSONObject;

public class PfpAdManyURLVO {

	private String searchURL;  //輸入的網址
	private int page = 0;      //第幾頁(初始預設第1頁)
	private int pageSize = 0;  //每頁筆數(初始預設每頁N筆)
	private int totalPage = 0; //總頁數
	private int redisDataTotalSize = 0; //總筆數
	private String id;
	
	//廣告明細
	private String adDetailPicURL; // 圖片路徑
	private String adDetailTitle; // 標題
	private String adDetailDescription; // 商品描述
	private String adDetailLinkURL; // 網頁連結
	private String adDetailShowURL; // 顯示網址
	private String adDetailSalePrice; // 原價
	private String adDetailPrice; // 促銷價
	
//	private int modifyPrice; //查詢結果修改促銷價
	private String modifyPrice; //查詢結果修改促銷價
	private String modifyADTitle; //查詢結果修改標題
	private String modifyADContent; //查詢結果修改商品描述
	private String modifyADShowURL; //查詢結果修改顯示連結
	
//	private StringBuffer adCrawlerResult;
	private JSONArray apiJsonArray; //取得廣告爬蟲api資料
	private JSONObject redisJsonObject; //redis內取得的資料
	private String message = ""; //訊息
	
	
	public String getSearchURL() {
		return searchURL;
	}
	
	public void setSearchURL(String searchURL) {
		this.searchURL = searchURL;
	}
	
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getTotalPage() {
		return totalPage;
	}
	
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public int getRedisDataTotalSize() {
		return redisDataTotalSize;
	}

	public void setRedisDataTotalSize(int redisDataTotalSize) {
		this.redisDataTotalSize = redisDataTotalSize;
	}

	//
//	public String getAdDetailPicURL() {
//		return adDetailPicURL;
//	}
//
//	public void setAdDetailPicURL(String adDetailPicURL) {
//		this.adDetailPicURL = adDetailPicURL;
//	}
//
//	public String getAdDetailTitle() {
//		return adDetailTitle;
//	}
//
//	public void setAdDetailTitle(String adDetailTitle) {
//		this.adDetailTitle = adDetailTitle;
//	}
//
//	public String getAdDetailDescription() {
//		return adDetailDescription;
//	}
//
//	public void setAdDetailDescription(String adDetailDescription) {
//		this.adDetailDescription = adDetailDescription;
//	}
//
//	public String getAdDetailLinkURL() {
//		return adDetailLinkURL;
//	}
//
//	public void setAdDetailLinkURL(String adDetailLinkURL) {
//		this.adDetailLinkURL = adDetailLinkURL;
//	}
//
//	public String getAdDetailPrice() {
//		return adDetailPrice;
//	}
//
//	public void setAdDetailPrice(String adDetailPrice) {
//		this.adDetailPrice = adDetailPrice;
//	}
//
//	public String getAdDetailSalePrice() {
//		return adDetailSalePrice;
//	}
//
//	public void setAdDetailSalePrice(String adDetailSalePrice) {
//		this.adDetailSalePrice = adDetailSalePrice;
//	}
//
//	public int getPageTotalSize() {
//		return pageTotalSize;
//	}
//
//	public void setPageTotalSize(int pageTotalSize) {
//		this.pageTotalSize = pageTotalSize;
//	}
//
//	public String getAdDetailShowURL() {
//		return adDetailShowURL;
//	}
//
//	public void setAdDetailShowURL(String adDetailShowURL) {
//		this.adDetailShowURL = adDetailShowURL;
//	}
//
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JSONArray getApiJsonArray() {
		return apiJsonArray;
	}

	public void setApiJsonArray(JSONArray apiJsonArray) {
		this.apiJsonArray = apiJsonArray;
	}

	public JSONObject getRedisJsonObject() {
		return redisJsonObject;
	}

	public void setRedisJsonObject(JSONObject redisJsonObject) {
		this.redisJsonObject = redisJsonObject;
	}

	public String getModifyPrice() {
		return modifyPrice;
	}

	public void setModifyPrice(String modifyPrice) {
		this.modifyPrice = modifyPrice;
	}

	public String getModifyADTitle() {
		return modifyADTitle;
	}

	public void setModifyADTitle(String modifyADTitle) {
		this.modifyADTitle = modifyADTitle;
	}

	public String getModifyADContent() {
		return modifyADContent;
	}

	public void setModifyADContent(String modifyADContent) {
		this.modifyADContent = modifyADContent;
	}

	public String getModifyADShowURL() {
		return modifyADShowURL;
	}

	public void setModifyADShowURL(String modifyADShowURL) {
		this.modifyADShowURL = modifyADShowURL;
	}

}
