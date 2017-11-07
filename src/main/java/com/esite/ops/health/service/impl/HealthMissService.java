/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package com.esite.ops.health.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.esite.ops.health.dao.HealthMissDao;
import com.esite.ops.health.entity.HealthMissEntity;

/**
 * @author shitianshu on 2017/11/6 下午8:27.
 */
@Service("healthMissService")
public class HealthMissService {

    @Resource
    private HealthMissDao healthMissDao;

    public void saveOrUpdateMiss(HealthMissEntity healthMissEntity) {
        try {
            healthMissDao.save(healthMissEntity);
        } catch (JpaSystemException e) {
            HealthMissEntity healthMissDB = healthMissDao.queryByOldPersonIdAndCycleId(healthMissEntity.getOldPersonId(), healthMissEntity.getCycleId());
            healthMissEntity.setId(healthMissDB.getId());
            healthMissDao.save(healthMissEntity);
        }
    }

    public List<HealthMissEntity> fetchMiss(String oldPersonId) {
        return this.healthMissDao.queryByOldPersonId(oldPersonId);
    }

    public Page<HealthMissEntity> fetchMiss(String oldPersonId, Pageable instance) {
        return this.healthMissDao.queryByOldPersonId(oldPersonId,instance);
    }
}
