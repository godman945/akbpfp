package com.pchome.akbpfp.db.dao.pfd.user;

import java.util.ArrayList;
import java.util.List;




import org.hibernate.Query;
import org.hibernate.Session;

import com.pchome.akbpfd.db.vo.user.PfdUserAdAccountRefVO;
import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfdUserAdAccountRef;


public class PfdUserAdAccountRefDAO extends BaseDAO <PfdUserAdAccountRef, String> implements IPfdUserAdAccountRefDAO{

	
	@SuppressWarnings("unchecked")
	public List<PfdUserAdAccountRef> checkPfdAndPfpRef(String pfdCustomerInfoId, String pfpCustomerInfoId) {
		
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		hql.append(" from PfdUserAdAccountRef ");
		hql.append(" where pfdCustomerInfo.customerInfoId = ? ");
		hql.append(" and pfpCustomerInfo.customerInfoId = ? ");
		hql.append(" and pfdCustomerInfo.activateDate != null ");
		hql.append(" and pfpCustomerInfo.activateDate != null ");
		
		list.add(pfdCustomerInfoId);
		list.add(pfpCustomerInfoId);
		
		return super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public List<PfdUserAdAccountRef> findPfdUserAdAccountRef(String pfpCustomerInfoId) {
		
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		hql.append(" from PfdUserAdAccountRef ");
		hql.append(" where pfpCustomerInfo.customerInfoId = ? ");
		hql.append(" order by refId desc ");
		
		list.add(pfpCustomerInfoId);		
		
		return super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}
	
	public void saveOrUpdatePfdUserAdAccountRef(PfdUserAdAccountRefVO pfdUserAdAccountRefVO){
		final StringBuffer sql = new StringBuffer()
		.append("INSERT INTO pfd_user_ad_account_ref(ref_id,pfd_customer_info_id,pfd_user_id,pfp_customer_info_id,pfp_user_id,pfp_pay_type) ")
		.append("VALUES ( :refId")
		.append(", :pfdCustomerInfoId")
		.append(", :pfdUserId")
		.append(", :pfpCustomerInfoId")
		.append(", :pfpUserId")
		.append(", :pfpPayType)");
		
		Session session = getSession();
        session.createSQLQuery(sql.toString())
        		.setInteger("refId", pfdUserAdAccountRefVO.getRefId())
        		.setString("pfdCustomerInfoId", pfdUserAdAccountRefVO.getPfdCustomerInfoId())
        		.setString("pfdUserId", pfdUserAdAccountRefVO.getPfdUserId())
        		.setString("pfpCustomerInfoId", pfdUserAdAccountRefVO.getPfpCustomerInfoId())
        		.setString("pfpUserId", pfdUserAdAccountRefVO.getPfpUserId())
        		.setString("pfpPayType", pfdUserAdAccountRefVO.getPfpPayType())
        		.executeUpdate();
        session.flush();
	}
	
	public Integer getNewRefId(){
		final StringBuffer hql = new StringBuffer()
		.append("select max(refId) + 1 ")
		.append(" from PfdUserAdAccountRef ");
		
		Query q = getSession().createQuery(hql.toString());
		
		Integer reId = 0;
		List<Object> resultData = q.list();
		if(resultData != null) {
			reId = Integer.parseInt(resultData.get(0).toString());
		}
		return reId;
	}
}
