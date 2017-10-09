package com.esite.framework.security.service;

import java.util.List;

import com.esite.framework.security.entity.Role;
import com.esite.framework.security.entity.SystemMenu;
import com.esite.framework.user.entity.User;


public interface SystemMenuService {
	
	public List<SystemMenu> getMenu(User user);

	/**
	 * 取得系统中的所有角色，并将角色与系统菜单关联
	 */
	public void initMenuWithRole();
	
	/**
	 * 获得系统菜单
	 * @return
	 */
	public List<SystemMenu> getSystemMenu();
	
	
	
}
