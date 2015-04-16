package com.pchome.akbpfp.api;

import com.pchome.akbpfp.db.pojo.PfpOrder;
import com.pchome.akbpfp.db.service.customerInfo.PfpCustomerInfoService;
import com.pchome.akbpfp.db.service.order.PfpOrderService;
import com.pchome.akbpfp.db.vo.account.AccountVO;
import com.pchome.enumerate.account.EnumAccountStatus;
import com.pchome.enumerate.billing.EnumBillingStatus;
import com.pchome.enumerate.billing.EnumRedirectStatus;

public class AccountStatusAPI {

	private PfpCustomerInfoService pfpCustomerInfoService;
	private PfpOrderService pfpOrderService;
	
	public String verifyAccountStatus(String memberId) throws Exception{
		
		// 預設帳號都是申請狀態
		String result = EnumRedirectStatus.APPLY.getAction();
		 
		AccountVO accountVO = pfpCustomerInfoService.existentAccount(memberId);
		
		if(accountVO != null){
			
			// 取最後一筆訂單資料
			PfpOrder order = pfpOrderService.latestOrder(accountVO.getCustomerInfoId());

			// 帳戶申請中
			if(EnumAccountStatus.APPLY.getStatus().equals(accountVO.getCustomerInfoStatus())){
				
				/** 
				 *  如果帳戶註冊狀態還停留在「註冊中」，就判定註冊失敗
				 */
				result = EnumRedirectStatus.APPLY_FAIL.getAction();
					
				// 未付款訂單成立
				if(EnumBillingStatus.B101.toString().equals(order.getStatus())){
				}
				
				// 付款中
				if(EnumBillingStatus.B201.toString().equals(order.getStatus()) || 
						EnumBillingStatus.B202.toString().equals(order.getStatus()) ||
						EnumBillingStatus.B203.toString().equals(order.getStatus())){
					result = EnumRedirectStatus.APPLY_WAIT.getAction();
				}
				
				// 付款成功
				if(EnumBillingStatus.B301.toString().equals(order.getStatus()) || 
						EnumBillingStatus.B302.toString().equals(order.getStatus())){
					result = EnumRedirectStatus.SUMMARY.getAction();
				}
				
				// 付款失敗
				if(EnumBillingStatus.B401.toString().equals(order.getStatus()) || 
						EnumBillingStatus.B402.toString().equals(order.getStatus()) ||
						EnumBillingStatus.B403.toString().equals(order.getStatus()) ||
						EnumBillingStatus.B404.toString().equals(order.getStatus()) ||
						EnumBillingStatus.B405.toString().equals(order.getStatus()) ||
						EnumBillingStatus.B406.toString().equals(order.getStatus()) ||
						EnumBillingStatus.B407.toString().equals(order.getStatus()) ||
						EnumBillingStatus.B408.toString().equals(order.getStatus())){
					result = EnumRedirectStatus.APPLY_FAIL.getAction();
				}
			}
			
			// 帳戶停權
			if(EnumAccountStatus.STOP.getStatus().equals(accountVO.getCustomerInfoStatus())){
				result = EnumRedirectStatus.ACCOUNT_REMAIN.getAction();
			}
			
			// 帳戶關閉
			if(EnumAccountStatus.CLOSE.getStatus().equals(accountVO.getCustomerInfoStatus())){
				result = EnumRedirectStatus.ACCOUNT_REMAIN.getAction();
			}
			
			// 帳戶使用
			if(EnumAccountStatus.START.getStatus().equals(accountVO.getCustomerInfoStatus())){
				result = EnumRedirectStatus.ACCOUNT_REMAIN.getAction();
			}
			
		}

		return result;
	}
	
	
	public void setPfpCustomerInfoService(PfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}
	public void setPfpOrderService(PfpOrderService pfpOrderService) {
		this.pfpOrderService = pfpOrderService;
	}
	
	
	
}
