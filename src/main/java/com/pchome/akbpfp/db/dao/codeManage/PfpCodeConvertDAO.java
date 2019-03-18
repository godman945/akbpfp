package com.pchome.akbpfp.db.dao.codeManage;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCodeConvert;
import com.pchome.akbpfp.db.vo.codeManage.ConvertTrackingVO;
import com.pchome.enumerate.codeManage.EnumConvertStatusType;

public class PfpCodeConvertDAO extends BaseDAO<PfpCodeConvert,String> implements IPfpCodeConvertDAO{
	
	public void saveOrUpdateWithCommit(PfpCodeConvert pfpCodeConvert) throws Exception{
		super.getHibernateTemplate().getSessionFactory().getCurrentSession().saveOrUpdate(pfpCodeConvert);
//		super.getHibernateTemplate().getSessionFactory().getCurrentSession().beginTransaction().commit();
	}
	
	public String getConvertTrackingCount(ConvertTrackingVO convertTrackingVO) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" select count(*) ");
		hql.append(" from pfp_code_convert ");
		hql.append(" where 1=1 ");
		hql.append(" and pfp_customer_info_id = '"+convertTrackingVO.getPfpCustomerInfoId()+"' ");
		hql.append(" and convert_status != '"+EnumConvertStatusType.Delete.getType()+"' ");
		
		if (StringUtils.isNotBlank(convertTrackingVO.getConvertName())){
			hql.append(" and convert_name like '%"+convertTrackingVO.getConvertName()+"%' ");
		}
		
		log.info(hql.toString());

		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		String retargetingTrackingCount = String.valueOf(query.list().get(0));
		
		return retargetingTrackingCount;
	}
	
	
	public void deletePfpCodeConvert(PfpCodeConvert pfpCodeConvert) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" DELETE FROM pfp_code_convert ");
		hql.append(" WHERE convert_seq = :convert_seq");
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setString("convert_seq", pfpCodeConvert.getConvertSeq());
		
		log.info(hql.toString());

		query.executeUpdate();
		super.getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
	}	
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getPfpCodeConvertByCondition(ConvertTrackingVO convertTrackingVO) throws Exception{

		StringBuffer hql = new StringBuffer();
		hql.append(" select * ");
		hql.append(" from pfp_code_convert ");
		hql.append(" where 1=1 ");
		
		if (StringUtils.isNotBlank(convertTrackingVO.getPfpCustomerInfoId())){
			hql.append(" and pfp_customer_info_id = '"+convertTrackingVO.getPfpCustomerInfoId()+"' ");
		}
		if (StringUtils.isNotBlank(convertTrackingVO.getConvertSeq())){
			hql.append(" and convert_seq = '"+convertTrackingVO.getConvertSeq()+"' ");
		}
		
		hql.append(" and convert_status != "+EnumConvertStatusType.Delete.getType());
		
		log.info(hql.toString());

		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getConvertTrackingList(ConvertTrackingVO convertTrackingVO) throws Exception{

		StringBuffer hql = new StringBuffer();
		
		hql.append(" select a.*,IFNULL(b.trans_convert_price,0)trans_convert_price,IFNULL(c.ck_convert_count,0)trans_ck_convert_count,IFNULL(d.pv_convert_count,0)trans_pv_convert_count from ");
		hql.append(" (	select * from pfp_code_convert where 1=1 ");
		hql.append(" 	and pfp_customer_info_id = '"+convertTrackingVO.getPfpCustomerInfoId()+"' ");
		if (StringUtils.isNotBlank(convertTrackingVO.getConvertName())){
			hql.append(" and convert_name like '%"+convertTrackingVO.getConvertName()+"%' ");
		}
		hql.append(" 	and convert_seq = '"+convertTrackingVO.getConvertSeq()+"' ");
		hql.append(" 	and convert_status != '"+EnumConvertStatusType.Delete.getType()+"' ) as a");
		hql.append(" left join ");
		hql.append(" (	select convert_seq, sum(convert_price) as trans_convert_price from pfp_code_convert_trans where 1=1 ");
		hql.append(" 	and convert_trigger_type ='CK' ");
		hql.append(" 	and convert_seq = '"+convertTrackingVO.getConvertSeq()+"' ");
		hql.append(" 	and convert_date >= '"+convertTrackingVO.getCkStartDate()+"' ");
		hql.append(" 	and convert_date <= '"+convertTrackingVO.getCkEndDate()+"' ");
		hql.append("	) as b");
		hql.append(" on a.convert_seq = b.convert_seq ");
		hql.append(" left join ");
		hql.append(" (	select convert_seq,sum(convert_count)as ck_convert_count from pfp_code_convert_trans where 1=1 ");
		hql.append(" 	and convert_trigger_type ='CK' ");
		hql.append(" 	and convert_seq = '"+convertTrackingVO.getConvertSeq()+"' ");
		hql.append(" 	and convert_date >= '"+convertTrackingVO.getCkStartDate()+"' ");
		hql.append(" 	and convert_date <= '"+convertTrackingVO.getCkEndDate()+"' ");
		hql.append(" 	) as c ");
		hql.append(" on a.convert_seq = c.convert_seq ");
		hql.append(" left join ");
		hql.append(" (	select convert_seq,sum(convert_count)as pv_convert_count from pfp_code_convert_trans where 1=1 ");
		hql.append(" 	and convert_trigger_type ='PV' ");
		hql.append(" 	and convert_seq = '"+convertTrackingVO.getConvertSeq()+"' ");
		hql.append(" 	and convert_date >= '"+convertTrackingVO.getPvStartDate()+"' ");
		hql.append(" 	and convert_date <= '"+convertTrackingVO.getPvEndDate()+"' ");
		hql.append(" 	) as d ");
		hql.append(" on a.convert_seq = d.convert_seq ");
		
		log.info(hql.toString());
		
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpCodeConvert> findPfpCodeConvertList(ConvertTrackingVO convertTrackingVO) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCodeConvert where 1=1");
		hql.append(" and pfpCustomerInfoId = :pfpCustomerInfoId");
		hql.append(" and convertStatus != :convertStatusType ");
		
		if (StringUtils.isNotBlank(convertTrackingVO.getConvertName())){
			hql.append(" and convertName like :convertName ");
		}
		
		
		String strHQL = hql.toString();
		log.info(">>> strHQL = " + strHQL);

		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query q = session.createQuery(strHQL);
		
		q.setFirstResult((convertTrackingVO.getPage()-1)*convertTrackingVO.getPageSize());
		q.setMaxResults(convertTrackingVO.getPageSize());
		
		q.setString("pfpCustomerInfoId", convertTrackingVO.getPfpCustomerInfoId());
		q.setString("convertStatusType", EnumConvertStatusType.Delete.getType());
		
		if (StringUtils.isNotBlank(convertTrackingVO.getConvertName())){
			q.setString("convertName", "%" +convertTrackingVO.getConvertName()+"%");
		}
		
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpCodeConvert> findPfpCodeConvertListAll(ConvertTrackingVO convertTrackingVO) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCodeConvert where 1=1");
		hql.append(" and pfpCustomerInfoId = :pfpCustomerInfoId");
		hql.append(" and convertStatus != :convertStatusType ");
		
		if (StringUtils.isNotBlank(convertTrackingVO.getConvertName())){
			hql.append(" and convertName like :convertName ");
		}
		
		
		String strHQL = hql.toString();
		log.info(">>> strHQL = " + strHQL);

		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query q = session.createQuery(strHQL);
		
		q.setString("pfpCustomerInfoId", convertTrackingVO.getPfpCustomerInfoId());
		q.setString("convertStatusType", EnumConvertStatusType.Delete.getType());
		
		if (StringUtils.isNotBlank(convertTrackingVO.getConvertName())){
			q.setString("convertName", "%" +convertTrackingVO.getConvertName()+"%");
		}
		
		return q.list();
	}

	
	public void updateConvertStatus(String pfpCustomerInfoId, String convertSeq,String convertStatus) throws Exception {
		StringBuffer sql = new StringBuffer()
				.append(" update pfp_code_convert set convert_status = :convertStatus where pfp_customer_info_id = :pfpCustomerInfoId and convert_seq = :convertSeq ");
		
		 log.info("updateConvertStatus.sql = " + sql.toString());

		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.createSQLQuery(sql.toString()).setString("convertStatus", convertStatus)
				.setString("pfpCustomerInfoId", pfpCustomerInfoId)
				.setString("convertSeq", convertSeq).executeUpdate();
		session.flush();
	}

	@SuppressWarnings("unchecked")
	public List<PfpCodeConvert> findConvertCodeByCustomerInfoId(String pfpCustomerInfoId) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCodeConvert ");
		hql.append(" where 1 = 1 ");
		hql.append(" and convertStatus = 1 " );
		hql.append(" and pfpCustomerInfoId = :pfpCustomerInfoId" );
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql.toString());
		query.setString("pfpCustomerInfoId", pfpCustomerInfoId);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpCodeConvert> findPfpCodeConvertListByCustomerInfoId(String pfpCustomerInfoId) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCodeConvert ");
		hql.append(" where 1 = 1 ");
		hql.append(" and pfpCustomerInfoId = :pfpCustomerInfoId" );
		hql.append(" and convertStatus != '"+EnumConvertStatusType.Delete.getType()+"' ");
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Query query = session.createQuery(hql.toString());
		query.setString("pfpCustomerInfoId", pfpCustomerInfoId);
		return query.list();
	}
	
}
