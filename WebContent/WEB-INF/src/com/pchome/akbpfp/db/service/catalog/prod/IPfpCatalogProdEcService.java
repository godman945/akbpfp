package com.pchome.akbpfp.db.service.catalog.prod;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.pojo.PfpCatalogProdEc;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfpCatalogProdEcService extends IBaseService<PfpCatalogProdEc,Integer>{
	
	public List<Object> getProdList(String catalogSeq, String prodStatus, String pfpCustomerInfoId, int page, int pageSize) throws Exception;
	
	public String getProdListCount(String catalogSeq, String prodStatus) throws Exception;
	
	public void updateProdListProdStatus(String catalogSeq, String prodStatus, List<String> prodIdList) throws Exception;
	
	public List<Map<String,Object>> queryProdListDetail(String catalogSeq,String prodId) throws Exception;
	
	
}