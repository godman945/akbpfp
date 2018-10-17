package com.pchome.akbpfp.db.dao.catalog;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogVO;

public interface IPfpCatalogDAO extends IBaseDAO<PfpCatalog,String>{

	List<Map<String, Object>> getPfpCatalogList(PfpCatalogVO vo);

	void deletePfpCatalog(PfpCatalogVO vo);

	void savePfpCatalog(PfpCatalogVO vo);

	void updatePfpCatalog(PfpCatalogVO vo);

	void updatePfpCatalogForShoppingProd(PfpCatalogVO vo);

}
