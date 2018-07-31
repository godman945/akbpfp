package com.pchome.akbpfp.catalog.prodGroup.factory;

import com.pchome.akbpfp.db.service.catalog.prodGroup.IPfpCatalogGroupService;
import com.pchome.akbpfp.db.service.catalog.prodGroup.PfpCatalogGroupService;
import com.pchome.akbpfp.struts2.action.api.CatalogProdGroupAPIAction;

public abstract class AProdGroup {
	
	public IPfpCatalogGroupService pfpCatalogGroupService;
	public IPfpCatalogGroupService PfpCatalogGroupService;
//	private AProdGroup aProdGroup;
	
	public abstract String getProdGroupList(String groupId) throws Exception;

	public String getCatalogGroupSQL() throws Exception{
		System.out.println(PfpCatalogGroupService);
		System.out.println(pfpCatalogGroupService);
		String catalogType = pfpCatalogGroupService.getCatalogType("PCG20180724000000001");
		System.out.println(this.getPfpCatalogGroupService());
		return "test";
	}

	public IPfpCatalogGroupService getPfpCatalogGroupService() {
		return pfpCatalogGroupService;
	}

	public void setPfpCatalogGroupService(IPfpCatalogGroupService pfpCatalogGroupService) {
		this.pfpCatalogGroupService = pfpCatalogGroupService;
	}
	
	

//	public void setPfpCatalogGroupService(PfpCatalogGroupService pfpCatalogGroupService) {
//		this.pfpCatalogGroupService = pfpCatalogGroupService;
//	}
	
	
}
