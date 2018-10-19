package com.pchome.akbpfp.db.dao.catalog.prod;

import java.util.List;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogLogo;

public interface IPfpCatalogLogoDAO extends IBaseDAO<PfpCatalogLogo,String>{
	
	public List<PfpCatalogLogo> findCatalogLogoByCustomerInfoId(String customerInfoId) throws Exception;
}
