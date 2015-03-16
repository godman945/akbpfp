package com.pchome.akbpfp.db.dao.ad;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.AdmDefineAd;

public class AdmDefineAdDAO extends BaseDAO<AdmDefineAd, String> implements IAdmDefineAdDAO {

	@SuppressWarnings("unchecked")
	public List<AdmDefineAd> getDefineAdByCondition(String defineAdSeq, String defineAdId, String defineAdName, String adPoolSeq) throws Exception {
		StringBuffer sql = new StringBuffer("from AdmDefineAd where 1=1");
		List<String> sqlParam = new ArrayList<String>(); 
		if (StringUtils.isNotEmpty(defineAdSeq)) {
			sql.append(" and defineAdSeq like ?");
			sqlParam.add("%" + defineAdSeq.trim() + "%");
		}

		if (StringUtils.isNotEmpty(defineAdId)) {
			sql.append(" and defineAdId like ?");
			sqlParam.add("%" + defineAdId.trim() + "%");
		}

		if (StringUtils.isNotEmpty(defineAdName)) {
			sql.append(" and defineAdName like ?");
			sqlParam.add("%" + defineAdName.trim() + "%");
		}

		if (StringUtils.isNotEmpty(adPoolSeq)) {
			sql.append(" and adPoolSeq like ?");
			sqlParam.add("%" + adPoolSeq.trim() + "%");
		}

        Query query = this.getSession().createQuery(sql.toString());
        for (int i = 0; i < sqlParam.size(); i++) {
            query.setParameter(i, sqlParam.get(i));
        }
        return query.list();
	}

	@SuppressWarnings("unchecked")
	public AdmDefineAd getDefineAdById(String defineAdId) throws Exception {
		//List<AdmDefineAd> list = super.getHibernateTemplate().find("from AdmDefineAd where defineAdId = '" + defineAdId + "'");
		List<AdmDefineAd> list = super.getHibernateTemplate().find("from AdmDefineAd where defineAdId = ?", defineAdId);
		if (list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public AdmDefineAd getDefineAdBySeq(String defineAdSeq) throws Exception {
		//List<AdmDefineAd> list = super.getHibernateTemplate().find("from AdmDefineAd where defineAdSeq = '" + defineAdSeq + "'");
		List<AdmDefineAd> list = super.getHibernateTemplate().find("from AdmDefineAd where defineAdSeq = ?", defineAdSeq);
		if (list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public AdmDefineAd getDefineAdByPoolSeq(String adPoolSeq) throws Exception {
		//List<AdmDefineAd> list = super.getHibernateTemplate().find("from AdmDefineAd where adPoolSeq = '" + adPoolSeq + "'");
		List<AdmDefineAd> list = super.getHibernateTemplate().find("from AdmDefineAd where adPoolSeq = ?", adPoolSeq);
		if (list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
