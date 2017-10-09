package com.esite.ops.operator.service;

import com.esite.ops.operator.entity.OperatorEntity;

public interface IOperatorSecurityService {

	public static final String TERMINAL_REQUEST_PARAM_OPERATOR_TOKEN = "TERMINAL_OPERATOR_TOKEN";
	
	public static final String TERMINAL_IMEI = "TERMINAL_IMEI";
	
	public static final String SERVLET_OPERATOR_TOKEN_KEY_PREFIX = "SERVLET_OPERATOR_TOKEN_KEY_PREFIX_";
	
	/**
	 * 向系统中增加一个用户,该用户来源于操作员的信息
	 * @param operator
	 */
	public void addSystemUser(OperatorEntity operator);

	/**
	 * 从系统中删除一个用户
	 * @param operator
	 */
	public void deleteSystemUser(OperatorEntity operator);
	
}
