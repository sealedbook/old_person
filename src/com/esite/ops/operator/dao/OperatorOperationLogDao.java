package com.esite.ops.operator.dao;

import org.springframework.data.repository.CrudRepository;

import com.esite.ops.operator.entity.OperatorOperationLogEntity;

public interface OperatorOperationLogDao extends CrudRepository<OperatorOperationLogEntity, String> {

}
