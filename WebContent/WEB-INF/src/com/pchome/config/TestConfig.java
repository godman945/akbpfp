package com.pchome.config;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pchome.akbpfp.db.service.catalog.prod.PfpCatalogSetupService;




public class TestConfig {
	
	static String  contentTitle = "WebContent/WEB-INF/src/";
//	static String  prop = "　";
	public static String[] path = {
			contentTitle + "config/spring/local/spring-prop.xml",
			contentTitle + "config/spring/spring-action.xml",
			contentTitle + "config/spring/spring-api.xml",
			contentTitle + "config/spring/spring-api-action.xml",
			contentTitle + "config/spring/spring-factory.xml",
			contentTitle + "config/spring/spring-dao.xml",
			contentTitle + "config/spring/spring-service.xml",
			contentTitle + "config/spring/spring-hibernate.xml",
			contentTitle + "config/spring/spring-datasource.xml",
			contentTitle + "config/spring/spring-rmi-client.xml",
			contentTitle + "config/spring/spring-util.xml",
			contentTitle + "config/spring/spring-mail-config.xml",
			contentTitle + "config/log4j/log4j.xml",
			
			
//			contentTitle + "config/spring/spring-factory.xml",
//			contentTitle + "config/spring/spring-log4j.xml",
//			contentTitle + "config/spring/local/spring-prop.xml",
//			contentTitle + "config/spring/spring-service.xml",
			
			
//		contentTitle+"config/spring/spring-action.xml",
//		contentTitle+"config/spring/spring-service.xml",
//		contentTitle+"config/spring/spring-dao.xml",
//		contentTitle+"config/spring/spring-datasource.xml",
//		contentTitle+"config/spring/spring-hibernate.xml",
//		contentTitle+"config/spring/spring-log4j.xml",
//		contentTitle+"config/spring/local/spring-prop.xml",
//		contentTitle+"config/spring/spring-api.xml",
//		contentTitle+"config/spring/spring-mail-config.xml",
//		contentTitle+"config/spring/spring-util.xml",
//		contentTitle+"config/spring/spring-factory.xml",
//		contentTitle+"config/spring/spring-rmi-client.xml"
    };
	
	
	public static void main(String[] args) throws Exception{
		  
		
		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);
//		PfpCatalogLogoService a = (PfpCatalogLogoService) context.getBean("PfpCatalogLogoService");
//		PfpCatalogLogo pfpCatalogLogo = a.findCatalogLogoByCustomerInfoId("AC2013071700005");
//		
//		System.out.println(pfpCatalogLogo.getCatalogLogoUrl());
//		Set<PfpCatalogLogoDetail> set = pfpCatalogLogo.getPfpCatalogLogoDetails();
//		for (PfpCatalogLogoDetail pfpCatalogLogoDetail : set) {
//			System.out.println(pfpCatalogLogoDetail.getCatalogLogoRgb());
//		}
//		PfpAdService b = (PfpAdService) context.getBean("PfpAdService");
//		PfpAd h = b.get("ad_201804230004");
		
		PfpCatalogSetupService a = (PfpCatalogSetupService) context.getBean("PfpCatalogSetupService");
		System.out.println(a == null);
		System.out.println(a.loadAllSize());
		
		
//		System.out.println(a == null);
//		System.out.println(a.getProdGroupListDataByServer("PCG20180919000000001", 10));
		
		
//		AsidRateUtile AsidRateUtile = (AsidRateUtile) context.getBean("AsidRateUtile");
		
//		a.getProdGroupListData();
//		log.info(">>>>>PROD DATA API:"+"http://showstg.pchome.com.tw/pfp/prodGroupListAPI.html?groupId="+adProdgroupId+"&prodNum=10");
//		String prodData = com.pchome.soft.depot.utils.HttpUtil.getInstance().getResult("http://showstg.pchome.com.tw/pfp/prodGroupListAPI.html?groupId="+adProdgroupId+"&prodNum=10", "UTF-8");
//		log.info(">>>>>>DATA:"+prodData);
		
		
//		System.out.println(pfpAdGroup.getPfpAdKeywords() == null);
//		
		
//		System.out.println(keywordsSet);
		
//		for (PfpAdKeyword pfpAdKeyword : keywordsSet) {
//			System.out.println(pfpAdKeyword.getAdKeyword());
//		}
		
//		List<String> keyWordList = new ArrayList<>();
//		keyWordList.add("液晶電視");
//		String[] stockArr = new String[keyWordList.size()];
//		stockArr = keyWordList.toArray(stockArr);
//
//		for (String string : stockArr) {
//			System.out.println("比對用:"+string);
//		}
//		
//		
//		String redisKey = "pfpcart_AC2013071700001";
//		String redisData = redisAPI.getRedisData(redisKey);
//		JSONObject redisJson = new JSONObject(redisData);
//		JSONArray  redisAdArrayJson = (JSONArray) redisJson.get("products");
//		for (int i = 0; i < redisAdArrayJson.length(); i++) {
//			JSONObject addAdJson = (JSONObject) redisAdArrayJson.get(i);
//			JSONArray radisKeywords = (JSONArray) addAdJson.get("suggest");
//			for (int j = 0; j < radisKeywords.length(); j++) {
//				boolean isExist = false;
//				for (String string : stockArr) {
//					System.out.println(string+":"+radisKeywords.get(j)+":"+string.equals(radisKeywords.get(j)));
//					if(string.equals(radisKeywords.get(j).toString())){
//						isExist = true;
//						break;
//					}
//				}
//				if(isExist){
//					System.out.println("新增過:"+radisKeywords.get(j));
//				}
//			}
			
			
			
//			String[] a = (String[]) addAdJson.get("suggest");
//			System.out.println(a);
			
//			String[] stringArray = addAdJson.getString("suggest").split(",");
//			for (String string : stringArray) {
//				System.out.println(string);
//			}
//			String[] stringArray = addAdJson.getString("suggest");
//			String[] stringArray = Arrays.copyOf(addAdJson.get("suggest"), addAdJson.get("suggest"), String[].class);
			
//			System.out.println(addAdJson.get("suggest"));
			
//			String a = (addAdJson.get("suggest")).toString();
			
//			String[] keywords = (String[]) (addAdJson.get("suggest"));
//			for (String keyword : keywords) {
//				System.out.println(keyword);
//			}
//		}
		
		
		
		
		
		
//		AsidRateUtile AsidRateUtile = (AsidRateUtile) context.getBean("AsidRateUtile");
//		System.out.println(AsidRateUtile.getSyspriceRates(Float.valueOf("100")));
//		System.out.println(AsidRateUtile.getSyspriceRates(Float.valueOf("0.3")));
		
		
//		BillVOList billVOList= pfpTransDetailService.findPfpTransDetail("AC2013071700001", "2018-01-02", "2018-01-02");
//		System.out.println(billVOList.getTotalAdSpentMoney());
//		PfpAdService pfpAdService = (PfpAdService) context.getBean("PfpAdService");
//		
//		String adGroupSeq = "ag_201709070001";
//		String adType = "0";
//		String startDate = "2017-05-27";
//		String endDate = "2017-05-28";
//		String keyword = "";
//		int pageNo = 1; 
//		int pageSize = 10;
//		
//		PfpAdAdViewConditionVO pfpAdAdViewConditionVO = new PfpAdAdViewConditionVO();
////		pfpAdAdViewConditionVO.setCustomerInfoId(super.getCustomer_info_id());
//		pfpAdAdViewConditionVO.setAdGroupSeq(adGroupSeq);
//		pfpAdAdViewConditionVO.setKeyword(keyword);
//		pfpAdAdViewConditionVO.setAdType(adType);
//		pfpAdAdViewConditionVO.setStartDate(startDate);
//		pfpAdAdViewConditionVO.setEndDate(endDate);
//		pfpAdAdViewConditionVO.setPage(pageNo);
//		pfpAdAdViewConditionVO.setPageSize(pageSize);
//		int limit = (pageNo - 1) * pageSize;
//		int max = pageSize;
//		pfpAdAdViewConditionVO.setLimit(limit);
//		pfpAdAdViewConditionVO.setMax(max);
//		
//		
//		List<PfpAdAdVideoViewVO> pfpAdAdVideoViewVOList  = pfpAdService.getAdAdVideoDetailView(pfpAdAdViewConditionVO);
//		for (PfpAdAdVideoViewVO pfpAdAdVideoViewVO : pfpAdAdVideoViewVOList) {
//			System.out.println(pfpAdAdVideoViewVO.getAdSeq());
//			System.out.println(pfpAdAdVideoViewVO.getMp4Path());
//			System.out.println("-----------");
//		}
//		System.out.println("count:"+pfpAdService.getAdAdVideoDetailViewCount(pfpAdAdViewConditionVO));
		
		
		
//		PfpAdVideoSourceService service = (PfpAdVideoSourceService) context.getBean("PfpAdVideoSourceService");
//		System.out.println(service.getPfpAdBySeq(null));
	}
	
}

