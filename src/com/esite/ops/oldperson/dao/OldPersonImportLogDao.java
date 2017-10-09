package com.esite.ops.oldperson.dao;

import org.springframework.data.repository.CrudRepository;

import com.esite.ops.oldperson.entity.OldPersonImportLogEntity;

public interface OldPersonImportLogDao extends CrudRepository<OldPersonImportLogEntity, String> {

}
