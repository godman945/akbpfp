package com.pchome.akbpfp.db.service.catalog;

import java.util.List;

import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogVO;

public interface IPfpCatalogService extends IBaseService<PfpCatalog, String> {

	/**
	 * 查詢商品目錄清單
	 * @param PfpCatalogVO
	 * @return
	 */
	List<PfpCatalogVO> getPfpCatalogList(PfpCatalogVO vo);

	/**
	 * 刪除商品目錄
	 * @param PfpCatalogVO
	 * @return
	 */
	List<PfpCatalogVO> deletePfpCatalog(PfpCatalogVO vo);

}
