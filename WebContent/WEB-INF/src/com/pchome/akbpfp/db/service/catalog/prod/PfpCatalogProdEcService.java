package com.pchome.akbpfp.db.service.catalog.prod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.catalog.prod.IPfpCatalogProdEcDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogProdEc;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.catalog.prodGroup.ProdGroupConditionVO;
import com.pchome.akbpfp.db.vo.catalog.prodList.PfpCatalogProdEcVO;
import com.pchome.akbpfp.db.vo.catalog.prodList.ProdListConditionVO;
import com.pchome.enumerate.catalogprod.EnumEcCheckStatusType;
import com.pchome.enumerate.catalogprod.EnumEcStatusType;
import com.pchome.enumerate.catalogprod.EnumEcStockStatusType;
import com.pchome.enumerate.catalogprod.EnumEcUseStatusType;

public class PfpCatalogProdEcService extends BaseService<PfpCatalogProdEc,Integer> implements IPfpCatalogProdEcService{
	
	public List<Object> getProdList(ProdListConditionVO prodListConditionVO)  throws Exception{
	
		List<Map<String,Object>>  prodLists = ((IPfpCatalogProdEcDAO)dao).getProdList(prodListConditionVO);
		List<Object> prodBeanLists = new ArrayList<Object>();
		
		if( (!prodLists.isEmpty()) && (prodLists.size()>0) ){
			
			for (Object object : prodLists) {
				PfpCatalogProdEcVO prodListBean = new PfpCatalogProdEcVO();
				
				Map obj = (Map) object;
				prodListBean.setId(obj.get("id").toString());	//流水號
				prodListBean.setCatalogProdSeq(obj.get("catalog_prod_seq").toString()); //商品ID	
				prodListBean.setCatalogSeq(obj.get("catalog_seq").toString());	//商品目錄ID
				prodListBean.setEcName(obj.get("ec_name").toString());		//商品名稱
				prodListBean.setEcTitle(obj.get("ec_title").toString());	//商品敘述
				prodListBean.setEcImg(obj.get("ec_img").toString());		//商品圖像路徑
				prodListBean.setEcUrl(obj.get("ec_url").toString());		//商品網址
				prodListBean.setEcPrice(obj.get("ec_price").toString());		//商品價格
				prodListBean.setEcDiscountPrice(obj.get("ec_discount_price").toString());//商品特價
				prodListBean.setEcStockStatus(obj.get("ec_stock_status").toString());	 //商品庫存
				prodListBean.setEcUseStatus(obj.get("ec_use_status").toString());		 //商品使用狀態(全新/二手)
				prodListBean.setEcCategory(obj.get("ec_category").toString());			 //商品組合篩選分類
				prodListBean.setEcStatus(obj.get("ec_status").toString());				 //商品狀態(開啟/關閉)
				prodListBean.setEcCheckStatus(obj.get("ec_check_status").toString());	 //商品審核狀態
				
				//商品庫存中文
				for(EnumEcStockStatusType ecStockStatusType:EnumEcStockStatusType.values()){
					if( StringUtils.equals(ecStockStatusType.getType(), obj.get("ec_stock_status").toString()) ){
						prodListBean.setEcStockStatusDesc(ecStockStatusType.getChName());
						break;
					}
				}
				
				//商品使用狀態(全新/二手)中文
				for(EnumEcUseStatusType ecUseStatusType:EnumEcUseStatusType.values()){
					if( StringUtils.equals(ecUseStatusType.getType(), obj.get("ec_use_status").toString()) ){
						prodListBean.setEcUseStatusDesc(ecUseStatusType.getChName());
						break;
					}
				}
				
				//商品狀態(開啟/關閉)中文
				for(EnumEcStatusType ecStatusType:EnumEcStatusType.values()){
					if( StringUtils.equals(ecStatusType.getType(),obj.get("ec_status").toString()) ){
						prodListBean.setEcStatusDesc(ecStatusType.getChName());
						break;
					}
				}
				
				//商品審核狀態中文
				for(EnumEcCheckStatusType ecCheckStatusType:EnumEcCheckStatusType.values()){
					if( StringUtils.equals(ecCheckStatusType.getType(),obj.get("ec_check_status").toString()) ){
						prodListBean.setEcCheckStatusDesc(ecCheckStatusType.getChName());
						break;
					}
				}
				
				prodBeanLists.add(prodListBean);
			}
		}
		return prodBeanLists;
	}
	
	public String getProdListCount(ProdListConditionVO prodListConditionVO) throws Exception{
		return ((IPfpCatalogProdEcDAO)dao).getProdListCount(prodListConditionVO);
	}
	
	public void updateProdListProdStatus(String catalogSeq, String prodStatus, List<String> prodIdList) throws Exception{
		((IPfpCatalogProdEcDAO)dao).updateProdListProdStatus(catalogSeq, prodStatus, prodIdList);
	}
	
	public List<Map<String,Object>> queryProdListDetail(String catalogSeq,String prodId) throws Exception{
		return ((IPfpCatalogProdEcDAO)dao).queryProdListDetail(catalogSeq, prodId);
	}
	
	public String getProdGroupCount(String catalogSeq, String filterSQL) throws Exception{
		List<Map<String,Object>>  prodGroupCountMap = ((IPfpCatalogProdEcDAO)dao).getProdGroupCount(catalogSeq, filterSQL);
		
		String prodGroupCount = "0";
		for (Object object : prodGroupCountMap) {
			
			Map obj = (Map) object;
			prodGroupCount = obj.get("count").toString();
		}
		
		return prodGroupCount;
	}
	
	public List<Map<String,Object>> getEcProdGroupListByRandom(String catalogSeq, String filterSQL, int prodNum) throws Exception{
		List<Map<String,Object>> ecProdGroupLists = ((IPfpCatalogProdEcDAO)dao).getEcProdGroupListByRandom(catalogSeq,filterSQL,prodNum);
		
		return ecProdGroupLists;
	}

	public List<Object> getEcProdGroupList(ProdGroupConditionVO prodGroupConditionVO) throws Exception{
		List<Map<String,Object>> ecProdGroupLists = ((IPfpCatalogProdEcDAO)dao).getEcProdGroupList(prodGroupConditionVO);
		List<Object> prodBeanLists = new ArrayList<Object>();
		
		if( (!ecProdGroupLists.isEmpty()) && (ecProdGroupLists.size()>0) ){
			
			for (Object object : ecProdGroupLists) {
				PfpCatalogProdEcVO prodListBean = new PfpCatalogProdEcVO();
				
				Map obj = (Map) object;
				prodListBean.setId(obj.get("id").toString());	//流水號
				prodListBean.setCatalogProdSeq(obj.get("catalog_prod_seq").toString()); //商品ID	
				prodListBean.setCatalogSeq(obj.get("catalog_seq").toString());	//商品目錄ID
				prodListBean.setEcName(obj.get("ec_name").toString());		//商品名稱
				prodListBean.setEcTitle(obj.get("ec_title").toString());	//商品敘述
				prodListBean.setEcImg(obj.get("ec_img").toString());		//商品圖像路徑
				prodListBean.setEcUrl(obj.get("ec_url").toString());		//商品網址
				prodListBean.setEcPrice(obj.get("ec_price").toString());		//商品價格
				prodListBean.setEcDiscountPrice(obj.get("ec_discount_price").toString());//商品特價
				prodListBean.setEcStockStatus(obj.get("ec_stock_status").toString());	 //商品庫存
				prodListBean.setEcUseStatus(obj.get("ec_use_status").toString());		 //商品使用狀態(全新/二手)
				prodListBean.setEcCategory(obj.get("ec_category").toString());			 //商品組合篩選分類
				prodListBean.setEcStatus(obj.get("ec_status").toString());				 //商品狀態(開啟/關閉)
				prodListBean.setEcCheckStatus(obj.get("ec_check_status").toString());	 //商品審核狀態
				
				//商品庫存中文
				for(EnumEcStockStatusType ecStockStatusType:EnumEcStockStatusType.values()){
					if( StringUtils.equals(ecStockStatusType.getType(), obj.get("ec_stock_status").toString()) ){
						prodListBean.setEcStockStatusDesc(ecStockStatusType.getChName());
						break;
					}
				}
				
				//商品使用狀態(全新/二手)中文
				for(EnumEcUseStatusType ecUseStatusType:EnumEcUseStatusType.values()){
					if( StringUtils.equals(ecUseStatusType.getType(), obj.get("ec_use_status").toString()) ){
						prodListBean.setEcUseStatusDesc(ecUseStatusType.getChName());
						break;
					}
				}
				
				//商品狀態(開啟/關閉)中文
				for(EnumEcStatusType ecStatusType:EnumEcStatusType.values()){
					if( StringUtils.equals(ecStatusType.getType(),obj.get("ec_status").toString()) ){
						prodListBean.setEcStatusDesc(ecStatusType.getChName());
						break;
					}
				}
				
				//商品審核狀態中文
				for(EnumEcCheckStatusType ecCheckStatusType:EnumEcCheckStatusType.values()){
					if( StringUtils.equals(ecCheckStatusType.getType(),obj.get("ec_check_status").toString()) ){
						prodListBean.setEcCheckStatusDesc(ecCheckStatusType.getChName());
						break;
					}
				}
				
				prodBeanLists.add(prodListBean);
			}
		}
		return prodBeanLists;
		
	}
	
	public List<String> queryCategoryGroupByVal(String catalogSeq)  throws Exception{
		List<Map<String,Object>>  categoryMapList = ((IPfpCatalogProdEcDAO)dao).queryCategoryGroupByVal(catalogSeq);
		List<String> categoryList = new ArrayList<String>();
		
		if( (!categoryMapList.isEmpty()) && (categoryMapList.size()>0) ){
			for (Object object : categoryMapList) {
				Map obj = (Map) object;
				String category= obj.get("ec_category").toString(); 
				categoryList.add(category);
			}
		}
		return categoryList;
	}

}
