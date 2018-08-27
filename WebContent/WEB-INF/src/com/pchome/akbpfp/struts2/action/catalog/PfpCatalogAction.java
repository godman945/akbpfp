package com.pchome.akbpfp.struts2.action.catalog;


import java.util.ArrayList;
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
	
}
