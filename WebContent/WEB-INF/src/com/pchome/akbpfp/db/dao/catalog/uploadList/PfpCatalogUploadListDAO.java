package com.pchome.akbpfp.db.dao.catalog.uploadList;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogProdEc;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadErrLog;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadLog;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogVO;

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

	/**
	 * 刪除 商品目錄更新錯誤紀錄
	 * @param vo
	 */
	@Override
	public void deletePfpCatalogUploadErrLog(PfpCatalogVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append(" DELETE FROM pfp_catalog_upload_err_log ");
		hql.append(" WHERE catalog_upload_log_seq IN ");
		hql.append(" (SELECT catalog_upload_log_seq FROM pfp_catalog_upload_log WHERE catalog_seq = :catalog_seq) ");

		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setString("catalog_seq", vo.getCatalogSeq());

		query.executeUpdate();
		super.getSession().flush();
	}

	/**
	 * 刪除 商品目錄更新紀錄
	 * @param vo
	 */
	@Override
	public void deletePfpCatalogUploadLog(PfpCatalogVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append(" DELETE FROM pfp_catalog_upload_log ");
		hql.append(" WHERE catalog_seq = :catalog_seq ");
		
		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setString("catalog_seq", vo.getCatalogSeq());

		query.executeUpdate();
		super.getSession().flush();
	}

	/**
	 * 刪除 一般購物類商品清單
	 * @param vo
	 */
	@Override
	public void deletePfpCatalogProdEc(PfpCatalogVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append(" DELETE FROM pfp_catalog_prod_ec ");
		hql.append(" WHERE catalog_seq = :catalog_seq ");
		
		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setString("catalog_seq", vo.getCatalogSeq());

		query.executeUpdate();
		super.getSession().flush();
	}
	
	/**
	 * 刪除 商品目錄群組明細
	 * @param vo
	 */
	@Override
	public void deletePfpCatalogGroupItem(PfpCatalogVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append(" DELETE FROM pfp_catalog_group_item ");
		hql.append(" WHERE catalog_group_seq = ");
		hql.append(" (SELECT catalog_group_seq FROM pfp_catalog_group WHERE catalog_seq = :catalog_seq) ");
		
		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setString("catalog_seq", vo.getCatalogSeq());

		query.executeUpdate();
		super.getSession().flush();
	}

	/**
	 * 刪除 商品目錄群組
	 * @param vo
	 */
	@Override
	public void deletePfpCatalogGroup(PfpCatalogVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append(" DELETE FROM pfp_catalog_group ");
		hql.append(" WHERE catalog_seq = :catalog_seq");
		
		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setString("catalog_seq", vo.getCatalogSeq());

		query.executeUpdate();
		super.getSession().flush();
	}

	/**
	 * 取得一般購物類商品清單 資料
	 */
	@Override
	public List<Map<String, Object>> getPfpCatalogProdEc(String catalogSeq, String catalogProdSeq) {
		StringBuffer hql = new StringBuffer();
		hql.append(" SELECT ");
		hql.append(" id, catalog_prod_seq, catalog_seq, ec_name, ec_title, ");
		hql.append(" ec_img, ec_img_region, ec_img_md5, ec_url, ec_price, ");
		hql.append(" ec_discount_price, ec_stock_status, ec_use_status, ec_category, ec_status, ");
		hql.append(" ec_check_status, update_date, create_date ");
		hql.append(" FROM pfp_catalog_prod_ec ");
		hql.append(" WHERE 1 = 1 ");
		
		if (StringUtils.isNotBlank(catalogSeq)) {
			hql.append(" AND catalog_seq = :catalog_seq ");
		}
		
		if (StringUtils.isNotBlank(catalogProdSeq)) {
			hql.append(" AND catalog_prod_seq = :catalog_prod_seq ");
		}
		
		Query query = super.getSession().createSQLQuery(hql.toString());
		
		if (StringUtils.isNotBlank(catalogSeq)) {
			query.setString("catalog_seq", catalogSeq);
		}
		
		if (StringUtils.isNotBlank(catalogProdSeq)) {
			query.setString("catalog_prod_seq", catalogProdSeq);
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
}