package com.pchome.akbpfp.db.dao.freeAction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.AdmFreeGift;
import com.pchome.enumerate.freeAction.EnumGiftSnoUsed;

public class AdmFreeGiftDAO extends BaseDAO<AdmFreeGift, Integer> implements IAdmFreeGiftDAO{

	@SuppressWarnings("unchecked")
	public List<AdmFreeGift> findAdmFreeGiftSno(String sno) {
		
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		hql.append(" from AdmFreeGift ");
		hql.append(" where giftSno = ? ");
			
		list.add(sno);
		
		return (List<AdmFreeGift>) super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmFreeGift> findUnusedAdmFreeGiftSno(String sno, Date today, String snoStyle) {
		
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		hql.append(" from AdmFreeGift ");
		hql.append(" where 1=1 ");
		/*hql.append(" where customerInfoId = null ");
		hql.append(" and openDate = null ");*/
		hql.append(" and giftSno = ? ");
		hql.append(" and giftSnoStatus  = ? ");
		hql.append(" and admFreeAction.actionEndDate >= ? ");
		hql.append(" and admFreeAction.actionStartDate <= ? ");
		hql.append(" and admFreeAction.giftStyle = ? ");
		
		list.add(sno);
		list.add(EnumGiftSnoUsed.NO.getStatus());
		list.add(today);
		list.add(today);
		list.add(snoStyle);
		
		return (List<AdmFreeGift>) super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmFreeGift> findAdmFreeGiftSnoByOrderId(String orderId) {
		
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		hql.append(" from AdmFreeGift ");
		hql.append(" where orderId = ? ");
			
		list.add(orderId);
		
		return (List<AdmFreeGift>) super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmFreeGift> findUsedHistory(String actionId, String customerInfoId){
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		hql.append(" from AdmFreeGift ");
		hql.append(" where openDate != null ");
		hql.append(" and admFreeAction.actionId = ? ");
		hql.append(" and customerInfoId = ? ");
		hql.append(" and giftSnoStatus = ? ");
			
		list.add(actionId);
		list.add(customerInfoId);
		list.add(EnumGiftSnoUsed.YES.getStatus());
		
		return (List<AdmFreeGift>) super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmFreeGift> findAdmFreeGiftBySno(String giftSno) {
		
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		hql.append(" from AdmFreeGift ");
		hql.append(" where giftSno = ? ");
			
		list.add(giftSno);
		
		return (List<AdmFreeGift>) super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}
}
