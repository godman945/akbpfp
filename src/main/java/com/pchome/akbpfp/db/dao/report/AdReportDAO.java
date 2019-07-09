package com.pchome.akbpfp.db.dao.report;

import java.math.BigDecimal;
import java.math.BigInteger;
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
					public List<AdReportVO> doInHibernate(Session session) throws HibernateException {

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
								BigDecimal convertCount = (BigDecimal) objArray[18];
								BigDecimal convertPriceCount = (BigDecimal) objArray[19];
								
								adReportVO = new AdReportVO();
								adReportVO.setConvertCount(convertCount);
								adReportVO.setConvertPriceCount(convertPriceCount);
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
								BigDecimal convertCount = (BigDecimal) objArray[5];
								BigDecimal convertPriceCount = (BigDecimal) objArray[6];
								
								AdReportVO adReportVO = new AdReportVO();
								adReportVO.setConvertCount(convertCount);
								adReportVO.setConvertPriceCount(convertPriceCount);
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
								adReportVO.setConvertCount((BigDecimal) objArray[7]);
								adReportVO.setConvertPriceCount((BigDecimal) objArray[8]);
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
		/* 產生pfp_ad_group_report 的時候，已經減過無效點擊數了，所以不用再減
		   20180510 檢查排程未減無效點擊數，因pfp很多程式都有引用報表數據，因此不能重跑排程，所以直接調整SQL部分 */
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then (r.ad_clk - r.ad_invalid_clk) else r.ad_view end)), ");
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" SUM(r.convert_count),  ");
		hql.append(" SUM(r.convert_price_count) ");
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
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then (r.ad_clk - r.ad_invalid_clk) else r.ad_view end)), ");
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
		hql.append(" ( select g.ad_group_status from pfp_ad_group g where g.ad_group_seq  = r.ad_group_seq)ad_group_status, ");
		hql.append(" SUM(r.convert_count),  ");
		hql.append(" SUM(r.convert_price_count) ");
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
		/* 產生pfp_ad_group_report 的時候，已經減過無效點擊數了，所以不用再減
		   20180510 檢查排程未減無效點擊數，因pfp很多程式都有引用報表數據，因此不能重跑排程，所以直接調整SQL部分 */
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then (r.ad_clk - r.ad_invalid_clk) else r.ad_view end)), ");
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" count(r.ad_report_seq), ");
		hql.append(" SUM(r.convert_count),  ");
		hql.append(" SUM(r.convert_price_count) ");
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

	/**
	 * 總廣告成效-明細、廣告明細成效共用(明細)
	 */
	@Override
	public List<Map<String, Object>> getAdvertiseList(AdvertiseReportVO vo) {
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
		hql.append("SELECT ");
		hql.append(" SUM(r.ad_pv) AS ad_pv_sum, ");
		hql.append(" SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN (r.ad_clk - r.ad_invalid_clk) ELSE r.ad_view END)) AS ad_clk_sum, ");
		hql.append(" SUM(r.ad_clk_price) AS ad_price_sum, ");
		hql.append(" SUM(r.ad_invalid_clk) AS ad_invalid_clk_sum, ");
		hql.append(" r.ad_pvclk_date, ");
		hql.append(" r.ad_action_seq, ");
		hql.append(" r.ad_group_seq, ");
		hql.append(" r.ad_seq, ");
		hql.append(" r.template_product_seq, ");
		hql.append(" r.customer_info_id, ");
		hql.append(" r.ad_pvclk_device AS ad_device, ");
		hql.append(" r.ad_operating_rule, ");
		hql.append(" r.ad_clk_price_type, ");
		hql.append(" r.ad_type, ");
//		hql.append(" ( SELECT g.ad_group_status FROM pfp_ad_group g WHERE g.ad_group_seq  = r.ad_group_seq) AS ad_group_status, ");
//		hql.append(" ( SELECT g.ad_group_name FROM pfp_ad_group g WHERE g.ad_group_seq  = r.ad_group_seq) AS ad_group_name, ");
//		hql.append(" ( SELECT a.ad_status FROM pfp_ad a WHERE a.ad_seq = r.ad_seq) AS ad_status, ");
//		hql.append(" ( SELECT aa.ad_action_name FROM pfp_ad_group g,pfp_ad_action aa WHERE g.ad_group_seq  = r.ad_group_seq and g.ad_action_seq = aa.ad_action_seq) AS ad_action_name,");
		hql.append(" SUM(r.convert_count) AS convert_count, ");
		hql.append(" SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_report AS r ");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id = :customerInfoId");
		hql.append(" AND r.ad_pvclk_date >= :startDate");
		hql.append(" AND r.ad_pvclk_date <= :endDate");

		if (StringUtils.isNotBlank(vo.getAdGroupSeq())) { // 總廣告成效使用到
			hql.append(" AND r.ad_group_seq = :adGroupSeq ");
		}
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND r.ad_seq IN (SELECT DISTINCT ad_seq FROM pfp_ad_detail WHERE 1=1 AND (ad_detail_id = 'title' OR  ad_detail_id = 'content' OR ad_detail_id = 'prod_report_name') ");
			hql.append(" AND customer_info_id = :customerInfoId");
			hql.append(" AND ad_detail_content LIKE :searchStr)");
		}
		
		if (StringUtils.isNotBlank(adType) && !"all".equalsIgnoreCase(adType)) {
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

		
		hql.append(" GROUP BY r.ad_seq, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		// 裝置空值或選擇全部時則將資料區分PC和mobile
		if (StringUtils.isBlank(adDevice) || "all".equalsIgnoreCase(adDevice)) {
			hql.append(" ,r.ad_pvclk_device");
		}
		hql.append(" ORDER BY r.ad_seq DESC, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type ");
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getAdGroupSeq())) { // 總廣告成效使用到
			query.setString("adGroupSeq", vo.getAdGroupSeq());
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
	 * 總廣告成效-明細、廣告明細成效共用(加總)
	 */
	@Override
	public List<Map<String, Object>> getAdvertiseListSum(AdvertiseReportVO vo) {
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
		hql.append("SELECT ");
		hql.append(" SUM(r.ad_pv) AS ad_pv_sum, ");
		// 20180510 檢查排程未減無效點擊數，因pfp很多程式都有引用報表數據，因此不能重跑排程，所以直接調整SQL部分
		hql.append(" SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN (r.ad_clk - r.ad_invalid_clk) ELSE r.ad_view END)) AS ad_clk_sum, ");
		hql.append(" SUM(r.ad_clk_price) AS ad_price_sum, "); // 產生pfp_ad_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" SUM(r.ad_invalid_clk) AS ad_invalid_clk_sum, ");
		hql.append(" SUM(r.convert_count) AS convert_count, ");
		hql.append(" SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_report AS r ");
		hql.append("	LEFT OUTER JOIN (SELECT DISTINCT ad_seq FROM pfp_ad_detail) AS pad ON (r.ad_seq = pad.ad_seq)");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id = :customerInfoId");
		hql.append(" AND r.ad_pvclk_date >= :startDate");
		hql.append(" AND r.ad_pvclk_date <= :endDate");
		
		if (StringUtils.isNotBlank(vo.getAdGroupSeq())) { // 總廣告成效使用到
			hql.append(" AND r.ad_group_seq = :adGroupSeq ");
		}
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND r.ad_seq IN (SELECT DISTINCT ad_seq FROM pfp_ad_detail WHERE 1=1 AND (ad_detail_id = 'title' OR  ad_detail_id = 'content' OR ad_detail_id = 'prod_report_name') ");
			hql.append(" AND customer_info_id = :customerInfoId");
			hql.append(" AND ad_detail_content LIKE :searchStr)");
			
//			hql.append(" and r.ad_seq in (select DISTINCT ad_seq from pfp_ad_detail where 1=1");
//			hql.append(" and customer_info_id = :customerInfoId");
//			hql.append(" and ad_detail_content like :searchStr)");
		}
		
		if (StringUtils.isNotBlank(adType) && !"all".equalsIgnoreCase(adType)) {
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

		hql.append(" GROUP BY r.ad_seq, r.ad_type, r.ad_operating_rule, r.ad_clk_price_type");
		// 裝置空值或選擇全部時則將資料區分PC和mobile
		if (StringUtils.isBlank(adDevice) || "all".equalsIgnoreCase(adDevice)) {
			hql.append(" ,r.ad_pvclk_device");
		}
//		hql.append(" GROUP BY r.ad_seq");
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getAdGroupSeq())) { // 總廣告成效使用到
			query.setString("adGroupSeq", vo.getAdGroupSeq());
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
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			query.setString("adPvclkDevice", adDevice);
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 總廣告成效-明細、廣告明細成效共用(圖表)
	 */
	@Override
	public List<Map<String, Object>> getAdvertiseListChart(AdvertiseReportVO vo) {
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
		hql.append(" r.ad_pvclk_date, ");
		hql.append(" SUM(r.ad_pv) AS ad_pv_sum, ");
		// 20180510 檢查排程未減無效點擊數，因pfp很多程式都有引用報表數據，因此不能重跑排程，所以直接調整SQL部分
		hql.append(" SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN (r.ad_clk - r.ad_invalid_clk) ELSE r.ad_view END)) AS ad_clk_sum, ");
		hql.append(" SUM(r.ad_clk_price) AS ad_price_sum, "); // 產生pfp_ad_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" SUM(r.ad_invalid_clk) AS ad_invalid_clk_sum, ");
		hql.append(" SUM(r.convert_count) AS convert_count, ");
		hql.append(" SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_report AS r ");
		hql.append("	LEFT OUTER JOIN (SELECT DISTINCT ad_seq FROM pfp_ad_detail) AS pad ON (r.ad_seq = pad.ad_seq)");
		hql.append(" WHERE 1 = 1 ");
		hql.append(" AND r.customer_info_id = :customerInfoId");
		hql.append(" AND r.ad_pvclk_date >= :startDate");
		hql.append(" AND r.ad_pvclk_date <= :endDate");
		
		if (StringUtils.isNotBlank(vo.getAdGroupSeq())) { // 總廣告成效使用到
			hql.append(" AND r.ad_group_seq = :adGroupSeq ");
		}
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND r.ad_seq IN (SELECT DISTINCT ad_seq FROM pfp_ad_detail WHERE 1=1 AND (ad_detail_id = 'title' OR  ad_detail_id = 'content' OR ad_detail_id = 'prod_report_name') ");
			hql.append(" AND customer_info_id = :customerInfoId");
			hql.append(" AND ad_detail_content LIKE :searchStr)");
			
//			hql.append(" and r.ad_seq in (select ad_seq from pfp_ad_detail where 1=1");
//			hql.append(" and ad_detail_content like :searchStr)");
		}
		
		if (StringUtils.isNotBlank(adType) && !"all".equalsIgnoreCase(adType)) {
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
		
		hql.append(" GROUP BY r.ad_pvclk_date");
		
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getAdGroupSeq())) { // 總廣告成效使用到
			query.setString("adGroupSeq", vo.getAdGroupSeq());
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
		
		if (StringUtils.isNotBlank(adDevice) && !"all".equalsIgnoreCase(adDevice) && !"PCandMobile".equalsIgnoreCase(adDevice)) {
			query.setString("adPvclkDevice", adDevice);
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
}