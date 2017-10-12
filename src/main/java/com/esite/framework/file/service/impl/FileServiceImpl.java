package com.esite.framework.file.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.esite.framework.file.dao.FileDAO;
import com.esite.framework.file.entity.PersonPhotoFile;
import com.esite.framework.file.service.FileService;

public class FileServiceImpl implements FileService {
	@Autowired
	private FileDAO fileDAO;
	
	public PersonPhotoFile findByPersonId(String id) {
		return fileDAO.findByPersonId(id);
	}

	@Override
	public PersonPhotoFile save(PersonPhotoFile file) {
		return this.fileDAO.save(file);
	}

	@Override
	public void deleteByPersonId(String id) {
		fileDAO.deleteByPersonId(id);
	}

}
