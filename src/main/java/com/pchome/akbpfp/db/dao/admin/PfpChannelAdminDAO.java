package com.pchome.akbpfp.db.dao.admin;

import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpChannelAdmin;

public class PfpChannelAdminDAO extends BaseDAO<PfpChannelAdmin, Integer> implements IPfpChannelAdminDAO{

	@SuppressWarnings("unchecked")
	public List<PfpChannelAdmin> findChannelAdmin(String memberId){
		
		StringBuffer hql = new StringBuffer();
		
		hql.append(" from PfpChannelAdmin ");
		hql.append(" where memberId = ? ");
		
		return (List<PfpChannelAdmin>) super.getHibernateTemplate().find(hql.toString(), memberId);
	}
}
