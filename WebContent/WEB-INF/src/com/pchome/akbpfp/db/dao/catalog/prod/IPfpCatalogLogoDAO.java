package com.pchome.akbpfp.db.dao.catalog.prod;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogLogo;

public interface IPfpCatalogLogoDAO extends IBaseDAO<PfpCatalogLogo,String>{
	
	public PfpCatalogLogo findCatalogLogoByCustomerInfoId(String customerInfoId) throws Exception;
}
