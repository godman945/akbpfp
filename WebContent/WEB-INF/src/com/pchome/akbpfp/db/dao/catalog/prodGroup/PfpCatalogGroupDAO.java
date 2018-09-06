package com.pchome.akbpfp.db.dao.catalog.prodGroup;

import java.util.List;

import org.hibernate.Session;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;

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
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalogGroup> getPfpCatalogGroup (String catalogGroupSeq) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalogGroup ");
		hql.append(" where catalogGroupSeq = ? ");
		
		return super.getHibernateTemplate().find(hql.toString(), catalogGroupSeq);
		
	}
	
    @Override
    @SuppressWarnings("unchecked")
    public void saveOrUpdateWithCommit(PfpCatalogGroup pfpCatalogGroup) throws Exception{
    	super.getSession().saveOrUpdate(pfpCatalogGroup);
		super.getSession().beginTransaction().commit();
    }
	
    
    @Override
	@SuppressWarnings("unchecked")
	public void deleteCatalogGroup(String catalogGroupSeq) throws Exception{
		String sql = "delete from PfpCatalogGroup where catalogGroupSeq = :catalogGroupSeq";		
		Session session = getSession();
		session.createQuery(sql).setString("catalogGroupSeq", catalogGroupSeq).executeUpdate();
		session.flush();
	}
	

}
