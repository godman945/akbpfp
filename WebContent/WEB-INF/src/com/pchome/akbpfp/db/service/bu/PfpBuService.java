package com.pchome.akbpfp.db.service.bu;

import java.util.List;

import com.pchome.akbpfp.db.dao.bu.IPfpBuDAO;
import com.pchome.akbpfp.db.pojo.PfpBuAccount;
import com.pchome.akbpfp.db.service.BaseService;

public class PfpBuService extends BaseService <PfpBuAccount,Integer> implements IPfpBuService{

	public List<PfpBuAccount> findPfpBuAccountByBuId(String buId) throws Exception {
		return ((IPfpBuDAO)dao).findPfpBuAccountByBuId(buId);
	}

	public List<PfpBuAccount> findPfpBuAccountByMemberId(String memberId) throws Exception {
		return ((IPfpBuDAO)dao).findPfpBuAccountByMemberId(memberId);
	}
}
