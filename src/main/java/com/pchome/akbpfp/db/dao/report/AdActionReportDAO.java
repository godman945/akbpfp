package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import com.pchome.akbpfp.db.pojo.PfpAdActionReport;
import com.pchome.akbpfp.db.service.bill.IPfpTransDetailService;
import com.pchome.enumerate.ad.EnumAdPriceType;
import com.pchome.enumerate.ad.EnumAdStyleType;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.report.EnumReport;

@Transactional
public class AdActionReportDAO extends BaseDAO<PfpAdActionReport, Integer> implements IAdActionReportDAO {

	private IPfpTransDetailService transDetailService;
	
	public List<AdActionReportVO> getReportList(final String sqlType, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate, final int page, final int pageSize) {

		List<AdActionReportVO> result = (List<AdActionReportVO>) getHibernateTemplate().execute(
				new HibernateCallback<List<AdActionReportVO>>() {
					public List<AdActionReportVO> doInHibernate(Session session) throws HibernateException {

						log.info(">>> sqlType = " + sqlType);

						String hqlStr = "";
                        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
                        
                        //報表下方總計
						if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_COUNT.getTextValue())) {

							//總廣告成效 -> 報表類型:廣告 (數量及加總)
							//廣告成效 (數量及加總)
							sqlParams = getAdActionReportCountSQL(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate);
							//hqlStr = getAdActionReportCountSQL(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT.getTextValue())) {

							//總廣告成效 -> 報表類型:廣告 (資料)
							//廣告成效 (資料)
							sqlParams = getAdActionReportDataSQL(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate);
							//hqlStr = getAdActionReportDataSQL(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_CHART.getTextValue())) {

							//總廣告成效 -> 報表類型:廣告 (圖表)
							//廣告成效 (圖表)
							sqlParams = getAdActionReportChartSQL(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, adOperatingRule, startDate, endDate);
							//hqlStr = getAdActionReportChartSQL(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_DAILY_COUNT.getTextValue())) {

							//每日廣告成效 (數量及加總)
							sqlParams = getDailyCountHQLStr(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							//hqlStr = getDailyCountHQLStr(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_DAILY.getTextValue())) {

							//每日廣告成效 (資料)
							sqlParams = getDailyHQLStr(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							//hqlStr = getDailyHQLStr(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);

						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_DAILY_CHART.getTextValue())) {

							//每日廣告成效 (圖表)
							sqlParams = getDailyChartHQLStr(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							//hqlStr = getDailyChartHQLStr(searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
						}
						hqlStr = sqlParams.get("sql").toString();
						log.info(">>> sql = " + hqlStr);

						List<Object> dataList = null;

						Query query = session.createSQLQuery(hqlStr);
				        for (String paramName:sqlParams.keySet()) {
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
						
						//Query q = session.createSQLQuery(hqlStr);

						////page=-1 取得全部不分頁用於download
						//if (page!=-1) {
						//	q.setFirstResult((page-1)*pageSize).setMaxResults(pageSize);
						//}

						//List<Object> dataList = q.list();
						log.info(">>> dataList.size() = " + dataList.size());

						List<AdActionReportVO> resultData = new ArrayList<AdActionReportVO>();

						if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_COUNT.getTextValue())) {
							for (int i=0; i<dataList.size(); i++) {
								Object[] objArray = (Object[]) dataList.get(i);
								BigDecimal pv = (BigDecimal) objArray[0];
								BigDecimal click = (BigDecimal) objArray[1];
								Double cost = (Double) objArray[2];
								BigDecimal invClick = (BigDecimal) objArray[3];
								BigDecimal convertCount = (BigDecimal) objArray[5];
								BigDecimal convertPriceCount = (BigDecimal) objArray[6];
								
								AdActionReportVO vo = new AdActionReportVO();
								vo.setConvertCount(convertCount);
								vo.setConvertPriceCount(convertPriceCount);
								vo.setAdPriceSum(cost);
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
								Double adActionMaxPrice = (Double) objArray[5];
								BigInteger count = new BigInteger(String.valueOf(objArray[6]));
								String adActionSeq = (String) objArray[7];
								String adDevice = (String) objArray[8];
								String adOperatingRuleCode = objArray[9].toString();
								Integer adType = Integer.parseInt(objArray[10].toString());
								BigDecimal convertCount = (BigDecimal) objArray[11];
								BigDecimal convertPriceCount = (BigDecimal) objArray[12];
								
								AdActionReportVO adActionReportVO = new AdActionReportVO();
								adActionReportVO.setConvertCount(convertCount);
								adActionReportVO.setConvertPriceCount(convertPriceCount);
								adActionReportVO.setAdPvSum(pv);
								adActionReportVO.setAdClkSum(click);
								adActionReportVO.setAdPriceSum(cost);
								adActionReportVO.setAdInvClkSum(invClick);
								adActionReportVO.setAdActionMaxPriceSum(adActionMaxPrice);
								adActionReportVO.setCount(count);
								adActionReportVO.setAdActionSeq(adActionSeq);
								if(StringUtils.isNotBlank(adPvclkDevice)) {
									if("PC".equals(adDevice)){
										adActionReportVO.setAdDevice("電腦");
									}else if("mobile".equals(adDevice)){
										adActionReportVO.setAdDevice("行動裝置");
									} else {
										adActionReportVO.setAdDevice(adDevice);	
									}
								} else {
									adActionReportVO.setAdDevice("全部");
								}
								adActionReportVO.setAdOperatingRuleDesc(adStyleTypeMap.get(adOperatingRuleCode));
								adActionReportVO.setAdOperatingRule(adOperatingRuleCode);
								adActionReportVO.setAdType(adTypeMap.get(adType));
								resultData.add(adActionReportVO);
							}

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_CHART.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								Date reportDate = (Date) objArray[0];
								BigDecimal pv = (BigDecimal) objArray[1];
								BigDecimal click = (BigDecimal) objArray[2];
								Double cost = Double.valueOf(objArray[3].toString());
								BigDecimal invClick = (BigDecimal) objArray[4];
								Double adActionMaxPrice = (Double) objArray[6];
								BigInteger count = new BigInteger(String.valueOf(objArray[7]));
								BigDecimal convertCount = (BigDecimal) objArray[8];
								BigDecimal convertPriceCount = (BigDecimal) objArray[9];
								
								AdActionReportVO vo = new AdActionReportVO();
								vo.setConvertCount(convertCount);
								vo.setConvertPriceCount(convertPriceCount);
								vo.setReportDate(reportDate);
								vo.setAdPvSum(pv);
								vo.setAdClkSum(click);
								vo.setAdPriceSum(cost);
								vo.setAdInvClkSum(invClick);
								vo.setAdActionMaxPriceSum(adActionMaxPrice);
								vo.setCount(count);
								resultData.add(vo);

							}

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_DAILY_COUNT.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {
								Object[] objArray = (Object[]) dataList.get(i);
								BigDecimal pv = (BigDecimal) objArray[0];
								BigDecimal click = (BigDecimal) objArray[1];
								Double cost = (Double) objArray[2];
								BigDecimal invClick = (BigDecimal) objArray[3];
								BigDecimal convertCount = (BigDecimal) objArray[5];
								BigDecimal convertPriceCount = (BigDecimal) objArray[6];
								
								
								AdActionReportVO vo = new AdActionReportVO();
								vo.setConvertCount(convertCount);
								vo.setConvertPriceCount(convertPriceCount);
								vo.setAdPvSum(pv);
								vo.setAdClkSum(click);
								vo.setAdPriceSum(cost);
								vo.setAdInvClkSum(invClick);
								
								resultData.add(vo);

							}

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_DAILY.getTextValue())) {

							Map<Integer,String> adTypeMap = new HashMap<Integer,String>();
							adTypeMap = getAdType();
							
							for (int i=0; i<dataList.size(); i++) {
								Object[] objArray = (Object[]) dataList.get(i);
								Date reportDate = (Date) objArray[0];
								BigDecimal pv = (BigDecimal) objArray[1];
								BigDecimal click = (BigDecimal) objArray[2];
								Double cost = (Double) objArray[3];
								BigDecimal invClick = (BigDecimal) objArray[4];
								String adDevice = (String) objArray[6];
								Integer adType = Integer.parseInt(objArray[7].toString());
								BigDecimal convertCount = (BigDecimal) objArray[8];
								BigDecimal convertPriceCount = (BigDecimal) objArray[9];
								
								AdActionReportVO vo = new AdActionReportVO();
								vo.setConvertCount(convertCount);
								vo.setConvertPriceCount(convertPriceCount);
								vo.setReportDate(reportDate);
								vo.setAdPvSum(pv);
								vo.setAdClkSum(click);
								vo.setAdPriceSum(cost);
								vo.setAdInvClkSum(invClick);
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

								vo.setAdType(adTypeMap.get(adType));
								
								resultData.add(vo);

							}

						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_DAILY_CHART.getTextValue())) {
							for (int i=0; i<dataList.size(); i++) {
								Object[] objArray = (Object[]) dataList.get(i);
								Date reportDate = (Date) objArray[0];
								BigDecimal pv = (BigDecimal) objArray[1];
								BigDecimal click = (BigDecimal) objArray[2];
								Double cost = ((Double) objArray[3]).doubleValue();
								BigDecimal invClick = (BigDecimal) objArray[4];
								BigDecimal convertCount = (BigDecimal) objArray[6];
								BigDecimal convertPriceCount = (BigDecimal) objArray[7];
								
								AdActionReportVO vo = new AdActionReportVO();
								vo.setConvertCount(convertCount);
								vo.setConvertPriceCount(convertPriceCount);
								vo.setReportDate(reportDate);
								vo.setAdPvSum(pv);
								vo.setAdClkSum(click);
								vo.setAdPriceSum(cost);
								vo.setAdInvClkSum(invClick);
								resultData.add(vo);
							}
						}
//
//						// 使用舊的 hql 抓每日花費比對用，目前每日花費仍有問題，不可以刪掉
//						try {
//							System.out.println("old");
//							StringBuffer hql = new StringBuffer();
//
//							hql.append("select ");
//							hql.append(" r.pfpAd.pfpAdGroup.pfpAdAction.adActionSeq,");
//							hql.append(" r.pfpAd.pfpAdGroup.pfpAdAction.adActionName,");
//							hql.append(" sum(r.adPv), ");
//							hql.append(" (sum(r.adClk)-sum(r.adInvalidClk)), ");
//							hql.append(" (sum(r.adClkPrice)-sum(r.adInvalidClkPrice)), ");
//							hql.append(" sum(r.adInvalidClk), ");
//							hql.append(" sum(r.adInvalidClkPrice), ");
//							hql.append(" sum(r.adActionMaxPrice), ");
//							hql.append(" count(r.adPvclkSeq), ");
//							hql.append(" r.pfpAd.pfpAdGroup.pfpAdAction.adType,");  
//							hql.append(" r.pfpAd.pfpAdGroup.pfpAdAction.adActionStartDate,");  
//							hql.append(" r.pfpAd.pfpAdGroup.pfpAdAction.adActionEndDate,");  
//							hql.append(" r.pfpAd.pfpAdGroup.pfpAdAction.adType,");
//							hql.append(" r.pfpAd.pfpAdGroup.pfpAdAction.adActionStatus");
//							hql.append(" from PfpAdPvclk as r ");
//							hql.append(" where 1 = 1 ");
//							hql.append(" and r.customerInfoId ='" + customerInfoId + "'");
//							hql.append(" and r.adPvclkDate >='" + startDate + "'");
//							hql.append(" and r.adPvclkDate <='" + endDate + "'");
//
//							if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
//								hql.append(" and r.adType = " + adShowWay);
//							}
//
//							if (StringUtils.isNotEmpty(searchText)) {
//								String searchStr=getSearchText(searchText,adSearchWay);
//								hql.append(" and r.pfpAd.pfpAdGroup.pfpAdAction.adActionName LIKE ('"+searchStr+"')");
//							}
//
//							hql.append(" group by r.pfpAd.pfpAdGroup.pfpAdAction.adActionSeq");
//							hql.append(" order by r.pfpAd.pfpAdGroup.pfpAdAction.adActionUpdateTime desc");
//
//						
//							Query q2 = session.createQuery(hql.toString());
//
//							//page=-1 取得全部不分頁用於download
//							if (page!=-1) {
//								q2.setFirstResult((page-1)*pageSize).setMaxResults(pageSize);
//							}
//
//							List<Object> dataList2 = q2.list();
//							log.info(">>> dataList2.size() = " + dataList2.size());
//
//							for (int i=0; i<dataList2.size(); i++) {
//
//								Object[] objArray = (Object[]) dataList2.get(i);
//
////								BigDecimal pv = (BigDecimal) objArray[0];
////								BigDecimal click = (BigDecimal) objArray[1];
////								Double cost = (Double) objArray[2];
////								BigDecimal invClick = (BigDecimal) objArray[3];
////								Double adActionMaxPrice = (Double) objArray[5];
////								BigInteger count = (BigInteger) objArray[6];
////								String adActionSeq = (String) objArray[7];
////								String adDevice = (String) objArray[8];
//
//								System.out.println("adActionMaxPrice2 = " + objArray[7]);
//								System.out.println("count = " + objArray[8]);
//							}
//							
//						} catch(Exception ex) {
//							System.out.println("ex = " + ex);
//						}
						
						return resultData;
					}
				}
		);

		return result;
	}

	/**
	 * 統計 pfp_ad_action_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用的數量
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_action_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 * @param searchText 查詢字串
	 * @param adSearchWay 查詢方式，adsearch_include:字詞包含、adsearch_begin:開頭文字是、adsearch_same:完全符合
	 * @param adShowWay 廣告顯示方式
	 * @param adPvclkDevice 裝置
	 * @param customerInfoId 用戶帳號ID
	 * @param startDate 開始日期
	 * @param endDate 結束日期
	 * @return String
	 * @throws Exception
	 */
	private HashMap<String, Object> getAdActionReportCountSQL(final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate) {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select ");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");
		hql.append(" sum(r.ad_clk_price), ");	
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" SUM(r.convert_count),  ");
		hql.append(" SUM(r.convert_price_count) ");
		hql.append(" from pfp_ad_action_report as r ");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id = :customerInfoId");
		hql.append(" and r.ad_pvclk_date >= :startDate");
		hql.append(" and r.ad_pvclk_date <= :endDate");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", startDate);
		sqlParams.put("endDate", endDate);

		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" and r.ad_type = :adShowWay");
			sqlParams.put("adShowWay", adShowWay);
		}

		if (StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" and r.ad_pvclk_device = :adPvclkDevice");
			sqlParams.put("adPvclkDevice", adPvclkDevice);
		}

		if (StringUtils.isNotEmpty(searchText)) {
			String searchStr = getSearchText(searchText, adSearchWay);
			hql.append(" and r.ad_action_seq in (select ad_action_seq from pfp_ad_action where 1=1");
			hql.append(" and customer_info_id = :customerInfoId");
//			if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
//				hql.append(" and ad_type = " + adShowWay);
//			}
			hql.append(" and ad_action_name like :searchStr)");
			sqlParams.put("searchStr", searchStr);
		}

		if (StringUtils.isNotBlank(adOperatingRule)) {
			hql.append(" and r.ad_operating_rule = :adOperatingRule ");
			sqlParams.put("adOperatingRule", adOperatingRule);
		}
		
		hql.append(" group by r.ad_action_seq");
		/*if(StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" , r.ad_pvclk_device");
		}*/
		sqlParams.put("sql", hql);
		return sqlParams;
	}

	/**
	 * 統計 pfp_ad_action_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_action_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 * @param searchText 查詢字串
	 * @param adSearchWay 查詢方式，adsearch_include:字詞包含、adsearch_begin:開頭文字是、adsearch_same:完全符合
	 * @param adShowWay 廣告顯示方式
	 * @param adPvclkDevice 裝置
	 * @param customerInfoId 用戶帳號ID
	 * @param startDate 開始日期
	 * @param endDate 結束日期
	 * @return String
	 * @throws Exception
	 */
	private HashMap<String, Object> getAdActionReportDataSQL(final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate) {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select ");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");		
		hql.append(" sum(r.ad_clk_price), ");	
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" sum(r.ad_action_max_price * r.ad_action_count), ");
		hql.append(" sum(r.ad_action_count), ");
		hql.append(" r.ad_action_seq, ");
		hql.append(" r.ad_pvclk_device, ");
		hql.append(" r.ad_operating_rule, ");
		hql.append(" r.ad_type, ");
		hql.append(" SUM(r.convert_count),  ");
		hql.append(" SUM(r.convert_price_count) ");
		hql.append(" from pfp_ad_action_report as r ");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id = :customerInfoId");
		hql.append(" and r.ad_pvclk_date >= :startDate");
		hql.append(" and r.ad_pvclk_date <= :endDate");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", startDate);
		sqlParams.put("endDate", endDate);

		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" and r.ad_type = :adShowWay");
			sqlParams.put("adShowWay", adShowWay);
		}

		if (StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" and r.ad_pvclk_device = :adPvclkDevice");
			sqlParams.put("adPvclkDevice", adPvclkDevice);
		}

		if (StringUtils.isNotEmpty(searchText)) {
			String searchStr = getSearchText(searchText, adSearchWay);
			hql.append(" and r.ad_action_seq in (select ad_action_seq from pfp_ad_action where 1=1");
			hql.append(" and customer_info_id = :customerInfoId");
//			if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
//				hql.append(" and ad_type = " + adShowWay);
//			}
			hql.append(" and ad_action_name like :searchStr)");
			sqlParams.put("searchStr", searchStr);
		}

		if (StringUtils.isNotBlank(adOperatingRule)) {
			hql.append(" and r.ad_operating_rule = :adOperatingRule ");
			sqlParams.put("adOperatingRule", adOperatingRule);
		}
		
		hql.append(" group by r.ad_action_seq, r.ad_type, r.ad_operating_rule");
		/*if(StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" , r.ad_pvclk_device");
		}

		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" , r.ad_type ");
		}*/
		
		hql.append(" order by r.ad_action_seq desc, r.ad_type, r.ad_operating_rule");
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	/**
	 * 統計 pfp_ad_action_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的PDF資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_action_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 * @param searchText 查詢字串
	 * @param adSearchWay 查詢方式，adsearch_include:字詞包含、adsearch_begin:開頭文字是、adsearch_same:完全符合
	 * @param adShowWay 廣告顯示方式
	 * @param adPvclkDevice 裝置
	 * @param customerInfoId 用戶帳號ID
	 * @param startDate 開始日期
	 * @param endDate 結束日期
	 * @return String
	 * @throws Exception
	 */
	private HashMap<String, Object> getAdActionReportChartSQL(final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate) {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" r.ad_pvclk_date,");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' THEN r.ad_clk else r.ad_view end)), ");				// 產生pfp_ad_action_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_action_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" sum(r.ad_action_max_price * r.ad_action_count), ");
		hql.append(" sum(r.ad_action_count), ");
		hql.append(" SUM(r.convert_count),  ");
		hql.append(" SUM(r.convert_price_count) ");
//		hql.append(" count(r.ad_action_report_seq) ");
		hql.append(" from pfp_ad_action_report as r ");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id = :customerInfoId");
		hql.append(" and r.ad_pvclk_date >= :startDate");
		hql.append(" and r.ad_pvclk_date <= :endDate");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", startDate);
		sqlParams.put("endDate", endDate);

		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" and r.ad_type = :adShowWay");
			sqlParams.put("adShowWay", adShowWay);
		}

		if (StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" and r.ad_pvclk_device = :adPvclkDevice");
			sqlParams.put("adPvclkDevice", adPvclkDevice);
		}

		if (StringUtils.isNotEmpty(searchText)) {
			String searchStr = getSearchText(searchText, adSearchWay);
			hql.append(" and r.ad_action_seq in (select ad_action_seq from pfp_ad_action where 1=1");
			hql.append(" and customer_info_id = :customerInfoId");
//			if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
//				hql.append(" and ad_type = " + adShowWay);
//			}
			hql.append(" and ad_action_name like :searchStr)");
			sqlParams.put("searchStr", searchStr);
		}

		if (StringUtils.isNotBlank(adOperatingRule)) {
			hql.append(" and r.ad_operating_rule = :adOperatingRule ");
			sqlParams.put("adOperatingRule", adOperatingRule);
		}
		
		hql.append(" group by r.ad_pvclk_date");
		/*if(StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" , r.ad_pvclk_device");
		}*/
		hql.append(" order by r.ad_pvclk_date");
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	/**
	 * 統計 pfp_ad_action_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_action_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 * @param searchText 查詢字串
	 * @param adSearchWay 查詢方式，adsearch_include:字詞包含、adsearch_begin:開頭文字是、adsearch_same:完全符合
	 * @param adShowWay 廣告顯示方式
	 * @param adPvclkDevice 裝置
	 * @param customerInfoId 用戶帳號ID
	 * @param startDate 開始日期
	 * @param endDate 結束日期
	 * @return String
	 * @throws Exception
	 */
	private HashMap<String, Object> getDailyCountHQLStr(final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String startDate, final String endDate){
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
		hql.append(" from pfp_ad_action_report as r ");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id =:customerInfoId");
		hql.append(" and r.ad_pvclk_date >=:startDate");
		hql.append(" and r.ad_pvclk_date <=:endDate");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", startDate);
		sqlParams.put("endDate", endDate);

		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" and r.ad_type = :adShowWay");
			sqlParams.put("adShowWay", adShowWay);
		}

		if (StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" and r.ad_pvclk_device = :adPvclkDevice");
			sqlParams.put("adPvclkDevice", adPvclkDevice);
		}

		if (StringUtils.isNotEmpty(searchText)) {
			String searchStr = getSearchText(searchText, adSearchWay);
			hql.append(" and r.ad_action_seq in (select ad_action_seq from pfp_ad_action where 1=1");
			hql.append(" and customer_info_id = :customerInfoId");
//			if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
//				hql.append(" and ad_type = " + adShowWay);
//			}
			hql.append(" and ad_action_name like :searchStr)");
			sqlParams.put("searchStr", searchStr);
		}

		hql.append(" group by r.ad_pvclk_date");
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	/**
	 * 統計 pfp_ad_action_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_action_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 * @param searchText 查詢字串
	 * @param adSearchWay 查詢方式，adsearch_include:字詞包含、adsearch_begin:開頭文字是、adsearch_same:完全符合
	 * @param adShowWay 廣告顯示方式
	 * @param adPvclkDevice 裝置
	 * @param customerInfoId 用戶帳號ID
	 * @param startDate 開始日期
	 * @param endDate 結束日期
	 * @return String
	 * @throws Exception
	 */
	private HashMap<String, Object> getDailyHQLStr(final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String startDate, final String endDate){
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" r.ad_pvclk_date,");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");		
		hql.append(" sum(r.ad_clk_price), ");	
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" r.ad_pvclk_device, ");
		hql.append(" r.ad_type, ");
		hql.append(" SUM(r.convert_count),  ");
		hql.append(" SUM(r.convert_price_count) ");
		hql.append(" from pfp_ad_action_report as r ");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id =:customerInfoId");
		hql.append(" and r.ad_pvclk_date >=:startDate");
		hql.append(" and r.ad_pvclk_date <=:endDate");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", startDate);
		sqlParams.put("endDate", endDate);

		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" and r.ad_type = :adShowWay");
			sqlParams.put("adShowWay", adShowWay);
		}

		if (StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" and r.ad_pvclk_device = :adPvclkDevice");
			sqlParams.put("adPvclkDevice", adPvclkDevice);
		}

		if (StringUtils.isNotEmpty(searchText)) {
			String searchStr = getSearchText(searchText, adSearchWay);
			hql.append(" and r.ad_action_seq in (select ad_action_seq from pfp_ad_action where 1=1");
			hql.append(" and customer_info_id = :customerInfoId");
//			if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
//				hql.append(" and ad_type = " + adShowWay);
//			}
			hql.append(" and ad_action_name like :searchStr)");
			sqlParams.put("searchStr", searchStr);
		}

		hql.append(" group by r.ad_pvclk_date");
		if(StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" , r.ad_pvclk_device");
		}

		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" , r.ad_type");
		}
		
		hql.append(" order by r.ad_pvclk_date desc");
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	/**
	 * 統計 pfp_ad_action_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_action_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 * @param searchText 查詢字串
	 * @param adSearchWay 查詢方式，adsearch_include:字詞包含、adsearch_begin:開頭文字是、adsearch_same:完全符合
	 * @param adShowWay 廣告顯示方式
	 * @param adPvclkDevice 裝置
	 * @param customerInfoId 用戶帳號ID
	 * @param startDate 開始日期
	 * @param endDate 結束日期
	 * @return String
	 * @throws Exception
	 */
	private HashMap<String, Object> getDailyChartHQLStr(final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String startDate, final String endDate){
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" r.ad_pvclk_date,");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");
		hql.append(" sum(r.ad_clk_price), ");	
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" SUM(r.convert_count),  ");
		hql.append(" SUM(r.convert_price_count) ");
		hql.append(" from pfp_ad_action_report as r ");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id =:customerInfoId");
		hql.append(" and r.ad_pvclk_date >=:startDate");
		hql.append(" and r.ad_pvclk_date <=:endDate");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", startDate);
		sqlParams.put("endDate", endDate);

		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" and r.ad_type = :adShowWay");
			sqlParams.put("adShowWay", adShowWay);
		}

		if (StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" and r.ad_pvclk_device = :adPvclkDevice");
			sqlParams.put("adPvclkDevice", adPvclkDevice);
		}

		if (StringUtils.isNotEmpty(searchText)) {
			String searchStr = getSearchText(searchText, adSearchWay);
			hql.append(" and r.ad_action_seq in (select ad_action_seq from pfp_ad_action where 1=1");
			hql.append(" and customer_info_id = :customerInfoId");
//			if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
//				hql.append(" and ad_type = " + adShowWay);
//			}
			hql.append(" and ad_action_name like :searchStr)");
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
	 * 每日花費成效(明細)
	 * 統計 pfp_ad_action_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_action_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdDailyList(AdActionReportVO vo) {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT ");
		hql.append("  r.ad_pvclk_date, ");
		hql.append("  SUM(r.ad_pv) AS ad_pv_sum, ");
		hql.append("  SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN r.ad_clk ELSE r.ad_view END)) AS ad_clk_sum, ");		
		hql.append("  SUM(r.ad_clk_price) AS ad_price_sum, ");
//		hql.append("  r.ad_pvclk_device AS ad_device, ");
		hql.append("  SUM(r.convert_count) AS convert_count, ");
		hql.append("  SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_action_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id = :customerInfoId ");
		hql.append(" AND r.ad_pvclk_date >= :startDate ");
		hql.append(" AND r.ad_pvclk_date <= :endDate ");
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND REPLACE(r.ad_pvclk_date, '-', '') LIKE REPLACE(:searchStr, '-', '') ");
		}
		
//		String adDevice = "";
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			
//			adDevice = tempJSONObject.optString("adDevice");
//			if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice)) {
//				hql.append(" AND r.ad_pvclk_device = :adPvclkDevice");
//			}
		}
		
		hql.append(" GROUP BY r.ad_pvclk_date ");
		hql.append(" ORDER BY r.ad_pvclk_date DESC ");
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
//		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice)) {
//			query.setString("adPvclkDevice", adDevice);
//		}
		
		if (!vo.isDownloadOrIsNotCuttingPagination()) { // 不是下載則做分頁處理
			// 取得分頁
			query.setFirstResult((vo.getPage() - 1) * vo.getPageSize());
			query.setMaxResults(vo.getPageSize());
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 每日花費成效(加總)
	 * 統計 pfp_ad_action_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_action_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 * @param vo
	 * @return
	 */
	public List<Map<String, Object>> getAdDailyListSum(AdActionReportVO vo) {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT ");
		hql.append("  SUM(r.ad_pv) AS ad_pv_sum, ");
		hql.append("  SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN r.ad_clk ELSE r.ad_view end)) AS ad_clk_sum, ");		
		hql.append("  SUM(r.ad_clk_price) AS ad_price_sum, ");
		hql.append("  SUM(r.convert_count) AS convert_count, ");
		hql.append("  SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_action_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id = :customerInfoId ");
		hql.append(" AND r.ad_pvclk_date >= :startDate ");
		hql.append(" AND r.ad_pvclk_date <= :endDate ");
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND REPLACE(r.ad_pvclk_date, '-', '') LIKE REPLACE(:searchStr, '-', '') ");
		}
		
//		String adDevice = "";
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			
//			adDevice = tempJSONObject.optString("adDevice");
//			if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice)) {
//				hql.append(" AND r.ad_pvclk_device = :adPvclkDevice");
//			}
		}
		
		hql.append(" GROUP BY r.ad_pvclk_date ");
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
//		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice)) {
//			query.setString("adPvclkDevice", adDevice);
//		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 總廣告成效、廣告成效共用(明細)
	 * 統計 pfp_ad_action_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_action_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 */
	@Override
	public List<Map<String, Object>> getAdCampaginList(AdCampaginReportVO vo) {
		String adType = ""; // 播放類型
		String adOperatingRule = ""; // 廣告樣式
		String adDevice = ""; // 裝置
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			adType = tempJSONObject.optString("adType");
			adOperatingRule = tempJSONObject.optString("adOperatingRule");
			adDevice = tempJSONObject.optString("adDevice");
		}
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT ");
		hql.append(" SUM(r.ad_pv) AS ad_pv_sum, ");
		hql.append(" SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN r.ad_clk ELSE r.ad_view END)) AS ad_clk_sum, ");		
		hql.append(" SUM(r.ad_clk_price) AS ad_price_sum, ");
		hql.append(" SUM(r.ad_invalid_clk) AS ad_invalid_clk_sum, ");
		hql.append(" SUM(r.ad_action_max_price * r.ad_action_count) AS ad_action_max_price_sum, ");
		hql.append(" SUM(r.ad_action_count) AS ad_action_count_sum, ");
		hql.append(" r.ad_action_seq, ");
		hql.append(" r.ad_pvclk_device AS ad_device, ");
		hql.append(" r.ad_operating_rule, ");
		hql.append(" r.ad_type, ");
		hql.append(" SUM(r.convert_count) AS convert_count, ");
		hql.append(" SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_action_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id = :customerInfoId");
		hql.append(" AND r.ad_pvclk_date >= :startDate");
		hql.append(" AND r.ad_pvclk_date <= :endDate");
		
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
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			hql.append(" AND r.ad_pvclk_device = :adPvclkDevice");
		}
		
		hql.append(" GROUP BY r.ad_action_seq, r.ad_operating_rule");
		// 非搜尋 + 聯播網廣告選項，則將資料區分聯播網廣告和搜尋廣告
		if (StringUtils.isBlank(adType) || "all".equalsIgnoreCase(adType)) {
			hql.append(" ,r.ad_type");
		}
		
		// 裝置空值或選擇全部時，則將資料區分PC和mobile
		if (StringUtils.isBlank(adDevice) || "all".equalsIgnoreCase(adDevice)) {
			hql.append(" ,r.ad_pvclk_device");
		}
		
		hql.append(" ORDER BY r.ad_action_seq DESC, r.ad_type, r.ad_operating_rule");
		
		
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
	 * 總廣告成效、廣告成效共用(加總)
	 * 統計 pfp_ad_action_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_action_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 */
	@Override
	public List<Map<String, Object>> getAdCampaginListSum(AdCampaginReportVO vo) {
		String adType = ""; // 播放類型
		String adOperatingRule = ""; // 廣告樣式
		String adDevice = ""; // 裝置
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			adType = tempJSONObject.optString("adType");
			adOperatingRule = tempJSONObject.optString("adOperatingRule");
			adDevice = tempJSONObject.optString("adDevice");
		}
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT ");
		hql.append(" SUM(r.ad_pv) AS ad_pv_sum, ");
		hql.append(" SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN r.ad_clk ELSE r.ad_view END)) AS ad_clk_sum, ");
		hql.append(" SUM(r.ad_clk_price) AS ad_price_sum, ");
		hql.append(" SUM(r.ad_invalid_clk) AS ad_invalid_clk_sum, ");
		hql.append(" SUM(r.ad_action_max_price * r.ad_action_count) AS ad_action_max_price_sum, ");
		hql.append(" SUM(r.ad_action_count) AS ad_action_count_sum, ");
		hql.append(" SUM(r.convert_count) AS convert_count, ");
		hql.append(" SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_action_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id = :customerInfoId");
		hql.append(" AND r.ad_pvclk_date >= :startDate");
		hql.append(" AND r.ad_pvclk_date <= :endDate");
		
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
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			hql.append(" AND r.ad_pvclk_device = :adPvclkDevice");
		}
		
		hql.append(" GROUP BY r.ad_action_seq, r.ad_operating_rule");
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
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			query.setString("adPvclkDevice", adDevice);
		}
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 總廣告成效、廣告成效共用(圖表)
	 * 統計 pfp_ad_action_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的PDF資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_action_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 */
	@Override
	public List<Map<String, Object>> getAdCampaginListChart(AdCampaginReportVO vo) {
		String adType = ""; // 播放類型
		String adOperatingRule = ""; // 廣告樣式
		String adDevice = ""; // 裝置
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			adType = tempJSONObject.optString("adType");
			adOperatingRule = tempJSONObject.optString("adOperatingRule");
			adDevice = tempJSONObject.optString("adDevice");
		}
		
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT");
		hql.append(" r.ad_pvclk_date,");
		hql.append(" SUM(r.ad_pv) AS ad_pv_sum, ");
		hql.append(" SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN r.ad_clk ELSE r.ad_view END)) AS ad_clk_sum, ");
		hql.append(" SUM(r.ad_clk_price) AS ad_price_sum, ");
		hql.append(" SUM(r.ad_invalid_clk) AS ad_invalid_clk_sum, ");
		hql.append(" SUM(r.convert_count) AS convert_count, ");
		hql.append(" SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_action_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id = :customerInfoId");
		hql.append(" AND r.ad_pvclk_date >= :startDate");
		hql.append(" AND r.ad_pvclk_date <= :endDate");
		
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
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			hql.append(" AND r.ad_pvclk_device = :adPvclkDevice");
		}
		
		hql.append(" GROUP BY r.ad_pvclk_date");
		hql.append(" ORDER BY r.ad_pvclk_date");
		
		
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
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			query.setString("adPvclkDevice", adDevice);
		}
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

}