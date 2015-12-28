package com.pchome.akbpfp.db.service.sysprice;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pchome.akbpfp.db.dao.sysprice.PfpKeywordSyspriceDAO;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.pojo.PfpKeywordSysprice;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.config.TestConfig;

public class PfpKeywordSyspriceService extends BaseService<PfpKeywordSysprice,String> implements IPfpKeywordSyspriceService{

	private String definePrice;
	
	//關鍵字建議出價(不區分比對方式)
	public PfpKeywordSysprice getKeywordSysprice(String keyword) throws Exception{
		
		PfpKeywordSysprice keywordSysprice = ((PfpKeywordSyspriceDAO) dao).getKeywordSysprice(keyword);
		
		if(keywordSysprice == null){
			
			keywordSysprice = new PfpKeywordSysprice();
			keywordSysprice.setKeyword(keyword);
			keywordSysprice.setSysprice(Float.parseFloat(definePrice));	// 廣告預設價錢
			keywordSysprice.setAmount(0);
			
			super.saveOrUpdate(keywordSysprice);			
		}
		
		return keywordSysprice;
	}

	//關鍵字建議出價(區分比對方式)
	public PfpKeywordSysprice getKeywordSysprice(String keyword,String keywordType) throws Exception{
		
		//比對關鍵字系統價格是否有該資料存在，若無則建一筆資料
		PfpKeywordSysprice keywordSysprice = ((PfpKeywordSyspriceDAO) dao).getKeywordSysprice(keyword);
		
		if(keywordSysprice == null){
			
			keywordSysprice = new PfpKeywordSysprice();
			keywordSysprice.setKeyword(keyword);
			keywordSysprice.setSysprice(Float.parseFloat(definePrice));	// 廣告預設價錢
			keywordSysprice.setAmount(0);
			
			super.saveOrUpdate(keywordSysprice);			
		}
		
		//依比對方式取得建議出價
		PfpKeywordSysprice keywordByTypeSysprice = ((PfpKeywordSyspriceDAO) dao).getKeywordSysprice(keyword,keywordType);
		
		return keywordByTypeSysprice;
	}
	
	public void setDefinePrice(String definePrice) {
		this.definePrice = definePrice;
	}

	@Override
	public Integer getKeywordSearchPriceRangeCount(String keyword) throws Exception {
		// TODO Auto-generated method stub
		
		List<PfpAdKeyword> o=((PfpKeywordSyspriceDAO) dao).getKeywordSearchPriceRange(keyword);
		
		int range=0;
		
		if(o.isEmpty()){
			range=0;
		}else{
			range=o.size();
		}
		
		return range;
	}
	
	public static void main(String arg[]) throws Exception{

		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);

		PfpKeywordSyspriceService pfpKeywordSyspriceService = (PfpKeywordSyspriceService) context.getBean("PfpKeywordSyspriceService");
		
		int l=pfpKeywordSyspriceService.getKeywordSearchPriceRangeCount("htc");
		
		System.out.println(l);
		
	
		
	}
	
}
