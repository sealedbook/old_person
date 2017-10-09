package com.esite.ops.health.service;

import com.esite.framework.user.entity.User;

public interface IHealthInfoImportLogService {

	public String log(String importHealthInfoBatchId, User user, String ipAddress,String convert);

}
