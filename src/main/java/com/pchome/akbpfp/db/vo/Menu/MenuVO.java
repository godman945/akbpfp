package com.pchome.akbpfp.db.vo.Menu;

import java.util.List;

public class MenuVO {
	private Integer menuId;
	private String menuName;
	private String menuAction;
	private Integer menuSort;
	private List<MenuVO> subMenu;
	
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuAction() {
		return menuAction;
	}
	public void setMenuAction(String menuAction) {
		this.menuAction = menuAction;
	}
	public Integer getMenuSort() {
		return menuSort;
	}
	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
	}
	public List<MenuVO> getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(List<MenuVO> subMenu) {
		this.subMenu = subMenu;
	}
	
}
