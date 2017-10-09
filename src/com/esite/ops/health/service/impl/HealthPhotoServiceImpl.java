package com.esite.ops.health.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.esite.ops.health.dao.HealthPhotoDao;
import com.esite.ops.health.entity.HealthPhotoEntity;
import com.esite.ops.health.service.IHealthPhotoService;

public class HealthPhotoServiceImpl implements IHealthPhotoService {

	@Autowired
	private HealthPhotoDao healthPhotoDao;
	
	@Override
	public List<HealthPhotoEntity> getPhotoCollectionByHealthId(String healthId) {
		return healthPhotoDao.queryByHealthId(healthId);
	}

	@Override
	public HealthPhotoEntity getPhotoCollectionById(String id) {
		return this.healthPhotoDao.findOne(id);
	}

}
