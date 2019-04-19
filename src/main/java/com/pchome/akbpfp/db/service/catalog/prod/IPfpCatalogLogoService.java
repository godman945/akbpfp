package com.pchome.akbpfp.db.service.catalog.prod;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpCatalogLogo;
import com.pchome.akbpfp.db.service.IBaseService;


public interface IPfpCatalogLogoService extends IBaseService<PfpCatalogLogo,String>{
	
	public List<PfpCatalogLogo> findCatalogLogoByCustomerInfoId(String customerInfoId) throws Exception;
	
	public PfpCatalogLogo findCatalogLogoByLogoType(String customerInfoId,String logoType) throws Exception;
	
}
