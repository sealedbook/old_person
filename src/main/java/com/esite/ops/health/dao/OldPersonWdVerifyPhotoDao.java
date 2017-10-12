package com.esite.ops.health.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.esite.ops.health.entity.OldPersonWdVerifyInfoEntity;
import com.esite.ops.health.entity.OldPersonWdVerifyPhotoEntity;

public interface OldPersonWdVerifyPhotoDao extends CrudRepository<OldPersonWdVerifyPhotoEntity, String> {

	public List<OldPersonWdVerifyPhotoEntity> queryByOldPersonVerifyId(String id);


}
