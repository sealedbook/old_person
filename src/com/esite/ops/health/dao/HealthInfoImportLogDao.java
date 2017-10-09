package com.esite.ops.health.dao;

import org.springframework.data.repository.CrudRepository;

import com.esite.ops.health.entity.HealthInfoImportLogEntity;

public interface HealthInfoImportLogDao extends CrudRepository<HealthInfoImportLogEntity, String> {

}
