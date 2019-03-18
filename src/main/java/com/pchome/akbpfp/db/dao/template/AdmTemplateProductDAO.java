package com.pchome.akbpfp.db.dao.template;

import java.util.List;

import org.hibernate.Query;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.AdmTemplateProduct;

public class AdmTemplateProductDAO extends BaseDAO<AdmTemplateProduct, String> implements IAdmTemplateProductDAO {

	public List<AdmTemplateProduct> getTemplateProductByXType(List<String> condition) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append(" from AdmTemplateProduct a where a.templateProductSeq like '%pad_tpro%' and a.templateProductName not like '%TBS1P1%'  ORDER BY  a.templateProductWidth ,a.templateProductHeight ASC  ");
		Query query = super.getHibernateTemplate().getSessionFactory().getCurrentSession().createQuery(hql.toString());
//		query.setParameterList("condition", condition);
		return query.list();
	}
}
