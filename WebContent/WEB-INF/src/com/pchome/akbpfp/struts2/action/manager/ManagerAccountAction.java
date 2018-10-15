package com.pchome.akbpfp.struts2.action.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.pchome.akbpfp.api.CookieProccessAPI;
import com.pchome.akbpfp.db.pojo.PfpUser;
import com.pchome.akbpfp.db.service.user.IPfpUserService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.account.EnumPfpRootUser;
import com.pchome.rmi.manager.IPfpProvider;
import com.pchome.rmi.manager.PfpAccountVO;

public class ManagerAccountAction extends BaseCookieAction{

	private IPfpProvider pfpProviderProxy;
	private IPfpUserService pfpUserService;
	private CookieProccessAPI cookieProccessAPI;

	private String accountId;				// 要登入的帳號

	private List<PfpAccountVO> vos;

	public String execute() {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String ip = request.getRemoteAddr();
		log.info(">>>login  ip = " + ip);
		
		vos = pfpProviderProxy.findPfpAccount(super.getId_pchome(),ip);
		
		//log.info(" vos: "+vos.size());
		// 無資料就導首頁
		if(vos.isEmpty()){
			log.info(">>>>>> vos is null");
			return "index";
		}
		
		return SUCCESS;
	}

	/**
	 * 切換其他帳戶
	 */
	public String changeAccountAction() {
		
		//log.info(" accountId: " + accountId);
		
			//super.getRoot_user()
		PfpUser pfpUser = pfpUserService.findRootUser(accountId);
		
		//log.info(" pfpUser: " + pfpUser);
		
		// 切換 PFP 其他帳號, 這時才需要金流的cookie
		if(pfpUser != null){
			
			// 小天使登入客戶帳戶
			cookieProccessAPI.writerPfpLoginCookie(super.response, pfpUser, EnumPfpRootUser.PCHOME_MANAGER, super.getId_pchome());
			
		}
		
		return SUCCESS;
	}
	
	public void setPfpProviderProxy(IPfpProvider pfpProviderProxy) {
		this.pfpProviderProxy = pfpProviderProxy;
	}

	public void setPfpUserService(IPfpUserService pfpUserService) {
		this.pfpUserService = pfpUserService;
	}

	public void setCookieProccessAPI(CookieProccessAPI cookieProccessAPI) {
		this.cookieProccessAPI = cookieProccessAPI;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public List<PfpAccountVO> getVos() {
		return vos;
	}
}
