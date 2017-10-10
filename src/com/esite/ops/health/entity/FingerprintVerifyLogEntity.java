package com.esite.ops.health.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="HEALTH_FINGERPRINT_VERIFY_LOG")
public class FingerprintVerifyLogEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(generator="system-uuid")
	//@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="HEALTH_ID")
	private String healthId;
	
	@Column(name="FINGERPRINT_ID")
	private String fingerprintId;
	
	@Column(name="VERIFT_STATE")
	private String veriftState;
	
	@Column(name="SUBMIT_USER_ID")
	private String submitUserId;
	
	@Column(name="SUBMIT_DATE_TIME",updatable=false)
	private Date submitDateTime = new Date();
	
	@Column(name="SUBMIT_IP_ADDRESS")
	private String submitIpAddress;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHealthId() {
		return healthId;
	}

	public void setHealthId(String healthId) {
		this.healthId = healthId;
	}

	public String getFingerprintId() {
		return fingerprintId;
	}

	public void setFingerprintId(String fingerprintId) {
		this.fingerprintId = fingerprintId;
	}

	public String getVeriftState() {
		return veriftState;
	}

	public void setVeriftState(String veriftState) {
		this.veriftState = veriftState;
	}

	public String getSubmitUserId() {
		return submitUserId;
	}

	public void setSubmitUserId(String submitUserId) {
		this.submitUserId = submitUserId;
	}

	public Date getSubmitDateTime() {
		return submitDateTime;
	}

	public void setSubmitDateTime(Date submitDateTime) {
		this.submitDateTime = submitDateTime;
	}

	public String getSubmitIpAddress() {
		return submitIpAddress;
	}

	public void setSubmitIpAddress(String submitIpAddress) {
		this.submitIpAddress = submitIpAddress;
	}
	
	
	
}
