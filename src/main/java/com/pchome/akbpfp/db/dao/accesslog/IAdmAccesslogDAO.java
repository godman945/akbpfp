package com.pchome.akbpfp.db.dao.accesslog;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.AdmAccesslog;


public interface IAdmAccesslogDAO extends IBaseDAO <AdmAccesslog,String>{
	
	public List<AdmAccesslog> findAccesslogByMemberId(String memberId) throws Exception;
	
	//寫入搜尋廣告出價設定批次紀錄
	public void recordAdLogList(List<AdmAccesslog> admAccesslogList) throws Exception;
	
}
