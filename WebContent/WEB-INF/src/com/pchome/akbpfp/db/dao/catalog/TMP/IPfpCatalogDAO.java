package com.pchome.akbpfp.db.dao.catalog.TMP;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;

public interface IPfpCatalogDAO extends IBaseDAO<PfpCatalog,String>{
	
	public List<PfpCatalog> getCatalogType(String catalogSeq) throws Exception;
	
	public List<PfpCatalog> getPfpCatalog(String catalogSeq) throws Exception;
	
	public List<PfpCatalog> getPfpCatalogByCustomerInfoId(String customerInfoId) throws Exception;

	public List<Map<String,Object>> getCatalogAllList(String pfpCustomerInfoId) throws Exception;
	
	public String getCatalogAllListCount(String pfpCustomerInfoId) throws Exception;

}
