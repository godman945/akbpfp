package com.pchome.akbpfp.db.dao.bill;

import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpTransDetail;

public class PfpTransDetailDAO extends BaseDAO <PfpTransDetail, String> implements IPfpTransDetailDAO{
	
	@SuppressWarnings("unchecked")
	public PfpTransDetail findLastTransDetail(String customerInfoId) throws Exception{
		
		String hql = "from PfpTransDetail where pfpCustomerInfo.customerInfoId = ? order by transId desc ";
		
		List<PfpTransDetail> list = (List<PfpTransDetail>) super.getHibernateTemplate().find(hql, customerInfoId);
		if(list.size()>0 && list != null){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpTransDetail> findAccountTransDetails(String customerInfoId, Date startDate, Date endDate){
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpTransDetail ");
		hql.append(" where pfpCustomerInfo.customerInfoId = ? ");
		hql.append(" and transDate >= ? ");
		hql.append(" and transDate <= ? ");
		hql.append(" order by transId desc ");
		
		Object[] ob = new Object[]{customerInfoId,startDate,endDate};
		
		return (List<PfpTransDetail>) super.getHibernateTemplate().find(hql.toString(), ob);
	}
	
}
