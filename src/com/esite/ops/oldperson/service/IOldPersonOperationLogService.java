package com.esite.ops.oldperson.service;

import com.esite.framework.security.entity.Customer;
import com.esite.ops.oldperson.entity.OldPersonEntity;

public interface IOldPersonOperationLogService {

	public void deleteLog(OldPersonEntity oldPerson, Customer customer);

	public void addLog(OldPersonEntity oldPerson, Customer customer);

	public void diedLog(OldPersonEntity oldPerson, Customer customer);

	public void undoLog(OldPersonEntity oldPerson, Customer customer);

}
