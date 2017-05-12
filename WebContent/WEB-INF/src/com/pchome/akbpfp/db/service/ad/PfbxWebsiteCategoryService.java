package com.pchome.akbpfp.db.service.ad;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.ad.IPfbxWebsiteCategoryDAO;
import com.pchome.akbpfp.db.pojo.PfbxWebsiteCategory;
import com.pchome.akbpfp.db.service.BaseService;

public class PfbxWebsiteCategoryService extends BaseService<PfbxWebsiteCategory,String> implements IPfbxWebsiteCategoryService {

	@Override
	public Map<String, List<Map<String, String>>> getAllOrderByCode() {
		
		List<PfbxWebsiteCategory> pfbxWebsiteCategoryList = ((IPfbxWebsiteCategoryDAO)dao).getAllOrderByCode();
		
		Map<String, List<PfbxWebsiteCategory>> pfbxWebsiteCategoryMap = new LinkedHashMap<String, List<PfbxWebsiteCategory>>();
		for (int i = 0; i <= pfbxWebsiteCategoryList.size() - 1; i++) {
			List<PfbxWebsiteCategory> a = new ArrayList<PfbxWebsiteCategory>();
			a.add(pfbxWebsiteCategoryList.get(i));
			pfbxWebsiteCategoryMap.put(pfbxWebsiteCategoryList.get(i).getCode(),a);
		    
		}
		
		//處理回傳格式
		Map<String,List<Map<String,String>>> map =new LinkedHashMap<String,List<Map<String,String>>>();
		
		String agoKey = "0";
		
		for (Object key : pfbxWebsiteCategoryMap.keySet()) {
			for (int i = 0; i < pfbxWebsiteCategoryMap.get(key).size(); i++) {
				List<Map<String,String>> LlistMap =new ArrayList<Map<String,String>>();
				Map<String,String>  a = new HashMap<String,String>(); 
				a.put("level",String.valueOf(pfbxWebsiteCategoryMap.get(key).get(i).getLevel()));
				a.put("code", pfbxWebsiteCategoryMap.get(key).get(i).getCode());
				a.put("name", pfbxWebsiteCategoryMap.get(key).get(i).getName());
				a.put("parentId", pfbxWebsiteCategoryMap.get(key).get(i).getParentId());
				a.put("id", String.valueOf(pfbxWebsiteCategoryMap.get(key).get(i).getId()));
				LlistMap.add(a);
				if(map.get(key)!=null){
					map.get(key).addAll(LlistMap);
				}else{
					map.put(key.toString(), LlistMap);	
				}
				
				//設定上一筆是否有下層
				if(map.get(agoKey) != null){
					List<Map<String,String>> agoListMap = new ArrayList<Map<String,String>>();
					Map<String,String> b = new HashMap<String,String>(); 
					agoListMap = map.get(agoKey);
					b = agoListMap.get(0);
					
					String children = "N";
					if(Integer.parseInt(b.get("level")) < Integer.parseInt(a.get("level"))){
						children = "Y";
					}
					
					b.put("children", children);
					agoListMap = new ArrayList<Map<String,String>>();
					agoListMap.add(b);
					map.put(agoKey, agoListMap);
				}
				
				agoKey = key.toString();
			}
		}
		
		//最後一筆直接設定children = N
		if(map.get(agoKey) != null){
			List<Map<String,String>> agoListMap = new ArrayList<Map<String,String>>();
			Map<String,String> b = new HashMap<String,String>(); 
			agoListMap = map.get(agoKey);
			b = agoListMap.get(0);
			b.put("children", "N");
			agoListMap = new ArrayList<Map<String,String>>();
			agoListMap.add(b);
			map.put(agoKey, agoListMap);
		}
		
		
		return map;
	}

}
