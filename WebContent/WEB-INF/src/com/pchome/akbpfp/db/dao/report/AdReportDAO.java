package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;
import java.math.BigInteger;
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

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdReport;
import com.pchome.enumerate.ad.EnumAdPriceType;
import com.pchome.enumerate.ad.EnumAdStyleType;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.enumerate.utils.EnumStatus;
import com.pchome.soft.depot.utils.ObjectTransUtil;

@Transactional
public class AdReportDAO extends BaseDAO<PfpAdReport, Integer> implements IAdReportDAO {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public List<AdReportVO> getReportList(final String sqlType, final String adGroupSeq, final String adSeq, final String searchText, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate, final int page, final int pageSize){

		List<AdReportVO> result = (List<AdReportVO> ) getHibernateTemplate().execute(
				new HibernateCallback<List<AdReportVO> >() {
					public List<AdReportVO> doInHibernate(Session session) throws HibernateException, SQLException {

						String hqlStr = "";
                        HashMap<String, Object> sqlParams = new HashMap<String, Object>();

						log.info(">>> sqlType = " + sqlType);
						log.info(">>> adPvclkDevice = " + adPvclkDevice);

						if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_ADVERTISE_COUNT.getTextValue())){

							//總廣告成效 -> 報表類型:廣告明細 (數量及加總)
							//廣告明細成效 (數量及加總)
							//hqlStr = getAdReportCountSQL(adGroupSeq, adSearchWay, adShowWay, adPvclkDevice, searchText, customerInfoId, startDate, endDate);
							try {
								sqlParams = getAdReportCountSQL(adGroupSeq, adSeq, adSearchWay, adShowWay, adPvclkDevice, searchText, customerInfoId, adOperatingRule, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_ADVERTISE.getTextValue())){

							//總廣告成效 -> 報表類型:廣告明細 (資料)
							//廣告明細成效 (資料)
							//hqlStr = getAdReportDataSQL(adGroupSeq, adSearchWay, adShowWay, adPvclkDevice, searchText, customerInfoId, startDate, endDate);
							try {
								sqlParams = getAdReportDataSQL(adGroupSeq, adSeq, adSearchWay, adShowWay, adPvclkDevice, searchText, customerInfoId, adOperatingRule, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_ADVERTISE_CHART.getTextValue())){

							//總廣告成效 -> 報表類型:廣告明細 (圖表)
							//廣告明細成效 (圖表)
							//hqlStr = getAdReportChartSQL(adGroupSeq, adSearchWay, adShowWay, adPvclkDevice, searchText, customerInfoId, startDate, endDate);
							try {
								sqlParams = getAdReportChartSQL(adGroupSeq, adSeq, adSearchWay, adShowWay, adPvclkDevice, searchText, customerInfoId, adOperatingRule, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

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

						List<AdReportVO> resultData = new ArrayList<AdReportVO>();

						if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_ADVERTISE.getTextValue())){

							Map<Integer,String> adTypeMap = new HashMap<Integer,String>();
							adTypeMap = getAdType();
							
							Object[] objArray = null;
							AdReportVO adReportVO = null;

							BigDecimal adPvSum = null;
							BigDecimal adClkSum = null;
							Double adPriceSum = null;
							BigDecimal adInvClkSum = null;
							String adPvclkDate = null;
							String adActionSeq = null;
							String adGroupSeq = null;
							String adSeq = null;
							String templateProductSeq = null;
							String customerInfoId = null;
							String adDevice = null;
							String adOperatingRuleCode = null;
							String adClkPriceType = null;
							Integer adType = 0;
							String adGroupName = "";
							String adActionName = "";
							Integer adStatus = null;
							Integer adGroupStatus = null;
							for (int i=0; i<dataList.size(); i++) {
								objArray = (Object[]) dataList.get(i);
								adPvSum = (BigDecimal)objArray[0];
								adClkSum = (BigDecimal)objArray[1];
								adPriceSum = (Double)objArray[2];
								adInvClkSum = (BigDecimal)objArray[3];
								adPvclkDate = ObjectTransUtil.getInstance().getObjectToString(objArray[4]);
								adActionSeq = ObjectTransUtil.getInstance().getObjectToString(objArray[5]);
								adGroupSeq = ObjectTransUtil.getInstance().getObjectToString(objArray[6]);
								adSeq = ObjectTransUtil.getInstance().getObjectToString(objArray[7]);
								templateProductSeq = ObjectTransUtil.getInstance().getObjectToString(objArray[8]);
								customerInfoId = ObjectTransUtil.getInstance().getObjectToString(objArray[9]);
								adDevice = ObjectTransUtil.getInstance().getObjectToString(objArray[10]);
								adOperatingRuleCode = ObjectTransUtil.getInstance().getObjectToString(objArray[11]);
								adClkPriceType = ObjectTransUtil.getInstance().getObjectToString(objArray[12]);
								adType = Integer.parseInt(objArray[13].toString());
								adGroupName = ObjectTransUtil.getInstance().getObjectToString(objArray[14]);
								adStatus = ((BigInteger)objArray[15]).intValue();
								adActionName = ObjectTransUtil.getInstance().getObjectToString(objArray[16]);
								adGroupStatus = ((BigInteger)objArray[17]).intValue();
								
								adReportVO = new AdReportVO();
								adReportVO.setAdPvSum(adPvSum.toString());
								adReportVO.setAdClkSum(adClkSum.toString());
								adReportVO.setAdPriceSum(adPriceSum.toString());
								adReportVO.setAdInvClkSum(adInvClkSum.toString());
								adReportVO.setAdPvclkDate(adPvclkDate);
								adReportVO.setAdActionSeq(adActionSeq);
								adReportVO.setAdGroupSeq(adGroupSeq);
								adReportVO.setAdSeq(adSeq);
								adReportVO.setTemplateProductSeq(templateProductSeq);
								adReportVO.setCustomerInfoId(customerInfoId);
								adReportVO.setAdGroupName(adGroupName);
								adReportVO.setReportDate(adPvclkDate);
								adReportVO.setAdStatus(String.valueOf(adStatus));
								adReportVO.setAdActionName(adActionName);
								adReportVO.setAdGroupStatus(adGroupStatus);
								
								if(StringUtils.isNotBlank(adPvclkDevice)) {
									if("PC".equals(adDevice)){
										adReportVO.setAdDevice("電腦");
									}else if("mobile".equals(adDevice)){
										adReportVO.setAdDevice("行動裝置");
									} else {
										adReportVO.setAdDevice(adDevice);	
									}
								} else {
									adReportVO.setAdDevice("全部");
								}
								adReportVO.setAdOperatingRule(adOperatingRuleCode);
								for (EnumAdStyleType enumAdStyleType : EnumAdStyleType.values()) {
									if(enumAdStyleType.getTypeName().equals(adOperatingRuleCode)){
										adReportVO.setAdOperatingRuleDesc(enumAdStyleType.getType());
										break;
									}
								}
								for (EnumAdPriceType enumAdPriceType : EnumAdPriceType.values()) {
									if(enumAdPriceType.getDbTypeName().equals(adClkPriceType)){
										adReportVO.setAdClkPriceType(enumAdPriceType.getTypeName());
										break;
									}
								}
								
								for (EnumStatus enumStatus : EnumStatus.values()) {
									if(adStatus == enumStatus.getStatusId()){
										adReportVO.setAdStatusDesc(enumStatus.getStatusDesc());
										break;
									}
								}
								for (EnumAdStyleType enumAdStyleType : EnumAdStyleType.values()) {
									if(adOperatingRuleCode.equals(enumAdStyleType.getType())){
										adReportVO.setAdOperatingRuleDesc(enumAdStyleType.getTypeName());
										break;
									}
								}
								
								adReportVO.setAdType(adTypeMap.get(adType));
								resultData.add(adReportVO);
							}
							
						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_ADVERTISE_COUNT.getTextValue())){
							for (int i=0; i<dataList.size(); i++) {
								Object[] objArray = (Object[]) dataList.get(i);
								BigDecimal adPvSum = (BigDecimal)objArray[0];
								BigDecimal adClkSum = (BigDecimal)objArray[1];
								Double adPriceSum = (Double)objArray[2];
								BigDecimal adInvClkSum = (BigDecimal)objArray[3];

								AdReportVO adReportVO = new AdReportVO();

								adReportVO.setAdPvSum(adPvSum.toString());
								adReportVO.setAdClkSum(adClkSum.toString());
								adReportVO.setAdPriceSum(adPriceSum.toString());
								adReportVO.setAdInvClkSum(adInvClkSum.toString());
								resultData.add(adReportVO);
							}

						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_ADVERTISE_CHART.getTextValue())){
							for (int i=0; i<dataList.size(); i++) {
								Object[] objArray = (Object[]) dataList.get(i);
								AdReportVO adReportVO = new AdReportVO();
								adReportVO.setReportDate(ObjectTransUtil.getInstance().getObjectToString(objArray[0]));
								adReportVO.setAdPvSum(((BigDecimal)objArray[1]).toString());
								adReportVO.setAdClkSum(((BigDecimal)objArray[2]).toString());
								adReportVO.setAdPriceSum(((Double)objArray[3]).toString());
								adReportVO.setAdInvClkSum(((BigDecimal)objArray[4]).toString());
								resultData.add(adReportVO);
							}
						}
						return resultData;

					}
				}
		);

		return result;
	}

	private HashMap<String, Object> getAdReportCountSQL(final String adGroupSeq, final String adSeq, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String searchText,
			final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate) throws ParseException {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select ");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");				// 產生pfp_ad_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price) ");
		hql.append(" from pfp_ad_report as r ");
		hql.append("	left outer join (select distinct ad_seq from pfp_ad_detail) as pad on (r.ad_seq = pad.ad_seq)");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id = :customerInfoId");
		hql.append(" and r.ad_pvclk_date >= :startDate");
		hql.append(" and r.ad_pvclk_date <= :endDate");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", sdf.parse(startDate));
		sqlParams.put("endDate", sdf.parse(endDate));

		if (StringUtils.isNotBlank(adSeq)) {
			hql.append(" and r.ad_seq = :adSeq");
			sqlParams.put("adSeq", adSeq);
		}

		if (StringUtils.isNotBlank(adGroupSeq)) {
			hql.append(" and r.ad_group_seq = :adGroupSeq");
			sqlParams.put("adGroupSeq", adGroupSeq);
		}
		
		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" and r.ad_type = :adShowWay");
			sqlParams.put("adShowWay", Integer.parseInt(adShowWay));
		}

		if (StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" and r.ad_pvclk_device = :adPvclkDevice");
			sqlParams.put("adPvclkDevice", adPvclkDevice);
		}

		if (StringUtils.isNotEmpty(searchText)) {
			String searchStr = getSearchText(searchText, adSearchWay);
			hql.append(" and r.ad_seq in (select ad_seq from pfp_ad_detail where 1=1");
			hql.append(" and customer_info_id = :customerInfoId");
			hql.append(" and ad_detail_content like :searchStr)");
			sqlParams.put("searchStr", searchStr);
		}

		if (StringUtils.isNotBlank(adOperatingRule)) {
			hql.append(" and r.ad_operating_rule = :adOperatingRule ");
			sqlParams.put("adOperatingRule", adOperatingRule);
		}
		
		if(StringUtils.isNotBlank(adSeq)) {
			hql.append(" group by r.ad_seq, r.ad_pvclk_date");
		} else {
			hql.append(" group by r.ad_seq");
		}
		/*if(StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" , r.ad_pvclk_device");
		}*/
		hql.append(" order by r.ad_seq desc");
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private HashMap<String, Object> getAdReportDataSQL(final String adGroupSeq, final String adSeq, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String searchText,
			final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate) throws ParseException {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select ");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");
		hql.append(" sum(r.ad_clk_price), ");		
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" r.ad_pvclk_date, ");
		hql.append(" r.ad_action_seq, ");
		hql.append(" r.ad_group_seq, ");
		hql.append(" r.ad_seq, ");
		hql.append(" r.template_product_seq, ");
		hql.append(" r.customer_info_id, ");
		hql.append(" r.ad_pvclk_device, ");
		hql.append(" r.ad_operating_rule, ");
		hql.append(" r.ad_clk_price_type, ");
		hql.append(" r.ad_type, ");
		hql.append(" ( select g.ad_group_name from pfp_ad_group g where g.ad_group_seq  = r.ad_group_seq)ad_group_name, ");
		hql.append(" ( select a.ad_status from pfp_ad a where a.ad_seq=r.ad_seq)ad_status, ");
		hql.append(" ( select aa.ad_action_name from pfp_ad_group g,pfp_ad_action aa where g.ad_group_seq  = r.ad_group_seq and g.ad_action_seq = aa.ad_action_seq)ad_action_name ,");
		hql.append(" ( select g.ad_group_status from pfp_ad_group g where g.ad_group_seq  = r.ad_group_seq)ad_group_status ");
		hql.append(" from pfp_ad_report as r ");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id = :customerInfoId");
		hql.append(" and r.ad_pvclk_date >= :startDate");
		hql.append(" and r.ad_pvclk_date <= :endDate");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", sdf.parse(startDate));
		sqlParams.put("endDate", sdf.parse(endDate));

		if (StringUtils.isNotBlank(adGroupSeq)) {
			hql.append(" and r.ad_group_seq = :adGroupSeq");
			sqlParams.put("adGroupSeq", adGroupSeq);
		}
		
		if (StringUtils.isNotBlank(adSeq)) {
			hql.append(" and r.ad_seq = :adSeq");
			sqlParams.put("adSeq", adSeq);
		}

		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" and r.ad_type = :adShowWay");
			sqlParams.put("adShowWay", Integer.parseInt(adShowWay));
		}

		if (StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" and r.ad_pvclk_device = :adPvclkDevice");
			sqlParams.put("adPvclkDevice", adPvclkDevice);
		}
//原有寫法會導致多於搜尋根無關的廣告被搜尋到
//		if (StringUtils.isNotEmpty(searchText)) {
//			String searchStr = getSearchText(searchText, adSearchWay);
//			hql.append(" and r.ad_seq in (select distinct ad_seq from pfp_ad_detail where 1=1");
//			hql.append(" and customer_info_id = :customerInfoId");
//			hql.append(" and ad_detail_content like :searchStr)");
//			sqlParams.put("searchStr", searchStr);
//		}
		
		if (StringUtils.isNotEmpty(searchText)) {
			String searchStr = getSearchText(searchText, adSearchWay);
			hql.append(" and r.ad_seq in (select distinct ad_seq from pfp_ad_detail where 1=1 and (ad_detail_id = 'title' or  ad_detail_id = 'content') ");
			hql.append(" and customer_info_id = :customerInfoId");
			hql.append(" and ad_detail_content like :searchStr)");
			sqlParams.put("searchStr", searchStr);
		}
		
		

		if (StringUtils.isNotBlank(adOperatingRule)) {
			hql.append(" and r.ad_operating_rule = :adOperatingRule ");
			sqlParams.put("adOperatingRule", adOperatingRule);
		}
		
		if(StringUtils.isNotBlank(adSeq)) {
			hql.append(" group by r.ad_pvclk_date, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		} else {
			hql.append(" group by r.ad_seq, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		}
		
		/*if(StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" , r.ad_pvclk_device");
		}
		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" , r.ad_type");
		}*/
		if(StringUtils.isNotBlank(adSeq)) {
			hql.append(" order by r.ad_pvclk_date, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type ");
		} else {
			hql.append(" order by r.ad_seq desc, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type ");
		}
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private HashMap<String, Object> getAdReportChartSQL(final String adGroupSeq, final String adSeq, final String adSearchWay, final String adShowWay, final String adPvclkDevice, final String searchText,
			final String customerInfoId, final String adOperatingRule, final String startDate, final String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select");
		hql.append(" r.ad_pvclk_date,");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");				// 產生pfp_ad_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" count(r.ad_report_seq) ");
		hql.append(" from pfp_ad_report as r ");
		hql.append("	left outer join (select distinct ad_seq from pfp_ad_detail) as pad on (r.ad_seq = pad.ad_seq)");
		hql.append(" where 1 = 1 ");
		hql.append(" and r.customer_info_id = :customerInfoId");
		hql.append(" and r.ad_pvclk_date >= :startDate");
		hql.append(" and r.ad_pvclk_date <= :endDate");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", sdf.parse(startDate));
		sqlParams.put("endDate", sdf.parse(endDate));

		if (StringUtils.isNotBlank(adGroupSeq)) {
			hql.append(" and r.ad_group_seq = :adGroupSeq");
			sqlParams.put("adGroupSeq", adGroupSeq);
		}
		
		if (StringUtils.isNotBlank(adSeq)) {
			hql.append(" and r.ad_seq = :adSeq");
			sqlParams.put("adSeq", adSeq);
		}

		if (StringUtils.isNotEmpty(adShowWay) && (Integer.parseInt(adShowWay) != EnumAdType.AD_ALL.getType())) {
			hql.append(" and r.ad_type = :adShowWay");
			sqlParams.put("adShowWay", Integer.parseInt(adShowWay));
		}

		if (StringUtils.isNotBlank(adPvclkDevice)) {
			hql.append(" and r.ad_pvclk_device = :adPvclkDevice");
			sqlParams.put("adPvclkDevice", adPvclkDevice);
		}

		if (StringUtils.isNotEmpty(searchText)) {
			String searchStr = getSearchText(searchText, adSearchWay);
			hql.append(" and r.ad_seq in (select ad_seq from pfp_ad_detail where 1=1");
			hql.append(" and ad_detail_content like :searchStr)");
			sqlParams.put("searchStr", searchStr);
		}

		if (StringUtils.isNotBlank(adOperatingRule)) {
			hql.append(" and r.ad_operating_rule = :adOperatingRule ");
			sqlParams.put("adOperatingRule", adOperatingRule);
		}
		
		hql.append(" group by r.ad_pvclk_date");
		hql.append(" order by r.ad_pvclk_date");
		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private String getSearchText(String searchText,String searchWay){
		String searchStr="";
		if(searchWay.trim().equals(EnumReport.ADSEARCH_BEGIN.getTextValue())){
			searchStr=searchText+"%";
		}
		if(searchWay.trim().equals(EnumReport.ADSEARCH_INCLUDE.getTextValue())){
			searchStr="%"+searchText+"%";
		}
		if(searchWay.trim().equals(EnumReport.ADSEARCH_SAME.getTextValue())){
			searchStr=searchText;
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
