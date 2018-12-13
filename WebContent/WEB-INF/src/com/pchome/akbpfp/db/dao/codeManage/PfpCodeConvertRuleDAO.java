package com.pchome.akbpfp.db.dao.codeManage;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCodeConvert;
import com.pchome.akbpfp.db.pojo.PfpCodeConvertRule;
import com.pchome.akbpfp.db.service.codeManage.IPfpCodeConvertRuleDAO;
import com.pchome.akbpfp.db.vo.codeManage.ConvertTrackingVO;
import com.pchome.enumerate.codeManage.EnumConvertStatusType;

public class PfpCodeConvertRuleDAO extends BaseDAO<PfpCodeConvertRule,String> implements IPfpCodeConvertRuleDAO{
	
	   @SuppressWarnings("unchecked")
	    public void saveOrUpdateWithCommit(PfpCodeConvertRule pfpCodeConvertRule) throws Exception{
	    	super.getSession().saveOrUpdate(pfpCodeConvertRule);
			super.getSession().beginTransaction().commit();
	    }
	   
		@SuppressWarnings("unchecked")
		public void deletePfpCodeConvertRule(String convertSeq) throws Exception{
			StringBuffer hql = new StringBuffer();
			hql.append(" DELETE FROM pfp_code_convert_rule ");
			hql.append(" WHERE convert_seq = :convert_seq");
			
			Query query = super.getSession().createSQLQuery(hql.toString());
			query.setString("convert_seq", convertSeq);

			query.executeUpdate();
			super.getSession().flush();
		}	
		
		@SuppressWarnings("unchecked")
		public List<Map<String,Object>> getPfpCodeConvertRuleByCondition(ConvertTrackingVO convertTrackingVO) throws Exception{

			StringBuffer hql = new StringBuffer();
			hql.append(" select * ");
			hql.append(" from pfp_code_convert_rule ");
			hql.append(" where 1=1 ");
			
			if (StringUtils.isNotBlank(convertTrackingVO.getConvertSeq())){
				hql.append(" and convert_seq = '"+convertTrackingVO.getConvertSeq()+"' ");
			}
			
			log.info(hql.toString());

			Query query = super.getSession().createSQLQuery(hql.toString());
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
			
			return query.list();
		}
		

}