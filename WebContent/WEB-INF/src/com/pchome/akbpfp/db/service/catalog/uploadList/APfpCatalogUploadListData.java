package com.pchome.akbpfp.db.service.catalog.uploadList;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pchome.akbpfp.struts2.ajax.ad.AdUtilAjax;
import com.pchome.enumerate.ad.EnumPfpCatalog;
import com.pchome.enumerate.catalogprod.EnumEcStockStatusType;
import com.pchome.enumerate.catalogprod.EnumEcUseStatusType;
import com.pchome.utils.CommonUtils;
import com.pchome.utils.ImgUtil;

public abstract class APfpCatalogUploadListData {

	private String akbPfpServer;
	
	public abstract Object processCatalogProdJsonData(JSONObject catalogProdJsonData) throws Exception;

	/**
	 * 檢查商品類別(非必填)
	 * @param errorPrdItemArray
	 * @param catalogProdSeq
	 * @param ecCategory
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray checkEcCategory(JSONArray errorPrdItemArray, String catalogProdSeq, String ecCategory) throws JSONException {
		String prodItemErrorMsg = "";
		int ecCategoryLimit = 20;

		if (ecCategory.length() > ecCategoryLimit) {
			prodItemErrorMsg = "字數限制" + ecCategoryLimit + "字";
		}
		
		if (StringUtils.isNotBlank(prodItemErrorMsg)) { // 錯誤訊息非空值則記錄
			JSONObject jsonObject = new JSONObject();
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
		String prodItemErrorMsg = "";

		if (StringUtils.isBlank(ecUrl)) {
			prodItemErrorMsg = "必填欄位";
		} else {
			AdUtilAjax adUtilAjax = new AdUtilAjax();
			String checkResultMsg = adUtilAjax.checkAdShowUrl(ecUrl, akbPfpServer);
			if (StringUtils.isNotBlank(checkResultMsg)) {
				prodItemErrorMsg = "連結錯誤";
			}
		}
		
		if (StringUtils.isNotBlank(prodItemErrorMsg)) { // 錯誤訊息非空值則記錄
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("catalog_prod_seq", catalogProdSeq);
			jsonObject.put("catalog_err_item", "ec_url");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", ecUrl);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查廣告圖像網址及圖片是否符合規範
	 * @param errorPrdItemArray
	 * @param photoPath // 圖片路徑
	 * @param catalogProdSeq // 商品編號
	 * @param ecImgUrl // 圖片網址
	 * @param ecImgBase64 // 圖片Base64編碼
	 * @param catalogUploadType // 上傳方式
	 * @return
	 * @throws Exception 
	 */
	public JSONArray checkEcImgUrl(JSONArray errorPrdItemArray, String photoPath, String catalogProdSeq, String ecImgUrl, String ecImgBase64, String catalogUploadType) throws Exception {
		String prodItemErrorMsg = ""; // 檢查到一個有錯誤，剩下檢查則略過
		
		// 將圖片下載至每個user自己的暫存圖片資料夾檢查
		String imgTempPath = photoPath + "/temp";
		String imgPath = "";

		if (StringUtils.isBlank(ecImgBase64)) {
			// 沒有Base64，有圖片網址則是用檔案上傳、自動排程上傳、PChome賣場上傳
			if (StringUtils.isBlank(prodItemErrorMsg)) {
				if (StringUtils.isBlank(ecImgUrl)) {
					prodItemErrorMsg = "必填欄位";
				}
			}
			
			if (StringUtils.isBlank(prodItemErrorMsg)) {
				AdUtilAjax adUtilAjax = new AdUtilAjax();
				String checkResultMsg = adUtilAjax.checkAdShowUrl(ecImgUrl, akbPfpServer);
				if (StringUtils.isNotBlank(checkResultMsg)) {
					prodItemErrorMsg = "連結錯誤";
				}
			}
			
			if (StringUtils.isBlank(prodItemErrorMsg)) {
				String filenameExtension = ImgUtil.getImgURLFilenameExtension(ecImgUrl);
				if (!"jpg".equalsIgnoreCase(filenameExtension) && !"gif".equalsIgnoreCase(filenameExtension)
						&& !"png".equalsIgnoreCase(filenameExtension)) {
					prodItemErrorMsg = "檔案格式錯誤";
				}
			}
			
			File imgFile = null;
			if (StringUtils.isBlank(prodItemErrorMsg)) {
				imgPath = ImgUtil.processImgPathForCatalogProd(ecImgUrl, imgTempPath, catalogProdSeq);
				String completePath = imgTempPath.substring(0, imgTempPath.indexOf("img/user/")) + imgPath;
				imgFile = new File(completePath);
				if (imgFile.length() > (180 * 1024)) {
					prodItemErrorMsg = "檔案過大";
				}
			}
			
			if (StringUtils.isBlank(prodItemErrorMsg)) {
				FileInputStream fis = new FileInputStream(imgFile);
				BufferedImage bufferedImage = ImageIO.read(fis);
				if (!EnumPfpCatalog.CATALOG_UPLOAD_STORE_URL.getType().equals(catalogUploadType) && 
						bufferedImage.getWidth() < 300 && bufferedImage.getHeight() < 300) {
					// 賣場網址上傳不檢查解析度
					prodItemErrorMsg = "解析度不足";
				}
				fis.close();
			}
			
			if (imgFile != null) {
				imgFile.delete(); // 結束後刪除暫存圖片
			}
			
		} else {
			//有Base64則用手動上傳
			if (StringUtils.isBlank(prodItemErrorMsg)) {
				String filenameExtension = ImgUtil.getImgBase64FilenameExtension(ecImgBase64);
				if (!"jpeg".equalsIgnoreCase(filenameExtension) && !"jpg".equalsIgnoreCase(filenameExtension)
						&& !"gif".equalsIgnoreCase(filenameExtension) && !"png".equalsIgnoreCase(filenameExtension)) {
					prodItemErrorMsg = "檔案格式錯誤";
				}
			}
			
			File imgFile = null;
			if (StringUtils.isBlank(prodItemErrorMsg)) {
				imgPath = ImgUtil.processImgBase64StringToImage(ecImgBase64, imgTempPath, catalogProdSeq);
				String completePath = imgTempPath.substring(0, imgTempPath.indexOf("img/user/")) + imgPath;
				imgFile = new File(completePath);
				if (imgFile.length() > (180 * 1024)) {
					prodItemErrorMsg = "檔案過大";
				}
			}
			
			if (StringUtils.isBlank(prodItemErrorMsg)) {
				FileInputStream fis = new FileInputStream(imgFile);
				BufferedImage bufferedImage = ImageIO.read(fis);
				if (bufferedImage.getWidth() < 300 && bufferedImage.getHeight() < 300) {
					prodItemErrorMsg = "解析度不足";
				}
				fis.close();
			}
			
			if (imgFile != null) {
				imgFile.delete(); // 結束後刪除暫存圖片
			}

		}

		if (StringUtils.isNotBlank(prodItemErrorMsg)) { // 錯誤訊息非空值則記錄
			JSONObject jsonObject = new JSONObject();
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
	 * 0:全新,1:二手,2:福利品
	 * @param errorPrdItemArray
	 * @param catalogProdSeq
	 * @param ecUseStatus
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray checkEcUseStatus(JSONArray errorPrdItemArray, String catalogProdSeq, String ecUseStatus) throws JSONException {
		String prodItemErrorMsg = "";
		
		if (StringUtils.isBlank(ecUseStatus)) {
			prodItemErrorMsg = "必填欄位";
		} else if (!EnumEcUseStatusType.New_Goods.getChName().equals(ecUseStatus)
				&& !EnumEcUseStatusType.Used_Goods.getChName().equals(ecUseStatus)
				&& !EnumEcUseStatusType.Welfare_Goods.getChName().equals(ecUseStatus)) {
			prodItemErrorMsg = "格式錯誤";
		}
		
		if (StringUtils.isNotBlank(prodItemErrorMsg)) { // 錯誤訊息非空值則記錄
			JSONObject jsonObject = new JSONObject();
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
	 * 0:無庫存,1:有庫存,2:預購,3:停售
	 * @param errorPrdItemArray
	 * @param catalogProdSeq
	 * @param ecStockStatus
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray checkEcStockStatus(JSONArray errorPrdItemArray, String catalogProdSeq, String ecStockStatus) throws JSONException {
		String prodItemErrorMsg = "";
		
		if (StringUtils.isBlank(ecStockStatus)) {
			prodItemErrorMsg = "必填欄位";
		} else if (!EnumEcStockStatusType.Out_Of_Stock.getChName().equals(ecStockStatus)
				&& !EnumEcStockStatusType.In_Stock.getChName().equals(ecStockStatus)
				&& !EnumEcStockStatusType.Pre_Order.getChName().equals(ecStockStatus)
				&& !EnumEcStockStatusType.Discontinued.getChName().equals(ecStockStatus)) {
			prodItemErrorMsg = "格式錯誤";
		}
		
		if (StringUtils.isNotBlank(prodItemErrorMsg)) { // 錯誤訊息非空值則記錄
			JSONObject jsonObject = new JSONObject();
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
		String prodItemErrorMsg = "";
		int ecDiscountPriceLimit = 6;

		if (StringUtils.isBlank(ecDiscountPrice)) {
			prodItemErrorMsg = "必填欄位";
		} else if (!StringUtils.isNumeric(ecDiscountPrice)) {
			prodItemErrorMsg = "格式錯誤";
		} else if (ecDiscountPrice.length() > ecDiscountPriceLimit) {
			prodItemErrorMsg = "超出金額位數限制";
		}

		if (StringUtils.isNotBlank(prodItemErrorMsg)) { // 錯誤訊息非空值則記錄
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("catalog_prod_seq", catalogProdSeq);
			jsonObject.put("catalog_err_item", "ec_discount_price");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", ecDiscountPrice);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}

	/**
	 * 檢查原價(非必填欄位)
	 * @param errorPrdItemArray
	 * @param catalogProdSeq
	 * @param ecPrice
	 * @return
	 * @throws JSONException 
	 */
	public JSONArray checkEcPrice(JSONArray errorPrdItemArray, String catalogProdSeq, String ecPrice) throws JSONException {
		String prodItemErrorMsg = "";
		int ecPriceLimit = 6;
		
		if (StringUtils.isNotBlank(ecPrice)) { // 只有空格也當沒輸入值  StringUtils.isNotBlank(" ") false
			if (!StringUtils.isNumeric(ecPrice)) {
				prodItemErrorMsg = "格式錯誤";
			} else if (ecPrice.length() > ecPriceLimit) {
				prodItemErrorMsg = "超出金額位數限制";
			}
		}

		if (StringUtils.isNotBlank(prodItemErrorMsg)) { // 錯誤訊息非空值則記錄
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("catalog_prod_seq", catalogProdSeq);
			jsonObject.put("catalog_err_item", "ec_price");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", ecPrice);
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
		String prodItemErrorMsg = "";
		int ecNameLimit = 20;
		
		if (StringUtils.isBlank(ecName)) {
			prodItemErrorMsg = "必填欄位";
		} else if (ecName.length() > ecNameLimit) {
			prodItemErrorMsg = "字數限制" + ecNameLimit + "字";
		} else if (CommonUtils.isHaveEmojiString(ecName)) {
			prodItemErrorMsg = "內含特殊字元";
			// 將Emoji圖片字串取代為空，寫入DB會噴錯
			ecName = CommonUtils.getReplaceEmojiStr(ecName);
		}
	    
		if (StringUtils.isNotBlank(prodItemErrorMsg)) { // 錯誤訊息非空值則記錄
			JSONObject jsonObject = new JSONObject();
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
		String prodItemErrorMsg = "";
		int catalogProdSeqLimit = 30;
		
		if (StringUtils.isBlank(catalogProdSeq)) {
			prodItemErrorMsg = "必填欄位";
		} else if (catalogProdSeq.length() > catalogProdSeqLimit) {
			prodItemErrorMsg = "字數限制" + catalogProdSeqLimit + "字";
		} else if (CommonUtils.isHaveEmojiString(catalogProdSeq) || CommonUtils.checkStringNonEnglishNumbers(catalogProdSeq)) {
			prodItemErrorMsg = "內含特殊字元";
			// 將Emoji圖片字串取代為空，寫入DB會噴錯
			catalogProdSeq = CommonUtils.getReplaceEmojiStr(catalogProdSeq);
		}
		
		if (StringUtils.isNotBlank(prodItemErrorMsg)) { // 錯誤訊息非空值則記錄
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("catalog_prod_seq", catalogProdSeq);
			jsonObject.put("catalog_err_item", "id");
			jsonObject.put("catalog_err_reason", prodItemErrorMsg);
			jsonObject.put("catalog_err_rawdata", catalogProdSeq);
			errorPrdItemArray.put(jsonObject);
		}
		return errorPrdItemArray;
	}
	
	public void setAkbPfpServer(String akbPfpServer) {
		this.akbPfpServer = akbPfpServer;
	}
}