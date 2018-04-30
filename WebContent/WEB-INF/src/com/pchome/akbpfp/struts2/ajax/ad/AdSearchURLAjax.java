package com.pchome.akbpfp.struts2.ajax.ad;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;

import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.service.ad.IPfpAdActionService;
import com.pchome.akbpfp.db.service.ad.IPfpAdGroupService;
import com.pchome.akbpfp.db.service.admanyurlsearch.IPfpAdManyURLSearchService;
import com.pchome.akbpfp.db.vo.ad.PfpAdManyURLVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.cookie.EnumCookieConstants;
import com.pchome.soft.depot.utils.CookieUtil;
import com.pchome.utils.CommonUtils;

public class AdSearchURLAjax extends BaseCookieAction{
	
	private IPfpAdManyURLSearchService pfpAdManyURLSearchService;
	private String akbPfpServer;
	
	private String searchURL;  //輸入的網址
	private int page = 1;      //第幾頁(初始預設第1頁)
	private int pageSize = 5; //每頁筆數(初始預設每頁N筆)
	private int totalPage = 1; //總頁數(初始預設1頁)
	private String modifyPrice; //ajax傳進來的修改促銷價
	private String modifyADTitle; //ajax傳進來的修改標題
	private String modifyADContent; //ajax傳進來的修改商品描述
	private String modifyADShowURL; //ajax傳進來的修改顯示連結
	private String adActionSeq; //廣告活動序號
	private String adGroupSeq; //廣告分類序號
	private String adFastPublishUrlInfo;
	private Map<String,Object> dataMap;
	private List<PfpAdGroup> pfpAdGroupList;
	private IPfpAdActionService pfpAdActionService;
	private IPfpAdGroupService pfpAdGroupService;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 1.檢查輸入網址，打廣告爬蟲api取得資料
	 * 2.廣告爬蟲api取得資料與redis內的資料比對，是否重複，存入redis前先將資料整理後再存
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
			vo.setRedisCookieVal(CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_PFPCART.getValue(), null));
			
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
		vo.setRedisCookieVal(CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_PFPCART.getValue(), null));

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
		
		String custId = super.getCustomer_info_id();
		PfpAdManyURLVO vo = new PfpAdManyURLVO();
		vo.setId(custId);
		vo.setModifyPrice(modifyPrice);
		vo.setSearchURL(searchURL);
		vo.setRedisCookieVal(CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_PFPCART.getValue(), null));
		
		pfpAdManyURLSearchService.getRedisURLData(vo);
		
		pfpAdManyURLSearchService.setModifyFieldData(vo, "price");
		if(StringUtils.isNotEmpty(vo.getMessage())){
			dataMap.put("status", "ERROR");
			dataMap.put("msg", vo.getMessage());
			return SUCCESS;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 查詢結果修改廣告明細按鈕事件
	 * @return
	 * @throws Exception 
	 */
	public String modifyADDetail() throws Exception {
		dataMap = new HashMap<String, Object>();
		
		String custId = super.getCustomer_info_id();
		PfpAdManyURLVO vo = new PfpAdManyURLVO();
		vo.setId(custId);
		vo.setSearchURL(searchURL);
		vo.setModifyADTitle(modifyADTitle);
		vo.setModifyADContent(modifyADContent);
		vo.setModifyADShowURL(modifyADShowURL);
		vo.setRedisCookieVal(CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_PFPCART.getValue(), null));
		
		AdUtilAjax adUtilAjax = new AdUtilAjax();
		String msg = adUtilAjax.checkAdShowUrl(modifyADShowURL, akbPfpServer);
		if (StringUtils.isNotEmpty(msg)) {
			dataMap.put("status", "ERROR");
			dataMap.put("msg", msg);
			return SUCCESS;
		}
		
		pfpAdManyURLSearchService.getRedisURLData(vo);
		
		pfpAdManyURLSearchService.setModifyFieldData(vo, "detail");
		if(StringUtils.isNotEmpty(vo.getMessage())){
			dataMap.put("status", "ERROR");
			dataMap.put("msg", vo.getMessage());
			return SUCCESS;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 快速上稿
	 * */
	public String adConfirmFastPublishUrl() throws Exception{
		log.info(">>>>>> START adFastPublishAction");
		dataMap = new HashMap<String, Object>();
		dataMap.put("alex", "CCC");
		pfpAdManyURLSearchService.adConfirmFastPublishUrl(adFastPublishUrlInfo, super.getCustomer_info_id(), CookieUtil.getCookie(request, EnumCookieConstants.COOKIE_PFPCART.getValue(), null));
		return SUCCESS;
	}
	
	/**
	 * 廣告活動設定資訊
	 * */
	public String adActionInfo() throws Exception{
		PfpAdAction pfpAdAction = pfpAdActionService.get(adActionSeq);
		Set<PfpAdGroup> groupSet = pfpAdAction.getPfpAdGroups();
		List<PfpAdGroup> list = new ArrayList<>(groupSet);
		dataMap = new HashMap<String, Object>();
		dataMap.put(adActionSeq,pfpAdAction.getAdActionSeq() );
		dataMap.put("defaultAdType", pfpAdAction.getAdType());
		dataMap.put("defaultAdOperatingRule", pfpAdAction.getAdOperatingRule());
		dataMap.put("defaultAdDevice", pfpAdAction.getAdDevice());
		dataMap.put("defaultAdActionMax", (int)pfpAdAction.getAdActionMax());
		dataMap.put("defaultAdActionEndDate", sdf.format(pfpAdAction.getAdActionEndDate()));
		dataMap.put("defaultAdActionStartDate", sdf.format(pfpAdAction.getAdActionStartDate()));
		Map<String,String> adGroupsMap = new HashMap<>();
		for (PfpAdGroup pfpAdGroup : groupSet) {
			adGroupsMap.put(pfpAdGroup.getAdGroupSeq()+"_"+(int)pfpAdGroup.getAdGroupChannelPrice(), pfpAdGroup.getAdGroupName());
		}
		dataMap.put("adGroups", adGroupsMap);
		
		if(list.size() > 0){
			PfpAdGroup pfpAdGroup = list.get(0);
			dataMap.put("adGroupChannelPrice", (int)pfpAdGroup.getAdGroupChannelPrice());
			dataMap.put("adGroupSearchPrice", (int)pfpAdGroup.getAdGroupSearchPrice());
			dataMap.put("adGroupSearchPriceType", (int)pfpAdGroup.getAdGroupSearchPriceType());
		}
		return SUCCESS;
	}
	
	
	/**
	 * 廣告分類設定資訊
	 * */
	public String adGroupInfo() throws Exception{
		PfpAdGroup pfpAdGroup = pfpAdGroupService.get(adGroupSeq);
		dataMap = new HashMap<String, Object>();
		dataMap.put("adGroupChannelPrice", (int)pfpAdGroup.getAdGroupChannelPrice());
		dataMap.put("adGroupSearchPrice", (int)pfpAdGroup.getAdGroupSearchPrice());
		dataMap.put("adGroupSearchPriceType", (int)pfpAdGroup.getAdGroupSearchPriceType());
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

	public String getAdFastPublishUrlInfo() {
		return adFastPublishUrlInfo;
	}

	public void setAdFastPublishUrlInfo(String adFastPublishUrlInfo) {
		this.adFastPublishUrlInfo = adFastPublishUrlInfo;
	}

	public String getAdActionSeq() {
		return adActionSeq;
	}

	public void setAdActionSeq(String adActionSeq) {
		this.adActionSeq = adActionSeq;
	}

	public IPfpAdActionService getPfpAdActionService() {
		return pfpAdActionService;
	}

	public void setPfpAdActionService(IPfpAdActionService pfpAdActionService) {
		this.pfpAdActionService = pfpAdActionService;
	}

	public List<PfpAdGroup> getPfpAdGroupList() {
		return pfpAdGroupList;
	}

	public void setPfpAdGroupList(List<PfpAdGroup> pfpAdGroupList) {
		this.pfpAdGroupList = pfpAdGroupList;
	}

	public String getAkbPfpServer() {
		return akbPfpServer;
	}

	public void setAkbPfpServer(String akbPfpServer) {
		this.akbPfpServer = akbPfpServer;
	}

	public String getAdGroupSeq() {
		return adGroupSeq;
	}

	public void setAdGroupSeq(String adGroupSeq) {
		this.adGroupSeq = adGroupSeq;
	}

	public IPfpAdGroupService getPfpAdGroupService() {
		return pfpAdGroupService;
	}

	public void setPfpAdGroupService(IPfpAdGroupService pfpAdGroupService) {
		this.pfpAdGroupService = pfpAdGroupService;
	}

}
