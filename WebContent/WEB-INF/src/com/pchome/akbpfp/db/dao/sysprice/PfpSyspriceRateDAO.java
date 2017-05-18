package com.pchome.akbpfp.db.dao.sysprice;

import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.Query;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.expression.spel.ast.Selection;

import com.pchome.akbpfp.api.AsidRateUtile;
import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpSyspriceRate;
import com.pchome.config.TestConfig;
import com.pchome.enumerate.sysprice.EnumVirtualPool;

public class PfpSyspriceRateDAO extends BaseDAO<PfpSyspriceRate, String> implements IPfpSyspriceRateDAO{

	@SuppressWarnings("unchecked")
	public List<Object> getPoolSyspriceRate(String date) throws Exception{
		
		StringBuffer hql = new StringBuffer();

		hql.append(" select adPoolSeq, sum(kernelPrice), sum(syspriceRatePv) ");
		hql.append(" from PfpSyspriceRate ");
		hql.append(" where syspriceRateDate = ? ");
		hql.append(" group by adPoolSeq ");
		//log.info(" hql = "+hql.toString());
		
		return super.getHibernateTemplate().find(hql.toString(), date);
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getSyspriceRate(String date) throws Exception{
		
		StringBuffer hql = new StringBuffer();

		//hql.append(" select '"+EnumVirtualPool.adp_201301010001.toString()+"', sum(kernelPrice), sum(syspriceRatePv) ");
		//hql.append(" from PfpSyspriceRate ");
		//hql.append(" where syspriceRateDate = '"+date+"' ");
		
		hql.append(" select   t.kernelPrice  , sum( t.syspriceRatePv )");
		hql.append(" from PfpSyspriceRate as t ");
		hql.append(" where   t.syspriceRateDate=?"); 
		hql.append(" group by t.kernelPrice ");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		return super.getHibernateTemplate().find(hql.toString(), sdf.parse(date));
	}
	
	@SuppressWarnings("unchecked")
	public float getNewSyspriceRate(String date) throws Exception{
		
		float syspriceRate = 0;
		StringBuffer hql = new StringBuffer();
		
		hql.append(" select sum(ad_clk)/sum(ad_clk_price)/count(distinct ad_seq) ");
		hql.append(" from pfp_ad_report ");
		hql.append(" where ad_pvclk_date = '" + date + "' "); 
		
		String sql = hql.toString();
		Query query = this.getSession().createSQLQuery(sql);
		List<Object> list = query.list();
		if(!list.isEmpty()){
			if(list.get(0).toString() != null){
				syspriceRate = Float.parseFloat(list.get(0).toString());
			}
		}
		
		return syspriceRate;
	}
	
	public static void main(String arg[]) throws Exception{

		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);

		PfpSyspriceRateDAO pfpSyspriceRateDAO = (PfpSyspriceRateDAO) context.getBean("PfpSyspriceRateDAO");
		
		List<Object> l=pfpSyspriceRateDAO.getSyspriceRate("2013-06-18");
		
		for(Object o:l){
			Object[] oo=(Object[])o;
		
			System.out.println(oo[0]);
			System.out.println(oo[1]);
		}
		
	}
	
}
