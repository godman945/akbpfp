package com.pchome.akbpfp.catalog.prodGroup.factory;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.vo.catalog.prodGroup.ProdGroupConditionVO;

public class BookingProdGroup extends AProdGroup {
	
	public String pfpCatalogGroupItemTofilterSQL(List<PfpCatalogGroupItem> pfpCatalogGroupItems) throws Exception{
		return "booking filterConditionsToSQL";
	}
	
	@Override
	public JSONArray getProdGroupListByRandom(String catalogSeq, String filterSQL, int prodNum) throws Exception {
		JSONArray prodListJson = new JSONArray(); 
		
		return prodListJson;
	}
	
	@Override
	public List<Object> getProdGroupList(ProdGroupConditionVO prodGroupConditionVO) throws Exception{
		
		return null;
	}
	
	public String getProdGroupCount(String catalogSeq, String filterSQL) throws Exception{
		return null;
	}
	
	
}