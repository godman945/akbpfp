package com.pchome.akbpfp.db.dao.user;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;
import com.pchome.config.TestConfig;
import com.pchome.enumerate.account.EnumAccountStatus;
import com.pchome.enumerate.user.EnumUserStatus;

public class PfpUserMemberRefDAO extends BaseDAO<PfpUserMemberRef,String> implements IPfpUserMemberRefDAO{
	
	@SuppressWarnings("unchecked")	
	public List<PfpUserMemberRef> activateUserMemberRef(String memberId) {
		
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		hql.append(" from PfpUserMemberRef ");
		hql.append(" where id.memberId = ? ");
		hql.append(" and (pfpUser.status = ? ");
		hql.append(" 		or pfpUser.status = ? ");
		hql.append(" 		or pfpUser.status = ? ) ");
		hql.append(" and pfpUser.pfpCustomerInfo.status != ? ");
		hql.append(" order by id.userId desc ");
		
		list.add(memberId);
		list.add(EnumUserStatus.CLOSE.getStatusId());
		list.add(EnumUserStatus.START.getStatusId());
		list.add(EnumUserStatus.STOP.getStatusId());
		list.add(EnumAccountStatus.DELETE.getStatus());
		
		return super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}

	@SuppressWarnings("unchecked")	
	public List<PfpUserMemberRef> activateUserMemberRef2(String memberId) throws Exception {
		
		StringBuffer hql = new StringBuffer();
		
		hql.append(" from PfpUserMemberRef ");
		hql.append(" where id.memberId = :memberId ");
		hql.append(" and pfpUser.status in (:pfpUserStatus) ");
		hql.append(" and pfpUser.pfpCustomerInfo.status in (:pfpCustInfoStatus) ");

		Session session = getSession();
		Query q = session.createQuery(hql.toString());
		q.setParameter("memberId", memberId);
		q.setParameterList("pfpUserStatus", new String[]{EnumUserStatus.CLOSE.getStatusId(),
				EnumUserStatus.START.getStatusId(),
				EnumUserStatus.STOP.getStatusId()});
		q.setParameterList("pfpCustInfoStatus", new String[]{EnumAccountStatus.CLOSE.getStatus(),
				EnumAccountStatus.START.getStatus()});
		
		return q.list();
	}

	@SuppressWarnings("unchecked")
	public List<PfpUserMemberRef> validUserMemberRef(String memberId) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		
		hql.append(" from PfpUserMemberRef where id.memberId = ? ");
		hql.append(" and pfpUser.status != ? ");
		hql.append(" and pfpUser.pfpCustomerInfo.status != ? ");
		hql.append(" order by id.userId desc ");
		
		Object[] ob = new Object[]{memberId, 
		EnumUserStatus.DELETE.getStatusId(),
		EnumAccountStatus.DELETE.getStatus()};
		
		return super.getHibernateTemplate().find(hql.toString(), ob);		 
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpUserMemberRef> applyUserMemberRef(String memberId) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpUserMemberRef where id.memberId = ? ");
		hql.append(" and pfpUser.pfpCustomerInfo.status = ? ");
		hql.append(" order by id.userId desc ");
		
		Object[] ob = new Object[]{memberId, EnumAccountStatus.APPLY.getStatus()};
		log.info(hql.toString());
		return super.getHibernateTemplate().find(hql.toString(), ob);	
	}
	
//	public void deleteUserMemberRef(String memberId) throws Exception{
//		
//		StringBuffer hql = new StringBuffer();
//		
//		hql.append(" delete from PfpUserMemberRef ");
//		hql.append(" where id.memberId = ? ");
//		
//		this.getSession().createQuery(hql.toString()).
//							setString(0, memberId)
//							.executeUpdate();
//	}
	
//	@SuppressWarnings("unchecked")
//	public List<PfpUserMemberRef> checkAccountUserStauts(String memberId) throws Exception{
//		
//		StringBuffer hql = new StringBuffer();
//		
//		hql.append(" from PfpUserMemberRef where id.memberId = ? ");
//		hql.append(" and pfpUser.status != ? ");
//		hql.append(" and pfpUser.status != ? ");
//		hql.append(" and pfpUser.status != ?  ");
//		hql.append(" order by pfpUser.createDate desc ");
//		
//		Object[] ob = new Object[]{memberId, 
//									EnumUserStatus.DELETE.getStatusId(),
//									EnumUserStatus.INVITE_NOT_PCID.getStatusId(),
//									EnumUserStatus.INVITE_PCID.getStatusId()};
//
//		return super.getHibernateTemplate().find(hql.toString(), ob);
//	}
	
	public static void main(String arg[]) throws Exception{

		ApplicationContext context = new FileSystemXmlApplicationContext(TestConfig.path);

		//Logger log = Logger.getLogger(CustomerInfoService.class);

		//PfpUserMemberRefDAO dao = (PfpUserMemberRefDAO) context.getBean("PfpUserMemberRefDAO");
		
		//log.info(" dao size   "+dao.findPfpUserMemberRef("reantoilpc").size());
	}
}
