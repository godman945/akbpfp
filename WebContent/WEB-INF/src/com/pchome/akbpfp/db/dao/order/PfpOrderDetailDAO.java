package com.pchome.akbpfp.db.dao.order;

import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpOrderDetail;

public class PfpOrderDetailDAO extends BaseDAO<PfpOrderDetail,String> implements IPfpOrderDetailDAO{

	public void saveOrUpdatePfpOrderDetail(PfpOrderDetail pfpOrderDetail) throws Exception{
		super.getHibernateTemplate().saveOrUpdate(pfpOrderDetail);
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpOrderDetail> findPfpOrderDetail(String orderId) throws Exception{
		return super.getHibernateTemplate().find("from PfpOrderDetail where id.orderId = '"+orderId+"'");
	}
	
//	public void deleteOrderDetail(String orderId) throws Exception{
//		
//		StringBuffer hql = new StringBuffer();
//		
//		hql.append(" delete from PfpOrderDetail ");
//		hql.append(" where id.orderId = ? ");
//		
//		this.getSession().createQuery(hql.toString()).
//							setString(0, orderId)
//							.executeUpdate();
//	}
}
