package com.pchome.akbpfp.db.service.bu;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpBuAccount;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfpBuService extends IBaseService<PfpBuAccount, Integer>{

	public List<PfpBuAccount> findPfpBuAccountByBuId(String buId) throws Exception;
	
	public List<PfpBuAccount> findPfpBuAccountByMemberId(String memberId) throws Exception;

}
