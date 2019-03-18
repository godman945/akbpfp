package com.pchome.akbpfp.db.dao.ad;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.akbpfp.db.pojo.PfpAdKeywordPvclk;


public class PfpAdKeywordPvclkDAO extends BaseDAO<PfpAdKeywordPvclk,String> implements IPfpAdKeywordPvclkDAO{
	
	@SuppressWarnings("unchecked")
	public List<PfpAdKeywordPvclk> getPfpAdKeywordPvclks(String adKeywordPvclkSeq, String adKeywordSeq, String adKeywordPvclkDate, String adKeywordPv, String adKeywordClk) throws Exception{
		StringBuffer sql = new StringBuffer("from PfpAdKeywordPvclk where 1=1");
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(adKeywordPvclkSeq)) {
			sql.append(" and adKeywordPvclkSeq like :adKeywordPvclkSeq");
			sqlParams.put("adKeywordPvclkSeq", "%" + adKeywordPvclkSeq.trim() + "%");
		}

		if (StringUtils.isNotEmpty(adKeywordSeq)) {
			sql.append(" and adKeywordSeq like :adKeywordSeq");
			sqlParams.put("adKeywordSeq", "%" + adKeywordSeq.trim() + "%");
		}

		if (StringUtils.isNotEmpty(adKeywordPvclkDate)) {
			sql.append(" and adKeywordPvclkDate like :adKeywordPvclkDate");
			sqlParams.put("adKeywordPvclkDate", "%" + adKeywordPvclkDate.trim() + "%");
		}

		if (StringUtils.isNotEmpty(adKeywordPv)) {
			sql.append(" and adKeywordPv like :adKeywordPv");
			sqlParams.put("adKeywordPv", "%" + adKeywordPv.trim() + "%");
		}

		if (StringUtils.isNotEmpty(adKeywordClk)) {
			sql.append(" and adKeywordClk like :adKeywordClk");
			sqlParams.put("adKeywordClk", "%" + adKeywordClk.trim() + "%");
		}

		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(sql.toString());
        for (String paramName:sqlParams.keySet()) {
            query.setParameter(paramName, sqlParams.get(paramName));
        }
		
        List<PfpAdKeywordPvclk> pfpAdKeywordPvclks = query.list();
		
		return pfpAdKeywordPvclks;
	}

	@SuppressWarnings("unchecked")
	public PfpAdKeywordPvclk getPfpAdKeywordPvclkBySeq(String adKeywordPvclkSeq) throws Exception {
		List<PfpAdKeywordPvclk> list = (List<PfpAdKeywordPvclk>) super.getHibernateTemplate().find("from PfpAdKeywordPvclk where adKeywordPvclkSeq = ?", adKeywordPvclkSeq);
		if (list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	public void saveOrUpdatePfpAdKeywordPvclk(PfpAdKeywordPvclk pfpAdKeywordPvclk) throws Exception{
		super.getHibernateTemplate().saveOrUpdate(pfpAdKeywordPvclk);
	}

	public void insertPfpAdKeywordPvclk(PfpAdKeywordPvclk pfpAdKeywordPvclk) throws Exception {
		this.saveOrUpdate(pfpAdKeywordPvclk);
	}

	public void updatePfpAdKeywordPvclk(PfpAdKeywordPvclk pfpAdKeywordPvclk) throws Exception {
		this.update(pfpAdKeywordPvclk);
	}
	
	public void deletePfpAdKeywordPvclk(String adKeywordPvclkSeq) throws Exception{
		String userSql = "delete from PfpAdKeywordPvclk where adKeywordPvclkSeq = :adKeywordPvclkSeq";		
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.createQuery(userSql).setString("adKeywordPvclkSeq", adKeywordPvclkSeq).executeUpdate();
		session.flush();
	}
}
