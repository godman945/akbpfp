package com.pchome.akbpfp.db.dao.catalog.uploadList;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogProdEc;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadErrLog;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadLog;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogVO;

public interface IPfpCatalogUploadListDAO extends IBaseDAO<String, String> {

	/**
	 * 更新一般購物類資料
	 * @param pfpCatalogProdEc
	 * @return 更新筆數
	 */
	int updatePfpCatalogProdEc(PfpCatalogProdEc pfpCatalogProdEc);

	/**
	 * 新增一般購物類資料
	 * @param pfpCatalogProdEc
	 */
	void savePfpCatalogProdEc(PfpCatalogProdEc pfpCatalogProdEc);

	/**
	 * 刪除不在catalogProdEcSeqList列表內的資料
	 * @param catalogSeq 商品目錄ID
	 * @param catalogProdEcSeqList 不被刪除的名單
	 */
	void deleteNotInPfpCatalogProdEc(String catalogSeq, List<String> catalogProdEcSeqList);

	/**
	 * 新增商品目錄更新紀錄
	 * @param pfpCatalogUploadLog
	 */
	void savePfpCatalogUploadLog(PfpCatalogUploadLog pfpCatalogUploadLog);

	/**
	 * 新增商品目錄更新錯誤紀錄
	 * @param pfpCatalogUploadErrLog
	 */
	void savePfpCatalogUploadErrLog(PfpCatalogUploadErrLog pfpCatalogUploadErrLog);

	/**
	 * 刪除 商品目錄更新錯誤紀錄
	 * @param vo
	 */
	void deletePfpCatalogUploadErrLog(PfpCatalogVO vo);

	/**
	 * 刪除 一般購物類商品清單
	 * @param vo
	 */
	void deletePfpCatalogProdEc(PfpCatalogVO vo);

	/**
	 * 刪除 商品目錄更新紀錄
	 * @param vo
	 */
	void deletePfpCatalogUploadLog(PfpCatalogVO vo);

	/**
	 * 刪除 商品目錄群組
	 * @param vo
	 */
	void deletePfpCatalogGroup(PfpCatalogVO vo);

	/**
	 * 刪除 商品目錄群組明細
	 * @param vo
	 */
	void deletePfpCatalogGroupItem(PfpCatalogVO vo);

	/**
	 * 取得一般購物類商品清單 資料
	 * @param catalogSeq 商品目錄
	 * @param catalogProdSeq 商品編號
	 * @return
	 */
	List<Map<String, Object>> getPfpCatalogProdEc(String catalogSeq, String catalogProdSeq);

}