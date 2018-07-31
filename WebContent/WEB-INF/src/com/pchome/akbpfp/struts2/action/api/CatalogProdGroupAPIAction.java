package com.pchome.akbpfp.struts2.action.api;

import com.pchome.akbpfp.catalog.prodGroup.factory.AProdGroup;
import com.pchome.akbpfp.catalog.prodGroup.factory.ProdGroupFactory;
import com.pchome.akbpfp.db.service.catalog.prodGroup.PfpCatalogGroupService;
import com.pchome.akbpfp.struts2.BaseCookieAction;

public class CatalogProdGroupAPIAction extends BaseCookieAction{
	
	private PfpCatalogGroupService pfpCatalogGroupService;
//	private PfpOrderService pfpOrderService;
	private ProdGroupFactory prodGroupFactory;
	private String groupId;
	private String returnJson;
	
	public String prodGroupList() throws Exception{
		
		System.out.println("groupId : "+groupId);
		
		
		//先依據商品組合id，撈商品組合條件組成sql
		
		
		
		
		String catalogType = pfpCatalogGroupService.getCatalogType(groupId) ;
		
		AProdGroup aProdGroup = prodGroupFactory.getAProdGroupObj(catalogType);
		
		
		System.out.println("父類別方法1: "+aProdGroup.getCatalogGroupSQL());
		System.out.println("子類別方法1: "+aProdGroup.getProdGroupList(groupId));
		
		
		
		aProdGroup = prodGroupFactory.getAProdGroupObj("2");
		System.out.println("父類別方法2: "+aProdGroup.getCatalogGroupSQL());
		System.out.println("子類別方法2: "+aProdGroup.getProdGroupList(groupId));
		
		groupId = aProdGroup.getProdGroupList(groupId);
		
		
		returnJson = groupId;
		
		return SUCCESS;
	}
	
	
	
//	public void setPfpOrderService(PfpOrderService pfpOrderService) {
//		this.pfpOrderService = pfpOrderService;
//	}


//	public String getGroupId() {
//		return groupId;
//	}


	public void setPfpCatalogGroupService(PfpCatalogGroupService pfpCatalogGroupService) {
		this.pfpCatalogGroupService = pfpCatalogGroupService;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	public String getReturnJson() {
		return returnJson;
	}


	public void setReturnJson(String returnJson) {
		this.returnJson = returnJson;
	}



	public void setProdGroupFactory(ProdGroupFactory prodGroupFactory) {
		this.prodGroupFactory = prodGroupFactory;
	}


	
	
	
	
	
	
}
