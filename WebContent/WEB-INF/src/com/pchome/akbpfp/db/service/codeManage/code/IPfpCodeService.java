package com.pchome.akbpfp.db.service.codeManage.code;

import com.pchome.akbpfp.db.pojo.PfpCode;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfpCodeService extends IBaseService<PfpCode,String>{
	
	public PfpCode getPfpCode(String pfpCustomerInfoId) throws Exception;
}