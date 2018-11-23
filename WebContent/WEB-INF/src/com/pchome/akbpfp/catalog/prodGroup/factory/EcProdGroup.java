package com.pchome.akbpfp.catalog.prodGroup.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.service.catalog.prod.PfpCatalogProdEcService;
import com.pchome.akbpfp.db.vo.catalog.prodGroup.ProdGroupConditionVO;
import com.pchome.enumerate.catalog.prodGroup.EnumEcProdGroupCondition;
import com.pchome.enumerate.catalog.prodGroup.EnumEcProdGroupField;


public class EcProdGroup extends AProdGroup {
	
	private PfpCatalogProdEcService pfpCatalogProdEcService;
	
	
	public String pfpCatalogGroupItemTofilterSQL(List<PfpCatalogGroupItem> pfpCatalogGroupItems) throws Exception{
		
		StringBuffer filterSQL = new StringBuffer();
		List<PfpCatalogGroupItem> replaceConditionGroupItemsList = new ArrayList<PfpCatalogGroupItem>(); 
		
		if( (!pfpCatalogGroupItems.isEmpty()) && (pfpCatalogGroupItems.size()>0) ){
			for (PfpCatalogGroupItem pfpCatalogGroupItem : pfpCatalogGroupItems) {
				PfpCatalogGroupItem replaceConditionGroupItems = new PfpCatalogGroupItem();
				
				String condition = pfpCatalogGroupItem.getCatalogGroupItemCondition();
				EnumEcProdGroupCondition enumEcProdGroupCondition = EnumEcProdGroupCondition.valueOf(condition);
				replaceConditionGroupItems.setCatalogGroupItemCondition(enumEcProdGroupCondition.getSymbol());
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
				EnumEcProdGroupField enumEcProdGroupField = EnumEcProdGroupField.valueOf(field);
				
				if (enumEcProdGroupField.getFieldType().equals("string")){
					if ( StringUtils.equals("catalog_prod_seq", field) || StringUtils.equals("ec_name", field) ){
						filterSQL.append("'%"+replaceConditionGroupItems.getCatalogGroupItemValue()+"%'");
					}else{
						filterSQL.append("'"+replaceConditionGroupItems.getCatalogGroupItemValue()+"'");
					}
				}
				if (enumEcProdGroupField.getFieldType().equals("int")){
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
	public List<Object> getProdGroupList(ProdGroupConditionVO prodGroupConditionVO) throws Exception{
		
		return pfpCatalogProdEcService.getEcProdGroupList(prodGroupConditionVO);
		
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
			prodDataJson.put("catalog_prod_seq", obj.get("catalog_prod_seq").toString());
			prodDataJson.put("catalog_seq", obj.get("catalog_seq").toString());
			prodDataJson.put("ec_name", obj.get("ec_name").toString());
			prodDataJson.put("ec_title", obj.get("ec_title").toString());
			prodDataJson.put("ec_url", obj.get("ec_url").toString());
			
			if (StringUtils.equals( obj.get("ec_img_region").toString(), "V")){
				prodDataJson.put("ec_img_region","vertical");
			}
			if (StringUtils.equals( obj.get("ec_img_region").toString(), "H")){
				prodDataJson.put("ec_img_region","horizontal");
			}
			
			prodDataJson.put("ec_img", obj.get("ec_img").toString());
			prodDataJson.put("ec_price", obj.get("ec_price").toString());
			prodDataJson.put("ec_discount_price", obj.get("ec_discount_price").toString());
			prodDataJson.put("ec_stock_status", obj.get("ec_stock_status").toString());
			prodDataJson.put("ec_use_status", obj.get("ec_use_status").toString());
			prodDataJson.put("ec_category", obj.get("ec_category").toString());
			prodDataJson.put("crop_type", obj.get("catalog_setup_value").toString());
			
			prodListJson.put(prodDataJson);
		}
		return prodListJson;
	}
	
	
	public String getProdGroupCount(String catalogSeq, String filterSQL) throws Exception{
		return pfpCatalogProdEcService.getProdGroupCount(catalogSeq, filterSQL);
	}
	

	
	
	
	public List<String> queryCategoryGroupByVal(String catalogSeq) throws Exception{
		return pfpCatalogProdEcService.queryCategoryGroupByVal(catalogSeq);
	}
	
	
	
	
	public void setPfpCatalogProdEcService(PfpCatalogProdEcService pfpCatalogProdEcService) {
		this.pfpCatalogProdEcService = pfpCatalogProdEcService;
	}
	
}