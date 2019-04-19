package com.pchome.akbpfp.db.service.manager;

import com.pchome.akbpfp.db.pojo.AdmManagerDetail;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.enumerate.manager.EnumChannelCategory;

public interface IAdmManagerDetailService extends IBaseService<AdmManagerDetail, Integer>{

	public AdmManagerDetail findAdmManagerDetail(String memberId, EnumChannelCategory enumChannelCategory);
}
