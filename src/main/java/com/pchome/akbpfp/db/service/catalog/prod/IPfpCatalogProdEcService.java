package com.pchome.akbpfp.db.service.catalog.prod;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.pojo.PfpCatalogProdEc;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.catalog.prodGroup.ProdGroupConditionVO;
import com.pchome.akbpfp.db.vo.catalog.prodList.ProdListConditionVO;

public interface IPfpCatalogProdEcService extends IBaseService<PfpCatalogProdEc,Integer>{
	
	public List<Object> getProdList(ProdListConditionVO prodListConditionVO) throws Exception;
	
	public String getProdListCount(ProdListConditionVO prodListConditionVO) throws Exception;
	
	public void updateProdListProdStatus(String catalogSeq, String prodStatus, List<String> prodIdList) throws Exception;
	
	public List<Map<String,Object>> queryProdListDetail(String catalogSeq,String prodId) throws Exception;
	
	public String getProdGroupCount(String catalogSeq, String filterSQL) throws Exception;
	
	public List<Map<String,Object>> getEcProdGroupListByRandom(String catalogSeq, String filterSQL, int prodNum) throws Exception;
	
	public List<Object> getEcProdGroupList(ProdGroupConditionVO prodGroupConditionVO) throws Exception;
	
	public List<String> queryCategoryGroupByVal(String catalogSeq)  throws Exception;
	
	
}