package com.pchome.akbpfp.db.dao.sysprice;

import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdSysprice;
import com.pchome.enumerate.account.EnumAccountStatus;
import com.pchome.enumerate.utils.EnumStatus;

public class PfpAdSyspriceDAO extends BaseDAO<PfpAdSysprice, String> implements IPfpAdSyspriceDAO{

	@SuppressWarnings("unchecked")
	public PfpAdSysprice getAdSysprice(String adPoolSeq) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpAdSysprice ");
		hql.append(" where adPoolSeq = ? ");
		
		List<PfpAdSysprice> list = super.getHibernateTemplate().find(hql.toString(), adPoolSeq);
		
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<PfpAdGroup> getAdChannelPriceRange() throws Exception {

		StringBuffer hql = new StringBuffer();
		
		hql.append(" from PfpAdGroup ");
		hql.append(" where adGroupStatus = ? ");
		hql.append(" and pfpAdAction.adActionStatus = ? ");
		hql.append(" and pfpAdAction.pfpCustomerInfo.status = ? ");
		hql.append(" and pfpAdAction.pfpCustomerInfo.remain > ? ");
		hql.append(" group by adGroupChannelPrice ");
		
		Object[] ob = new Object[]{EnumStatus.Open.getStatusId(),
									EnumStatus.Open.getStatusId(),
									EnumAccountStatus.START.getStatus(),(float)3};
		
		return super.getHibernateTemplate().find(hql.toString(), ob);
	}
}
