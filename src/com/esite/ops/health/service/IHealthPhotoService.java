package com.esite.ops.health.service;

import java.util.List;

import com.esite.ops.health.entity.HealthPhotoEntity;

public interface IHealthPhotoService {

	public List<HealthPhotoEntity> getPhotoCollectionByHealthId(String healthId);

	public HealthPhotoEntity getPhotoCollectionById(String id);

}
