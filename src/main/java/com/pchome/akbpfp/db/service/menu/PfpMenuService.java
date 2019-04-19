package com.pchome.akbpfp.db.service.menu;

import java.util.ArrayList;
import java.util.List;

import com.pchome.akbpfp.db.dao.menu.PfpMenuDAO;
import com.pchome.akbpfp.db.dao.privilege.PfpPrivilegeModelMenuRefDAO;
import com.pchome.akbpfp.db.pojo.PfpMenu;
import com.pchome.akbpfp.db.service.BaseService;
import com.pchome.akbpfp.db.vo.Menu.MenuVO;


public class PfpMenuService extends BaseService<PfpMenu,String> implements IPfpMenuService{
	
//	private PfpPrivilegeModelMenuRefDAO pfpPrivilegeModelMenuRefDAO;
//	
//	private PfpMenuDAO pfpMenuDAO;
//
//	public List<MenuVO> getMainMenu(String modelId) throws Exception{
//		
//		List<MenuVO> menuVOList = new ArrayList<MenuVO>();
//		
//		// 先取 parent menu
//		List<String> menuIdList = pfpPrivilegeModelMenuRefDAO.findPfpPrivilegeMenuIdByModelId(modelId);
//		for(int i=0;i<menuIdList.size();i++){
//			
//			PfpMenu pfpMenu = pfpMenuDAO.findPfpMenuByMenuId(menuIdList.get(i));
//			if(pfpMenu != null){
//				MenuVO vo = new MenuVO();
//				vo.setMenuId(pfpMenu.getMenuId());
//				vo.setMenuName(pfpMenu.getMenuName());
//				vo.setMenuAction(pfpMenu.getAction());
//				vo.setMenuSort(pfpMenu.getSort());
//				menuVOList.add(vo);
//			}
//			
//		}
//
//		return menuVOList;
//	}
//	
//	public List<MenuVO> getSubMenu(String parentMenuId) throws Exception{
//		
//		List<MenuVO> menuVOList = new ArrayList<MenuVO>();
//		
//		// 取 child menu
//		List<PfpMenu> menuList = pfpMenuDAO.findPfpMenuByParentMenuId(parentMenuId);
//		
//		for(int i=0;i<menuList.size();i++){
//			MenuVO vo = new MenuVO();
//			
//			vo.setMenuId(menuList.get(i).getMenuId());
//			vo.setMenuName(menuList.get(i).getMenuName());
//			vo.setMenuAction(menuList.get(i).getAction());
//			vo.setMenuSort(menuList.get(i).getSort());
//			menuVOList.add(vo);
//		}
//		
//		return menuVOList;
//	}
//	
//	public void setPfpPrivilegeModelMenuRefDAO(
//			PfpPrivilegeModelMenuRefDAO pfpPrivilegeModelMenuRefDAO) {
//		this.pfpPrivilegeModelMenuRefDAO = pfpPrivilegeModelMenuRefDAO;
//	}
//
//	public void setPfpMenuDAO(PfpMenuDAO pfpMenuDAO) {
//		this.pfpMenuDAO = pfpMenuDAO;
//	}
	
	
	
}
