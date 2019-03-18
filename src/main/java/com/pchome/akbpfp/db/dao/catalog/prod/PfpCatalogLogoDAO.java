package com.pchome.akbpfp.db.dao.catalog.prod;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogLogo;


public class PfpCatalogLogoDAO extends BaseDAO<PfpCatalogLogo,String> implements IPfpCatalogLogoDAO{
	
	@Override
	public List<PfpCatalogLogo> findCatalogLogoByCustomerInfoId(String customerInfoId) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from PfpCatalogLogo ");
		hql.append(" where 1=1");
		hql.append(" and pfpCustomerInfoId = :customerInfoId");
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql.toString()).setString("customerInfoId", customerInfoId);
		return query.list().size() > 0 ? (List<PfpCatalogLogo>) query.list() : null;
	}

	@Override
	public PfpCatalogLogo findCatalogLogoByLogoType(String customerInfoId, String logoType) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from PfpCatalogLogo ");
		hql.append(" where 1=1");
		hql.append(" and pfpCustomerInfoId = :customerInfoId and catalogLogoType = :logoType ");
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setString("customerInfoId", customerInfoId);
		query.setString("logoType", logoType);
		return query.list().size() > 0 ? (PfpCatalogLogo)query.list().get(0) : null;
	}

}
