package com.pchome.akbpfp.catalog.prodGroup.factory;

import java.util.List;

import org.json.JSONArray;

import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.service.catalog.prodGroup.IPfpCatalogGroupItemService;
import com.pchome.akbpfp.db.vo.catalog.prodGroup.ProdGroupConditionVO;

public abstract class AProdGroup {
	
	public IPfpCatalogGroupItemService pfpCatalogGroupItemService;
	

	public List<PfpCatalogGroupItem> getPfpCatalogGroupItemList(String groupId) throws Exception{
		
		List<PfpCatalogGroupItem> pfpCatalogGroupItems= pfpCatalogGroupItemService.getPfpCatalogGroupItemList(groupId);
		
		return pfpCatalogGroupItems;
	}
	
	public abstract String pfpCatalogGroupItemTofilterSQL(List<PfpCatalogGroupItem> pfpCatalogGroupItems) throws Exception;
	
	public abstract JSONArray getProdGroupListByRandom(String catalogSeq, String filterSQL, int prodNum) throws Exception;
	
	public abstract List<Object> getProdGroupList(ProdGroupConditionVO prodGroupConditionVO) throws Exception;
	
	public abstract String getProdGroupCount(String catalogSeq, String filterSQL) throws Exception;
	
	
	

	
	public IPfpCatalogGroupItemService getPfpCatalogGroupItemService() {
		return pfpCatalogGroupItemService;
	}

	public void setPfpCatalogGroupItemService(IPfpCatalogGroupItemService pfpCatalogGroupItemService) {
		this.pfpCatalogGroupItemService = pfpCatalogGroupItemService;
	}
	
}
