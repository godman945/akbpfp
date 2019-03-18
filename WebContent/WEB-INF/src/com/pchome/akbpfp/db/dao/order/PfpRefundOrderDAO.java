package com.pchome.akbpfp.db.dao.order;

import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpRefundOrder;

public class PfpRefundOrderDAO extends BaseDAO<PfpRefundOrder,String> implements IPfpRefundOrderDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<PfpRefundOrder> findPfpRefundOrder(String customerInfoId, String refundStatus) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpRefundOrder ");
		hql.append(" where pfpCustomerInfo.customerInfoId = '"+customerInfoId+"' ");
		hql.append(" and refundStatus = '" + refundStatus + "' ");
		
		return super.getHibernateTemplate().find(hql.toString());
	}
	
}
