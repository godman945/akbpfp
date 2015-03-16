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
		
		return super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public List<AdmFreeGift> findUnusedAdmFreeGiftSno(String sno, Date today) {
		
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		hql.append(" from AdmFreeGift ");
		hql.append(" where customerInfoId = null ");
		hql.append(" and openDate = null ");
		hql.append(" and giftSno = ? ");
		hql.append(" and giftSnoStatus  = ? ");
		hql.append(" and admFreeAction.actionEndDate >= ? ");
		hql.append(" and admFreeAction.actionStartDate <= ? ");
		
		list.add(sno);
		list.add(EnumGiftSnoUsed.NO.getStatus());
		list.add(today);
		list.add(today);
		
		return super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}
}
