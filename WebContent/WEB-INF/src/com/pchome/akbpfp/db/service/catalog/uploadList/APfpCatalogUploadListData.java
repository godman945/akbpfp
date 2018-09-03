package com.pchome.akbpfp.db.service.catalog.uploadList;

import org.json.JSONObject;

public abstract class APfpCatalogUploadListData {
//	private APfpCatalogUploadListData pfpCatalogUploadListData;
	
	public abstract Object processCatalogProdJsonData(JSONObject catalogProdJsonData) throws Exception;

//	public APfpCatalogUploadListData getPfpCatalogUploadListData() {
//		return pfpCatalogUploadListData;
//	}

}
