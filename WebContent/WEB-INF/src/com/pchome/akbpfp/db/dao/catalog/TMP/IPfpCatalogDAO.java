package com.pchome.akbpfp.db.dao.catalog.TMP;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;

public interface IPfpCatalogDAO extends IBaseDAO<PfpCatalog,String>{
	
	public List<PfpCatalog> getCatalogType(String catalogSeq) throws Exception;
	
	public List<PfpCatalog> getPfpCatalog(String catalogSeq) throws Exception;
	
	public List<PfpCatalog> getPfpCatalogByCustomerInfoId(String customerInfoId) throws Exception;
}
