package com.pchome.akbpfp.db.service.catalog.prodGroup;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.service.IBaseService;


public interface IPfpCatalogGroupService extends IBaseService<PfpCatalogGroup,String>{
	
	public String getCatalogType(String groupId) throws Exception;
	
	public String getCatalogSeq(String groupId) throws Exception;
	
	public List<Map<String,Object>> getEcProdGroupList(String catalogSeq, String filterSQL, int prodNum) throws Exception;
	
	
	
	
//	public AccountVO getAccountVO(String customerInfoId) throws Exception;
	
//	public PfpCustomerInfo findCustomerInfo(String customerInfoId);
		
//	public List<PfpCustomerInfo> findAllPfpCustomerInfo();
	
	
//	public void saveOrUpdateWithAccesslog(PfpCustomerInfo customerInfo, String pcId, String userId, String clientIp) throws Exception;
	
//	public PfpCustomerInfo findCustomerInfoByMmeberId(String memberId);
}
