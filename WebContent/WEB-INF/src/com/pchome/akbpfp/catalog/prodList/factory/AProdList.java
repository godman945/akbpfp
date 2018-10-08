package com.pchome.akbpfp.catalog.prodList.factory;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.service.catalog.TMP.IPfpCatalogService;
import com.pchome.akbpfp.db.vo.catalog.prodList.ProdListConditionVO;

public abstract class AProdList {
	
	public IPfpCatalogService pfpCatalogService;
//	public IPfpCatalogGroupService PfpCatalogGroupService;
//	private AProdList aProdGroup;
	

	public String getCatalogType(String catalogSeq) throws Exception{
		
		String catalogType = pfpCatalogService.getCatalogType(catalogSeq);
		
		return catalogType;
	}
	
	public abstract List<Object> getProdList(ProdListConditionVO prodListConditionVO) throws Exception;
	
	public abstract String getProdListCount(ProdListConditionVO prodListConditionVO) throws Exception;
	
	public abstract void updateProdListProdStatus(String catalogSeq, String prodStatus, List<String> prodIdList) throws Exception;
	
	public abstract List<Map<String,Object>> queryProdListDetail(String catalogSeq,String prodId) throws Exception;

	
	
	

	public IPfpCatalogService getPfpCatalogService() {
		return pfpCatalogService;
	}

	public void setPfpCatalogService(IPfpCatalogService pfpCatalogService) {
		this.pfpCatalogService = pfpCatalogService;
	}
	
}
