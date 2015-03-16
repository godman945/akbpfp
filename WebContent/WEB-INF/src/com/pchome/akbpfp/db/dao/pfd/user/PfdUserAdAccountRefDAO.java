package com.pchome.akbpfp.db.dao.pfd.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfdUserAdAccountRef;
import com.pchome.enumerate.account.EnumAccountStatus;

public class PfdUserAdAccountRefDAO extends BaseDAO <PfdUserAdAccountRef, String> implements IPfdUserAdAccountRefDAO{

	
	@SuppressWarnings("unchecked")
	public List<PfdUserAdAccountRef> checkPfdAndPfpRef(String pfdCustomerInfoId, String pfpCustomerInfoId) {
		
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		hql.append(" from PfdUserAdAccountRef ");
		hql.append(" where pfdCustomerInfo.customerInfoId = ? ");
		hql.append(" and pfpCustomerInfo.customerInfoId = ? ");
		hql.append(" and pfdCustomerInfo.activateDate != null ");
		hql.append(" and pfpCustomerInfo.activateDate != null ");
		
		list.add(pfdCustomerInfoId);
		list.add(pfpCustomerInfoId);
		
		return super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public List<PfdUserAdAccountRef> findPfdUserAdAccountRef(String pfpCustomerInfoId) {
		
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		hql.append(" from PfdUserAdAccountRef ");
		hql.append(" where pfpCustomerInfo.customerInfoId = ? ");
		hql.append(" order by refId desc ");
		
		list.add(pfpCustomerInfoId);		
		
		return super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}
}
