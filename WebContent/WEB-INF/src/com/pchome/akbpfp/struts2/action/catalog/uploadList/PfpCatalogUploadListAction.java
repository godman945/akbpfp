package com.pchome.akbpfp.struts2.action.catalog.uploadList;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.pchome.akbpfp.db.service.catalog.uploadList.IPfpCatalogUploadListService;
import com.pchome.akbpfp.struts2.BaseCookieAction;


public class PfpCatalogUploadListAction extends BaseCookieAction{
	
	private Map<String,Object> dataMap;
	private File fileUpload;
	private String fileUploadFileName;
	
//	private String fileName;
//	private String fileType;
	
//	private File myFile;
//	private String myFileFileName;
//	private String myFileContentType;
	
//	private String filename;
//	private String contenType;
	
	// 在main測試需要加static，static待移除
	private IPfpCatalogUploadListService pfpCatalogUploadListService;
	
//	JSONObject catalogProdJsonData;
	
	/**
	 * 商品廣告-檔案上傳CSV
	 * @return
	 * @throws Exception 
	 */
	public String fileUploadCSV() throws Exception {
		//dataMap中的資料將會被Struts2轉換成JSON字串，所以用Map<String,Object>
		dataMap = new HashMap<String, Object>();
		
		// 檔案上傳部分
//		System.out.println("fileUpload:" + fileUpload);
//		System.out.println("fileUpload:" + fileUploadFileName);
//		System.out.println(FilenameUtils.getBaseName(fileUploadFileName)); // 取得檔名部分
//		System.out.println(FilenameUtils.getExtension(fileUploadFileName)); // 取得副檔名(不含小數點)
//		File createFile = new File("/home/webuser/akb/pfp/csv/" + super.getCustomer_info_id() + "/" + fileUploadFileName);
//		FileUtils.copyFile(fileUpload, createFile);
		
//		String testStr = "{\"catalog_seq\":\"PC201807240000000001\", \"catalog_type\":\"1\", \"update_way\":\"2\", \"pfp_customer_info_id\":\"AC2013071700001\", \"catalog_prod_item\":[{\"id\":\"123456789\", \"prod_name\":\"PS4 PRO\", \"prod_price\":\"12999\", \"prod_discount_price\":\"10000\", \"prod_stock_status\":\"1\", \"prod_use_status\":\"1\", \"prod_img_url\":\"https://a.ecimg.tw/items/DGBJBC19008SGKD/000001_1522029304.jpg\", \"prod_url\":\"https://24h.pchome.com.tw/prod/DGBJBC-19008SGKD\", \"prod_category\":\"電玩\"}]}";
		String testStr = "{\"catalog_seq\":\"PC201807240000000001\", \"catalog_type\":\"1\", \"update_way\":\"2\", \"pfp_customer_info_id\":\"AC2013071700001\", \"catalog_prod_item\":[{\"id\":\"123456789\", \"prod_name\":\"PS4 PRO\", \"prod_price\":\"12999\", \"prod_discount_price\":\"10000\", \"prod_stock_status\":\"1\", \"prod_use_status\":\"1\", \"prod_img_url\":\"https://a.ecimg.tw/items/DGBJBC19008SGKD/000001_1522029304.jpg\", \"prod_url\":\"https://24h.pchome.com.tw/prod/DGBJBC-19008SGKD\", \"prod_category\":\"電玩\"}, "
				+ "{\"id\":\"987654321\", \"prod_name\":\"人中之龍3\", \"prod_price\":\"1290\", \"prod_discount_price\":\"1190\", \"prod_stock_status\":\"1\", \"prod_use_status\":\"1\", \"prod_img_url\":\"https://a.ecimg.tw/items/DGBJDJA90093KJR/000001_1528274186.jpg\", \"prod_url\":\"https://24h.pchome.com.tw/prod/DGBJDJ-A90093KJR?q=/S/DGBJ88\", \"prod_category\":\"電玩\"}, "
				+ "{\"id\":\"\", \"prod_name\":\"\", \"prod_price\":\"\", \"prod_discount_price\":\"\", \"prod_stock_status\":\"\", \"prod_use_status\":\"\", \"prod_img_url\":\"\", \"prod_url\":\"\", \"prod_category\":\"電玩\"}, "
				+ "{\"id\":\"\", \"prod_name\":\"\", \"prod_price\":\"\", \"prod_discount_price\":\"\", \"prod_stock_status\":\"\", \"prod_use_status\":\"\", \"prod_img_url\":\"https://a.ecimg.tw/items/aaa/000001_1528274186.jpg\", \"prod_url\":\"\", \"prod_category\":\"電玩\"}]}";
		JSONObject catalogProdJsonData = new JSONObject(testStr);
		dataMap = processCatalogProdJsonData(catalogProdJsonData);
		
		System.out.println("finalDataMap:" + dataMap);
		
		return SUCCESS;
	}
	
	/**
	 * 處理上傳的json資料
	 * @param catalogProdJsonData 
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> processCatalogProdJsonData(JSONObject catalogProdJsonData) throws Exception {
		dataMap = new HashMap<String, Object>();
		
		dataMap = pfpCatalogUploadListService.processCatalogProdJsonData(catalogProdJsonData);
		
		return dataMap;
	}
	
	
	
	public static void main(String age[]) throws Exception{
//		System.out.println("AAAOXISJD");
//		
//		String testStr = "{\"catalog_seq\":\"PC201807240000000001\", \"catalog_type\":\"1\", \"update_way\":\"2\", \"pfp_customer_info_id\":\"AC2013071700001\", \"catalog_prod_item\":[{\"id\":\"123456789\", \"prod_name\":\"PS4 PRO\", \"prod_price\":\"12999\", \"prod_discount_price\":\"10000\", \"prod_stock_status\":\"1\", \"prod_use_status\":\"1\", \"prod_img_url\":\"https://a.ecimg.tw/items/DGBJBC19008SGKD/000001_1522029304.jpg\", \"prod_url\":\"https://24h.pchome.com.tw/prod/DGBJBC-19008SGKD\", \"prod_category\":\"電玩\"}, "
//				+ "{\"id\":\"987654321\", \"prod_name\":\"人中之龍3\", \"prod_price\":\"1290\", \"prod_discount_price\":\"1190\", \"prod_stock_status\":\"1\", \"prod_use_status\":\"1\", \"prod_img_url\":\"https://a.ecimg.tw/items/DGBJDJA90093KJR/000001_1528274186.jpg\", \"prod_url\":\"https://24h.pchome.com.tw/prod/DGBJDJ-A90093KJR?q=/S/DGBJ88\", \"prod_category\":\"電玩\"}, "
//				+ "{\"id\":\"\", \"prod_name\":\"\", \"prod_price\":\"\", \"prod_discount_price\":\"\", \"prod_stock_status\":\"\", \"prod_use_status\":\"\", \"prod_img_url\":\"\", \"prod_url\":\"\", \"prod_category\":\"電玩\"}]}";
//		JSONObject apiJsonObject = new JSONObject(testStr);
//		System.out.println(apiJsonObject);
//		System.out.println(apiJsonObject.get("catalog_seq"));
//		catalogUploadListService.processCatalogProdJsonData(apiJsonObject);
	}






	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public void setPfpCatalogUploadListService(IPfpCatalogUploadListService pfpCatalogUploadListService) {
		this.pfpCatalogUploadListService = pfpCatalogUploadListService;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	
	
//	System.out.println("myFile:" + myFile);
//	System.out.println(fileUpload.getName());
//	System.out.println("filename:" + fileName);
//	System.out.println("contenType:" + contenType);
	
//	System.out.println("filename:" + filename);
//	System.out.println("fileType:" + fileType);
	
	// 用struts2的功能取myFileFileName
	// 參考 http://hubertjava.blogspot.com/2009/03/struts2-file-upload.html
//	System.out.println("myFile:" + myFile);
//	System.out.println("myFileFileName:" + myFileFileName);
//	System.out.println("myFileContentType:" + myFileContentType);
	
	/* 
	 * FileUtils.copyFile(srcFile, destFile);
	 * https://blog.csdn.net/shb_derek1/article/details/8530398
	 * 等於說 我在我要放檔案的位置先開一個，再用這個API就直接把檔案copy過去 
	*/
//	File createFile = new File("/home/webuser/akb/pfp/csv/" + fileName + fileType);
//	InputStream is = new FileInputStream(fileUpload);
//	OutputStream os = new FileOutputStream(createFile);
//	byte[] buffer = new byte[1024];
//	int length = 0;
//	while((length = is.read(buffer)) > 0) {
//		os.write(buffer, 0, length);
//	}
//	is.close();
//	os.close();
}
