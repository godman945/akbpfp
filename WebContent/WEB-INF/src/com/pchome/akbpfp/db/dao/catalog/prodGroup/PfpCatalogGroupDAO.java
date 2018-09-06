package com.pchome.akbpfp.db.dao.catalog.prodGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfdUserMemberRef;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroupItem;

public class PfpCatalogGroupDAO extends BaseDAO<PfpCatalogGroup,String> implements IPfpCatalogGroupDAO{
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalogGroup> getCatalogType(String groupId) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalogGroup ");
		hql.append(" where catalogGroupSeq = '"+groupId+"' ");
		
		return super.getHibernateTemplate().find(hql.toString());
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalogGroup> getCatalogSeq(String groupId) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalogGroup ");
		hql.append(" where catalogGroupSeq = '"+groupId+"' ");
		
		return super.getHibernateTemplate().find(hql.toString());
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalogGroup> getPfpCatalogGroupList (String catalogSeq) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalogGroup ");
		hql.append(" where pfpCatalog.catalogSeq = ? ");
		
		return super.getHibernateTemplate().find(hql.toString(), catalogSeq);
	}
	
	

}
