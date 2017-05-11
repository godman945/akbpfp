package com.pchome.akbpfp.db.service.ad;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfbxWebsiteCategory;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfbxWebsiteCategoryService extends IBaseService<PfbxWebsiteCategory, String> {
	public List<PfbxWebsiteCategory> getAllOrderByCode();
}
