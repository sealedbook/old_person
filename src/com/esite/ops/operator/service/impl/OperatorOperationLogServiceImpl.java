package com.esite.ops.operator.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.oldperson.entity.OldPersonSystemLogEntity;
import com.esite.ops.operator.dao.OperatorOperationLogDao;
import com.esite.ops.operator.entity.OperatorEntity;
import com.esite.ops.operator.entity.OperatorOperationLogEntity;
import com.esite.ops.operator.service.IOperatorOperationLogService;

public class OperatorOperationLogServiceImpl implements IOperatorOperationLogService {

	@Autowired
	private OperatorOperationLogDao operatorOperationLogDao;
	
	enum OperatorStatus {
		add,delete,undo;
	}
	
	@Override
	public void addLog(OperatorEntity operator, Customer customer) {
		log(operator.getId(),OperatorStatus.add,customer);
	}
	
	@Override
	public void deleteLog(OperatorEntity operator, Customer customer) {
		log(operator.getId(),OperatorStatus.delete,customer);
	}

	@Override
	public void undoLog(OperatorEntity operator, Customer customer) {
		log(operator.getId(),OperatorStatus.undo,customer);
	}
	
	private void log(String operatorId,OperatorStatus status, Customer customer) {
		OperatorOperationLogEntity log = new OperatorOperationLogEntity();
		log.setOperatorId(operatorId);
		log.setStatus(status.toString());
		log.setSubmitDateTime(new Date());
		log.setSubmitIpAddress(customer.getIp());
		log.setSubmitUserAgent(customer.getUserAgent());
		log.setSubmitUserName(customer.getUser().getShowName());
		operatorOperationLogDao.save(log);
	}

}
