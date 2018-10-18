package com.pchome.akbpfp.db.service.catalog;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.catalog.IPfpCatalogDAO;
import com.pchome.akbpfp.db.dao.catalog.prod.IPfpCatalogProdEcDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.service.BaseService;

public class PfpCatalogService extends BaseService<PfpCatalog,String> implements IPfpCatalogService{
	
	public String getCatalogType(String catalogSeq) throws Exception{
		List<PfpCatalog> pfpCatalog = ((IPfpCatalogDAO)dao).getCatalogType(catalogSeq);
		
		String catalogType = "";
		if( (!pfpCatalog.isEmpty()) && (pfpCatalog.size()>0) ){
			catalogType = pfpCatalog.get(0).getCatalogType();
		}
		return catalogType;
	}
	
	public PfpCatalog getPfpCatalog(String catalogSeq) throws Exception{
		return ((IPfpCatalogDAO)dao).getPfpCatalog(catalogSeq).get(0);
	}
	
	public List<PfpCatalog> getPfpCatalogList(String pfpCustomerInfoId) throws Exception{
		return ((IPfpCatalogDAO)dao).getPfpCatalogList(pfpCustomerInfoId);
	}

	
}
