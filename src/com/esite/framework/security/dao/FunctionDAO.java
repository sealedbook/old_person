package com.esite.framework.security.dao;

import java.util.List;

import com.esite.framework.core.dao.JdbcBaseDAO;
import com.esite.framework.security.entity.SystemFunction;

public interface FunctionDAO extends JdbcBaseDAO {

	public List<SystemFunction> getFunctionIdByRoleId(String id);

	public List<SystemFunction> getSystemFunctionByMenuId(String menuId);

	public void save(String roleId, List<Object[]> batchParam);

}
