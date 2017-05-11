package com.pchome.akbpfp.db.service.ad;


import java.util.List;

import com.pchome.akbpfp.db.dao.ad.IPfbxWebsiteCategoryDAO;
import com.pchome.akbpfp.db.pojo.PfbxWebsiteCategory;
import com.pchome.akbpfp.db.service.BaseService;

public class PfbxWebsiteCategoryService extends BaseService<PfbxWebsiteCategory,String> implements IPfbxWebsiteCategoryService {

	@Override
	public List<PfbxWebsiteCategory> getAllOrderByCode() {
		return ((IPfbxWebsiteCategoryDAO)dao).getAllOrderByCode();
	}

}
