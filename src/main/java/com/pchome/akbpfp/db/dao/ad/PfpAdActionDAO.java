package com.pchome.akbpfp.db.dao.ad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdAction;
import com.pchome.akbpfp.db.pojo.PfpCustomerInfo;
import com.pchome.enumerate.utils.EnumStatus;

public class PfpAdActionDAO extends BaseDAO<PfpAdAction,String> implements IPfpAdActionDAO{
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@SuppressWarnings("unchecked")
	public List<PfpAdAction> findPfpAdAction(String customerInfoId) throws Exception{
		String sql = "from PfpAdAction where pfpCustomerInfo.customerInfoId = ? ";
		return (List<PfpAdAction>) super.getHibernateTemplate().find(sql, customerInfoId);
	}

	@SuppressWarnings("unchecked")
	public List<PfpAdAction> getPfpAdActions(String adActionSeq, String adActionName, String adType, String adActionStartDate, String adActionEndDate, String adActionMax, String adActionStatus, String userId, PfpCustomerInfo pfpCustomerInfo) throws Exception{
		StringBuffer sql = new StringBuffer("from PfpAdAction  where 1=1");
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(adActionSeq)) {
			sql.append(" and adActionSeq = :adActionSeq");
			sqlParams.put("adActionSeq", adActionSeq.trim());
		}

		if (StringUtils.isNotEmpty(adActionName)) {
			sql.append(" and adActionName like :adActionName");
			sqlParams.put("adActionName", "'%" + adActionName + "%'");
		}

		if (StringUtils.isNotEmpty(adType) && !adType.equals("0")) {
			sql.append(" and adType = :adType");
			sqlParams.put("adType", adType.trim());
		}

		if (StringUtils.isNotEmpty(adActionStartDate)) {
			sql.append(" and adActionStartDate  >= :adActionStartDate");
			sqlParams.put("adActionStartDate", sdf.parse(adActionStartDate.trim()));
		}

		if (StringUtils.isNotEmpty(adActionEndDate)) {
			sql.append(" and adActionEndDate <= :adActionEndDate");
			sqlParams.put("adActionEndDate", sdf.parse(adActionEndDate.trim()));
		}

		if (StringUtils.isNotEmpty(adActionMax)) {
			sql.append(" and adActionMax = :adActionMax");
			sqlParams.put("adActionMax", adActionMax.trim());
		}

		if (StringUtils.isNotEmpty(adActionStatus)) {
			sql.append(" and adActionStatus = :adActionStatus");
			sqlParams.put("adActionStatus", adActionStatus.trim());
		}

		if (StringUtils.isNotEmpty(userId)) {
			sql.append(" and userId = :userId");
			sqlParams.put("userId", userId.trim());
		}

		if (pfpCustomerInfo != null) {
			sql.append(" and pfpCustomerInfo.customerInfoId = :customerInfoId");
			sqlParams.put("customerInfoId", pfpCustomerInfo.getCustomerInfoId());
		}
		log.info("getPfpAdActions.sql = " + sql);

		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(sql.toString());
        for (String paramName:sqlParams.keySet()) {
            query.setParameter(paramName, sqlParams.get(paramName));
        }
		
        List<PfpAdAction> pfpAdActions = query.list();
        //log.info("pfpAdActions.size() = " + pfpAdActions.size()); 
        
		return pfpAdActions;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> findAdActionView(final String actionName, final String startDate, final String endDate, final String adType, final int page, final int pageSize, final String customerInfoId) throws Exception{
		List<Object> result = (List<Object> ) getHibernateTemplate().execute(
				new HibernateCallback<List<Object> >() {
					public List<Object>  doInHibernate(Session session) throws HibernateException {


						// 統計帳戶下所有廣告成本
                        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
						try {
							sqlParams = mAdActionViewSQL(actionName, startDate, endDate, adType, customerInfoId);
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
	
	public String getCount(final String actionName, final String startDate, final String endDate, final String adType, final int page, final int pageSize, final String customerInfoId) throws Exception{
		String result = (String) getHibernateTemplate().execute(
				new HibernateCallback<String>() {
					public String doInHibernate(Session session) throws HibernateException {
                        HashMap<String, Object> sqlParams = new HashMap<String, Object>();
						try {
							sqlParams = mAdActionViewSQL(actionName, startDate, endDate, adType, customerInfoId);
						} catch (ParseException e) {
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
//	
//	private String adActionViewSQL(String actionName, String startDate, String endDate, String adType, String customerInfoId){
//		// 統計帳戶下所有廣告成本
//		StringBuffer sqlStr = new StringBuffer();
//		sqlStr.append("select paa.ad_action_seq,");
//		sqlStr.append("       paa.ad_action_name,");
//		sqlStr.append("       paa.ad_action_desc,");
//		sqlStr.append("       paa.ad_type,");
//		sqlStr.append("       paa.ad_action_max,");
//		sqlStr.append("       paa.ad_action_status,");
//		sqlStr.append("       pag.ad_group_seq,");
//		sqlStr.append("       pa.ad_seq,");
//		sqlStr.append("       papc.ad_pv,");
//		sqlStr.append("       papc.ad_clk,");
//		sqlStr.append("       papc.ad_pv_price,");
//		sqlStr.append("       papc.ad_clk_price");
//		sqlStr.append(" from pfp_ad_action as paa");
//		sqlStr.append("      left outer join pfp_ad_group as pag on paa.ad_action_seq = pag.ad_action_seq");
//		sqlStr.append("      left outer join pfp_ad as pa on pag.ad_group_seq = pa.ad_group_seq");
//		sqlStr.append("      left outer join ");
//		sqlStr.append("          (SELECT ad_seq, customer_info_id, sum(ad_pv) as ad_pv, sum(ad_clk) as ad_clk, sum(ad_pv_price) as ad_pv_price, sum(ad_clk_price) as ad_clk_price FROM pfp_ad_pvclk group by ad_seq, customer_info_id) as papc on pa.ad_seq = papc.ad_seq");
//		sqlStr.append(" where paa.ad_action_seq is not null");
//		if(StringUtils.isNotEmpty(actionName)) {
//			sqlStr.append(" and paa.ad_action_name like '%" + actionName + "%'");
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
//		sqlStr.append(" group by paa.ad_action_seq, paa.customer_info_id");
//		log.info("adActionViewSQL.sqlStr = " + sqlStr);
//	
//		return sqlStr.toString();
//	}
	
	private HashMap<String, Object> mAdActionViewSQL(String actionName, String startDate, String endDate, String adType, String customerInfoId) throws ParseException{
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		// 統計帳戶下所有廣告成本
		StringBuffer sqlStr = new StringBuffer();
		sqlStr.append("select paa.ad_action_seq,");
		sqlStr.append("       paa.ad_action_name,");
		sqlStr.append("       paa.ad_action_desc,");
		sqlStr.append("       paa.ad_type,");
		sqlStr.append("       paa.ad_action_max,");
		sqlStr.append("       paa.ad_action_status,");
		sqlStr.append("       pag.ad_group_seq,");
		sqlStr.append("       pa.ad_seq,");
		sqlStr.append("       papc.ad_pv,");
		sqlStr.append("       papc.ad_clk,");
		sqlStr.append("       papc.ad_pv_price,");
		sqlStr.append("       papc.ad_clk_price");
		sqlStr.append(" from pfp_ad_action as paa");
		sqlStr.append("      left outer join pfp_ad_group as pag on paa.ad_action_seq = pag.ad_action_seq");
		sqlStr.append("      left outer join pfp_ad as pa on pag.ad_group_seq = pa.ad_group_seq");
		sqlStr.append("      left outer join ");
		sqlStr.append("          (SELECT ad_seq, customer_info_id, sum(ad_pv) as ad_pv, sum(ad_clk) as ad_clk, sum(ad_pv_price) as ad_pv_price, sum(ad_clk_price) as ad_clk_price FROM pfp_ad_pvclk group by ad_seq, customer_info_id) as papc on pa.ad_seq = papc.ad_seq");
		sqlStr.append(" where paa.ad_action_seq is not null");
		if(StringUtils.isNotEmpty(actionName)) {
			sqlStr.append(" and paa.ad_action_name like :actionName");
			sqlParams.put("actionName", "%" + actionName + "%");
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
		sqlStr.append(" group by paa.ad_action_seq, paa.customer_info_id");
		sqlParams.put("sql", sqlStr);
		log.info("adActionViewSQL.sqlStr = " + sqlStr);
	
		return sqlParams;
	}

	@SuppressWarnings("unchecked")
	public boolean chkAdActionNameByCustomerInfoId(String adActionName, String adActionSeq, String customerInfoId) throws Exception {
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		String hql = "from PfpAdAction where pfpCustomerInfo.customerInfoId = :customerInfoId and adActionName = :adActionName";
		sqlParams.put("customerInfoId", customerInfoId);
		sqlParams.put("adActionName", adActionName);
		if(StringUtils.isNotEmpty(adActionSeq)) {
			hql += " and adActionSeq <> :adActionSeq";
			sqlParams.put("adActionSeq", adActionSeq);
		}
		Query query = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql);
        for (String paramName:sqlParams.keySet()) {
        	if(!paramName.equals("sql")) {
        		query.setParameter(paramName, sqlParams.get(paramName));
        	}
        }

        List<PfpAdAction> list = query.list();
		
		if (list!=null && list.size()>0) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public PfpAdAction getAdActionByAdActionName(String adActionName, String customerInfoId) throws Exception {
		String hql = "from PfpAdAction where pfpCustomerInfo.customerInfoId = :customerInfoId and adActionName = :adActionName";
		List<PfpAdAction> list = this.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql)
										.setString("customerInfoId", customerInfoId)
										.setString("adActionName", adActionName).list();
		
		if (list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public PfpAdAction getPfpAdActionBySeq(String adActionSeq) throws Exception {
		String hql = "from PfpAdAction where adActionSeq = ? ";
		Object[] obj = new Object[]{adActionSeq};
		List<PfpAdAction> list = (List<PfpAdAction>) super.getHibernateTemplate().find(hql, obj);
		
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
	public HashMap<String, Object> getPfpAdActionBySeqList(List<String> seqList) throws Exception {
		String hql = "from PfpAdAction where adActionSeq in (:adActionSeq) ";
		// 將條件資料設定給 Query，準備 query
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query q = session.createQuery(hql.toString());
		q.setParameterList("adActionSeq", seqList);

		HashMap<String, Object> adActionList = new HashMap<String, Object>();
		List<PfpAdAction> pfpAdActions = q.list();
		for(PfpAdAction pfpAdAction:pfpAdActions) {
			adActionList.put(pfpAdAction.getAdActionSeq(), pfpAdAction);
		}
		return adActionList;
	}

	public void saveOrUpdatePfpAdAction(PfpAdAction pfpAdAction) throws Exception{
		super.getHibernateTemplate().saveOrUpdate(pfpAdAction);
	}

	public void insertPfpAdAction(PfpAdAction pfpAdAction) throws Exception {
		this.saveOrUpdate(pfpAdAction);
	}

	public void updatePfpAdAction(PfpAdAction pfpAdAction) throws Exception {
		this.update(pfpAdAction);
	}

	public void updatePfpAdActionStatus(String pfpAdActionStatus, String adActionSeq) throws Exception {
		final StringBuffer sql = new StringBuffer()
		.append("UPDATE pfp_ad_action set ad_action_status = :pfpAdActionStatus where ad_action_seq = :adActionSeq");
		//log.info("updatePfpAdActionStatus.sql = " + sql);

        Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
        session.createSQLQuery(sql.toString()).setString("pfpAdActionStatus", pfpAdActionStatus).setString("adActionSeq", adActionSeq).executeUpdate();
        session.flush();
	}

	public void updatePfpAdActionMax(String adActionSeq, String pfpAdActionMax) throws Exception {
		final StringBuffer sql = new StringBuffer()
		.append("UPDATE pfp_ad_action set ad_action_max = :pfpAdActionMax where ad_action_seq = :adActionSeq");
		//log.info("updatePfpAdActionMax.sql = " + sql);

        Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
        session.createSQLQuery(sql.toString()).setString("pfpAdActionMax", pfpAdActionMax).setString("adActionSeq", adActionSeq).executeUpdate();
        session.flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpAdAction> getAdAction(String customerInfoId, Date today) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		
		hql.append(" from PfpAdAction ");
		hql.append(" where pfpCustomerInfo.customerInfoId = ? ");
		hql.append(" and adActionStatus = ? ");
		hql.append(" and adActionStartDate <= ? ");
		hql.append(" and adActionEndDate >= ? ");
		
		Object[] ob = new Object[]{customerInfoId, EnumStatus.Open.getStatusId(), today, today};
		
		return (List<PfpAdAction>) super.getHibernateTemplate().find(hql.toString(), ob);
		
	}

	/**
	 * (舊版)查詢 廣告管理>檢視廣告>廣告列表 的資料，查詢 pfp_ad_action、pfp_ad_group、pfp_ad、pfp_ad_pvclk outer join起來
	 * @param customerInfoId 客戶帳號
	 * @param keyword 關鍵字
	 * @param adType 廣告類型
	 * @param startDate 查詢開始時間
	 * @param endDate 查詢結束時間
	 * @param page 頁數
	 * @param pageSize 每頁的筆數
	 * @return List<Object> 回傳查詢的List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAdActionPvclk(final String customerInfoId, final String keyword, final int adType, final Date startDate, final Date endDate, final int page, final int pageSize) throws Exception{
		List<Object> result = (List<Object> ) getHibernateTemplate().execute(
				 
                new HibernateCallback<List<Object> >() {
                	
					public List<Object>  doInHibernate(Session session) throws HibernateException {
						StringBuffer hql = new StringBuffer();
						
						hql.append(" select pa.adActionSeq, ");
						hql.append("		pa.adActionName, ");
						hql.append("		pa.adType, ");
						hql.append("		pa.adActionStatus, ");
						hql.append("		pa.adActionMax, ");
						hql.append("		pa.adActionStartDate, ");
						hql.append("		pa.adActionEndDate, ");
						hql.append("		COALESCE(pag.adGroupSeq,''), ");
						hql.append("		COALESCE(sum(papc.adPv),0), ");
						hql.append("		COALESCE(sum(papc.adClk),0), ");
						hql.append("		COALESCE(sum(papc.adClkPrice),0), ");
						hql.append("		COALESCE(sum(papc.adInvalidClk),0), ");
						hql.append("		COALESCE(sum(papc.adInvalidClkPrice),0) ");
						hql.append(" from PfpAdAction as pa ");
						hql.append(" 		left join pa.pfpAdGroups pag ");
						hql.append(" 		left join pag.pfpAds paa  ");
						hql.append(" 		left join paa.pfpAdPvclks papc ");
						hql.append(" 			with (papc.adPvclkDate >= :startDate ");
						hql.append(" 					and papc.adPvclkDate <= :endDate ");
						
						if(adType > 0){
							hql.append(" 				and papc.adType = :adType) ");
						}else{
							hql.append(" 				and papc.adType != :adType) ");
						}

						hql.append(" where pa.pfpCustomerInfo.customerInfoId = :customerInfoId ");		
						hql.append(" and pa.adActionStatus != :status ");
						hql.append(" and pa.adActionName like :keyword ");						
						hql.append(" group by pa.adActionSeq ");
						hql.append(" order by pa.adActionCreatTime desc ");
						
						Query q = session.createQuery(hql.toString())
									.setDate("startDate", startDate)
									.setDate("endDate", endDate)
									.setInteger("adType", adType)
									.setString("customerInfoId", customerInfoId)
									.setInteger("status", EnumStatus.Close.getStatusId())
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
	 * (新版)查詢 廣告管理>檢視廣告>廣告列表 的資料，查詢 pfp_ad_action 跟 pfp_ad_action_report outer join起來
	 * @param customerInfoId 客戶帳號
	 * @param keyword 關鍵字
	 * @param adType 廣告類型
	 * @param startDate 查詢開始時間
	 * @param endDate 查詢結束時間
	 * @param page 頁數
	 * @param pageSize 每頁的筆數
	 * @return List<Object> 回傳查詢的List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAdActionReport(final String customerInfoId, final String keyword, final int adType, final Date startDate, final Date endDate, final int page, final int pageSize) throws Exception{
		List<Object> result = (List<Object> ) getHibernateTemplate().execute(
				 
                new HibernateCallback<List<Object> >() {
                	
					public List<Object>  doInHibernate(Session session) throws HibernateException {
						
						StringBuffer hql = new StringBuffer();
						
						hql.append(" select pa.ad_action_seq, ");
						hql.append("		pa.ad_action_name, ");
						hql.append("		pa.ad_type, ");
						hql.append("		pa.ad_action_status, ");
						hql.append("		pa.ad_action_max, ");
						hql.append("		pa.ad_action_start_date, ");
						hql.append("		pa.ad_action_end_date, ");
						hql.append("		'', ");
						hql.append("		COALESCE(sum(papc.ad_pv),0), ");
						hql.append("		COALESCE(sum(papc.ad_clk),0), ");
						hql.append("		COALESCE(sum(papc.ad_clk_price),0), ");
						hql.append("		COALESCE(sum(papc.ad_invalid_clk),0), ");
						hql.append("		COALESCE(sum(papc.ad_invalid_clk_price),0) ");
						hql.append(" from pfp_ad_action as pa ");
						hql.append(" 		left join pfp_ad_action_report papc ");
						hql.append(" 			on (pa.ad_action_seq = papc.ad_action_seq ");
						hql.append(" 			and papc.ad_pvclk_date >= :startDate ");
						hql.append(" 			and papc.ad_pvclk_date <= :endDate ");
						
						if(adType > 0){
							hql.append(" 				and papc.ad_type = :adType) ");
						}else{
							hql.append(" 				and papc.ad_type != :adType) ");
						}

						hql.append(" where pa.customer_info_id = :customerInfoId ");		
						hql.append(" and pa.ad_action_status != :status ");
						hql.append(" and pa.ad_action_name like :keyword ");						
						hql.append(" group by pa.ad_action_seq ");
						hql.append(" order by pa.ad_action_creat_time desc ");
						
						Query q = session.createSQLQuery(hql.toString())
									.setDate("startDate", startDate)
									.setDate("endDate", endDate)
									.setInteger("adType", adType)
									.setString("customerInfoId", customerInfoId)
									.setInteger("status", EnumStatus.Close.getStatusId())
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
	 * 查詢廣告活動資料(檢視廣告使用)
	 * @param customerInfoId
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PfpAdAction> getPfpAdActionForView(String customerInfoId, String keyword, String adType, int page, int pageSize) throws Exception{
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();

		StringBuffer sql = new StringBuffer("from PfpAdAction  where adActionStatus != :status");
		if (StringUtils.isNotEmpty(customerInfoId)) {
			sql.append(" and pfpCustomerInfo.customerInfoId = :customerInfoId");
		}

		if (StringUtils.isNotEmpty(keyword)) {
			sql.append(" and adActionName like :keyword");
		}
		
		if(StringUtils.isNotEmpty(adType)){
			sql.append(" and adType = :adType");
		}
		
		sql.append(" order by adActionSeq desc ");
		
		//log.info("getAllAdActionView.sql = " + sql);

		Query q = session.createQuery(sql.toString())
					.setInteger("status", EnumStatus.Close.getStatusId());
		if (StringUtils.isNotEmpty(customerInfoId)) {
			q.setString("customerInfoId", customerInfoId);
		}

		if (StringUtils.isNotEmpty(keyword)) {
			q.setString("keyword", "%"+keyword+"%");
		}

		if(StringUtils.isNotEmpty(adType)){
			q.setString("adType", adType);
		}
		
		//page=-1 取得全部不分頁用於download
		if(page!=-1){
			q.setFirstResult((page-1)*pageSize)  
				.setMaxResults(pageSize);
		}      
		log.info(" resultData size  = "+ q.list().size());

		return q.list();
	}

	/**
	 * 查詢廣告活動成效(檢視廣告使用)
	 * @param customerInfoId
	 * @param adActionSeq
	 * @param adType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAdActionReportByCID(String customerInfoId, String adActionSeq, int adType, Date startDate, Date endDate) throws Exception{
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COALESCE(sum(adPv),0), ");
		sql.append(" COALESCE(sum(adClk),0), ");
		sql.append(" COALESCE(sum(adClkPrice),0), ");
		sql.append(" COALESCE(sum(adInvalidClk),0), ");
		sql.append(" COALESCE(sum(adInvalidClkPrice),0) ");
		sql.append(" from PfpAdActionReport where 1=1 ");

		if (StringUtils.isNotEmpty(customerInfoId)) {
			sql.append(" and customerInfoId = :customerInfoId");
		}

		if (StringUtils.isNotEmpty(adActionSeq)) {
			sql.append(" and adActionSeq = :adActionSeq");
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

		Query q = session.createQuery(sql.toString())
					.setInteger("adType", adType);
		if (StringUtils.isNotEmpty(customerInfoId)) {
			q.setString("customerInfoId", customerInfoId);
		}

		if (StringUtils.isNotEmpty(adActionSeq)) {
			q.setString("adActionSeq", adActionSeq);
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
	 * 查詢廣告活動筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public long getPfpAdActionCount(String customerInfoId, String keyword, String adType, int page, int pageSize) throws Exception {
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();

		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("select count(adActionSeq) from PfpAdAction ");
		hql.append(" where adActionStatus != :status");
		if (StringUtils.isNotEmpty(customerInfoId)) {
			hql.append(" and pfpCustomerInfo.customerInfoId = :customerInfoId");
			sqlParams.put("customerInfoId", customerInfoId.trim());
		}

		if (StringUtils.isNotEmpty(keyword)) {
			hql.append(" and adActionName like :keyword ");						
			sqlParams.put("keyword", keyword.trim());
		}
		
		if(StringUtils.isNotEmpty(adType)){
			hql.append(" and adType = :adType ");
			sqlParams.put("adType", Integer.parseInt(adType));
		}
		
		hql.append(" order by adActionCreatTime desc ");
		//log.info("getPfpAdActionCount.sql = " + hql);

		// 將條件資料設定給 Query，準備 query
		Query q = session.createQuery(hql.toString())
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
	 * 查詢廣告關鍵字成效(檢視廣告使用)
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
	public HashMap<String, Object> getAdActionReportByAdActionsList(String customerInfoId, List<String> adActionSeqList, String adType, Date startDate, Date endDate) throws Exception{
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("select adActionSeq,");
		hql.append(" COALESCE(sum(adPv),0), ");
		hql.append(" COALESCE(sum((case when adClkPriceType ='CPC' then adClk else adView end)),0), ");
		hql.append(" COALESCE(sum(adClkPrice),0), ");
		hql.append(" COALESCE(sum(adInvalidClk),0), ");
		hql.append(" COALESCE(sum(adInvalidClkPrice),0) ");
		hql.append(" from PfpAdActionReport where 1=1 ");

		if (StringUtils.isNotEmpty(customerInfoId)) {
			hql.append(" and customerInfoId = :customerInfoId");
			sqlParams.put("customerInfoId", customerInfoId.trim());
		}

		if (adActionSeqList != null) {
			hql.append(" and adActionSeq in (:adActionSeq)");
			sqlParams.put("adActionSeq", adActionSeqList);
		}

		if(StringUtils.isNotEmpty(adType)){
			if(Integer.parseInt(adType) > 0){
				hql.append(" and adType = :adType ");
				sqlParams.put("adType", Integer.parseInt(adType));
			}else{
				hql.append(" and adType != :adType ");
				sqlParams.put("adType", Integer.parseInt(adType));
			}
		} else {
			hql.append(" and adType != 0 ");
		}

		if (startDate != null) {
			hql.append(" and adPvclkDate  >= :startDate");
			sqlParams.put("startDate", startDate);
		}

		if (endDate != null) {
			hql.append(" and adPvclkDate <= :endDate");
			sqlParams.put("endDate", endDate);
		}
		hql.append(" group by adActionSeq");
//		log.info("hql  = "+ hql.toString());

		// 將條件資料設定給 Query，準備 query
		Query query = session.createQuery(hql.toString());
        for (String paramName:sqlParams.keySet()) {
        	if(!paramName.equals("sql")) {
				if(paramName.equals("adActionSeq")) {
					query.setParameterList("adActionSeq", adActionSeqList);
				} else {
					query.setParameter(paramName, sqlParams.get(paramName));
				}
        	}
        }
		//log.info(" resultData size  = "+ q.list().size());

        // 將得到的廣告成效結果，設定成 Map, 以方便用 adKeywordSeq 抓取資料
		HashMap<String, Object> adActionSum = new HashMap<String, Object>();
		List<Object> pfpAdActionReports = query.list();
		
		for(Object object:pfpAdActionReports) {
			Object[] ob = (Object[])object;
			adActionSum.put(ob[0].toString(), object);
		}
		// 顯示廣告成效內容
//		for(String adActionSeq:adActionSum.keySet()) {
//			Object[] obj = (Object[])adActionSum.get(adActionSeq);
//			System.out.println(adActionSeq + " => " + obj[0] + ";" + obj[1] + ";" + obj[2]);
//		}

		return adActionSum;
	}

	@SuppressWarnings("unchecked")
	public List<PfpAdAction> getAdActionByCustomerInfoId(String customerInfoId) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		
		hql.append(" from PfpAdAction ");
		hql.append(" where pfpCustomerInfo.customerInfoId = ? ");
		hql.append(" and adActionStatus not in (8,9,10)");
		hql.append(" and adActionEndDate >= CURDATE()");		// 走期的結束日期要大於今天
		hql.append(" order by adActionCreatTime desc");


		Object[] ob = new Object[]{customerInfoId};
		
		return (List<PfpAdAction>) super.getHibernateTemplate().find(hql.toString(), ob);
		
	}
	
	@SuppressWarnings("unchecked")
    public List<PfpAdAction> findBroadcastAdAction(String customerInfoId) {
    	Date today = new Date();
    	StringBuffer hql = new StringBuffer();
    	
    	hql.append(" from PfpAdAction ");
    	hql.append(" where pfpCustomerInfo.customerInfoId = ? ");
    	hql.append(" and adActionStatus = ? ");
    	hql.append(" and adActionEndDate >= ? ");
    	hql.append(" and adActionStartDate <= ? ");
    	
    	Object[] ob = new Object[]{customerInfoId, EnumStatus.Open.getStatusId(), today, today};
    	
    	return (List<PfpAdAction>) super.getHibernateTemplate().find(hql.toString(), ob);
    }

	
	@SuppressWarnings("unchecked")
    public int getAdGroupCounts(String adActionSeq) {
		int count = 0;
    	StringBuffer hql = new StringBuffer();
    	
    	hql.append(" from PfpAdGroup as ag ");
    	hql.append(" where ag.pfpAdAction.adActionSeq = ? ");
    	
    	Object[] ob = new Object[]{adActionSeq};
    	List<PfpAdAction> pfpAdAction = (List<PfpAdAction>) super.getHibernateTemplate().find(hql.toString(), ob);
    	if(pfpAdAction != null) {
    		count = pfpAdAction.size();
    	}
    	
    	return count;
    }

	public List<PfpAdAction> getAdActionByCustomerInfoIdAndMediaAd(String customerInfoId) throws Exception{
		System.out.println(customerInfoId);
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpAdAction where pfpCustomerInfo.customerInfoId = :customerInfoId  ");
		hql.append(" and adActionStatus = 4 ");
		hql.append(" and adOperatingRule = 'MEDIA' ");
    	hql.append(" order by adActionCreatTime desc ");
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql.toString());
		query.setString("customerInfoId", customerInfoId);
		query.setMaxResults(10);
    	return query.list();
	}
}
