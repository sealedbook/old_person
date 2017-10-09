package com.esite.ops.health.service;

public interface IHealthInfoImportErrorLogService {

	public void log(String importHealthInfoBatchId, String logId, String message);

}
