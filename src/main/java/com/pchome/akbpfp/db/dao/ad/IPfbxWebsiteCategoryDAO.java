package com.pchome.akbpfp.db.dao.ad;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfbxWebsiteCategory;

public interface IPfbxWebsiteCategoryDAO extends IBaseDAO<PfbxWebsiteCategory, String> {

	public List<PfbxWebsiteCategory> getAllOrderByCode();
	
}
