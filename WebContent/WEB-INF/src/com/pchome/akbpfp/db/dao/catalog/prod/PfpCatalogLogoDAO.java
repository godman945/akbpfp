package com.pchome.akbpfp.db.dao.catalog.prod;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfdUserMemberRef;
import com.pchome.akbpfp.db.pojo.PfpCatalogLogo;

public class PfpCatalogLogoDAO extends BaseDAO<PfpCatalogLogo,String> implements IPfpCatalogLogoDAO{

	@Override
	public PfpCatalogLogo findCatalogLogoByCustomerInfoId(String customerInfoId) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append("from PfpCatalogLogo ");
		hql.append(" where 1=1");
		hql.append(" and pfpCustomerInfoId = :customerInfoId");
		Query query = super.getSession().createQuery(hql.toString()).setString("customerInfoId", customerInfoId);
		return query.list().size() > 0 ? (PfpCatalogLogo) query.list().get(0) : null;
	}

}
