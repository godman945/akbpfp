package com.pchome.akbpfp.db.dao.catalog.prodGroup;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;


public interface IPfpCatalogGroupDAO extends IBaseDAO<PfpCatalogGroup,String>{
		
	public List<PfpCatalogGroup> getCatalogType(String groupId) throws Exception;
	
	public List<PfpCatalogGroup> getCatalogSeq(String groupId) throws Exception;
	
	public List<PfpCatalogGroup> getPfpCatalogGroupList (String catalogSeq) throws Exception;
	
	public List<PfpCatalogGroup> getPfpCatalogGroup (String catalogGroupSeq) throws Exception;
	
	public void saveOrUpdateWithCommit(PfpCatalogGroup pfpCatalogGroup) throws Exception;
	
	public void deleteCatalogGroup(String catalogGroupSeq) throws Exception;
	
}
