package com.pchome.akbpfp.db.dao.catalog;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogVO;
import com.pchome.utils.CommonUtils;

public class PfpCatalogDAO extends BaseDAO<PfpCatalog,String> implements IPfpCatalogDAO{
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalog> getCatalogType(String catalogSeq) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalog ");
		hql.append(" where catalogSeq = ? ");
		
		return super.getHibernateTemplate().find(hql.toString(), catalogSeq);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalog> getPfpCatalog(String catalogSeq) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalog ");
		hql.append(" where catalogSeq = ? ");
		
		return super.getHibernateTemplate().find(hql.toString(), catalogSeq);
	}
	

	public List<PfpCatalog> getPfpCatalogList(String pfpCustomerInfoId) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalog ");
		hql.append(" where pfpCustomerInfoId = ? ");
		
		return super.getHibernateTemplate().find(hql.toString(), pfpCustomerInfoId);
	}
	

	public List<PfpCatalog> getPfpCatalogByCustomerInfoId(String customerInfoId) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalog ");
		hql.append(" where pfpCustomerInfoId =:customerInfoId ");
		Session session = super.getSession();
        Query query = session.createQuery(hql.toString());
        query.setParameter("customerInfoId", customerInfoId);
        return query.list();
	}
	
	
	public List<PfpCatalog> checkPfpCatalogPrivilege(String customerInfoId,String catalogSeq) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalog ");
		hql.append(" where pfpCustomerInfoId =:customerInfoId ");
		hql.append(" and catalogSeq =:catalogSeq ");
		Session session = super.getSession();
        Query query = session.createQuery(hql.toString());
        query.setParameter("customerInfoId", customerInfoId);
        query.setParameter("catalogSeq", catalogSeq);
        return query.list();
	}
	
	
	
	

	@Override
	public List<Map<String, Object>> getPfpCatalogList(PfpCatalogVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append(" SELECT                                                                                                                                  ");
		hql.append("   pc.catalog_seq,                                                                                                                       ");
		hql.append("   pc.catalog_name,                                                                                                                      ");
		hql.append("   pc.catalog_type,                                                                                                                      ");
		hql.append("   pc.catalog_upload_type,                                                                                                               ");
		hql.append("   pc.catalog_prod_num,                                                                                                                  ");
		hql.append("   pcul.update_content,                                                                                                                  ");
		hql.append("   pcul.update_datetime,                                                                                                                 ");
		hql.append("   pcul.error_num,                                                                                                                       ");
		hql.append("   pcul.catalog_upload_log_seq                                                                                                           ");
		hql.append(" FROM pfp_catalog pc LEFT JOIN(SELECT                                                                                                    ");
		hql.append(" 						  T1.catalog_seq,                                                                                                ");
		hql.append(" 						  T1.update_content,                                                                                             ");
		hql.append(" 						  T1.update_datetime,                                                                                            ");
		hql.append(" 						  T1.error_num,                                                                                                  ");
		hql.append(" 						  T1.catalog_upload_log_seq                                                                                      ");
		hql.append(" 						FROM pfp_catalog_upload_log T1,                                                                                  ");
		hql.append(" 						  (SELECT                                                                                                        ");
		hql.append(" 							 catalog_seq,                                                                                                ");
		hql.append(" 							 MAX(update_datetime)AS update_datetime                                                                      ");
		hql.append(" 						   FROM pfp_catalog_upload_log                                                                                   ");
		hql.append(" 						   WHERE catalog_seq IN (SELECT catalog_seq FROM pfp_catalog WHERE pfp_customer_info_id = :pfp_customer_info_id) ");
		hql.append(" 						   GROUP BY catalog_seq) T2                                                                                      ");
		hql.append(" 						WHERE T1.catalog_seq = T2.catalog_seq                                                                            ");
		hql.append(" 						AND T1.update_datetime = T2.update_datetime) pcul                                                                ");
		hql.append(" ON pc.catalog_seq = pcul.catalog_seq                                                                                                    ");
		hql.append(" WHERE pc.pfp_customer_info_id = :pfp_customer_info_id                                                                                   ");
		
		if (StringUtils.isNotBlank(vo.getQueryString())) {
			hql.append(" AND pc.catalog_name like :catalog_name                                                                                              ");
		}
		
        Query query = super.getSession().createSQLQuery(hql.toString());
		query.setString("pfp_customer_info_id", vo.getPfpCustomerInfoId());

		if (StringUtils.isNotBlank(vo.getQueryString())) {
			query.setString("catalog_name", "%" + vo.getQueryString() + "%");
		}
		
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

	/**
	 * 新增目錄
	 */
	@Override
	public void savePfpCatalog(PfpCatalogVO vo) {
		Date now = new Date();
		PfpCatalog pfpCatalog = new PfpCatalog();
		pfpCatalog.setCatalogName(vo.getCatalogName());
		pfpCatalog.setCatalogType(vo.getCatalogType());
		pfpCatalog.setPfpCustomerInfoId(vo.getPfpCustomerInfoId());
		pfpCatalog.setCatalogSeq(vo.getCatalogSeq());
		pfpCatalog.setCatalogUploadType(" ");
		pfpCatalog.setCatalogUploadContent(" ");
		pfpCatalog.setCatalogImgShowType("1");
		pfpCatalog.setUploadStatus("0");
		pfpCatalog.setUpdateDate(now);
		pfpCatalog.setCreateDate(now);
		super.save(pfpCatalog);
	}
	
	/**
	 * 刪除目錄
	 */
	@Override
	public void deletePfpCatalog(PfpCatalogVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append(" DELETE FROM pfp_catalog          ");
		hql.append(" WHERE catalog_seq = :catalog_seq ");
		hql.append(" AND pfp_customer_info_id = :pfp_customer_info_id ");
		
		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setString("catalog_seq", vo.getCatalogSeq());
		query.setString("pfp_customer_info_id", vo.getPfpCustomerInfoId());

		query.executeUpdate();
		super.getSession().flush();
	}

	/**
	 * 更新目錄
	 */
	@Override
	public void updatePfpCatalog(PfpCatalogVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append(" UPDATE pfp_catalog          ");
		hql.append(" SET catalog_upload_content = :catalog_upload_content, ");
		hql.append(" catalog_upload_type = :catalog_upload_type, ");
		hql.append(" update_date = CURRENT_TIMESTAMP() ");
		hql.append(" WHERE catalog_seq = :catalog_seq ");
		hql.append(" AND pfp_customer_info_id = :pfp_customer_info_id ");
		
		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setString("catalog_upload_content", vo.getUploadContent());
		query.setString("catalog_upload_type", vo.getCatalogUploadType());
		query.setString("catalog_seq", vo.getCatalogSeq());
		query.setString("pfp_customer_info_id", vo.getPfpCustomerInfoId());

		query.executeUpdate();
		super.getSession().flush();
	}

	/**
	 * 更新目錄資料，一般購物類使用
	 */
	@Override
	public void updatePfpCatalogForShoppingProd(PfpCatalogVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append(" UPDATE pfp_catalog ");
		hql.append(" SET catalog_upload_type = :catalog_upload_type, ");
		hql.append(" catalog_upload_content = :catalog_upload_content, ");
		hql.append(" catalog_prod_num = (SELECT COUNT(catalog_prod_seq) FROM pfp_catalog_prod_ec WHERE catalog_seq = :catalog_seq), ");
		hql.append(" update_date = CURRENT_TIMESTAMP() ");
		hql.append(" WHERE catalog_seq = :catalog_seq ");
		hql.append(" AND pfp_customer_info_id = :pfp_customer_info_id ");
		
		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setString("catalog_upload_content", vo.getUploadContent());
		query.setString("catalog_upload_type", vo.getCatalogUploadType());
		query.setString("catalog_seq", vo.getCatalogSeq());
		query.setString("pfp_customer_info_id", vo.getPfpCustomerInfoId());

		query.executeUpdate();
		super.getSession().flush();
	}
}
