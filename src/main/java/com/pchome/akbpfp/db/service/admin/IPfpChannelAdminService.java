package com.pchome.akbpfp.db.service.admin;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpChannelAdmin;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfpChannelAdminService extends IBaseService<PfpChannelAdmin, Integer>{

	public List<PfpChannelAdmin> findChannelAdmin(String memberId);
}
