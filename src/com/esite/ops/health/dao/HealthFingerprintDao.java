package com.esite.ops.health.dao;

import org.springframework.data.repository.CrudRepository;

import com.esite.ops.health.entity.HealthFingerprintEntity;

public interface HealthFingerprintDao extends CrudRepository<HealthFingerprintEntity, String> {

	public HealthFingerprintEntity queryByHealthId(String healthId);

}
