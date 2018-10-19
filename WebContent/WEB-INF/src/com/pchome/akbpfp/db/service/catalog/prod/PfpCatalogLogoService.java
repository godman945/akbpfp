package com.pchome.akbpfp.db.service.catalog.prod;

import java.util.List;

import com.pchome.akbpfp.db.dao.catalog.prod.IPfpCatalogLogoDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogLogo;
import com.pchome.akbpfp.db.service.BaseService;

public class PfpCatalogLogoService extends BaseService<PfpCatalogLogo,String> implements IPfpCatalogLogoService{

	@Override
	public List<PfpCatalogLogo> findCatalogLogoByCustomerInfoId(String customerInfoId) throws Exception {
		return ((IPfpCatalogLogoDAO)dao).findCatalogLogoByCustomerInfoId(customerInfoId);
	}

}
