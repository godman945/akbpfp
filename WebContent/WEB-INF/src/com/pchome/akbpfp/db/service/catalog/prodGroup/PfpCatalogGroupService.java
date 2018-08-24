package com.pchome.akbpfp.db.service.catalog.prodGroup;


import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.catalog.prodGroup.IPfpCatalogGroupDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.service.BaseService;


public class PfpCatalogGroupService extends BaseService<PfpCatalogGroup,String> implements IPfpCatalogGroupService{
	
	public String getCatalogType(String groupId) throws Exception{
		List<PfpCatalogGroup> pfpCatalogGroups = ((IPfpCatalogGroupDAO)dao).getCatalogType(groupId);
		
		String catalogType = "";
		if( (!pfpCatalogGroups.isEmpty()) && (pfpCatalogGroups.size()>0) ){
			catalogType = pfpCatalogGroups.get(0).getPfpCatalog().getCatalogType();
		}
		return catalogType;
	}
	
	public String getCatalogSeq(String groupId) throws Exception{
		List<PfpCatalogGroup> pfpCatalogGroups = ((IPfpCatalogGroupDAO)dao).getCatalogSeq(groupId);
		
		String catalogSeq = "";
		if( (!pfpCatalogGroups.isEmpty()) && (pfpCatalogGroups.size()>0) ){
			catalogSeq = pfpCatalogGroups.get(0).getPfpCatalog().getCatalogSeq();
		}
		return catalogSeq;
	}
	
	public List<PfpCatalogGroup> getPfpCatalogGroupList (String catalogSeq) throws Exception{
		 return ((IPfpCatalogGroupDAO)dao).getPfpCatalogGroupList(catalogSeq);
	}
	
	
	
	


}
