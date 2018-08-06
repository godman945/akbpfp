package com.pchome.akbpfp.db.dao.catalog.uploadList;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogProdEc;
import com.pchome.akbpfp.db.pojo.Sequence;

public class PfpCatalogUploadListDAO extends BaseDAO<Sequence,String> implements IPfpCatalogUploadListDAO{

	@Override
	public void saveOrUpdatePfpCatalogProdEc(PfpCatalogProdEc pfpCatalogProdEc) {
		super.getHibernateTemplate().saveOrUpdate(pfpCatalogProdEc);
	}

	/**
	 * 更新一般購物類table資料
	 * @param pfpCatalogProdEc
	 * @return 更新筆數
	 */
	public int updatePfpCatalogProdEc(PfpCatalogProdEc pfpCatalogProdEc) {
		String hql = "UPDATE pfp_catalog_prod_ec ";
			   hql += "SET prod_name = :prod_name, prod_title = :prod_title, prod_url = :prod_url, prod_price = :prod_price, ";
			   hql += "prod_discount_price = :prod_discount_price, prod_stock_status = :prod_stock_status, prod_use_status = :prod_use_status, ";
			   hql += "prod_category = :prod_category, prod_status = :prod_status, prod_check_status = :prod_check_status, ";
			   hql += "update_date = CURRENT_TIMESTAMP() ";
			   hql += "WHERE catalog_prod_ec_seq = :catalog_prod_ec_seq AND catalog_seq = :catalog_seq";

		Session session = getSession();
		Query query = session.createSQLQuery(hql);
		query.setString("prod_name", pfpCatalogProdEc.getProdName());
		query.setString("prod_title", pfpCatalogProdEc.getProdTitle());
		query.setString("prod_url", pfpCatalogProdEc.getProdUrl());
		query.setInteger("prod_price", pfpCatalogProdEc.getProdPrice());
		query.setInteger("prod_discount_price", pfpCatalogProdEc.getProdDiscountPrice());
		query.setString("prod_stock_status", pfpCatalogProdEc.getProdStockStatus());
		query.setString("prod_use_status", pfpCatalogProdEc.getProdUseStatus());
		query.setString("prod_category", pfpCatalogProdEc.getProdCategory());
		query.setString("prod_status", pfpCatalogProdEc.getProdStatus());
		query.setString("prod_check_status", pfpCatalogProdEc.getProdCheckStatus());
		
		query.setString("catalog_prod_ec_seq", pfpCatalogProdEc.getCatalogProdEcSeq());
		query.setString("catalog_seq", pfpCatalogProdEc.getPfpCatalog().getCatalogSeq());
		int updateCount = query.executeUpdate();
		session.flush();
		return updateCount;		
	}

	/**
	 * 新增一般購物類table資料
	 * @param pfpCatalogProdEc
	 */
	public void savePfpCatalogProdEc(PfpCatalogProdEc pfpCatalogProdEc) {
		super.getHibernateTemplate().save(pfpCatalogProdEc);
	}

	/**
	 * 刪除
	 * @param catalogSeq 商品目錄ID
	 * @param catalogProdEcSeqList 不被刪除的名單
	 */
	public void deleteNotInPfpCatalogProdEc(String catalogSeq, List<String> catalogProdEcSeqList) {
		// TODO Auto-generated method stub
//		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		
		String hql = "DELETE FROM pfp_catalog_prod_ec ";
			   hql += "WHERE catalog_seq = :catalog_seq AND catalog_prod_ec_seq NOT IN(:catalog_prod_ec_seq_list)";
		Session session = getSession();
		Query query = session.createSQLQuery(hql);
		query.setString("catalog_seq", catalogSeq);
		query.setParameterList("catalog_prod_ec_seq_list", catalogProdEcSeqList);
		query.executeUpdate();
		session.flush();
	}

}
