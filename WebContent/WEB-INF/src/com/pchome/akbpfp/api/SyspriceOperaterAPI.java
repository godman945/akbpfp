package com.pchome.akbpfp.api;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pchome.akbpfp.db.pojo.PfpAdSysprice;
import com.pchome.akbpfp.db.pojo.PfpKeywordSysprice;
import com.pchome.akbpfp.db.service.sysprice.PfpAdSyspriceService;
import com.pchome.akbpfp.db.service.sysprice.PfpKeywordSyspriceService;
import com.pchome.akbpfp.db.service.sysprice.PfpSyspriceRateService;
import com.pchome.config.TestConfig;
import com.pchome.enumerate.sysprice.EnumVirtualPool;

public class SyspriceOperaterAPI {

	protected Log log = LogFactory.getLog(this.getClass());

	private PfpAdSyspriceService adSyspriceService;
	private PfpKeywordSyspriceService keywordSyspriceService;
	private PfpSyspriceRateService syspriceRateService;
	private AsidRateUtile asidRateUtile;
	private String limiAmount;//建議價提高價格門檻
	private String sysPriceAdPoolSeq;
	private String definePrice;

	public float getAdSuggestPrice(String adPoolSeq) throws Exception{

		/**
		 * @author ray
		 * 1. amount >= 10, 價格+1 
		 * 取得廣告建議價格
		 */

		log.info("------get Ad SuggestPrice----- ");

		log.info("adPoolSeq="+adPoolSeq);

		PfpAdSysprice adSysprice = adSyspriceService.getAdSysprice(adPoolSeq);

		int amount = adSysprice.getAmount();
		float suggestPrice = adSysprice.getSysprice();

		if(amount >= Integer.parseInt(limiAmount)){
			suggestPrice++;
		}

		log.info("suggestPrice="+suggestPrice);

		return suggestPrice;
	}

	public synchronized void addAdSysprice(String adPoolSeq, float userPrice) throws Exception{


		log.info("------set Ad SysPrice----- ");

		log.info("adPoolSeq="+adPoolSeq);

		log.info("userPrice="+userPrice);

		PfpAdSysprice adSysprice = adSyspriceService.getAdSysprice(adPoolSeq);

		//出價數量
		int amount = adSysprice.getAmount();
		//目前系統價
		float sysprice = adSysprice.getSysprice();
		
		int rangeCount=0;

		log.info(" amount = "+amount);
		
		log.info(" sysprice = "+sysprice);
		
		//檢查價格帶
		//group by 所有廣告的 channelPrice (user 出價)
		//過濾狀態正常的廣告
		//看有幾個價格帶
		rangeCount = adSyspriceService.getAdChannelPriceRangeCount();
		
		log.info(" rangeCount = "+rangeCount);
		
		if(((sysprice-2) - rangeCount) != 0 ){



			log.info(" (sysprice-2) - rangeCount !=0 ");
			log.info(" ad sysprice reset");

			sysprice=rangeCount+2;
			adSysprice.setSysprice(sysprice);	
			adSysprice.setAmount(1);	// 至少1筆資料

			log.info(" new sysprice ="+sysprice);

		}else{

            //系統價等於價格帶 數量加 1大於10會影響建議價
			log.info(" (sysprice-2) = rangeCount ");
			log.info(" ad amount add");
			log.info(" amount ="+amount);

			amount++;	
			adSysprice.setAmount(amount);	

			log.info(" new amount ="+amount);


		}

		adSyspriceService.saveOrUpdate(adSysprice);


	}

	public float getKeywordSuggesPrice(String keyword) throws Exception{

		/**
		 * 取得關鍵字建議價
		 * @author ray
		 * 1. amount >= 10, 價格+1 
		 * 
		 */

		PfpKeywordSysprice keywordSysprice = keywordSyspriceService.getKeywordSysprice(keyword);

		int amount = keywordSysprice.getAmount();
		float suggestPrice = keywordSysprice.getSysprice();

		if(amount >= Integer.parseInt(limiAmount)){
			suggestPrice++;
		}
		
//		log.info(" suggestPrice= "+suggestPrice);
		return suggestPrice;
	}

	public float getKeywordSuggesPrice(String keyword,String keywordType) throws Exception{

		/**
		 * 取得關鍵字建議價(區分比對模式)
		 * @author ray
		 * 1. amount >= 10, 價格+1 
		 * 
		 */

		PfpKeywordSysprice keywordSysprice = keywordSyspriceService.getKeywordSysprice(keyword,keywordType);

		int amount = keywordSysprice.getAmount();
		float suggestPrice = keywordSysprice.getSysprice();

		if(amount >= Integer.parseInt(limiAmount)){
			suggestPrice++;
		}
		
//		log.info(" suggestPrice= "+suggestPrice);
		return suggestPrice;
	}
	
	public synchronized void addKeywordSysprice(String keyword, float userPrice) throws Exception{
		/**
		 * 新增關鍵字系統價
		 * @author ray
		 * 1. userPrice > systemPrice, 價格+1, 數量 1
		 * 2. userPrice == systemPrice, 數量+1
		 * 3. userPrice < systemPrice, 沒有動作
		 * 
		 */

		PfpKeywordSysprice keywordSysprice = keywordSyspriceService.getKeywordSysprice(keyword);
		//出價數量
		int amount = keywordSysprice.getAmount();
		//目前系統價
		float sysprice = keywordSysprice.getSysprice();

		int rangeCount=0;


		//使用者出價大於系統價
		//if(userPrice > sysprice){

			//檢查價格帶
			//group by 這個字的 searchPrice (user 出價)
			//過濾奘態正常的廣告
			//小於目前系統價
			//看有幾個價格帶
			//呼叫之前價格帶已更新
			rangeCount=keywordSyspriceService.getKeywordSearchPriceRangeCount(keyword);


			//呼叫之前價格帶已更新
			//目前sysprice-2 因為 default 價個是3元
			//價格帶跟系統價不相同的話  sysprice 設為 價格帶+2
			//這樣不管價格帶是增加還是減少 sysprice 都可以即時反應
			log.info("--------------------------------------");
			log.info(" keyword ="+keyword);
			log.info(" sysprice ="+sysprice);
			log.info(" rangeCount ="+rangeCount);


			if(((sysprice-2) - rangeCount) != 0 ){



				log.info(" (sysprice-2) - rangeCount !=0 ");
				log.info(" keyword sysprice reset");


				sysprice=rangeCount+2;
				float defPrice = Float.parseFloat(definePrice);
				if(sysprice < defPrice ){
					sysprice = defPrice;
				}
				
				keywordSysprice.setSysprice(sysprice);	
				keywordSysprice.setAmount(1);	// 至少1筆資料

				log.info(" new sysprice ="+sysprice);

			}else{

                //系統價等於價格帶 數量加 1大於10會影響建議價
				log.info(" (sysprice-2) = rangeCount ");
				log.info(" keyword amount add");
				log.info(" keyword ="+keyword);
				log.info(" amount ="+amount);

				amount++;	
				keywordSysprice.setAmount(amount);	

				log.info(" new amount ="+amount);


			}

			
			

		keywordSyspriceService.saveOrUpdate(keywordSysprice);
	}

	/*
	public float getAdAsideRate(String adPoolSeq, float userPrice) throws Exception{
		String rate = AsidRateUtile.getInstance().getSyspriceRates(syspriceRateService, adPoolSeq, userPrice);
		return Float.parseFloat(rate);	
	}
	 */

	public float getAdAsideRate(float userPrice) throws Exception{
		//取得廣告播放率
		
		PfpAdSysprice adSysprice = adSyspriceService.getAdSysprice(sysPriceAdPoolSeq);
	
		//目前系統價
		float sysprice = adSysprice.getSysprice();
		
		String rate=null;
		if(userPrice>sysprice){
		    rate = asidRateUtile.getSyspriceRates(sysprice);
		}else{
			rate = asidRateUtile.getSyspriceRates(userPrice);
		}
		return Float.parseFloat(rate);
	}


	public void setAdSyspriceService(PfpAdSyspriceService adSyspriceService) {
		this.adSyspriceService = adSyspriceService;
	}

	public void setKeywordSyspriceService(
			PfpKeywordSyspriceService keywordSyspriceService) {
		this.keywordSyspriceService = keywordSyspriceService;
	}

	public void setSyspriceRateService(PfpSyspriceRateService syspriceRateService) {
		this.syspriceRateService = syspriceRateService;
	}

	public void setLimiAmount(String limiAmount) {
		this.limiAmount = limiAmount;
	}

	public void setAsidRateUtile(AsidRateUtile asidRateUtile) {
		this.asidRateUtile = asidRateUtile;
	}


	public static void main(String arg[]) throws Exception{

		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);

		SyspriceOperaterAPI syspriceOperaterAPI = (SyspriceOperaterAPI) context.getBean("SyspriceOperaterAPI");

		syspriceOperaterAPI.addKeywordSysprice("ipad", 100);


	}

	public void setSysPriceAdPoolSeq(String sysPriceAdPoolSeq) {
		this.sysPriceAdPoolSeq = sysPriceAdPoolSeq;
	}

	public void setDefinePrice(String definePrice) {
		this.definePrice = definePrice;
	}


}
