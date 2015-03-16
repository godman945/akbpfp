package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.pchome.akbpfp.db.pojo.PfpAdGroupReport;

@Transactional
public class AdGroupReportDAO extends BaseDAO<PfpAdGroupReport, Integer> implements IAdGroupReportDAO {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public List<AdGroupReportVO> getReportList(final String sqlType, final String adActionSeq, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String startDate, final String endDate, final int page, final int pageSize) throws Exception{

		List<AdGroupReportVO> result = (List<AdGroupReportVO>) getHibernateTemplate().execute(
				new HibernateCallback<List<AdGroupReportVO>>() {
					public List<AdGroupReportVO>  doInHibernate(Session session) throws HibernateException, SQLException {

						log.info(">>> sqlType = " + sqlType);

						String hqlStr = "";
                        HashMap<String, Object> sqlParams = new HashMap<String, Object>();

						if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_COUNT.getTextValue())) {

							//總廣告成效 -> 報表類型:分類 -> 數量及加總
							//分類成效 (數量及加總)
							try {
								sqlParams = getAdGroupReportCountStr(adActionSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//hqlStr = getAdGroupReportCountStr(adActionSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT.getTextValue())) {

							//總廣告成效 -> 報表類型:分類 -> 資料
							//分類成效 (資料)
							try {
								sqlParams = getAdGroupReportDataSQL(adActionSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//hqlStr = getAdGroupReportDataSQL(adActionSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_CHART.getTextValue())) {

							//總廣告成效 -> 報表類型:分類 -> 圖表
							//分類成效 (圖表)
							try {
								sqlParams = getAdGroupReportChartSQL(adActionSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//hqlStr = getAdGroupReportChartSQL(adActionSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							
						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_DAILY_COUNT.getTextValue())) {

							//每日廣告成效 (數量及加總)
							try {
								sqlParams = getDailyCountHQLStr(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//hqlStr = getDailyCountHQLStr(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_DAILY.getTextValue())) {

							//每日廣告成效 (資料)
							try {
								sqlParams = getDailyHQLStr(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//hqlStr = getDailyHQLStr(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);

						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_DAILY_CHART.getTextValue())) {

							//每日廣告成效 (圖表)
							try {
								sqlParams = getDailyChartHQLStr(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//hqlStr = getDailyChartHQLStr(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
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
						//log.info(">> resultData_size  = "+resultData.size());


//
//						Query q = session.createSQLQuery(hqlStr);
//
//						//page=-1 取得全部不分頁用於download
//						if (page!=-1) {
//							q.setFirstResult((page-1)*pageSize).setMaxResults(pageSize);
//						}
//
//						List<Object> dataList = q.list();
//						log.info(">>> dataList.size() = " + dataList.size());

						List<AdGroupReportVO> resultData = new ArrayList<AdGroupReportVO>();

						if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_COUNT.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								BigDecimal pv = (BigDecimal) objArray[0];
								BigDecimal click = (BigDecimal) objArray[1];
								Double cost = (Double) objArray[2];
								BigDecimal invClick = (BigDecimal) objArray[3];

								AdGroupReportVO vo = new AdGroupReportVO();

								vo.setAdPvSum(pv);
								vo.setAdClkSum(click);
								vo.setAdPriceSum(cost);
								vo.setAdInvClkSum(invClick);

								resultData.add(vo);
							}

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								BigDecimal pv = (BigDecimal) objArray[0];
								BigDecimal click = (BigDecimal) objArray[1];
								Double cost = (Double) objArray[2];
								BigDecimal invClick = (BigDecimal) objArray[3];
								BigInteger count = (BigInteger) objArray[5];
								String adGroupSeq = (String) objArray[6];
								String adDevice = (String) objArray[7];

								AdGroupReportVO vo = new AdGroupReportVO();

								vo.setAdPvSum(pv);
								vo.setAdClkSum(click);
								vo.setAdPriceSum(cost);
								vo.setAdInvClkSum(invClick);
								vo.setCount(count);
								vo.setAdGroupSeq(adGroupSeq);
								if(StringUtils.isNotBlank(adPvclkDevice)) {
									vo.setAdDevice(adDevice);
								} else {
									vo.setAdDevice("全部");
								}

								resultData.add(vo);
							}

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_CHART.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								Date reportDate = (Date) objArray[0];
								BigDecimal pv = (BigDecimal) objArray[1];
								BigDecimal click = (BigDecimal) objArray[2];
								Double cost = (Double) objArray[3];
								BigDecimal invClick = (BigDecimal) objArray[4];

								AdGroupReportVO vo = new AdGroupReportVO();

								vo.setReportDate(reportDate);
								vo.setAdPvSum(pv);
								vo.setAdClkSum(click);
								vo.setAdPriceSum(cost);
								vo.setAdInvClkSum(invClick);

								resultData.add(vo);
							}

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_DAILY_COUNT.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								BigDecimal pv = (BigDecimal) objArray[0];
								BigDecimal click = (BigDecimal) objArray[1];
								Double cost = (Double) objArray[2];
								BigDecimal invClick = (BigDecimal) objArray[3];

								AdGroupReportVO vo = new AdGroupReportVO();
								
								vo.setAdPvSum(pv);
								vo.setAdClkSum(click);
								vo.setAdPriceSum(cost);
								vo.setAdInvClkSum(invClick);
								
								resultData.add(vo);

							}

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_DAILY.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								Date reportDate = (Date) objArray[0];
								BigDecimal pv = (BigDecimal) objArray[1];
								BigDecimal click = (BigDecimal) objArray[2];
								Double cost = (Double) objArray[3];
								BigDecimal invClick = (BigDecimal) objArray[4];

								AdGroupReportVO vo = new AdGroupReportVO();

								vo.setReportDate(reportDate);
								vo.setAdPvSum(pv);
								vo.setAdClkSum(click);
								vo.setAdPriceSum(cost);
								vo.setAdInvClkSum(invClick);
								
								resultData.add(vo);

							}

						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_DAILY_CHART.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								Date reportDate = (Date) objArray[0];
								BigDecimal pv = (BigDecimal) objArray[1];
								BigDecimal click = (BigDecimal) objArray[2];
								Double cost = (Double) objArray[3];
								BigDecimal invClick = (BigDecimal) objArray[4];

								AdGroupReportVO vo = new AdGroupReportVO();

								vo.setReportDate(reportDate);
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

	private HashMap<String, Object> getAdGroupReportCountStr(String adActionSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice,
			String customerInfoId, String startDate, String endDate) throws Exception {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select ");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum(r.ad_clk), ");				// 產生pfp_ad_group_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_group_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price) ");
		hql.append(" from pfp_ad_group_report as r ");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id = :customerInfoId ");
		hql.append(" and r.ad_pvclk_date >= :startDate ");
		hql.append(" and r.ad_pvclk_date <= :endDate ");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", sdf.parse(startDate));
		sqlParams.put("endDate", sdf.parse(endDate));

		if (StringUtils.isNotBlank(adActionSeq)) {
			hql.append(" and r.ad_action_seq = :adActionSeq ");
			sqlParams.put("adActionSeq", adActionSeq);
		}

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
			hql.append(" and r.ad_group_seq in (select ad_group_seq from pfp_ad_group where 1=1");
			hql.append(" and customer_info_id = :customerInfoId ");
			hql.append(" and ad_group_name like :searchStr )");
			sqlParams.put("searchStr", searchStr);
		}


		hql.append(" group by r.ad_group_seq");
		if(StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" , r.ad_pvclk_device");
		}
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private HashMap<String, Object> getAdGroupReportDataSQL(String adActionSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice,
			String customerInfoId, String startDate, String endDate) throws ParseException {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select ");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum(r.ad_clk), ");				// 產生pfp_ad_group_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_group_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" count(r.ad_group_report_seq), ");
		hql.append(" r.ad_group_seq, ");
		hql.append(" r.ad_pvclk_device ");
		hql.append(" from pfp_ad_group_report as r ");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id =:customerInfoId ");
		hql.append(" and r.ad_pvclk_date >=:startDate ");
		hql.append(" and r.ad_pvclk_date <=:endDate ");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", sdf.parse(startDate));
		sqlParams.put("endDate", sdf.parse(endDate));

		if (StringUtils.isNotBlank(adActionSeq)) {
			hql.append(" and r.ad_action_seq = :adActionSeq ");
			sqlParams.put("adActionSeq", adActionSeq);
		}

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
			hql.append(" and r.ad_group_seq in (select ad_group_seq from pfp_ad_group where 1=1");
			hql.append(" and customer_info_id = :customerInfoId ");
			hql.append(" and ad_group_name like :searchStr )");
			sqlParams.put("searchStr", searchStr);
		}

		hql.append(" group by r.ad_group_seq");
		if(StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" , r.ad_pvclk_device");
		}
		hql.append(" order by r.ad_group_seq desc");
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private HashMap<String, Object> getAdGroupReportChartSQL(String adActionSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice,
			String customerInfoId, String startDate, String endDate) throws ParseException {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" r.ad_pvclk_date,");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum(r.ad_clk), ");				// 產生pfp_ad_group_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_group_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" count(r.ad_group_report_seq) ");
		hql.append(" from pfp_ad_group_report as r ");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id =:customerInfoId ");
		hql.append(" and r.ad_pvclk_date >=:startDate ");
		hql.append(" and r.ad_pvclk_date <=:endDate ");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", sdf.parse(startDate));
		sqlParams.put("endDate", sdf.parse(endDate));

		if (StringUtils.isNotBlank(adActionSeq)) {
			hql.append(" and r.ad_action_seq = :adActionSeq ");
			sqlParams.put("adActionSeq", adActionSeq);
		}

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
			hql.append(" and r.ad_group_seq in (select ad_group_seq from pfp_ad_group where 1=1");
			hql.append(" and customer_info_id = :customerInfoId ");
			hql.append(" and ad_group_name like :searchStr )");
			sqlParams.put("searchStr", searchStr);
		}


		hql.append(" group by r.ad_pvclk_date");
		if(StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" , r.ad_pvclk_device");
		}
		hql.append(" order by r.ad_pvclk_date");
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private HashMap<String, Object> getDailyCountHQLStr(final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String startDate, final String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum(r.ad_clk), ");				// 產生pfp_ad_group_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_group_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price) ");
		hql.append(" from pfp_ad_group_report as r ");
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
//			if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
//				hql.append(" and ad_type = " + adShowWay);
//			}
			hql.append(" and ad_action_name like :searchStr )");
			sqlParams.put("searchStr", searchStr);
		}

		hql.append(" group by r.ad_pvclk_date");
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private HashMap<String, Object> getDailyHQLStr(final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String startDate, final String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" r.ad_pvclk_date,");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum(r.ad_clk), ");				// 產生pfp_ad_action_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_action_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price) ");
		hql.append(" from pfp_ad_group_report as r ");
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
//			if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
//				hql.append(" and ad_type = " + adShowWay);
//			}
			hql.append(" and ad_action_name like :searchStr )");
			sqlParams.put("searchStr", searchStr);
		}

		hql.append(" group by r.ad_pvclk_date");
		hql.append(" order by r.ad_pvclk_date desc");
		sqlParams.put("sql", hql);

		return sqlParams;
	}


	private HashMap<String, Object> getDailyChartHQLStr(final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String startDate, final String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" r.ad_pvclk_date,");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum(r.ad_clk), ");				// 產生pfp_ad_action_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_action_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price) ");
		hql.append(" from pfp_ad_group_report as r ");
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
//			if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
//				hql.append(" and ad_type = " + adShowWay);
//			}
			hql.append(" and ad_action_name like :searchStr )");
			sqlParams.put("searchStr", searchStr);
		}

		hql.append(" group by r.ad_pvclk_date");
		hql.append(" order by r.ad_pvclk_date");
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
