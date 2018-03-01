package com.pchome.akbpfp.struts2.ajax.ad;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pchome.akbpfp.api.RedisAPI;
import com.pchome.akbpfp.struts2.BaseCookieAction;



public class AdSearchURLAjax extends BaseCookieAction{
	
	private RedisAPI redisAPI;
	
	private String searchURL;  //輸入的網址
	private int page = 1;      //第幾頁(初始預設第1頁)
	private int pageSize = 10; //每頁筆數(初始預設每頁N筆)
	private int totalPage = 0; //總頁數
	
	/**
	 * 打董哥api > 取得資料後存入redis > 取出redis顯示
	 * @return
	 */
	public String searchURLData() {
		try {
			System.out.println("test LOOK");
			System.out.println("keyinURL:" + searchURL);

			//假設打董哥api取得回傳資料
//			String apiResult = "{\"products\": [{\"sp_price\": \"21990\", \"price\": \"21990\", \"pic_url\": \"//a.ecimg.tw/items/DGBQ1AA9007MI82/000001_1515150597.jpg\", \"description\": \"★106/12/21—107/02/28 將發票＆保卡回函元佑公司再加贈  \n1.郵政禮卷1000 \n2.原廠皮套\n\n■ 觸控式自拍螢幕 \n■ 內建WIFI/OI SHARE無線操控 \n■ 14種藝術濾鏡 \n■ 8fps 連拍/81 點對焦/峰值對焦功能 \n■全自動HDR高動態範圍相片  \n■本組合含ED14-42mmF3.5-5.6 EZ電動變焦鏡頭，圖片僅供參考。謝謝！\n★隨貨超值送 \n1.SanDisk Ultra 高速SDXC 64GB 記憶卡\n2.原廠鋰電池(含標配共兩顆)\n3.強力吹球+清潔拭鏡布+清潔拭鏡筆\n4.多合一讀卡機+桌上型小腳架+清潔組+通用型螢幕保護貼\n5.專用快門線\n6.專用HDMI線\n7.熱靴蓋水平儀\n8.日本KENKO 37mm超薄框多層鍍膜UV保護鏡\n9.專用副廠鋰電池\", \"link_url\": \"http://24h.pchome.com.tw/prod/DGBQ1A-A9007MI82\", \"title\": \"★64G原電UVOLYMPUS E-PL8 14-42mm EZ 電動鏡組 (公司貨)\", \"suggest\": [\"★\", \"64g\", \"原電\", \"uvolympus\", \"e\", \"pl8\", \"14\", \"42mm\", \"ez\", \"電動\", \"鏡組\", \"公司\", \"貨\", \"全配\", \"配組\", \"防手\", \"電保棒\", \"震微\", \"震\", \"三原\", \"pl7\", \"大全\", \"包腳\", \"電充\", \"距鏡\", \"870\", \"selp1650\", \"42mmi\", \"sel85f18\", \"sel1224g\", \"全片\", \"恆伸\", \"原電全\", \"幅中距\", \"5.8\", \"5100l\", \"快筆\", \"恆定\", \"司\", \"卡爾蔡\", \"g2\", \"望遠\", \"za\", \"原電組\", \"f1.8\", \"電池組\", \"b028\", \"a7m2k\", \"電\", \"b005\", \"a032\", \"人像鏡\", \"model\", \"le\", \"ssm\", \"a022\", \"清組\", \"rx100m2\", \"45mm\", \"50mm\", \"雙原\", \"每秒\", \"28\", \"90mm\", \"ax700\", \"ectreme\", \"f012\", \"80mm\", \"雙鏡組\", \"7sm2\", \"自拍機\", \"軸\", \"焦微\", \"tough\", \"遠攝\", \"幅\", \"包鏡\", \"原包組\", \"等效\", \"雙子星\", \"12\", \"45stm\", \"a007\", \"at90m\", \"zm\", \"送拭\", \"c001\", \"oss\", \"42\", \"m1240\", \"dn\", \"圈定\", \"sel90m28g\", \"17mm\", \"拆鏡\", \"m5\", \"x450\", \"6300l\", \"135\", \"新鏡\", \"70mm\", \"清全配\", \"a012\", \"200nbenro\", \"rx100m3\", \"hx400v\", \"sel100400gm\", \"sx610\", \"75mm\", \"a010\", \"as50r\", \"銳利\", \"f004\"]}]}";
			String apiResult = "{\"products\": [{\"sp_price\": \"8763\", \"price\": \"8763\", \"pic_url\": \"//a.ecimg.tw/items/DGBQ1AA9007MI82/000001_1515158763.jpg\", \"description\": \"★106/12/21—107/02/28 將發票＆保卡回函元佑公司再加贈  \n1.郵政禮卷1000 \n2.原廠皮套\n\n■ 觸控式自拍螢幕 \n■ 內建WIFI/OI SHARE無線操控 \n■ 14種藝術濾鏡 \n■ 8fps 連拍/81 點對焦/峰值對焦功能 \n■全自動HDR高動態範圍相片  \n■本組合含ED14-42mmF3.5-5.6 EZ電動變焦鏡頭，圖片僅供參考。謝謝！\n★隨貨超值送 \n1.SanDisk Ultra 高速SDXC 64GB 記憶卡\n2.原廠鋰電池(含標配共兩顆)\n3.強力吹球+清潔拭鏡布+清潔拭鏡筆\n4.多合一讀卡機+桌上型小腳架+清潔組+通用型螢幕保護貼\n5.專用快門線\n6.專用HDMI線\n7.熱靴蓋水平儀\n8.日本KENKO 37mm超薄框多層鍍膜UV保護鏡\n9.專用副廠鋰電池\", \"link_url\": \"http://24h.pchome.com.tw/prod/DGBQ1A-C8763\", \"title\": \"★64G原電UVOLYMPUS E-PL8 14-42mm EZ 電動鏡組 (公司貨)\", \"suggest\": [\"★\", \"64g\", \"原電\", \"uvolympus\", \"e\", \"pl8\", \"14\", \"42mm\", \"ez\", \"電動\", \"鏡組\", \"公司\", \"貨\", \"全配\", \"配組\", \"防手\", \"電保棒\", \"震微\", \"震\", \"三原\", \"pl7\", \"大全\", \"包腳\", \"電充\", \"距鏡\", \"870\", \"selp1650\", \"42mmi\", \"sel85f18\", \"sel1224g\", \"全片\", \"恆伸\", \"原電全\", \"幅中距\", \"5.8\", \"5100l\", \"快筆\", \"恆定\", \"司\", \"卡爾蔡\", \"g2\", \"望遠\", \"za\", \"原電組\", \"f1.8\", \"電池組\", \"b028\", \"a7m2k\", \"電\", \"b005\", \"a032\", \"人像鏡\", \"model\", \"le\", \"ssm\", \"a022\", \"清組\", \"rx100m2\", \"45mm\", \"50mm\", \"雙原\", \"每秒\", \"28\", \"90mm\", \"ax700\", \"ectreme\", \"f012\", \"80mm\", \"雙鏡組\", \"7sm2\", \"自拍機\", \"軸\", \"焦微\", \"tough\", \"遠攝\", \"幅\", \"包鏡\", \"原包組\", \"等效\", \"雙子星\", \"12\", \"45stm\", \"a007\", \"at90m\", \"zm\", \"送拭\", \"c001\", \"oss\", \"42\", \"m1240\", \"dn\", \"圈定\", \"sel90m28g\", \"17mm\", \"拆鏡\", \"m5\", \"x450\", \"6300l\", \"135\", \"新鏡\", \"70mm\", \"清全配\", \"a012\", \"200nbenro\", \"rx100m3\", \"hx400v\", \"sel100400gm\", \"sx610\", \"75mm\", \"a010\", \"as50r\", \"銳利\", \"f004\"]}]}";
			JSONObject apiJsonObject = new JSONObject(apiResult.replace("\n", "\\n"));
	//		JSONArray apiJsonArray = new JSONArray(apiJsonObject.get("products").toString());
			JSONArray apiJsonArray = apiJsonObject.getJSONArray("products");
			System.out.println("API:" + apiJsonArray.get(0));
				
			String redisKey = "pfpcart_" + super.getCustomer_info_id();
			String redisData = redisAPI.getRedisData(redisKey); // 查詢此客戶redis是否有資料
			JSONObject redisJsonObject = null;
			JSONArray redisJsonArray = null;
			if(redisData != null){ //有資料
				redisJsonObject = new JSONObject(redisData.replace("\n", "\\n"));
				redisJsonArray = redisJsonObject.getJSONArray("products");
				//檢查api回傳的資料是否已經是在redis內，如果api資料沒在redis內則新增進去
				redisJsonObject = checkRedisData(apiJsonArray, redisJsonArray, redisJsonObject);
			}
			
			//將資料整理，存入redis，redis有資料存整理過的，沒資料存董哥api取得的資料
			redisAPI.setRedisDataDefaultTimeout(redisKey, redisJsonObject != null ? redisJsonObject.toString() : apiJsonObject.toString());
			System.out.println(redisAPI.getRedisData(redisKey));
		
		
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("error:" +e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 新增商品網址按鈕執行
	 * @return
	 */
//	public String searchAddURLData() {
//		try {
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("error:" +e.getMessage());
//		}
//		return SUCCESS;
//	}
	
	
	/**
	 * 檢查api回傳的資料是否已經是在redis內的
	 * @param apiJsonArray
	 * @param redisJsonArray
	 * @param redisJsonObject 
	 * @return 
	 */
	private JSONObject checkRedisData(JSONArray apiJsonArray, JSONArray redisJsonArray, JSONObject redisJsonObject) {
		try {
			JSONObject apiJsonObjectDetil;
			for (int i = 0; i < apiJsonArray.length(); i++) {
				apiJsonObjectDetil = new JSONObject(apiJsonArray.get(i).toString());
				//如果打api拿到的值，在redis資料找不到，則將此筆api資料加入
				if(redisJsonArray.toString().indexOf(apiJsonObjectDetil.get("link_url").toString()) == -1){
					redisJsonObject.accumulate("products", apiJsonArray.get(i));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return redisJsonObject;
	}
	


	/**
	 * 設定查詢結果每頁幾筆項目
	 * @return
	 */
	public List<String> getPageSizeList() {
		List<String> list = new ArrayList<String>();
		list.add("20");
		list.add("50");
		list.add("100");
		return list;
	}

	public RedisAPI getRedisAPI() {
		return redisAPI;
	}

	public void setRedisAPI(RedisAPI redisAPI) {
		this.redisAPI = redisAPI;
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
	
	
	
//	/** 未整理版
//	 * 檢查api回傳的資料是否已經是在redis內的
//	 * @param apiJsonArray
//	 * @param redisJsonArray
//	 * @param redisJsonObject 
//	 * @return 
//	 */
//	private JSONObject checkRedisData(JSONArray apiJsonArray, JSONArray redisJsonArray, JSONObject redisJsonObject) {
//		try {
//			JSONObject redisJsonObjectDetil;
//			JSONObject apiJsonObjectDetil;
//			for (int i = 0; i < apiJsonArray.length(); i++) {
//				apiJsonObjectDetil = new JSONObject(apiJsonArray.get(i).toString());
//				//如果打api拿到的值，在redis資料找不到，則將此筆api資料加入
//				if(apiJsonObjectDetil.get("link_url").toString().indexOf(redisJsonArray.toString()) == -1){
//					redisJsonObject.accumulate("products", apiJsonArray.get(i));
//				}
//				
////				for (int j = 0; j < redisJsonArray.length(); j++) {
////					redisJsonObjectDetil = new JSONObject(redisJsonArray.get(j).toString());
////					
////					if(apiJsonObjectDetil.get("link_url").equals(redisJsonObjectDetil.get("link_url"))){
////						continue;
////					}else{
////						redisJsonObject.accumulate("products", apiJsonArray.get(i));
////					}
////				}
//			}
//			
//			System.out.println("qqqqqqqqq:" + redisJsonObject.toString());
//			System.out.println("aaaaaa:" + redisJsonArray.toString());
//			
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return redisJsonObject;
//	}
	
//	String redisKey = "pfpcart_" + super.getCustomer_info_id();
////JSONObject redisJsonObject = null;
//JSONObject redisJsonObject = new JSONObject(redisAPI.getRedisData(redisKey));
//JSONArray redisJsonArray = null;
////檢查此客戶redis是否有資料
////if(redisAPI.getRedisData(redisKey) != null){
//if(redisJsonObject == null){
//	redisJsonObject = apiJsonObject;
//}else if(redisJsonObject != null){
////	redisJsonObject = new JSONObject(redisAPI.getRedisData(redisKey));
//	redisJsonArray = redisJsonObject.getJSONArray("products");
//	//檢查api回傳的資料是否已經是在redis內的
//	redisJsonObject = checkRedisData(apiJsonArray, redisJsonArray, redisJsonObject);
//}
//
////將資料整理，存入redis
//redisAPI.setRedisData(redisKey, 6000, redisJsonObject.toString());
//System.out.println(redisAPI.getRedisData(redisKey));
	
	
	
	
	
	
	
	
//	
//	private static final long serialVersionUID = 1L;
//	private AdReportVO adReportVO;
//	private String previewUrl;
//	private IPfpAdService pfpAdService;
//	private String adGroupSeq;
//	private String keyword;
//	private String searchType;
//	private String startDate;
//	private String endDate;
//	private List<PfpAdAdViewVO> adAdViewVOList;
//	private List<AdReportVO> adReportVOList;
//	private int pageNo = 1;       				// 初始化目前頁數
//	private int pageSize = 20;     				// 初始化每頁幾筆
//	private int pageCount = 0;    				// 初始化共幾頁
//	private long totalCount = 0;   				// 初始化共幾筆
//	private int totalSize = 0;						
//	private int totalPv = 0;						
//	private int totalClk = 0;						
//	private float totalClkRate = 0;
//	private float totalAvgCost = 0;
//	private float totalThousandsCost = 0;
//	private int totalCost = 0;
//	private int totalInvalidClk = 0;
//	private String jdbcEnvironment;
//	private String adType;
//	
//	public String adAdViewTableAjax() throws Exception{
//		int type = Integer.parseInt(searchType);
//		long allAdActionViews = 0;
//		for(EnumAdType adType:EnumAdType.values()){
//			if(adType.getType() == type){
//				allAdActionViews = pfpAdService.getPfpAdCount(super.getCustomer_info_id(), 
//																adGroupSeq, 
//																keyword);
//				adAdViewVOList = pfpAdService.getAdAdView(super.getCustomer_info_id(), 
//																adGroupSeq, 
//																keyword, 
//																adType, 
//																DateValueUtil.getInstance().stringToDate(startDate), 
//																DateValueUtil.getInstance().stringToDate(endDate),
//																pageNo, 
//																pageSize);
//			}
//		}
//
//		if(allAdActionViews > 0) {
//			totalCount = allAdActionViews;
//			pageCount = (int) Math.ceil(((float)totalCount / pageSize));
//
//			if(adAdViewVOList != null && adAdViewVOList.size() > 0){
//				totalSize = adAdViewVOList.size();		
//				for(PfpAdAdViewVO vo:adAdViewVOList){
//					if(StringUtils.equals("N", vo.getHtml5Tag())){
//						Map<String,String> imgmap = new HashMap<String,String>();
//						imgmap = getImgSize(vo.getOriginalImg());
//						vo.setImgWidth(imgmap.get("imgWidth"));
//						vo.setImgHeight(imgmap.get("imgHeight"));
//					}
//					
//					if("IMG".equals(vo.getAdStyle())){
//						String showUrl = vo.getRealUrl();
//						showUrl = showUrl.replaceAll("http://", "");
//						showUrl = showUrl.replaceAll("https://", "");
//		            	if(showUrl.lastIndexOf(".com/") != -1){
//		            		showUrl = showUrl.substring(0, showUrl.lastIndexOf(".com/") + 4);
//		            	}
//		            	if(showUrl.lastIndexOf(".tw/") != -1){
//		            		showUrl = showUrl.substring(0, showUrl.lastIndexOf(".tw/") + 3);
//		            	}
//		            	vo.setShowUrl(showUrl);
//					}
//					
//					totalPv += vo.getAdPv();
//					totalClk += vo.getAdClk();		
//					totalCost += vo.getAdClkPrice();
//					totalInvalidClk += vo.getInvalidClk();
//					if(totalCost > 0 && ((float)totalPv / 1000) > 0){
//						this.totalThousandsCost += (float)totalCost / ((float)totalPv / 1000);
//					}
//				}
//				
//				if(totalClk > 0 || totalPv > 0){
//					totalClkRate = (float)totalClk / (float)totalPv*100;
//				}
//				
//				if(totalCost > 0 || totalClk > 0){
//					totalAvgCost = (float)totalCost / (float)totalClk;	
//				}
//			}
//		}
//		// 查詢日期寫進cookie
//		this.setChooseDate(startDate, endDate);
//		
//		return SUCCESS;
//	}
//	
//	public Map<String,String> getImgSize(String originalImg) throws Exception {
//		Map<String,String> imgmap = new HashMap<String,String>();
//		File picture = null;
//		String path = (originalImg.indexOf("D:/") >= 0) ? originalImg : "/home/webuser/akb/pfp/" +  originalImg.replace("\\", "/");
//		picture = new File(path);
//		if(picture != null){
//			Map<String,String> imgInfo = CommonUtils.getInstance().getImgInfo(picture);
//			imgmap.put("imgWidth", imgInfo.get("imgWidth"));
//			imgmap.put("imgHeight", imgInfo.get("imgHeight"));
//		}
//		return imgmap;
//	}
//
//	
//	/**
//	 * 影音廣告明細
//	 * */
//	public String adAdVideoViewTableAjax() throws Exception{
//		PfpAdAdViewConditionVO pfpAdAdViewConditionVO = new PfpAdAdViewConditionVO();
//		pfpAdAdViewConditionVO.setCustomerInfoId(super.getCustomer_info_id());
//		pfpAdAdViewConditionVO.setAdGroupSeq(adGroupSeq);
//		pfpAdAdViewConditionVO.setKeyword(keyword);
//		pfpAdAdViewConditionVO.setAdType(adType);
//		pfpAdAdViewConditionVO.setStartDate(startDate);
//		pfpAdAdViewConditionVO.setEndDate(endDate);
//		pfpAdAdViewConditionVO.setPage(pageNo);
//		pfpAdAdViewConditionVO.setPageSize(pageSize);
//		int limit = (pageNo - 1) * pageSize;
//		int max = pageNo * pageSize;
//		pfpAdAdViewConditionVO.setLimit(limit);
//		pfpAdAdViewConditionVO.setMax(max);
//		
//		this.adReportVO = pfpAdService.getAdAdVideoDetailViewCount(pfpAdAdViewConditionVO);
//		this.adReportVOList = pfpAdService.getAdAdVideoDetailView(pfpAdAdViewConditionVO);
//		
//		totalSize = adReportVOList!= null ? adReportVOList.size() : 0;
//		pageCount = (int) Math.ceil(((double)totalSize / (double)pageSize));
//		
//		
//		
//		/**多執行緒*/
////		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(500);
////		ThreadServiceBean threadServiceBean = new ThreadServiceBean();
////		threadServiceBean.setPfpAdService(pfpAdService);
////		JSONObject conditionJson = JSONObject.fromObject(pfpAdAdViewConditionVO);
//		
////		//第一條執行緒查詢總數
////		boolean adViewVideoCountResultFlag = true;
////		Future<String> pfpThreadProcessAdViewVideoCountResult = null;
////		PfpThreadProcess pfpThreadProcessAdViewVideoCount = new PfpThreadProcess(conditionJson,EnumAdThreadType.AD_VIEW_VIDEO_COUNT,threadServiceBean);
////		pfpThreadProcessAdViewVideoCountResult = executor.submit(pfpThreadProcessAdViewVideoCount);
////		
////		//第二條執行緒查詢總數
////		boolean adViewVideoDetailResultFlag = true;
////		Future<String> pfpThreadProcessAdViewVideoDetailResult = null;
////		PfpThreadProcess pfpThreadProcessAdViewVideoDetail = new PfpThreadProcess(conditionJson,EnumAdThreadType.AD_VIEW_VIDEO_DETAIL,threadServiceBean);
////		pfpThreadProcessAdViewVideoDetailResult = executor.submit(pfpThreadProcessAdViewVideoDetail);
////		
////		
////		//1.第一條執行緒查詢總數結果
////		while (adViewVideoCountResultFlag) {
////			if (pfpThreadProcessAdViewVideoCountResult.isDone()) {
////				String result = pfpThreadProcessAdViewVideoCountResult.get();
////				this.pfpAdAdVideoViewSumVO = (PfpAdAdVideoViewSumVO) JSONObject.toBean(JSONObject.fromObject(result), PfpAdAdVideoViewSumVO.class);
////				totalSize = pfpAdAdVideoViewSumVO.getTotalSize();
////				pageCount = (int) Math.ceil(((double)totalSize / (double)pageSize));
////				adViewVideoCountResultFlag = false;
////			}
////		}
////		
////		//2.第二條執行緒查詢明細結果
////		this.pfpAdAdVideoViewVOList = new ArrayList<>();
////		while (adViewVideoDetailResultFlag) {
////			if (pfpThreadProcessAdViewVideoDetailResult.isDone()) {
////				String result = pfpThreadProcessAdViewVideoDetailResult.get();
////				org.json.JSONArray jsonArray = new org.json.JSONArray(result);
////				for (int i = 0; i < jsonArray.length(); i++) {
////					org.json.JSONObject json =  (org.json.JSONObject) jsonArray.get(i);
////					PfpAdAdVideoViewVO pfpAdAdVideoViewVO = (PfpAdAdVideoViewVO) JSONObject.toBean(JSONObject.fromObject(json.toString()), PfpAdAdVideoViewVO.class);
////					if(i==0){
////						previewUrl = pfpAdAdVideoViewVO.getVideoUrl();
////					}
////					pfpAdAdVideoViewVOList.add(pfpAdAdVideoViewVO);
////				}
////				adViewVideoDetailResultFlag = false;
////			}
////		}
////		
////		//3.執行緒全部執行完畢
////		if(!adViewVideoCountResultFlag && !adViewVideoDetailResultFlag){
////			executor.shutdown();
////		}
//		
//		// 查詢日期寫進cookie
//		this.setChooseDate(startDate, endDate);
//		return SUCCESS;
//	}
//	
//	
//	public void setPfpAdService(IPfpAdService pfpAdService) {
//		this.pfpAdService = pfpAdService;
//	}
//
//	public void setAdGroupSeq(String adGroupSeq) {
//		this.adGroupSeq = adGroupSeq;
//	}
//
//	public void setKeyword(String keyword) {
//		this.keyword = keyword;
//	}
//
//	public void setSearchType(String searchType) {
//		this.searchType = searchType;
//	}
//
//	public void setStartDate(String startDate) {
//		this.startDate = startDate;
//	}
//
//	public void setEndDate(String endDate) {
//		this.endDate = endDate;
//	}
//	
//	public List<PfpAdAdViewVO> getAdAdViewVO() {
//		return adAdViewVOList;
//	}
//
//	public int getPageNo() {
//		return pageNo;
//	}
//
//	public void setPageNo(int pageNo) {
//		this.pageNo = pageNo;
//	}
//
//	public int getPageSize() {
//		return pageSize;
//	}
//
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}
//
//	public int getPageCount() {
//		return pageCount;
//	}
//
//	public long getTotalCount() {
//		return totalCount;
//	}
//
//	public int getTotalSize() {
//		return totalSize;
//	}
//
//	public int getTotalPv() {
//		return totalPv;
//	}
//
//	public int getTotalClk() {
//		return totalClk;
//	}
//
//	public float getTotalClkRate() {
//		return totalClkRate;
//	}
//
//	public float getTotalAvgCost() {
//		return totalAvgCost;
//	}
//
//	public int getTotalCost() {
//		return totalCost;
//	}
//
//	public int getTotalInvalidClk() {
//		return totalInvalidClk;
//	}
//
//	public String getAdType() {
//		return adType;
//	}
//
//	public void setAdType(String adType) {
//		this.adType = adType;
//	}
//
//	public AdReportVO getAdReportVO() {
//		return adReportVO;
//	}
//
//	public void setAdReportVO(AdReportVO adReportVO) {
//		this.adReportVO = adReportVO;
//	}
//
//	public String getPreviewUrl() {
//		return previewUrl;
//	}
//
//	public void setPreviewUrl(String previewUrl) {
//		this.previewUrl = previewUrl;
//	}
//
//	public String getJdbcEnvironment() {
//		return jdbcEnvironment;
//	}
//
//	public void setJdbcEnvironment(String jdbcEnvironment) {
//		this.jdbcEnvironment = jdbcEnvironment;
//	}
//
//	public float getTotalThousandsCost() {
//		return totalThousandsCost;
//	}
//
//	public List<PfpAdAdViewVO> getAdAdViewVOList() {
//		return adAdViewVOList;
//	}
//
//	public void setAdAdViewVOList(List<PfpAdAdViewVO> adAdViewVOList) {
//		this.adAdViewVOList = adAdViewVOList;
//	}
//
//	public List<AdReportVO> getAdReportVOList() {
//		return adReportVOList;
//	}
//
//	public void setAdReportVOList(List<AdReportVO> adReportVOList) {
//		this.adReportVOList = adReportVOList;
//	}
}
