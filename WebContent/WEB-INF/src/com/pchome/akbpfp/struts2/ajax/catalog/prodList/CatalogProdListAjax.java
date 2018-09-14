package com.pchome.akbpfp.struts2.ajax.catalog.prodList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.catalog.prodList.factory.AProdList;
import com.pchome.akbpfp.catalog.prodList.factory.ProdListFactory;
import com.pchome.akbpfp.db.service.catalog.TMP.IPfpCatalogService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.catalog.prodList.EnumProdListFactory;


 public class CatalogProdListAjax extends BaseCookieAction{

	private static final long serialVersionUID = 1L;
//	private IAdmFreeGiftService admFreeGiftService;
//	private IAdmFreeRecordService admFreeRecordService;
	
	private String catalogSeq;
	private String prodStatus;
	private String[] prodIdArray;
	private IPfpCatalogService pfpCatalogService;
	private ProdListFactory prodListFactory;
	
	private String prodId;
	Map<String, Object> prodDetailMap;
	

	/**
	 * 更新商品清單狀態
	 */
	public String updateProdStatusAjax() {
		try{
			log.info(">>> catalogSeq: " + catalogSeq);
			log.info(">>> ecStatus: " + prodStatus);
			log.info(">>> prodIdArray: " + prodIdArray.toString());
			
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
			
			List<String> prodIdList = Arrays.asList(prodIdArray); 
			aProdList.updateProdListProdStatus(catalogSeq, prodStatus, prodIdList);
			
		} catch (Exception e) {
//			dataMap.put("status", "ERROR");
//			dataMap.put("msg", "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			log.error("error:" + e);
		}

		return SUCCESS;
	}
	
	
	/**
	 * 取得商品名細
	 */
	public String queryProdListDetailAjax(){
		try{
			log.info(">>> catalogSeq: " + catalogSeq);
			log.info(">>> prodId: " + prodId);
			
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
			
			prodDetailMap = new HashMap<String, Object> ();
			prodDetailMap = aProdList.queryProdListDetail(catalogSeq,prodId).get(0);
			
		} catch (Exception e) {
//			dataMap.put("status", "ERROR");
//			dataMap.put("msg", "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			log.error("error:" + e);
		}
		
		return SUCCESS;
		
	}
	

	
	
	public void setCatalogSeq(String catalogSeq) {
		this.catalogSeq = catalogSeq;
	}

	public void setProdIdArray(String[] prodIdArray) {
		this.prodIdArray = prodIdArray;
	}

	public void setProdStatus(String prodStatus) {
		this.prodStatus = prodStatus;
	}
	
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	
	public Map<String, Object> getProdDetailMap() {
		return prodDetailMap;
	}

	public void setPfpCatalogService(IPfpCatalogService pfpCatalogService) {
		this.pfpCatalogService = pfpCatalogService;
	}
	
	public void setProdListFactory(ProdListFactory prodListFactory) {
		this.prodListFactory = prodListFactory;
	}

	
	
}