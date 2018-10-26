package com.pchome.akbpfp.db.service.catalog.uploadList;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.pchome.akbpfp.db.dao.catalog.uploadList.IPfpCatalogUploadListDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.pojo.PfpCatalogProdEc;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadErrLog;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadLog;
import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogVO;
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
		String pfpCustomerInfoId = catalogProdJsonData.getString("pfpCustomerInfoId");
		String updateWay = catalogProdJsonData.optString("updateWay"); // 更新方式 1.取代 2.更新
		String catalogSeq = catalogProdJsonData.optString("catalogSeq"); // 商品目錄編號
		String catalogUploadType = catalogProdJsonData.optString("catalogUploadType"); // 上傳方式(1:檔案上傳, 2:自動排程上傳, 3:賣場網址上傳, 4:手動上傳)
		String updateContent = catalogProdJsonData.optString("updateContent");
		String catalogProdItem = catalogProdJsonData.optString("catalogProdItem"); // 每一項商品
		if (updateWay.isEmpty() || !("1".equals(updateWay) || "2".equals(updateWay) || " ".equals(updateWay))) {
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
			ecTitle = " "; // 商品敘述尚未使用，先固定寫入空
			String ecPrice = catalogProdItemJson.optString("ec_price", " "); // 原價
			String ecDiscountPrice = catalogProdItemJson.optString("ec_discount_price"); // 促銷價*
			String ecStockStatus = catalogProdItemJson.optString("ec_stock_status"); // 商品供應情況*
			String ecUseStatus = catalogProdItemJson.optString("ec_use_status"); // 商品使用狀況*
			String ecImgUrl = catalogProdItemJson.optString("ec_img_url"); // 廣告圖像網址*
			String ecImgBase64 = catalogProdItemJson.optString("ec_Img_Base64", " "); // 手動上傳圖片的Base64編碼
			String ecUrl = catalogProdItemJson.optString("ec_url"); // 連結網址*
			String ecCategory = catalogProdItemJson.optString("ec_category", " "); // 商品類別

			String photoPath = photoDbPathNew + pfpCustomerInfoId + "/catalogProd/" + catalogSeq;
			
			// 檢查每個欄位
			errorPrdItemArray = super.checkCatalogProdSeq(errorPrdItemArray, catalogProdSeq);
			errorPrdItemArray = super.checkEcName(errorPrdItemArray, catalogProdSeq, ecName);
//			errorPrdItemArray = super.checkEcTitle(errorPrdItemArray, catalogProdSeq, ecTitle);
			errorPrdItemArray = super.checkEcPrice(errorPrdItemArray, catalogProdSeq, ecPrice);
			errorPrdItemArray = super.checkEcDiscountPrice(errorPrdItemArray, catalogProdSeq, ecDiscountPrice);
			errorPrdItemArray = super.checkEcStockStatus(errorPrdItemArray, catalogProdSeq, ecStockStatus);
			errorPrdItemArray = super.checkEcUseStatus(errorPrdItemArray, catalogProdSeq, ecUseStatus);
			errorPrdItemArray = super.checkEcImgUrl(errorPrdItemArray, photoPath, catalogProdSeq, ecImgUrl, ecImgBase64, catalogUploadType);
			errorPrdItemArray = super.checkEcUrl(errorPrdItemArray, catalogProdSeq, ecUrl);
			errorPrdItemArray = super.checkEcCategory(errorPrdItemArray, catalogProdSeq, ecCategory);
			
			if (errorPrdItemArray.length() > tempErrorPrdItemArrayCount) {
				// 每一次記錄錯誤陣列長度 超過 暫存的記錄長度，表示這一行資料有新增錯誤項目
				tempErrorPrdItemArrayCount = errorPrdItemArray.length();
				errorNum++;
			} else {
				// 檢查輸入的資料是否與DB內完全相同，相同則不做更新、新增處理，避免每次更新就需要重新審核商品
				List<Map<String, Object>> pfpCatalogProdEcList = pfpCatalogUploadListDAO.getPfpCatalogProdEc(catalogSeq, catalogProdSeq);
				
				// 記錄商品明細，寫入一般購物類table
				PfpCatalogProdEc pfpCatalogProdEc = new PfpCatalogProdEc();
				pfpCatalogProdEc.setCatalogProdSeq(catalogProdSeq); // id
				pfpCatalogProdEc.setPfpCatalog(pfpCatalog); // 商品目錄
				pfpCatalogProdEc.setEcName(ecName); // 商品名稱
				pfpCatalogProdEc.setEcTitle(ecTitle); // 商品敘述
				
				if (StringUtils.isBlank(ecImgBase64)) { // ecImgBase64為空表示非手動上傳，會有圖片網址需下載圖片
					pfpCatalogProdEc.setEcImg(ImgUtil.processImgPathForCatalogProd(ecImgUrl, photoPath, catalogProdSeq)); // 圖片路徑
				} else {
					pfpCatalogProdEc.setEcImg(ImgUtil.processImgBase64StringToImage(ecImgBase64, photoPath, catalogProdSeq));
				}
				
				pfpCatalogProdEc.setEcImgRegion(ImgUtil.getImgLongWidthCode(photoDbPathNew.replace("img/user/", "") + pfpCatalogProdEc.getEcImg())); // 商品影像長寬(V/H)
				pfpCatalogProdEc.setEcImgMd5(ImgUtil.getImgMD5Code(photoDbPathNew.replace("img/user/", "") + pfpCatalogProdEc.getEcImg())); // 商品影像MD5
				pfpCatalogProdEc.setEcUrl(ecUrl); // 連結網址
				if (StringUtils.isBlank(ecPrice)) { // 沒有原價則帶入促銷價
					pfpCatalogProdEc.setEcPrice(Integer.parseInt(ecDiscountPrice)); // 促銷價*
				} else {
					pfpCatalogProdEc.setEcPrice(Integer.parseInt(ecPrice)); // 原價
				}
				pfpCatalogProdEc.setEcDiscountPrice(Integer.parseInt(ecDiscountPrice)); // 促銷價*
				pfpCatalogProdEc.setEcStockStatus(ecStockStatus); // 商品供應情況
				pfpCatalogProdEc.setEcUseStatus(ecUseStatus); // 商品使用狀況
				pfpCatalogProdEc.setEcCategory(ecCategory); // 商品類別
				pfpCatalogProdEc.setEcStatus("1"); // 商品狀態(0:關閉, 1:開啟)
				pfpCatalogProdEc.setEcCheckStatus("0"); // 商品審核狀態(0:未審核, 1:已審核)
				pfpCatalogProdEc.setUpdateDate(new Date()); // 更新時間
				pfpCatalogProdEc.setCreateDate(new Date()); // 建立時間

				// 紀錄本次上傳哪些商品編號，最後不在此List內的資料將被全部刪除，updateWay為1:"取代"會需要使用
				catalogProdSeqList.add(pfpCatalogProdEc.getCatalogProdSeq());
				successNum++; // 成功筆數
				
				// 檢查輸入的資料是否與DB內完全相同，相同則不做更新、新增處理，避免每次更新就需要重新審核商品
				if (pfpCatalogProdEcList.size() > 0) {
					Map<String, Object> prodEcMap = new HashMap<String, Object>();
					prodEcMap = pfpCatalogProdEcList.get(0);
					if (catalogProdSeq.equals((String) prodEcMap.get("catalog_prod_seq"))
							&& catalogSeq.equals((String) prodEcMap.get("catalog_seq"))
							&& ecName.equals((String) prodEcMap.get("ec_name"))
							&& ecTitle.equals((String) prodEcMap.get("ec_title"))
							&& pfpCatalogProdEc.getEcImg().equals((String) prodEcMap.get("ec_img"))
							&& pfpCatalogProdEc.getEcImgRegion().equals((String) prodEcMap.get("ec_img_region"))
							&& pfpCatalogProdEc.getEcImgMd5().equals((String) prodEcMap.get("ec_img_md5"))
							&& ecUrl.equals((String) prodEcMap.get("ec_url"))
							&& ecPrice.equals(String.valueOf((int) prodEcMap.get("ec_price")))
							&& ecDiscountPrice.equals(String.valueOf((int) prodEcMap.get("ec_discount_price")))
							&& ecStockStatus.equals((String) prodEcMap.get("ec_stock_status"))
							&& ecUseStatus.equals((String) prodEcMap.get("ec_use_status"))
							&& ecCategory.equals((String) prodEcMap.get("ec_category"))) {
						continue;
					}
				}
				
				// 先更新，如果回傳更新筆數為0表示無資料，則新增
				int updateCount = pfpCatalogUploadListDAO.updatePfpCatalogProdEc(pfpCatalogProdEc);
				if (updateCount == 0) {
					pfpCatalogUploadListDAO.savePfpCatalogProdEc(pfpCatalogProdEc);
				}
				
			}
		}
		
		if ("1".equals(updateWay)) { // 如果是"取代"，刪除table內，不在本次上傳的名單內資料
			pfpCatalogUploadListDAO.deleteNotInPfpCatalogProdEc(catalogSeq, catalogProdSeqList);
		}
		
		// 記錄商品目錄更新紀錄pfp_catalog_upload_log
		PfpCatalogUploadLog pfpCatalogUploadLog = new PfpCatalogUploadLog();
		String catalogUploadLogSeq = sequenceService.getId(EnumSequenceTableName.PFP_CATALOG_UPLOAD_LOG, "", 20);
		pfpCatalogUploadLog.setCatalogUploadLogSeq(catalogUploadLogSeq); // 更新紀錄序號
		pfpCatalogUploadLog.setPfpCatalog(pfpCatalog); // 商品目錄
		pfpCatalogUploadLog.setUpdateWay(updateWay); // 更新方式
		pfpCatalogUploadLog.setUpdateContent(updateContent); // 更新內容
		
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
		
		// 記錄錯誤資料pfp_catalog_upload_err_log
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
		
		// 更新 pfp_catalog "商品目錄" 資料
		PfpCatalogVO pfpCatalogVO = new PfpCatalogVO();
		pfpCatalogVO.setCatalogSeq(catalogSeq);
		pfpCatalogVO.setPfpCustomerInfoId(pfpCustomerInfoId);
		pfpCatalogVO.setCatalogUploadType(catalogUploadType);
		pfpCatalogVO.setUploadContent(updateContent);
		pfpCatalogService.updatePfpCatalogForShoppingProd(pfpCatalogVO);
		
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