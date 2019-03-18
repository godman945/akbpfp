package com.pchome.akbpfp.db.dao.ad;

import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdSpecificWebsite;

public class PfpAdSpecificWebsiteDAO extends BaseDAO<PfpAdSpecificWebsite,String> implements IPfpAdSpecificWebsiteDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<PfpAdSpecificWebsite> findPfpAdSpecificWebsiteByAdActionSeq(String adActionSeq) throws Exception{
		String sql = "from PfpAdSpecificWebsite where pfpAdAction.adActionSeq = ? ";
		return super.getHibernateTemplate().find(sql, adActionSeq);
	}
	
	
}
