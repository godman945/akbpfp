package com.pchome.akbpfp.db.dao.catalog.uploadList;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogProdEc;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadErrLog;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadLog;

public class PfpCatalogUploadListDAO extends BaseDAO<String, String> implements IPfpCatalogUploadListDAO {

	/**
	 * 更新一般購物類資料
	 * @param pfpCatalogProdEc
	 * @return 更新筆數
	 */
	public int updatePfpCatalogProdEc(PfpCatalogProdEc pfpCatalogProdEc) {
		String hql = "UPDATE pfp_catalog_prod_ec ";
			   hql += "SET ec_name = :ec_name, ec_title = :ec_title, ec_url = :ec_url, ec_price = :ec_price, ";
			   hql += "ec_discount_price = :ec_discount_price, ec_stock_status = :ec_stock_status, ec_use_status = :ec_use_status, ";
			   hql += "ec_category = :ec_category, ec_status = :ec_status, ec_check_status = :ec_check_status, ";
			   hql += "update_date = CURRENT_TIMESTAMP() ";
			   hql += "WHERE catalog_prod_seq = :catalog_prod_seq AND catalog_seq = :catalog_seq";

		Session session = getSession();
		Query query = session.createSQLQuery(hql);
		query.setString("ec_name", pfpCatalogProdEc.getEcName());
		query.setString("ec_title", pfpCatalogProdEc.getEcTitle());
		query.setString("ec_url", pfpCatalogProdEc.getEcUrl());
		query.setInteger("ec_price", pfpCatalogProdEc.getEcPrice());
		query.setInteger("ec_discount_price", pfpCatalogProdEc.getEcDiscountPrice());
		query.setString("ec_stock_status", pfpCatalogProdEc.getEcStockStatus());
		query.setString("ec_use_status", pfpCatalogProdEc.getEcUseStatus());
		query.setString("ec_category", pfpCatalogProdEc.getEcCategory());
		query.setString("ec_status", pfpCatalogProdEc.getEcStatus());
		query.setString("ec_check_status", pfpCatalogProdEc.getEcCheckStatus());
		
		query.setString("catalog_prod_seq", pfpCatalogProdEc.getCatalogProdSeq());
		query.setString("catalog_seq", pfpCatalogProdEc.getPfpCatalog().getCatalogSeq());
		int updateCount = query.executeUpdate();
		session.flush();
		return updateCount;		
	}

	/**
	 * 新增一般購物類資料
	 * @param pfpCatalogProdEc
	 */
	public void savePfpCatalogProdEc(PfpCatalogProdEc pfpCatalogProdEc) {
		super.getHibernateTemplate().save(pfpCatalogProdEc);
	}

	/**
	 * 刪除不在catalogProdEcSeqList列表內的資料
	 * @param catalogSeq 商品目錄ID
	 * @param catalogProdEcSeqList 不被刪除的名單
	 */
	public void deleteNotInPfpCatalogProdEc(String catalogSeq, List<String> catalogProdSeqList) {
		String hql = "DELETE FROM pfp_catalog_prod_ec ";
			   hql += "WHERE catalog_seq = :catalog_seq ";
			   if (catalogProdSeqList.size() != 0) {
				   hql += "AND catalog_prod_seq NOT IN(:catalog_prod_seq_list)";
			   }

		Session session = getSession();
		Query query = session.createSQLQuery(hql);
		query.setString("catalog_seq", catalogSeq);
		if (catalogProdSeqList.size() != 0) {
			query.setParameterList("catalog_prod_seq_list", catalogProdSeqList);
		}
		query.executeUpdate();
		session.flush();
	}

	/**
	 * 新增商品目錄更新紀錄
	 * @param pfpCatalogUploadLog
	 */
	public void savePfpCatalogUploadLog(PfpCatalogUploadLog pfpCatalogUploadLog) {
		super.getHibernateTemplate().save(pfpCatalogUploadLog);
	}

	/**
	 * 新增商品目錄更新錯誤紀錄
	 * @param pfpCatalogUploadErrLog
	 */
	public void savePfpCatalogUploadErrLog(PfpCatalogUploadErrLog pfpCatalogUploadErrLog) {
		super.getHibernateTemplate().save(pfpCatalogUploadErrLog);
	}

}
