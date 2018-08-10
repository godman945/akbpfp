package com.pchome.akbpfp.struts2.action.api;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.pchome.akbpfp.catalog.prodGroup.factory.AProdGroup;
import com.pchome.akbpfp.catalog.prodGroup.factory.ProdGroupFactory;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.service.catalog.prodGroup.PfpCatalogGroupService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.catalog.prodGroup.EnumProdGroupFactory;
import com.pchome.enumerate.catalog.prodGroup.EnumProdGroupList;


public class ProdGroupListAPIAction extends BaseCookieAction{
	
	private PfpCatalogGroupService pfpCatalogGroupService;
	private ProdGroupFactory prodGroupFactory;
	private String groupId;
	private int prodNum;
	private InputStream returnJson;
	
	public String getProdGroupListData() throws Exception{
		//先依據商品組合id，撈商品組合條件組成sql
		log.info(">>> groupId: "+groupId);
		log.info(">>> prodNum: "+prodNum);
		
		//商品組合ID 的目錄型態
		String catalogType = pfpCatalogGroupService.getCatalogType(groupId) ;
		log.info(">>> catalogType: "+catalogType);
		if( StringUtils.isBlank(catalogType)){
			returnJson = new ByteArrayInputStream(getReturnJsonObj("error",EnumProdGroupList.E001.getStatus()).toString().getBytes());
			return SUCCESS;
		}

		EnumProdGroupFactory enumProdGroupFactory = EnumProdGroupFactory.getCatalogName(catalogType);
		log.info(">>> enumProdGroupFactory: "+enumProdGroupFactory);
		if ( enumProdGroupFactory == null ){
			returnJson = new ByteArrayInputStream(getReturnJsonObj("error",EnumProdGroupList.E002.getStatus()).toString().getBytes());
			return SUCCESS;
		}

		String catalogName = enumProdGroupFactory.getCatalogName();
		log.info(">>> catalogName: "+catalogName);
		AProdGroup aProdGroup = prodGroupFactory.getAProdGroupObj(catalogName);
		log.info(">>> aProdGroup: "+aProdGroup);
		if (aProdGroup == null){
			returnJson = new ByteArrayInputStream(getReturnJsonObj("error",EnumProdGroupList.E002.getStatus()).toString().getBytes());
			return SUCCESS;
		}
		
		
		List<PfpCatalogGroupItem> pfpCatalogGroupItems = aProdGroup.getPfpCatalogGroupItemList(groupId);
		log.info(">>> pfpCatalogGroupItems size : "+pfpCatalogGroupItems.size());
		if( (pfpCatalogGroupItems.isEmpty()) && (pfpCatalogGroupItems.size()<=0) ){
			returnJson = new ByteArrayInputStream(getReturnJsonObj("error",EnumProdGroupList.E003.getStatus()).toString().getBytes());
			return SUCCESS;
		}
		
		//將pfpCatalogGroupItems轉成sql
		String filterSQL = aProdGroup.pfpCatalogGroupItemTofilterSQL(pfpCatalogGroupItems);
		
		 
		String catalogSeq = pfpCatalogGroupService.getCatalogSeq(groupId) ;
		log.info(">>> catalogSeq : "+catalogSeq);
		//撈出該商品組合的list
		JSONArray returnListJson = aProdGroup.getProdGroupList(catalogSeq,filterSQL,prodNum);
		log.info(">>> returnListJson length : "+returnListJson.length());
		if (returnListJson.length()<=0){
			returnJson = new ByteArrayInputStream(getReturnJsonObj("error",EnumProdGroupList.E004.getStatus()).toString().getBytes());
			return SUCCESS;
		}
		 
		 
		JSONObject returnJsonObj = getReturnJsonObj("success",EnumProdGroupList.S001.getStatus());
		returnJsonObj.put("returnNum", returnListJson.length());
		returnJsonObj.put("prodGroupList", returnListJson);
		returnJson = new ByteArrayInputStream(returnJsonObj.toString().getBytes("UTF-8"));
		return SUCCESS;		
	}
	
	
	
	public JSONObject getReturnJsonObj(String status, String code) throws Exception {
		
		JSONObject prodDataJson = new JSONObject(); 
		
		prodDataJson.put("status", status);
		prodDataJson.put("code", code);

		return prodDataJson;
	}
		
	
	public void setPfpCatalogGroupService(PfpCatalogGroupService pfpCatalogGroupService) {
		this.pfpCatalogGroupService = pfpCatalogGroupService;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setProdNum(int prodNum) {
		this.prodNum = prodNum;
	}

	public void setProdGroupFactory(ProdGroupFactory prodGroupFactory) {
		this.prodGroupFactory = prodGroupFactory;
	}

	public InputStream getReturnJson() {
		return returnJson;
	}

	public void setReturnJson(InputStream returnJson) {
		this.returnJson = returnJson;
	}

}
