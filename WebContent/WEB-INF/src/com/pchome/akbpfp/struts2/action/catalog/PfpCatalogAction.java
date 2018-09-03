package com.pchome.akbpfp.struts2.action.catalog;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;


public class PfpCatalogAction extends BaseCookieAction{
	
	private IPfpCatalogService pfpCatalogService;
	
	private String queryString = ""; // 預設為空
	private int pageNo = 1;          // 初始化目前頁數
	private int pageSize = 10;       // 初始化每頁幾筆
	private int pageCount = 0;       // 初始化共幾頁
	private long totalCount = 0;     // 初始化共幾筆
	private List<PfpCatalogVO> dataList = new ArrayList<PfpCatalogVO>(); // 查詢結果

	private String catalogName;
	private String catalogType;
	
	private String deleteCatalogSeq;
	
	// 下載相關
	private InputStream downloadFileStream; // input stream
	private String downloadFileName; // 下載檔名
	
	/**
	 * 查詢商品目錄清單
	 * @return
	 */
	public String queryPfpCatalogList() {
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
	 * 新增商品目錄
	 * @return
	 * @throws Exception 
	 */
	public String savePfpCatalog() throws Exception {
		PfpCatalogVO vo = new PfpCatalogVO();
		vo.setCatalogName(catalogName);
		vo.setCatalogType(catalogType);
		vo.setPfpCustomerInfoId(super.getCustomer_info_id());
		dataList = pfpCatalogService.savePfpCatalog(vo);
		return SUCCESS;
	}
	
	/**
	 * 刪除目錄
	 * @return
	 */
	public String deletePfpCatalog() {
		PfpCatalogVO vo = new PfpCatalogVO();
		vo.setCatalogSeq(deleteCatalogSeq);
		dataList = pfpCatalogService.deletePfpCatalog(vo);
		
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
//		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > -1) {
//			downloadFileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
//		} else {
//			downloadFileName = URLEncoder.encode(fileName, "UTF-8");
//		}
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
	
	/**
	 * 目錄範例檔案下載
	 * @return
	 * @throws Exception 
	 */
//	public String catalogSampleFileDownload() throws Exception{
//		String fileName = "一般購物.csv";
////		downloadFileName = fileName;
//		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > -1) {
//			downloadFileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
//		} else {
//			downloadFileName = URLEncoder.encode(fileName, "UTF-8");			
//		}
//		
//		return SUCCESS;
//	}
	
	

	/**
	 * 下載檔案的檔案位置設定 
	 * 參考https://ifun01.com/OD48FKT.html
	 * @return
	 * @throws FileNotFoundException
	 */
//	public InputStream getDownloadFileStream() throws FileNotFoundException {
////		FileInputStream fis = new FileInputStream("F;/upload/" + filename);
//		FileInputStream fis = new FileInputStream("D:/Users/twkuo/Desktop/需求/商品廣告/欄位名稱整理 0809_假的都有資料的檔案.csv"); 
//        return fis;
////		return downloadFileStream;
//	}
	
	public void setPfpCatalogService(IPfpCatalogService pfpCatalogService) {
		this.pfpCatalogService = pfpCatalogService;
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

	public void setCatalogType(String catalogType) {
		this.catalogType = catalogType;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}
	
}
