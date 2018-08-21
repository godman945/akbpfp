package com.pchome.akbpfp.db.dao.catalog.TMP;

import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;

public class PfpCatalogDAO extends BaseDAO<PfpCatalog,String> implements IPfpCatalogDAO{
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalog> getCatalogType(String catalogSeq) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalog ");
		hql.append(" where catalogSeq = '"+catalogSeq+"' ");
		
		return super.getHibernateTemplate().find(hql.toString());
	}

}
