package com.pchome.akbpfp.db.dao.ad;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAd;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdDetail;
import com.pchome.akbpfp.db.vo.ad.PfpAdAdViewConditionVO;
import com.pchome.enumerate.utils.EnumStatus;

public class PfpAdDAO extends BaseDAO<PfpAd,String> implements IPfpAdDAO{
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@SuppressWarnings("unchecked")
	public List<PfpAd> getPfpAds(String adSeq, String adGroupSeq, String adClass, String adArea, String adStyle, String adStatus) throws Exception{
		StringBuffer sql = new StringBuffer("from PfpAd where 1=1");
		HashMap<String, Object> sqlParams = new HashMap<String, Object>(); 
		if (StringUtils.isNotEmpty(adSeq)) {
			sql.append(" and adSeq = :adSeq");
			sqlParams.put("adSeq", adSeq.trim());
		}

		if (StringUtils.isNotEmpty(adGroupSeq)) {
			sql.append(" and pfpAdGroup.adGroupSeq = :adGroupSeq");
			sqlParams.put("adGroupSeq", adGroupSeq.trim());
		}

		if (StringUtils.isNotEmpty(adClass)) {
			sql.append(" and adClass = :adClass");
			sqlParams.put("adClass", adClass.trim());
		}

		if (StringUtils.isNotEmpty(adArea)) {
			sql.append(" and adArea like :adArea");
			sqlParams.put("adArea", "%" + adArea.trim() + "%");
		}

		if (StringUtils.isNotEmpty(adStyle)) {
			sql.append(" and adStyle = :adStyle");
			sqlParams.put("adStyle", adStyle.trim());
		}

		if (StringUtils.isNotEmpty(adStatus)) {
			sql.append(" and adStatus = :adStatus");
			sqlParams.put("adStatus", adStatus.trim());
		}

		Query query = super.getSession().createQuery(sql.toString());
        for (String paramName:sqlParams.keySet()) {
        	if(!paramName.equals("sql")) {
        		query.setParameter(paramName, sqlParams.get(paramName));
        	}
        }
        List<PfpAd> resultData = query.list();
		//log.info(">> resultData_size  = "+resultData.size());
        return resultData;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> findAdView(final String adActionSeq, final String adType, final String adGroupSeq, final String adSeq, final String adStatus, final String searchWord, final String startDate, final String endDate, final int page, final int pageSize, final String customerInfoId) throws Exception{
		List<Object> result = (List<Object> ) getHibernateTemplate().execute(
                new HibernateCallback<List<Object> >() {
					public List<Object>  doInHibernate(Session session) throws HibernateException, SQLException {
						//String sql = adViewSQL(adActionSeq, adType, adGroupSeq, adSeq, adStatus, searchWord, startDate, endDate, customerInfoId);

						// 統計帳戶下所有廣告成本
                        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
						try {
							sqlParams = mAdViewSQL(adActionSeq, adType, adGroupSeq, adSeq, adStatus, searchWord, startDate, endDate, customerInfoId);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String sqlStr = sqlParams.get("sql").toString();
						//log.info(sqlStr);
					
						List<Object> resultData = null;

						Query query = session.createSQLQuery(sqlStr.toString())
				        		.setString("adActionSeq", adActionSeq)
				        		.setString("adGroupSeq", adGroupSeq);
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

						resultData = query.list();
						//log.info(">> resultData_size  = "+resultData.size());
                        return resultData;
                    }
                }
        );
        
        return result;
	}
	
	public String getCount(final String adActionSeq, final String adType, final String adGroupSeq, final String adSeq, final String adStatus, final String searchWord, final String startDate, final String endDate, final int page, final int pageSize, final String customerInfoId) throws Exception{
		String result = (String ) getHibernateTemplate().execute(
                new HibernateCallback<String >() {
					public String  doInHibernate(Session session) throws HibernateException, SQLException {
						//String sql = adViewSQL(adActionSeq, adType, adGroupSeq, adSeq, adStatus, searchWord, startDate, endDate, customerInfoId);
//						String resultData = Integer.toString(session.createSQLQuery(sql).list().size());
//						log.info(">> resultData_size  = "+ resultData);
//                        return resultData;

                        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
						try {
							sqlParams = mAdViewSQL(adActionSeq, adType, adGroupSeq, adSeq, adStatus, searchWord, startDate, endDate, customerInfoId);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String sql = sqlParams.get("sql").toString();
						
						Query query = session.createSQLQuery(sql);
				        for (String paramName:sqlParams.keySet()) {
				        	if(!paramName.equals("sql")) {
				        		query.setParameter(paramName, sqlParams.get(paramName));
				        	}
				        }

				        String resultData = Integer.toString(query.list().size());
						log.info(">> resultData_size  = "+ resultData);
                        return resultData;
                    }
                }
        );
        
        return result;
	}
	
//	private String adViewSQL(String adActionSeq, String adType, String adGroupSeq, String adSeq, String adStatus, String searchWord, String startDate, String endDate, String customerInfoId){
//		// 統計帳戶下所有廣告成本
//		StringBuffer sqlStr = new StringBuffer();
//		sqlStr.append("select");
//		sqlStr.append("	 paa.ad_action_seq,");
//		sqlStr.append("	 paa.ad_action_name,");
//		sqlStr.append("	 paa.ad_type,");
//		sqlStr.append("	 pag.ad_group_seq,");
//		sqlStr.append("	 pag.ad_group_name,");
//		sqlStr.append("	 pa.ad_seq,");
//		sqlStr.append("	 pa.ad_status,");
//		sqlStr.append("	 papc.ad_pv,");
//		sqlStr.append("	 papc.ad_clk,");
//		sqlStr.append("	 papc.ad_pv_price,");
//		sqlStr.append("	 papc.ad_clk_price");
//		sqlStr.append(" from");
//		sqlStr.append("	  pfp_ad_action as paa, pfp_ad_group as pag, pfp_ad as pa");
//		sqlStr.append("	  left outer join pfp_ad_detail as pad on pa.ad_seq = pad.ad_seq");
//		sqlStr.append("	  left outer join ");
//		sqlStr.append("	       (SELECT ad_seq, customer_info_id, sum(ad_pv) as ad_pv, sum(ad_clk) as ad_clk, sum(ad_pv_price) as ad_pv_price, sum(ad_clk_price) as ad_clk_price FROM pfp_ad_pvclk group by ad_seq, customer_info_id) as papc on pa.ad_seq = papc.ad_seq");
//		sqlStr.append(" where paa.ad_action_seq is not null");
//		sqlStr.append("   and paa.ad_action_seq = pag.ad_action_seq");
//		sqlStr.append("   and pag.ad_group_seq = pa.ad_group_seq");
//		sqlStr.append("   and paa.ad_action_seq = '" + adActionSeq + "'");
//		sqlStr.append("   and pag.ad_group_seq = '" + adGroupSeq + "'");
//		if(StringUtils.isNotEmpty(adSeq)) {
//			sqlStr.append(" and pa.ad_seq = '" + adSeq + "'");
//		}
//		if(StringUtils.isNotEmpty(adType)) {
//			sqlStr.append(" and paa.ad_type = '" + adType + "'");
//		}
//		if(StringUtils.isNotEmpty(searchWord)) {
//			sqlStr.append(" and pad.ad_detail_content like '%" + searchWord + "%'");
//		}
//		if(StringUtils.isNotEmpty(adStatus)) {
//			sqlStr.append(" and pa.ad_status = " + adStatus);
//		}
//		if(StringUtils.isNotEmpty(customerInfoId)) {
//			sqlStr.append(" and paa.customer_info_id = '" + customerInfoId + "'");
//		}
//		// 如果查詢開始日期或查詢結束日期有一個為空值
//		if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
//			String strDate = null;
//			if(StringUtils.isEmpty(startDate))		strDate = endDate;
//			else 									strDate = startDate;
//			if(StringUtils.isNotEmpty(strDate)) {
//				sqlStr.append(" and (paa.ad_action_start_date < '" + strDate + "'");
//				sqlStr.append(" and paa.ad_action_end_date > '" + strDate + "')");
//			}
//		} else {		// 如果查詢開始日期或查詢結束日期皆有值
//			sqlStr.append(" and ((paa.ad_action_start_date between '" + startDate + "' and '" + endDate + "')");
//			sqlStr.append(" or (paa.ad_action_end_date between '" + startDate + "' and '" + endDate + "')");
//			sqlStr.append(" or (paa.ad_action_start_date < '" + startDate + "'");
//			sqlStr.append(" and paa.ad_action_end_date > '" + endDate + "'))");
//		}
//		sqlStr.append(" group by paa.ad_action_seq, paa.customer_info_id, pag.ad_group_seq, pa.ad_seq");
//		log.info("sqlStr = " + sqlStr);
//	
//		return sqlStr.toString();
//	}
	
	private HashMap<String, Object> mAdViewSQL(String adActionSeq, String adType, String adGroupSeq, String adSeq, String adStatus, String searchWord, String startDate, String endDate, String customerInfoId) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		// 統計帳戶下所有廣告成本
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select");
		sqlStr.append("	 paa.ad_action_seq,");
		sqlStr.append("	 paa.ad_action_name,");
		sqlStr.append("	 paa.ad_type,");
		sqlStr.append("	 pag.ad_group_seq,");
		sqlStr.append("	 pag.ad_group_name,");
		sqlStr.append("	 pa.ad_seq,");
		sqlStr.append("	 pa.ad_status,");
		sqlStr.append("	 papc.ad_pv,");
		sqlStr.append("	 papc.ad_clk,");
		sqlStr.append("	 papc.ad_pv_price,");
		sqlStr.append("	 papc.ad_clk_price");
		sqlStr.append(" from");
		sqlStr.append("	  pfp_ad_action as paa, pfp_ad_group as pag, pfp_ad as pa");
		sqlStr.append("	  left outer join pfp_ad_detail as pad on pa.ad_seq = pad.ad_seq");
		sqlStr.append("	  left outer join ");
		sqlStr.append("	       (SELECT ad_seq, customer_info_id, sum(ad_pv) as ad_pv, sum(ad_clk) as ad_clk, sum(ad_pv_price) as ad_pv_price, sum(ad_clk_price) as ad_clk_price FROM pfp_ad_pvclk group by ad_seq, customer_info_id) as papc on pa.ad_seq = papc.ad_seq");
		sqlStr.append(" where paa.ad_action_seq is not null");
		sqlStr.append("   and paa.ad_action_seq = pag.ad_action_seq");
		sqlStr.append("   and pag.ad_group_seq = pa.ad_group_seq");
		sqlStr.append("   and paa.ad_action_seq = :adActionSeq");
		sqlStr.append("   and pag.ad_group_seq = :adGroupSeq");
		sqlParams.put("adActionSeq", adActionSeq);
		sqlParams.put("adGroupSeq", adGroupSeq);
		
		if(StringUtils.isNotEmpty(adSeq)) {
			sqlStr.append(" and pa.ad_seq = :adSeq");
			sqlParams.put("adSeq", adSeq);
		}
		if(StringUtils.isNotEmpty(adType)) {
			sqlStr.append(" and paa.ad_type = :adType");
			sqlParams.put("adType", adType);
		}
		if(StringUtils.isNotEmpty(searchWord)) {
			sqlStr.append(" and pad.ad_detail_content like :searchWord");
			sqlParams.put("searchWord", "%" + searchWord + "%");
		}
		if(StringUtils.isNotEmpty(adStatus)) {
			sqlStr.append(" and pa.ad_status = :adStatus");
			sqlParams.put("adStatus", adStatus);
		}
		if(StringUtils.isNotEmpty(customerInfoId)) {
			sqlStr.append(" and paa.customer_info_id = :customerInfoId");
			sqlParams.put("customerInfoId", customerInfoId);
		}
		// 如果查詢開始日期或查詢結束日期有一個為空值
		if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
			String strDate = null;
			if(StringUtils.isEmpty(startDate))		strDate = endDate;
			else 									strDate = startDate;
			if(StringUtils.isNotEmpty(strDate)) {
				sqlStr.append(" and (paa.ad_action_start_date < :strDate");
				sqlStr.append(" and paa.ad_action_end_date > :strDate)");
				sqlParams.put("strDate", sdf.parse(strDate));
			}
		} else {		// 如果查詢開始日期或查詢結束日期皆有值
			sqlStr.append(" and ((paa.ad_action_start_date between :startDate and :endDate)");
			sqlStr.append(" or (paa.ad_action_end_date between :startDate and :endDate)");
			sqlStr.append(" or (paa.ad_action_start_date < :startDate");
			sqlStr.append(" and paa.ad_action_end_date > :endDate))");
			sqlParams.put("startDate", sdf.parse(startDate));
			sqlParams.put("endDate", sdf.parse(endDate));
		}
		sqlStr.append(" group by paa.ad_action_seq, paa.customer_info_id, pag.ad_group_seq, pa.ad_seq");
		sqlParams.put("sql", sqlStr);
		log.info("sqlStr = " + sqlStr);
	
		return sqlParams;
	}

	@SuppressWarnings("unchecked")
	public PfpAd getPfpAdBySeq(String adSeq) throws Exception {
		List<PfpAd> list = super.getHibernateTemplate().find("from PfpAd where adSeq = ?", adSeq);
		if (list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 依傳入的 seq 列表，查詢廣告活動資料
	 * @param seqList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getPfpAdBySeqList(List<String> seqList) throws Exception {
		String hql = "from PfpAd where adSeq in (:adSeq) ";
		// 將條件資料設定給 Query，準備 query
		Session session = getSession();
		Query q = session.createQuery(hql.toString());
		q.setParameterList("adSeq", seqList);

		HashMap<String, Object> adList = new HashMap<String, Object>();
		List<PfpAd> pfpAds = q.list();
		for(PfpAd pfpAd:pfpAds) {
			adList.put(pfpAd.getAdSeq(), pfpAd);
		}
		return adList;
	}

	public void insertPfpAd(PfpAd pfpAd) throws Exception {
		this.saveOrUpdate(pfpAd);
	}

	public void updatePfpAd(PfpAd pfpAd) throws Exception {
		this.update(pfpAd);
	}
	
	public void saveOrUpdatePfpAd(PfpAd pfpAd) throws Exception{
		final StringBuffer sql = new StringBuffer()
		.append("INSERT INTO pfp_ad(ad_seq, ad_group_seq, ad_class, ad_area, ad_style, template_product_seq, ad_assign_tad_seq, ad_status, ad_send_verify_time, ad_search_price, ad_channel_price, ad_create_time, ad_update_time) ")
		.append("VALUES ( :ad_seq")
		.append(", :ad_group_seq")
		.append(", :ad_class")
		.append(", :ad_area")
		.append(", :ad_style")
		.append(", :template_product_seq")
		.append(", :ad_assign_tad_seq")
		.append(", :ad_status")
		.append(", :ad_send_verify_time")
		.append(", :ad_search_price")
		.append(", :ad_channel_price")
		.append(", :ad_create_time")
		.append(", :ad_update_time)");
		log.info(sql);

        Session session = getSession();
        session.createSQLQuery(sql.toString())
        	.setString("ad_seq", pfpAd.getAdSeq())
        	.setString("ad_group_seq", pfpAd.getPfpAdGroup().getAdGroupSeq())
        	.setString("ad_class", pfpAd.getAdClass())
        	.setString("ad_area", pfpAd.getAdArea())
        	.setString("ad_style", pfpAd.getAdStyle())
        	.setString("template_product_seq", pfpAd.getTemplateProductSeq())
        	.setString("ad_assign_tad_seq", pfpAd.getAdAssignTadSeq())
        	.setInteger("ad_status", pfpAd.getAdStatus())
        	.setTimestamp("ad_send_verify_time", pfpAd.getAdSendVerifyTime())
        	.setFloat("ad_search_price", pfpAd.getAdSearchPrice())
        	.setFloat("ad_channel_price", pfpAd.getAdChannelPrice())
        	.setTimestamp("ad_create_time", pfpAd.getAdCreateTime())
        	.setTimestamp("ad_update_time", pfpAd.getAdUpdateTime())
        	.executeUpdate();
        session.flush();
	}
	
	public void updatePfpAdStatus(String pfpAdStatus, String adSeq) throws Exception {
		String sql = "UPDATE pfp_ad set ad_status = :pfpAdStatus where ad_seq = :adSeq";
		log.info(sql);

        Session session = getSession();
        session.createSQLQuery(sql).setString("pfpAdStatus", pfpAdStatus).setString("adSeq", adSeq).executeUpdate();
        session.flush();
	}
	
	public void saveOrUpdateWithCommit(PfpAd adAd) throws Exception{
		super.getSession().saveOrUpdate(adAd);
		super.getSession().beginTransaction().commit();
	}

	/**
	 * (舊版)查詢廣告分類資料，由 pfp_ad_report 統計資料
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param adType
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAdAdPvclk(final String customerInfoId, final String adGroupSeq, final String keyword, final int adType, final Date startDate, final Date endDate, final int page, final int pageSize) throws Exception{
		List<Object> result = (List<Object> ) getHibernateTemplate().execute(
				 
                new HibernateCallback<List<Object> >() {
                	
					public List<Object>  doInHibernate(Session session) throws HibernateException, SQLException {
						
						StringBuffer hql = new StringBuffer();
										
						hql.append(" select paa.pfpAdGroup.pfpAdAction.adActionSeq, ");
						hql.append(" 		paa.pfpAdGroup.pfpAdAction.adActionName, ");
						hql.append(" 		paa.pfpAdGroup.pfpAdAction.adType, ");
						hql.append(" 		paa.pfpAdGroup.adGroupSeq, ");
						hql.append(" 		paa.pfpAdGroup.adGroupName, ");
						hql.append(" 		paa.adSeq, ");
						hql.append(" 		paa.templateProductSeq, ");
						hql.append(" 		paa.adStatus, ");
						hql.append(" 		COALESCE(paa.adVerifyRejectReason,''), ");
						hql.append(" 		COALESCE(sum(papc.adPv),0), ");
						hql.append(" 		COALESCE(sum(papc.adClk),0), ");
						hql.append("		COALESCE(sum(papc.adClkPrice),0), ");
						hql.append("		COALESCE(sum(papc.adInvalidClk),0), ");
						hql.append("		COALESCE(sum(papc.adInvalidClkPrice),0) ");
						hql.append(" from PfpAd as paa ");
						hql.append(" 		left join paa.pfpAdPvclks papc ");
						hql.append(" 			with (papc.adPvclkDate >= :startDate ");
						hql.append(" 					and papc.adPvclkDate <= :endDate ");
						
						if(adType > 0){
							hql.append(" 				and papc.adType = :adType) ");
						}
						else{
							hql.append(" 				and papc.adType != :adType) ");
						}
						
						hql.append(" 		left join paa.pfpAdDetails paad ");   
						hql.append(" 			with (paad.adDetailId = 'title') ");
						hql.append(" where paa.pfpAdGroup.pfpAdAction.pfpCustomerInfo.customerInfoId = :customerInfoId ");
						hql.append(" and paa.adStatus != :status ");
						hql.append(" and paa.pfpAdGroup.adGroupSeq = :adGroupSeq ");
						hql.append(" and paad.adDetailContent like :keyword  ");
						hql.append(" group by paa.adSeq ");
						hql.append(" order by paa.adCreateTime desc ");
						
						
						/*
						log.info(hql);
						log.info(" startDate = "+startDate);
						log.info(" endDate = "+endDate);
						log.info(" adType = "+adType);
						log.info(" customerInfoId = "+customerInfoId);
						log.info(" EnumStatus.Close.getStatusId() = "+EnumStatus.Close.getStatusId());
						log.info(" adGroupSeq = "+adGroupSeq);
						log.info(" keyword = "+keyword);
						*/
						Query q = session.createQuery(hql.toString())
									.setDate("startDate", startDate)
									.setDate("endDate", endDate)
									.setInteger("adType", adType)									
									.setString("customerInfoId", customerInfoId)								
									.setInteger("status", EnumStatus.Close.getStatusId())
									.setString("adGroupSeq", adGroupSeq)
									.setString("keyword", "%"+keyword+"%");
														
						
						//page=-1 取得全部不分頁用於download
						if(page!=-1){
							q.setFirstResult((page-1)*pageSize)  
								.setMaxResults(pageSize);
						}      

						//log.info(" resultData size  = "+ q.list().size());
                        
						return q.list();
                    }
                }
        );
        
        return result;
	}

	/**
	 * (新版)查詢廣告分類資料，由 pfp_ad_report 統計資料
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param adType
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAdAdReport(final String customerInfoId, final String adGroupSeq, final String keyword, final int adType, final Date startDate, final Date endDate, final int page, final int pageSize) throws Exception{
		List<Object> result = (List<Object> ) getHibernateTemplate().execute(

                new HibernateCallback<List<Object> >() {

					public List<Object>  doInHibernate(Session session) throws HibernateException, SQLException {
						System.out.println("getAdAdReport");
						StringBuffer hql = new StringBuffer();

						hql.append(" select pa.ad_action_seq, ");
						hql.append(" 		pa.ad_action_name, ");
						hql.append(" 		pa.ad_type, ");
						hql.append(" 		pag.ad_group_seq, ");
						hql.append(" 		pag.ad_group_name, ");
						hql.append(" 		paa.ad_seq, ");
						hql.append(" 		paa.template_product_seq, ");
						hql.append(" 		paa.ad_status, ");
						hql.append(" 		COALESCE(paa.ad_verify_reject_reason,''), ");
						hql.append(" 		COALESCE(sum(papc.ad_pv),0), ");
						hql.append(" 		COALESCE(sum(papc.ad_clk),0), ");
						hql.append("		COALESCE(sum(papc.ad_clk_price),0), ");
						hql.append("		COALESCE(sum(papc.ad_invalid_clk),0), ");
						hql.append("		COALESCE(sum(papc.ad_invalid_clk_price),0) ");
						hql.append(" from pfp_ad_action pa, pfp_ad_group pag, pfp_ad as paa");
						hql.append(" 		left join pfp_ad_detail paad on (paa.ad_seq = paad.ad_seq and paad.ad_detail_id = 'title')");
						hql.append(" 		left join pfp_ad_report papc ");
						hql.append(" 			on (paa.ad_seq = papc.ad_seq");
						hql.append(" 					and papc.ad_pvclk_date >= :startDate ");
						hql.append(" 					and papc.ad_pvclk_date <= :endDate ");

						if(adType > 0){
							hql.append(" 				and papc.ad_type = :adType) ");
						}
						else{
							hql.append(" 				and papc.ad_type != :adType) ");
						}

						hql.append(" where paa.ad_group_seq = pag.ad_group_seq ");
						hql.append(" and pag.ad_action_seq = pa.ad_action_seq ");
						hql.append(" and pa.customer_info_id = :customerInfoId ");
						hql.append(" and paa.ad_status != :status ");
						hql.append(" and paa.ad_group_seq = :adGroupSeq ");
						hql.append(" and paad.ad_detail_content like :keyword  ");
						hql.append(" group by paa.ad_seq ");
						hql.append(" order by paa.ad_create_time desc ");

						/*
						log.info(hql);
						log.info(" startDate = "+startDate);
						log.info(" endDate = "+endDate);
						log.info(" adType = "+adType);
						log.info(" customerInfoId = "+customerInfoId);
						log.info(" EnumStatus.Close.getStatusId() = "+EnumStatus.Close.getStatusId());
						log.info(" adGroupSeq = "+adGroupSeq);
						log.info(" keyword = "+keyword);
						*/
						Query q = session.createSQLQuery(hql.toString())
									.setDate("startDate", startDate)
									.setDate("endDate", endDate)
									.setInteger("adType", adType)
									.setString("customerInfoId", customerInfoId)			
									.setInteger("status", EnumStatus.Close.getStatusId())
									.setString("adGroupSeq", adGroupSeq)
									.setString("keyword", "%"+keyword+"%");

						//page=-1 取得全部不分頁用於download
						if(page!=-1){
							q.setFirstResult((page-1)*pageSize)  
								.setMaxResults(pageSize);
						}      

						//log.info(" resultData size  = "+ q.list().size());
                        
						return q.list();
                    }
                }
        );
        
        return result;
	}

	/**
	 * 查詢廣告明細筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public long getPfpAdCount(String customerInfoId, String adGroupSeq, String keyword, int page, int pageSize) throws Exception{
		Session session = getSession();

		StringBuffer hql = new StringBuffer();
		hql.append("select count(adSeq) from PfpAd ");
		hql.append(" where 1=1");
		hql.append(" and adStatus != :status");
		hql.append(" and pfpAdGroup.adGroupSeq = :adGroupSeq ");
		if (StringUtils.isNotEmpty(customerInfoId)) {
			hql.append(" and pfpAdGroup.pfpAdAction.pfpCustomerInfo.customerInfoId = :customerInfoId");
		}
		//log.info("getPfpAdForView.sql = " + hql);

		Query q = session.createQuery(hql.toString())
					.setString("adGroupSeq", adGroupSeq)
					.setInteger("status", EnumStatus.Close.getStatusId());
		if (StringUtils.isNotEmpty(customerInfoId)) {
			q.setString("customerInfoId", customerInfoId);
		}

		//page=-1 取得全部不分頁用於download
		if(page!=-1){
			q.setFirstResult((page-1)*pageSize)  
				.setMaxResults(pageSize);
		}      
		//log.info(" resultData size  = "+ q.list().size());

		Long count = 0L;
		List<Object> resultData = q.list();
		if(resultData != null) {
			count = Long.parseLong(resultData.get(0).toString());
		}

		return count;
	}

	/**
	 * 查詢廣告明細資料(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PfpAd> getPfpAdForView(String customerInfoId, String adGroupSeq, List<String> adSeqList, int page, int pageSize) throws Exception{
		Session session = getSession();

		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpAd ");
		hql.append(" where 1=1");
		hql.append(" and adStatus != :status");
		hql.append(" and pfpAdGroup.adGroupSeq = :adGroupSeq ");
		if (StringUtils.isNotEmpty(customerInfoId)) {
			hql.append(" and pfpAdGroup.pfpAdAction.pfpCustomerInfo.customerInfoId = :customerInfoId");
		}

		if (adSeqList != null) {
			hql.append(" and adSeq in (:adSeq)");
		}
		hql.append(" order by adCreateTime desc ");
		//log.info("getPfpAdForView.sql = " + hql);

		Query q = session.createQuery(hql.toString())
					.setString("adGroupSeq", adGroupSeq)
					.setInteger("status", EnumStatus.Close.getStatusId());
		if (StringUtils.isNotEmpty(customerInfoId)) {
			q.setString("customerInfoId", customerInfoId);
		}
		if (adSeqList != null) {
			q.setParameterList("adSeq", adSeqList);
		}

		//page=-1 取得全部不分頁用於download
		if(page!=-1){
			q.setFirstResult((page-1)*pageSize)  
				.setMaxResults(pageSize);
		}      
		//log.info(" resultData size  = "+ q.list().size());

		return q.list();
	}

	/**
	 * 查詢廣告明細詳細內容
	 * @param adSeq
	 * @param adDetailId
	 * @param adDetailContent
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PfpAdDetail> getPfpAdDetailByCondition(String adSeq, String adDetailId, String adDetailContent) throws Exception {
		StringBuffer sql = new StringBuffer("from PfpAdDetail where 1=1");
		if (StringUtils.isNotEmpty(adSeq)) {
			sql.append(" and pfpAd.adSeq = :adSeq");
		}
		if(StringUtils.isNotBlank(adDetailContent)) {
			sql.append(" and adDetailId = :adDetailId");
			sql.append(" and adDetailContent like :adDetailContent");
		}
		//log.info("getPfpAdDetailByCondition.sql = " + sql.toString());

		Query q = super.getSession().createQuery(sql.toString());
		if (StringUtils.isNotEmpty(adSeq)) {
			q.setString("adSeq", adSeq);
		}
		if(StringUtils.isNotBlank(adDetailContent)) {
			q.setString("adDetailId", adDetailId);
			q.setString("adDetailContent", "%" + adDetailContent + "%");
		}
		return q.list();
	}

	/**
	 * 查詢廣告明細成效(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param adSeq
	 * @param adType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAdReportByAKS(String customerInfoId, String adGroupSeq, String adSeq, int adType, Date startDate, Date endDate) throws Exception{
		Session session = getSession();
		
		StringBuffer hql = new StringBuffer();
		hql.append("select COALESCE(sum(adPv),0), ");
		hql.append(" COALESCE(sum(adClk),0), ");
		hql.append(" COALESCE(sum(adClkPrice),0), ");
		hql.append(" COALESCE(sum(adInvalidClk),0), ");
		hql.append(" COALESCE(sum(adInvalidClkPrice),0), ");
		hql.append(" COALESCE(adType,0) ");
		hql.append(" from PfpAdReport where 1=1 ");

		if (StringUtils.isNotEmpty(customerInfoId)) {
			hql.append(" and customerInfoId = :customerInfoId");
		}

		if (StringUtils.isNotEmpty(adGroupSeq)) {
			hql.append(" and adGroupSeq = :adGroupSeq");
		}

		if (StringUtils.isNotEmpty(adSeq)) {
			hql.append(" and adSeq = :adSeq");
		}

		if(adType > 0){
			hql.append(" and adType = :adType ");
		}else{
			hql.append(" and adType != :adType ");
		}

		if (startDate != null) {
			hql.append(" and adPvclkDate  >= :startDate");
		}

		if (endDate != null) {
			hql.append(" and adPvclkDate <= :endDate");
		}
		log.info("getAdReportByAKS.hql = " + hql);
		

		Query q = session.createQuery(hql.toString())
					.setInteger("adType", adType);
		if (StringUtils.isNotEmpty(customerInfoId)) {
			q.setString("customerInfoId", customerInfoId);
		}

		if (StringUtils.isNotEmpty(adGroupSeq)) {
			q.setString("adGroupSeq", adGroupSeq);
		}

		if (StringUtils.isNotEmpty(adSeq)) {
			q.setString("adSeq", adSeq);
		}

		if (startDate != null) {
			q.setDate("startDate", startDate);
		}

		if (endDate != null) {
			q.setDate("endDate", endDate);
		}
		//log.info(" resultData size  = "+ q.list().size());

		return q.list();
	}

	/**
	 * 查詢廣告明細成效(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param adSeq
	 * @param adType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getAdReportByAdsList(String customerInfoId, String adGroupSeq, List<String> adSeqList, int adType, Date startDate, Date endDate) throws Exception{
		Session session = getSession();
		
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("select adSeq, ");
		hql.append(" COALESCE(sum(adPv),0), ");
		hql.append(" COALESCE(sum(adClk),0), ");
		hql.append(" COALESCE(sum(adClkPrice),0), ");
		hql.append(" COALESCE(sum(adInvalidClk),0), ");
		hql.append(" COALESCE(sum(adInvalidClkPrice),0), ");
		hql.append(" COALESCE(adType,0) ");
		hql.append(" from PfpAdReport where 1=1 ");

		if (StringUtils.isNotEmpty(customerInfoId)) {
			hql.append(" and customerInfoId = :customerInfoId");
			sqlParams.put("customerInfoId", customerInfoId.trim());
		}

		if (StringUtils.isNotEmpty(adGroupSeq)) {
			hql.append(" and adGroupSeq = :adGroupSeq");
			sqlParams.put("adGroupSeq", adGroupSeq.trim());
		}

		if (adSeqList != null) {
			hql.append(" and adSeq in ( :adSeq )");
			sqlParams.put("adSeq", adSeqList);
		}

		if(adType > 0){
			hql.append(" and adType = :adType ");
			sqlParams.put("adType", adType);
		}else{
			hql.append(" and adType != :adType ");
			sqlParams.put("adType", adType);
		}

		if (startDate != null) {
			hql.append(" and adPvclkDate  >= :startDate");
			sqlParams.put("startDate", startDate);
		}

		if (endDate != null) {
			hql.append(" and adPvclkDate <= :endDate");
			sqlParams.put("endDate", endDate);
		}
		hql.append(" group by adSeq");
		//log.info("getAdReportByAdsList.hql = " + hql);
		

		// 將條件資料設定給 Query，準備 query
		Query q = session.createQuery(hql.toString());
        for (String paramName:sqlParams.keySet()) {
        	if(!paramName.equals("sql")) {
				if(paramName.equals("adSeq")) {
					q.setParameterList("adSeq", adSeqList);
				} else {
					q.setParameter(paramName, sqlParams.get(paramName));
				}
        	}
        }
		//log.info(" resultData size  = "+ q.list().size());

        // 將得到的廣告成效結果，設定成 Map, 以方便用 adKeywordSeq 抓取資料
		HashMap<String, Object> adSum = new HashMap<String, Object>();
		List<Object> pfpAdReports = q.list();
		for(Object object:pfpAdReports) {
			Object[] ob = (Object[])object;
			adSum.put(ob[0].toString(), object);
		}
//		// 顯示廣告成效內容
//		for(String adSeq:adSum.keySet()) {
//			Object[] obj = (Object[])adSum.get(adSeq);
//			System.out.println(adSeq + " => " + obj[0] + ";" + obj[1] + ";" + obj[2]);
//		}

		return adSum;
	}

	/**
	 * 查詢廣告明細詳細內容
	 * @param adSeq
	 * @param adDetailId
	 * @param adDetailContent
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, List<PfpAdDetail>> getPfpAdDetailByAdsList(String customerInfoId, String adGroupSeq, String adDetailId, String adDetailContent) throws Exception {

		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("from PfpAdDetail where 1=1");
		sql.append(" and pfpAd.adStatus != :status");
		sql.append(" and pfpAd.pfpAdGroup.adGroupSeq = :adGroupSeq ");
		if (StringUtils.isNotEmpty(customerInfoId)) {
			sql.append(" and pfpAd.pfpAdGroup.pfpAdAction.pfpCustomerInfo.customerInfoId = :customerInfoId");
			sqlParams.put("customerInfoId", customerInfoId);
		}
		if(StringUtils.isNotBlank(adDetailContent)) {
			sql.append(" and adDetailId = :adDetailId");
			sql.append(" and adDetailContent like :adDetailContent");
			sqlParams.put("adDetailId", adDetailId);
			sqlParams.put("adDetailContent", "%" + adDetailContent + "%");
		}
		sql.append(" order by pfpAd.adSeq");
		//log.info("getPfpAdDetailByCondition.sql = " + sql.toString());

		// 將條件資料設定給 Query，準備 query
		Query q = super.getSession().createQuery(sql.toString())
								.setString("adGroupSeq", adGroupSeq)
								.setInteger("status", EnumStatus.Close.getStatusId());
		for (String paramName:sqlParams.keySet()) {
        	if(!paramName.equals("sql")) {
				q.setParameter(paramName, sqlParams.get(paramName));
        	}
        }
		//log.info(" resultData size  = "+ q.list().size());

        // 將得到的廣告成效結果，設定成 Map, 以方便用 adKeywordSeq 抓取資料
		HashMap<String, List<PfpAdDetail>> adSum = new HashMap<String, List<PfpAdDetail>>();
		List<PfpAdDetail> pfpAdDetails = q.list();
		List<PfpAdDetail> tmpPfpAdDetails = new ArrayList<PfpAdDetail>();
		String tmpAdSeq = "";
		for(PfpAdDetail pfpAdDetail:pfpAdDetails) {
			if(!pfpAdDetail.getPfpAd().getAdSeq().equals(tmpAdSeq)) {
				if(StringUtils.isNotBlank(tmpAdSeq)) {
					adSum.put(tmpAdSeq, tmpPfpAdDetails);
				}
				tmpAdSeq = pfpAdDetail.getPfpAd().getAdSeq();
				tmpPfpAdDetails = new ArrayList<PfpAdDetail>();
			}
			tmpPfpAdDetails.add(pfpAdDetail);
		}
		if(StringUtils.isNotBlank(tmpAdSeq)) {
			adSum.put(tmpAdSeq, tmpPfpAdDetails);
		}
//
//		// 顯示廣告成效內容
//		for(String adSeq:adSum.keySet()) {
//			List<PfpAdDetail> printPfpAdDetails = new ArrayList(adSum.get(adSeq));
//			for(PfpAdDetail pfpAdDetail:printPfpAdDetails) {
//				System.out.println(adSeq + " => " + pfpAdDetail.getPfpAd().getAdSeq() + ";" + pfpAdDetail.getAdDetailSeq() + ";" + pfpAdDetail.getAdDetailId() + ";" + pfpAdDetail.getAdDetailContent() );
//			}
//		}

		return adSum;
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpAd> validAdAd(String adGroupSeq) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpAd ");
		hql.append(" where pfpAdGroup.adGroupSeq = ? ");
		hql.append(" and adStatus != ? ");
		return super.getHibernateTemplate().find(hql.toString(),adGroupSeq,EnumStatus.Close.getStatusId());
	}
	
	public List<Object> getAdAdVideoDetailView(PfpAdAdViewConditionVO pfpAdAdViewConditionVO) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ar.ad_group_seq, ");
		sql.append(" ar.ad_seq, ");
		sql.append(" ar.ad_clk_price_type, ");
		sql.append(" TRUNCATE(SUM(ar.ad_pv),0)ad_pv, ");
		sql.append(" TRUNCATE(SUM(ar.ad_clk_price),2)cost, ");
		sql.append(" TRUNCATE(SUM((CASE ");
		sql.append(" WHEN ar.ad_clk_price_type = 'CPC' THEN ar.ad_clk ");
		sql.append(" ELSE ar.ad_view ");
		sql.append(" END)),0), ");
		sql.append(" TRUNCATE((SUM(ar.ad_view) / SUM(ar.ad_pv)) * 100, 2) ad_view_ratings, ");
		sql.append(" TRUNCATE(SUM(ar.ad_clk_price) / SUM(ar.ad_view),2), ");
		sql.append(" TRUNCATE(SUM(ar.ad_clk_price) / (SUM(ar.ad_pv) / 1000), 2)thousands_cost, ");
		sql.append(" d.ad_status, ");
		sql.append(" (SELECT dt.ad_detail_content ");
		sql.append(" FROM pfp_ad_detail dt ");
		sql.append(" WHERE dt.ad_seq = ar.ad_seq ");
		sql.append(" AND dt.ad_detail_id ='img')img, ");
		sql.append(" IFNULL((SELECT dt.ad_detail_content ");
		sql.append(" FROM pfp_ad_detail dt ");
		sql.append(" WHERE dt.ad_seq = ar.ad_seq ");
		sql.append(" AND dt.ad_detail_id ='video_size'),'')video_size, ");
		sql.append(" IFNULL((SELECT dt.ad_detail_content ");
		sql.append(" FROM pfp_ad_detail dt ");
		sql.append(" WHERE dt.ad_seq = ar.ad_seq ");
		sql.append(" AND dt.ad_detail_id ='mp4'),'')mp4, ");
		sql.append(" IFNULL((SELECT dt.ad_detail_content ");
		sql.append(" FROM pfp_ad_detail dt ");
		sql.append(" WHERE dt.ad_seq = ar.ad_seq ");
		sql.append(" AND dt.ad_detail_id ='webm'),'')webm, ");
		sql.append(" IFNULL((SELECT dt.ad_detail_content ");
		sql.append(" FROM pfp_ad_detail dt ");
		sql.append(" WHERE dt.ad_seq = ar.ad_seq ");
		sql.append(" AND dt.ad_detail_id ='video_seconds'),'')video_seconds, ");
		sql.append(" (SELECT aa.ad_action_name ");
		sql.append(" FROM pfp_ad_action aa, ");
		sql.append(" pfp_ad_group ag ");
		sql.append(" WHERE ag.ad_group_seq = ar.ad_group_seq ");
		sql.append(" AND ag.ad_action_seq = aa.ad_action_seq)ad_action_name, ");
		sql.append(" (select dt.ad_detail_content from pfp_ad_detail dt where dt.ad_seq = ar.ad_seq and dt.ad_detail_id ='real_url')real_url ");
		sql.append(" FROM pfp_ad_report ar, ");
		sql.append(" pfp_ad d ");
		sql.append(" where ar.ad_group_seq  = :agSeq ");
		sql.append(" AND ar.ad_pvclk_date BETWEEN :startDate AND :endDate ");
		sql.append(" AND d.ad_seq = ar.ad_seq ");
		sql.append(" GROUP BY ad_group_seq, ");
		sql.append(" ad_seq ");
		sql.append(" LIMIT :limit, :max ");	
		
		Query query =  super.getSession().createSQLQuery(sql.toString());
		query.setParameter("agSeq", pfpAdAdViewConditionVO.getAdGroupSeq());
		query.setParameter("startDate", pfpAdAdViewConditionVO.getStartDate());
		query.setParameter("endDate", pfpAdAdViewConditionVO.getEndDate());
		query.setParameter("limit", pfpAdAdViewConditionVO.getLimit());
		query.setParameter("max", pfpAdAdViewConditionVO.getMax());
		return query.list();
	}

	public int getAdAdVideoDetailViewCount(PfpAdAdViewConditionVO pfpAdAdViewConditionVO) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ar.ad_seq ");	
		sql.append(" FROM pfp_ad_report ar ");	
		sql.append(" WHERE ar.ad_group_seq = :agSeq ");	
		sql.append(" and ar.ad_pvclk_date BETWEEN :startDate AND :endDate ");
		sql.append(" GROUP BY ad_group_seq, ");	
		sql.append(" ad_seq ");	
		
		Query query =  super.getSession().createSQLQuery(sql.toString());
		query.setParameter("agSeq", pfpAdAdViewConditionVO.getAdGroupSeq());
		query.setParameter("startDate", pfpAdAdViewConditionVO.getStartDate());
		query.setParameter("endDate", pfpAdAdViewConditionVO.getEndDate());
		return query.list().size();
	}
}
