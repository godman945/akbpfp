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
	private String adDetilPicURL; // 圖片路徑
	private String adDetilTitle; // 標題
	private String adDetilDescription; // 商品描述
	private String adDetilLinkURL; // 網頁連結
	private String adDetilShowURL; // 顯示網址
	private String adDetilSalePrice; // 原價
	private String adDetilPrice; // 促銷價
	
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
//	public String getAdDetilPicURL() {
//		return adDetilPicURL;
//	}
//
//	public void setAdDetilPicURL(String adDetilPicURL) {
//		this.adDetilPicURL = adDetilPicURL;
//	}
//
//	public String getAdDetilTitle() {
//		return adDetilTitle;
//	}
//
//	public void setAdDetilTitle(String adDetilTitle) {
//		this.adDetilTitle = adDetilTitle;
//	}
//
//	public String getAdDetilDescription() {
//		return adDetilDescription;
//	}
//
//	public void setAdDetilDescription(String adDetilDescription) {
//		this.adDetilDescription = adDetilDescription;
//	}
//
//	public String getAdDetilLinkURL() {
//		return adDetilLinkURL;
//	}
//
//	public void setAdDetilLinkURL(String adDetilLinkURL) {
//		this.adDetilLinkURL = adDetilLinkURL;
//	}
//
//	public String getAdDetilPrice() {
//		return adDetilPrice;
//	}
//
//	public void setAdDetilPrice(String adDetilPrice) {
//		this.adDetilPrice = adDetilPrice;
//	}
//
//	public String getAdDetilSalePrice() {
//		return adDetilSalePrice;
//	}
//
//	public void setAdDetilSalePrice(String adDetilSalePrice) {
//		this.adDetilSalePrice = adDetilSalePrice;
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
//	public String getAdDetilShowURL() {
//		return adDetilShowURL;
//	}
//
//	public void setAdDetilShowURL(String adDetilShowURL) {
//		this.adDetilShowURL = adDetilShowURL;
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
	
	

}
