/**
 * Copyright (c) 2017, TalkingData and/or its affiliates. All rights reserved.
 */
package com.esite.ops.health.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.esite.framework.organize.entity.OrganizeEntity;
import com.esite.ops.mission.entity.CycleEntity;
import com.esite.ops.oldperson.entity.OldPersonEntity;

/**
 * @author shitianshu on 2017/11/6 下午7:08.
 */
@Entity
@Table(name="health_miss")
public class HealthMissEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    //@Column(name="old_person_id")
    //private String oldPersonId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "old_person_id")
    private OldPersonEntity oldPerson;

    //@Column(name="cycle_id")
    //private String cycleId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cycle_id")
    private CycleEntity cycle;

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

    public OldPersonEntity getOldPerson() {
        return oldPerson;
    }

    public void setOldPerson(OldPersonEntity oldPerson) {
        this.oldPerson = oldPerson;
    }

    public CycleEntity getCycle() {

        return cycle;
    }

    public void setCycle(CycleEntity cycle) {
        this.cycle = cycle;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HealthMissEntity{");
        sb.append("id='").append(id).append('\'');
        sb.append(", oldPerson=").append(oldPerson);
        sb.append(", cycle=").append(cycle);
        sb.append(", missCause='").append(missCause).append('\'');
        sb.append(", missDate=").append(missDate);
        sb.append('}');
        return sb.toString();
    }
}
