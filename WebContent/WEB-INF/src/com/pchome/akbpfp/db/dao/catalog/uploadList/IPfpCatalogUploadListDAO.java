package com.pchome.akbpfp.db.dao.catalog.uploadList;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadLog;
import com.pchome.akbpfp.db.vo.catalog.PfpCatalogVO;
import com.pchome.akbpfp.db.vo.catalog.uploadList.PfpCatalogProdEcErrorVO;

public interface IPfpCatalogUploadListDAO extends IBaseDAO<String, String> {

	/**
	 * 新增商品目錄更新紀錄
	 * @param pfpCatalogUploadLog
	 */
	void savePfpCatalogUploadLog(PfpCatalogUploadLog pfpCatalogUploadLog);

	/**
	 * 刪除 商品目錄更新錯誤紀錄
	 * @param PfpCatalogVO
	 */
	void deletePfpCatalogUploadErrLog(PfpCatalogVO vo);

	/**
	 * 刪除 一般購物類商品上傳錯誤清單
	 * @param PfpCatalogVO
	 */
	void deletePfpCatalogProdEcError(PfpCatalogVO vo);
	
	/**
	 * 刪除 商品目錄更新紀錄
	 * @param PfpCatalogVO
	 */
	void deletePfpCatalogUploadLog(PfpCatalogVO vo);

	/**
	 * 刪除 商品目錄設定 
	 * @param PfpCatalogVO
	 */
	void deletePfpCatalogSetup(PfpCatalogVO vo);
	
	/**
	 * 取得一般購物類商品清單 資料
	 * @param catalogSeq 商品目錄
	 * @param catalogProdSeq 商品編號
	 * @return
	 */
	List<Map<String, Object>> getPfpCatalogProdEc(String catalogSeq, String catalogProdSeq);

	/**
	 * 查詢目錄商品上傳錯誤記錄清單
	 * @param PfpCatalogProdEcErrorVO
	 * @return
	 */
	List<Map<String, Object>> getCatalogProdUploadErrList(PfpCatalogProdEcErrorVO vo);

}