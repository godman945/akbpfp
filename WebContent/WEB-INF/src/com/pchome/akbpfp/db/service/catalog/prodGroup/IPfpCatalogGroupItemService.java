package com.pchome.akbpfp.db.service.catalog.prodGroup;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.service.IBaseService;


public interface IPfpCatalogGroupItemService extends IBaseService<PfpCatalogGroupItem,Integer>{
	
	public List<PfpCatalogGroupItem> getPfpCatalogGroupItemList(String catalogGroupSeq) throws Exception;
	
	public List<Map<String,Object>> getCatalogAllGroupItem(String catalogSeq, String catalogGroupSeq) throws Exception;
	
	public void deleteCatalogGroupItem(String catalogGroupSeq) throws Exception;
	
}
