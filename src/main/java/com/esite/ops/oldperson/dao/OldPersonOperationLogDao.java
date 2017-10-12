package com.esite.ops.oldperson.dao;

import org.springframework.data.repository.CrudRepository;

import com.esite.ops.oldperson.entity.OldPersonSystemLogEntity;

public interface OldPersonOperationLogDao extends CrudRepository<OldPersonSystemLogEntity, String>  {

}
