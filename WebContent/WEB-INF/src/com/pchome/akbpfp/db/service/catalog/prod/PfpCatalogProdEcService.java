package com.pchome.akbpfp.db.service.catalog.prod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pchome.akbpfp.db.dao.catalog.prod.IPfpCatalogProdEcDAO;
import com.pchome.akbpfp.db.dao.catalog.prodGroup.IPfpCatalogGroupDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogProdEc;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.catalog.prodList.PfpCatalogProdEcVO;

public class PfpCatalogProdEcService extends BaseService<PfpCatalogProdEc,Integer> implements IPfpCatalogProdEcService{
	
	public List<Object> getProdList(String catalogSeq, String prodStatus, String pfpCustomerInfoId, int page, int pageSize)  throws Exception{
	
		List<Map<String,Object>>  prodLists = ((IPfpCatalogProdEcDAO)dao).getProdList(catalogSeq, prodStatus, pfpCustomerInfoId, page,pageSize);
		List<Object> prodBeanLists = new ArrayList<Object>();
		
		if( (!prodLists.isEmpty()) && (prodLists.size()>0) ){
			
			for (Object object : prodLists) {
				PfpCatalogProdEcVO prodListBean = new PfpCatalogProdEcVO();
				
				Map obj = (Map) object;
				prodListBean.setId(obj.get("id").toString());	//流水號
				prodListBean.setCatalogProdEcSeq(obj.get("catalog_prod_ec_seq").toString()); //商品ID	
				prodListBean.setCatalogSeq(obj.get("catalog_seq").toString());	//商品目錄ID
				prodListBean.setProdName(obj.get("prod_name").toString());		//商品名稱
				prodListBean.setProdTitle(obj.get("prod_title").toString());	//商品敘述
				prodListBean.setProdImg(obj.get("prod_img").toString());		//商品圖像路徑
				prodListBean.setProdUrl(obj.get("prod_url").toString());		//商品網址
				prodListBean.setProdPrice(obj.get("prod_price").toString());		//商品價格
				prodListBean.setProdDiscountPrice(obj.get("prod_discount_price").toString());//商品特價
				prodListBean.setProdStockStatus(obj.get("prod_stock_status").toString());	 //商品庫存
				prodListBean.setProdUseStatus(obj.get("prod_use_status").toString());		 //商品使用狀態(全新/二手)
				prodListBean.setProdCategory(obj.get("prod_category").toString());			 //商品組合篩選分類
				prodListBean.setProdStatus(obj.get("prod_status").toString());				 //商品狀態(開啟/關閉)
				prodListBean.setProdCheckStatus(obj.get("prod_check_status").toString());	 //商品審核狀態
				
				prodBeanLists.add(prodListBean);
			}
		}
		return prodBeanLists;
	}
	
	public String getProdListCount(String catalogSeq, String prodStatus) throws Exception{
		return ((IPfpCatalogProdEcDAO)dao).getProdListCount(catalogSeq, prodStatus);
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

	public List<Map<String,Object>> getEcProdGroupList(String catalogSeq, String filterSQL) throws Exception{
		List<Map<String,Object>> ecProdGroupLists = ((IPfpCatalogProdEcDAO)dao).getEcProdGroupList(catalogSeq,filterSQL);
		
		return ecProdGroupLists;
	}

}
