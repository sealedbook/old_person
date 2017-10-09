package com.esite.ops.health.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.esite.ops.health.dao.HealthInfoImportErrorLogDao;
import com.esite.ops.health.entity.HealthInfoImportErrorLogEntity;
import com.esite.ops.health.service.IHealthInfoImportErrorLogService;

public class HealthInfoImportErrorLogServiceImpl implements IHealthInfoImportErrorLogService {
	@Autowired
	private HealthInfoImportErrorLogDao healthInfoImportErrorLogDao;

	@Override
	public void log(String importHealthInfoBatchId, String logId, String message) {
		HealthInfoImportErrorLogEntity healthInfoImportErrorLogEntity = new HealthInfoImportErrorLogEntity();
		healthInfoImportErrorLogEntity.setBatchId(importHealthInfoBatchId);
		healthInfoImportErrorLogEntity.setErrorMessage(message);
		healthInfoImportErrorLogEntity.setInsertDateTime(new Date());
		healthInfoImportErrorLogEntity.setLogId(logId);
		healthInfoImportErrorLogDao.save(healthInfoImportErrorLogEntity);
	}
}
