package com.pchome.akbpfp.db.service.catalog.uploadList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pchome.akbpfp.db.dao.catalog.uploadList.PfpCatalogUploadListDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.pojo.PfpCatalogProdEc;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadErrLog;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadLog;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.service.catalog.PfpCatalogService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.struts2.ajax.ad.AdUtilAjax;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.utils.ImgUtil;

public class PfpCatalogUploadListService extends BaseService<String, String> implements IPfpCatalogUploadListService {

	private PfpCatalogService pfpCatalogService;
	private ISequenceService sequenceService;
	private PfpCatalogUploadListDAO pfpCatalogUploadListDAO;
	
	private String akbPfpServer;
	private String photoDbPathNew;
	
	/**
	 * 依照商品目錄類別，處理相對應的部分
	 */
	@Override
	public Map<String, Object> processCatalogProdJsonData(JSONObject catalogProdJsonData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		String catalogType = catalogProdJsonData.optString("catalog_type");
		
		if (catalogType.isEmpty()) {
			dataMap.put("status", "ERROR");
			dataMap.put("msg", "商品目錄類型資料錯誤!");
			return dataMap;
		}
		
		//後面再改enum
		if ("1".equals(catalogType)) { // 1:一般購物類
			dataMap = processShoppingProd(catalogProdJsonData);
		} else if ("2".equals(catalogType)) { // 2:訂房住宿類
			// 功能待開發
		} else if ("3".equals(catalogType)) { // 3:交通航班類
			// 功能待開發
		} else if ("4".equals(catalogType)) { // 4:房產租售類
			// 功能待開發
		}
		
		return dataMap;
	}

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
	private Map<String, Object> processShoppingProd(JSONObject catalogProdJsonData) throws Exception {
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
		List<String> catalogProdEcSeqList = new ArrayList<String>();
		JSONArray errorPrdItemArray = new JSONArray(); // 記錄總錯誤資料
		int tempErrorPrdItemArrayCount = 0; // 暫存目前總共的錯誤數量
		
		JSONArray catalogProdItemJsonArray = new JSONArray(catalogProdItem);
		for (int i = 0; i < catalogProdItemJsonArray.length(); i++) {
			JSONObject catalogProdItemJson = (JSONObject) catalogProdItemJsonArray.get(i);
			String catalogProdEcSeq = catalogProdItemJson.optString("id"); // id*
			String prodName = catalogProdItemJson.optString("prod_name"); // 商品名稱*
			String prodTitle = catalogProdItemJson.optString("prod_title", " "); // 商品敘述*
			String prodPrice = catalogProdItemJson.optString("prod_price", " "); // 原價
			String prodDiscountPrice = catalogProdItemJson.optString("prod_discount_price"); // 促銷價*
			String prodStockStatus = catalogProdItemJson.optString("prod_stock_status"); // 商品供應情況*
			String prodUseStatus = catalogProdItemJson.optString("prod_use_status"); // 商品使用狀況*
			String prodImgUrl = catalogProdItemJson.optString("prod_img_url"); // 廣告圖像網址*
			String prodUrl = catalogProdItemJson.optString("prod_url"); // 連結網址*
			String prodCategory = catalogProdItemJson.optString("prod_category", " "); // 商品類別

			// 檢查每個欄位
			errorPrdItemArray = checkCatalogProdEcSeq(errorPrdItemArray, catalogProdEcSeq);
			errorPrdItemArray = checkProdName(errorPrdItemArray, catalogProdEcSeq, prodName);
//			errorPrdItemArray = checkProdTitle(errorPrdItemArray, catalogProdEcSeq, prodTitle);
			errorPrdItemArray = checkProdPrice(errorPrdItemArray, catalogProdEcSeq, prodPrice);
			errorPrdItemArray = checkProdDiscountPrice(errorPrdItemArray, catalogProdEcSeq, prodDiscountPrice);
			errorPrdItemArray = checkProdStockStatus(errorPrdItemArray, catalogProdEcSeq, prodStockStatus);
			errorPrdItemArray = checkProdUseStatus(errorPrdItemArray, catalogProdEcSeq, prodUseStatus);
			errorPrdItemArray = checkProdImgUrl(errorPrdItemArray, catalogProdEcSeq, prodImgUrl);
			errorPrdItemArray = checkProdUrl(errorPrdItemArray, catalogProdEcSeq, prodUrl);
			errorPrdItemArray = checkProdCategory(errorPrdItemArray, catalogProdEcSeq, prodCategory);
			
			if (errorPrdItemArray.length() > tempErrorPrdItemArrayCount) {
				// 每一次記錄錯誤陣列長度 超過 暫存的記錄長度，表示這一行資料有新增錯誤項目
				tempErrorPrdItemArrayCount = errorPrdItemArray.length();
				errorNum++;
			} else {
				// 記錄商品明細，寫入一般購物類table
				PfpCatalogProdEc pfpCatalogProdEc = new PfpCatalogProdEc();
				pfpCatalogProdEc.setCatalogProdEcSeq(catalogProdEcSeq); // id
				pfpCatalogProdEc.setPfpCatalog(pfpCatalog); // 商品目錄
				pfpCatalogProdEc.setProdName(prodName); // 商品名稱
				pfpCatalogProdEc.setProdTitle(prodTitle); // 商品敘述
				
				String photoPath = photoDbPathNew + pfpCustomerInfoId + "/catalogProd/" + catalogSeq;
				pfpCatalogProdEc.setProdImg(ImgUtil.processImgPathForCatalogProd(prodImgUrl, photoPath, catalogProdEcSeq)); // 圖片路徑
				
				pfpCatalogProdEc.setProdUrl(prodUrl); // 連結網址
				pfpCatalogProdEc.setProdPrice(Integer.parseInt(prodPrice)); // 原價
				pfpCatalogProdEc.setProdDiscountPrice(Integer.parseInt(prodDiscountPrice)); // 促銷價*
				pfpCatalogProdEc.setProdStockStatus(prodStockStatus); // 商品供應情況
				pfpCatalogProdEc.setProdUseStatus(prodUseStatus); // 商品使用狀況
				pfpCatalogProdEc.setProdCategory(prodCategory); // 商品類別
				pfpCatalogProdEc.setProdStatus("0"); // 商品狀態(0:關閉, 1:開啟)
				pfpCatalogProdEc.setProdCheckStatus("0"); // 商品審核狀態(0:未審核, 1:已審核)
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
					catalogProdEcSeqList.add(pfpCatalogProdEc.getCatalogProdEcSeq());
				}
				
				successNum++;
			}
		}
		
		if ("1".equals(updateWay)) { // 如果是"取代"，刪除table內，不在最新上傳的名單內資料
			pfpCatalogUploadListDAO.deleteNotInPfpCatalogProdEc(catalogSeq, catalogProdEcSeqList);
		}
		
		// 記錄商品目錄更新紀錄
		PfpCatalogUploadLog pfpCatalogUploadLog = new PfpCatalogUploadLog();
		String catalogUploadLogSeq = sequenceService.getId(EnumSequenceTableName.PFP_CATALOG_UPLOAD_LOG, "", 20);
		pfpCatalogUploadLog.setCatalogUploadLogSeq(catalogUploadLogSeq); // 更新紀錄序號
		pfpCatalogUploadLog.setPfpCatalog(pfpCatalog); // 商品目錄
		pfpCatalogUploadLog.setUpdateWay(updateWay); // 更新方式
		pfpCatalogUploadLog.setUpdateContent(catalogProdJsonData.optString("fileName")); // 更新內容
		
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
		if(errorPrdItemArray.length() > 0){
			for (int i = 0; i < errorPrdItemArray.length(); i++) {
				JSONObject errorPrdItemJson = (JSONObject) errorPrdItemArray.get(i);
				PfpCatalogUploadErrLog pfpCatalogUploadErrLog = new PfpCatalogUploadErrLog();
				pfpCatalogUploadErrLog.setPfpCatalogUploadLog(pfpCatalogUploadLog); // 更新紀錄序號
				pfpCatalogUploadErrLog.setCatalogProdEcSeq(errorPrdItemJson.getString("catalog_prod_ec_seq")); // 商品ID
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

	/**
	 * 檢查商品類別
	 * @param errorPrdItemArray
	 * @param catalogProdEcSeq
	 * @param prodCategory
	 * @return
	 * @throws JSONException 
	 */
	private JSONArray checkProdCategory(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodCategory) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";
		
		int prodCategoryLimit = 50;
//		if (prodCategory.isEmpty()) {
//			prodItemErrorMsg += "必填欄位必須輸入資訊。";
//		} else 
		if (prodCategory.length() > prodCategoryLimit) {
			prodItemErrorMsg += "欄位字數超過" + prodCategoryLimit + "個字。";
		}
		
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_ec_seq", catalogProdEcSeq);
			jsonObject.put("catalog_err_item", "prod_category");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", prodCategory);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查連結網址
	 * @param errorPrdItemArray
	 * @param catalogProdEcSeq
	 * @param prodUrl
	 * @return
	 * @throws Exception 
	 */
	private JSONArray checkProdUrl(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodUrl) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";

		if (prodUrl.isEmpty()) {
			prodItemErrorMsg += "必填欄位必須輸入資訊。";
		} else {
			AdUtilAjax adUtilAjax = new AdUtilAjax();
			String checkResultMsg = adUtilAjax.checkAdShowUrl(prodUrl, akbPfpServer);
			if (!checkResultMsg.isEmpty()) {
				prodItemErrorMsg += "請輸入正確的連結網址。";
			}
		}
		
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_ec_seq", catalogProdEcSeq);
			jsonObject.put("catalog_err_item", "prod_url");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", prodUrl);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查廣告圖像網址
	 * @param errorPrdItemArray
	 * @param catalogProdEcSeq
	 * @param prodImgUrl
	 * @return
	 * @throws Exception 
	 */
	private JSONArray checkProdImgUrl(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodImgUrl) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";

		if (prodImgUrl.isEmpty()) {
			prodItemErrorMsg += "必填欄位必須輸入資訊。";
		} else {
			AdUtilAjax adUtilAjax = new AdUtilAjax();
			String checkResultMsg = adUtilAjax.checkAdShowUrl(prodImgUrl, akbPfpServer);
			if (!checkResultMsg.isEmpty()) {
				prodItemErrorMsg += "請輸入正確的廣告圖像網址。";
			}
		}
		
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_ec_seq", catalogProdEcSeq);
			jsonObject.put("catalog_err_item", "prod_img_url");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", prodImgUrl);
			errorPrdItemArray.put(jsonObject);
		}
		
		return errorPrdItemArray;
	}

	/**
	 * 檢查商品使用狀況
	 * @param errorPrdItemArray
	 * @param catalogProdEcSeq
	 * @param prodUseStatus
	 * @return
	 * @throws JSONException 
	 */
	private JSONArray checkProdUseStatus(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodUseStatus) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";
		
		if (prodUseStatus.isEmpty()) {
			prodItemErrorMsg += "必填欄位必須輸入資訊。";
		} else if (!"0".equals(prodUseStatus) && !"1".equals(prodUseStatus)) {
			prodItemErrorMsg += "欄位內容不符合規定。";
		}
		
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_ec_seq", catalogProdEcSeq);
			jsonObject.put("catalog_err_item", "prod_use_status");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", prodUseStatus);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查商品供應情況
	 * @param errorPrdItemArray
	 * @param catalogProdEcSeq
	 * @param prodStockStatus
	 * @return
	 * @throws JSONException 
	 */
	private JSONArray checkProdStockStatus(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodStockStatus) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";
		
		if (prodStockStatus.isEmpty()) {
			prodItemErrorMsg += "必填欄位必須輸入資訊。";
		} else if (!"0".equals(prodStockStatus) && !"1".equals(prodStockStatus)) {
			prodItemErrorMsg += "欄位內容不符合規定。";
		}
		
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_ec_seq", catalogProdEcSeq);
			jsonObject.put("catalog_err_item", "prod_stock_status");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", prodStockStatus);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查促銷價
	 * @param errorPrdItemArray
	 * @param catalogProdEcSeq
	 * @param prodDiscountPrice
	 * @return
	 * @throws JSONException 
	 */
	private JSONArray checkProdDiscountPrice(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodDiscountPrice) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";
		int prodDiscountPriceLimit = 11;
		
		if (prodDiscountPrice.isEmpty()) {
			prodItemErrorMsg += "必填欄位必須輸入資訊。";
		} else if (!StringUtils.isNumeric(prodDiscountPrice)) {
			prodItemErrorMsg += "必須輸入數字。";
		} else if (prodDiscountPrice.length() > prodDiscountPriceLimit) {
			prodItemErrorMsg += "欄位數值超過" + prodDiscountPriceLimit + "位數。";
		}
		
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_ec_seq", catalogProdEcSeq);
			jsonObject.put("catalog_err_item", "prod_discount_price");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", prodDiscountPrice);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查原價
	 * @param errorPrdItemArray
	 * @param catalogProdEcSeq
	 * @param prodPrice
	 * @return
	 * @throws JSONException 
	 */
	private JSONArray checkProdPrice(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodPrice) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";
		int prodPriceLimit = 11;
		
//		if (prodPrice.isEmpty()) {
//			prodItemErrorMsg += "必填欄位必須輸入資訊。";
//		} else 
		if (!prodPrice.isEmpty() && !StringUtils.isNumeric(prodPrice.trim())) {
			prodItemErrorMsg += "必須輸入數字。";
		} else if (prodPrice.length() > prodPriceLimit) {
			prodItemErrorMsg += "欄位數值超過" + prodPriceLimit + "位數。";
		}
		
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_ec_seq", catalogProdEcSeq);
			jsonObject.put("catalog_err_item", "prod_price");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", prodPrice);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查商品敘述
	 * @param errorPrdItemArray
	 * @param catalogProdEcSeq
	 * @param prodTitle
	 * @return
	 * @throws JSONException 
	 */
	private JSONArray checkProdTitle(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodTitle) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";
		int prodTitleLimit = 1024;
		
		if (prodTitle.isEmpty()) {
			prodItemErrorMsg += "必填欄位必須輸入資訊。";
		} else if (prodTitle.length() > prodTitleLimit) {
			prodItemErrorMsg += "欄位字數超過" + prodTitleLimit + "個字。";
		}
		
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_ec_seq", catalogProdEcSeq);
			jsonObject.put("catalog_err_item", "prod_title");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", prodTitle);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查商品名稱
	 * @param errorPrdItemArray 
	 * @param catalogProdEcSeq
	 * @param prodName
	 * @return
	 * @throws JSONException 
	 */
	private JSONArray checkProdName(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodName) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";
		int prodNameLimit = 1024;
		
		if (prodName.isEmpty()) {
			prodItemErrorMsg += "必填欄位必須輸入資訊。";
		} else if (prodName.length() > prodNameLimit) {
			prodItemErrorMsg += "欄位字數超過" + prodNameLimit + "個字。";
		}
	    
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_ec_seq", catalogProdEcSeq);
			jsonObject.put("catalog_err_item", "prod_name");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", prodName);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查商品id
	 * @param errorPrdItemArray
	 * @param catalogProdEcSeq
	 * @return
	 * @throws JSONException 
	 */
	private JSONArray checkCatalogProdEcSeq(JSONArray errorPrdItemArray, String catalogProdEcSeq) throws JSONException {
		if (catalogProdEcSeq.isEmpty()) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("catalog_prod_ec_seq", catalogProdEcSeq);
			jsonObject.put("catalog_err_item", "id");
			jsonObject.put("catalog_err_reason", "必填欄位必須輸入資訊。");
			jsonObject.put("catalog_err_rawdata", catalogProdEcSeq);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 將csv檔案內容轉成json格式
	 * @throws IOException 
	 * @throws JSONException 
	 */
	@Override
	public JSONObject getCSVFileDataToJson(String path) {
		JSONObject catalogProdJsonData = new JSONObject();
		JSONArray prdItemArray = new JSONArray();
		
		BufferedReader brdFile = null;
		try {
			brdFile = new BufferedReader(new FileReader(path)); // 抓CSV檔進java，bufferedReader
			String strLine = null;
			int rowNumber = 0; // 第幾列
			
			while ((strLine = brdFile.readLine()) != null) { // 將CSV檔字串一列一列讀入並存起來直到沒有列為止
				if (++rowNumber == 1) { // 略過第一列
					continue;
				}
				
				String[] prdItem = new String[9]; // 每次重建陣列，避免上一筆資料殘留，固定9個明細
				String[] lineArray = strLine.split(","); // 因為預設是用"，"分開所以用split切開存入字串陣列
				for (int i = 0; i < lineArray.length; i++) { // 將切出來的陣列資料，放入建立好的陣列數量內，避免超出陣列的問題
					prdItem[i] = lineArray[i];
				}
				
				System.out.println("第" + rowNumber + "列:" + strLine);
				
				JSONObject prdItemObject = new JSONObject();
				
				try { // 處理變成科學符號的部分
					prdItemObject.put("id", new BigDecimal(prdItem[0].trim()).toPlainString());
				} catch (Exception e) {
					prdItemObject.put("id", prdItem[0]);
				}
				
				prdItemObject.put("prod_name", prdItem[1]);
//				prdItemObject.put("prod_title", prdItem[2]);
				prdItemObject.put("prod_price", prdItem[2]);
				prdItemObject.put("prod_discount_price", prdItem[3]);
				
				String prodStockStatus = prdItem[4];
				if ("無庫存".equals(prodStockStatus)) {
					prodStockStatus = "0";
				} else if ("有庫存".equals(prodStockStatus)) {
					prodStockStatus = "1";
				}
				prdItemObject.put("prod_stock_status", prodStockStatus);
				
				String prodUseStatus = prdItem[5];
				if ("全新".equals(prodUseStatus)) {
					prodUseStatus = "0";
				} else if ("二手".equals(prodUseStatus)) {
					prodUseStatus = "1";
				}
				prdItemObject.put("prod_use_status", prodUseStatus);
				prdItemObject.put("prod_img_url", prdItem[6]);
				prdItemObject.put("prod_url", prdItem[7]);
				prdItemObject.put("prod_category", prdItem[8]);
				prdItemArray.put(prdItemObject);
			}
			
			catalogProdJsonData.put("catalog_prod_item", prdItemArray);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {
			if (brdFile != null) {
				try {
					brdFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return catalogProdJsonData;
	}
	
	public void setPhotoDbPathNew(String photoDbPathNew) {
		this.photoDbPathNew = photoDbPathNew;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setPfpCatalogService(PfpCatalogService pfpCatalogService) {
		this.pfpCatalogService = pfpCatalogService;
	}

	public void setPfpCatalogUploadListDAO(PfpCatalogUploadListDAO pfpCatalogUploadListDAO) {
		this.pfpCatalogUploadListDAO = pfpCatalogUploadListDAO;
	}

	public void setAkbPfpServer(String akbPfpServer) {
		this.akbPfpServer = akbPfpServer;
	}

}
