/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package com.esite.ops.health.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author shitianshu on 2017/11/6 下午7:08.
 */
@Entity
@Table(name="health_miss")
public class HealthMissEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name="old_person_id")
    private String oldPersonId;

    @Column(name="cycle_id")
    private String cycleId;

    @Column(name="miss_cause")
    private String missCause;

    @Column(name="miss_date")
    private Date missDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOldPersonId() {
        return oldPersonId;
    }

    public void setOldPersonId(String oldPersonId) {
        this.oldPersonId = oldPersonId;
    }

    public String getCycleId() {
        return cycleId;
    }

    public void setCycleId(String cycleId) {
        this.cycleId = cycleId;
    }

    public String getMissCause() {
        return missCause;
    }

    public void setMissCause(String missCause) {
        this.missCause = missCause;
    }

    public Date getMissDate() {
        return missDate;
    }

    public void setMissDate(Date missDate) {
        this.missDate = missDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HealthMissEntity{");
        sb.append("id='").append(id).append('\'');
        sb.append(", oldPersonId='").append(oldPersonId).append('\'');
        sb.append(", cycleId='").append(cycleId).append('\'');
        sb.append(", missCause='").append(missCause).append('\'');
        sb.append(", missDate=").append(missDate);
        sb.append('}');
        return sb.toString();
    }
}
