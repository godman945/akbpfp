package com.pchome.akbpfp.db.dao.freeAction;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.AdmFreeRecord;

public class AdmFreeRecordDAO extends BaseDAO<AdmFreeRecord, Integer> implements IAdmFreeRecordDAO{

	@SuppressWarnings("unchecked")
	public List<AdmFreeRecord> findAccountFree(String customerInfoId, Date startDate, Date endDate){
		StringBuffer hql = new StringBuffer();
		hql.append(" from AdmFreeRecord ");
		hql.append(" where customerInfoId = ? ");
		hql.append(" and recordDate >= ? ");
		hql.append(" and recordDate <= ? ");
		hql.append(" order by recordId desc ");
		
		Object[] ob = new Object[]{customerInfoId,startDate,endDate};
		
		return (List<AdmFreeRecord>) super.getHibernateTemplate().find(hql.toString(), ob);
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmFreeRecord> findUserRecord(String actionId, String customerInfoId){
		StringBuffer hql = new StringBuffer();
		hql.append(" from AdmFreeRecord ");
		hql.append(" where admFreeAction.actionId = ? ");
		hql.append(" and customerInfoId = ? ");
		hql.append(" order by recordId desc ");
		
		Object[] ob = new Object[]{actionId,customerInfoId};
		
		return (List<AdmFreeRecord>) super.getHibernateTemplate().find(hql.toString(), ob);
	}
}
