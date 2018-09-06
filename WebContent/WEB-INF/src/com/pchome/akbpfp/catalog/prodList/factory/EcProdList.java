package com.pchome.akbpfp.catalog.prodList.factory;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.service.catalog.prod.PfpCatalogProdEcService;





public class EcProdList extends AProdList {
	
//	private PfpCatalogGroupService pfpCatalogGroupService;
	
	private PfpCatalogProdEcService pfpCatalogProdEcService;
	
	
	public List<Object> getProdList(String catalogSeq, String prodStatus, String pfpCustomerInfoId, int page, int pageSize) throws Exception{
		
		 List<Object> prodLists = pfpCatalogProdEcService.getProdList(catalogSeq, prodStatus, pfpCustomerInfoId, page, pageSize);
		
		
		return prodLists;
	}
	
	
	public void updateProdListProdStatus(String catalogSeq, String prodStatus, List<String> prodIdList) throws Exception{
		pfpCatalogProdEcService.updateProdListProdStatus(catalogSeq, prodStatus, prodIdList);
	}
	
	public List<Map<String,Object>> queryProdListDetail(String catalogSeq,String prodId) throws Exception{
		return pfpCatalogProdEcService.queryProdListDetail(catalogSeq,prodId);
	}
	
	
	public String getProdListCount(String catalogSeq, String prodStatus) throws Exception{
		return  pfpCatalogProdEcService.getProdListCount(catalogSeq, prodStatus);
	}

	
	
	
	
	
	
	public void setPfpCatalogProdEcService(PfpCatalogProdEcService pfpCatalogProdEcService) {
		this.pfpCatalogProdEcService = pfpCatalogProdEcService;
	}

	
	

}