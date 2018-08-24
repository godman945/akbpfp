package com.pchome.akbpfp.db.dao.catalog.prod;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogProdEc;

public interface IPfpCatalogProdEcDAO extends IBaseDAO<PfpCatalogProdEc,Integer>{
	
	public List<Map<String,Object>> getProdList(String catalogSeq, String prodStatus, String pfpCustomerInfoId, int page, int pageSize)  throws Exception;
	
	public String getProdListCount(String catalogSeq, String prodStatus) throws Exception;
	
	public void updateProdListProdStatus(String catalogSeq, String prodStatus, List<String> prodIdList) throws Exception;
	
	public List<Map<String,Object>> queryProdListDetail(String catalogSeq,String prodId) throws Exception;
	
	public List<Map<String,Object>> getProdGroupCount(String catalogSeq, String filterSQL) throws Exception;
	
	public List<Map<String,Object>> getEcProdGroupListByRandom(String catalogSeq, String filterSQL, int prodNum) throws Exception;
	
	public List<Map<String,Object>> getEcProdGroupList(String catalogSeq, String filterSQL) throws Exception;
	
}
