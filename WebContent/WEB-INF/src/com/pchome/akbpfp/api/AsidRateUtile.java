package com.pchome.akbpfp.api;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pchome.akbpfp.db.dao.order.PfpOrderDAO;
import com.pchome.akbpfp.db.pojo.PfpAdSysprice;
import com.pchome.akbpfp.db.service.ad.PfpAdRateService;
import com.pchome.akbpfp.db.service.sysprice.PfpAdSyspriceService;
import com.pchome.akbpfp.db.service.sysprice.PfpSyspriceRateService;
import com.pchome.config.TestConfig;
import com.pchome.enumerate.sysprice.EnumImpMapName;
import com.pchome.soft.util.DateValueUtil;


public class AsidRateUtile {
	
	protected Log log = LogFactory.getLog(this.getClass());
	

	private String searchDay;
	private Map<String,Map<String,String>> sysPriceRates;
	private float onePriceImp=0.0f;
	private double totalPrice=0.0;
	private long totalImp=0L;
	private long adNum=0L;
	
	private PfpSyspriceRateService pfpSyspriceRateService=null;
	private PfpAdRateService pfpAdRateService=null;
	private PfpAdSyspriceService pfpAdSyspriceService=null;
	private String sysPriceAdPoolSeq=null;
    
	public void setPfpAdRateService(PfpAdRateService pfpAdRateService) {
		this.pfpAdRateService = pfpAdRateService;
	}

	private String keepDate="";

	public void setPfpSyspriceRateService(
			PfpSyspriceRateService pfpSyspriceRateService) {
		this.pfpSyspriceRateService = pfpSyspriceRateService;
	}

	public AsidRateUtile(){}
	
	public void init() throws Exception{
		
		        
				log.info("init");
				
				String yesterday = DateValueUtil.getInstance().getDateValue(DateValueUtil.YESTERDAY, DateValueUtil.DBPATH);
				
				PfpAdSysprice adSysprice = pfpAdSyspriceService.getAdSysprice(sysPriceAdPoolSeq);
				
				//目前系統價
				float sysprice = adSysprice.getSysprice();
				
				
				if(StringUtils.isBlank(keepDate)){
					keepDate=yesterday;
				}
				
				if(!keepDate.equals(yesterday)){
					keepDate=yesterday;
				}
				
				log.info("rateDate="+keepDate);
				log.info("sysprice="+sysprice);
				
			
				
				
				try {
				
					//用系統價來當作價格區間
					for(float x=sysprice;x>0;x--){
						totalPrice+=x;
					}
					
					//totalPrice=價格帶價格總和
					//totalImp=總 imp
					List<Object> dateSysPricePvList=pfpSyspriceRateService.getSyspriceRate(keepDate);
					
					for(Object o:dateSysPricePvList){
						Object[] oa=(Object[])o;
						//totalPrice+=Double.valueOf(oa[0].toString());
						totalImp+=Long.valueOf(oa[1].toString());
				
					}
					
					log.info("totalPrice="+totalPrice);
					log.info("totalImp="+totalImp);
					
					//adNum = 總撥出廣告數
					adNum=pfpAdRateService.getDateAdCount(keepDate);
					
					log.info("adNum="+adNum);
					
					//一元 imp = 總 imp / 總撥出廣告數 X 價格帶價格總和
					
					if(totalImp==0){
						 onePriceImp=0;
					}else if(adNum==0){
						 onePriceImp=0;
					}else if(totalPrice==0){
						 onePriceImp=0;
					}else{
					
					     //onePriceImp=(float) (totalImp / (adNum*totalPrice));
					     onePriceImp=(float) (totalImp / (totalPrice));
					}
					
					log.info("onePriceImp="+onePriceImp);
				
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					log.info(e.toString());
					onePriceImp=0;
				}
				
				
		
	}
	
	public String getSyspriceRates(float userPrice) throws Exception{
		
		/**
		
		 * 2. 播放率 = ((一元imp x 出價) / 撥出總imp) x100
		 * 3. 只計算當天的播放率
		 * @return Map<pool, 播放率>
		 */
		
		String yesterday = DateValueUtil.getInstance().getDateValue(DateValueUtil.YESTERDAY, DateValueUtil.DBPATH);
		
		if(StringUtils.isBlank(keepDate)){
			keepDate=yesterday;
			init();
		}
		
		if(!keepDate.equals(yesterday)){
			keepDate=yesterday;
			init();
		}
				
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits( 2 ); 
		float rate=0.0f;
		try{
		
			//log.info(onePriceImp);
			//log.info(totalImp);
			
			if(onePriceImp==0){
				rate=0;
			}else{
				rate=((onePriceImp*userPrice)/totalImp)*100;
			}
		
		}catch(Exception e){
			
			log.info(e.toString());
			
			rate=0.0f;
		}
		
		String rateStr=String.valueOf(nf.format(rate));
			  
		if(StringUtils.isBlank(rateStr)){
			rateStr="0.0";
		}
		
	    return rateStr;
	}
	
	
	
	public static void main(String arg[]) throws Exception{

		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);

		AsidRateUtile asidRateUtile = (AsidRateUtile) context.getBean("AsidRateUtile");
		
		System.out.println(asidRateUtile.getSyspriceRates(20));

		
	}

	public void setPfpAdSyspriceService(PfpAdSyspriceService pfpAdSyspriceService) {
		this.pfpAdSyspriceService = pfpAdSyspriceService;
	}

	public void setSysPriceAdPoolSeq(String sysPriceAdPoolSeq) {
		this.sysPriceAdPoolSeq = sysPriceAdPoolSeq;
	}

}
