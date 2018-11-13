package com.pchome.akbpfp.db.service.catalog;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogVO;

public interface IPfpCatalogService extends IBaseService<PfpCatalog,String>{
	
	public String getCatalogType(String catalogSeq) throws Exception;
	
	public PfpCatalog getPfpCatalog(String catalogSeq) throws Exception;
	
	public List<PfpCatalog> getPfpCatalogList(String pfpCustomerInfoId) throws Exception;
	
	public List<PfpCatalog> getPfpCatalogByCustomerInfoId(String customerInfoId) throws Exception;
	
	public List<PfpCatalog> checkPfpCatalogPrivilege(String customerInfoId,String catalogSeq) throws Exception;
	
	/**
	 * 查詢商品目錄清單
	 * @param PfpCatalogVO
	 * @return
	 */
	List<PfpCatalogVO> getPfpCatalogList(PfpCatalogVO vo);

	/**
	 * 新增商品目錄
	 * @param PfpCatalogVO
	 * @return
	 * @throws Exception 
	 */
	void savePfpCatalog(PfpCatalogVO vo) throws Exception;
	
	/**
	 * 更新商品目錄
	 * @param PfpCatalogVO
	 */
	void updatePfpCatalog(PfpCatalogVO vo);

	/**
	 * 更新目錄資料，一般購物類使用
	 * @param pfpCatalogVO
	 */
	void updatePfpCatalogForShoppingProd(PfpCatalogVO pfpCatalogVO);

	/**
	 * 查詢目前目錄資料上傳狀態
	 * @param uploadingCatalogSeqList
	 * @return
	 */
	public List<Map<String, String>> getCatalogUploadingStatus(String uploadingCatalogSeqList);

	public void saveOrUpdateWithCommit(PfpCatalog pfpCatalog);

}