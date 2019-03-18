package com.pchome.akbpfp.db.dao.manager;

import java.util.ArrayList;
import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.AdmManagerDetail;

public class AdmManagerDetailDAO extends BaseDAO<AdmManagerDetail, Integer> implements IAdmManagerDetailDAO{

	@SuppressWarnings("unchecked")
	public List<AdmManagerDetail> findAdmManagerDetail(String memberId, String channelCategory) {
		
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		hql.append(" from AdmManagerDetail where memberId = ? ");
		hql.append(" and managerChannel = ? ");
		
		list.add(memberId);
		list.add(channelCategory);
		
		return super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}
}
