package com.pchome.akbpfp.db.dao.menu;

import java.util.List;

import com.pchome.akbpfp.db.dao.BaseDAO;
import com.pchome.akbpfp.db.dao.IBaseDAO;
import com.pchome.akbpfp.db.pojo.PfpMenu;

public class PfpMenuDAO extends BaseDAO <PfpMenu,String> implements IBaseDAO <PfpMenu,String>{

//	@SuppressWarnings("unchecked")
//	public PfpMenu findPfpMenuByMenuId(String menuId) throws Exception{
//		List<PfpMenu> list = super.getHibernateTemplate().find("from PfpMenu where menuId = '"+menuId+"' and parentMenuId is null order by sort");
//		if(list.size()>0 && list != null){
//			return list.get(0);
//		}else{
//			return null;
//		}
//	}
//	@SuppressWarnings("unchecked")
//	public List<PfpMenu> findPfpMenuByParentMenuId(String parentMenuId) throws Exception{
//		return super.getHibernateTemplate().find("from PfpMenu where parentMentuId = '"+parentMenuId+"' order by sort");
//	}
	
//	@SuppressWarnings("unchecked")
//	public List<PfpMenu> getParentMenu() throws Exception{
//		return super.getHibernateTemplate().find("from PfpMenu where parentMenuId is null order by sort");
//	}
	
//	@SuppressWarnings("unchecked")
//	public List<PfpMenu> getChildMenu() throws Exception{
//		return super.getHibernateTemplate().find("from PfpMenu where parentMenuId is not null order by sort");
//	}
//	@SuppressWarnings("unchecked")
//	public PfpMenu findPfpMenuByMenuId(String menuId) throws Exception{
//		List<PfpMenu> list = super.getHibernateTemplate().find("from PfpMenu where menuId = '"+menuId+"'");
//		if(list.size()>0 && list != null){
//			return list.get(0);
//		}else{
//			return null;
//		}
//	}
}
