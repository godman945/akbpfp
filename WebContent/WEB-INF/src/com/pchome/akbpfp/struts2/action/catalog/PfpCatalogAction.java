package com.pchome.akbpfp.struts2.action.catalog;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;

import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.pojo.PfpCatalogSetup;
import com.pchome.akbpfp.db.service.ad.IPfpAdService;
import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.service.catalog.prod.IPfpCatalogSetupService;
import com.pchome.akbpfp.db.service.catalog.prodGroup.IPfpCatalogGroupItemService;
import com.pchome.akbpfp.db.service.catalog.prodGroup.IPfpCatalogGroupService;
import com.pchome.akbpfp.db.service.catalog.uploadList.IPfpCatalogUploadListService;
import com.pchome.akbpfp.db.service.sequence.ISequenceService;
import com.pchome.akbpfp.db.vo.catalog.PfpCatalogVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.sequence.EnumSequenceTableName;
import com.pchome.enumerate.utils.EnumStatus;

public class PfpCatalogAction extends BaseCookieAction{
	
	private ISequenceService sequenceService;
	private IPfpCatalogService pfpCatalogService;
	private IPfpCatalogSetupService pfpCatalogSetupService;
	private IPfpCatalogGroupService pfpCatalogGroupService;
	private IPfpCatalogUploadListService pfpCatalogUploadListService;
	private IPfpCatalogGroupItemService pfpCatalogGroupItemService;
	private IPfpAdService pfpAdService;
	
	
	private String queryString = ""; // 預設為空
	private int pageNo = 1;          // 初始化目前頁數
	private int pageSize = 10;       // 初始化每頁幾筆
	private int pageCount = 0;       // 初始化共幾頁
	private long totalCount = 0;     // 初始化共幾筆
	private List<PfpCatalogVO> dataList = new ArrayList<PfpCatalogVO>(); // 查詢結果

	private String catalogSeq;
	private String catalogName; // 商品目錄名稱
	private boolean showPromptMessage;
	
	private String deleteCatalogSeq;
	private String uploadingCatalogSeqList; // 目前畫面顯示上傳中的目錄清單
	private Map<String,Object> dataMap;
	
	// 下載相關
	private InputStream downloadFileStream; // input stream
	private String downloadFileName; // 下載檔名
	
	/**
	 * 一進入檢查是否有目錄，有目錄無目錄各自導不同畫面
	 * @return
	 */
	public String initPfpCatalogList() {
		PfpCatalogVO vo = new PfpCatalogVO();
		vo.setPfpCustomerInfoId(super.getCustomer_info_id());
		
		dataList = pfpCatalogService.getPfpCatalogList(vo);
		pageCount = vo.getPageCount();
		totalCount = vo.getTotalCount();
		
		if (totalCount == 0) {
			return "noData";
		} else {
			showPromptMessage = pfpCatalogService.checkCatalogAndCatalogLogoIsShowMessage(super.getCustomer_info_id());
			return SUCCESS;
		}
	}
	
	/**
	 * 查詢商品目錄清單
	 * @return
	 */
	public String ajaxQueryPfpCatalogList() {
		PfpCatalogVO vo = new PfpCatalogVO();
		vo.setQueryString(queryString);
		vo.setPageNo(pageNo);
		vo.setPageSize(pageSize);
		vo.setPfpCustomerInfoId(super.getCustomer_info_id());
		
		dataList = pfpCatalogService.getPfpCatalogList(vo);
		pageCount = vo.getPageCount();
		totalCount = vo.getTotalCount();
		
		return SUCCESS;
	}
	
	/**
	 * 點選新增按鈕後，到新增頁時執行部分
	 * @return
	 * @throws Exception
	 */
	public String addPfpCatalog() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * 新增商品目錄
	 * @return
	 * @throws Exception 
	 */
	public String savePfpCatalog() throws Exception {
		dataMap = new HashMap<String, Object>();
		
		PfpCatalogVO vo = new PfpCatalogVO();
		vo.setCatalogName(catalogName);
		vo.setCatalogType("1"); // 目前僅提供一般購物，故先寫固定值
		vo.setPfpCustomerInfoId(super.getCustomer_info_id());
		pfpCatalogService.savePfpCatalog(vo);
		
		// 新增 pfp_catalog_setup "商品目錄設定"
		PfpCatalog pfpCatalog = pfpCatalogService.get(vo.getCatalogSeq());

		Date date = new Date();
		PfpCatalogSetup pfpCatalogSetup = new PfpCatalogSetup();
		pfpCatalogSetup.setPfpCatalog(pfpCatalog);
		pfpCatalogSetup.setCatalogSetupKey("img_proportiona");
		pfpCatalogSetup.setCatalogSetupValue("crop");
		pfpCatalogSetup.setUpdateDate(date);
		pfpCatalogSetup.setCreateDate(date);
		pfpCatalogSetupService.saveOrUpdate(pfpCatalogSetup);
		
		// 新增 pfp_catalog_group "商品目錄群組"
		String catalogGroupSeq = sequenceService.getSerialNumberByLength20(EnumSequenceTableName.PFP_CATALOG_GROUP);
		PfpCatalogGroup pfpCatalogGroup = new PfpCatalogGroup();
		pfpCatalogGroup.setCatalogGroupSeq(catalogGroupSeq);
		pfpCatalogGroup.setPfpCatalog(pfpCatalog);
		pfpCatalogGroup.setCatalogGroupName("全部商品");
		pfpCatalogGroup.setCatalogGroupDeleteStatus("0");
		pfpCatalogGroup.setUpdateDate(date);
		pfpCatalogGroup.setCreateDate(date);
		pfpCatalogGroupService.saveOrUpdateWithCommit(pfpCatalogGroup);
		
		catalogSeq = vo.getCatalogSeq();
		return SUCCESS;
	}
	
	/**
	 * 刪除目錄
	 * @return
	 * @throws Exception 
	 */
	public String ajaxDeletePfpCatalog() throws Exception {
		// 更新目錄狀態為刪除
		PfpCatalog pfpCatalog = pfpCatalogService.get(deleteCatalogSeq);
		if (!pfpCatalog.getPfpCustomerInfoId().equals(super.getCustomer_info_id())) {
			return SUCCESS;
		}
		
		// 更新商品目錄群組狀態為刪除
		Set<PfpCatalogGroup> group = pfpCatalog.getPfpCatalogGroups();
		for (PfpCatalogGroup pfpCatalogGroup : group) {
			// "全部商品"的目錄群組狀態不更新
			if (pfpCatalogGroup.getCatalogGroupName().equals("全部商品")) {
				continue;
			} else {
				Set<PfpCatalogGroupItem> pfpCatalogGroupItemSet = pfpCatalogGroup.getPfpCatalogGroupItems();
				for (PfpCatalogGroupItem pfpCatalogGroupItem : pfpCatalogGroupItemSet) {
					// "商品目錄群組明細"，依據pfp_catalog_group刪除明細
					pfpCatalogGroupItemService.delete(pfpCatalogGroupItem);
				}
				pfpCatalogGroup.setCatalogGroupDeleteStatus("1");
				pfpCatalogGroupService.saveOrUpdate(pfpCatalogGroup);
			}
		}
		
		// 更改目錄刪除狀態
		pfpCatalog.setCatalogDeleteStatus("1");
		
		// 更新廣告狀態為暫停
		pfpAdService.updateAdStatusByCatalogSeq(deleteCatalogSeq, String.valueOf(EnumStatus.Pause.getStatusId()), super.getCustomer_info_id());
		
		PfpCatalogVO vo = new PfpCatalogVO();
		vo.setCatalogSeq(deleteCatalogSeq);
		vo.setPfpCustomerInfoId(super.getCustomer_info_id());
		pfpCatalogUploadListService.deletePfpCatalogUploadErrLog(vo);
		pfpCatalogUploadListService.deletePfpCatalogProdEcError(vo);
		pfpCatalogUploadListService.deletePfpCatalogUploadLog(vo);
		pfpCatalogUploadListService.deletePfpCatalogSetup(vo);
		pfpCatalogUploadListService.deleteCatalogProdImgFolderAndData(vo);
		pfpCatalogUploadListService.deleteCatalogProdCSVFolderAndData(vo);
		return SUCCESS;
	}
	
	/**
	 * 檢查目錄資料上傳狀態
	 * @return
	 * @throws JSONException 
	 */
	public String ajaxCheckCatalogUploadingStatus() throws JSONException {
		dataMap = new HashMap<String, Object>();
		
		List<Map<String, String>> catalogUploadingStatusList = pfpCatalogService.getCatalogUploadingStatus(uploadingCatalogSeqList);
		if (catalogUploadingStatusList.size() > 0) {
			dataMap.put("status", "SUCCESS");
			dataMap.put("dataMap", catalogUploadingStatusList);
		} else {
			dataMap.put("status", "ERROR");
		}
		
		return SUCCESS;
	}
	
	/**
	 * 目錄範例檔案下載
	 * 參考 https://matthung0807.blogspot.com/2017/09/struts-2.html
	 * @return
	 * @throws Exception 
	 */
	public String catalogSampleFileDownload() throws Exception {
		String fileName = "shoppingSample.csv";
		downloadFileName = fileName;
		
		// 範例檔案放的位置
		String serverName = super.request.getServerName();
		String filePath = "";
		if (serverName.indexOf("showstg.pchome.com.tw") > -1 || serverName.indexOf("show.pchome.com.tw") > -1) {
			filePath = "/home/webuser/akb/git.project/akbpfp.master/WebContent/html/file/" + fileName;
		} else {
			// 本機路徑
			filePath = "D:/workspace/akbpfp_twkuo/WebContent/html/file/" + fileName;
		}
		downloadFileStream = new FileInputStream(new File(filePath));
		
		return SUCCESS;
	}
	
	public InputStream getDownloadFileStream() {
		return downloadFileStream;
	}

	public void setDownloadFileStream(InputStream downloadFileStream) {
		this.downloadFileStream = downloadFileStream;
	}
	
	public void setPfpCatalogService(IPfpCatalogService pfpCatalogService) {
		this.pfpCatalogService = pfpCatalogService;
	}

	public void setPfpCatalogUploadListService(IPfpCatalogUploadListService pfpCatalogUploadListService) {
		this.pfpCatalogUploadListService = pfpCatalogUploadListService;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public List<PfpCatalogVO> getDataList() {
		return dataList;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setDeleteCatalogSeq(String deleteCatalogSeq) {
		this.deleteCatalogSeq = deleteCatalogSeq;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public String getCatalogSeq() {
		return catalogSeq;
	}

	public void setPfpCatalogSetupService(IPfpCatalogSetupService pfpCatalogSetupService) {
		this.pfpCatalogSetupService = pfpCatalogSetupService;
	}

	public void setPfpCatalogGroupService(IPfpCatalogGroupService pfpCatalogGroupService) {
		this.pfpCatalogGroupService = pfpCatalogGroupService;
	}

	public void setSequenceService(ISequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public IPfpCatalogGroupItemService getPfpCatalogGroupItemService() {
		return pfpCatalogGroupItemService;
	}

	public void setPfpCatalogGroupItemService(IPfpCatalogGroupItemService pfpCatalogGroupItemService) {
		this.pfpCatalogGroupItemService = pfpCatalogGroupItemService;
	}

	public IPfpAdService getPfpAdService() {
		return pfpAdService;
	}

	public void setPfpAdService(IPfpAdService pfpAdService) {
		this.pfpAdService = pfpAdService;
	}

	public void setUploadingCatalogSeqList(String uploadingCatalogSeqList) {
		this.uploadingCatalogSeqList = uploadingCatalogSeqList;
	}

	public boolean isShowPromptMessage() {
		return showPromptMessage;
	}

}