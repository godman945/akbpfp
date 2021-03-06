package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.pchome.akbpfp.db.pojo.PfpAdTimeReport;
import com.pchome.enumerate.ad.EnumAdPriceType;
import com.pchome.enumerate.ad.EnumAdStyleType;
import com.pchome.enumerate.ad.EnumAdTimeCode;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.report.EnumReport;

@Transactional
public class AdTimeReportDAO extends BaseDAO<PfpAdTimeReport, Integer> implements IAdTimeReportDAO {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public List<AdTimeReportVO> getReportList(final String sqlType, final String searchTime, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate, final int page, final int pageSize) throws Exception{

		List<AdTimeReportVO> result = (List<AdTimeReportVO>) getHibernateTemplate().execute(
				new HibernateCallback<List<AdTimeReportVO>>() {
					public List<AdTimeReportVO>  doInHibernate(Session session) throws HibernateException {

						log.info(">>> sqlType = " + sqlType);

						String hqlStr = "";
                        HashMap<String, Object> sqlParams = new HashMap<String, Object>();

						if (sqlType.equals(EnumReport.REPORT_HQLTYPE_TIME_COUNT.getTextValue())) {

							//每日廣告成效 (數量及加總)
							try {
								sqlParams = getTimeCountHQLStr(searchTime, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_TIME.getTextValue())) {

							//每日廣告成效 (資料)
							try {
								sqlParams = getTimeHQLStr(searchTime, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_TIME_CHART.getTextValue())) {

							//每日廣告成效 (圖表)
							try {
								sqlParams = getTimeChartHQLStr(searchTime, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate);
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
						
						List<AdTimeReportVO> resultData = new ArrayList<AdTimeReportVO>();

						if (sqlType.equals(EnumReport.REPORT_HQLTYPE_TIME_COUNT.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								BigDecimal pv = (BigDecimal) objArray[0];
								BigDecimal click = (BigDecimal) objArray[1];
								Double cost = (Double) objArray[2];
								BigDecimal invClick = (BigDecimal) objArray[3];
								BigDecimal convertCount = (BigDecimal) objArray[5];
								BigDecimal convertPriceCount = (BigDecimal) objArray[6];
								
								AdTimeReportVO vo = new AdTimeReportVO();
								vo.setConvertCount(convertCount);
								vo.setConvertPriceCount(convertPriceCount);
								vo.setAdPvSum(pv);
								vo.setAdClkSum(click);
								vo.setAdPriceSum(cost);
								vo.setAdInvClkSum(invClick);
								
								resultData.add(vo);

							}

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_TIME.getTextValue())) {

							Map<String,String> adStyleTypeMap = new HashMap<String,String>();
							Map<String,String> adPriceTypeMap = new HashMap<String,String>();
							Map<Integer,String> adTypeMap = new HashMap<Integer,String>();
							adStyleTypeMap = getAdStyleTypeMap();
							adPriceTypeMap = getAdPriceTypeMap();
							adTypeMap = getAdType();
							
							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								String weekCode = objArray[0].toString();
								String timeCode = objArray[1].toString();
								BigDecimal pv = (BigDecimal) objArray[2];
								BigDecimal click = (BigDecimal) objArray[3];
								Double cost = Double.valueOf(objArray[4].toString());
								BigDecimal invClick = (BigDecimal) objArray[5];
								String adActionSeq = objArray[7].toString();
								String adGroupSeq = objArray[8].toString();
								String adDevice = objArray[9].toString();
								String adOperatingRuleCode = objArray[10].toString();
								String adClkPriceType = objArray[11].toString();
								Integer adType = Integer.parseInt(objArray[12].toString());
								BigDecimal convertCount = (BigDecimal) objArray[13];
								BigDecimal convertPriceCount = (BigDecimal) objArray[14];
								
								AdTimeReportVO vo = new AdTimeReportVO();
								vo.setConvertCount(convertCount);
								vo.setConvertPriceCount(convertPriceCount);
								vo.setWeek(getWeekName(weekCode));
								for(EnumAdTimeCode enumAdTimeCode:EnumAdTimeCode.values()){
									if(StringUtils.equals(timeCode, enumAdTimeCode.getCode())){
										vo.setTime(enumAdTimeCode.getName());
									}
								}
								
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
								
								vo.setAdOperatingRule(adStyleTypeMap.get(adOperatingRuleCode));
								vo.setAdClkPriceType(adPriceTypeMap.get(adClkPriceType));
								vo.setAdType(adTypeMap.get(adType));
								
								resultData.add(vo);

							}

						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_TIME_CHART.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								String weekCode = objArray[0].toString();
								String timeCode = objArray[1].toString();
								BigDecimal pv = (BigDecimal) objArray[2];
								BigDecimal click = (BigDecimal) objArray[3];
								Double cost = Double.valueOf(objArray[4].toString());
								BigDecimal invClick = (BigDecimal) objArray[5];
								BigDecimal convertCount = (BigDecimal) objArray[7];
								BigDecimal convertPriceCount = (BigDecimal) objArray[8];
								
								AdTimeReportVO vo = new AdTimeReportVO();
								vo.setConvertCount(convertCount);
								vo.setConvertPriceCount(convertPriceCount);
								vo.setWeek(weekCode);
								vo.setTime(timeCode);
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

	private HashMap<String, Object> getTimeCountHQLStr(final String searchTime, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");				// 產生pfp_ad_time_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_time_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" SUM(r.convert_count),  ");
		hql.append(" SUM(r.convert_price_count) ");
		hql.append(" from pfp_ad_time_report as r ");
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

		if (StringUtils.isNotBlank(adOperatingRule)) {
			hql.append(" and r.ad_operating_rule = :adOperatingRule ");
			sqlParams.put("adOperatingRule", adOperatingRule);
		}
		
		if(StringUtils.isNotEmpty(searchTime) && StringUtils.equals(searchTime, "W") ){
			hql.append(" group by r.ad_action_seq, r.ad_group_seq, DAYOFWEEK(r.ad_pvclk_date)");
		} else {
			hql.append(" group by r.ad_action_seq, r.ad_group_seq, r.time_code");
		}
		
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private HashMap<String, Object> getTimeHQLStr(final String searchTime, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" DAYOFWEEK(r.ad_pvclk_date),");
		hql.append(" r.time_code,");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");				// 產生pfp_ad_time_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_time_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" r.ad_action_seq, ");
		hql.append(" r.ad_group_seq, ");
		hql.append(" r.ad_pvclk_device, ");
		hql.append(" r.ad_operating_rule, ");
		hql.append(" r.ad_clk_price_type, ");
		hql.append(" r.ad_type, ");
		hql.append(" SUM(r.convert_count),  ");
		hql.append(" SUM(r.convert_price_count) ");
		hql.append(" from pfp_ad_time_report as r ");
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

		if (StringUtils.isNotBlank(adOperatingRule)) {
			hql.append(" and r.ad_operating_rule = :adOperatingRule ");
			sqlParams.put("adOperatingRule", adOperatingRule);
		}
		
		if(StringUtils.isNotEmpty(searchTime) && StringUtils.equals(searchTime, "W") ){
			hql.append(" group by r.ad_action_seq, r.ad_group_seq, DAYOFWEEK(r.ad_pvclk_date), r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
			hql.append(" order by r.ad_action_seq, r.ad_group_seq, DAYOFWEEK(r.ad_pvclk_date), r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		} else {
			hql.append(" group by r.ad_action_seq, r.ad_group_seq, r.time_code, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
			hql.append(" order by r.ad_action_seq, r.ad_group_seq, r.time_code, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		}
		
		sqlParams.put("sql", hql);

		return sqlParams;
	}


	private HashMap<String, Object> getTimeChartHQLStr(final String searchTime, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" DAYOFWEEK(r.ad_pvclk_date),");
		hql.append(" r.time_code,");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");				// 產生pfp_ad_time_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_time_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" SUM(r.convert_count),  ");
		hql.append(" SUM(r.convert_price_count) ");
		hql.append(" from pfp_ad_time_report as r ");
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

		if (StringUtils.isNotBlank(adOperatingRule)) {
			hql.append(" and r.ad_operating_rule = :adOperatingRule ");
			sqlParams.put("adOperatingRule", adOperatingRule);
		}
		
		if(StringUtils.isNotEmpty(searchTime) && StringUtils.equals(searchTime, "W") ){
			hql.append(" group by DAYOFWEEK(r.ad_pvclk_date)");
			hql.append(" order by DAYOFWEEK(r.ad_pvclk_date)");
		} else {
			hql.append(" group by r.time_code");
			hql.append(" order by r.time_code");
		}
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
	
	private String getWeekName(String weekCode){
		String name = "";
		
		switch (weekCode) {
		case "1":
			name = "星期日";
			break;
		case "2":
			name = "星期一";
			break;
		case "3":
			name = "星期二";
			break;
		case "4":
			name = "星期三";
			break;
		case "5":
			name = "星期四";
			break;
		case "6":
			name = "星期五";
			break;
		case "7":
			name = "星期六";
			break;
		default:
			break;
		}
		
		return name;
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
	 * 廣告播放時段成效(明細)
	 * 統計 pfp_ad_time_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_time_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 */
	@Override
	public List<Map<String, Object>> getAdTimeList(AdTimeReportVO vo) {
		String adType = ""; // 播放類型
		String adOperatingRule = ""; // 廣告樣式
		String adClkPriceType = ""; // 計價方式
		String adDevice = ""; // 裝置
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			adType = tempJSONObject.optString("adType");
			adOperatingRule = tempJSONObject.optString("adOperatingRule");
			adClkPriceType = tempJSONObject.optString("adClkPriceType");
			adDevice = tempJSONObject.optString("adDevice");
		}
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT");
		hql.append(" DAYOFWEEK(r.ad_pvclk_date) AS week_code, ");
		hql.append(" r.time_code, ");
		hql.append(" SUM(r.ad_pv) AS ad_pv_sum, ");
		hql.append(" SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN r.ad_clk ELSE r.ad_view END)) AS ad_clk_sum, ");
		hql.append(" SUM(r.ad_clk_price) AS ad_price_sum, ");
		hql.append(" r.ad_group_seq, ");
		hql.append(" r.ad_pvclk_device AS ad_device, ");
		hql.append(" r.ad_operating_rule, ");
		hql.append(" r.ad_clk_price_type, ");
		hql.append(" r.ad_type, ");
		hql.append(" SUM(r.convert_count) AS convert_count, ");
		hql.append(" SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_time_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id =:customerInfoId ");
		hql.append(" AND r.ad_pvclk_date >=:startDate ");
		hql.append(" AND r.ad_pvclk_date <=:endDate ");

		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND r.ad_action_seq IN (SELECT ad_action_seq FROM pfp_ad_action WHERE 1=1");
			hql.append(" AND customer_info_id = :customerInfoId ");
			hql.append(" AND ad_action_name LIKE :searchStr )");
		}
		
		if (StringUtils.isNotBlank(adType) && !"all".equalsIgnoreCase(adType) && !"0".equalsIgnoreCase(adType)) {
			hql.append(" AND r.ad_type = :adType");
		}
		
		if (StringUtils.isNotBlank(adOperatingRule) && !"all".equalsIgnoreCase(adOperatingRule)) {
			hql.append(" AND r.ad_operating_rule = :adOperatingRule");
		}
		
		if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
			hql.append(" AND r.ad_clk_price_type = :adClkPriceType");
		}
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			hql.append(" AND r.ad_pvclk_device = :adPvclkDevice");
		}
		
		if ("week".equalsIgnoreCase(vo.getViewType())) {
			hql.append(" GROUP BY r.ad_action_seq, r.ad_group_seq, DAYOFWEEK(r.ad_pvclk_date), r.ad_operating_rule, r.ad_clk_price_type");
			// 非搜尋 + 聯播網廣告選項，則將資料區分聯播網廣告和搜尋廣告
			if (StringUtils.isBlank(adType) || "all".equalsIgnoreCase(adType)) {
				hql.append(" ,r.ad_type");
			}
			// 裝置空值或選擇全部時則將資料區分PC和mobile
			if (StringUtils.isBlank(adDevice) || "all".equalsIgnoreCase(adDevice)) {
				hql.append(" ,r.ad_pvclk_device");
			}
			hql.append(" ORDER BY r.ad_action_seq, r.ad_group_seq, DAYOFWEEK(r.ad_pvclk_date), r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		} else {
			hql.append(" GROUP BY r.ad_action_seq, r.ad_group_seq, r.time_code, r.ad_operating_rule, r.ad_clk_price_type");
			// 非搜尋 + 聯播網廣告選項，則將資料區分聯播網廣告和搜尋廣告
			if (StringUtils.isBlank(adType) || "all".equalsIgnoreCase(adType)) {
				hql.append(" ,r.ad_type");
			}
			// 裝置空值或選擇全部時則將資料區分PC和mobile
			if (StringUtils.isBlank(adDevice) || "all".equalsIgnoreCase(adDevice)) {
				hql.append(" ,r.ad_pvclk_device");
			}
			hql.append(" ORDER BY r.ad_action_seq, r.ad_group_seq, r.time_code, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		}
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		if (StringUtils.isNotBlank(adType) && !"all".equalsIgnoreCase(adType) && !"0".equalsIgnoreCase(adType)) {
			query.setString("adType", adType);
		}
		
		if (StringUtils.isNotBlank(adOperatingRule) && !"all".equalsIgnoreCase(adOperatingRule)) {
			query.setString("adOperatingRule", adOperatingRule);
		}
		
		if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
			query.setString("adClkPriceType", adClkPriceType);
		}
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
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
	 * 廣告播放時段成效(加總)
	 * 統計 pfp_ad_time_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_time_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 */
	@Override
	public List<Map<String, Object>> getAdTimeListSum(AdTimeReportVO vo) {
		String adType = ""; // 播放類型
		String adOperatingRule = ""; // 廣告樣式
		String adClkPriceType = ""; // 計價方式
		String adDevice = ""; // 裝置
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			adType = tempJSONObject.optString("adType");
			adOperatingRule = tempJSONObject.optString("adOperatingRule");
			adClkPriceType = tempJSONObject.optString("adClkPriceType");
			adDevice = tempJSONObject.optString("adDevice");
		}
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT");
		hql.append(" SUM(r.ad_pv) AS ad_pv_sum, ");
		hql.append(" SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN r.ad_clk ELSE r.ad_view END)) AS ad_clk_sum, ");
		hql.append(" SUM(r.ad_clk_price) AS ad_price_sum, ");
		hql.append(" SUM(r.convert_count) AS convert_count, ");
		hql.append(" SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_time_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id =:customerInfoId ");
		hql.append(" AND r.ad_pvclk_date >=:startDate ");
		hql.append(" AND r.ad_pvclk_date <=:endDate ");

		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND r.ad_action_seq IN (SELECT ad_action_seq FROM pfp_ad_action WHERE 1=1");
			hql.append(" AND customer_info_id = :customerInfoId ");
			hql.append(" AND ad_action_name LIKE :searchStr )");
		}
		
		if (StringUtils.isNotBlank(adType) && !"all".equalsIgnoreCase(adType) && !"0".equalsIgnoreCase(adType)) {
			hql.append(" AND r.ad_type = :adType");
		}
		
		if (StringUtils.isNotBlank(adOperatingRule) && !"all".equalsIgnoreCase(adOperatingRule)) {
			hql.append(" AND r.ad_operating_rule = :adOperatingRule");
		}
		
		if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
			hql.append(" AND r.ad_clk_price_type = :adClkPriceType");
		}
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			hql.append(" AND r.ad_pvclk_device = :adPvclkDevice");
		}
		
		if ("week".equalsIgnoreCase(vo.getViewType())) {
			hql.append(" GROUP BY r.ad_action_seq, r.ad_group_seq, DAYOFWEEK(r.ad_pvclk_date)");
		} else {
			hql.append(" GROUP BY r.ad_action_seq, r.ad_group_seq, r.time_code");
		}
		// 非搜尋 + 聯播網廣告選項，則將資料區分聯播網廣告和搜尋廣告
		if (StringUtils.isBlank(adType) || "all".equalsIgnoreCase(adType)) {
			hql.append(" ,r.ad_type");
		}
		// 裝置空值或選擇全部時則將資料區分PC和mobile
		if (StringUtils.isBlank(adDevice) || "all".equalsIgnoreCase(adDevice)) {
			hql.append(" ,r.ad_pvclk_device");
		}
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		if (StringUtils.isNotBlank(adType) && !"all".equalsIgnoreCase(adType) && !"0".equalsIgnoreCase(adType)) {
			query.setString("adType", adType);
		}
		
		if (StringUtils.isNotBlank(adOperatingRule) && !"all".equalsIgnoreCase(adOperatingRule)) {
			query.setString("adOperatingRule", adOperatingRule);
		}
		
		if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
			query.setString("adClkPriceType", adClkPriceType);
		}
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			query.setString("adPvclkDevice", adDevice);
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 廣告播放時段成效(圖表)
	 * 統計 pfp_ad_time_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_time_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 */
	@Override
	public List<Map<String, Object>> getAdTimeListChart(AdTimeReportVO vo) {
		String adType = ""; // 播放類型
		String adOperatingRule = ""; // 廣告樣式
		String adClkPriceType = ""; // 計價方式
		String adDevice = ""; // 裝置
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			adType = tempJSONObject.optString("adType");
			adOperatingRule = tempJSONObject.optString("adOperatingRule");
			adClkPriceType = tempJSONObject.optString("adClkPriceType");
			adDevice = tempJSONObject.optString("adDevice");
		}
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT");
		hql.append(" DAYOFWEEK(r.ad_pvclk_date) AS week_code, ");
		hql.append(" r.time_code, ");
		hql.append(" SUM(r.ad_pv) AS ad_pv_sum, ");
		hql.append(" SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN r.ad_clk ELSE r.ad_view END)) AS ad_clk_sum, ");
		hql.append(" SUM(r.ad_clk_price) AS ad_price_sum, ");
		hql.append(" SUM(r.convert_count) AS convert_count, ");
		hql.append(" SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_time_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id =:customerInfoId ");
		hql.append(" AND r.ad_pvclk_date >=:startDate ");
		hql.append(" AND r.ad_pvclk_date <=:endDate ");
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND r.ad_action_seq IN (SELECT ad_action_seq FROM pfp_ad_action WHERE 1=1");
			hql.append(" AND customer_info_id = :customerInfoId ");
			hql.append(" AND ad_action_name LIKE :searchStr )");
		}
		
		if (StringUtils.isNotBlank(adType) && !"all".equalsIgnoreCase(adType) && !"0".equalsIgnoreCase(adType)) {
			hql.append(" AND r.ad_type = :adType");
		}
		
		if (StringUtils.isNotBlank(adOperatingRule) && !"all".equalsIgnoreCase(adOperatingRule)) {
			hql.append(" AND r.ad_operating_rule = :adOperatingRule");
		}
		
		if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
			hql.append(" AND r.ad_clk_price_type = :adClkPriceType");
		}
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			hql.append(" AND r.ad_pvclk_device = :adPvclkDevice");
		}
		
		if ("week".equalsIgnoreCase(vo.getViewType())) {
			hql.append(" GROUP BY DAYOFWEEK(r.ad_pvclk_date)");
		} else {
			hql.append(" GROUP BY r.time_code");
		}
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		if (StringUtils.isNotBlank(adType) && !"all".equalsIgnoreCase(adType) && !"0".equalsIgnoreCase(adType)) {
			query.setString("adType", adType);
		}
		
		if (StringUtils.isNotBlank(adOperatingRule) && !"all".equalsIgnoreCase(adOperatingRule)) {
			query.setString("adOperatingRule", adOperatingRule);
		}
		
		if (StringUtils.isNotBlank(adClkPriceType) && !"all".equalsIgnoreCase(adClkPriceType)) {
			query.setString("adClkPriceType", adClkPriceType);
		}
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			query.setString("adPvclkDevice", adDevice);
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
}