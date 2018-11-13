package com.pchome.akbpfp.db.service.catalog.uploadList;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.pchome.akbpfp.db.service.IBaseService;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogProdEcErrorVO;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogUploadListVO;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogUploadLogVO;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogVO;

public interface IPfpCatalogUploadListService extends IBaseService<String, String> {

	/**
	 * 依照商品目錄類別，處理相對應的部分
	 */
	Map<String, Object> processCatalogProdJsonData(JSONObject catalogProdJsonData) throws JSONException, Exception;

	/**
	 * 將csv檔案內容轉成json格式
	 * @throws IOException 
	 * @throws JSONException 
	 */
	JSONObject getCSVFileDataToJson(PfpCatalogUploadListVO vo);
	
	/**
	 * 檢查檔案格式是否為我們提供的CSV檔格式
	 * @param vo
	 * @return
	 */
	Map<String, Object> checkCSVFile(PfpCatalogUploadListVO vo);

	/**
	 * 刪除 商品目錄更新紀錄
	 * @param vo
	 */
	void deletePfpCatalogUploadLog(PfpCatalogVO vo);
	
	/**
	 * 刪除 商品目錄更新錯誤紀錄
	 * @param vo
	 */
	void deletePfpCatalogUploadErrLog(PfpCatalogVO vo);

	/**
	 * 新增log記錄
	 * @param pfpCatalogUploadLogVO
	 * @throws Exception 
	 */
	void savePfpCatalogUploadLog(PfpCatalogUploadLogVO pfpCatalogUploadLogVO) throws Exception;

	/**
	 * 檢查商品編號是否在此目錄下已重複
	 * @param catalogSeq 商品目錄
	 * @param catalogProdSeq 商品編號
	 * @return 重複:1  不重複:0
	 */
	int checkCatalogProdSeq(String catalogSeq, String catalogProdSeq);

	/**
	 * 刪除哪位客戶的哪個商品目錄的圖片資料夾內容
	 * @param vo.getPfpCustomerInfoId() pfp_id
	 * @param vo.getCatalogSeq() 商品目錄編號
	 */
	void deleteCatalogProdImgFolderAndData(PfpCatalogVO vo);

	/**
	 * 刪除哪位客戶的哪個商品目錄上傳過的CSV
	 * @param vo.getPfpCustomerInfoId() pfp_id
	 * @param vo.getCatalogSeq() 商品目錄編號
	 */
	void deleteCatalogProdCSVFolderAndData(PfpCatalogVO vo);

	/**
	 * 刪除 商品目錄設定 
	 * @param vo
	 */
	void deletePfpCatalogSetup(PfpCatalogVO vo);

	/**
	 * 查詢目錄商品上傳錯誤記錄清單
	 * @param vo
	 * @return
	 */
	List<PfpCatalogProdEcErrorVO> getCatalogProdUploadErrList(PfpCatalogProdEcErrorVO vo);

	/**
	 * 刪除 一般購物類商品上傳錯誤清單
	 * @param vo
	 */
	void deletePfpCatalogProdEcError(PfpCatalogVO vo);
	
}