package com.pchome.akbpfp.db.dao.ad;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpAdPvclk;
import com.pchome.enumerate.ad.EnumAdLayer;
import com.pchome.enumerate.utils.EnumStatus;


public class PfpAdPvclkDAO extends BaseDAO<PfpAdPvclk,String> implements IPfpAdPvclkDAO{
	
	/**
	 * 統計帳戶指定區間內的 adPv(曝光數)、adClk(點擊數)、adClkPrice(點擊費用)、adInvalidClk(無效點擊數)、adInvalidClkPrice(無效點擊費用)
	 * 舊版讀取 pfp_ad_pvclk
	 * 新版讀取 pfp_ad_action_report
	 * @param customerInfoId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> totalPvclkCost(String customerInfoId, Date startDate, Date endDate) throws Exception{
	
		StringBuffer hql = new StringBuffer();
		
		hql.append("select sum(adPv), sum(adClk), sum(adClkPrice), sum(adInvalidClk), sum(adInvalidClkPrice) ");
		//hql.append(" from PfpAdPvclk" );			// 舊版讀取 pfp_ad_pvclk
		hql.append(" from PfpAdActionReport" );		// 新版讀取 pfp_ad_action_report
		hql.append(" where customerInfoId = ? ");
		hql.append(" and adPvclkDate >= ? ");
		hql.append(" and adPvclkDate <= ? ");
		
		Object[] ob = new Object[]{customerInfoId, startDate, endDate};
		
		return super.getHibernateTemplate().find(hql.toString(), ob);
	}
	
	/**
	 * 依照紀錄日期為群組，group by 統計帳戶指定區間內的 adPv(曝光數)、adClk(點擊數)、adClkPrice(點擊費用)、adInvalidClk(無效點擊數)、adInvalidClkPrice(無效點擊費用)
	 * 舊版讀取 pfp_ad_pvclk
	 * 新版讀取 pfp_ad_action_report
	 * @param customerInfoId
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> chartPvclkCost(String customerInfoId, Date startDate, Date endDate) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		
		hql.append("select adPvclkDate, sum(adPv), sum(adClk), sum(adClkPrice), sum(adInvalidClk), sum(adInvalidClkPrice) ");
		//hql.append(" from PfpAdPvclk" );			// 舊版讀取 pfp_ad_pvclk
		hql.append(" from PfpAdActionReport" );		// 新版讀取 pfp_ad_action_report
		hql.append(" where customerInfoId = ? ");
		hql.append(" and adPvclkDate >= ? ");
		hql.append(" and adPvclkDate <= ? ");
		hql.append(" group by adPvclkDate ");

		Object[] ob = new Object[]{customerInfoId, startDate, endDate};

		return super.getHibernateTemplate().find(hql.toString(), ob);		
	}

//	/**
//	 * 查詢開啟中的 seq 列表
//	 * 依傳入的 adLayer 查詢走期中的 PfpAdAction、PfpAdGroup、PfpAdKeyword、PfpAd 的編號列表
//	 * @param customerInfoId
//	 * @param adLayer
//	 * @param startDate
//	 * @param endDate
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	public List<Object> getSeqList(final String customerInfoId, final String adLayer, final Date startDate, final Date endDate)throws Exception{
//		// 查詢編號列表
//		List<Object> result = (List<Object> ) getHibernateTemplate().execute(
//			new HibernateCallback<List<Object> >() {
//				public List<Object> doInHibernate(Session session) throws HibernateException, SQLException {
//					String hql = null;
//
//					// PfpAdACtion 
//					if(EnumAdLayer.AD_ACTION.getType().equals(adLayer)){
//						hql = "select adActionSeq from PfpAdAction" + 
//								" where pfpCustomerInfo.customerInfoId = :customerInfoId" + 
//								" and adActionStatus = :status";
//					}
//
//					// PfpAdGroup 
//					if(EnumAdLayer.AD_GROUP.getType().equals(adLayer)){
//						hql = "select adGroupSeq from PfpAdGroup" + 
//								" where pfpAdAction.pfpCustomerInfo.customerInfoId = :customerInfoId" + 
//								" and pfpAdAction.adActionStatus = :status" + 
//								" and adGroupStatus = :status";
//					}
//
//					// PfpAdKeywrod
//					if(EnumAdLayer.AD_KEYWORD.getType().equals(adLayer)){
//						hql = "select adKeywordSeq from PfpAdKeyword" +
//								" where pfpAdGroup.pfpAdAction.pfpCustomerInfo.customerInfoId = :customerInfoId" +
//								" and pfpAdGroup.pfpAdAction.adActionStatus = :status" +
//								" and pfpAdGroup.adGroupStatus = :status" +
//								" and adKeywordStatus = :status";
//					}
//
//			    	// PfpAd
//			    	if(EnumAdLayer.AD_AD.getType().equals(adLayer)){
//			    		hql = "select adSeq from PfpAd" + 
//			        			  " where pfpAdGroup.pfpAdAction.pfpCustomerInfo.customerInfoId = :customerInfoId" +
//			        			  " and pfpAdGroup.pfpAdAction.adActionStatus = :status" + 
//			        			  " and pfpAdGroup.adGroupStatus = :status" +
//			        			  " and adStatus = :status";
//			    	}
//					log.info(hql);
//
//					Query q = session.createQuery(hql)
//							.setString("customerInfoId", customerInfoId)
//							.setInteger("status", EnumStatus.Open.getStatusId());
//					//log.info(" resultData size  = "+ q.list().size());
//
//					return q.list();
//			    }
//			}
//        );
//        return result;
//	}
//
//	/**
//	 * 依傳入的 adLayer 查詢走期中廣告成效、分類成效、關鍵字成效、明細成效
//	 * @param customerInfoId
//	 * @param adLayer
//	 * @param seqList 查詢序號列表
//	 * @param startDate
//	 * @param endDate
//	 * @param page
//	 * @param pageSize
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	public List<Object> adActionCost(final String customerInfoId, final String adLayer, final List<Object> seqList, final Date startDate, final Date endDate, final int page, final int pageSize)throws Exception{
//		List<Object> result = new ArrayList<Object>();
//		if(seqList != null && seqList.size() > 0) {
//			// 走期中廣告成效
//			result = (List<Object>) getHibernateTemplate().execute(
//				new HibernateCallback<List<Object> >() {
//					public List<Object>  doInHibernate(Session session) throws HibernateException, SQLException {
//						String hql = null;
//	
//						// PfpAdAction 廣告活動
//						if(EnumAdLayer.AD_ACTION.getType().equals(adLayer)){
//							hql = adActionReportCostHQL(seqList);
//						}
//						// PfpAdGroup 廣告分類
//						if(EnumAdLayer.AD_GROUP.getType().equals(adLayer)){
//							hql = adGroupReportCostHQL(seqList);
//						}
//						// PfpAdKeyword 關鍵字廣告
//						if(EnumAdLayer.AD_KEYWORD.getType().equals(adLayer)){
//							hql = adKeywordReportCostHQL(seqList);
//						}
//						// PfpAd 廣告明細
//						if(EnumAdLayer.AD_AD.getType().equals(adLayer)){
//							hql = adAdReportCostHQL(seqList);
//						}
//						//log.info(hql);
//				
//						Query q = session.createQuery(hql)
//								.setString("customerInfoId", customerInfoId)
//								.setDate("startDate", startDate)
//								.setDate("endDate", endDate);
//						if(seqList != null && seqList.size() > 0) {
//							q.setParameterList("seqList", seqList);
//						}
//						//page=-1 取得全部不分頁用於download
//						if(page > -1){
//							q.setFirstResult((page-1)*pageSize);  
//							q.setMaxResults(pageSize);
//						}
//						//log.info(" resultData size  = "+ q.list().size());
//	
//						return q.list();
//					}
//				}
//			);
//		}
//		return result;
//	}
	
	/**
	 * 統計廣告活動成效(for 帳戶總覽 > 廣告成效摘要)
	 * @param seqList 查詢序號列表
	 * @return
	 */
	private String adActionReportCostHQL(List<Object> seqList){
		// 統計每項廣告活動成本
		StringBuffer hql = new StringBuffer();
		
		hql.append("select adActionSeq, sum(adPv), sum(adClk), sum(adClkPrice), ");
		hql.append(" 		sum(adInvalidClk), ");	
		hql.append(" 		sum(adInvalidClkPrice) ");
		hql.append(" from PfpAdActionReport");
		hql.append(" where customerInfoId = :customerInfoId ");	
		hql.append(" and adPvclkDate >= :startDate ");
		hql.append(" and adPvclkDate <= :endDate ");
		if(seqList != null && seqList.size() > 0) {
			hql.append(" and adActionSeq in (:seqList) ");
		}
		hql.append(" group by adActionSeq ");
		hql.append(" order by adActionSeq ");
		
		return hql.toString();
	}
	
	/**
	 * 統計廣告群組成效(for 帳戶總覽 > 廣告成效摘要)
	 * @param seqList 查詢序號列表
	 * @return
	 */
	private String adGroupReportCostHQL(List<Object> seqList){
		// 統計每項廣告群組成本
		StringBuffer hql = new StringBuffer();
		
		hql.append("select adGroupSeq, sum(adPv), sum(adClk), sum(adClkPrice), ");
		hql.append(" 		sum(adInvalidClk), ");	
		hql.append(" 		sum(adInvalidClkPrice) ");
		hql.append(" from PfpAdGroupReport");
		hql.append(" where customerInfoId = :customerInfoId ");	
		hql.append(" and adPvclkDate >= :startDate ");
		hql.append(" and adPvclkDate <= :endDate ");
		if(seqList != null && seqList.size() > 0) {
			hql.append(" and adGroupSeq in (:seqList) ");		
		}
		hql.append(" group by adGroupSeq ");
		hql.append(" order by adGroupSeq ");
		
		return hql.toString();
	}
	
	/**
	 * 統計關鍵字廣告成效(for 帳戶總覽 > 廣告成效摘要)
	 * @param seqList 查詢序號列表
	 * @return
	 */
	private String adKeywordReportCostHQL(List<Object> seqList){
		// 統計每項關鍵字廣告成本
		StringBuffer hql = new StringBuffer();
		
		hql.append("select adKeywordSeq, ");
		
		//廣泛比對
		hql.append(" 		sum(adKeywordPv), sum(adKeywordClk), sum(adKeywordClkPrice), ");
		hql.append(" 		sum(adKeywordInvalidClk), ");	
		hql.append(" 		sum(adKeywordInvalidClkPrice), ");
		
		//詞組比對(table尚未確定欄位，所以資料先以廣泛比對欄未替代)
		hql.append(" 		sum(adKeywordPhrasePv), sum(adKeywordPhraseClk), sum(adKeywordPhraseClkPrice), ");
		hql.append(" 		sum(adKeywordPhraseInvalidClk), ");	
		hql.append(" 		sum(adKeywordPhraseInvalidClkPrice), ");
		
		//精準比對(table尚未確定欄位，所以資料先以廣泛比對欄未替代)
		hql.append(" 		sum(adKeywordPrecisionPv), sum(adKeywordPrecisionClk), sum(adKeywordPrecisionClkPrice), ");
		hql.append(" 		sum(adKeywordPrecisionInvalidClk), ");	
		hql.append(" 		sum(adKeywordPrecisionInvalidClkPrice) ");
		
		hql.append(" from PfpAdKeywordReport");
		hql.append(" where customerInfoId = :customerInfoId ");	
		hql.append(" and adKeywordPvclkDate >= :startDate ");
		hql.append(" and adKeywordPvclkDate <= :endDate ");
		if(seqList != null && seqList.size() > 0) {
			hql.append(" and adKeywordSeq in (:seqList) ");
		}
		hql.append(" group by adKeywordSeq ");
		hql.append(" order by adKeywordSeq ");

		return hql.toString();
	}

	/**
	 * 統計廣告明細成效(for 帳戶總覽 > 廣告成效摘要)
	 * @param seqList 查詢序號列表
	 * @return
	 */
	private String adAdReportCostHQL(List<Object> seqList){
		// 統計每項廣告成本
		StringBuffer hql = new StringBuffer();
		
		hql.append("select adSeq, sum(adPv), sum(adClk), sum(adClkPrice), ");
		hql.append(" 		sum(adInvalidClk), ");	
		hql.append(" 		sum(adInvalidClkPrice) ");
		hql.append(" from PfpAdReport");
		hql.append(" where customerInfoId = :customerInfoId ");	
		hql.append(" and adPvclkDate >= :startDate ");
		hql.append(" and adPvclkDate <= :endDate ");
		if(seqList != null && seqList.size() > 0) {
			hql.append(" and adSeq in (:seqList) ");		
		}
		hql.append(" group by adSeq ");
		hql.append(" order by adSeq ");
		
		return hql.toString();
	}

	// 2014-04-23 修改
	/**
	 * 依傳入的 adLayer 查詢走期中廣告成效、分類成效、關鍵字成效、明細成效 筆數
	 * @param customerInfoId
	 * @param adLayer
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public long getAdCostCount(String customerInfoId, String adLayer, Date startDate, Date endDate, int page, int pageSize)throws Exception{


		StringBuffer hql = new StringBuffer();

		if(EnumAdLayer.AD_ACTION.getType().equals(adLayer)){ // PfpAdAction 廣告活動
			hql.append("select count(distinct adActionSeq) ");
			hql.append(" from PfpAdActionReport ");
			hql.append(" where customerInfoId = :customerInfoId ");	
			hql.append(" and adPvclkDate >= :startDate ");
			hql.append(" and adPvclkDate <= :endDate ");
		} else if(EnumAdLayer.AD_GROUP.getType().equals(adLayer)){ // PfpAdGroup 廣告分類
			hql.append("select count(distinct adGroupSeq) ");
			hql.append(" from PfpAdGroupReport");
			hql.append(" where customerInfoId = :customerInfoId ");	
			hql.append(" and adPvclkDate >= :startDate ");
			hql.append(" and adPvclkDate <= :endDate ");
		} else if(EnumAdLayer.AD_KEYWORD.getType().equals(adLayer)){ // PfpAdKeyword 關鍵字廣告
			hql.append("select count(distinct adKeywordSeq) ");
			hql.append(" from PfpAdKeywordReport");
			hql.append(" where customerInfoId = :customerInfoId ");	
			hql.append(" and adKeywordPvclkDate >= :startDate ");
			hql.append(" and adKeywordPvclkDate <= :endDate ");
			//hql.append(" group by adKeywordSeq ");
			hql.append(" order by adKeywordSeq ");
		} else if(EnumAdLayer.AD_AD.getType().equals(adLayer)){ // PfpAd 廣告明細
			hql.append("select count(distinct adSeq) ");
			hql.append(" from PfpAdReport");
			hql.append(" where customerInfoId = :customerInfoId ");	
			hql.append(" and adPvclkDate >= :startDate ");
			hql.append(" and adPvclkDate <= :endDate ");
		}

		Query q = getSession().createQuery(hql.toString())
				.setString("customerInfoId", customerInfoId)
				.setDate("startDate", startDate)
				.setDate("endDate", endDate);

		//page=-1 取得全部不分頁用於download
		if(page > -1){
			q.setFirstResult((page-1)*pageSize);  
			q.setMaxResults(pageSize);
		}

		long count = 0;
		List<Object> resultData = q.list();
		if(resultData != null) {
			// 由於是 report 來 group by seq，所以跟檢視廣告的取法是不一樣，用筆數來取 
			count = Long.parseLong(resultData.get(0).toString());
		}

		return count;
	}

	/**
	 * 依傳入的 adLayer 查詢走期中廣告成效、分類成效、關鍵字成效、明細成效
	 * @param customerInfoId
	 * @param adLayer
	 * @param seqList 查詢序號列表
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAdCost(final String customerInfoId, final String adLayer, final Date startDate, final Date endDate, final int page, final int pageSize)throws Exception{
		List<Object> result = new ArrayList<Object>();
		// 走期中廣告成效
		result = (List<Object>) getHibernateTemplate().execute(
			new HibernateCallback<List<Object> >() {
				public List<Object>  doInHibernate(Session session) throws HibernateException, SQLException {
					String hql = null;

					// PfpAdAction 廣告活動
					if(EnumAdLayer.AD_ACTION.getType().equals(adLayer)){
						hql = adActionReportCostHQL(null);
					}
					// PfpAdGroup 廣告分類
					if(EnumAdLayer.AD_GROUP.getType().equals(adLayer)){
						hql = adGroupReportCostHQL(null);
					}
					// PfpAdKeyword 關鍵字廣告
					if(EnumAdLayer.AD_KEYWORD.getType().equals(adLayer)){
						hql = adKeywordReportCostHQL(null);
					}
					// PfpAd 廣告明細
					if(EnumAdLayer.AD_AD.getType().equals(adLayer)){
						hql = adAdReportCostHQL(null);
					}
					//log.info(hql);
					//System.out.println("customerInfoId = " + customerInfoId);
					//System.out.println("startDate = " + startDate);
					//System.out.println("endDate = " + endDate);

					Query q = session.createQuery(hql)
							.setString("customerInfoId", customerInfoId)
							.setDate("startDate", startDate)
							.setDate("endDate", endDate);
					//page=-1 取得全部不分頁用於download
					if(page > -1){
						q.setFirstResult((page-1)*pageSize);  
						q.setMaxResults(pageSize);
					}
					//log.info(" resultData size  = "+ q.list().size());

					return q.list();
				}
			}
		);
		return result;
	}
}
