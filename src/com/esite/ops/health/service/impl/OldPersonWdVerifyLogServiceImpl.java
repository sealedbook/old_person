package com.esite.ops.health.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.health.dao.OldPersonWdVerifyLogDao;
import com.esite.ops.health.entity.OldPersonWdVerifyLogEntity;
import com.esite.ops.health.service.IOldPersonWdVerifyLogService;

public class OldPersonWdVerifyLogServiceImpl implements IOldPersonWdVerifyLogService {

	@Autowired
	private OldPersonWdVerifyLogDao oldPersonWdVerifyLogDao;
	
	@Override
	public void log(String verifyId, String verifyState, Customer customer) {
		OldPersonWdVerifyLogEntity oldPersonWdVerifyLogEntity = new OldPersonWdVerifyLogEntity();
		oldPersonWdVerifyLogEntity.setSubmitDateTime(new Date());
		oldPersonWdVerifyLogEntity.setSubmitUserIpAddress(customer.getIp());
		oldPersonWdVerifyLogEntity.setSubmitUserName(customer.getUser().getShowName());
		oldPersonWdVerifyLogEntity.setVerifyId(verifyId);
		oldPersonWdVerifyLogEntity.setVerifyState(verifyState);
		oldPersonWdVerifyLogDao.save(oldPersonWdVerifyLogEntity);
	}

}
