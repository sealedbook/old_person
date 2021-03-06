package com.esite.ops.health.service;

import com.esite.framework.user.entity.User;
import com.esite.ops.health.entity.FingerprintVerifyLogEntity;
import com.esite.ops.health.entity.HealthFingerprintEntity;

public interface IFingerprintInfoService {

	public HealthFingerprintEntity getFingerprintInfoByHealthId(String healthId);

	public HealthFingerprintEntity getFingerprintInfoById(String id);

	/**
	 * 记录认证日志
	 * @param operatorUser 执行人
	 * @param ipAddress 执行人ip地址
	 * @param healthId health编号
	 * @param fingerprintId 指纹信息编号
	 * @param verifyState 认证信息
	 */
	public void verifyLog(User operatorUser, String ipAddress,String healthId,String fingerprintId,String verifyState);
	
}
