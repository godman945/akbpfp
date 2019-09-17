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
import com.pchome.akbpfp.db.pojo.PfpAdWebsiteReport;
import com.pchome.enumerate.ad.EnumAdPriceType;
import com.pchome.enumerate.ad.EnumAdStyleType;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.report.EnumReport;

@Transactional
public class AdWebsiteReportDAO extends BaseDAO<PfpAdWebsiteReport, Integer> implements IAdWebsiteReportDAO {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public List<AdWebsiteReportVO> getReportList(final String sqlType, final String searchWebsiteCode, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate, final int page, final int pageSize) throws Exception{

		List<AdWebsiteReportVO> result = (List<AdWebsiteReportVO>) getHibernateTemplate().execute(
				new HibernateCallback<List<AdWebsiteReportVO>>() {
					public List<AdWebsiteReportVO>  doInHibernate(Session session) throws HibernateException {

						log.info(">>> sqlType = " + sqlType);

						String hqlStr = "";
                        HashMap<String, Object> sqlParams = new HashMap<String, Object>();

						if (sqlType.equals(EnumReport.REPORT_HQLTYPE_WEBSITE_COUNT.getTextValue())) {

							//每日廣告成效 (數量及加總)
							try {
								sqlParams = getWebsiteCountHQLStr(searchWebsiteCode,searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_WEBSITE.getTextValue())) {

							//每日廣告成效 (資料)
							try {
								sqlParams = getWebsiteHQLStr(searchWebsiteCode, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_WEBSITE_CHART.getTextValue())) {

							//每日廣告成效 (圖表)
							try {
								sqlParams = getWebsiteChartHQLStr(searchWebsiteCode,searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate);
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
								Double cost = (Double)objArray[2];
								BigDecimal invClick = (BigDecimal) objArray[3];
								BigDecimal convertCount = (BigDecimal) objArray[5];
								BigDecimal convertPriceCount = (BigDecimal) objArray[6];
								AdWebsiteReportVO vo = new AdWebsiteReportVO();
								vo.setConvertCount(convertCount);
								vo.setConvertPriceCount(convertPriceCount);
								vo.setAdPvSum(pv);
								vo.setAdClkSum(click);
								vo.setAdPriceSum(cost);
								vo.setAdInvClkSum(invClick);
								resultData.add(vo);
							}
						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_WEBSITE.getTextValue())) {
							Map<String,String> adStyleTypeMap = new HashMap<String,String>();
							Map<String,String> adPriceTypeMap = new HashMap<String,String>();
							Map<Integer,String> adTypeMap = new HashMap<Integer,String>();
							adStyleTypeMap = getAdStyleTypeMap();
							adPriceTypeMap = getAdPriceTypeMap();
							adTypeMap = getAdType();
							
							for (int i=0; i<dataList.size(); i++) {
								Object[] objArray = (Object[]) dataList.get(i);
								String websiteCategoryCode	 ="";
								if(objArray[0] != null){
									websiteCategoryCode = objArray[0].toString();
								}
								BigDecimal pv = (BigDecimal) objArray[1];
								BigDecimal click = (BigDecimal) objArray[2];
								Double cost = (Double)objArray[3];
								BigDecimal invClick = (BigDecimal) objArray[4];
								String adActionSeq = objArray[6].toString();
								String adGroupSeq = objArray[7].toString();
								String adDevice = objArray[8].toString();
								String adOperatingRuleCode = objArray[9].toString();
								String adClkPriceType = objArray[10].toString();
								Integer adType = Integer.parseInt(objArray[11].toString());
								BigDecimal convertCount = (BigDecimal) objArray[12];
								BigDecimal convertPriceCount = (BigDecimal) objArray[13];
								
								AdWebsiteReportVO vo = new AdWebsiteReportVO();
								vo.setWebsiteCategoryCode(websiteCategoryCode);
								vo.setConvertCount(convertCount);
								vo.setConvertPriceCount(convertPriceCount);
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

						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_WEBSITE_CHART.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {
								Object[] objArray = (Object[]) dataList.get(i);
								String websiteCategoryCode	 ="";
								if(objArray[0] != null){
									websiteCategoryCode = objArray[0].toString();
								}
								BigDecimal pv = (BigDecimal) objArray[1];
								BigDecimal click = (BigDecimal) objArray[2];
								Double cost = (Double)objArray[3];
								BigDecimal invClick = (BigDecimal) objArray[4];
								BigDecimal convertCount = (BigDecimal) objArray[6];
								BigDecimal convertPriceCount = (BigDecimal) objArray[7];
								AdWebsiteReportVO vo = new AdWebsiteReportVO();
								vo.setConvertCount(convertCount);
								vo.setConvertPriceCount(convertPriceCount);
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

	private HashMap<String, Object> getWebsiteCountHQLStr(final String searchWebsiteCode, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");	
		hql.append(" sum(r.ad_clk_price), ");	
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" SUM(r.convert_count),  ");
		hql.append(" SUM(r.convert_price_count) ");
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
		
		if (StringUtils.isNotBlank(adOperatingRule)) {
			hql.append(" and r.ad_operating_rule = :adOperatingRule ");
			sqlParams.put("adOperatingRule", adOperatingRule);
		}
		
		hql.append(" group by r.ad_action_seq, r.ad_group_seq, r.website_category_code");
		
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private HashMap<String, Object> getWebsiteHQLStr(final String searchWebsiteCode, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" r.website_category_code,");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");		
		hql.append(" sum(r.ad_clk_price), ");	
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" r.ad_action_seq, ");
		hql.append(" r.ad_group_seq, ");
		hql.append(" r.ad_pvclk_device, ");
		hql.append(" r.ad_operating_rule, ");
		hql.append(" r.ad_clk_price_type, ");
		hql.append(" r.ad_type, ");
		hql.append(" SUM(r.convert_count),");
		hql.append(" SUM(r.convert_price_count)");
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
		
		if (StringUtils.isNotBlank(adOperatingRule)) {
			hql.append(" and r.ad_operating_rule = :adOperatingRule ");
			sqlParams.put("adOperatingRule", adOperatingRule);
		}
		
		hql.append(" group by r.ad_action_seq, r.ad_group_seq, r.website_category_code, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		hql.append(" order by r.ad_action_seq, r.ad_group_seq, r.website_category_code, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		
		sqlParams.put("sql", hql);

		return sqlParams;
	}


	private HashMap<String, Object> getWebsiteChartHQLStr(final String searchWebsiteCode, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" r.website_category_code,");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");	
		hql.append(" sum(r.ad_clk_price), ");	
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" SUM(r.convert_count), ");
		hql.append(" SUM(r.convert_price_count) ");
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
		
		if (StringUtils.isNotBlank(adOperatingRule)) {
			hql.append(" and r.ad_operating_rule = :adOperatingRule ");
			sqlParams.put("adOperatingRule", adOperatingRule);
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
	 * 網站類型成效(明細)
	 * 統計 pfp_ad_website_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_website_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 */
	@Override
	public List<Map<String, Object>> getAdWebsiteList(AdWebsiteReportVO vo) {
		String websiteCategoryCode = ""; // 網站類型
		String adType = ""; // 播放類型
		String adOperatingRule = ""; // 廣告樣式
		String adClkPriceType = ""; // 計價方式
		String adDevice = ""; // 裝置
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			websiteCategoryCode = tempJSONObject.optString("websiteCategoryCode");
			adType = tempJSONObject.optString("adType");
			adOperatingRule = tempJSONObject.optString("adOperatingRule");
			adClkPriceType = tempJSONObject.optString("adClkPriceType");
			adDevice = tempJSONObject.optString("adDevice");
		}
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT");
		hql.append(" r.website_category_code,");
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
		hql.append(" FROM pfp_ad_website_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id =:customerInfoId ");
		hql.append(" AND r.ad_pvclk_date >=:startDate ");
		hql.append(" AND r.ad_pvclk_date <=:endDate ");
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND r.ad_action_seq IN (SELECT ad_action_seq FROM pfp_ad_action WHERE 1=1");
			hql.append(" AND customer_info_id = :customerInfoId ");
			hql.append(" AND ad_action_name LIKE :searchStr )");
		}
		
		if (StringUtils.isNotBlank(websiteCategoryCode) && !"all".equalsIgnoreCase(websiteCategoryCode)) {
			hql.append(" AND r.website_category_code = :websiteCategoryCode");
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
		
		hql.append(" GROUP BY r.ad_action_seq, r.ad_group_seq, r.website_category_code, r.ad_operating_rule, r.ad_clk_price_type");
		// 非搜尋 + 聯播網廣告選項，則將資料區分聯播網廣告和搜尋廣告
		if (StringUtils.isBlank(adType) || "all".equalsIgnoreCase(adType)) {
			hql.append(" ,r.ad_type");
		}
		
		// 裝置空值或選擇全部時則將資料區分PC和mobile
		if (StringUtils.isBlank(adDevice) || "all".equalsIgnoreCase(adDevice)) {
			hql.append(" ,r.ad_pvclk_device");
		}
		
		hql.append(" ORDER BY r.ad_action_seq, r.ad_group_seq, r.website_category_code, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		if (StringUtils.isNotBlank(websiteCategoryCode) && !"all".equalsIgnoreCase(websiteCategoryCode)) {
			query.setString("websiteCategoryCode", websiteCategoryCode);
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
	 * 網站類型成效(加總)
	 * 統計 pfp_ad_website_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_website_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 */
	@Override
	public List<Map<String, Object>> getAdWebsiteListSum(AdWebsiteReportVO vo) {
		String websiteCategoryCode = ""; // 網站類型
		String adType = ""; // 播放類型
		String adOperatingRule = ""; // 廣告樣式
		String adClkPriceType = ""; // 計價方式
		String adDevice = ""; // 裝置
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			websiteCategoryCode = tempJSONObject.optString("websiteCategoryCode");
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
		hql.append(" FROM pfp_ad_website_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id =:customerInfoId ");
		hql.append(" AND r.ad_pvclk_date >=:startDate ");
		hql.append(" AND r.ad_pvclk_date <=:endDate ");

		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND r.ad_action_seq IN (SELECT ad_action_seq FROM pfp_ad_action WHERE 1=1");
			hql.append(" AND customer_info_id = :customerInfoId ");
			hql.append(" AND ad_action_name LIKE :searchStr )");
		}
		
		if (StringUtils.isNotBlank(websiteCategoryCode) && !"all".equalsIgnoreCase(websiteCategoryCode)) {
			hql.append(" AND r.website_category_code = :websiteCategoryCode");
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
		
		hql.append(" GROUP BY r.ad_action_seq, r.ad_group_seq, r.website_category_code");
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
		
		if (StringUtils.isNotBlank(websiteCategoryCode) && !"all".equalsIgnoreCase(websiteCategoryCode)) {
			query.setString("websiteCategoryCode", websiteCategoryCode);
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
	 * 網站類型成效(圖表)
	 * 統計 pfp_ad_website_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_website_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 */
	@Override
	public List<Map<String, Object>> getAdWebsiteListChart(AdWebsiteReportVO vo) {
		String websiteCategoryCode = ""; // 網站類型
		String adType = ""; // 播放類型
		String adOperatingRule = ""; // 廣告樣式
		String adClkPriceType = ""; // 計價方式
		String adDevice = ""; // 裝置
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			websiteCategoryCode = tempJSONObject.optString("websiteCategoryCode");
			adType = tempJSONObject.optString("adType");
			adOperatingRule = tempJSONObject.optString("adOperatingRule");
			adClkPriceType = tempJSONObject.optString("adClkPriceType");
			adDevice = tempJSONObject.optString("adDevice");
		}
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT");
		hql.append(" r.website_category_code, ");
		hql.append(" SUM(r.ad_pv) AS ad_pv_sum, ");
		hql.append(" SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN r.ad_clk ELSE r.ad_view END)) AS ad_clk_sum, ");
		hql.append(" SUM(r.ad_clk_price) AS ad_price_sum, ");
		hql.append(" SUM(r.convert_count) AS convert_count, ");
		hql.append(" SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_website_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id =:customerInfoId ");
		hql.append(" AND r.ad_pvclk_date >=:startDate ");
		hql.append(" AND r.ad_pvclk_date <=:endDate ");
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND r.ad_action_seq IN (SELECT ad_action_seq FROM pfp_ad_action WHERE 1=1");
			hql.append(" AND customer_info_id = :customerInfoId ");
			hql.append(" AND ad_action_name LIKE :searchStr )");
		}
		
		if (StringUtils.isNotBlank(websiteCategoryCode) && !"all".equalsIgnoreCase(websiteCategoryCode)) {
			hql.append(" AND r.website_category_code = :websiteCategoryCode");
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
		
		hql.append(" GROUP BY r.website_category_code");
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		if (StringUtils.isNotBlank(websiteCategoryCode) && !"all".equalsIgnoreCase(websiteCategoryCode)) {
			query.setString("websiteCategoryCode", websiteCategoryCode);
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