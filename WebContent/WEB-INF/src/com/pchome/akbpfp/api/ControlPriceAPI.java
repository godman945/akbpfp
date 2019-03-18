package com.pchome.akbpfp.api;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.ad.IPfpAdActionService;

public class ControlPriceAPI {

	protected Log log = LogFactory.getLog(this.getClass());
	
	private IPfpAdActionService adActionService;
	
	private float totalControlPrice;
	private float totalMaxPrice;
	
	private float definePrice;
	
	
	public void countProcess(PfpCustomerInfo customerInfo) {
		// 重新計算帳戶下走期中的活動調控金額
		String customerInfoId = customerInfo.getCustomerInfoId();
		float remain = customerInfo.getRemain();
		// 餘額大於 3塊才計算
		if(remain > definePrice){
			
			List<PfpAdAction> adActions = adActionService.findBroadcastAdAction(customerInfoId);
			
			this.countControlPrice(adActions);
			
			log.info(" remain = "+remain);
			log.info(" totalMaxPrice = "+totalMaxPrice);
			log.info(" totalControlPrice = "+totalControlPrice);
			log.info("========================");
			
			// 總每日上限金額大於餘額
			if(remain < totalMaxPrice){
				
				// 依每日上限金額比率計算調控金額
				for(PfpAdAction adAction:adActions){

					// 計算後調控金額 = (原本每日上限金額 / 每日上限金額加總) * 帳戶餘額
					float controlPrice = (adAction.getAdActionMax() / totalMaxPrice) * remain;
					
					//log.info(" before controlPrice = "+adAction.getAdActionControlPrice());
					//log.info(" after controlPrice = "+controlPrice);
					
					
					adAction.setAdActionControlPrice(controlPrice);
					adAction.setAdActionUpdateTime(new Date());
					
					adActionService.saveOrUpdate(adAction);
					
				}
				
			}else if(remain > totalMaxPrice){
				
				// 設回每日最大上限金額
				for(PfpAdAction adAction:adActions){
					
					log.info(" controlPrice = "+adAction.getAdActionMax());
					
					adAction.setAdActionControlPrice(adAction.getAdActionMax());
					adAction.setAdActionUpdateTime(new Date());
					
					adActionService.saveOrUpdate(adAction);
				}
				
			}
			
			log.info("========================");
		}
	}
	
	private void countControlPrice(List<PfpAdAction> adActions) {
		
		totalControlPrice = 0;
		totalMaxPrice = 0;
		
		for(PfpAdAction adAction:adActions){
			//log.info(" adActionMax = "+adAction.getAdActionMax());
			//log.info(" ControlPrice = "+adAction.getAdActionControlPrice());
			
			totalControlPrice += adAction.getAdActionControlPrice();
			totalMaxPrice += adAction.getAdActionMax();
		}
		
		//log.info("========================");
	}

	public void setAdActionService(IPfpAdActionService adActionService) {
		this.adActionService = adActionService;
	}

	public void setDefinePrice(float definePrice) {
		this.definePrice = definePrice;
	}
	
	
	
	
}
