package com.esite.framework.file.service;

import com.esite.framework.file.entity.PersonPhotoFile;

public interface FileService {
	public PersonPhotoFile findByPersonId(String id);
	
	public PersonPhotoFile save(PersonPhotoFile file);

	public void deleteByPersonId(String id);

}
