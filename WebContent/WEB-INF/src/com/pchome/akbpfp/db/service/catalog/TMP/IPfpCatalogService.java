package com.pchome.akbpfp.db.service.catalog.TMP;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfpCatalogService extends IBaseService<PfpCatalog,String>{
	
	public String getCatalogType(String catalogSeq) throws Exception;
	
	public PfpCatalog getPfpCatalog(String catalogSeq) throws Exception;
	
	public List<Map<String,Object>> getCatalogAllList(String pfpCustomerInfoId) throws Exception;
	
	public String getCatalogAllListCount(String pfpCustomerInfoId) throws Exception;
	
}