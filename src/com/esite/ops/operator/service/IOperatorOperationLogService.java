package com.esite.ops.operator.service;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.operator.entity.OperatorEntity;

public interface IOperatorOperationLogService {

	public void addLog(OperatorEntity operator,Customer customer);
	
	public void deleteLog(OperatorEntity operator,Customer customer);

	public void undoLog(OperatorEntity operator, Customer customer);
	
}
