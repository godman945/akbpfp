package com.pchome.akbpfp.struts2.action.api;

import com.pchome.akbpfp.catalog.prodGroup.factory.AProdGroup;
import com.pchome.akbpfp.catalog.prodGroup.factory.ProdGroupFactory;
import com.pchome.akbpfp.db.service.catalog.prodGroup.PfpCatalogGroupService;
import com.pchome.akbpfp.struts2.BaseCookieAction;

public class CatalogProdGroupAPIAction extends BaseCookieAction{
	
//	private PfpOrderService pfpOrderService;
	private String groupId;
	private String returnJson;
	
	public String prodGroupList() throws Exception{
		
		System.out.println("groupId : "+groupId);
		
		
		
		
		returnJson = groupId;
		
		return SUCCESS;
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

	
	
	
	
}
