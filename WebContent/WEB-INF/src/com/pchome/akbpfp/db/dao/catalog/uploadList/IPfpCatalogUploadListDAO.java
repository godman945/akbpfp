package com.pchome.akbpfp.db.dao.catalog.uploadList;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogProdEc;
import com.pchome.akbpfp.db.pojo.Sequence;

public interface IPfpCatalogUploadListDAO extends IBaseDAO<Sequence,String>{

	void saveOrUpdatePfpCatalogProdEc(PfpCatalogProdEc pfpCatalogProdEc);

}
