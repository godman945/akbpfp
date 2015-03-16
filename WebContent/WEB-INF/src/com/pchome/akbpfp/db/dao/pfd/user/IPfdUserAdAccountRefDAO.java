package com.pchome.akbpfp.db.dao.pfd.user;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfdUserAdAccountRef;

public interface IPfdUserAdAccountRefDAO extends IBaseDAO<PfdUserAdAccountRef, String>{
	
	public List<PfdUserAdAccountRef> checkPfdAndPfpRef(String pfdCustomerInfoId, String pfpCustomerInfoId);
	
	public List<PfdUserAdAccountRef> findPfdUserAdAccountRef(String pfpCustomerInfoId);
}
