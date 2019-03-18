package com.pchome.akbpfp.db.service.accesslog;

import java.util.List;

import com.pchome.akbpfp.db.pojo.AdmAccesslog;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.rmi.accesslog.EnumAccesslogAction;

public interface IAdmAccesslogService extends IBaseService <AdmAccesslog,String>{
	
	public Integer recordMemberLog(String pcId, String ip) throws Exception;
	
	public Integer recordBillingLog(EnumAccesslogAction action, String message, String memberId, String orderId, String customerInfoId, String userId, String ip) throws Exception;	
	
	public Integer recordAdLog(EnumAccesslogAction action, String message, String memberId, String customerInfoId, String userId, String ip) throws Exception;
	//寫入搜尋廣告出價設定批次紀錄
	public void recordAdLogList(List<AdmAccesslog> admAccesslogList) throws Exception;
}
