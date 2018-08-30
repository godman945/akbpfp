package com.pchome.akbpfp.struts2.action.catalog.uploadList;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONObject;

import com.pchome.akbpfp.db.service.catalog.uploadList.IPfpCatalogUploadListService;
import com.pchome.akbpfp.struts2.BaseCookieAction;


public class PfpCatalogUploadListAction extends BaseCookieAction{
	
	private Map<String,Object> dataMap;
	private String productFilePath;
	private File fileUpload;
	private String fileUploadFileName;
	
	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	private String fileName;
//	private String fileType;
	
//	private File myFile;
//	private String myFileFileName;
//	private String myFileContentType;
	
//	private String filename;
//	private String contenType;
	
	private IPfpCatalogUploadListService pfpCatalogUploadListService;
	
	// api用
	private String catalog_seq;
	private String catalog_type;
	private String update_way;
	private String update_content;
	private String pfp_customer_info_id;
	private String catalog_prod_item;
	
	/**
	 * 商品廣告-檔案上傳CSV
	 * @return
	 * @throws Exception 
	 */
	public String fileUploadCSV() throws Exception {
		//dataMap中的資料將會被Struts2轉換成JSON字串，所以用Map<String,Object>
		dataMap = new HashMap<String, Object>();
		System.out.println(FilenameUtils.getExtension(fileUploadFileName));
		if(!"csv".equalsIgnoreCase(FilenameUtils.getExtension(fileUploadFileName))){
			dataMap.put("status", "ERROR");
			dataMap.put("msg", "上傳檔案錯誤!");
			return SUCCESS;
		}
		
		Date updateDatetime = new Date();
		
		// 檔案上傳部分
//		System.out.println("fileUpload:" + fileUpload);
//		System.out.println("fileUpload:" + fileUploadFileName);
//		System.out.println(FilenameUtils.getBaseName(fileUploadFileName)); // 取得檔名部分
//		System.out.println(FilenameUtils.getExtension(fileUploadFileName)); // 取得副檔名(不含小數點)
		String path = productFilePath + super.getCustomer_info_id() + "/" + fileUploadFileName;
//		String fileType = FilenameUtils.getExtension(fileUploadFileName);
		File createFile = new File(path);
		FileUtils.copyFile(fileUpload, createFile);
		
		JSONObject catalogProdJsonData = pfpCatalogUploadListService.getCSVFileDataToJson(path);
		catalogProdJsonData.put("catalog_seq", "PC201808220000000001");
		catalogProdJsonData.put("catalog_type", "1");
		catalogProdJsonData.put("update_way", "1");
		catalogProdJsonData.put("update_content", fileUploadFileName);
		catalogProdJsonData.put("pfp_customer_info_id", super.getCustomer_info_id());
		catalogProdJsonData.put("update_datetime", formatter.format(updateDatetime));
		
		dataMap = pfpCatalogUploadListService.processCatalogProdJsonData(catalogProdJsonData);

		return SUCCESS;
	}
	
	/**
	 * 提供API處理商品廣告的json資料
	 * @return
	 * @throws Exception
	 */
	public String processCatalogProdJsonDataApi() throws Exception {
		dataMap = new HashMap<String, Object>();

		JSONObject apiJsonData = new JSONObject();
		apiJsonData.put("catalog_seq", catalog_seq);
		apiJsonData.put("catalog_type", catalog_type);
		apiJsonData.put("update_way", update_way);
		apiJsonData.put("update_content", update_content);
		apiJsonData.put("pfp_customer_info_id", pfp_customer_info_id);
		apiJsonData.put("catalog_prod_item", catalog_prod_item);
		apiJsonData.put("update_datetime", formatter.format(new Date()));
		
		dataMap = pfpCatalogUploadListService.processCatalogProdJsonData(apiJsonData);
		
		return SUCCESS;
	}
	
	public void setProductFilePath(String productFilePath) {
		this.productFilePath = productFilePath;
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

	public void setCatalog_seq(String catalog_seq) {
		this.catalog_seq = catalog_seq;
	}

	public void setCatalog_type(String catalog_type) {
		this.catalog_type = catalog_type;
	}

	public void setUpdate_way(String update_way) {
		this.update_way = update_way;
	}

	public void setPfp_customer_info_id(String pfp_customer_info_id) {
		this.pfp_customer_info_id = pfp_customer_info_id;
	}

	public void setCatalog_prod_item(String catalog_prod_item) {
		this.catalog_prod_item = catalog_prod_item;
	}

	public void setUpdate_content(String update_content) {
		this.update_content = update_content;
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
