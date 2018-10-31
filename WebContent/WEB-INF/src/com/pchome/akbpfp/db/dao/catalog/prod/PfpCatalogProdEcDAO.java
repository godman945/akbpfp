package com.pchome.akbpfp.db.dao.catalog.prod;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogProdEc;
import com.pchome.akbpfp.db.vo.catalog.prodGroup.ProdGroupConditionVO;
import com.pchome.akbpfp.db.vo.catalog.prodList.ProdListConditionVO;
import com.pchome.enumerate.catalogprod.EnumEcCheckStatusType;
import com.pchome.enumerate.catalogprod.EnumEcStatusType;

public class PfpCatalogProdEcDAO extends BaseDAO<PfpCatalogProdEc,Integer> implements IPfpCatalogProdEcDAO{
	
	@SuppressWarnings("unchecked")
	public  List<Map<String,Object>> getProdList(ProdListConditionVO prodListConditionVO)  throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select a.*,b.pfp_customer_info_id ");
		hql.append(" from pfp_catalog_prod_ec as a ");
		hql.append(" join pfp_catalog as b ");
		hql.append(" on a.catalog_seq = b.catalog_seq  ");
		hql.append(" where a.catalog_seq = '"+prodListConditionVO.getCatalogSeq()+"' ");
		hql.append(" and b.pfp_customer_info_id = '"+prodListConditionVO.getPfpCustomerInfoId()+"' ");
		
		if (StringUtils.isNotBlank(prodListConditionVO.getProdStatus())){
			hql.append(" and a.ec_status = '"+prodListConditionVO.getProdStatus()+"' ");
		}
		
		if (StringUtils.isNotBlank(prodListConditionVO.getProdName())){
			hql.append(" and a.ec_name like '%"+prodListConditionVO.getProdName()+"%' ");
		}
		

		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setFirstResult((prodListConditionVO.getPage()-1)*prodListConditionVO.getPageSize());
		query.setMaxResults(prodListConditionVO.getPageSize());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		
		return query.list();
	}
	
	public String getProdListCount(ProdListConditionVO prodListConditionVO) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(*) ");
		hql.append(" from pfp_catalog_prod_ec as a ");
		hql.append(" join pfp_catalog as b ");
		hql.append(" on a.catalog_seq = b.catalog_seq  ");
		hql.append(" where a.catalog_seq = '"+prodListConditionVO.getCatalogSeq()+"' ");
		hql.append(" and b.pfp_customer_info_id = '"+prodListConditionVO.getPfpCustomerInfoId()+"' ");
		
		if (StringUtils.isNotBlank(prodListConditionVO.getProdStatus())){
			hql.append(" and a.ec_status = '"+prodListConditionVO.getProdStatus()+"' ");
		}
		
		if (StringUtils.isNotBlank(prodListConditionVO.getProdName())){
			hql.append(" and a.ec_name like '%"+prodListConditionVO.getProdName()+"%' ");
		}
		

		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		String prodListCount = String.valueOf(query.list().get(0));
		
		return prodListCount;
		
	}
	
	public void updateProdListProdStatus(String catalogSeq, String prodStatus, List<String> prodIdList) throws Exception {
		StringBuffer sql = new StringBuffer()
				.append(" update pfp_catalog_prod_ec set ec_status = :ecStatus where catalog_seq = :catalogSeq and id in (:prodListId) ");
		
		 log.info("updateProdListProdStatus.sql = " + sql.toString());

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
		hql.append(" and ec_status = '"+EnumEcStatusType.Open_Prod.getType()+"' ");
		hql.append(" and ec_check_status  = '"+EnumEcCheckStatusType.Reviewed_Passed.getType()+"' ");
		hql.append( filterSQL );
		
		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getEcProdGroupListByRandom(String catalogSeq, String filterSQL, int prodNum) throws Exception{

		StringBuffer hql = new StringBuffer();
		hql.append(" select a.*,b.catalog_setup_value  ");
		hql.append(" from pfp_catalog_prod_ec a ");
		hql.append(" join pfp_catalog_setup as b ");
		hql.append(" on a.catalog_seq = b.catalog_seq ");
		hql.append(" where 1 = 1 ");
		hql.append(" and a.catalog_seq =  '" + catalogSeq + "'");
		hql.append(" and a.ec_check_status = '"+EnumEcCheckStatusType.Reviewed_Passed.getType()+"' ");
		hql.append(" and a.ec_status = '"+EnumEcStatusType.Open_Prod.getType()+"' ");
		hql.append(" and b.catalog_setup_key='img_proportiona' ");
		hql.append(filterSQL);
		hql.append(" order by rand() limit "+prodNum);

		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getEcProdGroupList(ProdGroupConditionVO prodGroupConditionVO) throws Exception{

		StringBuffer hql = new StringBuffer();
		hql.append(" select a.*,b.pfp_customer_info_id ");
		hql.append(" from pfp_catalog_prod_ec as a ");
		hql.append(" join pfp_catalog as b ");
		hql.append(" on a.catalog_seq = b.catalog_seq  ");
		hql.append(" where a.catalog_seq = '"+prodGroupConditionVO.getCatalogSeq()+"' ");
		hql.append(" and b.pfp_customer_info_id = '"+prodGroupConditionVO.getPfpCustomerInfoId()+"' ");
		hql.append(" and a.ec_status = '"+EnumEcStatusType.Open_Prod.getType()+"' ");
		hql.append(prodGroupConditionVO.getFilterSQL());
		
		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setFirstResult((prodGroupConditionVO.getPage()-1)*prodGroupConditionVO.getPageSize());
		query.setMaxResults(prodGroupConditionVO.getPageSize());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryCategoryGroupByVal(String catalogSeq)  throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" select ec_category ");
		hql.append(" from pfp_catalog_prod_ec ");
		hql.append(" where 1=1 ");
		hql.append(" and catalog_seq = '"+catalogSeq+"' ");
		hql.append(" group by ec_category ");
		
		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		
		return query.list();
	}

}
