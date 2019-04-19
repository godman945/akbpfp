package com.pchome.akbpfp.struts2.action.billing;

import com.pchome.akbpfp.api.AccountStatusAPI;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.billing.EnumRedirectStatus;

public class BillingRedirectAction extends BaseCookieAction{

	private AccountStatusAPI accountStatusAPI;
	
	public String redirectAction() throws Exception{
		
		String result = accountStatusAPI.verifyAccountStatus(super.getId_pchome());
		
		if(EnumRedirectStatus.APPLY_WAIT.getAction().equals(result) ||
				EnumRedirectStatus.APPLY_FAIL.getAction().equals(result)){
			result = EnumRedirectStatus.APPLY.getAction();
		}
		
		return result;
	}

	public void setAccountStatusAPI(AccountStatusAPI accountStatusAPI) {
		this.accountStatusAPI = accountStatusAPI;
	}	

	
}
