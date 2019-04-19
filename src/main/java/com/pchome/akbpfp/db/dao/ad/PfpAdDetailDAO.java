package com.pchome.akbpfp.db.dao.ad;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.akbpfp.db.vo.ad.PfpAdDetailVO;
import com.pchome.enumerate.utils.EnumStatus;


public class PfpAdDetailDAO extends BaseDAO<PfpAdDetail,String> implements IPfpAdDetailDAO{
	
	@SuppressWarnings("unchecked")
	public List<PfpAdDetail> getPfpAdDetails(String adDetailSeq, String adSeq, String adPoolSeq, String defineAdSeq) throws Exception{
		StringBuffer sql = new StringBuffer("from PfpAdDetail where 1=1");
		HashMap<String, Object> sqlParams = new HashMap<String, Object>(); 
		if (StringUtils.isNotEmpty(adDetailSeq)) {
			sql.append(" and adDetailSeq = :adDetailSeq");
			sqlParams.put("adDetailSeq", adDetailSeq.trim());
		}

		if (StringUtils.isNotEmpty(adSeq)) {
			sql.append(" and pfpAd.adSeq = :adSeq");
			sqlParams.put("adSeq", adSeq.trim());
		}

		if (StringUtils.isNotEmpty(adPoolSeq)) {
			sql.append(" and adPoolSeq = :adPoolSeq");
			sqlParams.put("adPoolSeq", adPoolSeq.trim());
		}

		if (StringUtils.isNotEmpty(defineAdSeq)) {
			sql.append(" and defineAdSeq = :defineAdSeq");
			sqlParams.put("defineAdSeq", defineAdSeq.trim());
		}

		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(sql.toString());
        for (String paramName:sqlParams.keySet()) {
        	if(!paramName.equals("sql")) {
        		query.setParameter(paramName, sqlParams.get(paramName));
        	}
        }
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<PfpAdDetail> getPfpAdDetailByAdSeq(String adSeq) throws Exception {
		StringBuffer sql = new StringBuffer("from PfpAdDetail where 1=1");
		HashMap<String, Object> sqlParams = new HashMap<String, Object>(); 
		if (StringUtils.isNotEmpty(adSeq)) {
			sql.append(" and pfpAd.adSeq = :adSeq");
			sqlParams.put("adSeq", adSeq.trim());
		}
		//log.info("getPfpAdDetailByAdSeq.sql = " + sql.toString());

		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(sql.toString());
        for (String paramName:sqlParams.keySet()) {
        	if(!paramName.equals("sql")) {
        		query.setParameter(paramName, sqlParams.get(paramName));
        	}
        }
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public PfpAdDetail getPfpAdDetailBySeq(String adDetailSeq) throws Exception {
		List<PfpAdDetail> list = (List<PfpAdDetail>) super.getHibernateTemplate().find("from PfpAdDetail where adDetailSeq = ?", adDetailSeq);
		if (list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public void saveOrUpdatePfpAdDetail(PfpAdDetail pfpAdDetail) throws Exception{
		super.getHibernateTemplate().saveOrUpdate(pfpAdDetail);
	}
	
	public void saveOrUpdatePfpAdDetail(PfpAdDetailVO pfpAdDetailVO) throws Exception{
		final StringBuffer sql = new StringBuffer()
		.append("INSERT INTO pfp_ad_detail(ad_detail_seq, ad_seq, ad_detail_id, ad_detail_content, ad_pool_seq, define_ad_seq, ad_detail_create_time, ad_detail_update_time,verify_flag) ")
		.append("VALUES ( :adDetailSeq")
		.append(", :adSeq")
		.append(", :adDetailId")
		.append(", :adDetailContent")
		.append(", :adPoolSeq")
		.append(", :defineAdSeq")
		.append(", :adDetailCreateTime")
		.append(", :adDetailUpdateTime")
		.append(", :verifyFlag)");
		//log.info(sql);

        Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
        session.createSQLQuery(sql.toString())
        		.setString("adDetailSeq", pfpAdDetailVO.getAdDetailSeq())
        		.setString("adSeq", pfpAdDetailVO.getAdSeq())
        		.setString("adDetailId", pfpAdDetailVO.getAdDetailId())
        		.setString("adDetailContent", pfpAdDetailVO.getAdDetailContent())
        		.setString("adPoolSeq", pfpAdDetailVO.getAdPoolSeq())
        		.setString("defineAdSeq", pfpAdDetailVO.getDefineAdSeq())
        		.setDate("adDetailCreateTime", pfpAdDetailVO.getAdDetailCreateTime())
        		.setDate("adDetailUpdateTime", pfpAdDetailVO.getAdDetailUpdateTime())
        		.setString("verifyFlag", pfpAdDetailVO.getVerifyFlag())
        		.executeUpdate();
        session.flush();
	}

	public void insertPfpAdDetail(PfpAdDetail pfpAdDetail) throws Exception {
		this.saveOrUpdate(pfpAdDetail);
	}

	public void updatePfpAdDetail(PfpAdDetail pfpAdDetail) throws Exception {
		this.update(pfpAdDetail);
	}
	
	public void deletePfpAdDetail(String adDetailSeq) throws Exception{
		String userSql = "delete from PfpAdDetail where adDetailSeq = :adDetailSeq";		
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.createQuery(userSql).setString("adDetailSeq", adDetailSeq).executeUpdate();
		session.flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpAdDetail> getPfpAdDetailsForAdGroup(String customerInfoId, String adGroupSeq, String adDetailId, String adDetailContent)throws Exception {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("from PfpAdDetail where 1=1");
		sql.append(" and pfpAd.adStatus != :status");
		sql.append(" and pfpAd.pfpAdGroup.adGroupSeq = :adGroupSeq ");
		if (StringUtils.isNotEmpty(customerInfoId)) {
			sql.append(" and pfpAd.pfpAdGroup.pfpAdAction.pfpCustomerInfo.customerInfoId = :customerInfoId");
			sqlParams.put("customerInfoId", customerInfoId);
		}
		if(StringUtils.isNotBlank(adDetailId)) {
			sql.append(" and adDetailId = :adDetailId");
			sqlParams.put("adDetailId", adDetailId);
		}
		
		if(StringUtils.isNotBlank(adDetailContent)) {
			sql.append(" and adDetailContent like :adDetailContent");
			sqlParams.put("adDetailContent", adDetailContent);
		}
		
		// 將條件資料設定給 Query，準備 query
		Query q = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(sql.toString())
								.setString("adGroupSeq", adGroupSeq)
								.setInteger("status", EnumStatus.Close.getStatusId());
		for (String paramName:sqlParams.keySet()) {
        	if(!paramName.equals("sql")) {
				q.setParameter(paramName, sqlParams.get(paramName));
        	}
        }
		List<PfpAdDetail> pfpAdDetails = q.list();
		return pfpAdDetails;
	}
	
	
	public String checkCatalogGroupAdStatusCount(String catalogGroupSeq, String prodGroup ) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(*) ");
		hql.append(" from pfp_ad_detail ");
		hql.append(" where 1=1 ");
		hql.append(" and ad_detail_content = '"+catalogGroupSeq+"' ");
		hql.append(" and ad_detail_id = '"+prodGroup+"' ");
		
		log.info(hql.toString());

		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		String prodListCount = String.valueOf(query.list().get(0));
		
		return prodListCount;
		
	}
	
}
