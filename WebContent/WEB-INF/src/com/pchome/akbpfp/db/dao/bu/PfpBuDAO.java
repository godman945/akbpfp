package com.pchome.akbpfp.db.dao.bu;

import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpBuAccount;

public class PfpBuDAO extends BaseDAO<PfpBuAccount, Integer> implements IPfpBuDAO {

	@SuppressWarnings("unchecked")
	public List<PfpBuAccount> findPfpBuAccountByBuId(String buId) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpBuAccount ");
		hql.append(" where buId = ? ");
		Object[] ob = new Object[] { buId };
		return super.getHibernateTemplate().find(hql.toString(), ob);
	}

	@Override
	public List<PfpBuAccount> findPfpBuAccountByMemberId(String memberId) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpBuAccount ");
		hql.append(" where pcId = ? ");
		Object[] ob = new Object[] { memberId };
		return super.getHibernateTemplate().find(hql.toString(), ob);
	}

}
