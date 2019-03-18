package com.pchome.akbpfp.db.dao.ad;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdExcludeKeyword;
import com.pchome.enumerate.ad.EnumExcludeKeywordStatus;


public class PfpAdExcludeKeywordDAO extends BaseDAO<PfpAdExcludeKeyword,String> implements IPfpAdExcludeKeywordDAO{
	
	@SuppressWarnings("unchecked")
	public List<PfpAdExcludeKeyword> getPfpAdExcludeKeywords(String adExcludeKeywordSeq, String adGroupSeq, String adExcludeKeyword) throws Exception{
		StringBuffer sql = new StringBuffer("from PfpAdExcludeKeyword where 1=1");
		List<String> sqlParam = new ArrayList<String>(); 
		if (StringUtils.isNotEmpty(adExcludeKeywordSeq)) {
			sql.append(" and adExcludeKeywordSeq like ?");
			sqlParam.add("%" + adExcludeKeywordSeq.trim() + "%");
		}

		if (StringUtils.isNotEmpty(adGroupSeq)) {
			sql.append(" and adGroupSeq like ?");
			sqlParam.add("%" + adGroupSeq.trim() + "%");
		}

		if (StringUtils.isNotEmpty(adExcludeKeyword)) {
			sql.append(" and adExcludeKeyword like ?");
			sqlParam.add("%" + adExcludeKeyword.trim() + "%");
		}

        Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(sql.toString());
        for (int i = 0; i < sqlParam.size(); i++) {
            query.setParameter(i, sqlParam.get(i));
        }
        return query.list();
	}

	@SuppressWarnings("unchecked")
	public PfpAdExcludeKeyword getPfpAdExcludeKeywordBySeq(String adExcludeKeywordSeq) throws Exception {
		List<PfpAdExcludeKeyword> list = (List<PfpAdExcludeKeyword>) super.getHibernateTemplate().find("from PfpAdExcludeKeyword where adExcludeKeywordSeq = ?", adExcludeKeywordSeq);
		if (list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	public void saveOrUpdatePfpAdExcludeKeyword(PfpAdExcludeKeyword pfpAdExcludeKeyword) throws Exception{
		super.getHibernateTemplate().saveOrUpdate(pfpAdExcludeKeyword);
	}

	public void insertPfpAdExcludeKeyword(PfpAdExcludeKeyword pfpAdExcludeKeyword) throws Exception {
		this.saveOrUpdate(pfpAdExcludeKeyword);
	}

	public void updatePfpAdExcludeKeyword(PfpAdExcludeKeyword pfpAdExcludeKeyword) throws Exception {
		this.update(pfpAdExcludeKeyword);
	}
	
	public void deletePfpAdExcludeKeyword(String adExcludeKeywordSeq) throws Exception{
		String userSql = "delete from PfpAdExcludeKeyword where adExcludeKeywordSeq = :adExcludeKeywordSeq";		
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.createQuery(userSql).setString("adExcludeKeywordSeq", adExcludeKeywordSeq).executeUpdate();
		session.flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpAdExcludeKeyword> getPfpAdExcludeKeywords(String adGroupSeq, String customerInfoId) throws Exception{
		StringBuffer hql = new StringBuffer();
		
		hql.append(" from PfpAdExcludeKeyword  ");
		hql.append(" where pfpAdGroup.adGroupSeq = ? ");
		hql.append(" and adExcludeKeywordStatus != ?  ");	
		hql.append(" and pfpAdGroup.pfpAdAction.pfpCustomerInfo.customerInfoId = ? ");
		
		return (List<PfpAdExcludeKeyword>) super.getHibernateTemplate().find(hql.toString(), adGroupSeq, EnumExcludeKeywordStatus.CLOSE.getStatusId(), customerInfoId);
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpAdExcludeKeyword> findAdExcludeKeywords(String adGroupSeq) {
		
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		hql.append(" from PfpAdExcludeKeyword  ");
		hql.append(" where pfpAdGroup.adGroupSeq = ? ");
		hql.append(" and adExcludeKeywordStatus != ?  ");	
		
		
		list.add(adGroupSeq);
		list.add(EnumExcludeKeywordStatus.CLOSE.getStatusId());
		
		return (List<PfpAdExcludeKeyword>) super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}
}
