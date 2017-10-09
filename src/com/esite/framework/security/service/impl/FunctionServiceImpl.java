package com.esite.framework.security.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.esite.framework.security.dao.FunctionDAO;
import com.esite.framework.security.entity.Role;
import com.esite.framework.security.entity.SystemFunction;
import com.esite.framework.security.service.FunctionService;
import com.esite.framework.security.service.SystemMenuService;

public class FunctionServiceImpl implements FunctionService {
	
	@Autowired
	private FunctionDAO functionDAO;
	
	@Autowired
	private SystemMenuService systemMenuService;
	
	@Override
	public void relevancyFunction(Role role) {
		List<SystemFunction> functionCollection = functionDAO.getFunctionIdByRoleId(role.getId());
		role.setFunctionCollection(functionCollection);
	}

	@Override
	public List<SystemFunction> getSystemFunctionByMenuId(String menuId) {
		return functionDAO.getSystemFunctionByMenuId(menuId);
	}

	@Override
	public void save(String roleId, String[] functionIdArray) {
		if(null == functionIdArray || functionIdArray.length <= 0) {
			return;
		}
		List<Object[]> batchParam = new ArrayList<Object[]>(functionIdArray.length);
		for(String functionId : functionIdArray) {
			Object[] param = new Object[2];
			param[0] = roleId;
			param[1] = functionId;
			batchParam.add(param);
		}
		this.functionDAO.save(roleId, batchParam);
		
		//重新初始化菜单
		systemMenuService.initMenuWithRole();
	}

}
