package com.pchome.akbpfp.db.dao.catalog.uploadErrLogList;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogUploadLog;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogUploadLogVO;
import com.pchome.utils.CommonUtils;

public class PfpCatalogUploadErrLogDAO extends BaseDAO<PfpCatalogUploadLog, String> implements IPfpCatalogUploadErrLogDAO {

	/**
	 * 查詢目錄商品上傳錯誤記錄清單
	 * @param vo
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getCatalogProdUploadErrList(PfpCatalogUploadLogVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append(" SELECT                                                                                                                   ");
		hql.append(" 	pcpee.catalog_prod_err_item,                                                                                          ");
		hql.append(" 	IFNULL((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                         ");
		hql.append(" 	        WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 	        AND pcuel.catalog_err_item = 'id'), pcpee.catalog_prod_seq) AS catalog_prod_seq,                              ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	IFNULL((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                         ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_name'), pcpee.ec_name) AS ec_name,                                           ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	IFNULL((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                         ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_img'), pcpee.ec_img) AS ec_img,                                              ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	IFNULL((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                         ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_url'), pcpee.ec_url) AS ec_url,                                              ");
		// 因為int和varchar併，所以轉型字串中文才能正常顯示
		hql.append(" 	CAST(IFNULL((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                    ");
		hql.append(" 				WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                         ");
		hql.append(" 				AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                             ");
		hql.append(" 				AND pcuel.catalog_err_item = 'ec_price'), pcpee.ec_price)AS CHAR) AS ec_price,                            ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	CAST(IFNULL((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                    ");
		hql.append(" 				WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                         ");
		hql.append(" 				AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                             ");
		hql.append(" 				AND pcuel.catalog_err_item = 'ec_discount_price'), pcpee.ec_discount_price)AS CHAR) AS ec_discount_price, ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	IFNULL((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                         ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_stock_status'), pcpee.ec_stock_status) AS ec_stock_status,                   ");
		hql.append(" 	                                                                                                                      ");
		hql.append(" 	IFNULL((SELECT pcuel.catalog_err_reason FROM pfp_catalog_upload_err_log pcuel                                         ");
		hql.append(" 			WHERE pcpee.catalog_upload_log_seq = pcuel.catalog_upload_log_seq                                             ");
		hql.append(" 			AND pcpee.catalog_prod_err_item = pcuel.catalog_prod_err_item                                                 ");
		hql.append(" 			AND pcuel.catalog_err_item = 'ec_use_status'), pcpee.ec_use_status) AS ec_use_status,                         ");
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