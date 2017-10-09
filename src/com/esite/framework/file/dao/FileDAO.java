package com.esite.framework.file.dao;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.esite.framework.file.entity.PersonPhotoFile;

public interface FileDAO extends CrudRepository<PersonPhotoFile, String>{
	public PersonPhotoFile findByPersonId(String id);

	@Modifying
	@Query("delete from PersonPhotoFile t where t.personId=?1")
	@Transactional
	public void deleteByPersonId(String id);

}
