package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdWebsiteReport;

@Transactional
public class AdWebsiteReportDAO extends BaseDAO<PfpAdWebsiteReport, Integer> implements IAdWebsiteReportDAO {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public List<AdWebsiteReportVO> getReportList(final String sqlType, final String searchWebsiteCode, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String startDate, final String endDate, final int page, final int pageSize) throws Exception{

		List<AdWebsiteReportVO> result = (List<AdWebsiteReportVO>) getHibernateTemplate().execute(
				new HibernateCallback<List<AdWebsiteReportVO>>() {
					public List<AdWebsiteReportVO>  doInHibernate(Session session) throws HibernateException, SQLException {

						log.info(">>> sqlType = " + sqlType);

						String hqlStr = "";
                        HashMap<String, Object> sqlParams = new HashMap<String, Object>();

						if (sqlType.equals(EnumReport.REPORT_HQLTYPE_WEBSITE_COUNT.getTextValue())) {

							//每日廣告成效 (數量及加總)
							try {
								sqlParams = getWebsiteCountHQLStr(searchWebsiteCode,searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_WEBSITE.getTextValue())) {

							//每日廣告成效 (資料)
							try {
								sqlParams = getWebsiteHQLStr(searchWebsiteCode, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_WEBSITE_CHART.getTextValue())) {

							//每日廣告成效 (圖表)
							try {
								sqlParams = getWebsiteChartHQLStr(searchWebsiteCode,searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						hqlStr = sqlParams.get("sql").toString();
						log.info(">>> hqlStr = " + hqlStr);

						List<Object> dataList = null;

						Query query = session.createSQLQuery(hqlStr);
				        for (String paramName:sqlParams.keySet()) {
				        	System.out.println(paramName + " = " + sqlParams.get(paramName));
				        	if(!paramName.equals("sql")) {
				        		query.setParameter(paramName, sqlParams.get(paramName));
				        	}
				        }

						//page > -1 取得分頁用於download
						if(page > -1){
							query.setFirstResult((page-1)*pageSize);
							query.setMaxResults(pageSize);
						}

						dataList = query.list();
						
						List<AdWebsiteReportVO> resultData = new ArrayList<AdWebsiteReportVO>();

						if (sqlType.equals(EnumReport.REPORT_HQLTYPE_WEBSITE_COUNT.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								BigDecimal pv = (BigDecimal) objArray[0];
								BigDecimal click = (BigDecimal) objArray[1];
								Double cost = (Double) objArray[2];
								BigDecimal invClick = (BigDecimal) objArray[3];

								AdWebsiteReportVO vo = new AdWebsiteReportVO();
								
								vo.setAdPvSum(pv);
								vo.setAdClkSum(click);
								vo.setAdPriceSum(cost);
								vo.setAdInvClkSum(invClick);
								
								resultData.add(vo);

							}

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_WEBSITE.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								String websiteCategoryCode	 ="";
								if(objArray[0] != null){
									websiteCategoryCode = objArray[0].toString();
								}
								
								BigDecimal pv = (BigDecimal) objArray[1];
								BigDecimal click = (BigDecimal) objArray[2];
								Double cost = (Double) objArray[3];
								BigDecimal invClick = (BigDecimal) objArray[4];
								String adActionSeq = objArray[6].toString();
								String adGroupSeq = objArray[7].toString();
								String adDevice = objArray[8].toString();

								AdWebsiteReportVO vo = new AdWebsiteReportVO();

								vo.setWebsiteCategoryCode(websiteCategoryCode);
								vo.setAdPvSum(pv);
								vo.setAdClkSum(click);
								vo.setAdPriceSum(cost);
								vo.setAdInvClkSum(invClick);
								vo.setAdActionSeq(adActionSeq);
								vo.setAdGroupSeq(adGroupSeq);
								if(StringUtils.isNotBlank(adPvclkDevice)) {
									if("PC".equals(adDevice)){
										vo.setAdDevice("電腦");
									}else if("mobile".equals(adDevice)){
										vo.setAdDevice("行動裝置");
									} else {
										vo.setAdDevice(adDevice);	
									}
								} else {
									vo.setAdDevice("全部");
								}
								
								resultData.add(vo);

							}

						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_WEBSITE_CHART.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								String websiteCategoryCode	 ="";
								if(objArray[0] != null){
									websiteCategoryCode = objArray[0].toString();
								}
								
								BigDecimal pv = (BigDecimal) objArray[1];
								BigDecimal click = (BigDecimal) objArray[2];
								Double cost = (Double) objArray[3];
								BigDecimal invClick = (BigDecimal) objArray[4];

								AdWebsiteReportVO vo = new AdWebsiteReportVO();

								vo.setWebsiteCategoryCode(websiteCategoryCode);
								vo.setAdPvSum(pv);
								vo.setAdClkSum(click);
								vo.setAdPriceSum(cost);
								vo.setAdInvClkSum(invClick);
								
								resultData.add(vo);

							}
						}

						return resultData;

					}
				}
		);

		return result;
	}

	private HashMap<String, Object> getWebsiteCountHQLStr(final String searchWebsiteCode, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String startDate, final String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum(r.ad_clk), ");				// 產生pfp_ad_website_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_website_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price) ");
		hql.append(" from pfp_ad_website_report as r ");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id =:customerInfoId ");
		hql.append(" and r.ad_pvclk_date >=:startDate ");
		hql.append(" and r.ad_pvclk_date <=:endDate ");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", sdf.parse(startDate));
		sqlParams.put("endDate", sdf.parse(endDate));

		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" and r.ad_type = :adShowWay");
			sqlParams.put("adShowWay", adShowWay);
		}

		if (StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" and r.ad_pvclk_device = :adPvclkDevice ");
			sqlParams.put("adPvclkDevice", adPvclkDevice);
		}

		if (StringUtils.isNotEmpty(searchText)) {
			String searchStr = getSearchText(searchText, adSearchWay);
			hql.append(" and r.ad_action_seq in (select ad_action_seq from pfp_ad_action where 1=1");
			hql.append(" and customer_info_id = :customerInfoId ");
			hql.append(" and ad_action_name like :searchStr )");
			sqlParams.put("searchStr", searchStr);
		}

		if(StringUtils.isNotEmpty(searchWebsiteCode)){
			hql.append(" and r.website_category_code = :websiteCategoryCode ");
			sqlParams.put("websiteCategoryCode", searchWebsiteCode);
		}
		
		hql.append(" group by r.ad_action_seq, r.ad_group_seq, r.website_category_code");
		
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private HashMap<String, Object> getWebsiteHQLStr(final String searchWebsiteCode, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String startDate, final String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" r.website_category_code,");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum(r.ad_clk), ");				// 產生pfp_ad_website_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_website_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" r.ad_action_seq, ");
		hql.append(" r.ad_group_seq, ");
		hql.append(" r.ad_pvclk_device ");
		hql.append(" from pfp_ad_website_report as r ");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id =:customerInfoId ");
		hql.append(" and r.ad_pvclk_date >=:startDate ");
		hql.append(" and r.ad_pvclk_date <=:endDate ");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", sdf.parse(startDate));
		sqlParams.put("endDate", sdf.parse(endDate));

		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" and r.ad_type = :adShowWay");
			sqlParams.put("adShowWay", adShowWay);
		}

		if (StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" and r.ad_pvclk_device = :adPvclkDevice ");
			sqlParams.put("adPvclkDevice", adPvclkDevice);
		}

		if (StringUtils.isNotEmpty(searchText)) {
			String searchStr = getSearchText(searchText, adSearchWay);
			hql.append(" and r.ad_action_seq in (select ad_action_seq from pfp_ad_action where 1=1");
			hql.append(" and customer_info_id = :customerInfoId ");
			hql.append(" and ad_action_name like :searchStr )");
			sqlParams.put("searchStr", searchStr);
		}

		if(StringUtils.isNotEmpty(searchWebsiteCode)){
			hql.append(" and r.website_category_code = :websiteCategoryCode ");
			sqlParams.put("websiteCategoryCode", searchWebsiteCode);
		}
		
		hql.append(" group by r.ad_action_seq, r.ad_group_seq, r.website_category_code");
		hql.append(" order by r.ad_action_seq, r.ad_group_seq, r.website_category_code");
		
		sqlParams.put("sql", hql);

		return sqlParams;
	}


	private HashMap<String, Object> getWebsiteChartHQLStr(final String searchWebsiteCode, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String startDate, final String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" r.website_category_code,");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum(r.ad_clk), ");				// 產生pfp_ad_website_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_website_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price) ");
		hql.append(" from pfp_ad_website_report as r ");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id =:customerInfoId ");
		hql.append(" and r.ad_pvclk_date >=:startDate ");
		hql.append(" and r.ad_pvclk_date <=:endDate ");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", sdf.parse(startDate));
		sqlParams.put("endDate", sdf.parse(endDate));

		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" and r.ad_type = :adShowWay");
			sqlParams.put("adShowWay", adShowWay);
		}

		if (StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" and r.ad_pvclk_device = :adPvclkDevice ");
			sqlParams.put("adPvclkDevice", adPvclkDevice);
		}

		if (StringUtils.isNotEmpty(searchText)) {
			String searchStr = getSearchText(searchText, adSearchWay);
			hql.append(" and r.ad_action_seq in (select ad_action_seq from pfp_ad_action where 1=1");
			hql.append(" and customer_info_id = :customerInfoId ");
			hql.append(" and ad_action_name like :searchStr )");
			sqlParams.put("searchStr", searchStr);
		}

		if(StringUtils.isNotEmpty(searchWebsiteCode)){
			hql.append(" and r.website_category_code = :websiteCategoryCode ");
			sqlParams.put("websiteCategoryCode", searchWebsiteCode);
		}
		
		hql.append(" group by r.website_category_code");
		hql.append(" order by r.website_category_code");
		
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private String getSearchText(String searchText, String searchWay) {
		String searchStr = "";
		if (searchWay.equals(EnumReport.ADSEARCH_BEGIN.getTextValue())) {
			searchStr = searchText + "%";
		} else if (searchWay.equals(EnumReport.ADSEARCH_INCLUDE.getTextValue())) {
			searchStr = "%" + searchText + "%";
		} else {
			searchStr = searchText;
		}
		return searchStr;
	}
}