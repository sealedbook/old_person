package com.esite.ops.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esite.ops.health.dao.HealthPhotoDao;
import com.esite.ops.health.entity.HealthPhotoEntity;

@Service("healthPhotoService")
public class HealthPhotoService {

	@Autowired
	private HealthPhotoDao healthPhotoDao;
	
	public List<HealthPhotoEntity> getPhotoCollectionByHealthId(String healthId) {
		return healthPhotoDao.queryByHealthId(healthId);
	}

	public HealthPhotoEntity getPhotoCollectionById(String id) {
		return this.healthPhotoDao.findOne(id);
	}

}
