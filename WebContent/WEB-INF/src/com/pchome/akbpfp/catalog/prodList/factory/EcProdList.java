package com.pchome.akbpfp.catalog.prodList.factory;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.service.catalog.prod.PfpCatalogProdEcService;
import com.pchome.akbpfp.db.vo.catalog.prodList.ProdListConditionVO;

public class EcProdList extends AProdList {
	
	private PfpCatalogProdEcService pfpCatalogProdEcService;
	
	public List<Object> getProdList(ProdListConditionVO prodListConditionVO) throws Exception{
		List<Object> prodLists = pfpCatalogProdEcService.getProdList(prodListConditionVO);
		
		return prodLists;
	}
	
	public void updateProdListProdStatus(String catalogSeq, String prodStatus, List<String> prodIdList) throws Exception{
		pfpCatalogProdEcService.updateProdListProdStatus(catalogSeq, prodStatus, prodIdList);
	}
	
	public List<Map<String,Object>> queryProdListDetail(String catalogSeq,String prodId) throws Exception{
		return pfpCatalogProdEcService.queryProdListDetail(catalogSeq,prodId);
	}
	
	
	
	
	
	public String getProdListCount(ProdListConditionVO prodListConditionVO) throws Exception{
		return  pfpCatalogProdEcService.getProdListCount(prodListConditionVO);
	}
	
	public void setPfpCatalogProdEcService(PfpCatalogProdEcService pfpCatalogProdEcService) {
		this.pfpCatalogProdEcService = pfpCatalogProdEcService;
	}

}