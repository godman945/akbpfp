package com.pchome.akbpfp.struts2.action.catalog.prodGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import com.pchome.akbpfp.catalog.prodGroup.factory.AProdGroup;
import com.pchome.akbpfp.catalog.prodGroup.factory.ProdGroupFactory;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;
import com.pchome.akbpfp.db.service.catalog.TMP.IPfpCatalogService;
import com.pchome.akbpfp.db.service.catalog.prodGroup.IPfpCatalogGroupService;
import com.pchome.akbpfp.db.vo.catalog.prodGroup.pfpCatalogGroupVO;
import com.pchome.akbpfp.struts2.BaseCookieAction;
import com.pchome.enumerate.catalog.prodGroup.EnumProdGroupFactory;

public class ProdGroupAction  extends BaseCookieAction{

	private static final long serialVersionUID = 1L;
	
	private String catalogSeq;
	private PfpCatalog pfpCatalog;
	private List<pfpCatalogGroupVO> pfpCatalogGroupVOList; 
	private IPfpCatalogService pfpCatalogService;
	private IPfpCatalogGroupService pfpCatalogGroupService;
	private ProdGroupFactory prodGroupFactory;
	
	//商品群組清單明細
	private String catalogName;
	private String catalogGroupSeq;
	private String catalogGroupName;
	private String catalogGroupCount;
	private List<Map<String,Object>> prodGroupListMap;
	
	
	
	
	
	public String queryCatalogGroup(){
		try{
			log.info(">>> catalogSeq: " + catalogSeq);
			
			pfpCatalogGroupVOList = new ArrayList<pfpCatalogGroupVO>();
			
			//全部商品組合數量
			pfpCatalog =  pfpCatalogService.getPfpCatalog(catalogSeq);
			pfpCatalogGroupVO pfpCatalogAllProd=new pfpCatalogGroupVO();
			pfpCatalogAllProd.setCatalogGroupSeq(pfpCatalog.getCatalogSeq());
			pfpCatalogAllProd.setCatalogGroupName(pfpCatalog.getCatalogName());
			pfpCatalogAllProd.setCatalogProdNum( Integer.toString(pfpCatalog.getCatalogProdNum()) );
			pfpCatalogGroupVOList.add(pfpCatalogAllProd);
			
			
			
			//
			EnumProdGroupFactory enumProdGroupFactory = EnumProdGroupFactory.getCatalogName(pfpCatalog.getCatalogType());
			String catalogFactoryName = enumProdGroupFactory.getCatalogName();
			AProdGroup aProdGroup = prodGroupFactory.getAProdGroupObj(catalogFactoryName);
			
			//商品組合群組列表
			List<PfpCatalogGroup> pfpCatalogGroupList= pfpCatalogGroupService.getPfpCatalogGroupList(catalogSeq);
			
			for (PfpCatalogGroup pfpCatalogGroup : pfpCatalogGroupList) {
				
				pfpCatalogGroupVO pfpCatalogGroupVO = new pfpCatalogGroupVO();
				
				//商品群組條件
				List<PfpCatalogGroupItem> pfpCatalogGroupItems = aProdGroup.getPfpCatalogGroupItemList(pfpCatalogGroup.getCatalogGroupSeq());
				//將pfpCatalogGroupItems轉成sql
				String filterSQL = aProdGroup.pfpCatalogGroupItemTofilterSQL(pfpCatalogGroupItems);
				//撈出該商品組合的總數量
				String prodGroupCount = aProdGroup.getProdGroupCount(catalogSeq,filterSQL);
				
				pfpCatalogGroupVO.setCatalogGroupSeq(pfpCatalogGroup.getCatalogGroupSeq());
				pfpCatalogGroupVO.setCatalogGroupName(pfpCatalogGroup.getCatalogGroupName());
				pfpCatalogGroupVO.setCatalogProdNum(prodGroupCount);
				pfpCatalogGroupVOList.add(pfpCatalogGroupVO);
			}
			
		} catch (Exception e) {
//			dataMap.put("status", "ERROR");
//			dataMap.put("msg", "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			log.error("error:" + e);
		}
		
		return SUCCESS;
	}

	
	public String queryProdGroupList(){
		try{
			log.info(">>> catalogSeq: " + catalogSeq);
			log.info(">>> catalogGroupSeq: " + catalogGroupSeq);
			
			
			//取得商品目錄類型
			pfpCatalog =  pfpCatalogService.getPfpCatalog(catalogSeq);
			EnumProdGroupFactory enumProdGroupFactory = EnumProdGroupFactory.getCatalogName(pfpCatalog.getCatalogType());
			String catalogFactoryName = enumProdGroupFactory.getCatalogName();
			AProdGroup aProdGroup = prodGroupFactory.getAProdGroupObj(catalogFactoryName);
			//商品群組條件
			List<PfpCatalogGroupItem> pfpCatalogGroupItems = aProdGroup.getPfpCatalogGroupItemList(catalogGroupSeq);
			//將pfpCatalogGroupItems轉成sql
			String filterSQL = aProdGroup.pfpCatalogGroupItemTofilterSQL(pfpCatalogGroupItems);
			//取得商品群組清單
			prodGroupListMap = aProdGroup.getProdGroupList(catalogSeq, filterSQL);
			
			catalogName = pfpCatalog.getCatalogName();
			catalogGroupName = pfpCatalogGroupService.get(catalogGroupSeq).getCatalogGroupName();
			catalogGroupCount = aProdGroup.getProdGroupCount(catalogSeq,filterSQL);
			
			
		} catch (Exception e) {
//			dataMap.put("status", "ERROR");
//			dataMap.put("msg", "系統忙碌中，請稍後再試，如仍有問題請洽相關人員。");
			log.error("error:" + e);
		}
		
		return SUCCESS;
	}
	
	
	
	
	

	
	

	public void setCatalogSeq(String catalogSeq) {
		this.catalogSeq = catalogSeq;
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
	
	
	//帶至-03商品組合-商品list.html
	public List<Map<String, Object>> getProdGroupListMap() {
		return prodGroupListMap;
	}
	public String getCatalogName() {
		return catalogName;
	}
	public String getCatalogGroupName() {
		return catalogGroupName;
	}
	public String getCatalogGroupCount() {
		return catalogGroupCount;
	}
	
	
	
	
	
}
