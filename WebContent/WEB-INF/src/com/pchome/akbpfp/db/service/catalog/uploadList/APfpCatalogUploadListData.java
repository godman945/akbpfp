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
	 * @param catalogProdEcSeq
	 * @param prodCategory
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray checkProdCategory(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodCategory) throws JSONException {
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
	public JSONArray checkProdUrl(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodUrl) throws Exception {
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
	public JSONArray checkProdImgUrl(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodImgUrl) throws Exception {
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
	public JSONArray checkProdUseStatus(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodUseStatus) throws JSONException {
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
	public JSONArray checkProdStockStatus(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodStockStatus) throws JSONException {
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
	public JSONArray checkProdDiscountPrice(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodDiscountPrice) throws JSONException {
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
	public JSONArray checkProdPrice(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodPrice) throws JSONException {
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
	public JSONArray checkProdTitle(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodTitle) throws JSONException {
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
	public JSONArray checkProdName(JSONArray errorPrdItemArray, String catalogProdEcSeq, String prodName) throws JSONException {
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
	public JSONArray checkCatalogProdEcSeq(JSONArray errorPrdItemArray, String catalogProdEcSeq) throws JSONException {
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
	
	public void setAkbPfpServer(String akbPfpServer) {
		this.akbPfpServer = akbPfpServer;
	}
}
