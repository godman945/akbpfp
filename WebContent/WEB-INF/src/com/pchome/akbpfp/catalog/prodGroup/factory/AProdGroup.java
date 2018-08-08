package com.pchome.akbpfp.catalog.prodGroup.factory;

import java.util.List;

import org.json.JSONArray;

import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.service.catalog.prodGroup.IPfpCatalogGroupItemService;

public abstract class AProdGroup {
	
	public IPfpCatalogGroupItemService pfpCatalogGroupItemService;
//	public IPfpCatalogGroupService PfpCatalogGroupService;
//	private AProdGroup aProdGroup;
	

	public List<PfpCatalogGroupItem> getPfpCatalogGroupItemList(String groupId) throws Exception{
		
		List<PfpCatalogGroupItem> pfpCatalogGroupItems= pfpCatalogGroupItemService.getPfpCatalogGroupItemList(groupId);
		
		return pfpCatalogGroupItems;
	}
	
	public abstract String pfpCatalogGroupItemTofilterSQL(List<PfpCatalogGroupItem> pfpCatalogGroupItems) throws Exception;
	
	public abstract JSONArray getProdGroupList(String catalogSeq, String filterSQL, int prodNum) throws Exception;
	

	
	
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
