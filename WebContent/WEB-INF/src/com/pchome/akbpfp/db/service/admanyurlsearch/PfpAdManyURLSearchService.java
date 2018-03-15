package com.pchome.akbpfp.db.service.admanyurlsearch;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pchome.akbpfp.api.RedisAPI;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.ad.PfpAdManyURLVO;
import com.pchome.soft.depot.utils.HttpUtil;

public class PfpAdManyURLSearchService extends BaseService<PfpAdManyURLVO, String>
		implements IPfpAdManyURLSearchService {

	private RedisAPI redisAPI;
	
	/**
	 * 從廣告爬蟲api取得資料，及網址相關檢查
	 * api 500error 將服務重啟，參考指令如下
	 * pkill -9 -f uwsgi
	 * uwsgi --ini /var/www/api_project/api/apiconf.ini > /var/log/uwsgi/uwsgi.log 2>&1 &
	 */
	@Override
	public void getAdCrawlerAPIData(PfpAdManyURLVO vo) {
		try {
			StringBuffer adCrawlerResult = HttpUtil.getInstance().doGet("http://adcrawler.mypchome.com.tw:8080/product/?url=" + vo.getSearchURL());
			JSONObject apiJsonObject = new JSONObject(adCrawlerResult.toString());
			if (adCrawlerResult.toString().indexOf("status:200") == -1) { // 檢查連線是否正常
				log.error("getAdCrawlerAPIData error:status != 200");
				vo.setMessage("系統忙碌中，請稍後再試。");
			} else if (apiJsonObject.length() == 0) { // 檢查輸入網址是否正確
				log.info("getAdCrawlerAPIData error:URL error " + vo.getSearchURL());
				vo.setMessage("查無資料，請確認輸入網址是否正確。");
			} else {
				// 正確，將資料寫入vo
				vo.setApiJsonArray(apiJsonObject.getJSONArray("products"));
			}
		} catch (Exception e) {
			log.error("getAdCrawlerAPIData error:" + e);
			vo.setMessage("系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
		}
	}
	
	/**
	 * 檢查api取得資料是否已存在redis，已存在不動作，未存在新增進去
	 */
	@Override
	public void checkRedisData(PfpAdManyURLVO vo) {
		try {
			String redisKey = "pfpcart_" + vo.getId();
			String redisData = redisAPI.getRedisData(redisKey); // 查詢此客戶redis是否有資料

			JSONArray apiJsonArray = vo.getApiJsonArray();
			//存入Redis前，先將資料處理
			apiJsonArray = processData(apiJsonArray);
			
			JSONObject redisJsonObject = null;
			
			if (StringUtils.isNotBlank(redisData)) { // redis有資料
				redisJsonObject = new JSONObject(redisData);
				// 檢查api回傳的資料是否已經是在redis內，如果api資料沒在redis內則新增進去
				redisJsonObject = checkRedisData(apiJsonArray, redisJsonObject);
			} else { // redis沒資料，第一次新增時處理
				JSONObject tempJsonObject = new JSONObject();
				tempJsonObject.put("products", apiJsonArray);
				redisJsonObject = new JSONObject(tempJsonObject.toString());
			}
			
			String status = redisAPI.setRedisDataDefaultTimeout(redisKey, redisJsonObject.toString());
			if ("OK".equals(status)) {
				vo.setRedisJsonObject(redisJsonObject);
			} else { // 存redis失敗
				log.error("checkRedisData error:insert redisData err");
				vo.setMessage("系統忙碌中，請稍後再試。");
			}
		} catch (Exception e) {
			log.error("checkRedisData error:" + e);
			vo.setMessage("系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
		}
	}
	
	/**
	 * 存入Redis前，先將資料處理，取得的資料一次最多僅20筆
	 * @param apiJsonArray
	 * @return
	 * @throws JSONException 
	 */
	private JSONArray processData(JSONArray apiJsonArray) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		
		for (int arrayLength = 0; arrayLength < apiJsonArray.length(); arrayLength++) {
			JSONObject apiJsonObjectDetail = new JSONObject(apiJsonArray.get(arrayLength).toString());
			JSONObject jsonObjectDetail = new JSONObject();
			jsonObjectDetail.put("pic_url", processPicURL(apiJsonObjectDetail.get("pic_url").toString()));
			jsonObjectDetail.put("title", apiJsonObjectDetail.get("title").toString());
			jsonObjectDetail.put("description", apiJsonObjectDetail.get("description").toString());
			jsonObjectDetail.put("link_url", apiJsonObjectDetail.get("link_url").toString());
			jsonObjectDetail.put("show_url", processShowURL(apiJsonObjectDetail.get("link_url").toString()));
			jsonObjectDetail.put("sp_price", apiJsonObjectDetail.optString("sp_price"));
			jsonObjectDetail.put("price", apiJsonObjectDetail.get("price").toString());
			//只有24h才有此參數
			jsonObjectDetail.put("suggest", apiJsonObjectDetail.opt("suggest"));
			
			jsonArray.put(jsonObjectDetail);
		}
		return jsonArray;
	}

	/**
	 * 檢查api回傳的資料是否已經是在redis內的
	 * api取的資料最多20筆，由少比對多資料
	 * 
	 * @param apiJsonArray
	 * @param redisJsonObject
	 * @return
	 * @throws JSONException 
	 */
	private JSONObject checkRedisData(JSONArray apiJsonArray, JSONObject redisJsonObject) throws JSONException {
		for (int i = 0; i < apiJsonArray.length(); i++) {
			JSONObject apiJsonObjectDetail = new JSONObject(apiJsonArray.get(i).toString());
			//如果打api拿到的值，在redis資料找不到，則將此筆api資料加入
			if(redisJsonObject.getJSONArray("products").toString().indexOf(apiJsonObjectDetail.get("link_url").toString()) == -1){
				redisJsonObject.accumulate("products", apiJsonArray.get(i));
			}
		}
		return redisJsonObject;
	}
	
	/**
	 * 處理明細顯示網址，取得Domain部份
	 * @param url
	 * @return
	 */
	private String processShowURL(String url) {
		url = url.substring(url.indexOf("://") + 3);
		url = url.substring(0, url.indexOf("/"));
		return url;
	}

	/**
	 * 處理明細圖片路徑
	 * @param picURL
	 * @return
	 */
	private String processPicURL(String picURL) {
		String URLHttp = picURL.substring(0,6);
		if(URLHttp.indexOf("//") > -1){
			picURL = "http:" + picURL;
		}else if(URLHttp.indexOf("http:") == -1 && URLHttp.indexOf("https:") == -1){
			picURL = "http://" + picURL;
		}
		return picURL;
	}

	/**
	 * 取得redis上，輸入的客編搜尋的網址資料
	 */
	@Override
	public void getRedisURLData(PfpAdManyURLVO vo) throws JSONException {
		String redisKey = "pfpcart_" + vo.getId();
		String redisData = redisAPI.getRedisData(redisKey); // 查詢此客戶redis是否有資料
		vo.setRedisJsonObject(new JSONObject(redisData));
	}
	
	/**
	 * 依照每頁筆數，及目前頁數取得相對應資料
	 * @throws JSONException 
	 */
	@Override
	public void getRedisLimitData(PfpAdManyURLVO vo) throws JSONException {
		
		JSONObject redisJsonObject = vo.getRedisJsonObject();
		JSONArray redisJsonArray = redisJsonObject.getJSONArray("products");
		JSONArray tempJsonArray = new JSONArray();
		
		// 開始筆數 = 目前頁數 * 每頁幾筆
		int startNum = (vo.getPage() - 1) * vo.getPageSize();
		// 結束筆數 = redis總筆數小餘計算該顯示的總筆數，則使用redis總筆數 
		//ex:redis資料僅3筆,但此頁最多可顯示到10筆,則長度抓3,因為也無4~10資料抓3
		int maxNum = vo.getPage() * vo.getPageSize();
		int endNum = redisJsonArray.length() < maxNum ? redisJsonArray.length() : maxNum;
		for (int i = startNum; i < endNum; i++) {
			tempJsonArray.put(redisJsonArray.get(i));
		}
		redisJsonObject.put("products", tempJsonArray);
		vo.setRedisJsonObject(redisJsonObject);
		vo.setRedisDataTotalSize(redisJsonArray.length());
	}

	/**
	 * 將修改的欄位資料更新至redis
	 * 尋找到該字串段落修改後取代
	 * @throws JSONException 
	 */
	@Override
	public void setModifyFieldData(PfpAdManyURLVO vo, String modifyField) throws JSONException {
		JSONObject redisJsonObject = vo.getRedisJsonObject();
		String products = redisJsonObject.getJSONArray("products").toString();
		
		//找到URL位置
		int index = products.indexOf(vo.getSearchURL());
		
		//取得URL前後段字串
		String startStr = products.substring(0, index);
		String endStr = products.substring(index, products.length());
		
		//由前後段字串找到包url那一段的位置
		int startStrIndex = startStr.lastIndexOf("{\"");
		int endStrIndex = endStr.indexOf("\"}");
		
		//取得所需要斷落字串
		String data = products.substring(startStrIndex, (startStr.length() + endStrIndex + 2));
		
		//修改資料
		JSONObject redisJsonObjectDetail = new JSONObject(data.toString());
		if("price".equals(modifyField)){
			redisJsonObjectDetail.put("price", vo.getModifyPrice());
		}else if("detail".equals(modifyField)){
			redisJsonObjectDetail.put("title", vo.getModifyADTitle());
			redisJsonObjectDetail.put("description", vo.getModifyADContent());
			redisJsonObjectDetail.put("show_url", vo.getModifyADShowURL());
		}
		
		//取代資料
		JSONArray tempJsonArray = new JSONArray(products.replace(data, redisJsonObjectDetail.toString()));
		redisJsonObject.put("products", tempJsonArray);
		
		//更新至redis
		String status = redisAPI.setRedisDataDefaultTimeout("pfpcart_" + vo.getId(), redisJsonObject.toString());
		if (!"OK".equals(status)) { // 存redis失敗
			log.error("ModifyFieldData error:insert redisData err");
			vo.setMessage("系統忙碌中，請稍後再試。");
		}
	}
	
	
	/*
	 * 確認新增至redis網址
	 * */
	public String adConfirmFastPublishUrl(String adFastPublishUrlInfo,String userId) throws Exception{
		String result = "更新成功";
		String redisKey = "pfpcart_" + userId;
		String redisData = redisAPI.getRedisData(redisKey);
		JSONObject redisJson = new JSONObject(redisData);
		JSONArray productsJsonArray = redisJson.getJSONArray("products");
		
		JSONObject adFastPublishUrlInfoJson = new JSONObject(adFastPublishUrlInfo); 
		
		Iterator adFastPublishUrlInfoJsoIterator = adFastPublishUrlInfoJson.keys();
        while (adFastPublishUrlInfoJsoIterator.hasNext()) {
        	String key = adFastPublishUrlInfoJsoIterator.next().toString();
        	
        	if(adFastPublishUrlInfoJson.get(key).equals("Y")){
        		for (int i = 0; i < productsJsonArray.length(); i++) {
	        		JSONObject realSaveRedisJson = (JSONObject) productsJsonArray.get(i);
	        		if(realSaveRedisJson.get("link_url").equals(key.replace("_ckeck_flag", ""))){
	        			realSaveRedisJson.put("add", "Y");
	        			break;
	        		}
				}
        	}
        	
        	if(adFastPublishUrlInfoJson.get(key).equals("N")){
        		for (int i = 0; i < productsJsonArray.length(); i++) {
	        		JSONObject realSaveRedisJson = (JSONObject) productsJsonArray.get(i);
	        		if(realSaveRedisJson.get("link_url").equals(key.replace("_ckeck_flag", ""))){
	        			realSaveRedisJson.put("add", "N");
	        			break;
	        		}
				}
        	}
        }
        
        
        redisJson.put("products", productsJsonArray);
        redisAPI.setRedisDataDefaultTimeout("pfpcart_" + userId, redisJson.toString());
       
		return result;
		
	}
	
	
	
	public RedisAPI getRedisAPI() {
		return redisAPI;
	}

	public void setRedisAPI(RedisAPI redisAPI) {
		this.redisAPI = redisAPI;
	}

	

}
