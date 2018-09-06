package com.pchome.akbpfp.db.service.catalog.prodGroup;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.service.IBaseService;


public interface IPfpCatalogGroupService extends IBaseService<PfpCatalogGroup,String>{
	
	public String getCatalogType(String groupId) throws Exception;
	
	public String getCatalogSeq(String groupId) throws Exception;
	
	public List<PfpCatalogGroup> getPfpCatalogGroupList (String catalogSeq) throws Exception;
	
	public PfpCatalogGroup getPfpCatalogGroup (String catalogGroupSeq) throws Exception;
	
	public void saveOrUpdateWithCommit(PfpCatalogGroup pfpCatalogGroup)throws Exception;
}
