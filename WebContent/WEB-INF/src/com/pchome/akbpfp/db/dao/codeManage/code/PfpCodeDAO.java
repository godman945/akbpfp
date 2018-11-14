package com.pchome.akbpfp.db.dao.codeManage.code;

import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCode;

public class PfpCodeDAO extends BaseDAO<PfpCode,String> implements IPfpCodeDAO{
	
	@SuppressWarnings("unchecked")
	public List<PfpCode> getPfpCode(String pfpCustomerInfoId) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCode ");
		hql.append(" where pfpCustomerInfoId = ? ");
		
		log.info("getPfpCode.sql = " + hql.toString());
		
		return super.getHibernateTemplate().find(hql.toString(), pfpCustomerInfoId);
		
	}
	
	

}
