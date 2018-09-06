package com.pchome.akbpfp.struts2.ajax.catalog.prodGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.pchome.akbpfp.catalog.prodGroup.factory.AProdGroup;
import com.pchome.akbpfp.catalog.prodGroup.factory.ProdGroupFactory;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.service.catalog.TMP.IPfpCatalogService;
import com.pchome.akbpfp.db.service.catalog.prodGroup.IPfpCatalogGroupItemService;
import com.pchome.akbpfp.db.service.catalog.prodGroup.IPfpCatalogGroupService;
import com.pchome.akbpfp.db.service.sequence.SequenceService;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.catalog.prodGroup.EnumProdGroupCondition;
import com.pchome.enumerate.catalog.prodGroup.EnumProdGroupFactory;
import com.pchome.enumerate.catalog.prodGroup.EnumProdGroupField;
import com.pchome.enumerate.sequence.EnumSequenceTableName;

public class ProdGroupAjax  extends BaseCookieAction{

	private static final long serialVersionUID = 1L;
	
	private String catalogSeq;
	private String catalogGroupName;
	private String catalogGroupSeq;
	private String prodGroupFilterContent;
	private SequenceService sequenceService;
	private IPfpCatalogService pfpCatalogService;
	private IPfpCatalogGroupService pfpCatalogGroupService;
	private IPfpCatalogGroupItemService pfpCatalogGroupItemService;
	private ProdGroupFactory prodGroupFactory;
	
	private String[] filterContentMap;
	private List<Map<String,Object>> rtnProdGroupListMap;
	private String rtnProdGroupListSize;
	private String result;
	

	/**
	 * 更新商品清單狀態
	 */
	public String queryProdGroupFilterContentAjax() {
		try{
			log.info(">>> catalogGroupSeq: " + catalogGroupSeq);
			
			List<PfpCatalogGroupItem> pfpCatalogGroupItemList = pfpCatalogGroupItemService.getPfpCatalogGroupItemList(catalogGroupSeq);
			
			StringBuffer filterContent =new StringBuffer();
			String field;
			String condition;
			String value;
			for (PfpCatalogGroupItem pfpCatalogGroupItem : pfpCatalogGroupItemList) {
				field = pfpCatalogGroupItem.getCatalogGroupItemField();
				EnumProdGroupField enumProdGroupField = EnumProdGroupField.valueOf(field);
				field = enumProdGroupField.getFieldDesc();
				
				condition = pfpCatalogGroupItem.getCatalogGroupItemCondition();
				EnumProdGroupCondition enumProdGroupCondition = EnumProdGroupCondition.valueOf(condition);
				condition = enumProdGroupCondition.getConditionDesc();
				
				
				value = pfpCatalogGroupItem.getCatalogGroupItemValue();
				
				filterContent.append(field).append(" : ");
				filterContent.append(condition).append("&nbsp;");
				filterContent.append(value).append("<br>");
			}
			prodGroupFilterContent = filterContent.toString(); 
			
		} catch (Exception e) {
//			dataMap.put("status", "ERROR");
//			dataMap.put("msg", "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			log.error("error:" + e);
		}

		return SUCCESS;
		
	}

	
	/**
	 * 建立目錄商品組合
	 */
	public String addCatalogProdGroupAjax() {
		try{
			log.info(">>> catalogSeq: " + catalogSeq);
			log.info(">>> catalogGroupName: " + catalogGroupName);
			log.info(">>> filterContentMap: " + filterContentMap);
			
			
			
			
			System.out.println("MAP : "+filterContentMap);
			
			//新增的篩選條件
			JSONArray filterContentArray = new JSONArray(filterContentMap[0].toString());
			
			//將db篩選條件資料塞進map
			Map<String,List<Object>> dbCompareGroupItemMap=new HashMap<String,List<Object>>();
			List<Map<String,Object>> dbCatalogGroupItemList = pfpCatalogGroupItemService.getCatalogAllGroupItem(catalogSeq);
			for (Object object : dbCatalogGroupItemList) {
				Map obj = (Map) object;
				String dbGroupSeq = obj.get("catalog_group_seq").toString();
				String dbField = obj.get("catalog_group_item_field").toString();
				String dbCondition = obj.get("catalog_group_item_condition").toString();
				String dbValue = obj.get("catalog_group_item_value").toString();
				
				if (dbCompareGroupItemMap.get(dbGroupSeq) == null){
					
					List<Object> dbItemArray = new ArrayList<Object>();
					PfpCatalogGroupItem dbPfpCatalogGroupItem = new PfpCatalogGroupItem();
					dbPfpCatalogGroupItem.setCatalogGroupItemField(dbField);
					dbPfpCatalogGroupItem.setCatalogGroupItemCondition(dbCondition);
					dbPfpCatalogGroupItem.setCatalogGroupItemValue(dbValue);
					
					dbItemArray.add(dbPfpCatalogGroupItem);
					dbCompareGroupItemMap.put(dbGroupSeq, dbItemArray);
					
				}else{
					List<Object> dbItemArray =dbCompareGroupItemMap.get(dbGroupSeq);
					PfpCatalogGroupItem dbPfpCatalogGroupItem = new PfpCatalogGroupItem();
					dbPfpCatalogGroupItem.setCatalogGroupItemField(dbField);
					dbPfpCatalogGroupItem.setCatalogGroupItemCondition(dbCondition);
					dbPfpCatalogGroupItem.setCatalogGroupItemValue(dbValue);
					
					dbItemArray.add(dbPfpCatalogGroupItem);
				}
			}
			System.out.println("dbCompareItemMap rawdata : "+dbCompareGroupItemMap);
			
			//db群組篩選條件數量要與新增的數量相同，數量不相同即刪除map裡的db群組，只比較數量相同的群組
			for (Map.Entry<String,List<Object>> entry : dbCompareGroupItemMap.entrySet()) {
				if (filterContentArray.length() != entry.getValue().size()){
					dbCompareGroupItemMap.remove(entry.getKey());
				}
			}
			System.out.println("dbCompareItemMap del: "+dbCompareGroupItemMap);
			
			
			//將新增的篩選條件與db所有篩選條件比對是否一模一樣
			boolean isDuplicate = false;
			for (Map.Entry<String,List<Object>> entry : dbCompareGroupItemMap.entrySet()) {//db所有篩選條件
				if (isDuplicate){
					break;
				}  
				int count =0;
				List<Object> dbCompareGroupItemList = entry.getValue();
				for (int j = 0; j<dbCompareGroupItemList.size(); j++) {
					PfpCatalogGroupItem dbPfpCatalogGroupItem = (PfpCatalogGroupItem) dbCompareGroupItemList.get(j);
					//新增的篩選條件
					for (int i = 0; i<filterContentArray.length(); i++) {
						JSONObject filterContentObj = new JSONObject(filterContentArray.get(i).toString());
						String newField = filterContentObj.getString("field");
						String newCondition = filterContentObj.getString("condition");
						String newValue = filterContentObj.getString("value");
						//將新增的篩選條件與db所有條件相比
						if ( StringUtils.equals(newField, dbPfpCatalogGroupItem.getCatalogGroupItemField()) ){
							if ( StringUtils.equals(newCondition, dbPfpCatalogGroupItem.getCatalogGroupItemCondition()) &&
								 StringUtils.equals(newValue, dbPfpCatalogGroupItem.getCatalogGroupItemValue())	){
								count = count+1;
								if (count==filterContentArray.length()){
									isDuplicate = true;
									break;
								}
							}
						}
					}
				}
			}
			
			
			if (isDuplicate){
				System.out.println("篩選條件重覆: "+isDuplicate);
		        result = "篩選條件重覆";
		        return SUCCESS;
				
			}
		
			
			
			//寫入pfp_catalog_group
			String catalogGroupSeq = sequenceService.getSerialNumberByLength20(EnumSequenceTableName.PFP_CATALOG_GROUP);
			System.out.println("catalogGroupSeq 20 : "+catalogGroupSeq);
			PfpCatalog pfpCatalog= pfpCatalogService.getPfpCatalog(catalogSeq);
			
			PfpCatalogGroup pfpCatalogGroup = new PfpCatalogGroup();
			pfpCatalogGroup.setCatalogGroupSeq(catalogGroupSeq);
			pfpCatalogGroup.setPfpCatalog(pfpCatalog);
			pfpCatalogGroup.setCatalogGroupName(catalogGroupName);
			pfpCatalogGroup.setUpdateDate(new Date());
			pfpCatalogGroup.setCreateDate(new Date());
			pfpCatalogGroupService.saveOrUpdateWithCommit(pfpCatalogGroup);
			
			System.out.println("bessie success1");
			
			//寫入pfp_catalog_group_item
			PfpCatalogGroup pfpCatalogGroupDb =pfpCatalogGroupService.getPfpCatalogGroup(catalogGroupSeq);
			for (int i = 0; i<filterContentArray.length(); i++) {
				JSONObject filterContentObj = new JSONObject(filterContentArray.get(i).toString());
				String newField = filterContentObj.getString("field");
				String newCondition = filterContentObj.getString("condition");
				String newValue = filterContentObj.getString("value");
				
				PfpCatalogGroupItem pfpCatalogGroupItem = new PfpCatalogGroupItem();
				pfpCatalogGroupItem.setPfpCatalogGroup(pfpCatalogGroupDb);
				pfpCatalogGroupItem.setCatalogGroupItemField(newField);
				pfpCatalogGroupItem.setCatalogGroupItemCondition(newCondition);
				pfpCatalogGroupItem.setCatalogGroupItemValue(newValue);
				pfpCatalogGroupItem.setCreateDate(new Date());
				pfpCatalogGroupItem.setUpdateDate(new Date());
				pfpCatalogGroupItemService.saveOrUpdate(pfpCatalogGroupItem);
			}
			
			System.out.println("bessie success2222");
			result = "建立商品組合成功";
			
		} catch (Exception e) {
//			dataMap.put("status", "ERROR");
//			dataMap.put("msg", "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			log.error("error:" + e);
		}
		
		return SUCCESS;
		
	}
	
	
	/**
	 * 查詢建立商品組合篩選條件商品
	 */
	public String queryProdGroupFilterListAjax() {
		try{
			log.info(">>> catalogSeq: " + catalogSeq);
			log.info(">>> filterContentMap: " + filterContentMap);
			
			
			//商品組合ID 的目錄型態
			String catalogType = pfpCatalogService.getPfpCatalog(catalogSeq).getCatalogType() ;
			log.info(">>> catalogType: "+catalogType);
			
			EnumProdGroupFactory enumProdGroupFactory = EnumProdGroupFactory.getCatalogName(catalogType);
			log.info(">>> enumProdGroupFactory: "+enumProdGroupFactory);

			String catalogName = enumProdGroupFactory.getCatalogName();
			log.info(">>> catalogName: "+catalogName);
			
			AProdGroup aProdGroup = prodGroupFactory.getAProdGroupObj(catalogName);
			log.info(">>> aProdGroup: "+aProdGroup);
			
			
			
			JSONArray filterContentArray = new JSONArray(filterContentMap[0].toString());
			List<PfpCatalogGroupItem> pfpCatalogGroupItemList = new ArrayList<PfpCatalogGroupItem>();
			//查詢的篩選條件
			for (int i = 0; i<filterContentArray.length(); i++) {
				JSONObject filterContentObj = new JSONObject(filterContentArray.get(i).toString());
				String newField = filterContentObj.getString("field");
				String newCondition = filterContentObj.getString("condition");
				String newValue = filterContentObj.getString("value");
				
				PfpCatalogGroupItem pfpCatalogGroupItem = new PfpCatalogGroupItem();
				pfpCatalogGroupItem.setCatalogGroupItemField(newField);
				pfpCatalogGroupItem.setCatalogGroupItemCondition(newCondition);
				pfpCatalogGroupItem.setCatalogGroupItemValue(newValue);
				
				pfpCatalogGroupItemList.add(pfpCatalogGroupItem);
			}
			
			//將pfpCatalogGroupItems轉成sql
			String filterSQL = aProdGroup.pfpCatalogGroupItemTofilterSQL(pfpCatalogGroupItemList);
			 
			//撈出該商品組合的list
			rtnProdGroupListMap = aProdGroup.getProdGroupList(catalogSeq, filterSQL);
			log.info(">>> rtnProdGroupListMap : "+rtnProdGroupListMap);
			
			rtnProdGroupListSize = Integer.toString(rtnProdGroupListMap.size());
			log.info(">>> rtnProdGroupListSize length : "+rtnProdGroupListSize);
			
			
			
		} catch (Exception e) {
//			dataMap.put("status", "ERROR");
//			dataMap.put("msg", "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			log.error("error:" + e);
		}

		return SUCCESS;
		
	}
	
	
	
	/**
	 * 撈出商品群組篩選資料
	 */
	public String queryProdGroupFilterItemAjax() {
		try{
			log.info(">>> catalogGroupSeq: " + catalogGroupSeq);
			
			List<PfpCatalogGroupItem> ProdGroupFilterItemList = pfpCatalogGroupItemService.getPfpCatalogGroupItemList(catalogGroupSeq);
			
			result = "建立商品組合成功";
			System.out.println("ProdGroupFilterItemList : "+ProdGroupFilterItemList.toString());
			
			//用MAP格式回傳json
			Map<String, List<Map<String, String>>> catalogGroupItemMapList = new LinkedHashMap<String, List<Map<String, String>>>();
			List<Map<String, String>> catalogGroupItemList = new ArrayList<Map<String, String>>();
			for (PfpCatalogGroupItem pfpCatalogGroupItem : ProdGroupFilterItemList) {
				Map<String, String> catalogGroupItemMap = new LinkedHashMap<String, String>();
				catalogGroupItemMap.put("field", pfpCatalogGroupItem.getCatalogGroupItemField());
				catalogGroupItemMap.put("condition", pfpCatalogGroupItem.getCatalogGroupItemCondition());
				catalogGroupItemMap.put("value", pfpCatalogGroupItem.getCatalogGroupItemValue());
				catalogGroupItemList.add(catalogGroupItemMap);
			}
			catalogGroupItemMapList.put("catalogGroupItemList", catalogGroupItemList);
			JSONObject catalogGroupItemObj = new JSONObject(catalogGroupItemMapList);
			result = catalogGroupItemObj.toString();
			System.out.println(result);
			 
			
			/*用JSON格式回傳json
			JSONArray catalogGroupItemAry = new JSONArray();
			for (PfpCatalogGroupItem pfpCatalogGroupItem : ProdGroupFilterItemList) {
				JSONObject catalogGroupItemObj = new JSONObject();
				catalogGroupItemObj.put("field", pfpCatalogGroupItem.getCatalogGroupItemField());
				catalogGroupItemObj.put("condition", pfpCatalogGroupItem.getCatalogGroupItemCondition());
				catalogGroupItemObj.put("value", pfpCatalogGroupItem.getCatalogGroupItemValue());
				catalogGroupItemAry.put(catalogGroupItemObj);
			}
			JSONObject returnObj = new JSONObject();
			returnObj.put("catalogGroupItemList", catalogGroupItemAry);
			result = returnObj.toString();
			System.out.println("bessie sucess~~~");
			 */
			
			
		} catch (Exception e) {
//			dataMap.put("status", "ERROR");
//			dataMap.put("msg", "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			log.error("error:" + e);
		}
		
		return SUCCESS;
		
	}
	
	
	
	/**
	 * 刪除商品組合群組
	 */
	public String deleteCatalogGroupAjax() {
		try{
			log.info(">>> catalogGroupSeq: " + catalogGroupSeq);
			
			pfpCatalogGroupItemService.deleteCatalogGroupItem(catalogGroupSeq);
			
			pfpCatalogGroupService.deleteCatalogGroup(catalogGroupSeq);	
			
			result = "刪除商品組合成功";
			
			
			
			
//			
//			List<PfpCatalogGroupItem> ProdGroupFilterItemList = pfpCatalogGroupItemService.getPfpCatalogGroupItemList(catalogGroupSeq);
//			
//			result = "建立商品組合成功";
//			System.out.println("ProdGroupFilterItemList : "+ProdGroupFilterItemList.toString());
//			
//			//用MAP格式回傳json
//			Map<String, List<Map<String, String>>> catalogGroupItemMapList = new LinkedHashMap<String, List<Map<String, String>>>();
//			List<Map<String, String>> catalogGroupItemList = new ArrayList<Map<String, String>>();
//			for (PfpCatalogGroupItem pfpCatalogGroupItem : ProdGroupFilterItemList) {
//				Map<String, String> catalogGroupItemMap = new LinkedHashMap<String, String>();
//				catalogGroupItemMap.put("field", pfpCatalogGroupItem.getCatalogGroupItemField());
//				catalogGroupItemMap.put("condition", pfpCatalogGroupItem.getCatalogGroupItemCondition());
//				catalogGroupItemMap.put("value", pfpCatalogGroupItem.getCatalogGroupItemValue());
//				catalogGroupItemList.add(catalogGroupItemMap);
//			}
//			catalogGroupItemMapList.put("catalogGroupItemList", catalogGroupItemList);
//			JSONObject catalogGroupItemObj = new JSONObject(catalogGroupItemMapList);
//			result = catalogGroupItemObj.toString();
//			System.out.println(result);
//			 
//			
//			/*用JSON格式回傳json
//			JSONArray catalogGroupItemAry = new JSONArray();
//			for (PfpCatalogGroupItem pfpCatalogGroupItem : ProdGroupFilterItemList) {
//				JSONObject catalogGroupItemObj = new JSONObject();
//				catalogGroupItemObj.put("field", pfpCatalogGroupItem.getCatalogGroupItemField());
//				catalogGroupItemObj.put("condition", pfpCatalogGroupItem.getCatalogGroupItemCondition());
//				catalogGroupItemObj.put("value", pfpCatalogGroupItem.getCatalogGroupItemValue());
//				catalogGroupItemAry.put(catalogGroupItemObj);
//			}
//			JSONObject returnObj = new JSONObject();
//			returnObj.put("catalogGroupItemList", catalogGroupItemAry);
//			result = returnObj.toString();
//			System.out.println("bessie sucess~~~");
//			 */
			
			
		} catch (Exception e) {
//			dataMap.put("status", "ERROR");
//			dataMap.put("msg", "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			result = "刪除商品組合失敗";
			log.error("error:" + e);
		}
		
		return SUCCESS;
		
	}
	
	
	
	
	
	
	
	
	
	
	public String getCatalogSeq() {
		return catalogSeq;
	}

	public void setCatalogSeq(String catalogSeq) {
		this.catalogSeq = catalogSeq;
	}

	public String getProdGroupFilterContent() {
		return prodGroupFilterContent;
	}

	public void setCatalogGroupSeq(String catalogGroupSeq) {
		this.catalogGroupSeq = catalogGroupSeq;
	}

	public void setPfpCatalogGroupItemService(IPfpCatalogGroupItemService pfpCatalogGroupItemService) {
		this.pfpCatalogGroupItemService = pfpCatalogGroupItemService;
	}


	public String[] getFilterContentMap() {
		return filterContentMap;
	}

	public void setFilterContentMap(String[] filterContentMap) {
		this.filterContentMap = filterContentMap;
	}

	public String getResult() {
		return result;
	}

	public void setSequenceService(SequenceService sequenceService) {
		this.sequenceService = sequenceService;
	}

	public void setPfpCatalogService(IPfpCatalogService pfpCatalogService) {
		this.pfpCatalogService = pfpCatalogService;
	}

	public void setCatalogGroupName(String catalogGroupName) {
		this.catalogGroupName = catalogGroupName;
	}

	public void setPfpCatalogGroupService(IPfpCatalogGroupService pfpCatalogGroupService) {
		this.pfpCatalogGroupService = pfpCatalogGroupService;
	}

	public void setProdGroupFactory(ProdGroupFactory prodGroupFactory) {
		this.prodGroupFactory = prodGroupFactory;
	}

	public List<Map<String, Object>> getRtnProdGroupListMap() {
		return rtnProdGroupListMap;
	}

	public String getRtnProdGroupListSize() {
		return rtnProdGroupListSize;
	}

	
	
	
	
	
	
	

}
