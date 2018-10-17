package com.pchome.akbpfp.db.service.catalog.uploadList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.compass.retrotranslator.edu.emory.mathcs.backport.java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pchome.akbpfp.db.dao.catalog.IPfpCatalogDAO;
import com.pchome.akbpfp.db.dao.catalog.uploadList.IPfpCatalogUploadListDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadLog;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogUploadListVO;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogUploadLogVO;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogVO;
import com.pchome.enumerate.ad.EnumPfpCatalog;
import com.pchome.enumerate.sequence.EnumSequenceTableName;

public class PfpCatalogUploadListService extends BaseService<String, String> implements IPfpCatalogUploadListService {

	private ShoppingProd shoppingProd;
	private IPfpCatalogService pfpCatalogService;
	private ISequenceService sequenceService;
	private IPfpCatalogUploadListDAO pfpCatalogUploadListDAO;
	private String photoDbPathNew;
	
	/**
	 * 依照商品目錄類別，處理相對應的部分
	 */
	@Override
	public Map<String, Object> processCatalogProdJsonData(JSONObject catalogProdJsonData) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		String catalogType = catalogProdJsonData.optString("catalogType");
		
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
	 * 檢查檔案格式是否為我們提供的CSV檔格式
	 * @param vo
	 * @return
	 */
	@Override
	public Map<String, Object> checkCSVFile(PfpCatalogUploadListVO vo) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		BufferedReader brdFile = null;
		try {
			brdFile = new BufferedReader(new InputStreamReader(new FileInputStream(vo.getFileUploadPath()), "BIG5")); // 抓CSV檔進java，bufferedReader
			String strLine = null;
			
			while ((strLine = brdFile.readLine()) != null) { // 將CSV檔字串一列一列讀入並存起來直到沒有列為止
				String[] lineArray = strLine.split(","); // 因為預設是用"，"分開所以用split切開存入字串陣列
				
				// 一般購物類檢查方式
				if (vo.getCatalogType().equals(EnumPfpCatalog.CATALOG_SHOPPING.getType())) {
					dataMap.put("status", "SUCCESS");
					
					// 999表示沒有此欄位
					int idArrayIndex = 999;
					int ecNameArrayIndex = 999;
					int ecPriceArrayIndex = 999;
					int ecDiscountPriceArrayIndex = 999;
					int ecStockStatusArrayIndex = 999;
					int ecUseStatusArrayIndex = 999;
					int ecImgUrlArrayIndex = 999;
					int ecUrlArrayIndex = 999;
					int ecCategoryArrayIndex = 999;
					
					for (int i = 0; i < lineArray.length; i++) {
						if (idArrayIndex == 999 && "id*".equals(lineArray[i])) {
							idArrayIndex = i;
						} else if (ecNameArrayIndex == 999 && "商品名稱*".equals(lineArray[i])) {
							ecNameArrayIndex = i;
						} else if (ecPriceArrayIndex == 999 && "原價".equals(lineArray[i])) {
							ecPriceArrayIndex = i;
						} else if (ecDiscountPriceArrayIndex == 999 && "促銷價*".equals(lineArray[i])) {
							ecDiscountPriceArrayIndex = i;
						} else if (ecStockStatusArrayIndex == 999 && "商品供應情況*".equals(lineArray[i])) {
							ecStockStatusArrayIndex = i;
						} else if (ecUseStatusArrayIndex == 999 && "商品使用狀況*".equals(lineArray[i])) {
							ecUseStatusArrayIndex = i;
						} else if (ecImgUrlArrayIndex == 999 && "廣告圖像網址*".equals(lineArray[i])) {
							ecImgUrlArrayIndex = i;
						} else if (ecUrlArrayIndex == 999 && "連結網址*".equals(lineArray[i])) {
							ecUrlArrayIndex = i;
						} else if (ecCategoryArrayIndex == 999 && "商品類別".equals(lineArray[i])) {
							ecCategoryArrayIndex = i;
						} else if ((idArrayIndex != 999 && "id*".equals(lineArray[i]))
								|| (ecNameArrayIndex != 999 && "商品名稱*".equals(lineArray[i]))
								|| (ecPriceArrayIndex != 999 && "原價".equals(lineArray[i]))
								|| (ecDiscountPriceArrayIndex != 999 && "促銷價*".equals(lineArray[i]))
								|| (ecStockStatusArrayIndex != 999 && "商品供應情況*".equals(lineArray[i]))
								|| (ecUseStatusArrayIndex != 999 && "商品使用狀況*".equals(lineArray[i]))
								|| (ecImgUrlArrayIndex != 999 && "廣告圖像網址*".equals(lineArray[i]))
								|| (ecUrlArrayIndex != 999 && "連結網址*".equals(lineArray[i]))
								|| (ecCategoryArrayIndex != 999 && "商品類別".equals(lineArray[i]))) {
							// 判斷到有重複欄位
							dataMap.put("status", "ERROR");
						}
					}
					
					// 沒有必填欄位
					if (idArrayIndex == 999 || ecNameArrayIndex == 999 || ecDiscountPriceArrayIndex == 999
							|| ecStockStatusArrayIndex == 999 || ecUseStatusArrayIndex == 999
							|| ecImgUrlArrayIndex == 999 || ecUrlArrayIndex == 999) {
						dataMap.put("status", "ERROR");
					}
					
					// 僅檢查第一列
					break;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			dataMap.put("status", "ERROR");
		} finally {
			if (brdFile != null) {
				try {
					brdFile.close();
				} catch (IOException e) {
					e.printStackTrace();
					dataMap.put("status", "ERROR");
				}
			}
		}
		
		return dataMap;
	}
	
	/**
	 * 將csv檔案內容轉成json格式
	 * @throws IOException 
	 * @throws JSONException 
	 */
	@Override
	public JSONObject getCSVFileDataToJson(PfpCatalogUploadListVO vo) {
		JSONObject catalogProdJsonData = new JSONObject();
		JSONArray prdItemArray = new JSONArray();
		
		BufferedReader brdFile = null;
		try {
			brdFile = new BufferedReader(new InputStreamReader(new FileInputStream(vo.getFileUploadPath()), "BIG5")); // 抓CSV檔進java，bufferedReader
			String strLine = null;
			int rowNumber = 0; // 第幾列
			
			// 一般購物類
			if (vo.getCatalogType().equals(EnumPfpCatalog.CATALOG_SHOPPING.getType())) {
				// 999表示沒有此欄位
				int idArrayIndex = 999;
				int ecNameArrayIndex = 999;
				int ecPriceArrayIndex = 999;
				int ecDiscountPriceArrayIndex = 999;
				int ecStockStatusArrayIndex = 999;
				int ecUseStatusArrayIndex = 999;
				int ecImgUrlArrayIndex = 999;
				int ecUrlArrayIndex = 999;
				int ecCategoryArrayIndex = 999;
				int indexLength = 0; // 由第一列來判斷此文件陣列長度多長
				
				while ((strLine = brdFile.readLine()) != null) { // 將CSV檔字串一列一列讀入並存起來直到沒有列為止
					String[] lineArray = strLine.split(","); // 因為預設是用"，"分開所以用split切開存入字串陣列
					
					// 第一列處理，先知道哪個資料在哪一欄位
					if (++rowNumber == 1) {
						indexLength = lineArray.length; // 記陣列多長，僅確認一次就好，再來都使用這個長度
						for (int index = 0; index < lineArray.length; index++) {
							if (idArrayIndex == 999 && "id*".equals(lineArray[index])) {
								idArrayIndex = index;
							} else if (ecNameArrayIndex == 999 && "商品名稱*".equals(lineArray[index])) {
								ecNameArrayIndex = index;
							} else if (ecPriceArrayIndex == 999 && "原價".equals(lineArray[index])) {
								ecPriceArrayIndex = index;
							} else if (ecDiscountPriceArrayIndex == 999 && "促銷價*".equals(lineArray[index])) {
								ecDiscountPriceArrayIndex = index;
							} else if (ecStockStatusArrayIndex == 999 && "商品供應情況*".equals(lineArray[index])) {
								ecStockStatusArrayIndex = index;
							} else if (ecUseStatusArrayIndex == 999 && "商品使用狀況*".equals(lineArray[index])) {
								ecUseStatusArrayIndex = index;
							} else if (ecImgUrlArrayIndex == 999 && "廣告圖像網址*".equals(lineArray[index])) {
								ecImgUrlArrayIndex = index;
							} else if (ecUrlArrayIndex == 999 && "連結網址*".equals(lineArray[index])) {
								ecUrlArrayIndex = index;
							} else if (ecCategoryArrayIndex == 999 && "商品類別".equals(lineArray[index])) {
								ecCategoryArrayIndex = index;
							}
						}
						continue;
					}
					
					String[] prdItem = new String[indexLength]; // 每次重建陣列，避免上一筆資料殘留
					
					// 第二列開始
					for (int i = 0; i < lineArray.length; i++) { // 將切出來的陣列資料，放入建立好的陣列數量內，避免超出陣列的問題
						prdItem[i] = lineArray[i];
					}
					
					System.out.println("第" + rowNumber + "列:" + strLine);
					
					JSONObject prdItemObject = new JSONObject();
					
					try { // 處理變成科學符號的部分
						prdItemObject.put("id", new BigDecimal(prdItem[idArrayIndex].trim()).toPlainString());
					} catch (Exception e) {
						prdItemObject.put("id", prdItem[idArrayIndex]);
					}
					
					prdItemObject.put("ec_name", prdItem[ecNameArrayIndex]);
	//				prdItemObject.put("ec_title", prdItem[2]);
					// 非999表示，此份文件有原價非必填欄位的資料
					if (ecCategoryArrayIndex != 999) {
						prdItemObject.put("ec_price", prdItem[ecPriceArrayIndex]);
					} else {
						prdItemObject.put("ec_price", "0"); // 先給0測試，DB欄位資料待確認
					}
					
					prdItemObject.put("ec_discount_price", prdItem[ecDiscountPriceArrayIndex]);
					
					String ecStockStatus = prdItem[ecStockStatusArrayIndex];
					if ("無庫存".equals(ecStockStatus)) {
						ecStockStatus = "0";
					} else if ("有庫存".equals(ecStockStatus)) {
						ecStockStatus = "1";
					}
					prdItemObject.put("ec_stock_status", ecStockStatus);
					
					String ecUseStatus = prdItem[ecUseStatusArrayIndex];
					if ("全新".equals(ecUseStatus)) {
						ecUseStatus = "0";
					} else if ("二手".equals(ecUseStatus)) {
						ecUseStatus = "1";
					}
					prdItemObject.put("ec_use_status", ecUseStatus);
					prdItemObject.put("ec_img_url", prdItem[ecImgUrlArrayIndex]);
					prdItemObject.put("ec_url", prdItem[ecUrlArrayIndex]);
					
					// 非999表示，此份文件有商品類別非必填欄位的資料
					if (ecCategoryArrayIndex != 999) {
						prdItemObject.put("ec_category", prdItem[ecCategoryArrayIndex]);
					} else {
						prdItemObject.put("ec_category", " ");
					}
					prdItemArray.put(prdItemObject);
				}
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

	/**
	 * 刪除 一般購物類商品清單
	 * @param vo
	 */
	@Override
	public void deletePfpCatalogProdEc(PfpCatalogVO vo) {
		pfpCatalogUploadListDAO.deletePfpCatalogProdEc(vo);
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
	 * 刪除 商品目錄更新錯誤紀錄
	 * @param vo
	 */
	@Override
	public void deletePfpCatalogUploadErrLog(PfpCatalogVO vo) {
		pfpCatalogUploadListDAO.deletePfpCatalogUploadErrLog(vo);
	}

	/**
	 * 刪除 商品目錄群組
	 * @param vo
	 */
	@Override
	public void deletePfpCatalogGroup(PfpCatalogVO vo) {
		pfpCatalogUploadListDAO.deletePfpCatalogGroup(vo);
	}

	/**
	 * 刪除 商品目錄群組明細
	 * @param vo
	 */
	@Override
	public void deletePfpCatalogGroupItem(PfpCatalogVO vo) {
		pfpCatalogUploadListDAO.deletePfpCatalogGroupItem(vo);
	}

	/**
	 * 新增log記錄
	 * @throws Exception 
	 */
	@Override
	public void savePfpCatalogUploadLog(PfpCatalogUploadLogVO vo) throws Exception {
		PfpCatalogUploadLog pfpCatalogUploadLog = new PfpCatalogUploadLog();
		
		String catalogUploadLogSeq = sequenceService.getId(EnumSequenceTableName.PFP_CATALOG_UPLOAD_LOG, "", 20);
		pfpCatalogUploadLog.setCatalogUploadLogSeq(catalogUploadLogSeq); // 更新紀錄序號
		
		PfpCatalog pfpCatalog = pfpCatalogService.get(vo.getCatalogSeq());
		pfpCatalogUploadLog.setPfpCatalog(pfpCatalog); // 商品目錄
		
		pfpCatalogUploadLog.setUpdateWay(vo.getUpdateWay()); // 更新方式
		pfpCatalogUploadLog.setUpdateContent(vo.getUpdateContent()); // 更新內容
		pfpCatalogUploadLog.setUpdateDatetime(new Date()); // 更新時間
		pfpCatalogUploadLog.setErrorNum(vo.getErrorNum()); // 錯誤筆數
		pfpCatalogUploadLog.setSuccessNum(vo.getSuccessNum()); // 成功筆數
		pfpCatalogUploadLog.setUpdateDate(new Date()); // 更新時間
		pfpCatalogUploadLog.setCreateDate(new Date()); // 建立時間
		
		pfpCatalogUploadListDAO.savePfpCatalogUploadLog(pfpCatalogUploadLog);
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
	 * 刪除哪位客戶的哪個目錄資料夾內容
	 * @param vo.getPfpCustomerInfoId() pfp_id
	 * @param vo.getCatalogSeq() 商品目錄編號
	 */
	@Override
	public void deleteFolderAndData(PfpCatalogVO vo) {
		String folderPath = photoDbPathNew + vo.getPfpCustomerInfoId() + "/catalogProd/" + vo.getCatalogSeq();
		FileUtils.deleteQuietly(new File(folderPath));
	}
	
	public void setShoppingProd(ShoppingProd shoppingProd) {
		this.shoppingProd = shoppingProd;
	}

	public ShoppingProd getShoppingProd() {
		return shoppingProd;
	}

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

}
