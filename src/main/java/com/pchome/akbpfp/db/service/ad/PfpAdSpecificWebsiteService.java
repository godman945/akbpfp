package com.pchome.akbpfp.db.service.ad;

import java.util.List;

import com.pchome.akbpfp.db.dao.ad.IPfpAdSpecificWebsiteDAO;
import com.pchome.akbpfp.db.pojo.PfpAdSpecificWebsite;
import com.pchome.akbpfp.db.service.BaseService;

public class PfpAdSpecificWebsiteService extends BaseService<PfpAdSpecificWebsite,String> implements IPfpAdSpecificWebsiteService {

	@Override
	public List<PfpAdSpecificWebsite> findPfpAdSpecificWebsiteByAdActionSeq(String adActionSeq) throws Exception {
		return ((IPfpAdSpecificWebsiteDAO)dao).findPfpAdSpecificWebsiteByAdActionSeq(adActionSeq);
	}

}
