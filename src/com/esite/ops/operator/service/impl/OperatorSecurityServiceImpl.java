package com.esite.ops.operator.service.impl;


import org.springframework.beans.factory.annotation.Autowired;

import com.esite.framework.security.service.RoleService;
import com.esite.framework.user.entity.User;
import com.esite.framework.user.service.UserService;
import com.esite.ops.operator.entity.OperatorEntity;
import com.esite.ops.operator.service.IOperatorSecurityService;

public class OperatorSecurityServiceImpl implements IOperatorSecurityService {

	@Autowired
	private UserService userService;
	
	@Override
	public void addSystemUser(OperatorEntity operator) {
		User user = new User();
		user.setAccount(operator.getIdCard());
		user.setIdCard(operator.getIdCard());
		user.setPassword(operator.getIdCard().substring(12, 18));
		user.setSex(operator.getSex());
		user.setShowName(operator.getName());
		userService.save(user, new String[]{RoleService.OPERATOR_ROLE_ID});
	}

	@Override
	public void deleteSystemUser(OperatorEntity operator) {
		userService.deleteByIdCard(operator.getIdCard());
	}
	
}
