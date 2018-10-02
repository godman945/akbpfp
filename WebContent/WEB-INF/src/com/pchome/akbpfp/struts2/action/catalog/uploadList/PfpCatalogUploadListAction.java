package com.pchome.akbpfp.struts2.action.catalog.uploadList;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;


import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.service.catalog.uploadList.IPfpCatalogUploadListService;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogUploadListVO;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.akbpfp.struts2.ajax.ad.AdUtilAjax;
import com.pchome.enumerate.ad.EnumPfpCatalog;
import com.pchome.soft.depot.utils.HttpUtil;



public class PfpCatalogUploadListAction extends BaseCookieAction{
	
	private Map<String,Object> dataMap;
	private String productFilePath;
	private File fileUpload;
	private String fileUploadFileName;
	
	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmmss");
//	private String fileName;
//	private String fileType;
	
//	private File myFile;
//	private String myFileFileName;
//	private String myFileContentType;
	
//	private String filename;
//	private String contenType;
	
	private IPfpCatalogUploadListService pfpCatalogUploadListService;
	private IPfpCatalogService pfpCatalogService;
	protected final int bufferReaderKB = 8; // 讀取資料使用的buffer大小(預設8KB)
	
	private List<PfpCatalogVO> catalogList = new ArrayList<PfpCatalogVO>(); // 查詢結果
	private String catalogSeq; // 商品目錄ID
	private String selectUploadFlag; // 選擇上傳方式flag帶入相對畫面
	private String updateWay; // 更新方式(1.取代,2.更新)
	private String akbPfpServer;
	
	//自動排程
	private String jobURL; // 輸入的自動排程網址
	private String uploadContent;
	
	// api用
	private String catalog_seq;
	private String catalog_type;
	private String update_way;
	private String update_content;
	private String pfp_customer_info_id;
	private String catalog_prod_item;
	
	/**
	 * 選擇商品資料上傳方式
	 * @return
	 * @throws Exception
	 */
	public String selectProductDataSource() throws Exception {
		
		System.out.println("catalogSeq:" + catalogSeq);
		catalogDropDownMenu();
		return SUCCESS;
	}
	
	/**
	 * 根據選擇上傳方式，導相對畫面
	 * @return
	 * @throws Exception
	 */
	public String selectUpload() throws Exception {
		System.out.println("catalogSeq:" + catalogSeq);

		catalogDropDownMenu();
		
		PfpCatalog pfpCatalog = pfpCatalogService.get(catalogSeq); // 取得商品目錄 table資料
		if (StringUtils.isNotBlank(pfpCatalog.getCatalogUploadType()) && !selectUploadFlag.equals(pfpCatalog.getCatalogUploadType())) {
			// 參數確認，如果table有資料，與傳進來的資料不符，表示前端被修改值，則傳出去
			return SUCCESS;
		}
		
		if (selectUploadFlag.equals(EnumPfpCatalog.CATALOG_UPLOAD_FILE_UPLOAD.getType())) {
			return "fileUpload";
		} else if (selectUploadFlag.equals(EnumPfpCatalog.CATALOG_UPLOAD_AUTOMATIC_SCHEDULING.getType())) {
			uploadContent = pfpCatalog.getCatalogUploadContent().trim();
			return "automaticScheduling";
		} else if (selectUploadFlag.equals(EnumPfpCatalog.CATALOG_UPLOAD_STORE_URL.getType())) {
			return "storeURL";
		} else if (selectUploadFlag.equals(EnumPfpCatalog.CATALOG_UPLOAD_MANUAL_UPLOAD.getType())) {
			return "manualUpload";
		}
		return SUCCESS;
	}
	
	/**
	 * 檢查檔案
	 * @return
	 * @throws Exception
	 */
	public String catalogUploadCheckFileData() throws Exception {
		System.out.println("catalogSeq:" + catalogSeq);
		
		dataMap = new HashMap<String, Object>();
		if (!"csv".equalsIgnoreCase(FilenameUtils.getExtension(fileUploadFileName))) {
			dataMap.put("status", "ERROR");
			dataMap.put("msg", "上傳檔案錯誤!");
			return SUCCESS;
		}
		
		// 取得商品目錄 table資料
		PfpCatalog pfpCatalog = pfpCatalogService.get(catalogSeq);
		
		Date updateDatetime = new Date();
		PfpCatalogUploadListVO vo = new PfpCatalogUploadListVO();
		vo.setCatalogType(pfpCatalog.getCatalogType());
		
		// 檔案上傳部分
		vo.setFileUploadPath(productFilePath + super.getCustomer_info_id() + "/" + formatter2.format(updateDatetime) + "_" + fileUploadFileName);
		File createFile = new File(vo.getFileUploadPath());
		FileUtils.copyFile(fileUpload, createFile);
		
		dataMap = pfpCatalogUploadListService.checkCSVFile(vo);
		createFile.delete(); // 檢查完後刪除檔案
		
		return SUCCESS;
	}
	
	/**
	 * 商品廣告-檔案上傳CSV
	 * @return
	 * @throws Exception 
	 */
	public String catalogProdFileUploadCSV() throws Exception {
		//dataMap中的資料將會被Struts2轉換成JSON字串，所以用Map<String,Object>
		dataMap = new HashMap<String, Object>();
		
		if(!"csv".equalsIgnoreCase(FilenameUtils.getExtension(fileUploadFileName))){
			dataMap.put("status", "ERROR");
			dataMap.put("msg", "上傳檔案錯誤!");
			return SUCCESS;
		}
		
		// 取得商品目錄 table資料
		PfpCatalog pfpCatalog = pfpCatalogService.get(catalogSeq);
		
		Date updateDatetime = new Date();
		PfpCatalogUploadListVO vo = new PfpCatalogUploadListVO();
		vo.setCatalogType(pfpCatalog.getCatalogType());
		
		
		// 檔案上傳部分
//		System.out.println("fileUpload:" + fileUpload);
//		System.out.println("fileUpload:" + fileUploadFileName);
//		System.out.println(FilenameUtils.getBaseName(fileUploadFileName)); // 取得檔名部分
//		System.out.println(FilenameUtils.getExtension(fileUploadFileName)); // 取得副檔名(不含小數點)
		vo.setFileUploadPath(productFilePath + super.getCustomer_info_id() + "/" + formatter2.format(updateDatetime) + "_" + fileUploadFileName);
//		String fileType = FilenameUtils.getExtension(fileUploadFileName);
		File createFile = new File(vo.getFileUploadPath());
		FileUtils.copyFile(fileUpload, createFile);
		
		JSONObject catalogProdJsonData = pfpCatalogUploadListService.getCSVFileDataToJson(vo);
		catalogProdJsonData.put("catalog_seq", catalogSeq);
		catalogProdJsonData.put("catalog_type", pfpCatalog.getCatalogType());
		catalogProdJsonData.put("update_way", updateWay);
		catalogProdJsonData.put("update_content", fileUploadFileName);
		catalogProdJsonData.put("pfp_customer_info_id", super.getCustomer_info_id());
		catalogProdJsonData.put("update_datetime", formatter.format(updateDatetime));
		
		dataMap = pfpCatalogUploadListService.processCatalogProdJsonData(catalogProdJsonData);

		return SUCCESS;
	}
	
	/**
	 * 檢查輸入的自動排程網址
	 * @return
	 * @throws Exception
	 */
	public String ajaxCheckJobURL() throws Exception {
		dataMap = new HashMap<String, Object>();

		AdUtilAjax adUtilAjax = new AdUtilAjax();
		boolean checkUrlStatus = adUtilAjax.checkUrl(jobURL, akbPfpServer);
		if (!checkUrlStatus) {
			dataMap.put("status", "ERROR");
			return SUCCESS;
		}
		
		return SUCCESS;
	}
	
	/**
	 * 自動排程上傳 
	 * 1.下載檔案
	 * 2.匯入商品
	 * 3.更新目錄資料
	 * @return
	 * @throws Exception
	 */
	public String catalogProdAutoJob() throws Exception {
		//待測試
		
		// 取得商品目錄 table資料
		PfpCatalog pfpCatalog = pfpCatalogService.get(catalogSeq);
		
		PfpCatalogUploadListVO vo = new PfpCatalogUploadListVO();
		vo.setCatalogType(pfpCatalog.getCatalogType());
		
		Date updateDatetime = new Date();
		fileUploadFileName = jobURL.substring(jobURL.lastIndexOf("/") + 1);
		String downloadPath = productFilePath + super.getCustomer_info_id() + "/" + formatter2.format(updateDatetime) + "_" + fileUploadFileName;
		boolean downloadStatus = loadURLFile(jobURL, downloadPath);
		
		if (downloadStatus) {
			vo.setFileUploadPath(downloadPath);
			JSONObject catalogProdJsonData = pfpCatalogUploadListService.getCSVFileDataToJson(vo);
			catalogProdJsonData.put("catalog_seq", catalogSeq);
			catalogProdJsonData.put("catalog_type", pfpCatalog.getCatalogType());
			catalogProdJsonData.put("update_way", updateWay);
			catalogProdJsonData.put("update_content", fileUploadFileName);
			catalogProdJsonData.put("pfp_customer_info_id", super.getCustomer_info_id());
			catalogProdJsonData.put("update_datetime", formatter.format(updateDatetime));
			
			dataMap = pfpCatalogUploadListService.processCatalogProdJsonData(catalogProdJsonData);
		}
		
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
	
	/**
	 * 目錄下拉式選單資料
	 */
	private void catalogDropDownMenu() {
		PfpCatalogVO vo = new PfpCatalogVO();
		vo.setPfpCustomerInfoId(super.getCustomer_info_id());
		vo.setPaginationFlag(false);
		catalogList = pfpCatalogService.getPfpCatalogList(vo);
		System.out.println("catalogList:" + catalogList);
	}
	
	/**
	* 讀取遠端檔案，遠端檔案可以為任何格式rar,rmvb,jpg...
	* 註:此method一旦開始讀檔之後就會整個hold在此method,所以不建議讀大檔,取用讀大檔請用LoadNetFile Class
	* 參考http://blog.xuite.net/ray00000test/blog/63561879-http%E8%88%87https%E8%AE%80%E5%8F%96%E7%B6%B2%E9%A0%81%E3%80%81%E4%B8%8B%E8%BC%89%E6%AA%94%E6%A1%88
	* @param urlPath    檔案網址
	* @param savePath    存檔路徑
	* @return true:下載成功, false:下載失敗
	*/
	public boolean loadURLFile(String urlPath, String savePath) {
		try {
			HttpUtil.disableCertificateValidation();
			URL zeroFile = new URL(urlPath);
			InputStream is = zeroFile.openStream();
			BufferedInputStream bs = new BufferedInputStream(is, bufferReaderKB * 1024);
			byte[] b = new byte[1024]; // 一次取得 1024 個 bytes
			FileOutputStream fs = new FileOutputStream(savePath);
			int len;
			while ((len = bs.read(b, 0, b.length)) != -1) {
				fs.write(b, 0, len);
			}

			bs.close();
			fs.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
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

	public void setCatalogSeq(String catalogSeq) {
		this.catalogSeq = catalogSeq;
	}

	public String getCatalogSeq() {
		return catalogSeq;
	}

	public void setSelectUploadFlag(String selectUploadFlag) {
		this.selectUploadFlag = selectUploadFlag;
	}

	public void setUpdateWay(String updateWay) {
		this.updateWay = updateWay;
	}

	public void setPfpCatalogService(IPfpCatalogService pfpCatalogService) {
		this.pfpCatalogService = pfpCatalogService;
	}

	public List<PfpCatalogVO> getCatalogList() {
		return catalogList;
	}

	public void setJobURL(String jobURL) {
		this.jobURL = jobURL;
	}

	public void setAkbPfpServer(String akbPfpServer) {
		this.akbPfpServer = akbPfpServer;
	}

	public String getUploadContent() {
		return uploadContent;
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
