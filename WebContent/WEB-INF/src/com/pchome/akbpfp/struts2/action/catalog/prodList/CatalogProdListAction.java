package com.pchome.akbpfp.struts2.action.catalog.prodList;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.catalog.prodList.factory.AProdList;
import com.pchome.akbpfp.catalog.prodList.factory.ProdListFactory;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.vo.catalog.prodList.ProdListConditionVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.catalog.prodList.EnumProdListFactory;

public class CatalogProdListAction extends BaseCookieAction{
	
	private static final long serialVersionUID = 1L;

	private int currentPage = 1;      //第幾頁(初始預設第1頁)
	private int pageSizeSelected = 10; //每頁筆數(初始預設每頁10筆)
	private int pageCount = 0; //總頁數
	private int totalCount = 0; //資料總筆數
	private String catalogSeq;
	private String prodStatus;
//	private String catalogProdCount;
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
		
		customerInfoTitle = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()).getCustomerInfoTitle();//AC2013071700001
		
		
		return SUCCESS;
	}
	//<!--bessieTmp-暫時寫的之後合併拿掉 -->
	
	
	
	

	
	
	
	public String queryProdListByCardStyle(){		
		try{
			log.info(">>> catalogSeq: " + catalogSeq);
			log.info(">>> prodStatus: " + prodStatus);
			log.info(">>> currentPage: " + currentPage);
			log.info(">>> pageSizeSelected: " + pageSizeSelected);
			
			System.out.println("test  : "+catalogSeq);
			
			pfpCatalogList = pfpCatalogService.getPfpCatalogList(super.getCustomer_info_id());//super.getCustomer_info_id()
			
			customerInfoTitle = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()).getCustomerInfoTitle();//AC2013071700001
			
			
			
			
			
	
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
	
			String pfpCustomerInfoId = super.getCustomer_info_id(); //super.getCustomer_info_id();
			
			ProdListConditionVO prodListConditionVO =  new ProdListConditionVO();
			prodListConditionVO.setCatalogSeq(catalogSeq);
			prodListConditionVO.setProdStatus(prodStatus);
			prodListConditionVO.setPfpCustomerInfoId(pfpCustomerInfoId);
			prodListConditionVO.setPage(currentPage);
			prodListConditionVO.setPageSize(pageSizeSelected);
			
			prodList = aProdList.getProdList(prodListConditionVO);
			if (prodList.size() <= 0){
				returnMsg = "沒有商品清單資料";
				return ERROR;
			}
			
			//商品清單資料總筆數
			totalCount =  Integer.valueOf(aProdList.getProdListCount(prodListConditionVO));
			
			//總頁數
			pageCount = (int)Math.ceil((float)totalCount / pageSizeSelected);
			
//			//依據商品目錄型態回傳各別ftl(一般購物類、訂房住宿類、交通航班類、房產租售類)
			returnFtlName = enumProdListFactory.getCatalogName();
			
			
			
			
			
		} catch (Exception e) {
			returnMsg = "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。";
			log.error("error:" + e);
			return ERROR;
		}
		
		return returnFtlName;
	}


	
	

	public void setCatalogSeq(String catalogSeq) {
		this.catalogSeq = catalogSeq;
	}

	public String getCatalogSeq() {
		return catalogSeq;
	}

	public String getProdStatus() {
		return prodStatus;
	}

	public void setProdStatus(String prodStatus) {
		this.prodStatus = prodStatus;
	}

	public void setPfpCatalogService(IPfpCatalogService pfpCatalogService) {
		this.pfpCatalogService = pfpCatalogService;
	}

	public void setProdListFactory(ProdListFactory prodListFactory) {
		this.prodListFactory = prodListFactory;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getPageSizeSelected() {
		return pageSizeSelected;
	}

	public void setPageSizeSelected(int pageSizeSelected) {
		this.pageSizeSelected = pageSizeSelected;
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

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	
	
}
