package com.pchome.akbpfp.struts2.action.catalog.prodGroup;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.catalog.prodGroup.factory.AProdGroup;
import com.pchome.akbpfp.catalog.prodGroup.factory.ProdGroupFactory;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.service.catalog.IPfpCatalogService;
import com.pchome.akbpfp.db.service.catalog.prodGroup.IPfpCatalogGroupItemService;
import com.pchome.akbpfp.db.service.catalog.prodGroup.IPfpCatalogGroupService;
import com.pchome.akbpfp.db.service.customerInfo.IPfpCustomerInfoService;
import com.pchome.akbpfp.db.vo.catalog.prodGroup.ProdGroupConditionVO;
import com.pchome.akbpfp.db.vo.catalog.prodGroup.pfpCatalogGroupVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.catalog.prodGroup.EnumProdGroupFactory;
import com.pchome.enumerate.catalogprod.EnumEcProdGroupFilterContentType;
import com.pchome.enumerate.catalogprod.EnumEcStockStatusType;
import com.pchome.enumerate.catalogprod.EnumEcUseStatusType;

public class CatalogProdGroupAction  extends BaseCookieAction{

	private static final long serialVersionUID = 1L;
	
	private String catalogSeq;
	private PfpCatalog pfpCatalog;
	private IPfpCatalogService pfpCatalogService;
	private IPfpCatalogGroupService pfpCatalogGroupService;
	private IPfpCustomerInfoService pfpCustomerInfoService;
	private IPfpCatalogGroupItemService pfpCatalogGroupItemService;
	private ProdGroupFactory prodGroupFactory;
	//商品群組首頁
	private String catalogProdAllNum;
	private List<pfpCatalogGroupVO> pfpCatalogGroupVOList; 
	private List<PfpCatalog> pfpCatalogList;
	private String customerInfoTitle;
	
	
	//商品群組清單明細
	private String returnFtlName;
	private String catalogGroupSeq;
	private String catalogGroupName;
	private String returnMsg;
	private int currentPage = 1;      //第幾頁(初始預設第1頁)
	private int pageSizeSelected = 10; //每頁筆數(初始預設每頁10筆)
	private int totalCount = 0; //資料總筆數
	private int pageCount = 0; //總頁數
	private List<Object> prodList; 
	private List<PfpCatalogGroupItem> ProdGroupFilterItemList;
	
	
	public String queryCatalogGroup(){
		try{
			log.info(">>> catalogSeq: " + catalogSeq);
			
			//user catalog清單
			pfpCatalogList = pfpCatalogService.getPfpCatalogList(super.getCustomer_info_id());
			customerInfoTitle = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()).getCustomerInfoTitle();
			
			//確認有無查看商品組合的權限
			List<PfpCatalog> privilege = pfpCatalogService.checkPfpCatalogPrivilege(super.getCustomer_info_id(),catalogSeq);
			if (privilege.size()<= 0){
				returnMsg = "僅能查看有權限之商品組合";
				return ERROR;
			}
			
			//全部商品組合數量
			pfpCatalog =  pfpCatalogService.getPfpCatalog(catalogSeq);
			EnumProdGroupFactory enumProdGroupFactory = EnumProdGroupFactory.getCatalogName(pfpCatalog.getCatalogType());
			String catalogFactoryName = enumProdGroupFactory.getCatalogName();
			AProdGroup aProdGroup = prodGroupFactory.getAProdGroupObj(catalogFactoryName);
			
			//商品組合群組列表
			List<PfpCatalogGroup> pfpCatalogGroupList= pfpCatalogGroupService.getPfpCatalogGroupList(catalogSeq);
			log.info(">>> pfpCatalogGroupList size: " + pfpCatalogGroupList.size());
			
			
			pfpCatalogGroupVOList = new ArrayList<pfpCatalogGroupVO>();
			for (PfpCatalogGroup pfpCatalogGroup : pfpCatalogGroupList) {
				
				pfpCatalogGroupVO pfpCatalogGroupVO = new pfpCatalogGroupVO();
				
				//商品群組條件
				List<PfpCatalogGroupItem> pfpCatalogGroupItems = aProdGroup.getPfpCatalogGroupItemList(pfpCatalogGroup.getCatalogGroupSeq());
				//將pfpCatalogGroupItems轉成sql
				String filterSQL = aProdGroup.pfpCatalogGroupItemTofilterSQL(pfpCatalogGroupItems);
				//撈出該商品組合的總數量
				String prodGroupCount = aProdGroup.getProdGroupCount(catalogSeq,filterSQL);
				
				// 撈出商品組合篩選條件資料
				String filterContent = queryProdGroupFilterContent(pfpCatalogGroupItems);
				
				pfpCatalogGroupVO.setCatalogGroupSeq(pfpCatalogGroup.getCatalogGroupSeq());
				pfpCatalogGroupVO.setCatalogGroupName(pfpCatalogGroup.getCatalogGroupName());
				pfpCatalogGroupVO.setCatalogProdNum(prodGroupCount);
				pfpCatalogGroupVO.setFilterContent(filterContent);
				pfpCatalogGroupVOList.add(pfpCatalogGroupVO);
			}
			
		} catch (Exception e) {
			log.error("error:" + e);
			returnMsg = "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。";
			return ERROR;
		}
		
		return SUCCESS;
	}


	/**
	 * 撈出商品組合篩選條件資料
	 */
	public String queryProdGroupFilterContent(List<PfpCatalogGroupItem> pfpCatalogGroupItems) throws Exception {
		StringBuffer filterContent = new StringBuffer();
		String field;
		String condition;
		String value;
		for (PfpCatalogGroupItem pfpCatalogGroupItem : pfpCatalogGroupItems) {
			field = pfpCatalogGroupItem.getCatalogGroupItemField();
			condition = pfpCatalogGroupItem.getCatalogGroupItemCondition();
			value = pfpCatalogGroupItem.getCatalogGroupItemValue();
			String fieldCondition = field + "_" + condition;

			// 供應情況(0:無庫存, 1:有庫存, 2:預購, 3:停售)
			if (StringUtils.equals("ec_stock_status", field)) {
				for (EnumEcStockStatusType ecStockStatusType : EnumEcStockStatusType.values()) {
					if (StringUtils.equals(ecStockStatusType.getType(), value)) {
						value = ecStockStatusType.getChName();
						break;
					}
				}
			}

			// 使用狀況(0:全新, 1:二手, 2:福利品)
			if (StringUtils.equals("ec_use_status", field)) {
				for (EnumEcUseStatusType ecUseStatusType : EnumEcUseStatusType.values()) {
					if (StringUtils.equals(ecUseStatusType.getType(), value)) {
						value = ecUseStatusType.getChName();
						break;
					}
				}
			}

			for (EnumEcProdGroupFilterContentType ecProdGroupFilterContentType : EnumEcProdGroupFilterContentType
					.values()) {
				if (StringUtils.equals(ecProdGroupFilterContentType.getFieldCondition(), fieldCondition)) {
					field = ecProdGroupFilterContentType.getFieldDesc();
					condition = ecProdGroupFilterContentType.getConditionDesc();
					break;
				}
			}

			filterContent.append("<span>").append(field).append(" : <b><em>");
			filterContent.append(condition).append("</em>");
			filterContent.append(value).append("</b>").append("</span><br>");
		}
		return filterContent.toString();
	}

	
	public String queryProdGroupList(){
		try{
			log.info(">>> catalogGroupSeq: " + catalogGroupSeq);
			log.info(">>> currentPage: " + currentPage);
			log.info(">>> pageSizeSelected: " + pageSizeSelected);
			log.info(">>> totalCount: " + totalCount);
			log.info(">>> pageCount: " + pageCount);
			
			pfpCatalogList = pfpCatalogService.getPfpCatalogList(super.getCustomer_info_id());
			customerInfoTitle = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()).getCustomerInfoTitle();
			
			PfpCatalogGroup pfpCatalogGroup = pfpCatalogGroupService.get(catalogGroupSeq);
			if(pfpCatalogGroup == null){
				returnMsg = "商品組合編號不正確";
				return ERROR;
			}
			
			String catalogCustomerInfoId = pfpCatalogGroup.getPfpCatalog().getPfpCustomerInfoId();
			if(!StringUtils.equals(super.getCustomer_info_id(), catalogCustomerInfoId )){
				returnMsg = "僅能查看有權限之商品組合清單";
				return ERROR;
			}
			
			//商品組合ID 的目錄型態
			String catalogType = pfpCatalogGroup.getPfpCatalog().getCatalogType();
			EnumProdGroupFactory enumProdGroupFactory = EnumProdGroupFactory.getCatalogName(catalogType);
			log.info(">>> enumProdGroupFactory: " + enumProdGroupFactory);
			if (enumProdGroupFactory == null) {
				returnMsg = "目錄型態不正確";
				return ERROR;
			}
			
			catalogSeq = pfpCatalogGroup.getPfpCatalog().getCatalogSeq();
			//取得商品目錄類型
			pfpCatalog =  pfpCatalogService.getPfpCatalog(catalogSeq);
			enumProdGroupFactory = EnumProdGroupFactory.getCatalogName(pfpCatalog.getCatalogType());
			String catalogFactoryName = enumProdGroupFactory.getCatalogName();
			AProdGroup aProdGroup = prodGroupFactory.getAProdGroupObj(catalogFactoryName);
			//商品群組條件
			List<PfpCatalogGroupItem> pfpCatalogGroupItems = aProdGroup.getPfpCatalogGroupItemList(catalogGroupSeq);
			//將pfpCatalogGroupItems轉成sql
			String filterSQL = aProdGroup.pfpCatalogGroupItemTofilterSQL(pfpCatalogGroupItems);
			log.info(">>> filterSQL: " + filterSQL);
			
			//取得商品組合清單
			ProdGroupConditionVO prodGroupConditionVO =  new ProdGroupConditionVO();
			prodGroupConditionVO.setCatalogSeq(catalogSeq);
			prodGroupConditionVO.setPfpCustomerInfoId(super.getCustomer_info_id());
			prodGroupConditionVO.setPage(currentPage);
			prodGroupConditionVO.setPageSize(pageSizeSelected);
			prodGroupConditionVO.setFilterSQL(filterSQL);

			prodList = aProdGroup.getProdGroupList(prodGroupConditionVO);
			if(prodList.size() <= 0){
				returnMsg = "沒有商品清單資料";
				return ERROR;
			}
			
			//商品清單資料總筆數
			totalCount =  Integer.valueOf(aProdGroup.getProdGroupCount(catalogSeq,filterSQL));
			
			//總頁數
			pageCount = (int)Math.ceil((float)totalCount / pageSizeSelected);
			
			//依據商品目錄型態回傳各別ftl(一般購物類、訂房住宿類、交通航班類、房產租售類)
			returnFtlName = enumProdGroupFactory.getCatalogName();
			
			catalogGroupName = pfpCatalogGroupService.get(catalogGroupSeq).getCatalogGroupName();
			
		} catch (Exception e) {
			log.error("error:" + e);
			returnMsg = "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。";
			return ERROR;
		}
		
		return returnFtlName;
	}
	
	
	/**
	 * 新增商品組合篩選資料
	 */
	public String queryProdGroupFilterProdList(){
		try{
			log.info(">>> catalogSeq: " + catalogSeq);
			log.info(">>> catalogGroupSeq: " + catalogGroupSeq);
			log.info(">>> currentPage: " + currentPage);
			log.info(">>> pageSizeSelected: " + pageSizeSelected);
			
			pfpCatalogList = pfpCatalogService.getPfpCatalogList(super.getCustomer_info_id());
			customerInfoTitle = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()).getCustomerInfoTitle();

			//商品組合ID 的目錄型態
			pfpCatalog =  pfpCatalogService.getPfpCatalog(catalogSeq);
			EnumProdGroupFactory enumProdGroupFactory = EnumProdGroupFactory.getCatalogName(pfpCatalog.getCatalogType());
			log.info(">>> enumProdGroupFactory: " + enumProdGroupFactory);
			if (enumProdGroupFactory == null) {
				returnMsg = "目錄型態不正確";
				return ERROR;
			}
			
			// 商品組合ID 的目錄型態
			String catalogType = pfpCatalogService.getPfpCatalog(catalogSeq).getCatalogType();
			log.info(">>> catalogType: " + catalogType);

			enumProdGroupFactory = EnumProdGroupFactory.getCatalogName(catalogType);
			log.info(">>> enumProdGroupFactory: " + enumProdGroupFactory);

			String catalogName = enumProdGroupFactory.getCatalogName();
			log.info(">>> catalogName: " + catalogName);

			AProdGroup aProdGroup = prodGroupFactory.getAProdGroupObj(catalogName);
			
			// 撈出該商品組合篩選條件的list商品
			ProdGroupConditionVO prodGroupConditionVO = new ProdGroupConditionVO();
			prodGroupConditionVO.setCatalogSeq(catalogSeq);
			prodGroupConditionVO.setPfpCustomerInfoId(super.getCustomer_info_id());
			prodGroupConditionVO.setPage(currentPage);
			prodGroupConditionVO.setPageSize(pageSizeSelected);
			prodGroupConditionVO.setFilterSQL("");
			// 商品清單資料
			prodList = aProdGroup.getProdGroupList(prodGroupConditionVO);
			// 商品清單資料總筆數
			totalCount = Integer.valueOf(aProdGroup.getProdGroupCount(catalogSeq, prodGroupConditionVO.getFilterSQL()));
			// 總頁數
			pageCount = (int) Math.ceil((float) totalCount / pageSizeSelected);

			// 依據商品目錄型態回傳各別ftl(一般購物類、訂房住宿類、交通航班類、房產租售類)
			returnFtlName = enumProdGroupFactory.getCatalogName();
			
		} catch (Exception e) {
			log.error("error:" + e);
			returnMsg = "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。";
			return ERROR;
		}
		
		return returnFtlName;
	}
	
	
	/**
	 * 複製商品組合篩選資料
	 */
	public String queryProdGroupFilterItem() {
		try {
			log.info(">>> catalogSeq: " + catalogSeq);
			log.info(">>> catalogGroupSeq: " + catalogGroupSeq);
			
			pfpCatalogList = pfpCatalogService.getPfpCatalogList(super.getCustomer_info_id());
			customerInfoTitle = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()).getCustomerInfoTitle();
			
			//撈出商品組合篩選資料
			ProdGroupFilterItemList = pfpCatalogGroupItemService.getPfpCatalogGroupItemList(catalogGroupSeq);

			//全部商品組合數量
			pfpCatalog =  pfpCatalogService.getPfpCatalog(catalogSeq);
			//商品組合ID 的目錄型態
			EnumProdGroupFactory enumProdGroupFactory = EnumProdGroupFactory.getCatalogName(pfpCatalog.getCatalogType());
			log.info(">>> enumProdGroupFactory: " + enumProdGroupFactory);
			if (enumProdGroupFactory == null) {
				returnMsg = "目錄型態不正確";
				return ERROR;
			}
			
			// 商品組合ID 的目錄型態
			String catalogType = pfpCatalogService.getPfpCatalog(catalogSeq).getCatalogType();
			log.info(">>> catalogType: " + catalogType);

			enumProdGroupFactory = EnumProdGroupFactory.getCatalogName(catalogType);
			log.info(">>> enumProdGroupFactory: " + enumProdGroupFactory);

			String catalogName = enumProdGroupFactory.getCatalogName();
			log.info(">>> catalogName: " + catalogName);

			AProdGroup aProdGroup = prodGroupFactory.getAProdGroupObj(catalogName);
			
			// 依據商品篩選條件撈出商品組合清單
			String filterSQL = aProdGroup.pfpCatalogGroupItemTofilterSQL(ProdGroupFilterItemList);

			// 撈出該商品組合篩選條件的list商品
			ProdGroupConditionVO prodGroupConditionVO = new ProdGroupConditionVO();
			prodGroupConditionVO.setCatalogSeq(catalogSeq);
			prodGroupConditionVO.setPfpCustomerInfoId(super.getCustomer_info_id());
			prodGroupConditionVO.setPage(currentPage);
			prodGroupConditionVO.setPageSize(pageSizeSelected);
			prodGroupConditionVO.setFilterSQL(filterSQL);
			// 商品清單資料
			prodList = aProdGroup.getProdGroupList(prodGroupConditionVO);
			// 商品清單資料總筆數
			totalCount = Integer.valueOf(aProdGroup.getProdGroupCount(catalogSeq, filterSQL));
			// 總頁數
			pageCount = (int) Math.ceil((float) totalCount / pageSizeSelected);

			// 依據商品目錄型態回傳各別ftl(一般購物類、訂房住宿類、交通航班類、房產租售類)
			returnFtlName = enumProdGroupFactory.getCatalogName();

		} catch (Exception e) {
			log.error("error:" + e);
			returnMsg = "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。";
			return ERROR;
		}
		
		return returnFtlName;
	}
	

	/**
	 * 編輯商品組合篩選資料
	 */
	public String editProdGroupFilterItem() {
		try {
			log.info(">>> catalogSeq: " + catalogSeq);
			log.info(">>> catalogGroupSeq: " + catalogGroupSeq);
			
			pfpCatalogList = pfpCatalogService.getPfpCatalogList(super.getCustomer_info_id());
			customerInfoTitle = pfpCustomerInfoService.findCustomerInfo(super.getCustomer_info_id()).getCustomerInfoTitle();
			
			catalogGroupName = pfpCatalogGroupService.getPfpCatalogGroup(catalogGroupSeq).getCatalogGroupName();
			
			//撈出商品組合篩選資料
			ProdGroupFilterItemList = pfpCatalogGroupItemService.getPfpCatalogGroupItemList(catalogGroupSeq);

			//全部商品組合數量
			pfpCatalog =  pfpCatalogService.getPfpCatalog(catalogSeq);
			//商品組合ID 的目錄型態
			EnumProdGroupFactory enumProdGroupFactory = EnumProdGroupFactory.getCatalogName(pfpCatalog.getCatalogType());
			log.info(">>> enumProdGroupFactory: " + enumProdGroupFactory);
			if (enumProdGroupFactory == null) {
				returnMsg = "目錄型態不正確";
				return ERROR;
			}
			
			// 商品組合ID 的目錄型態
			String catalogType = pfpCatalogService.getPfpCatalog(catalogSeq).getCatalogType();
			log.info(">>> catalogType: " + catalogType);

			enumProdGroupFactory = EnumProdGroupFactory.getCatalogName(catalogType);
			log.info(">>> enumProdGroupFactory: " + enumProdGroupFactory);

			String catalogName = enumProdGroupFactory.getCatalogName();
			log.info(">>> catalogName: " + catalogName);

			AProdGroup aProdGroup = prodGroupFactory.getAProdGroupObj(catalogName);
			
			// 依據商品篩選條件撈出商品組合清單
			String filterSQL = aProdGroup.pfpCatalogGroupItemTofilterSQL(ProdGroupFilterItemList);

			// 撈出該商品組合篩選條件的list商品
			ProdGroupConditionVO prodGroupConditionVO = new ProdGroupConditionVO();
			prodGroupConditionVO.setCatalogSeq(catalogSeq);
			prodGroupConditionVO.setPfpCustomerInfoId(super.getCustomer_info_id());
			prodGroupConditionVO.setPage(currentPage);
			prodGroupConditionVO.setPageSize(pageSizeSelected);
			prodGroupConditionVO.setFilterSQL(filterSQL);
			// 商品清單資料
			prodList = aProdGroup.getProdGroupList(prodGroupConditionVO);
			// 商品清單資料總筆數
			totalCount = Integer.valueOf(aProdGroup.getProdGroupCount(catalogSeq, filterSQL));
			// 總頁數
			pageCount = (int) Math.ceil((float) totalCount / pageSizeSelected);

			// 依據商品目錄型態回傳各別ftl(一般購物類、訂房住宿類、交通航班類、房產租售類)
			returnFtlName = enumProdGroupFactory.getCatalogName();

		} catch (Exception e) {
			log.error("error:" + e);
			returnMsg = "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。";
			return ERROR;
		}
		
		return returnFtlName;
	}
	
	

	public void setCatalogSeq(String catalogSeq) {
		this.catalogSeq = catalogSeq;
	}
	
	public String getCatalogSeq() {
		return catalogSeq;
	}

	public void setPfpCatalogService(IPfpCatalogService pfpCatalogService) {
		this.pfpCatalogService = pfpCatalogService;
	}

	public void setPfpCatalogGroupService(IPfpCatalogGroupService pfpCatalogGroupService) {
		this.pfpCatalogGroupService = pfpCatalogGroupService;
	}

	public void setProdGroupFactory(ProdGroupFactory prodGroupFactory) {
		this.prodGroupFactory = prodGroupFactory;
	}

	public List<pfpCatalogGroupVO> getPfpCatalogGroupVOList() {
		return pfpCatalogGroupVOList;
	}

	public void setCatalogGroupSeq(String catalogGroupSeq) {
		this.catalogGroupSeq = catalogGroupSeq;
	}
	
	public String getCatalogGroupSeq() {
		return catalogGroupSeq;
	}

	public String getCatalogGroupName() {
		return catalogGroupName;
	}

	public String getCatalogProdAllNum() {
		return catalogProdAllNum;
	}

	public List<PfpCatalog> getPfpCatalogList() {
		return pfpCatalogList;
	}

	public String getCustomerInfoTitle() {
		return customerInfoTitle;
	}

	public IPfpCustomerInfoService getPfpCustomerInfoService() {
		return pfpCustomerInfoService;
	}

	public void setPfpCustomerInfoService(IPfpCustomerInfoService pfpCustomerInfoService) {
		this.pfpCustomerInfoService = pfpCustomerInfoService;
	}

	public String getReturnMsg() {
		return returnMsg;
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

	public String getReturnFtlName() {
		return returnFtlName;
	}

	public IPfpCatalogGroupItemService getPfpCatalogGroupItemService() {
		return pfpCatalogGroupItemService;
	}

	public void setPfpCatalogGroupItemService(IPfpCatalogGroupItemService pfpCatalogGroupItemService) {
		this.pfpCatalogGroupItemService = pfpCatalogGroupItemService;
	}

	public List<PfpCatalogGroupItem> getProdGroupFilterItemList() {
		return ProdGroupFilterItemList;
	}

	public void setProdGroupFilterItemList(List<PfpCatalogGroupItem> prodGroupFilterItemList) {
		ProdGroupFilterItemList = prodGroupFilterItemList;
	}
	
	
	
}
