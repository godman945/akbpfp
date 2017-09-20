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
	public List<Object> getReportList(ReportQueryConditionVO reportQueryConditionVO) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.ad_status, ");
		sql.append(" 	g.ad_group_name,  ");
		sql.append(" 	p.template_product_width, "); 
		sql.append(" 	p.template_product_height,	 ");
		sql.append(" 	d.ad_detail_content, "); 
		sql.append(" 	vr.ad_price_type, "); 
		sql.append(" 	vr.ad_pvclk_device, "); 
		sql.append(" 	SUM(vr.ad_pv), "); 
		sql.append(" 	SUM(vr.ad_view), "); 
		sql.append(" 	TRUNCATE(( SUM(vr.ad_view) / SUM(vr.ad_pv) ) * 100, 2)  ad_view_ratings, "); 
		sql.append(" 	TRUNCATE(SUM(vr.ad_price) / SUM(vr.ad_view),2), "); 
		sql.append(" 	TRUNCATE(SUM(vr.ad_price) / ( SUM(vr.ad_pv) / 1000 ), 2)thousands_cost, "); 
		sql.append(" 	SUM(vr.ad_price), "); 
		sql.append(" 	SUM(vr.ad_video_process_25), "); 
		sql.append(" 	SUM(vr.ad_video_process_50), "); 
		sql.append(" 	SUM(vr.ad_video_process_75), "); 
		sql.append(" 	SUM(vr.ad_video_process_100), "); 
		sql.append(" 	SUM(vr.ad_video_uniq),  ");
		sql.append(" 	SUM(vr.ad_video_music),  ");
		sql.append(" 	SUM(vr.ad_video_replay), "); 
		sql.append(" 	SUM(( CASE  ");
		sql.append(" 	WHEN vr.ad_price_type = 'CPC' "); 
		sql.append(" 		THEN vr.ad_clk "); 
		sql.append(" 		ELSE vr.ad_view "); 
		sql.append("	END )) ad_click, ");
		sql.append("	IFNULL((SELECT d.ad_detail_content ");
		sql.append("		FROM pfp_ad_detail d ");
		sql.append("			WHERE d.ad_seq = a.ad_seq ");
		sql.append("		AND d.ad_detail_id = 'img' ),''), ");
		sql.append("	TRUNCATE((SUM(vr.ad_video_process_100) / SUM(vr.ad_pv)) * 100,2), ");
		sql.append("	IFNULL((select dd.ad_detail_content from pfp_ad_detail dd where dd.ad_seq = vr.ad_seq and dd.ad_detail_id = 'real_url'),'') ");
		sql.append(" FROM   pfp_ad_video_report vr, "); 
		sql.append(" 	pfp_ad a, "); 
		sql.append(" 	pfp_ad_group g,"); 
		sql.append(" 	adm_template_product p,"); 
		sql.append(" 	pfp_ad_detail d "); 
		sql.append(" WHERE  1 = 1 ");
		sql.append("	AND vr.customer_info_id = :customerInfoId ");
		sql.append("	AND vr.ad_seq = a.ad_seq ");
		sql.append(" 	AND d.ad_seq = a.ad_seq "); 
		sql.append(" 	AND vr.ad_seq = d.ad_seq ");
		sql.append(" 	AND g.ad_group_seq = a.ad_group_seq "); 
		sql.append(" 	AND p.template_product_seq = a.template_product_seq "); 
		sql.append(" 	AND d.ad_detail_id = 'video_url' ");
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPriceType())){
			sql.append(" AND vr.ad_price_type = :adPriceType "); 
		}
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdSize())){
			sql.append(" AND CONCAT(p.template_product_width,p.template_product_height) = :adSize "); 
		}
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPvclkDevice())){
			sql.append(" AND vr.ad_pvclk_device = :adPvclkDevice "); 
		}
		if (StringUtils.isNotBlank(reportQueryConditionVO.getSearchText())) {
			if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_include")){
				sql.append(" AND g.ad_group_name like :searchText ");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_begin")){
				sql.append(" AND g.ad_group_name like :searchText");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_same")){
				sql.append(" AND g.ad_group_name  = :searchText ");
			}
		}
		
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdSize())){
			
		}
		sql.append(" 	GROUP  BY vr.ad_seq "); 
		
		
		log.info(">>>>>>table sql = " + sql.toString());
		
		Query query =  super.getSession().createSQLQuery(sql.toString());
		query.setParameter("customerInfoId", reportQueryConditionVO.getCustomerInfoId());
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
	public List<Object> getReportChartList(ReportQueryConditionVO reportQueryConditionVO) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT STR_TO_DATE(DATE_FORMAT(vr.create_date, '%Y/%m/%d'), '%Y/%m/%d'), "); 
		sql.append("	SUM(vr.ad_pv), "); 
		sql.append(" 	SUM(vr.ad_view), "); 
		sql.append(" 	TRUNCATE((SUM(vr.ad_view) / SUM(vr.ad_pv)) * 100, 2) ad_view_ratings, "); 
		sql.append(" 	TRUNCATE(SUM(vr.ad_price) / SUM(vr.ad_view),2), "); 
		sql.append(" 	TRUNCATE(SUM(vr.ad_price) / (SUM(vr.ad_pv) / 1000), 2)thousands_cost, "); 
		sql.append(" 	SUM(vr.ad_price), "); 
		sql.append(" 	SUM(vr.ad_video_uniq), "); 
		sql.append(" 	SUM(vr.ad_video_music), "); 
		sql.append(" 	SUM(vr.ad_video_replay), "); 
		sql.append(" 	SUM((CASE "); 
		sql.append(" 		WHEN vr.ad_price_type = 'CPC' "); 
		sql.append(" 		THEN vr.ad_clk "); 
		sql.append(" 			ELSE vr.ad_view "); 
		sql.append(" 		END)) ad_click, ");
		sql.append("	TRUNCATE((SUM(vr.ad_video_process_100) / SUM(vr.ad_pv)) * 100,2) ");
		sql.append(" FROM pfp_ad_video_report vr, "); 
		sql.append(" 	pfp_ad a, ");
		sql.append(" 	adm_template_product p,");
		sql.append(" 	pfp_ad_group g ");
		sql.append(" WHERE 1 = 1  ");
		sql.append("	AND vr.customer_info_id = :customerInfoId ");
		sql.append(" 	AND vr.ad_seq = a.ad_seq ");
		sql.append(" 	AND g.ad_group_seq = a.ad_group_seq ");
		sql.append(" 	AND p.template_product_seq = a.template_product_seq "); 
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPriceType())){
			sql.append(" AND vr.ad_price_type = :adPriceType "); 
		}
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdSize())){
			sql.append(" AND CONCAT(p.template_product_width,p.template_product_height) = :adSize "); 
		}
		if(StringUtils.isNotBlank(reportQueryConditionVO.getAdPvclkDevice())){
			sql.append(" AND vr.ad_pvclk_device = :adPvclkDevice "); 
		}
		if (StringUtils.isNotBlank(reportQueryConditionVO.getSearchText())) {
			if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_include")){
				sql.append(" AND g.ad_group_name like :searchText ");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_begin")){
				sql.append(" AND g.ad_group_name like :searchText");
			}else if(reportQueryConditionVO.getAdSearchWay().equals("adsearch_same")){
				sql.append(" AND g.ad_group_name  = :searchText ");
			}
		}
		
		sql.append(" GROUP BY DATE_FORMAT(vr.create_date, '%Y/%m/%d') "); 
		
		log.info(">>>>>>chart sql = " + sql.toString());
		Query query =  super.getSession().createSQLQuery(sql.toString());
		query.setParameter("customerInfoId", reportQueryConditionVO.getCustomerInfoId());
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
