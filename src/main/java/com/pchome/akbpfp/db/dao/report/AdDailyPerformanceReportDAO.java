package com.pchome.akbpfp.db.dao.report;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdReport;

@Transactional
public class AdDailyPerformanceReportDAO extends BaseDAO<PfpAdReport, Integer> implements IAdDailyPerformanceReportDAO {
	/**
	 * 每日成效(明細)
	 */
	@Override
	public List<Map<String, Object>> getAdDailyPerformanceList(AdDailyPerformanceReportVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT ");
		hql.append(" SUM(r.ad_pv) AS ad_pv_sum, ");
		hql.append(" SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN (r.ad_clk - r.ad_invalid_clk) ELSE r.ad_view END)) AS ad_clk_sum, ");
		hql.append(" SUM(r.ad_clk_price) AS ad_price_sum, ");
		hql.append(" r.ad_pvclk_date, ");
		hql.append(" r.ad_action_seq, ");
		hql.append(" r.ad_group_seq, ");
		hql.append(" r.ad_seq, ");
		hql.append(" r.template_product_seq, ");
		hql.append(" r.customer_info_id, ");
		hql.append(" r.ad_pvclk_device AS ad_device, ");
		hql.append(" r.ad_operating_rule, ");
		hql.append(" r.ad_clk_price_type, ");
		hql.append(" r.ad_type, ");
//		hql.append(" ( SELECT g.ad_group_status FROM pfp_ad_group g WHERE g.ad_group_seq  = r.ad_group_seq) AS ad_group_status, ");
//		hql.append(" ( SELECT g.ad_group_name FROM pfp_ad_group g WHERE g.ad_group_seq  = r.ad_group_seq) AS ad_group_name, ");
//		hql.append(" ( SELECT a.ad_status FROM pfp_ad a WHERE a.ad_seq = r.ad_seq) AS ad_status, ");
		hql.append(" ( SELECT aa.ad_action_name FROM pfp_ad_group g,pfp_ad_action aa WHERE g.ad_group_seq  = r.ad_group_seq and g.ad_action_seq = aa.ad_action_seq) AS ad_action_name,");
		hql.append(" SUM(r.convert_count) AS convert_count, ");
		hql.append(" SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id = :customerInfoId");
		hql.append(" AND r.ad_pvclk_date >= :startDate");
		hql.append(" AND r.ad_pvclk_date <= :endDate");
		hql.append(" AND r.ad_seq = :adSeq");

		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND REPLACE(r.ad_pvclk_date, '-', '') LIKE REPLACE(:searchStr, '-', '') ");
		}
		
		String adType = ""; // 播放類型
		String adOperatingRule = ""; // 廣告樣式
		String adClkPriceType = ""; // 計價方式
		String adDevice = ""; // 裝置
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			
			adType = tempJSONObject.optString("adType");
			if (StringUtils.isNotBlank(adType) && !"all".equalsIgnoreCase(adType)) {
				hql.append(" AND r.ad_type = :adType");
			}
			
			adOperatingRule = tempJSONObject.optString("adOperatingRule");
			if (StringUtils.isNotBlank(adOperatingRule) && !"all".equalsIgnoreCase(adOperatingRule)) {
				hql.append(" AND r.ad_operating_rule = :adOperatingRule");
			}
			
			adClkPriceType = tempJSONObject.optString("adClkPriceType");
			if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
				hql.append(" AND r.ad_clk_price_type = :adClkPriceType");
			}
			
			adDevice = tempJSONObject.optString("adDevice");
			if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice)) {
				hql.append(" AND r.ad_pvclk_device = :adPvclkDevice");
			}
		}
		
		hql.append(" GROUP BY r.ad_pvclk_date, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		hql.append(" ORDER BY r.ad_pvclk_date, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type ");
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		query.setString("adSeq", vo.getAdSeq());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		if (StringUtils.isNotBlank(adType) && !"all".equalsIgnoreCase(adType)) {
			query.setString("adType", adType);
		}
		
		if (StringUtils.isNotBlank(adOperatingRule) && !"all".equalsIgnoreCase(adOperatingRule)) {
			query.setString("adOperatingRule", adOperatingRule);
		}
		
		if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
			query.setString("adClkPriceType", adClkPriceType);
		}
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice)) {
			query.setString("adPvclkDevice", adDevice);
		}
		
		if (!vo.isDownloadOrIsNotCuttingPagination()) { // 不是下載則做分頁處理
			// 取得分頁
			query.setFirstResult((vo.getPage() - 1) * vo.getPageSize());
			query.setMaxResults(vo.getPageSize());
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 每日成效(加總)
	 */
	@Override
	public List<Map<String, Object>> getAdDailyPerformanceListSum(AdDailyPerformanceReportVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT ");
		hql.append(" SUM(r.ad_pv) AS ad_pv_sum, ");
		// 20180510 檢查排程未減無效點擊數，因pfp很多程式都有引用報表數據，因此不能重跑排程，所以直接調整SQL部分
		hql.append(" SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN (r.ad_clk - r.ad_invalid_clk) ELSE r.ad_view END)) AS ad_clk_sum, ");
		hql.append(" SUM(r.ad_clk_price) AS ad_price_sum, "); // 產生pfp_ad_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" SUM(r.convert_count) AS convert_count, ");
		hql.append(" SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_report AS r ");
		hql.append("	LEFT OUTER JOIN (SELECT DISTINCT ad_seq FROM pfp_ad_detail) AS pad ON (r.ad_seq = pad.ad_seq)");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id = :customerInfoId");
		hql.append(" AND r.ad_pvclk_date >= :startDate");
		hql.append(" AND r.ad_pvclk_date <= :endDate");
		hql.append(" AND r.ad_seq = :adSeq");
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND REPLACE(r.ad_pvclk_date, '-', '') LIKE REPLACE(:searchStr, '-', '') ");
		}
		
		String adType = ""; // 播放類型
		String adOperatingRule = ""; // 廣告樣式
		String adClkPriceType = ""; // 計價方式
		String adDevice = ""; // 裝置
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			
			adType = tempJSONObject.optString("adType");
			if (StringUtils.isNotBlank(adType) && !"all".equalsIgnoreCase(adType)) {
				hql.append(" AND r.ad_type = :adType");
			}
			
			adOperatingRule = tempJSONObject.optString("adOperatingRule");
			if (StringUtils.isNotBlank(adOperatingRule) && !"all".equalsIgnoreCase(adOperatingRule)) {
				hql.append(" AND r.ad_operating_rule = :adOperatingRule");
			}
			
			adClkPriceType = tempJSONObject.optString("adClkPriceType");
			if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
				hql.append(" AND r.ad_clk_price_type = :adClkPriceType");
			}
			
			adDevice = tempJSONObject.optString("adDevice");
			if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice)) {
				hql.append(" AND r.ad_pvclk_device = :adPvclkDevice");
			}
		}
		
		hql.append(" GROUP BY r.ad_seq, r.ad_pvclk_date");
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		query.setString("adSeq", vo.getAdSeq());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		if (StringUtils.isNotBlank(adType) && !"all".equalsIgnoreCase(adType)) {
			query.setString("adType", adType);
		}
		
		if (StringUtils.isNotBlank(adOperatingRule) && !"all".equalsIgnoreCase(adOperatingRule)) {
			query.setString("adOperatingRule", adOperatingRule);
		}
		
		if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
			query.setString("adClkPriceType", adClkPriceType);
		}
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice)) {
			query.setString("adPvclkDevice", adDevice);
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 每日成效(圖表)
	 */
	@Override
	public List<Map<String, Object>> getAdDailyPerformanceListChart(AdDailyPerformanceReportVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT");
		hql.append(" r.ad_pvclk_date, ");
		hql.append(" SUM(r.ad_pv) AS ad_pv_sum, ");
		// 20180510 檢查排程未減無效點擊數，因pfp很多程式都有引用報表數據，因此不能重跑排程，所以直接調整SQL部分
		hql.append(" SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN (r.ad_clk - r.ad_invalid_clk) ELSE r.ad_view END)) AS ad_clk_sum, ");
		hql.append(" SUM(r.ad_clk_price) AS ad_price_sum, "); // 產生pfp_ad_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" SUM(r.convert_count) AS convert_count, ");
		hql.append(" SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_report AS r ");
		hql.append("	LEFT OUTER JOIN (SELECT DISTINCT ad_seq FROM pfp_ad_detail) AS pad ON (r.ad_seq = pad.ad_seq)");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id = :customerInfoId");
		hql.append(" AND r.ad_pvclk_date >= :startDate");
		hql.append(" AND r.ad_pvclk_date <= :endDate");
		hql.append(" AND r.ad_seq = :adSeq");
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND REPLACE(r.ad_pvclk_date, '-', '') LIKE REPLACE(:searchStr, '-', '') ");
		}
		
		String adType = ""; // 播放類型
		String adOperatingRule = ""; // 廣告樣式
		String adClkPriceType = ""; // 計價方式
		String adDevice = ""; // 裝置
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			
			adType = tempJSONObject.optString("adType");
			if (StringUtils.isNotBlank(adType) && !"all".equalsIgnoreCase(adType)) {
				hql.append(" AND r.ad_type = :adType");
			}
			
			adOperatingRule = tempJSONObject.optString("adOperatingRule");
			if (StringUtils.isNotBlank(adOperatingRule) && !"all".equalsIgnoreCase(adOperatingRule)) {
				hql.append(" AND r.ad_operating_rule = :adOperatingRule");
			}
			
			adClkPriceType = tempJSONObject.optString("adClkPriceType");
			if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
				hql.append(" AND r.ad_clk_price_type = :adClkPriceType");
			}
			
			adDevice = tempJSONObject.optString("adDevice");
			if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice)) {
				hql.append(" AND r.ad_pvclk_device = :adPvclkDevice");
			}
		}
		
		hql.append(" GROUP BY r.ad_pvclk_date");
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		query.setString("adSeq", vo.getAdSeq());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		if (StringUtils.isNotBlank(adType) && !"all".equalsIgnoreCase(adType)) {
			query.setString("adType", adType);
		}
		
		if (StringUtils.isNotBlank(adOperatingRule) && !"all".equalsIgnoreCase(adOperatingRule)) {
			query.setString("adOperatingRule", adOperatingRule);
		}
		
		if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
			query.setString("adClkPriceType", adClkPriceType);
		}
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice)) {
			query.setString("adPvclkDevice", adDevice);
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
}