package com.pchome.akbpfp.struts2.ajax.ad;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;

import com.pchome.akbpfp.db.service.admanyurlsearch.IPfpAdManyURLSearchService;
import com.pchome.akbpfp.db.vo.ad.PfpAdManyURLVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.utils.CommonUtils;

public class AdSearchURLAjax extends BaseCookieAction{
	
	private IPfpAdManyURLSearchService pfpAdManyURLSearchService;
	
	private String searchURL;  //輸入的網址
	private int page = 1;      //第幾頁(初始預設第1頁)
//	private int pageSize = 20; //每頁筆數(初始預設每頁N筆)
	private int pageSize = 2; //每頁筆數(初始預設每頁N筆)
	private int totalPage = 1; //總頁數(初始預設1頁)
	
//	private int modifyPrice; //ajax傳進來的修改促銷價
	private String modifyPrice; //ajax傳進來的修改促銷價
	
	private Map<String,Object> dataMap;
	
	/**
	 * 1.檢查輸入網址，打廣告爬蟲api取得資料
	 * 2.廣告爬蟲api取得資料與redis內的資料比對，是否重複，存入redis前先將資料整理後再存
	 * 
	 * @return
	 */
	public String searchStoreProductURLData() {
		try {
			String custId = super.getCustomer_info_id();
			log.info("custId:" + custId + " ,keyinURL:" + searchURL);

			//dataMap中的資料將會被Struts2轉換成JSON字串，所以用Map<String,Object>
			dataMap = new HashMap<String, Object>();
			
			PfpAdManyURLVO vo = new PfpAdManyURLVO();
			vo.setSearchURL(searchURL);
			vo.setPage(page);
			vo.setPageSize(pageSize);
			vo.setTotalPage(totalPage);
			vo.setId(custId);
			
			//檢查輸入網址，取得資料
			pfpAdManyURLSearchService.getAdCrawlerAPIData(vo);
			
			if(StringUtils.isNotEmpty(vo.getMessage())){
				dataMap.put("status", "ERROR");
				dataMap.put("msg", vo.getMessage());
				return SUCCESS;
			}
			
			//與redis內的資料比對，是否重複
			pfpAdManyURLSearchService.checkRedisData(vo);
			
			if(StringUtils.isNotEmpty(vo.getMessage())){
				dataMap.put("status", "ERROR");
				dataMap.put("msg", vo.getMessage());
				return SUCCESS;
			}
			
			pfpAdManyURLSearchService.getRedisLimitData(vo);
			
			dataMap.put("redisData", vo.getRedisJsonObject().getJSONArray("products").toString());
			
			//目前頁數
			dataMap.put("page", vo.getPage()); 
			//總頁數
			dataMap.put("totalPage", CommonUtils.getTotalPage(vo.getRedisDataTotalSize(), vo.getPageSize()));
			//每頁幾筆
			dataMap.put("pageSize", vo.getPageSize());

		} catch (Exception e) {
			dataMap.put("status", "ERROR");
			dataMap.put("msg", "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			log.error("error:" + e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 切換上下頁或每頁顯示N筆時
	 * @return
	 * @throws JSONException 
	 */
	public String changePageOrPageSize() throws JSONException{
		dataMap = new HashMap<String, Object>();
		
		String custId = super.getCustomer_info_id();
		PfpAdManyURLVO vo = new PfpAdManyURLVO();
		vo.setPage(page);
		vo.setPageSize(pageSize);
		vo.setId(custId);
		pfpAdManyURLSearchService.getRedisURLData(vo);
		
		pfpAdManyURLSearchService.getRedisLimitData(vo);
		
		dataMap.put("redisData", vo.getRedisJsonObject().getJSONArray("products").toString());
		//目前頁數
		dataMap.put("page", vo.getPage()); 
		//總頁數
		dataMap.put("totalPage", CommonUtils.getTotalPage(vo.getRedisDataTotalSize(), vo.getPageSize()));
		//每頁幾筆
		dataMap.put("pageSize", vo.getPageSize());
		
		return SUCCESS;
	}

	/**
	 * 查詢結果修改促銷價按鈕事件
	 * @return
	 * @throws JSONException 
	 */
	public String modifyPrice() throws JSONException {
		dataMap = new HashMap<String, Object>();
		System.out.println("modifyPrice");
		
		String custId = super.getCustomer_info_id();
		PfpAdManyURLVO vo = new PfpAdManyURLVO();
		vo.setId(custId);
		vo.setModifyPrice(modifyPrice);
		vo.setSearchURL(searchURL);
		
		pfpAdManyURLSearchService.getRedisURLData(vo);
		
		pfpAdManyURLSearchService.setModifyFieldData(vo, "price");
		if(StringUtils.isNotEmpty(vo.getMessage())){
			dataMap.put("status", "ERROR");
			dataMap.put("msg", vo.getMessage());
			return SUCCESS;
		}
		
		return SUCCESS;
	}
	
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

	public IPfpAdManyURLSearchService getPfpAdManyURLSearchService() {
		return pfpAdManyURLSearchService;
	}

	public void setPfpAdManyURLSearchService(IPfpAdManyURLSearchService pfpAdManyURLSearchService) {
		this.pfpAdManyURLSearchService = pfpAdManyURLSearchService;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public String getModifyPrice() {
		return modifyPrice;
	}

	public void setModifyPrice(String modifyPrice) {
		this.modifyPrice = modifyPrice;
	}

}
