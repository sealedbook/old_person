package com.esite.ops.health.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.esite.framework.user.entity.User;
import com.esite.ops.health.dao.HealthInfoImportLogDao;
import com.esite.ops.health.entity.HealthInfoImportLogEntity;
import com.esite.ops.health.service.IHealthInfoImportLogService;

public class HealthInfoImportLogServiceImpl implements IHealthInfoImportLogService {

	@Autowired
	private HealthInfoImportLogDao healthInfoImportLogDao;

	@Override
	public String log(String importHealthInfoBatchId, User user,String ipAddress, String convert) {
		HealthInfoImportLogEntity healthInfoImportLog = new HealthInfoImportLogEntity();
		healthInfoImportLog.setBatchId(importHealthInfoBatchId);
		healthInfoImportLog.setImportContent(convert);
		healthInfoImportLog.setInsertDateTime(new Date());
		healthInfoImportLog.setSubmitIpAddress(ipAddress);
		healthInfoImportLog.setSubmitUserId(user.getId());
		this.healthInfoImportLogDao.save(healthInfoImportLog);
		return healthInfoImportLog.getId();
	}
	
}
