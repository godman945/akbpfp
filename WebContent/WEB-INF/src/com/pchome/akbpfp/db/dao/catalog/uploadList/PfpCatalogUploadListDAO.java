package com.pchome.akbpfp.db.dao.catalog.uploadList;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadLog;
import com.pchome.akbpfp.db.vo.catalog.PfpCatalogVO;
import com.pchome.akbpfp.db.vo.catalog.uploadList.PfpCatalogProdEcErrorVO;
import com.pchome.utils.CommonUtils;

public class PfpCatalogUploadListDAO extends BaseDAO<String, String> implements IPfpCatalogUploadListDAO {

	/**
	 * 新增商品目錄更新紀錄
	 * @param pfpCatalogUploadLog
	 */
	public void savePfpCatalogUploadLog(PfpCatalogUploadLog pfpCatalogUploadLog) {
		super.getHibernateTemplate().save(pfpCatalogUploadLog);
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
	 * 刪除 一般購物類商品上傳錯誤清單
	 * @param vo
	 */
	@Override
	public void deletePfpCatalogProdEcError(PfpCatalogVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append(" DELETE FROM pfp_catalog_prod_ec_error ");
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
	 * 刪除 商品目錄設定 
	 * @param vo
	 */
	@Override
	public void deletePfpCatalogSetup(PfpCatalogVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append(" DELETE FROM pfp_catalog_setup ");
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

	/**
	 * 查詢目錄商品上傳錯誤記錄清單
	 * @param vo
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getCatalogProdUploadErrList(PfpCatalogProdEcErrorVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append(" SELECT                                                                                                                   ");                                                       
		hql.append(" 	pcpee.catalog_prod_err_item,                                                                                          ");
		hql.append(" 	if((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                             ");
		hql.append(" 	        WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 	        AND pcuel.catalog_err_item = 'id') != 'null', 'isErr', '') AS catalog_prod_seq_errstatus,                     ");
		hql.append(" 			                                                                                                              ");
		hql.append(" 	IFNULL((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                         ");
		hql.append(" 	        WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 	        AND pcuel.catalog_err_item = 'id'), pcpee.catalog_prod_seq) AS catalog_prod_seq,                              ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	if((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                             ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_name') != 'null', 'isErr', '') AS ec_name_errstatus,                         ");
		hql.append(" 														                                                                  ");
		hql.append(" 	IFNULL((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                         ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_name'), pcpee.ec_name) AS ec_name,                                           ");
		hql.append(" 			                                                                                                              ");
		hql.append(" 	if((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                             ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_img_url') != 'null', 'isErr', '') AS ec_img_errstatus,	                      ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	IFNULL((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                         ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_img_url'), pcpee.ec_img) AS ec_img,                                          ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	if((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                             ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_url') != 'null', 'isErr', '') AS ec_url_errstatus,                           ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	IFNULL((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                         ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_url'), pcpee.ec_url) AS ec_url,                                              ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	if((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                             ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_price') != 'null', 'isErr', '') AS ec_price_errstatus,                       ");
		hql.append(" 	                                                                                                                      ");
		// 因為int和varchar併，所以轉型字串中文才能正常顯示
		hql.append(" 	CAST(IFNULL((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                    ");
		hql.append(" 				WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                         ");
		hql.append(" 				AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                             ");
		hql.append(" 				AND pcuel.catalog_err_item = 'ec_price'), pcpee.ec_price)AS CHAR) AS ec_price,                            ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	if((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                             ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_discount_price') != 'null', 'isErr', '') AS ec_discount_price_errstatus,     ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	CAST(IFNULL((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                    ");
		hql.append(" 				WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                         ");
		hql.append(" 				AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                             ");
		hql.append(" 				AND pcuel.catalog_err_item = 'ec_discount_price'), pcpee.ec_discount_price)AS CHAR) AS ec_discount_price, ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	if((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                             ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_stock_status') != 'null', 'isErr', '') AS ec_stock_status_errstatus,         ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	IFNULL((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                         ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_stock_status'), pcpee.ec_stock_status) AS ec_stock_status,                   ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	if((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                             ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_use_status') != 'null', 'isErr', '') AS ec_use_status_errstatus,             ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	IFNULL((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                         ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_use_status'), pcpee.ec_use_status) AS ec_use_status,                         ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	if((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                             ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_category') != 'null', 'isErr', '') AS ec_category_errstatus,                 ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	IFNULL((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                         ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_category'), pcpee.ec_category) AS ec_category                                ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" FROM pfp_catalog_prod_ec_error pcpee                                                                                     ");
		hql.append(" WHERE pcpee.catalog_upload_log_seq = :catalog_upload_log_seq                                                             ");
		
		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setString("catalog_upload_log_seq", vo.getCatalogUploadLogSeq());
		
		// 記錄總筆數
		vo.setTotalCount(query.list().size());
		// 計算總頁數
		vo.setPageCount(CommonUtils.getTotalPage(vo.getTotalCount(), vo.getPageSize()));
		
		if (vo.isPaginationFlag()) {
			// 取得分頁
			query.setFirstResult((vo.getPageNo() - 1) * vo.getPageSize());
			query.setMaxResults(vo.getPageSize());
		}
		
        return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

}