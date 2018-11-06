package com.pchome.akbpfp.struts2.action.catalog.uploadErrLogList;

import java.util.ArrayList;
import java.util.List;

import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.service.catalog.uploadErrLogList.IPfpCatalogUploadErrLogService;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogProdEcErrorVO;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogUploadLogVO;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;

public class PfpCatalogUploadErrLogAction extends BaseCookieAction {

	private IPfpCatalogUploadErrLogService pfpCatalogUploadErrLogService;
	private IPfpCatalogService pfpCatalogService;
	
	private List<PfpCatalogProdEcErrorVO> dataList = new ArrayList<PfpCatalogProdEcErrorVO>(); // 查詢結果
	private List<PfpCatalogVO> catalogList = new ArrayList<PfpCatalogVO>(); // 目錄下拉選單
	private String catalogUploadLogSeq; // 更新紀錄編號
	private int pageNo = 1;          // 初始化目前頁數
	private int pageCount = 0;       // 初始化共幾頁
	private long totalCount = 0;     // 初始化共幾筆
	
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
	 * 查詢目錄商品上傳錯誤紀錄
	 * @return
	 */
	public String queryCatalogProdUploadErrLog() {
		System.out.println("catalogUploadLogSeq:" + catalogUploadLogSeq);
		System.out.println("AZXC:PfpCatalogUploadErrLogAction");
		
		catalogDropDownMenu();
		
		PfpCatalogUploadLogVO vo = new PfpCatalogUploadLogVO();
		vo.setCatalogUploadLogSeq(catalogUploadLogSeq);
		vo.setPageNo(pageNo);
		dataList = pfpCatalogUploadErrLogService.getCatalogProdUploadErrList(vo);
		pageCount = vo.getPageCount();
		totalCount = vo.getTotalCount();
		
		System.out.println("LLLLOOOOKKKKK" + dataList);
//		PfpCatalogVO vo = new PfpCatalogVO();
//		vo.setQueryString(queryString);
//		vo.setPageNo(pageNo);
//		vo.setPageSize(pageSize);
//		vo.setPfpCustomerInfoId(super.getCustomer_info_id());
//		
//		dataList = pfpCatalogService.getPfpCatalogList(vo);
//		pageCount = vo.getPageCount();
//		totalCount = vo.getTotalCount();
		
		return SUCCESS;
	}

	public void setPfpCatalogUploadErrLogService(IPfpCatalogUploadErrLogService pfpCatalogUploadErrLogService) {
		this.pfpCatalogUploadErrLogService = pfpCatalogUploadErrLogService;
	}
	
	public void setCatalogList(List<PfpCatalogVO> catalogList) {
		this.catalogList = catalogList;
	}

	public void setCatalogUploadLogSeq(String catalogUploadLogSeq) {
		this.catalogUploadLogSeq = catalogUploadLogSeq;
	}

	public List<PfpCatalogProdEcErrorVO> getDataList() {
		return dataList;
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
}