package com.pchome.akbpfp.db.service.catalog.TMP;

import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfpCatalogService extends IBaseService<PfpCatalog,String>{
	
	public String getCatalogType(String catalogSeq) throws Exception;
	
	public PfpCatalog getPfpCatalog(String catalogSeq) throws Exception;
	
}