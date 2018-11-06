package com.pchome.akbpfp.db.dao.catalog.uploadErrLogList;

import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadLog;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogUploadLogVO;

public interface IPfpCatalogUploadErrLogDAO extends IBaseDAO<PfpCatalogUploadLog, String> {

	/**
	 * 查詢目錄商品上傳錯誤記錄清單
	 * @param vo
	 * @return
	 */
	List<Map<String, Object>> getCatalogProdUploadErrList(PfpCatalogUploadLogVO vo);

}