package com.pchome.akbpfp.db.dao.catalog.prod;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogProdEc;

public class PfpCatalogProdEcDAO extends BaseDAO<PfpCatalogProdEc,Integer> implements IPfpCatalogProdEcDAO{
	
	@SuppressWarnings("unchecked")
	public  List<Map<String,Object>> getProdList(String catalogSeq, String prodStatus, String pfpCustomerInfoId, int page, int pageSize)  throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select a.*,b.pfp_customer_info_id ");
		hql.append(" from pfp_catalog_prod_ec as a ");
		hql.append(" join pfp_catalog as b ");
		hql.append(" on a.catalog_seq = b.catalog_seq  ");
		hql.append(" where a.catalog_seq = '"+catalogSeq+"' ");
		hql.append(" and b.pfp_customer_info_id = '"+pfpCustomerInfoId+"' ");
		
		if (StringUtils.isNotBlank(prodStatus)){
			hql.append(" and a.prod_status = '"+prodStatus+"' ");
		}

		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setFirstResult((page-1)*pageSize);
		query.setMaxResults(pageSize);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		
		return query.list();
	}
	
	public String getProdListCount(String catalogSeq, String prodStatus) throws Exception{
		Session session = getSession();

		StringBuffer hql = new StringBuffer();
		hql.append("select count(*) from PfpCatalogProdEc ");
		hql.append(" where 1=1");
		hql.append(" and pfpCatalog.catalogSeq = :catalogSeq");
		if (StringUtils.isNotBlank(prodStatus)) {
			hql.append(" and ecStatus = :ecStatus");
		}

		Query q = session.createQuery(hql.toString())
					.setString("catalogSeq", catalogSeq);
		
		if (StringUtils.isNotBlank(prodStatus)) {
			q.setString("ecStatus", prodStatus);
		}
		
		log.info(" getProdListCount sql  = "+ hql.toString());
		log.info(" resultData size  = "+ q.list().size());

		List<Object> resultData = q.list();
//		if(resultData != null) {
//			count = Long.parseLong(resultData.get(0).toString());
//		}

		return resultData.get(0).toString();
	}
	
	public void updateProdListProdStatus(String catalogSeq, String prodStatus, List<String> prodIdList) throws Exception {
		StringBuffer sql = new StringBuffer()
				.append(" update pfp_catalog_prod_ec set prod_status = :ecStatus where catalog_seq = :catalogSeq and id in (:prodListId) ");
		
		 log.info("updateProdListProdStatus.sql = " + sql);

		Session session = getSession();
		session.createSQLQuery(sql.toString()).setString("ecStatus", prodStatus)
				.setString("catalogSeq", catalogSeq)
				.setParameterList("prodListId", prodIdList).executeUpdate();
		session.flush();
		
	}
	
	public List<Map<String,Object>> queryProdListDetail(String catalogSeq,String prodId) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" select  * from ");
		hql.append(" pfp_catalog_prod_ec ");
		hql.append(" where 1 = 1 ");
		hql.append(" and catalog_seq =  '" + catalogSeq + "'");
		hql.append(" and id = '"+prodId+"' ");

		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		
		return query.list();
	}
	
	
	public List<Map<String,Object>> getProdGroupCount(String catalogSeq, String filterSQL) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(*) as count ");
		hql.append(" from pfp_catalog_prod_ec ");
		hql.append(" where 1 = 1 ");
		hql.append(" and catalog_seq =  '" + catalogSeq + "' ");
		hql.append( filterSQL );
		
		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getEcProdGroupListByRandom(String catalogSeq, String filterSQL, int prodNum) throws Exception{

		StringBuffer hql = new StringBuffer();
		hql.append(" select  * from ");
		hql.append(" pfp_catalog_prod_ec ");
		hql.append(" where 1 = 1 ");
		hql.append(" and catalog_seq =  '" + catalogSeq + "'");
		hql.append(" and ec_check_status = '1' ");
		hql.append(" and ec_status = '1' ");
		hql.append(filterSQL);
		hql.append(" order by rand() limit "+prodNum);

		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getEcProdGroupList(String catalogSeq, String filterSQL) throws Exception{

		StringBuffer hql = new StringBuffer();
		hql.append(" select  * from ");
		hql.append(" pfp_catalog_prod_ec ");
		hql.append(" where 1 = 1 ");
		hql.append(" and catalog_seq =  '" + catalogSeq + "'");
		hql.append(filterSQL);
		
		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		return query.list();
	}

}
