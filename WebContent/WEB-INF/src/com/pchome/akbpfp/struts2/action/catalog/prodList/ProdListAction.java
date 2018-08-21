package com.pchome.akbpfp.struts2.action.catalog.prodList;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.catalog.prodList.factory.AProdList;
import com.pchome.akbpfp.catalog.prodList.factory.ProdListFactory;
import com.pchome.akbpfp.db.service.catalog.TMP.IPfpCatalogService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.catalog.prodList.EnumProdListFactory;

public class ProdListAction extends BaseCookieAction{
	
	private static final long serialVersionUID = 1L;

	private String catalogSeq;
	private String userProdStatus;
	private int page; 
	private int pageSize; 
	private String catalogProdCount;
	private IPfpCatalogService pfpCatalogService;
	private ProdListFactory prodListFactory;
	private List<Object> prodListObjs; 
	//	private SequenceService sequenceService;
	
	
	
	
	
	public String getProdList() throws Exception{		
		
		log.info(">>> catalogSeq: " + catalogSeq);
		
		System.out.println("test  : "+catalogSeq);
		

		//商品組合ID 的目錄型態
		String catalogType = pfpCatalogService.getCatalogType(catalogSeq);
		log.info(">>> catalogType: " + catalogType);
		if (StringUtils.isBlank(catalogType)) {
			String str = "目錄型態不正確";
			
//			returnJson = new ByteArrayInputStream(
//					getReturnJsonObj("error", EnumProdGroupList.E001.getStatus()).toString().getBytes());
			return SUCCESS;
		}
		
		EnumProdListFactory enumProdListFactory = EnumProdListFactory.getCatalogName(catalogType);
		log.info(">>> enumProdListFactory: " + enumProdListFactory);
		if (enumProdListFactory == null) {
			String str = "目錄型態不正確";
//			returnJson = new ByteArrayInputStream(
//					getReturnJsonObj("error", EnumProdGroupList.E002.getStatus()).toString().getBytes());
			return SUCCESS;
		}
		
		
		AProdList aProdList = prodListFactory.getAProdListObj(enumProdListFactory.getCatalogName());
		log.info(">>> aProdList: "+aProdList);
		if (aProdList == null){
			String str = "此商品組合ID的目錄型態不正確";
//			returnJson = new ByteArrayInputStream(getReturnJsonObj("error",EnumProdGroupList.E002.getStatus()).toString().getBytes());
			return SUCCESS;
		}
//		

		String pfpCustomerInfoId = "AC2014102900001"; //super.getCustomer_info_id();
		prodListObjs = aProdList.getProdList(catalogSeq, userProdStatus, pfpCustomerInfoId,page,pageSize);
		
		catalogProdCount = aProdList.getProdListCount(catalogSeq, userProdStatus);
		
		
		return SUCCESS;
	}


	
	

	public void setCatalogSeq(String catalogSeq) {
		this.catalogSeq = catalogSeq;
	}

	public String getUserProdStatus() {
		return userProdStatus;
	}

	public void setUserProdStatus(String userProdStatus) {
		this.userProdStatus = userProdStatus;
	}

	public void setPfpCatalogService(IPfpCatalogService pfpCatalogService) {
		this.pfpCatalogService = pfpCatalogService;
	}

	public void setProdListFactory(ProdListFactory prodListFactory) {
		this.prodListFactory = prodListFactory;
	}


	public void setPage(int page) {
		this.page = page;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPage() {
		return page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public String getCatalogProdCount() {
		return catalogProdCount;
	}

	public List<Object> getProdListObjs() {
		return prodListObjs;
	}
	
	
	
}
