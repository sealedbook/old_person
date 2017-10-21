package com.esite.ops.oldperson.entity;

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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.esite.framework.organize.entity.OrganizeEntity;
import com.esite.framework.util.IdentityCardHelper;

@Entity
@Table(name = "OLD_PERSON")
@JsonIgnoreProperties(value = {"photo"})
public class OldPersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name = "";

    @Column(name = "NAME_SPELL")
    private String nameSpell;

    @Column(name = "ID_CARD")
    private String idCard = "";

    @Column(name = "SEX")
    private int sex;

    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Column(name = "NATIONALITY")
    private String nationality;

    /**
     * 老年人建模状态
     */
    @Column(name = "modeling_status")
    private String modelingStatus = "none";

    /**
     * 老年人发放状态
     */
    @Column(name = "ff_status")
    private String ffStatus = "ffStatus01";

    /**
     * 老年人类型
     */
    @Column(name = "TYPE")
    private int type;

    /**
     * 电话号码
     */
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    /**
     * 家庭住址
     */
    @Column(name = "HOME_ADDRESS")
    private String homeAddress;

    @Column(name = "BATCH_ID", updatable = false)
    private String batchId = "";

    @Column(name = "ROOT_AREA_ID")
    private String rootAreaId = "";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AREA_ID")
    private OrganizeEntity area;

    //////////////////////////社保信息/////////////////////////////
    /**
     * 社保发放状态
     */
    @Column(name = "SOCIAL_STATUS")
    private String socialStatus;

    /**
     * 社保停发暂停状态
     */
    @Column(name = "SOCIAL_STOP_SEND_CAUSE")
    private String socialStopSendCause;

    /**
     * 社保号
     */
    @Column(name = "SOCIAL_NUMBER")
    private String socialNumber = "";

    /**
     * 缴纳社保时间
     */
    @Column(name = "JNSBRQ")
    @DateTimeFormat(iso = ISO.DATE)
    private Date jnsbrq;

    /**
     * 退休日期
     */
    @Column(name = "TXRQ")
    @DateTimeFormat(iso = ISO.DATE)
    private Date txrq;

    /**
     * 领取社保日期
     */
    @Column(name = "LQSBRQ")
    @DateTimeFormat(iso = ISO.DATE)
    private Date lqsbrq;

    /**
     * 身份类型
     */
    @Column(name = "SFLX")
    private String sflx;

    /**
     * 工作单位
     */
    @Column(name = "WORK_UNIT")
    private String workUnit = "";

    @Column(name = "SYS_INSERT_DATETIME", updatable = false)
    private Date sysInsertDatetime = new Date();

    @Column(name = "STATUS")
    private String status = "";

    @Column(name = "died_time")
    private Date diedTime;

    @Column(name = "died_location")
    private String diedLocation;

    @Column(name = "died_cause")
    private String diedCause;

    /**
     * 身高
     */
    @Column(name = "height")
    private int height;

    /**
     * 体重
     */
    @Column(name = "weight")
    private int weight;

    @Column(name = "photo_key")
    private String photoKey;

    /**
     * 原始基线队列编号
     */
    @Column(name = "base_queue_code")
    private String baseQueueCode;

    /**
     * 基线队列调查时间
     */
    @Column(name = "base_queue_time")
    private Date baseQueueTime;

    /**
     * 基线队列调查时住址
     */
    @Column(name = "base_queue_address")
    private String baseQueueAddress;

    /**
     * 转换后的基线队列编号
     */
    @Column(name = "convert_base_code")
    private String convertBaseCode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrganizeEntity getArea() {
        return area;
    }

    public void setArea(OrganizeEntity area) {
        this.area = area;
    }

    public String getSocialNumber() {
        return socialNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public void setSocialNumber(String socialNumber) {
        this.socialNumber = socialNumber;
    }

    public int getAge() {
        return IdentityCardHelper.getAge(idCard);
    }

    public Date getSysInsertDatetime() {
        return sysInsertDatetime;
    }

    public void setSysInsertDatetime(Date sysInsertDatetime) {
        this.sysInsertDatetime = sysInsertDatetime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        idCard = IdentityCardHelper.get18idCard(idCard);
        this.idCard = idCard;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNameSpell() {
        return nameSpell;
    }

    public void setNameSpell(String nameSpell) {
        this.nameSpell = nameSpell;
    }

    public String getPhotoKey() {
        return photoKey;
    }

    public void setPhotoKey(String photoKey) {
        this.photoKey = photoKey;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getRootAreaId() {
        return rootAreaId;
    }

    public void setRootAreaId(String rootAreaId) {
        this.rootAreaId = rootAreaId;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getSflx() {
        return sflx;
    }

    public void setSflx(String sflx) {
        this.sflx = sflx;
    }

    public Date getJnsbrq() {
        return jnsbrq;
    }

    public void setJnsbrq(Date jnsbrq) {
        this.jnsbrq = jnsbrq;
    }

    public Date getTxrq() {
        return txrq;
    }

    public void setTxrq(Date txrq) {
        this.txrq = txrq;
    }

    public Date getLqsbrq() {
        return lqsbrq;
    }

    public void setLqsbrq(Date lqsbrq) {
        this.lqsbrq = lqsbrq;
    }

    public String getFfStatus() {
        return ffStatus;
    }

    public void setFfStatus(String ffStatus) {
        this.ffStatus = ffStatus;
    }

    public String getSocialStatus() {
        return socialStatus;
    }

    public void setSocialStatus(String socialStatus) {
        this.socialStatus = socialStatus;
    }

    public String getSocialStopSendCause() {
        return socialStopSendCause;
    }

    public void setSocialStopSendCause(String socialStopSendCause) {
        this.socialStopSendCause = socialStopSendCause;
    }

    public String getModelingStatus() {
        return modelingStatus;
    }

    public void setModelingStatus(String modelingStatus) {
        this.modelingStatus = modelingStatus;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof OldPersonEntity) {
            OldPersonEntity oldPerson = (OldPersonEntity) obj;
            if (oldPerson.getName().equals(this.name) && oldPerson.getIdCard().equals(this.idCard) && oldPerson
                .getSocialNumber().equals(this.socialNumber)) {
                return true;
            }
        }
        return false;
    }

    public Date getDiedTime() {
        return diedTime;
    }

    public void setDiedTime(Date diedTime) {
        this.diedTime = diedTime;
    }

    public String getDiedLocation() {
        return diedLocation;
    }

    public void setDiedLocation(String diedLocation) {
        this.diedLocation = diedLocation;
    }

    public String getDiedCause() {
        return diedCause;
    }

    public void setDiedCause(String diedCause) {
        this.diedCause = diedCause;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getBaseQueueCode() {
        return baseQueueCode;
    }

    public void setBaseQueueCode(String baseQueueCode) {
        this.baseQueueCode = baseQueueCode;
    }

    public Date getBaseQueueTime() {
        return baseQueueTime;
    }

    public void setBaseQueueTime(Date baseQueueTime) {
        this.baseQueueTime = baseQueueTime;
    }

    public String getBaseQueueAddress() {
        return baseQueueAddress;
    }

    public void setBaseQueueAddress(String baseQueueAddress) {
        this.baseQueueAddress = baseQueueAddress;
    }

    public String getConvertBaseCode() {
        return convertBaseCode;
    }

    public void setConvertBaseCode(String convertBaseCode) {
        this.convertBaseCode = convertBaseCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OldPersonEntity{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", nameSpell='").append(nameSpell).append('\'');
        sb.append(", idCard='").append(idCard).append('\'');
        sb.append(", sex=").append(sex);
        sb.append(", birthday=").append(birthday);
        sb.append(", nationality='").append(nationality).append('\'');
        sb.append(", modelingStatus='").append(modelingStatus).append('\'');
        sb.append(", ffStatus='").append(ffStatus).append('\'');
        sb.append(", type=").append(type);
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", homeAddress='").append(homeAddress).append('\'');
        sb.append(", batchId='").append(batchId).append('\'');
        sb.append(", rootAreaId='").append(rootAreaId).append('\'');
        sb.append(", area=").append(area);
        sb.append(", socialStatus='").append(socialStatus).append('\'');
        sb.append(", socialStopSendCause='").append(socialStopSendCause).append('\'');
        sb.append(", socialNumber='").append(socialNumber).append('\'');
        sb.append(", jnsbrq=").append(jnsbrq);
        sb.append(", txrq=").append(txrq);
        sb.append(", lqsbrq=").append(lqsbrq);
        sb.append(", sflx='").append(sflx).append('\'');
        sb.append(", workUnit='").append(workUnit).append('\'');
        sb.append(", sysInsertDatetime=").append(sysInsertDatetime);
        sb.append(", status='").append(status).append('\'');
        sb.append(", diedTime=").append(diedTime);
        sb.append(", diedLocation='").append(diedLocation).append('\'');
        sb.append(", diedCause='").append(diedCause).append('\'');
        sb.append(", height=").append(height);
        sb.append(", weight=").append(weight);
        sb.append(", photoKey='").append(photoKey).append('\'');
        sb.append(", baseQueueCode='").append(baseQueueCode).append('\'');
        sb.append(", baseQueueTime=").append(baseQueueTime);
        sb.append(", baseQueueAddress='").append(baseQueueAddress).append('\'');
        sb.append(", convertBaseCode='").append(convertBaseCode).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
