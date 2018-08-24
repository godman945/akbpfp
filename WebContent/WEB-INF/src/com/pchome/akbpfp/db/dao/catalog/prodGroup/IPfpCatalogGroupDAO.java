package com.pchome.akbpfp.db.dao.catalog.prodGroup;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;


public interface IPfpCatalogGroupDAO extends IBaseDAO<PfpCatalogGroup,String>{
		
	public List<PfpCatalogGroup> getCatalogType(String groupId) throws Exception;
	
	public List<PfpCatalogGroup> getCatalogSeq(String groupId) throws Exception;
	
	public List<PfpCatalogGroup> getPfpCatalogGroupList (String catalogSeq) throws Exception;
	
}
