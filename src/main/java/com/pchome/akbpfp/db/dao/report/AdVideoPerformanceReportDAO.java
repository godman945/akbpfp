package com.pchome.akbpfp.db.dao.report;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdVideoReport;
import com.pchome.akbpfp.db.vo.report.ReportQueryConditionVO;

public class AdVideoPerformanceReportDAO extends BaseDAO<PfpAdVideoReport, Integer> implements IAdVideoPerformanceReportDAO {

	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Object> getReportDataList(ReportQueryConditionVO reportQueryConditionVO) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.* from ");
		sql.append(" ( ");
		sql.append(" SELECT vr.ad_seq,  ");
		sql.append(" a.ad_status,  ");
		sql.append(" IFNULL((select d.ad_detail_content from pfp_ad_detail d where d.ad_detail_id = 'content' and d.ad_seq = vr.ad_seq ),'')title,   ");
		sql.append(" vr.ad_price_type,  ");
		sql.append(" vr.ad_pvclk_device,  ");
		sql.append(" SUM(vr.ad_pv),  ");
		sql.append(" SUM(vr.ad_view),  ");
		sql.append(" ROUND((SUM(vr.ad_view) / SUM(vr.ad_pv)) * 100, 2) ad_view_ratings,  ");
		sql.append(" Ifnull(TRUNCATE(SUM(vr.ad_price) / SUM(vr.ad_view), 4), 0),  ");
		sql.append(" TRUNCATE((SUM(vr.ad_price) / (SUM(vr.ad_pv)) * 1000), 4)thousands_cost,  ");
		sql.append(" SUM(vr.ad_price),  ");
		sql.append(" SUM(vr.ad_video_process_25),  ");
		sql.append(" SUM(vr.ad_video_process_50),  ");
		sql.append(" SUM(vr.ad_video_process_75),  ");
		sql.append(" SUM(vr.ad_video_process_100),  ");
		sql.append(" SUM(vr.ad_video_uniq),  ");
		sql.append(" SUM(vr.ad_video_music),  ");
		sql.append(" SUM(vr.ad_video_replay),  ");
		sql.append(" SUM(vr.ad_clk)ad_click,  ");
		sql.append(" Ifnull(  ");
		sql.append(" (SELECT d.ad_detail_content  ");
		sql.append(" FROM pfp_ad_detail d  ");
		sql.append(" WHERE d.ad_seq = a.ad_seq  ");
		sql.append(" AND d.ad_detail_id = 'img'), ''),  ");
		sql.append(" ROUND((SUM(vr.ad_video_process_100) / SUM(vr.ad_pv)) * 100, 2),  ");
		sql.append(" Ifnull(  ");
		sql.append(" (SELECT dd.ad_detail_content  ");
		sql.append(" FROM pfp_ad_detail dd  ");
		sql.append(" WHERE dd.ad_seq = vr.ad_seq  ");
		sql.append(" AND dd.ad_detail_id = 'real_url'), ''),  ");
		sql.append(" (SELECT dd.ad_detail_content  ");
		sql.append(" FROM pfp_ad_detail dd  ");
		sql.append(" WHERE dd.ad_seq = vr.ad_seq  ");
		sql.append(" AND dd.ad_detail_id = 'video_url'),  ");
		sql.append(" (SELECT dd.ad_detail_content  ");
		sql.append(" FROM pfp_ad_detail dd  ");
		sql.append(" WHERE dd.ad_seq = vr.ad_seq  ");
		sql.append(" AND dd.ad_detail_id = 'video_size')video_size,  ");
		sql.append(" (SELECT aa.ad_action_name  ");
		sql.append(" FROM pfp_ad_action aa  ");
		sql.append(" WHERE aa.ad_action_seq = g.ad_action_seq), vr.ad_video_date,  ");
		sql.append(" IFNULL(uniq_count,0),  ");
		sql.append(" (SELECT dd.ad_detail_content  ");
		sql.append(" FROM pfp_ad_detail dd  ");
		sql.append(" WHERE dd.ad_seq = vr.ad_seq  ");
		sql.append(" AND dd.ad_detail_id = 'video_seconds')  ");
		sql.append(" FROM pfp_ad_video_report vr  ");
		sql.append(" LEFT JOIN  ");
		sql.append(" (SELECT ud.uniq_name ad_seq,  ");
		sql.append(" sum(ud.uniq_count)uniq_count  ");
		sql.append(" FROM adm_uniq_data ud  ");
		sql.append(" WHERE ud.record_date >= :startDate  ");
		sql.append(" AND ud.record_date <= :endDate  ");
		sql.append(" GROUP BY ud.uniq_name)d ON d.ad_seq = vr.ad_seq,  ");
		sql.append(" pfp_ad a,  ");
		sql.append(" pfp_ad_group g  ");
		sql.append(" WHERE 1 = 1  ");
		sql.append(" AND vr.customer_info_id = :customerInfoId  ");
		sql.append(" AND vr.ad_seq = a.ad_seq  ");
		sql.append(" AND g.ad_group_seq = a.ad_group_seq  ");
		sql.append(" AND vr.ad_video_date >= :startDate And vr.ad_video_date <= :endDate "); 
		sql.append(" GROUP BY a.ad_seq  ");
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPvclkDevice())){
			sql.append(" ,vr.ad_pvclk_device  ");
		}
		
		sql.append(" )a WHERE 1 = 1 ");
		
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPriceType())){
			sql.append(" AND a.ad_price_type = :adPriceType "); 
		}
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdSize())){
			sql.append(" AND a.video_size = :adSize "); 
		}
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPvclkDevice())){
			sql.append(" AND a.ad_pvclk_device = :adPvclkDevice "); 
		}
		if (StringUtils.isNotBlank(reportQueryConditionVO.getSearchText())) {
			if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_include")){
				sql.append(" AND a.title like :searchText ");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_begin")){
				sql.append(" AND a.title like :searchText");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_same")){
				sql.append(" AND a.title  = :searchText ");
			}
		}
		sql.append(" ORDER BY a.ad_video_date DESC  ");
		sql.append(" LIMIT :rowStart, :rowEnd  ");
		
		log.info(">>>>>>table list sql = " + sql.toString());
		
		System.out.println(sessionFactory == null);
		System.out.println(super.getSessionFactory().getCurrentSession());
		System.out.println(super.getHibernateTemplate().getSessionFactory() == null );
		
		
		
		
		Query query =  super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("startDate", reportQueryConditionVO.getStartDate());
		query.setParameter("endDate", reportQueryConditionVO.getEndDate());
		query.setParameter("customerInfoId", reportQueryConditionVO.getCustomerInfoId());
		query.setParameter("startDate", reportQueryConditionVO.getStartDate());
		query.setParameter("endDate", reportQueryConditionVO.getEndDate());
		
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPriceType())){
			query.setParameter("adPriceType", reportQueryConditionVO.getAdPriceType());
		}
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdSize())){
			query.setParameter("adSize", reportQueryConditionVO.getAdSize());
		}
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPvclkDevice())){
			query.setParameter("adPvclkDevice", reportQueryConditionVO.getAdPvclkDevice());
		}
		if (StringUtils.isNotBlank(reportQueryConditionVO.getSearchText())) {
			if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_include")){
				query.setParameter("searchText", "%"+reportQueryConditionVO.getSearchText()+"%");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_begin")){
				query.setParameter("searchText", reportQueryConditionVO.getSearchText()+"%");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_same")){
				query.setParameter("searchText", reportQueryConditionVO.getSearchText());
			}
		}
		query.setParameter("rowStart", reportQueryConditionVO.getRowStart());
		query.setParameter("rowEnd", reportQueryConditionVO.getRowEnd());
		return query.list();
	}

	@Override
	public List<Object> getReportCount(ReportQueryConditionVO reportQueryConditionVO) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.* from ");
		sql.append(" ( ");
		sql.append(" SELECT vr.ad_seq,  ");
		sql.append(" a.ad_status,  ");
		sql.append(" IFNULL((select d.ad_detail_content from pfp_ad_detail d where d.ad_detail_id = 'content' and d.ad_seq = vr.ad_seq ),'')title,   ");
		sql.append(" vr.ad_price_type,  ");
		sql.append(" vr.ad_pvclk_device,  ");
		sql.append(" SUM(vr.ad_pv),  ");
		sql.append(" SUM(vr.ad_view),  ");
		sql.append(" TRUNCATE((SUM(vr.ad_view) / SUM(vr.ad_pv)) * 100, 2) ad_view_ratings,  ");
		sql.append(" Ifnull(TRUNCATE(SUM(vr.ad_price) / SUM(vr.ad_view), 2), 0),  ");
		sql.append(" (SUM(vr.ad_price) / SUM(vr.ad_pv)) * 1000 thousands_cost,  ");
		sql.append(" SUM(vr.ad_price),  ");
		sql.append(" SUM(vr.ad_video_process_25),  ");
		sql.append(" SUM(vr.ad_video_process_50),  ");
		sql.append(" SUM(vr.ad_video_process_75),  ");
		sql.append(" SUM(vr.ad_video_process_100),  ");
		sql.append(" SUM(vr.ad_video_uniq),  ");
		sql.append(" SUM(vr.ad_video_music),  ");
		sql.append(" SUM(vr.ad_video_replay),  ");
		sql.append(" SUM(vr.ad_clk)ad_click,  ");
		sql.append(" Ifnull(  ");
		sql.append(" (SELECT d.ad_detail_content  ");
		sql.append(" FROM pfp_ad_detail d  ");
		sql.append(" WHERE d.ad_seq = a.ad_seq  ");
		sql.append(" AND d.ad_detail_id = 'img'), ''),  ");
		sql.append(" TRUNCATE((SUM(vr.ad_video_process_100) / SUM(vr.ad_pv)) * 100, 2),  ");
		sql.append(" Ifnull(  ");
		sql.append(" (SELECT dd.ad_detail_content  ");
		sql.append(" FROM pfp_ad_detail dd  ");
		sql.append(" WHERE dd.ad_seq = vr.ad_seq  ");
		sql.append(" AND dd.ad_detail_id = 'real_url'), ''),  ");
		sql.append(" (SELECT dd.ad_detail_content  ");
		sql.append(" FROM pfp_ad_detail dd  ");
		sql.append(" WHERE dd.ad_seq = vr.ad_seq  ");
		sql.append(" AND dd.ad_detail_id = 'video_url'),  ");
		sql.append(" (SELECT dd.ad_detail_content  ");
		sql.append(" FROM pfp_ad_detail dd  ");
		sql.append(" WHERE dd.ad_seq = vr.ad_seq  ");
		sql.append(" AND dd.ad_detail_id = 'video_size')video_size,  ");
		sql.append(" (SELECT aa.ad_action_name  ");
		sql.append(" FROM pfp_ad_action aa  ");
		sql.append(" WHERE aa.ad_action_seq = g.ad_action_seq), vr.ad_video_date,  ");
		sql.append(" IFNULL(uniq_count,0),  ");
		sql.append(" (SELECT dd.ad_detail_content  ");
		sql.append(" FROM pfp_ad_detail dd  ");
		sql.append(" WHERE dd.ad_seq = vr.ad_seq  ");
		sql.append(" AND dd.ad_detail_id = 'video_seconds')  ");
		sql.append(" FROM pfp_ad_video_report vr  ");
		sql.append(" LEFT JOIN  ");
		sql.append(" (SELECT ud.uniq_name ad_seq,  ");
		sql.append(" sum(ud.uniq_count)uniq_count  ");
		sql.append(" FROM adm_uniq_data ud  ");
		sql.append(" WHERE ud.record_date >= :startDate  ");
		sql.append(" AND ud.record_date <= :endDate  ");
		sql.append(" GROUP BY ud.uniq_name)d ON d.ad_seq = vr.ad_seq,  ");
		sql.append(" pfp_ad a,  ");
		sql.append(" pfp_ad_group g  ");
		sql.append(" WHERE 1 = 1  ");
		sql.append(" AND vr.customer_info_id = :customerInfoId  ");
		sql.append(" AND vr.ad_seq = a.ad_seq  ");
		sql.append(" AND g.ad_group_seq = a.ad_group_seq  ");
		sql.append(" AND vr.ad_video_date >= :startDate And vr.ad_video_date <= :endDate "); 
		sql.append(" GROUP BY a.ad_seq  ");
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPvclkDevice())){
			sql.append(" ,vr.ad_pvclk_device  ");
		}
		sql.append(" )a WHERE 1 = 1 ");
		
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPriceType())){
			sql.append(" AND a.ad_price_type = :adPriceType "); 
		}
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdSize())){
			sql.append(" AND a.video_size = :adSize "); 
		}
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPvclkDevice())){
			sql.append(" AND a.ad_pvclk_device = :adPvclkDevice "); 
		}
		if (StringUtils.isNotBlank(reportQueryConditionVO.getSearchText())) {
			if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_include")){
				sql.append(" AND a.title like :searchText ");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_begin")){
				sql.append(" AND a.title like :searchText");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_same")){
				sql.append(" AND a.title  = :searchText ");
			}
		}
		sql.append(" ORDER BY a.ad_video_date DESC  ");
		
		log.info(">>>>>>table count sql = " + sql.toString());
		
		Query query =  super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("startDate", reportQueryConditionVO.getStartDate());
		query.setParameter("endDate", reportQueryConditionVO.getEndDate());
		query.setParameter("customerInfoId", reportQueryConditionVO.getCustomerInfoId());
		query.setParameter("startDate", reportQueryConditionVO.getStartDate());
		query.setParameter("endDate", reportQueryConditionVO.getEndDate());
		
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPriceType())){
			query.setParameter("adPriceType", reportQueryConditionVO.getAdPriceType());
		}
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdSize())){
			query.setParameter("adSize", reportQueryConditionVO.getAdSize());
		}
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPvclkDevice())){
			query.setParameter("adPvclkDevice", reportQueryConditionVO.getAdPvclkDevice());
		}
		if (StringUtils.isNotBlank(reportQueryConditionVO.getSearchText())) {
			if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_include")){
				query.setParameter("searchText", "%"+reportQueryConditionVO.getSearchText()+"%");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_begin")){
				query.setParameter("searchText", reportQueryConditionVO.getSearchText()+"%");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_same")){
				query.setParameter("searchText", reportQueryConditionVO.getSearchText());
			}
		}
		return query.list();
	}
	
	public List<Object> getReportChart(ReportQueryConditionVO reportQueryConditionVO) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.ad_seq, ");
		sql.append("  a.ad_video_date, ");
		sql.append("  a.title,   ");
		sql.append("  a.ad_price_type, ");
		sql.append("  SUM(a.ad_pv), ");
		sql.append("  SUM(a.ad_view), ");
		sql.append("  TRUNCATE((SUM(a.ad_view) / SUM(a.ad_pv)) * 100, 2) ad_view_ratings, ");
		sql.append("  IFNULL(TRUNCATE(SUM(a.ad_price) / SUM(a.ad_view), 4), 0), ");
		sql.append("  ROUND((SUM(a.ad_price) / (SUM(a.ad_pv)) * 1000), 4)thousands_cost, ");
		sql.append("  SUM(a.ad_price)ad_price, ");
		sql.append("  TRUNCATE((SUM(a.ad_video_process_100) / SUM(a.ad_pv)) * 100, 2), ");
		sql.append("  SUM(a.ad_click)ad_click, ");
		sql.append("  SUM(a.ad_video_music)ad_video_music, ");
		sql.append("  SUM(a.ad_video_replay)ad_video_replay, ");
		sql.append("  a.ad_action_name, ");
		sql.append("  SUM(a.ad_video_process_25), ");
		sql.append("  SUM(a.ad_video_process_50), ");
		sql.append("  SUM(a.ad_video_process_75), ");
		sql.append("  SUM(a.ad_video_process_100), ");
		sql.append("  a.video_size, ");
		sql.append("  SUM(IFNULL(ud.uniq_count,0))uniq_count ");
		sql.append(" FROM ( ");
		sql.append(" SELECT vr.ad_seq, ");
		sql.append(" vr.ad_video_date, ");
		sql.append(" IFNULL((select d.ad_detail_content from pfp_ad_detail d where d.ad_detail_id = 'content' and d.ad_seq = vr.ad_seq ),'')title, ");
		sql.append(" vr.ad_price_type, ");
		sql.append(" SUM(vr.ad_pv)ad_pv, ");
		sql.append(" SUM(vr.ad_view)ad_view, ");
		sql.append(" TRUNCATE((SUM(vr.ad_view) / SUM(vr.ad_pv)) * 100, 2) ad_view_ratings, ");
		sql.append(" IFNULL(TRUNCATE(SUM(vr.ad_price) / SUM(vr.ad_view), 2), 0), ");
		sql.append(" TRUNCATE(SUM(vr.ad_price) / (SUM(vr.ad_pv) / 1000), 2)thousands_cost, ");
		sql.append(" SUM(vr.ad_price)ad_price, ");
		sql.append(" TRUNCATE((SUM(vr.ad_video_process_100) / SUM(vr.ad_pv)) * 100, 2), ");
		sql.append(" SUM(vr.ad_clk)ad_click, ");
		sql.append(" SUM(vr.ad_video_music)ad_video_music, ");
		sql.append(" SUM(vr.ad_video_replay)ad_video_replay, ");
		sql.append(" (SELECT aa.ad_action_name ");
		sql.append(" FROM pfp_ad_action aa ");
		sql.append(" WHERE aa.ad_action_seq = g.ad_action_seq)ad_action_name, ");
		sql.append(" SUM(vr.ad_video_process_25)ad_video_process_25, ");
		sql.append(" SUM(vr.ad_video_process_50)ad_video_process_50, ");
		sql.append(" SUM(vr.ad_video_process_75)ad_video_process_75, ");
		sql.append(" SUM(vr.ad_video_process_100)ad_video_process_100, ");
		sql.append(" (SELECT dd.ad_detail_content ");
		sql.append("  FROM pfp_ad_detail dd ");
		sql.append(" WHERE dd.ad_seq = vr.ad_seq ");
		sql.append("   AND dd.ad_detail_id = 'video_size')video_size, ");
		sql.append("   vr.ad_pvclk_device ");
		sql.append(" FROM pfp_ad_video_report vr, ");
		sql.append(" pfp_ad a, ");
		sql.append(" pfp_ad_group g ");
		sql.append(" WHERE 1 = 1 ");
		sql.append(" AND vr.customer_info_id = :customerInfoId ");
		sql.append(" AND vr.ad_seq = a.ad_seq ");
		sql.append(" AND g.ad_group_seq = a.ad_group_seq ");
		sql.append(" AND vr.ad_video_date >= :startDate ");
		sql.append(" AND vr.ad_video_date <= :endDate ");
		sql.append(" GROUP BY vr.ad_seq, ");
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPvclkDevice())){
			sql.append(" vr.ad_pvclk_device,  ");
		}
		sql.append(" vr.ad_video_date, ");
		sql.append(" video_size, ");
		sql.append(" vr.ad_price_type ");
		sql.append(" ORDER BY vr.ad_video_date ASC )a left join adm_uniq_data ud on ud.record_date = a.ad_video_date and ud.uniq_name = a.ad_seq ");
		sql.append(" where 1 = 1 ");
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPriceType())){
			sql.append(" AND a.ad_price_type = :adPriceType "); 
		}
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdSize())){
			sql.append(" AND a.video_size = :adSize "); 
		}
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPvclkDevice())){
			sql.append(" AND a.ad_pvclk_device = :adPvclkDevice "); 
		}
		if (StringUtils.isNotBlank(reportQueryConditionVO.getSearchText())) {
			if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_include")){
				sql.append(" AND a.title like :searchText ");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_begin")){
				sql.append(" AND a.title like :searchText");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_same")){
				sql.append(" AND a.title  = :searchText ");
			}
		}
		sql.append(" GROUP BY a.ad_video_date");
		log.info(">>>>>>chart sql  = " + sql.toString());
		
		Query query =  super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		query.setParameter("customerInfoId", reportQueryConditionVO.getCustomerInfoId());
		if(StringUtils.isNotBlank(reportQueryConditionVO.getStartDate()) && StringUtils.isNotBlank(reportQueryConditionVO.getEndDate())){
			query.setParameter("startDate", reportQueryConditionVO.getStartDate());
			query.setParameter("endDate", reportQueryConditionVO.getEndDate());
		}
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPriceType())){
			query.setParameter("adPriceType", reportQueryConditionVO.getAdPriceType());
		}
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdSize())){
			query.setParameter("adSize", reportQueryConditionVO.getAdSize());
		}
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPvclkDevice())){
			query.setParameter("adPvclkDevice", reportQueryConditionVO.getAdPvclkDevice());
		}
		if (StringUtils.isNotBlank(reportQueryConditionVO.getSearchText())) {
			if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_include")){
				query.setParameter("searchText", "%"+reportQueryConditionVO.getSearchText()+"%");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_begin")){
				query.setParameter("searchText", reportQueryConditionVO.getSearchText()+"%");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_same")){
				query.setParameter("searchText", reportQueryConditionVO.getSearchText());
			}
		}
		return query.list();
	}

	/**
	 * 影音廣告成效(明細)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdVideoPerformanceList(AdVideoPerformanceReportVO vo) {
		String adClkPriceType = ""; // 計價方式
		String adDevice = ""; // 裝置
		String adSize = ""; // 尺寸
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			adClkPriceType = tempJSONObject.optString("adClkPriceType");
			adDevice = tempJSONObject.optString("adDevice");
			adSize = tempJSONObject.optString("adSize");
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.* from ( ");
		sql.append(" SELECT ");
		sql.append(" vr.ad_seq, ");
		sql.append(" a.ad_status, ");
		sql.append(" IFNULL((SELECT d.ad_detail_content FROM pfp_ad_detail d WHERE d.ad_detail_id = 'content' AND d.ad_seq = vr.ad_seq ),'') AS title, ");
		sql.append(" vr.ad_price_type, ");
		sql.append(" vr.ad_pvclk_device, ");
		sql.append(" SUM(vr.ad_pv) AS ad_pv_sum, ");
		sql.append(" SUM(vr.ad_view) AS ad_view_sum, ");
		sql.append(" SUM(vr.ad_price) AS ad_price_sum, ");
		sql.append(" SUM(vr.ad_video_process_25) AS ad_video_process_25_sum, ");
		sql.append(" SUM(vr.ad_video_process_50) AS ad_video_process_50_sum, ");
		sql.append(" SUM(vr.ad_video_process_75) AS ad_video_process_75_sum, ");
		sql.append(" SUM(vr.ad_video_process_100) AS ad_video_process_100_sum, ");
		sql.append(" SUM(vr.ad_video_music) AS ad_video_music_sum, ");
		sql.append(" SUM(vr.ad_video_replay) AS ad_video_replay_sum, ");
		sql.append(" SUM(vr.ad_clk) AS ad_clk_sum, ");
		sql.append(" IFNULL((SELECT d.ad_detail_content FROM pfp_ad_detail d WHERE d.ad_seq = a.ad_seq AND d.ad_detail_id = 'img'), '') AS img, ");
		sql.append(" IFNULL((SELECT dd.ad_detail_content FROM pfp_ad_detail dd WHERE dd.ad_seq = vr.ad_seq AND dd.ad_detail_id = 'real_url'), '') AS real_url, ");
		sql.append(" (SELECT dd.ad_detail_content FROM pfp_ad_detail dd WHERE dd.ad_seq = vr.ad_seq AND dd.ad_detail_id = 'video_url') AS video_url, ");
		sql.append(" (SELECT dd.ad_detail_content FROM pfp_ad_detail dd WHERE dd.ad_seq = vr.ad_seq AND dd.ad_detail_id = 'video_size') AS video_size, ");
		sql.append(" (SELECT aa.ad_action_name FROM pfp_ad_action aa WHERE aa.ad_action_seq = g.ad_action_seq) AS ad_action_name, ");
		sql.append(" g.ad_group_name, ");
		sql.append(" vr.ad_video_date, ");
		sql.append(" IFNULL(d.uniq_count, 0) AS uniq_count, ");
		sql.append(" (SELECT dd.ad_detail_content FROM pfp_ad_detail dd WHERE dd.ad_seq = vr.ad_seq AND dd.ad_detail_id = 'video_seconds') AS video_seconds ");
		sql.append(" FROM pfp_ad_video_report vr ");
		sql.append(" LEFT JOIN ");
		sql.append(" (SELECT ud.uniq_name ad_seq, ");
		sql.append("   SUM(ud.uniq_count)uniq_count ");
		sql.append("   FROM adm_uniq_data ud ");
		sql.append("   WHERE ud.record_date >= :startDate ");
		sql.append("   AND ud.record_date <= :endDate ");
		sql.append("   GROUP BY ud.uniq_name)d ON d.ad_seq = vr.ad_seq, ");
		sql.append(" pfp_ad a, ");
		sql.append(" pfp_ad_group g ");
		sql.append(" WHERE 1 = 1 ");
		sql.append(" AND vr.customer_info_id = :customerInfoId ");
		sql.append(" AND vr.ad_seq = a.ad_seq ");
		sql.append(" AND g.ad_group_seq = a.ad_group_seq ");
		sql.append(" AND vr.ad_video_date >= :startDate AND vr.ad_video_date <= :endDate "); 
		sql.append(" GROUP BY a.ad_seq ");
		
		// 裝置非電腦+行動時則將資料區分PC和mobile
		if (!"PCandMobile".equalsIgnoreCase(adDevice)) {
			sql.append(" ,vr.ad_pvclk_device  ");
		}
		
		sql.append(" )a WHERE 1 = 1 ");
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			sql.append(" AND a.title LIKE :searchStr ");
		}
		
		if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
			sql.append(" AND a.ad_price_type = :adClkPriceType");
		}
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			sql.append(" AND a.ad_pvclk_device = :adPvclkDevice");
		}
		
		if (StringUtils.isNotBlank(adSize) && !"all".equalsIgnoreCase(adSize)) {
			sql.append(" AND a.video_size = :adSize "); 
		}
		
		sql.append(" ORDER BY a.ad_video_date DESC ");
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());

		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
			query.setString("adClkPriceType", adClkPriceType);
		}
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			query.setString("adPvclkDevice", adDevice);
		}
		
		if (StringUtils.isNotBlank(adSize) && !"all".equalsIgnoreCase(adSize)) {
			query.setString("adSize", adSize);
		}
		
		if (!vo.isDownloadOrIsNotCuttingPagination()) { // 不是下載則做分頁處理
			// 取得分頁
			query.setFirstResult((vo.getPage() - 1) * vo.getPageSize());
			query.setMaxResults(vo.getPageSize());
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 影音廣告成效(加總)
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdVideoPerformanceListSum(AdVideoPerformanceReportVO vo) {
		String adClkPriceType = ""; // 計價方式
		String adDevice = ""; // 裝置
		String adSize = ""; // 尺寸
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			adClkPriceType = tempJSONObject.optString("adClkPriceType");
			adDevice = tempJSONObject.optString("adDevice");
			adSize = tempJSONObject.optString("adSize");
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.* from ( ");
		sql.append(" SELECT ");
		sql.append(" vr.ad_seq, ");
		sql.append(" IFNULL((SELECT d.ad_detail_content FROM pfp_ad_detail d WHERE d.ad_detail_id = 'content' AND d.ad_seq = vr.ad_seq ),'') AS title, ");
		sql.append(" vr.ad_price_type, ");
		sql.append(" vr.ad_pvclk_device, ");
		sql.append(" SUM(vr.ad_pv) AS ad_pv_sum, ");
		sql.append(" SUM(vr.ad_view) AS ad_view_sum, ");
		sql.append(" SUM(vr.ad_price) AS ad_price_sum, ");
		sql.append(" SUM(vr.ad_video_process_25) AS ad_video_process_25_sum, ");
		sql.append(" SUM(vr.ad_video_process_50) AS ad_video_process_50_sum, ");
		sql.append(" SUM(vr.ad_video_process_75) AS ad_video_process_75_sum, ");
		sql.append(" SUM(vr.ad_video_process_100) AS ad_video_process_100_sum, ");
		sql.append(" SUM(vr.ad_video_music) AS ad_video_music_sum, ");
		sql.append(" SUM(vr.ad_video_replay) AS ad_video_replay_sum, ");
		sql.append(" SUM(vr.ad_clk) AS ad_clk_sum, ");
		sql.append(" (SELECT dd.ad_detail_content FROM pfp_ad_detail dd WHERE dd.ad_seq = vr.ad_seq AND dd.ad_detail_id = 'video_size') AS video_size, ");
		sql.append(" IFNULL(d.uniq_count, 0) AS uniq_count ");
		sql.append(" FROM pfp_ad_video_report vr ");
		sql.append(" LEFT JOIN ");
		sql.append(" (SELECT ud.uniq_name ad_seq, ");
		sql.append("   SUM(ud.uniq_count)uniq_count ");
		sql.append("   FROM adm_uniq_data ud ");
		sql.append("   WHERE ud.record_date >= :startDate ");
		sql.append("   AND ud.record_date <= :endDate ");
		sql.append("   GROUP BY ud.uniq_name)d ON d.ad_seq = vr.ad_seq, ");
		sql.append(" pfp_ad a, ");
		sql.append(" pfp_ad_group g ");
		sql.append(" WHERE 1 = 1 ");
		sql.append(" AND vr.customer_info_id = :customerInfoId ");
		sql.append(" AND vr.ad_seq = a.ad_seq ");
		sql.append(" AND g.ad_group_seq = a.ad_group_seq ");
		sql.append(" AND vr.ad_video_date >= :startDate AND vr.ad_video_date <= :endDate "); 
		sql.append(" GROUP BY a.ad_seq ");
		
		// 裝置非電腦+行動時則將資料區分PC和mobile
		if (!"PCandMobile".equalsIgnoreCase(adDevice)) {
			sql.append(" ,vr.ad_pvclk_device  ");
		}
		
		sql.append(" )a WHERE 1 = 1 ");
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			sql.append(" AND a.title LIKE :searchStr ");
		}
		
		if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
			sql.append(" AND a.ad_price_type = :adClkPriceType");
		}
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			sql.append(" AND a.ad_pvclk_device = :adPvclkDevice");
		}
		
		if (StringUtils.isNotBlank(adSize) && !"all".equalsIgnoreCase(adSize)) {
			sql.append(" AND a.video_size = :adSize "); 
		}
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());

		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
			query.setString("adClkPriceType", adClkPriceType);
		}
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			query.setString("adPvclkDevice", adDevice);
		}
		
		if (StringUtils.isNotBlank(adSize) && !"all".equalsIgnoreCase(adSize)) {
			query.setString("adSize", adSize);
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 影音廣告成效(圖表)
	 * @param vo
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getAdVideoPerformanceListChart(AdVideoPerformanceReportVO vo) {
		String adClkPriceType = ""; // 計價方式
		String adDevice = ""; // 裝置
		String adSize = ""; // 尺寸
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			adClkPriceType = tempJSONObject.optString("adClkPriceType");
			adDevice = tempJSONObject.optString("adDevice");
			adSize = tempJSONObject.optString("adSize");
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append("  a.ad_seq, ");
		sql.append("  a.ad_video_date, ");
		sql.append("  a.title, ");
		sql.append("  a.ad_price_type, ");
		sql.append("  SUM(a.ad_pv) AS ad_pv_sum, ");
		sql.append("  SUM(a.ad_view) AS ad_view_sum, ");
		sql.append("  SUM(a.ad_price) AS ad_price_sum, ");
		sql.append("  SUM(a.ad_click) AS ad_clk_sum, ");
		sql.append("  SUM(a.ad_video_music) AS ad_video_music_sum, ");
		sql.append("  SUM(a.ad_video_replay) AS ad_video_replay_sum, ");
		sql.append("  SUM(a.ad_video_process_100) AS ad_video_process_100_sum, ");
		sql.append("  a.video_size, ");
		sql.append("  SUM(IFNULL(ud.uniq_count,0)) AS uniq_count ");
		sql.append(" FROM ( ");
		sql.append(" SELECT ");
		sql.append(" vr.ad_seq, ");
		sql.append(" vr.ad_video_date, ");
		sql.append(" IFNULL((SELECT d.ad_detail_content FROM pfp_ad_detail d WHERE d.ad_detail_id = 'content' AND d.ad_seq = vr.ad_seq ),'')title, ");
		sql.append(" vr.ad_price_type, ");
		sql.append(" SUM(vr.ad_pv)ad_pv, ");
		sql.append(" SUM(vr.ad_view)ad_view, ");
		sql.append(" SUM(vr.ad_price)ad_price, ");
		sql.append(" SUM(vr.ad_clk)ad_click, ");
		sql.append(" SUM(vr.ad_video_music)ad_video_music, ");
		sql.append(" SUM(vr.ad_video_replay)ad_video_replay, ");
		sql.append(" SUM(vr.ad_video_process_100)ad_video_process_100, ");
		sql.append(" (SELECT dd.ad_detail_content ");
		sql.append("  FROM pfp_ad_detail dd ");
		sql.append("  WHERE dd.ad_seq = vr.ad_seq ");
		sql.append("  AND dd.ad_detail_id = 'video_size')video_size, ");
		sql.append("  vr.ad_pvclk_device ");
		sql.append(" FROM pfp_ad_video_report vr, pfp_ad a, pfp_ad_group g ");
		sql.append(" WHERE 1 = 1 ");
		sql.append(" AND vr.customer_info_id = :customerInfoId ");
		sql.append(" AND vr.ad_seq = a.ad_seq ");
		sql.append(" AND g.ad_group_seq = a.ad_group_seq ");
		sql.append(" AND vr.ad_video_date >= :startDate ");
		sql.append(" AND vr.ad_video_date <= :endDate ");
		sql.append(" GROUP BY vr.ad_seq ");
		
		// 裝置非電腦+行動時則將資料區分PC和mobile
		if (!"PCandMobile".equalsIgnoreCase(adDevice)) {
			sql.append(" ,vr.ad_pvclk_device ");
		}
		
		sql.append(" ,vr.ad_video_date ");
		sql.append(" ,video_size ");
		sql.append(" ,vr.ad_price_type ");
		sql.append(" ORDER BY vr.ad_video_date ASC )a LEFT JOIN adm_uniq_data ud ON ud.record_date = a.ad_video_date AND ud.uniq_name = a.ad_seq ");
		sql.append(" WHERE 1 = 1 ");

		if (StringUtils.isNotBlank(vo.getSearchText())) {
			sql.append(" AND a.title LIKE :searchStr ");
		}
		
		if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
			sql.append(" AND a.ad_price_type = :adClkPriceType");
		}
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			sql.append(" AND a.ad_pvclk_device = :adPvclkDevice");
		}
		
		if (StringUtils.isNotBlank(adSize) && !"all".equalsIgnoreCase(adSize)) {
			sql.append(" AND a.video_size = :adSize "); 
		}
		
		sql.append(" GROUP BY a.ad_video_date");
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
	
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
			query.setString("adClkPriceType", adClkPriceType);
		}
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			query.setString("adPvclkDevice", adDevice);
		}
		
		if (StringUtils.isNotBlank(adSize) && !"all".equalsIgnoreCase(adSize)) {
			query.setString("adSize", adSize);
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 影音廣告成效 尺寸下拉選單
	 * @param vo 
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getAdVideoPerformanceSizeList(AdVideoPerformanceReportVO vo) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.* from ( ");
		sql.append(" SELECT DISTINCT ");
		sql.append(" (SELECT dd.ad_detail_content FROM pfp_ad_detail dd WHERE dd.ad_seq = vr.ad_seq AND dd.ad_detail_id = 'video_size') AS video_size ");
		sql.append(" FROM pfp_ad_video_report vr, ");
		sql.append(" pfp_ad a, ");
		sql.append(" pfp_ad_group g ");
		sql.append(" WHERE 1 = 1 ");
		sql.append(" AND vr.customer_info_id = :customerInfoId ");
		sql.append(" AND vr.ad_seq = a.ad_seq ");
		sql.append(" AND g.ad_group_seq = a.ad_group_seq ");
		sql.append(" AND vr.ad_video_date >= :startDate AND vr.ad_video_date <= :endDate "); 
		sql.append(" GROUP BY a.ad_seq ");
		sql.append(" )a WHERE 1 = 1 ");
		
		sql.append(" ORDER BY a.video_size DESC ");
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(sql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());

		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
}