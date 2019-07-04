package com.pchome.akbpfp.db.dao.report;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdReport;

@Transactional
public class AdProdPerformanceReportDAO extends BaseDAO<PfpAdReport, Integer> implements IAdProdPerformanceReportDAO {

	/**
	 * 商品成效(明細)
	 */
	@Override
	public List<Map<String, Object>> getAdProdPerformanceList(AdProdPerformanceReportVO vo) {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT ");
		hql.append(" a.pfp_customer_info_id, ");
		hql.append(" a.ad_seq, ");
		hql.append(" a.catalog_seq, ");
		hql.append(" a.catalog_prod_seq, ");
		hql.append(" SUM(a.catalog_prod_clk) AS ad_clk_sum, ");
		hql.append(" SUM(a.catalog_prod_pv) AS ad_pv_sum, ");
		hql.append(" b.ec_name, ");
		hql.append(" b.ec_img, ");
		hql.append(" b.ec_status, ");
		hql.append(" (SELECT pad.ad_detail_content FROM pfp_ad_detail pad WHERE a.ad_seq = pad.ad_seq AND pad.ad_detail_id = 'prod_report_name') as prod_ad_name");
		hql.append(" FROM pfp_ad_pvclk_prod a ");
		hql.append(" LEFT JOIN pfp_catalog_prod_ec b ");
		hql.append(" ON a.catalog_seq = b.catalog_seq ");
		hql.append(" AND a.catalog_prod_seq = b.catalog_prod_seq ");
		hql.append(" WHERE 1=1 ");
		hql.append(" AND a.pfp_customer_info_id = :pfpCustomerInfoId ");
		hql.append(" AND a.ad_seq = :adSeq ");
		hql.append(" AND a.record_date >= :startDate ");
		hql.append(" AND a.record_date <= :endDate ");
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND b.ec_name LIKE :searchStr ");
		}
		
		hql.append(" GROUP BY a.catalog_seq, a.catalog_prod_seq ");
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("pfpCustomerInfoId", vo.getPfpCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		query.setString("adSeq", vo.getAdSeq());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		if (!vo.isDownloadOrIsNotCuttingPagination()) { // 不是下載則做分頁處理
			// 取得分頁
			query.setFirstResult((vo.getPage() - 1) * vo.getPageSize());
			query.setMaxResults(vo.getPageSize());
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 商品成效(加總)
	 */
	@Override
	public List<Map<String, Object>> getAdProdPerformanceListSum(AdProdPerformanceReportVO vo) {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT ");
		hql.append(" SUM(a.catalog_prod_clk) AS ad_clk_sum, ");
		hql.append(" SUM(a.catalog_prod_pv) AS ad_pv_sum ");
		hql.append(" FROM pfp_ad_pvclk_prod a ");
		hql.append(" LEFT JOIN pfp_catalog_prod_ec b ");
		hql.append(" ON a.catalog_seq = b.catalog_seq ");
		hql.append(" AND a.catalog_prod_seq  = b.catalog_prod_seq ");
		hql.append(" WHERE 1=1 ");
		hql.append(" AND a.pfp_customer_info_id = :pfpCustomerInfoId ");
		hql.append(" AND a.ad_seq = :adSeq ");
		hql.append(" AND a.record_date >= :startDate ");
		hql.append(" AND a.record_date <= :endDate ");
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND b.ec_name LIKE :searchStr ");
		}
		
		hql.append(" GROUP BY a.catalog_seq, a.catalog_prod_seq ");
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("pfpCustomerInfoId", vo.getPfpCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		query.setString("adSeq", vo.getAdSeq());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 商品成效(圖表)
	 */
	@Override
	public List<Map<String, Object>> getAdProdPerformanceListChart(AdProdPerformanceReportVO vo) {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT ");
		hql.append("  record_date, ");
		hql.append("  SUM(a.catalog_prod_pv) AS ad_pv_sum, ");
		hql.append("  SUM(a.catalog_prod_clk) AS ad_clk_sum ");
		hql.append(" FROM pfp_ad_pvclk_prod a ");
		hql.append(" WHERE 1=1 ");
		hql.append(" AND a.pfp_customer_info_id = :pfpCustomerInfoId ");
		hql.append(" AND a.ad_seq = :adSeq ");
		hql.append(" AND a.record_date >= :startDate ");
		hql.append(" AND a.record_date <= :endDate ");
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND b.ec_name LIKE :searchStr ");
		}
		
		hql.append(" GROUP BY a.record_date ");
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("pfpCustomerInfoId", vo.getPfpCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		query.setString("adSeq", vo.getAdSeq());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
}