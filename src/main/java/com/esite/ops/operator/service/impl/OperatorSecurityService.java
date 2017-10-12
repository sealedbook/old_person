package com.esite.ops.operator.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.esite.framework.security.service.RoleService;
import com.esite.framework.user.entity.User;
import com.esite.framework.user.service.impl.UserService;
import com.esite.ops.operator.entity.OperatorEntity;

@Service("operatorSecurityService")
public class OperatorSecurityService {

	public static final String TERMINAL_REQUEST_PARAM_OPERATOR_TOKEN = "TERMINAL_OPERATOR_TOKEN";

	public static final String TERMINAL_IMEI = "TERMINAL_IMEI";

	public static final String SERVLET_OPERATOR_TOKEN_KEY_PREFIX = "SERVLET_OPERATOR_TOKEN_KEY_PREFIX_";

	@Resource
	private UserService userService;

	/**
	 * 向系统中增加一个用户,该用户来源于操作员的信息
	 * @param operator
	 */
	public void addSystemUser(OperatorEntity operator) {
		User user = new User();
		user.setAccount(operator.getIdCard());
		user.setIdCard(operator.getIdCard());
		user.setPassword(operator.getIdCard().substring(12, 18));
		user.setSex(operator.getSex());
		user.setShowName(operator.getName());
		userService.save(user, new String[]{RoleService.OPERATOR_ROLE_ID});
	}

	/**
	 * 从系统中删除一个用户
	 * @param operator
	 */
	public void deleteSystemUser(OperatorEntity operator) {
		userService.deleteByIdCard(operator.getIdCard());
	}
	
}
