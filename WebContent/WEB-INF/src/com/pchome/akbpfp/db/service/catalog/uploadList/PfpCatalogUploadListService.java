package com.pchome.akbpfp.db.service.catalog.uploadList;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pchome.akbpfp.db.dao.catalog.uploadList.IPfpCatalogUploadListDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.pojo.PfpCatalogProdEc;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.struts2.ajax.ad.AdUtilAjax;

public class PfpCatalogUploadListService extends BaseService<String, String> implements IPfpCatalogUploadListService {

	private String akbPfpServer;
	
//	private IPfpCatalogUploadListDAO pfpCatalogUploadListDAO;
	
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
			System.out.println("1:一般購物類");
			dataMap = processShoppingProd(catalogProdJsonData);
		} else if ("2".equals(catalogType)) { // 2:訂房住宿類
			// 功能待開發
			System.out.println("2:訂房住宿類");
		} else if ("3".equals(catalogType)) { // 3:交通航班類
			// 功能待開發
			System.out.println("3:交通航班類");
		} else if ("4".equals(catalogType)) { // 4:房產租售類
			// 功能待開發
			System.out.println("4:房產租售類");
		}
		
		return dataMap;
	}

	/**
	 * 處理一般購物類資料
	 * @param catalogProdJsonData
	 * @return 
	 * @throws Exception 
	 */
	private Map<String, Object> processShoppingProd(JSONObject catalogProdJsonData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		// 各欄位資料檢核
		String errorMsg = "";
		String updateWay = catalogProdJsonData.optString("update_way"); // 更新方式
		String catalogSeq = catalogProdJsonData.optString("catalog_seq"); // 商品目錄編號
		String catalogProdItem = catalogProdJsonData.optString("catalog_prod_item"); // 每一項商品
		if (updateWay.isEmpty()) {
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
		
		// 處理每筆商品資料
		int successDataCount = 0;
		int errorDataCount = 0;
		JSONArray catalogProdItemJsonArray = new JSONArray(catalogProdItem);
		for (int i = 0; i < catalogProdItemJsonArray.length(); i++) {
			JSONObject catalogProdItemJson = (JSONObject) catalogProdItemJsonArray.get(i);
			System.out.println(catalogProdItemJson);
			
			String catalogProdEcSeq = catalogProdItemJson.optString("id"); // id*
			String prodName = catalogProdItemJson.optString("prod_name"); // 商品名稱*
			String prodPrice = catalogProdItemJson.optString("prod_price"); // 原價
			String prodDiscountPrice = catalogProdItemJson.optString("prod_discount_price"); // 促銷價*
			String prodStockStatus = catalogProdItemJson.optString("prod_stock_status"); // 商品供應情況*
			String prodUseStatus = catalogProdItemJson.optString("prod_use_status"); // 商品使用狀況*
			String prodImgUrl = catalogProdItemJson.optString("prod_img_url"); // 廣告圖像網址*
			String prodUrl = catalogProdItemJson.optString("prod_url"); // 連結網址*
			String prodCategory = catalogProdItemJson.optString("prod_category"); // 商品類別
			String prodItemErrorMsg = "";
			
			// id
			if (catalogProdEcSeq.isEmpty()) {
				prodItemErrorMsg += "id:必填欄位必須輸入資訊;";
			}

			// 商品名稱
			int prodNameLimit = 1024;
			if (prodName.isEmpty()) {
				prodItemErrorMsg += "prodName:必填欄位必須輸入資訊;";
			} else if (prodName.length() > prodNameLimit) {
				prodItemErrorMsg += "prodName:欄位字數超過" + prodNameLimit + "個字;";
			}
			
			// 原價
			if (!StringUtils.isNumeric(prodPrice)) {
				prodItemErrorMsg += "prodPrice:必須輸入數字;";
			}
			
			// 促銷價
			if (prodDiscountPrice.isEmpty()) {
				prodItemErrorMsg += "prodDiscountPrice:必填欄位必須輸入資訊;";
			} else if (!StringUtils.isNumeric(prodDiscountPrice)) {
				prodItemErrorMsg += "prodDiscountPrice:必須輸入數字;";
			}
			
			// 商品供應情況
			if (prodStockStatus.isEmpty()) {
				prodItemErrorMsg += "prodStockStatus:必填欄位必須輸入資訊;";
			} else if (!"0".equals(prodStockStatus) && !"1".equals(prodStockStatus)) {
				prodItemErrorMsg += "prodStockStatus:欄位內容不符合規定;";
			}
			
			// 商品使用狀況
			if (prodUseStatus.isEmpty()) {
				prodItemErrorMsg += "prodUseStatus:必填欄位必須輸入資訊;";
			} else if (!"0".equals(prodUseStatus) && !"1".equals(prodUseStatus)) {
				prodItemErrorMsg += "prodUseStatus:欄位內容不符合規定;";
			}
			
			AdUtilAjax adUtilAjax = new AdUtilAjax();
			// 廣告圖像網址
			if (prodImgUrl.isEmpty()) {
				prodItemErrorMsg += "prodImgUrl:必填欄位必須輸入資訊;";
			} else {
				String checkResultMsg = adUtilAjax.checkAdShowUrl(prodImgUrl, akbPfpServer);
				if(!checkResultMsg.isEmpty()){
					prodItemErrorMsg += "prodImgUrl:請輸入正確的廣告圖像網址;";
				}
			}
			
			// 連結網址
			if (prodUrl.isEmpty()) {
				prodItemErrorMsg += "prodUrl:必填欄位必須輸入資訊;";
			} else {
				String checkResultMsg = adUtilAjax.checkAdShowUrl(prodUrl, akbPfpServer);
				if(!checkResultMsg.isEmpty()){
					prodItemErrorMsg += "prodUrl:請輸入正確的連結網址;";
				}
			}
			
			// 商品類別
			int prodCategoryLimit = 50;
			if (prodCategory.length() > prodCategoryLimit) {
				prodItemErrorMsg += "prodCategory:欄位字數超過" + prodCategoryLimit + "個字;";
			}
			
			if (!prodItemErrorMsg.isEmpty()) {
				// 有錯誤記錄到錯誤table
				dataMap.put("第" + (i + 1) + "筆msg", prodItemErrorMsg);
				errorDataCount++;
			} else {
				// 記錄到正確table
				PfpCatalog pfpCatalog = new PfpCatalog();
				pfpCatalog.setCatalogSeq(catalogSeq); // 商品目錄編號
				
				PfpCatalogProdEc pfpCatalogProdEc = new PfpCatalogProdEc();
				pfpCatalogProdEc.setCatalogProdEcSeq(catalogProdEcSeq); // id*
				pfpCatalogProdEc.setPfpCatalog(pfpCatalog);
				pfpCatalogProdEc.setProdName(prodName); // 商品名稱*
				pfpCatalogProdEc.setProdUrl(prodUrl); // 連結網址*
				pfpCatalogProdEc.setProdPrice(Integer.parseInt(prodPrice)); // 原價
				pfpCatalogProdEc.setProdDiscountPrice(Integer.parseInt(prodDiscountPrice)); // 促銷價*
				pfpCatalogProdEc.setProdStockStatus(prodStockStatus); // 商品供應情況*
				pfpCatalogProdEc.setProdUseStatus(prodUseStatus); // 商品使用狀況*
				pfpCatalogProdEc.setProdCategory(prodCategory); // 商品類別
				pfpCatalogProdEc.setProdStatus("0"); // 商品狀態(0:關閉, 1:開啟)
				pfpCatalogProdEc.setProdCheckStatus("0"); // 商品審核狀態(0:未審核, 1:已審核)
				pfpCatalogProdEc.setUpdateDate(new Date()); // 更新時間
				pfpCatalogProdEc.setCreateDate(new Date()); // 建立時間
				
				System.out.println(pfpCatalogProdEc);
				if ("1".equals(updateWay)) { // 1.取代:上傳新檔案會取代目前的資料，table內未在新檔案中找到的產品將會遭到刪除。
				} else {
					// 2.更新:上傳新檔案會新增產品或更新現有的產品，不會刪除產品。
//					pfpCatalogUploadListDAO.saveOrUpdatePfpCatalogProdEc(pfpCatalogProdEc);
				}
				successDataCount++;
			}
		}

		dataMap.put("status", "SUCCESS");
		dataMap.put("msg", "一般購物類資料處理完成!");
		dataMap.put("successDataCount", successDataCount); // 正確總筆數
		dataMap.put("errorDataCount", errorDataCount); // 錯誤總筆數
		return dataMap;
	}

	
	public void setAkbPfpServer(String akbPfpServer) {
		this.akbPfpServer = akbPfpServer;
	}
	
}
