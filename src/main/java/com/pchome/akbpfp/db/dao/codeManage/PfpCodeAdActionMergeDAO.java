package com.pchome.akbpfp.db.dao.codeManage;

import java.util.List;

import org.hibernate.Query;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCodeAdactionMerge;

public class PfpCodeAdActionMergeDAO extends BaseDAO<PfpCodeAdactionMerge,Integer> implements IPfpCodeAdActionMergeDAO{

	public List<PfpCodeAdactionMerge> findProdCodeByAdactionSeq(String adActionSeq) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCodeAdactionMerge ");
		hql.append(" where 1 = 1  ");
		hql.append(" and adActionSeq = :adActionSeq  ");
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setString("adActionSeq", adActionSeq);
		return query.list();
	}

	public int deleteProdCodeByCodeType(String codeType ,String adActionSeq) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append(" delete PfpCodeAdactionMerge where 1 = 1 ");
		hql.append(" and codeType = :codeType  ");
		hql.append(" and adActionSeq = :adActionSeq  ");
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setString("codeType", codeType);
		query.setString("adActionSeq", adActionSeq);
		return query.executeUpdate();
	}
}
