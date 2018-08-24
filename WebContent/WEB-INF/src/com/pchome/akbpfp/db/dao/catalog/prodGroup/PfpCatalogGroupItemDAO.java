package com.pchome.akbpfp.db.dao.catalog.prodGroup;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;

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
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getCatalogAllGroupItem(String catalogSeq) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" select b.* ");
		hql.append(" from pfp_catalog_group as a ");
		hql.append(" join pfp_catalog_group_item as b ");
		hql.append(" on a.catalog_group_seq = b.catalog_group_seq ");
		hql.append(" where a.catalog_seq =  '" + catalogSeq + "' ");
		
		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		
		return query.list();
	}
	

	

}
