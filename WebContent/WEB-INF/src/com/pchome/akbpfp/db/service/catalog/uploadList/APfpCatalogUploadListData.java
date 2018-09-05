package com.pchome.akbpfp.db.service.catalog.uploadList;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pchome.akbpfp.struts2.ajax.ad.AdUtilAjax;

public abstract class APfpCatalogUploadListData {

	private String akbPfpServer;
	
	public abstract Object processCatalogProdJsonData(JSONObject catalogProdJsonData) throws Exception;

	/**
	 * 檢查商品類別
	 * @param errorPrdItemArray
	 * @param catalogProdSeq
	 * @param ecCategory
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray checkEcCategory(JSONArray errorPrdItemArray, String catalogProdSeq, String ecCategory) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";
		
		int ecCategoryLimit = 50;
//		if (ecCategory.isEmpty()) {
//			prodItemErrorMsg += "必填欄位必須輸入資訊。";
//		} else 
		if (ecCategory.length() > ecCategoryLimit) {
			prodItemErrorMsg += "欄位字數超過" + ecCategoryLimit + "個字。";
		}
		
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_seq", catalogProdSeq);
			jsonObject.put("catalog_err_item", "ec_category");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", ecCategory);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查連結網址
	 * @param errorPrdItemArray
	 * @param catalogProdSeq
	 * @param ecUrl
	 * @return
	 * @throws Exception 
	 */
	public JSONArray checkEcUrl(JSONArray errorPrdItemArray, String catalogProdSeq, String ecUrl) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";

		if (ecUrl.isEmpty()) {
			prodItemErrorMsg += "必填欄位必須輸入資訊。";
		} else {
			AdUtilAjax adUtilAjax = new AdUtilAjax();
			String checkResultMsg = adUtilAjax.checkAdShowUrl(ecUrl, akbPfpServer);
			if (!checkResultMsg.isEmpty()) {
				prodItemErrorMsg += "請輸入正確的連結網址。";
			}
		}
		
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_seq", catalogProdSeq);
			jsonObject.put("catalog_err_item", "ec_url");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", ecUrl);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查廣告圖像網址
	 * @param errorPrdItemArray
	 * @param catalogProdSeq
	 * @param ecImgUrl
	 * @return
	 * @throws Exception 
	 */
	public JSONArray checkEcImgUrl(JSONArray errorPrdItemArray, String catalogProdSeq, String ecImgUrl) throws Exception {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";

		if (ecImgUrl.isEmpty()) {
			prodItemErrorMsg += "必填欄位必須輸入資訊。";
		} else {
			AdUtilAjax adUtilAjax = new AdUtilAjax();
			String checkResultMsg = adUtilAjax.checkAdShowUrl(ecImgUrl, akbPfpServer);
			if (!checkResultMsg.isEmpty()) {
				prodItemErrorMsg += "請輸入正確的廣告圖像網址。";
			}
		}
		
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_seq", catalogProdSeq);
			jsonObject.put("catalog_err_item", "ec_img_url");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", ecImgUrl);
			errorPrdItemArray.put(jsonObject);
		}
		
		return errorPrdItemArray;
	}

	/**
	 * 檢查商品使用狀況
	 * @param errorPrdItemArray
	 * @param catalogProdSeq
	 * @param ecUseStatus
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray checkEcUseStatus(JSONArray errorPrdItemArray, String catalogProdSeq, String ecUseStatus) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";
		
		if (ecUseStatus.isEmpty()) {
			prodItemErrorMsg += "必填欄位必須輸入資訊。";
		} else if (!"0".equals(ecUseStatus) && !"1".equals(ecUseStatus)) {
			prodItemErrorMsg += "欄位內容不符合規定。";
		}
		
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_seq", catalogProdSeq);
			jsonObject.put("catalog_err_item", "ec_use_status");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", ecUseStatus);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查商品供應情況
	 * @param errorPrdItemArray
	 * @param catalogProdSeq
	 * @param ecStockStatus
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray checkEcStockStatus(JSONArray errorPrdItemArray, String catalogProdSeq, String ecStockStatus) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";
		
		if (ecStockStatus.isEmpty()) {
			prodItemErrorMsg += "必填欄位必須輸入資訊。";
		} else if (!"0".equals(ecStockStatus) && !"1".equals(ecStockStatus)) {
			prodItemErrorMsg += "欄位內容不符合規定。";
		}
		
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_seq", catalogProdSeq);
			jsonObject.put("catalog_err_item", "ec_stock_status");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", ecStockStatus);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查促銷價
	 * @param errorPrdItemArray
	 * @param catalogProdSeq
	 * @param ecDiscountPrice
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray checkEcDiscountPrice(JSONArray errorPrdItemArray, String catalogProdSeq, String ecDiscountPrice) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";
		int ecDiscountPriceLimit = 11;
		
		if (ecDiscountPrice.isEmpty()) {
			prodItemErrorMsg += "必填欄位必須輸入資訊。";
		} else if (!StringUtils.isNumeric(ecDiscountPrice)) {
			prodItemErrorMsg += "必須輸入數字。";
		} else if (ecDiscountPrice.length() > ecDiscountPriceLimit) {
			prodItemErrorMsg += "欄位數值超過" + ecDiscountPriceLimit + "位數。";
		}
		
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_seq", catalogProdSeq);
			jsonObject.put("catalog_err_item", "ec_discount_price");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", ecDiscountPrice);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查原價
	 * @param errorPrdItemArray
	 * @param catalogProdSeq
	 * @param ecPrice
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray checkEcPrice(JSONArray errorPrdItemArray, String catalogProdSeq, String ecPrice) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";
		int ecPriceLimit = 11;
		
//		if (ecPrice.isEmpty()) {
//			prodItemErrorMsg += "必填欄位必須輸入資訊。";
//		} else 
		if (!ecPrice.isEmpty() && !StringUtils.isNumeric(ecPrice.trim())) {
			prodItemErrorMsg += "必須輸入數字。";
		} else if (ecPrice.length() > ecPriceLimit) {
			prodItemErrorMsg += "欄位數值超過" + ecPriceLimit + "位數。";
		}
		
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_seq", catalogProdSeq);
			jsonObject.put("catalog_err_item", "ec_price");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", ecPrice);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查商品敘述
	 * @param errorPrdItemArray
	 * @param catalogProdSeq
	 * @param ecTitle
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray checkEcTitle(JSONArray errorPrdItemArray, String catalogProdSeq, String ecTitle) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";
		int ecTitleLimit = 1024;
		
		if (ecTitle.isEmpty()) {
			prodItemErrorMsg += "必填欄位必須輸入資訊。";
		} else if (ecTitle.length() > ecTitleLimit) {
			prodItemErrorMsg += "欄位字數超過" + ecTitleLimit + "個字。";
		}
		
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_seq", catalogProdSeq);
			jsonObject.put("catalog_err_item", "ec_title");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", ecTitle);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查商品名稱
	 * @param errorPrdItemArray 
	 * @param catalogProdSeq
	 * @param ecName
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray checkEcName(JSONArray errorPrdItemArray, String catalogProdSeq, String ecName) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String prodItemErrorMsg = "";
		int ecNameLimit = 1024;
		
		if (ecName.isEmpty()) {
			prodItemErrorMsg += "必填欄位必須輸入資訊。";
		} else if (ecName.length() > ecNameLimit) {
			prodItemErrorMsg += "欄位字數超過" + ecNameLimit + "個字。";
		}
	    
		if (!prodItemErrorMsg.isEmpty()) {
			jsonObject.put("catalog_prod_seq", catalogProdSeq);
			jsonObject.put("catalog_err_item", "ec_name");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", ecName);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查商品id
	 * @param errorPrdItemArray
	 * @param catalogProdSeq
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray checkCatalogProdSeq(JSONArray errorPrdItemArray, String catalogProdSeq) throws JSONException {
		if (catalogProdSeq.isEmpty()) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("catalog_prod_seq", catalogProdSeq);
			jsonObject.put("catalog_err_item", "id");
			jsonObject.put("catalog_err_reason", "必填欄位必須輸入資訊。");
			jsonObject.put("catalog_err_rawdata", catalogProdSeq);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}
	
	public void setAkbPfpServer(String akbPfpServer) {
		this.akbPfpServer = akbPfpServer;
	}
}
