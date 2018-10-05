package com.pchome.akbpfp.db.service.catalog.TMP;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.pchome.akbpfp.db.dao.catalog.TMP.IPfpCatalogDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.service.BaseService;

public class PfpCatalogService extends BaseService<PfpCatalog,String> implements IPfpCatalogService{
	
//	private List<Map,>
	
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
	
	public List<Map<String,Object>> getCatalogAllList(String pfpCustomerInfoId) throws Exception{
		return ((IPfpCatalogDAO)dao).getCatalogAllList(pfpCustomerInfoId);
	}
	
	public String getCatalogAllListCount(String pfpCustomerInfoId) throws Exception{
		return ((IPfpCatalogDAO)dao).getCatalogAllListCount(pfpCustomerInfoId);
	}

	@Override
	public List<PfpCatalog> getPfpCatalogByCustomerInfoId(String customerInfoId) throws Exception {
		List<PfpCatalog> data = ((IPfpCatalogDAO)dao).getPfpCatalogByCustomerInfoId(customerInfoId);
		return data;
	}

}
