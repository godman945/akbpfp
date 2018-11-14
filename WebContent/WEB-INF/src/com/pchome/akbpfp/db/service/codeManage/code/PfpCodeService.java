package com.pchome.akbpfp.db.service.codeManage.code;

import java.util.List;

import com.pchome.akbpfp.db.dao.codeManage.code.IPfpCodeDAO;
import com.pchome.akbpfp.db.pojo.PfpCode;
import com.pchome.akbpfp.db.service.BaseService;

public class PfpCodeService extends BaseService<PfpCode,String> implements IPfpCodeService{
	
	public PfpCode getPfpCode(String pfpCustomerInfoId) throws Exception{
		List<PfpCode> pfpCodeList = ((IPfpCodeDAO)dao).getPfpCode(pfpCustomerInfoId);
		if( (!pfpCodeList.isEmpty()) && (pfpCodeList.size()>0) ){
			return pfpCodeList.get(0);
		}
		return null;
	}
	
	
}