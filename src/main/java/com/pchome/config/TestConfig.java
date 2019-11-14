package com.pchome.config;


import java.io.File;
import java.io.FileWriter;
import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.akbpfp.api.AsidRateUtile;
import com.pchome.akbpfp.db.pojo.PfpCatalogLogo;
import com.pchome.akbpfp.db.service.catalog.prod.PfpCatalogLogoService;


public class TestConfig {
	
	

	static String  contentTitle = "src/main/resources/";
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
			contentTitle + "config/spring/spring-log4j.xml",
			
			
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
		
//		ApplicationContext context = new FileSystemXmlApplicationContext(new TestConfig().path);
//		Logger log = LogManager.getRootLogger();
//		log.info("AAAAAAAAA");
//		PfpCatalogLogoService a = (PfpCatalogLogoService) context.getBean("PfpCatalogLogoService");
//		log.info("size:"+a.loadAll().size());
//		
//		
//		
//		PfpCatalogLogoService b = (PfpCatalogLogoService) context.getBean("PfpCatalogLogoService");
//		List<PfpCatalogLogo> pfpCatalogLogoList = b.findCatalogLogoByCustomerInfoId("AC2013071700005");
//		PfpCatalogLogo d = pfpCatalogLogoList.get(0);
//		log.info(d.getCatalogLogoSeq());
//		d.setLogoSendVerifyTime(new Date());
//		b.saveOrUpdate(d);
//		String mon = " 100000000000000000000000100000000000000000000000100000000000000000000000100000000000000000000000100000000000000000000000100000000000000000000000100000000000000000000000";
////		
//		System.out.println(mon.substring(144));
		
		FileWriter fileWriter = new FileWriter("D:\\home\\webuser\\akb\\pfp\\img\\productFileUpload\\csv\\user\\AC2013071700001\\PC201911140000000003\\alex.txt",true);
		fileWriter.write("[CCCCC] \r\n");
		fileWriter.flush();
		fileWriter.close();
		
		
//		File file = new File("D:\\test_photo\\alex_prod\\banner\\image_1.jpg55.73028786 - 複製.gif");
//		System.out.println(file.getAbsolutePath());
//		System.out.println(FilenameUtils.getExtension(file.getAbsolutePath()));
//		System.out.println(FilenameUtils.getName(file.getAbsolutePath()));
//		System.out.println(FilenameUtils.getBaseName(file.getAbsolutePath()));
//		System.out.println(FilenameUtils.getPath(file.getAbsolutePath()));
//		System.out.println(FilenameUtils.getFullPathNoEndSeparator(file.getAbsolutePath()));
		
//		//設定播放時間初始化
//				String mon = " 111111111111111111111111";
//				String tue = " 111111111111111111111111";
//				String wed = " 111111111111111111111111";
//				String thu = " 111111111111111111111111";
//				String fri = " 111111111111111111111111";
//				String sat = " 111111111111111111111111";
//				String sun = " 111111111111111111111111";
//		
//				LinkedHashMap timeCodeMap = new LinkedHashMap<String,String>();
//				Object[][] object = {mon.split(""),tue.split(""),wed.split(""),thu.split(""),fri.split(""),sat.split(""),sun.split("")};
//				for(int i=0;i<7;i++){
//					for(int j=0;j<24;j++){
//						String key = String.format("%01d",i+1) + String.format("%02d",j);
//						String code = (String) object[i][j+1];
//							
//							System.out.println("i:"+i);
//							System.out.println("j:"+j);
//							System.out.println("key:"+key);
//							System.out.println("code:"+code);
//							System.out.println(StringUtils.equals(code, "1"));
//							System.out.println("---------");
//							
//							if(StringUtils.equals(code, "1")){
//								timeCodeMap.put(key, "checked");
//							} else {
//								timeCodeMap.put(key, " ");
//							}
//						}
//					}
//		
		
		
		
		
		
		
//
//		
//		JSONObject a = new JSONObject();
//		a.put("alex", "");
////		a.put("alex", new HashMap<>());
//		if(a.get("alex") instanceof JSONObject){
//			System.out.println("FFF");
//		}else{
//			System.out.println("CCC");
//		}
		
		
		
//		System.out.println("AAAA");
//		System.out.println(a.get("alex"));
		
//		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);
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
//		System.out.println("FFFFFFFF");
////		TemplateProductService a = (TemplateProductService) context.getBean("AdmTemplateProductService");
////		
////		List<String> f = new ArrayList<>();
////		f.add("x04");
////		f.add("x05");
////		
////		System.out.println(a.getTemplateProductByXType(f).size());
//		
//        File in = new File("d:\\142465265.gif");
//        File out2 = new File("d:\\scale555.gif");
////        GifImage srcImage = GifDecoder.decode(in);
////        GifImage resizeImage = GifTransformer.resize(srcImage, 300, 300, true);
////        GifEncoder.encode(resizeImage, out);
//
//		
//		
//		
//        BufferedImage img = ImageIO.read(in); // load image
        
        
        
//		System.out.println(a == null);
//		System.out.println(a.loadAllSize());
		
		
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

