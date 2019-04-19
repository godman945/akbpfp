package com.pchome.akbpfp.db.dao.order;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpOrder;
import com.pchome.config.TestConfig;
import com.pchome.enumerate.billing.EnumBillingStatus;

public class PfpOrderDAO extends BaseDAO<PfpOrder,String> implements IPfpOrderDAO{
	
	@SuppressWarnings("unchecked")
	public PfpOrder getOrder(String orderId) throws Exception{
		List<PfpOrder> list = (List<PfpOrder>) super.getHibernateTemplate().find("from PfpOrder where orderId = '"+orderId+"' ");
		if(list.size()>0 && list != null){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public PfpOrder findApplyPfpOrder(String customerInfoId) throws Exception {
		String hql = " from PfpOrder "+
					" where pfpCustomerInfo.customerInfoId = '"+customerInfoId+"' "+
					" order by createDate desc ";
		
		List<PfpOrder> list = (List<PfpOrder>) super.getHibernateTemplate().find(hql);
		if(list.size()>0 && list != null){
			return list.get(0);
		}else{
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public List<PfpOrder> findPfpOrder(String customerInfoId, String date) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpOrder ");
		hql.append(" where pfpCustomerInfo.customerInfoId = '"+customerInfoId+"' ");
		hql.append(" and (status = '"+EnumBillingStatus.B301+"' or status = '"+EnumBillingStatus.B302+"') ");
		hql.append(" and date_format(createDate, '%Y-%m-%d') = '"+date+"' ");
		
		return (List<PfpOrder>) super.getHibernateTemplate().find(hql.toString());
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpOrder> latestOrder(String customerInfoId) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpOrder ");
		hql.append(" where pfpCustomerInfo.customerInfoId = ? ");
		hql.append(" order by createDate desc ");

		return (List<PfpOrder>) super.getHibernateTemplate().find(hql.toString(), customerInfoId);
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpOrder> findPfpOrder(String orderId) {
		
		StringBuffer hql = new StringBuffer();
		
		hql.append(" from PfpOrder ");
		hql.append(" where orderId = ? ");
		
		return (List<PfpOrder>) super.getHibernateTemplate().find(hql.toString(), orderId);
	}
	
	public static void main(String arg[]) throws Exception{

		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);

		PfpOrderDAO dao = (PfpOrderDAO) context.getBean("PfpOrderDAO");

		//PfpOrder pfpOrder = dao.findApplyPfpOrder("AC201301310000000039");
		//System.out.println("  "+;
		//String date = DateValueUtil.getInstance().getDateValue(DateValueUtil.YESTERDAY, DateValueUtil.DBPATH);
		//System.out.println(" size  = "+dao.findPfpOrder("AC201303060000000023"));
	}
}
