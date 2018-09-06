package com.pchome.akbpfp.db.service.catalog.prodGroup;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.service.IBaseService;


public interface IPfpCatalogGroupService extends IBaseService<PfpCatalogGroup,String>{
	
	public String getCatalogType(String groupId) throws Exception;
	
	public String getCatalogSeq(String groupId) throws Exception;
	
	public List<PfpCatalogGroup> getPfpCatalogGroupList (String catalogSeq) throws Exception;
	
}
