package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdGroupReport;
import com.pchome.enumerate.ad.EnumAdPriceType;
import com.pchome.enumerate.ad.EnumAdStyleType;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.report.EnumReport;

@Transactional
public class AdGroupReportDAO extends BaseDAO<PfpAdGroupReport, Integer> implements IAdGroupReportDAO {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public List<AdGroupReportVO> getReportList(final String sqlType, final String adActionSeq, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate, final int page, final int pageSize) throws Exception{

		List<AdGroupReportVO> result = (List<AdGroupReportVO>) getHibernateTemplate().execute(
				new HibernateCallback<List<AdGroupReportVO>>() {
					public List<AdGroupReportVO>  doInHibernate(Session session) throws HibernateException {

						log.info(">>> sqlType = " + sqlType);

						String hqlStr = "";
                        HashMap<String, Object> sqlParams = new HashMap<String, Object>();

						if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_COUNT.getTextValue())) {

							//總廣告成效 -> 報表類型:分類 -> 數量及加總
							//分類成效 (數量及加總)
							try {
								sqlParams = getAdGroupReportCountStr(adActionSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//hqlStr = getAdGroupReportCountStr(adActionSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT.getTextValue())) {

							//總廣告成效 -> 報表類型:分類 -> 資料
							//分類成效 (資料)
							try {
								sqlParams = getAdGroupReportDataSQL(adActionSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//hqlStr = getAdGroupReportDataSQL(adActionSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_CHART.getTextValue())) {

							//總廣告成效 -> 報表類型:分類 -> 圖表
							//分類成效 (圖表)
							try {
								sqlParams = getAdGroupReportChartSQL(adActionSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate);
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
						List<AdGroupReportVO> resultData = new ArrayList<AdGroupReportVO>();

						if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_COUNT.getTextValue())) {
							for (int i=0; i<dataList.size(); i++) {
								Object[] objArray = (Object[]) dataList.get(i);
								BigDecimal pv = (BigDecimal) objArray[0];
								BigDecimal click = (BigDecimal) objArray[1];
								Double cost = (Double) objArray[2];
								BigDecimal invClick = (BigDecimal) objArray[3];
								BigDecimal convertCount = (BigDecimal) objArray[5];
								BigDecimal convertPriceCount = (BigDecimal) objArray[6];
								
								AdGroupReportVO vo = new AdGroupReportVO();
								vo.setConvertCount(convertCount);
								vo.setConvertPriceCount(convertPriceCount);
								vo.setAdPvSum(pv);
								vo.setAdClkSum(click);
								vo.setAdPriceSum(cost);
								vo.setAdInvClkSum(invClick);
								resultData.add(vo);
							}
						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT.getTextValue())) {

							Map<String,String> adStyleTypeMap = new HashMap<String,String>();
							Map<String,String> adPriceTypeMap = new HashMap<String,String>();
							Map<Integer,String> adTypeMap = new HashMap<Integer,String>();
							adStyleTypeMap = getAdStyleTypeMap();
							adPriceTypeMap = getAdPriceTypeMap();
							adTypeMap = getAdType();
							
							for (int i=0; i<dataList.size(); i++) {
								Object[] objArray = (Object[]) dataList.get(i);
								BigDecimal pv = (BigDecimal) objArray[0];
								BigDecimal click = (BigDecimal) objArray[1];
								Double cost = Double.valueOf(objArray[2].toString());
								BigDecimal invClick = (BigDecimal) objArray[3];
								BigInteger count = (BigInteger) objArray[5];
								String adGroupSeq = (String) objArray[6];
								String adDevice = (String) objArray[7];
								String adOperatingRuleCode = objArray[8].toString();
								String adClkPriceType = objArray[9].toString();
								Integer adType = Integer.parseInt(objArray[10].toString());
								BigDecimal convertCount = (BigDecimal) objArray[11];
								BigDecimal convertPriceCount = (BigDecimal) objArray[12];
								
								AdGroupReportVO vo = new AdGroupReportVO();
								vo.setConvertCount(convertCount);
								vo.setConvertPriceCount(convertPriceCount);
								vo.setAdPvSum(pv);
								vo.setAdClkSum(click);
								vo.setAdPriceSum(cost);
								vo.setAdInvClkSum(invClick);
								vo.setCount(count);
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

								vo.setAdOperatingRule(adStyleTypeMap.get(adOperatingRuleCode));
								vo.setAdClkPriceType(adPriceTypeMap.get(adClkPriceType));
								vo.setAdType(adTypeMap.get(adType));
								
								resultData.add(vo);
							}

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_CHART.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);
								Date reportDate = (Date) objArray[0];
								BigDecimal pv = (BigDecimal) objArray[1];
								BigDecimal click = (BigDecimal) objArray[2];
								Double cost = Double.valueOf(objArray[3].toString());
								BigDecimal invClick = (BigDecimal) objArray[4];
								BigDecimal convertCount = (BigDecimal) objArray[7];
								BigDecimal convertPriceCount = (BigDecimal) objArray[8];
								
								AdGroupReportVO vo = new AdGroupReportVO();
								vo.setConvertCount(convertCount);
								vo.setConvertPriceCount(convertPriceCount);
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
			String customerInfoId, String adOperatingRule, String startDate, String endDate) throws Exception {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select ");
		hql.append(" sum(r.ad_pv), ");
		/* 產生pfp_ad_group_report 的時候，已經減過無效點擊數了，所以不用再減
		   20180510 檢查排程未減無效點擊數，因pfp很多程式都有引用報表數據，因此不能重跑排程，所以直接調整SQL部分，減無效點擊數(- r.ad_invalid_clk) */
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then (r.ad_clk - r.ad_invalid_clk) else r.ad_view end)), ");
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_group_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" SUM(r.convert_count),  ");
		hql.append(" SUM(r.convert_price_count) ");
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

		if (StringUtils.isNotBlank(adOperatingRule)) {
			hql.append(" and r.ad_operating_rule = :adOperatingRule ");
			sqlParams.put("adOperatingRule", adOperatingRule);
		}

		hql.append(" group by r.ad_group_seq");
		/*if(StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" , r.ad_pvclk_device");
		}*/
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private HashMap<String, Object> getAdGroupReportDataSQL(String adActionSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice,
			String customerInfoId, String adOperatingRule, String startDate, String endDate) throws ParseException {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select ");
		hql.append(" sum(r.ad_pv), ");
		/* 產生pfp_ad_group_report 的時候，已經減過無效點擊數了，所以不用再減
		   20180510 檢查排程未減無效點擊數，因pfp很多程式都有引用報表數據，因此不能重跑排程，所以直接調整SQL部分，減無效點擊數(- r.ad_invalid_clk) */
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then (r.ad_clk - r.ad_invalid_clk) else r.ad_view end)), ");
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_group_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" count(r.ad_group_report_seq), ");
		hql.append(" r.ad_group_seq, ");
		hql.append(" r.ad_pvclk_device, ");
		hql.append(" r.ad_operating_rule, ");
		hql.append(" r.ad_clk_price_type, ");
		hql.append(" r.ad_type, ");
		hql.append(" SUM(r.convert_count),  ");
		hql.append(" SUM(r.convert_price_count) ");
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

		if (StringUtils.isNotBlank(adOperatingRule)) {
			hql.append(" and r.ad_operating_rule = :adOperatingRule ");
			sqlParams.put("adOperatingRule", adOperatingRule);
		}
		
		hql.append(" group by r.ad_group_seq, r.ad_action_seq, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		/*if(StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" , r.ad_pvclk_device");
		}
		
		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" , r.ad_type ");
		}*/
		
		hql.append(" order by r.ad_group_seq desc, r.ad_action_seq, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private HashMap<String, Object> getAdGroupReportChartSQL(String adActionSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice,
			String customerInfoId, String adOperatingRule, String startDate, String endDate) throws ParseException {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" r.ad_pvclk_date,");
		hql.append(" sum(r.ad_pv), ");
		/* 產生pfp_ad_group_report 的時候，已經減過無效點擊數了，所以不用再減
		   20180510 檢查排程未減無效點擊數，因pfp很多程式都有引用報表數據，因此不能重跑排程，所以直接調整SQL部分，減無效點擊數(- r.ad_invalid_clk) */
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then (r.ad_clk - r.ad_invalid_clk) else r.ad_view end)), ");
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_group_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" count(r.ad_group_report_seq), ");
		hql.append(" SUM(r.convert_count),  ");
		hql.append(" SUM(r.convert_price_count) ");
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

		if (StringUtils.isNotBlank(adOperatingRule)) {
			hql.append(" and r.ad_operating_rule = :adOperatingRule ");
			sqlParams.put("adOperatingRule", adOperatingRule);
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
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");				// 產生pfp_ad_group_report 的時候，已經減過無效點擊數了，所以不用再減
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
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");				// 產生pfp_ad_action_report 的時候，已經減過無效點擊數了，所以不用再減
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
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");				// 產生pfp_ad_action_report 的時候，已經減過無效點擊數了，所以不用再減
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
	
	private Map<String,String> getAdStyleTypeMap(){
		Map<String,String> adStyleTypeMap = new HashMap<String,String>();
		
		for(EnumAdStyleType enumAdStyleType:EnumAdStyleType.values()){
			adStyleTypeMap.put(enumAdStyleType.getTypeName(), enumAdStyleType.getType());
		}
		
		return adStyleTypeMap;
	}
	
	private Map<String,String> getAdPriceTypeMap(){
		Map<String,String> adPriceTypeMap = new HashMap<String,String>();
		
		for(EnumAdPriceType enumAdPriceType:EnumAdPriceType.values()){
			adPriceTypeMap.put(enumAdPriceType.getDbTypeName(), enumAdPriceType.getTypeName());
		}
		
		return adPriceTypeMap;
	}
	
	private Map<Integer,String> getAdType(){
		Map<Integer,String> adTypeMap = new HashMap<Integer,String>();
		
		for(EnumAdType enumAdType:EnumAdType.values()){
			adTypeMap.put(enumAdType.getType(), enumAdType.getTypeName());
		}
		
		return adTypeMap;
	}

	/**
	 * 分類成效(明細)
	 */
	@Override
	public List<Map<String, Object>> getAdGroupList(AdGroupReportVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT ");
		hql.append(" SUM(r.ad_pv) AS ad_pv_sum, ");
		//   20180510 檢查排程未減無效點擊數，因pfp很多程式都有引用報表數據，因此不能重跑排程，所以直接調整SQL部分，減無效點擊數(- r.ad_invalid_clk)
		hql.append(" SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN (r.ad_clk - r.ad_invalid_clk) ELSE r.ad_view END)) AS ad_clk_sum, ");
		hql.append(" SUM(r.ad_clk_price) AS ad_price_sum, "); // 產生pfp_ad_group_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" SUM(r.ad_invalid_clk) AS ad_invalid_clk_sum, ");
		hql.append(" r.ad_group_seq, ");
		hql.append(" r.ad_pvclk_device AS ad_device, ");
		hql.append(" r.ad_operating_rule, ");
		hql.append(" r.ad_clk_price_type, ");
		hql.append(" r.ad_type, ");
		hql.append(" SUM(r.convert_count) AS convert_count, ");
		hql.append(" SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_group_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id =:customerInfoId ");
		hql.append(" AND r.ad_pvclk_date >=:startDate ");
		hql.append(" AND r.ad_pvclk_date <=:endDate ");
		
		if (StringUtils.isNotBlank(vo.getAdActionSeq())) { // 總廣告成效使用到
			hql.append(" AND r.ad_action_seq = :adActionSeq ");
		}
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND r.ad_group_seq IN (SELECT ad_group_seq FROM pfp_ad_group WHERE 1=1");
			hql.append(" AND customer_info_id = :customerInfoId ");
			hql.append(" AND ad_group_name LIKE :searchStr )");
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
		
		hql.append(" GROUP BY r.ad_group_seq, r.ad_action_seq, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		hql.append(" ORDER BY r.ad_group_seq DESC, r.ad_action_seq, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getAdActionSeq())) { // 總廣告成效使用到
			query.setString("adActionSeq", vo.getAdActionSeq());
		}
		
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
	 * 分類成效(加總)
	 */
	@Override
	public List<Map<String, Object>> getAdGroupListSum(AdGroupReportVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT ");
		hql.append(" SUM(r.ad_pv) AS ad_pv_sum, ");
		// 20180510 檢查排程未減無效點擊數，因pfp很多程式都有引用報表數據，因此不能重跑排程，所以直接調整SQL部分，減無效點擊數(- r.ad_invalid_clk)
		hql.append(" SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN (r.ad_clk - r.ad_invalid_clk) ELSE r.ad_view END)) AS ad_clk_sum, ");
		hql.append(" SUM(r.ad_clk_price) AS ad_price_sum, "); // 產生pfp_ad_group_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" SUM(r.ad_invalid_clk) AS ad_invalid_clk_sum, ");
		hql.append(" SUM(r.convert_count) AS convert_count, ");
		hql.append(" SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_group_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id = :customerInfoId ");
		hql.append(" AND r.ad_pvclk_date >= :startDate ");
		hql.append(" AND r.ad_pvclk_date <= :endDate ");
		
		if (StringUtils.isNotBlank(vo.getAdActionSeq())) { // 總廣告成效使用到
			hql.append(" AND r.ad_action_seq = :adActionSeq ");
		}
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND r.ad_group_seq IN (SELECT ad_group_seq FROM pfp_ad_group WHERE 1=1");
			hql.append(" AND customer_info_id = :customerInfoId ");
			hql.append(" AND ad_group_name LIKE :searchStr )");
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
		
		hql.append(" GROUP BY r.ad_group_seq");
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getAdActionSeq())) { // 總廣告成效使用到
			query.setString("adActionSeq", vo.getAdActionSeq());
		}
		
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
	 * 分類成效(圖表)
	 */
	@Override
	public List<Map<String, Object>> getAdGroupListChart(AdGroupReportVO vo) {
		StringBuffer hql = new StringBuffer();
		hql.append("SELECT");
		hql.append(" r.ad_pvclk_date,");
		hql.append(" SUM(r.ad_pv) AS ad_pv_sum, ");
		// 20180510 檢查排程未減無效點擊數，因pfp很多程式都有引用報表數據，因此不能重跑排程，所以直接調整SQL部分，減無效點擊數(- r.ad_invalid_clk)
		hql.append(" SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN (r.ad_clk - r.ad_invalid_clk) ELSE r.ad_view END)) AS ad_clk_sum, ");
		hql.append(" SUM(r.ad_clk_price) AS ad_price_sum, "); // 產生pfp_ad_group_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" SUM(r.ad_invalid_clk) AS ad_invalid_clk_sum, ");
		hql.append(" SUM(r.convert_count) AS convert_count, ");
		hql.append(" SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_group_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id =:customerInfoId ");
		hql.append(" AND r.ad_pvclk_date >=:startDate ");
		hql.append(" AND r.ad_pvclk_date <=:endDate ");
		
		if (StringUtils.isNotBlank(vo.getAdActionSeq())) { // 總廣告成效使用到
			hql.append(" AND r.ad_action_seq = :adActionSeq ");
		}
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND r.ad_group_seq IN (SELECT ad_group_seq FROM pfp_ad_group WHERE 1=1");
			hql.append(" AND customer_info_id = :customerInfoId ");
			hql.append(" AND ad_group_name LIKE :searchStr )");
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
		hql.append(" ORDER BY r.ad_pvclk_date");
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getAdActionSeq())) { // 總廣告成效使用到
			query.setString("adActionSeq", vo.getAdActionSeq());
		}
		
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