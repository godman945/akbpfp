package com.pchome.akbpfp.db.dao.accesslog;

import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.AdmAccesslog;
import com.pchome.rmi.accesslog.IAccesslogProvider;

public class AdmAccesslogDAO extends BaseDAO <AdmAccesslog,String> implements IAdmAccesslogDAO{

	private IAccesslogProvider accesslogProvider;
	
	@SuppressWarnings("unchecked")
	public List<AdmAccesslog> findAccesslogByMemberId(String memberId) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" from AdmAccesslog where memberId = ? ");
		hql.append(" order by createDate desc ");
		return super.getHibernateTemplate().find(hql.toString(), memberId);
	}	
	
	public void setAccesslogProvider(IAccesslogProvider accesslogProvider) throws Exception {
		this.accesslogProvider = accesslogProvider;
	}
	//寫入搜尋廣告出價設定批次紀錄
	public void recordAdLogList(List<AdmAccesslog> admAccesslogList) throws Exception {
	    for (AdmAccesslog admAccesslog : admAccesslogList) {
		this.saveOrUpdate(admAccesslog);
	    }
	}
}
