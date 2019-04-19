package com.pchome.akbpfp.db.dao.codeManage;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCode;


public interface IPfpCodeDAO extends IBaseDAO<PfpCode,String>{
		
	public List<PfpCode> getPfpCode(String pfpCustomerInfoId) throws Exception;
	
	public void saveOrUpdateWithCommit(PfpCode pfpCode) throws Exception;
	
}
