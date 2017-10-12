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
@Table(name="old_person_wd_verify_log")
public class OldPersonWdVerifyLogEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@GeneratedValue(generator="system-uuid")
	//@GenericGenerator(name="system-uuid",strategy="uuid")
	private String id;
	
	@Column(name="verify_id")
	private String verifyId;
	
	@Column(name="verify_state")
	private String verifyState;
	
	@Column(name="submit_user_name")
	private String submitUserName;
	
	@Column(name="submit_user_ip_address")
	private String submitUserIpAddress;
	
	@Column(name="submit_date_time")
	private Date submitDateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVerifyId() {
		return verifyId;
	}

	public void setVerifyId(String verifyId) {
		this.verifyId = verifyId;
	}

	public String getVerifyState() {
		return verifyState;
	}

	public void setVerifyState(String verifyState) {
		this.verifyState = verifyState;
	}

	public String getSubmitUserName() {
		return submitUserName;
	}

	public void setSubmitUserName(String submitUserName) {
		this.submitUserName = submitUserName;
	}

	public String getSubmitUserIpAddress() {
		return submitUserIpAddress;
	}

	public void setSubmitUserIpAddress(String submitUserIpAddress) {
		this.submitUserIpAddress = submitUserIpAddress;
	}

	public Date getSubmitDateTime() {
		return submitDateTime;
	}

	public void setSubmitDateTime(Date submitDateTime) {
		this.submitDateTime = submitDateTime;
	}
	
}
