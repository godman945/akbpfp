package com.pchome.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pchome.akbpfd.db.vo.user.PfdUserAdAccountRefVO;
import com.pchome.akbpfp.db.pojo.PfdUserAdAccountRef;
import com.pchome.akbpfp.db.service.pfd.user.PfdUserAdAccountRefService;


public class TestConfig {
	
	static String  contentTitle = "WebContent/WEB-INF/src/";


	public static String[] path = {
		contentTitle+"config/spring/spring-action.xml",
		contentTitle+"config/spring/spring-dao.xml",
		contentTitle+"config/spring/spring-datasource.xml",
		contentTitle+"config/spring/spring-hibernate.xml",
		contentTitle+"config/spring/spring-log4j.xml",
		contentTitle+"config/spring/stg/spring-prop.xml",
		contentTitle+"config/spring/spring-api.xml",
		contentTitle+"config/spring/spring-service.xml",
		contentTitle+"config/spring/spring-util.xml",
		contentTitle+"config/spring/spring-factory.xml",
		contentTitle+"config/spring/spring-rmi-client.xml"
    };
	
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
//		2064
//		US2018010400001
//		PFDU20161111002
//		PFDC20141024003
		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);
//		AdmRecognizeRecordService service = (AdmRecognizeRecordService) context.getBean("AdmRecognizeRecordService");
//		service.createRecognizeRecord("AC2013071700001", "00000001", EnumOrderType.GIFT, 50000);
		
		PfdUserAdAccountRefService service = (PfdUserAdAccountRefService) context.getBean("PfdUserAdAccountRefService");
//		
//		PfdUserAdAccountRefVO pfdUserAdAccountRefVO = new PfdUserAdAccountRefVO();
//		pfdUserAdAccountRefVO.setRefId(2064);
//		pfdUserAdAccountRefVO.setPfdCustomerInfoId("PFDC20141024003");
//		pfdUserAdAccountRefVO.setPfdUserId("PFDU20161111002");
//		pfdUserAdAccountRefVO.setPfpCustomerInfoId("PFDC20141024003");
//		pfdUserAdAccountRefVO.setPfpUserId("SSSSSSSSSSSSSSSSs");
//		pfdUserAdAccountRefVO.setPfpPayType("1");
//		pfdUserAdAccountRefVO.setProof("");
//		
//		
//		PfdUserAdAccountRef PfdUserAdAccountRef = new PfdUserAdAccountRef();
//		PfdUserAdAccountRef.setRefId(2064);
//		PfdUserAdAccountRef.setp
		
		
//		service.savePfdUserAdAccountRef(pfdUserAdAccountRefVO);


	}
	
}