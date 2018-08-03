package com.pchome.akbpfp.db.dao.catalog.prodGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfdUserMemberRef;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;

public class PfpCatalogGroupDAO extends BaseDAO<PfpCatalogGroup,String> implements IPfpCatalogGroupDAO{
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalogGroup> getCatalogType(String groupId) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalogGroup ");
		hql.append(" where catalogGroupSeq = '"+groupId+"' ");
		
		return super.getHibernateTemplate().find(hql.toString());
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalogGroup> getCatalogSeq(String groupId) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalogGroup ");
		hql.append(" where catalogGroupSeq = '"+groupId+"' ");
		
		return super.getHibernateTemplate().find(hql.toString());
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getEcProdGroupList(String catalogSeq, String filterSQL) throws Exception{

		StringBuffer hql = new StringBuffer();
		hql.append(" select  * from ");
		hql.append(" pfp_catalog_prod_ec ");
		hql.append(" where 1 = 1 ");
		hql.append(" and catalog_seq =  '" + catalogSeq + "'");
		hql.append(" and prod_check_status = '1' ");
		hql.append(" and prod_status = '1' ");
		hql.append(filterSQL);

		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); // 这样子返回Map
		return query.list();
	}
	


//	@SuppressWarnings("unchecked")
//	public List<PfpCustomerInfo> findCustomerInfo(String customerInfoId) {
//		
//		StringBuffer hql = new StringBuffer();
//		
//		hql.append(" from PfpCustomerInfo ");
//		hql.append(" where customerInfoId = ? ");
//		hql.append(" and status != ? ");
//		
//		Object[] ob = new Object[]{customerInfoId, 
//									EnumAccountStatus.DELETE.getStatus()};
//		
//		return super.getHibernateTemplate().find(hql.toString(),ob);
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<PfpCustomerInfo> findAllPfpCustomerInfo() {
//		String hql = " from PfpCustomerInfo where status != '2' or status != '3' ";
//		return super.getHibernateTemplate().find(hql);
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<PfpCustomerInfo> findValidCustomerInfos(){
//		
//		StringBuffer hql = new StringBuffer();
//		hql.append(" from PfpCustomerInfo ");
//		hql.append(" where status = ? or status = ? or status = ? ");
//		
//		Object[] ob = new Object[]{EnumAccountStatus.CLOSE.getStatus(),
//									EnumAccountStatus.START.getStatus(),
//									EnumAccountStatus.STOP.getStatus()};
//		
//		return super.getHibernateTemplate().find(hql.toString(),ob);
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<PfpCustomerInfo> findCustomerInfoByMmeberId(String memberId) {
//		
//		StringBuffer hql = new StringBuffer();
//		List<Object> list = new ArrayList<Object>();
//		
//		hql.append(" from PfpCustomerInfo ");
//		hql.append(" where memberId = ? ");
//		hql.append(" and status != ? ");
//		hql.append(" order by createDate desc ");
//		
//		list.add(memberId);
//		list.add(EnumAccountStatus.DELETE.getStatus());
//		
//		return super.getHibernateTemplate().find(hql.toString(), list.toArray());
//	}
//	
//	public void deleteCustomerInfo(String memberId) throws Exception{
//		
//		StringBuffer hql = new StringBuffer();
//		
//		hql.append(" delete from PfpCustomerInfo ");
//		hql.append(" where customerInfoId = ? ");
//		
//		this.getSession().createQuery(hql.toString()).
//							setString(0, memberId)
//							.executeUpdate();
//
//	}
	

}
