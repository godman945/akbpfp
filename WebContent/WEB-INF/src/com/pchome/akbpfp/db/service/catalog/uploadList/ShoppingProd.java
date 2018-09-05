package com.pchome.akbpfp.db.service.catalog.uploadList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pchome.akbpfp.db.dao.catalog.uploadList.IPfpCatalogUploadListDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.pojo.PfpCatalogProdEc;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadErrLog;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadLog;
import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.utils.ImgUtil;

public class ShoppingProd extends APfpCatalogUploadListData {

	private IPfpCatalogService pfpCatalogService;
	private ISequenceService sequenceService;
	private IPfpCatalogUploadListDAO pfpCatalogUploadListDAO;
	
	private String photoDbPathNew;
	
	/**
	 * 處理一般購物類資料
	 * 1.各欄位資料檢查
	 * 2.每筆明細寫進 pfp_catalog_prod_ec(一般購物類)
	 * 3.更新記錄寫進 pfp_catalog_upload_log(商品目錄更新紀錄)
	 * 4.資料檢查有問題的寫進 pfp_catalog_upload_err_log(商品目錄更新錯誤紀錄)
	 * 
	 * @param catalogProdJsonData
	 * @return 
	 * @throws Exception 
	 */
	public Map<String, Object> processCatalogProdJsonData(JSONObject catalogProdJsonData) throws Exception {

		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		// 各欄位資料檢核
		String errorMsg = "";
		String pfpCustomerInfoId = catalogProdJsonData.getString("pfp_customer_info_id");
		String updateWay = catalogProdJsonData.optString("update_way"); // 更新方式 1.取代 2.更新
		String catalogSeq = catalogProdJsonData.optString("catalog_seq"); // 商品目錄編號
		String catalogProdItem = catalogProdJsonData.optString("catalog_prod_item"); // 每一項商品
		if (updateWay.isEmpty() || !("1".equals(updateWay) || "2".equals(updateWay))) {
			errorMsg += "更新方式資料錯誤!";
		}
		
		if (catalogSeq.isEmpty()) {
			errorMsg += "商品目錄編號資料錯誤!";
		}
		
		if (catalogProdItem.isEmpty()) {
			errorMsg += "商品資料錯誤!";
		} 
		
		if (!errorMsg.isEmpty()) {
			dataMap.put("status", "ERROR");
			dataMap.put("msg", errorMsg);
			return dataMap;
		}
		
		// 取得商品目錄資料
		PfpCatalog pfpCatalog = pfpCatalogService.get(catalogSeq);
		
		// 處理每筆商品資料
		int successNum = 0;
		int errorNum = 0;
		// 記錄商品品號列表，不在列表內的資料都刪除
		List<String> catalogProdSeqList = new ArrayList<String>();
		JSONArray errorPrdItemArray = new JSONArray(); // 記錄總錯誤資料
		int tempErrorPrdItemArrayCount = 0; // 暫存目前總共的錯誤數量
		
		JSONArray catalogProdItemJsonArray = new JSONArray(catalogProdItem);
		for (int i = 0; i < catalogProdItemJsonArray.length(); i++) {
			JSONObject catalogProdItemJson = (JSONObject) catalogProdItemJsonArray.get(i);
			String catalogProdSeq = catalogProdItemJson.optString("id"); // id*
			String ecName = catalogProdItemJson.optString("ec_name"); // 商品名稱*
			String ecTitle = catalogProdItemJson.optString("ec_title", " "); // 商品敘述*
			String ecPrice = catalogProdItemJson.optString("ec_price", " "); // 原價
			String ecDiscountPrice = catalogProdItemJson.optString("ec_discount_price"); // 促銷價*
			String ecStockStatus = catalogProdItemJson.optString("ec_stock_status"); // 商品供應情況*
			String ecUseStatus = catalogProdItemJson.optString("ec_use_status"); // 商品使用狀況*
			String ecImgUrl = catalogProdItemJson.optString("ec_img_url"); // 廣告圖像網址*
			String ecUrl = catalogProdItemJson.optString("ec_url"); // 連結網址*
			String ecCategory = catalogProdItemJson.optString("ec_category", " "); // 商品類別

			// 檢查每個欄位
			errorPrdItemArray = super.checkCatalogProdSeq(errorPrdItemArray, catalogProdSeq);
			errorPrdItemArray = super.checkEcName(errorPrdItemArray, catalogProdSeq, ecName);
//			errorPrdItemArray = super.checkEcTitle(errorPrdItemArray, catalogProdSeq, ecTitle);
			errorPrdItemArray = super.checkEcPrice(errorPrdItemArray, catalogProdSeq, ecPrice);
			errorPrdItemArray = super.checkEcDiscountPrice(errorPrdItemArray, catalogProdSeq, ecDiscountPrice);
			errorPrdItemArray = super.checkEcStockStatus(errorPrdItemArray, catalogProdSeq, ecStockStatus);
			errorPrdItemArray = super.checkEcUseStatus(errorPrdItemArray, catalogProdSeq, ecUseStatus);
			errorPrdItemArray = super.checkEcImgUrl(errorPrdItemArray, catalogProdSeq, ecImgUrl);
			errorPrdItemArray = super.checkEcUrl(errorPrdItemArray, catalogProdSeq, ecUrl);
			errorPrdItemArray = super.checkEcCategory(errorPrdItemArray, catalogProdSeq, ecCategory);
			
			if (errorPrdItemArray.length() > tempErrorPrdItemArrayCount) {
				// 每一次記錄錯誤陣列長度 超過 暫存的記錄長度，表示這一行資料有新增錯誤項目
				tempErrorPrdItemArrayCount = errorPrdItemArray.length();
				errorNum++;
			} else {
				// 記錄商品明細，寫入一般購物類table
				PfpCatalogProdEc pfpCatalogProdEc = new PfpCatalogProdEc();
				pfpCatalogProdEc.setCatalogProdSeq(catalogProdSeq); // id
				pfpCatalogProdEc.setPfpCatalog(pfpCatalog); // 商品目錄
				pfpCatalogProdEc.setEcName(ecName); // 商品名稱
				pfpCatalogProdEc.setEcTitle(ecTitle); // 商品敘述
				
				String photoPath = photoDbPathNew + pfpCustomerInfoId + "/catalogProd/" + catalogSeq;
				pfpCatalogProdEc.setEcImg(ImgUtil.processImgPathForCatalogProd(ecImgUrl, photoPath, catalogProdSeq)); // 圖片路徑
				
				pfpCatalogProdEc.setEcUrl(ecUrl); // 連結網址
				pfpCatalogProdEc.setEcPrice(Integer.parseInt(ecPrice)); // 原價
				pfpCatalogProdEc.setEcDiscountPrice(Integer.parseInt(ecDiscountPrice)); // 促銷價*
				pfpCatalogProdEc.setEcStockStatus(ecStockStatus); // 商品供應情況
				pfpCatalogProdEc.setEcUseStatus(ecUseStatus); // 商品使用狀況
				pfpCatalogProdEc.setEcCategory(ecCategory); // 商品類別
				pfpCatalogProdEc.setEcStatus("0"); // 商品狀態(0:關閉, 1:開啟)
				pfpCatalogProdEc.setEcCheckStatus("0"); // 商品審核狀態(0:未審核, 1:已審核)
				pfpCatalogProdEc.setUpdateDate(new Date()); // 更新時間
				pfpCatalogProdEc.setCreateDate(new Date()); // 建立時間

				// 先更新，如果回傳更新筆數為0表示無資料，則新增
				int updateCount = pfpCatalogUploadListDAO.updatePfpCatalogProdEc(pfpCatalogProdEc);
				if (updateCount == 0) {
					pfpCatalogUploadListDAO.savePfpCatalogProdEc(pfpCatalogProdEc);
				}
				
				/* 
				 * 1.取代:上傳新檔案會取代目前的資料，table內未在新檔案中找到的產品將會遭到刪除。
				 * 記錄上傳資料的每筆商品ID，最後不在此List內的資料將被全部刪除
				 */
				if ("1".equals(updateWay)) {
					catalogProdSeqList.add(pfpCatalogProdEc.getCatalogProdSeq());
				}
				
				successNum++;
			}
		}
		
		if ("1".equals(updateWay)) { // 如果是"取代"，刪除table內，不在最新上傳的名單內資料
			pfpCatalogUploadListDAO.deleteNotInPfpCatalogProdEc(catalogSeq, catalogProdSeqList);
		}
		
		// 記錄商品目錄更新紀錄
		PfpCatalogUploadLog pfpCatalogUploadLog = new PfpCatalogUploadLog();
		String catalogUploadLogSeq = sequenceService.getId(EnumSequenceTableName.PFP_CATALOG_UPLOAD_LOG, "", 20);
		pfpCatalogUploadLog.setCatalogUploadLogSeq(catalogUploadLogSeq); // 更新紀錄序號
		pfpCatalogUploadLog.setPfpCatalog(pfpCatalog); // 商品目錄
		pfpCatalogUploadLog.setUpdateWay(updateWay); // 更新方式
		pfpCatalogUploadLog.setUpdateContent(catalogProdJsonData.optString("update_content")); // 更新內容
		
		String updateDatetime = catalogProdJsonData.optString("update_datetime");
		if (updateDatetime.isEmpty()) {
			pfpCatalogUploadLog.setUpdateDatetime(new Date()); // 更新時間
		} else {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			pfpCatalogUploadLog.setUpdateDatetime(formatter.parse(updateDatetime)); // 更新時間
		}
		
		pfpCatalogUploadLog.setErrorNum(errorNum); // 錯誤筆數
		pfpCatalogUploadLog.setSuccessNum(successNum); // 成功筆數
		pfpCatalogUploadLog.setUpdateDate(new Date()); // 更新時間
		pfpCatalogUploadLog.setCreateDate(new Date()); // 建立時間
		pfpCatalogUploadListDAO.savePfpCatalogUploadLog(pfpCatalogUploadLog);
		
		// 記錄錯誤資料
		if (errorPrdItemArray.length() > 0) {
			for (int i = 0; i < errorPrdItemArray.length(); i++) {
				JSONObject errorPrdItemJson = (JSONObject) errorPrdItemArray.get(i);
				PfpCatalogUploadErrLog pfpCatalogUploadErrLog = new PfpCatalogUploadErrLog();
				pfpCatalogUploadErrLog.setPfpCatalogUploadLog(pfpCatalogUploadLog); // 更新紀錄序號
				pfpCatalogUploadErrLog.setCatalogProdSeq(errorPrdItemJson.getString("catalog_prod_seq")); // 商品ID
				pfpCatalogUploadErrLog.setCatalogErrItem(errorPrdItemJson.getString("catalog_err_item")); // 錯誤欄位
				pfpCatalogUploadErrLog.setCatalogErrReason(errorPrdItemJson.getString("catalog_err_reason")); // 錯誤原因
				pfpCatalogUploadErrLog.setCatalogErrRawdata(errorPrdItemJson.getString("catalog_err_rawdata")); // 原始資料
				pfpCatalogUploadErrLog.setUpdateDate(new Date()); // 更新時間
				pfpCatalogUploadErrLog.setCreateDate(new Date()); // 建立時間
				pfpCatalogUploadListDAO.savePfpCatalogUploadErrLog(pfpCatalogUploadErrLog);
			}
		}
		
		dataMap.put("status", "SUCCESS");
		dataMap.put("msg", "一般購物類資料處理完成!");
		dataMap.put("successDataCount", successNum); // 正確總筆數
		dataMap.put("errorDataCount", errorNum); // 錯誤總筆數
		dataMap.put("errorPrdItemArray", errorPrdItemArray.toString()); // 錯誤總記錄
		return dataMap;
	}
	
	public void setPhotoDbPathNew(String photoDbPathNew) {
		this.photoDbPathNew = photoDbPathNew;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setPfpCatalogService(IPfpCatalogService pfpCatalogService) {
		this.pfpCatalogService = pfpCatalogService;
	}

	public void setPfpCatalogUploadListDAO(IPfpCatalogUploadListDAO pfpCatalogUploadListDAO) {
		this.pfpCatalogUploadListDAO = pfpCatalogUploadListDAO;
	}

}