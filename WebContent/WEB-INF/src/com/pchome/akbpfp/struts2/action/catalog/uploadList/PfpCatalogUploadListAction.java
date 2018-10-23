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
import org.json.JSONArray;
import org.json.JSONObject;

import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.service.catalog.uploadList.IPfpCatalogUploadListService;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogUploadListVO;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogUploadLogVO;
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
	
	private IPfpCatalogUploadListService pfpCatalogUploadListService;
	private IPfpCatalogService pfpCatalogService;
	protected final int bufferReaderKB = 8; // 讀取資料使用的buffer大小(預設8KB)
	
	private List<PfpCatalogVO> catalogList = new ArrayList<PfpCatalogVO>(); // 查詢結果
	private String catalogSeq; // 商品目錄ID
	private String selectUploadFlag; // 選擇上傳方式flag帶入相對畫面
	private String updateWay; // 更新方式(1.取代,2.更新)
	private String akbPfpServer;
	
	// 自動排程上傳
	private String jobURL; // 輸入的自動排程網址
	private String uploadContent;
	
	// 商店網址上傳
	private String pchomeStoreURL; // 輸入的商店網址
	
	// 手動上傳
	private String ecUrl; // 輸入的商品網址
	private String manualInputDataMap;
	private String catalogProdSeq;
	
	// api用
	private String catalog_seq;
	private String catalog_type;
	private String update_way;
	private String update_content;
	private String pfp_customer_info_id;
	private String catalog_prod_item;
	
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
			return "fileUpload";
		} else if ((StringUtils.isNotBlank(catalogUploadType)
				&& EnumPfpCatalog.CATALOG_UPLOAD_AUTOMATIC_SCHEDULING.getType().equals(catalogUploadType))
				|| (catalogUploadType.isEmpty()
						&& EnumPfpCatalog.CATALOG_UPLOAD_AUTOMATIC_SCHEDULING.getType().equals(selectUploadFlag))) {
			uploadContent = pfpCatalog.getCatalogUploadContent().trim();
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
			return "noUploadType";
		}
		return SUCCESS;
	}
	
	/**
	 * 取得上傳方式代碼
	 * @return
	 */
//	public String getCatalogUploadType() {
//		dataMap = new HashMap<String, Object>();
//		PfpCatalog pfpCatalog = pfpCatalogService.get(catalogSeq);
//		dataMap.put("catalogUploadType", pfpCatalog.getCatalogUploadType().trim());
//		return SUCCESS;
//	}
	
	/**
	 * 檢查檔案
	 * @return
	 * @throws Exception
	 */
	public String catalogUploadCheckFileData() throws Exception {
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
		//TODO 檔案上傳
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
		vo.setFileUploadPath(productFilePath + super.getCustomer_info_id() + "/" + formatter2.format(updateDatetime) + "_" + fileUploadFileName);
		File createFile = new File(vo.getFileUploadPath());
		FileUtils.copyFile(fileUpload, createFile);
		
		JSONObject catalogProdJsonData = pfpCatalogUploadListService.getCSVFileDataToJson(vo);
		catalogProdJsonData.put("catalogSeq", catalogSeq);
		catalogProdJsonData.put("catalogType", pfpCatalog.getCatalogType());
		catalogProdJsonData.put("updateWay", updateWay);
		catalogProdJsonData.put("catalogUploadType", "1");
		catalogProdJsonData.put("updateContent", fileUploadFileName);
		catalogProdJsonData.put("pfpCustomerInfoId", super.getCustomer_info_id());
		catalogProdJsonData.put("updateDatetime", formatter.format(updateDatetime));
		
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
		//TODO 自動排程上傳
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
			catalogProdJsonData.put("catalogSeq", catalogSeq);
			catalogProdJsonData.put("catalogType", pfpCatalog.getCatalogType());
			catalogProdJsonData.put("updateWay", updateWay);
			catalogProdJsonData.put("catalogUploadType", "2");
			catalogProdJsonData.put("updateContent", fileUploadFileName);
			catalogProdJsonData.put("pfpCustomerInfoId", super.getCustomer_info_id());
			catalogProdJsonData.put("updateDatetime", formatter.format(updateDatetime));
			
			dataMap = pfpCatalogUploadListService.processCatalogProdJsonData(catalogProdJsonData);
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
		
		// 更新目錄
		PfpCatalogVO vo = new PfpCatalogVO();
		vo.setCatalogSeq(catalogSeq);
		vo.setPfpCustomerInfoId(super.getCustomer_info_id());
		vo.setCatalogUploadType("3");
		vo.setUploadContent(pchomeStoreURL);
		pfpCatalogService.updatePfpCatalog(vo);
		
		//新增Log記錄
		PfpCatalogUploadLogVO pfpCatalogUploadLogVO = new PfpCatalogUploadLogVO();
		pfpCatalogUploadLogVO.setCatalogSeq(catalogSeq);
		pfpCatalogUploadLogVO.setUpdateWay(updateWay);
		pfpCatalogUploadLogVO.setUpdateContent(pchomeStoreURL);
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
		//TODO 手動上傳
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
		apiJsonData.put("catalogUploadType", "4");
		apiJsonData.put("updateContent", " ");
		apiJsonData.put("pfpCustomerInfoId", super.getCustomer_info_id());
		apiJsonData.put("catalogProdItem", catalogProdItemJSONArray);
		apiJsonData.put("updateDatetime", formatter.format(new Date()));
		
		dataMap = pfpCatalogUploadListService.processCatalogProdJsonData(apiJsonData);
		
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
		apiJsonData.put("catalogSeq", catalog_seq);
		apiJsonData.put("catalogType", catalog_type);
		apiJsonData.put("updateWay", update_way);
		apiJsonData.put("updateContent", update_content);
		apiJsonData.put("pfpCustomerInfoId", pfp_customer_info_id);
		apiJsonData.put("catalogProdItem", catalog_prod_item);
		apiJsonData.put("updateDatetime", formatter.format(new Date()));
		
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
	
}