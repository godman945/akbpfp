package com.pchome.config;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

//import com.pchome.akbpfp.api.RedisAPI;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.service.ad.PfpAdGroupService;




public class TestConfig {
	
	static String  contentTitle = "WebContent/WEB-INF/src/";
//	static String  prop = "　";
	public static String[] path = {
			
//			contentTitle + "config/spring/spring-action.xml",
//			contentTitle + "config/spring/spring-api.xml",
//			contentTitle + "config/spring/spring-dao.xml",
//			contentTitle + "config/spring/spring-datasource.xml",
//			contentTitle + "config/spring/spring-factory.xml",
//			contentTitle + "config/spring/spring-hibernate.xml",
//			contentTitle + "config/spring/spring-log4j.xml",
//			contentTitle + "config/spring/spring-service.xml",
//			contentTitle + "config/spring/local/local-spring-prop.xml",
			
			
//		contentTitle+"config/spring/spring-action.xml",
		contentTitle+"config/spring/spring-service.xml",
		contentTitle+"config/spring/spring-dao.xml",
		contentTitle+"config/spring/spring-datasource.xml",
		contentTitle+"config/spring/spring-hibernate.xml",
		contentTitle+"config/spring/spring-log4j.xml",
		contentTitle+"config/spring/local/spring-prop.xml",
		contentTitle+"config/spring/spring-api.xml",
		contentTitle+"config/spring/spring-util.xml",
		contentTitle+"config/spring/spring-factory.xml",
		contentTitle+"config/spring/spring-rmi-client.xml"
    };
	
	
	public static void main(String[] args) throws Exception{
		  
		
		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);
//		RedisAPI redisAPI = (RedisAPI) context.getBean("RedisAPI");
		
		
		
//		System.out.println(pfpAdGroup.getPfpAdKeywords() == null);
//		
		
//		System.out.println(keywordsSet);
		
//		for (PfpAdKeyword pfpAdKeyword : keywordsSet) {
//			System.out.println(pfpAdKeyword.getAdKeyword());
//		}
		
		List<String> keyWordList = new ArrayList<>();
		keyWordList.add("液晶電視");
		String[] stockArr = new String[keyWordList.size()];
		stockArr = keyWordList.toArray(stockArr);

		for (String string : stockArr) {
			System.out.println("比對用:"+string);
		}
		
		
		String redisKey = "pfpcart_AC2013071700001";
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

