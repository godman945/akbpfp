package com.pchome.akbpfp.catalog.prodGroup.factory;

import org.json.JSONArray;

import com.pchome.akbpfp.db.service.catalog.prodGroup.IPfpCatalogGroupItemService;

public abstract class AProdGroup {
	
	public IPfpCatalogGroupItemService pfpCatalogGroupItemService;
//	public IPfpCatalogGroupService PfpCatalogGroupService;
//	private AProdGroup aProdGroup;
	
	public abstract JSONArray getProdGroupList(String catalogSeq, String filterSQL) throws Exception;

	public String getCatalogGroupFilterSQL(String groupId) throws Exception{
		
		String filterSQL = pfpCatalogGroupItemService.getCatalogGroupFilterSQL(groupId);
		
		return filterSQL;
	}

	
	
	public IPfpCatalogGroupItemService getPfpCatalogGroupItemService() {
		return pfpCatalogGroupItemService;
	}

	public void setPfpCatalogGroupItemService(IPfpCatalogGroupItemService pfpCatalogGroupItemService) {
		this.pfpCatalogGroupItemService = pfpCatalogGroupItemService;
	}

	

//	public void setPfpCatalogGroupService(PfpCatalogGroupService pfpCatalogGroupService) {
//		this.pfpCatalogGroupService = pfpCatalogGroupService;
//	}
	
	
}
