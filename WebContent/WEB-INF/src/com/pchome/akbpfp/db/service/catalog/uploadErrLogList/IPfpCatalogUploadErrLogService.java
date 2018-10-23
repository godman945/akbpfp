package com.pchome.akbpfp.db.service.catalog.uploadErrLogList;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpCatalogUploadLog;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogUploadLogVO;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogVO;

public interface IPfpCatalogUploadErrLogService extends IBaseService<PfpCatalogUploadLog, String> {

	/**
	 * 查詢目錄商品上傳錯誤記錄清單
	 * @param vo
	 * @return
	 */
	List<PfpCatalogVO> getCatalogProdUploadErrList(PfpCatalogUploadLogVO vo);

}