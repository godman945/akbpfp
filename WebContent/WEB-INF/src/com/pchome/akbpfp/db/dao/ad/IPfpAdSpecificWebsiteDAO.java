package com.pchome.akbpfp.db.dao.ad;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdSpecificWebsite;

public interface IPfpAdSpecificWebsiteDAO  extends IBaseDAO<PfpAdSpecificWebsite, String> {

	public List<PfpAdSpecificWebsite> findPfpAdSpecificWebsiteByAdActionSeq(String adActionSeq) throws Exception;
}
