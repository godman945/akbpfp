package com.pchome.akbpfp.db.service.catalog.prodGroup;


import java.util.List;

import com.pchome.akbpfp.db.dao.catalog.prodGroup.IPfpCatalogGroupItemDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.service.BaseService;


public class PfpCatalogGroupItemService extends BaseService<PfpCatalogGroupItem,Integer> implements IPfpCatalogGroupItemService{
//	private PfpCatalogGroupDAO pfpCatalogGroupDAO;
	
	
//	private IAccesslogProvider accesslogProvider;
//	private PfpUserMemberRefDAO pfpUserMemberRefDAO;
	
	
	public List<PfpCatalogGroupItem> getPfpCatalogGroupItemList(String groupId) throws Exception{
		List<PfpCatalogGroupItem> pfpCatalogGroupItems = ((IPfpCatalogGroupItemDAO)dao).getPfpCatalogGroupItemList(groupId);
		
		return pfpCatalogGroupItems;
	}
	
	





}
