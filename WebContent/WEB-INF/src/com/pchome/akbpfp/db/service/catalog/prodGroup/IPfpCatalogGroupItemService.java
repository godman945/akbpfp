package com.pchome.akbpfp.db.service.catalog.prodGroup;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.service.IBaseService;


public interface IPfpCatalogGroupItemService extends IBaseService<PfpCatalogGroupItem,Integer>{
	
	public List<PfpCatalogGroupItem> getPfpCatalogGroupItemList(String groupId) throws Exception;
	
	
	
	
//	public AccountVO getAccountVO(String customerInfoId) throws Exception;
	
//	public PfpCustomerInfo findCustomerInfo(String customerInfoId);
		
//	public List<PfpCustomerInfo> findAllPfpCustomerInfo();
	
	
//	public void saveOrUpdateWithAccesslog(PfpCustomerInfo customerInfo, String pcId, String userId, String clientIp) throws Exception;
	
//	public PfpCustomerInfo findCustomerInfoByMmeberId(String memberId);
}
