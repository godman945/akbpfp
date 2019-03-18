package com.pchome.akbpfp.db.service.accesslog;

import java.util.List;

import com.pchome.akbpfp.db.dao.accesslog.IAdmAccesslogDAO;
import com.pchome.akbpfp.db.pojo.AdmAccesslog;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.rmi.accesslog.EnumAccesslogAction;
import com.pchome.rmi.accesslog.EnumAccesslogChannel;
import com.pchome.rmi.accesslog.EnumAccesslogEmailStatus;
import com.pchome.rmi.accesslog.IAccesslogProvider;

public class AdmAccesslogService extends BaseService <AdmAccesslog,String> implements IAdmAccesslogService{
	private IAccesslogProvider accesslogProvider;
	public Integer recordMemberLog(String pcId, String ip) throws Exception{
		// 會員登入 Accesslog
		Integer id = accesslogProvider.addAccesslog(EnumAccesslogChannel.MEMBER, 
			EnumAccesslogAction.MEMBER_LOGIN,
			EnumAccesslogAction.MEMBER_LOGIN.getMessage(), 
			pcId, 
			null, 
			null, 
			null, 
			ip,
			EnumAccesslogEmailStatus.NO);
		return id;
	}
	
	public Integer recordBillingLog(EnumAccesslogAction action, String message, String memberId, String orderId, String customerInfoId, String userId, String ip) throws Exception{
		// 金流介接  Accesslog	
		Integer id = accesslogProvider.addAccesslog(EnumAccesslogChannel.BILLING, 
			action, 
			message, 
			memberId, 
			orderId, 
			customerInfoId, 
			userId, 
			ip, 
			EnumAccesslogEmailStatus.NO);
		return id;
	}
	
	public Integer recordAdLog(EnumAccesslogAction action, String message, String memberId, String customerInfoId, String userId, String ip) throws Exception {		
	    // 廣告異動記錄 Accesslog
		Integer id = accesslogProvider.addAccesslog(EnumAccesslogChannel.PFP, 
			action, 
			message, 
			memberId, 
			null,
			customerInfoId, 
			userId, 
			ip, 
			EnumAccesslogEmailStatus.NO);
		return id ;
	}
	
	//寫入搜尋廣告出價設定批次紀錄
	public void recordAdLogList(List<AdmAccesslog> admAccesslogList) throws Exception {
	    ((IAdmAccesslogDAO) dao).recordAdLogList(admAccesslogList);
	}
	
	public void setAccesslogProvider(IAccesslogProvider accesslogProvider) {
		this.accesslogProvider = accesslogProvider;
	}


}
