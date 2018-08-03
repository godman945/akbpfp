package com.pchome.akbpfp.catalog.prodGroup.factory;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pchome.akbpfp.db.service.catalog.prodGroup.PfpCatalogGroupService;



public class EcProdGroup extends AProdGroup {
	
	private PfpCatalogGroupService pfpCatalogGroupService;
	
	@Override
	public JSONArray getProdGroupList(String catalogSeq, String filterSQL) throws Exception {
		
			
		List<Map<String,Object>> ecProdGroupList = pfpCatalogGroupService.getEcProdGroupList(catalogSeq, filterSQL);
		
		JSONArray prodListJson = prodGroupListToArray(ecProdGroupList);
		
		return prodListJson;
	}
	
	
	
	
	public JSONArray prodGroupListToArray(List<Map<String,Object>> ecProdGroupList) throws Exception {
		
		JSONArray prodListJson = new JSONArray(); 
		if ( ecProdGroupList.isEmpty() || ecProdGroupList.size()<=0 ){
			return prodListJson;
		}
		
		
		for (Object object : ecProdGroupList) {
			JSONObject prodDataJson = new JSONObject(); 
			
			Map obj = (Map) object;
			prodDataJson.put("id", obj.get("id").toString());
			prodDataJson.put("catalog_prod_ec_seq", obj.get("catalog_prod_ec_seq").toString());
			prodDataJson.put("catalog_seq", obj.get("catalog_seq").toString());
			prodDataJson.put("prod_name", obj.get("prod_name").toString());
			prodDataJson.put("prod_title", obj.get("prod_title").toString());
			prodDataJson.put("prod_url", obj.get("prod_url").toString());
			prodDataJson.put("prod_price", "NT."+obj.get("prod_price").toString());
			prodDataJson.put("prod_discount_price", "NT."+obj.get("prod_discount_price").toString());
			prodDataJson.put("prod_stock_status", obj.get("prod_stock_status").toString());
			prodDataJson.put("prod_use_status", obj.get("prod_use_status").toString());
			prodDataJson.put("prod_category", obj.get("prod_category").toString());
			
			prodListJson.put(prodDataJson);
		}
		return prodListJson;
	}
	
	

	public void setPfpCatalogGroupService(PfpCatalogGroupService pfpCatalogGroupService) {
		this.pfpCatalogGroupService = pfpCatalogGroupService;
	}
	
	
	

}