package com.pchome.akbpfp.db.dao.catalog.TMP;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getCatalogAllList(String pfpCustomerInfoId) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" select * ");
		hql.append(" from pfp_catalog as a ");
		hql.append(" join pfp_catalog_upload_log as b ");
		hql.append(" on a.catalog_seq = b.catalog_seq ");
		hql.append(" where a.pfp_customer_info_id = '"+pfpCustomerInfoId+"'");

		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		
		return query.list();
	}
	
	public String getCatalogAllListCount(String pfpCustomerInfoId) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(*) ");
		hql.append(" from pfp_catalog as a ");
		hql.append(" join pfp_catalog_upload_log as b ");
		hql.append(" on a.catalog_seq = b.catalog_seq ");
		hql.append(" where a.pfp_customer_info_id = '"+pfpCustomerInfoId+"'");

		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		String count =  String.valueOf(query.list().get(0));
		
		return  count;
	}
	

	
}
