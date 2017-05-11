package com.pchome.akbpfp.db.dao.ad;

import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfbxWebsiteCategory;


public class PfbxWebsiteCategoryDAO extends BaseDAO<PfbxWebsiteCategory,String> implements IPfbxWebsiteCategoryDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<PfbxWebsiteCategory> getAllOrderByCode() {
		String sql = "from PfbxWebsiteCategory order by code ";
		return super.getHibernateTemplate().find(sql);
	}

}
