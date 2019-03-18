package com.pchome.akbpfp.db.service.manager;

import java.util.List;

import com.pchome.akbpfp.db.dao.manager.IAdmManagerDetailDAO;
import com.pchome.akbpfp.db.pojo.AdmManagerDetail;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.enumerate.manager.EnumChannelCategory;

public class AdmManagerDetailService extends BaseService<AdmManagerDetail, Integer> implements IAdmManagerDetailService{

	public AdmManagerDetail findAdmManagerDetail(String memberId, EnumChannelCategory enumChannelCategory) {
		List<AdmManagerDetail> list = ((IAdmManagerDetailDAO)dao).findAdmManagerDetail(memberId, enumChannelCategory.getCategory());
		
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
}
