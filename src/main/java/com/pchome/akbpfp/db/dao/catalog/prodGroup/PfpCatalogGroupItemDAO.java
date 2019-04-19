package com.pchome.akbpfp.db.dao.catalog.prodGroup;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;

public class PfpCatalogGroupItemDAO extends BaseDAO<PfpCatalogGroupItem,Integer> implements IPfpCatalogGroupItemDAO{
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalogGroupItem> getPfpCatalogGroupItemList(String catalogGroupSeq) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalogGroupItem ");
		hql.append(" where pfpCatalogGroup.catalogGroupSeq = '"+catalogGroupSeq+"' ");
		
		return (List<PfpCatalogGroupItem>) super.getHibernateTemplate().find(hql.toString());
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getCatalogAllGroupItem(String catalogSeq,String catalogGroupSeq) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" select b.* ");
		hql.append(" from pfp_catalog_group as a ");
		hql.append(" join pfp_catalog_group_item as b ");
		hql.append(" on a.catalog_group_seq = b.catalog_group_seq ");
		hql.append(" where a.catalog_seq =  '" + catalogSeq + "' ");
		
		if(StringUtils.isNotBlank(catalogGroupSeq)){
			hql.append(" and a.catalog_group_seq  <>  '" + catalogGroupSeq + "' ");
		}
		
		log.info(hql.toString());

		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public void deleteCatalogGroupItem(String catalogGroupSeq) throws Exception{
		String sql = "delete from PfpCatalogGroupItem where pfpCatalogGroup.catalogGroupSeq = :catalogGroupSeq";		
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.createQuery(sql).setString("catalogGroupSeq", catalogGroupSeq).executeUpdate();
		session.flush();
	}
	

	

}
