/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package com.esite.ops.health.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.esite.framework.util.PagerRequest;
import com.esite.framework.util.PagerResponse;
import com.esite.ops.health.entity.HealthInfoEntity;
import com.esite.ops.health.entity.HealthMissEntity;
import com.esite.ops.health.entity.HealthMissQueryEntity;
import com.esite.ops.health.service.impl.HealthMissService;

/**
 * @author shitianshu on 2017/11/18 下午8:29.
 */
@Controller
public class MissController {

    /**
     * 日志
     */
    private static final Logger LOG = LoggerFactory.getLogger(MissController.class);

    @Resource
    private HealthMissService healthMissService;

    @RequestMapping(value = "/miss/list/page")
    public String missListPage() {
        return "/health/miss";
    }

    @RequestMapping(value = "/miss/list")
    @ResponseBody
    public PagerResponse<HealthMissEntity> fetchMiss(HealthMissQueryEntity healthMissQueryEntity, PagerRequest pageRequest) {
        Page<HealthMissEntity> page = this.healthMissService.queryMiss(healthMissQueryEntity, pageRequest.getInstance());
        return new PagerResponse<HealthMissEntity>(page);
    }

}
