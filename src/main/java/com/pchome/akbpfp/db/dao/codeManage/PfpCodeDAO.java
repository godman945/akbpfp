package com.pchome.akbpfp.db.dao.codeManage;

import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCode;

public class PfpCodeDAO extends BaseDAO<PfpCode,String> implements IPfpCodeDAO{
	
	@SuppressWarnings("unchecked")
	public List<PfpCode> getPfpCode(String pfpCustomerInfoId) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCode ");
		hql.append(" where pfpCustomerInfoId = ? ");
		
		log.info("getPfpCode.sql = " + hql.toString());
		
		return (List<PfpCode>) super.getHibernateTemplate().find(hql.toString(), pfpCustomerInfoId);
	}
	
    @SuppressWarnings("unchecked")
    public void saveOrUpdateWithCommit(PfpCode pfpCode) throws Exception{
    	super.getHibernateTemplate().getSessionFactory().getCurrentSession().saveOrUpdate(pfpCode);
//		super.getHibernateTemplate().getSessionFactory().getCurrentSession().beginTransaction().commit();
    }
	
	

}
