package com.pchome.akbpfp.db.service.catalog.prodGroup;

import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.service.IBaseService;


public interface IPfpCatalogGroupService extends IBaseService<PfpCatalogGroup,String>{
	
	public String getCatalogType(String groupId) throws Exception;
	
//	public AccountVO getAccountVO(String customerInfoId) throws Exception;
	
//	public PfpCustomerInfo findCustomerInfo(String customerInfoId);
		
//	public List<PfpCustomerInfo> findAllPfpCustomerInfo();
	
	
//	public void saveOrUpdateWithAccesslog(PfpCustomerInfo customerInfo, String pcId, String userId, String clientIp) throws Exception;
	
//	public PfpCustomerInfo findCustomerInfoByMmeberId(String memberId);
}
