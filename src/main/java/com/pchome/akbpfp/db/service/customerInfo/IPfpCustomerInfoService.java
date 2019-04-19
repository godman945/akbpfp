package com.pchome.akbpfp.db.service.customerInfo;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.pojo.PfpUserMemberRef;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.account.AccountVO;


public interface IPfpCustomerInfoService extends IBaseService<PfpCustomerInfo,String>{
	
	public AccountVO existentAccount(String memberId) throws Exception;
	
	public AccountVO getAccountVO(String customerInfoId) throws Exception;
	
	public PfpCustomerInfo findCustomerInfo(String customerInfoId);
		
	public List<PfpCustomerInfo> findAllPfpCustomerInfo();
	
	public List<PfpCustomerInfo> findValidCustomerInfos();
	
	public void saveOrUpdateWithAccesslog(PfpCustomerInfo customerInfo, String pcId, String userId, String clientIp) throws Exception;
	
	/**
	 * 確認帳戶狀態
	 * 1. 除了刪除以外
	 */
	public PfpCustomerInfo findCustomerInfoByMmeberId(String memberId);
}
