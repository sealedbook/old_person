package com.esite.framework.security.service;

import java.util.List;

import com.esite.framework.security.entity.Role;
import com.esite.framework.security.entity.SystemFunction;

public interface FunctionService {
	/**
	 * 关联角色与function
	 * @param role
	 */
	public void relevancyFunction(Role role);
	
	public List<SystemFunction> getSystemFunctionByMenuId(String menuId);

	public void save(String roleId, String[] functionIdArray);
}
