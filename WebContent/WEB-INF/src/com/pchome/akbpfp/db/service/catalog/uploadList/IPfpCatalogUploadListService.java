package com.pchome.akbpfp.db.service.catalog.uploadList;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.pchome.akbpfp.db.service.IBaseService;

public interface IPfpCatalogUploadListService extends IBaseService<String, String> {

	Map<String, Object> processCatalogProdJsonData(JSONObject catalogProdJsonData) throws JSONException, Exception;
	
	
	
	
//	// 取流水編號
//	public String getSerialNumber(EnumSequenceTableName enumSequenceTableName) throws Exception;
//
//	//取得流水編號 part 2
//	public String getId(EnumSequenceTableName enumSequenceTableName,String mid) throws Exception;

}
