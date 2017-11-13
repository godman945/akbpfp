package com.pchome.akbpfp.db.dao.report;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdVideoReport;
import com.pchome.akbpfp.db.vo.report.ReportQueryConditionVO;

@Transactional
public class AdVideoPerformanceReportDAO extends BaseDAO<PfpAdVideoReport, Integer> implements IAdVideoPerformanceReportDAO {

	@Override
	public List<Object> getReportDataList(ReportQueryConditionVO reportQueryConditionVO) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.* from ");
		sql.append(" ( ");
		sql.append(" SELECT vr.ad_seq,  ");
		sql.append(" a.ad_status,  ");
		sql.append(" g.ad_group_name,  ");
		sql.append(" vr.ad_price_type,  ");
		sql.append(" vr.ad_pvclk_device,  ");
		sql.append(" SUM(vr.ad_pv),  ");
		sql.append(" SUM(vr.ad_view),  ");
		sql.append(" TRUNCATE((SUM(vr.ad_view) / SUM(vr.ad_pv)) * 100, 2) ad_view_ratings,  ");
		sql.append(" Ifnull(TRUNCATE(SUM(vr.ad_price) / SUM(vr.ad_view), 2), 0),  ");
		sql.append(" TRUNCATE(SUM(vr.ad_price) / (SUM(vr.ad_pv) / 1000), 2)thousands_cost,  ");
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
				sql.append(" AND a.ad_group_name like :searchText ");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_begin")){
				sql.append(" AND a.ad_group_name like :searchText");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_same")){
				sql.append(" AND a.ad_group_name  = :searchText ");
			}
		}
		
		sql.append(" ORDER BY a.ad_video_date DESC  ");
		
		log.info(">>>>>>table list sql = " + sql.toString());
		
		Query query =  super.getSession().createSQLQuery(sql.toString());
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

	@Override
	public List<Object> getReportCount(ReportQueryConditionVO reportQueryConditionVO,String type) throws Exception {
		StringBuilder sql = new StringBuilder();
		
		
		sql.append(" SELECT a.ad_seq, ");
		sql.append("  a.ad_video_date, ");
		sql.append("  a.ad_group_name, ");
		sql.append("  a.ad_price_type, ");
		sql.append("  SUM(a.ad_pv), ");
		sql.append("  SUM(a.ad_view), ");
		sql.append("  TRUNCATE((SUM(a.ad_view) / SUM(a.ad_pv)) * 100, 2) ad_view_ratings, ");
		sql.append("  IFNULL(TRUNCATE(SUM(a.ad_price) / SUM(a.ad_view), 2), 0), ");
		sql.append("  TRUNCATE(SUM(a.ad_price) / (SUM(a.ad_pv) / 1000), 2)thousands_cost, ");
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
		sql.append(" g.ad_group_name, ");
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
		sql.append("   AND dd.ad_detail_id = 'video_size')video_size ");
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
				sql.append(" AND a.ad_group_name like :searchText ");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_begin")){
				sql.append(" AND a.ad_group_name like :searchText");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_same")){
				sql.append(" AND a.ad_group_name  = :searchText ");
			}
		}
		if(type.equals("chart")){
			sql.append(" GROUP BY a.ad_video_date");
			log.info(">>>>>>chart sql  = " + sql.toString());
		}else{
			log.info(">>>>>>count sql  = " + sql.toString());
		}
		
		Query query =  super.getSession().createSQLQuery(sql.toString());
		
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
}
