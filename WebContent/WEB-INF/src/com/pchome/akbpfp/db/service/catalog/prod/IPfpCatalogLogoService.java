package com.pchome.akbpfp.db.service.catalog.prod;

import com.pchome.akbpfp.db.pojo.PfpCatalogLogo;
import com.pchome.akbpfp.db.service.IBaseService;


public interface IPfpCatalogLogoService extends IBaseService<PfpCatalogLogo,String>{
	
	public PfpCatalogLogo findCatalogLogoByCustomerInfoId(String customerInfoId) throws Exception; 
	
}
