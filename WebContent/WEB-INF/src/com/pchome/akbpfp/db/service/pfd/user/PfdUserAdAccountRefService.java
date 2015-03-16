package com.pchome.akbpfp.db.service.pfd.user;

import java.util.List;

import com.pchome.akbpfp.db.dao.pfd.user.PfdUserAdAccountRefDAO;
import com.pchome.akbpfp.db.pojo.PfdUserAdAccountRef;
import com.pchome.akbpfp.db.service.BaseService;

public class PfdUserAdAccountRefService extends BaseService <PfdUserAdAccountRef, String> implements IPfdUserAdAccountRefService{
	
	public PfdUserAdAccountRef checkPfdAndPfpRef(String pfdCustomerInfoId, String pfpCustomerInfoId) {
		List<PfdUserAdAccountRef> ref = ((PfdUserAdAccountRefDAO)dao).checkPfdAndPfpRef(pfdCustomerInfoId, pfpCustomerInfoId);

		if(ref.isEmpty()){
			return null;
		}else{
			return ref.get(0);
		}
	}
	
	public PfdUserAdAccountRef findPfdUserAdAccountRef(String pfpCustomerInfoId) {
		
		List<PfdUserAdAccountRef> ref = ((PfdUserAdAccountRefDAO)dao).findPfdUserAdAccountRef(pfpCustomerInfoId);

		if(ref.isEmpty()){
			return null;
		}else{
			return ref.get(0);
		}
	}
}
