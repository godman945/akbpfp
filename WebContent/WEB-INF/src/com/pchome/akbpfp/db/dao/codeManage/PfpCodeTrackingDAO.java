package com.pchome.akbpfp.db.dao.codeManage;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCodeTracking;
import com.pchome.akbpfp.db.vo.codeManage.RetargetingTrackingVO;
import com.pchome.enumerate.catalogprod.EnumEcCheckStatusType;
import com.pchome.enumerate.catalogprod.EnumEcStatusType;
import com.pchome.enumerate.codeManage.EnumTrackingStatusType;

public class PfpCodeTrackingDAO extends BaseDAO<PfpCodeTracking,String> implements IPfpCodeTrackingDAO{
	
//    @SuppressWarnings("unchecked")
//    public void saveOrUpdateWithCommit(PfpCode pfpCode) throws Exception{
//    	super.getSession().saveOrUpdate(pfpCode);
//		super.getSession().beginTransaction().commit();
//    }
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getRetargetingTrackingList(RetargetingTrackingVO retargetingTrackingVO) throws Exception{

		StringBuffer hql = new StringBuffer();
		hql.append(" select * ");
		hql.append(" from pfp_code_tracking ");
		hql.append(" where 1=1 ");
		hql.append(" and pfp_customer_info_id = '"+retargetingTrackingVO.getPfpCustomerInfoId()+"' ");
		hql.append(" and tracking_status != '"+EnumTrackingStatusType.Delete.getType()+"' ");
		
		if (StringUtils.isNotBlank(retargetingTrackingVO.getRetargetingName())){
			hql.append(" and tracking_name like '%"+retargetingTrackingVO.getRetargetingName()+"%' ");
		}
		
		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setFirstResult((retargetingTrackingVO.getPage()-1)*retargetingTrackingVO.getPageSize());
		query.setMaxResults(retargetingTrackingVO.getPageSize());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public String getRetargetingTrackingCount(RetargetingTrackingVO retargetingTrackingVO) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(*) ");
		hql.append(" from pfp_code_tracking ");
		hql.append(" where 1=1 ");
		hql.append(" and pfp_customer_info_id = '"+retargetingTrackingVO.getPfpCustomerInfoId()+"' ");
		hql.append(" and tracking_status != '"+EnumTrackingStatusType.Delete.getType()+"' ");
		
		if (StringUtils.isNotBlank(retargetingTrackingVO.getRetargetingName())){
			hql.append(" and tracking_name like '%"+retargetingTrackingVO.getRetargetingName()+"%' ");
		}
		
		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		String retargetingTrackingCount = String.valueOf(query.list().get(0));
		
		return retargetingTrackingCount;
	}
	
	
	public void updateTrackingStatus(String pfpCustomerInfoId, String trackingSeq,String trackingStatus) throws Exception {
		StringBuffer sql = new StringBuffer()
				.append(" update pfp_code_tracking set tracking_status = :trackingStatus where pfp_customer_info_id = :pfpCustomerInfoId and tracking_seq =:trackingSeq ");
		
		 log.info("updateProdListProdStatus.sql = " + sql.toString());

		Session session = getSession();
		session.createSQLQuery(sql.toString()).setString("trackingStatus", trackingStatus)
				.setString("pfpCustomerInfoId", pfpCustomerInfoId)
				.setString("trackingSeq", trackingSeq).executeUpdate();
		session.flush();
		
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPfpCodeTrackingByCondition(RetargetingTrackingVO retargetingTrackingVO) throws Exception{

		StringBuffer hql = new StringBuffer();
		hql.append(" select * ");
		hql.append(" from pfp_code_tracking ");
		hql.append(" where 1=1 ");
		hql.append(" and pfp_customer_info_id = '"+retargetingTrackingVO.getPfpCustomerInfoId()+"' ");
		hql.append(" and tracking_seq = '"+retargetingTrackingVO.getTrackingSeq()+"' ");
		
		log.info(hql.toString());

		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		
		return query.list();
	}
	
	

}
