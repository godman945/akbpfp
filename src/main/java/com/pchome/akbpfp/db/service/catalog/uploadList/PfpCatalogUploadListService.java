package com.pchome.akbpfp.db.service.catalog.uploadList;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.opencsv.CSVReader;
import com.pchome.akbpfp.db.dao.catalog.uploadList.IPfpCatalogUploadListDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadLog;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.service.accesslog.AdmAccesslogService;
import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.db.vo.catalog.PfpCatalogVO;
import com.pchome.akbpfp.db.vo.catalog.uploadList.PfpCatalogProdEcErrorVO;
import com.pchome.akbpfp.db.vo.catalog.uploadList.PfpCatalogUploadListVO;
import com.pchome.akbpfp.db.vo.catalog.uploadList.PfpCatalogUploadLogVO;
import com.pchome.enumerate.ad.EnumPfpCatalog;
import com.pchome.enumerate.ad.EnumPfpCatalogUploadType;
import com.pchome.enumerate.catalogprod.EnumEcStockStatusType;
import com.pchome.enumerate.catalogprod.EnumEcUseStatusType;
import com.pchome.enumerate.prod.EnumEcCsvCheck;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.rmi.accesslog.EnumAccesslogAction;
import com.pchome.utils.CommonUtils;

public class PfpCatalogUploadListService extends BaseService<String, String> implements IPfpCatalogUploadListService {

//	private ShoppingProd shoppingProd;
	private IPfpCatalogService pfpCatalogService;
	private ISequenceService sequenceService;
	private IPfpCatalogUploadListDAO pfpCatalogUploadListDAO;
	private String akbPfpServer;
	private String photoDbPathNew;
	private String catalogProdCsvFilePath;
	private String catalogProdCsvFileBackupPath;
	private AdmAccesslogService accesslogService;
	/**
	 * 檢查檔案格式是否為我們提供的CSV檔格式
	 * @param vo
	 * @return
	 */
	@SuppressWarnings("finally")
	public Map<String, Object> checkCSVFile(PfpCatalogUploadListVO vo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("status", "ERROR");
		try {
			File file = new File(vo.getFileUploadPath());
			InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"big5");
			CSVReader reader = new CSVReader(isr);
			String [] csvTitleArray;
			boolean flagCheck = true;
			
			if (vo.getCatalogType().equals(EnumPfpCatalog.CATALOG_SHOPPING.getType())) {
				while ((csvTitleArray = reader.readNext()) != null) {
					// 1.判斷csv標題是否重複
					HashSet<String> titleSet = new HashSet<>(Arrays.asList(csvTitleArray));
					if (titleSet.size() < csvTitleArray.length) {
						flagCheck = false;
						break;
					}
					
					// 2.檢查必填欄位是否存在
					int countMustCount = 0;
					for (String csvTitleStr : csvTitleArray) {
						for (EnumEcCsvCheck enumEcCsvCheck : EnumEcCsvCheck.values()) {
							if (enumEcCsvCheck.isNeedFlag() && csvTitleStr.equals(enumEcCsvCheck.getTitleName())) {
								countMustCount = countMustCount + 1;
							}
						}
					}
					
					if (countMustCount != 7) { // 必填欄位，不為7欄則錯
						flagCheck = false;
					}
					break;
				}
			}
			
			if (flagCheck) {
				dataMap.put("status", "SUCCESS");
			}

			if (reader != null) {
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("status", "ERROR");
			return dataMap;
		} finally {
			return	dataMap;
		}
	}
	
	/**
	 * 查詢目錄商品上傳錯誤記錄清單
	 * @param vo
	 * @return
	 */
	@Override
	public List<PfpCatalogProdEcErrorVO> getCatalogProdUploadErrList(PfpCatalogProdEcErrorVO vo) {
		// 總數與明細分開執行，增加查詢速度
		int totalCount = pfpCatalogUploadListDAO.getCatalogProdUploadErrListTotalCount(vo);
		vo.setTotalCount(totalCount);
		vo.setPageCount(CommonUtils.getTotalPage(vo.getTotalCount(), vo.getPageSize()));
		
		List<Map<String, Object>> catalogProdUploadErrList = pfpCatalogUploadListDAO.getCatalogProdUploadErrList(vo);
		
		List<PfpCatalogProdEcErrorVO> pfpCatalogProdEcErrorList = new ArrayList<>();
		for (Map<String, Object> dataMap : catalogProdUploadErrList) {
			PfpCatalogProdEcErrorVO pfpCatalogProdEcErrorVO = new PfpCatalogProdEcErrorVO();
			pfpCatalogProdEcErrorVO.setCatalogProdErrItem(String.valueOf(dataMap.get("catalog_prod_err_item"))); // 上傳錯誤清單ID
			
			pfpCatalogProdEcErrorVO.setCatalogProdSeqErrstatus((String) dataMap.get("catalog_prod_seq_errstatus"));
			pfpCatalogProdEcErrorVO.setCatalogProdSeq((String) dataMap.get("catalog_prod_seq")); // 商品ID
			
			pfpCatalogProdEcErrorVO.setEcNameErrstatus((String) dataMap.get("ec_name_errstatus"));
			pfpCatalogProdEcErrorVO.setEcName((String) dataMap.get("ec_name")); // 商品名稱
			
			pfpCatalogProdEcErrorVO.setEcImgErrstatus((String) dataMap.get("ec_img_errstatus"));
			String ecImg = (String) dataMap.get("ec_img");
			if (StringUtils.isBlank(pfpCatalogProdEcErrorVO.getEcImgErrstatus())) {
				pfpCatalogProdEcErrorVO.setEcImg(akbPfpServer + ecImg); // 商品圖像路徑
			} else {
				pfpCatalogProdEcErrorVO.setEcImg(ecImg); // 商品圖像錯誤訊息
			}
			
			pfpCatalogProdEcErrorVO.setEcUrlErrstatus((String) dataMap.get("ec_url_errstatus"));
			pfpCatalogProdEcErrorVO.setEcUrl((String) dataMap.get("ec_url")); // 商品網址
			
			pfpCatalogProdEcErrorVO.setEcPriceErrstatus((String) dataMap.get("ec_price_errstatus"));
			pfpCatalogProdEcErrorVO.setEcPrice((String) dataMap.get("ec_price")); // 商品價格
			
			pfpCatalogProdEcErrorVO.setEcDiscountPriceErrstatus((String) dataMap.get("ec_discount_price_errstatus"));
			pfpCatalogProdEcErrorVO.setEcDiscountPrice((String) dataMap.get("ec_discount_price")); // 商品特價
			
			pfpCatalogProdEcErrorVO.setEcStockStatusErrstatus((String) dataMap.get("ec_stock_status_errstatus"));
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
			} else {
				pfpCatalogProdEcErrorVO.setEcStockStatus(ecStockStatus);
			}
			
			pfpCatalogProdEcErrorVO.setEcUseStatusErrstatus((String) dataMap.get("ec_use_status_errstatus"));
			// 商品使用狀態(0:全新, 1:二手, 2:福利品)
			String ecUseStatus = (String) dataMap.get("ec_use_status");
			if (EnumEcUseStatusType.New_Goods.getType().equals(ecUseStatus)) {
				pfpCatalogProdEcErrorVO.setEcUseStatus(EnumEcUseStatusType.New_Goods.getChName());
			} else if (EnumEcUseStatusType.Used_Goods.getType().equals(ecUseStatus)) {
				pfpCatalogProdEcErrorVO.setEcUseStatus(EnumEcUseStatusType.Used_Goods.getChName());
			} else if (EnumEcUseStatusType.Welfare_Goods.getType().equals(ecUseStatus)) {
				pfpCatalogProdEcErrorVO.setEcUseStatus(EnumEcUseStatusType.Welfare_Goods.getChName());
			} else {
				pfpCatalogProdEcErrorVO.setEcUseStatus(ecUseStatus);
			}
			
			pfpCatalogProdEcErrorVO.setEcCategoryErrstatus((String) dataMap.get("ec_category_errstatus"));
			pfpCatalogProdEcErrorVO.setEcCategory((String) dataMap.get("ec_category")); // 商品組合篩選分類
			
			pfpCatalogProdEcErrorList.add(pfpCatalogProdEcErrorVO);
		}
		return pfpCatalogProdEcErrorList;
	}
	
	/**
	 * 刪除 商品目錄更新紀錄
	 * @param vo
	 */
	@Override
	public void deletePfpCatalogUploadLog(PfpCatalogVO vo) {
		pfpCatalogUploadListDAO.deletePfpCatalogUploadLog(vo);
	}

	/**
	 * 刪除 一般購物類商品上傳錯誤清單
	 * @param vo
	 */
	@Override
	public void deletePfpCatalogProdEcError(PfpCatalogVO vo) {
		pfpCatalogUploadListDAO.deletePfpCatalogProdEcError(vo);
	}
	
	/**
	 * 刪除 商品目錄更新錯誤紀錄
	 * @param vo
	 */
	@Override
	public void deletePfpCatalogUploadErrLog(PfpCatalogVO vo) {
		pfpCatalogUploadListDAO.deletePfpCatalogUploadErrLog(vo);
	}

	/**
	 * 刪除 商品目錄設定 
	 * @param vo
	 */
	@Override
	public void deletePfpCatalogSetup(PfpCatalogVO vo) {
		pfpCatalogUploadListDAO.deletePfpCatalogSetup(vo);
	}
	
	/**
	 * 新增log記錄
	 * @throws Exception 
	 */
	@Override
	public void savePfpCatalogUploadLog(PfpCatalogUploadLogVO vo) throws Exception {
		PfpCatalogUploadLog pfpCatalogUploadLog = new PfpCatalogUploadLog();
		Date date = new Date();
		
		String catalogUploadLogSeq = sequenceService.getId(EnumSequenceTableName.PFP_CATALOG_UPLOAD_LOG, "", 20);
		pfpCatalogUploadLog.setCatalogUploadLogSeq(catalogUploadLogSeq); // 更新紀錄序號
		
		PfpCatalog pfpCatalog = pfpCatalogService.get(vo.getCatalogSeq());
		pfpCatalogUploadLog.setPfpCatalog(pfpCatalog); // 商品目錄
		pfpCatalogUploadLog.setUpdateWay(vo.getUpdateWay()); // 更新方式
		pfpCatalogUploadLog.setUpdateContent(vo.getUpdateContent()); // 更新內容
		
		// 更新時間
		if (vo.getUpdateDatetime() != null) {
			pfpCatalogUploadLog.setUpdateDatetime(vo.getUpdateDatetime());
		} else {
			pfpCatalogUploadLog.setUpdateDatetime(date);
		}
		
		pfpCatalogUploadLog.setErrorNum(vo.getErrorNum()); // 錯誤筆數
		pfpCatalogUploadLog.setSuccessNum(vo.getSuccessNum()); // 成功筆數
		pfpCatalogUploadLog.setUpdateDate(date); // 更新時間
		pfpCatalogUploadLog.setCreateDate(date); // 建立時間
		
		pfpCatalogUploadListDAO.savePfpCatalogUploadLog(pfpCatalogUploadLog);
		
		
		//accesslog
		log.info("success:"+vo.getSuccessNum() + " fail:"+vo.getErrorNum());
		for (EnumPfpCatalogUploadType enumPfpCatalogUploadType : EnumPfpCatalogUploadType.values()) {
			if (enumPfpCatalogUploadType.getType().equals(pfpCatalogUploadLog.getUpdateWay())) {
				String message = pfpCatalog.getCatalogName() + "=>檔案更新：成功 "+vo.getSuccessNum()+",失敗 "+vo.getErrorNum();
				accesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, vo.getIdPchome(),vo.getCustomerInfoId(), vo.getUserId(), vo.getRemoteAddr());
				break;
			}
		}
		
		
	}

	/**
	 * 檢查商品編號是否在此目錄下已重複
	 * @param catalogSeq 商品目錄
	 * @param catalogProdSeq 商品編號
	 * @return 重複:1  不重複:0
	 */
	@Override
	public int checkCatalogProdSeq(String catalogSeq, String catalogProdSeq) {
		// 取得商品清單,DAO之後應該調整寫到 pfp_catalog_prod_ec 的DAO去
		List<Map<String, Object>> pfpCatalogProdEcList = pfpCatalogUploadListDAO.getPfpCatalogProdEc(catalogSeq, catalogProdSeq);
		return pfpCatalogProdEcList.size();
	}
	
	/**
	 * 刪除哪位客戶的哪個商品目錄的圖片資料夾內容
	 * @param vo.getPfpCustomerInfoId() pfp_id
	 * @param vo.getCatalogSeq() 商品目錄編號
	 */
	@Override
	public void deleteCatalogProdImgFolderAndData(PfpCatalogVO vo) {
		String folderPath = photoDbPathNew + vo.getPfpCustomerInfoId() + "/catalogProd/" + vo.getCatalogSeq();
		FileUtils.deleteQuietly(new File(folderPath));
	}
	
	/**
	 * 刪除哪位客戶的哪個商品目錄上傳過的CSV及備份資料夾
	 * @param vo.getPfpCustomerInfoId() pfp_id
	 * @param vo.getCatalogSeq() 商品目錄編號
	 */
	@Override
	public void deleteCatalogProdCSVFolderAndData(PfpCatalogVO vo) {
		String folderPath = catalogProdCsvFilePath + vo.getPfpCustomerInfoId() + "/" + vo.getCatalogSeq();
		FileUtils.deleteQuietly(new File(folderPath));
		
		String folderBackupPath = catalogProdCsvFileBackupPath + vo.getPfpCustomerInfoId() + "/" + vo.getCatalogSeq();
		FileUtils.deleteQuietly(new File(folderBackupPath));
	}
	
//	public void setShoppingProd(ShoppingProd shoppingProd) {
//		this.shoppingProd = shoppingProd;
//	}
//
//	public ShoppingProd getShoppingProd() {
//		return shoppingProd;
//	}

	public void setPfpCatalogUploadListDAO(IPfpCatalogUploadListDAO pfpCatalogUploadListDAO) {
		this.pfpCatalogUploadListDAO = pfpCatalogUploadListDAO;
	}
	
	public void setPfpCatalogService(IPfpCatalogService pfpCatalogService) {
		this.pfpCatalogService = pfpCatalogService;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setPhotoDbPathNew(String photoDbPathNew) {
		this.photoDbPathNew = photoDbPathNew;
	}

	public void setCatalogProdCsvFilePath(String catalogProdCsvFilePath) {
		this.catalogProdCsvFilePath = catalogProdCsvFilePath;
	}

	public void setAkbPfpServer(String akbPfpServer) {
		this.akbPfpServer = akbPfpServer;
	}

	public void setCatalogProdCsvFileBackupPath(String catalogProdCsvFileBackupPath) {
		this.catalogProdCsvFileBackupPath = catalogProdCsvFileBackupPath;
	}

	public AdmAccesslogService getAccesslogService() {
		return accesslogService;
	}

	public void setAccesslogService(AdmAccesslogService accesslogService) {
		this.accesslogService = accesslogService;
	}


}