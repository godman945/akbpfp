package com.pchome.akbpfp.db.dao.catalog;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalog;
import com.pchome.akbpfp.db.vo.ad.PfpCatalogVO;
import com.pchome.utils.CommonUtils;

public class PfpCatalogDAO extends BaseDAO<PfpCatalog, String> implements IPfpCatalogDAO {

	@Override
	public List<Map<String, Object>> getPfpCatalogList(PfpCatalogVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append(" SELECT                                                                                                                                  ");
		hql.append("   pc.catalog_seq,                                                                                                                       ");
		hql.append("   pc.catalog_name,                                                                                                                      ");
		hql.append("   pc.catalog_type,                                                                                                                      ");
		hql.append("   pc.catalog_upload_type,                                                                                                               ");
		hql.append("   pcul.update_content,                                                                                                                  ");
		hql.append("   pcul.update_datetime,                                                                                                                 ");
		hql.append("   pcul.success_num,                                                                                                                     ");
		hql.append("   pcul.error_num                                                                                                                        ");
		hql.append(" FROM pfp_catalog pc, (SELECT                                                                                                            ");
		hql.append(" 						  T1.catalog_seq,                                                                                                ");
		hql.append(" 						  T1.update_content,                                                                                             ");
		hql.append(" 						  T1.update_datetime,                                                                                            ");
		hql.append(" 						  T1.success_num,                                                                                                ");
		hql.append(" 						  T1.error_num                                                                                                   ");
		hql.append(" 						FROM pfp_catalog_upload_log T1,                                                                                  ");
		hql.append(" 						  (SELECT                                                                                                        ");
		hql.append(" 							 catalog_seq,                                                                                                ");
		hql.append(" 							 MAX(update_datetime)AS update_datetime                                                                      ");
		hql.append(" 						   FROM pfp_catalog_upload_log                                                                                   ");
		hql.append(" 						   WHERE catalog_seq IN (SELECT catalog_seq FROM pfp_catalog WHERE pfp_customer_info_id = :pfp_customer_info_id) ");
		hql.append(" 						   GROUP BY catalog_seq) T2                                                                                      ");
		hql.append(" 						WHERE T1.catalog_seq = T2.catalog_seq                                                                            ");
		hql.append(" 						AND T1.update_datetime = T2.update_datetime) pcul                                                                ");
		hql.append(" WHERE pc.catalog_seq = pcul.catalog_seq                                                                                                 ");
		
        Query query = super.getSession().createSQLQuery(hql.toString());
		query.setString("pfp_customer_info_id", vo.getPfpCustomerInfoId());

		// 記錄總筆數
		vo.setTotalCount(query.list().size());
		// 計算總頁數
		vo.setPageCount(CommonUtils.getTotalPage(vo.getTotalCount(), vo.getPageSize()));
		
		//取得分頁
		query.setFirstResult((vo.getPageNo() - 1) * vo.getPageSize());
		query.setMaxResults(vo.getPageSize());
		
        return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	@Override
	public void savePfpCatalog(PfpCatalogVO vo) {
		Date now = new Date();
		
		PfpCatalog pfpCatalog = new PfpCatalog();
		pfpCatalog.setCatalogName(vo.getCatalogName());
		pfpCatalog.setCatalogType(vo.getCatalogType());
		pfpCatalog.setPfpCustomerInfoId(vo.getPfpCustomerInfoId());
		pfpCatalog.setCatalogSeq(vo.getCatalogSeq());
		
		pfpCatalog.setCatalogUploadType("1");
		pfpCatalog.setCatalogUploadContent(" ");
		pfpCatalog.setCatalogImgShowType("1");
		
		pfpCatalog.setUpdateDate(now);
		pfpCatalog.setCreateDate(now);
		super.save(pfpCatalog);
	}
	
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

}
