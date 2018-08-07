package com.pchome.akbpfp.db.service.catalog.uploadList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.service.catalog.PfpCatalogService;
import com.pchome.akbpfp.struts2.ajax.ad.AdUtilAjax;

public class PfpCatalogUploadListService extends BaseService<String, String> implements IPfpCatalogUploadListService {

	private PfpCatalogService pfpCatalogService;
	private PfpCatalogUploadListDAO pfpCatalogUploadListDAO;
	
	private String akbPfpServer;
	
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
	 * @param catalogProdJsonData
	 * @return 
	 * @throws Exception 
	 */
	private Map<String, Object> processShoppingProd(JSONObject catalogProdJsonData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		// 各欄位資料檢核
		String errorMsg = "";
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
		int successDataCount = 0;
		int errorDataCount = 0;
		// 記錄商品品號列表，不在列表內的資料都刪除
		List<String> catalogProdEcSeqList = new ArrayList<String>();
		JSONArray catalogProdItemJsonArray = new JSONArray(catalogProdItem);
		for (int i = 0; i < catalogProdItemJsonArray.length(); i++) {
			JSONObject catalogProdItemJson = (JSONObject) catalogProdItemJsonArray.get(i);
			String catalogProdEcSeq = catalogProdItemJson.optString("id"); // id*
			String prodName = catalogProdItemJson.optString("prod_name"); // 商品名稱*
			String prodTitle = catalogProdItemJson.optString("prod_title"); // 商品敘述*
			String prodPrice = catalogProdItemJson.optString("prod_price"); // 原價
			String prodDiscountPrice = catalogProdItemJson.optString("prod_discount_price"); // 促銷價*
			String prodStockStatus = catalogProdItemJson.optString("prod_stock_status"); // 商品供應情況*
			String prodUseStatus = catalogProdItemJson.optString("prod_use_status"); // 商品使用狀況*
			String prodImgUrl = catalogProdItemJson.optString("prod_img_url"); // 廣告圖像網址*
			String prodUrl = catalogProdItemJson.optString("prod_url"); // 連結網址*
			String prodCategory = catalogProdItemJson.optString("prod_category"); // 商品類別
			String prodItemErrorMsg = ""; // 記錄錯誤訊息
			
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
			
			// 商品敘述
			int prodTitleLimit = 1024;
			if (prodTitle.isEmpty()) {
				prodItemErrorMsg += "prodTitle:必填欄位必須輸入資訊;";
			} else if (prodName.length() > prodTitleLimit) {
				prodItemErrorMsg += "prodTitle:欄位字數超過" + prodTitleLimit + "個字;";
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
				if (!checkResultMsg.isEmpty()) {
					prodItemErrorMsg += "prodImgUrl:請輸入正確的廣告圖像網址;";
				}
			}
			
			// 連結網址
			if (prodUrl.isEmpty()) {
				prodItemErrorMsg += "prodUrl:必填欄位必須輸入資訊;";
			} else {
				String checkResultMsg = adUtilAjax.checkAdShowUrl(prodUrl, akbPfpServer);
				if (!checkResultMsg.isEmpty()) {
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
				// 記錄商品明細，寫入一般購物類table
				PfpCatalogProdEc pfpCatalogProdEc = new PfpCatalogProdEc();
				pfpCatalogProdEc.setCatalogProdEcSeq(catalogProdEcSeq); // id*
				pfpCatalogProdEc.setPfpCatalog(pfpCatalog); // 商品目錄
				pfpCatalogProdEc.setProdName(prodName); // 商品名稱*
				pfpCatalogProdEc.setProdTitle(prodTitle); // 商品敘述
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

				// 先更新，如果回傳更新筆數為0表示無資料，則新增
				System.out.println("商品ID:" + pfpCatalogProdEc.getCatalogProdEcSeq());
				int updateCount = pfpCatalogUploadListDAO.updatePfpCatalogProdEc(pfpCatalogProdEc);
				System.out.println("更新筆數:" + updateCount);
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
				
				successDataCount++;
			}
		}
		
		if ("1".equals(updateWay)) { // 如果是"取代"，刪除table內，不在最新上傳的名單內資料
			pfpCatalogUploadListDAO.deleteNotInPfpCatalogProdEc(catalogSeq, catalogProdEcSeqList);
		}
		
		dataMap.put("status", "SUCCESS");
		dataMap.put("msg", "一般購物類資料處理完成!");
		dataMap.put("successDataCount", successDataCount); // 正確總筆數
		dataMap.put("errorDataCount", errorDataCount); // 錯誤總筆數
		return dataMap;
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
				
				String[] prdItem = new String[10]; // 每次重建陣列，避免上一筆資料殘留，固定10個明細
				String[] lineArray = strLine.split(","); // 因為預設是用"，"分開所以用split切開存入字串陣列
				for (int i = 0; i < lineArray.length; i++) { // 將切出來的陣列資料，放入建立好的陣列數量內，避免超出陣列的問題
					prdItem[i] = lineArray[i];
				}
				
				System.out.println("第" + rowNumber + "列:" + strLine);
				
				JSONObject prdItemObject = new JSONObject();
				prdItemObject.put("id", prdItem[0]);
				prdItemObject.put("prod_name", prdItem[1]);
				prdItemObject.put("prod_title", prdItem[2]);
				prdItemObject.put("prod_price", prdItem[3]);
				prdItemObject.put("prod_discount_price", prdItem[4]);
				
				String prodStockStatus = prdItem[5];
				if ("無庫存".equals(prodStockStatus)) {
					prodStockStatus = "0";
				} else if ("有庫存".equals(prodStockStatus)) {
					prodStockStatus = "1";
				}
				prdItemObject.put("prod_stock_status", prodStockStatus);
				
				String prodUseStatus = prdItem[6];
				if ("全新".equals(prodUseStatus)) {
					prodUseStatus = "0";
				} else if ("二手".equals(prodUseStatus)) {
					prodUseStatus = "1";
				}
				prdItemObject.put("prod_use_status", prodUseStatus);
				prdItemObject.put("prod_img_url", prdItem[7]);
				prdItemObject.put("prod_url", prdItem[8]);
				prdItemObject.put("prod_category", prdItem[9]);
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
	

	public void setPfpCatalogService(PfpCatalogService pfpCatalogService) {
		this.pfpCatalogService = pfpCatalogService;
	}

	public void setPfpCatalogUploadListDAO(PfpCatalogUploadListDAO pfpCatalogUploadListDAO) {
		this.pfpCatalogUploadListDAO = pfpCatalogUploadListDAO;
	}

	public void setAkbPfpServer(String akbPfpServer) {
		this.akbPfpServer = akbPfpServer;
	}

	

	
	
	

//	if ("1".equals(updateWay)) { // 1.取代:上傳新檔案會取代目前的資料，table內未在新檔案中找到的產品將會遭到刪除。
//	} else {
//		// 2.更新:上傳新檔案會新增產品或更新現有的產品，不會刪除產品。
////		pfpCatalogUploadListDAO.saveOrUpdatePfpCatalogProdEc(pfpCatalogProdEc);
//		
//		// 方法2 先更新，如果回傳更新筆數為0表示無資料，改新增
//		System.out.println("商品ID:" + pfpCatalogProdEc.getCatalogProdEcSeq());
//		int updateCount = pfpCatalogUploadListDAO.updatePfpCatalogProdEc(pfpCatalogProdEc);
//		System.out.println("更新筆數:" + updateCount);
//		if (updateCount == 0) {
////			pfpCatalogUploadListDAO.insertPfpCatalogProdEc(pfpCatalogProdEc);
//			pfpCatalogUploadListDAO.savePfpCatalogProdEc(pfpCatalogProdEc);
//		}
//	}
}
