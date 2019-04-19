package com.pchome.akbpfp.struts2.action.summary;

import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.struts2.BaseSSLAction;

public class ThanksAction extends BaseSSLAction{		

	private IPfpCustomerInfoService pfpCustomerInfoService;
	
	/**
	 * 感謝頁面
	 * 1. html 有埋 google 和 yahoo JS code
	 */
	public String thanksAction() throws Exception{
		
		return SUCCESS;
	}
	
//	/**
//	 * 返回關鍵字帳戶頁面
//	 * 1. 帳戶開通 --> redirect.html --> summary.html
//	 * 2. 未開通 --> index.html
//	 */
//	public String backSummaryAction(){
//		
//		String result = "success";
//		
//		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.checkPfpCustomerInfoStatus(super.getId_pchome());
//		
//		if(pfpCustomerInfo == null){	
//			result = "index";
//		}
//		
//		log.info(" result: "+result);
//		
//		return result;
//	}

	public void setPfpCustomerInfoService(
			IPfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}
	
	
}
