package com.esite.ops.oldperson.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.oldperson.dao.OldPersonOperationLogDao;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.entity.OldPersonSystemLogEntity;
import com.esite.ops.oldperson.service.IOldPersonOperationLogService;

public class OldPersonOperationLogServiceImpl implements IOldPersonOperationLogService {
	enum OldPersonStatus {
		add,delete,died,undo;
	}
	@Autowired
	private OldPersonOperationLogDao oldPersonOperationLog;
	@Autowired
	private OldPersonOperationLogDao oldPersonOperationLogDao;
	
	@Override
	public void deleteLog(OldPersonEntity oldPerson, Customer customer) {
		log(oldPerson.getId(),OldPersonStatus.delete,customer);
	}
	
	@Override
	public void addLog(OldPersonEntity oldPerson, Customer customer) {
		log(oldPerson.getId(),OldPersonStatus.add,customer);
	}
	
	@Override
	public void diedLog(OldPersonEntity oldPerson, Customer customer) {
		log(oldPerson.getId(),OldPersonStatus.died,customer);
	}
	
	@Override
	public void undoLog(OldPersonEntity oldPerson, Customer customer) {
		log(oldPerson.getId(),OldPersonStatus.undo,customer);
	}
	
	private void log(String oldPersonId,OldPersonStatus status, Customer customer) {
		OldPersonSystemLogEntity log = new OldPersonSystemLogEntity();
		log.setOldPersonId(oldPersonId);
		log.setStatus(status.toString());
		log.setSubmitDateTime(new Date());
		log.setSubmitIpAddress(customer.getIp());
		log.setSubmitUserAgent(customer.getUserAgent());
		log.setSubmitUserName(customer.getUser().getShowName());
		oldPersonOperationLogDao.save(log);
	}

}
