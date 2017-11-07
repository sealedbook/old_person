/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package com.esite.ops.health.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.esite.ops.health.entity.HealthMissEntity;

/**
 * @author shitianshu on 2017/11/6 下午9:08.
 */
public interface HealthMissDao extends CrudRepository<HealthMissEntity, String> {

    List<HealthMissEntity> queryByOldPersonId(String oldPersonId);

    Page<HealthMissEntity> queryByOldPersonId(String oldPersonId, Pageable instance);

    HealthMissEntity queryByOldPersonIdAndCycleId(String oldPersonId, String cycleId);

}
