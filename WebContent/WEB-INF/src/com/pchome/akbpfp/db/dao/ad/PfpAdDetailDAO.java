package com.pchome.akbpfp.db.dao.ad;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.akbpfp.db.vo.ad.PfpAdDetailVO;


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

		Query query = super.getSession().createQuery(sql.toString());
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

		Query query = super.getSession().createQuery(sql.toString());
        for (String paramName:sqlParams.keySet()) {
        	if(!paramName.equals("sql")) {
        		query.setParameter(paramName, sqlParams.get(paramName));
        	}
        }
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public PfpAdDetail getPfpAdDetailBySeq(String adDetailSeq) throws Exception {
		List<PfpAdDetail> list = super.getHibernateTemplate().find("from PfpAdDetail where adDetailSeq = ?", adDetailSeq);
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
		.append("INSERT INTO pfp_ad_detail(ad_detail_seq, ad_seq, ad_detail_id, ad_detail_content, ad_pool_seq, define_ad_seq, ad_detail_create_time, ad_detail_update_time) ")
		.append("VALUES ( :adDetailSeq")
		.append(", :adSeq")
		.append(", :adDetailId")
		.append(", :adDetailContent")
		.append(", :adPoolSeq")
		.append(", :defineAdSeq")
		.append(", :adDetailCreateTime")
		.append(", :adDetailUpdateTime)");
		//log.info(sql);

        Session session = getSession();
        session.createSQLQuery(sql.toString())
        		.setString("adDetailSeq", pfpAdDetailVO.getAdDetailSeq())
        		.setString("adSeq", pfpAdDetailVO.getAdSeq())
        		.setString("adDetailId", pfpAdDetailVO.getAdDetailId())
        		.setString("adDetailContent", pfpAdDetailVO.getAdDetailContent())
        		.setString("adPoolSeq", pfpAdDetailVO.getAdPoolSeq())
        		.setString("defineAdSeq", pfpAdDetailVO.getDefineAdSeq())
        		.setDate("adDetailCreateTime", pfpAdDetailVO.getAdDetailCreateTime())
        		.setDate("adDetailUpdateTime", pfpAdDetailVO.getAdDetailUpdateTime())
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
		Session session = getSession();
		session.createQuery(userSql).setString("adDetailSeq", adDetailSeq).executeUpdate();
		session.flush();
	}
}
