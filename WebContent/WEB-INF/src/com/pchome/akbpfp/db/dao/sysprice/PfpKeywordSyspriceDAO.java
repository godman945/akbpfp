package com.pchome.akbpfp.db.dao.sysprice;



import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.akbpfp.db.pojo.PfpKeywordSysprice;
import com.pchome.config.TestConfig;
import com.pchome.enumerate.account.EnumAccountStatus;
import com.pchome.enumerate.utils.EnumStatus;

public class PfpKeywordSyspriceDAO extends BaseDAO<PfpKeywordSysprice, String> implements IPfpKeywordSyspriceDAO{

	@SuppressWarnings("unchecked")
	public PfpKeywordSysprice getKeywordSysprice(String keyword) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpKeywordSysprice ");
		hql.append(" where keyword = ? ");
		
		List<PfpKeywordSysprice> list = super.getHibernateTemplate().find(hql.toString(), keyword);
		
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PfpAdKeyword> getKeywordSearchPriceRange(String keyword) {
		
		StringBuffer hql = new StringBuffer();
		
		hql.append(" from PfpAdKeyword ");
		hql.append(" where adKeyword= ? ");
		hql.append(" and adKeywordStatus = ? ");
		hql.append(" and pfpAdGroup.adGroupStatus = ? ");
		hql.append(" and pfpAdGroup.pfpAdAction.adActionStatus = ? ");
		hql.append(" and pfpAdGroup.pfpAdAction.pfpCustomerInfo.status = ? ");
		hql.append(" and pfpAdGroup.pfpAdAction.pfpCustomerInfo.remain > ? ");
		hql.append(" group by adKeywordSearchPrice ");

		Object[] ob = new Object[]{keyword,
									EnumStatus.Open.getStatusId(),
									EnumStatus.Open.getStatusId(),
									EnumStatus.Open.getStatusId(),
									EnumAccountStatus.START.getStatus(),
									(float)3};
		
		return super.getHibernateTemplate().find(hql.toString(), ob);
	}
	
	public static void main(String arg[]) throws Exception{

		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);

		PfpKeywordSyspriceDAO pfpKeywordSyspriceDAO = (PfpKeywordSyspriceDAO) context.getBean("PfpKeywordSyspriceDAO");
		
		List<PfpAdKeyword> l = pfpKeywordSyspriceDAO.getKeywordSearchPriceRange("htc");
		
		System.out.println(l.size());
		
		for(Object o:l){
			Object oo[]=(Object[])o;
			System.out.println(oo[0]);
			System.out.println(oo[1]);
		}
		
		
	}

	
	
	
}
