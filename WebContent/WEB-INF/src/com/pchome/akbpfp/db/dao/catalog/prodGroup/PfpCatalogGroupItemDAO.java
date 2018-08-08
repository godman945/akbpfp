package com.pchome.akbpfp.db.dao.catalog.prodGroup;

import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;

public class PfpCatalogGroupItemDAO extends BaseDAO<PfpCatalogGroupItem,Integer> implements IPfpCatalogGroupItemDAO{
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalogGroupItem> getPfpCatalogGroupItemList(String groupId) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalogGroupItem ");
		hql.append(" where pfpCatalogGroup.catalogGroupSeq = '"+groupId+"' ");
		
		return super.getHibernateTemplate().find(hql.toString());
	}
	

	

}
