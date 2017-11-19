/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package com.esite.ops.health.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import com.esite.framework.util.JpaLikeQueryHelper;
import com.esite.framework.util.StringHelper;
import com.esite.ops.health.dao.HealthMissDao;
import com.esite.ops.health.entity.HealthMissEntity;
import com.esite.ops.health.entity.HealthMissQueryEntity;
import com.esite.ops.oldperson.entity.OldPersonEntity;

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
            HealthMissEntity healthMissDB = healthMissDao
                .queryByOldPersonIdAndCycleId(healthMissEntity.getOldPerson().getId(), healthMissEntity.getCycle().getId());
            healthMissEntity.setId(healthMissDB.getId());
            healthMissDao.save(healthMissEntity);
        }
    }

    public List<HealthMissEntity> fetchMiss(String oldPersonId) {
        return this.healthMissDao.queryByOldPersonId(oldPersonId);
    }

    public Page<HealthMissEntity> fetchMiss(String oldPersonId, Pageable instance) {
        return this.healthMissDao.queryByOldPersonId(oldPersonId, instance);
    }


    public Page<HealthMissEntity> queryMiss(final HealthMissQueryEntity healthMissQueryEntity, Pageable instance) {
        return healthMissDao.findAll(new Specification<HealthMissEntity>() {
            @Override
            public Predicate toPredicate(Root<HealthMissEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();

                if (StringHelper.isNotEmpty(healthMissQueryEntity.getCycleId())) {
                    expressions.add(cb.equal(root.<String>get("cycle").get("id"), healthMissQueryEntity.getCycleId()));

                }

                if (StringHelper.isNotEmpty(healthMissQueryEntity.getOldPersonId())) {
                    expressions.add(cb.equal(root.<String>get("oldPerson").get("id"), healthMissQueryEntity.getOldPersonId()));
                }
                //query.orderBy(cb.desc(root.get("miss_date")));
                return predicate;
            }
        }, instance);
    }
}
