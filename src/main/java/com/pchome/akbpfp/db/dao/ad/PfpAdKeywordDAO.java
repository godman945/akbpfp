package com.pchome.akbpfp.db.dao.ad;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdKeyword;
import com.pchome.enumerate.ad.EnumAdType;
import com.pchome.enumerate.utils.EnumStatus;

public class PfpAdKeywordDAO extends BaseDAO<PfpAdKeyword,String> implements IPfpAdKeywordDAO{
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@SuppressWarnings("unchecked")
	public List<PfpAdKeyword> findAdKeywords(String adGroupSeq) {
		StringBuffer hql = new StringBuffer();
		List<Object> list = new ArrayList<Object>();
		
		hql.append(" from PfpAdKeyword ");
		hql.append(" where pfpAdGroup.adGroupSeq = ? ");
		hql.append(" and adKeywordStatus != ? ");
		
		list.add(adGroupSeq);
		list.add(EnumStatus.Close.getStatusId());
		
		return (List<PfpAdKeyword>) super.getHibernateTemplate().find(hql.toString(), list.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpAdKeyword> findAdKeywords(String adKeywordSeq, String adGroupSeq, String adKeyword, String adKeywordSearchPrice, String adKeywordChannelPrice, String adKeywordStatus) throws Exception{
		StringBuffer sql = new StringBuffer("from PfpAdKeyword where 1=1 ");
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(adKeywordSeq)) {
			sql.append(" and adKeywordSeq = :adKeywordSeq");
			sqlParams.put("adKeywordSeq", adKeywordSeq.trim());
		}

		if (StringUtils.isNotEmpty(adGroupSeq)) {
			sql.append(" and pfpAdGroup.adGroupSeq = :adGroupSeq");
			sqlParams.put("adGroupSeq", adGroupSeq.trim());
		}

		if (StringUtils.isNotEmpty(adKeyword)) {
			sql.append(" and adKeyword = binary(:adKeyword)");
			sqlParams.put("adKeyword", adKeyword.trim());
		}

		if (StringUtils.isNotEmpty(adKeywordSearchPrice)) {
			sql.append(" and adKeywordSearchPrice = :adKeywordSearchPrice");
			sqlParams.put("adKeywordSearchPrice", Float.parseFloat(adKeywordSearchPrice.trim()));
		}

		if (StringUtils.isNotEmpty(adKeywordChannelPrice)) {
			sql.append(" and adKeywordChannelPrice = :adKeywordChannelPrice");
			sqlParams.put("adKeywordChannelPrice", Float.parseFloat(adKeywordChannelPrice.trim()));
		}

		if (StringUtils.isNotEmpty(adKeywordStatus)) {
			sql.append(" and adKeywordStatus <> :adKeywordStatus");
			sqlParams.put("adKeywordStatus", Integer.parseInt(adKeywordStatus.trim()));
		}
		log.info("findAdKeywords.sql = " + sql);

		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(sql.toString());
        for (String paramName:sqlParams.keySet()) {
            query.setParameter(paramName, sqlParams.get(paramName));
        }
		
        List<PfpAdKeyword> pfpAdKeywords = query.list();
		
		return pfpAdKeywords;
	}

	@SuppressWarnings("unchecked")
	public List<PfpAdKeyword> findAdKeyword(String adKeywordSeq) throws Exception {
		StringBuffer hql = new StringBuffer();
		
		hql.append(" from PfpAdKeyword where adKeywordSeq = ? ");
		hql.append(" order by adKeywordSeq desc ");
		
		return (List<PfpAdKeyword>) super.getHibernateTemplate().find(hql.toString(), adKeywordSeq);
	}

	@SuppressWarnings("unchecked")
	public PfpAdKeyword getPfpAdKeywordBySeq(String adKeywordSeq) throws Exception {
		Object[] obj = new Object[]{adKeywordSeq};
		List<PfpAdKeyword> list = (List<PfpAdKeyword>) super.getHibernateTemplate().find("from PfpAdKeyword where adKeywordSeq = ?", obj);
		if (list!=null && list.size()>0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 依傳入的 seq 列表，查詢廣告關鍵字資料
	 * @param seqList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getPfpAdKeywordBySeqList(List<String> seqList) throws Exception {
		String hql = "from PfpAdKeyword where adKeywordSeq in (:adKeywordSeq) ";
		// 將條件資料設定給 Query，準備 query
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query q = session.createQuery(hql.toString());
		q.setParameterList("adKeywordSeq", seqList);

		HashMap<String, Object> adKeywordList = new HashMap<String, Object>();
		List<PfpAdKeyword> pfpAdKeywords = q.list();
		for(PfpAdKeyword pfpAdKeyword:pfpAdKeywords) {
			adKeywordList.put(pfpAdKeyword.getAdKeywordSeq(), pfpAdKeyword);
		}
		return adKeywordList;
	}
	
	@Transactional
	public void saveOrUpdatePfpAdKeyword(PfpAdKeyword pfpAdKeyword) throws Exception{
		super.getHibernateTemplate().saveOrUpdate(pfpAdKeyword);
	}

	public void insertPfpAdKeyword(PfpAdKeyword pfpAdKeyword) throws Exception {
		this.saveOrUpdate(pfpAdKeyword);
	}

	@Transactional
	public void updatePfpAdKeyword(PfpAdKeyword pfpAdKeyword) throws Exception {
		this.update(pfpAdKeyword);
	}
	
	public void updatePfpAdKeywordStatus(String pfpAdKeywordStatus, String adKeywordSeq) throws Exception {
		String sql = "UPDATE pfp_ad_keyword set ad_keyword_status = :pfpAdKeywordStatus where ad_keyword_seq = :adKeywordSeq";
		//log.info("updatePfpAdKeywordStatus.sql = " + sql);

        Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
        session.createSQLQuery(sql).setString("pfpAdKeywordStatus", pfpAdKeywordStatus).setString("adKeywordSeq", adKeywordSeq).executeUpdate();
        session.flush();
	}
	
	public void saveOrUpdateWithCommit(PfpAdKeyword adKeyword) throws Exception{
		super.getHibernateTemplate().getSessionFactory().getCurrentSession().saveOrUpdate(adKeyword);
//		super.getHibernateTemplate().getSessionFactory().getCurrentSession().beginTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getAdRank(String customerInfoId, String adGroupSeq, String keyword, Date startDate, Date endDate) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select pakar.pfpAdKeyword.adKeywordSeq, ");
		hql.append(" 		COALESCE(sum(pakar.adRankAvg)/count(pakar.adRankAvg),0) ");
		hql.append(" from PfpAdKeyword as pak ");
		hql.append(" 		left join pak.pfpAdRanks pakar ");
		hql.append("			with (pakar.adRankDate >= :startDate ");
		hql.append("					and pakar.adRankDate <= :endDate ");
		hql.append(" 					and pakar.adType = :adRankType)  ");
		hql.append(" where pak.pfpAdGroup.pfpAdAction.pfpCustomerInfo.customerInfoId = :customerInfoId ");
		hql.append(" and pak.adKeywordStatus != :status ");
		hql.append(" and pak.pfpAdGroup.adGroupSeq = :adGroupSeq ");						
		hql.append(" and pak.adKeyword like :keyword ");						
		hql.append(" group by pak.adKeywordSeq ");
		hql.append(" order by pak.adKeywordCreateTime desc ");
		
		Query q = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql.toString())
							.setDate("startDate", startDate)
							.setDate("endDate", endDate)
							.setInteger("adRankType", EnumAdType.AD_SEARCH.getType())									
							.setString("customerInfoId", customerInfoId)
							.setInteger("status", EnumStatus.Close.getStatusId())									
							.setString("adGroupSeq", adGroupSeq)
							.setString("keyword", "%"+keyword+"%");
		
		return q.list();
	}

	/**
	 * (舊版)查詢關鍵字統計資料，由 pfp_ad_keyword_pvclk 統計資料
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAdKeywordPvclk(final String customerInfoId, final String adGroupSeq, final String keyword, final Date startDate, final Date endDate, final int page, final int pageSize) throws Exception{
		List<Object> result = (List<Object> ) getHibernateTemplate().execute(
				 
                new HibernateCallback<List<Object> >() {
                	
					public List<Object>  doInHibernate(Session session) throws HibernateException {
						
						StringBuffer hql = new StringBuffer();
						
						hql.append(" select pak.pfpAdGroup.pfpAdAction.adActionSeq, ");
						hql.append(" 		pak.pfpAdGroup.pfpAdAction.adActionName, ");
						hql.append(" 		pak.pfpAdGroup.pfpAdAction.adActionMax, ");
						hql.append(" 		pak.pfpAdGroup.adGroupSeq, ");
						hql.append(" 		pak.pfpAdGroup.adGroupName, ");
						hql.append(" 		pak.adKeywordSeq, ");
						hql.append(" 		pak.adKeyword, ");
						hql.append(" 		pak.adKeywordStatus, ");
						hql.append(" 		pak.adKeywordSearchPrice, ");
						hql.append(" 		pak.adKeywordChannelPrice, ");
						hql.append(" 		COALESCE(sum(pakpc.adKeywordPv),0), ");
						hql.append(" 		COALESCE(sum(pakpc.adKeywordClk),0), ");
						hql.append(" 		COALESCE(sum(pakpc.adKeywordClkPrice),0), ");
						hql.append("		COALESCE(sum(pakpc.adKeywordInvalidClk),0), ");
						hql.append("		COALESCE(sum(pakpc.adKeywordInvalidClkPrice),0), ");
						hql.append("		COALESCE(pakpc.adKeywordType,0) ");
						hql.append(" from PfpAdKeyword as pak ");
						hql.append(" 		left join pak.pfpAdKeywordPvclks pakpc ");
						hql.append(" 			with (pakpc.adKeywordPvclkDate >= :startDate ");
						hql.append(" 					and pakpc.adKeywordPvclkDate <= :endDate ");
						hql.append(" 					and pakpc.adKeywordType = :adType) ");
						hql.append(" where pak.pfpAdGroup.pfpAdAction.pfpCustomerInfo.customerInfoId = :customerInfoId ");
						hql.append(" and pak.adKeywordStatus != :status ");
						hql.append(" and pak.pfpAdGroup.adGroupSeq = :adGroupSeq ");
						hql.append(" and pak.adKeyword like :keyword ");						
						hql.append(" group by pak.adKeywordSeq ");
						hql.append(" order by pak.adKeywordCreateTime desc ");
						
						log.info(hql);	
						log.info(" customerInfoId = "+customerInfoId);
						log.info(" adGroupSeq = "+adGroupSeq);
						log.info(" adType = "+EnumAdType.AD_SEARCH.getType());
						log.info(" status = "+EnumStatus.Close.getStatusId());
						log.info(" adRankType = "+EnumAdType.AD_SEARCH.getType());
						//log.info(" startDate = "+startDate);
						//log.info(" endDate = "+endDate);
						//log.info(" keyword = "+keyword);
						
						Query q = session.createQuery(hql.toString())
									.setDate("startDate", startDate)
									.setDate("endDate", endDate)
									.setInteger("adType", EnumAdType.AD_SEARCH.getType())																	
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
	 * (新版)查詢關鍵字統計資料，由 pfp_ad_keyword_pvclk 統計資料
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAdKeywordReport(final String customerInfoId, final String adGroupSeq, final String keyword, final Date startDate, final Date endDate, final int page, final int pageSize) throws Exception{
		List<Object> result = (List<Object> ) getHibernateTemplate().execute(
				 
                new HibernateCallback<List<Object> >() {
                	
					public List<Object>  doInHibernate(Session session) throws HibernateException {
						System.out.println("getAdKeywordReport");
						StringBuffer hql = new StringBuffer();
						
						hql.append("select pa.ad_action_seq, ");
						hql.append("		pa.ad_action_name, ");
						hql.append("		pa.ad_action_max, ");
						hql.append("		pag.ad_group_seq, ");
						hql.append("		pag.ad_group_name, ");
						hql.append("		pak.ad_keyword_seq, ");
						hql.append("		pak.ad_keyword, ");
						hql.append("		pak.ad_keyword_status, ");
						hql.append("		pak.ad_keyword_search_price, ");
						hql.append("		pak.ad_keyword_channel_price, ");
						hql.append("		COALESCE(sum(pakpc.ad_keyword_pv),0), ");
						hql.append("		COALESCE(sum(pakpc.ad_keyword_clk),0), ");
						hql.append("		COALESCE(sum(pakpc.ad_keyword_clk_price),0), ");
						hql.append("		COALESCE(sum(pakpc.ad_keyword_invalid_clk),0), ");
						hql.append("		COALESCE(sum(pakpc.ad_keyword_invalid_clk_price),0), ");
						hql.append("		COALESCE(pakpc.ad_keyword_type,0) ");
						hql.append(" from pfp_ad_action pa, pfp_ad_group pag, pfp_ad_keyword as pak");
						hql.append(" 		left join pfp_ad_keyword_report pakpc ");
						hql.append(" 			on (pak.ad_keyword_seq = pakpc.ad_keyword_seq ");
						hql.append(" 					and pakpc.ad_keyword_pvclk_date >= :startDate ");
						hql.append(" 					and pakpc.ad_keyword_pvclk_date <= :endDate ");
						hql.append(" 					and pakpc.ad_keyword_type = :adType) ");
						hql.append(" where pakpc.ad_action_seq = pa.ad_action_seq ");
						hql.append(" and pakpc.ad_group_seq = pag.ad_group_seq ");
						hql.append(" and pakpc.customer_info_id = :customerInfoId ");
						hql.append(" and pak.ad_keyword_status != :status ");
						hql.append(" and pak.ad_group_seq = :adGroupSeq ");
						hql.append(" and pak.ad_keyword like :keyword ");						
						hql.append(" group by pak.ad_keyword_seq ");
						hql.append(" order by pak.ad_keyword_create_time desc, pak.ad_keyword_seq");
						
						log.info(hql);	
						log.info(" customerInfoId = "+customerInfoId);
						log.info(" adGroupSeq = "+adGroupSeq);
						log.info(" adType = "+EnumAdType.AD_SEARCH.getType());
						log.info(" status = "+EnumStatus.Close.getStatusId());
						log.info(" adRankType = "+EnumAdType.AD_SEARCH.getType());
						log.info(" startDate = "+startDate);
						log.info(" endDate = "+endDate);
						log.info(" keyword = "+keyword);
						
						Query q = session.createSQLQuery(hql.toString())
									.setDate("startDate", startDate)
									.setDate("endDate", endDate)
									.setInteger("adType", EnumAdType.AD_SEARCH.getType())																	
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
	 * 查詢廣告關鍵字資料(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PfpAdKeyword> getPfpAdKeywordForView(String customerInfoId, String adGroupSeq, String keyword, int page, int pageSize) throws Exception{
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();

		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpAdKeyword ");
		hql.append(" where adKeywordStatus != :status");
		hql.append(" and pfpAdGroup.adGroupSeq = :adGroupSeq ");
		if (StringUtils.isNotEmpty(customerInfoId)) {
			hql.append(" and pfpAdGroup.pfpAdAction.pfpCustomerInfo.customerInfoId = :customerInfoId");
		}

		if (StringUtils.isNotEmpty(keyword)) {
			hql.append(" and adKeyword like :keyword ");						
		}
		hql.append(" order by adKeywordCreateTime desc ");
		//log.info("getPfpAdKeywordForView.sql = " + hql);

		Query q = session.createQuery(hql.toString())
					.setString("adGroupSeq", adGroupSeq)
					.setInteger("status", EnumStatus.Close.getStatusId());
		if (StringUtils.isNotEmpty(customerInfoId)) {
			q.setString("customerInfoId", customerInfoId);
		}

		if (StringUtils.isNotEmpty(keyword)) {
			q.setString("keyword", "%"+keyword+"%");
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
		//log.info(" resultData size  = "+ q.list().size());

		return q.list();
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
	public List<Object> getAdKeywordReportByAKS(String customerInfoId, String adGroupSeq, String adKeywordSeq, int adKeywordType, Date startDate, Date endDate) throws Exception{
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
//		System.out.println("customerInfoId = " + customerInfoId);
//		System.out.println("adGroupSeq = " + adGroupSeq);
//		System.out.println("adKeywordSeq = " + adKeywordSeq);
//		System.out.println("adKeywordType = " + adKeywordType);
//		System.out.println("startDate = " + startDate);
//		System.out.println("endDate = " + endDate);
		
		StringBuffer hql = new StringBuffer();
		hql.append("select COALESCE(sum(adKeywordPv),0), ");
		hql.append(" COALESCE(sum(adKeywordClk),0), ");
		hql.append(" COALESCE(sum(adKeywordClkPrice),0), ");
		hql.append(" COALESCE(sum(adKeywordInvalidClk),0), ");
		hql.append(" COALESCE(sum(adKeywordInvalidClkPrice),0), ");
		hql.append(" COALESCE(adKeywordType,0) ");
		hql.append(" from PfpAdKeywordReport where 1=1 ");

		if (StringUtils.isNotEmpty(customerInfoId)) {
			hql.append(" and customerInfoId = :customerInfoId");
		}

		if (StringUtils.isNotEmpty(adGroupSeq)) {
			hql.append(" and adGroupSeq = :adGroupSeq");
		}

		if (StringUtils.isNotEmpty(adKeywordSeq)) {
			hql.append(" and adKeywordSeq = :adKeywordSeq");
		}

		if(adKeywordType > 0){
			hql.append(" and adKeywordType = :adKeywordType ");
		}else{
			hql.append(" and adKeywordType != :adKeywordType ");
		}

		if (startDate != null) {
			hql.append(" and adKeywordPvclkDate  >= :startDate");
		}

		if (endDate != null) {
			hql.append(" and adKeywordPvclkDate <= :endDate");
		}
		hql.append(" group by adKeywordSeq");
		log.info("hql  = "+ hql.toString());

		Query q = session.createQuery(hql.toString())
					.setInteger("adKeywordType", adKeywordType);
		if (StringUtils.isNotEmpty(customerInfoId)) {
			q.setString("customerInfoId", customerInfoId);
		}

		if (StringUtils.isNotEmpty(adGroupSeq)) {
			q.setString("adGroupSeq", adGroupSeq);
		}

		if (StringUtils.isNotEmpty(adKeywordSeq)) {
			q.setString("adKeywordSeq", adKeywordSeq);
		}

		if (startDate != null) {
			q.setDate("startDate", startDate);
		}

		if (endDate != null) {
			q.setDate("endDate", endDate);
		}
		log.info(" resultData size  = "+ q.list().size());

		return q.list();
	}

	// 以下為 2014/04/21 修改版本
	/**
	 * 查詢廣告關鍵字平均排名(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param adKeywordType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAdRankByAGS(String customerInfoId, String adGroupSeq, int adKeywordType, Date startDate, Date endDate) throws Exception{

		System.out.println("customerInfoId = " + customerInfoId);
		System.out.println("adGroupSeq = " + adGroupSeq);
		System.out.println("adKeywordType = " + adKeywordType);
		System.out.println("startDate = " + startDate);
		System.out.println("endDate = " + endDate);
		
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" select pfpAdKeyword.adKeywordSeq, ");
		hql.append(" 		COALESCE(sum(adRankAvg)/count(adRankAvg),0) ");
		hql.append(" from PfpAdRank ");
		hql.append(" where 1=1");

		if (StringUtils.isNotEmpty(customerInfoId)) {
			hql.append(" and customerInfoId = :customerInfoId");
			sqlParams.put("customerInfoId", customerInfoId);
		}

		if (StringUtils.isNotEmpty(adGroupSeq)) {
			hql.append(" and pfpAdKeyword.pfpAdGroup.adGroupSeq = :adGroupSeq");
			sqlParams.put("adGroupSeq", adGroupSeq);
		}

//		if(adKeywordType > 0){
//			hql.append(" and adType = :adRankType ");
//			sqlParams.put("adRankType", EnumAdType.AD_SEARCH.getType());
//		}else{
//			hql.append(" and adType != :adRankType ");
//			sqlParams.put("adRankType", EnumAdType.AD_SEARCH.getType());
//		}

		if (startDate != null) {
			hql.append(" and adRankDate  >= :startDate");
			sqlParams.put("startDate", startDate);
		}

		if (endDate != null) {
			hql.append(" and adRankDate <= :endDate");
			sqlParams.put("endDate", endDate);
		}
		hql.append(" group by pfpAdKeyword.adKeywordSeq ");
		log.info("getAdRankByAGS.hql = " + hql);

		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql.toString());
        for (String paramName:sqlParams.keySet()) {
        	if(!paramName.equals("sql")) {
        		query.setParameter(paramName, sqlParams.get(paramName));
        	}
        }
//        for (int i = 0; i < sqlParams.size(); i++) {
//            query.setParameter(i, sqlParams.get(i));
//        }
//
//		//q.setInteger("adRankType", EnumAdType.AD_SEARCH.getType());									
//		if (StringUtils.isNotEmpty(customerInfoId)) {
//			q.setString("customerInfoId", customerInfoId);
//		}
//
//		if (StringUtils.isNotEmpty(adGroupSeq)) {
//			q.setString("adGroupSeq", adGroupSeq);
//		}
//
//		if (startDate != null) {
//			q.setDate("startDate", startDate);
//		}
//
//		if (endDate != null) {
//			q.setDate("endDate", endDate);
//		}
		
		return query.list();
	}

	/**
	 * 查詢廣告關鍵字序號列表(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> getPfpAdKeywordSeqList(String customerInfoId, String adGroupSeq, String keyword, int page, int pageSize) throws Exception{
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();

		StringBuffer hql = new StringBuffer();
		hql.append("select adKeywordSeq from PfpAdKeyword ");
		hql.append(" where adKeywordStatus != :status");
		hql.append(" and pfpAdGroup.adGroupSeq = :adGroupSeq ");
		if (StringUtils.isNotEmpty(customerInfoId)) {
			hql.append(" and pfpAdGroup.pfpAdAction.pfpCustomerInfo.customerInfoId = :customerInfoId");
		}

		if (StringUtils.isNotEmpty(keyword)) {
			hql.append(" and adKeyword like :keyword ");						
		}
		hql.append(" order by adKeywordCreateTime desc ");
		//log.info("getPfpAdKeywordForView.sql = " + hql);

		Query q = session.createQuery(hql.toString())
					.setString("adGroupSeq", adGroupSeq)
					.setInteger("status", EnumStatus.Close.getStatusId());
		if (StringUtils.isNotEmpty(customerInfoId)) {
			q.setString("customerInfoId", customerInfoId);
		}

		if (StringUtils.isNotEmpty(keyword)) {
			q.setString("keyword", "%"+keyword+"%");
		}
		System.out.println("customerInfoId = " + customerInfoId);
		System.out.println("adGroupSeq = " + adGroupSeq);
		System.out.println("keyword = " + keyword);
		System.out.println("page = " + page);
		System.out.println("pageSize = " + pageSize);
		System.out.println("hql = " + hql);

		//page=-1 取得全部不分頁用於download
		if(page!=-1){
			q.setFirstResult((page-1)*pageSize)  
				.setMaxResults(pageSize);
		}      
		//log.info(" resultData size  = "+ q.list().size());

		return q.list();
	}

	// 2014-04-21 修改的版本，此版處理是將 DB 連線數量壓到最低來處理，不然速度會因為與 DB 連線數量過多而變得非常慢
	/**
	 * 查詢廣告關鍵字筆數(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param keyword
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public long getPfpAdKeywordCount(String customerInfoId, String adGroupSeq, String keyword, int page, int pageSize) throws Exception{
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();

		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("select count(adKeywordSeq) from PfpAdKeyword ");
		hql.append(" where adKeywordStatus != :status");
		hql.append(" and pfpAdGroup.adGroupSeq = :adGroupSeq ");
		if (StringUtils.isNotEmpty(customerInfoId)) {
			hql.append(" and pfpAdGroup.pfpAdAction.pfpCustomerInfo.customerInfoId = :customerInfoId");
			sqlParams.put("customerInfoId", customerInfoId.trim());
		}

		if (StringUtils.isNotEmpty(keyword)) {
			hql.append(" and adKeyword like :keyword ");						
			sqlParams.put("keyword", keyword.trim());
		}
		hql.append(" order by adKeywordCreateTime desc ");
		//log.info("getPfpAdKeywordForView.sql = " + hql);

		// 將條件資料設定給 Query，準備 query
		Query q = session.createQuery(hql.toString())
						.setString("adGroupSeq", adGroupSeq)
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
	public HashMap<String, Object> getAdKeywordReportByAdKeywordsList(String customerInfoId, String adGroupSeq, List<String> adKeywordSeqList, int adKeywordType, Date startDate, Date endDate) throws Exception{
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		//System.out.println("customerInfoId = " + customerInfoId);
		//System.out.println("adGroupSeq = " + adGroupSeq);
		//System.out.println("adKeywordSeq = " + adKeywordSeqList);
		//System.out.println("adKeywordType = " + adKeywordType);
		//System.out.println("startDate = " + startDate);
		//System.out.println("endDate = " + endDate);
		
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("select adKeywordSeq,");
		//廣泛比對
		hql.append(" COALESCE(sum(adKeywordPv),0), ");
		hql.append(" COALESCE(sum(adKeywordClk),0), ");
		hql.append(" COALESCE(sum(adKeywordClkPrice),0), ");
		hql.append(" COALESCE(sum(adKeywordInvalidClk),0), ");
		hql.append(" COALESCE(sum(adKeywordInvalidClkPrice),0), ");
		hql.append(" COALESCE(adKeywordType,0), ");
		
		//詞組比對
		hql.append(" COALESCE(sum(adKeywordPhrasePv),0), ");
		hql.append(" COALESCE(sum(adKeywordPhraseClk),0), ");
		hql.append(" COALESCE(sum(adKeywordPhraseClkPrice),0), ");
		hql.append(" COALESCE(sum(adKeywordPhraseInvalidClk),0), ");
		hql.append(" COALESCE(sum(adKeywordPhraseInvalidClkPrice),0), ");
		hql.append(" COALESCE(adKeywordType,0), ");
		
		//精準比對
		hql.append(" COALESCE(sum(adKeywordPrecisionPv),0), ");
		hql.append(" COALESCE(sum(adKeywordPrecisionClk),0), ");
		hql.append(" COALESCE(sum(adKeywordPrecisionClkPrice),0), ");
		hql.append(" COALESCE(sum(adKeywordPrecisionInvalidClk),0), ");
		hql.append(" COALESCE(sum(adKeywordPrecisionInvalidClkPrice),0), ");
		hql.append(" COALESCE(adKeywordType,0) ");
		
		hql.append(" from PfpAdKeywordReport where 1=1 ");

		if (StringUtils.isNotEmpty(customerInfoId)) {
			hql.append(" and customerInfoId = :customerInfoId");
			sqlParams.put("customerInfoId", customerInfoId.trim());
		}

		if (StringUtils.isNotEmpty(adGroupSeq)) {
			hql.append(" and adGroupSeq = :adGroupSeq");
			sqlParams.put("adGroupSeq", adGroupSeq.trim());
		}

		if (adKeywordSeqList != null) {
			hql.append(" and adKeywordSeq in (:adKeywordSeq)");
			sqlParams.put("adKeywordSeq", adKeywordSeqList);
		}

		if(adKeywordType > 0){
			hql.append(" and adKeywordType = :adKeywordType ");
			sqlParams.put("adKeywordType", adKeywordType);
		}else{
			hql.append(" and adKeywordType != :adKeywordType ");
			sqlParams.put("adKeywordType", adKeywordType);
		}

		if (startDate != null) {
			hql.append(" and adKeywordPvclkDate  >= :startDate");
			sqlParams.put("startDate", startDate);
		}

		if (endDate != null) {
			hql.append(" and adKeywordPvclkDate <= :endDate");
			sqlParams.put("endDate", endDate);
		}
		hql.append(" group by adKeywordSeq");
		//log.info("hql  = "+ hql.toString());

		// 將條件資料設定給 Query，準備 query
		Query q = session.createQuery(hql.toString());
        for (String paramName:sqlParams.keySet()) {
        	if(!paramName.equals("sql")) {
				if(paramName.equals("adKeywordSeq")) {
					q.setParameterList("adKeywordSeq", adKeywordSeqList);
				} else {
					q.setParameter(paramName, sqlParams.get(paramName));
				}
        	}
        }
		//log.info(" resultData size  = "+ q.list().size());

        // 將得到的廣告成效結果，設定成 Map, 以方便用 adKeywordSeq 抓取資料
		HashMap<String, Object> adKeywordSum = new HashMap<String, Object>();
		List<Object> pfpAdKeywordReports = q.list();
		for(Object object:pfpAdKeywordReports) {
			Object[] ob = (Object[])object;
			adKeywordSum.put(ob[0].toString(), object);
		}
		// 顯示廣告成效內容
//		for(String adKeywordSeq:adKeywordSum.keySet()) {
//			Object[] obj = (Object[])adKeywordSum.get(adKeywordSeq);
//			System.out.println(adKeywordSeq + " => " + obj[0] + ";" + obj[1] + ";" + obj[2]);
//		}

		return adKeywordSum;
	}

	/**
	 * 查詢廣告關鍵字平均排名(檢視廣告使用)
	 * @param customerInfoId
	 * @param adGroupSeq
	 * @param adKeywordType
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Float> getAdRankByAGS(String customerInfoId, List<String> adKeywordSeqList, Date startDate, Date endDate,String keywordStyle) throws Exception{

//		System.out.println("customerInfoId = " + customerInfoId);
//		System.out.println("startDate = " + startDate);
//		System.out.println("endDate = " + endDate);
		
		HashMap<String, Object> sqlParams = new HashMap<String, Object>();
		StringBuffer hql = new StringBuffer();
		hql.append(" select par.pfpAdKeyword.adKeywordSeq, COALESCE(sum(par.adRankAvg)/count(par.adRankAvg),0) ");
		hql.append(" from PfpAdRank par ");
		hql.append(" where 1=1");
		hql.append(" and adType = :adRankType ");
		sqlParams.put("adRankType", EnumAdType.AD_SEARCH.getType());

		if (StringUtils.isNotEmpty(customerInfoId)) {
			hql.append(" and par.customerInfoId = :customerInfoId");
			sqlParams.put("customerInfoId", customerInfoId);
		}

		if (adKeywordSeqList != null) {
			hql.append(" and par.pfpAdKeyword.adKeywordSeq in (:adKeywordSeq)");
			sqlParams.put("adKeywordSeq", adKeywordSeqList);
		}

		if (startDate != null) {
			hql.append(" and par.adRankDate  >= :startDate");
			sqlParams.put("startDate", startDate);
		}

		if (endDate != null) {
			hql.append(" and par.adRankDate <= :endDate");
			sqlParams.put("endDate", endDate);
		}
		
		if (StringUtils.isNotEmpty(keywordStyle)) {
			hql.append(" and IFNULL(par.adKeywordSearchStyle,'1') = :keywordStyle");
			sqlParams.put("keywordStyle", keywordStyle);
		}
		
		hql.append(" group by par.pfpAdKeyword.adKeywordSeq ");
		log.info("getAdRankByAGS.hql = " + hql);

		// 將條件資料設定給 Query，準備 query
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql.toString());
        for (String paramName:sqlParams.keySet()) {
        	if(!paramName.equals("sql")) {
				if(paramName.equals("adKeywordSeq")) {
					query.setParameterList("adKeywordSeq", adKeywordSeqList);
				} else {
					query.setParameter(paramName, sqlParams.get(paramName));
				}
        	}
        }

        // 將得到的廣告成效結果，設定成 Map, 以方便用 adKeywordSeq 抓取資料
        HashMap<String, Float> adRankSum = new HashMap<String, Float>();
		List<Object> pfpAdRanks = query.list();
		for(Object object:pfpAdRanks) {
			Object[] ob = (Object[])object;
			adRankSum.put(ob[0].toString(), Float.parseFloat(ob[1].toString()));
		}
//		for(String adKeywordSeq:adRankSum.keySet()) {
//			float rank = adRankSum.get(adKeywordSeq);
//			System.out.println(adKeywordSeq + " => " + adKeywordSeq + ";" + rank);
//		}
		
		return adRankSum;
	}

	@SuppressWarnings("unchecked")
	public List<PfpAdKeyword> validAdKeyword(String adGroupSeq) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpAdKeyword ");
		hql.append(" where pfpAdGroup.adGroupSeq = ? ");
		hql.append(" and adKeywordStatus != ? ");
		
		return (List<PfpAdKeyword>) super.getHibernateTemplate().find(hql.toString(),adGroupSeq,EnumStatus.Close.getStatusId());
	}
	//keyWord list update
	public void savePfpAdKeywordList(List<PfpAdKeyword> pfpAdKeywordList) throws Exception {
	    for (PfpAdKeyword pfpAdKeyword : pfpAdKeywordList) {
		super.saveOrUpdate(pfpAdKeyword);
	    }
	}
}
