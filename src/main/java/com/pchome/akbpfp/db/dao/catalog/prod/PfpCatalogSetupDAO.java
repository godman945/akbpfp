package com.pchome.akbpfp.db.dao.catalog.prod;

import org.hibernate.Query;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogSetup;

public class PfpCatalogSetupDAO extends BaseDAO<PfpCatalogSetup,Integer> implements IPfpCatalogSetupDAO{

	@Override
	public PfpCatalogSetup findSetupByCatalogSeq(String catalogSeq) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from PfpCatalogSetup ");
		hql.append(" where 1=1");
		hql.append(" and pfpCatalog.catalogSeq = :catalogSeq");
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql.toString()).setString("catalogSeq", catalogSeq);
		return query.list().size() > 0 ? (PfpCatalogSetup) query.list().get(0) : null;
	}


}
