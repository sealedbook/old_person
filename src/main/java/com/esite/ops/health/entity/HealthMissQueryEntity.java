/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package com.esite.ops.health.entity;

/**
 * @author shitianshu on 2017/11/18 下午8:41.
 */
public class HealthMissQueryEntity {

    private String cycleId;

    private String oldPersonId;

    public String getCycleId() {
        return cycleId;
    }

    public void setCycleId(String cycleId) {
        this.cycleId = cycleId;
    }

    public String getOldPersonId() {
        return oldPersonId;
    }

    public void setOldPersonId(String oldPersonId) {
        this.oldPersonId = oldPersonId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HealthMissQueryEntity{");
        sb.append("cycleId='").append(cycleId).append('\'');
        sb.append(", oldPersonId='").append(oldPersonId).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
