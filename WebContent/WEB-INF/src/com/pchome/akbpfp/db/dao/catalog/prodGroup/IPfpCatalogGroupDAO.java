package com.pchome.akbpfp.db.dao.catalog.prodGroup;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;


public interface IPfpCatalogGroupDAO extends IBaseDAO<PfpCatalogGroup,String>{
		
	public List<PfpCatalogGroup> getCatalogType(String groupId) throws Exception;

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
