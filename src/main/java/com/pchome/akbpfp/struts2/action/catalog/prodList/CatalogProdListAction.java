package com.pchome.akbpfp.struts2.action.catalog.prodList;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.pchome.akbpfp.catalog.prodList.factory.AProdList;
import com.pchome.akbpfp.catalog.prodList.factory.ProdListFactory;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.pojo.PfpCatalogSetup;
import com.pchome.akbpfp.db.service.accesslog.AdmAccesslogService;
import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.service.catalog.prod.IPfpCatalogSetupService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.vo.catalog.prodList.ProdListConditionVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.catalog.prodList.EnumProdListFactory;
import com.pchome.enumerate.catalogprod.EnumProdCropType;
import com.pchome.rmi.accesslog.EnumAccesslogAction;

public class CatalogProdListAction extends BaseCookieAction{
	
	private static final long serialVersionUID = 1L;

	private int currentPage = 1;      //第幾頁(初始預設第1頁)
	private int pageSizeSelected = 10; //每頁筆數(初始預設每頁10筆)
	private int pageCount = 0; //總頁數
	private int totalCount = 0; //資料總筆數
	private String catalogSeq;
	private String prodStatus;
	private String prodName;
	private String prodStatusType;
	private IPfpCatalogService pfpCatalogService;
	private IPfpCustomerInfoService pfpCustomerInfoService;
	private ProdListFactory prodListFactory;
	private List<Object> prodList; 
	private List<PfpCatalog> pfpCatalogList;
	private String customerInfoTitle;
	private String returnFtlName;
	private String returnMsg;
	private AdmAccesslogService accesslogService;
	//設定內容值 0:crop,1:nocrop
	private String cropType;
	//ajax回傳內容
	private String result;
	private IPfpCatalogSetupService pfpCatalogSetupService;
	
	
	public String queryProdListByCardStyle(){		
		try{
			log.info(">>> catalogSeq: " + catalogSeq);
			log.info(">>> currentPage: " + currentPage);
			log.info(">>> pageSizeSelected: " + pageSizeSelected);
			log.info(">>> prodStatus: " + prodStatus);
			log.info(">>> prodName: " + prodName);
			
			
			//全部商品 or 已啟用 or 已封存類型
			if (StringUtils.isBlank(prodStatus)){
				prodStatusType = "all";
			}else if(StringUtils.equals("1", prodStatus)){
				prodStatusType = "enable";
			}else if(StringUtils.equals("0", prodStatus)){
				prodStatusType = "sealed";
			}
			log.info(">>> prodStatusType: " + prodStatusType);
			
			pfpCatalogList = pfpCatalogService.getPfpCatalogList(super.getCustomer_info_id());
			customerInfoTitle = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()).getCustomerInfoTitle();
	
			//商品組合ID 的目錄型態
			String catalogType = pfpCatalogService.getCatalogType(catalogSeq);
			log.info(">>> catalogType: " + catalogType);
			if (StringUtils.isBlank(catalogType)) {
				returnMsg = "目錄型態不正確";
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
	
			String pfpCustomerInfoId = super.getCustomer_info_id();
			
			ProdListConditionVO prodListConditionVO =  new ProdListConditionVO();
			prodListConditionVO.setCatalogSeq(catalogSeq);
			prodListConditionVO.setPage(currentPage);
			prodListConditionVO.setPageSize(pageSizeSelected);
			prodListConditionVO.setProdStatus(prodStatus);
			prodListConditionVO.setProdName(prodName);
			prodListConditionVO.setPfpCustomerInfoId(pfpCustomerInfoId);
			
			prodList = aProdList.getProdList(prodListConditionVO);
			if (prodList.size() <= 0){
				returnMsg = "沒有商品清單資料";
				return ERROR;
			}
			
			//商品清單資料總筆數
			totalCount =  Integer.valueOf(aProdList.getProdListCount(prodListConditionVO));
			
			//總頁數
			pageCount = (int)Math.ceil((float)totalCount / pageSizeSelected);
			
			//依據商品目錄型態回傳各別ftl(一般購物類、訂房住宿類、交通航班類、房產租售類)
			returnFtlName = enumProdListFactory.getCatalogName();
			
		} catch (Exception e) {
			log.error("error:" + e);
			returnMsg = "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。";
			return ERROR;
		}
		
		return returnFtlName;
	}


	/**
	 * 商品設定功能初始畫面
	 * */
	public String setupInit() throws Exception{
		//user catalog清單
		pfpCatalogList = pfpCatalogService.getPfpCatalogList(super.getCustomer_info_id());
		customerInfoTitle = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()).getCustomerInfoTitle();
		
		
		PfpCatalogSetup pfpCatalogSetup = pfpCatalogSetupService.findSetupByCatalogSeq(catalogSeq);
		if(pfpCatalogSetup != null){
			String crop = pfpCatalogSetup.getCatalogSetupValue();
			cropType = crop.equals("crop")? "0" : "1";
		}
		System.out.println(catalogSeq);
		return SUCCESS;
	}
	
	/**
	 * 儲存設定
	 * */
	public String catalogSetupSaveAjax() throws Exception{
		JSONObject json = new JSONObject();
		PfpCatalog pfpCatalog = pfpCatalogService.getPfpCatalog(catalogSeq);
		if(pfpCatalog == null){
			json.put("result", "fail");
			result = json.toString();
			return SUCCESS;
		}
		String crop = null;
		String afterCropName = EnumProdCropType.CROP.getChName();
		String beforeCropName = "";
		for (EnumProdCropType enumProdCropType : EnumProdCropType.values()) {
			if(cropType.equals(enumProdCropType.getType())){
				crop = enumProdCropType.getValue();
				afterCropName = enumProdCropType.getChName();
				break;
			}
		}
		if(StringUtils.isBlank(crop)){
			json.put("result", "fail");
			result = json.toString();
			return SUCCESS;
		}
		Date date = new Date();
		PfpCatalogSetup pfpCatalogSetup = pfpCatalogSetupService.findSetupByCatalogSeq(catalogSeq);
		if(pfpCatalogSetup == null){
			pfpCatalogSetup = new PfpCatalogSetup();
			pfpCatalogSetup.setPfpCatalog(pfpCatalog);
			pfpCatalogSetup.setCatalogSetupKey("img_proportiona");
			pfpCatalogSetup.setCatalogSetupValue(crop);
			pfpCatalogSetup.setUpdateDate(date);
			pfpCatalogSetup.setCreateDate(date);
			pfpCatalogSetupService.saveOrUpdate(pfpCatalogSetup);
			
			json.put("result", "success");
			result = json.toString();
			
			
			//accesslog
			if(!beforeCropName.equals(afterCropName)) {
				String message = "目錄："+pfpCatalog.getCatalogName()+"=>商品圖片展示設定修改："+beforeCropName+"=>"+afterCropName;
				accesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, super.getId_pchome(),super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			}
			return SUCCESS;
		}else{
			
			beforeCropName = pfpCatalogSetup.getCatalogSetupValue();
			for (EnumProdCropType enumProdCropType : EnumProdCropType.values()) {
				if(beforeCropName.equals(enumProdCropType.getValue())){
					beforeCropName = enumProdCropType.getChName();
					break;
				}
			}
			pfpCatalogSetup.setCatalogSetupKey("img_proportiona");
			pfpCatalogSetup.setCatalogSetupValue(crop);
			pfpCatalogSetup.setPfpCatalog(pfpCatalog);
			pfpCatalogSetup.setUpdateDate(date);
			json.put("result", "success");
			result = json.toString();
			
			
			//accesslog
			if(!beforeCropName.equals(afterCropName)) {
				String message = "目錄："+pfpCatalog.getCatalogName()+"=>商品圖片展示設定修改："+beforeCropName+"=>"+afterCropName;
				accesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, super.getId_pchome(),super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
			}
			return SUCCESS;
		}
		
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
	
	public String getProdStatusType() {
		return prodStatusType;
	}

	public void setProdStatusType(String prodStatusType) {
		this.prodStatusType = prodStatusType;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
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
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public IPfpCatalogSetupService getPfpCatalogSetupService() {
		return pfpCatalogSetupService;
	}
	public void setPfpCatalogSetupService(IPfpCatalogSetupService pfpCatalogSetupService) {
		this.pfpCatalogSetupService = pfpCatalogSetupService;
	}
	public String getCropType() {
		return cropType;
	}
	public void setCropType(String cropType) {
		this.cropType = cropType;
	}
	public AdmAccesslogService getAccesslogService() {
		return accesslogService;
	}
	public void setAccesslogService(AdmAccesslogService accesslogService) {
		this.accesslogService = accesslogService;
	}
	
}
