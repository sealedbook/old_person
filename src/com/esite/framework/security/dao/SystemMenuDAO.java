package com.esite.framework.security.dao;

import java.util.List;

import com.esite.framework.core.dao.JdbcBaseDAO;
import com.esite.framework.security.entity.SystemMenu;

public interface SystemMenuDAO extends JdbcBaseDAO {

	public List<SystemMenu> loadFirstMenu();

	public List<SystemMenu> loadMenuByParentId(String menuId);

}
