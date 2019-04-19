package com.pchome.akbpfp.db.dao.manager;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.AdmManagerDetail;

public interface IAdmManagerDetailDAO extends IBaseDAO<AdmManagerDetail, Integer>{

	public List<AdmManagerDetail> findAdmManagerDetail(String memberId, String channelCategory);
}
