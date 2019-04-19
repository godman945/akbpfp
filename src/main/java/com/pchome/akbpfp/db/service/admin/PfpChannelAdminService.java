package com.pchome.akbpfp.db.service.admin;

import java.util.List;

import com.pchome.akbpfp.db.dao.admin.IPfpChannelAdminDAO;
import com.pchome.akbpfp.db.pojo.PfpChannelAdmin;
import com.pchome.akbpfp.db.service.BaseService;

public class PfpChannelAdminService extends BaseService<PfpChannelAdmin, Integer> implements IPfpChannelAdminService{

	public List<PfpChannelAdmin> findChannelAdmin(String memberId) {
		return ((IPfpChannelAdminDAO)dao).findChannelAdmin(memberId);
	}
}
