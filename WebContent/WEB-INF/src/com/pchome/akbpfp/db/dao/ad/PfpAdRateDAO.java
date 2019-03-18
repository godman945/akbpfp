package com.pchome.akbpfp.db.dao.ad;

import java.text.SimpleDateFormat;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdRate;
import com.pchome.config.TestConfig;

public class PfpAdRateDAO extends BaseDAO<PfpAdRate,String> implements IPfpAdRateDAO{
	
	@Override
	public Long getDateAdCount(String date) throws Exception {
		StringBuffer hql = new StringBuffer();

		hql.append(" select  count(DISTINCT r.pfpAd.adSeq)");
		hql.append(" from PfpAdRate as r ");
		hql.append(" where r.recordDate = ?");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		//return (Long) super.getHibernateTemplate().find(hql.toString(), "'"+date+"'").get(0);
		return (Long) super.getHibernateTemplate().find(hql.toString(), sdf.parse(date)).get(0);
	}
	
	public static void main(String arg[]) throws Exception{

		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);

		PfpAdRateDAO pfpAdRateDAO = (PfpAdRateDAO) context.getBean("PfpAdRateDAO");
		
		long count=pfpAdRateDAO.getDateAdCount("2013-06-24");
		System.out.println(count);
	}
}
