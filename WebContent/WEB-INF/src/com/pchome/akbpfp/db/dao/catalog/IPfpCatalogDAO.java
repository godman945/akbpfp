package com.pchome.akbpfp.db.dao.catalog;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.vo.catalog.PfpCatalogVO;

public interface IPfpCatalogDAO extends IBaseDAO<PfpCatalog,String>{
	
	public List<PfpCatalog> getCatalogType(String catalogSeq) throws Exception;
	
	public List<PfpCatalog> getPfpCatalog(String catalogSeq) throws Exception;
	
	public List<PfpCatalog> getPfpCatalogList(String pfpCustomerInfoId) throws Exception;
	
	public List<PfpCatalog> getPfpCatalogByCustomerInfoId(String customerInfoId) throws Exception;
	
	public List<PfpCatalog> checkPfpCatalogPrivilege(String customerInfoId,String catalogSeq) throws Exception;
	
	List<Map<String, Object>> getPfpCatalogList(PfpCatalogVO vo);

	void savePfpCatalog(PfpCatalogVO vo);

	void updatePfpCatalog(PfpCatalogVO vo);

	void updatePfpCatalogForShoppingProd(PfpCatalogVO vo);

	public List<Map<String, Object>> getCatalogUploadingStatus(List<String> catalogSeqList);

	public void saveOrUpdateWithCommit(PfpCatalog pfpCatalog);

	public List<Map<String, Object>> getCatalogAndCatalogLogoData(String customer_info_id);
}