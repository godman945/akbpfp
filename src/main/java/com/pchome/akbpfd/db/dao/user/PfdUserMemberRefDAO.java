package com.pchome.akbpfd.db.dao.user;

import org.hibernate.Query;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfdUserMemberRef;

public class PfdUserMemberRefDAO extends BaseDAO<PfdUserMemberRef,String> implements IPfdUserMemberRefDAO{

    public PfdUserMemberRef getUserMemberRef(String memberId) {
	log.info("find PFD memberId:"+memberId);
	StringBuffer hql = new StringBuffer();
	hql.append(" from PfdUserMemberRef ");
	hql.append(" where id.memberId = '"+memberId+"'");
	Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql.toString());
	return query.list().size() > 0 ? (PfdUserMemberRef) query.list().get(0) : null;
    }
}
