package com.pchome.config;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pchome.akbpfp.db.service.ad.PfpAdService;
import com.pchome.akbpfp.db.vo.ad.PfpAdAdVideoViewVO;
import com.pchome.akbpfp.db.vo.ad.PfpAdAdViewConditionVO;


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
		PfpAdService pfpAdService = (PfpAdService) context.getBean("PfpAdService");
		
		String adGroupSeq = "ag_201709070001";
		String adType = "0";
		String startDate = "2017-05-27";
		String endDate = "2017-05-28";
		String keyword = "";
		int pageNo = 1; 
		int pageSize = 10;
		
		PfpAdAdViewConditionVO pfpAdAdViewConditionVO = new PfpAdAdViewConditionVO();
//		pfpAdAdViewConditionVO.setCustomerInfoId(super.getCustomer_info_id());
		pfpAdAdViewConditionVO.setAdGroupSeq(adGroupSeq);
		pfpAdAdViewConditionVO.setKeyword(keyword);
		pfpAdAdViewConditionVO.setAdType(adType);
		pfpAdAdViewConditionVO.setStartDate(startDate);
		pfpAdAdViewConditionVO.setEndDate(endDate);
		pfpAdAdViewConditionVO.setPage(pageNo);
		pfpAdAdViewConditionVO.setPageSize(pageSize);
		int limit = (pageNo - 1) * pageSize;
		int max = pageSize;
		pfpAdAdViewConditionVO.setLimit(limit);
		pfpAdAdViewConditionVO.setMax(max);
		
		
		List<PfpAdAdVideoViewVO> pfpAdAdVideoViewVOList  = pfpAdService.getAdAdVideoDetailView(pfpAdAdViewConditionVO);
		for (PfpAdAdVideoViewVO pfpAdAdVideoViewVO : pfpAdAdVideoViewVOList) {
			System.out.println(pfpAdAdVideoViewVO.getAdSeq());
			System.out.println(pfpAdAdVideoViewVO.getMp4Path());
			System.out.println("-----------");
		}
		System.out.println("count:"+pfpAdService.getAdAdVideoDetailViewCount(pfpAdAdViewConditionVO));
		
		
		
//		PfpAdVideoSourceService service = (PfpAdVideoSourceService) context.getBean("PfpAdVideoSourceService");
//		System.out.println(service.getPfpAdBySeq(null));
	}
	
}

