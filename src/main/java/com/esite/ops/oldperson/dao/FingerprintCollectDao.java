package com.esite.ops.oldperson.dao;

import org.springframework.data.repository.CrudRepository;

import com.esite.ops.oldperson.entity.FingerprintCollectEntity;

public interface FingerprintCollectDao extends CrudRepository<FingerprintCollectEntity, String> {

	public FingerprintCollectEntity queryByOldPersonId(String oldPersonId);

}
