package com.esite.framework.security.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.esite.framework.core.dao.jdbcDaoImpl.AbstractJdbcBaseDAO;
import com.esite.framework.core.dao.jdbcDaoImpl.JdbcDAOImpl;
import com.esite.framework.core.dao.jdbcDaoImpl.OracleJdbcBaseDAOImpl;
import com.esite.framework.security.dao.FunctionDAO;
import com.esite.framework.security.entity.SystemFunction;

public class FunctionDAOImpl extends JdbcDAOImpl implements FunctionDAO {

	@Override
	public List<SystemFunction> getFunctionIdByRoleId(String id) {
		String sql = "select f.id,f.name from SYS_SECURITY_RF_MAP rf,sys_security_function f where f.id=rf.function_id and rf.role_id=?";
		return super.query(sql, new Object[]{id}, new SystemFunctionRowMapper());
	}

	@Override
	public List<SystemFunction> getSystemFunctionByMenuId(String menuId) {
		String sql = "select f.id,f.name from SYS_SECURITY_FUNCTION f,SYS_SECURITY_MF_MAP mf where f.id = mf.function_id and mf.module_id=?";
		return super.query(sql, new Object[]{menuId}, new SystemFunctionRowMapper());
	}

	//@Override
	public void save(String roleId, List<Object[]> batchParam) {
		String deletesql="delete from SYS_SECURITY_RF_MAP where role_id=?";
		super.update(deletesql, roleId);
		String sql="insert into SYS_SECURITY_RF_MAP(ID,ROLE_ID,FUNCTION_ID)values(replace(UUID(),'-',''),?,?)";
		super.batchUpdate(sql, batchParam);
	}

}

class SystemFunctionRowMapper implements RowMapper<SystemFunction> {

	@Override
	public SystemFunction mapRow(ResultSet rs, int rowNum) throws SQLException {
		SystemFunction systemFunction = new SystemFunction();
		systemFunction.setId(rs.getString("ID"));
		systemFunction.setName(rs.getString("NAME"));
		return systemFunction;
	}
	
}