package com.pchome.akbpfp.db.service.sysprice;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pchome.akbpfp.db.dao.sysprice.IPfpAdSyspriceDAO;
import com.pchome.akbpfp.db.dao.sysprice.PfpAdSyspriceDAO;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdSysprice;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.config.TestConfig;

public class PfpAdSyspriceService extends BaseService <PfpAdSysprice,String> implements IPfpAdSyspriceService{
	
	private String definePrice;
	
	public PfpAdSysprice getAdSysprice(String adPoolSeq) throws Exception{
		
		PfpAdSysprice adSysprice = ((PfpAdSyspriceDAO) dao).getAdSysprice(adPoolSeq);
		if(adSysprice == null){
			adSysprice = new PfpAdSysprice();
			adSysprice.setAdPoolSeq(adPoolSeq);
			adSysprice.setSysprice(Float.parseFloat(definePrice));		// 廣告預設價錢
			adSysprice.setAmount(0);
			super.saveOrUpdate(adSysprice);			
		}
		return adSysprice;
	}

	public void setDefinePrice(String definePrice) {
		this.definePrice = definePrice;
	}

	
	

	


	@Override
	public Integer getAdChannelPriceRangeCount() throws Exception {
        
		List<PfpAdGroup> o = ((IPfpAdSyspriceDAO) dao).getAdChannelPriceRange();
		
		int range = 0;
		
		if(o.isEmpty()){
			range = 0;
		}else{
			range = o.size();
		}
		
		return range;
	}
	
	public static void main(String arg[]) throws Exception{

		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);

		PfpAdSyspriceService pfpAdSyspriceService = (PfpAdSyspriceService) context.getBean("PfpAdSyspriceService");
		
		int l=pfpAdSyspriceService.getAdChannelPriceRangeCount();
		
		System.out.println(l);
		
	
		
	}
	
}
