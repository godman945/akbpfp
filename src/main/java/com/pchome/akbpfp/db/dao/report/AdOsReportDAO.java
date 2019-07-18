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
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdOsReport;
import com.pchome.enumerate.report.EnumReport;
import com.pchome.soft.depot.utils.ObjectTransUtil;

@Transactional
public class AdOsReportDAO extends BaseDAO<PfpAdOsReport, Integer> implements IAdOsReportDAO {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public List<AdOsReportVO> getAdOsReportList(final String sqlType, final String adPvclkOs, final String adSearchWay, final String searchText, final String customerInfoId, final String startDate, final String endDate, final int page, final int pageSize){

		List<AdOsReportVO> result = (List<AdOsReportVO> ) getHibernateTemplate().execute(
				new HibernateCallback<List<AdOsReportVO> >() {
					public List<AdOsReportVO>  doInHibernate(Session session) throws HibernateException {

						String hqlStr="";
                        HashMap<String, Object> sqlParams = new HashMap<String, Object>();

						log.info(">>> sqlType = " + sqlType);
						log.info(">>> adPvclkOs = " + adPvclkOs);

						//行動廣告成效(數量及加總)
						if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_ADMOBILE_COUNT.getTextValue())){
							//hqlStr = getAdOsReportCountSQL(adPvclkOs, adSearchWay, searchText, customerInfoId, startDate, endDate);
							try {
								sqlParams = getAdOsReportCountSQL(adPvclkOs, adSearchWay, searchText, customerInfoId, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						//行動廣告成效(資料)
						if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_ADMOBILE.getTextValue())){
							//hqlStr = getAdOsReportDataSQL(adPvclkOs, adSearchWay, searchText, customerInfoId, startDate, endDate);
							try {
								sqlParams = getAdOsReportDataSQL(adPvclkOs, adSearchWay, searchText, customerInfoId, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						//行動廣告成效(圖表)
						if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_ADMOBILE_CHART.getTextValue())){
							//hqlStr = getAdOsReportChartSQL(adPvclkOs, adSearchWay, searchText, customerInfoId, startDate, endDate);
							try {
								sqlParams = getAdOsReportChartSQL(adPvclkOs, adSearchWay, searchText, customerInfoId, startDate, endDate);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						hqlStr = sqlParams.get("sql").toString();
						log.info(">>> getAdOsReportList sql = " + hqlStr);

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
//
						List<AdOsReportVO> resultData = new ArrayList<AdOsReportVO>();

						if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_ADMOBILE.getTextValue())){

							Object[] objArray = null;
							AdOsReportVO adOsReportVO = null;

							for (int i=0; i<dataList.size(); i++) {

								objArray = (Object[]) dataList.get(i);

								adOsReportVO = new AdOsReportVO();
								adOsReportVO.setAdPvSum(((BigDecimal)objArray[0]));
								adOsReportVO.setAdClkSum(((BigDecimal)objArray[1]));
								adOsReportVO.setAdClkPriceSum(((Double)objArray[2]).toString());
								adOsReportVO.setAdInvalidClkSum(((BigDecimal)objArray[3]).toString());
								adOsReportVO.setAdPvclkDate(ObjectTransUtil.getInstance().getObjectToString(objArray[4]));
								adOsReportVO.setAdPvclkDevice(ObjectTransUtil.getInstance().getObjectToString(objArray[5]));
								adOsReportVO.setAdPvclkOs(ObjectTransUtil.getInstance().getObjectToString(objArray[6]));
								adOsReportVO.setCustomerInfoId(ObjectTransUtil.getInstance().getObjectToString(objArray[7]));
								adOsReportVO.setConvertCount((BigDecimal) objArray[8]);
								adOsReportVO.setConvertPriceCount((BigDecimal) objArray[9]);
								resultData.add(adOsReportVO);
							}
						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_ADMOBILE_COUNT.getTextValue())){

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								AdOsReportVO adOsReportVO = new AdOsReportVO();

								adOsReportVO.setAdPvSum(((BigDecimal)objArray[0]));
								adOsReportVO.setAdClkSum(((BigDecimal)objArray[1]));
								adOsReportVO.setAdClkPriceSum(((Double)objArray[2]).toString());
								adOsReportVO.setAdInvalidClkSum(((BigDecimal)objArray[3]).toString());
								adOsReportVO.setAdInvalidClkPriceSum(((Double)objArray[4]).toString());
								adOsReportVO.setConvertCount((BigDecimal) objArray[5]);
								adOsReportVO.setConvertPriceCount((BigDecimal) objArray[6]);
								resultData.add(adOsReportVO);
							}

						} else if (sqlType.trim().equals(EnumReport.REPORT_HQLTYPE_ADMOBILE_CHART.getTextValue())){

							for (int i=0; i<dataList.size(); i++) {

								Object[] objArray = (Object[]) dataList.get(i);

								AdOsReportVO adOsReportVO = new AdOsReportVO();

								adOsReportVO.setAdPvSum(((BigDecimal)objArray[0]));
								adOsReportVO.setAdClkSum(((BigDecimal)objArray[1]));
								adOsReportVO.setAdClkPriceSum(((Double)objArray[2]).toString());
								adOsReportVO.setAdInvalidClkSum(((BigDecimal)objArray[3]).toString());
								adOsReportVO.setAdInvalidClkPriceSum(((Double)objArray[4]).toString());
								adOsReportVO.setAdPvclkDate(ObjectTransUtil.getInstance().getObjectToString(objArray[5]));
								adOsReportVO.setConvertCount((BigDecimal) objArray[6]);
								adOsReportVO.setConvertPriceCount((BigDecimal) objArray[7]);
								resultData.add(adOsReportVO);
							}
						}

						return resultData;

					}
				}
		);

		return result;
	}

	private HashMap<String, Object> getAdOsReportCountSQL(final String adPvclkOs, final String adSearchWay, final String searchText, final String customerInfoId, final String startDate, final String endDate) throws ParseException {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select ");
		hql.append("  sum(r.ad_pv), ");
		hql.append("  sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");			// 產生pfp_ad_os_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append("  sum(r.ad_clk_price), ");		// 產生pfp_ad_os_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append("  sum(r.ad_invalid_clk), ");
		hql.append("  sum(r.ad_invalid_clk_price), ");
		hql.append("  SUM(r.convert_count),  ");
		hql.append("  SUM(r.convert_price_count) ");
		hql.append(" from pfp_ad_os_report as r");
		hql.append(" where 1 = 1 ");
		hql.append("   and r.customer_info_id = :customerInfoId");
		hql.append("   and r.ad_pvclk_date between :startDate and :endDate");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", sdf.parse(startDate));
		sqlParams.put("endDate", sdf.parse(endDate));

		if(!adPvclkOs.equals("all")){
			hql.append(" and r.ad_pvclk_os = :adPvclkOs");
			sqlParams.put("adPvclkOs", adPvclkOs);
		}

//		if (StringUtils.isNotEmpty(searchText)) {
//			String searchStr = getSearchText(searchText, adSearchWay);
//		}

		hql.append(" group by r.ad_pvclk_os");
		hql.append(" order by r.ad_pvclk_os");

		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private HashMap<String, Object> getAdOsReportDataSQL(final String adPvclkOs, final String adSearchWay, final String searchText, final String customerInfoId, final String startDate, final String endDate) throws ParseException {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select ");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");				// 產生pfp_ad_os_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_os_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" r.ad_pvclk_date, ");
		hql.append(" r.ad_pvclk_device, ");
		hql.append(" r.ad_pvclk_os, ");
		hql.append(" r.customer_info_id, ");
		hql.append(" SUM(r.convert_count),  ");
		hql.append(" SUM(r.convert_price_count) ");
		hql.append(" from pfp_ad_os_report as r ");
		hql.append(" where 1 = 1");
		hql.append("   and r.customer_info_id = :customerInfoId");
		hql.append("   and r.ad_pvclk_date between :startDate and :endDate ");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", sdf.parse(startDate));
		sqlParams.put("endDate", sdf.parse(endDate));

		if(!adPvclkOs.equals("all")){
			hql.append(" and r.ad_pvclk_os = :adPvclkOs");
			sqlParams.put("adPvclkOs", adPvclkOs);
		}

//		if(StringUtils.isNotBlank(searchText)){
//			String searchStr = getSearchText(searchText,adSearchWay);
//		}

		hql.append(" group by r.ad_pvclk_os");
		hql.append(" order by r.ad_pvclk_os desc");

		sqlParams.put("sql", hql);

		return sqlParams;
	}

	private HashMap<String, Object> getAdOsReportChartSQL(final String adPvclkOs, final String adSearchWay, final String searchText, final String customerInfoId, final String startDate, final String endDate) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select ");
		hql.append(" sum(r.ad_pv), ");
		hql.append(" sum((case when r.ad_clk_price_type = 'CPC' then r.ad_clk else r.ad_view end)), ");				// 產生pfp_ad_os_report 的時候，已經減過無效點擊數了，所以不用再減
		hql.append(" sum(r.ad_clk_price), ");		// 產生pfp_ad_os_report 的時候，已經減過無效點擊金額了，所以不用再減
		hql.append(" sum(r.ad_invalid_clk), ");
		hql.append(" sum(r.ad_invalid_clk_price), ");
		hql.append(" r.ad_pvclk_date, ");
		hql.append(" SUM(r.convert_count),  ");
		hql.append(" SUM(r.convert_price_count) ");
		hql.append(" from pfp_ad_os_report as r");
		hql.append(" where 1 = 1");
		hql.append("   and r.customer_info_id = :customerInfoId");
		hql.append("   and r.ad_pvclk_date between :startDate and :endDate ");
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("startDate", sdf.parse(startDate));
		sqlParams.put("endDate", sdf.parse(endDate));

		if(!adPvclkOs.equals("all")){
			hql.append(" and r.ad_pvclk_os = :adPvclkOs");
			sqlParams.put("adPvclkOs", adPvclkOs);
		}

//		if(StringUtils.isNotEmpty(searchText)){
//			String searchStr = getSearchText(searchText,adSearchWay);
//		}

		hql.append(" group by r.ad_pvclk_date");
		hql.append(" order by r.ad_pvclk_date");

		sqlParams.put("sql", hql);

		return sqlParams;
	}

	/**
	 *  行動廣告成效(明細)
	 * 統計 pfp_ad_os_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_os_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 */
	@Override
	public List<Map<String, Object>> getAdOsList(AdOsReportVO vo) {
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT ");
		hql.append(" SUM(r.ad_pv) AS ad_pv_sum, ");
		hql.append(" SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN r.ad_clk ELSE r.ad_view END)) AS ad_clk_sum, ");
		hql.append(" SUM(r.ad_clk_price) AS ad_price_sum, ");
		hql.append(" r.ad_pvclk_date, ");
		hql.append(" r.ad_pvclk_device, ");
		hql.append(" r.ad_pvclk_os, ");
		hql.append(" r.customer_info_id, ");
		hql.append(" SUM(r.convert_count) AS convert_count, ");
		hql.append(" SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_os_report AS r ");
		hql.append(" WHERE 1 = 1");
		hql.append("   AND r.customer_info_id = :customerInfoId");
		hql.append("   AND r.ad_pvclk_date BETWEEN :startDate AND :endDate ");
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND r.ad_pvclk_os LIKE :searchStr ");
		}
		
		hql.append(" GROUP BY r.ad_pvclk_os");
		hql.append(" ORDER BY r.ad_pvclk_os DESC");
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		if (!vo.isDownloadOrIsNotCuttingPagination()) { // 不是下載則做分頁處理
			// 取得分頁
			query.setFirstResult((vo.getPage() - 1) * vo.getPageSize());
			query.setMaxResults(vo.getPageSize());
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	/**
	 * 行動廣告成效(加總)
	 * 統計 pfp_ad_os_report 的曝光數、點擊數、點擊數總費用、無效點擊數、無效點擊數總費用...等的資料
	 * 注意：ad_clk、ad_clk_price 在產生 pfp_ad_os_report 的時候，已經減過無效點擊的資料了，所以不用再減一次，不然會錯誤
	 */
	@Override
	public List<Map<String, Object>> getAdOsListSum(AdOsReportVO vo) {
		StringBuilder hql = new StringBuilder();
		hql.append("SELECT ");
		hql.append("  SUM(r.ad_pv) AS ad_pv_sum, ");
		hql.append("  SUM((CASE WHEN r.ad_clk_price_type = 'CPC' THEN r.ad_clk ELSE r.ad_view END)) AS ad_clk_sum, ");
		hql.append("  SUM(r.ad_clk_price) AS ad_price_sum, ");
		hql.append("  SUM(r.convert_count) AS convert_count,  ");
		hql.append("  SUM(r.convert_price_count) AS convert_price_count ");
		hql.append(" FROM pfp_ad_os_report AS r");
		hql.append(" WHERE 1 = 1 ");
		hql.append("   AND r.customer_info_id = :customerInfoId");
		hql.append("   AND r.ad_pvclk_date BETWEEN :startDate AND :endDate");
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			hql.append(" AND r.ad_pvclk_os LIKE :searchStr ");
		}
		
		hql.append(" GROUP BY r.ad_pvclk_os");
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("customerInfoId", vo.getCustomerInfoId());
		query.setString("startDate", vo.getStartDate());
		query.setString("endDate", vo.getEndDate());
		
		if (StringUtils.isNotBlank(vo.getSearchText())) {
			query.setString("searchStr", "%" + vo.getSearchText() + "%");
		}
		
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

}