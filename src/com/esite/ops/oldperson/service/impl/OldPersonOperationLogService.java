package com.esite.ops.oldperson.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.oldperson.dao.OldPersonOperationLogDao;
import com.esite.ops.oldperson.entity.OldPersonEntity;
import com.esite.ops.oldperson.entity.OldPersonSystemLogEntity;

@Service("oldPersonOperationLogService")
public class OldPersonOperationLogService {
	enum OldPersonStatus {
		add,delete,died,undo;
	}
	@Autowired
	private OldPersonOperationLogDao oldPersonOperationLogDao;
	
	public void deleteLog(OldPersonEntity oldPerson, Customer customer) {
		log(oldPerson.getId(),OldPersonStatus.delete,customer);
	}
	
	public void addLog(OldPersonEntity oldPerson, Customer customer) {
		log(oldPerson.getId(),OldPersonStatus.add,customer);
	}
	
	public void diedLog(OldPersonEntity oldPerson, Customer customer) {
		log(oldPerson.getId(),OldPersonStatus.died,customer);
	}
	
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
