package com.pchome.akbpfp.db.dao.ad;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdGift;

public class PfpAdGiftDAO extends BaseDAO<PfpAdGift,String> implements IPfpAdGiftDAO{
	
//	@SuppressWarnings("unchecked")
//	public List<PfpAdGift> getPfpAdGifts(String adGiftId, String adGiftName, String adGiftSno, String adGiftStatus, String adGiftEndDate) throws Exception{
//		StringBuffer sql = new StringBuffer("from PfpAdGift where 1=1");
//		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
//		if (StringUtils.isNotBlank(adGiftId)) {
//			sql.append(" and adGiftId = :adGiftId");
//			sqlParams.put("adGiftId", adGiftId.trim());
//		}
//
//		if (StringUtils.isNotBlank(adGiftName)) {
//			sql.append(" and adGiftName like :adGiftName");
//			sqlParams.put("adGiftName", "%" + adGiftName.trim() + "%");
//		}
//
//		if (StringUtils.isNotBlank(adGiftSno)) {
//			sql.append(" and adGiftSno = :adGiftSno");
//			sqlParams.put("adGiftSno", adGiftSno.trim());
//		}
//
//		if (StringUtils.isNotBlank(adGiftStatus)) {
//			sql.append(" and adGiftStatus = :adGiftStatus");
//			sqlParams.put("adGiftStatus", adGiftStatus.trim());
//		}
//
//		if (StringUtils.isNotBlank(adGiftEndDate)) {
//			sql.append(" and adGiftEndDate = :adGiftEndDate");
//			sqlParams.put("adGiftEndDate", adGiftEndDate.trim());
//		}
//		log.info("getPfpAdGifts.SQL = " + sql);
//
//		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(sql.toString());
//        for (String paramName:sqlParams.keySet()) {
//            query.setParameter(paramName, sqlParams.get(paramName));
//        }
//		
//        List<PfpAdGift> pfpAdGifts = query.list();
//		
//		return pfpAdGifts;
//	}
//
//	@SuppressWarnings("unchecked")
//	public String chkAdGiftSnoExist(String adGiftSno) throws Exception {
//		System.out.println("adGiftSno = " + adGiftSno);
//		String existStatus = "noExist";
//		Date today = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		if(StringUtils.isNotBlank(adGiftSno)) {
//			String hql = "from PfpAdGift where adGiftSno = ? and adGiftEndDate >= ?";
//			//log.info("chkAdGroupNameByAdActionSeq.hql = " + hql);
//	
//			List<PfpAdGift> list = super.getHibernateTemplate().find(hql, adGiftSno, today);
//
//			if (list != null && list.size() > 0) {
//				for(PfpAdGift pfpAdGift:list){
//					if(StringUtils.isBlank(pfpAdGift.getCustomerInfoId()) && StringUtils.isBlank(pfpAdGift.getOrderId())) {
//						if(pfpAdGift.getAdGiftSno().indexOf("GQQCG14031") >= 0) {
//							existStatus = "exist;" + ((int)EnumActivityType.valueOf("GIFT2014036000").getCondition()) + ";" + pfpAdGift.getAdGiftName();
//						} else {
//							String type = pfpAdGift.getAdGiftSno().substring(0,2);
//							existStatus = "exist;" + ((int)EnumActivityType.valueOf(type).getCondition()) + ";" + pfpAdGift.getAdGiftName();
//						}
//					} else {
//						existStatus = "used";
//					}
//				}
//			} else {
//				existStatus = "noExist";
//			}
//		} else {
//			existStatus = "noAdGiftSno";
//		}
//		return existStatus;
//	}
//
//	@SuppressWarnings("unchecked")
//	public PfpAdGift getPfpAdGiftBySno(String adGiftSno) throws Exception {
//		Object[] obj = new Object[]{adGiftSno};
//		List<PfpAdGift> list = super.getHibernateTemplate().find("from PfpAdGift where adGiftSno = ?", obj);
//		if (list!=null && list.size()>0) {
//			return list.get(0);
//		} else {
//			return null;
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	public PfpAdGift getPfpAdGiftByOrderId(String orderId) throws Exception {
//		Object[] obj = new Object[]{orderId};
//		List<PfpAdGift> list = super.getHibernateTemplate().find("from PfpAdGift where orderId = ?", obj);
//		if (list!=null && list.size()>0) {
//			return list.get(0);
//		} else {
//			return null;
//		}
//	}
//
//	@SuppressWarnings("unchecked")
//	public PfpAdGift getPfpAdGiftByCustomer(String customerInfoId) throws Exception {
//		Object[] obj = new Object[]{customerInfoId};
//		List<PfpAdGift> list = super.getHibernateTemplate().find("from PfpAdGift where adGiftStatus <> 4 and customerInfoId = ?", obj);
//		if (list!=null && list.size()>0) {
//			return list.get(0);
//		} else {
//			return null;
//		}
//	}
//	
//	@SuppressWarnings("unchecked")
//	public List<PfpAdGift> getAdGifts() throws Exception{
//		StringBuffer hql = new StringBuffer();
//		hql.append(" from PfpAdGift ");
//		hql.append(" where adGiftStatus not in (8,9,10)");
//		hql.append(" and adGiftEndDate >= CURDATE()");		// 走期的結束日期要大於今天
//		
//		return super.getHibernateTemplate().find(hql.toString());
//	}
//	
//	public void saveOrUpdatePfpAdGift(PfpAdGift pfpAdGift) throws Exception{
//		super.getHibernateTemplate().saveOrUpdate(pfpAdGift);
//	}
//
//	public void insertPfpAdGift(PfpAdGift pfpAdGift) throws Exception {
//		this.saveOrUpdate(pfpAdGift);
//	}
//
//	public void insertPfpAdGift(List<PfpAdGift> dataList) throws Exception {
//		for (int i=0; i<dataList.size(); i++) {
//			this.save(dataList.get(i));
//		}
//	}
//
//	public void updatePfpAdGift(PfpAdGift pfpAdGift) throws Exception {
//		this.update(pfpAdGift);
//	}
//
//	public void updatePfpAdGiftStatus(String adGiftStatus, String adGroupSno) throws Exception {
//		final StringBuffer sql = new StringBuffer()
//		.append("UPDATE pfp_ad_gift set ad_gift_status = :adGiftStatus where ad_gift_sno = :adGroupSno");
//		//log.info("updatePfpAdGiftStatus.sql = " + sql);
//
//        Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
//        session.createSQLQuery(sql.toString()).setString("adGiftStatus", adGiftStatus).setString("adGroupSno", adGroupSno).executeUpdate();
//        session.flush();
//	}
//	
//	public void saveOrUpdateWithCommit(PfpAdGift pfpAdGift) throws Exception{
//		super.getHibernateTemplate().getSessionFactory().getCurrentSession().saveOrUpdate(pfpAdGift);
//		super.getHibernateTemplate().getSessionFactory().getCurrentSession().beginTransaction().commit();
//	}
}
