package com.pchome.akbpfp.db.dao.catalog.TMP;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.enumerate.utils.EnumStatus;

public class PfpCatalogDAO extends BaseDAO<PfpCatalog,String> implements IPfpCatalogDAO{
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalog> getCatalogType(String catalogSeq) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalog ");
		hql.append(" where catalogSeq = ? ");
		
		return super.getHibernateTemplate().find(hql.toString(), catalogSeq);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalog> getPfpCatalog(String catalogSeq) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalog ");
		hql.append(" where catalogSeq = ? ");
		
		return super.getHibernateTemplate().find(hql.toString(), catalogSeq);
		
		
//		StringBuffer hql = new StringBuffer();
//		hql.append(" from PfpCatalog ");
//		hql.append(" where catalogSeq = '"+catalogSeq+"' ");
//		
//		return super.getHibernateTemplate().find(hql.toString());
		
		
		
	}

	public List<PfpCatalog> getPfpCatalogByCustomerInfoId(String customerInfoId) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalog ");
		hql.append(" where pfpCustomerInfoId =:customerInfoId ");
		Session session = super.getSession();
        Query query = session.createQuery(hql.toString());
        query.setParameter("customerInfoId", customerInfoId);
        return query.list();
	}
}
