package com.pchome.akbpfp.struts2.action.catalog.uploadList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.service.catalog.uploadList.IPfpCatalogUploadListService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.vo.catalog.PfpCatalogVO;
import com.pchome.akbpfp.db.vo.catalog.uploadList.PfpCatalogProdEcErrorVO;
import com.pchome.akbpfp.db.vo.catalog.uploadList.PfpCatalogUploadListVO;
import com.pchome.akbpfp.db.vo.catalog.uploadList.PfpCatalogUploadLogVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.akbpfp.struts2.ajax.ad.AdUtilAjax;
import com.pchome.enumerate.ad.EnumPfpCatalog;
import com.pchome.utils.CommonUtils;
import com.pchome.utils.HttpUtil;

public class PfpCatalogUploadListAction extends BaseCookieAction{
	
	private Map<String,Object> dataMap;
	private String catalogProdCsvFilePath;
	private String catalogProdCsvFileTempPath;
	private String catalogProdStoreURLlistPath;
	private File fileUpload;
	private String fileUploadFileName;
	
	private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMddHHmmss");
	private final SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy/MM/dd ahh:mm");
	
	private IPfpCatalogUploadListService pfpCatalogUploadListService;
	private IPfpCatalogService pfpCatalogService;
	private IPfpCustomerInfoService pfpCustomerInfoService;
	
	private String akbPfpServer;
	private String akbAdqServer;
	
	private List<PfpCatalogVO> catalogList = new ArrayList<PfpCatalogVO>(); // 查詢結果
	private String catalogSeq; // 商品目錄ID
	private String selectUploadFlag; // 選擇上傳方式flag帶入相對畫面
	private String updateWay; // 更新方式(1.取代,2.更新)
	private String updateDate; // 更新時間
	
	// 查詢錯誤記錄清單
	private List<PfpCatalogProdEcErrorVO> catalogProdUploadErrDataList = new ArrayList<PfpCatalogProdEcErrorVO>(); // 查詢結果
	private String catalogUploadLogSeq; // 更新紀錄編號
	private int pageNo = 1;          // 初始化目前頁數
	private int pageCount = 0;       // 初始化共幾頁
	private long totalCount = 0;     // 初始化共幾筆
	
	// 自動排程上傳
	private String jobURL; // 輸入的自動排程網址
	private String uploadContent;
	private String uploadFileName;
	
	// 商店網址上傳
	private String pchomeStoreURL; // 輸入的商店網址
	
	// 手動上傳
	private String ecUrl; // 輸入的商品網址
	private String manualInputDataMap;
	private String catalogProdSeq;
	
	/**
	 * 根據選擇上傳方式，導相對畫面
	 * @return
	 * @throws Exception
	 */
	public String selectUpload() throws Exception {
		catalogDropDownMenu();
		
		PfpCatalog pfpCatalog = pfpCatalogService.get(catalogSeq); // 取得商品目錄 table資料
		String catalogUploadType = pfpCatalog.getCatalogUploadType().trim();

		// 依據傳入的Flag帶到相對應的畫面，如果DB有資料，上傳方式為1，或DB沒資料但是有傳上傳方式flag為1，則導檔案上傳頁
		if ((StringUtils.isNotBlank(catalogUploadType)
				&& EnumPfpCatalog.CATALOG_UPLOAD_FILE_UPLOAD.getType().equals(catalogUploadType))
				|| (catalogUploadType.isEmpty()
						&& EnumPfpCatalog.CATALOG_UPLOAD_FILE_UPLOAD.getType().equals(selectUploadFlag))) {
			uploadContent = pfpCatalog.getCatalogUploadContent().trim();
			updateDate = formatter3.format(pfpCatalog.getUpdateDate());
			return "fileUpload";
		} else if ((StringUtils.isNotBlank(catalogUploadType)
				&& EnumPfpCatalog.CATALOG_UPLOAD_AUTOMATIC_SCHEDULING.getType().equals(catalogUploadType))
				|| (catalogUploadType.isEmpty()
						&& EnumPfpCatalog.CATALOG_UPLOAD_AUTOMATIC_SCHEDULING.getType().equals(selectUploadFlag))) {
			uploadContent = pfpCatalog.getCatalogUploadContent().trim();
			uploadFileName = CommonUtils.getDataFromUrl(uploadContent).get("fileName");
			return "automaticScheduling";
		} else if ((StringUtils.isNotBlank(catalogUploadType)
				&& EnumPfpCatalog.CATALOG_UPLOAD_STORE_URL.getType().equals(catalogUploadType))
				|| (catalogUploadType.isEmpty()
						&& EnumPfpCatalog.CATALOG_UPLOAD_STORE_URL.getType().equals(selectUploadFlag))) {
			uploadContent = pfpCatalog.getCatalogUploadContent().trim();
			return "storeURL";
		} else if ((StringUtils.isNotBlank(catalogUploadType)
				&& EnumPfpCatalog.CATALOG_UPLOAD_MANUAL_UPLOAD.getType().equals(catalogUploadType))
				|| (catalogUploadType.isEmpty()
						&& EnumPfpCatalog.CATALOG_UPLOAD_MANUAL_UPLOAD.getType().equals(selectUploadFlag))) {
			return "manualUpload";
		} else if (catalogUploadType.isEmpty()) {
			// 刪除目錄，避免有人已經選擇上傳方式，結果最後伺服器重啟或其他因素，檔案已經上傳完成，但table尚未寫入資料，將目錄資料刪除
			FileUtils.deleteQuietly(new File(catalogProdCsvFilePath + super.getCustomer_info_id() + "/" + pfpCatalog.getCatalogSeq()));
			return "noUploadType";
		}
		return SUCCESS;
	}
	
	/**
	 * 檢查檔案
	 * @return
	 * @throws Exception
	 */
	public String catalogUploadCheckFileData() throws Exception {
		dataMap = new HashMap<String, Object>();
		if (!"csv".equalsIgnoreCase(FilenameUtils.getExtension(fileUploadFileName))) {
			dataMap.put("status", "ERROR");
			dataMap.put("msg", "檔案格式錯誤，僅支援.CSV檔案!");
			return SUCCESS;
		}
		
		// 取得商品目錄 table資料
		PfpCatalog pfpCatalog = pfpCatalogService.get(catalogSeq);
		
		Date updateDatetime = new Date();
		PfpCatalogUploadListVO vo = new PfpCatalogUploadListVO();
		vo.setCatalogType(pfpCatalog.getCatalogType());
		
		// 檔案上傳，放在暫存路徑，避免在做檢查檔案時，排程剛好啟動正在匯入資料
		vo.setFileUploadPath(catalogProdCsvFileTempPath + super.getCustomer_info_id() + "/" + pfpCatalog.getCatalogSeq() + "/" + formatter2.format(updateDatetime) + "_" + fileUploadFileName);
		File createFile = new File(vo.getFileUploadPath());
		FileUtils.copyFile(fileUpload, createFile); // 建立檔案兼建立路徑上需要資料夾
		
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
		//TODO 檔案上傳
		Date updateDatetime = new Date();
		dataMap = new HashMap<String, Object>();
		
		if (!"csv".equalsIgnoreCase(FilenameUtils.getExtension(fileUploadFileName))) {
			dataMap.put("status", "ERROR");
			dataMap.put("msg", "上傳檔案錯誤!");
			return SUCCESS;
		}
		
		// 取得商品目錄 table資料
		PfpCatalog pfpCatalog = pfpCatalogService.get(catalogSeq);
		
		CommonUtils.createFolder(catalogProdCsvFilePath + super.getCustomer_info_id() + "/" + pfpCatalog.getCatalogSeq());
		
		// 檔案下載未完成先命名副檔名為tmp，已完成改為.csv
		String fileUploadTempPath = catalogProdCsvFilePath + super.getCustomer_info_id() + "/" + pfpCatalog.getCatalogSeq() + "/" + 
				formatter2.format(updateDatetime) + "_" + pfpCatalog.getCatalogSeq() + ".tmp";
		File createFile = new File(fileUploadTempPath);

		/* 
		 * 參考 https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/320329/
		 * FileChannels 速度最快，注意:此方式不會建立路徑上的資料夾
		 */
		FileChannel inputChannel = new FileInputStream(fileUpload).getChannel();
		FileChannel outputChannel = new FileOutputStream(createFile).getChannel();
		outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		inputChannel.close();
		outputChannel.close();
		
		String fileUploadCsvPath = catalogProdCsvFilePath + super.getCustomer_info_id() + "/" + pfpCatalog.getCatalogSeq() + "/" + 
				formatter2.format(updateDatetime) + "_" + pfpCatalog.getCatalogSeq() + ".csv";
		File tempfile = new File(fileUploadTempPath);
		File csvfile = new File(fileUploadCsvPath);
		tempfile.renameTo(csvfile);
		
		// 更新目錄
		pfpCatalog.setCatalogUploadType(EnumPfpCatalog.CATALOG_UPLOAD_FILE_UPLOAD.getType()); // 上傳方式
		pfpCatalog.setCatalogUploadContent(fileUploadFileName); // 上傳內容(檔名OR網址)
		pfpCatalog.setUploadStatus(EnumPfpCatalog.CATALOG_UPLOAD_STATUS_UPLOADING.getType()); // 目錄資料上傳狀態調整為上傳中
		pfpCatalog.setUpdateDate(updateDatetime); // 更新時間
		// 用BaseService.update，如果伺服器重啟斷線，資料將rollback
		pfpCatalogService.update(pfpCatalog);
		
		// 新增 pfp_catalog_upload_log "商品目錄更新紀錄"
		PfpCatalogUploadLogVO pfpCatalogUploadLogVO = new PfpCatalogUploadLogVO();
		pfpCatalogUploadLogVO.setCatalogSeq(catalogSeq);
		pfpCatalogUploadLogVO.setUpdateWay(updateWay);
		pfpCatalogUploadLogVO.setUpdateContent(fileUploadFileName);
		pfpCatalogUploadLogVO.setUpdateDatetime(updateDatetime);
		pfpCatalogUploadListService.savePfpCatalogUploadLog(pfpCatalogUploadLogVO);
		
		return SUCCESS;
	}

	/**
	 * 檢查輸入的自動排程網址
	 * @return
	 */
	public String ajaxCheckJobURL() {
		dataMap = new HashMap<String, Object>();

		try {
			// 先檢查網址
			Map<String, String> map = CommonUtils.getDataFromUrl(jobURL);
			if (StringUtils.isBlank(map.get("fileName").toString())
					&& StringUtils.isBlank(map.get("filenameExtension").toString())) {
				dataMap.put("status", "ERROR");
				return SUCCESS;
			}
			
			// 網址OK再做連線機制檢查
			HttpUtil.disableCertificateValidation();
			URL urlData = new URL(jobURL);
			// 增加User-Agent，避免被發現是機器人被阻擋掉
			HttpURLConnection urlConnection = (HttpURLConnection) urlData.openConnection();
			urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36");
			urlConnection.setRequestMethod("GET");
			if (urlConnection.getResponseCode() != HttpStatus.SC_OK) {
				dataMap.put("status", "ERROR");
				return SUCCESS;
			}
			
			dataMap.put("fileName", map.get("fileName").toString());
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("status", "ERROR");
			return SUCCESS;
		}
	}
	
	/**
	 * 自動排程上傳 
	 * @return
	 * @throws Exception
	 */
	public String catalogProdAutoJob() throws Exception {
		//TODO 自動排程上傳
		dataMap = new HashMap<String, Object>();
		Date updateDatetime = new Date();
		
		PfpCatalog pfpCatalog = pfpCatalogService.get(catalogSeq);
		
		String catalogPath = catalogProdCsvFilePath + super.getCustomer_info_id() + "/" + pfpCatalog.getCatalogSeq();
		CommonUtils.createFolder(catalogPath);
		
		// 將網址紀錄url.txt
		String urlTxtPath = catalogPath + "/url.txt";
		FileWriter fw = new FileWriter(urlTxtPath);
		fw.write(jobURL);
		fw.write("\r\n" + formatter2.format(updateDatetime));
		fw.flush();
		fw.close();
		
		// 更新目錄
		pfpCatalog.setCatalogUploadType(EnumPfpCatalog.CATALOG_UPLOAD_AUTOMATIC_SCHEDULING.getType()); // 上傳方式
		pfpCatalog.setCatalogUploadContent(jobURL); // 上傳內容(檔名OR網址)
		pfpCatalog.setUploadStatus(EnumPfpCatalog.CATALOG_UPLOAD_STATUS_UPLOADING.getType()); // 目錄資料上傳狀態調整為上傳中
		pfpCatalog.setUpdateDate(updateDatetime); // 更新時間
		// 用BaseService.update，如果伺服器重啟斷線，資料將rollback
		pfpCatalogService.update(pfpCatalog);

		// 新增 pfp_catalog_upload_log "商品目錄更新紀錄"
		PfpCatalogUploadLogVO pfpCatalogUploadLogVO = new PfpCatalogUploadLogVO();
		pfpCatalogUploadLogVO.setCatalogSeq(catalogSeq);
		pfpCatalogUploadLogVO.setUpdateWay(updateWay);
		pfpCatalogUploadLogVO.setUpdateContent(jobURL);
		pfpCatalogUploadLogVO.setUpdateDatetime(updateDatetime);
		pfpCatalogUploadLogVO.setIdPchome(super.getId_pchome());
		pfpCatalogUploadLogVO.setCustomerInfoId(super.getCustomer_info_id());
		pfpCatalogUploadLogVO.setUserId(super.getUser_id());
		pfpCatalogUploadLogVO.setRemoteAddr(request.getRemoteAddr());
		
		
		pfpCatalogUploadListService.savePfpCatalogUploadLog(pfpCatalogUploadLogVO);
		
		// 檢查帳戶餘額，顯示提示訊息
		PfpCustomerInfo pfpCustomerInfo = pfpCustomerInfoService.get(super.getCustomer_info_id());
		BigDecimal money = new BigDecimal(pfpCustomerInfo.getRemain());
		if (money.compareTo(BigDecimal.ZERO) == 0) {
			dataMap.put("msg", "目前帳戶無餘額，排程不會執行。");
		}
		
		return SUCCESS;
	}
	
	/**
	 * 檢查輸入的pchome賣場網址
	 * @return
	 * @throws Exception
	 */
	public String ajaxCheckPchomeStoreURL() throws Exception {
		dataMap = new HashMap<String, Object>();

		AdUtilAjax adUtilAjax = new AdUtilAjax();
		boolean checkUrlStatus = adUtilAjax.checkUrl(pchomeStoreURL, akbPfpServer);
		if (!checkUrlStatus) {
			dataMap.put("status", "ERROR");
		}
		
		return SUCCESS;
	}
	
	/**
	 * pchome賣場網址 新增更新
	 * @return
	 * @throws Exception
	 */
	public String catalogProdPchomeStoreURL() throws Exception {
		//TODO pchome賣場網址上傳
		dataMap = new HashMap<String, Object>();
		Date updateDatetime = new Date();
		
		// 建立每天凌晨1點排程產url.txt用到的資料夾
		CommonUtils.createFolder(catalogProdCsvFilePath + super.getCustomer_info_id() + "/" + catalogSeq);
		// 建立傳給董哥商店網址會用到的資料夾
		CommonUtils.createFolder(catalogProdStoreURLlistPath);

		// 如果文件存在，則追加內容；如果文件不存在，則創建文件
		File file = new File(catalogProdStoreURLlistPath + "urllist.txt");
		FileWriter fileWriter = new FileWriter(file, true); // true,進行追加寫。
		
		PrintWriter printWriter = new PrintWriter(fileWriter); 
		printWriter.println(catalogSeq + "," + pchomeStoreURL); 
		printWriter.flush();
		fileWriter.flush();
		printWriter.close();
		fileWriter.close();
		
		PfpCatalog pfpCatalog = pfpCatalogService.get(catalogSeq);
		pfpCatalog.setCatalogUploadType(EnumPfpCatalog.CATALOG_UPLOAD_STORE_URL.getType()); // 上傳方式
		pfpCatalog.setCatalogUploadContent(pchomeStoreURL); // 上傳內容(檔名OR網址)
		pfpCatalog.setUpdateDate(updateDatetime); // 更新時間
		// 用BaseService.update，如果伺服器重啟斷線，資料將rollback
		pfpCatalogService.update(pfpCatalog);
		
		//新增Log記錄
		PfpCatalogUploadLogVO pfpCatalogUploadLogVO = new PfpCatalogUploadLogVO();
		pfpCatalogUploadLogVO.setCatalogSeq(catalogSeq);
		pfpCatalogUploadLogVO.setUpdateWay(updateWay);
		pfpCatalogUploadLogVO.setUpdateContent(pchomeStoreURL);
		pfpCatalogUploadLogVO.setUpdateDatetime(updateDatetime);
		pfpCatalogUploadListService.savePfpCatalogUploadLog(pfpCatalogUploadLogVO);

		return SUCCESS;
	}
	
	/**
	 * 檢查手動上傳輸入資料
	 * 1.網址是否OK
	 * 2.商品編號是否重複
	 * @return
	 * @throws Exception
	 */
	public String ajaxCheckManualInputData() throws Exception {
		dataMap = new HashMap<String, Object>();
		
		// 檢查網址
		AdUtilAjax adUtilAjax = new AdUtilAjax();
		boolean checkUrlStatus = adUtilAjax.checkUrl(ecUrl, akbPfpServer);
		if (!checkUrlStatus) {
			dataMap.put("checkURLStatus", "ERROR");
		}
		
		// 檢查商品編號是否重複
		int count = pfpCatalogUploadListService.checkCatalogProdSeq(catalogSeq, catalogProdSeq);
		if (count > 0) {
			dataMap.put("checkCatalogProdSeqStatus", "ERROR");
		}
		
		return SUCCESS;
	}
	
	/**
	 * 手動上傳 新增
	 * @return
	 * @throws Exception
	 */
	public String catalogProdManualInput() throws Exception {
		//TODO 手動上傳 1126更新版
		// 將JSONArray內的字串資料轉成JSONObject
		JSONArray catalogProdManualInputItemJsonArray = new JSONArray(manualInputDataMap);
		JSONArray catalogProdItemJSONArray = new JSONArray();
		for (int i = 0; i < catalogProdManualInputItemJsonArray.length(); i++) {
			// 畫面上的欄位 調整成pfpCatalogUploadListService.processCatalogProdJsonData內要用的對應key
			JSONObject tempViewJSONObject = new JSONObject(catalogProdManualInputItemJsonArray.get(i).toString());
			JSONObject tempSetDBJSONObject = new JSONObject();
			tempSetDBJSONObject.put("id", tempViewJSONObject.get("catalogProdSeq"));
			tempSetDBJSONObject.put("ec_name", tempViewJSONObject.get("ecName"));
			tempSetDBJSONObject.put("ec_title", " ");
			tempSetDBJSONObject.put("ec_price", tempViewJSONObject.get("ecPrice"));
			tempSetDBJSONObject.put("ec_discount_price", tempViewJSONObject.get("ecDiscountPrice"));
			tempSetDBJSONObject.put("ec_stock_status", tempViewJSONObject.get("ecStockStatus"));
			tempSetDBJSONObject.put("ec_use_status", tempViewJSONObject.get("ecUseStatus"));
			tempSetDBJSONObject.put("ec_img_url", " ");
			tempSetDBJSONObject.put("ec_Img_Base64", tempViewJSONObject.get("ecImgBase64"));
			tempSetDBJSONObject.put("ec_url", tempViewJSONObject.get("ecUrl"));
			tempSetDBJSONObject.put("ec_category", tempViewJSONObject.get("ecCategory"));
			catalogProdItemJSONArray.put(tempSetDBJSONObject);
		}
		
		// 取得商品目錄 table資料
		PfpCatalog pfpCatalog = pfpCatalogService.get(catalogSeq);
				
		JSONObject apiJsonData = new JSONObject();
		apiJsonData.put("catalogSeq", catalogSeq);
		apiJsonData.put("catalogType", pfpCatalog.getCatalogType());
		apiJsonData.put("updateWay", " "); // 手動上傳沒有更新方式
		apiJsonData.put("catalogUploadType", EnumPfpCatalog.CATALOG_UPLOAD_MANUAL_UPLOAD.getType());
		apiJsonData.put("updateContent", " ");
		apiJsonData.put("pfpCustomerInfoId", super.getCustomer_info_id());
		apiJsonData.put("catalogProdItem", catalogProdItemJSONArray);

		HttpUtil.doPost(akbAdqServer + "catalogProdManualInputApi.html", apiJsonData);
		return SUCCESS;
	}
	
	/**
	 * 查詢目錄商品上傳錯誤紀錄
	 * @return
	 */
	public String queryCatalogProdUploadErrLog() {
		//TODO 錯誤紀錄
		catalogDropDownMenu();
		
		PfpCatalogProdEcErrorVO vo = new PfpCatalogProdEcErrorVO();
		vo.setPfpCustomerInfoId(super.getCustomer_info_id());
		vo.setCatalogUploadLogSeq(catalogUploadLogSeq);
		vo.setPageNo(pageNo);
		catalogProdUploadErrDataList = pfpCatalogUploadListService.getCatalogProdUploadErrList(vo);
		pageCount = vo.getPageCount();
		totalCount = vo.getTotalCount(); // 總共多少筆上傳失敗商品

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
	}
	
	public void setCatalogProdCsvFilePath(String catalogProdCsvFilePath) {
		this.catalogProdCsvFilePath = catalogProdCsvFilePath;
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

	public void setPchomeStoreURL(String pchomeStoreURL) {
		this.pchomeStoreURL = pchomeStoreURL;
	}

	public void setEcUrl(String ecUrl) {
		this.ecUrl = ecUrl;
	}

	public void setManualInputDataMap(String manualInputDataMap) {
		this.manualInputDataMap = manualInputDataMap;
	}

	public void setCatalogProdSeq(String catalogProdSeq) {
		this.catalogProdSeq = catalogProdSeq;
	}

	public String getUpdateDate() {
		return updateDate;
	}
	
	public void setCatalogUploadLogSeq(String catalogUploadLogSeq) {
		this.catalogUploadLogSeq = catalogUploadLogSeq;
	}
	
	public String getCatalogUploadLogSeq() {
		return catalogUploadLogSeq;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageCount() {
		return pageCount;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public List<PfpCatalogProdEcErrorVO> getCatalogProdUploadErrDataList() {
		return catalogProdUploadErrDataList;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setCatalogProdCsvFileTempPath(String catalogProdCsvFileTempPath) {
		this.catalogProdCsvFileTempPath = catalogProdCsvFileTempPath;
	}

	public void setPfpCustomerInfoService(IPfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public void setAkbAdqServer(String akbAdqServer) {
		this.akbAdqServer = akbAdqServer;
	}

	public void setCatalogProdStoreURLlistPath(String catalogProdStoreURLlistPath) {
		this.catalogProdStoreURLlistPath = catalogProdStoreURLlistPath;
	}
	
}