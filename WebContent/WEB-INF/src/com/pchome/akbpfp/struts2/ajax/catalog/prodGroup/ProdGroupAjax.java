package com.pchome.akbpfp.struts2.ajax.catalog.prodGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.akbpfp.db.pojo.PfpAdVideoSource;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.service.catalog.prodGroup.IPfpCatalogGroupItemService;
import com.pchome.akbpfp.db.vo.catalog.prodList.PfpCatalogProdEcVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.ad.EnumAdDetail;
import com.pchome.enumerate.ad.EnumAdVideoDownloadStatus;
import com.pchome.enumerate.ad.EnumAdVideoSizePoolType;
import com.pchome.enumerate.catalog.prodGroup.EnumProdGroupCondition;
import com.pchome.enumerate.catalog.prodGroup.EnumProdGroupField;
import com.pchome.enumerate.sequence.EnumSequenceTableName;

public class ProdGroupAjax  extends BaseCookieAction{

	private static final long serialVersionUID = 1L;
	
	private String catalogSeq;
	private String catalogGroupSeq;
	private String prodGroupFilterContent;
	private IPfpCatalogGroupItemService pfpCatalogGroupItemService;
	
	private String[] filterContentMap;
	

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
			}
		
			System.out.println("bessie success");
			
			
			
			
			
			
			
			
		} catch (Exception e) {
//			dataMap.put("status", "ERROR");
//			dataMap.put("msg", "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
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
	

}
