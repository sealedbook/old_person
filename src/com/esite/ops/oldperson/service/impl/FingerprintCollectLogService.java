package com.esite.ops.oldperson.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.oldperson.dao.FingerprintCollectLogDao;
import com.esite.ops.oldperson.entity.FingerprintCollectEntity;
import com.esite.ops.oldperson.entity.FingerprintCollectLogEntity;

@Service("fingerprintCollectLogService")
public class FingerprintCollectLogService {
	@Autowired
	private FingerprintCollectLogDao fingerprintCollectLogDao;
	
	public void log(FingerprintCollectEntity dbFingerprintCollectEntity,Customer customer) {
		FingerprintCollectLogEntity fingerprintCollectLog = new FingerprintCollectLogEntity();
		fingerprintCollectLog.setFinger(dbFingerprintCollectEntity.getFinger());
		fingerprintCollectLog.setFingerprintCharOne(dbFingerprintCollectEntity.getFingerprintCharOne());
		fingerprintCollectLog.setFingerprintCharTwo(dbFingerprintCollectEntity.getFingerprintCharTwo());
		fingerprintCollectLog.setFingerprintCharThree(dbFingerprintCollectEntity.getFingerprintCharThree());
		
		fingerprintCollectLog.setFingerprintPhotoOne(dbFingerprintCollectEntity.getFingerprintPhotoOne());
		fingerprintCollectLog.setFingerprintPhotoTwo(dbFingerprintCollectEntity.getFingerprintPhotoTwo());
		fingerprintCollectLog.setFingerprintPhotoThree(dbFingerprintCollectEntity.getFingerprintPhotoThree());
		
		fingerprintCollectLog.setFingerprintTemplate(dbFingerprintCollectEntity.getFingerprintTemplate());
		fingerprintCollectLog.setFingerVerifyState(dbFingerprintCollectEntity.getFingerVerifyState());
		fingerprintCollectLog.setHand(dbFingerprintCollectEntity.getHand());
		fingerprintCollectLog.setInsertDateTime(new Date());
		fingerprintCollectLog.setInsertIpAddress(customer.getIp());
		fingerprintCollectLog.setInsertUserName(customer.getUser().getShowName());
		fingerprintCollectLog.setOldPersonId(dbFingerprintCollectEntity.getOldPersonId());
		fingerprintCollectLogDao.save(fingerprintCollectLog);
	}

}
