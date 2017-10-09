package com.esite.ops.health.service;

import com.esite.framework.security.entity.Customer;

public interface IOldPersonWdVerifyLogService {

	public void log(String verifyId, String verifyState, Customer customer);

}
