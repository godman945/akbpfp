package com.pchome.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pchome.akbpfp.db.service.recognize.AdmRecognizeRecordService;
import com.pchome.enumerate.recognize.EnumOrderType;


public class TestConfig {
	
	static String  contentTitle = "WebContent/WEB-INF/src/";


	public static String[] path = {
		contentTitle+"config/spring/spring-action.xml",
		contentTitle+"config/spring/spring-dao.xml",
		contentTitle+"config/spring/spring-datasource.xml",
		contentTitle+"config/spring/spring-hibernate.xml",
		contentTitle+"config/spring/spring-log4j.xml",
		contentTitle+"config/spring/spring-prop.xml",
		contentTitle+"config/spring/spring-api.xml",
		contentTitle+"config/spring/spring-service.xml",
		contentTitle+"config/spring/spring-util.xml",
		contentTitle+"config/spring/spring-factory.xml",
		contentTitle+"config/spring/spring-rmi-client.xml"
    };
	
	
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);
		
		AdmRecognizeRecordService service = (AdmRecognizeRecordService) context.getBean("AdmRecognizeRecordService");
		service.createRecognizeRecord("AC2013071700001", "00000001", EnumOrderType.GIFT, 50000);


	}
	
}