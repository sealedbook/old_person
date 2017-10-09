package com.esite.ops.oldperson.service;

import com.esite.ops.oldperson.entity.OldPersonEntity;

public interface IOldPersonSecurityService {
	public void addSystemUser(OldPersonEntity oldPerson);
	
	public void deleteSystemUser(OldPersonEntity oldPerson);
}
