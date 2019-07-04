package com.pchome.akbpfp.db.dao.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdKeywordPvclk;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.report.EnumReport;

@Transactional
public class AdKeywordReportDAO extends BaseDAO<PfpAdKeywordPvclk, Integer> implements IAdKeywordReportDAO {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//	public List<AdKeywordReportVO> getReportList(final String sqlType, final String adGroupSeq, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String startDate, final String endDate, final int page, final int pageSize) {
//
//		List<AdKeywordReportVO> result = (List<AdKeywordReportVO>) getHibernateTemplate().execute(
//				new HibernateCallback<List<AdKeywordReportVO>>() {
//					public List<AdKeywordReportVO> doInHibernate(Session session) throws HibernateException {
//
//						String hqlStr = "";
//                        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
//
//						//log.info(">>> sqlType = " + sqlType);
//						//log.info(">>> searchText = " + searchText);
//
//						if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_COUNT.getTextValue())) {
//
//							//總廣告成效 -> 報表類型:關鍵字 (數量及加總)
//							//關鍵字成效 (數量及加總)
//							//hqlStr = getAdKeywordReportCountSQL(adGroupSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
//							try {
//								sqlParams = getAdKeywordReportCountSQL(adGroupSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
//							} catch (ParseException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//
//						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT.getTextValue())) {
//
//							//總廣告成效 -> 報表類型:關鍵字 (資料)
//							//關鍵字成效 (資料)
//							//hqlStr = getAdKeywordReportDataSQL(adGroupSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
//							try {
//								sqlParams = getAdKeywordReportDataSQL(adGroupSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
//							} catch (ParseException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//
//						} else if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_CHART.getTextValue())) {
//
//							//總廣告成效 -> 報表類型:關鍵字 (圖表)
//							//關鍵字成效 (圖表)
//							//hqlStr = getAdKeywordReportChartSQL(adGroupSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
//							try {
//								sqlParams = getAdKeywordReportChartSQL(adGroupSeq, searchText, adSearchWay, adShowWay, adPvclkDevice, customerInfoId, startDate, endDate);
//							} catch (ParseException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//						hqlStr = sqlParams.get("sql").toString();
//						log.info(">>> hqlStr = " + hqlStr);
//
//						List<Object> dataList = null;
//
//						Query query = session.createSQLQuery(hqlStr);
//				        for (String paramName:sqlParams.keySet()) {
//				        	if(!paramName.equals("sql")) {
//				        		query.setParameter(paramName, sqlParams.get(paramName));
//				        	}
//				        }
//
//						//page > -1 取得分頁用於download
//						if(page > -1){
//							query.setFirstResult((page-1)*pageSize);
//							query.setMaxResults(pageSize);
//						}
//
//						dataList = query.list();
//
////						Query q = session.createSQLQuery(hqlStr);
////
////						//page=-1 取得全部不分頁用於download
////						if (page!=-1) {
////							q.setFirstResult((page-1)*pageSize).setMaxResults(pageSize);
////						}
////
////						List<Object> dataList = q.list();
////						log.info(">>> dataList.size() = " + dataList.size());
//
//						List<AdKeywordReportVO> resultData = new ArrayList<AdKeywordReportVO>();
//						
//						if (sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT.getTextValue())) {
//
//							Object[] objArray = null;
//							AdKeywordReportVO kwReportVO = null;
//
//							//廣泛比對
//							BigDecimal kwPvSum = null;
//							BigDecimal kwClkSum = null;
//							Double kwPriceSum = null;
//							BigDecimal kwInvClkSum = null;
//							
//							//詞組比對
//							BigDecimal kwPhrPvSum = null;
//							BigDecimal kwPhrClkSum = null;
//							Double kwPhrPriceSum = null;
//							BigDecimal kwPhrInvClkSum = null;
//							
//							//精準比對
//							BigDecimal kwPrePvSum = null;
//							BigDecimal kwPreClkSum = null;
//							Double kwPrePriceSum = null;
//							BigDecimal kwPreInvClkSum = null;
//							
//							String kwPvclkDate = null;
//							String adActionSeq = null;
//							String adGroupSeq = null;
//							String adKeywordSeq = null;
//							String AdKeyword = null;
//							String customerInfoId = null;
//							String kwDevice = null;
//							String kwAdType = null;
//
//							for (int i=0; i<dataList.size(); i++) {
//
//								objArray = (Object[]) dataList.get(i);
//								
//								//廣泛比對
//								kwPvSum = (BigDecimal)objArray[0];
//								kwClkSum = (BigDecimal)objArray[1];
//								kwPriceSum = (Double)objArray[2];
//								kwInvClkSum = (BigDecimal)objArray[3];
//								
//								//詞組比對
//								kwPhrPvSum = (BigDecimal)objArray[4];
//								kwPhrClkSum = (BigDecimal)objArray[5];
//								kwPhrPriceSum = (Double)objArray[6];
//								kwPhrInvClkSum = (BigDecimal)objArray[7];
//								
//								//精準比對
//								kwPrePvSum = (BigDecimal)objArray[8];
//								kwPreClkSum = (BigDecimal)objArray[9];
//								kwPrePriceSum = (Double)objArray[10];
//								kwPreInvClkSum = (BigDecimal)objArray[11];
//								
//								kwPvclkDate = ObjectTransUtil.getInstance().getObjectToString(objArray[12]);
//								adActionSeq = ObjectTransUtil.getInstance().getObjectToString(objArray[13]);
//								adGroupSeq = ObjectTransUtil.getInstance().getObjectToString(objArray[14]);
//								adKeywordSeq = ObjectTransUtil.getInstance().getObjectToString(objArray[15]);
//								AdKeyword = ObjectTransUtil.getInstance().getObjectToString(objArray[16]);
//								customerInfoId = ObjectTransUtil.getInstance().getObjectToString(objArray[17]);
//								kwDevice = ObjectTransUtil.getInstance().getObjectToString(objArray[18]);
//								kwAdType = ObjectTransUtil.getInstance().getObjectToString(objArray[19]);
////								System.out.println("kwPvSum = " + kwPvSum);
////								System.out.println("kwClkSum = " + kwClkSum);
////								System.out.println("kwPriceSum = " + kwPriceSum);
////								System.out.println("kwInvClkSum = " + kwInvClkSum);
////								System.out.println("kwPvclkDate = " + kwPvclkDate);
////								System.out.println("adActionSeq = " + adActionSeq);
////								System.out.println("adGroupSeq = " + adGroupSeq);
////								System.out.println("adKeywordSeq = " + adKeywordSeq);
////								System.out.println("AdKeyword = " + AdKeyword);
////								System.out.println("adActionSeq = " + objArray[5]);
////								System.out.println("adGroupSeq = " + objArray[6]);
////								System.out.println("adKeywordSeq = " + objArray[7]);
////								System.out.println("AdKeyword = " + objArray[8]);
//
//								String hql2 = null;
//								String hql3 = null;
//								String hql4 = null;
//
//								//廣泛比對求平均排名
//								//hql2 = getAdRankHQLStr(customerInfoId, adKeywordSeq, adShowWay, startDate, endDate);
//								HashMap<String, Object> sqlParams_2 = new HashMap<String, Object>();
//								try {
//									sqlParams_2 = getAdRankHQLStr(customerInfoId, adKeywordSeq, adShowWay, startDate, endDate,EnumAdKeywordType.WIDELY.getStyle());
//								} catch (ParseException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//								hql2 = sqlParams_2.get("sql").toString();
//								System.out.println("hql2 = " + hql2);
//								
//								Query query_2 = session.createQuery(hql2);
//						        for (String paramName_2:sqlParams_2.keySet()) {
//						        	if(!paramName_2.equals("sql")) {
//						        		query_2.setParameter(paramName_2, sqlParams_2.get(paramName_2));
//						        	}
//						        }
//
//								String adRankAvg = query_2.list().get(0).toString();
//								
//								//詞組比對求平均排名
//								HashMap<String, Object> sqlParams_3 = new HashMap<String, Object>();
//								try {
//									sqlParams_3 = getAdRankHQLStr(customerInfoId, adKeywordSeq, adShowWay, startDate, endDate,EnumAdKeywordType.PHRASE.getStyle());
//								} catch (ParseException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//								hql3 = sqlParams_3.get("sql").toString();
//								System.out.println("hql3 = " + hql3);
//								
//								Query query_3 = session.createQuery(hql3);
//						        for (String paramName_3:sqlParams_3.keySet()) {
//						        	if(!paramName_3.equals("sql")) {
//						        		query_3.setParameter(paramName_3, sqlParams_3.get(paramName_3));
//						        	}
//						        }
//
//								String adPhrRankAvg = query_3.list().get(0).toString();
//								
//								//精準比對求平均排名
//								HashMap<String, Object> sqlParams_4 = new HashMap<String, Object>();
//								try {
//									sqlParams_4 = getAdRankHQLStr(customerInfoId, adKeywordSeq, adShowWay, startDate, endDate,EnumAdKeywordType.PRECISION.getStyle());
//								} catch (ParseException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//								hql4 = sqlParams_4.get("sql").toString();
//								System.out.println("hql4 = " + hql4);
//								
//								Query query_4 = session.createQuery(hql4);
//						        for (String paramName_4:sqlParams_4.keySet()) {
//						        	if(!paramName_4.equals("sql")) {
//						        		query_4.setParameter(paramName_4, sqlParams_4.get(paramName_4));
//						        	}
//						        }
//
//								String adPreRankAvg = query_4.list().get(0).toString();
//
//								kwReportVO = new AdKeywordReportVO();
//
//								kwReportVO.setKwPvSum(kwPvSum.toString());
//								kwReportVO.setKwClkSum(kwClkSum.toString());
//								kwReportVO.setKwPriceSum(kwPriceSum.toString());
//								kwReportVO.setKwInvClkSum(kwInvClkSum.toString());
//								kwReportVO.setKwPhrPvSum(kwPhrPvSum.toString());
//								kwReportVO.setKwPhrClkSum(kwPhrClkSum.toString());
//								kwReportVO.setKwPhrPriceSum(kwPhrPriceSum.toString());
//								kwReportVO.setKwPhrInvClkSum(kwPhrInvClkSum.toString());
//								kwReportVO.setKwPrePvSum(kwPrePvSum.toString());
//								kwReportVO.setKwPreClkSum(kwPreClkSum.toString());
//								kwReportVO.setKwPrePriceSum(kwPrePriceSum.toString());
//								kwReportVO.setKwPreInvClkSum(kwPreInvClkSum.toString());
//								kwReportVO.setKwPvclkDate(kwPvclkDate);
//								kwReportVO.setAdActionSeq(adActionSeq);
//								kwReportVO.setAdGroupSeq(adGroupSeq);
//								kwReportVO.setAdKeywordSeq(adKeywordSeq);
//								kwReportVO.setAdKeyword(AdKeyword);
//								
//								//排名
//								kwReportVO.setAdRankAvg(adRankAvg);
//								kwReportVO.setAdPhrRankAvg(adPhrRankAvg);
//								kwReportVO.setAdPreRankAvg(adPreRankAvg);
//								
//								kwReportVO.setCustomerInfoId(customerInfoId);
//								if(StringUtils.isNotBlank(adPvclkDevice)) {
//									if("PC".equals(kwDevice)){
//										kwReportVO.setKwDevice("電腦");
//									}else if("mobile".equals(kwDevice)){
//										kwReportVO.setKwDevice("行動裝置");
//									} else {
//										kwReportVO.setKwDevice(kwDevice);	
//									}
//								} else {
//									kwReportVO.setKwDevice("全部");
//								}
//								
//								if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
//									for(EnumAdType enumAdType:EnumAdType.values()){
//										if(Integer.parseInt(kwAdType) == enumAdType.getType()){
//											kwReportVO.setKwAdType(enumAdType.getChName());
//										}
//									}
//								} else {
//									kwReportVO.setKwAdType("全部");
//								}
//								
////								System.out.println("kwReportVO.getKwPvSum() = " + kwReportVO.getKwPvSum());
////								System.out.println("kwReportVO.getKwClkSum() = " + kwReportVO.getKwClkSum());
////								System.out.println("kwReportVO.getKwPriceSum() = " + kwReportVO.getKwPriceSum());
////								System.out.println("kwReportVO.getKwInvClkSum() = " + kwReportVO.getKwInvClkSum());
////								System.out.println("kwReportVO.getKwPvclkDate = " + kwReportVO.getKwPvclkDate());
////								System.out.println("kwReportVO.getAdActionSeq = " + kwReportVO.getAdActionSeq());
////								System.out.println("kwReportVO.getAdKeywordSeq = " + kwReportVO.getAdKeywordSeq());
////								System.out.println("kwReportVO.getAdGroupSeq = " + kwReportVO.getAdGroupSeq());
////								System.out.println("kwReportVO.getAdKeyword() = " + kwReportVO.getAdKeyword());
////								System.out.println("kwReportVO.getAdRankAvg() = " + kwReportVO.getAdRankAvg());
////								System.out.println("");
//
//								resultData.add(kwReportVO);
//							}
//
//						} else if(sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_COUNT.getTextValue())) {
//
//							for (int i=0; i<dataList.size(); i++) {
//
//								Object[] objArray = (Object[]) dataList.get(i);
//
//								AdKeywordReportVO kwReportVO = new AdKeywordReportVO();
//
//								//廣泛比對
//								kwReportVO.setKwPvSum(((BigDecimal)objArray[0]).toString());
//								kwReportVO.setKwClkSum(((BigDecimal)objArray[1]).toString());
//								kwReportVO.setKwPriceSum(((Double)objArray[2]).toString());
//								kwReportVO.setKwInvClkSum(((BigDecimal)objArray[3]).toString());
//								//詞組比對
//								kwReportVO.setKwPhrPvSum(((BigDecimal)objArray[4]).toString());
//								kwReportVO.setKwPhrClkSum(((BigDecimal)objArray[5]).toString());
//								kwReportVO.setKwPhrPriceSum(((Double)objArray[6]).toString());
//								kwReportVO.setKwPhrInvClkSum(((BigDecimal)objArray[7]).toString());
//								//精準比對
//								kwReportVO.setKwPrePvSum(((BigDecimal)objArray[8]).toString());
//								kwReportVO.setKwPreClkSum(((BigDecimal)objArray[9]).toString());
//								kwReportVO.setKwPrePriceSum(((Double)objArray[10]).toString());
//								kwReportVO.setKwPreInvClkSum(((BigDecimal)objArray[11]).toString());
//								
//								resultData.add(kwReportVO);
//							}
//
//						} else if(sqlType.equals(EnumReport.REPORT_HQLTYPE_EXCERPT_CHART.getTextValue())) {
//
//							for (int i=0; i<dataList.size(); i++) {
//
//								Object[] objArray = (Object[]) dataList.get(i);
//
//								AdKeywordReportVO kwReportVO = new AdKeywordReportVO();
//
//								kwReportVO.setReportDate(ObjectTransUtil.getInstance().getObjectToString(objArray[0]));
//								//廣泛比對
//								kwReportVO.setKwPvSum(((BigDecimal)objArray[1]).toString());
//								kwReportVO.setKwClkSum(((BigDecimal)objArray[2]).toString());
//								kwReportVO.setKwPriceSum(((Double)objArray[3]).toString());
//								kwReportVO.setKwInvClkSum(((BigDecimal)objArray[4]).toString());
//								//詞組比對
//								kwReportVO.setKwPhrPvSum(((BigDecimal)objArray[5]).toString());
//								kwReportVO.setKwPhrClkSum(((BigDecimal)objArray[6]).toString());
//								kwReportVO.setKwPhrPriceSum(((Double)objArray[7]).toString());
//								kwReportVO.setKwPhrInvClkSum(((BigDecimal)objArray[8]).toString());
//								//精準比對
//								kwReportVO.setKwPrePvSum(((BigDecimal)objArray[9]).toString());
//								kwReportVO.setKwPreClkSum(((BigDecimal)objArray[10]).toString());
//								kwReportVO.setKwPrePriceSum(((Double)objArray[11]).toString());
//								kwReportVO.setKwPreInvClkSum(((BigDecimal)objArray[12]).toString());
//								
//								resultData.add(kwReportVO);
//							}
//						}
//
//						return resultData;
//
//					}
//				}
//		);
//
//		return result;
//	}

	private HashMap<String, Object> getAdKeywordReportCountSQL(String adGroupSeq, String searchText, String adSearchWay, String adShowWay, String adPvclkDevice, String customerInfoId, String startDate, String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select ");
		
		//廣泛比對
		hql.append(" sum(r.ad_keyword_pv), ");
		hql.append(" sum(r.ad_keyword_clk), ");			// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_keyword_clk_price), ");	// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_keyword_invalid_clk), ");
		
		//詞組比對
		hql.append(" sum(r.ad_keyword_phrase_pv), ");
		hql.append(" sum(r.ad_keyword_phrase_clk), ");			// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_keyword_phrase_clk_price), ");	// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_keyword_phrase_invalid_clk), ");
		
		//精準比對
		hql.append(" sum(r.ad_keyword_precision_pv), ");
		hql.append(" sum(r.ad_keyword_precision_clk), ");			// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_keyword_precision_clk_price), ");	// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_keyword_precision_invalid_clk) ");
		
		//hql.append(" sum(r.ad_keyword_invalid_clk_price) ");
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
		//廣泛比對
		hql.append(" sum(r.ad_keyword_pv), ");
		hql.append(" sum(r.ad_keyword_clk), ");			// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_keyword_clk_price), ");	// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_keyword_invalid_clk), ");
		
		//詞組比對
		hql.append(" sum(r.ad_keyword_phrase_pv), ");
		hql.append(" sum(r.ad_keyword_phrase_clk), ");			// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_keyword_phrase_clk_price), ");	// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_keyword_phrase_invalid_clk), ");
		
		//精準比對
		hql.append(" sum(r.ad_keyword_precision_pv), ");
		hql.append(" sum(r.ad_keyword_precision_clk), ");			// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_keyword_precision_clk_price), ");	// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_keyword_precision_invalid_clk), ");
		
		hql.append(" r.ad_keyword_pvclk_date, ");
		hql.append(" r.ad_action_seq, ");
		hql.append(" r.ad_group_seq, ");
		hql.append(" r.ad_keyword_seq, ");
		hql.append(" r.ad_keyword, ");
		hql.append(" r.customer_info_id, ");
		hql.append(" r.ad_keyword_pvclk_device, ");
		hql.append(" r.ad_keyword_type ");
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

		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" , r.ad_keyword_type");
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
		
		//廣泛比對
		hql.append(" sum(r.ad_keyword_pv), ");
		hql.append(" sum(r.ad_keyword_clk), ");			// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_keyword_clk_price), ");	// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_keyword_invalid_clk), ");
		
		//詞組比對
		hql.append(" sum(r.ad_keyword_phrase_pv), ");
		hql.append(" sum(r.ad_keyword_phrase_clk), ");			// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_keyword_phrase_clk_price), ");	// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_keyword_phrase_invalid_clk), ");
		
		//精準比對
		hql.append(" sum(r.ad_keyword_precision_pv), ");
		hql.append(" sum(r.ad_keyword_precision_clk), ");			// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_keyword_precision_clk_price), ");	// 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_keyword_precision_invalid_clk) ");
		
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

	private HashMap<String, Object> getAdRankHQLStr(String customerInfoId, String adKeywordSeq, String adType, String startDate, String endDate, String keywordStyle) throws ParseException {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" select COALESCE(sum(pakar.adRankAvg)/count(pakar.adRankAvg),0) ");
		hql.append(" from PfpAdRank pakar ");
		hql.append(" where pakar.adRankDate >= :startDate ");
		hql.append(" and pakar.adRankDate <= :endDate ");
		hql.append(" and IFNULL(pakar.adKeywordSearchStyle,'1') = :keywordStyle ");
		sqlParams.put("startDate", sdf.parse(startDate));
		sqlParams.put("endDate", sdf.parse(endDate));
		sqlParams.put("keywordStyle", keywordStyle);
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

	/**
	 * 關鍵字成效(明細)
	 */
	@Override
	public List<Map<String, Object>> getAdKeywordList(AdKeywordReportVO vo) {
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT ");
		// 廣泛比對
		hql.append(" SUM(r.ad_keyword_pv) AS ad_keyword_pv_sum, ");
		hql.append(" SUM(r.ad_keyword_clk) AS ad_keyword_clk_sum, ");             // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" SUM(r.ad_keyword_clk_price) AS ad_keyword_clk_price_sum, "); // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		
		// 詞組比對
		hql.append(" SUM(r.ad_keyword_phrase_pv) AS ad_keyword_phrase_pv_sum, ");
		hql.append(" SUM(r.ad_keyword_phrase_clk) AS ad_keyword_phrase_clk_sum, ");             // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" SUM(r.ad_keyword_phrase_clk_price) AS ad_keyword_phrase_clk_price_sum, "); // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		
		// 精準比對
		hql.append(" SUM(r.ad_keyword_precision_pv) AS ad_keyword_precision_pv_sum, ");
		hql.append(" SUM(r.ad_keyword_precision_clk) AS ad_keyword_precision_clk_sum, ");             // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" SUM(r.ad_keyword_precision_clk_price) AS ad_keyword_precision_clk_price_sum, "); // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		
		hql.append(" r.ad_keyword_pvclk_date, ");
		hql.append(" r.ad_action_seq, ");
		hql.append(" r.ad_group_seq, ");
		hql.append(" r.ad_keyword_seq, ");
		hql.append(" r.ad_keyword, ");
		hql.append(" r.customer_info_id, ");
		hql.append(" r.ad_keyword_pvclk_device, ");
		hql.append(" r.ad_keyword_type ");
		hql.append(" FROM pfp_ad_keyword_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id =:customerInfoId ");
		hql.append(" AND r.ad_keyword_pvclk_date >=:startDate ");
		hql.append(" AND r.ad_keyword_pvclk_date <=:endDate ");
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND r.ad_keyword LIKE :searchStr ");
		}
		
		String adDevice = ""; // 裝置
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			
			adDevice = tempJSONObject.optString("adDevice");
			if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice)) {
				hql.append(" AND r.ad_keyword_pvclk_device = :adPvclkDevice");
			}
		}
		
		hql.append(" GROUP BY r.ad_keyword_seq");
		hql.append(" ORDER BY r.ad_keyword_seq DESC");
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
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
	 * 取得平均廣告排名
	 */
	@Override
	public List<Map<String, Object>> getAdKeywordRank(AdKeywordReportVO vo) {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT ");
		hql.append("   COALESCE(SUM(pakar.adRankAvg)/COUNT(pakar.adRankAvg),0) AS ad_rank_avg ");
		hql.append(" FROM PfpAdRank pakar ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND pakar.customerInfoId = :customerInfoId ");
		hql.append(" AND pakar.adRankDate >= :startDate ");
		hql.append(" AND pakar.adRankDate <= :endDate ");
		hql.append(" AND pakar.pfpAdKeyword.adKeywordSeq = :adKeywordSeq ");
		hql.append(" AND IFNULL(pakar.adKeywordSearchStyle,'1') = :keywordStyle ");
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		query.setString("adKeywordSeq", vo.getAdKeywordSeq());
		query.setString("keywordStyle", vo.getKeywordStyle());
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 關鍵字成效(加總)
	 */
	@Override
	public List<Map<String, Object>> getAdKeywordListSum(AdKeywordReportVO vo) {
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT ");
		// 廣泛比對
		hql.append(" SUM(r.ad_keyword_pv) AS ad_keyword_pv_sum, ");
		hql.append(" SUM(r.ad_keyword_clk) AS ad_keyword_clk_sum, ");             // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" SUM(r.ad_keyword_clk_price) AS ad_keyword_clk_price_sum, "); // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		
		// 詞組比對
		hql.append(" SUM(r.ad_keyword_phrase_pv) AS ad_keyword_phrase_pv_sum, ");
		hql.append(" SUM(r.ad_keyword_phrase_clk) AS ad_keyword_phrase_clk_sum, ");             // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" SUM(r.ad_keyword_phrase_clk_price) AS ad_keyword_phrase_clk_price_sum, "); // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		
		// 精準比對
		hql.append(" sum(r.ad_keyword_precision_pv) AS ad_keyword_precision_pv_sum, ");
		hql.append(" sum(r.ad_keyword_precision_clk) AS ad_keyword_precision_clk_sum, ");             // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_keyword_precision_clk_price) AS ad_keyword_precision_clk_price_sum "); // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減
		
		hql.append(" FROM pfp_ad_keyword_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id = :customerInfoId ");
		hql.append(" AND r.ad_keyword_pvclk_date >= :startDate ");
		hql.append(" AND r.ad_keyword_pvclk_date <= :endDate ");
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND r.ad_keyword LIKE :searchStr ");
		}
		
		String adDevice = ""; // 裝置
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			
			adDevice = tempJSONObject.optString("adDevice");
			if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice)) {
				hql.append(" AND r.ad_keyword_pvclk_device = :adPvclkDevice");
			}
		}
		
		hql.append(" GROUP BY r.ad_keyword_seq");
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice)) {
			query.setString("adPvclkDevice", adDevice);
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 關鍵字成效(圖表)
	 */
	@Override
	public List<Map<String, Object>> getAdKeywordListChart(AdKeywordReportVO vo) {
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT ");
		hql.append(" r.ad_keyword_pvclk_date, ");

		// 廣泛比對
		hql.append(" SUM(r.ad_keyword_pv) AS ad_keyword_pv_sum, ");
		hql.append(" SUM(r.ad_keyword_clk) AS ad_keyword_clk_sum, "); // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" SUM(r.ad_keyword_clk_price) AS ad_keyword_clk_price_sum, "); // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減

		// 詞組比對
		hql.append(" SUM(r.ad_keyword_phrase_pv) AS ad_keyword_phrase_pv_sum, ");
		hql.append(" SUM(r.ad_keyword_phrase_clk) AS ad_keyword_phrase_clk_sum, "); // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" SUM(r.ad_keyword_phrase_clk_price) AS ad_keyword_phrase_clk_price_sum, "); // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減

		// 精準比對
		hql.append(" SUM(r.ad_keyword_precision_pv) AS ad_keyword_precision_pv_sum, ");
		hql.append(" SUM(r.ad_keyword_precision_clk) AS ad_keyword_precision_clk_sum, "); // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" SUM(r.ad_keyword_precision_clk_price) AS ad_keyword_precision_clk_price_sum "); // 產生pfp_ad_keyword_report 的時候，已經減過無效點擊金額了，所以不用再減

		hql.append(" FROM pfp_ad_keyword_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id =:customerInfoId ");
		hql.append(" AND r.ad_keyword_pvclk_date >=:startDate ");
		hql.append(" AND r.ad_keyword_pvclk_date <=:endDate ");
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND r.ad_keyword LIKE :searchStr ");
		}
		
		String adDevice = ""; // 裝置
		if (StringUtils.isNotBlank(vo.getWhereMap())) {
			JSONObject tempJSONObject = new JSONObject(vo.getWhereMap());
			
			adDevice = tempJSONObject.optString("adDevice");
			if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice)) {
				hql.append(" AND r.ad_keyword_pvclk_device = :adPvclkDevice");
			}
		}
		
		hql.append(" GROUP BY r.ad_keyword_pvclk_date");
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice)) {
			query.setString("adPvclkDevice", adDevice);
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
}