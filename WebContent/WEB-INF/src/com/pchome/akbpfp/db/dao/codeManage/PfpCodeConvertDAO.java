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
	
	
	
	
	

}
