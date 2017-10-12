package com.esite.ops.health.dao;

import org.springframework.data.repository.CrudRepository;

import com.esite.ops.health.entity.HealthInfoImportErrorLogEntity;

public interface HealthInfoImportErrorLogDao extends CrudRepository<HealthInfoImportErrorLogEntity, String> {

}
