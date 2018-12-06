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
import com.pchome.akbpfp.db.vo.codeManage.RetargetingTrackingVO;
import com.pchome.enumerate.codeManage.EnumConvertStatusType;
import com.pchome.enumerate.codeManage.EnumTrackingStatusType;

public class PfpCodeConvertDAO extends BaseDAO<PfpCodeConvert,String> implements IPfpCodeConvertDAO{
	
	@SuppressWarnings("unchecked")
	public void saveOrUpdateWithCommit(PfpCodeConvert pfpCodeConvert) throws Exception{
		super.getSession().saveOrUpdate(pfpCodeConvert);
		super.getSession().beginTransaction().commit();
	}
	
	@SuppressWarnings("unchecked")
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

		Query query = super.getSession().createSQLQuery(hql.toString());
		String retargetingTrackingCount = String.valueOf(query.list().get(0));
		
		return retargetingTrackingCount;
	}
	
	
	@SuppressWarnings("unchecked")
	public void deletePfpCodeConvert(PfpCodeConvert pfpCodeConvert) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" DELETE FROM pfp_code_convert ");
		hql.append(" WHERE convert_seq = :convert_seq");
		
		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setString("convert_seq", pfpCodeConvert.getConvertSeq());

		query.executeUpdate();
		super.getSession().flush();
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

		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getConvertTrackingList(ConvertTrackingVO convertTrackingVO) throws Exception{

		StringBuffer hql = new StringBuffer();
		
		hql.append(" select a.*,IFNULL(b.trans_convert_price,0)trans_convert_price,IFNULL(c.ck_convert_price,0)trans_ck_convert_count,IFNULL(d.pv_convert_price,0)trans_pv_convert_count from ");
		hql.append(" (	select * from pfp_code_convert where 1=1 ");
		hql.append(" 	and pfp_customer_info_id = '"+convertTrackingVO.getPfpCustomerInfoId()+"' ");
		if (StringUtils.isNotBlank(convertTrackingVO.getConvertName())){
			hql.append(" and convert_name like '%"+convertTrackingVO.getConvertName()+"%' ");
		}
		hql.append(" 	and convert_status != '"+EnumConvertStatusType.Delete.getType()+"' ) as a");
		hql.append(" left join ");
		hql.append(" (	select customer_info_id, convert_seq, sum(convert_price) as trans_convert_price from pfp_code_convert_trans where 1=1 ");
		hql.append(" 	and customer_info_id = '"+convertTrackingVO.getPfpCustomerInfoId()+"' ");
		hql.append(" 	and convert_trigger_type ='CK' ");
		hql.append("	group by customer_info_id,convert_seq ) as b");
		hql.append(" on a.convert_seq = b.convert_seq ");
		hql.append(" left join ");
		hql.append(" (	select customer_info_id,convert_seq,sum(convert_count)as ck_convert_price from pfp_code_convert_trans where 1=1 ");
		hql.append(" 	and customer_info_id = '"+convertTrackingVO.getPfpCustomerInfoId()+"' ");
		hql.append(" 	and convert_trigger_type ='CK' ");
		hql.append(" 	group by customer_info_id,convert_seq ) as c ");
		hql.append(" on a.convert_seq = c.convert_seq ");
		hql.append(" left join ");
		hql.append(" (	select customer_info_id,convert_seq,sum(convert_count)as pv_convert_price from pfp_code_convert_trans where 1=1 ");
		hql.append(" 	and customer_info_id = '"+convertTrackingVO.getPfpCustomerInfoId()+"' ");
		hql.append(" 	and convert_trigger_type ='PV' ");
		hql.append(" 	group by customer_info_id,convert_seq ) as d ");
		hql.append(" on a.convert_seq = d.convert_seq ");
		
		log.info(hql.toString());
		
		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setFirstResult((convertTrackingVO.getPage()-1)*convertTrackingVO.getPageSize());
		query.setMaxResults(convertTrackingVO.getPageSize());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		
		return query.list();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getSumConvertCount(ConvertTrackingVO convertTrackingVO) throws Exception{
		StringBuffer hql = new StringBuffer();
		
		hql.append(" select a.pfp_customer_info_id, IFNULL(sum(b.sum_ck_convert_count), 0)sum_ck_convert_count, IFNULL(sum(c.sum_pv_convert_count), 0)sum_pv_convert_count from ");
		hql.append(" (	select * from pfp_code_convert where 1 = 1 ");
		hql.append(" 	and pfp_customer_info_id = '"+convertTrackingVO.getPfpCustomerInfoId()+"' ");
		if (StringUtils.isNotBlank(convertTrackingVO.getConvertName())){
			hql.append(" 	and convert_name like '%"+convertTrackingVO.getConvertName()+"%' ");
		}
		hql.append(" 	and convert_status != "+EnumConvertStatusType.Delete.getType()+" ) as a ");
		hql.append(" left join ");
		hql.append(" (	select convert_seq,convert_trigger_type,convert_count as sum_ck_convert_count,customer_info_id from pfp_code_convert_trans where 1 = 1 ");
		hql.append(" 	and customer_info_id = '"+convertTrackingVO.getPfpCustomerInfoId()+"' ");
		hql.append(" 	and convert_trigger_type = 'CK' ) as b ");
		hql.append(" on a.convert_seq = b.convert_seq ");
		hql.append(" left join ");
		hql.append(" (	select convert_seq,convert_trigger_type,convert_count as sum_pv_convert_count,customer_info_id from pfp_code_convert_trans where 1 = 1 ");
		hql.append("	and customer_info_id = '"+convertTrackingVO.getPfpCustomerInfoId()+"' ");
		hql.append(" 	and convert_trigger_type = 'PV' ) as c ");
		hql.append(" on a.convert_seq = c.convert_seq ");
		hql.append(" group by pfp_customer_info_id ");
		
		log.info(hql.toString());
		
		Query query = super.getSession().createSQLQuery(hql.toString());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
		
		return query.list();
	}
	
	
	public void updateConvertStatus(String pfpCustomerInfoId, List<String> convertIdArray,String convertStatus) throws Exception {
		StringBuffer sql = new StringBuffer()
				.append(" update pfp_code_convert set convert_status = :convertStatus where pfp_customer_info_id = :pfpCustomerInfoId and convert_seq in (:convertIdArray) ");
		
		 log.info("updateConvertStatus.sql = " + sql.toString());

		Session session = getSession();
		session.createSQLQuery(sql.toString()).setString("convertStatus", convertStatus)
				.setString("pfpCustomerInfoId", pfpCustomerInfoId)
				.setParameterList("convertIdArray", convertIdArray).executeUpdate();
		session.flush();
	}
	
	

}
