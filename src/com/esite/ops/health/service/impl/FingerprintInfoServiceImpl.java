package com.esite.ops.health.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.esite.framework.user.entity.User;
import com.esite.ops.health.dao.FingerprintVerifyLogDao;
import com.esite.ops.health.dao.HealthFingerprintDao;
import com.esite.ops.health.entity.FingerprintVerifyLogEntity;
import com.esite.ops.health.entity.HealthFingerprintEntity;
import com.esite.ops.health.service.IFingerprintInfoService;

public class FingerprintInfoServiceImpl implements IFingerprintInfoService {

	@Autowired
	private FingerprintVerifyLogDao fingerprintVerifyLogDao;
	
	@Autowired
	private HealthFingerprintDao healthFingerprintDao;
	
	@Override
	public void verifyLog(User operatorUser, String ipAddress,String healthId,String fingerprintId,String verifyState) {
		FingerprintVerifyLogEntity fingerprintVerifyEntity = new FingerprintVerifyLogEntity();
		fingerprintVerifyEntity.setFingerprintId(fingerprintId);
		fingerprintVerifyEntity.setHealthId(healthId);
		fingerprintVerifyEntity.setSubmitIpAddress(ipAddress);
		fingerprintVerifyEntity.setSubmitUserId(operatorUser.getId());
		fingerprintVerifyEntity.setVeriftState(verifyState);
		fingerprintVerifyLogDao.save(fingerprintVerifyEntity);
	}

	@Override
	public HealthFingerprintEntity getFingerprintInfoByHealthId(String healthId) {
		HealthFingerprintEntity healthFingerprintEntity = healthFingerprintDao.queryByHealthId(healthId);
		return healthFingerprintEntity;
	}

	@Override
	public HealthFingerprintEntity getFingerprintInfoById(String id) {
		return this.healthFingerprintDao.findOne(id);
	}


}
