package com.pchome.akbpfp.db.service.catalog.prodGroup;

import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.service.IBaseService;


public interface IPfpCatalogGroupItemService extends IBaseService<PfpCatalogGroupItem,Integer>{
	
	public String getCatalogGroupFilterSQL(String groupId) throws Exception;
	
	
	
	
//	public AccountVO getAccountVO(String customerInfoId) throws Exception;
	
//	public PfpCustomerInfo findCustomerInfo(String customerInfoId);
		
//	public List<PfpCustomerInfo> findAllPfpCustomerInfo();
	
	
//	public void saveOrUpdateWithAccesslog(PfpCustomerInfo customerInfo, String pcId, String userId, String clientIp) throws Exception;
	
//	public PfpCustomerInfo findCustomerInfoByMmeberId(String memberId);
}
