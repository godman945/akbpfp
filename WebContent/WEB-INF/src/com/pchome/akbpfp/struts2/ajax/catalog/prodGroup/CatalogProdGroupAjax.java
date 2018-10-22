package com.pchome.akbpfp.struts2.ajax.catalog.prodGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.pchome.akbpfp.catalog.prodGroup.factory.AProdGroup;
import com.pchome.akbpfp.catalog.prodGroup.factory.ProdGroupFactory;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.service.catalog.prodGroup.IPfpCatalogGroupItemService;
import com.pchome.akbpfp.db.service.catalog.prodGroup.IPfpCatalogGroupService;
import com.pchome.akbpfp.db.service.sequence.SequenceService;
import com.pchome.akbpfp.db.vo.catalog.prodGroup.ProdGroupConditionVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.catalog.prodGroup.EnumProdGroupFactory;
import com.pchome.enumerate.sequence.EnumSequenceTableName;

public class CatalogProdGroupAjax extends BaseCookieAction {

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
	private PfpCatalog pfpCatalog;

	private String[] filterContentMap;
	private String result;
	private Map<String, Object> resultMap;

	private int currentPage = 1; // 第幾頁(初始預設第1頁)
	private int pageSizeSelected = 10; // 每頁筆數(初始預設每頁10筆)
	private int totalCount = 0; // 資料總筆數
	private int pageCount = 0; // 總頁數
	private List<Object> prodList;

	/**
	 * 取得商品組合清單
	 */
	public String queryProdGroupListAjax() {
		try {
			log.info(">>> catalogGroupSeq: " + catalogGroupSeq);
			log.info(">>> currentPage: " + currentPage);
			log.info(">>> pageSizeSelected: " + pageSizeSelected);

			log.info(">>> totalCount: " + totalCount);
			log.info(">>> pageCount: " + pageCount);

			resultMap = new HashMap<String, Object>();

			PfpCatalogGroup pfpCatalogGroup = pfpCatalogGroupService.get(catalogGroupSeq);
			if (pfpCatalogGroup == null) {
				resultMap = returnErrorMsgMap("商品組合編號不正確");
				return SUCCESS;
			}

			String catalogCustomerInfoId = pfpCatalogGroup.getPfpCatalog().getPfpCustomerInfoId();
			if (!StringUtils.equals(super.getCustomer_info_id(), catalogCustomerInfoId)) {
				resultMap = returnErrorMsgMap("僅能查看有權限之商品組合清單");
				return SUCCESS;
			}

			// 商品組合ID 的目錄型態
			String catalogType = pfpCatalogGroup.getPfpCatalog().getCatalogType();
			EnumProdGroupFactory enumProdGroupFactory = EnumProdGroupFactory.getCatalogName(catalogType);
			log.info(">>> enumProdGroupFactory: " + enumProdGroupFactory);
			if (enumProdGroupFactory == null) {
				resultMap = returnErrorMsgMap("目錄型態不正確");
				return SUCCESS;
			}

			catalogSeq = pfpCatalogGroup.getPfpCatalog().getCatalogSeq();
			// 取得商品目錄類型
			pfpCatalog = pfpCatalogService.getPfpCatalog(catalogSeq);
			enumProdGroupFactory = EnumProdGroupFactory.getCatalogName(pfpCatalog.getCatalogType());
			String catalogFactoryName = enumProdGroupFactory.getCatalogName();
			AProdGroup aProdGroup = prodGroupFactory.getAProdGroupObj(catalogFactoryName);
			// 商品群組條件
			List<PfpCatalogGroupItem> pfpCatalogGroupItems = aProdGroup.getPfpCatalogGroupItemList(catalogGroupSeq);
			// 將pfpCatalogGroupItems轉成sql
			String filterSQL = aProdGroup.pfpCatalogGroupItemTofilterSQL(pfpCatalogGroupItems);

			// 取得商品組合清單
			ProdGroupConditionVO prodGroupConditionVO = new ProdGroupConditionVO();
			prodGroupConditionVO.setCatalogSeq(catalogSeq);
			prodGroupConditionVO.setPfpCustomerInfoId(super.getCustomer_info_id());
			prodGroupConditionVO.setPage(currentPage);
			prodGroupConditionVO.setPageSize(pageSizeSelected);
			prodGroupConditionVO.setFilterSQL(filterSQL);

			prodList = aProdGroup.getProdGroupList(prodGroupConditionVO);
			if (prodList.size() <= 0) {
				resultMap = returnErrorMsgMap("沒有商品清單資料");
				return SUCCESS;
			}

			// 商品清單資料總筆數
			totalCount = Integer.valueOf(aProdGroup.getProdGroupCount(catalogSeq, filterSQL));
			// 總頁數
			pageCount = (int) Math.ceil((float) totalCount / pageSizeSelected);

			resultMap.put("currentPage", currentPage);
			resultMap.put("pageSizeSelected", pageSizeSelected);
			resultMap.put("totalCount", totalCount);
			resultMap.put("pageCount", pageCount);
			resultMap.put("prodList", prodList);
			resultMap.put("status", "SUCCESS");

		} catch (Exception e) {
			log.error("error:" + e);
			resultMap = returnErrorMsgMap("系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			return SUCCESS;
		}

		return SUCCESS;
	}

	/**
	 * 回傳錯誤訊息
	 */
	public Map<String, Object> returnErrorMsgMap(String errorMsg) {

		Map<String, Object> errorMsgMap = new HashMap<String, Object>();
		errorMsgMap.put("currentPage", 1);
		errorMsgMap.put("pageCount", 1);
		errorMsgMap.put("totalCount", 0);
		errorMsgMap.put("status", "ERROR");
		errorMsgMap.put("msg", errorMsg);

		return errorMsgMap;
	}

	

	/**
	 * 建立目錄商品組合
	 */
	public String addCatalogProdGroupAjax() {
		try {
			log.info(">>> catalogSeq: " + catalogSeq);
			log.info(">>> catalogGroupName: " + catalogGroupName);
			log.info(">>> filterContentMap: " + filterContentMap);

			resultMap = new HashMap<String, Object>();

			if( catalogGroupName.trim().length() > 20){
				resultMap.put("msg", "組合名稱最多20字");
				resultMap.put("status", "ERROR");
				return SUCCESS;
			}
			
			if( StringUtils.isBlank(catalogGroupName)){
				resultMap.put("msg", "組合名稱不得為空");
				resultMap.put("status", "ERROR");
				return SUCCESS;
			}
			
			List<PfpCatalogGroup> pfpCatalogGroupList= pfpCatalogGroupService.getPfpCatalogGroupList(catalogSeq);
			for (PfpCatalogGroup pfpCatalogGroup : pfpCatalogGroupList) {
				if( StringUtils.equals(catalogGroupName.trim(), pfpCatalogGroup.getCatalogGroupName().trim()) ){
					resultMap.put("msg", "組合名稱重覆");
					resultMap.put("status", "ERROR");
					return SUCCESS;
				}
			}
			
			
			// 新增的篩選條件
			JSONArray filterContentArray = new JSONArray(filterContentMap[0].toString());
			
			// 將db篩選條件資料塞進map
			Map<String, List<Object>> dbCompareGroupItemMap = new HashMap<String, List<Object>>();
			List<Map<String, Object>> dbCatalogGroupItemList = pfpCatalogGroupItemService
					.getCatalogAllGroupItem(catalogSeq);
			for (Object object : dbCatalogGroupItemList) {
				Map obj = (Map) object;
				String dbGroupSeq = obj.get("catalog_group_seq").toString();
				String dbField = obj.get("catalog_group_item_field").toString();
				String dbCondition = obj.get("catalog_group_item_condition").toString();
				String dbValue = obj.get("catalog_group_item_value").toString();

				if (dbCompareGroupItemMap.get(dbGroupSeq) == null) {

					List<Object> dbItemArray = new ArrayList<Object>();
					PfpCatalogGroupItem dbPfpCatalogGroupItem = new PfpCatalogGroupItem();
					dbPfpCatalogGroupItem.setCatalogGroupItemField(dbField);
					dbPfpCatalogGroupItem.setCatalogGroupItemCondition(dbCondition);
					dbPfpCatalogGroupItem.setCatalogGroupItemValue(dbValue);

					dbItemArray.add(dbPfpCatalogGroupItem);
					dbCompareGroupItemMap.put(dbGroupSeq, dbItemArray);

				} else {
					List<Object> dbItemArray = dbCompareGroupItemMap.get(dbGroupSeq);
					PfpCatalogGroupItem dbPfpCatalogGroupItem = new PfpCatalogGroupItem();
					dbPfpCatalogGroupItem.setCatalogGroupItemField(dbField);
					dbPfpCatalogGroupItem.setCatalogGroupItemCondition(dbCondition);
					dbPfpCatalogGroupItem.setCatalogGroupItemValue(dbValue);

					dbItemArray.add(dbPfpCatalogGroupItem);
				}
			}
			
			Map<String, List<Object>> dbCompareGroupItemMapNew = new HashMap<String, List<Object>>();
			// db群組篩選條件數量要與新增的數量相同才比較
			for (Map.Entry<String, List<Object>> entry : dbCompareGroupItemMap.entrySet()) {
				if (filterContentArray.length() == entry.getValue().size()) {
					dbCompareGroupItemMapNew.put(entry.getKey(), entry.getValue());
				}
			}

			// 將新增的篩選條件與db所有篩選條件比對是否一模一樣
			boolean isDuplicate = false;
			for (Map.Entry<String, List<Object>> entry : dbCompareGroupItemMapNew.entrySet()) {// db所有篩選條件
				if (isDuplicate) {
					break;
				}
				int count = 0;
				List<Object> dbCompareGroupItemList = entry.getValue();
				for (int j = 0; j < dbCompareGroupItemList.size(); j++) {
					PfpCatalogGroupItem dbPfpCatalogGroupItem = (PfpCatalogGroupItem) dbCompareGroupItemList.get(j);
					// 新增的篩選條件
					for (int i = 0; i < filterContentArray.length(); i++) {
						JSONObject filterContentObj = new JSONObject(filterContentArray.get(i).toString());
						String newField = filterContentObj.getString("field");
						String newCondition = filterContentObj.getString("condition");
						String newValue = filterContentObj.getString("value");
						// 將新增的篩選條件與db所有條件相比
						if (StringUtils.equals(newField, dbPfpCatalogGroupItem.getCatalogGroupItemField())) {
							if (StringUtils.equals(newCondition, dbPfpCatalogGroupItem.getCatalogGroupItemCondition())
									&& StringUtils.equals(newValue, dbPfpCatalogGroupItem.getCatalogGroupItemValue())) {
								count = count + 1;
								if (count == filterContentArray.length()) {
									isDuplicate = true;
									break;
								}
							}
						}
					}
				}
			}

			if (isDuplicate) {
				resultMap.put("msg", "篩選條件重覆");
				resultMap.put("status", "ERROR");
				return SUCCESS;
			}

			// 寫入pfp_catalog_group
			String catalogGroupSeq = sequenceService.getSerialNumberByLength20(EnumSequenceTableName.PFP_CATALOG_GROUP);
			PfpCatalog pfpCatalog = pfpCatalogService.getPfpCatalog(catalogSeq);

			PfpCatalogGroup pfpCatalogGroup = new PfpCatalogGroup();
			pfpCatalogGroup.setCatalogGroupSeq(catalogGroupSeq);
			pfpCatalogGroup.setPfpCatalog(pfpCatalog);
			pfpCatalogGroup.setCatalogGroupName(catalogGroupName);
			pfpCatalogGroup.setUpdateDate(new Date());
			pfpCatalogGroup.setCreateDate(new Date());
			pfpCatalogGroupService.saveOrUpdateWithCommit(pfpCatalogGroup);


			// 寫入pfp_catalog_group_item
			PfpCatalogGroup pfpCatalogGroupDb = pfpCatalogGroupService.getPfpCatalogGroup(catalogGroupSeq);
			for (int i = 0; i < filterContentArray.length(); i++) {
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

			resultMap.put("msg", "建立商品組合成功");
			resultMap.put("status", "SUCCESS");

		} catch (Exception e) {
			log.error("error:" + e);
			resultMap.put("status", "ERROR");
			resultMap.put("msg", "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			return SUCCESS;
		}

		return SUCCESS;

	}

	/**
	 * 查詢建立商品組合篩選條件商品
	 */
	public String queryProdGroupFilterListAjax() {
		try {
			log.info(">>> catalogSeq: " + catalogSeq);
			log.info(">>> currentPage: " + currentPage);
			log.info(">>> pageSizeSelected: " + pageSizeSelected);
			log.info(">>> filterContentMap: " + filterContentMap);

			resultMap = new HashMap<String, Object>();
			
			// 商品組合ID 的目錄型態
			String catalogType = pfpCatalogService.getPfpCatalog(catalogSeq).getCatalogType();
			log.info(">>> catalogType: " + catalogType);

			EnumProdGroupFactory enumProdGroupFactory = EnumProdGroupFactory.getCatalogName(catalogType);
			log.info(">>> enumProdGroupFactory: " + enumProdGroupFactory);

			String catalogName = enumProdGroupFactory.getCatalogName();
			log.info(">>> catalogName: " + catalogName);

			AProdGroup aProdGroup = prodGroupFactory.getAProdGroupObj(catalogName);
			log.info(">>> aProdGroup: " + aProdGroup);

			JSONArray filterContentArray = new JSONArray(filterContentMap[0].toString());
			if (filterContentArray.length()<=0){
				resultMap = returnErrorMsgMap("請填選商品組合篩選條件");
				return SUCCESS;
			}
			
			// 檢查篩選條件value是否符合型態
			for (int i = 0; i < filterContentArray.length(); i++) {
				JSONObject filterContentObj = new JSONObject(filterContentArray.get(i).toString());
				String newField = filterContentObj.getString("field");
				String newCondition = filterContentObj.getString("condition");
				String newValue = filterContentObj.getString("value");
				
				if (StringUtils.equals("ec_price", newField)){
					boolean isIntegerFlag = isInteger(newValue);
					if (!isIntegerFlag){
						resultMap = returnErrorMsgMap("原價請填寫數字");
						return SUCCESS;
					}
				}
				
				if (StringUtils.equals("ec_discount_price", newField)){
					boolean isIntegerFlag = isInteger(newValue);
					if (!isIntegerFlag){
						resultMap = returnErrorMsgMap("特價請填寫數字");
						return SUCCESS;
					}
				}
			}
			
			
			List<PfpCatalogGroupItem> pfpCatalogGroupItemList = new ArrayList<PfpCatalogGroupItem>();
			// 查詢的篩選條件
			for (int i = 0; i < filterContentArray.length(); i++) {
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

			// 將pfpCatalogGroupItems轉成sql
			String filterSQL = aProdGroup.pfpCatalogGroupItemTofilterSQL(pfpCatalogGroupItemList);

			// 撈出該商品組合篩選條件的list商品
			ProdGroupConditionVO prodGroupConditionVO = new ProdGroupConditionVO();
			prodGroupConditionVO.setCatalogSeq(catalogSeq);
			prodGroupConditionVO.setPfpCustomerInfoId(super.getCustomer_info_id());
			prodGroupConditionVO.setPage(currentPage);
			prodGroupConditionVO.setPageSize(pageSizeSelected);
			prodGroupConditionVO.setFilterSQL(filterSQL);

			prodList = aProdGroup.getProdGroupList(prodGroupConditionVO);
			
			if (prodList.size() <= 0) {
				resultMap = returnErrorMsgMap("沒有商品清單資料");
				return SUCCESS;
			}

			// 商品清單資料總筆數
			totalCount = Integer.valueOf(aProdGroup.getProdGroupCount(catalogSeq, filterSQL));

			// 總頁數
			pageCount = (int) Math.ceil((float) totalCount / pageSizeSelected);

			resultMap.put("currentPage", currentPage);
			resultMap.put("pageSizeSelected", pageSizeSelected);
			resultMap.put("totalCount", totalCount);
			resultMap.put("pageCount", pageCount);
			resultMap.put("prodList", prodList);
			resultMap.put("status", "SUCCESS");

		} catch (Exception e) {
			log.error("error:" + e);
			resultMap = returnErrorMsgMap("系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			return SUCCESS;
		}

		return SUCCESS;
	}


	public boolean isInteger(String value) {
	    Pattern pattern = Pattern.compile("\\d+$");
	    return pattern.matcher(value).matches();
	}
	

	/**
	 * 刪除商品組合群組
	 */
	public String deleteCatalogGroupAjax() {
		try {
			log.info(">>> catalogGroupSeq: " + catalogGroupSeq);

			resultMap = new HashMap<String, Object>();

			PfpCatalogGroup pfpCatalogGroup = pfpCatalogGroupService.get(catalogGroupSeq);

			String catalogCustomerInfoId = pfpCatalogGroup.getPfpCatalog().getPfpCustomerInfoId();

			if (!StringUtils.equals(super.getCustomer_info_id(), catalogCustomerInfoId)) {
				resultMap.put("status", "ERROR");
				resultMap.put("msg", "僅能刪除有權限之商品組合");
				return SUCCESS;
			}

			pfpCatalogGroupItemService.deleteCatalogGroupItem(catalogGroupSeq);
			pfpCatalogGroupService.deleteCatalogGroup(catalogGroupSeq);

			resultMap.put("status", "SUCCESS");
			resultMap.put("msg", "刪除商品組合成功");

		} catch (Exception e) {
			log.error("error:" + e);
			resultMap.put("status", "ERROR");
			resultMap.put("msg", "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			return SUCCESS;
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

	public Map<String, Object> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSizeSelected() {
		return pageSizeSelected;
	}

	public void setPageSizeSelected(int pageSizeSelected) {
		this.pageSizeSelected = pageSizeSelected;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public List<Object> getProdList() {
		return prodList;
	}

}
