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
import com.pchome.soft.depot.utils.ObjectTransUtil;
import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdKeywordPvclk;

@Transactional
public class AdKeywordReportDAO extends BaseDAO<PfpAdKeywordPvclk, Integer> implements IAdKeywordReportDAO {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public List<AdKeywordReportVO> getReportList(final String sqlType, final String adGroupSeq, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String startDate, final String endDate, final int page, final int pageSize) {

		List<AdKeywordReportVO> result = (List<AdKeywordReportVO>) getHibernateTemplate().execute(
				new HibernateCallback<List<AdKeywordReportVO>>() {
					public List<AdKeywordReportVO> doInHibernate(Session session) throws HibernateException, SQLException {

						String hqlStr = "";
                        HashMap<String, Object> sqlParams = new HashMap<String, Object>();

						//log.info(">>> sqlType = " + sqlType);
						//log.info(">>> searchText = " + searchText);

						if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_COUNT.getTextValue())) {

							//總廣告成效 -> 報表類型:關鍵字 (數量及加總)
							//關鍵字成效 (數量及加總)
							//hqlStr = getAdKeywordReportCountSQL(adGroupSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							try {
								sqlParams = getAdKeywordReportCountSQL(adGroupSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT.getTextValue())) {

							//總廣告成效 -> 報表類型:關鍵字 (資料)
							//關鍵字成效 (資料)
							//hqlStr = getAdKeywordReportDataSQL(adGroupSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							try {
								sqlParams = getAdKeywordReportDataSQL(adGroupSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_CHART.getTextValue())) {

							//總廣告成效 -> 報表類型:關鍵字 (圖表)
							//關鍵字成效 (圖表)
							//hqlStr = getAdKeywordReportChartSQL(adGroupSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
							try {
								sqlParams = getAdKeywordReportChartSQL(adGroupSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
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

//						Query q = session.createSQLQuery(hqlStr);
//
//						//page=-1 取得全部不分頁用於download
//						if (page!=-1) {
//							q.setFirstResult((page-1)*pageSize).setMaxResults(pageSize);
//						}
//
//						List<Object> dataList = q.list();
//						log.info(">>> dataList.size() = " + dataList.size());

						List<AdKeywordReportVO> resultData = new ArrayList<AdKeywordReportVO>();
						
						if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT.getTextValue())) {

							Object[] objArray = null;
							AdKeywordReportVO kwReportVO = null;

							BigDecimal kwPvSum = null;
							BigDecimal kwClkSum = null;
							Double kwPriceSum = null;
							BigDecimal kwInvClkSum = null;
							String kwPvclkDate = null;
							String adActionSeq = null;
							String adGroupSeq = null;
							String adKeywordSeq = null;
							String AdKeyword = null;
							String customerInfoId = null;
							String kwDevice = null;

							for (int i=0; i<dataList.size(); i++) {

								objArray = (Object[]) dataList.get(i);

								kwPvSum = (BigDecimal)objArray[0];
								kwClkSum = (BigDecimal)objArray[1];
								kwPriceSum = (Double)objArray[2];
								kwInvClkSum = (BigDecimal)objArray[3];
								kwPvclkDate = ObjectTransUtil.getInstance().getObjectToString(objArray[4]);
								adActionSeq = ObjectTransUtil.getInstance().getObjectToString(objArray[5]);
								adGroupSeq = ObjectTransUtil.getInstance().getObjectToString(objArray[6]);
								adKeywordSeq = ObjectTransUtil.getInstance().getObjectToString(objArray[7]);
								AdKeyword = ObjectTransUtil.getInstance().getObjectToString(objArray[8]);
								customerInfoId = ObjectTransUtil.getInstance().getObjectToString(objArray[9]);
								kwDevice = ObjectTransUtil.getInstance().getObjectToString(objArray[10]);
//								System.out.println("kwPvSum = " + kwPvSum);
//								System.out.println("kwClkSum = " + kwClkSum);
//								System.out.println("kwPriceSum = " + kwPriceSum);
//								System.out.println("kwInvClkSum = " + kwInvClkSum);
//								System.out.println("kwPvclkDate = " + kwPvclkDate);
//								System.out.println("adActionSeq = " + adActionSeq);
//								System.out.println("adGroupSeq = " + adGroupSeq);
//								System.out.println("adKeywordSeq = " + adKeywordSeq);
//								System.out.println("AdKeyword = " + AdKeyword);
//								System.out.println("adActionSeq = " + objArray[5]);
//								System.out.println("adGroupSeq = " + objArray[6]);
//								System.out.println("adKeywordSeq = " + objArray[7]);
//								System.out.println("AdKeyword = " + objArray[8]);

								String hql2 = null;

								//求平均排名
								//hql2 = getAdRankHQLStr(customerInfoId, adKeywordSeq, adShowWay, startDate, endDate);
								HashMap<String, Object> sqlParams_2 = new HashMap<String, Object>();
								try {
									sqlParams_2 = getAdRankHQLStr(customerInfoId, adKeywordSeq, adShowWay, startDate, endDate);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								hql2 = sqlParams_2.get("sql").toString();
								System.out.println("hql2 = " + hql2);
								
								Query query_2 = session.createQuery(hql2);
						        for (String paramName_2:sqlParams_2.keySet()) {
						        	if(!paramName_2.equals("sql")) {
						        		query_2.setParameter(paramName_2, sqlParams_2.get(paramName_2));
						        	}
						        }

								String adRankAvg = query_2.list().get(0).toString();

								kwReportVO = new AdKeywordReportVO();

								kwReportVO.setKwPvSum(kwPvSum.toString());
								kwReportVO.setKwClkSum(kwClkSum.toString());
								kwReportVO.setKwPriceSum(kwPriceSum.toString());
								kwReportVO.setKwInvClkSum(kwInvClkSum.toString());
								kwReportVO.setKwPvclkDate(kwPvclkDate);
								kwReportVO.setAdActionSeq(adActionSeq);
								kwReportVO.setAdGroupSeq(adGroupSeq);
								kwReportVO.setAdKeywordSeq(adKeywordSeq);
								kwReportVO.setAdKeyword(AdKeyword);
								kwReportVO.setAdRankAvg(adRankAvg);
								kwReportVO.setCustomerInfoId(customerInfoId);
								if(StringUtils.isNotBlank(adPvclkDevice)) {
									kwReportVO.setKwDevice(kwDevice);
								} else {
									kwReportVO.setKwDevice("全部");
								}
//								System.out.println("kwReportVO.getKwPvSum() = " + kwReportVO.getKwPvSum());
//								System.out.println("kwReportVO.getKwClkSum() = " + kwReportVO.getKwClkSum());
//								System.out.println("kwReportVO.getKwPriceSum() = " + kwReportVO.getKwPriceSum());
//								System.out.println("kwReportVO.getKwInvClkSum() = " + kwReportVO.getKwInvClkSum());
//								System.out.println("kwReportVO.getKwPvclkDate = " + kwReportVO.getKwPvclkDate());
//								System.out.println("kwReportVO.getAdActionSeq = " + kwReportVO.getAdActionSeq());
//								System.out.println("kwReportVO.getAdKeywordSeq = " + kwReportVO.getAdKeywordSeq());
//								System.out.println("kwReportVO.getAdGroupSeq = " + kwReportVO.getAdGroupSeq());
//								System.out.println("kwReportVO.getAdKeyword() = " + kwReportVO.getAdKeyword());
//								System.out.println("kwReportVO.getAdRankAvg() = " + kwReportVO.getAdRankAvg());
//								System.out.println("");

								resultData.add(kwReportVO);
							}

						} else if(sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_COUNT.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								AdKeywordReportVO kwReportVO = new AdKeywordReportVO();

								kwReportVO.setKwPvSum(((BigDecimal)objArray[0]).toString());
								kwReportVO.setKwClkSum(((BigDecimal)objArray[1]).toString());
								kwReportVO.setKwPriceSum(((Double)objArray[2]).toString());
								kwReportVO.setKwInvClkSum(((BigDecimal)objArray[3]).toString());
								
								resultData.add(kwReportVO);
							}

						} else if(sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_CHART.getTextValue())) {

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								AdKeywordReportVO kwReportVO = new AdKeywordReportVO();

								kwReportVO.setReportDate(ObjectTransUtil.getInstance().getObjectToString(objArray[0]));
								kwReportVO.setKwPvSum(((BigDecimal)objArray[1]).toString());
								kwReportVO.setKwClkSum(((BigDecimal)objArray[2]).toString());
								kwReportVO.setKwPriceSum(((Double)objArray[3]).toString());
								kwReportVO.setKwInvClkSum(((BigDecimal)objArray[4]).toString());
								
								resultData.add(kwReportVO);
							}
						}

						return resultData;

					}
				}
		);

		return result;
	}

	private HashMap<String, Object> getAdKeywordReportCountSQL(String adGroupSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String startDate, String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select ");
		hql.append(" sum(r.ad_keyword_pv), ");
		hql.append(" sum(r.ad_keyword_clk), ");			// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_keyword_clk_price), ");	// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_keyword_invalid_clk), ");
		hql.append(" sum(r.ad_keyword_invalid_clk_price) ");
		hql.append(" from pfp_ad_keyword_report as r ");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id = :customerInfoId ");
		hql.append(" and r.ad_keyword_pvclk_date >= :startDate ");
		hql.append(" and r.ad_keyword_pvclk_date <= :endDate ");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", sdf.parse(startDate));
		sqlParams.put("endDate", sdf.parse(endDate));

		if (StringUtils.isNotBlank(adGroupSeq)) {
			hql.append(" and r.ad_group_seq = :adGroupSeq ");
			sqlParams.put("adGroupSeq", adGroupSeq);
		}

		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" and r.ad_keyword_type = :adShowWay");
			sqlParams.put("adShowWay", Integer.parseInt(adShowWay));
		}

		if (StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" and r.ad_keyword_pvclk_device = :adPvclkDevice ");
			sqlParams.put("adPvclkDevice", adPvclkDevice);
		}

		if (StringUtils.isNotEmpty(searchText)) {
			String searchStr = getSearchText(searchText, adSearchWay);
			hql.append(" and ad_keyword like :searchStr ");
			sqlParams.put("searchStr", searchStr);
		}

		hql.append(" group by r.ad_keyword_seq");
		if(StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" , r.ad_keyword_pvclk_device");
		}
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private HashMap<String, Object> getAdKeywordReportDataSQL(String adGroupSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String startDate, String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select ");
		hql.append(" sum(r.ad_keyword_pv), ");
		hql.append(" sum(r.ad_keyword_clk), ");			// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_keyword_clk_price), ");	// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_keyword_invalid_clk), ");
		hql.append(" r.ad_keyword_pvclk_date, ");
		hql.append(" r.ad_action_seq, ");
		hql.append(" r.ad_group_seq, ");
		hql.append(" r.ad_keyword_seq, ");
		hql.append(" r.ad_keyword, ");
		hql.append(" r.customer_info_id, ");
		hql.append(" r.ad_keyword_pvclk_device ");
		hql.append(" from pfp_ad_keyword_report as r ");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id =:customerInfoId ");
		hql.append(" and r.ad_keyword_pvclk_date >=:startDate ");
		hql.append(" and r.ad_keyword_pvclk_date <=:endDate ");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", sdf.parse(startDate));
		sqlParams.put("endDate", sdf.parse(endDate));

		if (StringUtils.isNotBlank(adGroupSeq)) {
			hql.append(" and r.ad_group_seq = :adGroupSeq ");
			sqlParams.put("adGroupSeq", adGroupSeq);
		}

		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" and r.ad_keyword_type = :adShowWay");
			sqlParams.put("adShowWay", Integer.parseInt(adShowWay));
		}

		if (StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" and r.ad_keyword_pvclk_device = :adPvclkDevice ");
			sqlParams.put("adPvclkDevice", adPvclkDevice);
		}

		if (StringUtils.isNotEmpty(searchText)) {
			String searchStr = getSearchText(searchText, adSearchWay);
			hql.append(" and ad_keyword like :searchStr ");
			sqlParams.put("searchStr", searchStr);
		}

		hql.append(" group by r.ad_keyword_seq");
		if(StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" , r.ad_keyword_pvclk_device");
		}

		hql.append(" order by r.ad_keyword_seq desc");
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private HashMap<String, Object> getAdKeywordReportChartSQL(String adGroupSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String startDate, String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql=new StringBuffer();

		hql.append("select ");
		hql.append(" r.ad_keyword_pvclk_date, ");
		hql.append(" sum(r.ad_keyword_pv), ");
		hql.append(" sum(r.ad_keyword_clk), ");			// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_keyword_clk_price), ");	// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_keyword_invalid_clk) ");
		hql.append(" from pfp_ad_keyword_report as r ");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id =:customerInfoId ");
		hql.append(" and r.ad_keyword_pvclk_date >=:startDate ");
		hql.append(" and r.ad_keyword_pvclk_date <=:endDate ");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", sdf.parse(startDate));
		sqlParams.put("endDate", sdf.parse(endDate));

		if (StringUtils.isNotBlank(adGroupSeq)) {
			hql.append(" and r.ad_group_seq = :adGroupSeq ");
			sqlParams.put("adGroupSeq", adGroupSeq);
		}

		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" and r.ad_keyword_type = :adShowWay");
			sqlParams.put("adShowWay", Integer.parseInt(adShowWay));
		}

		if (StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" and r.ad_keyword_pvclk_device = :adPvclkDevice ");
			sqlParams.put("adPvclkDevice", adPvclkDevice);
		}

		if (StringUtils.isNotEmpty(searchText)) {
			String searchStr = getSearchText(searchText, adSearchWay);
			hql.append(" and ad_keyword like :searchStr ");
			sqlParams.put("searchStr", searchStr);
		}

		hql.append(" group by r.ad_keyword_pvclk_date");
		if(StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" , r.ad_keyword_pvclk_device");
		}

		hql.append(" order by r.ad_keyword_pvclk_date");
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private HashMap<String, Object> getAdRankHQLStr(String customerInfoId, String adKeywordSeq, String adType, String startDate, String endDate) throws ParseException {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" select COALESCE(sum(pakar.adRankAvg)/count(pakar.adRankAvg),0) ");
		hql.append(" from PfpAdRank pakar ");
		hql.append(" where pakar.adRankDate >= :startDate ");
		hql.append(" and pakar.adRankDate <= :endDate ");
		sqlParams.put("startDate", sdf.parse(startDate));
		sqlParams.put("endDate", sdf.parse(endDate));
		if (StringUtils.isNotEmpty(adType) && (Integer.parseInt(adType) != EnumAdType.AD_ALL.getType())) {
			hql.append(" and pakar.adType = :adType");
			sqlParams.put("adType", Integer.parseInt(adType));
		}
		hql.append(" and pakar.pfpAdKeyword.adKeywordSeq = :adKeywordSeq ");
		hql.append(" and pakar.customerInfoId = :customerInfoId ");
		sqlParams.put("adKeywordSeq", adKeywordSeq);
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private String getSearchText(String searchText, String searchWay) {
		String searchStr = "";
		if (searchWay.equals(EnumReport.ADSEARCH_BEGIN.getTextValue())) {
			searchStr = searchText + "%";
		}
		if (searchWay.equals(EnumReport.ADSEARCH_INCLUDE.getTextValue())) {
			searchStr = "%" + searchText + "%";
		}
		if (searchWay.equals(EnumReport.ADSEARCH_SAME.getTextValue())) {
			searchStr = searchText;
		}
		return searchStr;
	}
}
