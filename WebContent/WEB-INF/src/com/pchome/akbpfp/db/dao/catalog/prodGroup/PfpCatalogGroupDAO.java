package com.pchome.akbpfp.db.dao.catalog.prodGroup;

import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;

public class PfpCatalogGroupDAO extends BaseDAO<PfpCatalogGroup,String> implements IPfpCatalogGroupDAO{
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalogGroup> getCatalogType(String groupId) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalogGroup ");
		hql.append(" where catalogGroupSeq = '"+groupId+"' ");
		
		return super.getHibernateTemplate().find(hql.toString());
		
//		StringBuffer hql = new StringBuffer();
//		hql.append(" from PfpCatalogGroup ");
//		hql.append(" where catalogGroupSeq = ? ");
//		Object[] ob = new Object[]{groupId};
//		return super.getHibernateTemplate().find(hql.toString(), ob);
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
