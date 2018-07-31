package com.pchome.akbpfp.catalog.prodGroup.factory;

import com.pchome.akbpfp.db.service.catalog.prodGroup.IPfpCatalogGroupService;
import com.pchome.akbpfp.db.service.catalog.prodGroup.PfpCatalogGroupService;

public abstract class AProdGroup {
	
	protected IPfpCatalogGroupService pfpCatalogGroupService ;
//	private AProdGroup aProdGroup;
	
	public abstract String getProdGroupList(String groupId) throws Exception;

	public String getCatalogGroupSQL() throws Exception{
		String catalogType = pfpCatalogGroupService.getCatalogType("PCG20180724000000001");
		
		return "test";
	}
	
	

//	public void setPfpCatalogGroupService(PfpCatalogGroupService pfpCatalogGroupService) {
//		this.pfpCatalogGroupService = pfpCatalogGroupService;
//	}
	
	
}
