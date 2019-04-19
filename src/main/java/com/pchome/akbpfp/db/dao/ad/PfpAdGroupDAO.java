package com.pchome.akbpfp.db.dao.ad;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.apache.commons.lang.StringUtils;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpAdGift;
import com.pchome.akbpfp.db.pojo.PfpAdGroup;
import com.pchome.enumerate.utils.EnumStatus;


public class PfpAdGroupDAO extends BaseDAO<PfpAdGroup,String> implements IPfpAdGroupDAO{
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@SuppressWarnings("unchecked")
	public List<PfpAdGroup> getPfpAdGroups(String adGroupSeq, String adActionSeq, String adGroupName, String adGroupSearchPrice, String adGroupChannelPrice, String adGroupStatus) throws Exception{
		StringBuffer sql = new StringBuffer("from PfpAdGroup where 1=1");
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(adGroupSeq)) {
			sql.append(" and adGroupSeq like :adGroupSeq");
			sqlParams.put("adGroupSeq", "%" + adGroupSeq.trim() + "%");
		}

		if (StringUtils.isNotEmpty(adActionSeq)) {
			sql.append(" and pfpAdAction.adActionSeq = :adActionSeq");
			sqlParams.put("adActionSeq", adActionSeq.trim());
		}

		if (StringUtils.isNotEmpty(adGroupName)) {
			sql.append(" and adGroupName like :adGroupName");
			sqlParams.put("adGroupName", "%" + adGroupName.trim() + "%");
		}

		if (StringUtils.isNotEmpty(adGroupSearchPrice)) {
			sql.append(" and adGroupSearchPrice like :adGroupSearchPrice");
			sqlParams.put("adGroupSearchPrice", "%" + adGroupSearchPrice.trim() + "%");
		}

		if (StringUtils.isNotEmpty(adGroupChannelPrice)) {
			sql.append(" and adGroupChannelPrice like :adGroupChannelPrice");
			sqlParams.put("adGroupChannelPrice", "%" + adGroupChannelPrice.trim() + "%");
		}

		if (StringUtils.isNotEmpty(adGroupStatus)) {
			sql.append(" and adGroupStatus like :adGroupStatus");
			sqlParams.put("adGroupStatus", "%" + adGroupStatus.trim() + "%");
		}
		//log.info("getPfpAdGroups.SQL = " + sql);

		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(sql.toString());
        for (String paramName:sqlParams.keySet()) {
            query.setParameter(paramName, sqlParams.get(paramName));
        }
		
        List<PfpAdGroup> pfpAdGroups = query.list();
		
		return pfpAdGroups;
	}
	
	public List<Object> findAdGroupView(final String adActionSeq, final String adType, final String adGroupName, final String startDate, final String endDate, final int page, final int pageSize, final String customerInfoId) throws Exception{
		return findAdGroupView(adActionSeq, adType, null, adGroupName, null, startDate, endDate, page, pageSize, customerInfoId);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> findAdGroupView(final String adActionSeq, final String adType, final String adGroupSeq, final String adGroupName, final String adGroupStatus, final String startDate, final String endDate, final int page, final int pageSize, final String customerInfoId) throws Exception{
		List<Object> result = (List<Object> ) getHibernateTemplate().execute(
                new HibernateCallback<List<Object> >() {
					public List<Object>  doInHibernate(Session session) throws HibernateException {
//						String sql = adGroupViewSQL(adActionSeq, adType, adGroupSeq, adGroupName, adGroupStatus, startDate, endDate, customerInfoId);
//						//log.info("findAdGroupView.hql = " + sql);
//						
//						List<Object> resultData = null;
//
//						//page=-1 取得全部不分頁用於download
//						if(page==-1){
//							resultData = session.createSQLQuery(sql).list();
//						}else{
//							resultData = session.createSQLQuery(sql)
//							.setFirstResult((page-1)*pageSize)  
//							.setMaxResults(pageSize)
//							.list();
//						}
//						//log.info(">> resultData_size  = "+resultData.size());
//                        return resultData;

						// 統計帳戶下所有廣告成本
                        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
						try {
							sqlParams = mAdGroupViewSQL(adActionSeq, adType, adGroupSeq, adGroupName, adGroupStatus, startDate, endDate, customerInfoId);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String sqlStr = sqlParams.get("sql").toString();
						//log.info(sqlStr);
					
						List<Object> resultData = null;

						Query query = session.createSQLQuery(sqlStr.toString());
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
	
	public String getCount(final String adActionSeq, final String adType, final String adGroupSeq, final String adGroupName, final String adGroupStatus, final String startDate, final String endDate, final int page, final int pageSize, final String customerInfoId) throws Exception{
		String result = (String) getHibernateTemplate().execute(
				new HibernateCallback<String>() {
					public String doInHibernate(Session session) throws HibernateException {
//						String sql = adGroupViewSQL(adActionSeq, adType, adGroupSeq, adGroupName, adGroupStatus, startDate, endDate, customerInfoId);
//						
//						String resultData = Integer.toString(session.createSQLQuery(sql).list().size());
//						//log.info(">> resultData_size  = "+ resultData);
//                        return resultData;

                        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
						try {
							sqlParams = mAdGroupViewSQL(adActionSeq, adType, adGroupSeq, adGroupName, adGroupStatus, startDate, endDate, customerInfoId);
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
	
//	private String adGroupViewSQL(String adActionSeq, String adType, String adGroupSeq, String adGroupName, String adGroupStatus, String startDate, String endDate, String customerInfoId){
//		// 統計帳戶下所有廣告成本
//		StringBuffer sqlStr = new StringBuffer();
//		sqlStr.append("select");
//		sqlStr.append("	 paa.ad_action_seq,");
//		sqlStr.append("	 paa.ad_action_name,");
//		sqlStr.append("	 pag.ad_group_seq,");
//		sqlStr.append("	 pag.ad_group_name,");
//		sqlStr.append("	 pag.ad_group_search_price,");
//		sqlStr.append("	 pag.ad_group_channel_price,");
//		sqlStr.append("	 pag.ad_group_status,");
//		sqlStr.append("	 pa.ad_seq,");
//		sqlStr.append("	 papc.ad_pv,");
//		sqlStr.append("	 papc.ad_clk,");
//		sqlStr.append("	 papc.ad_pv_price,");
//		sqlStr.append("	 papc.ad_clk_price");
//		sqlStr.append(" from");
//		sqlStr.append("	  pfp_ad_action as paa,");
//		sqlStr.append("	  pfp_ad_group as pag");
//		sqlStr.append("	  left outer join pfp_ad as pa on pag.ad_group_seq = pa.ad_group_seq");
//		sqlStr.append("	  left outer join ");
//		sqlStr.append("	       (SELECT ad_seq, customer_info_id, sum(ad_pv) as ad_pv, sum(ad_clk) as ad_clk, sum(ad_pv_price) as ad_pv_price, sum(ad_clk_price) as ad_clk_price FROM pfp_ad_pvclk group by ad_seq, customer_info_id) as papc on pa.ad_seq = papc.ad_seq");
//		sqlStr.append(" where paa.ad_action_seq is not null");
//		sqlStr.append("   and pag.ad_action_seq = paa.ad_action_seq");
//		sqlStr.append("   and paa.ad_action_seq = '" + adActionSeq + "'");
//		if(StringUtils.isNotEmpty(adGroupSeq)) {
//			sqlStr.append(" and pag.ad_group_seq = " + adGroupSeq);
//		}
//		if(StringUtils.isNotEmpty(adGroupStatus)) {
//			sqlStr.append(" and pag.ad_group_status = " + adGroupStatus);
//		}
//		if(StringUtils.isNotEmpty(adGroupName)) {
//			sqlStr.append(" and pag.ad_group_name like '%" + adGroupName + "%'");
//		}
//		if(StringUtils.isNotEmpty(adType)) {
//			sqlStr.append(" and paa.ad_type = " + adType);
//		}
//		if(StringUtils.isNotEmpty(customerInfoId)) {
//			sqlStr.append(" and paa.customer_info_id = '" + customerInfoId +"'");
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
//		sqlStr.append(" group by paa.ad_action_seq, paa.customer_info_id, pag.ad_group_seq");
//		//log.info("adGroupViewSQL.sqlStr = " + sqlStr);
//	
//		return sqlStr.toString();
//	}
	
	private HashMap<String, Object> mAdGroupViewSQL(String adActionSeq, String adType, String adGroupSeq, String adGroupName, String adGroupStatus, String startDate, String endDate, String customerInfoId) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		// 統計帳戶下所有廣告成本
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select");
		sqlStr.append("	 paa.ad_action_seq,");
		sqlStr.append("	 paa.ad_action_name,");
		sqlStr.append("	 pag.ad_group_seq,");
		sqlStr.append("	 pag.ad_group_name,");
		sqlStr.append("	 pag.ad_group_search_price,");
		sqlStr.append("	 pag.ad_group_channel_price,");
		sqlStr.append("	 pag.ad_group_status,");
		sqlStr.append("	 pa.ad_seq,");
		sqlStr.append("	 papc.ad_pv,");
		sqlStr.append("	 papc.ad_clk,");
		sqlStr.append("	 papc.ad_pv_price,");
		sqlStr.append("	 papc.ad_clk_price");
		sqlStr.append(" from");
		sqlStr.append("	  pfp_ad_action as paa,");
		sqlStr.append("	  pfp_ad_group as pag");
		sqlStr.append("	  left outer join pfp_ad as pa on pag.ad_group_seq = pa.ad_group_seq");
		sqlStr.append("	  left outer join ");
		sqlStr.append("	       (SELECT ad_seq, customer_info_id, sum(ad_pv) as ad_pv, sum(ad_clk) as ad_clk, sum(ad_pv_price) as ad_pv_price, sum(ad_clk_price) as ad_clk_price FROM pfp_ad_pvclk group by ad_seq, customer_info_id) as papc on pa.ad_seq = papc.ad_seq");
		sqlStr.append(" where paa.ad_action_seq is not null");
		sqlStr.append("   and pag.ad_action_seq = paa.ad_action_seq");
		sqlStr.append("   and paa.ad_action_seq = :adActionSeq");
		sqlParams.put("adActionSeq", adActionSeq);
		if(StringUtils.isNotEmpty(adGroupSeq)) {
			sqlStr.append(" and pag.ad_group_seq = :adGroupSeq");
			sqlParams.put("adGroupSeq", adGroupSeq);
		}
		if(StringUtils.isNotEmpty(adGroupStatus)) {
			sqlStr.append(" and pag.ad_group_status = :adGroupStatus");
			sqlParams.put("adGroupStatus", adGroupStatus);
		}
		if(StringUtils.isNotEmpty(adGroupName)) {
			sqlStr.append(" and pag.ad_group_name like :adGroupName");
			sqlParams.put("adGroupName", "%" + adGroupName + "%");
		}
		if(StringUtils.isNotEmpty(adType)) {
			sqlStr.append(" and paa.ad_type = :adType");
			sqlParams.put("adType", adType);
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
		sqlStr.append(" group by paa.ad_action_seq, paa.customer_info_id, pag.ad_group_seq");
		//log.info("adGroupViewSQL.sqlStr = " + sqlStr);
		sqlParams.put("sql", sqlStr);
	
		return sqlParams;
	}

	@SuppressWarnings("unchecked")
	public boolean chkAdGroupNameByAdActionSeq(String adGroupName, String adGroupSeq, String adActionSeq) throws Exception {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		String hql = "from PfpAdGroup where pfpAdAction.adActionSeq = :adActionSeq and adGroupName = :adGroupName";
		sqlParams.put("adActionSeq", adActionSeq);
		sqlParams.put("adGroupName", adGroupName);
		if(StringUtils.isNotEmpty(adGroupSeq)) {
			hql += " and adGroupSeq <> :adGroupSeq";
			sqlParams.put("adGroupSeq", adGroupSeq);
		}
		
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
        for (String paramName:sqlParams.keySet()) {
        	if(!paramName.equals("sql")) {
        		query.setParameter(paramName, sqlParams.get(paramName));
        	}
        }

		//log.info("chkAdGroupNameByAdActionSeq.hql = " + hql);
		//List<PfpAdAction> list = super.getHibernateTemplate().find(hql);
		List<PfpAdAction> list = query.list();

		
		if (list!=null && list.size()>0) {
			log.info("list.size() = " + list.size());
			return true;
		} else {
			log.info("chkAdGroupNameByAdActionSeq false");
			return false;
		}
	}

	/**
	 * 依傳入的 seq 列表，查詢廣告活動資料
	 * @param seqList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getPfpAdGroupBySeqList(List<String> seqList) throws Exception {
		String hql = "from PfpAdGroup where adGroupSeq in (:adGroupSeq) ";
		// 將條件資料設定給 Query，準備 query
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query q = session.createQuery(hql.toString());
		q.setParameterList("adGroupSeq", seqList);

		HashMap<String, Object> adActionList = new HashMap<String, Object>();
		List<PfpAdGroup> pfpAdGroups = q.list();
		for(PfpAdGroup pfpAdGroup:pfpAdGroups) {
			adActionList.put(pfpAdGroup.getAdGroupSeq(), pfpAdGroup);
		}
		return adActionList;
	}

	@SuppressWarnings("unchecked")
	public PfpAdGroup getPfpAdGroupBySeq(String adGroupSeq) throws Exception {
		Object[] obj = new Object[]{adGroupSeq};
		List<PfpAdGroup> list = (List<PfpAdGroup>) super.getHibernateTemplate().find("from PfpAdGroup where adGroupSeq = ?", obj);
		if (list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	public void saveOrUpdatePfpAdGroup(PfpAdGroup pfpAdGroup) throws Exception{
		super.getHibernateTemplate().saveOrUpdate(pfpAdGroup);
	}

	public void insertPfpAdGroup(PfpAdGroup pfpAdGroup) throws Exception {
		this.saveOrUpdate(pfpAdGroup);
	}

	public void updatePfpAdGroup(PfpAdGroup pfpAdGroup) throws Exception {
		this.update(pfpAdGroup);
	}

	public void updatePfpAdGroupStatus(String pfpAdGroupStatus, String adGroupSeq) throws Exception {
		final StringBuffer sql = new StringBuffer()
		.append("UPDATE pfp_ad_group set ad_group_status = :pfpAdGroupStatus where ad_group_seq = :adGroupSeq");
		//log.info("updatePfpAdGroupStatus.sql = " + sql);

        Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
        session.createSQLQuery(sql.toString()).setString("pfpAdGroupStatus", pfpAdGroupStatus).setString("adGroupSeq", adGroupSeq).executeUpdate();
        session.flush();
	}
	
	public void saveOrUpdateWithCommit(PfpAdGroup adGroup) throws Exception{
		super.getHibernateTemplate().getSessionFactory().getCurrentSession().saveOrUpdate(adGroup);
//		super.getHibernateTemplate().getSessionFactory().getCurrentSession().beginTransaction().commit();
	}

	/**
	 * (舊版)查詢廣告分類資料，由 pfp_ad_pvclks 統計資料
	 * @param customerInfoId
	 * @param adActionSeq
	 * @param keyword
	 * @param adType
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return List<Object>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAdGroupPvclk(final String customerInfoId, final String adActionSeq, final String keyword, final int adType, final Date startDate, final Date endDate, final int page, final int pageSize) throws Exception{
		List<Object> result = (List<Object> ) getHibernateTemplate().execute(
				 
                new HibernateCallback<List<Object> >() {
                	
					public List<Object>  doInHibernate(Session session) throws HibernateException {
						
						StringBuffer hql = new StringBuffer();
						
						hql.append(" select pag.pfpAdAction.adActionSeq, ");
						hql.append("  		pag.pfpAdAction.adActionName, ");
						hql.append("  		pag.pfpAdAction.adActionMax, ");
						hql.append("  		pag.adGroupSeq, ");
						hql.append("  		pag.adGroupName, ");
						hql.append("  		pag.adGroupStatus, ");
						hql.append("  		pag.adGroupSearchPriceType, ");
						hql.append("  		pag.adGroupSearchPrice, ");
						hql.append("  		pag.adGroupChannelPrice, ");
						hql.append("  		COALESCE(sum(papc.adPv),0), ");
						hql.append("  		COALESCE(sum(papc.adClk),0), ");
						hql.append("  		COALESCE(sum(papc.adClkPrice),0), ");
						hql.append("		COALESCE(sum(papc.adInvalidClk),0), ");
						hql.append("		COALESCE(sum(papc.adInvalidClkPrice),0) ");
						hql.append(" from PfpAdGroup as pag ");
						hql.append(" 		left join pag.pfpAds paa  ");
						hql.append(" 		left join paa.pfpAdPvclks papc ");
						hql.append(" 			with (papc.adPvclkDate >= :startDate ");
						hql.append(" 					and papc.adPvclkDate <= :endDate ");
						
						if(adType > 0){
							hql.append(" 				and papc.adType = :adType) ");
						}else{
							hql.append(" 				and papc.adType != :adType) ");
						}
						
						hql.append(" where pag.pfpAdAction.pfpCustomerInfo.customerInfoId = :customerInfoId ");
						hql.append(" and pag.adGroupStatus != :status ");
						hql.append(" and pag.pfpAdAction.adActionSeq = :adActionSeq ");
						hql.append(" and pag.adGroupName like :keyword ");						
						hql.append(" group by pag.adGroupSeq ");
						hql.append(" order by pag.adGroupCreateTime desc ");
						
						//log.info("getAdGroupPvclk.hql = " + hql);
						
						Query q = session.createQuery(hql.toString())
									.setDate("startDate", startDate)
									.setDate("endDate", endDate)
									.setInteger("adType", adType)
									.setString("customerInfoId", customerInfoId)
									.setInteger("status", EnumStatus.Close.getStatusId())
									.setString("adActionSeq", adActionSeq)
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
	 * (新版)查詢廣告分類資料，由 pfp_ad_group_report 統計資料
	 * @param customerInfoId
	 * @param adActionSeq
	 * @param keyword
	 * @param adType
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return List<Object>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAdGroupReport(final String customerInfoId, final String adActionSeq, final String keyword, final int adType, final Date startDate, final Date endDate, final int page, final int pageSize) throws Exception{
		List<Object> result = (List<Object> ) getHibernateTemplate().execute(
                new HibernateCallback<List<Object> >() {
					public List<Object>  doInHibernate(Session session) throws HibernateException {
						StringBuffer hql = new StringBuffer();
						hql.append(" select pa.ad_action_seq, ");
						hql.append("  		pa.ad_action_name, ");
						hql.append("  		pa.ad_action_max, ");
						hql.append("  		pag.ad_group_seq, ");
						hql.append("  		pag.ad_group_name, ");
						hql.append("  		pag.ad_group_status, ");
						hql.append("  		pag.ad_group_search_price_type, ");
						hql.append("  		pag.ad_group_search_price, ");
						hql.append("  		pag.ad_group_channel_price, ");
						hql.append("  		COALESCE(sum(papc.ad_pv),0), ");
						hql.append("  		COALESCE(sum(papc.ad_clk),0), ");
						hql.append("  		COALESCE(sum(papc.ad_clk_price),0), ");
						hql.append("		COALESCE(sum(papc.ad_invalid_clk),0), ");
						hql.append("		COALESCE(sum(papc.ad_invalid_clk_price),0) ");
						hql.append(" from pfp_ad_action pa, pfp_ad_group pag ");
						hql.append(" 		left join pfp_ad_group_report papc ");
						hql.append(" 			on (pag.ad_group_seq = papc.ad_group_seq ");
						hql.append(" 			and papc.ad_pvclk_date >= :startDate ");
						hql.append(" 					and papc.ad_pvclk_date <= :endDate ");
						
						if(adType > 0){
							hql.append(" 				and papc.ad_type = :adType) ");
						}else{
							hql.append(" 				and papc.ad_type != :adType) ");
						}
						
						hql.append(" where pa.ad_action_seq = pag.ad_action_seq ");
						hql.append(" and pa.customer_info_id = :customerInfoId ");
						hql.append(" and pag.ad_group_status != :status ");
						hql.append(" and pag.ad_action_seq = :adActionSeq ");
						hql.append(" and pag.ad_group_name like :keyword ");						
						hql.append(" group by pag.ad_group_seq ");
						hql.append(" order by pag.ad_group_create_time desc ");
						
						//log.info("getAdGroupReport.hql = " + hql);

						Query q = session.createSQLQuery(hql.toString())
									.setDate("startDate", startDate)
									.setDate("endDate", endDate)
									.setInteger("adType", adType)
									.setString("customerInfoId", customerInfoId)
									.setInteger("status", EnumStatus.Close.getStatusId())
									.setString("adActionSeq", adActionSeq)
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
	 * 查詢廣告分類資料(檢視廣告使用)
	 * @param customerInfoId
	 * @param adActionSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PfpAdGroup> getPfpAdGroupForView(String customerInfoId, String adActionSeq, String keyword, int page, int pageSize) throws Exception{
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();

		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpAdGroup ");
		hql.append(" where adGroupStatus != :status");
		hql.append(" and pfpAdAction.adActionSeq = :adActionSeq ");
		if (StringUtils.isNotEmpty(customerInfoId)) {
			hql.append(" and pfpAdAction.pfpCustomerInfo.customerInfoId = :customerInfoId");
		}

		if (StringUtils.isNotEmpty(keyword)) {
			hql.append(" and adGroupName like :keyword ");						
		}
		hql.append(" order by adGroupCreateTime desc ");
		//log.info("getPfpAdGroupForView.sql = " + hql);

		Query q = session.createQuery(hql.toString())
					.setString("adActionSeq", adActionSeq)
					.setInteger("status", EnumStatus.Close.getStatusId());
		if (StringUtils.isNotEmpty(customerInfoId)) {
			q.setString("customerInfoId", customerInfoId);
		}

		if (StringUtils.isNotEmpty(keyword)) {
			q.setString("keyword", "%"+keyword+"%");
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
	 * 查詢廣告分類成效(檢視廣告使用)
	 * @param customerInfoId
	 * @param adActionSeq
	 * @param adGroupSeq
	 * @param adType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAdGroupReportByAGS(String customerInfoId, String adActionSeq, String adGroupSeq, int adType, Date startDate, Date endDate) throws Exception{
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COALESCE(sum(adPv),0), ");
		sql.append(" COALESCE(sum(adClk),0), ");
		sql.append(" COALESCE(sum(adClkPrice),0), ");
		sql.append(" COALESCE(sum(adInvalidClk),0), ");
		sql.append(" COALESCE(sum(adInvalidClkPrice),0) ");
		sql.append(" from PfpAdGroupReport where 1=1 ");

		if (StringUtils.isNotEmpty(customerInfoId)) {
			sql.append(" and customerInfoId = :customerInfoId");
		}

		if (StringUtils.isNotEmpty(adActionSeq)) {
			sql.append(" and adActionSeq = :adActionSeq");
		}

		if (StringUtils.isNotEmpty(adGroupSeq)) {
			sql.append(" and adGroupSeq = :adGroupSeq");
		}

		if(adType > 0){
			sql.append(" and adType = :adType ");
		}else{
			sql.append(" and adType != :adType ");
		}

		if (startDate != null) {
			sql.append(" and adPvclkDate  >= :startDate");
		}

		if (endDate != null) {
			sql.append(" and adPvclkDate <= :endDate");
		}
		System.out.println(sql);
		System.out.println("customerInfoId = " + customerInfoId);
		System.out.println("adActionSeq = " + adActionSeq);
		System.out.println("adGroupSeq = " + adGroupSeq);
		System.out.println("adType = " + adType);
		System.out.println("startDate = " + startDate);
		System.out.println("endDate = " + endDate);

		Query q = session.createQuery(sql.toString())
					.setInteger("adType", adType);
		if (StringUtils.isNotEmpty(customerInfoId)) {
			q.setString("customerInfoId", customerInfoId);
		}

		if (StringUtils.isNotEmpty(adActionSeq)) {
			q.setString("adActionSeq", adActionSeq);
		}

		if (StringUtils.isNotEmpty(adGroupSeq)) {
			q.setString("adGroupSeq", adGroupSeq);
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

	// 2014-04-21 修改的版本，此版處理是將 DB 連線數量壓到最低來處理，不然速度會因為與 DB 連線數量過多而變得非常慢
	/**
	 * 查詢廣告分類資料(檢視廣告使用)
	 * @param customerInfoId
	 * @param adActionSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public long getPfpAdGroupCount(String customerInfoId, String adActionSeq, String keyword, int page, int pageSize) throws Exception{
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();

		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();

		hql.append("select count(adGroupSeq) from PfpAdGroup ");
		hql.append(" where adGroupStatus != :status");
		hql.append(" and pfpAdAction.adActionSeq = :adActionSeq ");
		if (StringUtils.isNotEmpty(customerInfoId)) {
			hql.append(" and pfpAdAction.pfpCustomerInfo.customerInfoId = :customerInfoId");
			sqlParams.put("customerInfoId", customerInfoId.trim());
		}

		if (StringUtils.isNotEmpty(keyword)) {
			hql.append(" and adGroupName like :keyword ");						
			sqlParams.put("keyword", keyword.trim());
		}
		hql.append(" order by adGroupCreateTime desc ");
		//log.info("getPfpAdKeywordForView.sql = " + hql);

		// 將條件資料設定給 Query，準備 query
		Query q = session.createQuery(hql.toString())
						.setString("adActionSeq", adActionSeq)
						.setInteger("status", EnumStatus.Close.getStatusId());
        for (String paramName:sqlParams.keySet()) {
        	if(!paramName.equals("sql")) {
				q.setParameter(paramName, sqlParams.get(paramName));
        	}
        }
//		System.out.println("customerInfoId = " + customerInfoId);
//		System.out.println("adGroupSeq = " + adGroupSeq);
//		System.out.println("keyword = " + keyword);
//		System.out.println("page = " + page);
//		System.out.println("pageSize = " + pageSize);
//		System.out.println("hql = " + hql);

		//page=-1 取得全部不分頁用於download
		if(page!=-1){
			q.setFirstResult((page-1)*pageSize) 
			 .setMaxResults(pageSize);
		}      

		Long count = 0L;
		List<Object> resultData = q.list();
		if(resultData != null) {
			count = Long.parseLong(resultData.get(0).toString());
		}

		return count;
	}

	/**
	 * 查詢廣告分類成效(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param adKeywordSeq
	 * @param adKeywordType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getAdGroupReportByAdGroupsList(String customerInfoId, String adActionSeq, List<String> adGroupSeqList, int adType, Date startDate, Date endDate) throws Exception{
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("select adGroupSeq,");
		hql.append(" COALESCE(sum(adPv),0), ");
		hql.append(" COALESCE(sum((case when adClkPriceType = 'CPC' then (adClk - adInvalidClk) else adView end)),0), ");
		hql.append(" COALESCE(sum(adClkPrice),0), ");
		hql.append(" COALESCE(sum(adInvalidClk),0), ");
		hql.append(" COALESCE(sum(adInvalidClkPrice),0), ");
		hql.append(" COALESCE(adType,0) ");
		hql.append(" from PfpAdGroupReport where 1=1 ");

		if (StringUtils.isNotEmpty(customerInfoId)) {
			hql.append(" and customerInfoId = :customerInfoId");
			sqlParams.put("customerInfoId", customerInfoId.trim());
		}

		if (StringUtils.isNotEmpty(adActionSeq)) {
			hql.append(" and adActionSeq = :adActionSeq");
			sqlParams.put("adActionSeq", adActionSeq.trim());
		}

		if (adGroupSeqList != null) {
			hql.append(" and adGroupSeq in (:adGroupSeq)");
			sqlParams.put("adGroupSeq", adGroupSeqList);
		}

		if(adType > 0){
			hql.append(" and adType = :adType ");
			sqlParams.put("adType", adType);
		}else{
			hql.append(" and adType != :adType ");
			sqlParams.put("adType", adType);
		}

		if (startDate != null) {
			hql.append(" and adPvclkDate >= :startDate");
			sqlParams.put("startDate", startDate);
		}

		if (endDate != null) {
			hql.append(" and adPvclkDate <= :endDate");
			sqlParams.put("endDate", endDate);
		}
		hql.append(" group by adGroupSeq");
		log.info("hql  = "+ hql.toString());

		// 將條件資料設定給 Query，準備 query
		Query q = session.createQuery(hql.toString());
        for (String paramName:sqlParams.keySet()) {
        	if(!paramName.equals("sql")) {
				if(paramName.equals("adGroupSeq")) {
					q.setParameterList("adGroupSeq", adGroupSeqList);
				} else {
					q.setParameter(paramName, sqlParams.get(paramName));
				}
        	}
        }
		//log.info(" resultData size  = "+ q.list().size());

        // 將得到的廣告分類成效結果，設定成 Map, 以方便用 adGroupSeq 抓取資料
		HashMap<String, Object> adGroupSum = new HashMap<String, Object>();
		List<Object> pfpAdGroupReports = q.list();
		for(Object object:pfpAdGroupReports) {
			Object[] ob = (Object[])object;
			adGroupSum.put(ob[0].toString(), object);
		}
		// 顯示廣告成效內容
//		for(String adKeywordSeq:adKeywordSum.keySet()) {
//			Object[] obj = (Object[])adKeywordSum.get(adKeywordSeq);
//			System.out.println(adKeywordSeq + " => " + obj[0] + ";" + obj[1] + ";" + obj[2]);
//		}

		return adGroupSum;
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpAdGroup> validAdGroup(String adActionSeq) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpAdGroup ");
		hql.append(" where pfpAdAction.adActionSeq = ? ");
		hql.append(" and adGroupStatus != ? ");

		return (List<PfpAdGroup>) super.getHibernateTemplate().find(hql.toString(),adActionSeq,EnumStatus.Close.getStatusId());
	}

	
	@SuppressWarnings("unchecked")
	public List<PfpAdGroup> getAdGroups(String adActionSeq, String orderBy, boolean desc) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpAdGroup ");
		hql.append(" where pfpAdAction.adActionSeq = ? ");
		hql.append(" and adGroupStatus not in (8,9,10)");
		hql.append(" and pfpAdAction.adActionEndDate >= CURDATE()");		// 走期的結束日期要大於今天
		if(StringUtils.isBlank(orderBy)) {
			hql.append(" order by " + orderBy + (desc?" desc":""));
		}
		
		return (List<PfpAdGroup>) super.getHibernateTemplate().find(hql.toString(),adActionSeq);
	}
}
