package com.esite.framework.security.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.esite.framework.security.dao.SystemMenuDAO;
import com.esite.framework.security.entity.SystemFunction;
import com.esite.framework.security.entity.SystemMenu;
import com.esite.framework.security.entity.Role;
import com.esite.framework.security.service.FunctionService;
import com.esite.framework.security.service.SystemMenuService;
import com.esite.framework.security.service.RoleService;
import com.esite.framework.user.entity.User;
import com.esite.framework.util.StringHelper;

public class SystemMenuServiceImpl implements SystemMenuService {

	public static final String MENU_CACHE_KEY = "GAB.RESOURCE.CATALOG.MENU.CACHE.KEY.";
	public static final String SYSTEM_MENU_CACHE_KEY = "GAB.RESOURCE.CATALOG.SYSTEM.MENU.CACHE.KEY";
	private Logger logger = Logger.getLogger(SystemMenuServiceImpl.class);
	
	@Autowired
	private SystemMenuDAO systemMenuDAO;
	@Autowired
	private RoleService roleService;
	@Autowired
	private FunctionService functionService;
	
	private Cache systemMenuCache;
	public void setSystemMenuCache(Cache systemMenuCache) {
		this.systemMenuCache = systemMenuCache;
	}
	private Cache menuCache;
	public void setMenuCache(Cache menuCache) {
		this.menuCache = menuCache;
	}

	/**
	 * 初始化系统菜单(不分角色)
	 */
	private void initSystemMenu() {
		systemMenuCache.removeAll();
		getSystemMenu();
	}
	
	@Override
	public void initMenuWithRole() {
		initSystemMenu();
		menuCache.removeAll();
		List<Role> roleList = roleService.getAllRoleInSystem();
		logger.info("系统共有" + roleList.size() + "个角色");
		for(Role role : roleList) {
			logger.info("初始化" + role.getName() + "角色的菜单.");
			initRoleMenu(role);
		}
	}
	
	/**
	 * 初始化角色对应的菜单
	 * @param role
	 * @return
	 */
	private List<SystemMenu> initRoleMenu(Role role) {
		List<SystemMenu> menuList = buildRoleMenu(role);
		String key = MENU_CACHE_KEY + role.getId();
		menuCache.put(new Element(key,menuList));
		return menuList;
	}
	
	private List<SystemMenu> buildRoleMenu(Role role) {
		List<SystemMenu> systemMenuCollection = getSystemMenu();
		List<SystemMenu> userMenuCollection = new ArrayList<SystemMenu>();
		for(SystemMenu systemMenu : systemMenuCollection) {
			SystemMenu menu = createUserMenu(role,systemMenu);
			if(null != menu) {
				userMenuCollection.add(menu);
			}
		}
		return userMenuCollection;
	}

	private SystemMenu createUserMenu(Role role,SystemMenu systemMenu) {
		SystemMenu subSystemMenu = new SystemMenu(systemMenu);
		boolean hasSubMenu = false;
		if(systemMenu.getSubSystemMenuSize() > 0) {
			List<SystemMenu> systemMenuCollection = systemMenu.getSubSystemMenu();
			for(SystemMenu sMenu : systemMenuCollection) {
				SystemMenu subMenu = createUserMenu(role,sMenu);
				if(null != subMenu) {
					subSystemMenu.getSubSystemMenu().add(subMenu);
					hasSubMenu = true;
				}
			}
			if(hasSubMenu) {
				return subSystemMenu;
			}
			return null;
		}
		if(roleContainsSystemFunction(role,systemMenu)) {
			return systemMenu;
		} else {
			return null;
		}
	}
	
	private boolean roleContainsSystemFunction(Role role,SystemMenu subSystemMenu) {
		for(SystemFunction systemFunction : subSystemMenu.getSubFunction()) {
			for(SystemFunction roleFunction : role.getFunctionCollection()) {
				if(roleFunction.equals(systemFunction)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<SystemMenu> getMenu(User user) {
		if(null == user) {
			return new ArrayList<SystemMenu>(0);
		}
		List<Role> userRoleList = user.getRoleCollection();
		List<SystemMenu> userMenu = new ArrayList<SystemMenu>();
		for(Role role : userRoleList) {
			List<SystemMenu> roleSystemMenu = getMenuByRole(role);
			if(null != roleSystemMenu) {
				userMenu.addAll(roleSystemMenu);
			}
		}
		return processUserMenu(userMenu);
	}
	
	private List<SystemMenu> processUserMenu(List<SystemMenu> userMenus) {
		List<SystemMenu> userMenu = new ArrayList<SystemMenu>();
		for(SystemMenu roleMenu : userMenus) {
			if(!userMenu.contains(roleMenu)) {
				userMenu.add(roleMenu);
			} else {
				SystemMenu containsMenu = userMenu.get(userMenu.indexOf(roleMenu));
				List<SystemMenu> roleSubMenuList = roleMenu.getSubSystemMenu();
				List<SystemMenu> containsSubMenuList = containsMenu.getSubSystemMenu();
				for(SystemMenu roleSubMenu : roleSubMenuList) {
					if(!containsSubMenuList.contains(roleSubMenu)) {
						containsSubMenuList.add(roleSubMenu);
					}
				}
			}
		}
		menuSort(userMenu);
		return userMenu;
	}
	
	/**
	 * 按照系统模块的order字段排序
	 * @param menus
	 */
	private void menuSort(List<SystemMenu> menus) {
		Collections.sort(menus);
		for(SystemMenu menu : menus) {
			if(!menu.getSubSystemMenu().isEmpty()) {
				menuSort(menu.getSubSystemMenu());
			}
		}
	}
	
	private List<SystemMenu> getMenuByRole(Role role) {
		String key = MENU_CACHE_KEY + role.getId();
		Element elemnt = menuCache.get(key);
		if(null == elemnt) {
			return initRoleMenu(role);
		} else {
			return (List<SystemMenu>)elemnt.getObjectValue();
		}
	}

	@Override
	public List<SystemMenu> getSystemMenu() {
		Element systemMenuElement = systemMenuCache.get(SYSTEM_MENU_CACHE_KEY);
		if(null == systemMenuElement) {
			List<SystemMenu> systemMenu = createSystemMenu(null);
			menuSort(systemMenu);
			systemMenuCache.put(new Element(SYSTEM_MENU_CACHE_KEY,systemMenu));
			return systemMenu;
		} else {
			return (List<SystemMenu>) systemMenuElement.getObjectValue();
		}
	}
	
	private List<SystemMenu> createSystemMenu(String menuId) {
		if(StringHelper.isEmpty(menuId)) {
			List<SystemMenu> firstMenuList = systemMenuDAO.loadFirstMenu();
			for(SystemMenu menu : firstMenuList) {
				menu.setSubFunction(functionService.getSystemFunctionByMenuId(menu.getId()));
				menu.setSubSystemMenu(createSystemMenu(menu.getId()));
			}
			return firstMenuList;
		} else {
			List<SystemMenu> menuList = systemMenuDAO.loadMenuByParentId(menuId);
			for(SystemMenu menu : menuList) {
				menu.setSubFunction(functionService.getSystemFunctionByMenuId(menu.getId()));
				menu.setSubSystemMenu(createSystemMenu(menu.getId()));
			}
			return menuList;
		}
	}
}
