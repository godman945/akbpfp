package com.pchome.akbpfp.db.service.catalog.prodGroup;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.catalog.prodGroup.IPfpCatalogGroupItemDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.service.BaseService;


public class PfpCatalogGroupItemService extends BaseService<PfpCatalogGroupItem,Integer> implements IPfpCatalogGroupItemService{
	
	public List<PfpCatalogGroupItem> getPfpCatalogGroupItemList(String catalogGroupSeq) throws Exception{
		List<PfpCatalogGroupItem> pfpCatalogGroupItems = ((IPfpCatalogGroupItemDAO)dao).getPfpCatalogGroupItemList(catalogGroupSeq);
		
		return pfpCatalogGroupItems;
	}
	
	public List<Map<String,Object>> getCatalogAllGroupItem(String catalogSeq, String catalogGroupSeq) throws Exception{
		return ((IPfpCatalogGroupItemDAO)dao).getCatalogAllGroupItem(catalogSeq,catalogGroupSeq);
	}
	
	public void deleteCatalogGroupItem(String catalogGroupSeq) throws Exception{
		((IPfpCatalogGroupItemDAO)dao).deleteCatalogGroupItem(catalogGroupSeq);
	}
	
}
