package com.pchome.akbpfp.db.service.catalog.prod;

import com.pchome.akbpfp.db.dao.catalog.prod.IPfpCatalogSetupDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogSetup;
import com.pchome.akbpfp.db.service.BaseService;

public class PfpCatalogSetupService extends BaseService<PfpCatalogSetup,Integer> implements IPfpCatalogSetupService{

	@Override
	public PfpCatalogSetup findSetupByCustomerInfoId(String customerInfoId) throws Exception {
		return ((IPfpCatalogSetupDAO)dao).findSetupByCustomerInfoId(customerInfoId);
	}

}
