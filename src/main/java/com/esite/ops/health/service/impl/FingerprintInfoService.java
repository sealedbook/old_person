package com.esite.ops.health.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esite.framework.user.entity.User;
import com.esite.ops.health.dao.FingerprintVerifyLogDao;
import com.esite.ops.health.dao.HealthFingerprintDao;
import com.esite.ops.health.entity.FingerprintVerifyLogEntity;
import com.esite.ops.health.entity.HealthFingerprintEntity;
import com.mchange.v2.log.LogUtils;

@Service("fingerprintInfoService")
public class FingerprintInfoService {

	private static final Logger LOG = LoggerFactory.getLogger(FingerprintInfoService.class);

	@Autowired
	private FingerprintVerifyLogDao fingerprintVerifyLogDao;
	
	@Autowired
	private HealthFingerprintDao healthFingerprintDao;

	/**
	 * 记录认证日志
	 * @param operatorUser 执行人
	 * @param ipAddress 执行人ip地址
	 * @param healthId health编号
	 * @param fingerprintId 指纹信息编号
	 * @param verifyState 认证信息
	 */
	public void verifyLog(User operatorUser, String ipAddress,String healthId,String fingerprintId,String verifyState) {
		FingerprintVerifyLogEntity fingerprintVerifyEntity = new FingerprintVerifyLogEntity();
		fingerprintVerifyEntity.setFingerprintId(fingerprintId);
		fingerprintVerifyEntity.setHealthId(healthId);
		fingerprintVerifyEntity.setSubmitIpAddress(ipAddress);
		fingerprintVerifyEntity.setSubmitUserId(operatorUser.getId());
		fingerprintVerifyEntity.setVeriftState(verifyState);
		try {
			fingerprintVerifyLogDao.save(fingerprintVerifyEntity);
		} catch (Throwable e) {
			LOG.error("fingerprintVerifyLogDao.save error", e);
		}
	}

	public HealthFingerprintEntity getFingerprintInfoByHealthId(String healthId) {
		HealthFingerprintEntity healthFingerprintEntity = healthFingerprintDao.queryByHealthId(healthId);
		return healthFingerprintEntity;
	}

	public HealthFingerprintEntity getFingerprintInfoById(String id) {
		return this.healthFingerprintDao.findOne(id);
	}

}
