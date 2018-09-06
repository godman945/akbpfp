package com.pchome.akbpfp.db.dao.catalog.prodGroup;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;


public interface IPfpCatalogGroupItemDAO extends IBaseDAO<PfpCatalogGroupItem,Integer>{
		
	public List<PfpCatalogGroupItem> getPfpCatalogGroupItemList(String catalogGroupSeq) throws Exception;
	
	public List<Map<String,Object>> getCatalogAllGroupItem(String catalogSeq) throws Exception;

//	public List<PfpCustomerInfo> findCustomerInfo(String customerInfoId);
//
//	public List<PfpCustomerInfo> findAllPfpCustomerInfo();
//	
//	public List<PfpCustomerInfo> findValidCustomerInfos();
//	
//	//public void deleteCustomerInfo(String memberId) throws Exception;
//	
//	public List<PfpCustomerInfo> findCustomerInfoByMmeberId(String memberId);
}
