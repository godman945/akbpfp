package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;
import java.sql.SQLException;
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
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.enumerate.ad.EnumAdAgeCode;
import com.pchome.enumerate.ad.EnumAdPriceType;
import com.pchome.enumerate.ad.EnumAdStyleType;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdAgeReport;

@Transactional
public class AdAgesexReportDAO extends BaseDAO<PfpAdAgeReport, Integer> implements IAdAgesexReportDAO {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public List<AdAgesexReportVO> getReportList(final String sqlType, final String searchAgesex, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate, final int page, final int pageSize) throws Exception{

		List<AdAgesexReportVO> result = (List<AdAgesexReportVO>) getHibernateTemplate().execute(
				new HibernateCallback<List<AdAgesexReportVO>>() {
					public List<AdAgesexReportVO>  doInHibernate(Session session) throws HibernateException, SQLException {

						log.info(">>> sqlType = " + sqlType);

						String hqlStr = "";
                        HashMap<String, Object> sqlParams = new HashMap<String, Object>();

						if (sqlType.equals(EnumReport.REPORT_HQLTYPE_AGESEX_COUNT.getTextValue())) {

							//每日廣告成效 (數量及加總)
							try {
								sqlParams = getAgesexCountHQLStr(searchAgesex,searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_AGESEX.getTextValue())) {

							//每日廣告成效 (資料)
							try {
								sqlParams = getAgesexHQLStr(searchAgesex, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_AGESEX_CHART.getTextValue())) {

							//每日廣告成效 (圖表)
							try {
								sqlParams = getAgesexChartHQLStr(searchAgesex,searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate);
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
						
						List<AdAgesexReportVO> resultData = new ArrayList<AdAgesexReportVO>();

						if (sqlType.equals(EnumReport.REPORT_HQLTYPE_AGESEX_COUNT.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								BigDecimal pv = (BigDecimal) objArray[0];
								BigDecimal click = (BigDecimal) objArray[1];
								Double cost = (Double) objArray[2];
								BigDecimal invClick = (BigDecimal) objArray[3];

								AdAgesexReportVO vo = new AdAgesexReportVO();
								
								vo.setAdPvSum(pv);
								vo.setAdClkSum(click);
								vo.setAdPriceSum(cost);
								vo.setAdInvClkSum(invClick);
								
								resultData.add(vo);

							}

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_AGESEX.getTextValue())) {

							Map<String,String> adStyleTypeMap = new HashMap<String,String>();
							Map<String,String> adPriceTypeMap = new HashMap<String,String>();
							Map<Integer,String> adTypeMap = new HashMap<Integer,String>();
							adStyleTypeMap = getAdStyleTypeMap();
							adPriceTypeMap = getAdPriceTypeMap();
							adTypeMap = getAdType();
							
							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								String ageCode ="";
								if(objArray[0] != null){
									ageCode = objArray[0].toString();
								}
								String sex ="";
								if(objArray[1] != null){
									sex = objArray[1].toString();
								}
								BigDecimal pv = (BigDecimal) objArray[2];
								BigDecimal click = (BigDecimal) objArray[3];
								Double cost = (Double) objArray[4];
								BigDecimal invClick = (BigDecimal) objArray[5];
								String adActionSeq = objArray[7].toString();
								String adGroupSeq = objArray[8].toString();
								String adDevice = objArray[9].toString();
								String adOperatingRuleCode = objArray[10].toString();
								String adClkPriceType = objArray[11].toString();
								Integer adType = Integer.parseInt(objArray[12].toString());

								AdAgesexReportVO vo = new AdAgesexReportVO();

								String ageName = "未知";
								if(StringUtils.isNotEmpty(ageCode)){
									for(EnumAdAgeCode enumAdAgeCode:EnumAdAgeCode.values()){
										if(StringUtils.equals(ageCode, enumAdAgeCode.getCode())){
											ageName = enumAdAgeCode.getName();
										}
									}	
								}
								vo.setAge(ageName);
								
								String sexName = "不分性別";
								if(StringUtils.endsWith(sex, "M")){
									sexName = "男性";
								} else if(StringUtils.endsWith(sex, "F")){
									sexName = "女性";
								}
								vo.setSex(sexName);
								
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

						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_AGESEX_CHART.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								String ageCode ="I";
								if(objArray[0] != null){
									ageCode = objArray[0].toString();
								}
								String sex ="N";
								if(objArray[1] != null){
									sex = objArray[1].toString();
								}
								BigDecimal pv = (BigDecimal) objArray[2];
								BigDecimal click = (BigDecimal) objArray[3];
								Double cost = (Double) objArray[4];
								BigDecimal invClick = (BigDecimal) objArray[5];

								AdAgesexReportVO vo = new AdAgesexReportVO();

								vo.setAge(ageCode);
								vo.setSex(sex);
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

	private HashMap<String, Object> getAgesexCountHQLStr(final String searchAgesex, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk when r.ad_clk_price_type = 'CPM' then r.ad_clk else r.ad_view end)), ");					// 產生pfp_ad_time_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_time_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price) ");
		hql.append(" from pfp_ad_age_report as r ");
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
		
		if(StringUtils.isNotEmpty(searchAgesex) && StringUtils.equals(searchAgesex, "A") ){
			hql.append(" group by r.ad_action_seq, r.ad_group_seq, r.age_code");
		} else {
			hql.append(" group by r.ad_action_seq, r.ad_group_seq, r.sex");
		}
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private HashMap<String, Object> getAgesexHQLStr(final String searchAgesex, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" r.age_code,");
		hql.append(" r.sex,");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk when r.ad_clk_price_type = 'CPM' then r.ad_clk else r.ad_view end)), ");				// 產生pfp_ad_time_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_time_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" r.ad_action_seq, ");
		hql.append(" r.ad_group_seq, ");
		hql.append(" r.ad_pvclk_device, ");
		hql.append(" r.ad_operating_rule, ");
		hql.append(" r.ad_clk_price_type, ");
		hql.append(" r.ad_type ");
		hql.append(" from pfp_ad_age_report as r ");
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
		
		if(StringUtils.isNotEmpty(searchAgesex) && StringUtils.equals(searchAgesex, "A") ){
			hql.append(" group by r.ad_action_seq, r.ad_group_seq, r.age_code, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
			hql.append(" order by r.ad_action_seq, r.ad_group_seq, r.age_code, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		} else {
			hql.append(" group by r.ad_action_seq, r.ad_group_seq, r.sex, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
			hql.append(" order by r.ad_action_seq, r.ad_group_seq, r.sex desc, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		}
		
		sqlParams.put("sql", hql);

		return sqlParams;
	}


	private HashMap<String, Object> getAgesexChartHQLStr(final String searchAgesex, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" r.age_code,");
		hql.append(" r.sex,");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk when r.ad_clk_price_type = 'CPM' then r.ad_clk else r.ad_view end)), ");					// 產生pfp_ad_time_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_time_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price) ");
		hql.append(" from pfp_ad_age_report as r ");
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
		
		if(StringUtils.isNotEmpty(searchAgesex) && StringUtils.equals(searchAgesex, "A") ){
			hql.append(" group by r.age_code");
			hql.append(" order by r.age_code");
		} else {
			hql.append(" group by r.sex");
			hql.append(" order by r.sex");		
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
}
