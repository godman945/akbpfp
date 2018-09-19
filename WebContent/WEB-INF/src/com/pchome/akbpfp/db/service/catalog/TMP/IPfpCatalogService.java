package com.pchome.akbpfp.db.service.catalog.TMP;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfpCatalogService extends IBaseService<PfpCatalog,String>{
	
	public String getCatalogType(String catalogSeq) throws Exception;
	
	public PfpCatalog getPfpCatalog(String catalogSeq) throws Exception;
	
	//根據帳戶取得目錄資訊
	public List<PfpCatalog> getPfpCatalogByCustomerInfoId(String customerInfoId) throws Exception;
	
}