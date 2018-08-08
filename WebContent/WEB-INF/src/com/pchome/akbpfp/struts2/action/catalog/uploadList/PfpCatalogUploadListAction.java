package com.pchome.akbpfp.struts2.action.catalog.uploadList;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.pchome.akbpfp.db.pojo.PfpCatalogUploadLog;
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
	
	// api用
	private String jsonData;
	
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
		String path = "/home/webuser/akb/pfp/fileUpload/" + super.getCustomer_info_id() + "/" + fileUploadFileName;
//		String fileType = FilenameUtils.getExtension(fileUploadFileName);
		File createFile = new File(path);
		FileUtils.copyFile(fileUpload, createFile);
		
		JSONObject catalogProdJsonData = pfpCatalogUploadListService.getCSVFileDataToJson(path);
		catalogProdJsonData.put("catalog_seq", "PC201808060000000001");
		catalogProdJsonData.put("catalog_type", "1");
		catalogProdJsonData.put("update_way", "1");
		catalogProdJsonData.put("fileName", fileUploadFileName);
		catalogProdJsonData.put("pfp_customer_info_id", super.getCustomer_info_id());
		
		System.out.println("catalogProdJsonData:" + catalogProdJsonData);
		
//		String testStr = "{\"catalog_seq\":\"PC201808060000000001\", \"catalog_type\":\"1\", \"update_way\":\"1\", \"pfp_customer_info_id\":\"AC2013071700001\", \"catalog_prod_item\":[{\"id\":\"123456789\", \"prod_name\":\"PS4 PRO\", \"prod_title\":\"PS4 PRO_title\", \"prod_price\":\"12999\", \"prod_discount_price\":\"10000\", \"prod_stock_status\":\"1\", \"prod_use_status\":\"1\", \"prod_img_url\":\"https://a.ecimg.tw/items/DGBJBC19008SGKD/000001_1522029304.jpg\", \"prod_url\":\"https://24h.pchome.com.tw/prod/DGBJBC-19008SGKD\", \"prod_category\":\"電玩\"}, "
//				+ "{\"id\":\"987654321\", \"prod_name\":\"人中之龍3\", \"prod_title\":\"人中之龍3_title\", \"prod_price\":\"1290\", \"prod_discount_price\":\"1190\", \"prod_stock_status\":\"1\", \"prod_use_status\":\"1\", \"prod_img_url\":\"https://a.ecimg.tw/items/DGBJDJA90093KJR/000001_1528274186.jpg\", \"prod_url\":\"https://24h.pchome.com.tw/prod/DGBJDJ-A90093KJR?q=/S/DGBJ88\", \"prod_category\":\"電玩\"}, "
//				+ "{\"id\":\"001\", \"prod_name\":\"GIRLY打褶設計口袋傘裙\", \"prod_title\":\"傘裙\", \"prod_price\":\"20\", \"prod_discount_price\":\"349\", \"prod_stock_status\":\"0\", \"prod_use_status\":\"1\", \"prod_img_url\":\"https://a.ecimg.tw/items/DGBJDJA90093KJR/000001_1528274186.jpg\", \"prod_url\":\"http://www.pazzo.com.tw/Detail/P401014501204\", \"prod_category\":\"服飾\"}, "
//				+ "{\"id\":\"002\", \"prod_name\":\"洋裝輕夏蕾絲簍空小花洋裝\", \"prod_title\":\"洋裝\", \"prod_price\":\"890\", \"prod_discount_price\":\"549\", \"prod_stock_status\":\"1\", \"prod_use_status\":\"1\", \"prod_img_url\":\"https://a.ecimg.tw/items/DGBJDJA90093KJR/000001_1528274186.jpg\", \"prod_url\":\"http://www.pazzo.com.tw/Detail/P402017880204\", \"prod_category\":\"服飾\"}, "
//				+ "{\"id\":\"003\", \"prod_name\":\"CHIC GIRL激瘦小喇叭吊帶牛仔褲\", \"prod_title\":\"牛仔褲\", \"prod_price\":\"1000\", \"prod_discount_price\":\"880\", \"prod_stock_status\":\"1\", \"prod_use_status\":\"1\", \"prod_img_url\":\"https://a.ecimg.tw/items/DGBJDJA90093KJR/000001_1528274186.jpg\", \"prod_url\":\"http://www.pazzo.com.tw/Detail/P402016801904\", \"prod_category\":\"服飾\"}, "
//				+ "{\"id\":\"\", \"prod_name\":\"\", \"prod_title\":\"\", \"prod_price\":\"\", \"prod_discount_price\":\"\", \"prod_stock_status\":\"\", \"prod_use_status\":\"\", \"prod_img_url\":\"\", \"prod_url\":\"\", \"prod_category\":\"電玩\"}, "
//				+ "{\"id\":\"\", \"prod_name\":\"\", \"prod_title\":\"\", \"prod_price\":\"\", \"prod_discount_price\":\"\", \"prod_stock_status\":\"\", \"prod_use_status\":\"\", \"prod_img_url\":\"https://a.ecimg.tw/items/aaa/000001_1528274186.jpg\", \"prod_url\":\"\", \"prod_category\":\"電玩\"}, "
//				+ "{\"id\":\"3345678\", \"prod_name\":\"最後生還者\", \"prod_title\":\"最後生還者_title\", \"prod_price\":\"800\", \"prod_discount_price\":\"690\", \"prod_stock_status\":\"1\", \"prod_use_status\":\"1\", \"prod_img_url\":\"https://a.ecimg.tw/items/DGBJDJA90093KJR/000001_1528274186.jpg\", \"prod_url\":\"https://24h.pchome.com.tw/prod/DGBJDJ-A90093KJR?q=/S/DGBJ88\", \"prod_category\":\"電玩\"}, "
//				+ "{\"id\":\"\", \"prod_name\":\"\", \"prod_title\":\"\", \"prod_price\":\"\", \"prod_discount_price\":\"\", \"prod_stock_status\":\"\", \"prod_use_status\":\"\", \"prod_img_url\":\"https://a.ecimg.tw/items/aaa/000001_1528274186.jpg\", \"prod_url\":\"\", \"prod_category\":\"電玩\"}]} ";
//		JSONObject catalogProdJsonData = new JSONObject(testStr);
//		
		dataMap = pfpCatalogUploadListService.processCatalogProdJsonData(catalogProdJsonData);
		System.out.println("finalDataMap:" + dataMap);
		
		return SUCCESS;
	}
	
	/**
	 * 處理上傳的json資料
	 * @return
	 * @throws Exception
	 */
	public String processCatalogProdJsonDataApi() {
		dataMap = new HashMap<String, Object>();
		System.out.println("jsonData:" + jsonData);
//		dataMap = pfpCatalogUploadListService.processCatalogProdJsonData(catalogProdJsonData);
		
		return SUCCESS;
	}
	
	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
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
	
	
//	public static void main(String age[]) throws Exception{
//	System.out.println("AAAOXISJD");
//	
//	String testStr = "{\"catalog_seq\":\"PC201808060000000001\", \"catalog_type\":\"1\", \"update_way\":\"2\", \"pfp_customer_info_id\":\"AC2013071700001\", \"catalog_prod_item\":[{\"id\":\"123456789\", \"prod_name\":\"PS4 PRO\", \"prod_price\":\"12999\", \"prod_discount_price\":\"10000\", \"prod_stock_status\":\"1\", \"prod_use_status\":\"1\", \"prod_img_url\":\"https://a.ecimg.tw/items/DGBJBC19008SGKD/000001_1522029304.jpg\", \"prod_url\":\"https://24h.pchome.com.tw/prod/DGBJBC-19008SGKD\", \"prod_category\":\"電玩\"}, "
//			+ "{\"id\":\"987654321\", \"prod_name\":\"人中之龍3\", \"prod_price\":\"1290\", \"prod_discount_price\":\"1190\", \"prod_stock_status\":\"1\", \"prod_use_status\":\"1\", \"prod_img_url\":\"https://a.ecimg.tw/items/DGBJDJA90093KJR/000001_1528274186.jpg\", \"prod_url\":\"https://24h.pchome.com.tw/prod/DGBJDJ-A90093KJR?q=/S/DGBJ88\", \"prod_category\":\"電玩\"}, "
//			+ "{\"id\":\"\", \"prod_name\":\"\", \"prod_price\":\"\", \"prod_discount_price\":\"\", \"prod_stock_status\":\"\", \"prod_use_status\":\"\", \"prod_img_url\":\"\", \"prod_url\":\"\", \"prod_category\":\"電玩\"}]}";
//	JSONObject apiJsonObject = new JSONObject(testStr);
//	System.out.println(apiJsonObject);
//	System.out.println(apiJsonObject.get("catalog_seq"));
//	catalogUploadListService.processCatalogProdJsonData(apiJsonObject);
//}
	
	
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
//	File createFile = new File("/home/webuser/akb/pfp/fileUpload/" + fileName + fileType);
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
