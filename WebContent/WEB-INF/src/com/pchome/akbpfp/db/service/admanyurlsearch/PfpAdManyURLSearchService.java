package com.pchome.akbpfp.db.service.admanyurlsearch;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pchome.akbpfp.api.RedisAPI;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.ad.PfpAdManyURLVO;
import com.pchome.service.portalcms.PortalcmsUtil;
import com.pchome.service.portalcms.bean.Mail;
import com.pchome.soft.depot.utils.HttpUtil;
import com.pchome.soft.util.SpringEmailUtil;

public class PfpAdManyURLSearchService extends BaseService<PfpAdManyURLVO, String>
		implements IPfpAdManyURLSearchService {

	private RedisAPI redisAPI;
	private String manyURLRediskey;
	private SpringEmailUtil springEmailUtil;
	
	/**
	 * 從廣告爬蟲api取得資料，及網址相關檢查
	 * @throws Exception 
	 */
	@Override
	public void getAdCrawlerAPIData(PfpAdManyURLVO vo) throws Exception {
		try {
			StringBuffer adCrawlerResult = HttpUtil.getInstance().doGet("http://pysvr.mypchome.com.tw/product/?url=" + vo.getSearchURL());
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
			
			// 出錯時寄信
			Mail mail = null;
			mail = PortalcmsUtil.getInstance().getMail("P098");
			if (mail == null) {
				throw new Exception("Mail Object is null.");
			}
			String subject = "pfp 呼叫 python api 錯誤 ";
			mail.setMsg("<html><body>網址:" + vo.getSearchURL() + "<br />錯誤訊息 :" + e + "</body></html>");
			springEmailUtil.sendHtmlEmail(subject, mail.getMailFrom(), mail.getMailTo(), mail.getMailBcc(), mail.getMsg());
		}
	}
	
	/**
	 * 檢查api取得資料是否已存在redis，已存在不動作，未存在新增進去
	 * @param vo
	 */
	@Override
	public void checkRedisData(PfpAdManyURLVO vo) {
		try {
			String redisKey = manyURLRediskey + vo.getId() + vo.getRedisCookieVal();
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
	 * @throws IOException 
	 */
	private JSONArray processData(JSONArray apiJsonArray) throws JSONException, IOException {
		JSONArray jsonArray = new JSONArray();
		
		for (int arrayLength = 0; arrayLength < apiJsonArray.length(); arrayLength++) {
			JSONObject apiJsonObjectDetail = new JSONObject(apiJsonArray.get(arrayLength).toString());
			JSONObject jsonObjectDetail = new JSONObject();
			jsonObjectDetail.put("pic_url", processPicURL(apiJsonObjectDetail.get("pic_url").toString()));
			
			String title = apiJsonObjectDetail.get("title").toString();
			jsonObjectDetail.put("title", processTitle(title));
			jsonObjectDetail.put("intact_title", processIntactTitle(title));

			String description = apiJsonObjectDetail.get("description").toString();
			String linkUrl = apiJsonObjectDetail.get("link_url").toString();
			jsonObjectDetail.put("description", processDescription(description, linkUrl));
			jsonObjectDetail.put("intact_description", processIntactDescription(description, linkUrl));
			
			jsonObjectDetail.put("link_url", linkUrl);
			jsonObjectDetail.put("show_url", processShowURL(linkUrl));
			jsonObjectDetail.put("sp_price", processPrice(apiJsonObjectDetail.optString("sp_price")));
			jsonObjectDetail.put("price",    processPrice(apiJsonObjectDetail.get("price").toString()));
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
			// 如果打api拿到的值，在redis資料找不到，則將此筆api資料加入
			if (redisJsonObject.getJSONArray("products").toString().indexOf(apiJsonObjectDetail.get("link_url").toString()) == -1) {
				redisJsonObject.accumulate("products", apiJsonArray.get(i));
			}
		}
		return redisJsonObject;
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

		// 前端js已經先加減頁數，所以需要先扣1，方便計算開始筆數與結束筆數
		// 開始筆數 = 目前頁數 * 每頁幾筆
		int startNum = (vo.getPage() - 1) * vo.getPageSize();
		// 結束筆數 = redis總筆數小餘計算該顯示的總筆數，則使用redis總筆數
		// ex:redis資料僅3筆,但此頁最多可顯示到10筆,則長度抓3,因為也無4~10資料抓3
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
	 * 將修改的欄位更新至redis
	 * 尋找到該字串段落修改後取代
	 * @param vo
	 * @param modifyField price:修改促銷價、detail:修改明細資料標題 描述 顯示網址
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
		if ("price".equals(modifyField)) {
			redisJsonObjectDetail.put("price", vo.getModifyPrice());
		} else if ("detail".equals(modifyField)) {
			redisJsonObjectDetail.put("title", vo.getModifyADTitle());
			redisJsonObjectDetail.put("description", vo.getModifyADContent());
			redisJsonObjectDetail.put("show_url", vo.getModifyADShowURL());
		}
		
		//取代資料
		JSONArray tempJsonArray = new JSONArray(products.replace(data, redisJsonObjectDetail.toString()));
		redisJsonObject.put("products", tempJsonArray);
		
		//更新至redis
		String status = redisAPI.setRedisDataDefaultTimeout(manyURLRediskey + vo.getId() + vo.getRedisCookieVal(), redisJsonObject.toString());
		if (!"OK".equals(status)) { // 存redis失敗
			log.error("ModifyFieldData error:update redisData err");
			vo.setMessage("系統忙碌中，請稍後再試。");
		}
	}
	
	/**
	 * 確認新增至redis網址
	 * */
	public String adConfirmFastPublishUrl(String adFastPublishUrlInfo, String userId, String redisCookieVal) throws Exception {
		String result = "更新成功";
		String redisKey = manyURLRediskey + userId + redisCookieVal;
		String redisData = redisAPI.getRedisData(redisKey);
		JSONObject redisJson = new JSONObject(redisData);
		JSONArray productsJsonArray = redisJson.getJSONArray("products");

		JSONObject adFastPublishUrlInfoJson = new JSONObject(adFastPublishUrlInfo);

		Iterator adFastPublishUrlInfoJsoIterator = adFastPublishUrlInfoJson.keys();
		while (adFastPublishUrlInfoJsoIterator.hasNext()) {
			String key = adFastPublishUrlInfoJsoIterator.next().toString();

			if (adFastPublishUrlInfoJson.get(key).equals("Y")) {
				for (int i = 0; i < productsJsonArray.length(); i++) {
					JSONObject realSaveRedisJson = (JSONObject) productsJsonArray.get(i);
					if (realSaveRedisJson.get("link_url").equals(key.replace("_ckeck_flag", ""))) {
						realSaveRedisJson.put("add", "Y");
						break;
					}
				}
			}

			if (adFastPublishUrlInfoJson.get(key).equals("N")) {
				for (int i = 0; i < productsJsonArray.length(); i++) {
					JSONObject realSaveRedisJson = (JSONObject) productsJsonArray.get(i);
					if (realSaveRedisJson.get("link_url").equals(key.replace("_ckeck_flag", ""))) {
						realSaveRedisJson.put("add", "N");
						break;
					}
				}
			}
		}

		redisJson.put("products", productsJsonArray);
		redisAPI.setRedisDataDefaultTimeout(redisKey, redisJson.toString());

		return result;
	}
	
	/**
	 * 取得redis上，輸入的客編搜尋的網址資料
	 */
	@Override
	public void getRedisURLData(PfpAdManyURLVO vo) throws JSONException {
		String redisKey = manyURLRediskey + vo.getId() + vo.getRedisCookieVal();
		String redisData = redisAPI.getRedisData(redisKey); // 查詢此客戶redis是否有資料
		vo.setRedisJsonObject(new JSONObject(redisData));
	}
	
	/**
	 * 處理標題
	 * 1.篩選掉emoji特殊符號
	 * 2.取最多17個字
	 * @param title
	 * @return
	 */
	private String processTitle(String title) {
		//篩選掉emoji特殊符號
		title = processReplaceAll("emoji", title, "");
		
		// 取最多17個字
		int titleMaxLength = (title.length() > 17) ? 17 : title.length();
		title = title.substring(0, titleMaxLength);
		
		return title;
	}
	
	/**
	 * 處理完整的標題
	 * 1.篩選掉emoji特殊符號
	 * @param title
	 * @return
	 */
	private String processIntactTitle(String intactTitle) {
		//篩選掉emoji特殊符號
		intactTitle = processReplaceAll("emoji", intactTitle, "");
		return intactTitle;
	}
	
	/**
	 * 處理商品描述(內文)
	 * 1.篩選掉emoji特殊符號
	 * 2.取最多36個字
	 * 3.露天跟個人賣場，內容欄位沒有值，則帶入預設文字"直接購買"
	 * @param description
	 * @param string
	 * @return
	 */
	private String processDescription(String description, String url) {
		//篩選掉emoji特殊符號
		description = processReplaceAll("emoji", description, "");
		
		// 取最多36個字
		int descriptionMaxLength = (description.length() > 36) ? 36 : description.length();
		description = description.substring(0, descriptionMaxLength);

		description = processDescriptionFromURL(description, url);

		return description;
	}
	
	/**
	 * 處理完整的商品描述(內文)
	 * 1.露天跟個人賣場，內容欄位沒有值，則帶入預設文字"直接購買"
	 * @param intactDescription
	 * @param url
	 * @return
	 */
	private String processIntactDescription(String intactDescription, String url) {
		//篩選掉emoji特殊符號
		intactDescription = processReplaceAll("emoji", intactDescription, "");
		
		intactDescription = processDescriptionFromURL(intactDescription, url);
		
		return intactDescription;
	}
	
	/**
	 * 處理取代，可用正規表示式
	 * @param flag emoji(手機特殊符號) 或 無
	 * @param string 被處理字串
	 * @param newString 取代成為新字串
	 * @return
	 */
	private String processReplaceAll(String flag, String string, String newString) {
		if ("emoji".equalsIgnoreCase(flag)) {
			string = string.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", newString);
		} else {
			string = string.replaceAll(string, newString);
		}
		return string;
	}
	
	/**
	 * 依據不同網址，處理相對應的商品描述(內文)
	 * @param description
	 * @param url
	 * @return
	 */
	private String processDescriptionFromURL(String description, String url){
		// 露天或個人賣場沒有值，改為直接購買
		if ((url.indexOf("goods.ruten.com.tw") > -1 || url.indexOf("seller.pcstore.com.tw") > -1)
				&& description.length() == 0) {
			description = "直接購買";
		}
		return description;
	}
	
	/**
	 * 處理價格
	 * 金錢符號、逗號去除
	 * @param price
	 * @return
	 */
	private String processPrice(String price) {
		price = price.replace("$", "");
		price = price.replace(",", "");
		return price;
	}
	
	/**
	 * 處理明細顯示網址，取得Domain部份
	 * @param url
	 * @return
	 */
	private String processShowURL(String url) {
		url = url.substring(url.indexOf("://") + 3);
		url = url.substring(0, url.indexOf("/"));
		
		// 如果是露天商品頁，調整為露天首頁
		if ("goods.ruten.com.tw".equals(url)) { 
			url = "www.ruten.com.tw";
		}
		
		return url;
	}

	/**
	 * 處理明細圖片路徑
	 * 1.沒有商品圖
	 * 2.有圖片路徑修正
	 * @param picURL
	 * @return
	 * @throws IOException 
	 */
	private String processPicURL(String picURL) throws IOException {
		// 沒有商品圖
		if(picURL.indexOf("no-product") > -1){
			picURL = "img/public/na.gif\" style=\"display:none";
			return picURL;
		}
		
		// 處理圖片路徑
		String URLHttp = picURL.substring(0, 6);
		if (URLHttp.indexOf("//") > -1) {
			picURL = "http:" + picURL;
		} else if (URLHttp.indexOf("http:") == -1 && URLHttp.indexOf("https:") == -1) {
			picURL = "http://" + picURL;
		}
		
		String filenameExtension = picURL.substring(picURL.length() -3 , picURL.length()); // 取得副檔名
		//長方形 GIF，圖片擋掉只留文字廣告
		if ("gif".equalsIgnoreCase(filenameExtension)) {
			URL url = new URL(picURL);
			BufferedImage img = ImageIO.read(url);
			int width = img.getWidth();
			int height = img.getHeight();
			if(width != height){ // 長寬不相同，為長方形
				picURL = "img/public/na.gif\" style=\"display:none";
				return picURL;
			}
		}
		
		return picURL;
	}
	
	public RedisAPI getRedisAPI() {
		return redisAPI;
	}

	public void setRedisAPI(RedisAPI redisAPI) {
		this.redisAPI = redisAPI;
	}

	public void setManyURLRediskey(String manyURLRediskey) {
		this.manyURLRediskey = manyURLRediskey;
	}

	public void setSpringEmailUtil(SpringEmailUtil springEmailUtil) {
		this.springEmailUtil = springEmailUtil;
	}

}
