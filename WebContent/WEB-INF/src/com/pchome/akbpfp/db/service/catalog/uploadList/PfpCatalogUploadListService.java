package com.pchome.akbpfp.db.service.catalog.uploadList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.enumerate.ad.EnumPfpCatalog;

public class PfpCatalogUploadListService extends BaseService<String, String> implements IPfpCatalogUploadListService {

	private ShoppingProd shoppingProd;
	
	/**
	 * 依照商品目錄類別，處理相對應的部分
	 */
	@Override
	public Map<String, Object> processCatalogProdJsonData(JSONObject catalogProdJsonData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		String catalogType = catalogProdJsonData.optString("catalog_type");
		
		if (catalogType.isEmpty() || 
				(!EnumPfpCatalog.CATALOG_SHOPPING.getType().equals(catalogType)
				&& !EnumPfpCatalog.CATALOG_BOOKING.getType().equals(catalogType)
				&& !EnumPfpCatalog.CATALOG_TRAFFIC.getType().equals(catalogType)
				&& !EnumPfpCatalog.CATALOG_RENT.getType().equals(catalogType))) {
			dataMap.put("status", "ERROR");
			dataMap.put("msg", "商品目錄類型資料錯誤!");
			return dataMap;
		}
		
		if (EnumPfpCatalog.CATALOG_SHOPPING.getType().equals(catalogType)) { // 1:一般購物類
			dataMap = shoppingProd.processCatalogProdJsonData(catalogProdJsonData);
		} else if (EnumPfpCatalog.CATALOG_BOOKING.getType().equals(catalogType)) { // 2:訂房住宿類
			// 功能待開發
		} else if (EnumPfpCatalog.CATALOG_TRAFFIC.getType().equals(catalogType)) { // 3:交通航班類
			// 功能待開發
		} else if (EnumPfpCatalog.CATALOG_RENT.getType().equals(catalogType)) { // 4:房產租售類
			// 功能待開發
		}
		
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
				
				prdItemObject.put("ec_name", prdItem[1]);
//				prdItemObject.put("ec_title", prdItem[2]);
				prdItemObject.put("ec_price", prdItem[2]);
				prdItemObject.put("ec_discount_price", prdItem[3]);
				
				String ecStockStatus = prdItem[4];
				if ("無庫存".equals(ecStockStatus)) {
					ecStockStatus = "0";
				} else if ("有庫存".equals(ecStockStatus)) {
					ecStockStatus = "1";
				}
				prdItemObject.put("ec_stock_status", ecStockStatus);
				
				String ecUseStatus = prdItem[5];
				if ("全新".equals(ecUseStatus)) {
					ecUseStatus = "0";
				} else if ("二手".equals(ecUseStatus)) {
					ecUseStatus = "1";
				}
				prdItemObject.put("ec_use_status", ecUseStatus);
				prdItemObject.put("ec_img_url", prdItem[6]);
				prdItemObject.put("ec_url", prdItem[7]);
				prdItemObject.put("ec_category", prdItem[8]);
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

	public void setShoppingProd(ShoppingProd shoppingProd) {
		this.shoppingProd = shoppingProd;
	}

	public ShoppingProd getShoppingProd() {
		return shoppingProd;
	}

}
