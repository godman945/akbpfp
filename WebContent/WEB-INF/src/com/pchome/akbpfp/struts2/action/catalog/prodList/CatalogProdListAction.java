package com.pchome.akbpfp.struts2.action.catalog.prodList;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.catalog.prodList.factory.AProdList;
import com.pchome.akbpfp.catalog.prodList.factory.ProdListFactory;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.service.catalog.TMP.IPfpCatalogService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.catalog.prodList.EnumProdListFactory;

public class CatalogProdListAction extends BaseCookieAction{
	
	private static final long serialVersionUID = 1L;

	private String catalogSeq;
	private String userProdStatus;
	private int page; 
	private int pageSize; 
	private String catalogProdCount;
	private IPfpCatalogService pfpCatalogService;
	private IPfpCustomerInfoService pfpCustomerInfoService;
	private ProdListFactory prodListFactory;
	private List<Object> prodList; 
	private List<PfpCatalog> pfpCatalogList;
	private String customerInfoTitle;
	private String returnFtlName;
	private String returnMsg;
	//	private SequenceService sequenceService;
	
	
	
	//<!--bessieTmp-暫時寫的之後合併拿掉 -->
	public String execute() throws Exception{
		
		
		
		
		return SUCCESS;
	}
	//<!--bessieTmp-暫時寫的之後合併拿掉 -->
	
	
	
	

	
	
	
	public String queryProdListByCardStyle(){		
		try{
			log.info(">>> catalogSeq: " + catalogSeq);
			log.info(">>> userProdStatus: " + userProdStatus);
			log.info(">>> page: " + page);
			log.info(">>> pageSize: " + pageSize);
			
			System.out.println("test  : "+catalogSeq);
			
			pfpCatalogList = pfpCatalogService.getPfpCatalogList("AC2013071700001");//super.getCustomer_info_id()
			
			customerInfoTitle = pfpCustomerInfoService.findCustomerInfo("AC2013071700001").getCustomerInfoTitle();
			
			
			
			
			
	
			//商品組合ID 的目錄型態
			String catalogType = pfpCatalogService.getCatalogType(catalogSeq);
			log.info(">>> catalogType: " + catalogType);
			if (StringUtils.isBlank(catalogType)) {
				returnMsg = "目錄型態不正確";
	//			returnJson = new ByteArrayInputStream(
	//					getReturnJsonObj("error", EnumProdGroupList.E001.getStatus()).toString().getBytes());
				return ERROR;
			}
			
			EnumProdListFactory enumProdListFactory = EnumProdListFactory.getCatalogName(catalogType);
			log.info(">>> enumProdListFactory: " + enumProdListFactory);
			if (enumProdListFactory == null) {
				returnMsg = "目錄型態不正確";
				return ERROR;
			}
			
			
			AProdList aProdList = prodListFactory.getAProdListObj(enumProdListFactory.getCatalogName());
			log.info(">>> aProdList: "+aProdList);
			if (aProdList == null){
				returnMsg = "此商品組合ID的目錄型態不正確";
				return ERROR;
			}
	//		
	
			String pfpCustomerInfoId = "AC2013071700001"; //super.getCustomer_info_id();
			prodList = aProdList.getProdList(catalogSeq, userProdStatus, pfpCustomerInfoId,page,pageSize);
			if (prodList.size() <= 0){
				returnMsg = "沒有商品清單資料";
				return ERROR;
			}
			
			catalogProdCount = aProdList.getProdListCount(catalogSeq, userProdStatus);
			
//			//依據商品目錄型態回傳各別ftl(一般購物類、訂房住宿類、交通航班類、房產租售類)
			returnFtlName = enumProdListFactory.getCatalogName();
			
		} catch (Exception e) {
//			dataMap.put("status", "ERROR");
//			dataMap.put("msg", "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			log.error("error:" + e);
		}
		
		return returnFtlName;
	}


	
	

	public void setCatalogSeq(String catalogSeq) {
		this.catalogSeq = catalogSeq;
	}

	public String getCatalogSeq() {
		return catalogSeq;
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

	public List<Object> getProdList() {
		return prodList;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public IPfpCustomerInfoService getPfpCustomerInfoService() {
		return pfpCustomerInfoService;
	}

	public void setPfpCustomerInfoService(IPfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public List<PfpCatalog> getPfpCatalogList() {
		return pfpCatalogList;
	}

	public String getCustomerInfoTitle() {
		return customerInfoTitle;
	}
	
	
	
	
	
}
