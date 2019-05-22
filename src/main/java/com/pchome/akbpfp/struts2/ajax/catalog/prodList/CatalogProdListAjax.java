package com.pchome.akbpfp.struts2.ajax.catalog.prodList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import com.pchome.akbpfp.catalog.prodList.factory.AProdList;
import com.pchome.akbpfp.catalog.prodList.factory.ProdListFactory;
import com.pchome.akbpfp.db.service.accesslog.AdmAccesslogService;
import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.vo.catalog.prodList.ProdListConditionVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.catalog.prodList.EnumProdListFactory;
import com.pchome.enumerate.catalogprod.EnumEcStatusType;
import com.pchome.rmi.accesslog.EnumAccesslogAction;


 public class CatalogProdListAjax extends BaseCookieAction{

	private static final long serialVersionUID = 1L;
	
	private String catalogSeq;
	private int currentPage;
	private int pageSizeSelected;
	private String prodStatus;
	private String updateProdStatus; //更新商品狀態
	private String prodName;
	private String prodIdArray;
	private IPfpCatalogService pfpCatalogService;
	private ProdListFactory prodListFactory;
	private List<Object> prodList; 
	private String prodId;
	private int pageCount = 0; //總頁數
	private int totalCount = 0; //資料總筆數
	private Map<String, Object> prodDetailMap;
	private Map<String,Object> resultMap;
	private AdmAccesslogService accesslogService;
	
	
	/**
	 * 取得商品清單Ajax
	 */
	public String queryProdListAjax(){
		try{
			log.info(">>> catalogSeq: " + catalogSeq);
			log.info(">>> currentPage: " + currentPage);
			log.info(">>> pageSizeSelected: " + pageSizeSelected);
			log.info(">>> prodStatus: " + prodStatus);
			log.info(">>> prodName: " + prodName);
			
			resultMap = new HashMap<String, Object>();
	
			//商品組合ID 的目錄型態
			String catalogType = pfpCatalogService.getCatalogType(catalogSeq);
			log.info(">>> catalogType: " + catalogType);
			if (StringUtils.isBlank(catalogType)) {
				resultMap = returnErrorMsgMap("目錄型態不正確");
				return SUCCESS;
			}
			
			EnumProdListFactory enumProdListFactory = EnumProdListFactory.getCatalogName(catalogType);
			log.info(">>> enumProdListFactory: " + enumProdListFactory);
			if (enumProdListFactory == null) {
				resultMap = returnErrorMsgMap("目錄型態不正確");
				return SUCCESS;
			}
			
			AProdList aProdList = prodListFactory.getAProdListObj(enumProdListFactory.getCatalogName());
			log.info(">>> aProdList: "+aProdList);
			if (aProdList == null){
				resultMap = returnErrorMsgMap("此商品組合ID的目錄型態不正確");
				return SUCCESS;
			}
	
			String pfpCustomerInfoId = super.getCustomer_info_id();
			
			ProdListConditionVO prodListConditionVO =  new ProdListConditionVO();
			prodListConditionVO.setCatalogSeq(catalogSeq);
			prodListConditionVO.setProdStatus(prodStatus);
			prodListConditionVO.setPfpCustomerInfoId(pfpCustomerInfoId);
			prodListConditionVO.setPage(currentPage);
			prodListConditionVO.setPageSize(pageSizeSelected);
			prodListConditionVO.setProdName(prodName);
			
			
			prodList = aProdList.getProdList(prodListConditionVO);
			if (prodList.size() <= 0){
				resultMap = returnErrorMsgMap("沒有商品清單資料");
				return SUCCESS;
			}
			
			//商品清單資料總筆數
			totalCount =  Integer.valueOf(aProdList.getProdListCount(prodListConditionVO));
			
			//總頁數
			pageCount = (int)Math.ceil((float)totalCount / pageSizeSelected);
			
			resultMap.put("currentPage", currentPage);
			resultMap.put("pageSizeSelected", pageSizeSelected);
			resultMap.put("prodStatus", prodStatus);
			resultMap.put("prodName", prodName);
			resultMap.put("totalCount", totalCount);
			resultMap.put("pageCount", pageCount);
			resultMap.put("prodList", prodList);
			resultMap.put("status", "SUCCESS");
			
		} catch (Exception e) {
			log.error("error:" + e);
			resultMap = returnErrorMsgMap("系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			return SUCCESS;
		}
		
		return SUCCESS;
	}
	
	
	
	
	/**
	 * 回傳錯誤訊息
	 */
	public Map<String,Object> returnErrorMsgMap(String errorMsg) {
		
		Map<String,Object> errorMsgMap = new HashMap<String, Object>();
		errorMsgMap.put("currentPage", 1);
		errorMsgMap.put("pageCount", 1);
		errorMsgMap.put("totalCount", 0);
		errorMsgMap.put("status", "ERROR");
		errorMsgMap.put("msg", errorMsg);
		
		return errorMsgMap;
	}
	
	
	
	

	/**
	 * 更新商品清單狀態
	 */
	@SuppressWarnings("unchecked")
	public String updateProdStatusAjax() {
		try{
			log.info(">>> catalogSeq: " + catalogSeq);
			log.info(">>> updateProdStatus: " + updateProdStatus);
			log.info(">>> prodIdArray: " + prodIdArray.toString());
			
			//還原頁面狀態
			log.info(">>> currentPage: " + currentPage);
			log.info(">>> pageSizeSelected: " + pageSizeSelected);
			log.info(">>> prodStatus: " + prodStatus);
			log.info(">>> prodName: " + prodName);
			
			resultMap = new HashMap<String, Object>();
			
			//商品組合ID 的目錄型態
			String catalogType = pfpCatalogService.getCatalogType(catalogSeq);
			log.info(">>> catalogType: " + catalogType);
			if (StringUtils.isBlank(catalogType)) {
				resultMap = returnErrorMsgMap("目錄型態不正確");
				return SUCCESS;
			}
			
			EnumProdListFactory enumProdListFactory = EnumProdListFactory.getCatalogName(catalogType);
			log.info(">>> enumProdListFactory: " + enumProdListFactory);
			if (enumProdListFactory == null) {
				resultMap = returnErrorMsgMap("目錄型態不正確");
				return SUCCESS;
			}
			
			AProdList aProdList = prodListFactory.getAProdListObj(enumProdListFactory.getCatalogName());
			log.info(">>> aProdList: "+aProdList);
			if (aProdList == null){
				resultMap = returnErrorMsgMap("此商品組合ID的目錄型態不正確");
				return SUCCESS;
			}
			
			JSONObject prodIdJson = new JSONObject(prodIdArray);
			Iterator<String> keys = prodIdJson.keys();
			List<String> prodIdList = new ArrayList<String>();
			while(keys.hasNext()) {
			    String key = keys.next();
			    String prodId = ((JSONObject)prodIdJson.get(key)).getString("prodId");
			    prodIdList.add(prodId);
			}
			aProdList.updateProdListProdStatus(catalogSeq, updateProdStatus, prodIdList);
			
			resultMap.put("currentPage", 1);
			resultMap.put("pageSizeSelected", pageSizeSelected);
			resultMap.put("prodStatus", prodStatus);
			resultMap.put("prodName", prodName);
			resultMap.put("msg", "商品狀態更新成功");
			resultMap.put("status", "SUCCESS");
			
			//accesslog
			for (EnumEcStatusType enumEcStatusType : EnumEcStatusType.values()) {
				if(enumEcStatusType.getType().equals(updateProdStatus)) {
					String catalogName = pfpCatalogService.get(catalogSeq).getCatalogName();
					String message = "目錄："+catalogName+"=>["+prodIdList.size()+"筆]：=>"+enumEcStatusType.getChName();
					accesslogService.recordAdLog(EnumAccesslogAction.PLAY_MODIFY, message, super.getId_pchome(),super.getCustomer_info_id(), super.getUser_id(), request.getRemoteAddr());
					break;
				}
			}
			
			
		} catch (Exception e) {
			log.error("error:" + e);
			resultMap = returnErrorMsgMap("系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			return SUCCESS;
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

			resultMap = new HashMap<String, Object>();
			
			//商品組合ID 的目錄型態
			String catalogType = pfpCatalogService.getCatalogType(catalogSeq);
			log.info(">>> catalogType: " + catalogType);
			if (StringUtils.isBlank(catalogType)) {
				resultMap.put("msg", "目錄型態不正確");
				resultMap.put("status", "ERROR");
				return SUCCESS;
			}
			
			EnumProdListFactory enumProdListFactory = EnumProdListFactory.getCatalogName(catalogType);
			log.info(">>> enumProdListFactory: " + enumProdListFactory);
			if (enumProdListFactory == null) {
				resultMap.put("msg", "目錄型態不正確");
				resultMap.put("status", "ERROR");
				return SUCCESS;
			}
			
			AProdList aProdList = prodListFactory.getAProdListObj(enumProdListFactory.getCatalogName());
			log.info(">>> aProdList: "+aProdList);
			if (aProdList == null){
				resultMap.put("msg", "此商品組合ID的目錄型態不正確");
				resultMap.put("status", "ERROR");
				return SUCCESS;
			}
			
			prodDetailMap = new HashMap<String, Object> ();
			prodDetailMap = aProdList.queryProdListDetail(catalogSeq,prodId).get(0);
			
		} catch (Exception e) {
			log.error("error:" + e);
			resultMap.put("msg", "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			resultMap.put("status", "ERROR");
			return SUCCESS;
		}
		
		return SUCCESS;
	}
	

	
	
	public void setCatalogSeq(String catalogSeq) {
		this.catalogSeq = catalogSeq;
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

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getCatalogSeq() {
		return catalogSeq;
	}

	public String getProdStatus() {
		return prodStatus;
	}

	public String getProdIdArray() {
		return prodIdArray;
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

	public List<Object> getProdList() {
		return prodList;
	}

	public void setProdList(List<Object> prodList) {
		this.prodList = prodList;
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

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public String getUpdateProdStatus() {
		return updateProdStatus;
	}

	public void setUpdateProdStatus(String updateProdStatus) {
		this.updateProdStatus = updateProdStatus;
	}

	public void setProdIdArray(String prodIdArray) {
		this.prodIdArray = prodIdArray;
	}
	public AdmAccesslogService getAccesslogService() {
		return accesslogService;
	}
	public void setAccesslogService(AdmAccesslogService accesslogService) {
		this.accesslogService = accesslogService;
	}
	
}