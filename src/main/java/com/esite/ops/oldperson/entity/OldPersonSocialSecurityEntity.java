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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="OLD_PERSON_SOCIAL_SECURITY")
public class OldPersonSocialSecurityEntity {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid",strategy="uuid")
	@Column(name="ID",updatable=false)
	private String id;
	
	@Column(name="BATCH_ID")
	private String batchId;
	@Column(name="OLD_BATCH_ID")
	private String oldBatchId = "";
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="OLD_PERSON_ID")
	private OldPersonEntity oldPerson; 
	
	@Column(name="SEND_DATE")
	private Date sendDate;
	
	@Column(name="SEND_STATUS")
	private String sendStatus;
	
	@Column(name="INSERT_DATE_TIME",updatable=false)
	private Date insertDateTime = new Date();
	
	@Column(name="STATUS")
	private String status = "insert";
	
	@Column(name="SOCIAL_STOP_SEND_CAUSE")
	private String socialStopSendCause;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public OldPersonEntity getOldPerson() {
		return oldPerson;
	}

	public void setOldPerson(OldPersonEntity oldPerson) {
		this.oldPerson = oldPerson;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Date getInsertDateTime() {
		return insertDateTime;
	}

	public void setInsertDateTime(Date insertDateTime) {
		this.insertDateTime = insertDateTime;
	}
	
	public String getName() {
		return this.oldPerson.getName();
	}
	public String getIdCard() {
		return this.oldPerson.getIdCard();
	}
	public String getSocialNumber() {
		return this.oldPerson.getSocialNumber();
	}
	public int getAge() {
		return this.oldPerson.getAge();
	}
	public String getArea() {
		return this.oldPerson.getArea().getName();
	}
	public int getSex() {
		return this.oldPerson.getSex();
	}
	public int getType() {
		return this.oldPerson.getType();
	}

	public String getOldBatchId() {
		return oldBatchId;
	}

	public void setOldBatchId(String oldBatchId) {
		this.oldBatchId = oldBatchId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSocialStopSendCause() {
		return socialStopSendCause;
	}

	public void setSocialStopSendCause(String socialStopSendCause) {
		this.socialStopSendCause = socialStopSendCause;
	}
	
}
