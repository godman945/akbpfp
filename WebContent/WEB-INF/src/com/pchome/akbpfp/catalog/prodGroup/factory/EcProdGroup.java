package com.pchome.akbpfp.catalog.prodGroup.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.service.catalog.prod.PfpCatalogProdEcService;
import com.pchome.enumerate.catalog.prodGroup.EnumProdGroupCondition;
import com.pchome.enumerate.catalog.prodGroup.EnumProdGroupField;





public class EcProdGroup extends AProdGroup {
	
	private PfpCatalogProdEcService pfpCatalogProdEcService;
	
	
	public String pfpCatalogGroupItemTofilterSQL(List<PfpCatalogGroupItem> pfpCatalogGroupItems) throws Exception{
		
		StringBuffer filterSQL = new StringBuffer();
		List<PfpCatalogGroupItem> replaceConditionGroupItemsList = new ArrayList<PfpCatalogGroupItem>(); 
		
		if( (!pfpCatalogGroupItems.isEmpty()) && (pfpCatalogGroupItems.size()>0) ){
			for (PfpCatalogGroupItem pfpCatalogGroupItem : pfpCatalogGroupItems) {
				PfpCatalogGroupItem replaceConditionGroupItems = new PfpCatalogGroupItem();
				
				String condition = pfpCatalogGroupItem.getCatalogGroupItemCondition();
				EnumProdGroupCondition enumProdGroupCondition = EnumProdGroupCondition.valueOf(condition);
				replaceConditionGroupItems.setCatalogGroupItemCondition(enumProdGroupCondition.getSymbol());
				replaceConditionGroupItems.setCatalogGroupItemField(pfpCatalogGroupItem.getCatalogGroupItemField());
				replaceConditionGroupItems.setCatalogGroupItemValue(pfpCatalogGroupItem.getCatalogGroupItemValue());
				
				replaceConditionGroupItemsList.add(replaceConditionGroupItems);
			}
			
			
			for (PfpCatalogGroupItem replaceConditionGroupItems : replaceConditionGroupItemsList) {
				filterSQL.append(" and ");
				filterSQL.append(replaceConditionGroupItems.getCatalogGroupItemField());
				filterSQL.append(" ");
				filterSQL.append(replaceConditionGroupItems.getCatalogGroupItemCondition());
				filterSQL.append(" ");
				
				String field = replaceConditionGroupItems.getCatalogGroupItemField();
				EnumProdGroupField enumProdGroupField = EnumProdGroupField.valueOf(field);
				
				if (enumProdGroupField.getFieldType().equals("string")){
					filterSQL.append("'"+replaceConditionGroupItems.getCatalogGroupItemValue()+"'");
				}
				if (enumProdGroupField.getFieldType().equals("int")){
					filterSQL.append(""+replaceConditionGroupItems.getCatalogGroupItemValue()+"");
				}
			}
			
		}

		return  filterSQL.toString();
	}
	
	
	@Override
	public JSONArray getProdGroupListByRandom(String catalogSeq, String filterSQL, int prodNum) throws Exception {
		
			
		List<Map<String,Object>> ecProdGroupList = pfpCatalogProdEcService.getEcProdGroupListByRandom(catalogSeq, filterSQL,prodNum);
		
		JSONArray prodListJson = prodGroupListToArray(ecProdGroupList);
		
		return prodListJson;
	}
	
	
	@Override
	public List<Map<String,Object>> getProdGroupList(String catalogSeq, String filterSQL) throws Exception{
		
		return pfpCatalogProdEcService.getEcProdGroupList(catalogSeq, filterSQL);
		
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
	
	
	public String getProdGroupCount(String catalogSeq, String filterSQL) throws Exception{
		return pfpCatalogProdEcService.getProdGroupCount(catalogSeq, filterSQL);
	}
	

	
	
	
	
	public void setPfpCatalogProdEcService(PfpCatalogProdEcService pfpCatalogProdEcService) {
		this.pfpCatalogProdEcService = pfpCatalogProdEcService;
	}
	
	
	
	
	
	

}