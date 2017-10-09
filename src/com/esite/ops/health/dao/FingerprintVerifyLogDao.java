package com.esite.ops.health.dao;

import org.springframework.data.repository.CrudRepository;

import com.esite.ops.health.entity.FingerprintVerifyLogEntity;

public interface FingerprintVerifyLogDao extends CrudRepository<FingerprintVerifyLogEntity, String> {

}
