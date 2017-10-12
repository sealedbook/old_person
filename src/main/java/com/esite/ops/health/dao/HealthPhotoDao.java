package com.esite.ops.health.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.esite.ops.health.entity.HealthPhotoEntity;

public interface HealthPhotoDao extends CrudRepository<HealthPhotoEntity, java.lang.String> {

	public List<HealthPhotoEntity> queryByHealthId(String healthId);

}
