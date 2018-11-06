package com.pchome.akbpfp.db.service.catalog.uploadErrLogList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.catalog.uploadErrLogList.IPfpCatalogUploadErrLogDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadLog;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogProdEcErrorVO;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogUploadLogVO;
import com.pchome.enumerate.catalogprod.EnumEcStockStatusType;
import com.pchome.enumerate.catalogprod.EnumEcUseStatusType;

public class PfpCatalogUploadErrLogService extends BaseService<PfpCatalogUploadLog, String> implements IPfpCatalogUploadErrLogService {

	/**
	 * 查詢目錄商品上傳錯誤記錄清單
	 * @param vo
	 * @return
	 */
	@Override
	public List<PfpCatalogProdEcErrorVO> getCatalogProdUploadErrList(PfpCatalogUploadLogVO vo) {
		System.out.println("getCatalogProdUploadErrList Service");
		List<Map<String, Object>> catalogProdUploadErrList = ((IPfpCatalogUploadErrLogDAO) dao).getCatalogProdUploadErrList(vo);
		
		List<PfpCatalogProdEcErrorVO> pfpCatalogProdEcErrorList = new ArrayList<>();
		for (Map<String, Object> dataMap : catalogProdUploadErrList) {
			PfpCatalogProdEcErrorVO pfpCatalogProdEcErrorVO = new PfpCatalogProdEcErrorVO();
			pfpCatalogProdEcErrorVO.setCatalogProdErrItem(String.valueOf(dataMap.get("catalog_prod_err_item"))); // 上傳錯誤清單ID
			pfpCatalogProdEcErrorVO.setCatalogProdSeq((String) dataMap.get("catalog_prod_seq")); // 商品ID
			pfpCatalogProdEcErrorVO.setEcName((String) dataMap.get("ec_name")); // 商品名稱
			pfpCatalogProdEcErrorVO.setEcImg((String) dataMap.get("ec_img")); // 商品圖像路徑
			pfpCatalogProdEcErrorVO.setEcUrl((String) dataMap.get("ec_url")); // 商品網址
			pfpCatalogProdEcErrorVO.setEcPrice((String) dataMap.get("ec_price")); // 商品價格
			pfpCatalogProdEcErrorVO.setEcDiscountPrice((String) dataMap.get("ec_discount_price")); // 商品特價
			
			// 商品庫存(0:無庫存, 1:有庫存, 2:預購, 3:停售)
			String ecStockStatus = (String) dataMap.get("ec_stock_status");
			if (EnumEcStockStatusType.Out_Of_Stock.getType().equals(ecStockStatus)) {
				pfpCatalogProdEcErrorVO.setEcStockStatus(EnumEcStockStatusType.Out_Of_Stock.getChName());
			} else if (EnumEcStockStatusType.In_Stock.getType().equals(ecStockStatus)) {
				pfpCatalogProdEcErrorVO.setEcStockStatus(EnumEcStockStatusType.In_Stock.getChName());
			} else if (EnumEcStockStatusType.Pre_Order.getType().equals(ecStockStatus)) {
				pfpCatalogProdEcErrorVO.setEcStockStatus(EnumEcStockStatusType.Pre_Order.getChName());
			} else if (EnumEcStockStatusType.Discontinued.getType().equals(ecStockStatus)) {
				pfpCatalogProdEcErrorVO.setEcStockStatus(EnumEcStockStatusType.Discontinued.getChName());
			}
			
			// 商品使用狀態(0:全新, 1:二手, 2:福利品)
			String ecUseStatus = (String) dataMap.get("ec_use_status");
			if (EnumEcUseStatusType.New_Goods.getType().equals(ecUseStatus)) {
				pfpCatalogProdEcErrorVO.setEcUseStatus(EnumEcUseStatusType.New_Goods.getChName());
			} else if (EnumEcUseStatusType.Used_Goods.getType().equals(ecUseStatus)) {
				pfpCatalogProdEcErrorVO.setEcUseStatus(EnumEcUseStatusType.Used_Goods.getChName());
			} else if (EnumEcUseStatusType.Welfare_Goods.getType().equals(ecUseStatus)) {
				pfpCatalogProdEcErrorVO.setEcUseStatus(EnumEcUseStatusType.Welfare_Goods.getChName());
			}
			 
			pfpCatalogProdEcErrorVO.setEcCategory((String) dataMap.get("ec_category")); // 商品組合篩選分類
			
			pfpCatalogProdEcErrorList.add(pfpCatalogProdEcErrorVO);
		}
		return pfpCatalogProdEcErrorList;
	}

}