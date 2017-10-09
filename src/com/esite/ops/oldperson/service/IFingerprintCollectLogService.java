package com.esite.ops.oldperson.service;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.oldperson.entity.FingerprintCollectEntity;

public interface IFingerprintCollectLogService {

	public void log(FingerprintCollectEntity dbFingerprintCollectEntity,Customer customer);

}
