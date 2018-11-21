package com.pchome.akbpfp.db.service.catalog.uploadList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.pchome.akbpfp.db.dao.catalog.uploadList.IPfpCatalogUploadListDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.pojo.PfpCatalogProdEc;
import com.pchome.akbpfp.db.pojo.PfpCatalogProdEcError;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadErrLog;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadLog;
import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.db.vo.catalog.PfpCatalogVO;
import com.pchome.akbpfp.db.vo.catalog.uploadList.ShoppingProdVO;
import com.pchome.enumerate.ad.EnumPfpCatalog;
import com.pchome.enumerate.catalogprod.EnumEcStockStatusType;
import com.pchome.enumerate.catalogprod.EnumEcUseStatusType;
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
		pfpCatalog.setUploadStatus("1"); // 目錄資料上傳狀態調整為上傳中
		//更新有Commit，馬上改變狀態
		pfpCatalogService.saveOrUpdateWithCommit(pfpCatalog);
		
		// 處理每筆商品資料
		int successNum = 0;
		int errorNum = 0;
		List<String> catalogProdSeqList = new ArrayList<String>(); // 記錄商品品號列表，不在列表內的資料都刪除
		JSONArray errorPrdItemArray = new JSONArray(); // 記錄總錯誤資料
		JSONArray pfpCatalogProdEcErrorArray = new JSONArray(); // 記錄該筆項目 主要寫入一般購物類商品上傳錯誤清單用
		int tempErrorPrdItemArrayCount = 0; // 暫存目前總共的錯誤數量
		int tempErrorPrdDetailItemCount = 0; // 暫存明細項目錯誤總數量
		// 上傳方式為1:檔案上傳 2:自動排程上傳 項目編號由excel 2開始
		int itemSeq = EnumPfpCatalog.CATALOG_UPLOAD_FILE_UPLOAD.getType().equals(catalogUploadType)
				|| EnumPfpCatalog.CATALOG_UPLOAD_AUTOMATIC_SCHEDULING.getType().equals(catalogUploadType) ? 2 : 1;
		
		String photoPath = photoDbPathNew + pfpCustomerInfoId + "/catalogProd/" + catalogSeq; // 商品圖片存放路徑
		log.info("11111111111111.photoPath:" + photoPath);
		String imgTempFolder = photoPath + "/temp"; // 商品圖片暫存路徑
		log.info("2222222222.imgTempFolder:" + imgTempFolder);
		if ("1".equals(updateWay)) { // 如果是"取代"，直接刪除全部商品圖片，全部資料重新開始
			FileUtils.deleteQuietly(new File(photoPath));
		} else {
			FileUtils.deleteQuietly(new File(imgTempFolder)); // 每次上傳先刪除暫存圖片
		}
		
		JSONArray catalogProdItemJsonArray = new JSONArray(catalogProdItem);
		for (int i = 0; i < catalogProdItemJsonArray.length(); i++) {
			File xxx = new File("img/user/AC2013071700004/catalogProd/testTest");
			log.info("圖片建立資料夾路徑:img/user/AC2013071700004/catalogProd/testTest");
			if (!xxx.exists()) {
				log.info("建立建立建立建立建立建立建立建立建立建立建立建立建立建立建立建立建立建立建立建立建立建立建立建立建立");
				xxx.mkdirs(); // 建立資料夾
			}
			
			JSONObject catalogProdItemJson = (JSONObject) catalogProdItemJsonArray.get(i);
			ShoppingProdVO shoppingProdItemVO = new ShoppingProdVO();
			shoppingProdItemVO.setCatalogProdSeq(catalogProdItemJson.optString("id", " ")); // id*
			shoppingProdItemVO.setEcName(catalogProdItemJson.optString("ec_name")); // 商品名稱*
			shoppingProdItemVO.setEcTitle(" "); // 商品敘述尚未使用，先固定寫入空
			shoppingProdItemVO.setEcPrice(catalogProdItemJson.optString("ec_price", " ").replace(",", "")); // 原價
			shoppingProdItemVO.setEcDiscountPrice(catalogProdItemJson.optString("ec_discount_price").replace(",", "")); // 促銷價*
			// 沒有原價則帶入促銷價
			if (StringUtils.isBlank(shoppingProdItemVO.getEcPrice())) {
				shoppingProdItemVO.setEcPrice(shoppingProdItemVO.getEcDiscountPrice());
			}
			
			shoppingProdItemVO.setEcStockStatusDesc(catalogProdItemJson.optString("ec_stock_status")); // 商品供應情況*(中文)
			shoppingProdItemVO.setEcUseStatusDesc(catalogProdItemJson.optString("ec_use_status")); // 商品使用狀況*(中文)
			shoppingProdItemVO.setEcImgUrl(catalogProdItemJson.optString("ec_img_url")); // 廣告圖像網址*
			shoppingProdItemVO.setEcImgBase64(catalogProdItemJson.optString("ec_Img_Base64", " ")); // 手動上傳圖片的Base64編碼
			shoppingProdItemVO.setEcUrl(catalogProdItemJson.optString("ec_url")); // 連結網址*
			shoppingProdItemVO.setEcCategory(catalogProdItemJson.optString("ec_category", " ")); // 商品類別
			
			// 檢查每個欄位  有錯塞入空值
			JSONObject addPfpCatalogProdEcErrorObject = new JSONObject();
			addPfpCatalogProdEcErrorObject.put("itemSeq", itemSeq);
			errorPrdItemArray = super.checkCatalogProdSeq(errorPrdItemArray, itemSeq, shoppingProdItemVO.getCatalogProdSeq());
			// 有增加表示目前項目有錯，錯誤則將該筆資料設定為空格，如果直接將錯誤資料寫進DB，錯誤資料含emoji DB不支援的部分會直接出錯
			if (errorPrdItemArray.length() > tempErrorPrdDetailItemCount) {
				tempErrorPrdDetailItemCount = errorPrdItemArray.length();
				addPfpCatalogProdEcErrorObject.put("catalogProdSeq", " ");
			} else {
				addPfpCatalogProdEcErrorObject.put("catalogProdSeq", shoppingProdItemVO.getCatalogProdSeq());
			}
			
			errorPrdItemArray = super.checkEcName(errorPrdItemArray, itemSeq, shoppingProdItemVO.getEcName());
			if (errorPrdItemArray.length() > tempErrorPrdDetailItemCount) {
				tempErrorPrdDetailItemCount = errorPrdItemArray.length();
				addPfpCatalogProdEcErrorObject.put("ecName", " ");
			} else {
				addPfpCatalogProdEcErrorObject.put("ecName", shoppingProdItemVO.getEcName());
			}
			
			errorPrdItemArray = super.checkEcPrice(errorPrdItemArray, itemSeq, shoppingProdItemVO.getEcPrice());
			if (errorPrdItemArray.length() > tempErrorPrdDetailItemCount) {
				tempErrorPrdDetailItemCount = errorPrdItemArray.length();
				addPfpCatalogProdEcErrorObject.put("ecPrice", "0");
			} else {
				addPfpCatalogProdEcErrorObject.put("ecPrice", shoppingProdItemVO.getEcPrice());
			}
			
			errorPrdItemArray = super.checkEcDiscountPrice(errorPrdItemArray, itemSeq, shoppingProdItemVO.getEcDiscountPrice(), shoppingProdItemVO.getEcPrice());
			if (errorPrdItemArray.length() > tempErrorPrdDetailItemCount) {
				tempErrorPrdDetailItemCount = errorPrdItemArray.length();
				addPfpCatalogProdEcErrorObject.put("ecDiscountPrice", "0");
			} else {
				addPfpCatalogProdEcErrorObject.put("ecDiscountPrice", shoppingProdItemVO.getEcDiscountPrice());
			}
			
			errorPrdItemArray = super.checkEcStockStatus(errorPrdItemArray, itemSeq, shoppingProdItemVO.getEcStockStatusDesc());
			if (errorPrdItemArray.length() > tempErrorPrdDetailItemCount) {
				tempErrorPrdDetailItemCount = errorPrdItemArray.length();
				addPfpCatalogProdEcErrorObject.put("ecStockStatus", " ");
			} else {
				if (shoppingProdItemVO.getEcStockStatusDesc().equals(EnumEcStockStatusType.Out_Of_Stock.getChName())) {
					shoppingProdItemVO.setEcStockStatus(EnumEcStockStatusType.Out_Of_Stock.getType());
				} else if (shoppingProdItemVO.getEcStockStatusDesc().equals(EnumEcStockStatusType.In_Stock.getChName())) {
					shoppingProdItemVO.setEcStockStatus(EnumEcStockStatusType.In_Stock.getType());
				} else if (shoppingProdItemVO.getEcStockStatusDesc().equals(EnumEcStockStatusType.Pre_Order.getChName())) {
					shoppingProdItemVO.setEcStockStatus(EnumEcStockStatusType.Pre_Order.getType());
				} else if (shoppingProdItemVO.getEcStockStatusDesc().equals(EnumEcStockStatusType.Discontinued.getChName())) {
					shoppingProdItemVO.setEcStockStatus(EnumEcStockStatusType.Discontinued.getType());
				}
				addPfpCatalogProdEcErrorObject.put("ecStockStatus", shoppingProdItemVO.getEcStockStatus());
			}
			
			errorPrdItemArray = super.checkEcUseStatus(errorPrdItemArray, itemSeq, shoppingProdItemVO.getEcUseStatusDesc());
			if (errorPrdItemArray.length() > tempErrorPrdDetailItemCount) {
				tempErrorPrdDetailItemCount = errorPrdItemArray.length();
				addPfpCatalogProdEcErrorObject.put("ecUseStatus", " ");
			} else {
				if (shoppingProdItemVO.getEcUseStatusDesc().equals(EnumEcUseStatusType.New_Goods.getChName())) {
					shoppingProdItemVO.setEcUseStatus(EnumEcUseStatusType.New_Goods.getType());
				} else if (shoppingProdItemVO.getEcUseStatusDesc().equals(EnumEcUseStatusType.Used_Goods.getChName())) {
					shoppingProdItemVO.setEcUseStatus(EnumEcUseStatusType.Used_Goods.getType());
				} else if (shoppingProdItemVO.getEcUseStatusDesc().equals(EnumEcUseStatusType.Welfare_Goods.getChName())) {
					shoppingProdItemVO.setEcUseStatus(EnumEcUseStatusType.Welfare_Goods.getType());
				}
				addPfpCatalogProdEcErrorObject.put("ecUseStatus", shoppingProdItemVO.getEcUseStatus());
			}
			
			String imgTempCompletePath = ""; // 暫存圖片完整路徑
			String imgCompletePath = ""; // 正式圖片完整路徑
			String addDbImgPath = ""; // 正式圖片寫入DB欄位路徑
			errorPrdItemArray = super.checkEcImgUrl(errorPrdItemArray, itemSeq, photoPath, shoppingProdItemVO.getEcImgUrl(), shoppingProdItemVO.getEcImgBase64(), catalogUploadType, shoppingProdItemVO);
			if (errorPrdItemArray.length() > tempErrorPrdDetailItemCount) {
				tempErrorPrdDetailItemCount = errorPrdItemArray.length();
				addPfpCatalogProdEcErrorObject.put("ecImg", " ");
			} else {
				// 正式圖片完整路徑(此筆資料正確時使用商品編號當檔名)
				imgCompletePath = photoPath + "/" + shoppingProdItemVO.getCatalogProdSeq() + "." + shoppingProdItemVO.getEcImgFilenameExtension();
				log.info("3333333.imgCompletePath:" + imgCompletePath);
				addDbImgPath = imgCompletePath.substring(imgCompletePath.indexOf("img/"));
				log.info("44444444.addDbImgPath:" + addDbImgPath);
				// 暫存圖片完整路徑(此筆資料錯誤時使用匯入序號當檔名)
				imgTempCompletePath = imgTempFolder + "/" + itemSeq + "." + shoppingProdItemVO.getEcImgFilenameExtension();
				log.info("55555555.addDbImgPath:" + imgTempCompletePath);
				String addDbImgTempPath = imgTempCompletePath.substring(imgTempCompletePath.indexOf("img/")); // 暫存圖片寫入DB欄位路徑
				log.info("6666666.addDbImgTempPath:" + addDbImgTempPath);
				addPfpCatalogProdEcErrorObject.put("ecImg", addDbImgTempPath);
			}
			
			errorPrdItemArray = super.checkEcUrl(errorPrdItemArray, itemSeq, shoppingProdItemVO.getEcUrl());
			if (errorPrdItemArray.length() > tempErrorPrdDetailItemCount) {
				tempErrorPrdDetailItemCount = errorPrdItemArray.length();
				addPfpCatalogProdEcErrorObject.put("ecUrl", " ");
			} else {
				addPfpCatalogProdEcErrorObject.put("ecUrl", shoppingProdItemVO.getEcUrl());
			}
			
			errorPrdItemArray = super.checkEcCategory(errorPrdItemArray, itemSeq, shoppingProdItemVO.getEcCategory());
			if (errorPrdItemArray.length() > tempErrorPrdDetailItemCount) {
				tempErrorPrdDetailItemCount = errorPrdItemArray.length();
				addPfpCatalogProdEcErrorObject.put("ecCategory", " ");
			} else {
				addPfpCatalogProdEcErrorObject.put("ecCategory", shoppingProdItemVO.getEcCategory());
			}
			
			itemSeq++; // 因為下面寫入table資料 if判斷有continue，避免沒累加到，先在這邊做這筆項目結束累加，準備進入下一筆
			if (errorPrdItemArray.length() > tempErrorPrdItemArrayCount) {
				// 每一次記錄錯誤陣列長度 超過 暫存的記錄長度，表示這一行資料有新增錯誤項目
				tempErrorPrdItemArrayCount = errorPrdItemArray.length();
				errorNum++;
				
				pfpCatalogProdEcErrorArray.put(addPfpCatalogProdEcErrorObject);
			} else {
				// 檢查輸入的資料是否與DB內完全相同，相同則不做更新、新增處理，避免每次更新就需要重新審核商品
				List<Map<String, Object>> pfpCatalogProdEcList = pfpCatalogUploadListDAO.getPfpCatalogProdEc(catalogSeq, shoppingProdItemVO.getCatalogProdSeq());
				
				// 記錄商品明細，寫入一般購物類table
				PfpCatalogProdEc pfpCatalogProdEc = new PfpCatalogProdEc();
				pfpCatalogProdEc.setCatalogProdSeq(shoppingProdItemVO.getCatalogProdSeq()); // id
				pfpCatalogProdEc.setPfpCatalog(pfpCatalog); // 商品目錄
				pfpCatalogProdEc.setEcName(shoppingProdItemVO.getEcName()); // 商品名稱
				pfpCatalogProdEc.setEcTitle(shoppingProdItemVO.getEcTitle()); // 商品敘述
				
				try {
					// 將放在暫存資料夾內的圖片複製到外面正式位置，檔名由匯入序號改為商品編號當檔名
					File f1 = new File(imgTempCompletePath);
					File f2 = new File(imgCompletePath);
					Files.copy(f1.toPath(), f2.toPath(), StandardCopyOption.REPLACE_EXISTING);
					f1.delete();
				} catch (IOException e) {
					e.printStackTrace();
				}
				pfpCatalogProdEc.setEcImg(addDbImgPath);
				
				pfpCatalogProdEc.setEcImgRegion(ImgUtil.getImgLongWidthCode(imgCompletePath)); // 商品影像長寬(V/H)
				pfpCatalogProdEc.setEcImgMd5(ImgUtil.getImgMD5Code(imgCompletePath)); // 商品影像MD5
				pfpCatalogProdEc.setEcUrl(super.urlAddHttpOrHttps(shoppingProdItemVO.getEcUrl())); // 連結網址
				pfpCatalogProdEc.setEcPrice(Integer.parseInt(shoppingProdItemVO.getEcPrice())); // 原價
				pfpCatalogProdEc.setEcDiscountPrice(Integer.parseInt(shoppingProdItemVO.getEcDiscountPrice())); // 促銷價*
				pfpCatalogProdEc.setEcStockStatus(shoppingProdItemVO.getEcStockStatus()); // 商品供應情況
				pfpCatalogProdEc.setEcUseStatus(shoppingProdItemVO.getEcUseStatus()); // 商品使用狀況
				pfpCatalogProdEc.setEcCategory(shoppingProdItemVO.getEcCategory()); // 商品類別
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
					if (shoppingProdItemVO.getCatalogProdSeq().equals((String) prodEcMap.get("catalog_prod_seq"))
							&& catalogSeq.equals((String) prodEcMap.get("catalog_seq"))
							&& shoppingProdItemVO.getEcName().equals((String) prodEcMap.get("ec_name"))
							&& shoppingProdItemVO.getEcTitle().equals((String) prodEcMap.get("ec_title"))
							&& pfpCatalogProdEc.getEcImg().equals((String) prodEcMap.get("ec_img"))
							&& pfpCatalogProdEc.getEcImgRegion().equals((String) prodEcMap.get("ec_img_region"))
							&& pfpCatalogProdEc.getEcImgMd5().equals((String) prodEcMap.get("ec_img_md5"))
							&& shoppingProdItemVO.getEcUrl().equals((String) prodEcMap.get("ec_url"))
							&& shoppingProdItemVO.getEcPrice().equals(String.valueOf((int) prodEcMap.get("ec_price")))
							&& shoppingProdItemVO.getEcDiscountPrice().equals(String.valueOf((int) prodEcMap.get("ec_discount_price")))
							&& shoppingProdItemVO.getEcStockStatus().equals((String) prodEcMap.get("ec_stock_status"))
							&& shoppingProdItemVO.getEcUseStatus().equals((String) prodEcMap.get("ec_use_status"))
							&& shoppingProdItemVO.getEcCategory().equals((String) prodEcMap.get("ec_category"))) {
						continue;
					} else if (EnumPfpCatalog.CATALOG_UPLOAD_MANUAL_UPLOAD.getType().equals(catalogUploadType)
							&& shoppingProdItemVO.getCatalogProdSeq().equals((String) prodEcMap.get("catalog_prod_seq"))) {
						// 如果是手動上傳，商品編號已重複，則不做更新、新增處理(此處檢核為避免一個帳號多人使用手動上傳時商品編號重複)
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
		
		// 先刪除錯誤紀錄pfp_catalog_upload_err_log、pfp_catalog_prod_ec_error(每個目錄只有一組對應錯誤資料)
		PfpCatalogVO pfpCatalogVO = new PfpCatalogVO();
		pfpCatalogVO.setCatalogSeq(catalogSeq);
		pfpCatalogUploadListDAO.deletePfpCatalogUploadErrLog(pfpCatalogVO);
		pfpCatalogUploadListDAO.deletePfpCatalogProdEcError(pfpCatalogVO);
		
		// 有錯誤再記錄2個錯誤相關table內
		if (errorPrdItemArray.length() > 0) {
			// 記錄錯誤資料 pfp_catalog_upload_err_log "商品目錄更新錯誤紀錄"
			for (int i = 0; i < errorPrdItemArray.length(); i++) {
				JSONObject errorPrdItemJson = (JSONObject) errorPrdItemArray.get(i);
				PfpCatalogUploadErrLog pfpCatalogUploadErrLog = new PfpCatalogUploadErrLog();
				pfpCatalogUploadErrLog.setPfpCatalogUploadLog(pfpCatalogUploadLog); // 更新紀錄序號
				pfpCatalogUploadErrLog.setCatalogProdErrItem(errorPrdItemJson.getInt("catalog_prod_err_item")); // 上傳錯誤清單ID(EXCEL欄位號碼)
				pfpCatalogUploadErrLog.setCatalogErrItem(errorPrdItemJson.getString("catalog_err_item")); // 錯誤欄位
				pfpCatalogUploadErrLog.setCatalogErrReason(errorPrdItemJson.getString("catalog_err_reason")); // 錯誤原因
				pfpCatalogUploadErrLog.setUpdateDate(new Date()); // 更新時間
				pfpCatalogUploadErrLog.setCreateDate(new Date()); // 建立時間
				pfpCatalogUploadListDAO.savePfpCatalogUploadErrLog(pfpCatalogUploadErrLog);
			}
			
			// 記錄錯誤資料 pfp_catalog_prod_ec_error "一般購物類商品上傳錯誤清單"
			for (int i = 0; i < pfpCatalogProdEcErrorArray.length(); i++) {
				JSONObject pfpCatalogProdEcErrorObjectJson = (JSONObject) pfpCatalogProdEcErrorArray.get(i);
				PfpCatalogProdEcError pfpCatalogProdEcError = new PfpCatalogProdEcError();
				pfpCatalogProdEcError.setPfpCatalogUploadLog(pfpCatalogUploadLog); // 更新紀錄序號
				pfpCatalogProdEcError.setCatalogProdErrItem(pfpCatalogProdEcErrorObjectJson.getInt("itemSeq"));
				pfpCatalogProdEcError.setCatalogSeq(catalogSeq);
				pfpCatalogProdEcError.setCatalogProdSeq(pfpCatalogProdEcErrorObjectJson.getString("catalogProdSeq"));
				pfpCatalogProdEcError.setEcName(pfpCatalogProdEcErrorObjectJson.getString("ecName"));
				pfpCatalogProdEcError.setEcTitle(" ");
				pfpCatalogProdEcError.setEcImg(pfpCatalogProdEcErrorObjectJson.getString("ecImg"));
				pfpCatalogProdEcError.setEcUrl(pfpCatalogProdEcErrorObjectJson.getString("ecUrl"));
				pfpCatalogProdEcError.setEcPrice(pfpCatalogProdEcErrorObjectJson.getString("ecPrice"));
				pfpCatalogProdEcError.setEcDiscountPrice(pfpCatalogProdEcErrorObjectJson.getInt("ecDiscountPrice"));
				pfpCatalogProdEcError.setEcStockStatus(pfpCatalogProdEcErrorObjectJson.getString("ecStockStatus"));
				pfpCatalogProdEcError.setEcUseStatus(pfpCatalogProdEcErrorObjectJson.getString("ecUseStatus"));
				pfpCatalogProdEcError.setEcCategory(pfpCatalogProdEcErrorObjectJson.getString("ecCategory"));
				pfpCatalogProdEcError.setCreateDate(new Date());
				pfpCatalogUploadListDAO.savePfpCatalogProdEcError(pfpCatalogProdEcError);
			}
		} else { // 沒有錯誤
			// 刪除暫存資料夾圖片
			FileUtils.deleteQuietly(new File(imgTempFolder));
		}
		
		// 更新 pfp_catalog "商品目錄" 資料
		pfpCatalogVO = new PfpCatalogVO();
		pfpCatalogVO.setCatalogSeq(catalogSeq);
		pfpCatalogVO.setPfpCustomerInfoId(pfpCustomerInfoId);
		pfpCatalogVO.setCatalogUploadType(catalogUploadType);
		pfpCatalogVO.setUploadContent(updateContent);
		pfpCatalogVO.setUploadStatus("2");
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