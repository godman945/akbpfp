package com.pchome.akbpfp.db.dao.catalog.prod;

import org.hibernate.Query;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogSetup;

public class PfpCatalogSetupDAO extends BaseDAO<PfpCatalogSetup,Integer> implements IPfpCatalogSetupDAO{

	@Override
	public PfpCatalogSetup findSetupByCustomerInfoId(String customerInfoId) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from PfpCatalogSetup ");
		hql.append(" where 1=1");
		hql.append(" and pfpCustomerInfoId = :customerInfoId");
		Query query = super.getSession().createQuery(hql.toString()).setString("customerInfoId", customerInfoId);
		return query.list().size() > 0 ? (PfpCatalogSetup) query.list().get(0) : null;
	}


}
