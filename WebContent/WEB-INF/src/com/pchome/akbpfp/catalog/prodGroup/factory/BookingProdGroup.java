package com.pchome.akbpfp.catalog.prodGroup.factory;

import com.pchome.akbpfp.db.service.catalog.prodGroup.IPfpCatalogGroupService;

public class BookingProdGroup extends AProdGroup {
	public IPfpCatalogGroupService pfpCatalogGroupService ;
	@Override
	public String getProdGroupList(String groupId) throws Exception {
		
		
		
		
		
		return "Booking-- Prod Group";
	}
	public IPfpCatalogGroupService getPfpCatalogGroupService() {
		return pfpCatalogGroupService;
	}
	public void setPfpCatalogGroupService(IPfpCatalogGroupService pfpCatalogGroupService) {
		this.pfpCatalogGroupService = pfpCatalogGroupService;
	}
	
	
	
}