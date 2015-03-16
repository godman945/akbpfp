package com.pchome.akbpfp.db.dao.customerInfo;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;


public interface IPfpCustomerInfoDAO extends IBaseDAO<PfpCustomerInfo,String>{
		
	public List<PfpCustomerInfo> validMemberId(String memberId) throws Exception;

	public List<PfpCustomerInfo> findCustomerInfo(String customerInfoId);

	public List<PfpCustomerInfo> findAllPfpCustomerInfo();
	
	public List<PfpCustomerInfo> findValidCustomerInfos();
	
	//public void deleteCustomerInfo(String memberId) throws Exception;
	
	public List<PfpCustomerInfo> findCustomerInfoByMmeberId(String memberId);
}
