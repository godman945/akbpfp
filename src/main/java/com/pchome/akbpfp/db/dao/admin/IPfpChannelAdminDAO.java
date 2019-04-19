package com.pchome.akbpfp.db.dao.admin;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpChannelAdmin;

public interface IPfpChannelAdminDAO extends IBaseDAO <PfpChannelAdmin, Integer> {

	public List<PfpChannelAdmin> findChannelAdmin(String memberId);
}
