package com.pchome.akbpfp.db.dao.catalog.prodGroup;

import java.util.List;

import org.hibernate.Session;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.pojo.PfpCatalogGroup;
import com.pchome.enumerate.catalogprod.EnumCatalogGroupDeleteStatusType;

public class PfpCatalogGroupDAO extends BaseDAO<PfpCatalogGroup,String> implements IPfpCatalogGroupDAO{
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalogGroup> getCatalogType(String groupId) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalogGroup ");
		hql.append(" where catalogGroupSeq = '"+groupId+"' ");
		
		return (List<PfpCatalogGroup>) super.getHibernateTemplate().find(hql.toString());
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalogGroup> getCatalogSeq(String groupId) throws Exception{
		
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalogGroup ");
		hql.append(" where catalogGroupSeq = '"+groupId+"' ");
		
		return (List<PfpCatalogGroup>) super.getHibernateTemplate().find(hql.toString());
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalogGroup> getPfpCatalogGroupList (String catalogSeq) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalogGroup ");
		hql.append(" where pfpCatalog.catalogSeq = ? ");
		hql.append(" and catalogGroupDeleteStatus = ? ");
		
		log.info("getPfpCatalogGroupList.sql = " + hql.toString());
		
		return (List<PfpCatalogGroup>) super.getHibernateTemplate().find(hql.toString(), catalogSeq, EnumCatalogGroupDeleteStatusType.NotDeleted.getType());
	}
	
	@SuppressWarnings("unchecked")
	public List<PfpCatalogGroup> getPfpCatalogGroup (String catalogGroupSeq) throws Exception{
		StringBuffer hql = new StringBuffer();
		hql.append(" from PfpCatalogGroup ");
		hql.append(" where catalogGroupSeq = ? ");
		
		return (List<PfpCatalogGroup>) super.getHibernateTemplate().find(hql.toString(), catalogGroupSeq);
		
	}
	
    @Override
    @SuppressWarnings("unchecked")
    public void saveOrUpdateWithCommit(PfpCatalogGroup pfpCatalogGroup) throws Exception{
    	super.getHibernateTemplate().getSessionFactory().getCurrentSession().saveOrUpdate(pfpCatalogGroup);
//		super.getHibernateTemplate().getSessionFactory().getCurrentSession().beginTransaction().commit();
    }
	
    
	@SuppressWarnings("unchecked")
	public void deleteCatalogGroup(String catalogGroupSeq) throws Exception{
		String sql = " update PfpCatalogGroup set catalogGroupDeleteStatus = '"+EnumCatalogGroupDeleteStatusType.Deleted.getType()+"' where catalogGroupSeq = :catalogGroupSeq ";		
		Session session = super.getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.createQuery(sql).setString("catalogGroupSeq", catalogGroupSeq).executeUpdate();
		session.flush();
	}
	

}
