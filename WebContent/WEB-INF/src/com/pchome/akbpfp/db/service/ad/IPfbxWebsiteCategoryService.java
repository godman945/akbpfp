package com.pchome.akbpfp.db.service.ad;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.pojo.PfbxWebsiteCategory;
import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfbxWebsiteCategoryService extends IBaseService<PfbxWebsiteCategory, String> {
	public Map<String, List<Map<String, String>>> getAllOrderByCode();
	
	public Map<String,PfbxWebsiteCategory> getPfbxWebsiteCategoryMap();
	
	public Map<String,String> getPfbxWebsiteCategoryNameMap();
}
