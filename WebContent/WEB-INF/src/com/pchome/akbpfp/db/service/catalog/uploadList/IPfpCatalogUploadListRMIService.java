package com.pchome.akbpfp.db.service.catalog.uploadList;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;

public interface IPfpCatalogUploadListRMIService {

	/**
	 * 依照商品目錄類別，處理相對應的部分
	 */
	Map<String, Object> processCatalogProdJsonData(String catalogProdJsonDataStr) throws JSONException, Exception;

	/**
	 * 將csv檔案內容轉成json格式
	 * @throws IOException 
	 * @throws JSONException 
	 */
	String getCSVFileDataToJson(String path);

}
