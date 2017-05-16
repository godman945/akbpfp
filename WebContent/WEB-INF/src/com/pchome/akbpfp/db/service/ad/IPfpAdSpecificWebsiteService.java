package com.pchome.akbpfp.db.service.ad;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpAdSpecificWebsite;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfpAdSpecificWebsiteService extends IBaseService<PfpAdSpecificWebsite, String> {
	public List<PfpAdSpecificWebsite> findPfpAdSpecificWebsiteByAdActionSeq(String adActionSeq) throws Exception;
}
