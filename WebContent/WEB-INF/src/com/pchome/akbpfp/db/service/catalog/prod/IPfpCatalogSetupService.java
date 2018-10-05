package com.pchome.akbpfp.db.service.catalog.prod;

import com.pchome.akbpfp.db.pojo.PfpCatalogSetup;
import com.pchome.akbpfp.db.service.IBaseService;


public interface IPfpCatalogSetupService extends IBaseService<PfpCatalogSetup,Integer>{
	
	public PfpCatalogSetup findSetupByCatalogSeq(String catalogSeq) throws Exception;
}
