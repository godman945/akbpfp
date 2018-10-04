package com.pchome.akbpfp.db.dao.catalog.prod;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogSetup;

public interface IPfpCatalogSetupDAO extends IBaseDAO<PfpCatalogSetup,Integer>{
	
	public PfpCatalogSetup findSetupByCustomerInfoId(String customerInfoId) throws Exception;
}
