package com.pchome.akbpfp.db.service.catalog.uploadList;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.pchome.akbpfp.db.service.IBaseService;

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
	JSONObject getCSVFileDataToJson(String path);

}
