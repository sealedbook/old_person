package com.esite.framework.security.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.esite.framework.core.dao.jdbcDaoImpl.JdbcDAOImpl;
import com.esite.framework.security.dao.SystemMenuDAO;
import com.esite.framework.security.entity.SystemMenu;

public class SystemMenuDAOImpl extends JdbcDAOImpl implements SystemMenuDAO {

	@Override
	public List<SystemMenu> loadFirstMenu() {
		String sql = "select * from sys_security_module t where t.parent_id is null";
		return super.query(sql, new MenuModuleRowMapper());
	}
	
	@Override
	public List<SystemMenu> loadMenuByParentId(String menuId) {
		String sql = "select * from sys_security_module t where t.parent_id=?";
		return super.query(sql,new Object[]{menuId}, new MenuModuleRowMapper());
	}

}
class MenuModuleRowMapper implements RowMapper<SystemMenu> {

	@Override
	public SystemMenu mapRow(ResultSet rs, int rowNum) throws SQLException {
		SystemMenu menuModule = new SystemMenu();
		menuModule.setId(rs.getString("ID"));
		menuModule.setName(rs.getString("MODULE_NAME"));
		menuModule.setIcon(rs.getString("ICON"));
		menuModule.setOrder(rs.getInt("ORDERED"));
		menuModule.setUrl(rs.getString("ENTRY_URL"));
		return menuModule;
	}
	
}