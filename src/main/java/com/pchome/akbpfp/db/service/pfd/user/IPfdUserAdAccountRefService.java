package com.pchome.akbpfp.db.service.pfd.user;

import java.util.List;

import com.pchome.akbpfd.db.vo.user.PfdUserAdAccountRefVO;
import com.pchome.akbpfp.db.pojo.PfdUserAdAccountRef;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfdUserAdAccountRefService extends IBaseService<PfdUserAdAccountRef, String> {

	public PfdUserAdAccountRef checkPfdAndPfpRef(String pfdCustomerInfoId, String pfpCustomerInfoId);
	
	public PfdUserAdAccountRef findPfdUserAdAccountRef(String pfpCustomerInfoId);
	
	public void savePfdUserAdAccountRef(PfdUserAdAccountRefVO pfdUserAdAccountRefVO);
	
	public Integer getNewRefId();
}
