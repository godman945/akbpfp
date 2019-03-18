package com.pchome.akbpfp.db.dao.bu;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpBuAccount;

public interface IPfpBuDAO extends IBaseDAO<PfpBuAccount,Integer>{

	public List<PfpBuAccount> findPfpBuAccountByBuId(String buId) throws Exception;

	public List<PfpBuAccount> findPfpBuAccountByMemberId(String memberId) throws Exception;
}
